package com.gloco.exercise.presentation.homescreen;

import android.support.annotation.NonNull;

import com.gloco.exercise.domain.model.getdoctorlist.DoctorModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsResponseModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationResponseModel;
import com.gloco.exercise.presentation.common.BasePresenter;
import com.gloco.exercise.presentation.common.BaseView;

import java.util.List;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void setLoading(boolean isActive);

        void showGetLocationSuccess(@NonNull GetLocationResponseModel getLocationResponseModel);

        void showGetLocationFailed(int errorCode);

        void showGetDoctorsSuccess(@NonNull GetDoctorsResponseModel getDoctorsResponseModel);

        void showGetDoctorsFailed(int errorCode);

    }

    interface Presenter extends BasePresenter {

        void getLocation(int requestType);

        void getDoctors(int requestType, String sessionId);

        void saveDoctors(@NonNull List<DoctorModel> doctors);

    }

}
