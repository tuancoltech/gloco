package com.gloco.exercise.domain.usecase.savedoctors;

import android.support.annotation.NonNull;

import com.gloco.exercise.data.repository.doctor.source.DoctorRepository;
import com.gloco.exercise.domain.model.getdoctorlist.DoctorModel;
import com.gloco.exercise.domain.usecase.UseCase;
import com.google.common.base.Preconditions;

import java.util.List;

/**
 * Created by Tuan Nguyen on 2/20/2017.
 */

public class SaveDoctors extends UseCase<SaveDoctors.RequestValue, SaveDoctors.ResponseValue> {

    private final DoctorRepository mDoctorRepository;

    public SaveDoctors(@NonNull DoctorRepository doctorRepository) {
        mDoctorRepository = Preconditions.checkNotNull(doctorRepository);
    }

    @Override
    protected void executeUseCase(RequestValue requestValues) {
        Preconditions.checkNotNull(requestValues);
        Preconditions.checkNotNull(mDoctorRepository);

        mDoctorRepository.saveDoctors(requestValues.getDoctors());
    }

    public static final class RequestValue implements UseCase.RequestValues {

        private final List<DoctorModel> mDoctors;

        public RequestValue(@NonNull List<DoctorModel> doctors) {
            mDoctors = Preconditions.checkNotNull(doctors);
        }

        public List<DoctorModel> getDoctors() {
            return mDoctors;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

    }

}
