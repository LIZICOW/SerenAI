package com.example.serenai;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

// ...


public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
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
}