package com.example.marc.connect4.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marc.connect4.R;

public class LogFragment extends Fragment {

    private static final String PARCEL_FRAG_LOG = "log_frag";
    private static final String PARCEL_FRAG_LOG2 = "log_frag2";
    private static final String KEY_MOVIMENTS = "moviments";
    private static final String KEY_CONFIG = "config";


    TextView textView;
    TextView textView2;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            textView.setText(savedInstanceState.getString(PARCEL_FRAG_LOG));
            textView2.setText(savedInstanceState.getString(PARCEL_FRAG_LOG2));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);
        textView = view.findViewById(R.id.textLog);
        textView2 = view.findViewById(R.id.textConf);

        Intent a = getActivity().getIntent();
        if(a.getExtras() != null){
            textView.setText(a.getExtras().getString(KEY_MOVIMENTS));
            textView2.setText(a.getExtras().getString(KEY_CONFIG));
        }
        return view;
    }

    public void putLog(String text) {
        String textLog;
        textLog = textView.getText().toString() + "\n" + text;
        textView.setText(textLog);
    }

    public void clear(){
        textView.setText("");
        textView2.setText("");
    }

    public void putConfig(String text) {
        textView2.setText(text);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARCEL_FRAG_LOG, textView.getText().toString());
        outState.putString(PARCEL_FRAG_LOG2, textView2.getText().toString());

    }
}
