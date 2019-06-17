package jishiben;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sqlite.PictureSqlite;
import Adapters.JiShiBenPictureAdapter;
import Adapters.JiShiBenPictureSelectAdapter;
import Dialog.JSBPDialog;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.PictureEntity;

public class VideoActivity extends Activity implements OnClickListener {
	 private EditText JSBVContentEdit,JSBVEditText;
	 private ListView JSBVideoListView;
		private GridView JSBVEditGridView;
		private TextView JSBVSave,JSBVCancel;
		private ImageView JSBVadd;
	    private String pathImage;                //ѡ��ͼƬ·��  
	    private Bitmap bmp;                      //������ʱͼƬ  
	    private JSBPDialog dialog,alertdialog;
	    private String TuKupath,Camerpath;
	    private String time;
	    private PictureSqlite picturesqlite;
	    private SQLiteDatabase db;
	    private int biaozhi,Position;
	    private List<Bitmap> BitmapList;
	    private List<Uri> UriList;
	    private List<PictureEntity> PictureDatas;
	    private List<String> Paths;
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.jishibenvideo);
    	Calendar cal=Calendar.getInstance();
    	Date date=new Date(System.currentTimeMillis());
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	time=format.format(date);
    	cal.setTime(date);
    	picturesqlite=new PictureSqlite(this, "JSBPicture.db", null, 1);
    	db=picturesqlite.getReadableDatabase();
    	JSBVEditText=(EditText)findViewById(R.id.JSBVEditText);
    	JSBVideoListView=(ListView)findViewById(R.id.JSBVideoListView);
    	JSBVadd=(ImageView)findViewById(R.id.JSBVadd);
    	JSBVadd.setOnClickListener(this);
    	BitmapList=new ArrayList<Bitmap>();
    	Paths=new ArrayList<String>();
    	UriList=new ArrayList<Uri>();
    	PictureDatas=new ArrayList<PictureEntity>();
    	PictureDatas=picturesqlite.QueryData(db, time,"1");
    	JiShiBenPictureAdapter adapter=new JiShiBenPictureAdapter(PictureDatas, this,"Video");
    	JSBVideoListView.setAdapter(adapter);
    }
	public void onClick(View view) {
	   switch(view.getId()){
	   case R.id.JSBVSave:
		   if(UriList.size()!=0){
			   TuKupath=UriList.get(0).toString()+",";
		    for(int i=1;i<UriList.size();i++){
		    	TuKupath=TuKupath+UriList.get(i)+",";
		    	}
		    Toast.makeText(this, TuKupath, 1000).show();
		   PictureEntity picture=new PictureEntity(JSBVContentEdit.getText().toString(), TuKupath, time);
		   PictureDatas.add(picture);
		   picturesqlite.Add(db, JSBVContentEdit.getText().toString(), TuKupath, time,"1");
		  JiShiBenPictureAdapter Adapter=new JiShiBenPictureAdapter(PictureDatas, this,"Video");
	    	JSBVideoListView.setAdapter(Adapter);
		   dialog.dismiss();}
			break;
		case R.id.JSBVCancel:
			 Toast.makeText(this, TuKupath, 1000).show();
			dialog.dismiss();
			break;
		case R.id.SelectTuKu:
			if(biaozhi==1){
				Intent intent3 = new Intent(Intent.ACTION_PICK, null);
				intent3.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "video/*");
				startActivityForResult(intent3, 0x11); }
			if(biaozhi==2){
				Intent intent=new Intent(this,PlayVideo.class);
				intent.putExtra("video", Paths.get(Position));
				startActivity(intent);
			}
            alertdialog.dismiss();
			break;
		case R.id.SelectCamer:
			if(biaozhi==1){
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
				intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 1);
				//intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
				startActivityForResult(intent, 0x12);}
			if(biaozhi==2){
				BitmapList.remove(Position);
				Paths.remove(Position);
				UriList.remove(Position);
				JiShiBenPictureSelectAdapter adapter=new JiShiBenPictureSelectAdapter(this, BitmapList,"Video");
	        	JSBVEditGridView.setAdapter(adapter);
			}
			alertdialog.dismiss();
			break;
		case R.id.JSBVadd:
			AddDialog();
			break;
		case R.id.PictureSelectCancel:
			alertdialog.dismiss();
			break;
	   }

	}
	public void AddDialog(){
		BitmapList.clear();
		UriList.clear();
		Paths.clear();
		BitmapList.add(((BitmapDrawable) getResources().getDrawable(R.drawable.addpicture)).getBitmap());
		View view=LayoutInflater.from(this).inflate(R.layout.jishibenvideodialog, null);
		dialog=new JSBPDialog(this, view);
		DialogInit(view);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
		dialog.setCancelable(true);
        
	}
	  public void DialogInit(View view){
	    	JSBVContentEdit=(EditText)view.findViewById(R.id.JSBVContextEdit);
	    	JSBVEditGridView=(GridView)view.findViewById(R.id.JSBVEditGridView);
	    	JSBVSave=(TextView)view.findViewById(R.id.JSBVSave);
	    	JSBVCancel=(TextView)view.findViewById(R.id.JSBVCancel);
	    	JSBVCancel.setOnClickListener(this);
	    	JSBVSave.setOnClickListener(this);
	    	JiShiBenPictureSelectAdapter adapter=new JiShiBenPictureSelectAdapter(this, BitmapList,"Video");
        	JSBVEditGridView.setAdapter(adapter);
	        GridViewClick();
	    }
	  public void GridViewClick(){
		  JSBVEditGridView.setOnItemClickListener(new OnItemClickListener() {  
	          @Override  
	          public void onItemClick(AdapterView<?> parent, View v, int position, long id)  
	          {  
	             if(position == 0 && BitmapList.size()<10) { 
	            	  biaozhi=1;
	                 SelectDialog();
	                
	              }  
	              else {  
	            	biaozhi=2;
	            	  SelectDialog();
	              }  
	          Position=position;
	          }  
	      });    
	  }
	          
	        

	      
	    //��ȡͼƬ·�� ��ӦstartActivityForResult    
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {    
	        super.onActivityResult(requestCode, resultCode, data);          
	        if (requestCode == 0x12 && resultCode == RESULT_OK) {
				Uri uri = data.getData();
				String path = getRealPathFromURI(uri);
				File file = new File(path);
				MediaMetadataRetriever mmr = new MediaMetadataRetriever();
				mmr.setDataSource(file.getAbsolutePath());
				Bitmap bitmap1 = mmr.getFrameAtTime();// �����Ƶ��һ֡��Bitmap����
				Bitmap bitmap=ThumbnailUtils.extractThumbnail(bitmap1, 210, 210);
				 BitmapList.add(bitmap);
		         UriList.add(uri);
		         Paths.add(path);
			}
			if (requestCode == 0x13 && resultCode == RESULT_OK) {
				
				 Uri uri = data.getData();           
				 ContentResolver cr = this.getContentResolver();             /** ���ݿ��ѯ������             * ��һ������ uri��ΪҪ��ѯ�����ݿ�+������ơ�             * �ڶ������� projection �� Ҫ��ѯ���С�             * ���������� selection �� ��ѯ���������൱��SQL where��             * ���������� selectionArgs �� ��ѯ�����Ĳ������൱�� ����             * ���ĸ����� sortOrder �� �������             */        
				 assert uri != null;         
				 Cursor cursor = cr.query(uri, null, null, null, null);         
				 if (cursor != null) {              
					 if (cursor.moveToFirst()) {                    // ��ƵID:MediaStore.Audio.Media._ID                   
						// int videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));                    // ��Ƶ���ƣ�MediaStore.Audio.Media.TITLE           
						// String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));                    // ��Ƶ·����MediaStore.Audio.Media.DATA                 
						  String  path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));                    // ��Ƶʱ����MediaStore.Audio.Media.DURATION                   
						 //int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));                    // ��Ƶ��С��MediaStore.Audio.Media.SIZE               
						// long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));                               // ��Ƶ����ͼ·����MediaStore.Images.Media.DATA               
						 String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));                    // ����ͼID:MediaStore.Audio.Media._ID                
						 int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));                    // ����һ Thumbnails ����createVideoThumbnail ͨ��·���õ�����ͼ������Ϊ��Ƶ��Ĭ�ϱ���           
						 // ��һ������Ϊ ContentResolver���ڶ�������Ϊ��Ƶ����ͼID�� ����������kind������Ϊ��MICRO_KIND��MINI_KIND ������˼���Ϊ΢�ͺ�������������ģʽ��ǰ�߷ֱ��ʸ���һЩ��             
						 Bitmap bitmap1 = MediaStore.Video.Thumbnails.getThumbnail(cr, imageId, MediaStore.Video.Thumbnails.MICRO_KIND, null);                    
						 // ������ ThumbnailUtils ����createVideoThumbnail ͨ��·���õ�����ͼ������Ϊ��Ƶ��Ĭ�ϱ���                    // ��һ������Ϊ ��Ƶ/����ͼ��λ�ã��ڶ��������Ƿֱ�����ص�kind           
						 Bitmap bitmap3 = ThumbnailUtils.createVideoThumbnail(imagePath, MediaStore.Video.Thumbnails.MICRO_KIND);        
						 // ���׷����õĻ��������� ThumbnailUtils.extractThumbnail ������ͼת��Ϊ���ƶ���С 
						Bitmap bitmap2=ThumbnailUtils.extractThumbnail(bitmap3, 220, 220);
						BitmapList.add(bitmap2);
						  Paths.add(path);
						              }           
					 cursor.close();        
					 }
				 
				  UriList.add(uri);
				
			}
	     
	        if(BitmapList.size()==3){
				  BitmapList.remove(0);
			   }
	        JiShiBenPictureSelectAdapter adapter=new JiShiBenPictureSelectAdapter(this, BitmapList,"Video");
        	JSBVEditGridView.setAdapter(adapter);
	    }    
	  
	    public void SelectDialog(){
			View view1 = LayoutInflater.from(this).inflate(R.layout.jishibenpictureselect, null);
			TextView PictureSelectCancel=(TextView)view1.findViewById(R.id.PictureSelectCancel);
			TextView SelectTuKu=(TextView)view1.findViewById(R.id.SelectTuKu);
			TextView SelectCamer=(TextView)view1.findViewById(R.id.SelectCamer);
			SelectTuKu.setOnClickListener(this);
			SelectCamer.setOnClickListener(this);
			PictureSelectCancel.setOnClickListener(this);
			SelectTuKu.setText("��Ƶ��");
			SelectCamer.setText("����Ƶ");
			if(biaozhi==2){
				SelectTuKu.setText("����");
				SelectCamer.setText("ɾ��");
			}
			alertdialog=new JSBPDialog(this, view1);
		  alertdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alertdialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			alertdialog.setCancelable(true);
			alertdialog.show();
	    }
	    public Bitmap returnBitmap(Uri uri){
	    	//���������Ƭ��ʾ����
	        Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bitmap;
	    }
	    //��ȡ����·��
		public String getRealPathFromURI(Uri contentUri) {
			String res = null;
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(contentUri, proj, null,
					null, null);
			if (cursor.moveToFirst()) {
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				res = cursor.getString(column_index);
			}
			cursor.close();
			return res;
		}
}
