package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import datamodel.APIInterface;
import datamodel.Register;
import datamodel.User1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.elshare.utils.NetworkUtil;
import com.example.elshare.utils.SingletonRetrofit;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class landing extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    public static Context applicationContext;
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    ImageView imageview;
    Dialog myDialog;
    Register data_reg;
    EditText Email, Phone, Pass, Referd, confirm_pass;
    private AccessToken accessToken;
    CallbackManager callbackManager;
    String fb_email;
    ImageButton imagebutton, google_sign, hide_show, facebook_login;
    Button sign_up;
    Button skip_btn;
    Button sign_in;
    CheckBox terms_condistion;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
    boolean isEmailValid, isPasswordValid, isConfirmPass;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        terms_condistion = findViewById(R.id.t_c_checkbox);
        applicationContext = landing.this.getApplicationContext();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        google_sign = findViewById(R.id.imageButton4);
        google_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status = NetworkUtil.getConnectivityStatusString(landing.this);
                if (TextUtils.isEmpty(status)) {
                    status = "No Internet Connection";
                } else {
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(intent, RC_SIGN_IN);
                }
            }
        });
        facebook_login = findViewById(R.id.imageButton5);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();

        facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logInWithReadPermissions(landing.this, Arrays.asList("public_profile", "email", "user_friends"));
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(landing.this, Arrays.asList("public_profile", "email", "user_friends"));
                }
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        Log.i("Log Result", String.valueOf(loginResult));
                        Log.i("F_S", loginResult.getAccessToken().getSource().name());
                        //Registration api hit here.
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(final JSONObject object, GraphResponse response) {
                                        try {
                                            Log.i("ID:", object.optString("id"));
                                            Log.i("name:", object.optString("name"));

                                            try {
                                                Log.i("Email_id", object.getString("email"));
                                                fb_email = object.getString("email");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        APIInterface service = SingletonRetrofit.getAPIInterface();

                                        Call<Register> call = service.createUser(
                                                fb_email, null
                                        );

                                        Log.i("Call", String.valueOf(call.request().url()));
                                        call.enqueue(new Callback<Register>() {
                                            @Override
                                            public void onResponse(Call<Register> call, Response<Register> response) {
                                                if (response.body() != null) {

                                                    User1 us1 = response.body().getUser();
                                                    String my_id = String.valueOf(us1.getId());
                                                    Log.i("User:", String.valueOf(response.body().getUser()));

                                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    editor.putString("Name", my_id);
                                                    String email=us1.getEmail();
                                                    editor.putString("EMAIL", email);
                                                    editor.apply();
                                                    Toast.makeText(getApplicationContext(), "User register successfully", Toast.LENGTH_LONG).show();
//                                                    showPopUp3();
                                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(landing.this);
                                                    alertDialogBuilder.setTitle("User register successfully!!");
                                                    alertDialogBuilder.setMessage("Please verify your email id to continue!!!");
                                                    alertDialogBuilder.setPositiveButton("Ok",
                                                            new DialogInterface.OnClickListener() {

                                                                @Override
                                                                public void onClick(DialogInterface arg0, int arg1) {
                                                                    Intent intent = new Intent(landing.this, InitialMyProfile.class);
                                                                    startActivity(intent);
                                                                }
                                                            });
                                                    AlertDialog alertDialog = alertDialogBuilder.create();
                                                    alertDialog.show();

                                                } else {
                                                    Toast.makeText(getApplicationContext(), "User id and password already register.Please login", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(landing.this, LoginActivity.class);
                                                    startActivity(intent);
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Register> call, Throwable t) {
                                                //  progressDialog.dismiss();
                                                Log.i("Error", String.valueOf(t.getMessage()));
                                                Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_LONG).show();
                                            }


                                        });
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();


                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(landing.this, "Facebook Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(landing.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        myDialog = new Dialog(this);
        imagebutton = findViewById(R.id.imageButton3);
        imagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(landing.this, otp.class);
                startActivity(intent);
            }
        });

        sign_in = findViewById(R.id.button2);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(landing.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        skip_btn = findViewById(R.id.button132);
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipme(v);
                Intent i = new Intent(landing.this, login_home.class);
                String strName = "SKIP_FOR_NOW";
                i.putExtra("SKIP_FOR_NOW", strName);
                String editTextValue = i.getStringExtra("valueId");
                startActivity(i);
            }
        });


        sign_up = findViewById(R.id.button6);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Email = (com.google.android.material.textfield.TextInputEditText) findViewById(R.id.editText7);
                Pass = (com.google.android.material.textfield.TextInputEditText) findViewById(R.id.editText8);
                confirm_pass = (com.google.android.material.textfield.TextInputEditText) findViewById(R.id.editText67);
                if (Email.getText().toString().isEmpty()) {
                    Email.setError(getResources().getString(R.string.email_error));
                    isEmailValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {
                    Email.setError(getResources().getString(R.string.error_invalid_email));
                    isEmailValid = false;

                } else {
                    isEmailValid = true;
                }
                if (Pass.getText().toString().isEmpty()) {
                    Pass.setError(getResources().getString(R.string.password_error));
                    isPasswordValid = false;
                } else if (Pass.getText().length() < 8 && !isValidPassword(Pass.getText().toString())) {
                    Pass.setError(getResources().getString(R.string.error_invalid_password));
                    isPasswordValid = false;
                } else {
                    isPasswordValid = true;
                }
                if (terms_condistion.isChecked()) {
                    terms_condistion.isChecked();
                } else {
                    Toast.makeText(getApplicationContext(), "Select terms and condition", Toast.LENGTH_LONG).show();
                }


                if (Pass.getText().toString().equals(confirm_pass.getText().toString())){
                    isConfirmPass = true;
                }
                else {
                    isConfirmPass = false;
                    confirm_pass.setError(getResources().getString(R.string.conform_password_error));
                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_LONG).show();
                }

                if (isEmailValid && isPasswordValid && terms_condistion.isChecked() && isConfirmPass) {
                    String email = Email.getText().toString().trim();
                    String password = Pass.getText().toString().trim();
                    APIInterface service = SingletonRetrofit.getAPIInterface();

                    Call<Register> call = service.createUser(
                            email, password
                    );

                    Log.i("Call", String.valueOf(call));
                    call.enqueue(new Callback<Register>() {
                        @Override
                        public void onResponse(Call<Register> call, Response<Register> response) {
                            if (response.body() != null) {
                                User1 us1 = response.body().getUser();
                                String my_id = String.valueOf(us1.getId());
                                String email=us1.getEmail();

                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Name", my_id);
                                editor.putString("EMAIL", email);
                                editor.apply();
                                android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(landing.this);
                                alertDialogBuilder.setTitle("User register successfully!!");
                                alertDialogBuilder.setMessage("Please verify your email id to continue!!!");
                                alertDialogBuilder.setPositiveButton("Ok",
                                        new DialogInterface.OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                Intent intent = new Intent(landing.this, InitialMyProfile.class);
                                                startActivity(intent);
                                            }
                                        });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();


//                                showPopUp2(v);

                            } else {
                                Toast.makeText(getApplicationContext(), "User id and password already register.Please login", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(landing.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        }


                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {
                            Log.i("Error", String.valueOf(t.getMessage()));
                            Toast.makeText(getApplicationContext(), "No internet", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }


    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (tokenTracker == null) {
                Toast.makeText(landing.this, "User Logged out", Toast.LENGTH_LONG).show();
            } else {

            }
        }
    };

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public void skipme(View v) {
        Intent intent = new Intent(landing.this, login_home.class);
        startActivity(intent);
    }

    public void hooray(View v) {
        Button cancle;
        myDialog=new Dialog(v.getRootView().getContext(), R.style.PauseDialog);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        myDialog.setContentView(R.layout.terms_condition);
        myDialog.setTitle("Terms and conditions");
        cancle = (Button) myDialog.findViewById(R.id.button135);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    public void showPopUp2(View v) {
        TextView txtclose, myprofile_text;
        Button btnHost_list, find_char_account, driver_b;
        myDialog.setContentView(R.layout.popupwindow);
        myprofile_text = myDialog.findViewById(R.id.textView152);
        btnHost_list = myDialog.findViewById(R.id.button3);
        find_char_account = myDialog.findViewById(R.id.button33);
        driver_b = myDialog.findViewById(R.id.button137);

        myprofile_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(landing.this, login_home.class);
                String strName = "profile_my";
                i.putExtra("MY_PROFILE", strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });

        myDialog.show();
        btnHost_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(landing.this, login_home.class);
                String strName = "myStr";
                i.putExtra("BECOME_HOST", strName);
                Log.i("Tag", "Command  found: " + strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });

        find_char_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(landing.this, login_home.class);
                String strName = "mystation";
                i.putExtra("FIND_STATION", strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });

        driver_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(landing.this, login_home.class);
                String strName = "itsme";
                i.putExtra("MY_ACCOUNT", strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            assert result != null;
            handleSignInResult(result);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            final GoogleSignInAccount account = result.getSignInAccount();
            Log.i("User name: ", account.getDisplayName());
            Log.i("Email: ", account.getEmail());
            Log.i("Id: ", account.getId());

            APIInterface service = SingletonRetrofit.getAPIInterface();

            Call<Register> call = service.createUser(
                    account.getEmail(), null
            );

            Log.i("Call", String.valueOf(call.request().url()));
            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    if (response.body() != null) {
                        User1 us1 = response.body().getUser();
                        String my_id = String.valueOf(us1.getId());
                        Log.i("Call2", String.valueOf(us1.getId()));
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Name", my_id);
                        editor.putString("Logout", "GOOGLE");
                        String email=us1.getEmail();
                        editor.putString("EMAIL", email);
                        editor.apply();
//                        showPopUp3();
                        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(landing.this);
                        alertDialogBuilder.setTitle("User register successfully!!");
                        alertDialogBuilder.setMessage("Please verify your email id to continue!!!");
                        alertDialogBuilder.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent intent = new Intent(landing.this, InitialMyProfile.class);
                                        startActivity(intent);
                                    }
                                });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();


                    } else {
                        Toast.makeText(getApplicationContext(), account.getEmail() + " already exist.Please login ", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(landing.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
                    //  progressDialog.dismiss();
                    Log.i("Error", String.valueOf(t.getMessage()));
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
        }
    }

    private void showPopUp3() {
        TextView txtclose, myprofile_text;
        Button btnHost_list, find_char_account, driver_b;
        myDialog.setContentView(R.layout.popupwindow);
        txtclose = (TextView) myDialog.findViewById(R.id.textView13);
        myprofile_text = (TextView) myDialog.findViewById(R.id.textView152);
        btnHost_list = (Button) myDialog.findViewById(R.id.button3);
        find_char_account = (Button) myDialog.findViewById(R.id.button33);
        driver_b = (Button) myDialog.findViewById(R.id.button137);

        myprofile_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(landing.this, login_home.class);
                String strName = "profile_my";
                i.putExtra("MY_PROFILE", strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });
        myDialog.show();
        btnHost_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(landing.this, login_home.class);
                String strName = "myStr";
                i.putExtra("BECOME_HOST", strName);
                Log.i("Tag", "Command  found: " + strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });

        find_char_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(landing.this, login_home.class);
                String strName = "mystation";
                i.putExtra("FIND_STATION", strName);
                startActivity(i);
                myDialog.dismiss();


            }
        });

        driver_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(landing.this, login_home.class);
                String strName = "itsme";
                i.putExtra("MY_ACCOUNT", strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });

    }

    private void gotoProfile() {
        Intent intent = new Intent(landing.this, login_home.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
    }
}
