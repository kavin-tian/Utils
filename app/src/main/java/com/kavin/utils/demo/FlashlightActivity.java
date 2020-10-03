package com.kavin.utils.demo;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kavin.utils.R;
import com.kavin.myutils.tools.FlashlightUtils;

import java.util.ArrayList;

public class FlashlightActivity extends CheckPermissionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
    }

    @Override
    protected ArrayList<String> addNeedPermissionList(ArrayList<String> permissions) {
        permissions.add(Manifest.permission.CAMERA);
        return permissions;
    }

    public void isFlashlightEnable(View view) {
        boolean enable = FlashlightUtils.isFlashlightEnable();
        Toast.makeText(this, "" + enable, Toast.LENGTH_SHORT).show();
    }

    public void isFlashlightOn(View view) {
        boolean on = FlashlightUtils.isFlashlightOn();
        Toast.makeText(this, "" + on, Toast.LENGTH_SHORT).show();
    }

    public void setFlashlightStatus(View view) {
        boolean on = FlashlightUtils.isFlashlightOn();
        FlashlightUtils.setFlashlightStatus(!on);
        Toast.makeText(this, "" + !on, Toast.LENGTH_SHORT).show();
    }

    public void destroy(View view) {
        FlashlightUtils.destroy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FlashlightUtils.destroy();
    }
}
