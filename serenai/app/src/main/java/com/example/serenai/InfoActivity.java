package com.example.serenai;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.serenai.iflytek.voicedemo.TtsDemo;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class InfoActivity extends AppCompatActivity {
    public String url = "http://1.15.66.98:8081/api";
   private SharedPreferencesManager sharedPreferencesManager;
   private TextView usernameTextView;
   private EditText usernameEditText;
   private TextView signatureTextView;
   private EditText signatureEditText;
   private TextView days;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        //绑定试图
        usernameTextView=findViewById(R.id.usernameTextView);
        usernameEditText=findViewById(R.id.usernameEditText);
        signatureEditText=findViewById(R.id.signatureEditText);
        signatureTextView=findViewById(R.id.signatureTextView);
        days=findViewById(R.id.days);
        sharedPreferencesManager = new SharedPreferencesManager(this);
//        sendEditRequest(setName(),setSignature());
        getInfo();
        //countingDays(daysDifference);
        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    usernameEditText.clearFocus();
                    usernameEditText.setCursorVisible(false);
                }
                else
                    usernameEditText.setCursorVisible(true);
            }
        });
        signatureEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b){
                    signatureEditText.clearFocus();
                    signatureEditText.setCursorVisible(false);
                }
                else
                    signatureEditText.setCursorVisible(true);
            }
        });
    }
//    public void countingDays(int num){
//        String notice=getString(R.string.days);
//        String meet_days=String.format(notice,num);
//        days.setText(meet_days);
//    }
    public void onLogOffClick(View view){
        sharedPreferencesManager.logout();
        Intent intent=new Intent(this,EntryActivity.class);
        startActivity(intent);
        finish();
    }
    public String setName(){
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                //文本变化之前的回调，不需要处理
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // 文本变化时的回调，在这里更新 TextView 的可见性和内容
                if (charSequence.length() > 0) {
                    usernameTextView.setVisibility(View.GONE);
                } else {
                    usernameTextView.setVisibility(View.VISIBLE);
                }
                //usernameTextView.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //文本变化后的回调，不需要处理
            }
        });
        return usernameTextView.getText().toString();
    }
    public String setSignature(){
        signatureEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                //文本变化之前的回调，不需要处理
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // 文本变化时的回调，在这里更新 TextView 的可见性和内容
                if (charSequence.length() > 0) {
                    signatureTextView.setVisibility(View.GONE);
                } else {
                    signatureTextView.setVisibility(View.VISIBLE);
                }
                //signatureTextView.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //文本变化后的回调，不需要处理
            }
        });

        return signatureTextView.getText().toString();
    }

    // 获取个人信息，并进行页面渲染
    public void getInfo() {
        // 创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        String Url = url + "/user/getBasicInfo/" + sharedPreferencesManager.getUserID();
        Request request = new Request.Builder()
                .url(Url)
                .get()
                .build();

        // 异步执行HTTP请求
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // 获取响应体
                    ResponseBody responseBody = response.body();
                    try {
                        // 处理响应数据
                        String responseData = responseBody.string();
                        JSONObject responseJson = new JSONObject(responseData);
                        // 提取键为"code"的值
                        int code = responseJson.getInt("code");

                        // 在这里处理响应数据，例如更新 UI 或者执行其他操作
                        if (code == 0) {
                            // 处理成功的情况
                            // 获取 "data" 对象
                            JSONObject data = responseJson.getJSONObject("data");

                            // 提取需要的数据
                            sharedPreferencesManager.setUsername(data.optString("user_name", ""));
                            sharedPreferencesManager.setUserSignature(data.optString("user_signature", ""));
                            sharedPreferencesManager.setUser_daysDifference(data.optInt("daysDifference", 0));

                            // 更新 UI，确保在主线程更新 UI
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    usernameTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                                    usernameTextView.setText(sharedPreferencesManager.getUsername());
                                    signatureTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                                    signatureTextView.setText(sharedPreferencesManager.getUSERSignature());
                                    days.setText(String.format("结识Seren%d天", sharedPreferencesManager.getUser_daysDifference()));
                                }
                            });
                        } else {
                            // 处理其他情况
                            // ...
                        }
                    } catch (JSONException e) {
                        // 处理JSON解析错误
                        e.printStackTrace();
                    } finally {
                        // 关闭响应体
                        responseBody.close();
                    }
                } else {
                    // 处理非成功响应，例如处理HTTP状态码4xx或5xx
                    // ...
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                // 处理请求失败，例如网络问题等
                e.printStackTrace();
            }
        });
    }
    // 修改个人信息
//    private void sendEditRequest(String newName, String newSignature) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String Url = url + "/user/updateProfile";
//
//                // 创建请求体
//                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
//                JSONObject json = new JSONObject();
//                try {
//                    json.put("uid", sharedPreferencesManager.getUserID());
//                    json.put("uname", newName);
//                    json.put("signature", newSignature);
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//
//                //创建一个OkHttpClient对象
//                OkHttpClient okHttpClient = new OkHttpClient();
//                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
//                Request request = new Request.Builder()
//                        .url(Url)
//                        .post(requestBody)
//                        .build();
//
//                // 发送请求并获取响应
//                // 异步执行HTTP请求
//                okHttpClient.newCall(request).enqueue(new Callback() {
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        if (response.isSuccessful()) {
//
//                            // 获取响应体
//                            ResponseBody responseBody = response.body();
//                            try {
//                                // 处理响应数据
//                                String responseData = responseBody.string();
//                                JSONObject responseJson = new JSONObject(responseData);
//                                // 提取键为"code"的值
//                                int code = responseJson.getInt("code");
//
//                                // 在这里处理响应数据，例如更新 UI 或者执行其他操作
//                                // 这里可以根据返回的数据 code 值来决定如何处理数据
//                                if (code == 0) {
//                                    // 处理成功的情况
//                                    sharedPreferencesManager.setUsername(newName);
//                                    sharedPreferencesManager.setUserSignature(newSignature);
//                                } else {
//                                    // 处理其他情况
//                                    // ...
//                                }
//
//                            } catch (JSONException e) {
//                                // 处理JSON解析错误
//                                e.printStackTrace();
//                            } finally {
//                                // 关闭响应体
//                                responseBody.close();
//                            }
//                        } else {
//                            // 处理非成功响应，例如处理HTTP状态码4xx或5xx
//                            // ...
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        // 处理请求失败，例如网络问题等
//                        e.printStackTrace();
//                    }
//                });
//            }
//        }).start();
//    }
    // 处理返回逻辑
    public void onInfoBackClick(View view) {
        // 创建一个 Intent，将当前 Activity 与目标 InfoActivity 关联
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent); // 启动 MainActivity
        // 关闭当前活动
        finish();
    }
}
