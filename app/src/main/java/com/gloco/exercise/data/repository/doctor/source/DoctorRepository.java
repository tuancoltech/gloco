package com.gloco.exercise.data.repository.doctor.source;

import android.support.annotation.NonNull;

import com.gloco.exercise.data.repository.doctor.DoctorDataSource;
import com.gloco.exercise.domain.model.getdoctorlist.DoctorModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsRequestModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsResponseModel;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class DoctorRepository implements DoctorDataSource {

    private static DoctorRepository sDoctorRepository = null;

    private final DoctorDataSource mDoctorLocalDataSource;

    private final DoctorDataSource mDoctorRemoteDataSource;

    private DoctorRepository(@NonNull DoctorDataSource doctorLocalDataSource,
                             @NonNull DoctorDataSource doctorRemoteDataSource) {

        mDoctorLocalDataSource = Preconditions.checkNotNull(doctorLocalDataSource);
        mDoctorRemoteDataSource = Preconditions.checkNotNull(doctorRemoteDataSource);
    }

    public static DoctorRepository getInstance(@NonNull DoctorDataSource doctorLocalDataSource,
                                               @NonNull DoctorDataSource doctorRemoteDataSource) {

        if (sDoctorRepository == null) {
            synchronized (DoctorRepository.class) {
                if (sDoctorRepository == null) {
                    sDoctorRepository = new DoctorRepository(doctorLocalDataSource,
                            doctorRemoteDataSource);
                }
            }
        }

        return sDoctorRepository;
    }

    @Override
    public void getDoctors(@NonNull GetDoctorsRequestModel getDoctorsRequestModel,
                           GetDoctorCallback getDoctorCallback) {

        getDoctorsFromRemoteDataSource(getDoctorsRequestModel, getDoctorCallback);
    }

    @Override
    public void saveDoctors(@NonNull List<DoctorModel> doctorModels) {
        mDoctorLocalDataSource.saveDoctors(doctorModels);
    }

    private void getDoctorsFromRemoteDataSource(@NonNull GetDoctorsRequestModel
                                                        getDoctorsRequestModel,
                                                @NonNull final GetDoctorCallback
                                                        getDoctorCallback) {

        mDoctorRemoteDataSource.getDoctors(getDoctorsRequestModel,
                new GetDoctorCallback() {
                    @Override
                    public void onGetDoctorSuccess(@NonNull GetDoctorsResponseModel
                                                           getDoctorsResponseModel) {
                        getDoctorCallback.onGetDoctorSuccess(getDoctorsResponseModel);
                    }

                    @Override
                    public void onGetDoctorFailed(int errorCode) {
                        getDoctorCallback.onGetDoctorFailed(errorCode);
                    }
                });
    }
}
