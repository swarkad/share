package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.elshare.utils.RequiredFieldUtil;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datamodel.APIInterface;
import datamodel.Make;
import datamodel.Model;
import datamodel.ShowVehicleDetail;
import datamodel.battery_size;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEvDriverInformation extends AppCompatActivity {
    Toolbar mBackButton;
    Spinner mEditMake;
    Spinner mEditModel;
    Spinner mEditVehicleType;
    EditText mEditModelYear;
    EditText mEditLicencePlate;
    EditText mEditChargerSize;
    TextView mEditBatterySize;
    Button mEditChanges;
    String makeString;
    String modelString;
    String vehicleTypeString;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Integer vehicleId;

    private List<Make> make;
    private List<Model> model;
    private Make my_make;
    private Model my_model;
    private battery_size battery_size_string;

    Boolean isValidVehicl;
    Boolean isMakeValid;
    Boolean isModelValid;
    Boolean isValidLicence;
    Boolean isYearValid;

    ArrayList<String> array_make = new ArrayList<String>();
    ArrayList<String> array_model = new ArrayList<String>();
    ArrayList<String> spinnerArrayDefaultValueList = new ArrayList<>();
    ArrayList<Spinner> spinnerArrayList = new ArrayList<>();



    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ev_driver_information);

        preferences = PreferenceManager.getDefaultSharedPreferences((getApplicationContext()));
        editor=preferences.edit();
        vehicleId=preferences.getInt("VEHICLE_ID",0);

        mBackButton =  findViewById(R.id.editEvDriverBack);
        setSupportActionBar(mBackButton);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mBackButton.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mEditMake=findViewById(R.id.edit_Make_Spinner);
        mEditModel=findViewById(R.id.edit_Model_spinner);
        mEditVehicleType=findViewById(R.id.editvehicleType);
        mEditModelYear=findViewById(R.id.editVehicleYear);
        mEditLicencePlate=findViewById(R.id.editDerivceRegistrationNumber);
        mEditChargerSize=findViewById(R.id.editDeriverOnBoardSize);
        mEditBatterySize=findViewById(R.id.editVehicleBatterySize);
        mEditChanges=findViewById(R.id.editDriver);

        showallDetail();

        spinnerArrayList.add(mEditMake);
        spinnerArrayList.add(mEditModel);

        spinnerArrayDefaultValueList.add("Select Make");
        spinnerArrayDefaultValueList.add("Select Model");

        mEditChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editVehicleProfile(v);
            }
        });
    }

    @SuppressLint("LongLogTag")
    private void editVehicleProfile(final View view)
    {
        name = preferences.getString("Name", "");
        if (name.isEmpty()) {
            Log.i("User not able to add driver without login .Please login !!", "");
        }
        Log.i("Call ID", name);

        if(RequiredFieldUtil.isRequiredSpinner(spinnerArrayDefaultValueList, spinnerArrayList)){
            Toast.makeText(getApplicationContext(), "The required drop down field can not be blank", Toast.LENGTH_LONG).show();
            return;
        }

        ArrayList<EditText> editTextArrayList = new ArrayList<>();
        editTextArrayList.add(mEditModelYear);
        editTextArrayList.add(mEditLicencePlate);
        editTextArrayList.add(mEditChargerSize);

        if (!isValidLicence(mEditLicencePlate.getText().toString())) {
            mEditLicencePlate.setError(getResources().getString(R.string.error_invalid_licence));
            isValidLicence = false;
        }
        else {
            isValidLicence = true;
            int j = Integer.parseInt(mEditModelYear.getText().toString().trim());
            Log.i("J is:", String.valueOf(j));
            if ((j > 2020 ) || (j < 2005)) {
                mEditModelYear.setError(getResources().getString(R.string.error_model_year));
                isYearValid = false;
            } else {
                isYearValid = true;

                if (isValidLicence && isYearValid) {
                    int i = 0;
                    if (!TextUtils.isEmpty(name)) {
                        try {
                            i = Integer.parseInt(String.valueOf(name));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<ResponseBody> call = service.driverEdit(i,vehicleId, makeString, modelString, mEditModelYear.getText().toString(), mEditLicencePlate.getText().toString(), mEditBatterySize.getText().toString(), mEditChargerSize.getText().toString(), vehicleTypeString);

                    Log.i("Call", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() != null) {

                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                                alertDialogBuilder.setMessage("Vehicle edited  successfully!!!");
                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                Intent i = new Intent(view.getContext(), MyAllVehicle.class);
                                                startActivity(i);
                                                finish();
                                            }
                                        });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();

                            } else {
                                Toast.makeText(getApplicationContext(), "Driver not able to add", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("Error", String.valueOf(t.getMessage()));
                            Toast.makeText(getApplicationContext(), "Internal server error.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
    }

    private boolean isValidLicence(String toString) {
        Pattern pattern;
        Matcher matcher;
//        String licen_pattern = "^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])(?: [A-Z]*)? [0-9]{4}$";
        String licen_pattern = "^([A-Z|a-z]{2}\\s{1}\\d{2}\\s{1}[A-Z|a-z]{1,2}\\s{1}\\d{1,4})?([A-Z|a-z]{3}\\s{1}\\d{1,4})?$";

        pattern = Pattern.compile(licen_pattern);
        matcher = pattern.matcher(mEditLicencePlate.getText().toString());
        return matcher.matches();
    }

    private void showallDetail()
    {
        final APIInterface service = SingletonRetrofit.getAPIInterface();
        Call<ShowVehicleDetail> call=service.getVehicleDetail(vehicleId);
        Log.i("Book url:",String.valueOf(call.request().url()));
        call.enqueue(new Callback<ShowVehicleDetail>() {
            @Override
            public void onResponse(Call<ShowVehicleDetail> call, Response<ShowVehicleDetail> response) {
                if (response.body() != null) {
                    ShowVehicleDetail showVehicleDetail=response.body();
                    vehicleTypeString=(String.valueOf(showVehicleDetail.getType()));
                    makeString=(showVehicleDetail.getMake());
                    modelString=(showVehicleDetail.getModel());
                    initDropDownField();
                    mEditLicencePlate.setText(String.valueOf(showVehicleDetail.getLicence()));
                    mEditModelYear.setText(String.valueOf(showVehicleDetail.getModelYear()));
                    mEditBatterySize.setText(String.valueOf(showVehicleDetail.getBatterySize()));
                    mEditChargerSize.setText(String.valueOf(showVehicleDetail.getChargerSize()));

                }
            }
            @Override
            public void onFailure(Call<ShowVehicleDetail> call, Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initDropDownField()
    {
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("Select Vehicle type");
        arrayList1.add("2");
        arrayList1.add("3");
        arrayList1.add("4");

        ArrayAdapter<String> adapter101 = new ArrayAdapter<String>(EditEvDriverInformation.this, R.layout.sample_text, arrayList1);
        mEditVehicleType.setAdapter(adapter101);
        mEditVehicleType.setSelection(adapter101.getPosition(vehicleTypeString));
        mEditVehicleType.setVisibility(View.VISIBLE);

        initSpinnerApi();
    }

    private void initSpinnerApi()
    {
        mEditVehicleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                vehicleTypeString = mEditVehicleType.getSelectedItem().toString();
                if (mEditVehicleType.getSelectedItem().toString().trim().equals("Select Vehicle type")) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                    isValidVehicl = false;
                    array_make.add("Select Make");
                    mEditModel.setVisibility(View.GONE);
                    ArrayAdapter<String> adapter_19 = new ArrayAdapter<>(getApplicationContext(), R.layout.sample_text, array_make);
                    mEditMake.setAdapter(adapter_19);
                } else {
                    isValidVehicl = true;
                    mEditModel.setVisibility(View.GONE);
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                if (isValidVehicl) {
                    array_make.clear();
                    array_make.add("Select Make");
                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<Make>> call = service.createMake(vehicleTypeString);
                    call.enqueue(new Callback<List<Make>>() {
                        @Override
                        public void onResponse(Call<List<Make>> call, Response<List<Make>> response) {
                            if (response.body() != null) {
                                Log.i("Make  response: ", String.valueOf(response.body()));
                                make = response.body();

                                ArrayList make_array = (ArrayList) response.body();
                                String demo_str = null;
                                int i;
                                for (i = 0; i < make_array.size(); i++) {
                                    my_make = make.get(i);
                                    demo_str = my_make.getMy_make();
                                    Log.i("Make response: ", demo_str);
                                    array_make.add(demo_str);
                                    Log.i("Make Array: ", array_make.toString());
                                }
                                ArrayAdapter<String> adapterMake = new ArrayAdapter<String>(EditEvDriverInformation.this, R.layout.sample_text, array_make);
                                mEditMake.setAdapter(adapterMake);
                                mEditMake.setSelection(adapterMake.getPosition(makeString));
                                mEditMake.setVisibility(View.VISIBLE);
                            } else {
                                String new_str = "Make is empty";
                                Log.i("Make: ", new_str);
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Make>> call, Throwable t) {
                            Log.i("Make error: ", String.valueOf(t));
                            Toast.makeText(getApplicationContext(), "Unable to connect server.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mEditMake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                makeString = mEditMake.getSelectedItem().toString();

                if (mEditMake.getSelectedItem().toString().trim().equals("Select Make")) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                    isMakeValid = false;
                    mEditModel.setVisibility(View.GONE);
                } else {
                    isMakeValid = true;
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                    mEditModel.setVisibility(View.VISIBLE);
                }
                if (isMakeValid) {
                    array_model.clear();
                    array_model.add("Select Model");
                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<List<Model>> call = service.getModelData(makeString);
                    Log.i("Model response: ", String.valueOf(call));

                    call.enqueue(new Callback<List<Model>>() {
                        @Override
                        public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {

                            if (response.body() != null) {
                                Log.i("Model response: ", String.valueOf(response.body()));
                                model = response.body();
                                Log.i("Model : ", String.valueOf(model));
                                ArrayList model_array = (ArrayList) response.body();

                                String demo_model = null;
                                int i;
                                for (i = 0; i < model_array.size(); i++) {
                                    my_model = model.get(i);
                                    demo_model = my_model.getModel();
                                    Log.i("Model response: ", demo_model);
                                    array_model.add(demo_model);
                                    Log.i("Model Array: ", array_model.toString());
                                }
                                ArrayAdapter<String> adapterMake = new ArrayAdapter<String>(EditEvDriverInformation.this, R.layout.sample_text, array_model);
                                mEditModel.setAdapter(adapterMake);
                                mEditModel.setSelection(adapterMake.getPosition(modelString));
                                mEditModel.setVisibility(View.VISIBLE);
                            } else {

                                Toast.makeText(getApplicationContext(), "Please Select make", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<List<Model>> call, Throwable t) {
                            Log.i("Model error: ", String.valueOf(t));
                            Toast.makeText(getApplicationContext(), "Unable to connect server.", Toast.LENGTH_SHORT).show();
                        }
                    });

//                    mEditModel.setEnabled(true);
//                    ArrayAdapter<String> adapter_18 = new ArrayAdapter<>(getApplicationContext(), R.layout.sample_text, spinner_model);
//                    mEditModel.setAdapter(adapter_18);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mEditModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                makeString = mEditMake.getSelectedItem().toString();
                modelString = mEditModel.getSelectedItem().toString();

                if (mEditModel.getSelectedItem().toString().trim().equals("Select Model")) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                    isModelValid = false;
                } else {
                    isModelValid = true;
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                }
                if (isModelValid && isMakeValid) {
                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<battery_size> call = service.getBatterySize(makeString, modelString);
                    Log.i("URL of battery:", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<battery_size>() {
                        @Override
                        public void onResponse(Call<battery_size> call, Response<battery_size> response) {
                            if (response.body() != null) {
                                Log.i("Model  response: ", String.valueOf(response.body()));

                                battery_size_string = response.body();
                                String demo_str = String.valueOf(battery_size_string.getBatteryCapacity());
                                mEditBatterySize.setText(demo_str);
                            } else {
                                Toast.makeText(getApplicationContext(), "No battery size available.", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<battery_size> call, Throwable t) {
                            Log.i("battery size error: ", String.valueOf(t));
                            Toast.makeText(getApplicationContext(), "Unable to connect server.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}