package com.example.marc.connect4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ConfiguracioPartida extends AppCompatActivity {
    CheckBox cb;
    EditText jugador2;
    Switch s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracio_partida);
        cb = findViewById(R.id.checkBox);
        s = (Switch) findViewById(R.id.sJugador);
        jugador2 = findViewById(R.id.etNom2);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) jugador2.setVisibility(View.VISIBLE);
                else jugador2.setVisibility(View.INVISIBLE);
            }
        });
    }

    void gotoJugar(View v){
        EditText nom =  findViewById(R.id.etNom);
        String ed_text = nom.getText().toString().trim();
        String ed_text2 = jugador2.getText().toString().trim();

        if(TextUtils.isEmpty(ed_text) || (s.isChecked() && TextUtils.isEmpty(ed_text2))){
            Toast.makeText(getBaseContext(), R.string.toastNom, Toast.LENGTH_LONG).show();
        }
        else {
            Intent a = new Intent(this, Joc.class);
            if(cb.isChecked()) a.putExtra("Temps", true);
            a.putExtra("Jugador",ed_text);
            if(s.isChecked()) {
                a.putExtra("CPU",false);
                a.putExtra("Jugador2", ed_text2);
            }
            startActivity(a);
            finish();
        }
    }
}
