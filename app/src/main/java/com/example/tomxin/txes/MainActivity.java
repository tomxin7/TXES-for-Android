package com.example.tomxin.txes;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private User_name user_name;
    private Spinner type_spinner;//类型的下拉列表
    private Spinner mail_spinner;//邮箱的下拉列表
    private String add_type = "活动提醒";//获取下拉列表中当前内容
    private String add_mail = "活动提醒";//获取下拉列表中当前内容
    private EditText add_title;//提醒的标题
    private TextView add_time;//提醒的时间24小时制
    private TextView add_date;//提醒的日期
    private EditText add_conten;//提醒内容
    private EditText mail_conten;//邮箱的内容，不包括@
    private Button add_button;//提交按钮
    private DatePickerDialog date_dialog;//设置日期选择弹出框
    private TimePickerDialog time_dialog;//设置小时分钟弹出框
    private int year,monthOfYear,dayOfMonth,hourOfDay,minute;//用来存时间日期
    private List<String>type_list;
    private List<String>mail_list;
    private ArrayAdapter<String>type_adapter;
    private ArrayAdapter<String>mail_adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);
        type_spinner = (Spinner) findViewById(R.id.main_spinner);
        mail_spinner = (Spinner) findViewById(R.id.mail_spinner);
        add_time = (TextView) findViewById(R.id.add_time);
        add_date = (TextView) findViewById(R.id.add_date);
        add_conten = (EditText) findViewById(R.id.add_content);
        add_title = (EditText) findViewById(R.id.editText2);
        add_button = (Button) findViewById(R.id.main_button);
        mail_conten = (EditText) findViewById(R.id.mail_conten);
        user_name = (User_name)getApplication();
        if(user_name.getUser_name().equals("tomxin")){
            mail_conten.setText("865498311");
        }

        /*
        *这里开始初始化Spinner控件
         */
        //设置"提醒类型"的Spinner的数据源
        type_list = new ArrayList<String>();
        type_list.add("生活提醒");
        type_list.add("学习提醒");
        type_list.add("活动提醒");
        //新建ArrayAdapter（数组适配器）
        type_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,type_list);
        //adapter设置一个下拉列表样式
        type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //加载spinner的适配器
        type_spinner.setAdapter(type_adapter);
        //spinner设置监听器，可以获取到spinner中某一条数据
        // 为下拉菜单添加监听事件
        type_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // 设定选中的内容
                add_type = arg0.getItemAtPosition(arg2).toString();
                // 让下拉菜单的内容显示
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        //设置"邮箱类型"的Spinner的数据源
        mail_list = new ArrayList<String>();
        mail_list.add("@qq.com");
        mail_list.add("@163.com");
        mail_list.add("@126.com");
        mail_list.add("@139.com");
        //新建ArrayAdapter（数组适配器）
        mail_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,mail_list);
        //adapter设置一个下拉列表样式
        mail_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        //加载spinner的适配器
        mail_spinner.setAdapter(mail_adapter);
        //spinner设置监听器，可以获取到spinner中某一条数据
        // 为下拉菜单添加监听事件
        mail_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // 设定选中的内容
                add_mail = arg0.getItemAtPosition(arg2).toString();
                // 让下拉菜单的内容显示
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        /*
        *开始初始化时间控件
         */
        //获取系统的当前日期
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat nowDate =  new  SimpleDateFormat("yyyy/MM/dd");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    nDate    =    nowDate.format(curDate);
        add_date.setText(nDate);
        //获取系统的当前时间
        SimpleDateFormat nowTime =  new  SimpleDateFormat("HH:mm");
        Date curTime    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    nTime    =    nowTime.format(curTime);
        add_time.setText(nTime);

        //弹出小时分钟的选择控件 time_dialog
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute=calendar.get(Calendar.MINUTE);
        time_dialog=new TimePickerDialog(MainActivity.this,new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String mtext = ""+minute;
                String mhourOfDay =""+hourOfDay;
                //这里进行一个判断，是因为当分钟为10：09的时候，会显示10：9而0不见了，判断长度后把0给加上。
                if(mtext.length()==1){
                    mtext = "0"+mtext;
                }
                if(mhourOfDay.length()==1){
                    mhourOfDay = "0"+mhourOfDay;
                }
                String text = mhourOfDay + ":" + mtext ;
                add_time.setText(text);
            }
        },hourOfDay,minute,true);

        //弹出日期的选择控件
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        date_dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                String mmonthOfYear = ""+monthOfYear;
                String mdayOfMonth = ""+dayOfMonth;
                //前面的两个判断是把时间 2016.5.6转换成2016.05.06这种类型
                if(mmonthOfYear.length()==1){
                    mmonthOfYear = "0"+mmonthOfYear;
                }
                if(mdayOfMonth.length()==1){
                    mdayOfMonth = "0"+mdayOfMonth;
                }
                String text = year + "/" + mmonthOfYear + "/" + mdayOfMonth;
                add_date.setText(text);
            }
            },year,monthOfYear,dayOfMonth);

        //日期控件的监听，如果有改变就更新TextView的数据
        add_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_dialog.show();
            }
        });

        //小时分钟控件的监听，如果有改变就更新TextView的数据
        add_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_dialog.show();
            }
        });

        //监听Button按钮
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Volley_Post();//post请求函数
            }
        });
    }



    //给servlet服务端发送post请求，把数据发送过去
    private void Volley_Post(){
        String url = "http://tomxin.cn/mail_System_web_server/servlet/add_remind_servlet?";
        final String add_name = user_name.getUser_name();
        final String type = add_type;
        final String title =  add_title.getText().toString();
        final String date = add_date.getText().toString();
        final String time = add_time.getText().toString();
        final String conten = add_conten.getText().toString();
        final String mail = mail_conten.getText().toString() + add_mail;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,response, Toast.LENGTH_LONG).show();
                        Log.d(TAG, "response -> " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("add_name",add_name);
                map.put("add_type", type);
                map.put("add_title", title);
                map.put("add_date", date);
                map.put("add_time", time);
                map.put("add_conten", conten);
                map.put("add_mail", mail);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}
