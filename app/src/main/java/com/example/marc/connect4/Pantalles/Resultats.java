package com.example.marc.connect4.Pantalles;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.marc.connect4.Game.Log;
import com.example.marc.connect4.SharedPreferences.ConfiguracioPredeterminada;
import com.example.marc.connect4.R;
import com.example.marc.connect4.Sqlite3.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class Resultats extends AppCompatActivity {
    EditText email;
    TextView resultat;
    MediaPlayer buttonSound;
    DatabaseHelper dh;
    SQLiteDatabase db;
    String currentDateandTime;
    Log log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);
        TextView fecha = findViewById(R.id.tvData);
        resultat = findViewById(R.id.tvResultat);
        email = findViewById(R.id.etNom3);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
        currentDateandTime = sdf.format(new Date());
        fecha.setText(currentDateandTime);

        Bundle bundle = getIntent().getExtras();
        log = bundle.getParcelable("Log");

        if(log.getTiempo().equals("50") || log.getTiempo().equals("-1")){
            resultat.setText(log.getResultado());
        } else resultat.setText(log.getResultado()+" y han sobrats "+log.getTiempo()+" secs");

        buttonSound = MediaPlayer.create(this, R.raw.buttonsound);
        guardar();
    }
    void guardar() {
        dh = new DatabaseHelper(this);
        String vs;
        if(!log.getJugador2().equals("")) vs = "VS "+log.getJugador2();
        else vs = log.getJugador2();
        dh.addResult(log.getJugador1(), vs, currentDateandTime, log.getGraella(), log.getTiempo(), log.getResultado(), log.getMovimientos());

    }
    void gotoSortir(View v){
        buttonSound.start();
        finish();
    }

    void gotoNova(View v) {
        buttonSound.start();
        Intent a = new Intent(this, PantallaJoc.class);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(sp.getString(getString(R.string.aliasMenu), "").equals("")){
            Toasty.error(getBaseContext(), R.string.toastNom, Toast.LENGTH_SHORT, true).show();
        } else
        {
            a.putExtra("Temps", sp.getBoolean(getString(R.string.timeMenu), false));
            a.putExtra("Jugador",sp.getString(getString(R.string.aliasMenu), "Player1"));
            a.putExtra("Rows",Integer.parseInt(sp.getString(getString(R.string.boardMenu), "6")));
            if(!sp.getString(getString(R.string.aliasMenu2), "").equals("")){
                a.putExtra("CPU",false);
                a.putExtra("Jugador2", sp.getString(getString(R.string.aliasMenu2), "Player2"));
            }
            startActivity(a);
            finish();
        }
    }

    void gotoEmail(View v){
        buttonSound.start();
        String ed_text = email.getText().toString().trim();
        if(TextUtils.isEmpty(ed_text)){
            Toasty.error(getBaseContext(), R.string.emailNeed, Toast.LENGTH_SHORT, true).show();
        } else {
            Intent in = new Intent(Intent.ACTION_SEND);
            in.putExtra(Intent.EXTRA_EMAIL, ed_text);
            in.putExtra(Intent.EXTRA_SUBJECT, "Resultados Connect 4");
            in.putExtra(Intent.EXTRA_TEXT, resultat.getText());
            in.setType("message/rfc822");
            startActivity(in);
        }
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
