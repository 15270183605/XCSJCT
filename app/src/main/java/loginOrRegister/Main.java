 package loginOrRegister;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import saoma.encoding.EncodingHandler;
import sqlite.UserSQLite;
import start.StartMain;
import work.mine;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.MainActivity;
import com.example.jiacaitong.R;
import com.google.zxing.WriterException;
public class Main extends Activity implements OnClickListener,
		OnFocusChangeListener {
	private static Bitmap bitmap;
	private TextView forgotPwd, findpwd, updatepwd;
	private LinearLayout layout1, layout2, layout3;
	private RelativeLayout Loginlayout;
	private EditText userName, password, username, userpwd, userphone,
			usernames, userpassword, yanzhengma, newpwd,salary;
	private RadioGroup radiogroup;
	private RadioButton radiobutton1, radiobutton2;
	private Button login, register, getyanzhengma;
	private CheckBox remPwd;
	private CheckBox autoLogin;
	private ImageView seepwd,PswImage;
	private boolean flag = true;
	private LayoutInflater inflater1;
	private View view1, view2;
	private String YanZhengMa;
	private int count = 60;//倒计时计数
	private int number=0;//开始或结束倒计时标志
	private NotificationManager notificationManager;
	private  SharedPreferences share;
	private int jizhu, auto;
	private UserSQLite usersqlite;
	private SQLiteDatabase db;
	private  Long usercount;
	private int status;
	private String credential;
	private static String Name;
	private static String Psd;
	Handler handler1 = new Handler();
	private Bitmap bitMap;
	private Runnable thread1 = new Runnable() {
		public void run() {
			daojishi();
			handler1.postDelayed(thread1, 1000);
		}
	};;
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		inflater1 = (LayoutInflater) Main.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		view2 = inflater1.inflate(R.layout.findpassword, null);
		usersqlite = new UserSQLite(this,"FamilyFinance.db",null,1);
		db=usersqlite.getReadableDatabase();
		getyanzhengma = (Button) view2.findViewById(R.id.getYanZhengMa);
		forgotPwd = (TextView) findViewById(R.id.forgotPwd);
		userName = (EditText) findViewById(R.id.userName);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);
		remPwd = (CheckBox) findViewById(R.id.remPwd);
		autoLogin = (CheckBox) findViewById(R.id.autoLogin);
		forgotPwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//添加下划线
		seepwd = (ImageView) findViewById(R.id.seepwd);
		PswImage = (ImageView) findViewById(R.id.PswImage);
		Loginlayout = (RelativeLayout)findViewById(R.id.LoginLayout);
		login.setOnClickListener(this);
		register.setOnClickListener(this);
		forgotPwd.setOnClickListener(this);
		seepwd.setOnClickListener(this);
		userName.setOnFocusChangeListener(this);
		password.setOnFocusChangeListener(this);
		PswImage.setOnClickListener(this);
		password.setTransformationMethod(PasswordTransformationMethod
				.getInstance());
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		new Thread(thread1).start();
		
		// 记住密码，自动登陆
		share = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		jizhu = share.getInt("jizhu", 0);
		auto = share.getInt("auto", 0);
		if(mine.ExitDengLu()==1){ 
    		userName.setText("");
    		password.setText("");
    		remPwd.setChecked(false);
    		autoLogin.setChecked(false);}
		else{ 
		if (jizhu == 1) {
			remPwd.setChecked(true);
			userName.setText(share.getString("username", ""));
			password.setText(share.getString("password", ""));
		}
		if (auto==2) {
			autoLogin.setChecked(true);
			Name=share.getString("username", "");
			Psd=password.getText().toString();
			Intent intent = new Intent(Main.this, MainActivity.class);
			startActivity(intent);
			this.finish();
		}
	}
		passwordCheck();}
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.login:
		    login();

			break;
		case R.id.forgotPwd:
			findORupdatePwd();
			break;
		case R.id.register:
			register();
			break;
		case R.id.seepwd:
			// 明码
			if (flag == true) {
				password.setTransformationMethod(HideReturnsTransformationMethod
						.getInstance());
				flag = false;
				seepwd.setBackgroundResource(R.drawable.pwdsee);
			} else {
				// 密码
				password.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
				flag = true;
				seepwd.setBackgroundResource(R.drawable.pwdsee1);
			}
			break;
		case R.id.getYanZhengMa:
			number = 1;
			if (count <= 0) {// 启动线程
				count = 60;
			}
			YanZhengMa = String.format("%04d",
					(int) (Math.random() * 10000 % 10000));
			// Toast.makeText(FindQQPassword.this, "验证码为:"+YanZhengMa,
			// 1000).show();
			AddNotify(usernames.getText()
					.toString() + "用户" + ",这次验证码是" + YanZhengMa,0);
			break;
		case R.id.textviews:

			layout2.setVisibility(View.GONE);
			layout1.setVisibility(View.GONE);
			break;
		case R.id.textview2:
			layout1.setVisibility(View.VISIBLE);
			layout2.setVisibility(View.VISIBLE);
			break;
		case R.id.LuYin:
			break;
		
		}
	}

	@Override
	public void onFocusChange(View view, boolean hasFocus) {
		switch (view.getId()) {
		case R.id.userName:
			if (!hasFocus) {
				if (userName.getText().toString().trim().length() == 0) {
					Toast.makeText(Main.this, "用户名不能为空", Toast.LENGTH_SHORT)
							.show();
				}

			}
			break;
		case R.id.password:
			if (!hasFocus) {
				if (password.getText().toString().trim().length() < 6) {
					Toast.makeText(Main.this, "密码长度不能小于6个单位",
							Toast.LENGTH_SHORT).show();
				}

			}
			break;
		case R.id.usernames:
			usercount=usersqlite.UserCount(db, usernames.getText().toString(),"");
			if(usercount!=1){
				Toast.makeText(Main.this, "用户不存在",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.userpassword:
			String pwd=null;
			Cursor cursor=usersqlite.queryUser(db, usernames.getText().toString());
			if(cursor.moveToNext()){
				 pwd=cursor.getString(cursor.getColumnIndex("passWord"));
				
			}
			 cursor.close();
		    if(userpassword.getText().toString().length()<6){
		Toast.makeText(Main.this,"请先确认输入密码的合法性", Toast.LENGTH_SHORT).show();
	}
		    else if(!(userpassword.getText().toString()).equals(pwd)){
		    	Toast.makeText(Main.this,"原密码错误", Toast.LENGTH_SHORT).show();
		    }
			break;
		case R.id.yanzhengma:
			 if(!YanZhengMa.equals(yanzhengma.getText().toString())){
		    	 Toast.makeText(Main.this,"验证码错误", Toast.LENGTH_SHORT).show();
		     }
			break;
		case R.id.userpwd:
			if(userpwd.getText().toString().trim().length()<6){
				Toast.makeText(this, "密码长度不能小于6", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.userphone:
			if(userphone.getText().toString().trim().length()==0 || userphone.getText().toString().equals(null)){
				Toast.makeText(this, "电话号码不能为空！", Toast.LENGTH_LONG).show();
			}
			break;
		}

	}

	// 用户注册
	public void register() {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date(System.currentTimeMillis());
		final String Time=format.format(date);
		view1 = inflater1.inflate(R.layout.register, null);
		registerinit();
		AlertDialog.Builder alert = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.Alert));
		alert.setTitle("用户操作");
		alert.setView(view1);

		alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}

		});
		alert.setPositiveButton("注册", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
		   usercount=usersqlite.UserCount(db, username.getText().toString(), userpwd.getText().toString());
		   if(usercount==1){
			   Toast.makeText(Main.this, "用户已存在", Toast.LENGTH_LONG).show();
		   }else if(usercount==0){
			   String userphoneText = userphone.getText().toString();
				if (!userphoneText.equals("")) {
					try {
						bitMap = EncodingHandler.createQRCode("用户信息"+userphoneText, 350);//创建二维码
					} catch (WriterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(userpwd.getText().toString().trim().length()<6){
						Toast.makeText(Main.this, "密码长度不能小于6", Toast.LENGTH_LONG).show();
					}
					else if(userphone.getText().toString().trim().length()==0 || userphone.getText().toString().equals(null)){
						Toast.makeText(Main.this, "电话号码不能为空！", Toast.LENGTH_LONG).show();
					}else {
					 usersqlite.addUser(db, username.getText().toString(), userpwd.getText().toString(), userphone.getText().toString(), String.valueOf(status), salary.getText().toString(), "",img(R.drawable.user),0,code(bitMap),Time);
					   Toast.makeText(Main.this, "注册成功", Toast.LENGTH_LONG).show();
				}}else {
					Toast.makeText(Main.this, "信息填写不正确!", Toast.LENGTH_SHORT).show();
				}
			  
		   }
			}

		});
		alert.show();
	}
