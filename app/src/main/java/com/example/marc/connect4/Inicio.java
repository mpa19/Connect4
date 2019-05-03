package com.example.marc.connect4;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Inicio extends AppCompatActivity {
    MediaPlayer buttonSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        buttonSound = MediaPlayer.create(this, R.raw.buttonsound);
    }

    void gotoAjuda(View v){
        buttonSound.start();
        Intent a = new Intent(this, Ajuda.class);
        startActivity(a);
    }

    void gotoComençar(View v){
        buttonSound.start();
        Intent a = new Intent(this, ConfiguracioPartida.class);
        startActivity(a);
        finish();
    }

    void gotoSortir(View v){
        buttonSound.start();
        finish();
    }
}
