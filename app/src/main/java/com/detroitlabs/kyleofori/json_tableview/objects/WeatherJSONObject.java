package com.detroitlabs.kyleofori.json_tableview.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kyleofori on 11/6/14.
 */
public class WeatherJSONObject implements Parcelable {
    private String mMax = "";
    private String mMin = "";
    private String mDate = "";
    private String mDescription = "";

    public String getMax() {
        return mMax;
    }

    public void setMax(String max) {
        mMax = max;
    }

    public String getMin() {
        return mMin;
    }

    public void setMin(String min) {
        mMin = min;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mMax);
        dest.writeString(this.mMin);
        dest.writeString(this.mDate);
        dest.writeString(this.mDescription);
    }

    public WeatherJSONObject() {
    }

    private WeatherJSONObject(Parcel in) {
        this.mMax = in.readString();
        this.mMin = in.readString();
        this.mDate = in.readString();
        this.mDescription = in.readString();
    }

    public static final Parcelable.Creator<WeatherJSONObject> CREATOR = new Parcelable.Creator<WeatherJSONObject>() {
        public WeatherJSONObject createFromParcel(Parcel source) {
            return new WeatherJSONObject(source);
        }

        public WeatherJSONObject[] newArray(int size) {
            return new WeatherJSONObject[size];
        }
    };
}
