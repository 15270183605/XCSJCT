package usercenter;

import java.io.ByteArrayOutputStream;

import sqlite.UserShouZhiSQLite;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

public class UserAddShouZhiCode extends Activity implements OnClickListener {
	private EditText UserNameEdit, UserPhoneEdit;
	private ImageView BaoShouCodeImage, BaoFuCodeImage, WeiShouCodeImage,
			WeiFuCodeImage;
	private TextView Save, Cancel;
	private String UserName, Phone, Password;
	private byte[] baoshou, baofu, weishou, weifu;
	private UserShouZhiSQLite usershouzhisqlite;
	private SQLiteDatabase db;
	private int number = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.useropenzhifu);
		init();
	}

	public void init() {
		Intent intent=getIntent();
		UserName=intent.getStringExtra("username");
		Phone=intent.getStringExtra("phone");
		Password=intent.getStringExtra("pwd");
		UserNameEdit = (EditText) findViewById(R.id.UserNameEdit);
		UserPhoneEdit = (EditText) findViewById(R.id.UserPhoneEdit);
		BaoShouCodeImage = (ImageView) findViewById(R.id.BaoShouCodeImage);
		BaoFuCodeImage = (ImageView) findViewById(R.id.BaoFuCodeImage);
		WeiShouCodeImage = (ImageView) findViewById(R.id.WeiShouCodeImage);
		WeiFuCodeImage = (ImageView) findViewById(R.id.WeiFuCodeImage);
		Save = (TextView) findViewById(R.id.Save);
		Cancel = (TextView) findViewById(R.id.Cancel);
		Save.setOnClickListener(this);
		Cancel.setOnClickListener(this);
		BaoShouCodeImage.setOnClickListener(this);
		BaoFuCodeImage.setOnClickListener(this);
		WeiShouCodeImage.setOnClickListener(this);
		WeiFuCodeImage.setOnClickListener(this);
		UserNameEdit.setText(UserName);
		UserPhoneEdit.setText(Phone);
		usershouzhisqlite = new UserShouZhiSQLite(this, "ShouZhiCode.db", null,
				1);
		db = usershouzhisqlite.getReadableDatabase();
		
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.Save:
			baoshou=detailImage(BaoShouCodeImage);
			weishou=detailImage(WeiShouCodeImage);
			baofu=detailImage(BaoFuCodeImage);
			weifu=detailImage(WeiFuCodeImage);
			Toast.makeText(this, UserName+Password, 1000).show();

			usershouzhisqlite.AddUserShouZhi(db, UserName, Password, Phone,
					baoshou, weishou, baofu, weifu);
			Cursor cursor=usershouzhisqlite.queryUserShouFuCode(db, UserName, Password);
			if(cursor.getCount()==0){
				Toast.makeText(this, "插入失败", 1000).show();

			}else{
				Toast.makeText(this, "数据保存成功!", 1000).show();

			}
			  cursor.close();
					break;
		case R.id.Cancel:
			Intent intent=new Intent(this,UserCenter.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.BaoShouCodeImage:
			number = 1;
			getImage();
			break;
		case R.id.WeiShouCodeImage:
			number = 2;
			getImage();
			break;
		case R.id.BaoFuCodeImage:
			number = 3;
			getImage();
			break;
		case R.id.WeiFuCodeImage:
			number = 4;
			getImage();
			break;
		}

	}

	public void getImage() {
		Intent intent3 = new Intent(Intent.ACTION_PICK, null);
		intent3.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		this.startActivityForResult(intent3, 0x12);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 从图库中反馈数据;
		if (requestCode ==0x12 && data != null) {
			 if (data != null) {               
				 // 得到图片的全路径                
				 Uri uri = data.getData();               
				           
			if (number == 1) {
				BaoShouCodeImage.setImageURI(uri);  
			}
			if (number == 2) {
				WeiShouCodeImage.setImageURI(uri);
			}
			if (number == 3) {
				BaoFuCodeImage.setImageURI(uri);
			}

			if (number == 4) {
				WeiFuCodeImage.setImageURI(uri);
			}
	    }

		} else if (data == null) {
			Toast.makeText(this, "未检测到设备，数据获取失败", 1000).show();
		}

	}
	//解析图片转化成二进制  
	public byte[] detailImage(ImageView headImage1){	
		headImage1.setDrawingCacheEnabled(true);
		Bitmap bitmap = Bitmap.createBitmap(headImage1.getDrawingCache());
		headImage1.setDrawingCacheEnabled(false);
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	    byte[] HeadImage= baos.toByteArray();
	    bitmap.recycle();
	    return HeadImage;
	}
}
