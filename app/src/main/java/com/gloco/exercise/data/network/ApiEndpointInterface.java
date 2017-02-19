package com.gloco.exercise.data.network;

import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsRequestModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsResponseModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationRequestModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationResponseModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by "Tuan Nguyen" on 11/10/2016.
 */

interface ApiEndpointInterface {

    @POST("/getLocation")
    Call<GetLocationResponseModel> getLocation(@Body GetLocationRequestModel
                                                       getLocationRequestModel);

    @POST("/getDoctors")
    Call<GetDoctorsResponseModel> getDoctors(@Body GetDoctorsRequestModel
                                                     getDoctorsRequestModel);

}
