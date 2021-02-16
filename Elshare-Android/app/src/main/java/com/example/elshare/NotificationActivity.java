package com.example.elshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.material.tabs.TabLayout;

import datamodel.APIInterface;
import datamodel.User;
import datamodel.UserId;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TabLayout tabLayout;
    FrameLayout frameLayout;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    SharedPreferences preferences;
    String USER_ID;
    String ROLE_OF_USER;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mToolbar = findViewById(R.id.myNotificationBack);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout=findViewById(R.id.notificationTabLayout);
        frameLayout=findViewById(R.id.notificationFrameLayout);

        preferences= PreferenceManager.getDefaultSharedPreferences(NotificationActivity.this);
        USER_ID = preferences.getString("Name", "null");

        if (USER_ID.equals("null")) {
            Log.i("User not able to add driver without login .Please login !!", "");
        }
        else {
            Log.i("Call ID", USER_ID);
            APIInterface service = SingletonRetrofit.getAPIInterface();
            Call<UserId> call = service.getUserData(USER_ID);
            call.enqueue(new Callback<UserId>() {
                @Override
                public void onResponse(Call<UserId> call, Response<UserId> response) {

                    if (response.body() != null) {
                        User user_data=response.body().getUser();
                        if (user_data!=null)
                        {
                            Integer roleOfuser=user_data.getRoles();
                            ROLE_OF_USER=String.valueOf(roleOfuser);
                            Log.i("Role:",ROLE_OF_USER);
                            if (ROLE_OF_USER.equals("0"))
                            {
//            No notification
                                tabLayout.setVisibility(View.GONE);
                                frameLayout.setVisibility(View.GONE);

                            }
                            else if (ROLE_OF_USER.equals("1"))
                            {
//            Host Notification
                                fragment = new HostNotification();
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.notificationFrameLayout, fragment);
                                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                fragmentTransaction.commit();
                                tabLayout.setVisibility(View.GONE);
                            }
                            else if (ROLE_OF_USER.equals("2"))
                            {
                                fragment = new DriverNotification();
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.notificationFrameLayout, fragment);
                                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                fragmentTransaction.commit();
                                tabLayout.setVisibility(View.GONE);

                            }
                            else if (ROLE_OF_USER.equals("3"))
                            {
//            Host+Driver Notification
                                fragment = new DriverNotification();
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.notificationFrameLayout, fragment);
                                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                fragmentTransaction.commit();
                                tabLayout.setVisibility(View.VISIBLE);
                            }


                        }

                    }
                }

                @Override
                public void onFailure(Call<UserId> call, Throwable t) {

                }
            });
        }



        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 20, 0);
            tab.requestLayout();
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new DriverNotification();
                        break;
                    case 1:
                        fragment = new HostNotification();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.notificationFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onResume() {
        mToolbar = findViewById(R.id.myNotificationBack);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tabLayout=findViewById(R.id.notificationTabLayout);
        frameLayout=findViewById(R.id.notificationFrameLayout);

        preferences= PreferenceManager.getDefaultSharedPreferences(NotificationActivity.this);
        USER_ID = preferences.getString("Name", "null");
//        ROLE_OF_USER=preferences.getString("ROLE","null");

        if (USER_ID.equals("null")) {
            Log.i("User not able to add driver without login .Please login !!", "");
        }
        else {
            Log.i("Call ID", USER_ID);
            APIInterface service = SingletonRetrofit.getAPIInterface();
            Call<UserId> call = service.getUserData(USER_ID);
            call.enqueue(new Callback<UserId>() {
                @Override
                public void onResponse(Call<UserId> call, Response<UserId> response) {

                    if (response.body() != null) {
                        User user_data=response.body().getUser();
                        if (user_data!=null)
                        {
                            Integer roleOfuser=user_data.getRoles();
                            ROLE_OF_USER=String.valueOf(roleOfuser);
                            Log.i("Role:",ROLE_OF_USER);
                            if (ROLE_OF_USER.equals("0"))
                            {
//            No notification
                                tabLayout.setVisibility(View.GONE);
                                frameLayout.setVisibility(View.GONE);

                            }
                            else if (ROLE_OF_USER.equals("1"))
                            {
//            Host Notification
                                fragment = new HostNotification();
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.notificationFrameLayout, fragment);
                                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                fragmentTransaction.commit();
                                tabLayout.setVisibility(View.GONE);
                            }
                            else if (ROLE_OF_USER.equals("2"))
                            {
                                fragment = new DriverNotification();
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.notificationFrameLayout, fragment);
                                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                fragmentTransaction.commit();
                                tabLayout.setVisibility(View.GONE);

                            }
                            else if (ROLE_OF_USER.equals("3"))
                            {
//            Host+Driver Notification
                                fragment = new DriverNotification();
                                fragmentManager = getSupportFragmentManager();
                                fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.notificationFrameLayout, fragment);
                                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                fragmentTransaction.commit();
                                tabLayout.setVisibility(View.VISIBLE);
                            }


                        }

                    }
                }

                @Override
                public void onFailure(Call<UserId> call, Throwable t) {

                }
            });
        }



        for(int i=0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(0, 0, 20, 0);
            tab.requestLayout();
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new DriverNotification();
                        break;
                    case 1:
                        fragment = new HostNotification();
                        break;
                }
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.notificationFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        super.onResume();
    }
}