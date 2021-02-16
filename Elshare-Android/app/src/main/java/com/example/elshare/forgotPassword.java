package com.example.elshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.elshare.utils.SingletonRetrofit;
import com.facebook.login.Login;

import datamodel.APIInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgotPassword extends AppCompatActivity {

    Toolbar mToolbar;
    Button mSubmit;
    EditText mEmail;
    Boolean isEmailValid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mToolbar=findViewById(R.id.forgotPasswordBack);
        mEmail=findViewById(R.id.forgotPasswordEmail);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mSubmit=findViewById(R.id.submitForgotPassword);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEmail.getText().toString().isEmpty()) {
                    mEmail.setError(getResources().getString(R.string.email_error));
                    isEmailValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
                    mEmail.setError(getResources().getString(R.string.error_invalid_email));
                    isEmailValid = false;

                } else {
                    isEmailValid = true;
                }
                if (isEmailValid) {
                    APIInterface service = SingletonRetrofit.getAPIInterface();
                    Call<ResponseBody> call = service.forgotPassword(mEmail.getText().toString());
                    Log.i("Forgot pass:", String.valueOf(call.request().url()));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() != null) {
                                Toast.makeText(getApplicationContext(), "Password rest link send on your mail.Please check your Inbox", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
    }
}