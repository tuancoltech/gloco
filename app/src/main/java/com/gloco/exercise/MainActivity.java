package com.gloco.exercise;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.gloco.exercise.data.network.GeofenceTrasitionService;
import com.gloco.exercise.data.network.NetworkUtils;
import com.gloco.exercise.dependencies.Injection;
import com.gloco.exercise.domain.model.getdoctorlist.DoctorModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsResponseModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationResponseModel;
import com.gloco.exercise.presentation.common.BaseActivity;
import com.gloco.exercise.presentation.doctorlist.DoctorActivity;
import com.gloco.exercise.presentation.homescreen.HomeContract;
import com.gloco.exercise.presentation.homescreen.HomePresenter;
import com.gloco.exercise.utils.ActivityUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener, HomeContract
        .View, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        android.location.LocationListener, ResultCallback {

    private static final long GEO_DURATION = 60 * 60 * 1000;

    private static final String GEOFENCE_REQ_ID = "geofence_req_id";

    private static final float GEOFENCE_RADIUS = 100.0f; // in meters

    private final int REQUEST_TYPE_DEFAULT = 1;

    private final String SESSION_ID_DEFAULT = "xxx";

    private final int GEOFENCE_REQ_CODE = 0;

    private HomeContract.Presenter mHomePresenter;

    private GoogleApiClient mGoogleApiClient;

    private LocationManager mLocationManager;

    private PendingIntent mGeoFencePendingIntent;

    public static Intent makeNotificationIntent(Context context, String m) {

        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        new HomePresenter(this,
                Injection.provideUseCaseHandler(),
                Injection.provideGetLocation(getApplicationContext()),
                Injection.provideGetDoctors(getApplicationContext()),
                Injection.provideSaveDoctors(getApplicationContext()));

        initViews();

        createGoogleApi();

        requestLocation();
    }

    /**
     * Initialize views in home screens
     */
    private void initViews() {

        findViewById(R.id.btn_get_location).setOnClickListener(this);
        findViewById(R.id.btn_get_doctors).setOnClickListener(this);
    }

    private void createGoogleApi() {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private PendingIntent createGeofencePendingIntent() {
        if (mGeoFencePendingIntent != null)
            return mGeoFencePendingIntent;

        Intent intent = new Intent(this, GeofenceTrasitionService.class);
        return PendingIntent.getService(
                this, GEOFENCE_REQ_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request) {
        if (checkPermission())
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    request,
                    createGeofencePendingIntent()
            ).setResultCallback(this);
    }

    // Check for permission to access Location
    private boolean checkPermission() {
        // Ask for permission if it wasn't granted yet
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mGoogleApiClient.disconnect();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_get_location:
                if (NetworkUtils.isConnectedWifi(this)) {
                    mHomePresenter.getLocation(REQUEST_TYPE_DEFAULT);
                } else {
                    Toast.makeText(this, getString(R.string.msg_only_work_with_wifi), Toast
                            .LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_get_doctors:
                mHomePresenter.getDoctors(REQUEST_TYPE_DEFAULT, SESSION_ID_DEFAULT);
                /*if (NetworkUtils.isConnectedCellular(this)) {
                    mHomePresenter.getDoctors(REQUEST_TYPE_DEFAULT, SESSION_ID_DEFAULT);
                } else {
                    Toast.makeText(this, getString(R.string.msg_only_work_with_cellular), Toast
                            .LENGTH_SHORT).show();
                }*/
                break;

            default:
                break;

        }

    }

    @Override
    public void setLoading(boolean isActive) {
        setLoadingDialog(isActive);
    }

    @Override
    public void showGetLocationSuccess(@NonNull GetLocationResponseModel getLocationResponseModel) {
        setLoading(false);

        startGeofence(Double.valueOf(getLocationResponseModel.getLatitude()),
                Double.valueOf(getLocationResponseModel.getLongitude()));
    }

    @Override
    public void showGetLocationFailed(int errorCode) {
        setLoading(false);
    }

    @Override
    public void showGetDoctorsSuccess(@NonNull GetDoctorsResponseModel getDoctorsResponseModel) {
        setLoading(false);

        List<DoctorModel> doctorList = getDoctorsResponseModel.getDoctors();
        if (doctorList != null && doctorList.size() > 0) {

            Bundle doctorBundle = new Bundle();
            doctorBundle.putParcelableArrayList(Const.BUNDLE_EXTRAS_DOCTORS, (ArrayList)
                    doctorList);

            ActivityUtils.goToActivityWithBundle(this, DoctorActivity.class, doctorBundle);
        } else {
            Toast.makeText(this, getString(R.string.blank_response), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showGetDoctorsFailed(int errorCode) {
        setLoading(false);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mHomePresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private Geofence createGeofence(double latitude, double longitude, float radius) {

        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion(latitude, longitude, radius)
                .setExpirationDuration(GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER
                        | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    private GeofencingRequest createGeofenceRequest(Geofence geofence) {

        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();
    }

    // Start Geofence creation process
    private void startGeofence(double latitude, double longitude) {
        Geofence geofence = createGeofence(latitude, longitude, GEOFENCE_RADIUS);
        GeofencingRequest geofenceRequest = createGeofenceRequest(geofence);
        addGeofence(geofenceRequest);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == Const.REQ_CODE_LOCATION) {
            if (permissions.length == 1 &&
                    permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            } else {
                Toast.makeText(this, getString(R.string.message_location_not_allow), Toast
                        .LENGTH_SHORT).show();
            }
        }
    }

    private void requestLocation() {

        if (mLocationManager != null) {
            boolean gpsIsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean networkIsEnabled = mLocationManager.isProviderEnabled(LocationManager
                    .NETWORK_PROVIDER);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission
                            .ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Const.REQ_CODE_LOCATION);
            } else {

                if (networkIsEnabled) {
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            Const.MIN_TIME_BW_UPDATES, Const
                                    .MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    android.location.Location location = mLocationManager.getLastKnownLocation
                            (LocationManager.NETWORK_PROVIDER);
                } else if (gpsIsEnabled) {
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            Const.MIN_TIME_BW_UPDATES, Const
                                    .MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                }

            }

        }
    }

    @Override
    public void onResult(@NonNull Result result) {

    }

    /**
     * Create and trigger Geofence
     *
     * */

}
