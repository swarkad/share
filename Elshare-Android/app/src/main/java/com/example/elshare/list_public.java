package com.example.elshare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.adapter.PublicListingCustomAdapter;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;

import datamodel.APIInterface;
import datamodel.PublicListing;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class list_public extends Fragment
{
    String name;
    private PublicListing resi_list;
    private List<PublicListing> resi_array;
    private static ArrayList<DataModel> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;


    ArrayList<String> nameArray;
    ArrayList<Integer> id_array,user_array;
    private static PublicListingCustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_public, container, false);

        myOnClickListener = new list_public.MyOnClickListener(getContext());

        recyclerView = rootView.findViewById(R.id.recyclerView_public);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<DataModel>();
        nameArray=new ArrayList<String>();
        id_array=new ArrayList<Integer>();
        user_array=new ArrayList<Integer>();


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        name = preferences.getString("Name", "");
        Log.i("Call ID", name);
        int i = 0;
        if(!TextUtils.isEmpty(name)) {
            try {
                i = Integer.parseInt(String.valueOf(name));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        APIInterface service = SingletonRetrofit.getAPIInterface();

        Call<List<PublicListing>> call = service.getPublicListing(i);
        Log.i("Call", String.valueOf(call.request().url()));

        call.enqueue(new Callback<List<PublicListing>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<List<PublicListing>> call, Response<List<PublicListing>> response) {

                Log.e("PublicListing response1:", String.valueOf(response.isSuccessful()));
                Log.e("PublicListing response2:", String.valueOf(response.code()));
                Log.e("PublicListing response3:", String.valueOf(response.errorBody()));
                if (response.body() != null) {
                    Log.i("PublicListing  response: ", String.valueOf(response.body()));
                    resi_array = response.body();

                    ArrayList make_array = (ArrayList) response.body();

                    String demo_str;
                    int id_res,id_lsting;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        //  Log.i("login response: ", String.valueOf((make_array.get(i))));
                        resi_list = resi_array.get(i);
                        demo_str = resi_list.getAddress().getAddress_1();
                        id_res = resi_list.getId();
                        id_lsting=resi_list.getUser_id();
//                        Log.i("Make response Array: ", String.valueOf(nameArray.size()));
                        nameArray.add(demo_str);
                        id_array.add(id_res);
                        user_array.add(id_lsting);


                    }
                    for (int j = 0; j < nameArray.size(); j++)
                    {
                        data.add(new DataModel(
                                nameArray.get(j),
                                id_array.get(j),
                                user_array.get(j)

                        ));
                        adapter = new PublicListingCustomAdapter(data);
                        recyclerView.setAdapter(adapter);


                    }


                } else {
                    String new_str = "PublicListing is empty";
                    Log.i("PublicListing: ", new_str);
                    //  Toast.makeText(getContext(), "Please Select make", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<PublicListing>> call, Throwable t) {
                Log.i("PublicListing error: ", String.valueOf(t));

            }
        });

        return  rootView;
    }
    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
