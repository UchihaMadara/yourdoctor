package com.ahmed.yourdoc.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kuliza-195 on 12/23/16.
 */


public class SubTitle implements Parcelable {

    public static final Parcelable.Creator<SubTitle> CREATOR = new Parcelable.Creator<SubTitle>() {
        @Override
        public SubTitle createFromParcel(Parcel source) {
            return new SubTitle(source);
        }

        @Override
        public SubTitle[] newArray(int size) {
            return new SubTitle[size];
        }
    };
    private String name;

    public SubTitle(String name) {
        this.name = name;
    }

    protected SubTitle(Parcel in) {
        this.name = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }
}
