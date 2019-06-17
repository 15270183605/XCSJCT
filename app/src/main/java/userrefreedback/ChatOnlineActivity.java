package userrefreedback;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import loginOrRegister.Main;
import sqlite.UserRefreedbackSQLite;
import sqlite.UserSQLite;
import Adapters.UserefreedBackChatAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.FamilyMember;
import entity.UserRefreedEntity;

public class ChatOnlineActivity extends Activity implements OnClickListener {
   private ListView UserRefreedListView;
   private ImageView ProblemPicture;
   private EditText Content;
   private Button sendContent;
   private Html.ImageGetter mImageGetter;
   private UserRefreedbackSQLite userrefreedbacksqlite;
   private UserSQLite usersqlite;
   private SQLiteDatabase db,db1;
   private List<UserRefreedEntity> userrefreedDatas; 
   private UserefreedBackChatAdapter userrefreedadapter;
   private int Type=1,Way=1;
   private FamilyMember member;
   private Uri uri;
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.userrefreedonlinechat);
	init();
}
public void init(){
	userrefreedbacksqlite=new UserRefreedbackSQLite(this, "UserRefreed.db", null, 1);
	db=userrefreedbacksqlite.getReadableDatabase();
	usersqlite = new UserSQLite(this, "FamilyFinance.db", null, 1);
	db1= usersqlite.getReadableDatabase();
	member = usersqlite.queryMember(db1, Main.returnName());
	userrefreedDatas=new ArrayList<UserRefreedEntity>();
	userrefreedDatas=userrefreedbacksqlite.queryMessage(db);
	UserRefreedListView=(ListView)findViewById(R.id.UserRefreedListView);
	ProblemPicture=(ImageView)findViewById(R.id.ProblemPicture);
	Content=(EditText)findViewById(R.id.Content);
	sendContent=(Button)findViewById(R.id.sendContent);
	sendContent.setOnClickListener(this);
	ProblemPicture.setOnClickListener(this);
	if(userrefreedDatas.size()==0){
		String content="��ӭ�����û��������棬��������ʲô���⣬���ڴ˸����ǹ�ͨ�����ǽ�����������!";
		
	UserRefreedEntity entity=new UserRefreedEntity();
	entity.setTime(System.currentTimeMillis());
	entity.setContent(content);
	entity.setHeadImage(member.getHeadImage());
	entity.setType(0);
	entity.setNickName("����Ա");
	entity.setUserName("δ֪");
	userrefreedDatas.add(entity);
	userrefreedbacksqlite.AddUserRefreedContentByEntity(db,entity);
	}
	//��ʼ��mImageGetterע����ʼ��Ҫ����������ʼ��֮ǰ������mImageGetterΪ�գ��ò���ͼƬ���������ˢ��ֻ��ˢ������;
			mImageGetter=new Html.ImageGetter() {
				
				@Override
				public Drawable getDrawable(String source) {
					Drawable drawable=null;
					if(source==null){
						drawable=getResources().getDrawable(R.drawable.feedback);
						drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
						
					}else{
						//ͨ������õ�R.drawable��
						Class clazz=R.drawable.class;
						try{
							//ͨ������õ�face[position]��source���ƣ��������Ϊsource���ԣ�
							//������face15���String���͵�"face15"��Ȼ��õ�����Ϊface15�����ԣ�face15��drawable��Ϊ��̬int���ԣ�
							Field field=clazz.getDeclaredField(source);
							//�õ����Եľ�̬intֵ��
							int sourceId=field.getInt(clazz);
							//getDrawable(sourceId)���е�ֵΪ��̬Int���ͣ�
							drawable=getResources().getDrawable(sourceId);
							drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
						}catch(NoSuchFieldException e){
							e.printStackTrace();
						}catch(IllegalAccessException e){
							e.printStackTrace();
						}
					}
					return drawable;
				}
			};
		userrefreedadapter=new UserefreedBackChatAdapter(this, userrefreedDatas, mImageGetter);
		UserRefreedListView.setAdapter(userrefreedadapter);
}
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ProblemPicture:
			Way=2;
			Intent intent3 = new Intent(Intent.ACTION_PICK, null);
			intent3.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			this.startActivityForResult(intent3, 0x12);
			break;
		case R.id.sendContent:
			Way=1;
			AddContent();
			break;
		}

	}
	 //����ʼ����ͼƬת���ɶ����ƴ洢
	 public byte[] img(int id)
	{
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
	     Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(id)).getBitmap();
	     bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
      return baos.toByteArray();
	}
public void AddContent(){
	if(Type==1){
		UserRefreedEntity entity=new UserRefreedEntity();
		entity.setUserName(member.getUserName());
		entity.setNickName(member.getNickName());
		entity.setType(Type);
		entity.setTime(System.currentTimeMillis());
		entity.setHeadImage(member.getHeadImage());
	if(Content.getText().toString().trim().length()!=0 && Way==1){
		entity.setContent(Content.getText().toString());
		Content.setText("");
		userrefreedDatas.add(entity);
		userrefreedadapter.notifyDataSetChanged();
		UserRefreedListView.setSelection(userrefreedDatas.size()-1);
		userrefreedbacksqlite.AddUserRefreedContentByEntity(db,entity);}
		else if(Way==2 && uri.toString().trim().length()!=0){
			Spanned spanned=Html.fromHtml("<img src='"+uri+"'>",mImageGetter,null);
			//��Editable(Spanned������)ת��ΪString���ͽ��д���"toHtml(Spanned text)"
			entity.setContent(filterHtml(Html.toHtml(spanned)));
			userrefreedDatas.add(entity);
			userrefreedadapter.notifyDataSetChanged();
			UserRefreedListView.setSelection(userrefreedDatas.size()-1);
			userrefreedbacksqlite.AddUserRefreedContentByEntity(db,entity);
		}
		
		
		
	}
	
}
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	super.onActivityResult(requestCode, resultCode, data);
	// ��ͼ���з�������;
	if (requestCode == 0x12 && data != null) {             
			 // �õ�ͼƬ��ȫ·��                
		uri = data.getData();               
	     AddContent();
    

	} else if (data == null) {
		Toast.makeText(this, "δ��⵽�豸�����ݻ�ȡʧ��", 1000).show();
	}

}
//���˵�����Ҫ�ĳɷ֣�
public String filterHtml(String str){
	str=str.replaceAll("<(?!br|img)[^>]+>","").trim();
	return str;
}
}
