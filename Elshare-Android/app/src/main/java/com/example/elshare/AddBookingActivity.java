package com.example.elshare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elshare.utils.DatePickerUniversal;
import com.example.elshare.utils.SingletonRetrofit;
import com.example.elshare.utils.TimePickerUniversal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import datamodel.APIInterface;
import datamodel.AddBooking;
import datamodel.AllVehicle;
import datamodel.BookingConfirmation;
import datamodel.MyProfilePojo;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBookingActivity extends AppCompatActivity implements View.OnClickListener, PaymentResultWithDataListener {

    private static final String TAG = AddBookingActivity.class.getSimpleName();

    private Spinner mAllAddedVehicleSpinner;
    private List<AllVehicle> mAllAddedVehicleList = new ArrayList<>();
    private RadioGroup mRadioGroup;
    private LinearLayout timeLinearLayout;
    private LinearLayout uniteLinearLayout;
    private LinearLayout amountLinearLayout;
    private Double mBatterySize = 3.00d;
    AddBooking user_data;

    private Double mHostPowerOutput = 15.00d;
    private Double mHostPrice = 12.24d;
    private int mHostId=0;
    private int mUserId=0;
    private String mTableName;
    private ArrayList<String> mAvailabilityDaysArray=new ArrayList<>();
    private ArrayList<String> mAvailabilityTimeArray=new ArrayList<>();
    private TextView mMaxTimeValueTextView;
    private TextView mMaxUniteValueTextView;
    private TextView mMaxAmountValueTextView;
    private CheckBox mHostAvailabilityButton;
    private TableRow mHostAvailabilityTableRow;
    private TextView mHostPriceTextView;

    private Button mTimeRadioTimeSlotTabButton;
    private Button mTimeRadioSummaryTabButton;
    private LinearLayout mTimeRadioTimeSlotTabLinearLayout;
    private LinearLayout mTimeRadioSummaryLinearLayout;
    private EditText mTimeRadioStartDateEditText;
    private EditText mTimeRadioStartTimeEditText;
    private EditText mTimeRadioEndDateEditText;
    private EditText mTimeRadioEndTimeEditText;
    private DatePickerUniversal mTimeRadioStartDate_DatePickerUniversal;
    private DatePickerUniversal mTimeRadioEndDate_DatePickerUniversal;
    private Button mTimeRadioNextButton;
    private String mTimeRadioSelectedTimeInMinutes;
    private TextView mTimeRadioTotalMinuteSelectedValueTextView;
    private TextView mTimeRadioTotalPriceValueTextView;
    private TextView mTimeRadioTotalUniteValueTextView;
    private Button mTimeRadioPreviousButton;
    private Button mTimeRadioProceedButton;

    private Button mUniteRadioUniteSlotButton;
    private Button mUniteRadioSummarySlotButton;
    private Button mUniteRadioTimeSlotButton;
    private LinearLayout mUniteRadioUniteLinearLayout;
    private EditText mUniteRadioInputUniteEditText;
    private TextView mUniteRadioAmountValueTextView;
    private Button mUniteRadioTenUniteButton;
    private Button mUniteRadioFifteenUniteButton;
    private Button mUniteRadioTwentyUniteButton;
    private Button mUniteRadioFromUniteScreenNextButton;
    private LinearLayout mUniteRadioSummaryLinearLayout;
    private TextView mUniteRadioRequiredMinutesTextView;
    private TextView mUniteRadioRequiredMinutesValueTextView;
    private TextView mUniteRadioTotalPriceTextView;
    private TextView mUniteRadioTotalPriceValueTextView;
    private TextView mUniteRadioTotalUniteTextView;
    private TextView mUniteRadioTotalUniteValueTextView;
    private TextView mUniteRadioTotalMinuteSelectedTextView;
    private TextView mUniteRadioTotalMinuteSelectedValueTextView;
    private Button mUniteRadioFromSummaryScreenPreviousButton;
    private Button mUniteRadioFromSummaryScreenNextButton;
    private LinearLayout mUniteRadioTimeSlotLinearLayout;
    private EditText mUniteRadioStartDateEditText;
    private EditText mUniteRadioStartTimeEditText;
    private EditText mUniteRadioEndDateEditText;
    private EditText mUniteRadioEndTimeEditText;
    private Button mUniteRadioFromTimeSlotPreviousButton;
    private Button mUniteRadioFromTimeSlotProceedButton;
    private DatePickerUniversal mUniteRadioStartDate_DatePickerUniversal;
    private DatePickerUniversal mUniteRadioEndDate_DatePickerUniversal;


    private Button mAmountRadioUniteSlotButton;
    private Button mAmountRadioSummarySlotButton;
    private Button mAmountRadioTimeSlotButton;
    private LinearLayout mAmountRadioUniteLinearLayout;
    private EditText mAmountRadioInputAmountEditText;
    private TextView mAmountRadioUniteValueTextView;
    private Button mAmountRadioTenUniteButton;
    private Button mAmountRadioFifteenUniteButton;
    private Button mAmountRadioTwentyUniteButton;
    private Button mAmountRadioFromUniteScreenNextButton;
    private LinearLayout mAmountRadioSummaryLinearLayout;
    private TextView mAmountRadioRequiredMinutesTextView;
    private TextView mAmountRadioRequiredMinutesValueTextView;
    private TextView mAmountRadioTotalPriceTextView;
    private TextView mAmountRadioTotalPriceValueTextView;
    private TextView mAmountRadioTotalUniteTextView;
    private TextView mAmountRadioTotalUniteValueTextView;
    private TextView mAmountRadioTotalMinuteSelectedTextView;
    private TextView mAmountRadioTotalMinuteSelectedValueTextView;
    private Button mAmountRadioFromSummaryScreenPreviousButton;
    private Button mAmountRadioFromSummaryScreenNextButton;
    private LinearLayout mAmountRadioTimeSlotLinearLayout;
    private EditText mAmountRadioStartDateEditText;
    private EditText mAmountRadioStartTimeEditText;
    private EditText mAmountRadioEndDateEditText;
    private EditText mAmountRadioEndTimeEditText;
    private Button mAmountRadioFromTimeSlotPreviousButton;
    private Button mAmountRadioFromTimeSlotProceedButton;
    private DatePickerUniversal mAmountRadioStartDate_DatePickerUniversal;
    private DatePickerUniversal mAmountRadioEndDate_DatePickerUniversal;
    SharedPreferences preferences;
    private TextView mHostAvailabilityTextView;

    RequestBody bodyUserId;
    RequestBody bodyHostId;
    RequestBody bodyTime;
    RequestBody bodyAmount;
    RequestBody bodyUnit;
    RequestBody bodyStartTime;
    RequestBody bodyEndTime;
    RequestBody bodyStartDate;
    RequestBody bodyEndDate;
    RequestBody bodyTable;
    RequestBody bodyBookingId;
    RequestBody bodySignature;
    RequestBody bodyPaymentId;
    RequestBody bodyOrderId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mHostAvailabilityTextView=findViewById(R.id.host_availability_text_view);
        try {
            mHostId=Integer.parseInt(preferences.getString("HOST_ID",""));
            mUserId=Integer.parseInt(preferences.getString("USER_ID",""));
            mHostPowerOutput=Double.parseDouble(preferences.getString("HOST_BOOKING_POWER_OUTPUT",""));
            mHostPrice=Double.parseDouble(preferences.getString("HOST_BOOKING_PRICE",""));
            mTableName=preferences.getString("TABLE_NAME","");
            Gson gson = new Gson();

            String dayArrayString = preferences.getString("DAY_ARRAY", null);
            String timeArrayString = preferences.getString("TIME_ARRAY", null);

            if (dayArrayString.isEmpty()) {
            }
            else {
                Type type = new TypeToken<List<String>>() {
                }.getType();
                mAvailabilityDaysArray = gson.fromJson(dayArrayString, type);
            }

            if (timeArrayString.isEmpty()) {
                Type type = new TypeToken<List<String>>() {
                }.getType();
                mAvailabilityTimeArray = gson.fromJson(timeArrayString, type);
            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }

        StringBuilder stringBuilderDayAvailability = new StringBuilder("");
        int listSize = mAvailabilityDaysArray.size();

        for (int itemIndex = 0; itemIndex < mAvailabilityDaysArray.size(); itemIndex++) {
            stringBuilderDayAvailability.append(mAvailabilityDaysArray.get(itemIndex)  + "\n" );
        }
        mHostAvailabilityTextView.setText(stringBuilderDayAvailability.toString());

        mAllAddedVehicleSpinner = findViewById(R.id.all_added_vehicle_spinner);
        mRadioGroup = (RadioGroup) findViewById(R.id.bookingTypeRadioGroup);
        timeLinearLayout = findViewById(R.id.time_linear_layout);
        uniteLinearLayout = findViewById(R.id.unite_linear_layout);
        amountLinearLayout = findViewById(R.id.amount_linear_layout);
        mMaxTimeValueTextView = findViewById(R.id.max_time_value_textView);
        mMaxUniteValueTextView = findViewById(R.id.max_unite_value_textView);
        mMaxAmountValueTextView = findViewById(R.id.max_amount_value_textView);
        mHostAvailabilityButton = findViewById(R.id.host_availability_button);
        mHostAvailabilityTableRow = findViewById(R.id.host_availability_row);
        mHostPriceTextView = findViewById(R.id.host_price_text_view);

        mTimeRadioTimeSlotTabButton = findViewById(R.id.time_radio_time_slot_button);
        mTimeRadioSummaryTabButton = findViewById(R.id.time_radio_summary_button);
        mTimeRadioTimeSlotTabLinearLayout = findViewById(R.id.time_radio_time_slot_linear_layout);
        mTimeRadioSummaryLinearLayout = findViewById(R.id.time_radio_summary_linear_layout);
        mTimeRadioStartDateEditText = findViewById(R.id.time_radio_startDateEditText);
        mTimeRadioStartTimeEditText = findViewById(R.id.time_radio_startTimeEditText);
        mTimeRadioEndDateEditText = findViewById(R.id.time_radio_endDateEditText);
        mTimeRadioEndTimeEditText = findViewById(R.id.time_radio_endTimeEditText);
        mTimeRadioNextButton = findViewById(R.id.time_radio_nextButton);
        mTimeRadioTotalMinuteSelectedValueTextView = findViewById(R.id.time_radio_total_minute_selected_value_textView);
        mTimeRadioTotalPriceValueTextView = findViewById(R.id.time_radio_total_price_value_textView);
        mTimeRadioTotalUniteValueTextView = findViewById(R.id.time_radio_total_unite_value_textView);
        mTimeRadioPreviousButton = findViewById(R.id.time_radio_previousButton);
        mTimeRadioProceedButton = findViewById(R.id.time_radio_proceedButton);
        mTimeRadioProceedButton.setOnClickListener(this);


        mUniteRadioUniteSlotButton = findViewById(R.id.unite_radio_select_unit_slot_button);
        mUniteRadioSummarySlotButton = findViewById(R.id.unite_radio_unit_summary_button);
        mUniteRadioTimeSlotButton = findViewById(R.id.unite_radio_time_slot_button);
        mUniteRadioUniteLinearLayout = findViewById(R.id.unite_radio_unite_linear_layout);
        mUniteRadioInputUniteEditText = findViewById(R.id.unite_radio_input_unite_edit_text);
        mUniteRadioAmountValueTextView = findViewById(R.id.unite_radio_amount_value_text_view);
        mUniteRadioTenUniteButton = findViewById(R.id.unite_radio_ten_unites_button);
        mUniteRadioFifteenUniteButton = findViewById(R.id.unite_radio_fifteen_unites_button);
        mUniteRadioTwentyUniteButton = findViewById(R.id.unite_radio_twenty_unites_button);
        mUniteRadioFromUniteScreenNextButton = findViewById(R.id.unite_radio_from_unite_screen_next_button);
        mUniteRadioSummaryLinearLayout = findViewById(R.id.unite_radio_summary_linear_layout);
        mUniteRadioRequiredMinutesTextView = findViewById(R.id.unite_radio_required_minutes_textView);
        mUniteRadioRequiredMinutesValueTextView = findViewById(R.id.unite_radio_required_minutes_value_textView);
        mUniteRadioTotalPriceTextView = findViewById(R.id.unite_radio_total_price_textView);
        mUniteRadioTotalPriceValueTextView = findViewById(R.id.unite_radio_total_price_value_textView);
        mUniteRadioTotalUniteTextView = findViewById(R.id.unite_radio_total_unite_textView);
        mUniteRadioTotalUniteValueTextView = findViewById(R.id.unite_radio_total_unite_value_textView);
        mUniteRadioTotalMinuteSelectedTextView = findViewById(R.id.unite_radio_total_minute_selected_textView);
        mUniteRadioTotalMinuteSelectedValueTextView = findViewById(R.id.unite_radio_total_minute_selected_value_textView);
        mUniteRadioFromSummaryScreenPreviousButton = findViewById(R.id.unite_radio_from_summary_previous_button);
        mUniteRadioFromSummaryScreenNextButton = findViewById(R.id.unite_radio_from_summary_next_button);
        mUniteRadioTimeSlotLinearLayout = findViewById(R.id.unite_radio_time_slot_linear_layout);
        mUniteRadioStartDateEditText = findViewById(R.id.unite_radio_startDateEditText);
        mUniteRadioStartTimeEditText = findViewById(R.id.unite_radio_startTimeEditText);
        mUniteRadioEndDateEditText = findViewById(R.id.unite_radio_endDateEditText);
        mUniteRadioEndTimeEditText = findViewById(R.id.unite_radio_endTimeEditText);
        mUniteRadioFromTimeSlotPreviousButton = findViewById(R.id.unite_radio_from_time_slot_previous_button);
        mUniteRadioFromTimeSlotProceedButton = findViewById(R.id.unite_radio_from_time_slot_proceed);
        mUniteRadioFromTimeSlotProceedButton.setOnClickListener(this);


        mUniteRadioSummaryLinearLayout.setVisibility(View.GONE);
        mUniteRadioTimeSlotLinearLayout.setVisibility(View.GONE);


        mUniteRadioInputUniteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String uniteValue = "0";
                if(mUniteRadioInputUniteEditText.getText() != null &&
                        !TextUtils.isEmpty(mUniteRadioInputUniteEditText.getText())) {
                    uniteValue = mUniteRadioInputUniteEditText.getText().toString();
                }
                Double uniteDoubleValue = new Double(uniteValue);
                String amountValue = String.format("%.2f", (uniteDoubleValue * mHostPrice));
                mUniteRadioAmountValueTextView.setText(amountValue);
            }
        });

        mUniteRadioTenUniteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUniteRadioInputUniteEditText.setText("10");
            }
        });

        mUniteRadioFifteenUniteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUniteRadioInputUniteEditText.setText("15");
            }
        });

        mUniteRadioTwentyUniteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUniteRadioInputUniteEditText.setText("20");
            }
        });

        mUniteRadioFromUniteScreenNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicleDetail = mAllAddedVehicleSpinner.getSelectedItem().toString();
                if (vehicleDetail.equalsIgnoreCase("Select Your Vehicle")) {
                    Toast.makeText(AddBookingActivity.this, "please select your vehicle", Toast.LENGTH_SHORT).show();
                    return;
                }

                String uniteValue = "0";
                if(mUniteRadioInputUniteEditText.getText() != null &&
                        !TextUtils.isEmpty(mUniteRadioInputUniteEditText.getText())) {
                    uniteValue = mUniteRadioInputUniteEditText.getText().toString();
                }else {
                    Toast.makeText(AddBookingActivity.this, "Please Select Unite", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double selectedUnite = new Double(uniteValue);
                Double maxUniteAllow = new Double(mMaxUniteValueTextView.getText().toString());
                if(selectedUnite > maxUniteAllow) {
                    Toast.makeText(AddBookingActivity.this,
                            "selected Unite Should be <= " + maxUniteAllow  , Toast.LENGTH_SHORT).show();
                    return;
                }

                // Time = Unite/ powerOutput * 60
                // Amount = unite * price
                int totalTime = (int) ((selectedUnite / mHostPowerOutput) * 60);
                mUniteRadioRequiredMinutesValueTextView.setText(totalTime + " Minutes");
                mUniteRadioTotalPriceValueTextView.setText(mUniteRadioAmountValueTextView.getText());
                mUniteRadioTotalUniteValueTextView.setText(mUniteRadioInputUniteEditText.getText());
                mUniteRadioTotalMinuteSelectedValueTextView.setText("0 Minutes");

                mUniteRadioUniteLinearLayout.setVisibility(View.GONE);
                mUniteRadioSummaryLinearLayout.setVisibility(View.VISIBLE);
                mUniteRadioTimeSlotLinearLayout.setVisibility(View.GONE);
            }
        });


        mUniteRadioFromSummaryScreenPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUniteRadioUniteLinearLayout.setVisibility(View.VISIBLE);
                mUniteRadioSummaryLinearLayout.setVisibility(View.GONE);
                mUniteRadioTimeSlotLinearLayout.setVisibility(View.GONE);
            }
        });


        mUniteRadioFromSummaryScreenNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUniteRadioUniteLinearLayout.setVisibility(View.GONE);
                mUniteRadioSummaryLinearLayout.setVisibility(View.GONE);
                mUniteRadioTimeSlotLinearLayout.setVisibility(View.VISIBLE);
            }
        });


        mUniteRadioFromTimeSlotPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUniteRadioUniteLinearLayout.setVisibility(View.GONE);
                mUniteRadioSummaryLinearLayout.setVisibility(View.VISIBLE);
                mUniteRadioTimeSlotLinearLayout.setVisibility(View.GONE);
            }
        });

        TimePickerUniversal timePickerUniversal3 = new TimePickerUniversal();
        TimePickerUniversal timePickerUniversal4 = new TimePickerUniversal();
        timePickerUniversal3.setTimePickerUniversal(mUniteRadioStartTimeEditText, false);
        timePickerUniversal4.setTimePickerUniversal(mUniteRadioEndTimeEditText, false);

        mUniteRadioStartDate_DatePickerUniversal = new DatePickerUniversal(getApplicationContext(),
                mUniteRadioStartDateEditText, AddBookingActivity.this);

        mUniteRadioEndDate_DatePickerUniversal = new DatePickerUniversal(getApplicationContext(),
                mUniteRadioEndDateEditText, AddBookingActivity.this);

        mUniteRadioStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> disableDaysArray = getDisableDays();
                mUniteRadioStartDate_DatePickerUniversal.setDateOnEditText(disableDaysArray);
            }
        });

        mUniteRadioEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> disableDaysArray = getDisableDays();
                mUniteRadioEndDate_DatePickerUniversal.setDateOnEditText(disableDaysArray);
            }
        });


        mAmountRadioUniteSlotButton = findViewById(R.id.amount_radio_select_amount_slot_button);
        mAmountRadioSummarySlotButton = findViewById(R.id.amount_radio_amount_summary_button);
        mAmountRadioTimeSlotButton = findViewById(R.id.amount_radio_time_slot_button);
        mAmountRadioUniteLinearLayout = findViewById(R.id.amount_radio_unite_linear_layout);
        mAmountRadioInputAmountEditText = findViewById(R.id.amount_radio_input_unite_edit_text);
        mAmountRadioUniteValueTextView = findViewById(R.id.amount_radio_unite_value_text_view);
        mAmountRadioTenUniteButton = findViewById(R.id.amount_radio_ten_unites_button);
        mAmountRadioFifteenUniteButton = findViewById(R.id.amount_radio_fifteen_unites_button);
        mAmountRadioTwentyUniteButton = findViewById(R.id.amount_radio_twenty_unites_button);
        mAmountRadioFromUniteScreenNextButton = findViewById(R.id.amount_radio_from_unite_screen_next_button);
        mAmountRadioSummaryLinearLayout = findViewById(R.id.amount_radio_summary_linear_layout);
        mAmountRadioRequiredMinutesTextView = findViewById(R.id.amount_radio_required_minutes_textView);
        mAmountRadioRequiredMinutesValueTextView = findViewById(R.id.amount_radio_required_minutes_value_textView);
        mAmountRadioTotalPriceTextView = findViewById(R.id.amount_radio_total_price_textView);
        mAmountRadioTotalPriceValueTextView = findViewById(R.id.amount_radio_total_price_value_textView);
        mAmountRadioTotalUniteTextView = findViewById(R.id.amount_radio_total_unite_textView);
        mAmountRadioTotalUniteValueTextView = findViewById(R.id.amount_radio_total_unite_value_textView);
        mAmountRadioTotalMinuteSelectedTextView = findViewById(R.id.amount_radio_total_minute_selected_textView);
        mAmountRadioTotalMinuteSelectedValueTextView = findViewById(R.id.amount_radio_total_minute_selected_value_textView);
        mAmountRadioFromSummaryScreenPreviousButton = findViewById(R.id.amount_radio_from_summary_previous_button);
        mAmountRadioFromSummaryScreenNextButton = findViewById(R.id.amount_radio_from_summary_next_button);
        mAmountRadioTimeSlotLinearLayout = findViewById(R.id.amount_radio_time_slot_linear_layout);
        mAmountRadioStartDateEditText = findViewById(R.id.amount_radio_startDateEditText);
        mAmountRadioStartTimeEditText = findViewById(R.id.amount_radio_startTimeEditText);
        mAmountRadioEndDateEditText = findViewById(R.id.amount_radio_endDateEditText);
        mAmountRadioEndTimeEditText = findViewById(R.id.amount_radio_endTimeEditText);
        mAmountRadioFromTimeSlotPreviousButton = findViewById(R.id.amount_radio_from_time_slot_previous_button);
        mAmountRadioFromTimeSlotProceedButton = findViewById(R.id.amount_radio_from_time_slot_proceed);
        mAmountRadioFromTimeSlotProceedButton.setOnClickListener(this);

        mAmountRadioSummaryLinearLayout.setVisibility(View.GONE);
        mAmountRadioTimeSlotLinearLayout.setVisibility(View.GONE);


        mAmountRadioInputAmountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String amountValue = "0";
                if(mAmountRadioInputAmountEditText.getText() != null &&
                        !TextUtils.isEmpty(mAmountRadioInputAmountEditText.getText())) {
                    amountValue = mAmountRadioInputAmountEditText.getText().toString();
                }
                //Unite = selected Amount/Host price
                Double amountDoubleValue = new Double(amountValue);
                String uniteValue = String.format("%.2f", (amountDoubleValue / mHostPrice));
                mAmountRadioUniteValueTextView.setText(uniteValue);
            }
        });

        mAmountRadioTenUniteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmountRadioInputAmountEditText.setText("100");
            }
        });

        mAmountRadioFifteenUniteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmountRadioInputAmountEditText.setText("150");
            }
        });

        mAmountRadioTwentyUniteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmountRadioInputAmountEditText.setText("200");
            }
        });

        mAmountRadioFromUniteScreenNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String vehicleDetail = mAllAddedVehicleSpinner.getSelectedItem().toString();
                if (vehicleDetail.equalsIgnoreCase("Select Your Vehicle")) {
                    Toast.makeText(AddBookingActivity.this, "please select your vehicle", Toast.LENGTH_SHORT).show();
                    return;
                }

                String amountValue = "0";
                String uniteValue = "0";
                if(mAmountRadioInputAmountEditText.getText() != null &&
                        !TextUtils.isEmpty(mAmountRadioInputAmountEditText.getText())) {
                    amountValue = mAmountRadioInputAmountEditText.getText().toString();
                    uniteValue = mAmountRadioUniteValueTextView.getText().toString();
                }else {
                    Toast.makeText(AddBookingActivity.this, "Please Select Amount", Toast.LENGTH_SHORT).show();
                    return;
                }

                Double selectedAmount = new Double(amountValue);
                Double maxAmountAllow = new Double(mMaxAmountValueTextView.getText().toString());
                if(selectedAmount > maxAmountAllow) {
                    Toast.makeText(AddBookingActivity.this,
                            "selected Amount Should be <= " + maxAmountAllow  , Toast.LENGTH_SHORT).show();
                    return;
                }

                // Unite = selected Amount/Host price
                // Time = unite/powerOutput * 60
                Double totalUnite = new Double(uniteValue);
                int totalTime = (int) ((totalUnite / mHostPowerOutput) * 60);
                mAmountRadioRequiredMinutesValueTextView.setText(totalTime + " Minutes");
                mAmountRadioTotalPriceValueTextView.setText(mAmountRadioInputAmountEditText.getText());
                mAmountRadioTotalUniteValueTextView.setText(mAmountRadioUniteValueTextView.getText());
                mAmountRadioTotalMinuteSelectedValueTextView.setText("0 Minutes");

                mAmountRadioUniteLinearLayout.setVisibility(View.GONE);
                mAmountRadioSummaryLinearLayout.setVisibility(View.VISIBLE);
                mAmountRadioTimeSlotLinearLayout.setVisibility(View.GONE);
            }
        });


        mAmountRadioFromSummaryScreenPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmountRadioUniteLinearLayout.setVisibility(View.VISIBLE);
                mAmountRadioSummaryLinearLayout.setVisibility(View.GONE);
                mAmountRadioTimeSlotLinearLayout.setVisibility(View.GONE);
            }
        });


        mAmountRadioFromSummaryScreenNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmountRadioUniteLinearLayout.setVisibility(View.GONE);
                mAmountRadioSummaryLinearLayout.setVisibility(View.GONE);
                mAmountRadioTimeSlotLinearLayout.setVisibility(View.VISIBLE);
            }
        });


        mAmountRadioFromTimeSlotPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAmountRadioUniteLinearLayout.setVisibility(View.GONE);
                mAmountRadioSummaryLinearLayout.setVisibility(View.VISIBLE);
                mAmountRadioTimeSlotLinearLayout.setVisibility(View.GONE);
            }
        });

        TimePickerUniversal timePickerUniversal5 = new TimePickerUniversal();
        TimePickerUniversal timePickerUniversal6 = new TimePickerUniversal();
        timePickerUniversal5.setTimePickerUniversal(mAmountRadioStartTimeEditText, false);
        timePickerUniversal6.setTimePickerUniversal(mAmountRadioEndTimeEditText, false);

        mAmountRadioStartDate_DatePickerUniversal = new DatePickerUniversal(getApplicationContext(),
                mAmountRadioStartDateEditText, AddBookingActivity.this);

        mAmountRadioEndDate_DatePickerUniversal = new DatePickerUniversal(getApplicationContext(),
                mAmountRadioEndDateEditText, AddBookingActivity.this);

        mAmountRadioStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> disableDaysArray = getDisableDays();
                mAmountRadioStartDate_DatePickerUniversal.setDateOnEditText(disableDaysArray);
            }
        });

        mAmountRadioEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> disableDaysArray = getDisableDays();
                mAmountRadioEndDate_DatePickerUniversal.setDateOnEditText(disableDaysArray);
            }
        });


        mHostPriceTextView.setText("Price : R\\kwh."+ mHostPrice);

        mTimeRadioStartDate_DatePickerUniversal = new DatePickerUniversal(getApplicationContext(),
                mTimeRadioStartDateEditText, AddBookingActivity.this);
        mTimeRadioEndDate_DatePickerUniversal = new DatePickerUniversal(getApplicationContext(),
                mTimeRadioEndDateEditText, AddBookingActivity.this);

        mTimeRadioSummaryLinearLayout.setVisibility(View.GONE);
        mTimeRadioNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String vehicleDetail = mAllAddedVehicleSpinner.getSelectedItem().toString();
                if (vehicleDetail.equalsIgnoreCase("Select Your Vehicle")) {
                    Toast.makeText(AddBookingActivity.this, "please select your vehicle", Toast.LENGTH_SHORT).show();
                    return;
                }

                String startTime = "";
                String endTime = "";
                if(mTimeRadioStartTimeEditText.getText() != null) {
                    startTime = mTimeRadioStartTimeEditText.getText().toString();
                }
                if(mTimeRadioEndTimeEditText.getText() != null) {
                    endTime = mTimeRadioEndTimeEditText.getText().toString();
                }

                calculateTime(mTimeRadioStartDate_DatePickerUniversal.getSelectedDate(),
                        mTimeRadioEndDate_DatePickerUniversal.getSelectedDate(),
                        startTime, endTime);
            }
        });

        mTimeRadioPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTimeRadioTimeSlotTabLinearLayout.setVisibility(View.VISIBLE);
                mTimeRadioSummaryLinearLayout.setVisibility(View.GONE);
            }
        });

        TimePickerUniversal timePickerUniversal1 = new TimePickerUniversal();
        TimePickerUniversal timePickerUniversal2 = new TimePickerUniversal();
        timePickerUniversal1.setTimePickerUniversal(mTimeRadioStartTimeEditText, false);
        timePickerUniversal2.setTimePickerUniversal(mTimeRadioEndTimeEditText, false);


        mTimeRadioStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> disableDaysArray = getDisableDays();
                mTimeRadioStartDate_DatePickerUniversal.setDateOnEditText(disableDaysArray);
            }
        });

        mTimeRadioEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> disableDaysArray = getDisableDays();
                mTimeRadioEndDate_DatePickerUniversal.setDateOnEditText(disableDaysArray);
            }
        });


        mHostAvailabilityButton.setOnCheckedChangeListener(new AddBookingActivity.mCheckBoxChangeListener());

        initSpinnerApi();
        mRadioGroup.clearCheck();
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                handleRadioButtonClick(group, checkedId);
            }
        });
        mRadioGroup.check(R.id.timeRadioButton);


        //cs_connector
        mAllAddedVehicleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String vehicleDetail = mAllAddedVehicleSpinner.getSelectedItem().toString();
                if (vehicleDetail.equalsIgnoreCase("Select Your Vehicle")) {
                    if (((TextView) parent.getChildAt(0)) != null) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                    }
                    calculateMaxTimeUniteAmount(0);
                } else {
                    if (((TextView) parent.getChildAt(0)) != null) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                    }
                    float batterySize = mAllAddedVehicleList.get(position -1).getBatterySize();
                    calculateMaxTimeUniteAmount(batterySize);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void calculateMaxTimeUniteAmount(float batterySize) {
        mBatterySize = new Double(batterySize);
        Double maxUniteAllow = mBatterySize + 0.2 * mBatterySize;
        Double maxTimeAllow = (maxUniteAllow / mHostPowerOutput) * 60;
        Double maxAmountAllow = maxUniteAllow * mHostPrice;

        mMaxTimeValueTextView.setText(String.format("%.0f", maxTimeAllow));
        mMaxUniteValueTextView.setText(String.format("%.2f", maxUniteAllow));
        mMaxAmountValueTextView.setText(String.format("%.2f", maxAmountAllow));

      /*  app.maximum_units = (app.batt_size + 0.2 * app.batt_size).toFixed(2) //20% elshare charges
        app.maximum_time = parseFloat(app.maximum_units)/parseFloat(app.power_output)*60
        app.maximum_time = app.maximum_time.toFixed(0)
        app.maximum_amount = (app.maximum_units * app.price).toFixed(2)*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.time_radio_proceedButton:
                proceedWithTime();
                break;
            case R.id.unite_radio_from_time_slot_proceed:
                proceedWithUnit();
                break;

            case  R.id.amount_radio_from_time_slot_proceed:
                proceedWithAmount();
                break;

        }
    }

    private void proceedWithAmount()
    {
        String dateString=mAmountRadioStartDateEditText.getText().toString();
        String timeString=mAmountRadioEndDateEditText.getText().toString();

        String dateStartArraySplit[] =dateString.split(",");
        String dateEndArraySplit[] =timeString.split(",");


        bodyUserId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(mUserId));
        bodyHostId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(mHostId));
        bodyTime = RequestBody.create(MediaType.parse("multipart/form-data"), mAmountRadioTotalMinuteSelectedValueTextView.getText().toString());
        bodyAmount = RequestBody.create(MediaType.parse("multipart/form-data"), mAmountRadioTotalPriceValueTextView.getText().toString());
        bodyUnit = RequestBody.create(MediaType.parse("multipart/form-data"), mAmountRadioTotalUniteValueTextView.getText().toString());
        bodyStartDate = RequestBody.create(MediaType.parse("multipart/form-data"), dateStartArraySplit[1]);
        bodyEndDate = RequestBody.create(MediaType.parse("multipart/form-data"),dateEndArraySplit[1]);
        bodyStartTime = RequestBody.create(MediaType.parse("multipart/form-data"), mAmountRadioStartTimeEditText.getText().toString());
        bodyEndTime = RequestBody.create(MediaType.parse("multipart/form-data"), mAmountRadioEndTimeEditText.getText().toString());
        bodyTable = RequestBody.create(MediaType.parse("multipart/form-data"), mTableName);
        //proceedToPayment();
        checkHostAvailability(dateStartArraySplit[1], dateEndArraySplit[1],
                mAmountRadioStartTimeEditText.getText().toString(), mAmountRadioEndTimeEditText.getText().toString(),
                String.valueOf(mHostId), mTableName);
    }

    private void proceedWithUnit()
    {
        String dateString=mUniteRadioStartDateEditText.getText().toString();
        String timeString=mUniteRadioEndDateEditText.getText().toString();

        String dateStartArraySplit[] =dateString.split(",");
        String dateEndArraySplit[] =timeString.split(",");


        bodyUserId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(mUserId));
        bodyHostId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(mHostId));
        bodyTime = RequestBody.create(MediaType.parse("multipart/form-data"), mUniteRadioTotalMinuteSelectedValueTextView.getText().toString());
        bodyAmount = RequestBody.create(MediaType.parse("multipart/form-data"), mUniteRadioTotalPriceValueTextView.getText().toString());
        bodyUnit = RequestBody.create(MediaType.parse("multipart/form-data"), mUniteRadioTotalUniteValueTextView.getText().toString());
        bodyStartDate = RequestBody.create(MediaType.parse("multipart/form-data"), dateStartArraySplit[1]);
        bodyEndDate = RequestBody.create(MediaType.parse("multipart/form-data"),dateEndArraySplit[1]);
        bodyStartTime = RequestBody.create(MediaType.parse("multipart/form-data"), mUniteRadioStartTimeEditText.getText().toString());
        bodyEndTime = RequestBody.create(MediaType.parse("multipart/form-data"), mUniteRadioEndTimeEditText.getText().toString());
        bodyTable = RequestBody.create(MediaType.parse("multipart/form-data"), mTableName);
        //proceedToPayment();
        checkHostAvailability(dateStartArraySplit[1], dateEndArraySplit[1],
                mUniteRadioStartTimeEditText.getText().toString(), mUniteRadioEndTimeEditText.getText().toString(),
                String.valueOf(mHostId), mTableName);
    }

    private void proceedWithTime()
    {
        String dateString=mTimeRadioStartDateEditText.getText().toString();
        String timeString=mTimeRadioEndDateEditText.getText().toString();

        String dateStartArraySplit[] =dateString.split(",");
        String dateEndArraySplit[] =timeString.split(",");


        bodyUserId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(mUserId));
        bodyHostId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(mHostId));
        bodyTime = RequestBody.create(MediaType.parse("multipart/form-data"), mMaxTimeValueTextView.getText().toString());
        bodyAmount = RequestBody.create(MediaType.parse("multipart/form-data"), mMaxAmountValueTextView.getText().toString());
        bodyUnit = RequestBody.create(MediaType.parse("multipart/form-data"), mMaxUniteValueTextView.getText().toString());
        bodyStartDate = RequestBody.create(MediaType.parse("multipart/form-data"), dateStartArraySplit[1]);
        bodyEndDate = RequestBody.create(MediaType.parse("multipart/form-data"),dateEndArraySplit[1]);
        bodyStartTime = RequestBody.create(MediaType.parse("multipart/form-data"), mTimeRadioStartTimeEditText.getText().toString());
        bodyEndTime = RequestBody.create(MediaType.parse("multipart/form-data"), mTimeRadioEndTimeEditText.getText().toString());
        bodyTable = RequestBody.create(MediaType.parse("multipart/form-data"), mTableName);
       // proceedToPayment();

        checkHostAvailability(dateStartArraySplit[1], dateEndArraySplit[1],
                mTimeRadioStartTimeEditText.getText().toString(), mTimeRadioEndTimeEditText.getText().toString(),
                String.valueOf(mHostId), mTableName);
    }


    private void checkHostAvailability(String startDay, String endDay,
                                       String startTime, String endTime,
                                       String hostId, String tableName) {

        APIInterface service = SingletonRetrofit.getAPIInterface();
        Call<Integer> call = service.checkHostAvailability(startDay, startTime, endDay, endTime, hostId, tableName);
        Log.i("Url=", String.valueOf(call.request().url()));
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() != null) {
                    Integer responseValue = response.body();
                    if(responseValue == 1){
                        proceedToPayment();
                    }else {
                        Toast.makeText(AddBookingActivity.this,"Booking is unavailable for given time please try with other time", Toast.LENGTH_LONG).show();
                    }
                }
                Toast.makeText(AddBookingActivity.this, "value = "+ response.body(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
                Toast.makeText(AddBookingActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }



    private void proceedToPayment()
    {
        APIInterface service = SingletonRetrofit.getAPIInterface();
        Call<AddBooking> call = service.addBooking(bodyAmount,bodyStartDate,bodyEndDate,bodyStartTime,bodyEndTime,bodyUserId,bodyHostId,bodyTable,bodyTime,bodyUnit);
        Log.i("Url=", String.valueOf(call.request().url()));
        call.enqueue(new Callback<AddBooking>() {
            @Override
            public void onResponse(Call<AddBooking> call, Response<AddBooking> response) {
                if (response.body() != null) {
                    user_data= response.body();
                    if (user_data!=null) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddBookingActivity.this);
                        alertDialogBuilder.setMessage("Add Booking  successfully!!!");
                        alertDialogBuilder.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Log.i("amount",String.valueOf(user_data.getAmount()));
                                        startPayment();
                                    }
                                });


                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    } else {
                        Toast.makeText(AddBookingActivity.this,"Booking fail!!", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(AddBookingActivity.this, "Booking fail...", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AddBooking> call, Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
                Toast.makeText(AddBookingActivity.this, String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startPayment()
    {
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", user_data.getName());
            options.put("description", user_data.getDescription());
            //You can omit the image option to fetch the image from dashboard
            options.put("order_id", user_data.getOrderId());
            options.put("image", "http://shravanbaaldemo.bookingadbazaar.com/img/logo.jpg");
            options.put("currency", user_data.getCurrency());
            options.put("receipt",user_data.getReceipt());
            options.put("amount", user_data.getAmount());
            options.put("payment_capture", true);
            options.put("notes","My Payment");
            JSONObject preFill = new JSONObject();
            preFill.put("email", user_data.getEmail());
            preFill.put("contact", user_data.getContactNumber());
            options.put("prefill", preFill);
            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        Log.i ("Payment Successful: ",s);
        Log.i ("sign: ", String.valueOf(paymentData.getSignature()));
        Log.i ("order: ",paymentData.getOrderId());
        Log.i ("pay id: ", String.valueOf(paymentData.getPaymentId()));
        Log.i("Booking_id",String.valueOf(user_data.getBookingId()));
        Log.i("ID:", String.valueOf(mUserId));

        bodySignature = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(paymentData.getSignature()));
        bodyPaymentId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(paymentData.getPaymentId()));
        bodyOrderId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(paymentData.getOrderId()));
        bodyBookingId = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(user_data.getBookingId()));
        String UserIdString=String.valueOf(mUserId);
        APIInterface service = SingletonRetrofit.getAPIInterface();
        Call<BookingConfirmation> call = service.bookingCheckout(UserIdString,bodyBookingId,bodySignature,bodyPaymentId,bodyOrderId);
        Log.i("Url=", String.valueOf(call.request().url()));
        call.enqueue(new Callback<BookingConfirmation>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<BookingConfirmation> call, Response<BookingConfirmation> response) {
                if (response.body() != null) {
                    BookingConfirmation bookingCallbackData = response.body();
                    Toast.makeText(getApplicationContext(), bookingCallbackData.getMessage(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddBookingActivity.this, map_frag.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Something went wrong, please try again later!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BookingConfirmation> call, Throwable t) {

            }
        });
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        try {
            Toast.makeText(this, "Payment failed: " + i + " " + s, Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }


    public class mCheckBoxChangeListener implements CheckBox.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                mHostAvailabilityTableRow.setVisibility(View.VISIBLE);
            }else {
                mHostAvailabilityTableRow.setVisibility(View.GONE);
            }
        }
    }

    private void handleRadioButtonClick(RadioGroup group, int checkedId) {
        RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
        if (radioButton != null) {
            String radioButtonValue = radioButton.getText().toString();
            Toast.makeText(AddBookingActivity.this, radioButtonValue, Toast.LENGTH_SHORT).show();
            if(!TextUtils.isEmpty(radioButtonValue) && radioButtonValue.equalsIgnoreCase("time")){
                timeLinearLayout.setVisibility(View.VISIBLE);
                uniteLinearLayout.setVisibility(View.GONE);
                amountLinearLayout.setVisibility(View.GONE);
            }else if(!TextUtils.isEmpty(radioButtonValue) && radioButtonValue.equalsIgnoreCase("unite")){
                timeLinearLayout.setVisibility(View.GONE);
                uniteLinearLayout.setVisibility(View.VISIBLE);
                amountLinearLayout.setVisibility(View.GONE);
            }else  if(!TextUtils.isEmpty(radioButtonValue) && radioButtonValue.equalsIgnoreCase("amount")){
                timeLinearLayout.setVisibility(View.GONE);
                uniteLinearLayout.setVisibility(View.GONE);
                amountLinearLayout.setVisibility(View.VISIBLE);
            }else{

            }
        }
    }

    public void calculateTime(String startDate, String endDate, String startTime, String endTime) {
        if(TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)
                || TextUtils.isEmpty(startTime) || TextUtils.isEmpty(endTime)) {
            Toast.makeText(AddBookingActivity.this, "please select correct date and time", Toast.LENGTH_SHORT).show();
            return;
        }
        APIInterface service = SingletonRetrofit.getAPIInterface();
        Call<Integer> call = service.getCalculateTime(startDate, startTime ,endDate, endTime);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() != null) {
                    mTimeRadioSelectedTimeInMinutes = response.body().toString();

                    Double selectedMinutes = new Double(mTimeRadioSelectedTimeInMinutes);
                    Double maxTimeAllow = new Double(mMaxTimeValueTextView.getText().toString());
                    if(selectedMinutes > maxTimeAllow) {
                        Toast.makeText(AddBookingActivity.this,
                                "selected Time Should be <= " + maxTimeAllow  , Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(AddBookingActivity.this, mTimeRadioSelectedTimeInMinutes , Toast.LENGTH_SHORT).show();
                    mTimeRadioTimeSlotTabLinearLayout.setVisibility(View.GONE);
                    mTimeRadioSummaryLinearLayout.setVisibility(View.VISIBLE);

                    // unite = poweroutput/60 * selected time minutes
                    // Amount = unite * price
                    Double uniteValue = (mHostPowerOutput/60) * selectedMinutes;
                    Double amountValue = uniteValue * mHostPrice;
                    mTimeRadioTotalMinuteSelectedValueTextView.setText(""+selectedMinutes);
                    mTimeRadioTotalUniteValueTextView.setText(""+uniteValue);
                    mTimeRadioTotalPriceValueTextView.setText(""+amountValue);
                } else {
                    Toast.makeText(AddBookingActivity.this, "please select correct date and time", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(AddBookingActivity.this, "please select correct date and time", Toast.LENGTH_SHORT).show();
                Log.i("login error: ", String.valueOf(t));
            }
        });
    }


    public void initSpinnerApi() {
        final ArrayList<String> allAddedVehicleList = new ArrayList<>();
        allAddedVehicleList.add("Select Your Vehicle");
        APIInterface service = SingletonRetrofit.getAPIInterface();

        Call<List<AllVehicle>> call = service.initAllVehicleOfParticularDriver(8);
        call.enqueue(new Callback<List<AllVehicle>>() {
            @Override
            public void onResponse(Call<List<AllVehicle>> call, Response<List<AllVehicle>> response) {
                if (response.body() != null) {
                    mAllAddedVehicleList = response.body();
                    for(AllVehicle vehicle : response.body()){
                        StringBuilder stringBuilder = new StringBuilder("");
                        stringBuilder.append(vehicle.getModelYear());
                        stringBuilder.append("-"+vehicle.getMake());
                        stringBuilder.append("-"+vehicle.getBatterySize());

                        allAddedVehicleList.add(stringBuilder.toString());
                    }

                    ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<>(AddBookingActivity.this, R.layout.sample_text, allAddedVehicleList);
                    mAllAddedVehicleSpinner.setAdapter(vehicleAdapter);
                    mAllAddedVehicleSpinner.setSelection(0);
                } else {
                    Log.i("vehicle not added : ",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<AllVehicle>> call, Throwable t) {
                Log.i("login error: ", String.valueOf(t));
            }
        });
    }

    public ArrayList<Integer> getDisableDays(){
        ArrayList<Integer> disableDaysArray = new ArrayList<>();
        if(!mAvailabilityDaysArray.contains("Sunday")){
            disableDaysArray.add(Calendar.SUNDAY);
        }
        if(!mAvailabilityDaysArray.contains("Monday")){
            disableDaysArray.add(Calendar.MONDAY);
        }
        if(!mAvailabilityDaysArray.contains("Tuesday")){
            disableDaysArray.add(Calendar.TUESDAY);
        }
        if(!mAvailabilityDaysArray.contains("Wednesday")){
            disableDaysArray.add(Calendar.WEDNESDAY);
        }
        if(!mAvailabilityDaysArray.contains("Thursday")){
            disableDaysArray.add(Calendar.THURSDAY);
        }
        if(!mAvailabilityDaysArray.contains("Friday")){
            disableDaysArray.add(Calendar.FRIDAY);
        }
        if(!mAvailabilityDaysArray.contains("Saturday")){
            disableDaysArray.add(Calendar.SATURDAY);
        }

        return disableDaysArray;
    }
}