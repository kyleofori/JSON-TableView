package com.detroitlabs.kyleofori.json_tableview.asynctasks;

import android.os.AsyncTask;
import android.util.Log;

import com.detroitlabs.kyleofori.json_tableview.parsers.WeatherJSONParser;

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
public class WeatherJSONRequest extends AsyncTask {
    //    http://api.openweathermap.org/data/2.5/forecast/daily?q=Detroit&mode=json&units=metric&cnt=2
    private final String BASE_API = "https://api.openweathermap.org/data/2.5/forecast/daily?";
    private final String PLACE_QUERY = "q=";
    private final String MODE = "&mode=json";
    private final String UNITS = "&units=metric";
    private final String NUMBER_OF_DAYS = "&cnt=2";

    //constructor
    public WeatherJSONRequest() {
    }

    //some needed variables
    InputStream mInputStream = null;
    HttpsURLConnection mURLConnector = null;
    public String weatherJSONString;
    public String fullURL;
    public BufferedReader mBufferedReader;

    @Override
    protected String doInBackground(Object[] params) { //Need to research what's going on here...

        try {
            //this string builder is used to assemble the JSON data
            StringBuilder mStringBuilder = new StringBuilder();

            //building our complete URL :)
//            String placeName = "";
            fullURL = BASE_API + PLACE_QUERY /*+ placeName*/ + MODE + UNITS + NUMBER_OF_DAYS;

            URL openWeatherMapURL = new URL(fullURL);

            //this opens an internet connection with the URL built above
            mURLConnector = (HttpsURLConnection) openWeatherMapURL.openConnection();

            //this tells the API what we are planning on doing... GET
            mURLConnector.setRequestMethod("GET");

            //the input stream is going to handle the info coming in from the URL. Helps open a line for it to follow in
            mInputStream = mURLConnector.getInputStream();

            //birthing new buffered reader, telling it to be an input stream reader and to read mInputStream
            mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));

            String line;


            //this is saying that as long as there are more lines coming in from the JSON, the string builder will add the next line
            while ((line = mBufferedReader.readLine()) != null) {
                mStringBuilder.append(line);
            }

            //this is basically saying that all of the strings that were appended are now set to weatherJSONString
            weatherJSONString = mStringBuilder.toString();

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

        return weatherJSONString;
    }


    //takes the weatherJSONString from above (you can rename in the onPostExecute method) and does a thing
    @Override
    protected void onPostExecute(Object SearchResult) {

        //creating instance of JSON data class
        WeatherJSONParser weatherJSONParser = new WeatherJSONParser();

        //calling the set method
        weatherJSONParser.setSearchResults(weatherJSONString);
    }

}
