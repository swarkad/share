package com.example.elshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class sign_up extends AppCompatActivity {
    Dialog myDialog;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDialog=new Dialog(this);
        btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewActivity();
            }
        });
    }
    public void openNewActivity(){
//        Intent intent = new Intent(this, landing.class);
//        startActivity(intent);
    }

    public void showPopUp(View v)
    {
        TextView  txtclose;
        Button  btnHost_list,find_char_account;
        myDialog.setContentView(R.layout.popupwindow);
        txtclose=(TextView) myDialog.findViewById(R.id.textView13);
        btnHost_list =(Button) myDialog.findViewById(R.id.button3);
        find_char_account =(Button) myDialog.findViewById(R.id.button33);

        txtclose.setOnClickListener(new  View.OnClickListener() {
            @Override
            public void onClick(View v){
                myDialog.dismiss();
        }
        });
        myDialog.show();
                btnHost_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
//        find_char_account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(sign_up.this,account_frag.class);
//                startActivity(intent);
//            }
//        });
    }



}