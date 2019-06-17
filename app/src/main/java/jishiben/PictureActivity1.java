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
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
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

public class PictureActivity1 extends Activity implements OnClickListener {
	 private EditText JSBPContentEdit,JSBPEditText;
	 private ListView JSBPictureListView;
		private GridView JSBPEditGridView;
		private TextView JSBPSave,JSBPCancel;
		private ImageView JSBPadd;
	    private String pathImage;                //选择图片路径  
	    private Bitmap bmp;                      //导入临时图片  
	    private JSBPDialog dialog,alertdialog;
	    private String TuKupath,Camerpath;
	    private String time;
	    private PictureSqlite picturesqlite;
	    private SQLiteDatabase db;
	    private int biaozhi,Position;
	    private List<Bitmap> BitmapList;
	    private List<Uri> UriList;
	    private List<PictureEntity> PictureDatas;
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.jishibenpicture);
    	Calendar cal=Calendar.getInstance();
    	Date date=new Date(System.currentTimeMillis());
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	time=format.format(date);
    	cal.setTime(date);
    	picturesqlite=new PictureSqlite(this, "JSBPicture.db", null, 1);
    	db=picturesqlite.getReadableDatabase();
    	JSBPEditText=(EditText)findViewById(R.id.JSBPEditText);
    	JSBPictureListView=(ListView)findViewById(R.id.JSBPictureListView);
    	JSBPadd=(ImageView)findViewById(R.id.JSBPadd);
    	JSBPadd.setOnClickListener(this);
    	BitmapList=new ArrayList<Bitmap>();
    	UriList=new ArrayList<Uri>();
    	PictureDatas=new ArrayList<PictureEntity>();
    	PictureDatas=picturesqlite.QueryData(db, time,"0");
    	JiShiBenPictureAdapter adapter=new JiShiBenPictureAdapter(PictureDatas, this,"Picture");
    	JSBPictureListView.setAdapter(adapter);
    }
	public void onClick(View view) {
	   switch(view.getId()){
	   case R.id.JSBPSave:
		   if(UriList.size()!=0){
			   TuKupath=UriList.get(0).toString()+",";
		    for(int i=1;i<UriList.size();i++){
		    	TuKupath=TuKupath+UriList.get(i)+",";
		    	}
		    Toast.makeText(this, TuKupath, 1000).show();
		   PictureEntity picture=new PictureEntity(JSBPContentEdit.getText().toString(), TuKupath, time);
		   PictureDatas.add(picture);
		   picturesqlite.Add(db, JSBPContentEdit.getText().toString(), TuKupath, time,"0");
		  JiShiBenPictureAdapter Adapter=new JiShiBenPictureAdapter(PictureDatas, this,"Picture");
	    	JSBPictureListView.setAdapter(Adapter);
		   dialog.dismiss();}
			break;
		case R.id.JSBPCancel:
			 Toast.makeText(this, TuKupath, 1000).show();
			dialog.dismiss();
			break;
		case R.id.SelectTuKu:
			if(biaozhi==1){
			Intent intent = new Intent(Intent.ACTION_PICK,         
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);    
            startActivityForResult(intent, 0x11); }
			if(biaozhi==2){
				
			}
            alertdialog.dismiss();
			break;
		case R.id.SelectCamer:
			if(biaozhi==1){
			TakePhoto();}
			if(biaozhi==2){
				BitmapList.remove(Position);
				JiShiBenPictureSelectAdapter adapter=new JiShiBenPictureSelectAdapter(this, BitmapList,"Picture");
	        	JSBPEditGridView.setAdapter(adapter);
			}
			alertdialog.dismiss();
			break;
		case R.id.JSBPadd:
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
		BitmapList.add(((BitmapDrawable) getResources().getDrawable(R.drawable.addpicture)).getBitmap());
		View view=LayoutInflater.from(this).inflate(R.layout.jishibenpicturedialog, null);
		dialog=new JSBPDialog(this, view);
		DialogInit(view);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.show();
		dialog.setCancelable(true);
        
	}
	  public void DialogInit(View view){
	    	JSBPContentEdit=(EditText)view.findViewById(R.id.JSBPContextEdit);
	    	JSBPEditGridView=(GridView)view.findViewById(R.id.JSBPEditGridView);
	    	JSBPSave=(TextView)view.findViewById(R.id.JSBPSave);
	    	JSBPCancel=(TextView)view.findViewById(R.id.JSBPCancel);
	    	JSBPCancel.setOnClickListener(this);
	    	JSBPSave.setOnClickListener(this);
	    	JiShiBenPictureSelectAdapter adapter=new JiShiBenPictureSelectAdapter(this, BitmapList,"Picture");
        	JSBPEditGridView.setAdapter(adapter);
	        GridViewClick();
	    }
	  public void GridViewClick(){
		  JSBPEditGridView.setOnItemClickListener(new OnItemClickListener() {  
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
	          
	        

	      
	    //获取图片路径 响应startActivityForResult    
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {    
	        super.onActivityResult(requestCode, resultCode, data);          
	        //打开图片    
	        if(resultCode==RESULT_OK && requestCode==0x11) {          
	            Uri uri = data.getData();    
	            BitmapList.add(returnBitmap(uri));
	            UriList.add(uri);
	           
	        } 
	        if(resultCode==RESULT_OK && requestCode==0x12){
	        }
	     
	        if(BitmapList.size()==11){
				  BitmapList.remove(0);
			   }
	        JiShiBenPictureSelectAdapter adapter=new JiShiBenPictureSelectAdapter(this, BitmapList,"Picture");
        	JSBPEditGridView.setAdapter(adapter);
	    }    
	  
	    public void SelectDialog(){
			View view1 = LayoutInflater.from(this).inflate(R.layout.jishibenpictureselect, null);
			TextView PictureSelectCancel=(TextView)view1.findViewById(R.id.PictureSelectCancel);
			TextView SelectTuKu=(TextView)view1.findViewById(R.id.SelectTuKu);
			TextView SelectCamer=(TextView)view1.findViewById(R.id.SelectCamer);
			SelectTuKu.setOnClickListener(this);
			SelectCamer.setOnClickListener(this);
			PictureSelectCancel.setOnClickListener(this);
			if(biaozhi==2){
				SelectTuKu.setText("放大");
				SelectCamer.setText("删除");
			}
			alertdialog=new JSBPDialog(this, view1);
		  alertdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alertdialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			alertdialog.setCancelable(true);
			alertdialog.show();
	    }
	    
	    public void TakePhoto(){
			   SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
			   Date date=new Date(System.currentTimeMillis());
			   String filename=format.format(date);
			   Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
			   File outputImage=new File(getExternalCacheDir(),filename+".jpg");
			   Uri imageUri=Uri.fromFile(outputImage);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent,0x12);
				Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);   
				Uri contentUri = Uri.fromFile(outputImage);    
				mediaScanIntent.setData(contentUri);   
				this.sendBroadcast(mediaScanIntent);
				BitmapList.add(returnBitmap(imageUri));
			    UriList.add(imageUri);
		
	}
	    public Bitmap returnBitmap(Uri uri){
	    	//将拍摄的照片显示出来
	        Bitmap bitmap = null;
			try {
				bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bitmap;
	    }
}
