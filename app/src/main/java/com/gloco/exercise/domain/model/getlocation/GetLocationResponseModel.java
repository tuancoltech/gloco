package com.gloco.exercise.domain.model.getlocation;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class GetLocationResponseModel {

    @SerializedName("latitude")
    private String mLatitude;

    @SerializedName("longitude")
    private String mLongitude;

    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

}
