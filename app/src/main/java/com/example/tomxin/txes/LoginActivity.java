package com.example.tomxin.txes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {
    private Button login_button;
    private EditText login_username;
    private EditText login_password;
    private TextView register_textview;
    private User_name user_name;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user_name = (User_name)getApplication();
        login_button = (Button) findViewById(R.id.login_button);
        register_textview = (TextView) findViewById(R.id.register);
        url = "http://tomxin.cn/mail_System_web_server/servlet/mail_sql_servlet";

        //监听登录按钮，获取用户名和密码进行登录判断
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_username = (EditText) findViewById(R.id.login_username);
                login_password = (EditText) findViewById(R.id.login_password);
                url = "http://tomxin.cn/mail_System_web_server/servlet/add_login_servlet";
                url=url+"?name="+login_username.getText().toString()+"&password="+login_password.getText().toString();
                StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        if(s.equals("ok")){
                            user_name.setUser_name(login_username.getText().toString());
                            Toast.makeText(LoginActivity.this,"欢迎您，尊敬的 "+login_username.getText().toString(), Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"请检查您的用户名和密码", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("TAG", volleyError.getMessage(), volleyError);
                    }
                }
                );
                RequestQueue mQueue = Volley.newRequestQueue(LoginActivity.this);
                mQueue.add(stringRequest);


        }
    });
        register_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });


}
}