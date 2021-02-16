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
import java.util.List;


public class SocketTypeAdapter extends RecyclerView.Adapter <SocketTypeAdapter.ViewHolder>{
    private ArrayList<String> makeStr;
    private ArrayList<String> socketArray;
    SharedPreferences.Editor editor;
    private ArrayList<String> selectedSocket;
    SharedPreferences preferences;


    private Context context;

    public SocketTypeAdapter(Context context, ArrayList<String> makeStr) {
        super();
        this.context = context;
        this.makeStr = makeStr;
        socketArray =new ArrayList<>();
        selectedSocket=new ArrayList<>();

        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor=preferences.edit();

    }

    @Override
    public SocketTypeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.map_charger_type, viewGroup, false);
        return new SocketTypeAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final SocketTypeAdapter.ViewHolder viewHolder, final int i) {

        Gson gson = new Gson();
        String socket_str = preferences.getString("SOCKET_SET", "null");
        if (socket_str.equals("null")) { } else {
            Type type = new TypeToken<List<String>>() {
            }.getType();
            selectedSocket = gson.fromJson(socket_str, type);
            Log.i("Selected socket:", String.valueOf(selectedSocket));
        }
        if (selectedSocket.contains(makeStr.get(i)))
        {
            viewHolder.socketType.setChecked(true);
            socketArray.add(makeStr.get(i));
        }
        else
        {
            viewHolder.socketType.setChecked(false);
            socketArray.remove(makeStr.get(i));
        }

        viewHolder.socketType.setText(makeStr.get(i));
        viewHolder.socketType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.socketType.isChecked())
                {
                    socketArray.add(makeStr.get(i));
                }
                else
                {
                    socketArray.remove(makeStr.get(i));
                }
                Log.i("Socket Type:",String.valueOf(socketArray));
                Gson gson = new Gson();
                String json = gson.toJson(socketArray);
                editor.putString("SOCKET_SET", json);
                editor.commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return makeStr.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox socketType;

        ViewHolder(View itemView) {
            super(itemView);

            socketType =itemView.findViewById(R.id.mapFilterChargerTypeItem);
        }
    }
}