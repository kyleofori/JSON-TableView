package com.detroitlabs.kyleofori.json_tableview.parsers;

import android.util.Log;

import com.detroitlabs.kyleofori.json_tableview.objects.WeatherJSONObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by kyleofori on 11/6/14.
 */
public class WeatherJSONParser {
    private String mSearchResults = "";
    JSONObject mJsonObject;
    private final String HIGH_KEY = "max";
    private final String LOW_KEY = "min";
    private final String DESCRIPTION_KEY = "description";
    private final String DATE_KEY = "date";
    private final String LIST_KEY = "list";

    public String mHigh;
    public String mLow;
    public String mDescription;
    public String mDate;

    WeatherJSONObject mWeatherJSONObject = new WeatherJSONObject();

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
            JSONArray listArray = mJsonObject.getJSONArray(LIST_KEY);

            String high, low, date, description;
                //FOLLOW THE SUNSHINE APP / FetchWeatherTask class example

            for(int i = 0; i < listArray.length(); i++) {

                //tell it to get the first array result (outer onion layer)
                JSONObject temperatureObject = listArray.getJSONObject(i);
                JSONObject dateObject = listArray.getJSONObject(i);
                JSONObject weatherObject = listArray.getJSONObject(i);



                //get the title, description and price key out of the first array result (still part of outer onion)
                JSONObject highObject = dateObject.getJSONObject(HIGH_KEY);
                high = highObject.getString(HIGH_KEY);

                mHigh = temperatureObject.getString(HIGH_KEY);
                mWeatherJSONObject.setHigh(mHigh);//this part sets all of the information for the weather json object class

                mLow = temperatureObject.getString(LOW_KEY);
                mWeatherJSONObject.setLow(mLow);

                mDescription = temperatureObject.getString(DESCRIPTION_KEY);
                mWeatherJSONObject.setDescription(mDescription);

                mDate = dateObject.getString(DATE_KEY);
                mWeatherJSONObject.setDate(mDate);
            }
        }

        catch (JSONException e){
            Log.e("TAG RESULT ARRAY", "exception creating result array");
        }
    }


}
