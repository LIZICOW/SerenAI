package com.example.serenai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DailyInteractionActivity extends AppCompatActivity{
    public String url = "http://1.15.66.98:8081/api";

    public ImageView intentMain;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.daily_interaction);
       sharedPreferencesManager =new SharedPreferencesManager(this);
        //绑定视图
        intentMain =findViewById(R.id.intentMain);
    }

    public  void onMainClick(View view){
        Intent intent=new Intent(DailyInteractionActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
