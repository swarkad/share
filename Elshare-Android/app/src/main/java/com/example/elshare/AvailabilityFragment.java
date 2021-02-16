package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Calendar;

public class AvailabilityFragment extends Fragment {

    private EditText edittext1, editText2;
    private int mHour, mMinute;
    private CheckBox mon_d, tue_d, wed_d, thr_d, fri_d, sat_d, sun_d;
    private Switch day_sw, instant_booking;
    private int CalendarHour, CalendarMinute;
    private Calendar calendar;

    private String format;
    private TimePickerDialog timepickerdialog;
    private Button all_day_button;
    private ImageButton delete_row;
    Boolean FLAG, swich_state;
    private TableLayout table;
    private int checked, selected;
    private String host_str;
    private String all_mon, all_tue, all_wed, all_thr, all_fri, all_sat, all_sun, all_day_str;

    private Boolean isMonday, isTuesday, isWednesday, isThuresday, isFriday, isSatursday, isSunday;
    private Button add_time_block;

    @SuppressLint({"CutPasteId", "LongLogTag"})
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.availability_frag, container, false);
        StringBuffer result = new StringBuffer();
        Button btn = (Button) rootView.findViewById(R.id.button61);
        Button btn2 = (Button) rootView.findViewById(R.id.button60);
        final Button multi_b = rootView.findViewById(R.id.button105);
        final Button single_b = rootView.findViewById(R.id.button106);
        final TextView select_day = rootView.findViewById(R.id.textView38);
        instant_booking = rootView.findViewById(R.id.switch1);

        final ArrayList<String> single_day = new ArrayList<>();
        final ArrayList<String> all_day_start = new ArrayList<>();
        final ArrayList<String> all_day_end = new ArrayList<>();
        final ArrayList<String> mon_start = new ArrayList<>();
        final ArrayList<String> mon_end = new ArrayList<>();
        final ArrayList<String> tue_start = new ArrayList<>();
        final ArrayList<String> tue_end = new ArrayList<>();
        final ArrayList<String> wed_start = new ArrayList<>();
        final ArrayList<String> wed_end = new ArrayList<>();
        final ArrayList<String> thr_start = new ArrayList<>();
        final ArrayList<String> thr_end = new ArrayList<>();
        final ArrayList<String> fri_start = new ArrayList<>();
        final ArrayList<String> fri_end = new ArrayList<>();
        final ArrayList<String> sat_start = new ArrayList<>();
        final ArrayList<String> sat_end = new ArrayList<>();
        final ArrayList<String> sun_start = new ArrayList<>();
        final ArrayList<String> sun_end = new ArrayList<>();
        all_mon = "not_all_day";
        all_tue = "not_all_day";
        all_wed = "not_all_day";
        all_thr = "not_all_day";
        all_fri = "not_all_day";
        all_sat = "not_all_day";
        all_sun = "not_all_day";
        all_day_str = "not_all_day";

        final ArrayList<String> all_day = new ArrayList<>();
        all_day_button = rootView.findViewById(R.id.button108);
        selected = 1;
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

        edittext1 = rootView.findViewById(R.id.editText27);
        editText2 = rootView.findViewById(R.id.editText28);
        day_sw = rootView.findViewById(R.id.switch2);
        mon_d = rootView.findViewById(R.id.button53);
        tue_d = rootView.findViewById(R.id.button54);
        wed_d = rootView.findViewById(R.id.button55);
        thr_d = rootView.findViewById(R.id.button56);
        fri_d = rootView.findViewById(R.id.button57);
        sat_d = rootView.findViewById(R.id.button58);
        sun_d = rootView.findViewById(R.id.button59);
        day_sw.refreshDrawableState();

        day_sw.toggle();
        Log.i("Switch state:", String.valueOf(day_sw));
        day_sw.setChecked(false);
        Log.i("State checked", String.valueOf(checked));
        single_day.clear();
        checked = 0;
        mon_d.setChecked(false);
        tue_d.setChecked(false);
        wed_d.setChecked(false);
        thr_d.setChecked(false);
        fri_d.setChecked(false);
        sat_d.setChecked(false);
        sun_d.setChecked(false);

        mon_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String demo_str = "monday";
                    all_day.add(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                } else {
                    String demo_str = "monday";
                    all_day.remove(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        tue_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String demo_str = "tuesday";
                    all_day.add(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                } else {
                    String demo_str = "tuesday";
                    all_day.remove(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        wed_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String demo_str = "wednesday";
                    all_day.add(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                } else {
                    String demo_str = "wednesday";
                    all_day.remove(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        thr_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String demo_str = "thursday";
                    all_day.add(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                } else {
                    String demo_str = "thursday";
                    all_day.remove(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        fri_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String demo_str = "friday";
                    all_day.add(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                } else {
                    String demo_str = "friday";
                    all_day.remove(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        sat_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String demo_str = "saturday";
                    all_day.add(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                } else {
                    String demo_str = "saturday";
                    all_day.remove(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        sun_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String demo_str = "sunday";
                    all_day.add(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                } else {
                    String demo_str = "sunday";
                    all_day.remove(demo_str);
                    Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        String day_str = "Select time slot for selected days";
        select_day.setText(day_str);
        // The toggle is disabled
        day_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                  if (buttonView.isChecked()) {
                                                      //day selection-------------------------------
                                                      checked = 0;
                                                      Log.i("State checked", String.valueOf(checked));
                                                      all_day.clear();

                                                      mon_d.setChecked(false);
                                                      tue_d.setChecked(false);
                                                      wed_d.setChecked(false);
                                                      thr_d.setChecked(false);
                                                      fri_d.setChecked(false);
                                                      sat_d.setChecked(false);
                                                      sun_d.setChecked(false);

                                                      final ArrayList<String> day_select = new ArrayList<>();
                                                      String result = "Selected Courses";

                                                      //Single day----------------------------------
                                                      mon_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  isMonday = true;
                                                                  if (tue_start.isEmpty()) {
                                                                      tue_d.setChecked(false);
                                                                      isTuesday = false;
                                                                  } else {
                                                                      tue_d.setChecked(true);
                                                                      isTuesday = false;
                                                                  }

                                                                  if (wed_start.isEmpty()) {
                                                                      wed_d.setChecked(false);
                                                                      isWednesday = false;
                                                                  } else {
                                                                      wed_d.setChecked(true);
                                                                      isWednesday = false;
                                                                  }

                                                                  if (thr_start.isEmpty()) {
                                                                      thr_d.setChecked(false);
                                                                      isThuresday = false;
                                                                  } else {
                                                                      thr_d.setChecked(true);
                                                                      isThuresday = false;
                                                                  }

                                                                  if (fri_start.isEmpty()) {
                                                                      fri_d.setChecked(false);
                                                                      isFriday = false;
                                                                  } else {
                                                                      fri_d.setChecked(true);
                                                                      isFriday = false;
                                                                  }

                                                                  if (sat_start.isEmpty()) {
                                                                      sat_d.setChecked(false);
                                                                      isSatursday = false;
                                                                  } else {
                                                                      sat_d.setChecked(true);
                                                                      isSatursday = false;
                                                                  }

                                                                  if (sun_start.isEmpty()) {
                                                                      sun_d.setChecked(false);
                                                                      isSunday = false;
                                                                  } else {
                                                                      sun_d.setChecked(true);
                                                                      isSunday = false;
                                                                  }

                                                                  String demo_str = "monday";
                                                                  single_day.add(demo_str);
                                                                  if (table != null) {
                                                                      table.removeAllViews();
                                                                  }
                                                                  String day_str = "Select time slot for Monday";
                                                                  select_day.setText(day_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = " monday";
                                                                  single_day.remove(demo_str);
                                                                  mon_end.remove(true);
                                                                  mon_start.remove(true);
                                                                  mon_d.setChecked(false);
                                                                  isMonday = false;
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      tue_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  isTuesday = true;

                                                                  if (mon_start.isEmpty()) {
                                                                      mon_d.setChecked(false);
                                                                      isMonday = false;
                                                                  } else {
                                                                      mon_d.setChecked(true);
                                                                      isMonday = false;
                                                                  }

                                                                  if (wed_start.isEmpty()) {
                                                                      wed_d.setChecked(false);
                                                                      isWednesday = false;
                                                                  } else {
                                                                      wed_d.setChecked(true);
                                                                      isWednesday = false;
                                                                  }

                                                                  if (thr_start.isEmpty()) {
                                                                      thr_d.setChecked(false);
                                                                      isThuresday = false;
                                                                  } else {
                                                                      thr_d.setChecked(true);
                                                                      isThuresday = false;
                                                                  }

                                                                  if (fri_start.isEmpty()) {
                                                                      fri_d.setChecked(false);
                                                                      isFriday = false;
                                                                  } else {
                                                                      fri_d.setChecked(true);
                                                                      isFriday = false;
                                                                  }

                                                                  if (sat_start.isEmpty()) {
                                                                      sat_d.setChecked(false);
                                                                      isSatursday = false;
                                                                  } else {
                                                                      sat_d.setChecked(true);
                                                                      isSatursday = false;
                                                                  }

                                                                  if (sun_start.isEmpty()) {
                                                                      sun_d.setChecked(false);
                                                                      isSunday = false;
                                                                  } else {
                                                                      sun_d.setChecked(true);
                                                                      isSunday = false;
                                                                  }

                                                                  String demo_str = "tuesday";
                                                                  single_day.add(demo_str);

                                                                  if (table != null) {
                                                                      table.removeAllViews();
                                                                  }
                                                                  String day_str = "Select time slot for Tuesday";
                                                                  select_day.setText(day_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "tuesday";
                                                                  single_day.remove(demo_str);
                                                                  tue_end.remove(true);
                                                                  tue_start.remove(true);
                                                                  tue_d.setChecked(false);
                                                                  isTuesday = false;
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      wed_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  isWednesday = true;

                                                                  if (tue_start.isEmpty()) {
                                                                      tue_d.setChecked(false);
                                                                      isTuesday = false;
                                                                  } else {
                                                                      tue_d.setChecked(true);
                                                                      isTuesday = false;
                                                                  }

                                                                  if (mon_start.isEmpty()) {
                                                                      mon_d.setChecked(false);
                                                                      isMonday = false;
                                                                  } else {
                                                                      mon_d.setChecked(true);
                                                                      isMonday = false;
                                                                  }

                                                                  if (thr_start.isEmpty()) {
                                                                      thr_d.setChecked(false);
                                                                      isThuresday = false;
                                                                  } else {
                                                                      thr_d.setChecked(true);
                                                                      isThuresday = false;
                                                                  }

                                                                  if (fri_start.isEmpty()) {
                                                                      fri_d.setChecked(false);
                                                                      isFriday = false;
                                                                  } else {
                                                                      fri_d.setChecked(true);
                                                                      isFriday = false;
                                                                  }

                                                                  if (sat_start.isEmpty()) {
                                                                      sat_d.setChecked(false);
                                                                      isSatursday = false;
                                                                  } else {
                                                                      sat_d.setChecked(true);
                                                                      isSatursday = false;
                                                                  }

                                                                  if (sun_start.isEmpty()) {
                                                                      sun_d.setChecked(false);
                                                                      isSunday = false;
                                                                  } else {
                                                                      sun_d.setChecked(true);
                                                                      isSunday = false;
                                                                  }

                                                                  String demo_str = "wednesday";
                                                                  single_day.add(demo_str);
                                                                  if (table != null) {
                                                                      table.removeAllViews();
                                                                  }
                                                                  String day_str = "Select time slot for wednesday";
                                                                  select_day.setText(day_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "wednesday";
                                                                  single_day.remove(demo_str);
                                                                  wed_end.remove(true);
                                                                  wed_start.remove(true);
                                                                  wed_d.setChecked(false);
                                                                  isWednesday = false;
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      thr_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {

                                                                  isThuresday = true;
                                                                  if (tue_start.isEmpty()) {
                                                                      tue_d.setChecked(false);
                                                                      isTuesday = false;
                                                                  } else {
                                                                      tue_d.setChecked(true);
                                                                      isTuesday = false;
                                                                  }

                                                                  if (mon_start.isEmpty()) {
                                                                      mon_d.setChecked(false);
                                                                      isMonday = false;
                                                                  } else {
                                                                      mon_d.setChecked(true);
                                                                      isMonday = false;
                                                                  }

                                                                  if (wed_start.isEmpty()) {
                                                                      wed_d.setChecked(false);
                                                                      isWednesday = false;
                                                                  } else {
                                                                      wed_d.setChecked(true);
                                                                      isWednesday = false;
                                                                  }

                                                                  if (fri_start.isEmpty()) {
                                                                      fri_d.setChecked(false);
                                                                      isFriday = false;
                                                                  } else {
                                                                      fri_d.setChecked(true);
                                                                      isFriday = false;
                                                                  }

                                                                  if (sat_start.isEmpty()) {
                                                                      sat_d.setChecked(false);
                                                                      isSatursday = false;
                                                                  } else {
                                                                      sat_d.setChecked(true);
                                                                      isSatursday = false;
                                                                  }

                                                                  if (sun_start.isEmpty()) {
                                                                      sun_d.setChecked(false);
                                                                      isSunday = false;
                                                                  } else {
                                                                      sun_d.setChecked(true);
                                                                      isSunday = false;
                                                                  }

                                                                  String demo_str = "thrusday";
                                                                  single_day.add(demo_str);
                                                                  if (table != null) {
                                                                      table.removeAllViews();
                                                                  }
                                                                  String day_str = "Select time slot for Thrusday";
                                                                  select_day.setText(day_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "thrusday";
                                                                  single_day.remove(demo_str);
                                                                  thr_end.remove(true);
                                                                  thr_start.remove(true);
                                                                  thr_d.setChecked(false);
                                                                  isThuresday = false;
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      fri_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  isFriday = true;
                                                                  if (tue_start.isEmpty()) {
                                                                      tue_d.setChecked(false);
                                                                      isTuesday = false;
                                                                  } else {
                                                                      tue_d.setChecked(true);
                                                                      isTuesday = false;
                                                                  }

                                                                  if (mon_start.isEmpty()) {
                                                                      mon_d.setChecked(false);
                                                                      isMonday = false;
                                                                  } else {
                                                                      mon_d.setChecked(true);
                                                                      isMonday = false;
                                                                  }

                                                                  if (thr_start.isEmpty()) {
                                                                      thr_d.setChecked(false);
                                                                      isThuresday = false;
                                                                  } else {
                                                                      thr_d.setChecked(true);
                                                                      isThuresday = false;
                                                                  }

                                                                  if (wed_start.isEmpty()) {
                                                                      wed_d.setChecked(false);
                                                                      isWednesday = false;
                                                                  } else {
                                                                      wed_d.setChecked(true);
                                                                      isWednesday = false;
                                                                  }

                                                                  if (sat_start.isEmpty()) {
                                                                      sat_d.setChecked(false);
                                                                      isSatursday = false;
                                                                  } else {
                                                                      sat_d.setChecked(true);
                                                                      isSatursday = false;
                                                                  }

                                                                  if (sun_start.isEmpty()) {
                                                                      sun_d.setChecked(false);
                                                                      isSunday = false;
                                                                  } else {
                                                                      sun_d.setChecked(true);
                                                                      isSunday = false;
                                                                  }

                                                                  String demo_str = "friday";
                                                                  single_day.add(demo_str);

                                                                  if (table != null) {
                                                                      table.removeAllViews();
                                                                  }
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "friday";
                                                                  single_day.remove(demo_str);
                                                                  fri_end.remove(true);
                                                                  fri_start.remove(true);
                                                                  fri_d.setChecked(false);
                                                                  isFriday = false;
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      sat_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  isSatursday = true;
                                                                  if (tue_start.isEmpty()) {
                                                                      tue_d.setChecked(false);
                                                                      isTuesday = false;
                                                                  } else {
                                                                      tue_d.setChecked(true);
                                                                      isTuesday = false;
                                                                  }

                                                                  if (mon_start.isEmpty()) {
                                                                      mon_d.setChecked(false);
                                                                      isMonday = false;
                                                                  } else {
                                                                      mon_d.setChecked(true);
                                                                      isMonday = false;
                                                                  }

                                                                  if (thr_start.isEmpty()) {
                                                                      thr_d.setChecked(false);
                                                                      isThuresday = false;
                                                                  } else {
                                                                      thr_d.setChecked(true);
                                                                      isThuresday = false;
                                                                  }

                                                                  if (fri_start.isEmpty()) {
                                                                      fri_d.setChecked(false);
                                                                      isFriday = false;
                                                                  } else {
                                                                      fri_d.setChecked(true);
                                                                      isFriday = false;
                                                                  }

                                                                  if (wed_start.isEmpty()) {
                                                                      wed_d.setChecked(false);
                                                                      isWednesday = false;
                                                                  } else {
                                                                      wed_d.setChecked(true);
                                                                      isWednesday = false;
                                                                  }

                                                                  if (sun_start.isEmpty()) {
                                                                      sun_d.setChecked(false);
                                                                      isSunday = false;
                                                                  } else {
                                                                      sun_d.setChecked(true);
                                                                      isSunday = false;
                                                                  }

                                                                  String demo_str = "satursday";
                                                                  single_day.add(demo_str);
                                                                  if (table != null) {
                                                                      table.removeAllViews();
                                                                  }
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "satursday";
                                                                  single_day.remove(demo_str);
                                                                  sat_end.remove(true);
                                                                  sat_start.remove(true);
                                                                  isSatursday = false;
                                                                  sat_d.setChecked(false);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      sun_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  isSunday = true;
                                                                  if (tue_start.isEmpty()) {
                                                                      tue_d.setChecked(false);
                                                                      isTuesday = false;
                                                                  } else {
                                                                      tue_d.setChecked(true);
                                                                      isTuesday = false;
                                                                  }

                                                                  if (mon_start.isEmpty()) {
                                                                      mon_d.setChecked(false);
                                                                      isMonday = false;
                                                                  } else {
                                                                      mon_d.setChecked(true);
                                                                      isMonday = false;
                                                                  }

                                                                  if (thr_start.isEmpty()) {
                                                                      thr_d.setChecked(false);
                                                                      isThuresday = false;
                                                                  } else {
                                                                      thr_d.setChecked(true);
                                                                      isThuresday = false;
                                                                  }

                                                                  if (fri_start.isEmpty()) {
                                                                      fri_d.setChecked(false);
                                                                      isFriday = false;
                                                                  } else {
                                                                      fri_d.setChecked(true);
                                                                      isFriday = false;
                                                                  }

                                                                  if (sat_start.isEmpty()) {
                                                                      sat_d.setChecked(false);
                                                                      isSatursday = false;
                                                                  } else {
                                                                      sat_d.setChecked(true);
                                                                      isSatursday = false;
                                                                  }

                                                                  if (wed_start.isEmpty()) {
                                                                      wed_d.setChecked(false);
                                                                      isWednesday = false;
                                                                  } else {
                                                                      wed_d.setChecked(true);
                                                                      isWednesday = false;
                                                                  }

                                                                  String demo_str = "sunday";
                                                                  single_day.add(demo_str);
                                                                  if (table != null) {
                                                                      table.removeAllViews();
                                                                  }
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "sunday";
                                                                  single_day.remove(demo_str);
                                                                  sun_end.remove(true);
                                                                  sun_start.remove(true);
                                                                  sun_d.setChecked(false);
                                                                  isSunday = false;
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                  } else {
                                                      checked = 1;
                                                      Log.i("State checked", String.valueOf(checked));
                                                      single_day.clear();
                                                      checked = 1;
                                                      mon_d.setChecked(false);
                                                      tue_d.setChecked(false);
                                                      wed_d.setChecked(false);
                                                      thr_d.setChecked(false);
                                                      fri_d.setChecked(false);
                                                      sat_d.setChecked(false);
                                                      sun_d.setChecked(false);

                                                      mon_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  String demo_str = "monday";
                                                                  all_day.add(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "monday";
                                                                  all_day.remove(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      tue_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  String demo_str = "tuesday";
                                                                  all_day.add(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "tuesday";
                                                                  all_day.remove(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      wed_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  String demo_str = "wednesday";
                                                                  all_day.add(demo_str);

                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "wednesday";
                                                                  all_day.remove(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      thr_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  String demo_str = "thursday";
                                                                  all_day.add(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "thursday";
                                                                  all_day.remove(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      fri_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  String demo_str = "friday";
                                                                  all_day.add(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "friday";
                                                                  all_day.remove(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      sat_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  String demo_str = "satursday";
                                                                  all_day.add(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "satursday";
                                                                  all_day.remove(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      sun_d.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              if (isChecked) {
                                                                  String demo_str = "sunday";
                                                                  all_day.add(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              } else {
                                                                  String demo_str = "sunday";
                                                                  all_day.remove(demo_str);
                                                                  Toast.makeText(getContext(), demo_str, Toast.LENGTH_SHORT).show();
                                                              }
                                                          }
                                                      });

                                                      String day_str = "Select time slot for selected days";
                                                      select_day.setText(day_str);
                                                      // The toggle is disabled
                                                  }
                                              }
                                          }
        );

        add_time_block = rootView.findViewById(R.id.button300);
        add_time_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a new row to add
                final TableRow row = (TableRow) LayoutInflater.from(getContext()).inflate(R.layout.sample_row, null);
                table = rootView.findViewById(R.id.tableLayout1);
                final EditText end_txt = row.findViewById(R.id.editText200);
                delete_row = row.findViewById(R.id.image_b);
                final EditText start_txt = row.findViewById(R.id.editText201);

                end_txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        calendar = Calendar.getInstance();
                        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                        CalendarMinute = calendar.get(Calendar.MINUTE);
                        Log.i("time 24 :", String.valueOf(CalendarHour + CalendarMinute));

                        timepickerdialog = new TimePickerDialog(getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        if (hourOfDay == 0) {
                                            hourOfDay += 12;
                                            format = "AM";
                                        } else if (hourOfDay == 12) {
                                            format = "PM";
                                        } else if (hourOfDay > 12) {
                                            hourOfDay -= 12;
                                            format = "PM";
                                        } else {
                                            format = "AM";
                                        }

                                        if (day_sw.isChecked()) {
                                            if (mon_d.isChecked() && isMonday == true) {
                                                end_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = end_txt.getText().toString();
                                                mon_end.add(end_str_time);
                                            } else if (tue_d.isChecked() && isTuesday == true) {
                                                end_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = end_txt.getText().toString();
                                                tue_end.add(end_str_time);
                                            } else if (wed_d.isChecked() && isWednesday == true) {
                                                end_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = end_txt.getText().toString();
                                                wed_end.add(end_str_time);
                                            } else if (thr_d.isChecked() && isThuresday == true) {
                                                end_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = end_txt.getText().toString();
                                                thr_end.add(end_str_time);
                                            } else if (fri_d.isChecked() && isFriday == true) {
                                                end_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = end_txt.getText().toString();
                                                fri_end.add(end_str_time);
                                            } else if (sat_d.isChecked() && isSatursday == true) {
                                                end_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = end_txt.getText().toString();
                                                sat_end.add(end_str_time);
                                            } else if (sun_d.isChecked() && isSunday == true) {
                                                end_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = end_txt.getText().toString();
                                                sun_end.add(end_str_time);
                                            }
                                        } else {
                                            end_txt.setText(hourOfDay + ":" + minute);
                                            String end_str_time = end_txt.getText().toString();
                                            all_day_end.add(end_str_time);
                                        }
                                    }
                                }, CalendarHour, CalendarMinute, false);
                        timepickerdialog.show();
                    }

                });

                start_txt.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        calendar = Calendar.getInstance();
                        CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                        CalendarMinute = calendar.get(Calendar.MINUTE);
                        
                        timepickerdialog = new TimePickerDialog(getContext(),
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        if (hourOfDay == 0) {
                                            hourOfDay += 12;
                                            format = "AM";
                                        } else if (hourOfDay == 12) {
                                            format = "PM";
                                        } else if (hourOfDay > 12) {
                                            hourOfDay -= 12;
                                            format = "PM";
                                        } else {
                                            format = "AM";
                                        }
                                        
                                        if (day_sw.isChecked()) {
                                            if (mon_d.isChecked() && isMonday == true) {
                                                start_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = start_txt.getText().toString();
                                                mon_start.add(end_str_time);
                                            } else if (tue_d.isChecked() && isTuesday == true) {
                                                start_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = start_txt.getText().toString();
                                                tue_start.add(end_str_time);
                                            } else if (wed_d.isChecked() && isWednesday == true) {
                                                start_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = start_txt.getText().toString();
                                                wed_start.add(end_str_time);
                                            } else if (thr_d.isChecked() && isThuresday == true) {
                                                start_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = start_txt.getText().toString();
                                                thr_start.add(end_str_time);
                                            } else if (fri_d.isChecked() && isFriday == true) {
                                                start_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = start_txt.getText().toString();
                                                fri_start.add(end_str_time);
                                            } else if (sat_d.isChecked() && isSatursday == true) {
                                                start_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = start_txt.getText().toString();
                                                sat_start.add(end_str_time);
                                            } else if (sun_d.isChecked() && isSunday == true) {
                                                start_txt.setText(hourOfDay + ":" + minute + " " + format);
                                                String end_str_time = start_txt.getText().toString();
                                                sun_start.add(end_str_time);
                                            }
                                        } else {
                                            start_txt.setText(hourOfDay + ":" + minute);
                                            String start_str_time = start_txt.getText().toString();
                                            all_day_start.add(start_str_time);
                                        }
                                    }
                                }, CalendarHour, CalendarMinute, false);
                        timepickerdialog.show();
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
                    }
                });
                
                //add your new row to the TableLayout:
                table.addView(row);
            }
        });
        
        edittext1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);
                
                timepickerdialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }
                                
                                if (day_sw.isChecked()) {
                                    if (mon_d.isChecked() && isMonday == true) {
                                        edittext1.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = edittext1.getText().toString();
                                        mon_start.add(end_str_time);
                                    } else if (tue_d.isChecked() && isTuesday == true) {
                                        edittext1.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = edittext1.getText().toString();
                                        tue_start.add(end_str_time);
                                    } else if (wed_d.isChecked() && isWednesday == true) {
                                        edittext1.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = edittext1.getText().toString();
                                        wed_start.add(end_str_time);
                                    } else if (thr_d.isChecked() && isThuresday == true) {
                                        edittext1.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = edittext1.getText().toString();
                                        thr_start.add(end_str_time);
                                    } else if (fri_d.isChecked() && isFriday == true) {
                                        edittext1.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = edittext1.getText().toString();
                                        fri_start.add(end_str_time);
                                    } else if (sat_d.isChecked() && isSatursday == true) {
                                        edittext1.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = edittext1.getText().toString();
                                        sat_start.add(end_str_time);
                                    } else if (sun_d.isChecked() && isSunday == true) {
                                        edittext1.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = edittext1.getText().toString();
                                        sun_start.add(end_str_time);
                                    }
                                } else {
                                    edittext1.setText(hourOfDay + ":" + minute);
                                    String start_str_time = edittext1.getText().toString();
                                    all_day_start.add(start_str_time);
                                }
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });
        
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);
                
                timepickerdialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                if (hourOfDay == 0) {
                                    hourOfDay += 12;
                                    format = "AM";
                                } else if (hourOfDay == 12) {
                                    format = "PM";
                                } else if (hourOfDay > 12) {
                                    hourOfDay -= 12;
                                    format = "PM";
                                } else {
                                    format = "AM";
                                }

                                if (day_sw.isChecked()) {
                                    if (mon_d.isChecked() && isMonday == true) {
                                        editText2.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = editText2.getText().toString();
                                        mon_end.add(end_str_time);
                                    } else if (tue_d.isChecked() && isTuesday == true) {
                                        editText2.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = editText2.getText().toString();
                                        tue_end.add(end_str_time);
                                    } else if (wed_d.isChecked() && isWednesday == true) {
                                        editText2.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = editText2.getText().toString();
                                        wed_end.add(end_str_time);
                                    } else if (thr_d.isChecked() && isThuresday == true) {
                                        editText2.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = editText2.getText().toString();
                                        thr_end.add(end_str_time);
                                    } else if (fri_d.isChecked() && isFriday == true) {
                                        editText2.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = editText2.getText().toString();
                                        fri_end.add(end_str_time);
                                    } else if (sat_d.isChecked() && isSatursday == true) {
                                        editText2.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = editText2.getText().toString();
                                        sat_end.add(end_str_time);
                                    } else if (sun_d.isChecked() && isSunday == true) {
                                        editText2.setText(hourOfDay + ":" + minute + " " + format);
                                        String end_str_time = editText2.getText().toString();
                                        sun_end.add(end_str_time);
                                    }
                                } else {
                                    editText2.setText(CalendarHour + ":" + CalendarMinute);
                                    String end_str_time = editText2.getText().toString();
                                    all_day_end.add(end_str_time);
                                }
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerdialog.show();
            }
        });
        
        all_day_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (day_sw.isChecked()) {
                    all_day_str = "not_all_day";
                    
                    //Single day
                    if (mon_d.isChecked()) {
                        if (isMonday) {
                            all_mon = "all_day";
                        } else {
                            all_mon = "not_all_day";
                        }
                    } else if (tue_d.isChecked()) {
                        if (isTuesday) {
                            all_tue = "all_day";
                        } else {
                            all_tue = "not_all_day";
                        }
                    } else if (wed_d.isChecked()) {
                        if (isWednesday) {
                            all_wed = "all_day";
                        } else {
                            all_wed = "not_all_day";
                        }
                    } else if (thr_d.isChecked()) {
                        if (isThuresday) {
                            all_thr = "all_day";
                        } else {
                            all_thr = "not_all_day";
                        }
                    } else if (fri_d.isChecked()) {
                        if (isFriday) {
                            all_fri = "all_day";
                        } else {
                            all_fri = "not_all_day";
                        }
                    } else if (sat_d.isChecked()) {
                        if (isSatursday) {
                            all_sat = "all_day";
                        } else {
                            all_sat = "not_all_day";
                        }
                    } else if (sun_d.isChecked()) {
                        if (isSunday) {
                            all_sun = "all_day";
                        } else {
                            all_sun = "not_all_day";
                        }
                    }
                } else {
                    //Multi day
                    all_day_str = "all_day";
                    all_mon = "not_all_day";
                    all_tue = "not_all_day";
                    all_wed = "not_all_day";
                    all_thr = "not_all_day";
                    all_fri = "not_all_day";
                    all_sat = "not_all_day";
                    all_sun = "not_all_day";
                }
            }
        });
        
        btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                if (mon_end.isEmpty()) {
                    mon_end.add("");
                    mon_start.add("");
                } 
                if (tue_end.isEmpty()) {
                    tue_end.add("");
                    tue_start.add("");
                } 
                if (wed_end.isEmpty()) {
                    wed_end.add("");
                    wed_start.add("");
                } 
                if (thr_end.isEmpty()) {
                    thr_end.add("");
                    thr_start.add("");
                } 
                if (fri_end.isEmpty()) {
                    fri_end.add("");
                    fri_start.add("");
                } 
                if (sat_end.isEmpty()) {
                    sat_end.add("");
                    sat_start.add("");
                } 
                if (sun_end.isEmpty()) {
                    mon_end.add("");
                    sun_start.add("");
                } 
                if (all_day.isEmpty()) {
                    all_day.add("");
                } 
                if (all_day_end.isEmpty()) {
                    all_day_end.add("");
                    all_day_start.add("");
                } 
                if (single_day.isEmpty()) {
                    single_day.add("");
                } 

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = preferences.edit();
                Gson gson = new Gson();

                if (all_mon.equals("not_all_day")) {
                    String mon_end_array = gson.toJson(mon_end);
                    editor.putString("MON_END", mon_end_array);
                    String mon_start_array = gson.toJson(mon_start);
                    editor.putString("MON_START", mon_start_array);
                    editor.putString("ALL_MON", all_mon);
                } else {
                    mon_end.clear();
                    mon_end.add("");
                    mon_start.clear();
                    mon_start.add("");
                    String mon_end_array = gson.toJson(mon_end);
                    editor.putString("MON_END", mon_end_array);
                    String mon_start_array = gson.toJson(mon_start);
                    editor.putString("MON_START", mon_start_array);
                    editor.putString("ALL_MON", all_mon);
                    Log.i("Single mon ----------------------------------", String.valueOf(mon_start));
                    Log.i("Single mon ----------------------------------", String.valueOf(mon_end));
                }
                
                if (all_tue.equals("not_all_day")) {
                    String tue_end_array = gson.toJson(tue_end);
                    editor.putString("TUE_END", tue_end_array);
                    String tue_start_array = gson.toJson(tue_start);
                    editor.putString("TUE_START", tue_start_array);
                    editor.putString("ALL_TUE", all_tue);
                } else {
                    tue_end.clear();
                    tue_end.add("");
                    tue_start.clear();
                    tue_start.add("");
                    String tue_end_array = gson.toJson(tue_end);
                    editor.putString("TUE_END", tue_end_array);
                    String tue_start_array = gson.toJson(tue_start);
                    editor.putString("TUE_START", tue_start_array);
                    editor.putString("ALL_TUE", all_tue);
                }

                if (all_wed.equals("not_all_day")) {
                    String wed_end_array = gson.toJson(wed_end);
                    editor.putString("WED_END", wed_end_array);
                    String wed_start_array = gson.toJson(wed_start);
                    editor.putString("WED_START", wed_start_array);
                    editor.putString("ALL_WED", all_wed);
                } else {
                    wed_end.clear();
                    wed_end.add("");
                    wed_start.clear();
                    wed_start.add("");
                    String wed_end_array = gson.toJson(wed_end);
                    editor.putString("WED_END", wed_end_array);
                    String wed_start_array = gson.toJson(wed_start);
                    editor.putString("WED_START", wed_start_array);
                    editor.putString("ALL_WED", all_wed);
                }

                if (all_thr.equals("not_all_day")) {
                    String thr_end_array = gson.toJson(thr_end);
                    editor.putString("THU_END", thr_end_array);
                    String thr_start_array = gson.toJson(thr_start);
                    editor.putString("THU_START", thr_start_array);
                    editor.putString("ALL_THR", all_thr);
                } else {
                    thr_end.clear();
                    thr_end.add("");
                    thr_start.clear();
                    thr_start.add("");
                    String thr_end_array = gson.toJson(thr_end);
                    editor.putString("THU_END", thr_end_array);
                    String thr_start_array = gson.toJson(thr_start);
                    editor.putString("THU_START", thr_start_array);
                    editor.putString("ALL_THR", all_thr);
                }
                
                if (all_fri.equals("not_all_day")) {
                    String fri_end_array = gson.toJson(fri_end);
                    editor.putString("FRI_END", fri_end_array);
                    String fri_start_array = gson.toJson(fri_start);
                    editor.putString("FRI_START", fri_start_array);
                    editor.putString("ALL_FRI", all_fri);
                } else {
                    fri_end.clear();
                    fri_end.add("");
                    fri_start.clear();
                    fri_start.add("");
                    String fri_end_array = gson.toJson(fri_end);
                    editor.putString("FRI_END", fri_end_array);
                    String fri_start_array = gson.toJson(fri_start);
                    editor.putString("FRI_START", fri_start_array);
                    editor.putString("ALL_FRI", all_fri);
                }
                
                if (all_sat.equals("not_all_day")) {
                    String sat_end_array = gson.toJson(sat_end);
                    editor.putString("SAT_END", sat_end_array);
                    String sat_start_array = gson.toJson(sat_start);
                    editor.putString("SAT_START", sat_start_array);
                    editor.putString("ALL_SAT", all_sat);
                } else {
                    sat_end.clear();
                    sat_end.add("");
                    sat_start.clear();
                    sat_start.add("");
                    String sat_end_array = gson.toJson(sat_end);
                    editor.putString("SAT_END", sat_end_array);
                    String sat_start_array = gson.toJson(sat_start);
                    editor.putString("SAT_START", sat_start_array);
                    editor.putString("ALL_SAT", all_sat);
                }
                
                if (all_sun.equals("not_all_day")) {
                    String sun_end_array = gson.toJson(sun_end);
                    editor.putString("SUN_END", sun_end_array);
                    String sun_start_array = gson.toJson(sun_start);
                    editor.putString("SUN_START", sun_start_array);
                    editor.putString("ALL_SUN", all_sun);
                } else {
                    sun_end.clear();
                    sun_end.add("");
                    sun_start.clear();
                    sun_start.add("");
                    String sun_end_array = gson.toJson(sun_end);
                    editor.putString("SUN_END", sun_end_array);
                    String sun_start_array = gson.toJson(sun_start);
                    editor.putString("SUN_START", sun_start_array);
                    editor.putString("ALL_SUN", all_sun);

                }
                
                if (all_day_str.equals("not_all_day")) {
                    String all_end_array = gson.toJson(all_day_end);
                    editor.putString("ALL_END", all_end_array);
                    String all_start_array = gson.toJson(all_day_start);
                    editor.putString("ALL_START", all_start_array);
                    editor.putString("ALL_DAY_STR", all_day_str);
                } else {
                    all_day_end.clear();
                    all_day_end.add("");
                    all_day_start.clear();
                    all_day_start.add("");
                    String all_end_array = gson.toJson(all_day_end);
                    editor.putString("ALL_END", all_end_array);
                    String all_start_array = gson.toJson(all_day_start);
                    editor.putString("ALL_START", all_start_array);
                    editor.putString("ALL_DAY_STR", all_day_str);
                }

                String single_array = gson.toJson(single_day);
                editor.putString("SINGLE", single_array);
                String all_array = gson.toJson(all_day);
                editor.putString("ALL_DAY", all_array);
                editor.putInt("FLAG", checked);
                editor.putInt("BOOKING", selected);
                editor.commit();

                Log.i("Single ----------------------------------", String.valueOf(single_day));
                Log.i("ALL ----------------------------------", String.valueOf(all_day));
                Log.i("Single mon ----------------------------------", String.valueOf(mon_start));
                Log.i("Single mon ----------------------------------", String.valueOf(mon_end));

                if (all_tue.equals("not_all_day")) {
                    Log.i("Single T ----------------------------------", String.valueOf(tue_start));
                    Log.i("Single T ----------------------------------", String.valueOf(tue_end));
                } else {
                    Log.i("Single T ----------------------------------", String.valueOf(all_tue));
                }
                
                if (all_wed.equals("not_all_day")) {
                    Log.i("Single W ----------------------------------", String.valueOf(wed_start));
                    Log.i("Single W ----------------------------------", String.valueOf(wed_end));
                } else {
                    Log.i("Single W ----------------------------------", String.valueOf(all_wed));
                }
                
                Log.i("Single Thus ----------------------------------", String.valueOf(thr_start));
                Log.i("Single Thus ----------------------------------", String.valueOf(thr_end));
                Log.i("Single fri ----------------------------------", String.valueOf(fri_start));
                Log.i("Single fri ----------------------------------", String.valueOf(fri_end));
                Log.i("Single sat ----------------------------------", String.valueOf(sat_start));
                Log.i("Single sat ----------------------------------", String.valueOf(sat_end));
                Log.i("Single sun ----------------------------------", String.valueOf(sun_start));
                Log.i("Single sun ----------------------------------", String.valueOf(sun_end));
                Log.i("Select day ----------------------------------", String.valueOf(select_day.getText()));
                Log.i("Start All ----------------------------------", all_day_start.toString());
                Log.i("End All ----------------------------------", String.valueOf(all_day_end));
                Log.i("All M ----------------------------------", all_mon);
                Log.i("All Tue ----------------------------------", all_tue);
                Log.i("All W ----------------------------------", all_wed);
                Log.i("All TH ----------------------------------", all_thr);
                Log.i("All F----------------------------------", all_fri);
                Log.i("All Sat ----------------------------------", all_sat);
                Log.i("All Sun---------------------------------", all_sun);
                
                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout2);
                tabhost.getTabAt(2).select();
            }
        });

        ///Back button
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.simpleTabLayout2);
                tabhost.getTabAt(0).select();
            }
        });
        
        return rootView;
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String str = "";
        
        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.button53:
                str = checked ? "Monday Selected" : "Monday Deselected";
                break;
            case R.id.button54:
                str = checked ? "Tuesday Selected" : "AngularJS Deselected";
                break;
            case R.id.button55:
                str = checked ? "Wednesday Selected" : "Java Deselected";
                break;
            case R.id.button56:
                str = checked ? "Thrusday Selected" : "Python Deselected";
                break;
            case R.id.button57:
                str = checked ? "Friday Selected" : "Python Deselected";
                break;
            case R.id.button58:
                str = checked ? "Satursday Selected" : "Python Deselected";
                break;
            case R.id.button59:
                str = checked ? "Sunday Selected" : "Python Deselected";
                break;
            default:
                break;
        }
        
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }
    
}
