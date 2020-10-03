package com.kavin.utils.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.kavin.utils.R;
import com.kavin.myutils.tools.NotificationUtils;
import com.kavin.myutils.tools.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationActivity extends AppCompatActivity {

    private String TAG = "---NotificationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initButtonReceiver();
    }

    public void areNotificationsEnabled(View view) {
        boolean b = NotificationUtils.areNotificationsEnabled();
        Toast.makeText(this, "通知是否可用: " + b, Toast.LENGTH_SHORT).show();
    }


    public void notify(View view) {
        NotificationUtils.notify(10086, new Utils.Consumer<NotificationCompat.Builder>() {
            @Override
            public void accept(NotificationCompat.Builder builder) {

                createNotification(builder);

            }
        });
    }


    private void createNotification(NotificationCompat.Builder builder) {
        Intent intent = new Intent(this, MainActivity.class);
        /*
         * 调用PendingIntent的静态放法创建一个 PendingIntent对象用于点击通知之后执行的操作，
         * PendingIntent可以理解为延时的Intent，在这里即为点击通知之后执行的Intent
         * 这里调用getActivity(Context context, int requestCode, Intent intent, int flag)方法
         * 表示这个PendingIntent对象启动的是Activity，类似的还有getService方法、getBroadcast方法
         */
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        builder.setContentTitle("简单的通知") // 创建通知的标题
                .setContentText("通知的内容") // 创建通知的内容
                .setSmallIcon(R.mipmap.ic_launcher) // 创建通知的小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)) // 创建通知的大图标
                /*
                 * 首先，无论你是使用自定义视图还是系统提供的视图，上面4的属性一定要设置，不然这个通知显示不出来
                 */
                .setWhen(System.currentTimeMillis()) // 设定通知显示的时间
                .setContentIntent(pi) // 设定点击通知之后启动的内容，这个内容由方法中的参数：PendingIntent对象决定
                //.setPriority(NotificationCompat.PRIORITY_MAX) // 设置通知的优先级
                .setAutoCancel(true); // 设置点击通知之后通知是否消失

        builder.setVibrate(new long[]{1000, 500, 2000});

    }


    public void customNotify(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_notification_view_custom);
        remoteViews.setImageViewResource(R.id.custom_icon, R.mipmap.notification_icon);
        remoteViews.setTextViewText(R.id.tv_custom_title, "今日头条");
        remoteViews.setTextViewText(R.id.tv_custom_content, "人生不止于苟且，还有诗和远方！");
        String time = new SimpleDateFormat("hh:mm").format(new Date());
        remoteViews.setTextViewText(R.id.tv_custom_time, time);

        NotificationUtils.customNotify(remoteViews, 100, "110", "cname", intent, R.mipmap.ic_launcher);
    }

    public void showButtonNotify(View view) {
        showButtonNotify();
    }

    /**
     * 自定义通知
     */
    private void customNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.activity_notification_view_custom);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //8.0系统需要添加通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "123";
            CharSequence name = "name";
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(id);
            notificationManager.createNotificationChannel(channel);
        }

        mBuilder.setContentIntent(pendingIntent);//点击通知跳转的页面
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContent(remoteViews);
        remoteViews.setImageViewResource(R.id.custom_icon, R.mipmap.logo);
        remoteViews.setTextViewText(R.id.tv_custom_title, "我是标题");
        remoteViews.setTextViewText(R.id.tv_custom_content, "我是内容");
        notificationManager.notify(10, mBuilder.build());

    }

    /**
     * @获取默认的pendingIntent,为了防止2.3及以下版本报错
     * @flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT
     * 点击去除： Notification.FLAG_AUTO_CANCEL
     */
    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }


    public void cancel(View view) {
        NotificationUtils.cancel(10086);
    }

    public void cancelAll(View view) {
        NotificationUtils.cancelAll();
    }

    public void setNotificationBarVisibility(View view) {
        NotificationUtils.setNotificationBarVisibility(true);
    }


    /**
     * 是否在播放
     */
    public boolean isPlay = false;
    /**
     * 通知栏按钮广播
     */
    public ButtonBroadcastReceiver bReceiver;
    /**
     * 通知栏按钮点击事件对应的ACTION
     */
    public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";

    /**
     * 带按钮的通知栏
     */
    public void showButtonNotify() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //8.0系统需要添加通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String id = "123";
            CharSequence name = "name";
            NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(id);
            notificationManager.createNotificationChannel(channel);
        }


        RemoteViews mRemoteViews = new RemoteViews(getPackageName(), R.layout.activity_notification_view_custom_button);
        mRemoteViews.setImageViewResource(R.id.custom_song_icon, R.mipmap.notification_sing_icon);
        // API3.0 以上的时候显示按钮，否则消失
        mRemoteViews.setTextViewText(R.id.tv_custom_song_singer, "周杰伦");
        mRemoteViews.setTextViewText(R.id.tv_custom_song_name, "七里香");

        // 如果版本号低于（3。0），那么不显示按钮
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD) {
            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.GONE);
        } else {
            mRemoteViews.setViewVisibility(R.id.ll_custom_button, View.VISIBLE);
            //
            if (isPlay) {
                mRemoteViews.setImageViewResource(R.id.btn_custom_play,
                        R.mipmap.notification_btn_pause);
            } else {
                mRemoteViews.setImageViewResource(R.id.btn_custom_play,
                        R.mipmap.notification_btn_play);
            }
        }

        // 点击的事件处理
        Intent buttonIntent = new Intent(ACTION_BUTTON);
        /* 上一首按钮 */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
        // 这里加了广播，所及INTENT的必须用getBroadcast方法
        PendingIntent intent_prev = PendingIntent.getBroadcast(this, 1,
                buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_prev, intent_prev);

        /* 播放/暂停 按钮 */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
        PendingIntent intent_paly = PendingIntent.getBroadcast(this, 2,
                buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_play, intent_paly);

        /* 下一首 按钮 */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_NEXT_ID);
        PendingIntent intent_next = PendingIntent.getBroadcast(this, 3,
                buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_next, intent_next);

        builder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                .setWhen(System.currentTimeMillis())
                // 通知产生的时间，会在通知信息里显示
                .setTicker("正在播放").setPriority(Notification.PRIORITY_DEFAULT)
                // 设置该通知优先级
                .setOngoing(true)
                .setSmallIcon(R.mipmap.notification_sing_icon);
        Notification notify = builder.build();
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        // 会报错，还在找解决思路
        // notify.contentView = mRemoteViews;
        // notify.contentIntent = PendingIntent.getActivity(this, 0, new
        // Intent(), 0);
        notificationManager.notify(200, notify);
    }


    /**
     * 带按钮的通知栏点击广播接收
     */
    public void initButtonReceiver() {
        bReceiver = new ButtonBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BUTTON);
        registerReceiver(bReceiver, intentFilter);
    }

    public final static String INTENT_BUTTONID_TAG = "ButtonId";
    /**
     * 上一首 按钮点击 ID
     */
    public final static int BUTTON_PREV_ID = 1;
    /**
     * 播放/暂停 按钮点击 ID
     */
    public final static int BUTTON_PALY_ID = 2;
    /**
     * 下一首 按钮点击 ID
     */
    public final static int BUTTON_NEXT_ID = 3;

    /**
     * 广播监听按钮点击时间
     */
    public class ButtonBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(ACTION_BUTTON)) {
                // 通过传递过来的ID判断按钮点击属性或者通过getResultCode()获得相应点击事件
                int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
                switch (buttonId) {
                    case BUTTON_PREV_ID:
                        Log.d(TAG, "上一首");
                        Toast.makeText(getApplicationContext(), "上一首",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case BUTTON_PALY_ID:
                        String play_status = "";
                        isPlay = !isPlay;
                        MediaPlayer play = null;
                        if (isPlay) {
                            play_status = "开始播放";
                            playBackgroundMusic();
                        } else {
                            play_status = "已暂停";
                            stopBackgroundMusic();

                        }
                        showButtonNotify();
                        Log.d(TAG, play_status);
                        Toast.makeText(getApplicationContext(), play_status,
                                Toast.LENGTH_SHORT).show();
                        break;
                    case BUTTON_NEXT_ID:
                        Log.d(TAG, "下一首");
                        Toast.makeText(getApplicationContext(), "下一首",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (bReceiver != null) {
            unregisterReceiver(bReceiver);
        }
        super.onDestroy();
    }

    private MediaPlayer mplayer;

    /*
     * 后台播放背景音
     */
    private void playBackgroundMusic() {
        if (mplayer == null) {
            mplayer = new MediaPlayer();
            try {
                AssetFileDescriptor afd = this.getAssets().openFd("angel.mp3");
                // 获取音乐数据源
                mplayer.setDataSource(afd.getFileDescriptor(),
                        afd.getStartOffset(), afd.getLength());
                afd.close();
                mplayer.setLooping(true); // 设为循环播放
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (mplayer.isPlaying()) {
                return;
            }
            mplayer.prepare();
            mplayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 停止播放背景音乐
     */
    private void stopBackgroundMusic() {
        try {
            if (null != mplayer) {
                if (mplayer.isPlaying()) {
                    mplayer.pause();
//					mplayer.release();
                    mplayer = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
