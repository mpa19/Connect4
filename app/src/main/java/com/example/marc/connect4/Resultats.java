package com.example.marc.connect4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Resultats extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        TextView fecha = findViewById(R.id.tvData);
        fecha.setText(currentDateandTime);
        TextView resultat = findViewById(R.id.tvResultat);
        resultat.setText(getIntent().getExtras().getString("Result"));

    }

    void gotoSortir(View v){
        finish();
    }

    void gotoNova(View v) {
        Intent a = new Intent(this, ConfiguracioPartida.class);
        startActivity(a);
        finish();
    }
}
