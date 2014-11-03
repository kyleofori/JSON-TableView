package com.detroitlabs.kyleofori.json_tableview.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.detroitlabs.kyleofori.json_tableview.R;

/**
 * Created by kyleofori on 11/3/14.
 */
public class MainFragment extends Fragment {

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle onSavedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

}
