package com.example.admin.smsapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*created by Matuku Ngilu on 20th August 2018
*on attachment
* trial app
 */

public class MainActivity2 extends Activity {
    private final static  int SEND_SMS_PERMISSION_REQUEST_CODE=111;
    private Button sendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendMessage=(Button)findViewById(R.id.send_message);
        final EditText phone=(EditText)findViewById(R.id.phone_no);
        final EditText message=(EditText)findViewById(R.id.message);
        sendMessage.setEnabled(false);

        if(checkPermission(Manifest.permission.SEND_SMS)) {
         sendMessage.setEnabled(true);
        }
        else {
          ActivityCompat.requestPermissions(this,new String []{Manifest.permission.SEND_SMS},SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber=phone.getText().toString();
                String msg=message.getText().toString();
                if (!TextUtils.isEmpty(msg)&& !TextUtils.isEmpty(phoneNumber)){
                    if(checkPermission(Manifest.permission.SEND_SMS)){
                        SmsManager smsManager=SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber,null,msg,null,null);
                        Toast.makeText(MainActivity2.this,"Message Sent",Toast.LENGTH_SHORT).show();
                        phone.setText("");
                        message.setText("");
                    }else{
                        Toast.makeText(MainActivity2.this,"Permission denied",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity2.this, "Enter a message and a valid phone Number which", Toast.LENGTH_SHORT).show();
                }
            }
        });
 }



    private boolean checkPermission(String permission){
        int checkPermission= ContextCompat.checkSelfPermission(this,permission);
        return checkPermission== PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case SEND_SMS_PERMISSION_REQUEST_CODE:
                if(grantResults.length>0 && (grantResults[0]==PackageManager.PERMISSION_GRANTED)){
                    sendMessage.setEnabled(true);
                }
                break;
        }

    }
}
