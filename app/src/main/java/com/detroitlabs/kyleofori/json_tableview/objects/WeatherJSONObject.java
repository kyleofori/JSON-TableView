package com.detroitlabs.kyleofori.json_tableview.objects;

/**
 * Created by kyleofori on 11/6/14.
 */
public class WeatherJSONObject {
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
}
