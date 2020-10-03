package com.kavin.utils.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kavin.utils.R;
import com.kavin.myutils.tools.AppUtils;

import java.util.List;

public class AppListActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        progressBar = findViewById(R.id.progressBar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<AppUtils.AppInfo> allAppInfo = getAllAppInfo();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initData(allAppInfo);
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        }).start();
    }

    private void initData(final List<AppUtils.AppInfo> allAppInfo) {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        //添加Android自带的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_app_info, parent, false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                AppUtils.AppInfo appInfo = allAppInfo.get(position);
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.appName.setText(appInfo.getName());
                myViewHolder.setData(appInfo);
            }

            @Override
            public int getItemCount() {
                return allAppInfo.size();
            }
        });


    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView appName;
        private AppUtils.AppInfo data;

        public MyViewHolder(View view) {
            super(view);
            appName = view.findViewById(R.id.app_name);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppUtils.launchApp(data.getPackageName());
                }
            });
        }

        public void setData(AppUtils.AppInfo data) {
            this.data = data;
        }
    }

    private List<AppUtils.AppInfo> getAllAppInfo() {
        List<AppUtils.AppInfo> appsInfo = AppUtils.getAppsInfo();
        for (int i = 0; i < appsInfo.size(); i++) {
            System.out.println(appsInfo.get(i));
        }
        return appsInfo;
    }


}