//用户登录
	public void login(){
		 usercount=usersqlite.UserCount(db, userName.getText().toString(), password.getText().toString());
			if (usercount==1) {
				usersqlite.UpdateCount(db, userName.getText().toString(), password.getText().toString(), 1);
				getImageView(Loginlayout);
				Editor editor = share.edit();
				if (remPwd.isChecked()) {
					editor.putString("username",  userName.getText().toString());
					editor.putString("password", password.getText().toString());
					editor.putInt("jizhu", 1);
					} else {
					editor.putInt("jizhu", 0);
					
				}

				if (autoLogin.isChecked()) {
					editor.putInt("auto", 2);
				} else {
					editor.putInt("auto", 0);
				}
				editor.commit();
				Name=userName.getText().toString();
				Psd=password.getText().toString();
				Intent intent = new Intent(Main.this, StartMain.class);
				startActivity(intent);
			    this.finish();
				overridePendingTransition(0, 0);
				
			} else {
				Toast.makeText(Main.this, "用户名/密码错误", Toast.LENGTH_LONG).show();
			}
	}
	// 找回或修改密码
	public void findORupdatePwd() {
		view2 = inflater1.inflate(R.layout.findpassword, null);
		findORupdateinit();
		AlertDialog.Builder alert = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.Alert));
		alert.setTitle("用户操作");

		alert.setView(view2);

		alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}

		});
		alert.setPositiveButton("修改", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
				String username=usernames.getText().toString();
				String pwd=newpwd.getText().toString();
			    usersqlite.updatePwd(db,username,pwd);
			    Toast.makeText(Main.this,"密码修改成功，您现在的密码是"+newpwd.getText().toString(), Toast.LENGTH_SHORT).show();
			}
			}

		);
		alert.setNeutralButton("找回", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String pwd = null;
				Cursor cursor=usersqlite.queryUser(db, usernames.getText().toString());
				if(cursor.moveToNext()){
					 pwd=cursor.getString(cursor.getColumnIndex("passWord"));
					
				}
				 cursor.close();
				AddNotify(usernames.getText()
						.toString() + "用户" + ",您的密码是" + pwd,2);
			}
		});
		alert.show();
	}
	// 单选按钮监听方法
	public void click(View v) {

		int checkedRadioButtonId = radiogroup.getCheckedRadioButtonId();
		int radio = 0; // 默认值为0
		switch (checkedRadioButtonId) {
		case R.id.radioButton1: // 选中的是在职按钮
			radio = 1;
			status=0;
			layout3.setVisibility(View.VISIBLE);
			break;

		case R.id.radioButton2: // 选中的不在职按钮
			radio = 2;
			status=1;
			layout3.setVisibility(View.GONE);
			break;
		}
	}

	// 注册界面初始化
	public void registerinit() {
		username = (EditText) view1.findViewById(R.id.username);
		userpwd = (EditText) view1.findViewById(R.id.userpwd);
		userphone = (EditText) view1.findViewById(R.id.userphone);
		salary = (EditText) view1.findViewById(R.id.salary);
		radiogroup = (RadioGroup) view1.findViewById(R.id.radioGroup1);
		radiobutton1 = (RadioButton) view1.findViewById(R.id.radioButton1);
		radiobutton2 = (RadioButton) view1.findViewById(R.id.radioButton2);
		layout3 = (LinearLayout) view1.findViewById(R.id.layout3);
	}

	// 找回密码界面初始化
	public void findORupdateinit() {
		usernames = (EditText) view2.findViewById(R.id.usernames);
		userpassword = (EditText) view2.findViewById(R.id.userpassword);
		newpwd = (EditText) view2.findViewById(R.id.newpwd);
		yanzhengma = (EditText) view2.findViewById(R.id.yanzhengma);
		findpwd = (TextView) view2.findViewById(R.id.textviews);
		findpwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		updatepwd = (TextView) view2.findViewById(R.id.textview2);
		updatepwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		getyanzhengma = (Button) view2.findViewById(R.id.getYanZhengMa);
		layout1 = (LinearLayout) view2.findViewById(R.id.layout1);
		layout2 = (LinearLayout) view2.findViewById(R.id.layout2);
		getyanzhengma.setOnClickListener(this);
		updatepwd.setOnClickListener(this);
		findpwd.setOnClickListener(this);
		userpassword.setOnFocusChangeListener(this);
		usernames.setOnFocusChangeListener(this);
		newpwd.setOnFocusChangeListener(this);
		yanzhengma.setOnFocusChangeListener(this);
	}
	// 验证码倒计时
		public void daojishi() {
			if (number == 0) {
				getyanzhengma.setText("获取验证码");
			} else {
				count--;
				if (count > 0) {
					getyanzhengma.setClickable(false);
					getyanzhengma.setText(count + "秒后重新获取");

				} else {
					getyanzhengma.setClickable(true);
					getyanzhengma.setText("获取验证码");

				}
			}
		}
		public static String returnName(){
			return Name;
		}
		public static String returnPsd(){
			return Psd;
		}
		 //获取视图
		 public Bitmap getImageView(View view){
			 int width=view.getMeasuredWidth();
			 int height=view.getMeasuredHeight();
			 bitmap=Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			 view.draw(new Canvas(bitmap));
			 Canvas canvas=new Canvas(bitmap);
			 Paint paint=new Paint();
			 paint.setColor(Color.TRANSPARENT);
			 canvas.drawBitmap(bitmap, 0, 0,null);
			 return bitmap;
		 }
		 public static Bitmap returnBitmap(){
			 return bitmap;
		 }
		 public void passwordCheck(){
			 password.addTextChangedListener(new TextWatcher() {
				 
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			long count=usersqlite.UserCount(db, userName.getText().toString(), password.getText().toString());
					if (count==1) {
						PswImage.setImageResource(R.drawable.pswopen);
					}else{
						PswImage.setImageResource(R.drawable.pswlock);
					}
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
						int arg3) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		 }
		 //将初始化的图片转换成二进制存储
		 public byte[] img(int id)
		{
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(id)).getBitmap();
		     bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	       return baos.toByteArray();
		}
		 public byte[] code(Bitmap bit){
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		      bit.compress(Bitmap.CompressFormat.PNG, 100, baos);
	       return baos.toByteArray();
		 }
		 public void AddNotify(String msg,int number){
			 Notification.Builder builder = new Notification.Builder(this);
			 builder.setSmallIcon(R.drawable.image7);
			 builder.setTicker("您有一条新消息");
			 builder.setContentTitle("消息：");
			 builder.setContentText(msg);
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
			 notificationManager.notify(number, notification);
		 }

}
