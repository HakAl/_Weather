package com.jacmobile.weather.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacmobile.weather.activities.MainActivity;
import com.jacmobile.weather.views.CurrentWeatherView;

import javax.inject.Inject;

import jacmobile.com.weather.R;

public class WeatherFragment extends ABaseV4Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Inject CurrentWeatherView currentWeatherView;

    public static WeatherFragment newInstance(int sectionNumber) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        int resId = getArguments().getInt(ARG_SECTION_NUMBER);
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        switch (resId) {
            case 1:
                currentWeatherView.onCreateView(root);
                break;
        }
        return root;
    }


    @Override public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) return;

        Bundle args = getArguments();
        if (args != null) {
            switch (args.getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    currentWeatherView.onResume();
                    break;
            }
        }
    }

    @Override public void onStop() {
        super.onStop();
        currentWeatherView.onStop();
    }

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
    }
}