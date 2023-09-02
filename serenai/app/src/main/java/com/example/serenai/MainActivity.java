package com.example.serenai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

// ...


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
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

}