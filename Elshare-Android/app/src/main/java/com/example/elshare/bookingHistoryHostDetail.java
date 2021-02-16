package com.example.elshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elshare.utils.SingletonRetrofit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Booking_history_pojo.hostParticularBookingDetail;
import datamodel.APIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class bookingHistoryHostDetail extends AppCompatActivity implements View.OnClickListener {

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
        setContentView(R.layout.activity_booking_history_host_detail);
        mToolbar = findViewById(R.id.hostBookingDetailBack);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAddress=findViewById(R.id.hostBookingAddress);
        mOrderNumber=findViewById(R.id.hostBookingOrderNumbar);
        mUnit=findViewById(R.id.hostBookingUnit);
        mStatus=findViewById(R.id.hostBookingStatus);
        mTime=findViewById(R.id.hostBookingTime);
        mTimeSlot=findViewById(R.id.hostBookingTimeSlotes);
        mBookedOn=findViewById(R.id.hostBookingBookedOn);
        mTotalAmount=findViewById(R.id.hostBookingTotalAmount);

        mApprove=findViewById(R.id.hostDetailApprove);
        mCancel=findViewById(R.id.hostDetailCancel);
        mDecline=findViewById(R.id.hostDetailDecline);
        mCancelledByUser=findViewById(R.id.hostDetailCancelled);

        mApprove.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mDecline.setOnClickListener(this);
        mCancelledByUser.setOnClickListener(this);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        booking_id = preferences.getString("BH_BOOKING_ID", "null");
        bookedOnString=preferences.getString("BOOKED_ON","");


        if (booking_id.equals("null")) {
            Toast.makeText(getApplicationContext(),"No Detail Found!!", Toast.LENGTH_LONG).show();
        }
        else {
             final ProgressDialog mProgressDialog = new ProgressDialog(bookingHistoryHostDetail.this);
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

                             if(isCancle.equals("1"))
                            {
                                mCancelledByUser.setVisibility(View.VISIBLE);
                                mApprove.setVisibility(View.GONE);
                                mDecline.setVisibility(View.GONE);
                                mCancel.setVisibility(View.GONE);
                                mStatus.setText("Cancelled");
                                mStatus.setTextColor(Color.parseColor("#dc3545"));

                            }
                            else if (isReject.equals("1"))
                            {
                                mApprove.setVisibility(View.GONE);
                                mDecline.setVisibility(View.GONE);
                                mCancel.setVisibility(View.GONE);
                                mStatus.setText("Rejected");
                                mCancelledByUser.setVisibility(View.GONE);
                                mStatus.setTextColor(Color.parseColor("#dc3545"));


                            }
                            else if(isCancle.equals("0") & isReject.equals("0") & isApprove.equals("0")){
                                mApprove.setVisibility(View.VISIBLE);
                                mDecline.setVisibility(View.VISIBLE);
                                mCancel.setVisibility(View.GONE);
                                 mCancelledByUser.setVisibility(View.GONE);
                                 mStatus.setText("Pending");
                                 mStatus.setTextColor(Color.parseColor("#FBD11D"));

                             }
                            else if (isApprove.equals("1"))
                             {
                                 mApprove.setVisibility(View.GONE);
                                 mDecline.setVisibility(View.GONE);
                                 mCancel.setVisibility(View.VISIBLE);
                                 mCancelledByUser.setVisibility(View.GONE);
                                 mStatus.setText("Approve");
                                 mStatus.setTextColor(Color.parseColor("#010483"));

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

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.hostDetailApprove:
                    approveBooking(v);
                break;
            case R.id.hostDetailCancelled:

                break;
            case R.id.hostDetailCancel:
                bookingCancle(v);
                break;
            case R.id.hostDetailDecline:
                bookingDecline(v);
                break;
        }
    }

    private void bookingCancle(View view)
    {

        if (chargerTohost!=null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
            alertDialogBuilder.setMessage("Are you sure want to be cancel fees booking? You will be charger " + chargerTohost + "Rs for cancellation.");
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
                                        Intent intent = new Intent(getApplicationContext(), bookingHistoryHostDetail.class);
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
        else
        {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
            alertDialogBuilder.setMessage("Are you sure want to be cancel fees booking? You will be charger 0 Rs for cancellation.");
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
                                        Intent intent = new Intent(getApplicationContext(), bookingHistoryHostDetail.class);
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

    private void bookingDecline(View view)
    {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setMessage("Are you sure want to be cancel fees booking? You will be charger 0 Rs for cancellation.");
        alertDialogBuilder.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        APIInterface service = SingletonRetrofit.getAPIInterface();
                        Call<ResponseBody> call = service.hostBookingDecline(booking_id);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.body() != null) {
                                    Intent intent = new Intent(getApplicationContext(), bookingHistoryHostDetail.class);
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

    private void approveBooking(final View view) {
        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM HH:mm");
        String getCurrentDateTime = sdf.format(c.getTime());
        Log.d("getCurrentDateTime",getCurrentDateTime);
//        String timeSlotTime=sdf.format(timeSlotString);
        Log.i("End Time",timeSlotString);


        if (getCurrentDateTime.compareTo(timeSlotString) < 0)
        {
            Log.i("Time not old","");
            APIInterface service = SingletonRetrofit.getAPIInterface();
            Call<ResponseBody> call = service.hostBookingApprove(booking_id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.body() != null) {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                        alertDialogBuilder.setTitle("Thank you for approval!!");
                        alertDialogBuilder.setMessage("Booking Confirm");
                        alertDialogBuilder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent intent = new Intent(getApplicationContext(), HostBookingHistory.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                        alertDialogBuilder.show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "This booking already outdated so you not able to approve this booking now.", Toast.LENGTH_LONG).show();

        }


    }
}