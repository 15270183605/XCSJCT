package life.lifemoney;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sqlite.ChongZhiSQLite;
import Adapters.ChongZhiJiLuAdapter;
import Adapters.SpinnerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.jiacaitong.R;

import entity.ChongZhiEntity;
public class ChongZhiJiLu extends Activity implements OnClickListener {
private Spinner ChongZhiYearSpinner,ChongZhiMonthSpinner,ChongZhiSpinner;
private LinearLayout ChongMonthLayout;
private ListView ChongZhiListView;
private ChongZhiSQLite chongzhisqlite;
private SQLiteDatabase db;
private List<ChongZhiEntity> ChongZhiDatas;
private String selectList[],selectList1[];
private String months[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
		"10", "11", "12" };
private int UserTime;// 用户注册日期
private Calendar cal;
private String spinnerData, spinnerData1,SpinnerTime;
private String ChongSelect[]={"按年排","按月排"};
private ImageView BackChongZhi;
protected void onCreate(Bundle savedInstanceState) {
	   requestWindowFeature(Window.FEATURE_NO_TITLE);
	super.onCreate(savedInstanceState);
	setContentView(R.layout.chongzhijilu);
	ChongZhiYearSpinner=(Spinner)findViewById(R.id.ChongZhiYearSpinner);
	ChongZhiMonthSpinner=(Spinner)findViewById(R.id.ChongZhiMonthSpinner);
	ChongZhiSpinner=(Spinner)findViewById(R.id.ChongZhiSpinner);
	ChongMonthLayout=(LinearLayout)findViewById(R.id.ChongMonthLayout);
	ChongZhiListView=(ListView)findViewById(R.id.ChongZhiListView);
	BackChongZhi=(ImageView)findViewById(R.id.BackChongZhi);
	BackChongZhi.setOnClickListener(this);
	chongzhisqlite=new ChongZhiSQLite(this, "Chongzhi.db", null, 1);
	db=chongzhisqlite.getReadableDatabase();
	ChongZhiDatas=new ArrayList<ChongZhiEntity>();
	cal=Calendar.getInstance();
	spinnerData = String.valueOf(cal.get(Calendar.YEAR));
	ChongZhiDatas=chongzhisqlite.QueryData(db, spinnerData);
	ChongZhiJiLuAdapter adapter=new ChongZhiJiLuAdapter(this, ChongZhiDatas);
	ChongZhiListView.setAdapter(adapter);
	Intent intent=getIntent();
	UserTime=intent.getIntExtra("time", 0);
	spinnerDatas(UserTime);
	spinnerDatas();
}

// 将初始化的图片转换成二进制存储
public byte[] img(int id) {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(id))
			.getBitmap();
	bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
	return baos.toByteArray();
}
// Spinner的响应
public void spinnerDatas(int i) {
	int num = 0;
	 selectList=new String[cal.get(Calendar.YEAR)-i+1]; 
	 for(int j=cal.get(Calendar.YEAR);j>=i;j--){
	 selectList[num]=String.valueOf(j); num++; }
	final SpinnerAdapter adapter1 = new SpinnerAdapter(this,
			android.R.layout.simple_spinner_item, selectList);
	adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
	ChongZhiYearSpinner.setAdapter(adapter1);
	ChongZhiYearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long arg3) {
			spinnerData = selectList[position];
			spinnerMonthDatas(Integer.parseInt(spinnerData));
			RefreshData(SpinnerTime);
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}
	});

}
// Spinner的响应
	public void spinnerMonthDatas(int i) {
		int num = 0;
		if(i==cal.get(Calendar.YEAR)){
			if((cal.get(Calendar.MONTH)+1)==1){
				selectList1=new String[cal.get(Calendar.MONTH)+1];
				selectList1[0]="01";
			}else{
			selectList1=new String[cal.get(Calendar.MONTH)+1]; 
			for(int j=cal.get(Calendar.MONTH)+1;j>0;j--){
				if(j<10){
					selectList1[num]="0"+j;
				}else{
					selectList1[num]=String.valueOf(j);
				}
				num++;
			}
		}}else{
			selectList1=new String[12]; 
				for(int j=12;j>0;j--){
					if(j<10){
						selectList1[num]="0"+j;
					}else{
						selectList1[num]=String.valueOf(j);
					}
					num++;
				}
		}
		final SpinnerAdapter adapter1 = new SpinnerAdapter(this,
				android.R.layout.simple_spinner_item, selectList1);
		adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		ChongZhiMonthSpinner.setAdapter(adapter1);
		ChongZhiMonthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				spinnerData1 = selectList1[position];
				SpinnerTime=spinnerData+"-"+spinnerData1;
				RefreshData(SpinnerTime);
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}
	//Spinner的响应
		public void spinnerDatas(){
			final SpinnerAdapter adapter1=new SpinnerAdapter(this,android.R.layout.simple_spinner_item,ChongSelect);
			adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
			ChongZhiSpinner.setAdapter(adapter1);
			ChongZhiSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long arg3) {
					if(position==0){
						ChongMonthLayout.setVisibility(View.GONE);
						SpinnerTime=spinnerData;
					}
					if(position==1){
						ChongMonthLayout.setVisibility(View.VISIBLE);
						SpinnerTime=spinnerData+"-"+spinnerData1;
						
					}
					RefreshData(SpinnerTime);
					}
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});
		}
		public void RefreshData(String time){
			ChongZhiDatas=new ArrayList<ChongZhiEntity>();
			ChongZhiDatas=chongzhisqlite.QueryData(db, time);
			ChongZhiJiLuAdapter adapter=new ChongZhiJiLuAdapter(this, ChongZhiDatas);
			ChongZhiListView.setAdapter(adapter);
		}

		@Override
		public void onClick(View view) {
			switch(view.getId()){
			case R.id.BackChongZhi:
				this.finish();
				break;
			}
			
		}
}
