package com.example.jiacaitong;


import Speak.AudioUtils;
import android.app.Application;

import com.iflytek.cloud.SpeechUtility;
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SpeechUtility.createUtility(getApplicationContext(), "appid=5b9907fb");
        AudioUtils.getInstance().init(getApplicationContext());
        //¹ã²¥¼àÌý
       /* IntentFilter filter = new IntentFilter(Intent.ACTION_TIME_TICK); 
        MyBroadcastReceiver receiver = new MyBroadcastReceiver(); 
        registerReceiver(receiver, filter); */
  
    }
}
