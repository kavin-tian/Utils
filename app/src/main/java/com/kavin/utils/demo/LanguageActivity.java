package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kavin.utils.R;
import com.kavin.myutils.tools.LanguageUtils;

import java.util.Locale;

public class LanguageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

    }

    public void applySystemLanguage(View view) {
        LanguageUtils.applySystemLanguage();
    }

    public void applyLanguage(View view) {
        LanguageUtils.applyLanguage(Locale.US);
    }

    public void getCurrentLocale(View view) {
        Locale locale = LanguageUtils.getCurrentLocale();
        Toast.makeText(this, "" + locale.getCountry(), Toast.LENGTH_SHORT).show();
    }
}
