package com.example.marc.connect4.Pantalles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.marc.connect4.SharedPreferences.ConfiguracioPredeterminada;
import com.example.marc.connect4.R;

import es.dmoral.toasty.Toasty;

public class Inicio extends AppCompatActivity {
    MediaPlayer buttonSound;

    private static final String KEY_JUGADOR1 = "Jugador1";
    private static final String KEY_JUGADOR2 = "Jugador2";
    private static final String KEY_TEMPS = "Temps";
    private static final String KEY_ROWS = "Rows";
    private static final String KEY_CPU = "CPU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        buttonSound = MediaPlayer.create(this, R.raw.buttonsound);
    }

    public void gotoAjuda(View v){
        //buttonSound.start();
        Intent a = new Intent(this, Ajuda.class);
        startActivity(a);
    }

    public void gotoResult(View v){
        //buttonSound.start();
        Intent a = new Intent(this, BDDresults.class);
        startActivity(a);
    }

    public void gotoComençar(View v){
        buttonSound.start();
        Intent a = new Intent(this, PantallaJoc.class);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(sp.getString(getString(R.string.aliasMenu), "").equals("")){
            Toasty.error(getBaseContext(), R.string.toastNom, Toast.LENGTH_SHORT, true).show();
        } else
        {
            a.putExtra(KEY_TEMPS, sp.getBoolean(getString(R.string.timeMenu), false));
            a.putExtra(KEY_JUGADOR1,sp.getString(getString(R.string.aliasMenu), "Player1"));
            a.putExtra(KEY_ROWS,Integer.parseInt(sp.getString(getString(R.string.boardMenu), "6")));
            if(!sp.getString(getString(R.string.aliasMenu2), "").equals("")){
                a.putExtra(KEY_CPU,false);
                a.putExtra(KEY_JUGADOR2, sp.getString(getString(R.string.aliasMenu2), "Player2"));
            }
            startActivity(a);
            finish();
        }
    }

    public void gotoSortir(View v){
        buttonSound.start();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingsMenu:
                startActivity(new Intent(this, ConfiguracioPredeterminada.class));
                break;
        }
        return true;
    }
}
