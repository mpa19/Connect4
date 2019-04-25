package com.example.marc.connect4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ConfiguracioPartida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracio_partida);
    }

    void gotoJugar(View v){
        EditText nom = (EditText) findViewById(R.id.etNom);
        String ed_text = nom.getText().toString().trim();
        if(TextUtils.isEmpty(ed_text)){
            Toast.makeText(getBaseContext(), R.string.toastNom, Toast.LENGTH_LONG).show();
        }
        else {
            Intent a = new Intent(this, Joc.class);
            startActivity(a);
            finish();
        }
    }
}
