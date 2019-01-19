package com.chia.myrecycleview.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chia.myrecycleview.MainActivity;
import com.chia.myrecycleview.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

// 此Activity將會以對話視窗模式顯示，呼叫setResult()設定回傳結果
public class Login_Activity extends AppCompatActivity {
    private static final String TAG = "LoginDialogActivity";
    private EditText etUser;
    private EditText etPassword;
    private TextView tvMessage;
    private MyTask loginTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        setResult(RESULT_CANCELED);
    }

    private void findViews() {
        etUser = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
        Button btLogin = findViewById(R.id.btLogin);
//        tvMessage = findViewById(R.id.tvMessage);

        btLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String user = etUser.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if (user.length() <= 0 || password.length() <= 0) {
//                    showMessage(R.string.msg_InvalidUserOrPassword);
                    return;
                }

                if (isUserValid(user, password)) {//是否登入成功
                    SharedPreferences pref = getSharedPreferences(Common.PREF_FILE,
                            MODE_PRIVATE);
                    pref.edit()//登入成功存入Android偏好設定(最高段做法),第二種為幫存好帳密,自動瑱入但要自己按登入鍵
                            .putBoolean("login", true)
                            .putString("user", user)
                            .putString("password", password)
                            .apply();
                    setResult(RESULT_OK);
                    finish();//回到前一頁
                } else {
//                    showMessage(R.string.msg_InvalidUserOrPassword);
                }
            }
        });
    }

    @Override
    protected void onStart() {//忘記看avtivity生命週期
        //登入的功能在每次使用功能的時候都要做登入,差別在於登入的時候有沒有看到登入畫面
        super.onStart();
        SharedPreferences pref = getSharedPreferences(Common.PREF_FILE, MODE_PRIVATE);//取得之前登入的參數
        boolean login = pref.getBoolean("login", false);
        if (login) {
            String name = pref.getString("user", "");
            String password = pref.getString("password", "");
            if (isUserValid(name, password)) {//如果帳密登入正確就finish,所以還沒看到登入畫面就看不到了
                setResult(RESULT_OK);
                finish();
            } else {
                pref.edit().putBoolean("login", false).apply();//若錯誤將login狀態改為false
//                showMessage(R.string.msg_InvalidUserOrPassword);
            }
        }
    }

    private void showMessage(int msgResId) {
        tvMessage.setText(msgResId);
    }

    private boolean isUserValid(String name, String password) {
        String url = Common.URL + "/LoginServlet";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("password", password);
        loginTask = new MyTask(url, jsonObject.toString());
        boolean isUserValid = false;
        try {
            String jsonIn = loginTask.execute().get();
            jsonObject = new Gson().fromJson(jsonIn, JsonObject.class);
            isUserValid = jsonObject.get("isUserValid").getAsBoolean();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return isUserValid;
    }


    public void onIgnoreClick(View view) {
        Intent loginIntent = new Intent(this, MainActivity.class);
        int onIgnore = 2;
        Bundle bundle = new Bundle();
        bundle.putInt("onIgnore", onIgnore);
        loginIntent.putExtras(bundle);

        startActivityForResult(loginIntent, 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (loginTask != null) {
            loginTask.cancel(true);
        }
    }
}
