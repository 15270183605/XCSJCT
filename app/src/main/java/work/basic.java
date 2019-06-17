package work;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import loginOrRegister.Main;
import saoma.activity.CaptureActivity;
import sqlite.UserSQLite;
import tool.PoupWindow;
import work.tool.ParticleView;
import Dialog.MyDialogText;
import add.Call;
import add.ExportData;
import add.JiShiBen;
import android.animation.Animator;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import copyshouye.ActivityViewGroup;
import copyshouye.CopyShouYeActivity;
import copyshouye.ShowAdDataActivity;
import copyshouye.copyshouye;
import entity.FamilyMember;

public class basic extends ActivityGroup implements OnClickListener {
	private TextView TextShouYe, TextLife, TextMine, TextJinRong, TextAdd,NetConnect;
	private LinearLayout container;
	private ImageView shouye, life, mine, JinRong, Add;
	private int mTimeNum;
	private BufferedReader buffer;
	private InputStream inputStream;
	String str = "";
	private MyDialogText dialog;
	private UserSQLite usersqlite;
	private SQLiteDatabase db;
	private static List<String> FestivalText = new ArrayList<String>();
	private static List<String> FestivalHttp = new ArrayList<String>();
	private ParticleView bottomImageAni;
	private PopupWindow popupwindow;
	private View customView;
   public static boolean netState=false;
   private Timer timer;
   private TimerTask timetask;
   private Handler handler ;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.basic);
		bottomImageAni = new ParticleView(this, 2000);
		usersqlite = new UserSQLite(this, "FamilyFinance.db", null, 1);
		db = usersqlite.getReadableDatabase();
		readFestivalTextViewFile();
		readFestivalHttpFile();
		TextShouYe = (TextView) findViewById(R.id.ShouYeText);
		TextLife = (TextView) findViewById(R.id.TextLife);
		TextMine = (TextView) findViewById(R.id.TextMine);
		TextJinRong = (TextView) findViewById(R.id.TextJinRong);
		TextAdd = (TextView) findViewById(R.id.TextAdd);
		container = (LinearLayout) findViewById(R.id.container);
		shouye = (ImageView) findViewById(R.id.ShouYe);
		life = (ImageView) findViewById(R.id.Life);
		mine = (ImageView) findViewById(R.id.Mine);
		JinRong = (ImageView) findViewById(R.id.JinRong);
		Add = (ImageView) findViewById(R.id.Add);
		SetBottomImageAnimation();
		if (ActivityViewGroup.returnNumber() == 0) {
			changeContainerView(shouye);
		} else if (ActivityViewGroup.returnNumber() == 1) {
			toActivity("four", CopyShouYeActivity.class);
			restTextStyle();
			TextShouYe.setTextColor(Color.RED);
		}

		if (usersqlite.QueryStatus(db, Main.returnName(), Main.returnPsd()) == 1) {
			Dialog();
		}
	shouye.setOnClickListener(this);
		life.setOnClickListener(this);
		mine.setOnClickListener(this);
		JinRong.setOnClickListener(this);
		Add.setOnClickListener(this);
		
		handler= new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 2:
					if(mTimeNum>=3){
						if(isNetworkConnected(basic.this)==false){
							initmPopupWindowView();
							popupwindow.showAtLocation(customView, Gravity.TOP,0, 120);
						}
						
						stopTask();
					}
					break;
				}
			}
		};
		
		startTimer();
	}

	public void changeContainerView(View v) {
		shouye.setSelected(true);
		life.setSelected(false);
		mine.setSelected(false);
		JinRong.setSelected(false);
		Add.setSelected(false);
		v.setSelected(true);
		toActivity("first", copyshouye.class);
		restTextStyle();
		TextShouYe.setTextColor(Color.RED);
		if (v == life) {
			toActivity("second", life.class);
			restTextStyle();
			TextLife.setTextColor(Color.RED);

		}
		if (v == mine) {

			toActivity("third", mine.class);
			restTextStyle();
			TextMine.setTextColor(Color.RED);
		}
		if (v == JinRong) {

			toActivity("jinrong", JinRong.class);
			restTextStyle();
			TextJinRong.setTextColor(Color.RED);
		}
		if (v == Add) {
			AddListener();
			restTextStyle();
			TextAdd.setTextColor(Color.RED);
		}
	}

	private void restTextStyle() {
		TextShouYe.setTextColor(Color.BLACK);
		TextLife.setTextColor(Color.BLACK);
		TextMine.setTextColor(Color.BLACK);
		TextJinRong.setTextColor(Color.BLACK);
		TextAdd.setTextColor(Color.BLACK);

	}

	private void toActivity(String lable, Class<?> cls) {
		Intent intent = new Intent(this, cls);
		// mViewPager.removeAllViews();
		container.removeAllViews();
		View v = getLocalActivityManager().startActivity(lable, intent)
				.getDecorView();
		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// mViewPager.addView(v);
		container.addView(v);
	}

	public void onClick(View view) {
		if (view.getId() == R.id.ShouYe) {
			// startAnmiation(shouye);
			if (ActivityViewGroup.returnNumber() == 0) {
				changeContainerView(shouye);
			} else {
				toActivity("four", CopyShouYeActivity.class);
				restTextStyle();
				TextShouYe.setTextColor(Color.RED);

			}
			bottomImageAni.AddViewAnimation(view);
		}
		if (view.getId() == R.id.Life) {
			// startAnmiation(life);
			changeContainerView(view);
			bottomImageAni.AddViewAnimation(view);
		}
		if (view.getId() == R.id.Mine) {
			// startAnmiation(mine);
			changeContainerView(view);
			bottomImageAni.AddViewAnimation(view);
		}
		if (view.getId() == R.id.JinRong) {
			// startAnmiation(JinRong);
			changeContainerView(view);
			bottomImageAni.AddViewAnimation(view);
		}
		if (view.getId() == R.id.Add) {
			changeContainerView(view);
		}
   if(view.getId()==R.id.NetConnect){
	   startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
   }
	}

	/*
	 * // 布局移动的动画效果旋转 public void startAnmiation(ImageView image) { AnimationSet
	 * animationSet = new AnimationSet(true); RotateAnimation rotateAnimation =
	 * new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
	 * Animation.RELATIVE_TO_SELF, 0.5f); rotateAnimation.setDuration(1000);
	 * animationSet.addAnimation(rotateAnimation);
	 * image.startAnimation(animationSet);
	 * 
	 * }
	 */
	// 弹出框设计
	public void Dialog() {
		readFile();
		dialog = new MyDialogText(basic.this, str);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.show();
		dialog.setCancelable(false);
	}

	// 读文件
	public void readFile() {
		try {
			inputStream = getAssets().open("helpText.txt");

			buffer = new BufferedReader(new InputStreamReader(inputStream));
			String temp = "";

			while ((temp = buffer.readLine()) != null) {

				temp += "\n";

				str = str + temp;
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}

	// 读取节日简介文件
	public void readFestivalTextViewFile() {
		int count = 0;
		try {
			InputStream inputStream = getAssets().open("festivaltext.txt");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					inputStream, "GBK"));
			String temp = "";
			while ((temp = buffer.readLine()) != null) {
				FestivalText.add(temp);
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	// 读取节日详情http文件
	public void readFestivalHttpFile() {
		int count = 0;
		try {
			InputStream inputStream = getAssets().open("festivalhttp.txt");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					inputStream, "GBK"));
			String temp = "";
			while ((temp = buffer.readLine()) != null) {
				FestivalHttp.add(temp);
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	public static List<String> returnFestivalTextView() {
		return FestivalText;
	}

	public static List<String> returnFestivalHttp() {
		return FestivalHttp;
	}

	public void AddListener() {
		PoupWindow mPopupWindow = new PoupWindow(basic.this,
				new PoupWindow.OnPopWindowClickListener() {
					@Override
					public void onPopWindowClickListener(View view) {
						switch (view.getId()) {
						case R.id.DaoChu:
							Intent intent = new Intent(basic.this,
									ExportData.class);
							startActivity(intent);
							break;
						case R.id.DaoRu:
							Toast.makeText(basic.this, "导入数据",
									Toast.LENGTH_SHORT).show();
							break;
						case R.id.JiShiBen:
							Intent jsbintent = new Intent(basic.this,
									JiShiBen.class);
							startActivity(jsbintent);
							break;
						case R.id.SaoMa:
							Intent openCameraIntent = new Intent(basic.this,
									CaptureActivity.class);
							startActivityForResult(openCameraIntent, 0x11);		
							break;
						case R.id.Call:
							Intent intent1 = new Intent(basic.this, Call.class);
							startActivity(intent1);
							
							break;
						}
					}
				});
		mPopupWindow.show();

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		copyshouye activity = (copyshouye) getLocalActivityManager()
				.getCurrentActivity();
		activity.handleActivityResult(requestCode, resultCode, data);
		if (requestCode == 0x11 && resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			SaoMaDialog(scanResult);
		}
		//super.onActivityResult(requestCode, resultCode, data);
	}
    //处理扫码反馈的内容
	public void SaoMaDialog(String str) {
		if (str.substring(0, 4).equals("用户信息")) {
			UserSQLite usersqlite = new UserSQLite(this, "FamilyFinance.db",
					null, 1);
			SQLiteDatabase db = usersqlite.getReadableDatabase();
			String tel = str.substring(4, str.trim().length());
			FamilyMember member = usersqlite.queryMemberByTel(db, tel);
			AlertDialog.Builder builder = new AlertDialog.Builder(
					new ContextThemeWrapper(this, R.style.Alert));
			View view = LayoutInflater.from(this).inflate(
					R.layout.saomycodexinxiitem, null);
			ImageView headiamge = (ImageView) view
					.findViewById(R.id.SaoMyHeadImage);
			TextView SaoMyNickName = (TextView) view
					.findViewById(R.id.SaoMyNickName);
			if (member.getHeadImage().length != 0) {
				headiamge.setImageBitmap(getBmp(member.getHeadImage()));
			}
			SaoMyNickName.setText(member.getNickName());
			builder.setView(view);
			final AlertDialog dialog = builder.show();
			dialog.setCancelable(true);

		} else if (str.indexOf("http") != -1) {
			Intent Adintent = new Intent(this, ShowAdDataActivity.class);
			Adintent.putExtra("adhttp", str);
			Adintent.putExtra("biaozhi", 1);
			startActivity(Adintent);

		} else {
			Toast.makeText(this, str, 1000).show();
		}

	}

	// 将二进制图片转换成bitmap
	public Bitmap getBmp(byte[] in) {
		Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
		return bmpout;
	}

	public void SetBottomImageAnimation() {

		bottomImageAni
				.setOnAnimationListener(new ParticleView.OnAnimationListener() {
					@Override
					public void onAnimationStart(View view, Animator animation) {
						view.setVisibility(View.INVISIBLE);
					}

					@Override
					public void onAnimationEnd(View view, Animator animation) {
						view.setVisibility(View.VISIBLE);
					}
				});
	}

	// 检查网络是否连接
	public boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				//return mNetworkInfo.isAvailable();
				netState=true;
			}
			else{
				netState=false;
			}
		}
		return netState;
	}
	// 网络未连接的弹出框
	public void initmPopupWindowView() { // // 获取自定义布局文件pop.xml的视图
		customView = LayoutInflater.from(getParent()).inflate(
				R.layout.netpopupwindow, null, false); // 创建PopupWindow实例,200,150分别是宽度和高度
		popupwindow = new PopupWindow(customView, 1000, 300);
		// 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
		// 自定义view添加触摸事件
		NetConnect=(TextView)customView.findViewById(R.id.NetConnect);
		NetConnect.setOnClickListener(this);
		ImageView PoupClose=(ImageView)customView.findViewById(R.id.PoupClose);
		PoupClose.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (popupwindow != null && popupwindow.isShowing()) {
					popupwindow.dismiss();
					popupwindow = null;
				}
				return false;
			}
		});
	}
	// 设置网络检查缓存时间
		public void startTimer() {
			timer = new Timer();
			timetask = new TimerTask() {
				public void run() {
					mTimeNum++;
					handler.sendEmptyMessage(2);
				}
			};
			timer.schedule(timetask,1* 1000, 1* 1000);
		}

		// 任务停止；
		public void stopTask() {
			try {
				timer.cancel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
