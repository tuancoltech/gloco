package com.gloco.exercise.data.repository.doctor;

import android.support.annotation.NonNull;

import com.gloco.exercise.domain.model.getdoctorlist.DoctorModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsRequestModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsResponseModel;

import java.util.List;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public interface DoctorDataSource {

    void getDoctors(@NonNull GetDoctorsRequestModel getDoctorsRequestModel,
                    GetDoctorCallback getDoctorCallback);

    void saveDoctors(@NonNull List<DoctorModel> doctorModels);

    interface GetDoctorCallback {

        void onGetDoctorSuccess(@NonNull GetDoctorsResponseModel getDoctorsResponseModel);

        void onGetDoctorFailed(int errorCode);

    }

}
