package com.detroitlabs.kyleofori.json_tableview.objects;

/**
 * Created by kyleofori on 11/6/14.
 */
public class WeatherJSONObject {
    private String mTitle = "";
    private String mDescription = "";
    private String mPrice = "";
    private String mThumbnail = "";
    private String mFullSize = "";

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String Thumbnail) {
        this.mThumbnail = Thumbnail;
        //remember to add the pictures to the inflated views
    }

    public String getmFullSize() {
        return mFullSize;
    }

    public void setmFullSize(String mFullSize) {
        this.mFullSize = mFullSize;
    }

}
