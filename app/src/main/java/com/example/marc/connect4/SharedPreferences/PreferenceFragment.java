package com.example.marc.connect4.SharedPreferences;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.marc.connect4.R;

public class PreferenceFragment extends android.preference.PreferenceFragment {

    public PreferenceFragment(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
