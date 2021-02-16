package com.example.elshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elshare.adapter.hostBookingHistoryAdapter;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;

import Booking_history_pojo.HostBookingHistoryDetail;
import Booking_history_pojo.HostBookingHistoryPojo;
import datamodel.APIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostBookingHistory extends AppCompatActivity {
    Toolbar mToolbar;
    RecyclerView mBookingHistoryRecycleView;
    hostBookingHistoryAdapter mHostBookingAdapter;
    RecyclerView.LayoutManager mBookinhHistoryLayout;
    String userId;
    SharedPreferences preferences;
    private List<HostBookingHistoryPojo> booking_set  ;
    private  HostBookingHistoryPojo booking_array ;
    private List<HostBookingHistoryDetail> bookingHistoryPojo_set;
    private  HostBookingHistoryDetail bookingHistoryPojo_array ;
    Button mNextButton;
    Button mPreviousButton;
    Integer last_page;
    Integer cuurent_page;
    Integer totalBooking;
    TextView pageCount;
    Integer bookingCountInteger;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_booking_history);
        mToolbar = findViewById(R.id.hostBookingHistoryBack);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBookingHistoryRecycleView = findViewById(R.id.bookingHistoryHost_recycleview);
        mNextButton =findViewById(R.id.nextBtn);
        mPreviousButton =findViewById(R.id.prevBtn);
        pageCount=findViewById(R.id.bookingHistoryHostCount);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        userId = preferences.getString("Name", "null");


        if (userId.equals("null")) {
            Toast.makeText(getApplicationContext(),"User not login!!", Toast.LENGTH_LONG).show();
        }
        else {
            cuurent_page=1;
            initialState();
        }


        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPrevious();
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goforward();
            }
        });
    }


    private void goforward()
    {
        cuurent_page=cuurent_page +1;
     if (cuurent_page<last_page|| cuurent_page.equals(last_page))
     {
         initialState();
     }
     else {

     }
    }

    private void goPrevious()
    {
        cuurent_page=cuurent_page -1;
        if (cuurent_page>0 )
        {
            initialState();
        }
        else {

        }
    }

    private void initialState()
    {
        Log.i("Call ID", userId);
        final ArrayList<String> orderNumberArray = new ArrayList<>();
        final ArrayList<String> bookingDateArray = new ArrayList<>();
        final ArrayList<String> totalAmountArray = new ArrayList<>();
        final ArrayList<String> bookingId = new ArrayList<>();
        final ArrayList<String> bookingCount = new ArrayList<>();
        final String role_string="Host";

        final ProgressDialog mProgressDialog = new ProgressDialog(HostBookingHistory.this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        APIInterface service = SingletonRetrofit.getAPIInterface();
        Call<HostBookingHistoryPojo> call = service.getBookingHistoryHostNext(userId,cuurent_page);
        call.enqueue(new Callback<HostBookingHistoryPojo>() {
            @Override
            public void onResponse(Call<HostBookingHistoryPojo> call, Response<HostBookingHistoryPojo> response) {
                if (response.body() != null) {
                    bookingHistoryPojo_set=response.body().getData();
                    if (bookingHistoryPojo_set!=null)
                    {
                        int i;
                        for (i=0;i<bookingHistoryPojo_set.size();i++)
                        {

                            if (cuurent_page==1)
                            {
                                mPreviousButton.setVisibility(View.GONE);
                            }
                            else
                            {
                                mPreviousButton.setVisibility(View.VISIBLE);
                            }
                            if (cuurent_page.equals(last_page))
                            {
                                mNextButton.setVisibility(View.GONE);
                            }
                            else
                            {
                                mNextButton.setVisibility(View.VISIBLE);
                            }
                            pageCount.setText(String.valueOf("Page "+cuurent_page));
                            bookingHistoryPojo_array=bookingHistoryPojo_set.get(i);
                            last_page=Integer.parseInt(response.body().getLast_page());
                            totalBooking=Integer.parseInt(response.body().getTotal());
                            orderNumberArray.add(bookingHistoryPojo_array.getPayment().getOrderId());
                            totalAmountArray.add(String.valueOf(bookingHistoryPojo_array.getAmount()));
                            bookingDateArray.add(bookingHistoryPojo_array.getCreated());
                            bookingId.add(String.valueOf(bookingHistoryPojo_array.getId()));

                                int page_new=cuurent_page-1;
                                Log.i("CurrentPage:", String.valueOf(page_new));
                                bookingCountInteger=((page_new*6)+(i+1));
                                bookingCount.add(String.valueOf(bookingCountInteger));

                        }
                    }

                    mBookingHistoryRecycleView.setVisibility(View.VISIBLE);
                    mBookingHistoryRecycleView.setHasFixedSize(true);
                    mBookinhHistoryLayout = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                    mBookingHistoryRecycleView.setLayoutManager(mBookinhHistoryLayout);
                    mHostBookingAdapter = new hostBookingHistoryAdapter(getApplicationContext(),orderNumberArray, totalAmountArray, bookingDateArray,bookingId,bookingCount,role_string);
                    mBookingHistoryRecycleView.setAdapter(mHostBookingAdapter);
                    mProgressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<HostBookingHistoryPojo> call, Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onResume() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBookingHistoryRecycleView = findViewById(R.id.bookingHistoryHost_recycleview);
        mNextButton =findViewById(R.id.nextBtn);
        mPreviousButton =findViewById(R.id.prevBtn);
        pageCount=findViewById(R.id.bookingHistoryHostCount);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        userId = preferences.getString("Name", "null");


        if (userId.equals("null")) {
            Toast.makeText(getApplicationContext(),"User not login!!", Toast.LENGTH_LONG).show();
        }
        else {
            cuurent_page=1;
            initialState();
        }


        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPrevious();
            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goforward();
            }
        });
        super.onResume();
    }
}