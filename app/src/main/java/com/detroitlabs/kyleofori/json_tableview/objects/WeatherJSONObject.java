package com.detroitlabs.kyleofori.json_tableview.objects;

/**
 * Created by kyleofori on 11/6/14.
 */
public class WeatherJSONObject {
    private String mHigh = "";
    private String mLow = "";
    private String mDate = "";
    private String mDescription = "";

    public String getHigh() {
        return mHigh;
    }

    public void setHigh(String high) {
        mHigh = high;
    }

    public String getLow() {
        return mLow;
    }

    public void setLow(String low) {
        mLow = low;
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
}
