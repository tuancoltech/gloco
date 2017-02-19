package com.gloco.exercise.data.repository.location.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gloco.exercise.data.repository.location.LocationDataSource;
import com.gloco.exercise.domain.model.getlocation.GetLocationRequestModel;
import com.google.common.base.Preconditions;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class LocationLocalDataSource implements LocationDataSource {

    private static LocationLocalDataSource sLocationLocalDataSource = null;

    private final Context mContext;

    private LocationLocalDataSource(@NonNull Context context) {
        mContext = Preconditions.checkNotNull(context);
    }

    /**
     * Return a singleton instance of LocationLocalDataSource for accessing the location data
     * from local database
     *
     * @param context The context which this data source is associated with
     * @return A LocationLocalDataSource singleton instance
     */
    public static LocationLocalDataSource getInstance(@NonNull Context context) {
        if (sLocationLocalDataSource == null) {
            synchronized (LocationLocalDataSource.class) {
                if (sLocationLocalDataSource == null) {
                    sLocationLocalDataSource = new LocationLocalDataSource(context);
                }
            }
        }
        return sLocationLocalDataSource;
    }

    @Override
    public void getLocation(@NonNull GetLocationRequestModel getLocationRequestModel,
                            GetLocationCallback getLocationCallback) {

    }
}
