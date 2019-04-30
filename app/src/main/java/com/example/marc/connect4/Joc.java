package com.example.marc.connect4;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Joc extends AppCompatActivity {
    GridView androidGridView;
    GridView androidGridView2;
    ImageAdapterGridView2 a;

    private static final int ROWS    = 6;
    private static final int COLUMNS = 7;
    private static final int TO_WIN  = 4;

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private Game game;

    TextView tvJug1;

    Integer[] imageIDs = {
            R.drawable.flecha, R.drawable.flecha, R.drawable.flecha, R.drawable.flecha, R.drawable.flecha, R.drawable.flecha, R.drawable.flecha
    };

    int[] graella= {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joc);
        game = new Game(ROWS,COLUMNS, TO_WIN);
        tvJug1 = (TextView) findViewById(R.id.tvJugador);
        String data = getIntent().getExtras().getString("Jugador","defaultKey");
        tvJug1.setText(data+":");
        //graella = new Integer[7][7];
        androidGridView = (GridView) findViewById(R.id.gridview_android_example);
        androidGridView2 = (GridView) findViewById(R.id.gridview_android_example2);
        androidGridView2.setNumColumns(7);

        androidGridView.setAdapter(new ImageAdapterGridView1(this));
        a = new ImageAdapterGridView2(this);
        androidGridView2.setAdapter(a);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, final int position, long id) {
                /*Toast.makeText(getBaseContext(), "Grid Item " + (position + 1) + " Selected", Toast.LENGTH_LONG).show();
                graella[position] = R.drawable.rojo;
                androidGridView2.setAdapter(a);*/

                if (game.isFinished()) return;

                if (game.canPlayColumn(position)) {
                    Move move = game.play(position);
                    updateCurrent(position);
                    int row = move.getPosition().getRow();
                    int posi = row * 7 + position;
                    graella[posi] = R.drawable.rojo;
                    androidGridView2.setAdapter(a);

                    if (game.isFinished()) return;
                    while(true) {
                        Random r = new Random();
                        int i1 = r.nextInt(7 - 0);
                        if (game.canPlayColumn(i1)) {
                            Move move1 = game.play(i1);
                            updateCurrent(i1);
                            int row1 = move1.getPosition().getRow();
                            int posi1 = row1 * 7 + i1;
                            graella[posi1] = R.drawable.amarillo;
                            androidGridView2.setAdapter(a);
                            break;

                        }
                    }
                }
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
                mImageView.setLayoutParams(new GridView.LayoutParams(130, 130));
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
            if(Status.PLAYER1_WINS == game.getStatus()) Toast.makeText(getBaseContext(), tvJug1.getText()+" Has guanyat", Toast.LENGTH_LONG).show();

        } else {
            //Toast.makeText(getBaseContext(), "Grid Item " + (1) + " Selected", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putIntArray("Graella", graella);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        graella = savedInstanceState.getIntArray("Graella");
    }

}
