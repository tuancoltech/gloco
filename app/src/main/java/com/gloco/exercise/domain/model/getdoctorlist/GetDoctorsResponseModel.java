package com.gloco.exercise.domain.model.getdoctorlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class GetDoctorsResponseModel {

    @SerializedName("doctor_list")
    private List<DoctorModel> mDoctors;

    public List<DoctorModel> getDoctors() {
        return mDoctors;
    }

}
