package com.atguigu.maxwu.mobileplayer.app;

import android.app.Application;
import android.content.Context;

/**
 * 作者: WuKai
 * 时间: 2017/5/28
 * 邮箱: 648838173@qq.com
 * 作用:
 */

public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    public static Context getContext(){
        return mContext;
    }
}
