package com.kavin.utils.demo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.kavin.utils.R;
import com.kavin.myutils.tools.AppUtils;

import java.io.File;
import java.util.ArrayList;

public class AppInfoActivity extends CheckPermissionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
    }

    @Override
    protected ArrayList<String> addNeedPermissionList(ArrayList<String> permissions) {
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        return permissions;
    }


    /**
     * 安装指定路径的apk
     * 添加权限：
     * <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
     */
    public void installApp(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "shiwujiaju.apk";
        System.out.println(path);
        AppUtils.installApp(path);
    }

    /**
     * 根据包名卸载app
     */
    public void uninstallApp(View view) {
        AppUtils.uninstallApp(AppUtils.getAppPackageName());
    }


    public void relaunchApp(View view) {
        AppUtils.relaunchApp(true);
    }

   /* public void exitApp(View view) {
        AppUtils.exitApp();
    }*/

    public void getAppInfo(View view) {
        AppUtils.AppInfo appInfo = AppUtils.getAppInfo();
        System.out.println(appInfo);
    }

    public void getAppsInfo(View view) {
       /* List<AppUtils.AppInfo> appsInfo = AppUtils.getAppsInfo();
        for (int i = 0; i < appsInfo.size(); i++) {
            System.out.println(appsInfo.get(i));
        }*/
       startActivity(new Intent(this,AppListActivity.class));

    }

    public void getApkInfo(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "shiwujiaju.apk";
        AppUtils.AppInfo apkInfo = AppUtils.getApkInfo(path);
        System.out.println("path: " + path);
        System.out.println("apkInfo: " + apkInfo);
    }

    public void getAppSignature(View view) {
        Signature[] signatures = AppUtils.getAppSignature();

        for (int i = 0; i < signatures.length; i++) {
            System.out.println("signatures: " + signatures[i].toCharsString());
        }

    }

    public void getAppSignatureMD5(View view) {
        String md5 = AppUtils.getAppSignatureMD5();
        System.out.println("md5: " + md5);
    }

    public void getAppSignatureSHA1(View view) {
        String sha1 = AppUtils.getAppSignatureSHA1();
        System.out.println("sha1: " + sha1);
    }

    public void getAppSignatureSHA256(View view) {
        String sha256 = AppUtils.getAppSignatureSHA256();
        System.out.println("sha256: " + sha256);
    }


}
