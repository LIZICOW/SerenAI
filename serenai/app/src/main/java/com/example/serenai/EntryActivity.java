package com.example.serenai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
    }

    // 方法用于处理按钮点击事件
    public void onLoginClick(View view) {
        // 创建一个 Intent，将当前 Activity 与目标 LoginActivity 关联
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent); // 启动 LoginActivity
        // 关闭当前活动
        finish();
    }

    public void onRegisterClick(View view) {
        // 创建一个 Intent，将当前 Activity 与目标 RegisterActivity 关联
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent); // 启动 RegisterActivity
        // 关闭当前活动
        finish();
    }
}