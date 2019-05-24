package com.example.marc.connect4.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marc.connect4.Game.Game;
import com.example.marc.connect4.Game.Log;
import com.example.marc.connect4.Game.Move;
import com.example.marc.connect4.Game.Status;
import com.example.marc.connect4.Pantalles.Resultats;
import com.example.marc.connect4.R;

import java.util.ArrayList;
import java.util.Random;

import es.dmoral.toasty.Toasty;

import static java.lang.Thread.sleep;

public class JocFragment extends Fragment {
    GridView androidGridView;
    GridView androidGridView2;
    ImageAdapterGridView2 a;
    ImageAdapterGridView1 b;
    MediaPlayer fondoSound;
    MediaPlayer buttonSound;
    ImageView musica;
    TextView tvJug1;
    TextView tvJug2;
    TextView tvTime;
    FrameLayout fmFechas;
    FrameLayout fmBoard;
    CountDownTimer cdt;

    private int ROWS    = 6;
    private static final int COLUMNS = 7;
    private static final int TO_WIN  = 4;

    Log log;

    private Game game;

    boolean cpu;
    boolean temps;
    int numRows;
    boolean music = true;
    boolean timer;

    ArrayList<Integer> jugadas = new ArrayList<>();
    int[] imageIDs = {
            R.drawable.flecha , R.drawable.flecha, R.drawable.flecha, R.drawable.flecha,
            R.drawable.flecha, R.drawable.flecha, R.drawable.flecha
    };

    int[] graella;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.activity_joc, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tvJug1 = getView().findViewById(R.id.tvJug);
        tvJug2 = getView().findViewById(R.id.tvJugador2);
        tvTime = getView().findViewById(R.id.tvTime);
        buttonSound = MediaPlayer.create(getActivity(), R.raw.buttonsound);
        fondoSound = MediaPlayer.create(getActivity(), R.raw.fondomusic);
        musica = getView().findViewById(R.id.ivSound);
        fmFechas = getView().findViewById(R.id.frameLayout);
        fmBoard = getView().findViewById(R.id.frameLayout2);

        if(music) {
            fondoSound.setLooping(true);
            fondoSound.start();
        } else {
            musica.setImageResource(R.drawable.mute);
        }

        String data = getActivity().getIntent().getExtras().getString("Jugador");
        String data2 = getActivity().getIntent().getExtras().getString("Jugador2","CPU");
        temps = getActivity().getIntent().getExtras().getBoolean("Temps",false);
        numRows = getActivity().getIntent().getExtras().getInt("Rows");
        cpu = getActivity().getIntent().getExtras().getBoolean("CPU",true);


        tvJug1.setText(data+":");
        tvJug2.setText(data2+":");

        tiempo();

        ROWS = numRows;

        /*ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) fmFechas.getLayoutParams();
        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) fmBoard.getLayoutParams();
        ConstraintLayout.LayoutParams params3 = (ConstraintLayout.LayoutParams) fondoBoard.getLayoutParams();
        int orientation = getResources().getConfiguration().orientation;*/

        if(ROWS == 6){
            graella = new int[42];
            for(int i=0; i<42; i++){
                graella[i] = R.drawable.border;
            }
        }
        else if(ROWS == 5) {
            graella = new int[35];
            for(int i=0; i<35; i++){
                graella[i] = R.drawable.border;
            }
        } else {
            graella = new int[28];
            for(int i=0; i<28; i++){
                graella[i] = R.drawable.border;
            }
        }


        androidGridView = getView().findViewById(R.id.gridview_android_example);
        androidGridView2 = getView().findViewById(R.id.gridview_android_example2);
        androidGridView2.setNumColumns(COLUMNS);
        b = new ImageAdapterGridView1(getActivity());
        androidGridView.setAdapter(b);
        a = new ImageAdapterGridView2(getActivity());
        androidGridView2.setAdapter(a);

