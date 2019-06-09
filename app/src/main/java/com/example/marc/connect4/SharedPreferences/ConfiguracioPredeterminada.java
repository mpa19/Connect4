package com.example.marc.connect4.SharedPreferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.marc.connect4.R;
import com.example.marc.connect4.SharedPreferences.PreferenceFragment;

public class ConfiguracioPredeterminada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment()).commit();
    }
}
