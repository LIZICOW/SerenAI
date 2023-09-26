package com.example.serenai;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.SeekBar;

import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.serenai.iflytek.voicedemo.TtsDemo;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;


// ...


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private AudioManager audioManager;

    private SeekBar volume;
    private AudioManager am;
    private int maxVolume, currentVolume;
    private SeekBar light;
    private float fBrightness;
    private int maxBrightness = 255;
    private WindowManager.LayoutParams lp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + getString(R.string.app_id));
        drawerLayout = findViewById(R.id.drawer_layout);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        volume = findViewById(R.id.volumeSeekBar);
        light = findViewById(R.id.brightnessSeekBar);

        am = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //获取系统最大音量
        maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume.setMax(maxVolume);
        //获取当前音量
        currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        volume.setProgress(currentVolume);
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                    int currentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
                    seekBar.setProgress(currentVolume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 当开始拖动SeekBar时触发
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 当停止拖动SeekBar时触发
            }
        });

        light.setMax(maxBrightness);
        light.setProgress(maxBrightness / 2);
        lp = getWindow().getAttributes();
        fBrightness = (float) light.getProgress() / maxBrightness;
        lp.screenBrightness =fBrightness;
        getWindow().setAttributes(lp);
        light.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                fBrightness = (float) progress/ (float) maxBrightness;
                lp.screenBrightness = fBrightness;
                getWindow().setAttributes(lp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // 当开始拖动SeekBar时触发
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 当停止拖动SeekBar时触发
            }
        });

        initSpeech();
        requestPermissions();
        
    }

    private void initSpeech() {
        String buf = "当前APPID为：" +
                getString(R.string.app_id) + "\n";
        // 语音合成
        findViewById(R.id.ttsBtn).setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, TtsDemo.class));
        });
    }

    private void requestPermissions() {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                ActivityCompat.requestPermissions(this, new String[]{
                        android.Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA
                }, 0x0010);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //
    public void onMoreClick(View view) {
        // 获取当前侧滑菜单状态
        boolean isOpen = drawerLayout.isDrawerOpen(GravityCompat.START);
        System.out.println(isOpen);
        // 如果当前打开就关上
        if(isOpen){
            drawerLayout.closeDrawer(GravityCompat.START); // 关闭
            System.out.println(isOpen);
        }
        // 否则就打开
        else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return;
    }

    public void onQuietClick(View view) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted()) {
            startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
        } else {
            // 获取当前的铃声模式
            int ringerMode = audioManager.getRingerMode();

            // 判断当前的铃声模式，根据状态切换静音或解除静音
            if (ringerMode == AudioManager.RINGER_MODE_NORMAL) {
                // 切换到静音模式
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                // 静音媒体音量
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
            } else if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
                // 解除静音，切换回正常模式
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

                // 恢复媒体音量（根据需要设置合适的音量值）
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, AudioManager.FLAG_SHOW_UI);
            }
        }
    }

    public void onDesignClick(View view){
        // 创建一个 Intent，将当前 Activity 与目标 DesignActivity 关联
        Intent intent = new Intent(this, DesignActivity.class);
        startActivity(intent); // 启动 DesignActivity
        // 关闭当前活动
        finish();
    }
    public void onInfoClick(View view) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent); // 启动 DesignActivity
        // 关闭当前活动
        finish();
    }

    public void onARClick(View view){
        Intent intent = new Intent(this, ARActivity.class);
        startActivity(intent); // 启动 DesignActivity
        // 关闭当前活动
        finish();
    }


}