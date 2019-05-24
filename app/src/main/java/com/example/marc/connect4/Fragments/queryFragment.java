package com.example.marc.connect4.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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

        return view;
    }

}
