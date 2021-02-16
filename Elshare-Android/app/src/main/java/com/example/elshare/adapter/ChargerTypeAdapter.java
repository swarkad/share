package com.example.elshare.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class ChargerTypeAdapter extends RecyclerView.Adapter <ChargerTypeAdapter.ViewHolder>{
    private ArrayList<String> makeStr;
    private ArrayList<String> chargerArray;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    private ArrayList<String> selectedCharger;
    int j;
    private Context context;
    boolean Check;

    public ChargerTypeAdapter(Context context, ArrayList<String> makeStr) {
        super();
        this.context = context;
        this.makeStr = makeStr;
        chargerArray=new ArrayList<>();
        selectedCharger=new ArrayList<>();
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();

    }

    @Override
    public ChargerTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.map_charger_type, viewGroup, false);
        return new ChargerTypeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ChargerTypeAdapter.ViewHolder viewHolder, final int i) {
        Gson gson = new Gson();
        String charger_str = preferences.getString("CHARGER_SET", "null");
        if (charger_str.equals("null")) { } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            selectedCharger = gson.fromJson(charger_str, type);
            Log.i("Selected charger:", String.valueOf(selectedCharger));
        }
        if (selectedCharger.contains(makeStr.get(i)))
        {
            viewHolder.chargerType.setChecked(true);
            chargerArray.add(makeStr.get(i));
        }
        else
        {
            viewHolder.chargerType.setChecked(false);
            chargerArray.remove(makeStr.get(i));
        }


        viewHolder.chargerType.setText(makeStr.get(i));
        viewHolder.chargerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(viewHolder.chargerType.isChecked())
               {
                   chargerArray.add(makeStr.get(i));
               }
               else
               {
                   chargerArray.remove(makeStr.get(i));
               }
                Log.i("Charger Type:",String.valueOf(chargerArray));
               Gson gson = new Gson();
               String json = gson.toJson(chargerArray);
               editor.putString("CHARGER_SET", json);
               editor.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return makeStr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox chargerType;

        ViewHolder(View itemView) {
            super(itemView);

            chargerType=itemView.findViewById(R.id.mapFilterChargerTypeItem);
        }
    }
}