package com.example.elshare;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.elshare.utils.ElshareConstants;
import com.example.elshare.utils.RequiredFieldUtil;
import com.example.elshare.utils.SigletonRetrofitService;
import com.example.elshare.utils.SingletonRetrofit;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import datamodel.APIInterface;
import datamodel.MyProfilePojo;
import datamodel.MyProfileUser;
import datamodel.NotificationHost;
import datamodel.NotificationHostPOJO;
import datamodel.User;
import datamodel.UserId;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class my_profile_frag extends Fragment implements View.OnClickListener
{
    TextInputEditText mName;
    TextInputEditText mMobile;
    TextInputEditText mEmail;
    ImageView mProfilePicture;
    Button mBrowseIdProof;
    Button mSubmitProfile;

    SharedPreferences preferences;
    String USER_ID;
    String EMAIL;
    String ROLE_OF_USER;
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
    TextView deactivateAccount;
    TextView mNotificationBladge;
    Integer count_host_str;
    Integer count_driver_str;
    Toolbar mToolbar;
    private List<NotificationHost> notification_object;
    FrameLayout notificationFrame;


    @SuppressLint({"LongLogTag", "SetTextI18n"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_myprofile, container, false);

        mProfilePicture= rootView.findViewById(R.id.profileImageView);
        mName=rootView.findViewById(R.id.firstNameProfile);
        mMobile=rootView.findViewById(R.id.mobileProfile);
        mEmail=rootView.findViewById(R.id.emailIdProfile);
        mBrowseIdProof=rootView.findViewById(R.id.browseIdProofProfile);
        mSubmitProfile=rootView.findViewById(R.id.saveProfile);
        deactivateAccount=rootView.findViewById(R.id.myProfileDeactivate);
        mNotificationBladge=rootView.findViewById(R.id.notificationMyProfileBaldge);
        notificationFrame=rootView.findViewById(R.id.NotificationMyProfileFrame);
        notificationFrame.setOnClickListener(this);
        mNotificationBladge.setOnClickListener(this);

        preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
        USER_ID = preferences.getString("Name", "");
        ROLE_OF_USER=preferences.getString("ROLE","null");
        EMAIL=preferences.getString("EMAIL","");
        if (ROLE_OF_USER.equals("0") ||ROLE_OF_USER.equals("null"))
        {
            deactivateAccount.setVisibility(View.GONE);
        }
        else
        {
            deactivateAccount.setVisibility(View.VISIBLE);
        }

        mEmail.setText(EMAIL);
        if (USER_ID.isEmpty()) {
            Log.i("User not able to add driver without login .Please login !!", "");
        }
        else {
            Log.i("Call ID", USER_ID);
            Log.i("ROLE",ROLE_OF_USER);
            fetchUserDetail();
            fetchNotificationCount();
            setCount();
            Log.i("here","1");
//            Log.i("Here:",String.valueOf(count_driver_str+count_host_str));
            mNotificationBladge.setText("0");

        }


        mProfilePicture.setOnClickListener(this);
        mBrowseIdProof.setOnClickListener(this);
        mSubmitProfile.setOnClickListener(this);
        deactivateAccount.setOnClickListener(this);
        return rootView;

    }

    private void setCount()
    {


    }

    @SuppressLint("SetTextI18n")
    private void fetchNotificationCount()
    {


        APIInterface service = SingletonRetrofit.getAPIInterface();
        Call<NotificationHostPOJO> call = service.getHostNotificationCount(USER_ID);
        Log.i("Url=", String.valueOf(call.request().url()));
        call.enqueue(new Callback<NotificationHostPOJO>() {
            @Override
            public void onResponse(Call<NotificationHostPOJO> call, Response<NotificationHostPOJO> response) {
                if (response.body() != null) {
                    notification_object=response.body().getNotification();
                     count_host_str=notification_object.size();
                     Integer count=Integer.parseInt(mNotificationBladge.getText().toString());
                     int addBoth=count_host_str+count;
                     Log.i("Both 1", String.valueOf(addBoth));
                     if (ROLE_OF_USER.equals("2"))
                     {
                         mNotificationBladge.setText(String.valueOf(count));
                     }
                     else {
                         mNotificationBladge.setText(String.valueOf(addBoth));
                     }

                } else { }
            }

            @Override
            public void onFailure(Call<NotificationHostPOJO> call, Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
                Toast.makeText(getContext(),String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
            }
        });
        Call<NotificationHostPOJO> call2 = service.getDriverNotificationCount(USER_ID);
        Log.i("Url=", String.valueOf(call2.request().url()));
        call2.enqueue(new Callback<NotificationHostPOJO>() {
            @Override
            public void onResponse(Call<NotificationHostPOJO> call2, Response<NotificationHostPOJO> response) {
                if (response.body() != null) {
                    notification_object=response.body().getNotification();
                    count_driver_str=notification_object.size();
                    Integer count=Integer.parseInt(mNotificationBladge.getText().toString());
                    int addBoth=count_driver_str+count;
                    Log.i("Both 2", String.valueOf(addBoth));
                    if (ROLE_OF_USER.equals("1"))
                    {
                        mNotificationBladge.setText(String.valueOf(count));
                    }
                    else {
                        mNotificationBladge.setText(String.valueOf(addBoth));
                    }

                } else { }
            }
            @Override
            public void onFailure(Call<NotificationHostPOJO> call2, Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
                Toast.makeText(getContext(),String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void fetchUserDetail()
    {
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        APIInterface service = SingletonRetrofit.getAPIInterface();

        Call<MyProfileUser> call = service.fetchMyProfile(USER_ID);
        Log.i("Url=", String.valueOf(call.request().url()));
        call.enqueue(new Callback<MyProfileUser>() {
            @Override
            public void onResponse(Call<MyProfileUser> call, Response<MyProfileUser> response) {
                if (response.body() != null) {
                    final MyProfileUser user_data = response.body();
                    if (user_data.getUser()!=null)
                    {
                        mSubmitProfile.setText("Update");
                        deactivateAccount.setVisibility(View.VISIBLE);
                        mName.setText(user_data.getUser().getName());
                        mMobile.setText(String.valueOf(user_data.getUser().getMobile()));
                        Log.i("Mobile",String.valueOf(user_data.getUser().getMobile()));
                        APIInterface service = SigletonRetrofitService.getAPIInterface();
                        final Call<ResponseBody> imageCall = service.fetchProfilePicture(user_data.getUser().getPicture());
                        imageCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.body() != null) {
                                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                    mProfilePicture.setImageBitmap(bmp);
                                    imageFileFromSever = new File(getContext().getCacheDir(), "temp");
                                    try {
                                        imageFileFromSever.createNewFile();
                                        Bitmap bitmap = bmp;
                                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                                        byte[] bitmapdata = bos.toByteArray();
                                        FileOutputStream fos = null;
                                        fos = new FileOutputStream(imageFileFromSever);
                                        fos.write(bitmapdata);
                                        fos.flush();
                                        fos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    mProgressDialog.dismiss();
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                mProgressDialog.dismiss();
                            }
                        });
                        mBrowseIdProof.setText(user_data.getUser().getIdPicture());

                        final Call<ResponseBody> imageCallForIdProof = service.fetchProfileId(user_data.getUser().getIdPicture());
                        imageCallForIdProof.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> imageCallForIdProof, Response<ResponseBody> response) {
                                if (response.body() != null) {
                                    Bitmap bmpForId = BitmapFactory.decodeStream(response.body().byteStream());
                                    idFileFromSever = new File(getContext().getCacheDir(), "tempId");
                                    try {
                                        idFileFromSever.createNewFile();
                                        Bitmap bitmap = bmpForId;
                                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                                        byte[] bitmapdataForId = bos.toByteArray();
                                        FileOutputStream fos = null;
                                        fos = new FileOutputStream(idFileFromSever);
                                        fos.write(bitmapdataForId);
                                        fos.flush();
                                        fos.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    mProgressDialog.dismiss();
                                }
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> imageCallForIdProof, Throwable t) {
                                mProgressDialog.dismiss();
                            }
                        });
                        mBrowseIdProof.setText(user_data.getUser().getIdPicture());


                    }
                    else
                    {
                        mSubmitProfile.setText("Complete profile");
                        mSubmitProfile.setAllCaps(false);
                        deactivateAccount.setVisibility(View.GONE);
                        mProgressDialog.dismiss();
                        notificationFrame.setVisibility(View.GONE);
                    }

                } else {

                }

            }

            @Override
            public void onFailure(Call<MyProfileUser> call, Throwable t) {
                Log.i("Error", String.valueOf(t.getMessage()));
                Toast.makeText(getContext(),"Please check network!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.profileImageView:
                showPictureDialog();
                break;
            case R.id.browseIdProofProfile:
                Intent businessId_intent = new Intent(Intent.ACTION_PICK);
                businessId_intent.setType("image/*");
                startActivityForResult(businessId_intent, select_IdProof);
                break;
            case R.id.saveProfile:
                submitProfile();
                break;
            case R.id.myProfileDeactivate:
                if (USER_ID.isEmpty()) {
                    Log.i("You do not have account at yet!", "");
                }else {
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
                                    deleteAccount(roleOfuser);
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<UserId> call, Throwable t) {

                        }
                    });
                }
                break;
            case R.id.NotificationMyProfileFrame:
                Intent intent=new Intent(getContext(), NotificationActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void deleteAccount(Integer roleOfuser)
    {
        {
            if (roleOfuser==2) {
                Log.i("It a driver", "");
                deleteDriver();
            }
            else if (roleOfuser==1) {
                Log.i("It a host", "");
                deleteHost();
            }
            else if (roleOfuser==3) {
                Log.i("It a BOTH", "");
                showAccountDeleteDialog();
            }
            else if (roleOfuser==0)
            {

            }

        }
    }

    private void showAccountDeleteDialog()
    {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
        deleteDialog.setTitle("Select Account");
        String[] pictureDialogItems = {
                "1)Delete Driver",
                "2)Delete Host",
                "3)Delete both driver and host"
        };
        deleteDialog.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        deleteDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                deleteDriver();
                                break;
                            case 1:
                                deleteHost();
                                break;
                            case 2:
                                deleteBoth();
                                break;
                        }
                    }
                });
        deleteDialog.show();

    }

    private void deleteBoth() {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
        deleteDialog.setTitle("Do you want to delete both Profile");

        deleteDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        APIInterface service = SingletonRetrofit.getAPIInterface();
                        Call<ResponseBody> call = service.deleteBoth( USER_ID);
                        Log.i("Url=", String.valueOf(call.request().url()));
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.body() != null) {
                                    Toast.makeText(getContext(), "Both profile delete successfully!!!", Toast.LENGTH_LONG).show();
                                    getActivity().finish();

                                } else {
                                    Toast.makeText(getContext(), "Both profile not delete!!!", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.i("Error", String.valueOf(t.getMessage()));
                                Toast.makeText(getContext(),String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
        deleteDialog.show();
    }

    private void deleteHost() {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
        deleteDialog.setTitle("Do you want to delete host Profile");

        deleteDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        APIInterface service = SingletonRetrofit.getAPIInterface();
                        Call<ResponseBody> call = service.deleteHost( USER_ID);
                        Log.i("Url=", String.valueOf(call.request().url()));
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.body() != null) {
                                    Toast.makeText(getContext(), "Host delete successfully!!!", Toast.LENGTH_LONG).show();
                                    getActivity().finish();

                                } else {
                                    Toast.makeText(getContext(), "Host not delete!!!", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.i("Error", String.valueOf(t.getMessage()));
                                Toast.makeText(getContext(),String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
        deleteDialog.show();
    }

    private void deleteDriver()
    {
        final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
        deleteDialog.setTitle("Do you want to delete driver Profile");

        deleteDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        APIInterface service = SingletonRetrofit.getAPIInterface();
                        Call<ResponseBody> call = service.deleteDriver( USER_ID);
                        Log.i("Url=", String.valueOf(call.request().url()));
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.body() != null) {
                                    Toast.makeText(getContext(), "Driver delete successfully!!!", Toast.LENGTH_LONG).show();
                                    getActivity().finish();


                                } else {
                                    Toast.makeText(getContext(), "Driver not delete!!!", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.i("Error", String.valueOf(t.getMessage()));
                                Toast.makeText(getContext(),String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
        deleteDialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            showPictureDialog();
        }
    }
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getContext());
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

    private void submitProfile()
    {

        final ArrayList<EditText> editTextArrayList = new ArrayList<>();
        editTextArrayList.add(mName);
        editTextArrayList.add(mMobile);

        if(RequiredFieldUtil.isRequiredFieldEmpty(editTextArrayList)){
            Toast.makeText(getContext(), "The required Field can not be blank", Toast.LENGTH_LONG).show();
            return;
        }

        else {

            if(imageuri!=null) {
                image_file = new File(profileRealPath);
                profileImageBody = RequestBody.create(MediaType.parse("multipart/form-data"), image_file);
            }
            else
            {
                profileImageBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFileFromSever);

            }
            if (businessId_uri!=null) {
                final File id_file = new File(idRealPath);
                businessIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), id_file);
            }
            else
            {
                businessIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), idFileFromSever);

            }

            nameBody = RequestBody.create(MediaType.parse("multipart/form-data"), mName.getText().toString());
            mobileBody = RequestBody.create(MediaType.parse("multipart/form-data"), mMobile.getText().toString());
            userIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), USER_ID);

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
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
                            alertDialogBuilder.setMessage("Profile  upload  successfully!!!");
                            alertDialogBuilder.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {

                                        }
                                    });


                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {
                            Toast.makeText(getContext(), user_data.getMessage(), Toast.LENGTH_LONG).show();

                        }

                    } else {
                        Toast.makeText(getContext(), "Profile not uploaded!!!", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<MyProfilePojo> call, Throwable t) {
                    Log.i("Error", String.valueOf(t.getMessage()));
                    Toast.makeText(getContext(), String.valueOf(t.getMessage()), Toast.LENGTH_LONG).show();
                }
            });
        }

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
                        profileRealPath = getRealPathFromUri(getContext(),
                                imageuri);
                        String real_Path = getRealPathFromUri(getContext(),
                                imageuri);
                        realPath=("Real Path: " + real_Path);

                        Bitmap bitmap = decodeUri(getContext(), imageuri, 300);
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("USER_IMAGE",String.valueOf(imageuri));
                        editor.commit();
                        if (bitmap != null)
                            mProfilePicture.setImageBitmap(bitmap);
                        else
                            Toast.makeText(getContext(),
                                    "Error while decoding image.",
                                    Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "File not found.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case select_IdProof:
                if (resultcode == RESULT_OK) {
                    businessId_uri = imagereturnintent.getData();
                    idRealPath = getRealPathFromUri(getContext(),
                            businessId_uri);
                    String real_Path = getRealPathFromUri(getContext(),
                            businessId_uri);
                    String filename=real_Path.substring(real_Path.lastIndexOf("/")+1);
                    Log.i("Real Path:",filename);
                    mBrowseIdProof.setText(filename);
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("USER_ID_PROOF", String.valueOf(businessId_uri));
                    editor.commit();
                }
                break;

            case CAMERA:
                if (resultcode == RESULT_OK) {
                    imageuri=imagereturnintent.getData();
                    try {
                        Bitmap bitmap = decodeUri(getContext(), imageuri, 300);
                        if (bitmap!=null)
                        {
                            mProfilePicture.setImageBitmap(bitmap);
                            profileRealPath = getRealPathFromUri(getContext(),
                                    imageuri);
                        }
                        else
                        {
                            Toast.makeText(getContext(),
                                    "Error while decoding image.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "File not found.",
                                Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
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
