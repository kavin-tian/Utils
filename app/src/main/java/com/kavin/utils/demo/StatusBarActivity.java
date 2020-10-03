package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.kavin.utils.R;
import com.kavin.myutils.statusbar.StatusBarUtils;

public class StatusBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);
    }

    public void setStatusBarVisibility(View view) {
        boolean visible = StatusBarUtils.isStatusBarVisible(this);
        StatusBarUtils.setStatusBarVisibility(this, !visible);
    }


    public void setStatusBarMode(View view) {
        StatusBarUtils.setStatusBarMode(this, true,  R.color.colorWhite);
    }

    public void setStatusBarColor(View view) {
        StatusBarUtils.setStatusBarColor(this, R.color.colorWhite);
    }
}
