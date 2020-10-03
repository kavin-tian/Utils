package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kavin.utils.R;
import com.kavin.myutils.tools.EncodeUtils;

public class EncodeActivity extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encode);
        editText = findViewById(R.id.editText);
        editText.setText("中国");
    }

    public void urlEncode(View view) {
        String string = editText.getText().toString().trim();
        String s = EncodeUtils.urlEncode(string, "utf-8");
        editText.setText(s);
    }

    public void urlDecode(View view) {
        String string = editText.getText().toString().trim();
        String s = EncodeUtils.urlDecode(string, "utf-8");
        editText.setText(s);
    }

  /*  public void base64Encode(View view) {
        String string = editText.getText().toString().trim();
        byte[] bytes = EncodeUtils.base64Encode(string);
        editText.setText(s);
    }
  */

    public void base64Encode2String(View view) {
        String string = editText.getText().toString().trim();
        byte[] stringBytes = string.getBytes();
        String s = EncodeUtils.base64Encode2String(stringBytes);
        editText.setText(s);
    }

    public void base64Decode(View view) {
        String string = editText.getText().toString().trim();
        byte[] bytes = EncodeUtils.base64Decode(string);
        editText.setText(new String(bytes));
    }

/*    public void htmlEncode(View view) {
        String string = editText.getText().toString().trim();
        String s = EncodeUtils.htmlEncode(string);
        editText.setText(s);
    }

    public void htmlDecode(View view) {
        String string = editText.getText().toString().trim();
        CharSequence charSequence = EncodeUtils.htmlDecode(string);
        editText.setText(charSequence);
    }*/

    public void binaryEncode(View view) {
        String string = editText.getText().toString().trim();
        String s = EncodeUtils.binaryEncode(string);
        editText.setText(s);
    }

    public void binaryDecode(View view) {
        String string = editText.getText().toString().trim();
        String s = EncodeUtils.binaryDecode(string);
        editText.setText(s);
    }
}
