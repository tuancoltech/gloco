package com.gloco.exercise.domain.usecase.getlocation;

import android.support.annotation.NonNull;

import com.gloco.exercise.Const;
import com.gloco.exercise.data.repository.location.LocationDataSource;
import com.gloco.exercise.data.repository.location.source.LocationRepository;
import com.gloco.exercise.domain.model.getlocation.GetLocationRequestModel;
import com.gloco.exercise.domain.model.getlocation.GetLocationResponseModel;
import com.gloco.exercise.domain.usecase.UseCase;
import com.google.common.base.Preconditions;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class GetLocation extends UseCase<GetLocation.RequestValue, GetLocation.ResponseValue> {

    private final LocationRepository mLocationRepository;

    public GetLocation(@NonNull LocationRepository locationRepository) {
        mLocationRepository = Preconditions.checkNotNull(locationRepository);
    }

    @Override
    protected void executeUseCase(RequestValue requestValues) {
        Preconditions.checkNotNull(requestValues);
        Preconditions.checkNotNull(mLocationRepository);

        mLocationRepository.getLocation(requestValues.getRequestModel(),
                new LocationDataSource.GetLocationCallback() {
                    @Override
                    public void onGetLocationSuccess(@NonNull GetLocationResponseModel
                                                             getLocationResponseModel) {

                        getUseCaseCallback().onSuccess(new ResponseValue
                                (getLocationResponseModel));
                    }

                    @Override
                    public void onGetLocationFailed(int errorCode) {
                        getUseCaseCallback().onError(Const.ERROR_UNKNOWN);
                    }
                });
    }

    public static final class RequestValue implements UseCase.RequestValues {

        private final GetLocationRequestModel mGetLocationRequestModel;

        public RequestValue(@NonNull GetLocationRequestModel getLocationRequestModel) {
            mGetLocationRequestModel = Preconditions.checkNotNull(getLocationRequestModel);
        }

        public GetLocationRequestModel getRequestModel() {
            return mGetLocationRequestModel;
        }

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final GetLocationResponseModel mGetLocationResponseModel;

        public ResponseValue(@NonNull GetLocationResponseModel getLocationResponseModel) {
            mGetLocationResponseModel = Preconditions.checkNotNull(getLocationResponseModel);
        }

        public GetLocationResponseModel getResponseModel() {
            return mGetLocationResponseModel;
        }

    }

}
