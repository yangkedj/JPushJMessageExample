package com.example.jpushdemo;


import android.app.Application;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;

/**
 * For developer startup JPush SDK
 * 
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class ExampleApplication extends Application {
    private static final String TAG = "JPush";

    @Override
    public void onCreate() {    	     
    	 Log.d(TAG, "[ExampleApplication] onCreate");
         super.onCreate();
         
         JMessageClient.init(getApplicationContext());
         JPushInterface.setDebugMode(true);
         new MessageEventReceiver(getApplicationContext());
    }
}
