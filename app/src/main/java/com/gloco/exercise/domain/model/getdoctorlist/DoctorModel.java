package com.gloco.exercise.domain.model.getdoctorlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Tuan Nguyen on 2/19/2017.
 */

public class DoctorModel implements Parcelable {

    public static final Creator<DoctorModel> CREATOR = new Creator<DoctorModel>() {
        @Override
        public DoctorModel createFromParcel(Parcel source) {
            return new DoctorModel(source);
        }

        @Override
        public DoctorModel[] newArray(int size) {
            return new DoctorModel[size];
        }
    };

    @SerializedName("name")
    private String mName;

    @SerializedName("age")
    private int mAge;

    public DoctorModel() {
    }

    protected DoctorModel(Parcel in) {
        this.mName = in.readString();
        this.mAge = in.readInt();
    }

    public String getName() {
        return mName;
    }

    public int getAge() {
        return mAge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeInt(this.mAge);
    }
}
