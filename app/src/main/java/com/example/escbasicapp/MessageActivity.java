package com.example.escbasicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MessageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText phoneNum;
    private EditText content;
    private FloatingActionButton send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        setUpUI();

        setSupportActionBar(toolbar);//액션바로 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 상단바에 뒤로가기가 보이게

        String getPhoneNum =  getIntent().getStringExtra("phone_num");
        phoneNum.setText(getPhoneNum);
        phoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); //전화번호 포맷으로
    }

    private void setUpUI(){
        toolbar = findViewById(R.id.message_toolbar);
        phoneNum = findViewById(R.id.message_et_phone);
        content = findViewById(R.id.message_et_content);
        send= findViewById(R.id.message_fab_send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNum.getText().toString(), null, content.getText().toString(), null, null); //문자 전송
                    finish();
                    Toast.makeText(MessageActivity.this, "SUCCESS",Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(MessageActivity.this, "FAILED",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) { //toolbar에서 뒤로가기 누를때 뒤로가게
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}