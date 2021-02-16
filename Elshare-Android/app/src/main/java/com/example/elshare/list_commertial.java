package com.example.elshare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.adapter.CommercialListingCustomAdapter;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;

import datamodel.APIInterface;
import datamodel.CommertialListing;
import datamodel.PublicListing;
import datamodel.ResidentialListing;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class list_commertial extends Fragment
{
    String name;
    private CommertialListing com_list;
    private List<CommertialListing> com_array;
    private static CommercialListingCustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
//    static String[] nameArray;
//    static Integer[] id_;
    ArrayList<String> nameArray;

    ArrayList<Integer> id_array,user_array;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_commertial, container, false);

        myOnClickListener = new list_commertial.MyOnClickListener(getContext());

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<DataModel>();
        nameArray=new ArrayList<String>();
        id_array=new ArrayList<Integer>();
        user_array = new ArrayList<Integer>();


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

        Call<List<CommertialListing>> call = service.getCommercialListing(i);
        Log.i("Call", String.valueOf(call.request().url()));

        call.enqueue(new Callback<List<CommertialListing>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<List<CommertialListing>> call, Response<List<CommertialListing>> response) {

                Log.e("PublicListing response1:", String.valueOf(response.isSuccessful()));
                Log.e("PublicListing response2:", String.valueOf(response.code()));
                Log.e("PublicListing response3:", String.valueOf(response.errorBody()));
                if (response.body() != null) {
                    Log.i("PublicListing  response: ", String.valueOf(response.body()));
                    com_array = response.body();

                    ArrayList make_array = (ArrayList) response.body();

                    String demo_str;
                    int id_res,id_listing;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        //  Log.i("login response: ", String.valueOf((make_array.get(i))));
                        com_list = com_array.get(i);
                        demo_str = com_list.getAddress().getAddress_1();
                        id_res = com_list.getId();
                        id_listing=com_list.getAddress().getId();
//                        Log.i("Make response Array: ", String.valueOf(nameArray.size()));
                        nameArray.add(demo_str);
                        id_array.add(id_res);
                        user_array.add(id_listing);


                    }
                    for (int j = 0; j < nameArray.size(); j++)
                    {
                        data.add(new DataModel(
                                nameArray.get(j),
                                id_array.get(j),
                                user_array.get(j)

                        ));
                        adapter = new CommercialListingCustomAdapter(data);
                        recyclerView.setAdapter(adapter);

                    }


                } else {
                    String new_str = "PublicListing is empty";
                    Log.i("PublicListing: ", new_str);
                    //  Toast.makeText(getContext(), "Please Select make", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<CommertialListing>> call, Throwable t) {
                Log.i("PublicListing error: ", String.valueOf(t));

            }
        });

        return  rootView;
    }
//
//    static class MyOnClickListener implements View.OnClickListener {
//
//        private final Context context;
//
//        MyOnClickListener(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        public void onClick(View v) {
//            removeItem(v);
//        }
//
//        private void removeItem(View v) {
//            int selectedItemPosition = recyclerView.getChildPosition(v);
//            RecyclerView.ViewHolder viewHolder
//                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
//            TextView textViewName
//                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
//            String selectedName = (String) textViewName.getText();
//            int selectedItemId = -1;
//            for (int i = 0; i <nameArray.length; i++) {
//                if (selectedName.equals(nameArray[i])) {
//                    selectedItemId = id_[i];
//                }
//            }
//            removedItems.add(selectedItemId);
//            data.remove(selectedItemPosition);
//            adapter.notifyItemRemoved(selectedItemPosition);
//        }
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        super.onCreateOptionsMenu(menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        super.onOptionsItemSelected(item);
//        if (item.getItemId() == R.id.add_item) {
//            //check if any items to add
//            if (removedItems.size() != 0) {
//                addRemovedItemToList();
//            } else {
//                Toast.makeText(getContext(),"Nothing to add", Toast.LENGTH_SHORT).show();
//            }
//        }
//        return true;
//    }

//    private void addRemovedItemToList() {
//        int addItemAtListPosition = 3;
//        data.add(addItemAtListPosition, new DataModel(
//                MyData.nameArray[removedItems.get(0)],
//                MyData.versionArray[removedItems.get(0)],
//                MyData.id_[removedItems.get(0)],
//                MyData.drawableArray[removedItems.get(0)]
//        ));
//        adapter.notifyItemInserted(addItemAtListPosition);
//        removedItems.remove(0);
//    }

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
