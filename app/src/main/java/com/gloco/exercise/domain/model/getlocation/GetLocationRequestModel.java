package com.gloco.exercise.domain.model.getlocation;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class GetLocationRequestModel {

    @SerializedName("type")
    private final int mRequestType;

    public GetLocationRequestModel(int requestType) {
        mRequestType = requestType;
    }

}
