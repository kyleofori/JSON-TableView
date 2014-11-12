package com.detroitlabs.kyleofori.json_tableview.asynctasks;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import com.detroitlabs.kyleofori.json_tableview.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kyleofori on 11/3/14.
 */

public class FetchWeatherTask extends AsyncTask<String, Void, String[]> {

    private final String LOG_TAG = FetchWeatherTask.class.getSimpleName(); //needs to match name of class
    private Context mContext;

    //The following interface indicates that any WeatherFetchedListener has to have a method
    // called weatherReceived for receiving a string array called weatherData.
    public interface WeatherFetchedListener {
        public void weatherReceived(String[] weatherData);
    }


    private WeatherFetchedListener mWeatherFetchedListener;

    public void setWeatherFetchedListener (WeatherFetchedListener mWeatherFetchedListener) {
        this.mWeatherFetchedListener = mWeatherFetchedListener;
    }

    public FetchWeatherTask(Context context) {
        super();
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    //I think the purpose of the WeatherFetchedListener's weatherReceived method is to get the
    //information that's returned from doInBackground() into the onPostExecute method.
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        mWeatherFetchedListener.weatherReceived(strings);
    }

    /* The date/time conversion code is going to be moved outside the asynctask later,
     * so for convenience we're breaking it out into its own method now.
     */
    private String getReadableDateString(long time) {
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        Date date = new Date(time * 1000);
        SimpleDateFormat format = new SimpleDateFormat("E, MMM d");
        return format.format(date).toString();
    }

    /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     */

    private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
            throws JSONException {

        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_DATETIME = "dt";
        final String OWM_DESCRIPTION = "main";

        ArrayList<String> resultsArrayList = new ArrayList<String>();

        JSONObject forecastJson = new JSONObject(forecastJsonStr); //this one is not in the JSON data.
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST); //this is found in the list object of the JSON data.

        String[] resultStrings = new String[numDays];
        for (int i = 0; i < weatherArray.length(); i++) {
            String day;
            String description;
            String highAndLow;

            JSONObject dayForecast = weatherArray.getJSONObject(i);

            long dateTime = dayForecast.getLong(OWM_DATETIME);
            day = getReadableDateString(dateTime);

            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            double high = temperatureObject.getDouble(OWM_MAX);
            double low = temperatureObject.getDouble(OWM_MIN);

            highAndLow = formatHighLows(high, low);
            resultStrings[i] = day + " - " + description + " - " + highAndLow;
        }

        return resultStrings;
    }

    @Override
    protected String[] doInBackground(String... zipCode) {
        if (zipCode.length == 0) {
            return null;
        }

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String[] weatherDataFromJson = null;


        // Will contain the raw JSON response as a string.
        String forecastJsonStr;

        try {

            //after checking StackOverflow
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http").authority("api.openweathermap.org")
                    .appendPath("data")
                    .appendPath("2.5")
                    .appendPath("forecast")
                    .appendPath("daily")
                    .appendQueryParameter("q", zipCode[0])
                    .appendQueryParameter("mode", "json")
                    .appendQueryParameter("units", "imperial")
                    .appendQueryParameter("cnt", "2");

            String myUrl = builder.build().toString();

            URL url = new URL(myUrl);
//                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=Detroit&mode=json&units=metric&cnt=7");

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();

            weatherDataFromJson = getWeatherDataFromJson(forecastJsonStr, 7);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return weatherDataFromJson; //onPostExecute() knows what I will return from this.
    }

}
