package com.example.administrator.shoping;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.shoping.bean.User;
import com.example.administrator.shoping.msg.LoginRespMsg;
import com.example.administrator.shoping.utils.DESUtil;
import com.example.administrator.shoping.utils.ToastUtils;
import com.example.administrator.shoping.view.ClearEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class LoginActivity extends AppCompatActivity{

    private Button login;
    private ClearEditText mEtxtPhone;
    private ClearEditText mEtxtPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.btn_login);
        mEtxtPhone = (ClearEditText) findViewById(R.id.etxt_phone);
        mEtxtPwd = (ClearEditText) findViewById(R.id.etxt_pwd);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String phone = mEtxtPhone.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    ToastUtils.show(LoginActivity.this, "请输入手机号码");
                    return;
                }
                String pwd = mEtxtPwd.getText().toString().trim();
                if(TextUtils.isEmpty(pwd)){
                    ToastUtils.show(LoginActivity.this, "请输入密码");
                    return;
                }
                Map<String, String> params = new HashMap<>(2);
                params.put("phone", phone);
                params.put("password", DESUtil.encode(Contants.DES_KEY, pwd));
                //.addParams("phone", "11").addParams("password", DESUtil.encode(Contants.DES_KEY, "11"))
                OkHttpUtils.post().url(Contants.API.REG).params(params).build().execute(new StringCallback(){
                    @Override
                    public void onError(Call call, Exception e, int id){
                    }

                    @Override
                    public void onResponse(String response, int id){
                        Gson gson=new Gson();
                        LoginRespMsg<User> userLoginRespMsg=gson.fromJson(response,new TypeToken<LoginRespMsg<User>>(){}.getType());
                        //User data = userLoginRespMsg.getData();

                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        Log.d("LoginActivity", response);
                    }
                });
            }
        });
    }
}
