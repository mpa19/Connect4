package com.example.marc.connect4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    void gotoAjuda(View v){
        Intent a = new Intent(this, Ajuda.class);
        startActivity(a);
    }

    void gotoComençar(View v){
        Intent a = new Intent(this, ConfiguracioPartida.class);
        startActivity(a);
        finish();
    }
}
