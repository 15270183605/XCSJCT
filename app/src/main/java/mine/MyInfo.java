package mine;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import sqlite.UserSQLite;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.FamilyMember;
public class MyInfo extends Activity implements OnClickListener{
    private EditText EditNickName,EditTele,EditSalary;
    private RadioGroup radiogroup;
    private RadioButton radiobutton,radiobutton1;
    private ImageView headImage,bank,MyCode;
    private TextView editName,reset,save,HeadImageText,HeadImageText1,HeadImageText2;
    private UserSQLite usersqlite;
    private SQLiteDatabase db;
    private FamilyMember member;
    private int status;
    private int imaged;
    private String name;
    private Dialog dialog;
    private Uri imageUri;//照片所在路径
    private byte[] HeadImage;
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.myinfo);
    	init();
    }
    public void init(){
    	Intent intent=getIntent();
    	 name=intent.getStringExtra("username");
    	EditNickName=(EditText)findViewById(R.id.EditNickName);
    	MyCode=(ImageView)findViewById(R.id.MyCode);
    	EditTele=(EditText)findViewById(R.id.EditTele);
    	EditSalary=(EditText)findViewById(R.id.EditSalary);
    	editName=(TextView)findViewById(R.id.EditName);
    	bank=(ImageView)findViewById(R.id.bank);
    	radiobutton=(RadioButton)findViewById(R.id.radioInfo1);
    	radiobutton1=(RadioButton)findViewById(R.id.radioInfo2);
    	headImage=(ImageView)findViewById(R.id.headImage);
    	reset=(TextView)findViewById(R.id.Reset);
    	save=(TextView)findViewById(R.id.Save);
    	reset.setOnClickListener(this);
    	save.setOnClickListener(this);
    	headImage.setOnClickListener(this);
    	radiobutton1.setOnClickListener(this);
    	radiobutton.setOnClickListener(this);
    	bank.setOnClickListener(this);
    	MyCode.setOnClickListener(this);
    	usersqlite=new UserSQLite(this, "FamilyFinance.db", null, 1);
		db=usersqlite.getReadableDatabase();
		member=new FamilyMember();
		member=usersqlite.queryMember(db, name);
		editName.setText(member.getUserName());
		EditTele.setText(member.getPhoneNumber());
		EditSalary.setText(String.valueOf(member.getSalary()));
		EditNickName.setText(member.getNickName());
		if(member.getHeadImage().length!=0){
		headImage.setImageBitmap(getBmp(member.getHeadImage()));}
		if(member.getMyCode().length!=0){
			MyCode.setImageBitmap(getBmp(member.getMyCode()));}
		if(member.getStatus()==0){
			radiobutton.isChecked();
		}
		else if(member.getStatus()==1){
			radiobutton1.isChecked();
		}
    }
	
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.Reset:
			EditNickName.setText("");
			EditTele.setText("");
			EditSalary.setText("");
			break;
		case R.id.Save:
			detailImage(headImage);
			String tele=EditTele.getText().toString();
			String nickname=EditNickName.getText().toString();
			double salary=Double.valueOf(EditSalary.getText().toString());
			usersqlite.updateMember(db, nickname,tele, salary, status, HeadImage, name);
			Toast.makeText(this, "修改成功", 1000).show();
			break;
		case R.id.headImage:
			AddDialogHeadImage();
			break;
		case R.id.radioInfo1:
			status=0;
			break;
		case R.id.radioInfo2:
			status=1;
			break;
		case R.id.bank:
			/*Intent intent2=new Intent(MyInfo.this,ActivityViewGroup.class);
			startActivity(intent2);*/
			finish();
			break;
		case R.id.HeadImageText:
			Intent intent1 = new Intent(MyInfo.this, PictureList.class);
			startActivityForResult(intent1, 0x11);
			dialog.dismiss();
			break;
		case R.id.HeadImageText1:
			//调出图库
			Intent intent3=new Intent(Intent.ACTION_PICK,null);
			intent3.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intent3, 0x12);
			dialog.dismiss();
			break;
		case R.id.HeadImageText2:
		     TakePhoto();
			dialog.dismiss();
			break;
		}
		
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//从模板中反馈数据
		if (requestCode == 0x11 && resultCode == 0x11) {
			Bundle bundle = data.getExtras();
			imaged= bundle.getInt("imaged");
			headImage.setImageResource(imaged);
		}
		//从图库中反馈数据;
		if(requestCode==0x12 && data!=null){
			Uri selectImage=data.getData();//获取图库中所有数据；
			headImage.setImageURI(selectImage);
			Toast.makeText(this, selectImage.toString(), 1000).show();
		}
		if(requestCode == 0x13){
			 try {
                 //将拍摄的照片显示出来
                 Bitmap bitmap= BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                 headImage.setImageBitmap(bitmap);
             } catch (FileNotFoundException e) {

                 e.printStackTrace();

             }
		}

	}
	//从底部弹出对话框
	public void AddDialogHeadImage(){
		View view=LayoutInflater.from(this).inflate(R.layout.bottomdialogitem, null);
		HeadImageText=(TextView)view.findViewById(R.id.HeadImageText);
		HeadImageText1=(TextView)view.findViewById(R.id.HeadImageText1);
		HeadImageText2=(TextView)view.findViewById(R.id.HeadImageText2);
		HeadImageText.setOnClickListener(this);
		HeadImageText1.setOnClickListener(this);
		HeadImageText2.setOnClickListener(this);
		dialog=new Dialog(this,R.style.DiaologAlert);
		dialog.setContentView(view);
		Window window=dialog.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.DialogStyle);
		WindowManager.LayoutParams lp=window.getAttributes();
		lp.height=350;
		lp.width=this.getWindowManager().getDefaultDisplay().getWidth();
		window.setAttributes(lp);
		dialog.show();
	}
	public void TakePhoto(){
		   SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		   Date date=new Date(System.currentTimeMillis());
		   String filename=format.format(date);
		   Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
		   File outputImage=new File(getExternalCacheDir(),filename+".jpg");
		    imageUri=Uri.fromFile(outputImage);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			startActivityForResult(intent,0x13);
			Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);   
			Uri contentUri = Uri.fromFile(outputImage);    
			mediaScanIntent.setData(contentUri);   
			this.sendBroadcast(mediaScanIntent);
				
	
}
	//解析图片转化成二进制  
public void detailImage(ImageView headImage1){	
	headImage1.setDrawingCacheEnabled(true);
	Bitmap bitmap = Bitmap.createBitmap(headImage1.getDrawingCache());
	headImage1.setDrawingCacheEnabled(false);
	 ByteArrayOutputStream baos = new ByteArrayOutputStream();
	 bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
     HeadImage= baos.toByteArray();
     bitmap.recycle();
     bitmap=null;
     System.gc();
}
//将二进制图片转换成bitmap
public Bitmap getBmp(byte[] in) 
{
    Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
    return bmpout;
}
}