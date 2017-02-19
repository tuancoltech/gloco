package com.gloco.exercise.domain.usecase.getdoctors;

import android.support.annotation.NonNull;

import com.gloco.exercise.data.repository.doctor.DoctorDataSource;
import com.gloco.exercise.data.repository.doctor.source.DoctorRepository;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsRequestModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsResponseModel;
import com.gloco.exercise.domain.usecase.UseCase;
import com.google.common.base.Preconditions;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class GetDoctors extends UseCase<GetDoctors.RequestValue, GetDoctors.ResponseValue> {

    private final DoctorRepository mDoctorRepository;

    public GetDoctors(@NonNull DoctorRepository doctorRepository) {
        mDoctorRepository = Preconditions.checkNotNull(doctorRepository);
    }


    @Override
    protected void executeUseCase(RequestValue requestValues) {
        Preconditions.checkNotNull(requestValues);
        Preconditions.checkNotNull(mDoctorRepository);

        mDoctorRepository.getDoctors(requestValues.getRequestModel(),
                new DoctorDataSource.GetDoctorCallback() {
                    @Override
                    public void onGetDoctorSuccess(@NonNull GetDoctorsResponseModel
                                                           getDoctorsResponseModel) {
                        getUseCaseCallback().onSuccess(new ResponseValue
                                (getDoctorsResponseModel));
                    }

                    @Override
                    public void onGetDoctorFailed(int errorCode) {
                        getUseCaseCallback().onError(errorCode);
                    }
                });
    }

    public static final class RequestValue implements UseCase.RequestValues {

        private final GetDoctorsRequestModel mRequestModel;

        public RequestValue(@NonNull GetDoctorsRequestModel getDoctorsRequestModel) {
            mRequestModel = Preconditions.checkNotNull(getDoctorsRequestModel);
        }

        public GetDoctorsRequestModel getRequestModel() {
            return mRequestModel;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final GetDoctorsResponseModel mResponseModel;

        public ResponseValue(@NonNull GetDoctorsResponseModel getDoctorsResponseModel) {
            mResponseModel = Preconditions.checkNotNull(getDoctorsResponseModel);
        }

        public GetDoctorsResponseModel getResponse() {
            return mResponseModel;
        }

    }
}
