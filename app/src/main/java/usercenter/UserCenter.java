package usercenter;

import loginOrRegister.Main;
import mine.MyInfo;
import sqlite.UserSQLite;
import Adapters.SystemAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.jiacaitong.R;

public class UserCenter extends Activity {
	private ImageView UserCenterBack;
      private ListView UserCenterListView;
      private String usercenterData[]={"用户收款","个人信息","我的二维码"};
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.usercenter);
    	UserCenterListView=(ListView)findViewById(R.id.UserCenterListView);
    	UserCenterBack=(ImageView)findViewById(R.id.UserCenterBack);
    	SystemAdapter adapter = new SystemAdapter(this, usercenterData);
    	UserCenterListView.setAdapter(adapter); 
    	UserCenterBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		UserCenterListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
			if(position==0){
				Intent intent=new Intent(UserCenter.this,UserShouZhi.class);
				startActivity(intent);
			}
			if(position==1){	
				Intent intent = new Intent();
				intent.putExtra("username", Main.returnName());
				intent.setClass(UserCenter.this, MyInfo.class);
				startActivity(intent);
			}
			if(position==2){
				ErWeiMaDialog();
			}
			}
		});
    }
    public void ErWeiMaDialog(){
    	UserSQLite usersqlite=new UserSQLite(this,"FamilyFinance.db", null, 1);
    	SQLiteDatabase db=usersqlite.getReadableDatabase();
        byte[] mycode=usersqlite.queryUserCode(db, Main.returnName(), Main.returnPsd());
    	AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.Alert));
		View view = LayoutInflater.from(this).inflate(R.layout.usercodedialog, null);
		ImageView usercode=(ImageView)view.findViewById(R.id.UserCode);
		if(mycode.length!=0){
			usercode.setImageBitmap(getBmp(mycode));
		}
		builder.setView(view);
		final AlertDialog dialog = builder.show();
		dialog.setCancelable(true);
    }
  //将二进制图片转换成bitmap
    public Bitmap getBmp(byte[] in) 
    {
        Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
        return bmpout;
    }
}
