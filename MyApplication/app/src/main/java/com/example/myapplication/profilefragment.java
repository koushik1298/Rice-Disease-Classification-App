package com.example.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class profilefragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile, container, false);
        ListView listView = (ListView)view.findViewById(R.id.listview);
        ArrayList<String> profile = new ArrayList<String>();
        profile.add("History");
        profile.add("Tip of the day");
        profile.add("Recommende pesticides this season");
        profile.add("Weather");
        String strtext = getArguments().getString("edttext");

        TextView textView=(TextView)view.findViewById(R.id.textView2);
        String g="Loggined as";
        String b=g+strtext;
        textView.setText(strtext);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, profile);
        listView.setAdapter(arrayAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

