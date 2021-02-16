package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.example.elshare.utils.ElshareConstants;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.navigation.NavigationView;

public class login_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageButton imagebtn;
    ScrollView scroll;
    Dialog myDialog;
    DrawerLayout drawerLayout;
    private NavigationView nv;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentTransaction fragmentTransaction;
    Fragment fragment;
    CharSequence mTitle;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    String newString, newString2;
    SharedPreferences mPrefrances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_home);
        myDialog = new Dialog(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.login_drawer);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        Bundle extras = getIntent().getExtras();
//        if (extras == null) {
//
//        } else {
//            newString = extras.getString("BECOME_HOST");
//            if (newString == null) {
//                newString = extras.getString("MY_ACCOUNT");
//                if (newString == null) {
//                    newString = extras.getString("FIND_STATION");
//                    if (newString == null) {
//                        newString = extras.getString("SKIP_FOR_NOW");
//                        if (newString == null) {
//                            newString = extras.getString("MY_ACCOUNT");
//                            if (newString == null) {
//                                newString = extras.getString("MY_LIST");
//                                if (newString == null) {
//                                    newString = extras.getString("MY_DRIVER");
//                                    if (newString == null) {
//
//                                    } else {
//                                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                        fragmentTransaction.add(R.id.content_frame, new map_frag());
//                                        fragmentTransaction.commit();
//                                    }
//                                } else {
//                                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                    fragmentTransaction.add(R.id.content_frame, new listing_frag());
//                                    fragmentTransaction.commit();
//                                }
//                            } else {
//                                fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                                fragmentTransaction.add(R.id.content_frame, new AccountFragment());
//                                fragmentTransaction.commit();
//                            }
//                        } else {
//                            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.add(R.id.content_frame, new map_frag());
//                            fragmentTransaction.commit();
//                        }
//                    } else {
//                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                        fragmentTransaction.add(R.id.content_frame, new map_frag());
//                        fragmentTransaction.commit();
//                    }
//                } else {
//                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.add(R.id.content_frame, new AccountFragment());
//                    fragmentTransaction.commit();
//                }
//            } else {
//                fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.content_frame, new host_frag());
//                fragmentTransaction.commit();
//            }
//        }

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_frame, new map_frag());
        fragmentTransaction.commit();

        mPrefrances= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ROLE_OF_USER=mPrefrances.getString("ROLE","null");
        nv = findViewById(R.id.nav3);
        Log.i("ROLE:",ROLE_OF_USER);

        if (ROLE_OF_USER.equals("0"))
        {
            nv.getMenu().clear();
            nv.inflateMenu(R.menu.skip_menu);
        }
        else if (ROLE_OF_USER.equals("2"))
        {
            nv.getMenu().clear();
            nv.inflateMenu(R.menu.driver_menu);
        }
        else if (ROLE_OF_USER.equals("1"))
        {
            nv.getMenu().clear();
            nv.inflateMenu(R.menu.host_menu);
        }
        else if (ROLE_OF_USER.equals("3"))
        {
            nv.getMenu().clear();
            nv.inflateMenu(R.menu.driver_host_menu);
        }
        else
        {
            nv.getMenu().clear();
            nv.inflateMenu(R.menu.skip_menu);
        }


//        Bundle extras1=getIntent().getExtras();
//        if (newString==null)
//        {
//
//        }
//       else if(newString.equals(extras1.getString("SKIP_FOR_NOW")))
//        {
//            nv.getMenu().clear();
//            nv.inflateMenu(R.menu.main_menu2);
//            nv.getMenu().findItem(R.id.my_listing).setVisible(false);
//            nv.getMenu().findItem(R.id.my_vehicles).setVisible(false);
//        } else {
//
//            nv.getMenu().clear();
//            nv.inflateMenu(R.menu.main_menu2);
//        }
        nv.setNavigationItemSelectedListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        fragment = null;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Intent intent;
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.find_station:
                fragmentTransaction.replace(R.id.content_frame, new map_frag());
                fragmentTransaction.commit();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                break;
//            case R.id.friend:
//                fragmentTransaction.replace(R.id.content_frame, new friend_frag());
//                fragmentTransaction.commit();
//                menuItem.setChecked(true);
//                drawerLayout.closeDrawers();
//                break;
            case R.id.my_profile:
                fragmentTransaction.replace(R.id.content_frame, new my_profile_frag());
                fragmentTransaction.commit();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.my_account:
                fragmentTransaction.replace(R.id.content_frame, new AccountFragment());
                fragmentTransaction.commit();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.my_listing:
                fragmentTransaction.replace(R.id.content_frame, new listing_frag());
                fragmentTransaction.commit();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                break;

            case R.id.bookingHistoryHost:
                 intent = new Intent(login_home.this, HostBookingHistory.class);
                startActivity(intent);
                menuItem.setCheckable(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.bookingHistoryDriver:
                intent = new Intent(login_home.this, BookingHistoryDriver.class);
                startActivity(intent);
                menuItem.setCheckable(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.paymentHistory:
                intent = new Intent(login_home.this, PaymentHistory.class);
                startActivity(intent);
                menuItem.setCheckable(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.notification:
                intent = new Intent(login_home.this, NotificationActivity.class);
                startActivity(intent);
                menuItem.setCheckable(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.host:
                fragmentTransaction.replace(R.id.content_frame, new BecomeHostFragment());
                fragmentTransaction.commit();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                break;

            case R.id.contact_us:
                fragmentTransaction.replace(R.id.content_frame, new contact_us());
                fragmentTransaction.commit();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                break;

            case R.id.help:
                Toast.makeText(login_home.this, "My Help", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_vehicles:
                intent = new Intent(login_home.this, MyAllVehicle.class);
                startActivity(intent);
                menuItem.setCheckable(true);
                drawerLayout.closeDrawers();
                break;
            case R.id.logout:
                signOut();
        }
        try {
            fragment = (Fragment) new contact_us();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public void showPopUp(View v) {
        myDialog.setContentView(R.layout.filter_window);
        myDialog.show();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private void signOut() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String logout_str;
        logout_str = preferences.getString(ElshareConstants.LOGOUT, null);
        if (!TextUtils.isEmpty(logout_str) && logout_str.equals(ElshareConstants.SP_GOOGLE)) {
            GoogleSignInOptions gso = new GoogleSignInOptions.
                    Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                    build();

            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
            googleSignInClient.signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(getApplicationContext(), ElshareConstants.GOOGLE_SIGN_OUT, Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        } else if (!TextUtils.isEmpty(logout_str) && logout_str.equals(ElshareConstants.SP_FACEBOOK)) {
            LoginManager.getInstance().logOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(getApplicationContext(), ElshareConstants.FACEBOOK_SIGN_OUT, Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Toast.makeText(getApplicationContext(), ElshareConstants.NORMAL_SIGN_OUT, Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
