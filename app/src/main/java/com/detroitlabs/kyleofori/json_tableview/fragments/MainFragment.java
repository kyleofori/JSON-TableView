package com.detroitlabs.kyleofori.json_tableview.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.detroitlabs.kyleofori.json_tableview.R;
import com.detroitlabs.kyleofori.json_tableview.asynctasks.FetchWeatherTask;
import com.detroitlabs.kyleofori.json_tableview.asynctasks.WeatherJSONRequest;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/**
 * Created by kyleofori on 11/3/14.
 */
public class MainFragment extends Fragment implements FetchWeatherTask.WeatherFetchedListener {

    @Override
    public void weatherReceived(String[] weatherData) {
    }

    private ArrayAdapter<String> mForecastAdapter;

    private ListView mListView;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle onSavedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_item_weather, container, false);

        mForecastAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.list_item_weather, //What View will be inflated for that element in array
                R.id.description, //which View within the layout does the element of the array bind to
                new ArrayList<String>());

        mListView = (ListView) rootView.findViewById(R.id.list_item_listview);
        mListView.setAdapter(mForecastAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });

        WeatherJSONRequest weatherJSONRequest = new WeatherJSONRequest();

        weatherJSONRequest.execute();  //not sure if this is complete...
        return rootView;
    }
}
