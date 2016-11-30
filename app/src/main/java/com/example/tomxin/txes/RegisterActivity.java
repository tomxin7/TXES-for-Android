package com.example.tomxin.txes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Timer;
import java.util.TimerTask;

public class RegisterActivity extends AppCompatActivity {
    private Button register_button;
    private EditText register_username;
    private EditText register_password;
    private EditText register_ok_password;
    private String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_button = (Button) findViewById(R.id.register_button);
        register_username = (EditText) findViewById(R.id.register_user);
        register_password = (EditText) findViewById(R.id.register_password);
        register_ok_password = (EditText)findViewById(R.id.register_ok_password);
        url = "http://tomxin.cn/mail_System_web_server/servlet/add_register_servlet?";
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = register_username.getText().toString();
                String password = register_password.getText().toString();
                String ok_password = register_ok_password.getText().toString();
                String get_url = url + "regiter_name="+username+"&regiter_password="+password;
                if(password.equals(ok_password)){
                    StringRequest stringRequest = new StringRequest(get_url, new Response.Listener<String>() {
                        /*
                         *s里面存着服务器传过来的返回值：
                         * "ok"代表注册成功
                         * "用户名已经存在，请更换注册或者找回密码"你懂的
                         * 所有错误直接返回，然后Toast打印出来
                         */
                        public void onResponse(String s) {
                            if(s.equals("ok")){
                                Toast.makeText(RegisterActivity.this,"注册成功，正在返回登录界面", Toast.LENGTH_LONG).show();
                                Timer timer = new Timer();
                                TimerTask timerTask = new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                                        startActivity(intent);
                                        RegisterActivity.this.finish();
                                    }
                                };
                                timer.schedule(timerTask, 1000 * 3);
                            }else{
                                Toast.makeText(RegisterActivity.this,s, Toast.LENGTH_LONG).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Log.e("TAG", volleyError.getMessage(), volleyError);
                        }
                    }
                    );
                    RequestQueue mQueue = Volley.newRequestQueue(RegisterActivity.this);
                    mQueue.add(stringRequest);

                }
                else{
                    Toast.makeText(RegisterActivity.this,"两次密码输入不一致", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
