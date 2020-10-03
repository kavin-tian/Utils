package com.kavin.utils.demo;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kavin.utils.R;
import com.kavin.myutils.tools.PhoneUtils;

import java.util.ArrayList;

public class PhoneActivity extends CheckPermissionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
    }

    @Override
    protected ArrayList<String> addNeedPermissionList(ArrayList<String> permissions) {
        permissions.add(Manifest.permission.CALL_PHONE);
        permissions.add(Manifest.permission.SEND_SMS);
        permissions.add(Manifest.permission.READ_PHONE_STATE);
        return permissions;
    }

    public void call(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        PhoneUtils.call("10086");
    }

    public void sendSms(View view) {

        PhoneUtils.sendSms("10086", "hello!");
    }

    public void getDeviceId(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        String deviceId = PhoneUtils.getDeviceId();
        Toast.makeText(this, "deviceId: "+deviceId, Toast.LENGTH_SHORT).show();
    }
}
