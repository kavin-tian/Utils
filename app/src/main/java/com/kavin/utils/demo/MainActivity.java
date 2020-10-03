package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.kavin.utils.R;
import com.kavin.myutils.statusbar.StatusBarUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtils.setStatusBarVisibility(this, false);
    }

    public void ucrop(View view) {
        startActivity(new Intent(this, UCropActivity.class));
    }

    public void statusBar(View view) {
        startActivity(new Intent(this, StatusBarActivity.class));
    }

    public void network(View view) {
        startActivity(new Intent(this, NetworkActivity.class));
    }

    public void keyboard(View view) {
        startActivity(new Intent(this, KeyboardActivity.class));
    }

    public void phone(View view) {
        startActivity(new Intent(this, PhoneActivity.class));
    }

    public void regex(View view) {
        startActivity(new Intent(this, RegexActivity.class));
    }

    public void device(View view) {
        startActivity(new Intent(this, DeviceActivity.class));
    }

    public void appInfo(View view) {
        startActivity(new Intent(this, AppInfoActivity.class));
    }

    public void flashlight(View view) {
        startActivity(new Intent(this, FlashlightActivity.class));
    }

    public void encode(View view) {
        startActivity(new Intent(this, EncodeActivity.class));
    }

    public void image(View view) {
        startActivity(new Intent(this, ImageActivity.class));
    }

    public void language(View view) {
        startActivity(new Intent(this, LanguageActivity.class));
    }

    public void path(View view) {
        startActivity(new Intent(this, PathActivity.class));
    }

    public void gsonUtils(View view) {
        startActivity(new Intent(this, GsonActivity.class));
    }

    public void intentUtils(View view) {
        startActivity(new Intent(this, IntentActivity.class));
    }

    public void notificationUtils(View view) {
        startActivity(new Intent(this, NotificationActivity.class));
    }

    public void snackbarUtils(View view) {
        startActivity(new Intent(this, SnackbarActivity.class));
    }

    public void encryptUtils(View view) {
        startActivity(new Intent(this, EncryptActivity.class));
    }

}
