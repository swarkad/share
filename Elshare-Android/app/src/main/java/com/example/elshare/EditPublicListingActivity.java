package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.example.elshare.adapter.GooglePlacesAutocompleteAdapter;
import com.example.elshare.utils.SingletonRetrofit;
import com.example.elshare.utils.TimePickerUniversal;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AddressComponent;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import commertial_pojo.commertial_charger_amphere;
import commertial_pojo.commertial_socket_amphere;
import datamodel.APIInterface;
import datamodel.Charegr_brand;
import datamodel.Connector_type;
import datamodel.Socket;
import datamodel.Socket_commertial;
import datamodel.Socket_public;
import datamodel.charger_model;
import datamodel.charger_type;
import datamodel.commertial_board;
import datamodel.commertial_board_socket;
import datamodel.commertial_charger_model;
import datamodel.commertial_charger_type;
import datamodel.commertial_connector;
import datamodel.commertial_power_output;
import datamodel.commertial_rate;
import datamodel.commertial_rate_socket;
import datamodel.commertial_voltage;
import datamodel.commertial_voltage_socket;
import datamodel.public_board;
import datamodel.public_board_socket;
import datamodel.public_charegr_brand;
import datamodel.public_charger_model;
import datamodel.public_connector;
import datamodel.public_power_output;
import datamodel.public_rate;
import datamodel.public_rate_socket;
import datamodel.public_voltage;
import datamodel.public_voltage_socket;
import datamodel.residential_board;
import datamodel.residential_board_socket;
import datamodel.residential_power_output;
import datamodel.residential_rate_socket;
import datamodel.residential_show_list.ChargerType;
import datamodel.residential_voltage_socket;
import datamodel.residetial_rate;
import datamodel.residrntial_voltage;
import datamodel.residential_show_list.show_residential;
import datamodel.show_commertial;
import datamodel.show_public;
import okhttp3.ResponseBody;
import public_pojo.public_charger_amphere;
import public_pojo.public_socket_amphere;
import residential_pojo.residential_charger_amphere;
import residential_pojo.residential_socket_amphere;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.elshare.utils.ElshareConstants.GOOGLE_MAP_API_KEY;

public class EditPublicListingActivity extends Activity implements AdapterView.OnItemClickListener {
    private static final int RESULT_OK = -1;
    private static final int RESULT_CANCELED = -1;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    final ArrayList<String> spinner_charger_type = new ArrayList<>();
    final ArrayList<String> spinner_brand = new ArrayList<>();
    final ArrayList<String> spinner_model = new ArrayList<>();
    final ArrayList<String> spinner_electricity_board = new ArrayList<>();
    final ArrayList<String> spinner_rate = new ArrayList<>();
    final ArrayList<String> spinner_connector = new ArrayList<>();
    final ArrayList<String> spinner_power_output = new ArrayList<>();
    final ArrayList<String> spinner_voltage = new ArrayList<>();
    final ArrayList<String> spinner_socket = new ArrayList<>();
    final ArrayList<String> socket_rate_array = new ArrayList<>();
    final ArrayList<String> socket_eboard = new ArrayList<>();
    final ArrayList<String> socket_voltage = new ArrayList<>();
    final ArrayList<String> charger_Amphere = new ArrayList<>();
    final ArrayList<String> socket_amphere = new ArrayList<>();
    String chargerTypeSpinnerValue;
    String chargerBrandSpinnerValue;
    String chargerModelSpinnerValue;
    String connectorTypeSpinnerValue;
    String electricityBoardSpinnerValue;
    String rateStructureSpinnerValue;
    String voltageSpinnerValue;
    String amphereSpinnerValue;
    String finalPowerOutputValue;
    String socketSpinnerValue;
    String socketElectricityBoardSpinnerValue;
    String socketRateSpinnerValue;
    String socketVoltageSpinnerValue;
    String socketAmphereSpinnerValue;
    String newString;
    String charger_brand_str;
    Spinner chargerTypeSpinner;
    Spinner chargerBrandSpinner;
    Spinner chargerModelSpinner;
    Spinner connectorTypeSpinner;
    Spinner electricityBoardSpinner;
    Spinner rateStructureSpinner;
    Spinner powerOutputSpinner;
    Spinner voltageSpinner;
    Spinner chargerAmphereSpinner;
    Spinner socketSpinner;
    Spinner socketElectricityBoardSpinner;
    Spinner socketRateSpinner;
    Spinner socketVoltageSpinner;
    Spinner socketAmphereSpinner;
    TextView mFinalPowerOutputTextView;
    EditText socketPowerOutputEditText;
    EditText address_2;
    EditText country_txt;
    EditText state_text;
    EditText landmark_txt;
    EditText zip_txt;
    EditText city_txt;
    com.google.android.material.textfield.TextInputEditText address_1;
    EditText start_time_txt;
    EditText end_time_txt;
    int id;
    ImageButton delete_row;
    int checked;
    Button multiDayAddTimeBlock;
    Button singleMondayAddTimeBlock;
    Button singleTuesdayAddTimeBlock;
    Button singleWednesdayAddTimeBlock;
    Button singleThursdayAddTimeBlock;
    Button singleFridayAddTimeBlock;
    Button singleSaturdayAddTimeBlock;
    Button singleSundayAddTimeBlock;
    Button save_changes;
    CheckBox multiDay24HoursCheckBox;
    CheckBox singleMonday24HoursCheckBox;
    CheckBox singleTuesday24HoursCheckBox;
    CheckBox singleWednesday24HoursCheckBox;
    CheckBox singleThursday24HoursCheckBox;
    CheckBox singleFriday24HoursCheckBox;
    CheckBox singleSaturday24HoursCheckBox;
    CheckBox singleSunday24HoursCheckBox;
    TableLayout tableLayout;
    TableLayout mondayTableLayout;
    TableLayout tuesdayTableLayout;
    TableLayout wednesdayTableLayout;
    TableLayout thursdayTableLayout;
    TableLayout fridayTableLayout;
    TableLayout saturdayTableLayout;
    TableLayout sundayTableLayout;
    LinearLayout singleMondayLinearLayout;
    LinearLayout singleTuesdayLinearLayout;
    LinearLayout singleWednesdayLinearLayout;
    LinearLayout singleThursdayLinearLayout;
    LinearLayout singleFridayLinearLayout;
    LinearLayout singleSaturdayLinearLayout;
    LinearLayout singleSundayLinearLayout;

    String charger_type_str;
    String brand_str;
    String cs_model_str;
    String cs_connector_str;
    String socket_str;
    String socket_rate;
    String power_str;
    String vtg_str;
    String socket_board_str;
    String board_str;
    String rate_str;
    String socket_amp_str;
    String charger_amp_str;
    String socket_vtg_str;
    Switch instant_booking;
    Switch multi_single_day;
    LinearLayout multiDayTableLayout;
    LinearLayout singleDayTableLayout;
    float longitude, latitude;
    TextView edit_header;
    Double final_profit_double;
    String final_profit;

    TextView profitTextView;
    TextView electricityBoardRateTextView;
    int selected, check, user_id, list_id;
    private GooglePlacesAutocompleteAdapter mGooglePlacesAutocompleteAdapter;
    private AutoCompleteTextView mAddressAutoCompleteTextView;

    private boolean isDefaultTime;
    private show_public show_resi_arry;

    private List<public_charegr_brand> charger_brand;
    private List<public_charger_model> charger_model;
    private List<public_board> board_m;
    private List<public_rate> rate_m;
    private List<public_connector> connetor_set;
    private List<Socket_public> socket_set;
    private List<public_power_output> power_com;
    private List<public_voltage> volt_com;
    private List<public_rate_socket> socket_rate_res;
    private List<public_board_socket> socket_board_res;
    private List<public_voltage_socket> socket_vtg_res;
    private List<public_charger_amphere> charger_amp_array;
    private List<public_socket_amphere> socket_amp_array;

    private public_charegr_brand my_brand;
    private public_charger_model my_model;
    private public_board my_board;
    private public_rate my_rate;
    private public_connector my_connector;
    private Socket_public my_socket;
    private public_power_output power_c;
    private public_voltage volt_c;
    private public_rate_socket rate_socket;
    private public_board_socket socket_board;
    private public_voltage_socket socket_vtg;
    private public_socket_amphere socket_amp;
    private public_charger_amphere charger_amp;
    private ArrayList<String> multidaysSelectedList = new ArrayList<>();
    private ArrayList<String> multiDayStartTimeList = new ArrayList<>();
    private ArrayList<String> multiDayEndTimeList = new ArrayList<>();
    private ArrayList<String> singleDaySelectedList = new ArrayList<>();
    private ArrayList<String> mondayStartTimeList = new ArrayList<>();
    private ArrayList<String> mondayEndTimeList = new ArrayList<>();
    private ArrayList<String> tuesdayStartTimeList = new ArrayList<>();
    private ArrayList<String> tuesdayEndTimeList = new ArrayList<>();
    private ArrayList<String> wednesdayStartTimeList = new ArrayList<>();
    private ArrayList<String> wednesdayEndTimeList = new ArrayList<>();
    private ArrayList<String> thursdayStartTimeList = new ArrayList<>();
    private ArrayList<String> thursdayEndTimeList = new ArrayList<>();
    private ArrayList<String> fridayStartTimeList = new ArrayList<>();
    private ArrayList<String> fridayEndTimeList = new ArrayList<>();
    private ArrayList<String> saturdayStartTimeList = new ArrayList<>();
    private ArrayList<String> saturdayEndTimeList = new ArrayList<>();
    private ArrayList<String> sundayStartTimeList = new ArrayList<>();
    private ArrayList<String> sundayEndTimeList = new ArrayList<>();

    private CheckBox mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox, thursdayCheckBox, fridayCheckBox, saturdayCheckBox, sundayCheckBox;
    private ArrayList<CheckBox> multiDaysCheckBoxArrayList = new ArrayList<>();

    private CheckBox singleMondayCheckBox;
    private CheckBox singleTuesdayCheckBox;
    private CheckBox singleWednesdayCheckBox;
    private CheckBox singleThursdayCheckBox;
    private CheckBox singleFridayCheckBox;
    private CheckBox singleSaturdayCheckBox;
    private CheckBox singleSundayCheckBox;
    private ArrayList<CheckBox> singleDayCheckBoxArrayList = new ArrayList<>();
    private ArrayList<ArrayList<String>> singleDayStartTimeArrayList = new ArrayList<>();
    private ArrayList<LinearLayout> singleDayLinearLayoutList = new ArrayList<>();
    private ArrayList<String> defaultSelectedDaysList = new ArrayList<>();
    private ArrayList<String> allAvailableDaysList = new ArrayList<>();

