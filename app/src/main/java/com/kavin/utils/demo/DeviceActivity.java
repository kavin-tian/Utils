package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kavin.utils.R;
import com.kavin.myutils.tools.DeviceUtils;

/**
 * isDeviceRooted   : 判断设备是否 rooted
 * isAdbEnabled     : 判断设备 ADB 是否可用
 * getSDKVersionName: 获取设备系统版本号
 * getSDKVersionCode: 获取设备系统版本码
 * getAndroidID     : 获取设备 AndroidID
 * getMacAddress    : 获取设备 MAC 地址
 * getManufacturer  : 获取设备厂商
 * getModel         : 获取设备型号
 * getABIs          : 获取设备 ABIs
 * isTablet         : 判断是否是平板
 * isEmulator       : 判断是否是模拟器
 * getUniqueDeviceId: 获取唯一设备 ID
 * isSameDevice     : 判断是否同一设备
 */
public class DeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
    }

    public void getSDKVersionName(View view) {
        String sdkVersionName = DeviceUtils.getSDKVersionName();
        Toast.makeText(this, "sdkVersionName: " + sdkVersionName, Toast.LENGTH_SHORT).show();
    }

    public void getMacAddress(View view) {
        String macAddress = DeviceUtils.getMacAddress();
        Toast.makeText(this, "macAddress: " + macAddress, Toast.LENGTH_SHORT).show();
    }

    public void getModel(View view) {
        String model = DeviceUtils.getModel();
        Toast.makeText(this, "model: " + model, Toast.LENGTH_SHORT).show();
    }

    public void isTablet(View view) {
        boolean b = DeviceUtils.isTablet();
        Toast.makeText(this, "isTablet: " + b, Toast.LENGTH_SHORT).show();
    }

    public void isEmulator(View view) {
        boolean b = DeviceUtils.isEmulator();
        Toast.makeText(this, "isEmulator: " + b, Toast.LENGTH_SHORT).show();
    }

    public void getUniqueDeviceId(View view) {
        String uniqueDeviceId = DeviceUtils.getUniqueDeviceId();
        Toast.makeText(this, "uniqueDeviceId: " + uniqueDeviceId, Toast.LENGTH_SHORT).show();
    }


}
