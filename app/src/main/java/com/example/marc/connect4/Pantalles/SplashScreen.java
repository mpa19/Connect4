package com.example.marc.connect4.Pantalles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.marc.connect4.R;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        Thread background = new Thread() {
            public void run() {
                try {
                    sleep(1*1000);

                    Intent i=new Intent(getBaseContext(), Inicio.class);
                    startActivity(i);

                    finish();
                } catch (Exception e) {
                }
            }
        };
        background.start();
    }


}
