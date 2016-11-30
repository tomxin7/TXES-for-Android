package com.example.tomxin.txes;

import android.app.Application;

public class User_name extends Application {
    private String user_name;

    public String getUser_name() {
        return this.user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    @Override
    public void onCreate() {
        user_name = "hello";
        super.onCreate();
    }
}