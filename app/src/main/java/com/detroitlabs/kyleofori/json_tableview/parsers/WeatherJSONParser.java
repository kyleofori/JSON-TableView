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
    private final String MAX_KEY = "max";
    private final String MIN_KEY = "min";
    private final String WEATHER_KEY = "weather";
    private final String DESCRIPTION_KEY = "description";
    private final String TEMPERATURE_KEY = "temp";
    private final String DATE_KEY = "dt";
    private final String LIST_KEY = "list";

    public String mMax;
    public String mMin;
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
            //FOLLOW THE SUNSHINE APP / FetchWeatherTask class example

            //take above json object and say make an array from the array that exists under results tag
            //11-10: this gets list*[ {} {} ]
            JSONArray listArray = mJsonObject.getJSONArray(LIST_KEY);



            for(int i = 0; i < listArray.length(); i++) {

                //11-10: this gets list[ *{} *{} ]
                JSONObject listArrayObject = listArray.getJSONObject(i);

                    //11-10: this gets weather*[ {} ], which was inside the * of list[*{} *{}]
                    JSONArray weatherArray = listArrayObject.getJSONArray(WEATHER_KEY);

                    //11-10: there is only one object inside the weather array, so we just get JSONObject(0)
                    JSONObject weatherArrayObject = weatherArray.getJSONObject(0);

                        //11-10: finally, get the description from inside the weatherArrayObject, which is a {}.
                        mDescription = weatherArrayObject.getString(DESCRIPTION_KEY);

                    //11-10: Following the pattern from here on out. Notice the outline form.
                    JSONObject temperatureObject = listArrayObject.getJSONObject(TEMPERATURE_KEY);

                        mMax = temperatureObject.getString(MAX_KEY);
                        mMin = temperatureObject.getString(MIN_KEY);

                    mDate = listArrayObject.getString(DATE_KEY);

            //this part sets all of the information for the weather json object class
                mWeatherJSONObject.setMax(mMax);
                mWeatherJSONObject.setMin(mMin);
                mWeatherJSONObject.setDescription(mDescription);
                mWeatherJSONObject.setDate(mDate);
            }
        }

        catch (JSONException e){
            Log.e("TAG RESULT ARRAY", "exception creating result array");
        }
    }


}
