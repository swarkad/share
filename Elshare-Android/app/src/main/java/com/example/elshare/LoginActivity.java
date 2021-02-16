package com.example.elshare;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import datamodel.APIInterface;
import datamodel.Register;
import datamodel.User1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button login_btn, sign_up;
    ImageButton imageButton, google_login, facebook_login;
    EditText edit_mail, edit_pass;
    Dialog myDialog;
    EditText Email, Phone, Pass, Referd;
    boolean isEmailValid, isPasswordValid;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    String fb_email;
    CallbackManager callbackManager;
    private AccessToken accessToken;
    com.google.android.material.switchmaterial.SwitchMaterial switchMaterial;
    TextView mForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mForgotPassword=findViewById(R.id.forgotPassword);
        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), forgotPassword.class);
                startActivity(intent);
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        myDialog = new Dialog(this);

        edit_mail = (com.google.android.material.textfield.TextInputEditText) findViewById(R.id.editText69);
        edit_pass = (com.google.android.material.textfield.TextInputEditText) findViewById(R.id.editText70);
        login_btn = findViewById(R.id.button15);
        google_login = findViewById(R.id.google_log);
        facebook_login = findViewById(R.id.fb_log);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Email = (com.google.android.material.textfield.TextInputEditText) findViewById(R.id.editText69);
                Pass = (com.google.android.material.textfield.TextInputEditText) findViewById(R.id.editText70);
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

                if (isEmailValid && isPasswordValid) {
                    String email = edit_mail.getText().toString();
                    String password = edit_pass.getText().toString();
                    APIInterface service = SingletonRetrofit.getAPIInterface();
                    Call<Register> call = service.userLogin(email, password);
                    Log.i("Call", String.valueOf(call));
                    Log.i("mail:", String.valueOf(email));
                    Log.i("pass:", String.valueOf(password));

                    call.enqueue(new Callback<Register>() {
                        @Override
                        public void onResponse(Call<Register> call, Response<Register> response) {
                            if (response.body() != null) {
                                User1 us1 = response.body().getUser();
                                String my_id = String.valueOf(us1.getId());
                                Log.i("Call2", String.valueOf(us1.getId()));
                                Log.i("token", response.body().getToken());
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("Name", my_id);
                                String email=us1.getEmail();
                                editor.putString("EMAIL", email);
                                editor.putString("ROLE", String.valueOf(us1.getRoles()));

                                editor.putString("token", response.body().getToken());
                                editor.apply();
                                int role_id = response.body().getUser().getRoles();
                                if (role_id == 0) {
                                    Intent i = new Intent(LoginActivity.this, login_home.class);
                                    String strName = "mystation";
                                    i.putExtra("FIND_STATION", strName);
                                    startActivity(i);
                                } else if (role_id == 1) {
                                    Intent i = new Intent(LoginActivity.this, login_home.class);
                                    String strName = "my_list";
                                    i.putExtra("MY_LIST", strName);
                                    startActivity(i);
                                } else if (role_id == 2) {
                                    Intent i = new Intent(LoginActivity.this, login_home.class);
                                    String strName = "profile_my";
                                    i.putExtra("MY_PROFILE", strName);
                                    startActivity(i);
                                } else {
                                    Intent i = new Intent(LoginActivity.this, login_home.class);
                                    String strName = "mystation";
                                    i.putExtra("FIND_STATION", strName);
                                    startActivity(i);
                                }
                                Toast.makeText(getApplicationContext(), "User login successfully", Toast.LENGTH_LONG).show();
                            } else {
                                Log.i("User not found", String.valueOf(response.message()));
                                Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Register> call, Throwable t) {
                            Log.i("Error", String.valueOf(t.getMessage()));
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        sign_up = findViewById(R.id.button125);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, landing.class);
                startActivity(intent);
            }
        });

        imageButton = findViewById(R.id.imageButton3);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, otp.class);
                startActivity(intent);
            }
        });

        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = NetworkUtil.getConnectivityStatusString(LoginActivity.this);
                if (TextUtils.isEmpty(status)) {
                    status = "No Internet Connection";
                } else {
                    Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                }
            }
        });

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessToken = AccessToken.getCurrentAccessToken();
        facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_friends"));
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_friends"));
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

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(final JSONObject object, GraphResponse response) {
                                        try {
                                            Log.i("ID:", object.optString("id"));
                                            Log.i("name:", object.optString("name"));
                                            Log.i("Email_id", object.optString("email"));

                                            try {
                                                Log.i("Email_id", object.getString("email"));
                                                fb_email = object.optString("email");

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        APIInterface service = SingletonRetrofit.getAPIInterface();

                                        Call<Register> call = service.userLogin(fb_email, null);
                                        Log.i("Call", String.valueOf(call));

                                        call.enqueue(new Callback<Register>() {
                                            @Override
                                            public void onResponse(Call<Register> call, Response<Register> response) {
                                                if (response.body() != null) {
                                                    User1 us1 = response.body().getUser();
                                                    String my_id = String.valueOf(us1.getId());
                                                    Log.i("Call2", String.valueOf(us1.getId()));
                                                    Log.i("token", response.body().getToken());
                                                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                                    SharedPreferences.Editor editor = preferences.edit();
                                                    editor.putString("Name", my_id);
                                                    String email=us1.getEmail();
                                                    editor.putString("EMAIL", email);
                                                    editor.putString("token", response.body().getToken());
                                                    editor.apply();
                                                    int role_id = response.body().getUser().getRoles();
                                                    if (role_id == 0) {
                                                        Intent i = new Intent(LoginActivity.this, login_home.class);
                                                        String strName = "mystation";
                                                        i.putExtra("FIND_STATION", strName);
                                                        startActivity(i);
                                                    } else if (role_id == 1) {
                                                        Intent i = new Intent(LoginActivity.this, login_home.class);
                                                        String strName = "my_list";
                                                        i.putExtra("MY_LIST", strName);
                                                        startActivity(i);
                                                    } else if (role_id == 2) {
                                                        Intent i = new Intent(LoginActivity.this, login_home.class);
                                                        String strName = "profile_my";
                                                        i.putExtra("MY_PROFILE", strName);
                                                        startActivity(i);
                                                    } else {
                                                        Intent i = new Intent(LoginActivity.this, login_home.class);
                                                        String strName = "mystation";
                                                        i.putExtra("FIND_STATION", strName);
                                                        startActivity(i);
                                                    }
                                                    Toast.makeText(getApplicationContext(), "User login successfully", Toast.LENGTH_LONG).show();
                                                } else {
                                                    Log.i("User not found", String.valueOf(response.message()));
                                                    Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Register> call, Throwable t) {
                                                Log.i("Error", String.valueOf(t.getMessage()));
                                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Facebook Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (tokenTracker == null) {
                Toast.makeText(LoginActivity.this, "User Logged out", Toast.LENGTH_LONG).show();
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

    public void showPopUp2(View v) {
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
                Intent i = new Intent(LoginActivity.this, login_home.class);
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
                Intent i = new Intent(LoginActivity.this, login_home.class);
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
                Intent i = new Intent(LoginActivity.this, login_home.class);
                String strName = "mystation";
                i.putExtra("FIND_STATION", strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });

        driver_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, login_home.class);
                String strName = "itsme";
                i.putExtra("MY_ACCOUNT", strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });
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
            assert account != null;
            Log.i("User name: ", account.getDisplayName());
            Log.i("Email: ", account.getEmail());
            Log.i("Id: ", account.getId());

            APIInterface service = SingletonRetrofit.getAPIInterface();
            Call<Register> call = service.userLogin(account.getEmail(), null);
            Log.i("Call", String.valueOf(call));
            Log.i("mail:", String.valueOf(account.getEmail()));

            call.enqueue(new Callback<Register>() {
                @Override
                public void onResponse(Call<Register> call, Response<Register> response) {
                    if (response.body() != null) {
                        User1 us1 = response.body().getUser();
                        String my_id = String.valueOf(us1.getId());
                        Log.i("Call2", String.valueOf(us1.getId()));
                        Log.i("token", response.body().getToken());
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Name", my_id);
                        String email=us1.getEmail();
                        editor.putString("EMAIL", email);
                        editor.putString("token", response.body().getToken());
                        editor.apply();
                        int role_id = response.body().getUser().getRoles();
                        if (role_id == 0) {
                            Intent i = new Intent(LoginActivity.this, login_home.class);
                            String strName = "mystation";
                            i.putExtra("FIND_STATION", strName);
                            startActivity(i);
                        } else if (role_id == 1) {
                            Intent i = new Intent(LoginActivity.this, login_home.class);
                            String strName = "my_list";
                            i.putExtra("MY_LIST", strName);
                            startActivity(i);
                        } else if (role_id == 2) {
                            Intent i = new Intent(LoginActivity.this, login_home.class);
                            String strName = "profile_my";
                            i.putExtra("MY_ACCOUNT", strName);
                            startActivity(i);
                        } else {
                            Intent i = new Intent(LoginActivity.this, login_home.class);
                            String strName = "mystation";
                            i.putExtra("FIND_STATION", strName);
                            startActivity(i);
                        }
                        Toast.makeText(getApplicationContext(), "User login successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Log.i("User not found", String.valueOf(response.message()));
                        Toast.makeText(getApplicationContext(), "User not found", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Register> call, Throwable t) {
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
                Intent i = new Intent(LoginActivity.this, login_home.class);
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
                Intent i = new Intent(LoginActivity.this, login_home.class);
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
                Intent i = new Intent(LoginActivity.this, login_home.class);
                String strName = "mystation";
                i.putExtra("FIND_STATION", strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });

        driver_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, login_home.class);
                String strName = "itsme";
                i.putExtra("MY_ACCOUNT", strName);
                startActivity(i);
                myDialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        finish();
    }

}
