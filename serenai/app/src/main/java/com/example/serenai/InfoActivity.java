package com.example.serenai;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
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
        countingDays();

        sendEditRequest(setName(),setSignature());
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
    public void countingDays(int num){
        String notice=getString(R.string.days);
        String meet_days=String.format(notice,num);
        days.setText(meet_days);
    }
    //设置天数的默认值为0
    public void countingDays(){
        int default_num=0;
        countingDays(default_num);
    }
    public void onLogOffClick(View view){
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
                usernameTextView.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //文本变化后的回调，不需要处理
            }
        });



        return usernameTextView.getText().toString();
    }
    public String  setSignature(){
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
                signatureTextView.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //文本变化后的回调，不需要处理
            }
        });

        return signatureTextView.getText().toString();
    }

    private void sendEditRequest(String newName, String newSignature) {
        // 在这里添加后端的代码
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                try {
                    json.put("yonghuming", newName);
                    json.put("signature", newSignature);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //创建一个OkHttpClient对象
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(url + "/user/info")
                        .post(requestBody)
                        .build();

                // 发送请求并获取响应
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    // 检查响应是否成功
                    if (response.isSuccessful()) {
                        // 获取响应体
                        ResponseBody responseBody = response.body();
                        // 处理响应数据
                        String responseData = responseBody.string();
                        JSONObject responseJson = new JSONObject(responseData);
                        // 提取键为"code"的值
                        int code = responseJson.getInt("code");
                        //确定返回状态
                        switch (code) {
                            case 0:
                                // 检查响应头部中是否存在 "Set-Cookie" 字段
                                Headers headers = response.headers();
                                List<String> cookies = headers.values("Set-Cookie");
                                if(!cookies.isEmpty()){
                                    String s = cookies.get(0);
                                    System.out.println("cookie  " + s);
                                    String sessionCookie;
                                    if (s != null) {
                                        // 在这里处理获取到的会话信息
                                        // sessionCookie 变量中存储了服务器返回的会话信息
                                        // 可以将其存储在本地，后续的请求可以携带这个会话信息
                                        sessionCookie = s.substring(0, s.indexOf(";"));
                                        sharedPreferencesManager.setKEY_Session_ID(sessionCookie);
                                    } else {
                                        // 服务器没有返回会话信息
                                        // 可能是未登录状态或者会话已经过期
                                    }
                                    int day_count= responseJson.getInt("day_count");
                                    countingDays(day_count);
                                    // 处理获取到的天数数据
                                    System.out.println("天数：" + day_count);
                                }
                                setData(responseJson);
                            //用户名存储
                        }
                        // 记得关闭响应体
                        responseBody.close();
                    } else {
                        // 请求失败，处理错误
                        System.out.println("Request failed");
                    }
                } catch (IOException e) {
                    showRequestFailedDialog("网络请求失败");
                    e.printStackTrace();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    private void showRequestFailedDialog(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                builder.setTitle("注意")
                        .setMessage(str)
                        .setPositiveButton("确定", null)
                        .show();
            }
        });
    }
    private void setData(JSONObject responseJson) throws JSONException {
        // 提取键为"data"的值
        JSONObject dataJson = responseJson.getJSONObject("data");
        System.out.println(dataJson);
        String yonghuming = dataJson.optString("yonghuming", "");
        String signature = dataJson.optString("signature", "");
        sharedPreferencesManager.setUserSignature(signature);
        sharedPreferencesManager.setUserYonghuming(yonghuming);
    }
}
