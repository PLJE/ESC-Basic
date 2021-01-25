package com.example.escbasicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
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

        checkPermissions();

        setUpUI();

        if(phoneNum.getText().length()==0){
            message.setVisibility(View.GONE);
            backspace.setVisibility(View.GONE);
        }
    }

    private void checkPermissions(){
        int resultCall = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
        int resultSms = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(resultCall == PackageManager.PERMISSION_DENIED || resultSms == PackageManager.PERMISSION_DENIED){ //둘중 하나라도 거부되면
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS},1001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1001){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"권한 허용 됨",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"권한 허용이 필요합니다. 설정에서 허용 해주세요",Toast.LENGTH_SHORT).show();
                Log.d("PermissionDenied","권한이 거부되어 앱을 종료합니다."); //로그가 뜨게
                finish();
            }
        }
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
                Intent addIntent = new Intent(MainActivity.this,AddEditActivity.class);
                startActivity(addIntent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactIntent = new Intent(MainActivity.this, ContactActivity.class);
                startActivity(contactIntent);
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
                Intent messageIntent = new Intent(MainActivity.this , MessageActivity.class); //어디로 가는지 정보를 담음
                messageIntent.putExtra("phone_num",phoneNum.getText().toString());//추가적인 정보를 넘겨줌.
                startActivity(messageIntent); //move
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissions();
                //Intent : 화면을 넘어가게 해줌
                Intent callIntent = new Intent(Intent.ACTION_CALL , Uri.parse("tel:"+phoneNum.getText()));
                startActivity(callIntent);
            }
        });

        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNum.getText().length()>0) {
                    phoneNum.setText(changeToDial(phoneNum.getText().subSequence(0,phoneNum.getText().length()-1).toString()));

                    if(phoneNum.getText().length()==0){
                        message.setVisibility(View.GONE);
                        backspace.setVisibility(View.GONE);
                    }
                }
            }
        });

        backspace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                phoneNum.setText("");

                message.setVisibility(View.GONE);
                backspace.setVisibility(View.GONE);

                return true;
            }
        });

    }

    private void setOnClickDial(View view, String input){
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                phoneNum.setText(changeToDial(phoneNum.getText()+input));

                message.setVisibility(View.VISIBLE);
                backspace.setVisibility(View.VISIBLE);
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