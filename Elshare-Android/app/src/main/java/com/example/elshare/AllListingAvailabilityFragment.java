package com.example.elshare;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.elshare.utils.TimePickerUniversal;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllListingAvailabilityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllListingAvailabilityFragment extends Fragment {

    ImageButton delete_row;
    int checked;
    int selected;
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

    Switch instant_booking;
    Switch multi_single_day;
    LinearLayout multiDayTableLayout;
    LinearLayout singleDayTableLayout;

    public AllListingAvailabilityFragment() {
        // Required empty public constructor
    }

   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_all_listing_availability, container, false);
        multiDayAddTimeBlock = rootView.findViewById(R.id.add_another_Block_resi);
        singleMondayAddTimeBlock = rootView.findViewById(R.id.singleMondayTimeBlock);
        singleTuesdayAddTimeBlock = rootView.findViewById(R.id.singleTuesdayTimeBlock);
        singleWednesdayAddTimeBlock = rootView.findViewById(R.id.singleWednesdayTimeBlock);
        singleThursdayAddTimeBlock = rootView.findViewById(R.id.singleThursdayTimeBlock);
        singleFridayAddTimeBlock = rootView.findViewById(R.id.singleFridayTimeBlock);
        singleSaturdayAddTimeBlock = rootView.findViewById(R.id.singleSaturdayTimeBlock);
        singleSundayAddTimeBlock = rootView.findViewById(R.id.singleSundayTimeBlock);

        multiDay24HoursCheckBox = rootView.findViewById(R.id.multiDay24HoursCheckBox);
        singleMonday24HoursCheckBox = rootView.findViewById(R.id.singleMonday24HoursCheckBox);
        singleTuesday24HoursCheckBox = rootView.findViewById(R.id.singleTuesday24HoursCheckBox);
        singleWednesday24HoursCheckBox = rootView.findViewById(R.id.singleWednesday24HoursCheckBox);
        singleThursday24HoursCheckBox = rootView.findViewById(R.id.singleThursday24HoursCheckBox);
        singleFriday24HoursCheckBox = rootView.findViewById(R.id.singleFriday24HoursCheckBox);
        singleSaturday24HoursCheckBox = rootView.findViewById(R.id.singleSaturday24HoursCheckBox);
        singleSunday24HoursCheckBox = rootView.findViewById(R.id.singleSunday24HoursCheckBox);


        mondayCheckBox = rootView.findViewById(R.id.mondayCheckBox);
        tuesdayCheckBox = rootView.findViewById(R.id.tuesdayCheckBox);
        wednesdayCheckBox = rootView.findViewById(R.id.wednesdayCheckBox);
        thursdayCheckBox = rootView.findViewById(R.id.thursdayCheckBox);
        fridayCheckBox = rootView.findViewById(R.id.fridayCheckBox);
        saturdayCheckBox = rootView.findViewById(R.id.saturdayCheckBox);
        sundayCheckBox = rootView.findViewById(R.id.sundayCheckBox);
        tableLayout = rootView.findViewById(R.id.table_resi_edit);
        mondayCheckBox.setChecked(false);
        tuesdayCheckBox.setChecked(false);
        wednesdayCheckBox.setChecked(false);
        thursdayCheckBox.setChecked(false);
        fridayCheckBox.setChecked(false);
        saturdayCheckBox.setChecked(false);
        sundayCheckBox.setChecked(false);

        mondayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mCheckBoxChangeListener());
        tuesdayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mCheckBoxChangeListener());
        wednesdayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mCheckBoxChangeListener());
        thursdayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mCheckBoxChangeListener());
        fridayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mCheckBoxChangeListener());
        saturdayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mCheckBoxChangeListener());
        sundayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mCheckBoxChangeListener());
        multiDaysCheckBoxArrayList.add(mondayCheckBox);
        multiDaysCheckBoxArrayList.add(tuesdayCheckBox);
        multiDaysCheckBoxArrayList.add(wednesdayCheckBox);
        multiDaysCheckBoxArrayList.add(thursdayCheckBox);
        multiDaysCheckBoxArrayList.add(fridayCheckBox);
        multiDaysCheckBoxArrayList.add(saturdayCheckBox);
        multiDaysCheckBoxArrayList.add(sundayCheckBox);

        singleMondayCheckBox = rootView.findViewById(R.id.singleMondayCheckBox);
        singleTuesdayCheckBox = rootView.findViewById(R.id.singleTuesdayCheckBox);
        singleWednesdayCheckBox = rootView.findViewById(R.id.singleWednesdayCheckBox);
        singleThursdayCheckBox = rootView.findViewById(R.id.singleThursdayCheckBox);
        singleFridayCheckBox = rootView.findViewById(R.id.singleFridayCheckBox);
        singleSaturdayCheckBox = rootView.findViewById(R.id.singleSaturdayCheckBox);
        singleSundayCheckBox = rootView.findViewById(R.id.singleSundayCheckBox);

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

        mondayTableLayout = rootView.findViewById(R.id.singleMondayTableLayout);
        tuesdayTableLayout = rootView.findViewById(R.id.singleTuesdayTableLayout);
        wednesdayTableLayout = rootView.findViewById(R.id.singleWednesdayTableLayout);
        thursdayTableLayout = rootView.findViewById(R.id.singleThursdayTableLayout);
        fridayTableLayout = rootView.findViewById(R.id.singleFridayTableLayout);
        saturdayTableLayout = rootView.findViewById(R.id.singleSaturdayTableLayout);
        sundayTableLayout = rootView.findViewById(R.id.singleSundayTableLayout);

        singleMondayLinearLayout = rootView.findViewById(R.id.singleMondayLinearLayout);
        singleTuesdayLinearLayout = rootView.findViewById(R.id.singleTuesdayLinearLayout);
        singleWednesdayLinearLayout = rootView.findViewById(R.id.singleWednesdayLinearLayout);
        singleThursdayLinearLayout = rootView.findViewById(R.id.singleThursdayLinearLayout);
        singleFridayLinearLayout = rootView.findViewById(R.id.singleFridayLinearLayout);
        singleSaturdayLinearLayout = rootView.findViewById(R.id.singleSaturdayLinearLayout);
        singleSundayLinearLayout = rootView.findViewById(R.id.singleSundayLinearLayout);

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

        singleMondayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mSingleCheckBoxChangeListener());
        singleTuesdayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mSingleCheckBoxChangeListener());
        singleWednesdayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mSingleCheckBoxChangeListener());
        singleThursdayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mSingleCheckBoxChangeListener());
        singleFridayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mSingleCheckBoxChangeListener());
        singleSaturdayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mSingleCheckBoxChangeListener());
        singleSundayCheckBox.setOnCheckedChangeListener(new AllListingAvailabilityFragment.mSingleCheckBoxChangeListener());

        instant_booking = rootView.findViewById(R.id.instance_booking_switch);
        multi_single_day = rootView.findViewById(R.id.single_multi_day_switch);
        multiDayTableLayout = rootView.findViewById(R.id.multiDayLinearLayout);
        singleDayTableLayout = rootView.findViewById(R.id.singleDayLinearLayout);

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

                /*if (isDefaultTime) {
                    startTimeEditText.setText(multiDayStartTimeList.get(multiDayStartTimeList.size() - 1));
                    endTimeEditText.setText(multiDayEndTimeList.get(multiDayEndTimeList.size() - 1));
                } else {
                    startTimeEditText.setText("8:00 AM");
                    endTimeEditText.setText("8:00 PM");
                }
                isDefaultTime = false;*/

                startTimeEditText.setText("8:00 AM");
                endTimeEditText.setText("8:00 PM");

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


        Button nextButton = (Button) rootView.findViewById(R.id.next_button);
        Button backButton = (Button) rootView.findViewById(R.id.back_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {



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



                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                Gson gson = new Gson();
                String mon_end_array = gson.toJson(mondayEndTimeList);
                editor.putString("MON_END", mon_end_array);
                String mon_start_array = gson.toJson(mondayStartTimeList);
                editor.putString("MON_START", mon_start_array);

                String tue_end_array = gson.toJson(tuesdayEndTimeList);
                editor.putString("TUE_END", tue_end_array);
                String tue_start_array = gson.toJson(tuesdayStartTimeList);
                editor.putString("TUE_START", tue_start_array);

                String wed_end_array = gson.toJson(wednesdayEndTimeList);
                editor.putString("WED_END", wed_end_array);
                String wed_start_array = gson.toJson(wednesdayStartTimeList);
                editor.putString("WED_START", wed_start_array);

                String thr_end_array = gson.toJson(thursdayEndTimeList);
                editor.putString("THU_END", thr_end_array);
                String thr_start_array = gson.toJson(thursdayStartTimeList);
                editor.putString("THU_START", thr_start_array);

                String fri_end_array = gson.toJson(fridayEndTimeList);
                editor.putString("FRI_END", fri_end_array);
                String fri_start_array = gson.toJson(fridayStartTimeList);
                editor.putString("FRI_START", fri_start_array);

                String sat_end_array = gson.toJson(saturdayEndTimeList);
                editor.putString("SAT_END", sat_end_array);
                String sat_start_array = gson.toJson(saturdayStartTimeList);
                editor.putString("SAT_START", sat_start_array);

                String sun_end_array = gson.toJson(sundayEndTimeList);
                editor.putString("SUN_END", sun_end_array);
                String sun_start_array = gson.toJson(sundayStartTimeList);
                editor.putString("SUN_START", sun_start_array);

                String all_end_array = gson.toJson(multiDayEndTimeList);
                editor.putString("multiDayEndTimeList", all_end_array);
                String all_start_array = gson.toJson(multiDayStartTimeList);
                editor.putString("multiDayStartTimeList", all_start_array);

                String single_array = gson.toJson(multidaysSelectedList);
                editor.putString("multidaysSelectedList", single_array);
                String all_array = gson.toJson(singleDaySelectedList);
                editor.putString("singleDaySelectedList", all_array);

                editor.putInt("INSTANCE_BOOKING_SWITCH", selected);
                editor.putInt("SINGLE_MULTI_DAY_SWITCH", checked);
                editor.commit();

                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout10);
                tabhost.getTabAt(2).select();
            }
        });

        ///Back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout10);
                tabhost.getTabAt(0).select();
            }
        });





        return rootView;
        //return inflater.inflate(R.layout.fragment_all_listing_availability, container, false);
    }




    public void showTextNotification(ArrayList<String> stringItemSelectedList) {
        StringBuilder stringBuilder = new StringBuilder("Selected Days : ");
        for (String singDay : stringItemSelectedList) {
            stringBuilder.append(", " + singDay);
        }
        Toast.makeText(getContext(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();
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

        /*if (isDefaultTime) {
            startTimeEditText.setText(currentDayStartTimeList.get(currentDayStartTimeList.size() - 1));
            endTimeEditText.setText(currentDayEndTimeList.get(currentDayEndTimeList.size() - 1));
        } else {
            startTimeEditText.setText("8:00 AM");
            endTimeEditText.setText("8:00 PM");
        }

        isDefaultTime = false;*/

        startTimeEditText.setText("8:00 AM");
        endTimeEditText.setText("8:00 PM");
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

            /*if (checked == 0 && show_resi_arry.getStartTime() != null
                    && show_resi_arry.getStartTime().size() > 0) {
                for (int index = 0; index < show_resi_arry.getStartTime().size(); index++) {
                    isDefaultTime = true;
                    multiDayStartTimeList.add((String) show_resi_arry.getStartTime().get(index));
                    multiDayEndTimeList.add((String) show_resi_arry.getEndTime().get(index));
                    multiDayAddTimeBlock.callOnClick();
                }
            }*/

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
                   /* if ("monday".equalsIgnoreCase(day)) {
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
                    }*/
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
                //isDefaultTime = true;
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
            int backgroundColorGrey = ContextCompat.getColor(getContext(), R.color.quantum_grey);
            int backgroundColorGreen = ContextCompat.getColor(getContext(), R.color.green);
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


}