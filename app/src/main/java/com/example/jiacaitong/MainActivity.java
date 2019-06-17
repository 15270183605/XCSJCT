package com.example.jiacaitong;

import life.lifemoney.ChongZhiJiLu;
import loginOrRegister.LoactionAndTianQi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import copyshouye.ActivityViewGroup;

public class MainActivity extends Activity implements Runnable{
	private Handler mainHandler;
	private Thread mThread;
	private boolean mflag;
	private Button skip;
	private int count =6;
	private double second=1;
	private RelativeLayout StartLoadLayout;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.startload);
		StartLoadLayout=(RelativeLayout)findViewById(R.id.StartLoadLayout);
		AlphaAnimation animation=new AlphaAnimation(0.3f, 1.0f);
		animation.setDuration(5000);
		StartLoadLayout.setAnimation(animation);
		skip = (Button) findViewById(R.id.skip);
		skip.getBackground().setAlpha(50);
		mThread =new Thread(this);
		AuToPlay();
		LoactionAndTianQi loaction=new LoactionAndTianQi(this);//初始化定位以及天气数据
		mainHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int what = msg.what;
				switch (what) {
				case 1:
					if(msg.arg1<4){
						skip.setVisibility(View.VISIBLE);
					}
					skip.setText("跳过  " + msg.arg1 + "秒");
					daojishi();
					break;
				} 
			}
		};
		skip.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if(mflag==false){
					mflag=true;}
				else{
					mflag=false;}
				Intent intent = new Intent(MainActivity.this, ActivityViewGroup.class);
				startActivity(intent);
                finish();
			}
		});
	}
	public void daojishi() {
		if (count == 0) {
			Intent intent = new Intent(MainActivity.this, ActivityViewGroup.class);
			startActivity(intent);
            finish(); 
		} 
	}
	public void AuToPlay() {
		if (!mThread.isAlive()) { // 开始计时器或者是重启计时器，设置标记为true
			mflag = true;
			// 判断是否是第一次启动，如果是不是第一次启动，那么状态就是Thread.State.TERMINATED
			// 不是的话，就需要重新的初始化，因为之前的已经结束了。
			// 并且要判断这个mCount 是否为-1，如果是的话，说名上一次的计时已经完成了，那么要重新设置。
			if (mThread.getState() == Thread.State.TERMINATED) {//结束
				mThread = new Thread(this); 
				mThread.start();
			} else {
				mThread.start();
			}
		} else { // 暂停计时器，设置标记为false
			mflag=false; }}
	
			//不可以使用 stop方法，会报错，java.lang.UnsupportedOperationException
					// //mThread.stop(); } } 
	public void run() {
					// //子线程必须要设置这个标记mflag和倒计时数。
				 while(mflag&&count>0&&count<=6){ 
					 try {
						
				  Thread.sleep((long) (second*1000)); 
				  count--;
				} 
					 catch (InterruptedException e) {
					 e.printStackTrace(); } //每间隔 一秒钟 发送 一个Message 给主线程的
					// handler让主线程的hanlder 来修改UI //注意 这里的 message可以是通过obtain来获取
					// 这样节省内存，它会自动的看有没有可以复用的，就不重复创建
					Message message =Message.obtain();
					message.what=1; 
					message.arg1=count;
					mainHandler.sendMessage(message);}
				 }
				
}
