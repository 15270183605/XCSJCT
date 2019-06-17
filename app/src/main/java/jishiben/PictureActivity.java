package jishiben;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sqlite.PictureSqlite;
import Adapters.JiShiBenPictureSelectAdapter;
import Dialog.JSBPDialog;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.PictureEntity;

public class PictureActivity extends Activity implements OnClickListener {
	 private EditText JSBPContentEdit,JSBPEditText;
	 private ListView JSBPictureListView;
		private GridView JSBPEditGridView;
		private TextView JSBPSave,JSBPCancel;
		private ImageView JSBPadd;
	    private String pathImage;                //选择图片路径  
	    private Bitmap bmp;                      //导入临时图片  
	    private ArrayList<HashMap<String, Object>> imageItem;  
	    private SimpleAdapter simpleAdapter;     //适配器 
	    private JSBPDialog dialog,alertdialog;
        private List<String> pathImagesList;
	    private String TuKupath,Camerpath;
	    private String time;
	    private PictureSqlite picturesqlite;
	    private SQLiteDatabase db;
	    private int biaozhi;
	    private List<PictureEntity> PictureDatas;
	    private List<Bitmap> BitmapList;
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
    	pathImagesList=new ArrayList<String>();
    	PictureDatas=new ArrayList<PictureEntity>();
    	PictureDatas=picturesqlite.QueryData(db, time,"0");
    }
	public void onClick(View view) {
	   switch(view.getId()){
	   case R.id.JSBPSave:
		   for(int i=0;i<pathImagesList.size();i++){
			   if(i!=pathImagesList.size()-1){
			   TuKupath=TuKupath+pathImagesList.get(i)+",";}else{
				   TuKupath=TuKupath+pathImagesList.get(i);
			   }
		   }
		   picturesqlite.Add(db, JSBPContentEdit.getText().toString(), TuKupath, time,"0");
		  
		   dialog.dismiss();
			break;
		case R.id.JSBPCancel:
			dialog.dismiss();
			break;
		case R.id.SelectTuKu:
			Intent intent = new Intent(Intent.ACTION_PICK,         
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);    
            startActivityForResult(intent, 0x11);   
            alertdialog.dismiss();
			break;
		case R.id.SelectCamer:
			TakePhoto();
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
	    	//获取资源图片加号  
	        bmp = BitmapFactory.decodeResource(getResources(),R.drawable.addpicture);  
	        imageItem = new ArrayList<HashMap<String, Object>>();  
	        HashMap<String, Object> map = new HashMap<String, Object>();  
	        map.put("itemImage", bmp);  
	        imageItem.add(map);  
	        simpleAdapter=new SimpleAdapter(this, imageItem, R.layout.jishibenpicturegridviewitem, new String[]{"itemImage"}, new int[]{R.id.imageView1});
	        simpleAdapter.setViewBinder(new ViewBinder() {    
	            @Override    
	            public boolean setViewValue(View view, Object data,    
	                    String textRepresentation) {    
	               
	                if(view instanceof ImageView && data instanceof Bitmap){    
	                    ImageView i = (ImageView)view;    
	                    i.setImageBitmap((Bitmap) data);    
	                    return true;    
	                }    
	                return false;    
	            }  
	        }); 
	        JSBPEditGridView.setAdapter(simpleAdapter); 
	        GridViewClick();
	    }
	  public void GridViewClick(){
		  JSBPEditGridView.setOnItemClickListener(new OnItemClickListener() {  
	          @Override  
	          public void onItemClick(AdapterView<?> parent, View v, int position, long id)  
	          {  
	              if( imageItem.size() == 11) { //第一张为默认图片  
	                  Toast.makeText(PictureActivity.this, "图片数10张已满", Toast.LENGTH_SHORT).show();  
	              }  
	              else if(position == 0) { 
	                 SelectDialog();
	                  
	              }  
	              else {  
	                
	              }  
	          }  
	      });    
	  }
	          
	        

	      
	    //获取图片路径 响应startActivityForResult    
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {    
	        super.onActivityResult(requestCode, resultCode, data);          
	        //打开图片    
	        if(resultCode==RESULT_OK && requestCode==0x11) {          
	            Uri uri = data.getData();    
	            if (!TextUtils.isEmpty(uri.getAuthority())) {    
	                //查询选择图片    
	                Cursor cursor = getContentResolver().query(    
	                        uri,    
	                        new String[] { MediaStore.Images.Media.DATA },    
	                        null,     
	                        null,     
	                        null);    
	               
	                if (null == cursor) {    
	                    return;    
	                }    
	                cursor.moveToFirst();    
	                pathImage = cursor.getString(cursor    
	                        .getColumnIndex(MediaStore.Images.Media.DATA));  
	                pathImagesList.add(pathImage);
	            }  
	        } 
	        if(resultCode==RESULT_OK && requestCode==0x12){
	        	JiShiBenPictureSelectAdapter adapter=new JiShiBenPictureSelectAdapter(this, BitmapList,"Picture");
	        	JSBPEditGridView.setAdapter(adapter);
	        }
	    }    
	    //刷新图片  
	    @Override  
	    protected void onResume() {  
	        super.onResume();  
	        if(!TextUtils.isEmpty(pathImage)){  
	            Bitmap addbmp=BitmapFactory.decodeFile(pathImage);  
	            HashMap<String, Object> map = new HashMap<String, Object>();  
	            map.put("itemImage", addbmp);  
	            imageItem.add(map);  
	            simpleAdapter = new SimpleAdapter(this,   
	                    imageItem, R.layout.jishibenpicturegridviewitem,   
	                    new String[] { "itemImage"}, new int[] { R.id.imageView1});   
	            simpleAdapter.setViewBinder(new ViewBinder() {    
	              
	                public boolean setViewValue(View view, Object data,    
	                        String textRepresentation) {    
	                    // TODO Auto-generated method stub    
	                    if(view instanceof ImageView && data instanceof Bitmap){    
	                        ImageView i = (ImageView)view;    
	                        i.setImageBitmap((Bitmap) data);    
	                        return true;    
	                    }    
	                    return false;    
	                }  
	            });   
	            JSBPEditGridView.setAdapter(simpleAdapter);  
	            simpleAdapter.notifyDataSetChanged();  
	            //刷新后释放防止手机休眠后自动添加  
	            pathImage = null;  
	        }  
	    }  
	    public void SelectDialog(){
			View view1 = LayoutInflater.from(this).inflate(R.layout.jishibenpictureselect, null);
			TextView PictureSelectCancel=(TextView)view1.findViewById(R.id.PictureSelectCancel);
			TextView SelectTuKu=(TextView)view1.findViewById(R.id.SelectTuKu);
			TextView SelectCamer=(TextView)view1.findViewById(R.id.SelectCamer);
			SelectTuKu.setOnClickListener(this);
			SelectCamer.setOnClickListener(this);
			PictureSelectCancel.setOnClickListener(this);
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
			   if(BitmapList.size()==11){
				  BitmapList.remove(0);
			   }
		
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
