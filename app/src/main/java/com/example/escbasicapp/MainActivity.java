package com.example.escbasicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageButton addContact;
    private ImageButton contact;
    private TextView phoneNum;
    private TextView[] dials = new TextView[10];
    private TextView star;
    private TextView sharp;
    private ImageButton message;
    private ImageButton call;
    private ImageButton backspace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUI();
    }
    private void setUpUI(){
        addContact = findViewById(R.id.main_ibtn_add); // connect the layout and code
        contact = findViewById(R.id.main_ibtn_contact);
        phoneNum = findViewById(R.id.main_tv_phone);

        for(int i=0;i<dials.length;i++){
            dials[i]=findViewById(getResourceID("main_tv_"+i,"id",this));
        }

        star=findViewById(R.id.main_tv_star);
        sharp=findViewById(R.id.main_tv_sharp);
        message = findViewById(R.id.main_ibtn_message);
        call=findViewById(R.id.main_ibtn_call);
        backspace=findViewById(R.id.main_ibtn_backspace);

        //event
        addContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: 연락처 추가
                Toast.makeText(MainActivity.this,"test",Toast.LENGTH_SHORT).show();
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : 연락처
            }
        });
        setOnClickDial(star,"*");
        setOnClickDial(sharp,"#");
        for(int i=0;i<10;i++){
            setOnClickDial(dials[i], String.valueOf(i));
        }

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : 메시지
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO :전화
            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNum.getText().length()>0) {
                    phoneNum.setText(changeToDial(phoneNum.getText().subSequence(0,phoneNum.getText().length()-1).toString())); //
                }
            }
        });
    }

    private void setOnClickDial(View view, String input){
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                phoneNum.setText(changeToDial(phoneNum.getText()+input)); //
            }
        });
    }

    private int getResourceID(final String resName,final String resType,final Context ctx){
        final int ResourceID =
                ctx.getResources().getIdentifier(resName,resType,ctx.getApplicationInfo().packageName);
        if(ResourceID==0){
            throw new IllegalArgumentException("No resource string found with name "+resName);
        }else{
            return ResourceID;
        }
    }
    private String changeToDial(String phoneNum){
        phoneNum = phoneNum.replaceAll("-","");

        if(phoneNum.length()>=4 && phoneNum.length()<=7){
            phoneNum =phoneNum.substring(0,3)+"-"+phoneNum.substring(3);
        }
        else if(phoneNum.length()>=8 &&phoneNum.length()<=11){
            phoneNum=phoneNum.substring(0,3)+"-"+phoneNum.substring(3,7)+"-"+phoneNum.substring(7);
        }
        else if(phoneNum.length()>=11){
            phoneNum=phoneNum.replaceAll("-","");
        }

        if(phoneNum.contains("*")||phoneNum.contains("#")){
            phoneNum =phoneNum.replaceAll("-","");
        }

        return phoneNum;
    }
}