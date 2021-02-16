package com.example.elshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elshare.utils.SingletonRetrofit;

import java.util.List;

import Booking_history_pojo.hostParticularBookingDetail;
import datamodel.APIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistoryDriverDetail extends AppCompatActivity implements View.OnClickListener{

    Toolbar mToolbar;
    TextView mAddress;
    TextView mOrderNumber;
    TextView mUnit;
    TextView mTime;
    TextView mTimeSlot;
    TextView mTotalAmount;
    TextView mBookedOn;
    TextView mStatus;
    Button mApprove;
    Button mDecline;
    Button mCancel;
    Button mCancelledByUser;
    SharedPreferences preferences;
    String booking_id;
    private List<hostParticularBookingDetail> booking_set;
    private  hostParticularBookingDetail booking_array;
    String bookedOnString;
    String chargerTohost;
    String timeSlotString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history_driver_detail);

        mToolbar = findViewById(R.id.DriverBookingDetailBack);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAddress=findViewById(R.id.DriverDetailBookingAddress);
        mOrderNumber=findViewById(R.id.DriverDetailBookingOrderNumbar);
        mUnit=findViewById(R.id.DriverDetailBookingUnit);
        mStatus=findViewById(R.id.DriverDetailBookingStatus);
        mTime=findViewById(R.id.DriverDetailBookingTime);
        mTimeSlot=findViewById(R.id.DriverDetailBookingTimeSlotes);
        mBookedOn=findViewById(R.id.DriverDetailBookingBookedOn);
        mTotalAmount=findViewById(R.id.DriverDetailBookingTotalAmount);

        mCancel=findViewById(R.id.BookingHistoryDriverDetailCancelBooking);

        mCancel.setOnClickListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        booking_id = preferences.getString("BH_BOOKING_ID", "null");
        bookedOnString=preferences.getString("BOOKED_ON","");


        if (booking_id.equals("null")) {
            Toast.makeText(getApplicationContext(),"No Detail Found!!", Toast.LENGTH_LONG).show();
        }
        else {
            final ProgressDialog mProgressDialog = new ProgressDialog(BookingHistoryDriverDetail.this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();
            final APIInterface service = SingletonRetrofit.getAPIInterface();
            Call<hostParticularBookingDetail> call = service.getBookingHistoryHostParticularBooking(booking_id);
            Log.i("Book url:", String.valueOf(call.request().url()));
            call.enqueue(new Callback<hostParticularBookingDetail>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<hostParticularBookingDetail> call, Response<hostParticularBookingDetail> response) {
                    if (response.body() != null) {
                        booking_array=response.body();
                        if (booking_array!=null)
                        {
                            mAddress.setText(booking_array.getAddress().getCity()+ ","+booking_array.getAddress().getState()+","+booking_array.getAddress().getAddress1() +"  " + booking_array.getAddress().getAddress2());
                            mOrderNumber.setText(booking_array.getPayment().getOrderId());
                            mUnit.setText(String.valueOf(booking_array.getBooking().getUnit()));
                            mTime.setText(String.valueOf(booking_array.getBooking().getMinute()));
                            mTotalAmount.setText(String.valueOf(booking_array.getBooking().getAmount()));
                            mBookedOn.setText(bookedOnString);
                            String time_slot_str=booking_array.getBooking().getStartDate() + " " + booking_array.getBooking().getStartTime() +" " +"to"+ " " +booking_array.getBooking().getEndDate() + " " + booking_array.getBooking().getEndTime();
                            String isApprove=booking_array.getBooking().getIsApproved();
                            String isCancle=booking_array.getBooking().getIsCancelled();
                            String isReject=booking_array.getBooking().getIsRejected();
                            mTimeSlot.setText(time_slot_str);
                            timeSlotString=booking_array.getBooking().getEndDate() + " " + booking_array.getBooking().getEndTime();
                            chargerTohost=String.valueOf(booking_array.getBooking().getChargedToHost());
                            Log.i("Boolean:",isApprove+isCancle+isReject);

                             if (isApprove.equals("1"))
                            {
                                mStatus.setText("Approve");
                                mStatus.setTextColor(Color.parseColor("#010483"));

                            }
                             else
                             {
                                 mStatus.setText("Pending");
                                 mStatus.setTextColor(Color.parseColor("#dc3545"));

                             }
                        }
                        mProgressDialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<hostParticularBookingDetail> call, Throwable t) {
                    Log.i("Error", String.valueOf(t.getMessage()));
                }
            });
        }

    }

    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.hostDetailCancel:
                bookingCancle(v);
                break;

        }
    }

    private void bookingCancle(View v)
    {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
            alertDialogBuilder.setMessage("Are you sure want to be cancel  booking?");
            alertDialogBuilder.setNegativeButton("OK",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            APIInterface service = SingletonRetrofit.getAPIInterface();
                            Call<ResponseBody> call = service.bookingCancle(booking_id);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.body() != null) {
                                        Intent intent = new Intent(getApplicationContext(), BookingHistoryDriver.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });

            alertDialogBuilder.show();
    }
}