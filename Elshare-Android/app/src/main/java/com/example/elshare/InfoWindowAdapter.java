package com.example.elshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private  final View mwindow;
    private Context mcontext;

    public InfoWindowAdapter(Context context) {
        mcontext= context;
        mwindow= LayoutInflater.from(context).inflate(R.layout.cursor_layout,null);
    }

    @Override
    public View getInfoWindow(Marker marker) {

        rendowWindowText(marker,mwindow);
        return null;
    }
    private void rendowWindowText(Marker marker,View view)
    {
        String title = marker.getTitle();
        TextView brand_str=(TextView) view.findViewById(R.id.marker_brand);

    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker,mwindow);
        return null;
    }
}
