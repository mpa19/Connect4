package com.example.marc.connect4;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class Resultats extends AppCompatActivity {
    EditText email;
    TextView resultat;
    MediaPlayer buttonSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultats);
        TextView fecha = findViewById(R.id.tvData);
        resultat = findViewById(R.id.tvResultat);
        email = findViewById(R.id.etNom3);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());

        fecha.setText(currentDateandTime);
        resultat.setText(getIntent().getExtras().getString("Result"));
        buttonSound = MediaPlayer.create(this, R.raw.buttonsound);
    }

    void gotoSortir(View v){
        buttonSound.start();
        finish();
    }

    void gotoNova(View v) {
        buttonSound.start();
        Intent a = new Intent(this, ConfiguracioPartida.class);
        startActivity(a);
        finish();
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
}
