package Dialog;

import loginOrRegister.Main;
import sqlite.UserSQLite;
import usercenter.UserAddShouZhiCode;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.jiacaitong.R;
import entity.FamilyMember;

public class UserJiaoYanDialog extends Dialog implements OnClickListener {
	private UserSQLite usersqlite;
	private SQLiteDatabase db;
	private Context context;
	private EditText UserNameEdit, UserPasswordEdit, YanZhengMaEdit;
	private TextView UserYanZheng, Cancel,GetYanZhengMa;
    private String yanzhengma;
    private int count = 60;//倒计时计数
    private int number=0;//开始或结束倒计时标志
    private int NOTIFYID_1 = 1;
	private NotificationManager notificationManager;
    Handler handler1 = new Handler();
	private Runnable thread1 = new Runnable() {
		public void run() {
			daojishi();
			handler1.postDelayed(thread1, 1000);
		}
	};;
	public UserJiaoYanDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uesrjiaoyan);
		init();
	}

	public void init() {
		UserNameEdit = (EditText) findViewById(R.id.UserNameEdit);
		UserPasswordEdit = (EditText) findViewById(R.id.UserPasswordEdit);
		YanZhengMaEdit = (EditText) findViewById(R.id.YanZhengMaEdit);
		UserYanZheng = (TextView) findViewById(R.id.UserYanZheng);
		Cancel = (TextView) findViewById(R.id.Cancel);
		GetYanZhengMa = (TextView) findViewById(R.id.UserGetYanZhengMa);
		UserYanZheng.setOnClickListener(this);
		Cancel.setOnClickListener(this);
		GetYanZhengMa.setOnClickListener(this);
		usersqlite=new UserSQLite(context, "FamilyFinance.db", null, 1);
		db=usersqlite.getReadableDatabase();
		notificationManager =(NotificationManager)context. getSystemService(Context.NOTIFICATION_SERVICE);
		new Thread(thread1).start();

	}
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.UserGetYanZhengMa:
			number = 1;
			if (count <= 0) {// 启动线程
				count = 60;
			}
			yanzhengma = String.format("%04d",
					(int) (Math.random() * 10000 % 10000));
            Notification.Builder builder = new Notification.Builder(context.getApplicationContext());
            builder.setSmallIcon(R.drawable.image7);
            builder.setTicker("您有一条新消息");
            builder.setContentTitle("消息：");
            builder.setContentText(UserNameEdit.getText()
                    .toString() + "用户" + ",这次验证码是" + yanzhengma);
            builder.setWhen(System.currentTimeMillis()); //发送时间
            builder.setDefaults(Notification.DEFAULT_ALL);
            builder.setAutoCancel(true);
            long[] vibrates =new long[]{800, 1000, 800, 1000, 800, 1000};// 第零个值表示手机静止的时长，第一个值表示手机震动的时长，
            Notification notification = builder.getNotification();
            notification.vibrate = vibrates;
            notification.ledARGB= Color.GREEN;
            notification.ledOnMS=1000;
            notification.ledOffMS=1000;
            notification.flags=Notification.FLAG_SHOW_LIGHTS;
            notificationManager.notify(1, notification);
			break;
		case R.id.Cancel:
			this.dismiss();
			break;
		case R.id.UserYanZheng:
			jiaoyan();
			break;
		}

	}

	public void jiaoyan() {
		
        String username=UserNameEdit.getText().toString();
        String password=UserPasswordEdit.getText().toString();
        long count=usersqlite.UserCount(db, username, password);
        if(count==0){
        	Toast.makeText(context, "您目前是非法用户!", Toast.LENGTH_LONG).show();
        }else{
        	if(!username.equals(Main.returnName())&&!password.equals(Main.returnPsd())){
        		Toast.makeText(context, "抱歉，您不是当前在线用户", Toast.LENGTH_LONG).show();
        	}else if(!YanZhengMaEdit.getText().toString().equals(yanzhengma)){
        		Toast.makeText(context, "验证码错误!", Toast.LENGTH_LONG).show();}
        	else {
        		FamilyMember member=new FamilyMember();
        		member=usersqlite.queryMember(db, username);
        		Toast.makeText(context, "校验成功,您的支付码界面已开启!", Toast.LENGTH_LONG).show();
        		this.dismiss();
        		Intent intent=new Intent(context,UserAddShouZhiCode.class);
        		intent.putExtra("username", username);
        		intent.putExtra("phone", member.getPhoneNumber());
        		intent.putExtra("pwd", password);
        		context.startActivity(intent);
        	}
        }
	}
	// 验证码倒计时
			public void daojishi() {
				if (number == 0) {
					GetYanZhengMa.setText("获取验证码");
				} else {
					count--;
					if (count > 0) {
						GetYanZhengMa.setClickable(false);
						GetYanZhengMa.setText(count + "秒后重获");

					} else {
						GetYanZhengMa.setClickable(true);
						GetYanZhengMa.setText("获取验证码");

					}
				}
			}
}
