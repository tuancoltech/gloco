package com.gloco.exercise.data.repository.location.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gloco.exercise.Const;
import com.gloco.exercise.data.network.ApiManager;
import com.gloco.exercise.data.repository.location.LocationDataSource;
import com.gloco.exercise.domain.model.getlocation.GetLocationRequestModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationResponseModel;
import com.google.common.base.Preconditions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class LocationRemoteDataSource implements LocationDataSource {

    private static LocationRemoteDataSource sLocationRemoteDataSource = null;

    private final Context mContext;

    private LocationRemoteDataSource(@NonNull Context context) {
        mContext = Preconditions.checkNotNull(context);
    }

    /**
     * Returns a singleton access to the remote data source of location data
     *
     * @param context A context which needs the access to this data source
     * @return A singleton instance of {@link LocationRemoteDataSource}
     */
    public static LocationRemoteDataSource getInstance(@NonNull Context context) {
        if (sLocationRemoteDataSource == null) {
            synchronized (LocationRemoteDataSource.class) {
                if (sLocationRemoteDataSource == null) {
                    sLocationRemoteDataSource = new LocationRemoteDataSource(context);
                }
            }
        }
        return sLocationRemoteDataSource;
    }

    @Override
    public void getLocation(@NonNull GetLocationRequestModel getLocationRequestModel,
                            final GetLocationCallback getLocationCallback) {

        ApiManager.getLocation(mContext, getLocationRequestModel,
                new Callback<GetLocationResponseModel>() {
                    @Override
                    public void onResponse(Call<GetLocationResponseModel> call,
                                           Response<GetLocationResponseModel> response) {

                        if (response != null && response.isSuccessful()
                                && response.body() != null) {

                            getLocationCallback.onGetLocationSuccess(response.body());
                        } else {

                            getLocationCallback.onGetLocationFailed(Const.ERROR_UNSUCCESSFUL);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetLocationResponseModel> call, Throwable t) {
                        getLocationCallback.onGetLocationFailed(Const.ERROR_UNKNOWN);
                        t.printStackTrace();
                    }
                });
    }
}
