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
		LoactionAndTianQi loaction=new LoactionAndTianQi(this);//��ʼ����λ�Լ���������
		mainHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int what = msg.what;
				switch (what) {
				case 1:
					if(msg.arg1<4){
						skip.setVisibility(View.VISIBLE);
					}
					skip.setText("����  " + msg.arg1 + "��");
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
		if (!mThread.isAlive()) { // ��ʼ��ʱ��������������ʱ�������ñ��Ϊtrue
			mflag = true;
			// �ж��Ƿ��ǵ�һ������������ǲ��ǵ�һ����������ô״̬����Thread.State.TERMINATED
			// ���ǵĻ�������Ҫ���µĳ�ʼ������Ϊ֮ǰ���Ѿ������ˡ�
			// ����Ҫ�ж����mCount �Ƿ�Ϊ-1������ǵĻ���˵����һ�εļ�ʱ�Ѿ�����ˣ���ôҪ�������á�
			if (mThread.getState() == Thread.State.TERMINATED) {//����
				mThread = new Thread(this); 
				mThread.start();
			} else {
				mThread.start();
			}
		} else { // ��ͣ��ʱ�������ñ��Ϊfalse
			mflag=false; }}
	
			//������ʹ�� stop�������ᱨ��java.lang.UnsupportedOperationException
					// //mThread.stop(); } } 
	public void run() {
					// //���̱߳���Ҫ����������mflag�͵���ʱ����
				 while(mflag&&count>0&&count<=6){ 
					 try {
						
				  Thread.sleep((long) (second*1000)); 
				  count--;
				} 
					 catch (InterruptedException e) {
					 e.printStackTrace(); } //ÿ��� һ���� ���� һ��Message �����̵߳�
					// handler�����̵߳�hanlder ���޸�UI //ע�� ����� message������ͨ��obtain����ȡ
					// ������ʡ�ڴ棬�����Զ��Ŀ���û�п��Ը��õģ��Ͳ��ظ�����
					Message message =Message.obtain();
					message.what=1; 
					message.arg1=count;
					mainHandler.sendMessage(message);}
				 }
				
}
