package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kavin.utils.R;
import com.kavin.myutils.tools.RegexUtils;

public class RegexActivity extends AppCompatActivity {

    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regex);
        phone = findViewById(R.id.phone);
    }

    public void isMobileSimple(View view) {
        String num = phone.getText().toString().trim();
        boolean b = RegexUtils.isMobileSimple(num);
        Toast.makeText(this, num + " isMobileSimple: " + b, Toast.LENGTH_SHORT).show();
    }

    public void isTel(View view) {
        String num = phone.getText().toString().trim();
        boolean b = RegexUtils.isTel(num);
        Toast.makeText(this, num + " isTel: " + b, Toast.LENGTH_SHORT).show();
    }

    public void isEmail(View view) {
        String num = phone.getText().toString().trim();
        boolean b = RegexUtils.isEmail(num);
        Toast.makeText(this, num + " isTel: " + b, Toast.LENGTH_SHORT).show();
    }

    public void isIDCard18(View view) {
        String num = phone.getText().toString().trim();
        boolean b = RegexUtils.isIDCard18(num);
        Toast.makeText(this, num + " isIDCard18: " + b, Toast.LENGTH_SHORT).show();
    }


}
