package copyshouye;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import loginOrRegister.LoactionAndTianQi;
import sqlite.IncomeSQLite;
import sqlite.PaySQLite;
import tool.ScrollLayout;
import usercenter.UserCenter;
import work.basic;
import Dialog.SampleLoadDialog;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class ActivityViewGroup extends ActivityGroup implements OnClickListener{
	private RelativeLayout LeftMenuLayout5 ;
	private int Month,Year;
	private static String LoactionText, City,LoactionTextTotal;
	private SampleLoadDialog sampledialog;
	private ScrollLayout slidingMenu;
	private LinearLayout LeftMenuLayout1, LeftMenuLayout2, LeftMenuLayout3,
			LeftMenuLayout4,LeftMenuLayout6,ActivityContainer;
	private ImageView RunImage,MyJinbiImage,TianPing;
	AnimationDrawable mAnimationDrawable;
	private static int number = 0;
	private boolean flag=false;
	private Animation translate;
	private TextView NowLoaction,MyMoney;
	private IncomeSQLite incomesqlite;
	private PaySQLite paysqlite;
	 private SQLiteDatabase db,db1;
		private AlertDialog dialog;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activityviewgroup);
		init();
	}
	// 初始化
	public void init() {
		ActivityContainer = (LinearLayout) findViewById(R.id.ActivityContainer);
		slidingMenu = (ScrollLayout) findViewById(R.id.sliding_menu);
		AddDay();
		LeftMenu();
		slidingMenu.closeMenu();
		incomesqlite = new IncomeSQLite(this,"income.db",null,1);
		db=incomesqlite.getReadableDatabase();
		paysqlite = new PaySQLite(this,"pay.db",null,1);
		db1=paysqlite.getReadableDatabase();
		double Count =incomesqlite.TotalCount(db, String.valueOf(Year))-paysqlite.TotalCount(db1, String.valueOf(Year));
		MyMoney.setText(String.valueOf(Count));
		if(Count<0){
			TianPing.setImageResource(R.drawable.tianping2);
		}
		if(Count>0){
			TianPing.setImageResource(R.drawable.tianping3);
		}else{
			TianPing.setImageResource(R.drawable.tianping1);
		}
		Intent intent = new Intent(ActivityViewGroup.this, basic.class);
		ActivityContainer.removeAllViews();
		View v = getLocalActivityManager().startActivity("basic",
				intent).getDecorView();
		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		ActivityContainer.addView(v);
	}
	public void AddDay() {
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		Year = cal.get(Calendar.YEAR);
		Month = cal.get(Calendar.MONTH) + 1;

	}
	public static int returnNumber() {
		return number;
	}
//加载页面获取信息
	public void getThingsDialog() {
		sampledialog = new SampleLoadDialog(this);
		sampledialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		sampledialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		sampledialog.show();
		sampledialog.setCancelable(false);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.LeftMenuLayout1:
			if (number == 0) {
				number = 1;
			} else if (number == 1) {
				number = 0;
			}
			slidingMenu.closeMenu();
			Intent intent = new Intent(this, ActivityViewGroup.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.LeftMenuLayout2:
			Intent intent3 = new Intent(Intent.ACTION_PICK, null);
			intent3.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivity(intent3);
			break;
		case R.id.LeftMenuLayout3:
			Intent intentToPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			String PhotoPath = Environment.getExternalStorageDirectory()
					+ File.separator + "photo.jpg";
			Uri imageUri = Uri.fromFile(new File(PhotoPath));
			intentToPhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivity(intentToPhoto);
			break;
		case R.id.LeftMenuLayout4:
			/*Toast.makeText(this, ActivityViewGroup.returnLoaction(), 1000)
					.show();*/
			break;
		case R.id.LeftMenuLayout5:
			//Toast.makeText(this, "为啥会变", 1000).show();
			MyMoney.setText(String.valueOf(incomesqlite.TotalCount(db, String.valueOf(Year))-paysqlite.TotalCount(db1, String.valueOf(Year))));
			break;
		case R.id.LeftMenuLayout6:
			Intent inten=new Intent(this,UserCenter.class);
			startActivity(inten);
			break;
		case R.id.RunImage:
			if(flag==true){
				//startAnimation();
				mAnimationDrawable.start();
				 RunImage.startAnimation(translate);
				 flag=false;
				 
			}else{
				mAnimationDrawable.stop();
				RunImage.clearAnimation();
				flag=true;			}
		break;
		
		}

	}

	public void LeftMenu() {
		LeftMenuLayout1 = (LinearLayout) findViewById(R.id.LeftMenuLayout1);
		LeftMenuLayout2 = (LinearLayout) findViewById(R.id.LeftMenuLayout2);
		LeftMenuLayout3 = (LinearLayout) findViewById(R.id.LeftMenuLayout3);
		LeftMenuLayout4 = (LinearLayout) findViewById(R.id.LeftMenuLayout4);
		LeftMenuLayout5 = (RelativeLayout) findViewById(R.id.LeftMenuLayout5);
		LeftMenuLayout6= (LinearLayout) findViewById(R.id.LeftMenuLayout6);
		RunImage = (ImageView) findViewById(R.id.RunImage);
		NowLoaction=(TextView)findViewById(R.id.NowLoaction);
		MyMoney=(TextView)findViewById(R.id.MyMoney);
		MyJinbiImage=(ImageView)findViewById(R.id.MyJinbiImage);
		TianPing=(ImageView)findViewById(R.id.TianPing);
		LeftMenuLayout1.setOnClickListener(this);
		LeftMenuLayout2.setOnClickListener(this);
		LeftMenuLayout3.setOnClickListener(this);
		LeftMenuLayout4.setOnClickListener(this);
		LeftMenuLayout5.setOnClickListener(this);
		LeftMenuLayout6.setOnClickListener(this);
		MyJinbiImage.setOnClickListener(this);
		RunImage.setOnClickListener(this);
		RunImage.setImageResource(R.drawable.animation);
		RunImage.setVisibility(View.GONE);
		mAnimationDrawable = (AnimationDrawable) RunImage.getDrawable();
		mAnimationDrawable.setOneShot(false);
		startAnimation();
		NowLoaction.setText(LoactionAndTianQi.returnLoaction());
		
	}
	    private void startAnimation() {

	        mAnimationDrawable.start();
	         translate = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -0.2f,
	                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_SELF, 0,
	                Animation.RELATIVE_TO_SELF, 0);
           
	        translate.setDuration(5000);
	        //translate.setRepeatCount(Animation.INFINITE);
	        translate.setRepeatCount(Animation.INFINITE);
	        RunImage.startAnimation(translate);
	        RunImage.setVisibility(View.VISIBLE);
	       
	    }
	  /*  public boolean onKeyDown(int keyCode, KeyEvent event) {   
			 switch (keyCode) {    	 
			 case KeyEvent.KEYCODE_BACK:         
				 AlertDialog.Builder builder = new AlertDialog.Builder(
							new ContextThemeWrapper(this, R.style.Alert));
					View view = LayoutInflater.from(this).inflate(R.layout.exitjiezhangcheck, null);
					TextView ExitCheck=(TextView)view.findViewById(R.id.ExitCheck);
					TextView ExitCancel=(TextView)view.findViewById(R.id.ExitCancel);
					ExitCheck.setOnClickListener(this);
					ExitCancel.setOnClickListener(this);
					builder.setView(view);
				     dialog= builder.show();        
				 break;    	}    	
			 return false;	    }*/
}
