package com.example.serenai;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 使用 Handler 延迟2秒后跳转到登录注册页
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 创建一个 Intent，跳转到登录注册页
                Intent intent = new Intent(SplashActivity.this, EntryActivity.class);
                startActivity(intent);
                // 关闭当前活动
                finish();
            }
        }, 100); // 2000 毫秒即2秒
    }
}
