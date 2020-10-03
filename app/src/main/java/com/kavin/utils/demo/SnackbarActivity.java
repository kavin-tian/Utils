package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.kavin.utils.R;
import com.kavin.myutils.tools.SnackbarUtils;

public class SnackbarActivity extends AppCompatActivity {

    private View bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snackbar);
        bt = findViewById(R.id.bt);
    }

    public void snackbar(View view) {
        SnackbarUtils.with(bt)
                .setMessage("Hello...!")
                .setBgColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }
}
