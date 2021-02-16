package com.example.elshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elshare.utils.ElshareConstants;
import com.example.elshare.utils.RequiredFieldUtil;
import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import datamodel.APIInterface;
import datamodel.MyProfilePojo;
import datamodel.NotificationHost;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InitialMyProfile extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText mName;
    TextInputEditText mMobile;
    TextInputEditText mEmail;
    ImageView mProfilePicture;
    Button mBrowseIdProof;
    Button mSubmitProfile;

    SharedPreferences preferences;
    String USER_ID;
    String EMAIL;
    private final int select_photo = 1;
    public static final int RESULT_OK = -1;
    private final int select_IdProof = 2;
    private final int  CAMERA = 3;
    private static final int REQUEST_WRITE_PERMISSION = ElshareConstants.REQUEST_WRITE_PERMISSION;

    String uriPath;
    String realPath;
    Uri imageuri;
    Uri businessId_uri;

    RequestBody nameBody;
    RequestBody userIdBody;
    RequestBody profileImageBody;
    RequestBody businessIdBody;
    RequestBody mobileBody;
    String profileRealPath;
    String idRealPath;
    FileOutputStream fos;
    File imageFileFromSever;
    File idFileFromSever;
    File image_file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_my_profile);
        mProfilePicture= findViewById(R.id.profileImageView);
        mName=findViewById(R.id.firstNameProfile);
        mMobile=findViewById(R.id.mobileProfile);
        mEmail=findViewById(R.id.emailIdProfile);
        mBrowseIdProof=findViewById(R.id.browseIdProofProfile);
        mSubmitProfile=findViewById(R.id.saveButtonProfile);

        preferences= PreferenceManager.getDefaultSharedPreferences(InitialMyProfile.this);
        USER_ID = preferences.getString("Name", "");
        EMAIL=preferences.getString("EMAIL","");
        mEmail.setText(EMAIL);

        mProfilePicture.setOnClickListener(this);
        mBrowseIdProof.setOnClickListener(this);
        mSubmitProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profileImageView:
                showPictureDialog();
                break;
            case R.id.browseIdProofProfile:
                Intent businessId_intent = new Intent(Intent.ACTION_PICK);
                businessId_intent.setType("image/*");
                startActivityForResult(businessId_intent, select_IdProof);
                break;
            case R.id.saveButtonProfile:
                submitProfile();
                break;
        }
    }

    private void submitProfile() {

        final ArrayList<EditText> editTextArrayList = new ArrayList<>();
        editTextArrayList.add(mName);
        editTextArrayList.add(mMobile);

        if(RequiredFieldUtil.isRequiredFieldEmpty(editTextArrayList)){
            Toast.makeText(InitialMyProfile.this, "The required Field can not be blank", Toast.LENGTH_LONG).show();
            return;
        }

        else {

            if(imageuri!=null) {
                image_file = new File(profileRealPath);
                profileImageBody = RequestBody.create(MediaType.parse("multipart/form-data"), image_file);
            }
            else
            {
                Toast.makeText(InitialMyProfile.this, "Please Select profile Picture", Toast.LENGTH_LONG).show();
            }

            if (businessId_uri!=null) {
                final File id_file = new File(idRealPath);
                businessIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), id_file);
            }
            else
            {
                Toast.makeText(InitialMyProfile.this, "Please Select Id Proof", Toast.LENGTH_LONG).show();
            }


            nameBody = RequestBody.create(MediaType.parse("multipart/form-data"), mName.getText().toString());
            mobileBody = RequestBody.create(MediaType.parse("multipart/form-data"), mMobile.getText().toString());
            userIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), USER_ID);

            final ProgressDialog mProgressDialog = new ProgressDialog(InitialMyProfile.this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.show();

            APIInterface service = SingletonRetrofit.getAPIInterface();
            Call<MyProfilePojo> call = service.uploadMyProfile(USER_ID, nameBody, mobileBody, profileImageBody, businessIdBody);
            Log.i("Url=", String.valueOf(call.request().url()));
            call.enqueue(new Callback<MyProfilePojo>() {
                @Override
                public void onResponse(Call<MyProfilePojo> call, Response<MyProfilePojo> response) {
                    if (response.body() != null) {
                        MyProfilePojo user_data = response.body();
                        if (user_data.isSuccess()) {
                            Log.i("Success:", String.valueOf(user_data.isSuccess()));
                            mProgressDialog.dismiss();
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(InitialMyProfile.this);
                            alertDialogBuilder.setMessage("Profile  upload  successfully!!!");
                            alertDialogBuilder.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            Intent intent = new Intent(InitialMyProfile.this, login_home.class);
                                            startActivity(intent);

                                        }
                                    });


                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {
                            Toast.makeText(InitialMyProfile.this, user_data.getMessage(), Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(InitialMyProfile.this, "Profile not uploaded!!!", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<MyProfilePojo> call, Throwable t) {
                    Log.i("Error", String.valueOf(t.getMessage()));
                    Toast.makeText(InitialMyProfile.this, String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(InitialMyProfile.this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void takePhotoFromCamera()
    {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void choosePhotoFromGallary()
    {
        Intent in = new Intent(Intent.ACTION_PICK);
        in.setType("image/*");
        startActivityForResult(in, select_photo);

    }

    public void onActivityResult(int requestcode, int resultcode,
                                 Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);
        switch (requestcode) {
            case select_photo:
                if (resultcode == RESULT_OK) {
                    try {
                        imageuri = imagereturnintent.getData();
                        File f=new File(String.valueOf(imageuri));
                        uriPath=("URI Path: " + imageuri.toString());
                        profileRealPath = getRealPathFromUri(InitialMyProfile.this,
                                imageuri);
                        String real_Path = getRealPathFromUri(InitialMyProfile.this,
                                imageuri);
                        realPath=("Real Path: " + real_Path);

                        Bitmap bitmap = decodeUri(InitialMyProfile.this, imageuri, 300);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(InitialMyProfile.this);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("USER_IMAGE",String.valueOf(imageuri));
                        editor.commit();
                        if (bitmap != null)
                            mProfilePicture.setImageBitmap(bitmap);
                        else
                            Toast.makeText(InitialMyProfile.this,
                                    "Error while decoding image.",
                                    Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(InitialMyProfile.this, "File not found.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case select_IdProof:
                if (resultcode == RESULT_OK) {
                    businessId_uri = imagereturnintent.getData();
                    idRealPath = getRealPathFromUri(InitialMyProfile.this,
                            businessId_uri);
                    String real_Path = getRealPathFromUri(InitialMyProfile.this,
                            businessId_uri);
                    String filename=real_Path.substring(real_Path.lastIndexOf("/")+1);
                    Log.i("Real Path:",filename);
                    mBrowseIdProof.setText(filename);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(InitialMyProfile.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("USER_ID_PROOF", String.valueOf(businessId_uri));
                    editor.commit();
                }
                break;

            case CAMERA:
                if (resultcode == RESULT_OK) {
                    imageuri=imagereturnintent.getData();
                    try {
                        Bitmap bitmap = decodeUri(InitialMyProfile.this, imageuri, 300);
                        if (bitmap!=null)
                        {
                            mProfilePicture.setImageBitmap(bitmap);
                            profileRealPath = getRealPathFromUri(InitialMyProfile.this,
                                    imageuri);
                        }
                        else
                        {
                            Toast.makeText(InitialMyProfile.this,
                                    "Error while decoding image.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(InitialMyProfile.this, "File not found.",
                                Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(InitialMyProfile.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                }
                break;
            default:break;
        }
    }

    public static Bitmap decodeUri(Context context, Uri uri,
                                   final int requiredSize) throws FileNotFoundException {
        BitmapFactory.Options bitmap_option = new BitmapFactory.Options();
        bitmap_option.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(uri), null, bitmap_option);

        int width_tmp = bitmap_option.outWidth, height_tmp = bitmap_option.outHeight;
        int scale = 1;

        while (true) {
            if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options bitmap_option_2 = new BitmapFactory.Options();
        bitmap_option_2.inSampleSize = scale;
        return BitmapFactory.decodeStream(context.getContentResolver()
                .openInputStream(uri), null, bitmap_option_2);
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] selected_media = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, selected_media, null,
                    null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}