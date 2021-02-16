package com.example.elshare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elshare.adapter.ResidentialListingCustomAdapter;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;

import datamodel.APIInterface;
import datamodel.ResidentialListing;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class list_residential extends Fragment {
    String name;
    private ResidentialListing resi_list;
    private List<ResidentialListing> resi_array;
    private static ArrayList<DataModel> data;
    public static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
//    static String[] nameArray;
//    static Integer[] id;

    ArrayList<String> nameArray;
    ArrayList<Integer> id_array,user_array;
    private static ResidentialListingCustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_residential, container, false);
        myOnClickListener = new list_residential.MyOnClickListener(getContext());

        recyclerView = rootView.findViewById(R.id.recyclerView_resi);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<DataModel>();
        nameArray = new ArrayList<String>();
        id_array = new ArrayList<Integer>();
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

        Call<List<ResidentialListing>> call = service.getResidentialListing(i);
        Log.i("Call", String.valueOf(call.request().url()));

        call.enqueue(new Callback<List<ResidentialListing>>() {
            @Override
            public void onResponse(Call<List<ResidentialListing>> call, Response<List<ResidentialListing>> response) {

                Log.e("Residential response1:", String.valueOf(response.isSuccessful()));
                Log.e("Residential response2:", String.valueOf(response.code()));
                Log.e("Residential response3:", String.valueOf(response.errorBody()));
                if (response.body() != null) {
                    Log.i("Make  response: ", String.valueOf(response.body()));
                    resi_array = response.body();

                    ArrayList make_array = (ArrayList) response.body();

                    String demo_str;
                    int id_res,id_listing,address_id;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {

                        resi_list = resi_array.get(i);
                        demo_str = resi_list.getAddress().getAddress_1();
                        id_res = resi_list.getId();
                        id_listing=resi_list.getAddress().getId();
                        address_id=resi_list.getAddress_Id();
                        nameArray.add(demo_str);
                        id_array.add(id_res);
                        user_array.add(id_listing);
                        Log.i("address ID: ", String.valueOf(address_id));

                        Log.i("User ID: ", String.valueOf(user_array));



                    }
                    for (int j = 0; j < nameArray.size(); j++) {
                        data.add(new DataModel(
                                nameArray.get(j),
                                id_array.get(j),
                                user_array.get(j)

                        ));
                        if (id_array.isEmpty())
                        {
                            Toast.makeText(getContext(),"No listing available",Toast.LENGTH_LONG).show();
                        }
                        else {
                            adapter = new ResidentialListingCustomAdapter(data);
                            recyclerView.setAdapter(adapter);
                        }

                    }


                } else {
                    String new_str = "Make is empty";
                    Log.i("Make: ", new_str);
                }
            }

            @Override
            public void onFailure(Call<List<ResidentialListing>> call, Throwable t) {
                Log.i("Make error: ", String.valueOf(t));
                Toast.makeText(getContext(), "Please check network", Toast.LENGTH_LONG).show();


            }
        });
        if(adapter !=null) {
            adapter.setItemOnClickListner(new ResidentialListingCustomAdapter.ItemOnClickListner() {
                @Override
                public void OnItemClickListner(int position) {
//                    removeEntry(position);
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                    alertDialog.setTitle("Your title");
                    alertDialog.setMessage("your message ");
                    alertDialog.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // DO SOMETHING HERE

                        }
                    });

                    AlertDialog dialog = alertDialog.create();
                    dialog.show();

                }
            });
        }


        return rootView;
    }

    private void removeEntry(int position)
    {
        data.remove(position);
        adapter.notifyItemRemoved(position);
    }

//    public void deleteView(View v) {
//        int selectedItemPosition = recyclerView.getChildPosition(v);
//        RecyclerView.ViewHolder viewHolder
//                = recyclerView.findViewHolderForPosition(selectedItemPosition);
//        TextView textViewName
//                = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
//        String selectedName = (String) textViewName.getText();
//        int selectedItemId = -1;
//        for (int i = 0; i < nameArray.size(); i++) {
//            if (selectedName.equals(nameArray.get(i))) {
//                selectedItemId = id_array.get(i);
//            }
//        }
//        removedItems.add(selectedItemId);
//        data.remove(selectedItemPosition);
//        adapter.notifyItemRemoved(selectedItemPosition);
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

//    public void removeItem(int position) {
//        if (adapter.getItemCount() != 0) {
//            removeData.data.get(position);
//            data.remove(position);
//            adapter.notifyItemRemoved(position);
//        }
//    }

//    private void removeData(DataModel dataModel){
//        boolean removeData = databaseHelper.removeData(itemToRemove);
//        if (removeData) {
//            toastMessage("Item successfully deleted!");
//        } else {
//            toastMessage("Something is wrong");
//        }
//    }

//
//            private void removeItem(View v) {
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



}