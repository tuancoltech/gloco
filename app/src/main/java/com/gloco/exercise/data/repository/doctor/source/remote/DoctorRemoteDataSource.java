package com.gloco.exercise.data.repository.doctor.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gloco.exercise.Const;
import com.gloco.exercise.data.network.ApiManager;
import com.gloco.exercise.data.repository.doctor.DoctorDataSource;
import com.gloco.exercise.domain.model.getdoctorlist.DoctorModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsRequestModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsResponseModel;
import com.google.common.base.Preconditions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class DoctorRemoteDataSource implements DoctorDataSource {

    private static DoctorRemoteDataSource sDoctorRemoteDataSource = null;

    private final Context mContext;

    private DoctorRemoteDataSource(@NonNull Context context) {
        mContext = Preconditions.checkNotNull(context);
    }

    public static DoctorRemoteDataSource getInstance(@NonNull Context context) {
        if (sDoctorRemoteDataSource == null) {
            synchronized (DoctorRemoteDataSource.class) {
                if (sDoctorRemoteDataSource == null) {
                    sDoctorRemoteDataSource = new DoctorRemoteDataSource(context);
                }
            }
        }
        return sDoctorRemoteDataSource;
    }

    @Override
    public void getDoctors(@NonNull GetDoctorsRequestModel getDoctorsRequestModel,
                           final GetDoctorCallback getDoctorCallback) {

        ApiManager.getDoctors(mContext, getDoctorsRequestModel,
                new Callback<GetDoctorsResponseModel>() {
                    @Override
                    public void onResponse(Call<GetDoctorsResponseModel> call,
                                           Response<GetDoctorsResponseModel> response) {

                        if (response != null && response.isSuccessful() && response.body() !=
                                null) {
                            getDoctorCallback.onGetDoctorSuccess(response.body());
                        } else {
                            getDoctorCallback.onGetDoctorFailed(Const.ERROR_UNSUCCESSFUL);
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDoctorsResponseModel> call, Throwable t) {
                        getDoctorCallback.onGetDoctorFailed(Const.ERROR_UNKNOWN);
                        t.printStackTrace();
                    }
                });
    }

    @Override
    public void saveDoctors(@NonNull List<DoctorModel> doctorModels) {

    }

}