    public static String trimDoubbleQuote(String value) {
        return value.replaceAll("^\"|\"$", "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_public_listing2);
        edit_header = findViewById(R.id.edit_header);
        edit_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        multiDayAddTimeBlock = findViewById(R.id.add_another_Block_resi);
        singleMondayAddTimeBlock = findViewById(R.id.singleMondayTimeBlock);
        singleTuesdayAddTimeBlock = findViewById(R.id.singleTuesdayTimeBlock);
        singleWednesdayAddTimeBlock = findViewById(R.id.singleWednesdayTimeBlock);
        singleThursdayAddTimeBlock = findViewById(R.id.singleThursdayTimeBlock);
        singleFridayAddTimeBlock = findViewById(R.id.singleFridayTimeBlock);
        singleSaturdayAddTimeBlock = findViewById(R.id.singleSaturdayTimeBlock);
        singleSundayAddTimeBlock = findViewById(R.id.singleSundayTimeBlock);

        final BubbleSeekBar bubbleSeekBar = findViewById(R.id.bubbleSeekBar);
        NestedScrollView nestedScrollView = findViewById(R.id.nestedScrollView);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                bubbleSeekBar.correctOffsetWhenContainerOnScrolling();
            }
        });

        electricityBoardRateTextView = findViewById(R.id.electricity_board_rate);
        profitTextView = findViewById(R.id.profit_text_view);
        save_changes = findViewById(R.id.edit_save_resi);
        multiDay24HoursCheckBox = findViewById(R.id.multiDay24HoursCheckBox);

        singleMonday24HoursCheckBox = findViewById(R.id.singleMonday24HoursCheckBox);
        singleTuesday24HoursCheckBox = findViewById(R.id.singleTuesday24HoursCheckBox);
        singleWednesday24HoursCheckBox = findViewById(R.id.singleWednesday24HoursCheckBox);
        singleThursday24HoursCheckBox = findViewById(R.id.singleThursday24HoursCheckBox);
        singleFriday24HoursCheckBox = findViewById(R.id.singleFriday24HoursCheckBox);
        singleSaturday24HoursCheckBox = findViewById(R.id.singleSaturday24HoursCheckBox);
        singleSunday24HoursCheckBox = findViewById(R.id.singleSunday24HoursCheckBox);

        charger_type_str = "";
        brand_str = "";
        cs_model_str = "";
        cs_connector_str = "";
        socket_str = "";
        socket_rate = "";
        power_str = "";
        vtg_str = "";
        socket_board_str = "";
        board_str = "";
        rate_str = "";

        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat, boolean fromUser) {
                String numberFormat = String.format(Locale.ENGLISH, " %.1f", progressFloat);
                Double bubble_str = Double.valueOf(numberFormat);
                Log.i("Profit rate str:", electricityBoardRateTextView.getText().toString());

                Double rate_integr = new Double(0);
                if (!TextUtils.isEmpty(electricityBoardRateTextView.getText().toString())) {
                    try {
                        rate_integr = Double.valueOf(electricityBoardRateTextView.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                // So 10% of 150 = 10/100 × 150 = 15
                final_profit_double = (bubble_str / 100) * rate_integr;
                final_profit = String.format("%.2f", final_profit_double);
                profitTextView.setText(final_profit);
                Log.i("Profit is", final_profit);
            }
        });

        chargerTypeSpinner = findViewById(R.id.chargerTypeSpinner);
        chargerBrandSpinner = findViewById(R.id.chargerBrandSpinner);
        chargerModelSpinner = findViewById(R.id.chargerModelSpinner);
        connectorTypeSpinner = findViewById(R.id.connectorTypeSpinner);
        electricityBoardSpinner = findViewById(R.id.electricityBoardSpinner);
        rateStructureSpinner = findViewById(R.id.rateStructureSpinner);
        powerOutputSpinner = findViewById(R.id.powerOutputSpinner);
        voltageSpinner = findViewById(R.id.voltageSpinner);
        chargerAmphereSpinner = findViewById(R.id.amphereSpinner);
        socketSpinner = findViewById(R.id.socketSpinner);
        socketVoltageSpinner = findViewById(R.id.socketVoltageSpinner);
        socketElectricityBoardSpinner = findViewById(R.id.socketElectricityBoardSpinner);
        socketRateSpinner = findViewById(R.id.socketRateSpinner);
        socketAmphereSpinner = findViewById(R.id.socketAmphereSpinner);
        mFinalPowerOutputTextView = findViewById(R.id.final_power_output_text_view);
        socketPowerOutputEditText = findViewById(R.id.socket_power_output_edit_text);

        instant_booking = findViewById(R.id.instance_booking_switch);
        multi_single_day = findViewById(R.id.single_multi_day_switch);
        multiDayTableLayout = findViewById(R.id.multiDayLinearLayout);
        singleDayTableLayout = findViewById(R.id.singleDayLinearLayout);
        start_time_txt = findViewById(R.id.start_time_resi);
        end_time_txt = findViewById(R.id.end_time_resi);

        address_1 = findViewById(R.id.editText90);
        address_2 = findViewById(R.id.editText91);
        country_txt = findViewById(R.id.editText92);
        state_text = findViewById(R.id.editText93);
        landmark_txt = findViewById(R.id.editText94);
        zip_txt = findViewById(R.id.editText95);
        city_txt = findViewById(R.id.editText96);
        mondayCheckBox = findViewById(R.id.mondayCheckBox);
        tuesdayCheckBox = findViewById(R.id.tuesdayCheckBox);
        wednesdayCheckBox = findViewById(R.id.wednesdayCheckBox);
        thursdayCheckBox = findViewById(R.id.thursdayCheckBox);
        fridayCheckBox = findViewById(R.id.fridayCheckBox);
        saturdayCheckBox = findViewById(R.id.saturdayCheckBox);
        sundayCheckBox = findViewById(R.id.sundayCheckBox);
        tableLayout = findViewById(R.id.table_resi_edit);
        mondayCheckBox.setChecked(false);
        tuesdayCheckBox.setChecked(false);
        wednesdayCheckBox.setChecked(false);
        thursdayCheckBox.setChecked(false);
        fridayCheckBox.setChecked(false);
        saturdayCheckBox.setChecked(false);
        sundayCheckBox.setChecked(false);

        mondayCheckBox.setOnCheckedChangeListener(new mCheckBoxChangeListener());
        tuesdayCheckBox.setOnCheckedChangeListener(new mCheckBoxChangeListener());
        wednesdayCheckBox.setOnCheckedChangeListener(new mCheckBoxChangeListener());
        thursdayCheckBox.setOnCheckedChangeListener(new mCheckBoxChangeListener());
        fridayCheckBox.setOnCheckedChangeListener(new mCheckBoxChangeListener());
        saturdayCheckBox.setOnCheckedChangeListener(new mCheckBoxChangeListener());
        sundayCheckBox.setOnCheckedChangeListener(new mCheckBoxChangeListener());
        multiDaysCheckBoxArrayList.add(mondayCheckBox);
        multiDaysCheckBoxArrayList.add(tuesdayCheckBox);
        multiDaysCheckBoxArrayList.add(wednesdayCheckBox);
        multiDaysCheckBoxArrayList.add(thursdayCheckBox);
        multiDaysCheckBoxArrayList.add(fridayCheckBox);
        multiDaysCheckBoxArrayList.add(saturdayCheckBox);
        multiDaysCheckBoxArrayList.add(sundayCheckBox);

        singleMondayCheckBox = findViewById(R.id.singleMondayCheckBox);
        singleTuesdayCheckBox = findViewById(R.id.singleTuesdayCheckBox);
        singleWednesdayCheckBox = findViewById(R.id.singleWednesdayCheckBox);
        singleThursdayCheckBox = findViewById(R.id.singleThursdayCheckBox);
        singleFridayCheckBox = findViewById(R.id.singleFridayCheckBox);
        singleSaturdayCheckBox = findViewById(R.id.singleSaturdayCheckBox);
        singleSundayCheckBox = findViewById(R.id.singleSundayCheckBox);

        singleDayCheckBoxArrayList.add(singleMondayCheckBox);
        singleDayCheckBoxArrayList.add(singleTuesdayCheckBox);
        singleDayCheckBoxArrayList.add(singleWednesdayCheckBox);
        singleDayCheckBoxArrayList.add(singleThursdayCheckBox);
        singleDayCheckBoxArrayList.add(singleFridayCheckBox);
        singleDayCheckBoxArrayList.add(singleSaturdayCheckBox);
        singleDayCheckBoxArrayList.add(singleSundayCheckBox);

        singleDayStartTimeArrayList.add(mondayStartTimeList);
        singleDayStartTimeArrayList.add(tuesdayStartTimeList);
        singleDayStartTimeArrayList.add(wednesdayStartTimeList);
        singleDayStartTimeArrayList.add(thursdayStartTimeList);
        singleDayStartTimeArrayList.add(fridayStartTimeList);
        singleDayStartTimeArrayList.add(saturdayStartTimeList);
        singleDayStartTimeArrayList.add(sundayStartTimeList);

        allAvailableDaysList.add("monday");
        allAvailableDaysList.add("tuesday");
        allAvailableDaysList.add("wednesday");
        allAvailableDaysList.add("thursday");
        allAvailableDaysList.add("friday");
        allAvailableDaysList.add("saturday");
        allAvailableDaysList.add("sunday");

        mondayTableLayout = findViewById(R.id.singleMondayTableLayout);
        tuesdayTableLayout = findViewById(R.id.singleTuesdayTableLayout);
        wednesdayTableLayout = findViewById(R.id.singleWednesdayTableLayout);
        thursdayTableLayout = findViewById(R.id.singleThursdayTableLayout);
        fridayTableLayout = findViewById(R.id.singleFridayTableLayout);
        saturdayTableLayout = findViewById(R.id.singleSaturdayTableLayout);
        sundayTableLayout = findViewById(R.id.singleSundayTableLayout);

        singleMondayLinearLayout = findViewById(R.id.singleMondayLinearLayout);
        singleTuesdayLinearLayout = findViewById(R.id.singleTuesdayLinearLayout);
        singleWednesdayLinearLayout = findViewById(R.id.singleWednesdayLinearLayout);
        singleThursdayLinearLayout = findViewById(R.id.singleThursdayLinearLayout);
        singleFridayLinearLayout = findViewById(R.id.singleFridayLinearLayout);
        singleSaturdayLinearLayout = findViewById(R.id.singleSaturdayLinearLayout);
        singleSundayLinearLayout = findViewById(R.id.singleSundayLinearLayout);

        singleDayLinearLayoutList.add(singleMondayLinearLayout);
        singleDayLinearLayoutList.add(singleTuesdayLinearLayout);
        singleDayLinearLayoutList.add(singleWednesdayLinearLayout);
        singleDayLinearLayoutList.add(singleThursdayLinearLayout);
        singleDayLinearLayoutList.add(singleFridayLinearLayout);
        singleDayLinearLayoutList.add(singleSaturdayLinearLayout);
        singleDayLinearLayoutList.add(singleSundayLinearLayout);


        singleMondayCheckBox.setChecked(false);
        singleTuesdayCheckBox.setChecked(false);
        singleWednesdayCheckBox.setChecked(false);
        singleThursdayCheckBox.setChecked(false);
        singleFridayCheckBox.setChecked(false);
        singleSaturdayCheckBox.setChecked(false);
        singleSundayCheckBox.setChecked(false);

        singleMondayCheckBox.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
        singleTuesdayCheckBox.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
        singleWednesdayCheckBox.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
        singleThursdayCheckBox.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
        singleFridayCheckBox.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
        singleSaturdayCheckBox.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());
        singleSundayCheckBox.setOnCheckedChangeListener(new mSingleCheckBoxChangeListener());

        instant_booking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.i("Select booking instant:", String.valueOf(isChecked));
                    selected = 1;
                } else {
                    Log.i("Select booking not instant:", String.valueOf(isChecked));
                    selected = 0;

                }
            }
        });

        multi_single_day.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    singleDayTableLayout.setVisibility(View.VISIBLE);
                    multiDayTableLayout.setVisibility(View.GONE);
                    checked = 1;
                } else {
                    singleDayTableLayout.setVisibility(View.GONE);
                    multiDayTableLayout.setVisibility(View.VISIBLE);
                    checked = 0;
                }
            }
        });


        multiDay24HoursCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                multiDayStartTimeList.clear();
                multiDayEndTimeList.clear();
                tableLayout.removeAllViews();
                if (isChecked) {
                    multiDayAddTimeBlock.setVisibility(View.GONE);
                    final TableRow row = (TableRow) LayoutInflater.from(buttonView.getContext()).inflate(R.layout.start_and_end_time_row, null);
                    final EditText startTimeEditText = row.findViewById(R.id.startTimeEditText);
                    final EditText endTimeEditText = row.findViewById(R.id.endTimeEditText);
                    final ImageButton deleteImageButton = row.findViewById(R.id.deleteTime);
                    deleteImageButton.setVisibility(View.GONE);
                    startTimeEditText.setText("12:00 AM");
                    endTimeEditText.setText("12:00 AM");
                    multiDayStartTimeList.add("12:00 AM");
                    multiDayEndTimeList.add("12:00 AM");
                    tableLayout.addView(row);
                } else {
                    multiDayAddTimeBlock.setVisibility(View.VISIBLE);
                }
                showTextNotification(multiDayStartTimeList);
                showTextNotification(multiDayEndTimeList);
            }
        });

        multiDayAddTimeBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiDay24HoursCheckBox.setVisibility(View.GONE);
                final TableRow row = (TableRow) LayoutInflater.from(v.getContext()).inflate(R.layout.start_and_end_time_row, null);
                final EditText startTimeEditText = row.findViewById(R.id.startTimeEditText);
                final EditText endTimeEditText = row.findViewById(R.id.endTimeEditText);

                if (isDefaultTime) {
                    startTimeEditText.setText(multiDayStartTimeList.get(multiDayStartTimeList.size() - 1));
                    endTimeEditText.setText(multiDayEndTimeList.get(multiDayEndTimeList.size() - 1));
                } else {
                    startTimeEditText.setText("8:00 AM");
                    endTimeEditText.setText("8:00 PM");
                }
                isDefaultTime = false;
                multiDayStartTimeList.add(startTimeEditText.getText().toString());
                multiDayEndTimeList.add(endTimeEditText.getText().toString());
                TimePickerUniversal timePickerUniversal1 = new TimePickerUniversal();
                TimePickerUniversal timePickerUniversal2 = new TimePickerUniversal();

                timePickerUniversal1.setTimePickerUniversal(startTimeEditText, true);
                timePickerUniversal2.setTimePickerUniversal(endTimeEditText, true);


                startTimeEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        multiDayStartTimeList.remove(startTimeEditText.getText().toString());
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        multiDayStartTimeList.add(startTimeEditText.getText().toString());
                        showTextNotification(multiDayStartTimeList);
                    }
                });

                endTimeEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        multiDayEndTimeList.remove(endTimeEditText.getText().toString());
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        multiDayEndTimeList.add(endTimeEditText.getText().toString());
                        showTextNotification(multiDayEndTimeList);
                    }
                });

                delete_row = row.findViewById(R.id.deleteTime);
                delete_row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // row is your row, the parent of the clicked button
                        View row = (View) v.getParent();
                        // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
                        ViewGroup container = ((ViewGroup) row.getParent());
                        // delete the row and invalidate your view so it gets redrawn
                        container.removeView(row);
                        Log.i("Index=", String.valueOf(v.getTag()));
                        container.invalidate();
                        //deleteRowInfo();
                        if (tableLayout.getChildCount() == 0) {
                            multiDay24HoursCheckBox.setVisibility(View.VISIBLE);
                        }
                    }
                });

                //add your new row to the TableLayout:
                tableLayout.addView(row);

            }
        });


        singleMonday24HoursCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handle24HoursCheckBoxClick(buttonView, isChecked, mondayStartTimeList,
                        mondayEndTimeList, mondayTableLayout, singleMondayAddTimeBlock);
            }
        });

        singleMondayAddTimeBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSingleDayAddAnotherTimeBlock(view, singleMonday24HoursCheckBox, singleDaySelectedList, mondayTableLayout,
                        mondayStartTimeList, mondayEndTimeList);
            }
        });


        singleTuesday24HoursCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handle24HoursCheckBoxClick(buttonView, isChecked, tuesdayStartTimeList,
                        tuesdayEndTimeList, tuesdayTableLayout, singleTuesdayAddTimeBlock);
            }
        });

        singleTuesdayAddTimeBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSingleDayAddAnotherTimeBlock(view, singleTuesday24HoursCheckBox, singleDaySelectedList, tuesdayTableLayout,
                        tuesdayStartTimeList, tuesdayEndTimeList);
            }
        });


        singleWednesday24HoursCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handle24HoursCheckBoxClick(buttonView, isChecked, wednesdayStartTimeList,
                        wednesdayEndTimeList, wednesdayTableLayout, singleWednesdayAddTimeBlock);
            }
        });

        singleWednesdayAddTimeBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSingleDayAddAnotherTimeBlock(view, singleWednesday24HoursCheckBox, singleDaySelectedList, wednesdayTableLayout,
                        wednesdayStartTimeList, wednesdayEndTimeList);
            }
        });


        singleThursday24HoursCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handle24HoursCheckBoxClick(buttonView, isChecked, thursdayStartTimeList,
                        thursdayEndTimeList, thursdayTableLayout, singleThursdayAddTimeBlock);
            }
        });

        singleThursdayAddTimeBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSingleDayAddAnotherTimeBlock(view, singleThursday24HoursCheckBox, singleDaySelectedList, thursdayTableLayout,
                        thursdayStartTimeList, thursdayEndTimeList);
            }
        });


        singleFriday24HoursCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handle24HoursCheckBoxClick(buttonView, isChecked, fridayStartTimeList,
                        fridayEndTimeList, fridayTableLayout, singleFridayAddTimeBlock);
            }
        });

        singleFridayAddTimeBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSingleDayAddAnotherTimeBlock(view, singleFriday24HoursCheckBox, singleDaySelectedList, fridayTableLayout,
                        fridayStartTimeList, fridayEndTimeList);
            }
        });


        singleSaturday24HoursCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handle24HoursCheckBoxClick(buttonView, isChecked, saturdayStartTimeList,
                        saturdayEndTimeList, saturdayTableLayout, singleSaturdayAddTimeBlock);
            }
        });

        singleSaturdayAddTimeBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSingleDayAddAnotherTimeBlock(view, singleSaturday24HoursCheckBox, singleDaySelectedList, saturdayTableLayout,
                        saturdayStartTimeList, saturdayEndTimeList);
            }
        });

        singleSunday24HoursCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handle24HoursCheckBoxClick(buttonView, isChecked, sundayStartTimeList,
                        sundayEndTimeList, sundayTableLayout, singleSundayAddTimeBlock);
            }
        });

        singleSundayAddTimeBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSingleDayAddAnotherTimeBlock(view, singleSunday24HoursCheckBox, singleDaySelectedList, sundayTableLayout,
                        sundayStartTimeList, sundayEndTimeList);
            }
        });


        mAddressAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        mGooglePlacesAutocompleteAdapter = new GooglePlacesAutocompleteAdapter(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item);
        mAddressAutoCompleteTextView.setAdapter(mGooglePlacesAutocompleteAdapter);
        mAddressAutoCompleteTextView.setOnItemClickListener(this);

        Places.initialize(getApplicationContext(), "AIzaSyDNdANqUDsLyz6s3mtEJuEQBNaW4xVpK8k");
        address_1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.ADDRESS_COMPONENTS);
                // Start the autocomplete intent.
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN") //NIGERIA
                        .build(Objects.requireNonNull(getApplicationContext()));
                //startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);

            }
        });


        Bundle extras = getIntent().getExtras();
        if (extras == null) {

        } else {
            newString = extras.getString("List_V_ID");
            id = Integer.parseInt(newString);
        }

        APIInterface service = SingletonRetrofit.getAPIInterface();

        Call<show_public> call = service.getListPublic(id);
        Log.i("Call", String.valueOf(call.request().url()));

        call.enqueue(new Callback<show_public>() {
            @Override
            public void onResponse(Call<show_public> call, Response<show_public> response) {
                if (response.body() != null) {
                    Log.i("Make  response: ", String.valueOf(response.body()));
                    show_resi_arry = response.body();

                    Log.i("Day array: ", String.valueOf(show_resi_arry.getAddress()));
                    Log.i("Day array: ", String.valueOf(show_resi_arry.getAddress().getCountry()));
                    Log.i("Day array: ", String.valueOf(show_resi_arry.getDaysArray()));
                    address_1.setText(String.valueOf(show_resi_arry.getAddress().getAddress1()));
                    address_2.setText(String.valueOf(show_resi_arry.getAddress().getAddress2()));
                    country_txt.setText(String.valueOf(show_resi_arry.getAddress().getCountry()));
                    state_text.setText(String.valueOf(show_resi_arry.getAddress().getState()));
                    city_txt.setText(String.valueOf(show_resi_arry.getAddress().getCity()));
                    landmark_txt.setText(String.valueOf(show_resi_arry.getAddress().getLandmark()));
                    zip_txt.setText(String.valueOf(show_resi_arry.getAddress().getPin()));
                    profitTextView.setText(String.valueOf(show_resi_arry.getProvider().getProfit()));
                    String rate_va = (String.valueOf(show_resi_arry.getRateStructure()));
                    socket_rate = String.valueOf(rate_va);
                    charger_brand_str = show_resi_arry.getProvider().getChargerBrand();

                    String powerOutputValue = show_resi_arry.getProvider().getPowerOutput();
                    float yourProfit = show_resi_arry.getProfits().floatValue();
                    bubbleSeekBar.setProgress(yourProfit);
                    profitTextView.setText(show_resi_arry.getProvider().getProfit());


                    /*if(show_resi_arry.getChargerType() == null){
                        ChargerType chargerType= new ChargerType();
                        chargerType.setChargerType("Charging Station");
                        show_resi_arry.setChargerType(chargerType);
                    }*/
                    chargerTypeSpinnerValue = show_resi_arry.getChargerType().getChargerType();
                    chargerBrandSpinnerValue = show_resi_arry.getProvider().getChargerBrand();
                    chargerModelSpinnerValue = show_resi_arry.getProvider().getChargerModel();
                    connectorTypeSpinnerValue = show_resi_arry.getProvider().getConnectorType();
                    electricityBoardSpinnerValue = show_resi_arry.getProvider().getElectricity();
                    rateStructureSpinnerValue = show_resi_arry.getProvider().getRateStructure();
                    finalPowerOutputValue = show_resi_arry.getProvider().getPowerOutput();
                    voltageSpinnerValue = show_resi_arry.getProvider().getVoltage();
                    amphereSpinnerValue = show_resi_arry.getProvider().getAmphere();

                    socketSpinnerValue = show_resi_arry.getProvider().getSocket();
                    socketVoltageSpinnerValue = show_resi_arry.getProvider().getVoltage();
                    socketElectricityBoardSpinnerValue = show_resi_arry.getProvider().getElectricity();
                    socketRateSpinnerValue = show_resi_arry.getProvider().getRateStructure();
                    socketVoltageSpinnerValue = show_resi_arry.getProvider().getVoltage();
                    socketAmphereSpinnerValue = show_resi_arry.getProvider().getAmphere();

                    mFinalPowerOutputTextView.setText(""+finalPowerOutputValue);

                    initDropDownFields();

                    Long value_select = (show_resi_arry.getProvider().getInstantBooking());
                    if (value_select == 0) {
                        instant_booking.setChecked(false);
                        selected = 0;
                    } else {
                        instant_booking.setChecked(true);
                        selected = 1;
                    }

                    Long day_value = (show_resi_arry.getProvider().getSingleDay());
                    if (day_value == 0) {
                        checked = 0;
                        multi_single_day.setChecked(false);
                    } else {
                        checked = 1;
                        multi_single_day.setChecked(true);
                    }

                    user_id = show_resi_arry.getData().getUserId();
                    list_id = show_resi_arry.getData().getId();

                    if (!TextUtils.isEmpty(show_resi_arry.getProvider().getDays())) {
                        String[] defaultSelectedDaysArray = show_resi_arry.getProvider().getDays().split(",");
                        Collections.addAll(defaultSelectedDaysList, defaultSelectedDaysArray);
                    }
                    setDefaultDaysAndTimeSelection();
                } else {
                    String new_str = "Make is empty";
                    Log.i("Make: ", new_str);
                }
            }

            @Override
            public void onFailure(Call<show_public> call, Throwable t) {
                Log.i("Make error: ", String.valueOf(t));
            }
        });


        chargerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                charger_type_str = chargerTypeSpinner.getSelectedItem().toString();
                String state_str = state_text.getText().toString();

                if(charger_type_str.equalsIgnoreCase("Charger Type")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                if(charger_type_str.equalsIgnoreCase("Charger Type")){
                    chargerBrandSpinner.setVisibility(View.GONE);
                    chargerModelSpinner.setVisibility(View.GONE);
                    connectorTypeSpinner.setVisibility(View.GONE);
                    electricityBoardSpinner.setVisibility(View.GONE);
                    rateStructureSpinner.setVisibility(View.GONE);
                    powerOutputSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    chargerAmphereSpinner.setVisibility(View.GONE);

                    socketSpinner.setVisibility(View.GONE);
                    socketElectricityBoardSpinner.setVisibility(View.GONE);
                    socketRateSpinner.setVisibility(View.GONE);
                    socketPowerOutputEditText.setVisibility(View.GONE);
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                }

                if (state_str.isEmpty()) {
                    Toast.makeText(EditPublicListingActivity.this, "State is empty", Toast.LENGTH_LONG).show();
                } else {
                    if (charger_type_str.equals("Charging Station")) {
                        chargerBrandSpinner.setVisibility(View.VISIBLE);
                        chargerModelSpinner.setVisibility(View.VISIBLE);
                        connectorTypeSpinner.setVisibility(View.VISIBLE);
                        electricityBoardSpinner.setVisibility(View.VISIBLE);
                        rateStructureSpinner.setVisibility(View.VISIBLE);
                        powerOutputSpinner.setVisibility(View.VISIBLE);
                        voltageSpinner.setVisibility(View.VISIBLE);
                        chargerAmphereSpinner.setVisibility(View.VISIBLE);

                        socketSpinner.setVisibility(View.GONE);
                        socketElectricityBoardSpinner.setVisibility(View.GONE);
                        socketRateSpinner.setVisibility(View.GONE);
                        socketPowerOutputEditText.setVisibility(View.GONE);
                        socketVoltageSpinner.setVisibility(View.GONE);
                        socketAmphereSpinner.setVisibility(View.GONE);

                    } else if (charger_type_str.equals("Standard Domestic Socket")) {
                        chargerBrandSpinner.setVisibility(View.GONE);
                        chargerModelSpinner.setVisibility(View.GONE);
                        connectorTypeSpinner.setVisibility(View.GONE);
                        electricityBoardSpinner.setVisibility(View.GONE);
                        rateStructureSpinner.setVisibility(View.GONE);
                        powerOutputSpinner.setVisibility(View.GONE);
                        voltageSpinner.setVisibility(View.GONE);
                        chargerAmphereSpinner.setVisibility(View.GONE);

                        socketSpinner.setVisibility(View.VISIBLE);
                        socketElectricityBoardSpinner.setVisibility(View.VISIBLE);
                        socketRateSpinner.setVisibility(View.VISIBLE);
                        socketPowerOutputEditText.setVisibility(View.VISIBLE);
                        socketVoltageSpinner.setVisibility(View.VISIBLE);
                        socketAmphereSpinner.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chargerBrandSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                brand_str = chargerBrandSpinner.getSelectedItem().toString();
                if(brand_str.equalsIgnoreCase("Charger Brand")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                String model_str = chargerBrandSpinner.getSelectedItem().toString();
                if (brand_str.equals("Charger Brand")) {
                    chargerModelSpinner.setVisibility(View.GONE);
                } else {
                    spinner_model.clear();
                    spinner_model.add("Charger Model");
                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<public_charger_model>> call = service.getChargerModelPublic(model_str);
                    Log.i("Model_________________________________________________________ : ", String.valueOf(call.request().url()));

                    call.enqueue(new Callback<List<public_charger_model>>() {
                        @Override
                        public void onResponse(Call<List<public_charger_model>> call, Response<List<public_charger_model>> response) {
                            if (response.body() != null) {
                                charger_model = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    my_model = charger_model.get(i);
                                    demo_str = my_model.getChargerModelPublic();
                                    spinner_model.add(demo_str);
                                }

                            } else {
                                String new_str = "Model is empty";
                            }
                        }

                        @Override
                        public void onFailure(Call<List<public_charger_model>> call, Throwable t) {
                            Log.i("login error: ", String.valueOf(t));
                        }
                    });

                    ArrayAdapter<String> adapter_103 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_model);
                    chargerModelSpinner.setAdapter(adapter_103);
                    chargerModelSpinner.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chargerModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String state_str = state_text.getText().toString();
                Log.i("State  : ", state_str);
                cs_model_str = chargerModelSpinner.getSelectedItem().toString();

                if(cs_model_str.equalsIgnoreCase("Charger Model")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                if (cs_model_str.equals("Charger Model")) {
                    connectorTypeSpinner.setVisibility(View.GONE);
                    chargerAmphereSpinner.setVisibility(View.GONE);
                    voltageSpinner.setVisibility(View.GONE);
                    powerOutputSpinner.setVisibility(View.GONE);
                } else {
                    String model_str = chargerModelSpinner.getSelectedItem().toString();
                    connectorTypeSpinner.setVisibility(View.VISIBLE);
                    spinner_power_output.clear();
                    spinner_voltage.clear();
                    charger_Amphere.clear();
                    spinner_voltage.add("Voltage");
                    spinner_power_output.add("Power output");
                    charger_Amphere.add("Amphere");

                    String board = "";
                    if(electricityBoardSpinner.getSelectedItem() != null) {
                        board = electricityBoardSpinner.getSelectedItem().toString();
                    }else {
                        board = electricityBoardSpinnerValue;
                    }

                    APIInterface service = SingletonRetrofit.getAPIInterface();
                    Call<List<public_power_output>> call2 = service.getPowerOutputPublic(brand_str, model_str, board);
                    Log.i("power Res***************************************************  : ", String.valueOf(call2.request().url()));
                    call2.enqueue(new Callback<List<public_power_output>>() {
                        @Override
                        public void onResponse(Call<List<public_power_output>> call, Response<List<public_power_output>> response) {
                            if (response.body() != null) {
                                power_com = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    power_c = power_com.get(i);
                                    demo_str = power_c.getPowerOutputPublic();
                                    spinner_power_output.add(demo_str);
                                    Log.i("power output Array: ", spinner_power_output.toString());
                                }

                            } else {
                                String new_str = "Power Output is empty";
                                Log.i("Power output: ", new_str);

                            }
                        }

                        @Override
                        public void onFailure(Call<List<public_power_output>> call2, Throwable t) {
                            Log.i("Pewer output Res error: ", String.valueOf(t));
                        }
                    });


                    Call<List<public_voltage>> call6 = service.getVoltagePublic(model_str, brand_str, board);
                    Log.i("Voltage***********************************************************************************************  : ", String.valueOf(call6.request().url()));
                    call6.enqueue(new Callback<List<public_voltage>>() {
                        @Override
                        public void onResponse(Call<List<public_voltage>> call6, Response<List<public_voltage>> response) {
                            if (response.body() != null) {
                                volt_com = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    volt_c = volt_com.get(i);
                                    demo_str = volt_c.getVoltagePublic();
                                    spinner_voltage.add(demo_str);
                                    Log.i("Voltage output Array: ", spinner_voltage.toString());
                                }

                            } else {
                                String new_str = "Voltage Output is empty";
                                Log.i("Voltage output: ", new_str);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<public_voltage>> call6, Throwable t) {
                            Log.i("Voltage output error: ", String.valueOf(t));
                        }
                    });


                    Call<List<public_charger_amphere>> call7 = service.getChargerAmpherePublic(brand_str, model_str, board);
                    Log.i("Amphere***********************************************************************************************  : ", String.valueOf(call6.request().url()));
                    call7.enqueue(new Callback<List<public_charger_amphere>>() {
                        @Override
                        public void onResponse(Call<List<public_charger_amphere>> call7, Response<List<public_charger_amphere>> response) {
                            if (response.body() != null) {
                                charger_amp_array = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    charger_amp = charger_amp_array.get(i);
                                    demo_str = charger_amp.getChargerAmpherePublic();
                                    charger_Amphere.add(demo_str);
                                    Log.i("Amphere output Array: ", charger_Amphere.toString());
                                }

                            } else {
                                String new_str = "Amphere Output is empty";
                                Log.i("Amphere output: ", new_str);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<public_charger_amphere>> call7, Throwable t) {
                            Log.i("Amphere output error: ", String.valueOf(t));
                        }
                    });

                    ArrayAdapter<String> adapter_107 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_power_output);
                    powerOutputSpinner.setAdapter(adapter_107);
                    powerOutputSpinner.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> adapter_108 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_voltage);
                    voltageSpinner.setAdapter(adapter_108);
                    voltageSpinner.setVisibility(View.VISIBLE);

                    ArrayAdapter<String> adapter_C_A = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, charger_Amphere);
                    chargerAmphereSpinner.setAdapter(adapter_C_A);
                    chargerAmphereSpinner.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //model
        connectorTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cs_connector_str = connectorTypeSpinner.getSelectedItem().toString().trim();
                if(cs_connector_str.equalsIgnoreCase("Connector Type")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //cs_connector
        socketSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String socket_voltage_str = socketSpinner.getSelectedItem().toString();
                if(socket_voltage_str.equalsIgnoreCase("Select socket")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                socket_str = socketSpinner.getSelectedItem().toString();
                String state_str = state_text.getText().toString();
                if (socket_str.equals("Select socket")) {
                    socketVoltageSpinner.setVisibility(View.GONE);
                    socketAmphereSpinner.setVisibility(View.GONE);
                } else {
                    socket_voltage.clear();
                    socket_voltage.add("socket voltage");
                    String socket_str = socketSpinner.getSelectedItem().toString();
                    APIInterface service = SingletonRetrofit.getAPIInterface();
                    Call<List<public_voltage_socket>> call4 = service.getVoltageSocketPublic(socket_str, board_str);

                    call4.enqueue(new Callback<List<public_voltage_socket>>() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(Call<List<public_voltage_socket>> call4, Response<List<public_voltage_socket>> response) {
                            if (response.body() != null) {
                                Log.i("Residential socket vtg: ", String.valueOf(response.body()));
                                socket_vtg_res = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    socket_vtg = socket_vtg_res.get(i);
                                    demo_str = socket_vtg.getVoltageSocketPublic();
                                    Log.i("Residential socket vtg R: ", String.valueOf(demo_str));
                                    socket_voltage.add(demo_str);
                                    Log.i("Residential socket vtg Array: ", socket_voltage.toString());
                                }
                            } else {
                                String new_str = "Residential socket vtg is empty";
                            }
                        }

                        @SuppressLint("LongLogTag")
                        @Override
                        public void onFailure(Call<List<public_voltage_socket>> call4, Throwable t) {
                            Log.i("Residential socket vtg error: ", String.valueOf(t));
                        }
                    });

                    ArrayAdapter<String> adapter_110 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, socket_voltage);
                    socketVoltageSpinner.setAdapter(adapter_110);
                    socketVoltageSpinner.setVisibility(View.VISIBLE);
                    socket_amphere.clear();
                    socket_amphere.add("socket Amphere");

                    //        Select voltage of socket------------------------------------------
                    Call<List<public_socket_amphere>> call2 = service.getSocketAmpherePublic(socket_str, socketElectricityBoardSpinnerValue);
                    call2.enqueue(new Callback<List<public_socket_amphere>>() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onResponse(Call<List<public_socket_amphere>> call2, Response<List<public_socket_amphere>> response) {
                            if (response.body() != null) {
                                Log.i("Residential socket amp: ", String.valueOf(response.body()));
                                socket_amp_array = response.body();
                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    socket_amp = socket_amp_array.get(i);
                                    demo_str = socket_amp.getSocketAmpherePublic();
                                    Log.i("Residential socket amp R: ", String.valueOf(demo_str));
                                    socket_amphere.add(demo_str);
                                    Log.i("Residential socket amp Array: ", socket_amphere.toString());
                                }
                            } else {
                                String new_str = "Residential socket amp is empty";
                            }
                        }


                        @SuppressLint("LongLogTag")
                        @Override
                        public void onFailure(Call<List<public_socket_amphere>> call2, Throwable t) {
                            Log.i("Residential socket amp error: ", String.valueOf(t));
                        }
                    });

                    ArrayAdapter<String> adapter_A_S = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, socket_amphere);
                    socketAmphereSpinner.setAdapter(adapter_A_S);
                    socketAmphereSpinner.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //socket_str
        socketElectricityBoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_board_str = socketElectricityBoardSpinner.getSelectedItem().toString();
                String selectedValue = socketElectricityBoardSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Select board")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        electricityBoardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                board_str = electricityBoardSpinner.getSelectedItem().toString();
                if(board_str.equalsIgnoreCase("Electricity board")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rateStructureSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rate_str = rateStructureSpinner.getSelectedItem().toString();
                electricityBoardRateTextView.setText(rateStructureSpinner.getSelectedItem().toString());
                if(rate_str.equalsIgnoreCase("Rate structure")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        socketRateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_rate = socketRateSpinner.getSelectedItem().toString();
                electricityBoardRateTextView.setText(socket_rate);

                String selectedValue = socketRateSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("select socket rate")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        powerOutputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                power_str = powerOutputSpinner.getSelectedItem().toString();

                String selectedValue = powerOutputSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Power output")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                float finalPowerOutputValue = 0;
                if (!TextUtils.isEmpty(selectedValue) && !selectedValue.equalsIgnoreCase("Power output")) {
                    try {
                        finalPowerOutputValue = Float.parseFloat(selectedValue);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                if(voltageSpinner.getSelectedItemPosition() != 0) {
                    voltageSpinner.setSelection(0);
                }
                if(chargerAmphereSpinner.getSelectedItemPosition() != 0) {
                    chargerAmphereSpinner.setSelection(0);
                }
                mFinalPowerOutputTextView.setText("" + finalPowerOutputValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        voltageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vtg_str = voltageSpinner.getSelectedItem().toString();

                String selectedValue = voltageSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Voltage")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(vtg_str) && !vtg_str.equalsIgnoreCase("Voltage")
                        && !TextUtils.isEmpty(charger_amp_str) && !charger_amp_str.equalsIgnoreCase("Amphere")){
                    try{
                        voltageValue = Float.parseFloat(vtg_str);
                        amphereValue = Float.parseFloat(charger_amp_str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                setFinalPowerOutput(voltageValue, amphereValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        socketVoltageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_vtg_str = socketVoltageSpinner.getSelectedItem().toString();//socket_vtg
                String selectedValue = socketVoltageSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("socket voltage")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(socket_vtg_str) && !socket_vtg_str.equalsIgnoreCase("socket voltage")
                        && !TextUtils.isEmpty(socket_amp_str) && !socket_amp_str.equalsIgnoreCase("socket Amphere")){
                    try{
                        voltageValue = Float.parseFloat(socket_vtg_str);
                        amphereValue = Float.parseFloat(socket_amp_str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                setFinalPowerOutput(voltageValue, amphereValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chargerAmphereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                charger_amp_str = chargerAmphereSpinner.getSelectedItem().toString();//chrager_amp

                String selectedValue = chargerAmphereSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("Amphere")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(vtg_str) && !vtg_str.equalsIgnoreCase("Voltage")
                        && !TextUtils.isEmpty(charger_amp_str) && !charger_amp_str.equalsIgnoreCase("Amphere")){
                    try{
                        voltageValue = Float.parseFloat(vtg_str);
                        amphereValue = Float.parseFloat(charger_amp_str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                setFinalPowerOutput(voltageValue, amphereValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        socketPowerOutputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String selectedValue = socketPowerOutputEditText.getText().toString();
                float finalPowerOutputValue = 0;
                if (!TextUtils.isEmpty(selectedValue) && !selectedValue.equalsIgnoreCase("Power output")) {
                    try {
                        finalPowerOutputValue = Float.parseFloat(selectedValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (socketVoltageSpinner.getSelectedItemPosition() != 0){
                    socketVoltageSpinner.setSelection(0);
                }
                if(socketAmphereSpinner.getSelectedItemPosition() != 0) {
                    socketAmphereSpinner.setSelection(0);
                }
                mFinalPowerOutputTextView.setText(""+finalPowerOutputValue);
            }
        });



        //charger_amp
        socketAmphereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                socket_amp_str = socketAmphereSpinner.getSelectedItem().toString();//socket_Amp
                String selectedValue = socketAmphereSpinner.getSelectedItem().toString();
                if(selectedValue.equalsIgnoreCase("socket Amphere")){
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                }else {
                    if(((TextView) parent.getChildAt(0)) != null)
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }

                float voltageValue = 0;
                float amphereValue = 0;
                if(!TextUtils.isEmpty(socket_vtg_str) && !socket_vtg_str.equalsIgnoreCase("socket voltage")
                        && !TextUtils.isEmpty(socket_amp_str) && !socket_amp_str.equalsIgnoreCase("socket Amphere")){
                    try{
                        voltageValue = Float.parseFloat(socket_vtg_str);
                        amphereValue = Float.parseFloat(socket_amp_str);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                setFinalPowerOutput(voltageValue, amphereValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final ArrayList<EditText> editTextArrayList = new ArrayList<>();
        editTextArrayList.add(address_1);
        editTextArrayList.add(address_2);
        editTextArrayList.add(country_txt);
        editTextArrayList.add(state_text);
        editTextArrayList.add(city_txt);
        editTextArrayList.add(landmark_txt);
        editTextArrayList.add(zip_txt);

        save_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String address1 = address_1.getText().toString();
                String address2 = address_2.getText().toString();
                String landmark = landmark_txt.getText().toString();
                String city = city_txt.getText().toString();
                String state = state_text.getText().toString();
                String country = country_txt.getText().toString();
                Long pinCode = Long.parseLong(zip_txt.getText().toString());
                String profiteTextViewValue = profitTextView.getText().toString();

                String chargerType = "";
                String chargerConnectorType = "";
                String chargerBrand = "";
                String chargerModel = "";
                String chargerVoltage = "";
                String chargerAmphere = "";
                String powerOutput = "";
                String electricityBoard = "";
                String boardRate = "";
                String socketSpinnerType = "";

                chargerType = chargerTypeSpinner.getSelectedItem().toString();
                if (chargerType.equalsIgnoreCase("Charging Station")) {
                    chargerConnectorType = connectorTypeSpinner.getSelectedItem().toString();
                    chargerBrand = chargerBrandSpinner.getSelectedItem().toString();
                    chargerModel = chargerModelSpinner.getSelectedItem().toString();
                    chargerVoltage = voltageSpinner.getSelectedItem().toString();
                    chargerAmphere = chargerAmphereSpinner.getSelectedItem().toString();
                    powerOutput = mFinalPowerOutputTextView.getText().toString();
                    electricityBoard = electricityBoardSpinner.getSelectedItem().toString();
                    boardRate = electricityBoardRateTextView.getText().toString();
                } else {
                    socketSpinnerType = socketSpinner.getSelectedItem().toString();
                    chargerVoltage = socketVoltageSpinner.getSelectedItem().toString();
                    chargerAmphere = socketAmphereSpinner.getSelectedItem().toString();
                    powerOutput = mFinalPowerOutputTextView.getText().toString();
                    electricityBoard = socketElectricityBoardSpinner.getSelectedItem().toString();
                    boardRate = socketRateSpinner.getSelectedItem().toString();
                }


                initializeAndSaveDayTime(tableLayout, multiDayStartTimeList, multiDayEndTimeList);

                initializeAndSaveDayTime(mondayTableLayout, mondayStartTimeList, mondayEndTimeList);
                initializeAndSaveDayTime(tuesdayTableLayout, tuesdayStartTimeList, tuesdayEndTimeList);
                initializeAndSaveDayTime(wednesdayTableLayout, wednesdayStartTimeList, wednesdayEndTimeList);
                initializeAndSaveDayTime(thursdayTableLayout, thursdayStartTimeList, thursdayEndTimeList);
                initializeAndSaveDayTime(fridayTableLayout, fridayStartTimeList, fridayEndTimeList);
                initializeAndSaveDayTime(saturdayTableLayout, saturdayStartTimeList, saturdayEndTimeList);
                initializeAndSaveDayTime(sundayTableLayout, sundayStartTimeList, sundayEndTimeList);

                singleDaySelectedList.clear();
                multidaysSelectedList.clear();
                if (checked == 1) {
                    for(String singleDay : allAvailableDaysList) {
                        int index = allAvailableDaysList.indexOf(singleDay);
                        if(singleDayStartTimeArrayList.get(index).size() > 0){
                            singleDaySelectedList.add(singleDay);
                        }
                    }
                } else {
                    for(String singleDay : allAvailableDaysList) {
                        int index = allAvailableDaysList.indexOf(singleDay);
                        if(multiDaysCheckBoxArrayList.get(index).isChecked()){
                            multidaysSelectedList.add(singleDay);
                        }
                    }
                }


                int temp_id = 2;//replace with id
                APIInterface service = SingletonRetrofit.getAPIInterface();
                Log.i("Call base url", String.valueOf(SingletonRetrofit.getRetrofit().baseUrl()));
                Log.i("Call converter", String.valueOf(SingletonRetrofit.getRetrofit().converterFactories()));
                Call<ResponseBody> call = service.edit_public_list(
                        (long) show_resi_arry.getData().getId(),
                        user_id,
                        address1,
                        address2,
                        landmark,
                        city,
                        state,
                        country,
                        latitude,
                        longitude,
                        pinCode,

                        profiteTextViewValue,
                        selected,
                        checked,

                        chargerType,
                        chargerConnectorType,
                        chargerBrand,
                        chargerModel,
                        chargerVoltage,
                        chargerAmphere,
                        powerOutput,
                        electricityBoard,
                        boardRate,
                        socketSpinnerType,

                        multidaysSelectedList,
                        multiDayStartTimeList,
                        multiDayEndTimeList,

                        singleDaySelectedList,
                        mondayStartTimeList,
                        mondayEndTimeList,
                        tuesdayStartTimeList,
                        tuesdayEndTimeList,
                        wednesdayStartTimeList,
                        wednesdayEndTimeList,
                        thursdayStartTimeList,
                        thursdayEndTimeList,
                        fridayStartTimeList,
                        fridayEndTimeList,
                        saturdayStartTimeList,
                        saturdayEndTimeList,
                        sundayStartTimeList,
                        sundayEndTimeList,


                        "null",
                        "null",
                        "null",
                        "null",
                        "null",
                        "null",
                        "null",
                        "null"
                );


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.i("Residential response1:", String.valueOf(response.isSuccessful()));
                        Log.i("Residential response2:", String.valueOf(response.raw().isSuccessful()));
                        Log.i("Residential response3:", String.valueOf(response.errorBody()));
                        Toast.makeText(EditPublicListingActivity.this, "Edit listing succesfully", Toast.LENGTH_LONG).show();
                        finish();
                        ///Future activity Navigate to MY Listing of login home navigation menu not implement at yet.
                    }


                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.i("Error", String.valueOf(t.getMessage()));
                        Toast.makeText(EditPublicListingActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i("TAG", "Place: " + place.getName() + ", " + place.getId() + "," + place.getLatLng() + "," + place.getAddressComponents());
                address_1.setText(place.getAddress());
                address_2.setText(place.getName());
                latitude = Float.parseFloat(String.valueOf(place.getLatLng().latitude));
                longitude = Float.parseFloat(String.valueOf(place.getLatLng().longitude));
                Log.i("Latitude :", String.valueOf(latitude));
                Log.i("Longitude :", String.valueOf(longitude));

                List<AddressComponent> list = place.getAddressComponents().asList();
                for (int x = 0; x < list.size(); x++) {
                    AddressComponent ac = list.get(x);
                    List<String> types = ac.getTypes();
                    for (int y = 0; y < types.size(); y++) {
                        String type = types.get(y);
                        if (type.equals("postal_code")) {
                            String sZIP = ac.getName();
                            Log.i("TAG", "pincode: " + sZIP);
                            zip_txt.setText(sZIP);
                        } else if (type.equals("administrative_area_level_2")) {
                            String sCity = ac.getName();
                            Log.i("TAG", "city: " + sCity);
                            city_txt.setText(sCity);
                        } else if (type.equals("administrative_area_level_1")) {
                            String sState = ac.getName();
                            Log.i("TAG", "state: " + sState);
                            state_text.setText(sState);
                        } else if (type.equals("country")) {
                            String sCountry = ac.getName();
                            Log.i("TAG", "Country: " + sCountry);
                            country_txt.setText(sCountry);
                        } else if (type.equals("sublocality_level_1") | (type.equals("sublocality_level_2") | (type.equals("route")))) {
                            String sLM = ac.getName();
                            Log.i("TAG", "Landmark: " + sLM);
                            landmark_txt.setText(sLM);
                        }
                    }
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("TAG", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                Log.i("TAG", String.valueOf(resultCode));

            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        mAddressAutoCompleteTextView.setText("");
        String place_id = mGooglePlacesAutocompleteAdapter.getPlaceID(position);
        String url = "https://maps.googleapis.com/maps/api/place/details/";
        final APIInterface apiInterface = SingletonRetrofit.getGoogleMapRetrofit(url).create(APIInterface.class);
        Call<JsonObject> call = apiInterface.getWholeAddress(GOOGLE_MAP_API_KEY, place_id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JsonArray addressComponents = (JsonArray) ((JsonObject) response.body().get("result")).get("address_components");
                    JsonObject geometry = (JsonObject) ((JsonObject) response.body().get("result")).get("geometry");

                    address_1.setText("");
                    address_2.setText("");
                    latitude = 0;
                    longitude = 0;
                    zip_txt.setText("");
                    city_txt.setText("");
                    state_text.setText("");
                    country_txt.setText("");
                    landmark_txt.setText("");

                    String formatted_address = ((JsonObject) response.body().get("result")).get("formatted_address").toString();
                    formatted_address = trimDoubbleQuote(formatted_address);
                    String lat = ((JsonObject) geometry.get("location")).get("lat").toString();
                    String lng = ((JsonObject) geometry.get("location")).get("lng").toString();

                    try {
                        latitude = (float) Double.parseDouble(lat);
                        longitude = (float) Double.parseDouble(lng);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    address_1.setText(formatted_address);
                    for (int i = 0; i < addressComponents.size(); i++) {
                        JsonObject object = ((JsonObject) (addressComponents).get(i));
                        for (int j = 0; j < object.size(); j++) {
                            JsonArray types = (JsonArray) (object).get("types");
                            for (int k = 0; k < types.size(); k++) {
                                if (types.get(k).toString().equalsIgnoreCase("\"neighborhood\"")) {
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "neighborhood: " + value);
                                    address_2.setText(value);
                                } else if (types.get(k).toString().equalsIgnoreCase("\"administrative_area_level_1\"")) {
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "administrative_area_level_1: " + value);
                                    state_text.setText(value);
                                } else if (types.get(k).toString().equalsIgnoreCase("\"administrative_area_level_2\"")) {
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "administrative_area_level_2: " + value);
                                    city_txt.setText(value);
                                } else if (types.get(k).toString().equalsIgnoreCase("\"country\"")) {
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "country: " + value);
                                    country_txt.setText(value);
                                } else if (types.get(k).toString().equalsIgnoreCase("\"sublocality_level_1\"")) {
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "sublocality_level_1: " + value);
                                    landmark_txt.setText(value);
                                } else if (types.get(k).toString().equalsIgnoreCase("\"postal_code\"")) {
                                    String value = object.get("long_name").toString();
                                    value = trimDoubbleQuote(value);
                                    Log.i("TAG", "pincode: " + value);
                                    zip_txt.setText(value);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    public void initDropDownFields() {
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("Charger Type");
        arrayList1.add("Charging Station");
        arrayList1.add("Standard Domestic Socket");

        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(EditPublicListingActivity.this, R.layout.sample_text, arrayList1);
        chargerTypeSpinner.setAdapter(adapter101);
        chargerTypeSpinner.setSelection(adapter101.getPosition(chargerTypeSpinnerValue));
        chargerTypeSpinner.setVisibility(View.VISIBLE);

        if (chargerTypeSpinnerValue.equalsIgnoreCase("Charging Station")) {
            chargerBrandSpinner.setVisibility(View.VISIBLE);
            chargerModelSpinner.setVisibility(View.VISIBLE);
            connectorTypeSpinner.setVisibility(View.VISIBLE);
            electricityBoardSpinner.setVisibility(View.VISIBLE);
            rateStructureSpinner.setVisibility(View.VISIBLE);
            powerOutputSpinner.setVisibility(View.VISIBLE);
            voltageSpinner.setVisibility(View.VISIBLE);
            chargerAmphereSpinner.setVisibility(View.VISIBLE);

            socketSpinner.setVisibility(View.GONE);
            socketElectricityBoardSpinner.setVisibility(View.GONE);
            socketRateSpinner.setVisibility(View.GONE);
            socketPowerOutputEditText.setVisibility(View.GONE);
            socketVoltageSpinner.setVisibility(View.GONE);
            socketAmphereSpinner.setVisibility(View.GONE);

            mFinalPowerOutputTextView.setVisibility(View.VISIBLE);

        } else if (chargerTypeSpinnerValue.equalsIgnoreCase("Standard Domestic Socket")) {
            chargerBrandSpinner.setVisibility(View.GONE);
            chargerModelSpinner.setVisibility(View.GONE);
            connectorTypeSpinner.setVisibility(View.GONE);
            electricityBoardSpinner.setVisibility(View.GONE);
            rateStructureSpinner.setVisibility(View.GONE);
            powerOutputSpinner.setVisibility(View.GONE);
            voltageSpinner.setVisibility(View.GONE);
            chargerAmphereSpinner.setVisibility(View.GONE);

            socketSpinner.setVisibility(View.VISIBLE);
            socketElectricityBoardSpinner.setVisibility(View.VISIBLE);
            socketRateSpinner.setVisibility(View.VISIBLE);
            socketPowerOutputEditText.setVisibility(View.VISIBLE);
            socketVoltageSpinner.setVisibility(View.VISIBLE);
            socketAmphereSpinner.setVisibility(View.VISIBLE);

            mFinalPowerOutputTextView.setVisibility(View.VISIBLE);
        }

        mFinalPowerOutputTextView.setText(finalPowerOutputValue);
        mFinalPowerOutputTextView.setVisibility(View.VISIBLE);
        initSpinnerApi();
    }

    public void initSpinnerApi() {

        spinner_brand.clear();
        spinner_brand.add("Charger Brand");
        APIInterface service = SingletonRetrofit.getAPIInterface();

        Call<List<public_charegr_brand>> call = service.getChargerBrandPublic();
        Log.i("Brand************************* : ", String.valueOf(call.request().url()));
        call.enqueue(new Callback<List<public_charegr_brand>>() {
            @Override
            public void onResponse(Call<List<public_charegr_brand>> call, Response<List<public_charegr_brand>> response) {
                if (response.body() != null) {
                    charger_brand = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        my_brand = charger_brand.get(i);
                        demo_str = my_brand.getChargerBrandPublic();
                        spinner_brand.add(demo_str);
                        Log.i("login Array: ", spinner_brand.toString());
                    }
                    ArrayAdapter<String> adapter_102 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_brand);
                    chargerBrandSpinner.setAdapter(adapter_102);
                    chargerBrandSpinner.setSelection(adapter_102.getPosition(chargerBrandSpinnerValue));
                } else {
                    String new_str = "charger is empty";
                    Log.i("login Array: ", new_str);
                }
            }

            @Override
            public void onFailure(Call<List<public_charegr_brand>> call, Throwable t) {
                Log.i("login error: ", String.valueOf(t));
            }
        });


        spinner_model.clear();
        spinner_model.add("Charger Model");
        Call<List<public_charger_model>> call7 = service.getChargerModelPublic(chargerBrandSpinnerValue);
        Log.i("Model_________________________________________________________ : ", String.valueOf(call.request().url()));

        call7.enqueue(new Callback<List<public_charger_model>>() {
            @Override
            public void onResponse(Call<List<public_charger_model>> call, Response<List<public_charger_model>> response) {
                if (response.body() != null) {
                    charger_model = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        my_model = charger_model.get(i);
                        demo_str = my_model.getChargerModelPublic();
                        spinner_model.add(demo_str);
                    }
                    ArrayAdapter<String> adapter_103 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_model);
                    chargerModelSpinner.setAdapter(adapter_103);
                    chargerModelSpinner.setSelection(adapter_103.getPosition(chargerModelSpinnerValue));
                } else {
                    String new_str = "Model is empty";
                }
            }

            @Override
            public void onFailure(Call<List<public_charger_model>> call, Throwable t) {
                Log.i("login error: ", String.valueOf(t));
            }
        });

        spinner_connector.clear();
        spinner_connector.add("Connector Type");
        Call<List<public_connector>> call8 = service.getConnectorTypePublic(chargerModelSpinnerValue,
                chargerBrandSpinnerValue, electricityBoardSpinnerValue);
        Log.i("Connector_api  : ", String.valueOf(call.request().url()));

        call8.enqueue(new Callback<List<public_connector>>() {
            @Override
            public void onResponse(Call<List<public_connector>> call, Response<List<public_connector>> response) {
                if (response.body() != null) {
                    Log.i("login response: ", String.valueOf(response.body()));
                    connetor_set = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        my_connector = connetor_set.get(i);
                        demo_str = my_connector.getConnectorTypePublic();
                        spinner_connector.add(demo_str);
                    }

                    ArrayAdapter<String> adapter_104 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_connector);
                    connectorTypeSpinner.setAdapter(adapter_104);
                    connectorTypeSpinner.setSelection(adapter_104.getPosition(connectorTypeSpinnerValue));
                } else {
                    String new_str = "connector is empty";
                }
            }

            @Override
            public void onFailure(Call<List<public_connector>> call, Throwable t) {
                Log.i("login error: ", String.valueOf(t));
            }
        });


        spinner_electricity_board.clear();
        spinner_electricity_board.add("Electricity board");
        APIInterface service5 = SingletonRetrofit.getAPIInterface();
        String state_str2 = state_text.getText().toString();

        Call<List<public_board>> call5 = service5.getElectricityBoardPublic(state_str2);
        call5.enqueue(new Callback<List<public_board>>() {
            @Override
            public void onResponse(Call<List<public_board>> call5, Response<List<public_board>> response) {
                if (response.body() != null) {
                    Log.i("Board residential: ", String.valueOf(response.body()));
                    board_m = response.body();

                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        my_board = board_m.get(i);
                        demo_str = my_board.getElectricityBoardPublic();
                        spinner_electricity_board.add(demo_str);
                    }
                    ArrayAdapter<String> adapter_105 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_electricity_board);
                    electricityBoardSpinner.setAdapter(adapter_105);
                    electricityBoardSpinner.setSelection(adapter_105.getPosition(electricityBoardSpinnerValue));
                } else {
                    String new_str = "Board is empty";
                }
            }

            @Override
            public void onFailure(Call<List<public_board>> call2, Throwable t) {
                Log.i("login error: ", String.valueOf(t));
            }
        });

        spinner_rate.clear();
        spinner_rate.add("Rate structure");
        Retrofit retrofit4 = new Retrofit.Builder()
                .baseUrl("http://elshare.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final APIInterface service4 = retrofit4.create(APIInterface.class);
        Call<List<public_rate>> call4 = service4.getRateStructurePublic(electricityBoardSpinnerValue);
        call4.enqueue(new Callback<List<public_rate>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<List<public_rate>> call4, Response<List<public_rate>> response) {
                if (response.body() != null) {
                    Log.i("Residential Rate: ", String.valueOf(response.body()));
                    rate_m = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        my_rate = rate_m.get(i);
                        demo_str = my_rate.getRateStructurePublic();
                        spinner_rate.add(demo_str);
                    }
                    ArrayAdapter<String> adapter_106 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_rate);
                    rateStructureSpinner.setAdapter(adapter_106);
                    rateStructureSpinner.setSelection(adapter_106.getPosition(rateStructureSpinnerValue));
                } else {
                    String new_str = "Residential rate is empty";
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<List<public_rate>> call4, Throwable t) {
                Log.i("Residential rate error: ", String.valueOf(t));
            }
        });


        spinner_power_output.clear();
        spinner_power_output.add("Power output");
        Call<List<public_power_output>> call2 = service.getPowerOutputPublic(chargerBrandSpinnerValue,
                chargerModelSpinnerValue, electricityBoardSpinnerValue);
        Log.i("power Res*******************************************  : ", String.valueOf(call2.request().url()));
        call2.enqueue(new Callback<List<public_power_output>>() {
            @Override
            public void onResponse(Call<List<public_power_output>> call, Response<List<public_power_output>> response) {
                if (response.body() != null) {
                    power_com = response.body();

                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        power_c = power_com.get(i);
                        demo_str = power_c.getPowerOutputPublic();
                        spinner_power_output.add(demo_str);
                        Log.i("power output Array: ", spinner_power_output.toString());
                    }
                    ArrayAdapter<String> adapter_107 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_power_output);
                    powerOutputSpinner.setAdapter(adapter_107);
                    powerOutputSpinner.setSelection(adapter_107.getPosition(finalPowerOutputValue));
                } else {
                    String new_str = "Power Output is empty";
                    Log.i("Power output: ", new_str);
                }
            }

            @Override
            public void onFailure(Call<List<public_power_output>> call2, Throwable t) {
                Log.i("Pewer output Res error: ", String.valueOf(t));

            }
        });


        spinner_voltage.clear();
        spinner_voltage.add("Voltage");
        Call<List<public_voltage>> call6 = service.getVoltagePublic(chargerModelSpinnerValue,
                chargerBrandSpinnerValue, electricityBoardSpinnerValue);
        Log.i("Voltage*******************************  : ", String.valueOf(call6.request().url()));
        call6.enqueue(new Callback<List<public_voltage>>() {
            @Override
            public void onResponse(Call<List<public_voltage>> call6, Response<List<public_voltage>> response) {
                if (response.body() != null) {
                    volt_com = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        volt_c = volt_com.get(i);
                        demo_str = volt_c.getVoltagePublic();
                        spinner_voltage.add(demo_str);
                        Log.i("Voltage output Array: ", spinner_voltage.toString());
                    }
                    ArrayAdapter<String> adapter_108 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_voltage);
                    voltageSpinner.setAdapter(adapter_108);
                    voltageSpinner.setSelection(adapter_108.getPosition(voltageSpinnerValue));
                } else {
                    String new_str = "Voltage Output is empty";
                    Log.i("Voltage output: ", new_str);
                }
            }

            @Override
            public void onFailure(Call<List<public_voltage>> call6, Throwable t) {
                Log.i("Voltage output error: ", String.valueOf(t));
            }
        });

        charger_Amphere.clear();
        charger_Amphere.add("Amphere");
        Call<List<public_charger_amphere>> call77 = service.getChargerAmpherePublic(chargerBrandSpinnerValue,
                chargerModelSpinnerValue, electricityBoardSpinnerValue);
        Log.i("Amphere**************************************  : ", String.valueOf(call6.request().url()));
        call77.enqueue(new Callback<List<public_charger_amphere>>() {
            @Override
            public void onResponse(Call<List<public_charger_amphere>> call7, Response<List<public_charger_amphere>> response) {
                if (response.body() != null) {
                    charger_amp_array = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        charger_amp = charger_amp_array.get(i);
                        demo_str = charger_amp.getChargerAmpherePublic();
                        charger_Amphere.add(demo_str);
                        Log.i("Amphere output Array: ", charger_Amphere.toString());
                    }
                    ArrayAdapter<String> adapter_C_A = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, charger_Amphere);
                    chargerAmphereSpinner.setAdapter(adapter_C_A);
                    chargerAmphereSpinner.setSelection(adapter_C_A.getPosition(amphereSpinnerValue));
                } else {
                    String new_str = "Amphere Output is empty";
                    Log.i("Amphere output: ", new_str);
                }
            }

            @Override
            public void onFailure(Call<List<public_charger_amphere>> call7, Throwable t) {
                Log.i("Amphere output error: ", String.valueOf(t));
            }
        });


        spinner_socket.clear();
        spinner_socket.add("Select socket");
        APIInterface service1 = SingletonRetrofit.getAPIInterface();

        Call<List<Socket_public>> call1 = service1.getSocketPublic();
        Log.i("Socket***********************************  : ", String.valueOf(call.request().url()));
        call1.enqueue(new Callback<List<Socket_public>>() {
            @Override
            public void onResponse(Call<List<Socket_public>> call, Response<List<Socket_public>> response) {
                if (response.body() != null) {
                    socket_set = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        my_socket = socket_set.get(i);
                        demo_str = my_socket.getSocketPublic();
                        spinner_socket.add(demo_str);
                    }
                    ArrayAdapter<String> adapter_109 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, spinner_socket);
                    socketSpinner.setAdapter(adapter_109);
                    socketSpinner.setSelection(adapter_109.getPosition(socketSpinnerValue));
                } else {
                    String new_str = "charger is empty";
                    Log.i("login Array: ", new_str);
                }
            }

            @Override
            public void onFailure(Call<List<Socket_public>> call, Throwable t) {
                Log.i("login error: ", String.valueOf(t));
            }
        });


        // Select socket board---------------------------------------------
        socket_eboard.clear();
        socket_eboard.add("Select board");
        APIInterface service55 = SingletonRetrofit.getAPIInterface();
        Call<List<public_board_socket>> call55 = service55.getElectricityBoardSocketPublic(socketElectricityBoardSpinnerValue);
        call55.enqueue(new Callback<List<public_board_socket>>() {
            @Override
            public void onResponse(Call<List<public_board_socket>> call5, Response<List<public_board_socket>> response) {
                if (response.body() != null) {
                    Log.i("socket board response: ", String.valueOf(response.body()));
                    socket_board_res = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        socket_board = socket_board_res.get(i);
                        demo_str = socket_board.getElectricityBoardSocketPublic();
                        Log.i("socket board response: ", demo_str);
                        socket_eboard.add(demo_str);
                        Log.i("Socket board Array: ", socket_eboard.toString());
                    }
                    ArrayAdapter<String> adapter_111 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, socket_eboard);
                    socketElectricityBoardSpinner.setAdapter(adapter_111);
                    socketElectricityBoardSpinner.setSelection(adapter_111.getPosition(socketElectricityBoardSpinnerValue));
                } else {
                    String new_str = "Socket board is empty";
                }
            }

            @Override
            public void onFailure(Call<List<public_board_socket>> call5, Throwable t) {
                Log.i("Socket board error: ", String.valueOf(t));
            }
        });


        //Select rate of socket------------------------------------------
        socket_rate_array.clear();
        socket_rate_array.add("select socket rate");
        APIInterface service44 = SingletonRetrofit.getAPIInterface();
        Call<List<public_rate_socket>> call44 = service44.getRateStructureSocketPublic(socketElectricityBoardSpinnerValue);
        call44.enqueue(new Callback<List<public_rate_socket>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<List<public_rate_socket>> call4, Response<List<public_rate_socket>> response) {
                if (response.body() != null) {
                    Log.i("Residential socket Rate: ", String.valueOf(response.body()));
                    socket_rate_res = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        rate_socket = socket_rate_res.get(i);
                        demo_str = rate_socket.getRateStructureSocketPublic();
                        Log.i("Residential socket Rate R: ", String.valueOf(demo_str));
                        socket_rate_array.add(demo_str);
                        Log.i("Residential socket rate Array: ", socket_rate_array.toString());
                    }
                    ArrayAdapter<String> adapter_112 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, socket_rate_array);
                    socketRateSpinner.setAdapter(adapter_112);
                    socketRateSpinner.setSelection(adapter_112.getPosition(socketRateSpinnerValue));
                } else {
                    String new_str = "Residential socket rate is empty";
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<List<public_rate_socket>> call4, Throwable t) {
                Log.i("Residential socket rate error: ", String.valueOf(t));
            }
        });


        socket_voltage.clear();
        socket_voltage.add("socket voltage");
        Call<List<public_voltage_socket>> call66 = service.getVoltageSocketPublic(socketSpinnerValue, electricityBoardSpinnerValue);
        call66.enqueue(new Callback<List<public_voltage_socket>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<List<public_voltage_socket>> call4, Response<List<public_voltage_socket>> response) {
                if (response.body() != null) {
                    Log.i("Residential socket vtg: ", String.valueOf(response.body()));
                    socket_vtg_res = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        socket_vtg = socket_vtg_res.get(i);
                        demo_str = socket_vtg.getVoltageSocketPublic();
                        Log.i("Residential socket vtg R: ", String.valueOf(demo_str));
                        socket_voltage.add(demo_str);
                        Log.i("Residential socket vtg Array: ", socket_voltage.toString());
                    }

                    ArrayAdapter<String> adapter_110 = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, socket_voltage);
                    socketVoltageSpinner.setAdapter(adapter_110);
                    socketVoltageSpinner.setSelection(adapter_110.getPosition(socketVoltageSpinnerValue));
                } else {
                    String new_str = "Residential socket vtg is empty";
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<List<public_voltage_socket>> call4, Throwable t) {
                Log.i("Residential socket vtg error: ", String.valueOf(t));
            }
        });


        // Select amphere of socket------------------------------------------
        socket_amphere.clear();
        socket_amphere.add("socket Amphere");
        Call<List<public_socket_amphere>> call22 = service.getSocketAmpherePublic(socketSpinnerValue, socketElectricityBoardSpinnerValue);
        call22.enqueue(new Callback<List<public_socket_amphere>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onResponse(Call<List<public_socket_amphere>> call2, Response<List<public_socket_amphere>> response) {
                if (response.body() != null) {
                    Log.i("Residential socket amp: ", String.valueOf(response.body()));
                    socket_amp_array = response.body();
                    ArrayList make_array = (ArrayList) response.body();
                    String demo_str = null;
                    int i;
                    for (i = 0; i < make_array.size(); i++) {
                        socket_amp = socket_amp_array.get(i);
                        demo_str = socket_amp.getSocketAmpherePublic();
                        Log.i("Residential socket amp R: ", String.valueOf(demo_str));
                        socket_amphere.add(demo_str);
                        Log.i("Residential socket amp Array: ", socket_amphere.toString());
                    }
                    ArrayAdapter<String> adapter_A_S = new ArrayAdapter<>(EditPublicListingActivity.this, R.layout.sample_text, socket_amphere);
                    socketAmphereSpinner.setAdapter(adapter_A_S);
                    socketAmphereSpinner.setSelection(adapter_A_S.getPosition(socketAmphereSpinnerValue));
                } else {
                    String new_str = "Residential socket amp is empty";
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<List<public_socket_amphere>> call2, Throwable t) {
                Log.i("Residential socket amp error: ", String.valueOf(t));
            }
        });

    }

    public void showTextNotification(ArrayList<String> stringItemSelectedList) {
        StringBuilder stringBuilder = new StringBuilder("Selected Days : ");
        for (String singDay : stringItemSelectedList) {
            stringBuilder.append(", " + singDay);
        }
        Toast.makeText(this, stringBuilder.toString(), Toast.LENGTH_SHORT).show();
    }

    public void deleteRowInfo() {
        if (tableLayout.getChildCount() > 0) {
            ArrayList<String> multiDayStartTimeList1 = new ArrayList<>();
            ArrayList<String> multiDayEndTimeList1 = new ArrayList<>();
            for (int itemIndex = 0; itemIndex < tableLayout.getChildCount(); itemIndex++) {
                TableRow tableRow = (TableRow) tableLayout.getChildAt(itemIndex);
                EditText startTimeEditText = tableRow.findViewById(R.id.startTimeEditText);
                EditText endTimeEditText = tableRow.findViewById(R.id.endTimeEditText);
                if (startTimeEditText.getText() != null && !TextUtils.isEmpty(startTimeEditText.getText().toString())) {
                    multiDayStartTimeList1.add(startTimeEditText.getText().toString());
                }
                if (endTimeEditText.getText() != null && !TextUtils.isEmpty(endTimeEditText.getText().toString())) {
                    multiDayEndTimeList1.add(endTimeEditText.getText().toString());
                }
            }
            multiDayStartTimeList = multiDayStartTimeList1;
            multiDayEndTimeList = multiDayEndTimeList1;
        } else {
            multiDayStartTimeList.clear();
            multiDayEndTimeList.clear();
        }
        showTextNotification(multiDayStartTimeList);
        showTextNotification(multiDayEndTimeList);
    }

    public void deleteSingleDayRowInfo(TableLayout currentDayTableLayout, ArrayList<String> currentDayStartTimeList,
                                       ArrayList<String> currentDayEndTimeList) {
        if (currentDayTableLayout.getChildCount() > 0) {
            currentDayStartTimeList.clear();
            currentDayEndTimeList.clear();
            for (int itemIndex = 0; itemIndex < currentDayTableLayout.getChildCount(); itemIndex++) {
                TableRow tableRow = (TableRow) currentDayTableLayout.getChildAt(itemIndex);
                EditText startTimeEditText = tableRow.findViewById(R.id.startTimeEditText);
                EditText endTimeEditText = tableRow.findViewById(R.id.endTimeEditText);
                if (startTimeEditText.getText() != null && !TextUtils.isEmpty(startTimeEditText.getText().toString())) {
                    currentDayStartTimeList.add(startTimeEditText.getText().toString());
                }
                if (endTimeEditText.getText() != null && !TextUtils.isEmpty(endTimeEditText.getText().toString())) {
                    currentDayEndTimeList.add(endTimeEditText.getText().toString());
                }
            }
        } else {
            currentDayStartTimeList.clear();
            currentDayEndTimeList.clear();
        }
        showTextNotification(currentDayStartTimeList);
        showTextNotification(currentDayEndTimeList);
    }

    public void handle24HoursCheckBoxClick(CompoundButton buttonView, boolean isChecked,
                                           ArrayList<String> currentDayStartTimeList, ArrayList<String> currentDayEndTimeList,
                                           TableLayout currentDayTableLayout, Button currentDayAddTimeBlock) {
        currentDayStartTimeList.clear();
        currentDayEndTimeList.clear();
        currentDayTableLayout.removeAllViews();
        if (isChecked) {
            currentDayAddTimeBlock.setVisibility(View.GONE);
            final TableRow row = (TableRow) LayoutInflater.from(buttonView.getContext()).inflate(R.layout.start_and_end_time_row, null);
            final EditText startTimeEditText = row.findViewById(R.id.startTimeEditText);
            final EditText endTimeEditText = row.findViewById(R.id.endTimeEditText);
            final ImageButton deleteImageButton = row.findViewById(R.id.deleteTime);
            deleteImageButton.setVisibility(View.GONE);
            startTimeEditText.setText("12:00 AM");
            endTimeEditText.setText("12:00 AM");
            currentDayStartTimeList.add("12:00 AM");
            currentDayEndTimeList.add("12:00 AM");
            currentDayTableLayout.addView(row);
        } else {
            currentDayAddTimeBlock.setVisibility(View.VISIBLE);
        }
        showTextNotification(currentDayStartTimeList);
        showTextNotification(currentDayEndTimeList);
    }

    public void handleSingleDayAddAnotherTimeBlock(View view, final CheckBox currentDay24HoursCheckBox,
                                                   final ArrayList<String> singleDaySelectedList,
                                                   final TableLayout currentDayTableLayout,
                                                   final ArrayList<String> currentDayStartTimeList,
                                                   final ArrayList<String> currentDayEndTimeList) {
        currentDay24HoursCheckBox.setVisibility(View.GONE);
        final TableRow row = (TableRow) LayoutInflater.from(view.getContext()).inflate(R.layout.start_and_end_time_row, null);
        final EditText startTimeEditText = row.findViewById(R.id.startTimeEditText);
        final EditText endTimeEditText = row.findViewById(R.id.endTimeEditText);
        ImageButton delete_row = row.findViewById(R.id.deleteTime);

        if (isDefaultTime) {
            startTimeEditText.setText(currentDayStartTimeList.get(currentDayStartTimeList.size() - 1));
            endTimeEditText.setText(currentDayEndTimeList.get(currentDayEndTimeList.size() - 1));
        } else {
            startTimeEditText.setText("8:00 AM");
            endTimeEditText.setText("8:00 PM");
        }

        isDefaultTime = false;
        currentDayStartTimeList.add(startTimeEditText.getText().toString());
        currentDayEndTimeList.add(endTimeEditText.getText().toString());

        TimePickerUniversal timePickerUniversal1 = new TimePickerUniversal();
        TimePickerUniversal timePickerUniversal2 = new TimePickerUniversal();
        timePickerUniversal1.setTimePickerUniversal(startTimeEditText, true);
        timePickerUniversal2.setTimePickerUniversal(endTimeEditText, true);

        startTimeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentDayStartTimeList.remove(startTimeEditText.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentDayStartTimeList.add(startTimeEditText.getText().toString());
                showTextNotification(currentDayStartTimeList);
            }
        });

        endTimeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                currentDayEndTimeList.remove(endTimeEditText.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentDayEndTimeList.add(endTimeEditText.getText().toString());
                showTextNotification(currentDayEndTimeList);
            }
        });

        delete_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // row is your row, the parent of the clicked button
                View row = (View) v.getParent();
                // container contains all the rows, you could keep a variable somewhere else to the container which you can refer to here
                ViewGroup container = ((ViewGroup) row.getParent());
                // delete the row and invalidate your view so it gets redrawn
                container.removeView(row);
                Log.i("Index=", String.valueOf(v.getTag()));
                container.invalidate();
                //deleteSingleDayRowInfo(currentDayTableLayout, currentDayStartTimeList, currentDayEndTimeList);
                if (currentDayTableLayout.getChildCount() == 0) {
                    currentDay24HoursCheckBox.setVisibility(View.VISIBLE);
                }
            }
        });

        //add your new row to the TableLayout:
        currentDayTableLayout.addView(row);
    }

    public void setDefaultDaysAndTimeSelection() {
        if (defaultSelectedDaysList != null && !defaultSelectedDaysList.isEmpty()) {
            if (checked == 1) {
                singleDayTableLayout.setVisibility(View.VISIBLE);
                multiDayTableLayout.setVisibility(View.GONE);
            } else {
                singleDayTableLayout.setVisibility(View.GONE);
                multiDayTableLayout.setVisibility(View.VISIBLE);
            }

            if (checked == 0 && show_resi_arry.getStartTime() != null
                    && show_resi_arry.getStartTime().size() > 0) {
                for (int index = 0; index < show_resi_arry.getStartTime().size(); index++) {
                    isDefaultTime = true;
                    multiDayStartTimeList.add((String) show_resi_arry.getStartTime().get(index));
                    multiDayEndTimeList.add((String) show_resi_arry.getEndTime().get(index));
                    multiDayAddTimeBlock.callOnClick();
                }
            }

            for (String day : defaultSelectedDaysList) {
                if (checked == 1) {
                    singleDaySelectedList.add(day);
                } else {
                    multidaysSelectedList.add(day);
                }
                if (checked == 0) {
                    if ("monday".equalsIgnoreCase(day)) {
                        mondayCheckBox.setChecked(true);
                    } else if ("tuesday".equalsIgnoreCase(day)) {
                        tuesdayCheckBox.setChecked(true);
                    } else if ("wednesday".equalsIgnoreCase(day)) {
                        wednesdayCheckBox.setChecked(true);
                    } else if ("thursday".equalsIgnoreCase(day)) {
                        thursdayCheckBox.setChecked(true);
                    } else if ("friday".equalsIgnoreCase(day)) {
                        fridayCheckBox.setChecked(true);
                    } else if ("saturday".equalsIgnoreCase(day)) {
                        saturdayCheckBox.setChecked(true);
                    } else if ("sunday".equalsIgnoreCase(day)) {
                        sundayCheckBox.setChecked(true);
                    }

                } else {
                    if ("monday".equalsIgnoreCase(day)) {
                        showSingleDayDefaultTime(singleMondayCheckBox, show_resi_arry.getStartTimeMon(),
                                show_resi_arry.getEndTimeMon(), mondayStartTimeList, mondayEndTimeList, singleMondayAddTimeBlock);
                    } else if ("tuesday".equalsIgnoreCase(day)) {
                        showSingleDayDefaultTime(singleTuesdayCheckBox, show_resi_arry.getStartTimeTues(),
                                show_resi_arry.getEndTimeTues(), tuesdayStartTimeList, tuesdayEndTimeList, singleTuesdayAddTimeBlock);
                    } else if ("wednesday".equalsIgnoreCase(day)) {
                        showSingleDayDefaultTime(singleWednesdayCheckBox, show_resi_arry.getStartTimeWed(),
                                show_resi_arry.getEndTimeWed(), wednesdayStartTimeList, wednesdayEndTimeList, singleWednesdayAddTimeBlock);
                    } else if ("thursday".equalsIgnoreCase(day)) {
                        showSingleDayDefaultTime(singleThursdayCheckBox, show_resi_arry.getStartTimeThus(),
                                show_resi_arry.getEndTimeThus(), thursdayStartTimeList, thursdayEndTimeList, singleThursdayAddTimeBlock);
                    } else if ("friday".equalsIgnoreCase(day)) {
                        showSingleDayDefaultTime(singleFridayCheckBox, show_resi_arry.getStartTimeFri(),
                                show_resi_arry.getEndTimeFri(), fridayStartTimeList, fridayEndTimeList, singleFridayAddTimeBlock);
                    } else if ("saturday".equalsIgnoreCase(day)) {
                        showSingleDayDefaultTime(singleSaturdayCheckBox, show_resi_arry.getStartTimeSat(),
                                show_resi_arry.getEndTimeSat(), saturdayStartTimeList, saturdayEndTimeList, singleSaturdayAddTimeBlock);
                    } else if ("sunday".equalsIgnoreCase(day)) {
                        showSingleDayDefaultTime(singleSundayCheckBox, show_resi_arry.getStartTimeSun(),
                                show_resi_arry.getEndTimeSun(), sundayStartTimeList, sundayEndTimeList, singleSundayAddTimeBlock);
                    }
                }
            }
        }
    }

    public void showSingleDayDefaultTime(CheckBox singleDayCheckBox, List<String> defaultSelectedStartTimeList,
                                         List<String> defaultSelectedEndTimeList,
                                         ArrayList<String> currentDayStartTimeList, ArrayList<String> currentDayEndTimeList,
                                         Button currentDayAddTimeBlock) {
        singleDayCheckBox.setChecked(true);
        if (defaultSelectedStartTimeList != null && defaultSelectedStartTimeList.size() > 0) {
            for (int index = 0; index < defaultSelectedStartTimeList.size(); index++) {
                isDefaultTime = true;
                currentDayStartTimeList.add(defaultSelectedStartTimeList.get(index));
                currentDayEndTimeList.add((defaultSelectedEndTimeList.get(index)));
                currentDayAddTimeBlock.callOnClick();
            }
        }
    }

    public class mCheckBoxChangeListener implements CheckBox.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (buttonView == mondayCheckBox) {
                if (isChecked) {
                    multidaysSelectedList.add("monday");
                } else {
                    multidaysSelectedList.remove("monday");
                }
            }

            if (buttonView == tuesdayCheckBox) {
                if (isChecked) {
                    multidaysSelectedList.add("tuesday");
                } else {
                    multidaysSelectedList.remove("tuesday");
                }
            }

            if (buttonView == wednesdayCheckBox) {
                if (isChecked) {
                    multidaysSelectedList.add("wednesday");
                } else {
                    multidaysSelectedList.remove("wednesday");
                }
            }

            if (buttonView == thursdayCheckBox) {
                if (isChecked) {
                    multidaysSelectedList.add("thursday");
                } else {
                    multidaysSelectedList.remove("thursday");
                }
            }

            if (buttonView == fridayCheckBox) {
                if (isChecked) {
                    multidaysSelectedList.add("friday");
                } else {
                    multidaysSelectedList.remove("friday");
                }
            }

            if (buttonView == saturdayCheckBox) {
                if (isChecked) {
                    multidaysSelectedList.add("saturday");
                } else {
                    multidaysSelectedList.remove("saturday");
                }
            }

            if (buttonView == sundayCheckBox) {
                if (isChecked) {
                    multidaysSelectedList.add("sunday");
                } else {
                    multidaysSelectedList.remove("sunday");
                }
            }

            showTextNotification(multidaysSelectedList);
        }
    }

    public class mSingleCheckBoxChangeListener implements CheckBox.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (buttonView == singleMondayCheckBox) {
                if (isChecked) {
                    singleDaySelectedList.add("monday");
                } else {
                    singleDaySelectedList.remove("monday");
                }
            }

            if (buttonView == singleTuesdayCheckBox) {
                if (isChecked) {
                    singleDaySelectedList.add("tuesday");
                } else {
                    singleDaySelectedList.remove("tuesday");
                }
            }

            if (buttonView == singleWednesdayCheckBox) {
                if (isChecked) {
                    singleDaySelectedList.add("wednesday");
                } else {
                    singleDaySelectedList.remove("wednesday");
                }
            }

            if (buttonView == singleThursdayCheckBox) {
                if (isChecked) {
                    singleDaySelectedList.add("thursday");
                } else {
                    singleDaySelectedList.remove("thursday");
                }
            }

            if (buttonView == singleFridayCheckBox) {
                if (isChecked) {
                    singleDaySelectedList.add("friday");
                } else {
                    singleDaySelectedList.remove("friday");
                }
            }

            if (buttonView == singleSaturdayCheckBox) {
                if (isChecked) {
                    singleDaySelectedList.add("saturday");
                } else {
                    singleDaySelectedList.remove("saturday");
                }
            }

            if (buttonView == singleSundayCheckBox) {
                if (isChecked) {
                    singleDaySelectedList.add("sunday");
                } else {
                    singleDaySelectedList.remove("sunday");
                }
            }

            handleLayoutVisibility(buttonView);
            handleColorChange(buttonView);
            showTextNotification(singleDaySelectedList);
        }

        public void handleLayoutVisibility(CompoundButton buttonView) {
            for (int itemIndex = 0; itemIndex < singleDayCheckBoxArrayList.size(); itemIndex++) {
                CheckBox checkBox = singleDayCheckBoxArrayList.get(itemIndex);
                LinearLayout currentDayLinearLayout = singleDayLinearLayoutList.get(itemIndex);
                if (checkBox != buttonView) {
                    currentDayLinearLayout.setVisibility(View.GONE);
                } else {
                    currentDayLinearLayout.setVisibility(View.VISIBLE);
                }
            }
        }

        public void handleColorChange(CompoundButton buttonView) {
            int backgroundColorGrey = ContextCompat.getColor(getApplicationContext(), R.color.quantum_grey);
            int backgroundColorGreen = ContextCompat.getColor(getApplicationContext(), R.color.green);
            buttonView.setBackgroundColor(backgroundColorGreen);
            for (int itemIndex = 0; itemIndex < singleDayCheckBoxArrayList.size(); itemIndex++) {
                CheckBox checkBox = singleDayCheckBoxArrayList.get(itemIndex);
                int startTimeCount = singleDayStartTimeArrayList.get(itemIndex).size();
                if (checkBox != buttonView) {
                    if (startTimeCount == 0) {
                        checkBox.setBackgroundColor(backgroundColorGrey);
                    } else {
                        checkBox.setBackgroundColor(backgroundColorGreen);
                    }
                }
            }
        }
    }

    public void initializeAndSaveDayTime(TableLayout currentDayTableLayout, ArrayList<String> currentDayStartTimeList,
                                         ArrayList<String> currentDayEndTimeList) {
        if (currentDayTableLayout.getChildCount() > 0) {
            currentDayStartTimeList.clear();
            currentDayEndTimeList.clear();
            for (int itemIndex = 0; itemIndex < currentDayTableLayout.getChildCount(); itemIndex++) {
                TableRow tableRow = (TableRow) currentDayTableLayout.getChildAt(itemIndex);
                EditText startTimeEditText = tableRow.findViewById(R.id.startTimeEditText);
                EditText endTimeEditText = tableRow.findViewById(R.id.endTimeEditText);
                if (startTimeEditText.getText() != null && !TextUtils.isEmpty(startTimeEditText.getText().toString())) {
                    currentDayStartTimeList.add(startTimeEditText.getText().toString());
                }
                if (endTimeEditText.getText() != null && !TextUtils.isEmpty(endTimeEditText.getText().toString())) {
                    currentDayEndTimeList.add(endTimeEditText.getText().toString());
                }
            }
        } else {
            currentDayStartTimeList.clear();
            currentDayEndTimeList.clear();
        }
        showTextNotification(currentDayStartTimeList);
        showTextNotification(currentDayEndTimeList);
    }

    public void setFinalPowerOutput(float voltageValue, float amphereValue){
        //(voltage * amp)/1000 = mFinalPowerOutputTextView (final power output)
        mFinalPowerOutputTextView.setVisibility(View.VISIBLE);
        mFinalPowerOutputTextView.setTextColor(Color.parseColor("#62ba46"));
        float finalValue = (float) ((voltageValue * amphereValue) / 1000.0);
        if(powerOutputSpinner.getSelectedItemPosition() != 0){
            powerOutputSpinner.setSelection(0);
        }
        if(!"".equalsIgnoreCase(socketPowerOutputEditText.getText().toString())){
            socketPowerOutputEditText.setText("");
        }
        mFinalPowerOutputTextView.setText(""+ finalValue);
    }
}
