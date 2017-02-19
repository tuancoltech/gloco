package com.gloco.exercise.data.repository.location;

import android.support.annotation.NonNull;

import com.gloco.exercise.domain.model.getlocation.GetLocationRequestModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationResponseModel;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public interface LocationDataSource {

    void getLocation(GetLocationRequestModel getLocationRequestModel,
                     GetLocationCallback getLocationCallback);

    interface GetLocationCallback {

        void onGetLocationSuccess(@NonNull GetLocationResponseModel getLocationResponseModel);

        void onGetLocationFailed(int errorCode);

    }

}
