package com.example.marc.connect4.Fragments;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.marc.connect4.Game.Log;
import com.example.marc.connect4.R;
import com.example.marc.connect4.Sqlite3.DatabaseHelper;

public class queryFragment extends Fragment {
    DatabaseHelper dh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_query_fragment, container, false);
        dh = new DatabaseHelper(getActivity());
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.itemlist,
                dh.getResultat(),
                new String[] { "jugador1", "jugador2", "resultado", "data" },
                new int[] { R.id.tvJugador1, R.id.tvJug2, R.id.tvResult, R.id.tvDataRE });

        ListView listView = view.findViewById(R.id.lvResult);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Cursor cursorReg = dh.getMoviments(Integer.toString(position+1));
                if(cursorReg.moveToFirst()){
                    String moviments = cursorReg.getString(cursorReg.getColumnIndex("movimientos"));
                    String config = "Jugador1: "+cursorReg.getString(cursorReg.getColumnIndex("jugador1"));
                    if((cursorReg.getString(cursorReg.getColumnIndex("jugador2"))).equals("")){
                        config = config+"\nJugador2: CPU";
                    }else config = config+"\nJugador2: "+(cursorReg.getString(cursorReg.getColumnIndex("jugador2"))).split(" ")[1];
                    config = config+"\nData: "+cursorReg.getString(cursorReg.getColumnIndex("data"));
                    config = config+"\nGraela: "+cursorReg.getString(cursorReg.getColumnIndex("graella"))+"x7";
                    if((cursorReg.getString(cursorReg.getColumnIndex("tiempo"))).equals("50")){
                        config = config+"\nControlador de temps: desactivat";
                    } else {
                        config = config+"\nControlador de temps: activat";
                        moviments = moviments + "\nHan sobrat: "+cursorReg.getString(cursorReg.getColumnIndex("tiempo"));
                    }
                    sendData(moviments);
                    sendConf(config);

                }
            }
        });

        return view;
    }

    public void sendData(String text) {
        LogFragment logFragment = (LogFragment) getFragmentManager().findFragmentById(R.id.logFragment);
        if (logFragment != null && logFragment.isInLayout())
            logFragment.clear();
            logFragment.putLog(text);
    }

    public void sendConf(String text) {
        LogFragment logFragment = (LogFragment) getFragmentManager().findFragmentById(R.id.logFragment);
        if (logFragment != null && logFragment.isInLayout())
            logFragment.putConfig(text);
    }

}
