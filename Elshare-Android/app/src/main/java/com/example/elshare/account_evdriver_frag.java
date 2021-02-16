package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.elshare.utils.RequiredFieldUtil;
import com.example.elshare.utils.SingletonRetrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datamodel.APIInterface;
import datamodel.Make;
import datamodel.Model;
import datamodel.User;
import datamodel.UserId;
import datamodel.battery_size;
import datamodel.driver;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class account_evdriver_frag extends Fragment {
    private List<Make> make;
    private List<Model> model;
    private battery_size bat_size;
    String name;
    private Make my_make;
    private Model my_model;
    Button addDriver;
    Spinner Make_Spinner;
    Spinner Model_spinner;
    EditText addDerivceRegistrationNumber;
    private boolean isValidLicence;
    Boolean isMakeValid;
    Boolean isModelValid;
    Boolean isValidVehicl;
    Boolean isYearValid;
    private String makeString;
    String carType;
    String yearString;
    String model_str;
    String licn_str;
    String battery_str;
    String board_str;
    EditText mAddVehicleModel;
    TextView mAddVehicleBatterySize;
    EditText mAddDriverOnBoardCharger;
    Boolean emailVerify;

    @SuppressLint({"ResourceAsColor", "LongLogTag"})
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.account_evdriver_frag, container, false);
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        name = preferences.getString("Name", "null");
        if (name.equals("null")) {
            Toast.makeText(getContext(), "User not able to add driver without login .Please login !!", Toast.LENGTH_LONG).show();
        }
        else
            {

            Log.i("Call ID", name);
            checkEmailVerify();

            final ArrayList<String> spinner_make = new ArrayList<String>();
            final ArrayList<String> spinner_model = new ArrayList<String>();

            addDriver = rootView.findViewById(R.id.addDriver);
            Model_spinner = rootView.findViewById(R.id.Model_spinner);
            addDerivceRegistrationNumber = rootView.findViewById(R.id.addDerivceRegistrationNumber);
            mAddVehicleModel = rootView.findViewById(R.id.addVihicleYear);
            mAddVehicleBatterySize = rootView.findViewById(R.id.addVihicleBatterySize);
            mAddDriverOnBoardCharger = rootView.findViewById(R.id.addDeriverOnBoardSize);
            Model_spinner.setEnabled(false);
            Model_spinner.setVisibility(View.GONE);

            String[] array4 = {"Select Vehicle type", "2", "3", "4"};
            final Spinner vehicle_spinner = rootView.findViewById(R.id.spinner15);
            vehicle_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    String car_str = vehicle_spinner.getSelectedItem().toString();
                    if (vehicle_spinner.getSelectedItem().toString().trim().equals("Select Vehicle type")) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                        isValidVehicl = false;
                        spinner_make.add("Select Make");
                        Model_spinner.setVisibility(View.GONE);
                        ArrayAdapter<String> adapter_19 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_make);
                        Make_Spinner.setAdapter(adapter_19);
                    } else {
                        isValidVehicl = true;
                        Model_spinner.setVisibility(View.GONE);
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                    }
                    if (isValidVehicl) {
                        spinner_make.clear();
                        spinner_make.add("Select Make");
                        APIInterface service = SingletonRetrofit.getAPIInterface();

                        Call<List<Make>> call = service.createMake(car_str);
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
                                        spinner_make.add(demo_str);
                                        Log.i("Make Array: ", spinner_make.toString());
                                    }
                                } else {
                                    String new_str = "Make is empty";
                                    Log.i("Make: ", new_str);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Make>> call, Throwable t) {
                                Log.i("Make error: ", String.valueOf(t));
                                Toast.makeText(getContext(), "Unable to connect server.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        ArrayAdapter<String> adapter_19 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_make);
                        Make_Spinner.setAdapter(adapter_19);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ArrayAdapter<String> adapter_15 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, array4);
            vehicle_spinner.setAdapter(adapter_15);
            Make_Spinner = rootView.findViewById(R.id.Make_Spinner);
            Make_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final String model_str = Make_Spinner.getSelectedItem().toString();

                    if (Make_Spinner.getSelectedItem().toString().trim().equals("Select Make")) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                        isMakeValid = false;
                        Model_spinner.setVisibility(View.GONE);
                    } else {
                        isMakeValid = true;
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                        Model_spinner.setVisibility(View.VISIBLE);
                    }
                    if (isMakeValid) {
                        Log.i("Array===: ", Make_Spinner.getSelectedItem().toString());
                        spinner_model.clear();
                        spinner_model.add("Select Model");
                        APIInterface service = SingletonRetrofit.getAPIInterface();

                        Call<List<Model>> call = service.getModelData(model_str);
                        Log.i("Model response: ", String.valueOf(call));

                        call.enqueue(new Callback<List<Model>>() {
                            @Override
                            public void onResponse(Call<List<Model>> call, Response<List<Model>> response) {

                                if (response.body() != null) {
                                    Log.i("Model response: ", String.valueOf(response.body()));
                                    model = response.body();
                                    if (model != null) {
                                        Log.i("Model : ", String.valueOf(model));
                                        ArrayList model_array = (ArrayList) response.body();
                                        if (model_array != null) {
                                            String demo_model = null;
                                            int i;
                                            for (i = 0; i < model_array.size(); i++) {
                                                my_model = model.get(i);
                                                demo_model = my_model.getModel();
                                                Log.i("Model response: ", demo_model);
                                                spinner_model.add(demo_model);
                                                Log.i("Model Array: ", spinner_model.toString());
                                            }
                                        } else {
                                            Log.i("Model_str:", String.valueOf(model_array));
                                        }
                                    } else {
                                        Log.i("Model:", String.valueOf(model));
                                    }
                                } else {
                                    String new_str = "Model is empty";
                                    Log.i("Model: ", new_str);
                                    Toast.makeText(getContext(), "Please Select make", Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<List<Model>> call, Throwable t) {
                                Log.i("login error: ", String.valueOf(t));
                                Toast.makeText(getContext(), "Unable to connect server.", Toast.LENGTH_SHORT).show();
                            }
                        });
                        Model_spinner.setEnabled(true);
                        ArrayAdapter<String> adapter_18 = new ArrayAdapter<>(getActivity(), R.layout.sample_text, spinner_model);
                        Model_spinner.setAdapter(adapter_18);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String make_str = Make_Spinner.getSelectedItem().toString();
                    String model_str = Model_spinner.getSelectedItem().toString();

                    if (Model_spinner.getSelectedItem().toString().trim().equals("Select Model")) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#000000"));
                        isModelValid = false;
                    } else {
                        isModelValid = true;
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#62ba46"));
                    }
                    if (isModelValid && isMakeValid) {
                        APIInterface service = SingletonRetrofit.getAPIInterface();

                        Call<battery_size> call = service.getBatterySize(make_str, model_str);
                        Log.i("URL of battery:", String.valueOf(call.request().url()));
                        call.enqueue(new Callback<battery_size>() {
                            @Override
                            public void onResponse(Call<battery_size> call, Response<battery_size> response) {
                                if (response.body() != null) {
                                    Log.i("Make  response: ", String.valueOf(response.body()));

                                    bat_size = response.body();
                                    String demo_str = String.valueOf(bat_size.getBatteryCapacity());
                                    mAddVehicleBatterySize.setText(demo_str);
                                } else {
                                    String new_str = "Make is empty";
                                    Log.i("Make: ", new_str);
                                }
                            }

                            @Override
                            public void onFailure(Call<battery_size> call, Throwable t) {
                                Log.i("Make error: ", String.valueOf(t));
                                Toast.makeText(getContext(), "Unable to connect server.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //add all spinner
            final ArrayList<Spinner> spinnerArrayList = new ArrayList<>();
            spinnerArrayList.add(Make_Spinner);
            spinnerArrayList.add(Model_spinner);

            //add default value for each spinner in same sequence.
            final ArrayList<String> spinnerArrayDefaultValueList = new ArrayList<>();
            spinnerArrayDefaultValueList.add("Select Make");
            spinnerArrayDefaultValueList.add("Select Model");


            addDriver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (emailVerify)
                    {
                    if (RequiredFieldUtil.isRequiredSpinner(spinnerArrayDefaultValueList, spinnerArrayList)) {
                        Toast.makeText(getContext(), "The required drop down field can not be blank", Toast.LENGTH_LONG).show();
                        return;
                    }
                    ArrayList<EditText> editTextArrayList = new ArrayList<>();
                    editTextArrayList.add(mAddDriverOnBoardCharger);
                    editTextArrayList.add(mAddVehicleModel);
                    editTextArrayList.add(addDerivceRegistrationNumber);

                    if (RequiredFieldUtil.isRequiredFieldEmpty(editTextArrayList)) {
                        Toast.makeText(getContext(), "The required Field can not be blank", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (!isValidLicence(addDerivceRegistrationNumber.getText().toString())) {
                        addDerivceRegistrationNumber.setError(getResources().getString(R.string.error_invalid_licence));
                        isValidLicence = false;
                    } else {
                        isValidLicence = true;
                        int j = Integer.parseInt(mAddVehicleModel.getText().toString());
                        Log.i("J is:", String.valueOf(j));
                        if ((j > 2020) || (j < 2005)) {
                            mAddVehicleModel.setError(getResources().getString(R.string.error_model_year));
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
                                makeString = Make_Spinner.getSelectedItem().toString().trim();
                                carType = vehicle_spinner.getSelectedItem().toString().trim();
                                yearString = mAddVehicleModel.getText().toString();
                                model_str = Model_spinner.getSelectedItem().toString().trim();
                                licn_str = addDerivceRegistrationNumber.getText().toString().trim();
                                battery_str = mAddVehicleBatterySize.getText().toString();
                                board_str = mAddDriverOnBoardCharger.getText().toString().trim();
                                APIInterface service = SingletonRetrofit.getAPIInterface();

                                Call<ResponseBody> call = service.driver_add(i, makeString, model_str, yearString, licn_str, battery_str, board_str, carType);

                                Log.i("Call", String.valueOf(call.request().url()));
                                call.enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.body() != null) {

                                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                                            alertDialogBuilder.setMessage("Driver added successfully!!!");
                                            alertDialogBuilder.setPositiveButton("Ok",
                                                    new DialogInterface.OnClickListener() {

                                                        @Override
                                                        public void onClick(DialogInterface arg0, int arg1) {

                                                            Intent i = new Intent(getContext(), MyAllVehicle.class);
                                                            startActivity(i);
                                                            getActivity().finish();
                                                        }
                                                    });


                                            AlertDialog alertDialog = alertDialogBuilder.create();
                                            alertDialog.show();

                                        } else {

                                            Toast.makeText(getContext(), "Driver not able to add", Toast.LENGTH_LONG).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.i("Error", String.valueOf(t.getMessage()));
                                        Toast.makeText(getContext(), "Internal server error.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }
                }
                else {
                        Toast.makeText(getContext(), "Please verify your email address.Still user not able to submit a driver", Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

        return rootView;
    }

    private void checkEmailVerify() {
        APIInterface service = SingletonRetrofit.getAPIInterface();

        Call<UserId> call = service.getUserData(name);
        Log.i("Model response: ", String.valueOf(call));

        call.enqueue(new Callback<UserId>() {
            @Override
            public void onResponse(Call<UserId> call, Response<UserId> response) {

                if (response.body() != null) {
                    User user_data=response.body().getUser();
                    if (user_data!=null)
                    {
                        if (user_data.getEmailVerifiedAt()!=null)
                        {
                            emailVerify=true;
                        }
                        else
                        {
                            emailVerify=false;
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<UserId> call, Throwable t) {

            }


        });
    }


     private boolean isValidLicence(String toString) {
        Pattern pattern;
        Matcher matcher;
        String licen_pattern = "^([A-Z|a-z]{2}\\s{1}\\d{2}\\s{1}[A-Z|a-z]{1,2}\\s{1}\\d{1,4})?([A-Z|a-z]{3}\\s{1}\\d{1,4})?$";
        pattern = Pattern.compile(licen_pattern);
        matcher = pattern.matcher(addDerivceRegistrationNumber.getText().toString());
        return matcher.matches();
    }

    public void updateUI() {
    }

}
