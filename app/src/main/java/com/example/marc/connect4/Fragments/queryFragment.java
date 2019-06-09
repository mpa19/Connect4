package com.example.marc.connect4.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.marc.connect4.Pantalles.Pantalla_log;
import com.example.marc.connect4.R;
import com.example.marc.connect4.Sqlite3.DatabaseHelper;

public class queryFragment extends Fragment {
    DatabaseHelper dh;

    private static final String KEY_MOVIMENTS = "moviments";
    private static final String KEY_CONFIG = "config";
    int positionSelect;
    SimpleCursorAdapter adapter;
    Cursor results;
    ListView listView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        positionSelect = info.position;
        results.moveToPosition(info.position);

        getActivity().getMenuInflater().inflate(R.menu.menu_bdd, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.borrarMenu:
                dh.deleteRegister(results.getString(results.getColumnIndex(dh.COLUMN_ID)));
                refresh();
        }

        return super.onContextItemSelected(item);
    }

    private void refresh(){
        adapter = new SimpleCursorAdapter(getActivity(),
                R.layout.itemlist,
                results = dh.getResultat(),
                new String[] { dh.COLUMN_JUGADOR1, dh.COLUMN_JUGADOR2, dh.COLUMN_RESULTADO, dh.COLUMN_DATA },
                new int[] { R.id.tvJugador1, R.id.tvJug2, R.id.tvResult, R.id.tvDataRE });

        listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_query_fragment, container, false);
        dh = new DatabaseHelper(getActivity());
        listView = view.findViewById(R.id.lvResult);
        refresh();

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                results.moveToPosition(position);

                Cursor cursorReg = dh.getMoviments(results.getString(results.getColumnIndex(dh.COLUMN_ID)));
                if(cursorReg.moveToFirst()){
                    String moviments = cursorReg.getString(cursorReg.getColumnIndex(dh.COLUMN_MOVIMIENTOS));
                    String config = getString(R.string.alias1)+cursorReg.getString(cursorReg.getColumnIndex(dh.COLUMN_JUGADOR1));
                    if((cursorReg.getString(cursorReg.getColumnIndex(dh.COLUMN_JUGADOR2))).equals("")){
                        config = config+"\n"+getString(R.string.alias2)+getString(R.string.alias3);
                    }else config = config+"\n"+getString(R.string.alias2)+(cursorReg.getString(cursorReg.getColumnIndex(dh.COLUMN_JUGADOR2))).split(" ")[1];
                    config = config+"\n"+getString(R.string.data1)+cursorReg.getString(cursorReg.getColumnIndex(dh.COLUMN_DATA));
                    config = config+"\n"+getString(R.string.graella1)+cursorReg.getString(cursorReg.getColumnIndex(dh.COLUMN_GRAELLA))+"x7";
                    if((cursorReg.getString(cursorReg.getColumnIndex(dh.COLUMN_TIEMPO))).equals("50")){
                        config = config+"\n"+getString(R.string.controlDesc);
                    } else {
                        config = config+"\n"+getString(R.string.controlActiu);
                        if(!((cursorReg.getString(cursorReg.getColumnIndex(dh.COLUMN_TIEMPO))).equals("-1"))) {
                            moviments = moviments + getString(R.string.sobrat) + cursorReg.getString(cursorReg.getColumnIndex(dh.COLUMN_TIEMPO));
                        }
                    }
                    sendData(moviments);
                    sendConf(config, moviments);

                }
            }
        });

        return view;
    }

    public void sendData(String text) {
        LogFragment logFragment = (LogFragment) getFragmentManager().findFragmentById(R.id.logFragment);
        if (logFragment != null && logFragment.isInLayout()) {
            logFragment.clear();
            logFragment.putLog(text);
        }
    }

    public void sendConf(String text, String text2) {
        LogFragment logFragment = (LogFragment) getFragmentManager().findFragmentById(R.id.logFragment);
        if (logFragment != null && logFragment.isInLayout())
            logFragment.putConfig(text);
        else {
            Intent a = new Intent(getActivity(), Pantalla_log.class);
            a.putExtra(KEY_MOVIMENTS, text2);
            a.putExtra(KEY_CONFIG, text);
            startActivity(a);
        }
    }

}
