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
	private int count = 60;//����ʱ����
	private int number=0;//��ʼ���������ʱ��־
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
		forgotPwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//����»���
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
		
		// ��ס���룬�Զ���½
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
			// ����
			if (flag == true) {
				password.setTransformationMethod(HideReturnsTransformationMethod
						.getInstance());
				flag = false;
				seepwd.setBackgroundResource(R.drawable.pwdsee);
			} else {
				// ����
				password.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
				flag = true;
				seepwd.setBackgroundResource(R.drawable.pwdsee1);
			}
			break;
		case R.id.getYanZhengMa:
			number = 1;
			if (count <= 0) {// �����߳�
				count = 60;
			}
			YanZhengMa = String.format("%04d",
					(int) (Math.random() * 10000 % 10000));
			// Toast.makeText(FindQQPassword.this, "��֤��Ϊ:"+YanZhengMa,
			// 1000).show();
			AddNotify(usernames.getText()
					.toString() + "�û�" + ",�����֤����" + YanZhengMa,0);
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
					Toast.makeText(Main.this, "�û�������Ϊ��", Toast.LENGTH_SHORT)
							.show();
				}

			}
			break;
		case R.id.password:
			if (!hasFocus) {
				if (password.getText().toString().trim().length() < 6) {
					Toast.makeText(Main.this, "���볤�Ȳ���С��6����λ",
							Toast.LENGTH_SHORT).show();
				}

			}
			break;
		case R.id.usernames:
			usercount=usersqlite.UserCount(db, usernames.getText().toString(),"");
			if(usercount!=1){
				Toast.makeText(Main.this, "�û�������",
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
		Toast.makeText(Main.this,"����ȷ����������ĺϷ���", Toast.LENGTH_SHORT).show();
	}
		    else if(!(userpassword.getText().toString()).equals(pwd)){
		    	Toast.makeText(Main.this,"ԭ�������", Toast.LENGTH_SHORT).show();
		    }
			break;
		case R.id.yanzhengma:
			 if(!YanZhengMa.equals(yanzhengma.getText().toString())){
		    	 Toast.makeText(Main.this,"��֤�����", Toast.LENGTH_SHORT).show();
		     }
			break;
		case R.id.userpwd:
			if(userpwd.getText().toString().trim().length()<6){
				Toast.makeText(this, "���볤�Ȳ���С��6", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.userphone:
			if(userphone.getText().toString().trim().length()==0 || userphone.getText().toString().equals(null)){
				Toast.makeText(this, "�绰���벻��Ϊ�գ�", Toast.LENGTH_LONG).show();
			}
			break;
		}

	}

	// �û�ע��
	public void register() {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date(System.currentTimeMillis());
		final String Time=format.format(date);
		view1 = inflater1.inflate(R.layout.register, null);
		registerinit();
		AlertDialog.Builder alert = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.Alert));
		alert.setTitle("�û�����");
		alert.setView(view1);

		alert.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}

		});
		alert.setPositiveButton("ע��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
		   usercount=usersqlite.UserCount(db, username.getText().toString(), userpwd.getText().toString());
		   if(usercount==1){
			   Toast.makeText(Main.this, "�û��Ѵ���", Toast.LENGTH_LONG).show();
		   }else if(usercount==0){
			   String userphoneText = userphone.getText().toString();
				if (!userphoneText.equals("")) {
					try {
						bitMap = EncodingHandler.createQRCode("�û���Ϣ"+userphoneText, 350);//������ά��
					} catch (WriterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(userpwd.getText().toString().trim().length()<6){
						Toast.makeText(Main.this, "���볤�Ȳ���С��6", Toast.LENGTH_LONG).show();
					}
					else if(userphone.getText().toString().trim().length()==0 || userphone.getText().toString().equals(null)){
						Toast.makeText(Main.this, "�绰���벻��Ϊ�գ�", Toast.LENGTH_LONG).show();
					}else {
					 usersqlite.addUser(db, username.getText().toString(), userpwd.getText().toString(), userphone.getText().toString(), String.valueOf(status), salary.getText().toString(), "",img(R.drawable.user),0,code(bitMap),Time);
					   Toast.makeText(Main.this, "ע��ɹ�", Toast.LENGTH_LONG).show();
				}}else {
					Toast.makeText(Main.this, "��Ϣ��д����ȷ!", Toast.LENGTH_SHORT).show();
				}
			  
		   }
			}

		});
		alert.show();
	}