        game = new Game(ROWS,COLUMNS, TO_WIN);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, final int position, long id) {
                buttonSound.start();
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
        musica.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                if(music) {
                    musica.setImageResource(R.drawable.mute);
                    fondoSound.pause();
                    music = false;
                } else {
                    musica.setImageResource(R.drawable.sound);
                    fondoSound.start();
                    music = true;
                }
            }
        });

        if(savedInstanceState != null){
            graella = savedInstanceState.getIntArray("Graella");
            imageIDs = savedInstanceState.getIntArray("Flecha");
            tvTime.setText(savedInstanceState.getString("Time"));
            cpu = savedInstanceState.getBoolean("Cpu");
            temps = savedInstanceState.getBoolean("Temps");
            jugadas = savedInstanceState.getIntegerArrayList("Jugadas");
            music = savedInstanceState.getBoolean("Music");

            if (!music) {
                fondoSound.pause();
                musica.setImageResource(R.drawable.mute);
            }

            empezar();
            if(temps) {
                cdt.cancel();
                tiempo();
            }
        }
    }

    /*public void paraMusica(View v){
        if(music) {
            musica.setImageResource(R.drawable.mute);
            fondoSound.pause();
            music = false;
        } else {
            musica.setImageResource(R.drawable.sound);
            fondoSound.start();
            music = true;
        }
    }*/

    private void tiempo(){
        if(temps) {
            tvTime.setTextColor(Color.RED);
            TextView tvTime2 = getView().findViewById(R.id.tvTime2);
            tvTime2.setTextColor(Color.RED);

            int mili = Integer.valueOf(tvTime.getText().toString()) * 1000;
            timer = true;
            cdt = new CountDownTimer(mili, 1000) {
                int time = Integer.valueOf(tvTime.getText().toString());
                public void onTick(long millisUntilFinished) {
                    tvTime.setText(checkDigit(time));
                    time--;
                    if(game.isFinished()){
                        timer = false;
                        cdt.onFinish();
                    }
                }

                public void onFinish() {
                    if(timer) acabar("El temps s'ha esgotat");
                }

            }.start();
        }
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
    private void acabar(String text){
        Intent a = new Intent(getActivity(), Resultats.class);
        Bundle b = new Bundle();
        b.putParcelable("Log", log);
        a.putExtras(b);
        startActivity(a);
        fondoSound.stop();
        getActivity().finish();
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
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // In landscape
                    mImageView.setLayoutParams(new GridView.LayoutParams(100, 100));
                    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    mImageView.setPadding(30, 16, 20, 15);
                    androidGridView.setColumnWidth(125);

                } else {
                    // In portrait
                    mImageView.setLayoutParams(new GridView.LayoutParams(133, 133));
                    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    mImageView.setPadding(16, 16, 16, 0);
                }

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
                int orientation = getResources().getConfiguration().orientation;
                /*if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // In landscape
                    mImageView.setLayoutParams(new GridView.LayoutParams(105, 105));
                    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    mImageView.setPadding(20, 5, 20, 0);
                    androidGridView2.setColumnWidth(150);

                } else {*/
                    // In portrait
                    mImageView.setLayoutParams(new GridView.LayoutParams(115, 115));
                    mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    mImageView.setPadding(16, 5, 16, 0);
                //}

            } else {
                mImageView = (ImageView) convertView;
            }

            mImageView.setImageResource(graella[position]);

            return mImageView;
        }
    }
    private void mssg(final int i){
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    if(temps) cdt.cancel();
                    Thread.sleep(3000);
                    if(i == 1) {
                        if(temps) acabar(getString(R.string.win)+tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1)+" y han sobrat: "+tvTime.getText()+" secs");
                        else acabar(getString(R.string.win)+tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1));
                    }
                    else if(i == 2) {
                        if(cpu) {
                            if(temps) acabar(getString(R.string.lose)+tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1)+" y han sobrat: "+tvTime.getText()+" secs");
                            else acabar(getString(R.string.lose)+tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1));
                        }
                        else {
                            if(temps) acabar(getString(R.string.win)+tvJug2.getText().toString().substring(0, tvJug2.getText().length()-1)+" y han sobrat: "+tvTime.getText()+" secs");
                            else acabar(getString(R.string.win)+tvJug2.getText().toString().substring(0, tvJug2.getText().length()-1));

                        }
                    } else {
                        acabar(getString(R.string.draw));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }
    private void updateCurrent(int column) {
        if (game.isFinished() || !game.canPlayColumn(column)) {
            if(Status.PLAYER1_WINS == game.getStatus()) {

                Toast toast = Toasty.success(getActivity().getBaseContext(), getString(R.string.winToast)+tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1), Toast.LENGTH_LONG, true);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();

                if(cpu) {
                    log = new Log(tvJug1.getText().toString().substring(0, tvJug1.getText().length() - 1),
                            "", "", String.valueOf(ROWS),
                            tvTime.getText().toString(), "Has guanyat " + tvJug1.getText().toString().substring(0, tvJug1.getText().length() - 1), "Marc: 4x4\nCPU: 5x5");
                } else {
                    log = new Log(tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1),
                            tvJug2.getText().toString().substring(0, tvJug2.getText().length()-1),"", String.valueOf(ROWS),
                            tvTime.getText().toString(),"Has guanyat  "+tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1),
                            "Marc: 4x4\nCPU: 5x5");
                }

                mssg(1);
            }
            else if(Status.PLAYER2_WINS == game.getStatus()) {
                Toast toast;
                if(cpu) {
                    toast = Toasty.error(getActivity().getBaseContext(), getString(R.string.loseToast), Toast.LENGTH_LONG, true);
                    log = new Log(tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1),
                            "","", String.valueOf(ROWS),
                            tvTime.getText().toString(),"Has perdut "+tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1),"Marc: 4x4\nCPU: 5x5");
                }
                else {
                    toast = Toasty.success(getActivity().getBaseContext(), getString(R.string.winToast)+tvJug2.getText().toString().substring(0, tvJug2.getText().length()-1), Toast.LENGTH_LONG, true);
                    log = new Log(tvJug1.getText().toString().substring(0, tvJug1.getText().length()-1),
                            tvJug2.getText().toString().substring(0, tvJug2.getText().length()-1),"", String.valueOf(ROWS),
                            tvTime.getText().toString(),"Has guanyat "+tvJug2.getText().toString().substring(0, tvJug2.getText().length()-1),
                            "Marc: 4x4\nCPU: 5x5");
                }
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
                mssg(2);
            } else if(Status.DRAW == game.getStatus()) {
                Toast toast = Toasty.info(getActivity().getBaseContext(), getString(R.string.draw), Toast.LENGTH_LONG, true);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.show();
                if(cpu) {
                    log = new Log(tvJug1.getText().toString().substring(0, tvJug1.getText().length() - 1),
                            "","", String.valueOf(ROWS), tvTime.getText().toString(), "Heu quedat empat", "Marc: 4x4\nCPU: 5x5");
                } else {
                    log = new Log(tvJug1.getText().toString().substring(0, tvJug1.getText().length() - 1),
                            tvJug2.getText().toString().substring(0, tvJug2.getText().length() - 1),
                            "", String.valueOf(ROWS), tvTime.getText().toString(), "Heu quedat empat", "Marc: 4x4\nCPU: 5x5");
                }
                mssg(3);
            }
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
        savedInstanceState.putBoolean("Music", music);
        if(temps) cdt.cancel();
        fondoSound.pause();
    }

    /*@Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        graella = savedInstanceState.getIntArray("Graella");
        imageIDs = savedInstanceState.getIntArray("Flecha");
        tvTime.setText(savedInstanceState.getString("Time"));
        cpu = savedInstanceState.getBoolean("Cpu");
        temps = savedInstanceState.getBoolean("Temps");
        jugadas = savedInstanceState.getIntegerArrayList("Jugadas");
        music = savedInstanceState.getBoolean("Music");

        if (!music) {
            fondoSound.pause();
            musica.setImageResource(R.drawable.mute);
        }

        empezar();
        if(temps) {
            cdt.cancel();
            tiempo();
        }
    }*/

    private void empezar(){
        for(int a = 0; a < jugadas.size(); a++){
            game.play(jugadas.get(a));
            updateCurrent(jugadas.get(a));
        }
    }

}
