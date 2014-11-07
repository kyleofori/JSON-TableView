package com.detroitlabs.kyleofori.json_tableview.asynctasks;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by kyleofori on 11/6/14.
 */
public class WeatherJSONRequest {
    private final String API_KEY = "&api_key=rf882apukk32kmsqamjwej8w";
    private final String BASE_API = "https://openapi.etsy.com/v2/listings/active?";
    private final String SORTING_HAT = "&sort_on=score&sort_order=down";
    private final String RESULT_LIMIT = "limit=25";
    private final String SEARCH_TERM = "&keywords=";
    private final String INCLUDE_IMAGES = "&includes=Images";
    public String searchKeyword = "";

    public EtsyAPI(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    InputStream mInputStream = null;
    HttpsURLConnection mURLConnector = null;
    JSONObject mJsonObject;
    public String mSearchResult;
    public String mFullURL;

    @Override
    protected Object doInBackground(Object[] objects) {

        try {
            //this string builder is used to assemble the JSON data
            StringBuilder mStringBuilder = new StringBuilder();

            //building our complete URL :)
            mFullURL = BASE_API + RESULT_LIMIT + INCLUDE_IMAGES + SEARCH_TERM + searchKeyword + SORTING_HAT + API_KEY;

            URL mEtsyUrl = new URL(mFullURL);

            //this opens an internet connection with the URL built above
            mURLConnector = (HttpsURLConnection) mEtsyUrl.openConnection();

            //this tells the API what we are planning on doing... GET
            mURLConnector.setRequestMethod("GET");


            //the input stream is going to handle the info coming in from the URL. Helps open a line for it to follow in
            mInputStream = mURLConnector.getInputStream();

            //birthing new buffered reader, telling it to be an input stream reader and to read mInputStream
            BufferedReader mBufferReader = new BufferedReader(new InputStreamReader(mInputStream));

            String line;


            //this is saying that as long as there are more lines coming in from the JSON, the string builder will add the next line
            while ((line = mBufferReader.readLine()) != null) {
                mStringBuilder.append(line);
            }

            //this is basically saying that all of the strings that were appended are now set to msearchresult
            mSearchResult = mStringBuilder.toString();

            //closing input stream bc if you leave open it will keep draining resources
            mInputStream.close();

            //closing url connector bc if you leave open it will keep draining resources
            mURLConnector.disconnect();
        }


        //this is where you tell android studio to put in a log message for potential errors that may be happening
        catch (MalformedURLException e) {
            Log.e("TAG URL", e.getLocalizedMessage());

        } catch (IOException e) {
            Log.e("TAG URL CONNECTOR", e.getLocalizedMessage());
        }


        return mSearchResult;

    }


    //takes the m search result from above (you can rename in the onpost execute method) and does a thing
    @Override
    protected void onPostExecute(Object SearchResult) {

        super.onPostExecute(SearchResult);


        //creating isnatnce of JSON data class
        JsonData mJsonData = new JsonData();

        //calling the set method
        mJsonData.setSearchResults(mSearchResult);
    }

}
