/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gloco.exercise.dependencies;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gloco.exercise.data.repository.doctor.source.DoctorRepository;
import com.gloco.exercise.data.repository.doctor.source.local.DoctorLocalDataSource;
import com.gloco.exercise.data.repository.doctor.source.remote.DoctorRemoteDataSource;
import com.gloco.exercise.data.repository.location.source.LocationRepository;
import com.gloco.exercise.data.repository.location.source.local.LocationLocalDataSource;
import com.gloco.exercise.data.repository.location.source.remote.LocationRemoteDataSource;
import com.gloco.exercise.domain.usecase.UseCaseHandler;
import com.gloco.exercise.domain.usecase.getdoctors.GetDoctors;
import com.gloco.exercise.domain.usecase.getlocation.GetLocation;
import com.gloco.exercise.domain.usecase.savedoctors.SaveDoctors;

/**
 * Enables injection of mock implementations for
 * testing, since it allows us to use
 * a fake instance of the class to isolate the dependencies and run a test hermetically.
 */
public class Injection {

    public static UseCaseHandler provideUseCaseHandler() {
        return UseCaseHandler.getInstance();
    }


    /**
     * Provides a dependency for {@link LocationRepository}
     *
     * @param context The context where this dependency is injected
     * @return A dependency of {@link LocationRepository}
     */
    private static LocationRepository provideLocationRepository(@NonNull Context context) {
        return LocationRepository.getInstance(LocationLocalDataSource.getInstance(context),
                LocationRemoteDataSource.getInstance(context));
    }

    /**
     * Provides a dependency for {@link DoctorRepository}
     *
     * @param context Where the {@link DoctorRepository} is injected
     * @return A dependency of {@link DoctorRepository}
     */
    private static DoctorRepository provideDoctorRepository(@NonNull Context context) {
        return DoctorRepository.getInstance(DoctorLocalDataSource.getInstance(context),
                DoctorRemoteDataSource.getInstance(context));
    }

    /**
     * Provides a dependency for {@link GetLocation}
     *
     * @param context Where the {@link GetLocation} is injected
     * @return An instance of {@link GetLocation}
     */
    public static GetLocation provideGetLocation(@NonNull Context context) {
        return new GetLocation(provideLocationRepository(context));
    }

    /**
     * Provides a dependency for {@link GetDoctors}
     *
     * @param context Where {@link GetDoctors} is injected
     * @return An instance of {@link GetDoctors}
     */
    public static GetDoctors provideGetDoctors(@NonNull Context context) {
        return new GetDoctors(provideDoctorRepository(context));
    }


    public static SaveDoctors provideSaveDoctors(@NonNull Context context) {
        return new SaveDoctors(provideDoctorRepository(context));
    }

}
