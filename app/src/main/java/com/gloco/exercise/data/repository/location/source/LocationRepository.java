package com.gloco.exercise.data.repository.location.source;

import android.support.annotation.NonNull;

import com.gloco.exercise.data.repository.location.LocationDataSource;
import com.gloco.exercise.domain.model.getlocation.GetLocationRequestModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationResponseModel;
import com.google.common.base.Preconditions;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class LocationRepository implements LocationDataSource {

    private static LocationRepository sLocationRepository = null;

    private final LocationDataSource mLocationLocalDataSource;

    private final LocationDataSource mLocationRemoteDataSource;

    private LocationRepository(@NonNull LocationDataSource locationLocalDataSource,
                               @NonNull LocationDataSource locationRemoteDataSource) {

        mLocationLocalDataSource = Preconditions.checkNotNull(locationLocalDataSource);
        mLocationRemoteDataSource = Preconditions.checkNotNull(locationRemoteDataSource);
    }

    /**
     * Returns a single access to {@link LocationRepository}
     *
     * @param locationLocalDataSource  Reference to the location local data source
     * @param locationRemoteDataSource Reference to the location remote data source
     * @return A singleton access point of {@link LocationRepository}
     */
    public static LocationRepository getInstance(@NonNull LocationDataSource
                                                         locationLocalDataSource,
                                                 @NonNull LocationDataSource
                                                         locationRemoteDataSource) {

        if (sLocationRepository == null) {
            synchronized (LocationRepository.class) {
                if (sLocationRepository == null) {
                    sLocationRepository = new LocationRepository(locationLocalDataSource,
                            locationRemoteDataSource);
                }
            }
        }
        return sLocationRepository;
    }

    @Override
    public void getLocation(@NonNull GetLocationRequestModel getLocationRequestModel,
                            GetLocationCallback getLocationCallback) {

        getLocationFromRemoteDataSource(getLocationRequestModel, getLocationCallback);

    }

    private void getLocationFromRemoteDataSource(@NonNull GetLocationRequestModel
                                                         getLocationRequestModel,
                                                 @NonNull final GetLocationCallback
                                                         getLocationCallback) {

        mLocationRemoteDataSource.getLocation(getLocationRequestModel,
                new GetLocationCallback() {
                    @Override
                    public void onGetLocationSuccess(@NonNull GetLocationResponseModel
                                                             getLocationResponseModel) {

                        getLocationCallback.onGetLocationSuccess(getLocationResponseModel);
                    }

                    @Override
                    public void onGetLocationFailed(int errorCode) {
                        getLocationCallback.onGetLocationFailed(errorCode);
                    }
                });
    }
}
