package usercenter;

import loginOrRegister.Main;
import sqlite.UserShouZhiSQLite;
import Dialog.UserJiaoYanDialog;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.jiacaitong.R;

public class UserShouZhi extends Activity implements OnClickListener{
     private Button OpenUserPay;
     private LinearLayout UserPayLayout;
     private RelativeLayout UserPayLayout1;
     private TextView UserName;
     private RadioButton BaoShouCode,WeiXinShouCode;
     private ViewFlipper  UserShouCodeFlipper;
     private UserShouZhiSQLite usershouzhisqlite;
     private SQLiteDatabase db;
     private byte[] baoshou,weishou,baofu,weifu;
     private int number=1;
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.userzhifu);
    	init();
    }
    public void init(){
    	OpenUserPay=(Button)findViewById(R.id.OpenUserPay);
    	UserPayLayout=(LinearLayout)findViewById(R.id.UserPayLayout);
    	UserPayLayout1=(RelativeLayout)findViewById(R.id.UserPayLayout1);
    	UserName=(TextView)findViewById(R.id.UserName);
    	BaoShouCode=(RadioButton)findViewById(R.id.BaoShouCode);
    	WeiXinShouCode=(RadioButton)findViewById(R.id.WeiXinShouCode);
    	UserShouCodeFlipper=(ViewFlipper)findViewById(R.id.UserShouCodeFlipper);
    	OpenUserPay.setOnClickListener(this);
    	BaoShouCode.setOnClickListener(this);
    	WeiXinShouCode.setOnClickListener(this);
    	usershouzhisqlite=new UserShouZhiSQLite(this, "ShouZhiCode.db", null, 1);
    	db=usershouzhisqlite.getReadableDatabase();
    	Cursor cursor=usershouzhisqlite.queryUserShouFuCode(db, Main.returnName(), Main.returnPsd());
      if(cursor.getCount()==0){
    	  UserPayLayout.setVisibility(View.VISIBLE);
    	  UserPayLayout1.setVisibility(View.GONE);
      }
      else{
    	  UserPayLayout.setVisibility(View.GONE);
    	  UserPayLayout1.setVisibility(View.VISIBLE);
    	  cursor.moveToFirst();
    	  UserName.setText(cursor.getString(cursor.getColumnIndex("UserName")));
    	  baoshou=cursor.getBlob(cursor.getColumnIndex("BaoShouImage"));
    	  weishou=cursor.getBlob(cursor.getColumnIndex("WeiShouImage"));
    	  BaoShouCode.setChecked(true);
    	  FlipperAddData();
      }
      cursor.close();
    }
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.OpenUserPay:
			UserJiaoYanDialog dialog=new UserJiaoYanDialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();
			dialog.setCancelable(false);
			break;
		case R.id.BaoShouCode:
			number=1;
			FlipperAddData();
			UserShouCodeFlipper.showPrevious();
			break;
		case R.id.WeiXinShouCode:
			number=2;
			FlipperAddData();
			UserShouCodeFlipper.showNext();
			break;
		}
		
	}
	public void FlipperAddData(){
		//UserShouCodeFlipper.removeAllViews();
		View view=LayoutInflater.from(this).inflate(R.layout.usershouzhiflipperitem, null);
		ImageView shouzhiimage=(ImageView)view.findViewById(R.id.ShouZhiImage);
		if(number==1){
		shouzhiimage.setImageBitmap(getBmp(baoshou));}
		if(number==2){
			shouzhiimage.setImageBitmap(getBmp(weishou));
		}
		UserShouCodeFlipper.addView(view);
		UserShouCodeFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
		UserShouCodeFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
	}
	//将二进制的图片转换成bitmap
	public Bitmap getBmp(byte[] in) 
	{
	    Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
	    return bmpout;
	  
	}
	/*public Drawable chage_to_drawable()
	 {
	     //因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
	     bp=getBmp(baoshou); 
	   BitmapDrawable bd= new BitmapDrawable(getResources(), bp); 
	    return bd;
	 }*/
	
}
