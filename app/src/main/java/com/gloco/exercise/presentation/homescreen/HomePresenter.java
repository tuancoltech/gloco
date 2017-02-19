package com.gloco.exercise.presentation.homescreen;

import android.support.annotation.NonNull;

import com.gloco.exercise.Const;
import com.gloco.exercise.domain.model.getdoctorlist.DoctorModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsRequestModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationRequestModel;
import com.gloco.exercise.domain.usecase.UseCase;
import com.gloco.exercise.domain.usecase.UseCaseHandler;
import com.gloco.exercise.domain.usecase.getdoctors.GetDoctors;
import com.gloco.exercise.domain.usecase.getlocation.GetLocation;
import com.gloco.exercise.domain.usecase.savedoctors.SaveDoctors;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mView;

    private final UseCaseHandler mUseCaseHandler;

    private final GetLocation mGetLocation;

    private final GetDoctors mGetDoctors;

    private final SaveDoctors mSaveDoctors;

    public HomePresenter(@NonNull HomeContract.View view,
                         @NonNull UseCaseHandler useCaseHandler,
                         @NonNull GetLocation getLocation,
                         @NonNull GetDoctors getDoctors,
                         @NonNull SaveDoctors saveDoctors) {

        mView = Preconditions.checkNotNull(view);
        mUseCaseHandler = Preconditions.checkNotNull(useCaseHandler);
        mGetLocation = Preconditions.checkNotNull(getLocation);
        mGetDoctors = Preconditions.checkNotNull(getDoctors);
        mSaveDoctors = Preconditions.checkNotNull(saveDoctors);

        mView.setPresenter(this);
    }


    @Override
    public void getLocation(int requestType) {
        mView.setLoading(true);
        GetLocationRequestModel requestModel = new GetLocationRequestModel(requestType);

        mUseCaseHandler.execute(mGetLocation, new GetLocation.RequestValue(requestModel),
                new UseCase.UseCaseCallback<GetLocation.ResponseValue>() {
                    @Override
                    public void onSuccess(GetLocation.ResponseValue response) {

                        if (response != null && response.getResponseModel() != null) {
                            mView.showGetLocationSuccess(response.getResponseModel());
                        } else {
                            mView.showGetLocationFailed(Const.ERROR_UNSUCCESSFUL);
                        }
                    }

                    @Override
                    public void onError(int errorCode) {
                        mView.showGetLocationFailed(errorCode);
                    }
                });

    }

    @Override
    public void getDoctors(int requestType, String sessionId) {
        mView.setLoading(true);
        GetDoctorsRequestModel requestModel = new GetDoctorsRequestModel(requestType, sessionId);

        mUseCaseHandler.execute(mGetDoctors, new GetDoctors.RequestValue(requestModel),
                new UseCase.UseCaseCallback<GetDoctors.ResponseValue>() {
                    @Override
                    public void onSuccess(GetDoctors.ResponseValue response) {
                        if (response != null && response.getResponse() != null) {
                            mView.showGetDoctorsSuccess(response.getResponse());

                            List<DoctorModel> doctors = response.getResponse().getDoctors();
                            if (doctors != null && doctors.size() > 0) {
                                saveDoctors(doctors);
                            }
                        } else {
                            mView.showGetDoctorsFailed(Const.ERROR_UNSUCCESSFUL);
                        }
                    }

                    @Override
                    public void onError(int errorCode) {
                        mView.showGetDoctorsFailed(errorCode);
                    }
                });
    }

    @Override
    public void saveDoctors(@NonNull List<DoctorModel> doctors) {
        mUseCaseHandler.execute(mSaveDoctors, new SaveDoctors.RequestValue(doctors),
                new UseCase.UseCaseCallback<SaveDoctors.ResponseValue>() {
                    @Override
                    public void onSuccess(SaveDoctors.ResponseValue response) {

                    }

                    @Override
                    public void onError(int errorCode) {

                    }
                });
    }

    @Override
    public void start() {

    }
}
