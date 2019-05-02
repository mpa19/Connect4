package com.example.marc.connect4;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


import static java.lang.Thread.sleep;


public class Joc extends AppCompatActivity {
    GridView androidGridView;
    GridView androidGridView2;
    ImageAdapterGridView2 a;
    ImageAdapterGridView1 b;
    ImageView fondoBoard;

    private int ROWS    = 6;
    private static final int COLUMNS = 7;
    private static final int TO_WIN  = 4;

    private Game game;

    TextView tvJug1;
    TextView tvJug2;
    TextView tvTime;
    boolean cpu;
    boolean temps;
    int numRows;

    ArrayList<Integer> jugadas = new ArrayList<>();
    int[] imageIDs = {
            R.drawable.flecha , R.drawable.flecha, R.drawable.flecha, R.drawable.flecha,
            R.drawable.flecha, R.drawable.flecha, R.drawable.flecha
    };

    int[] graella= {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joc);
        tvJug1 = findViewById(R.id.tvJugador);
        tvJug2 = findViewById(R.id.tvJugador2);
        tvTime = findViewById(R.id.tvTime);
        fondoBoard = findViewById(R.id.ivBoard);

        String data = getIntent().getExtras().getString("Jugador");
        String data2 = getIntent().getExtras().getString("Jugador2","CPU");
        temps = getIntent().getExtras().getBoolean("Temps",false);
        numRows = getIntent().getExtras().getInt("Rows");
        cpu = getIntent().getExtras().getBoolean("CPU",true);


        tvJug1.setText(data+":");
        tvJug2.setText(data2+":");
        //tvTime.setText("50");

        tiempo();
        ROWS = numRows;
        if(ROWS == 6) fondoBoard.setImageResource(R.drawable.board);
        else if(ROWS == 5) fondoBoard.setImageResource(R.drawable.board5);

        androidGridView = findViewById(R.id.gridview_android_example);
        androidGridView2 = findViewById(R.id.gridview_android_example2);
        androidGridView2.setNumColumns(COLUMNS);
        b = new ImageAdapterGridView1(this);
        androidGridView.setAdapter(b);
        a = new ImageAdapterGridView2(this);
        androidGridView2.setAdapter(a);

        game = new Game(ROWS,COLUMNS, TO_WIN);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, final int position, long id) {

                if (game.isFinished()) return;

                if (game.canPlayColumn(position)) {
                    if(game.getStatus() == Status.PLAYER1_PLAYS){
                        Move move = game.play(position);
                        int row = move.getPosition().getRow();
                        int posi = row * 7 + position;
                        graella[posi] = R.drawable.rojo;
                        for(int a = 0; a < COLUMNS; a++) {
                            imageIDs[a] = R.drawable.flecha2;
                        }
                    } else {
                        Move move = game.play(position);
                        int row = move.getPosition().getRow();
                        int posi = row * 7 + position;
                        graella[posi] = R.drawable.amarillo;
                        for(int a = 0; a < COLUMNS; a++){
                            imageIDs[a] = R.drawable.flecha;
                        }
                    }
                    jugadas.add(position);
                    androidGridView.setAdapter(b);
                    androidGridView2.setAdapter(a);
                    updateCurrent(position);


                    if(cpu) {
                        if (game.isFinished()) return;
                        while (true) {
                            Random r = new Random();
                            int i1 = r.nextInt(7 - 0);
                            if (game.canPlayColumn(i1)) {
                                Move move1 = game.play(i1);
                                updateCurrent(i1);
                                int row1 = move1.getPosition().getRow();
                                int posi1 = row1 * 7 + i1;
                                graella[posi1] = R.drawable.amarillo;
                                androidGridView2.setAdapter(a);
                                androidGridView.setAdapter(b);
                                jugadas.add(i1);


                                for(int a = 0; a < COLUMNS; a++) {
                                    imageIDs[a] = R.drawable.flecha;
                                }
                                break;

                            }
                        }
                    }
                }
            }
        });
    }

    private void tiempo(){
        if(temps) {
            tvTime.setTextColor(Color.RED);
            TextView tvTime2 = findViewById(R.id.tvTime2);
            tvTime2.setTextColor(Color.RED);

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    boolean tiempo = true;

                    for (int a = Integer.parseInt(tvTime.getText().toString()); a > 0; a--) {
                        setText(tvTime, String.valueOf(a));
                        try {
                            sleep(1 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (game.isFinished()) {
                            tiempo = false;
                            break;

                        }
                    }
                    if (tiempo) {
                        acabar("El temps s'ha esgotat");
                    }
                }
            });
        }
    }
    private void acabar(String text){

        Intent a = new Intent(this, Resultats.class);
        a.putExtra("Result", text);
        startActivity(a);
        finish();
    }
    private void setText(final TextView text,final String value){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }
    public class ImageAdapterGridView1 extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView1(Context c) {
            mContext = c;
        }

        public int getCount() {
            return imageIDs.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(133, 133));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(16, 16, 16, 0);
            } else {
                mImageView = (ImageView) convertView;
            }
            mImageView.setImageResource(imageIDs[position]);
            return mImageView;
        }
    }

    public class ImageAdapterGridView2 extends BaseAdapter {
        private Context mContext;

        public ImageAdapterGridView2(Context c) {
            mContext = c;
        }

        public int getCount() {
            return graella.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView mImageView;

            if (convertView == null) {
                mImageView = new ImageView(mContext);
                mImageView.setLayoutParams(new GridView.LayoutParams(133, 133));
                mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImageView.setPadding(16, 5, 16, 0);
            } else {
                mImageView = (ImageView) convertView;
            }

            mImageView.setImageResource(graella[position]);

            return mImageView;
        }
    }

    private void updateCurrent(int column) {
        // We must re-check canPlay because we have played a move
        if (game.isFinished() || !game.canPlayColumn(column)) {
            if(Status.PLAYER1_WINS == game.getStatus()) {
                acabar("Has guanyat "+tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1)+" y han sobrat: "+tvTime.getText());
            }
            else if(Status.PLAYER2_WINS == game.getStatus()) {
                if(cpu) acabar("Has perdut "+tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1)+" y han sobrat: "+tvTime.getText());
                else acabar("Has guanyat "+tvJug2.getText().toString().substring(0, tvJug2.getText().length()-1)+" y han sobrat: "+tvTime.getText());
            } else if(Status.DRAW == game.getStatus()) acabar("Heu quedat empatats!!");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putIntArray("Graella", graella);
        savedInstanceState.putIntArray("Flecha", imageIDs);
        savedInstanceState.putString("Time", tvTime.getText().toString());
        savedInstanceState.putBoolean("Cpu", cpu);
        savedInstanceState.putBoolean("Temps", temps);
        savedInstanceState.putIntegerArrayList("Jugadas", jugadas);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        graella = savedInstanceState.getIntArray("Graella");
        imageIDs = savedInstanceState.getIntArray("Flecha");
        tvTime.setText(savedInstanceState.getString("Time"));
        cpu = savedInstanceState.getBoolean("Cpu");
        temps = savedInstanceState.getBoolean("Temps");
        jugadas = savedInstanceState.getIntegerArrayList("Jugadas");
        empezar();
    }

    private void empezar(){
        for(int a = 0; a < jugadas.size(); a++){
            game.play(jugadas.get(a));
            updateCurrent(jugadas.get(a));
        }
    }

}
