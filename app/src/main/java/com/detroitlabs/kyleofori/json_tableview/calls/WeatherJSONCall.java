package com.detroitlabs.kyleofori.json_tableview.calls;

import android.util.Log;

import com.detroitlabs.kyleofori.json_tableview.objects.WeatherJSONObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyleofori on 11/6/14.
 */
public class WeatherJSONCall {
    private String mSearchResults = "";
    JSONObject mJsonObject;
    private final String TITLE_KEY = "title";
    private final String DESCRIPTION_KEY = "description";
    private final String PRICE_KEY = "price";
    private final String IMAGE_KEY = "Images";
    private final String THUMBNAIL_KEY = "url_75x75";
    private final String FULL_SIZE_IMAGE_KEY = "url_570xN";
    public String mTitle;
    public String mDescription;
    public String mPrice;
    public String mImage;
    public String mThumbnail;

    WeatherJSONObject mEtsyObject = new WeatherJSONObject();

    public void setSearchResults(String result){

        mSearchResults = result;

    }

    public void parseJson(){
        try{
            //take these msearchresults, convert them to a JSON Object.
            mJsonObject = new JSONObject(mSearchResults);
        }
        catch(JSONException e){
            Log.e("TAG PARSE", "exception in converting string to JSON");
        }

        try{
            //take above json object and say make an array from the array that exists under results tag
            JSONArray mResultsArray = mJsonObject.getJSONArray("Results");

            //tell it to get the first array result (outer onion layer)
            JSONObject mTitleObject = mResultsArray.getJSONObject(0);


            //get the title, description and price key out of the first array result (still part of outer onion)
            mTitle = mTitleObject.getString(TITLE_KEY);
            mEtsyObject.setmTitle(mTitle);//this part sets all of the information for the etsy object class

            mDescription = mTitleObject.getString(DESCRIPTION_KEY);
            mEtsyObject.setmDescription(mDescription);

            mPrice = mTitleObject.getString(PRICE_KEY);
            mEtsyObject.setmPrice(mPrice);

            //second layer of onion, starting out with an array to get it's stuff
            JSONArray mImageArray = mTitleObject.getJSONArray(IMAGE_KEY);
            JSONObject mImageObject = mImageArray.getJSONObject(0);
            mThumbnail = mImageObject.getString(THUMBNAIL_KEY);
            mEtsyObject.setmThumbnail(mThumbnail);

            mImage = mImageObject.getString(FULL_SIZE_IMAGE_KEY);
            mEtsyObject.setmFullSize(mImage);
        }

        catch (JSONException e){
            Log.e("TAG RESULT ARRAY", "exception creating result array");
        }
    }


}
