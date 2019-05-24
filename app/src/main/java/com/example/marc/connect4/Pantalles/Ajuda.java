package com.example.marc.connect4.Pantalles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.marc.connect4.R;

public class Ajuda extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajuda);
        TextView tv = findViewById(R.id.textView6);
        tv.setMovementMethod(new ScrollingMovementMethod());
    }
}
