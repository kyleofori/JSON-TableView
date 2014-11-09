package com.detroitlabs.kyleofori.json_tableview.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.detroitlabs.kyleofori.json_tableview.R;
import com.detroitlabs.kyleofori.json_tableview.asynctasks.FetchWeatherTask;
import com.detroitlabs.kyleofori.json_tableview.asynctasks.WeatherJSONRequest;
import com.detroitlabs.kyleofori.json_tableview.objects.WeatherJSONObject;

/**
 * Created by kyleofori on 11/3/14.
 */
public class MainFragment extends Fragment implements FetchWeatherTask.WeatherFetchedListener {

    @Override
    public void weatherReceived(String[] weatherData) {
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle onSavedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        WeatherJSONRequest weatherJSONRequest = new WeatherJSONRequest();

        weatherJSONRequest.execute();  //not sure if this is complete...
        return rootView;
    }

}
