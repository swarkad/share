package com.example.elshare.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.elshare.utils.SingletonRetrofit;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import datamodel.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.elshare.AddAnotherResidentialFragment.trimDoubbleQuote;
import static com.example.elshare.utils.ElshareConstants.GOOGLE_MAP_API_KEY;

public class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
    private ArrayList<String> addressValueList = new ArrayList<>();
    private ArrayList<String> addressKeyList = new ArrayList<>();
    private Map<String, String> addressMap = new HashMap<>();

    public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        return addressValueList.size();
    }

    @Override
    public String getItem(int index) {
        return addressValueList.get(index);
        //  return addressKeyList.get(index);
    }

    public String getPlaceID(int index) {
        return addressKeyList.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    addressMap.clear();
                    addressKeyList.clear();
                    addressValueList.clear();
                    addressMap = autocomplete(constraint.toString());
                    for (Map.Entry<String,String> entry : addressMap.entrySet()) {
                        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        addressKeyList.add(entry.getKey());
                        addressValueList.add(entry.getValue());
                    }

                    // Assign the data to the FilterResults
                    filterResults.values = addressValueList;
                    filterResults.count = addressValueList.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    public static Map<String, String> autocomplete(String input) {
        final Map<String, String> addressMap = new HashMap<>();
        String newUrl = "https://maps.googleapis.com/maps/api/place/autocomplete/";
        final APIInterface service3 = SingletonRetrofit.getGoogleMapRetrofit(newUrl).create(APIInterface.class);
        Call<JsonObject> call3 = service3.getPlaceId(GOOGLE_MAP_API_KEY, input);
        call3.enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray predictionArray = ((JsonArray)response.body().get("predictions"));
                for(int i = 0; i < predictionArray.size(); i++) {
                    String description = ((JsonObject) (predictionArray).get(i)).get("description").toString();
                    String place_id = ((JsonObject) (predictionArray).get(i)).get("place_id").toString();
                    place_id = trimDoubbleQuote(place_id);
                    Log.d("description : ", description);
                    Log.d("place id : ", place_id);
                    addressMap.put(place_id, description);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });

        try{
            Thread.sleep(2000);
        }catch(InterruptedException e) {
            System.out.println(e);
        }

        return addressMap;
    }


}
