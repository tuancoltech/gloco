package com.gloco.exercise.domain.model.getdoctorlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class GetDoctorsRequestModel {

    @SerializedName("type")
    private final int mRequestType;

    @SerializedName("session_id")
    private final String mSessionId;

    public GetDoctorsRequestModel(int requestType, String sessionId) {
        mRequestType = requestType;
        mSessionId = sessionId;
    }

}
