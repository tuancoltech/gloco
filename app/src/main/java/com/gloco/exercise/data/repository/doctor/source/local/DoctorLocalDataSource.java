package com.gloco.exercise.data.repository.doctor.source.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.gloco.exercise.data.database.DatabaseDAO;
import com.gloco.exercise.data.repository.doctor.DoctorDataSource;
import com.gloco.exercise.domain.model.getdoctorlist.DoctorModel;
import com.gloco.exercise.domain.model.getdoctorlist.GetDoctorsRequestModel;
import com.google.common.base.Preconditions;

import java.util.List;

import static com.gloco.exercise.data.repository.doctor.source.local.DoctorsPersistenceContract
        .DoctorEntry.COL_AGE;
import static com.gloco.exercise.data.repository.doctor.source.local.DoctorsPersistenceContract
        .DoctorEntry.COL_NAME;
import static com.gloco.exercise.data.repository.doctor.source.local.DoctorsPersistenceContract
        .DoctorEntry.TABLE_NAME;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class DoctorLocalDataSource implements DoctorDataSource {

    private static DoctorLocalDataSource sDoctorLocalDataSource = null;

    private final Context mContext;

    private DoctorLocalDataSource(@NonNull Context context) {
        mContext = Preconditions.checkNotNull(context);
    }

    public static DoctorLocalDataSource getInstance(@NonNull Context context) {
        if (sDoctorLocalDataSource == null) {
            synchronized (DoctorLocalDataSource.class) {
                if (sDoctorLocalDataSource == null) {
                    sDoctorLocalDataSource = new DoctorLocalDataSource(context);
                }
            }
        }

        return sDoctorLocalDataSource;
    }

    @Override
    public void getDoctors(@NonNull GetDoctorsRequestModel getDoctorsRequestModel,
                           GetDoctorCallback getDoctorCallback) {


    }

    @Override
    public void saveDoctors(@NonNull List<DoctorModel> doctorModels) {
        Preconditions.checkNotNull(doctorModels);

        SQLiteDatabase database = DatabaseDAO.getInstance(mContext).getWritableDatabase();

        for (DoctorModel doctorModel : doctorModels) {

            ContentValues values = new ContentValues();
            values.put(COL_NAME, doctorModel.getName());
            values.put(COL_AGE, doctorModel.getAge());
            database.insert(TABLE_NAME, null, values);

        }

    }
}
