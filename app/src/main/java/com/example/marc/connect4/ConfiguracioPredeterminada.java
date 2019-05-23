package com.example.marc.connect4;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ConfiguracioPredeterminada extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment()).commit();
    }
}
