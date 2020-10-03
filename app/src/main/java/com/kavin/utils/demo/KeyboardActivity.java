package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kavin.utils.R;
import com.kavin.myutils.tools.KeyboardUtils;

public class KeyboardActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private static final String TAG = "---KeyboardActivity";
    private EditText editText;
    private EditText editText2;
    private EditText editText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);

        editText.setOnEditorActionListener(this);
        editText2.setOnEditorActionListener(this);
        editText3.setOnEditorActionListener(this);


    }

    public void showSoftInput(View view) {
        KeyboardUtils.showSoftInput();
    }

    public void hideSoftInput(View view) {
        KeyboardUtils.hideSoftInput(this);
    }

    public void toggleSoftInput(View view) {
        KeyboardUtils.toggleSoftInput();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (actionId) {
            case EditorInfo.IME_NULL:
                Log.e(TAG, "Done_content: " + v.getText());
                break;
            case EditorInfo.IME_ACTION_SEND:
                Log.e(TAG, "send a email: " + v.getText());
                Toast.makeText(this, "send: " + v.getText(), Toast.LENGTH_SHORT).show();
                break;
            case EditorInfo.IME_ACTION_NEXT:
                Log.e(TAG, "IME_ACTION_NEXT: " + v.getText());
                Toast.makeText(this, "next: " + v.getText(), Toast.LENGTH_SHORT).show();
                editText3.requestFocus();//让editText2获取焦点
                editText3.setSelection(editText3.getText().length());//若editText2有内容就将光标移动到文本末尾
                break;
            case EditorInfo.IME_ACTION_DONE:
                Log.e(TAG, "action done for number_content: " + v.getText());
                Toast.makeText(this, "done: " + v.getText(), Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}
