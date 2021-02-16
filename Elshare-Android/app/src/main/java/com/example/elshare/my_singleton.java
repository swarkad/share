package com.example.elshare;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class my_singleton
{
    private static my_singleton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;
    private my_singleton (Context Context)
    {
        mCtx = Context;
        requestQueue = getRequestQueue();
    }

    public static  synchronized my_singleton getInstance (Context context)
    {
        if (mInstance==null)
        {
            mInstance =new my_singleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;

    }

    public<T> void addTorequestque(Request<T> request)
    {
        requestQueue.add(request);
    }
}