//�û���¼
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
				Toast.makeText(Main.this, "�û���/�������", Toast.LENGTH_LONG).show();
			}
	}
	// �һػ��޸�����
	public void findORupdatePwd() {
		view2 = inflater1.inflate(R.layout.findpassword, null);
		findORupdateinit();
		AlertDialog.Builder alert = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.Alert));
		alert.setTitle("�û�����");

		alert.setView(view2);

		alert.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}

		});
		alert.setPositiveButton("�޸�", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
				String username=usernames.getText().toString();
				String pwd=newpwd.getText().toString();
			    usersqlite.updatePwd(db,username,pwd);
			    Toast.makeText(Main.this,"�����޸ĳɹ��������ڵ�������"+newpwd.getText().toString(), Toast.LENGTH_SHORT).show();
			}
			}

		);
		alert.setNeutralButton("�һ�", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String pwd = null;
				Cursor cursor=usersqlite.queryUser(db, usernames.getText().toString());
				if(cursor.moveToNext()){
					 pwd=cursor.getString(cursor.getColumnIndex("passWord"));
					
				}
				 cursor.close();
				AddNotify(usernames.getText()
						.toString() + "�û�" + ",����������" + pwd,2);
			}
		});
		alert.show();
	}
	// ��ѡ��ť��������
	public void click(View v) {

		int checkedRadioButtonId = radiogroup.getCheckedRadioButtonId();
		int radio = 0; // Ĭ��ֵΪ0
		switch (checkedRadioButtonId) {
		case R.id.radioButton1: // ѡ�е�����ְ��ť
			radio = 1;
			status=0;
			layout3.setVisibility(View.VISIBLE);
			break;

		case R.id.radioButton2: // ѡ�еĲ���ְ��ť
			radio = 2;
			status=1;
			layout3.setVisibility(View.GONE);
			break;
		}
	}

	// ע������ʼ��
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

	// �һ���������ʼ��
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
	// ��֤�뵹��ʱ
		public void daojishi() {
			if (number == 0) {
				getyanzhengma.setText("��ȡ��֤��");
			} else {
				count--;
				if (count > 0) {
					getyanzhengma.setClickable(false);
					getyanzhengma.setText(count + "������»�ȡ");

				} else {
					getyanzhengma.setClickable(true);
					getyanzhengma.setText("��ȡ��֤��");

				}
			}
		}
		public static String returnName(){
			return Name;
		}
		public static String returnPsd(){
			return Psd;
		}
		 //��ȡ��ͼ
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
		 //����ʼ����ͼƬת���ɶ����ƴ洢
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
			 builder.setTicker("����һ������Ϣ");
			 builder.setContentTitle("��Ϣ��");
			 builder.setContentText(msg);
			 builder.setWhen(System.currentTimeMillis()); //����ʱ��
			 builder.setDefaults(Notification.DEFAULT_ALL);
			 builder.setAutoCancel(true);
			 long[] vibrates =new long[]{800, 1000, 800, 1000, 800, 1000};// �����ֵ��ʾ�ֻ���ֹ��ʱ������һ��ֵ��ʾ�ֻ��𶯵�ʱ����
			 Notification notification = builder.getNotification();
			 notification.vibrate = vibrates;
			 notification.ledARGB= Color.GREEN;
			 notification.ledOnMS=1000;
			 notification.ledOffMS=1000;
			 notification.flags=Notification.FLAG_SHOW_LIGHTS;
			 notificationManager.notify(number, notification);
		 }

}
