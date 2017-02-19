package com.gloco.exercise.data.repository.doctor.source.local;

import android.provider.BaseColumns;

/**
 * Created by AnPEthan on 9/8/2016.
 */

public class DoctorsPersistenceContract {

    private DoctorsPersistenceContract() {
    }

    public static abstract class DoctorEntry implements BaseColumns {

        public static final String TABLE_NAME = "doctors";

        public static final String COL_NAME = "name";

        public static final String COL_AGE = "age";
    }

}
