package com.kavin.utils.demo;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.kavin.utils.R;
import com.kavin.myutils.tools.ActivityUtils;
import com.kavin.myutils.tools.IntentUtils;
import com.kavin.myutils.tools.UriUtils;

import java.io.File;
import java.util.ArrayList;

public class IntentActivity extends CheckPermissionsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);
    }

    @Override
    protected ArrayList<String> addNeedPermissionList(ArrayList<String> permissions) {
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return permissions;
    }

    public void getUninstallAppIntent(View view) {
        Intent intent = IntentUtils.getUninstallAppIntent(this.getPackageName());
        startActivity(intent);
    }

    public void getLaunchAppDetailsSettingsIntent(View view) {
        Intent intent = IntentUtils.getLaunchAppDetailsSettingsIntent(this.getPackageName());
        startActivity(intent);
    }

    public void getShareTextIntent(View view) {
        Intent intent = IntentUtils.getShareTextIntent("hello!");
        startActivity(intent);
    }

    public void getShareImageIntent(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "logo.jpg";
        Intent intent = IntentUtils.getShareImageIntent("hello!", path);
        startActivity(intent);
    }

    public void getComponentIntent(View view) {
        String pkgName ="com.android.calculator2";
        String launcherActivity = ActivityUtils.getLauncherActivity(pkgName);
        Intent launchAppIntent = IntentUtils.getComponentIntent(pkgName, launcherActivity);
        //Intent launchAppIntent = IntentUtils.getLaunchAppIntent(pkgName);
        startActivity(launchAppIntent);
    }

    public void getLaunchAppIntent(View view) {
        String pkgName ="com.android.calculator2";
        Intent launchAppIntent = IntentUtils.getLaunchAppIntent(pkgName);
        startActivity(launchAppIntent);
    }

    public void getCaptureIntent(View view) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "logo.jpg";
        Uri outUri = UriUtils.file2Uri(new File(path));
        Intent intent = IntentUtils.getCaptureIntent(outUri);
        startActivity(intent);
    }

}
