package com.example.serenai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
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
public class LoginActivity extends AppCompatActivity {
    public String url = "http://1.15.66.98:8081/api";

    private EditText editTextAccount;
    private EditText editTextPwd;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        // 绑定视图
        editTextAccount = findViewById(R.id.editTextAccount);
        editTextPwd = findViewById(R.id.editTextPwd);
    }

    // 处理返回逻辑
    public void onBackClick(View view) {
        // 创建一个 Intent，将当前 Activity 与目标 LoginActivity 关联
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent); // 启动 EntryActivity
        // 关闭当前活动
        finish();
    }

    // 处理登录按钮点击事件
    public void Login(View view) {
        String account = editTextAccount.getText().toString();
        String password = editTextPwd.getText().toString();
        if (account.equals("") || password.equals("")) {
            return;
        }
        // 对密码进行 SHA256 加密
        String encryptedPassword = encryptSHA256(password);

        // 发送登录请求至后端
        sendLoginRequest(account, encryptedPassword);
    }

    // 判断登录凭证是否有效的逻辑
    private boolean isValidCredentials(String account, String password) {
        // 在这里可以添加你的验证逻辑，例如检查账号和密码是否匹配
        // 返回 true 表示验证通过，返回 false 表示验证失败
        return true; // 这里暂时假设验证总是通过
    }

    // 发送登录请求至后端
    private void sendLoginRequest(String account, String encryptedPassword) {
        // 在这里添加发送登录请求至后端的代码
        new Thread(new Runnable() {
            @Override
            public void run() {
                MediaType JSON = MediaType.parse("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                try {
                    json.put("uname", account);
                    json.put("password", encryptedPassword);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //创建一个OkHttpClient对象
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                Request request = new Request.Builder()
                        .url(url + "/user/login")
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
                                }

                                setData(responseJson);
                                // 登录成功，改变登录状态
                                if (sharedPreferencesManager.isLoggedIn()) {
                                    //这里添加逻辑，跳转到主页面
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showRequestFailedDialog("登录成功");
                                        }
                                    });
                                }
                                break;
                            //登录成功
                            case 2002:
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showRequestFailedDialog("用户不存在");
                                    }
                                });
                                break;
                            //用户不存在
                            case 1002:
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showRequestFailedDialog("密码错误");
                                    }
                                });
                                break;
                            //密码错误
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

    // 对密码进行 SHA256 加密
    private String encryptSHA256(String input) {
        // 在这里添加 SHA256 加密逻辑
        return input; // 这里暂时直接返回输入，需要实际加密逻辑
    }

    // 显示 Toast 提示信息
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // 弹出请求失败的对话框
    private void showRequestFailedDialog(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
        String uid = dataJson.optString("uid", "");
        String uname = dataJson.optString("uname", "");

        sharedPreferencesManager.setUserID(uid);
        sharedPreferencesManager.setUsername(uname);
        sharedPreferencesManager.setLoggedIn(true);
    }
}
