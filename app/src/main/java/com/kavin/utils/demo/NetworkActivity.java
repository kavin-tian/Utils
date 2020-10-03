package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.kavin.utils.R;
import com.kavin.myutils.network.NetworkTask;
import com.kavin.myutils.network.NetworkUtils;

public class NetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
    }

    public void isAvailableAsync(View view) {
        NetworkUtils.isAvailableAsync(new NetworkTask.Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                Toast.makeText(NetworkActivity.this, "" + aBoolean, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void openWirelessSettings(View view) {
        NetworkUtils.openWirelessSettings();
    }

    public void isConnected(View view) {
        boolean connected = NetworkUtils.isConnected();
        Toast.makeText(NetworkActivity.this, "" + connected, Toast.LENGTH_SHORT).show();
    }

    public void isMobileData(View view) {
        boolean connected = NetworkUtils.isMobileData();
        Toast.makeText(NetworkActivity.this, "" + connected, Toast.LENGTH_SHORT).show();
    }

    public void isWifiConnected(View view) {
        boolean connected = NetworkUtils.isWifiConnected();
        Toast.makeText(NetworkActivity.this, "" + connected, Toast.LENGTH_SHORT).show();
    }

    public void isAvailableByPingAsync(View view) {

        NetworkUtils.isAvailableByPingAsync("www.baidu.com", new NetworkTask.Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                Toast.makeText(NetworkActivity.this, "" + aBoolean, Toast.LENGTH_SHORT).show();
            }
        });

        /*NetworkUtils.isAvailableByPingAsync(new NetworkTask.Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) {
                Toast.makeText(NetworkActivity.this, "" + aBoolean, Toast.LENGTH_SHORT).show();
            }
        });*/


    }
}
