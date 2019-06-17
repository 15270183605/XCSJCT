package Dialog;

import java.util.ArrayList;
import java.util.List;

import sqlite.IncomeSQLite;
import sqlite.PaySQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import Adapters.DataDetailViewPagerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.Income;
import entity.Pay;
import entity.YingFu;
import entity.YingShou;
import android.support.v4.view.*;
import android.support.v4.view.ViewPager.OnPageChangeListener;
public class CountDetailDialog extends Dialog implements android.view.View.OnClickListener{
  private Context context;
  private List<Income> incomelist= new ArrayList<Income>(); 
  private List<Pay> paylist= new ArrayList<Pay>(); 
  private List<YingShou> yingshoulist = new ArrayList<YingShou>();
  private List<YingFu> yingfulist= new ArrayList<YingFu>();
  private List<View> Views=new ArrayList<View>();
  private ViewPager datailviewpager;
  private TextView NumText,YeMaNum;
  private int count=0;
  private int YeMa=1;
  private int biaozhi,number,CurrentIndex;//标志变量
  private String str,time;
/*  private  IncomeSheetAdapter<T> incomesheetadapter;
  private  PaySheetAdapter paysheetadapter; 
  private  YingShouSheetAdapter yingshousheetadapter;
  private  YingFuSheetAdapter yingfusheetadapter;*/
	public CountDetailDialog(Context context,String str,String time) {
		super(context);
	this.context=context;	
	this.str=str;
	this.time=time;
	}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sheetshowdatasdetail);
		TextView DetailText=(TextView)findViewById(R.id.DetailText);
		ImageView close=(ImageView)findViewById(R.id.close);
		datailviewpager=(ViewPager)findViewById(R.id.DataDetailViewPager);
		NumText=(TextView)findViewById(R.id.NumText);
		YeMaNum=(TextView)findViewById(R.id.YeMaNum);
		close.setOnClickListener(this);
		DetailText.setText(str+"明细");
		getDatas(str,time);
		ViewPagerListener();
	}
	 public void getDatas(String str,String time){
		    SQLiteDatabase db1,db2,db3,db4;
			if(str.equals("收入单")){
				  IncomeSQLite  incomesqlite = new IncomeSQLite(context,"income.db",null,1);
				db1=incomesqlite.getReadableDatabase();
				incomelist=incomesqlite.queryAllIncomeByTime(db1, time);
				
				if((incomelist.size()%10)==0){
					count=incomelist.size()/10;
				}else{
					count=incomelist.size()/10+1;
				}
				for(int i=0;i<count;i++){
					View view=LayoutInflater.from(context).inflate(R.layout.detaillistview, null);
					Views.add(view);
				}
				DataDetailViewPagerAdapter<Income> adapter=new DataDetailViewPagerAdapter<Income>(context,Views, incomelist,str);
				datailviewpager.setAdapter(adapter);
				NumText.setText(String.valueOf(incomelist.size()));
				YeMaNum.setText(YeMa+"/"+String.valueOf(count));
			}	
		       else if(str.equals("支出单")){
		    	   PaySQLite paysqlite = new PaySQLite(context,"pay.db",null,1);
		    	   db2=paysqlite.getReadableDatabase();
		    	  paylist=paysqlite.queryAllPayByTime(db2, time);
					if((paylist.size()%10)==0){
						count=paylist.size()/10;
					}else{
						count=paylist.size()/10+1;
					}
					for(int i=0;i<count;i++){
						View view=LayoutInflater.from(context).inflate(R.layout.detaillistview, null);
						Views.add(view);
					}
					DataDetailViewPagerAdapter<Pay> adapter=new DataDetailViewPagerAdapter<Pay>(context,Views, paylist,str);
					datailviewpager.setAdapter(adapter);
					NumText.setText(String.valueOf(paylist.size()));
					YeMaNum.setText(YeMa+"/"+String.valueOf(count));
				}
		      else if(str.equals("借款单") || str.equals("实收单")){
		    	  YingShouSQLite yingshousqlite = new YingShouSQLite(context,"yingshou.db",null,1);
		    		db3=yingshousqlite.getReadableDatabase();
		    		if(str.equals("借款单")){
		    	 yingshoulist=yingshousqlite.queryAllYingShouByProperty(db3, time,"0");}
		    		else if(str.equals("实收单")){
		    			yingshoulist=yingshousqlite.queryAllYingShouByProperty(db3, time,"1");
		    		}
					if((yingshoulist.size()%10)==0){
						count=yingshoulist.size()/10;
					}else{
						count=yingshoulist.size()/10+1;
					}
					for(int i=0;i<count;i++){
						View view=LayoutInflater.from(context).inflate(R.layout.detaillistview, null);
						Views.add(view);
					}
					DataDetailViewPagerAdapter<YingShou> adapter=new DataDetailViewPagerAdapter<YingShou>(context,Views, yingshoulist,str);
					datailviewpager.setAdapter(adapter);
					NumText.setText(String.valueOf(yingshoulist.size()));
					YeMaNum.setText(YeMa+"/"+String.valueOf(count));
		     	   
		         }
		      else if(str.equals("贷款单") || str.equals("实付单")){
		    	  YingFuSQLite yingfusqlite = new YingFuSQLite(context,"yingfu.db",null,1);
		    	  db4=yingfusqlite.getReadableDatabase();
		    	  if(str.equals("贷款单")){
		    		  yingfulist=yingfusqlite.queryAllYingFuByProperty(db4, time, "0"); }
				    		else if(str.equals("实付单")){
				    			yingfulist=yingfusqlite.queryAllYingFuByProperty(db4, time, "1"); 
				    		}
		    	
		    	 
					if((yingfulist.size()%10)==0){
						count=yingfulist.size()/10;
					}else{
						count=yingfulist.size()/10+1;
					}
					for(int i=0;i<count;i++){
						View view=LayoutInflater.from(context).inflate(R.layout.detaillistview, null);
						Views.add(view);
					}
					DataDetailViewPagerAdapter<YingFu> adapter=new DataDetailViewPagerAdapter<YingFu>(context,Views, yingfulist,str);
					datailviewpager.setAdapter(adapter);
					NumText.setText(String.valueOf(yingfulist.size()));
					YeMaNum.setText(YeMa+"/"+String.valueOf(count));
		      }
	   }
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.close:
			this.dismiss();
			break;
		}
		
	}
	public void ViewPagerListener(){
		datailviewpager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int position) {
				if(number!=position){
					if(biaozhi==1){
						YeMa=YeMa+1;
						YeMaNum.setText(YeMa+"/"+count);
					}if(biaozhi==2){
						YeMa=YeMa-1;
						YeMaNum.setText(YeMa+"/"+count);
					}
				}
				number=position;
				
			}

			public void onPageScrolled(int position, float arg1, int arg2) {
			if(CurrentIndex==position){
				biaozhi=1;
			}else{
				biaozhi=2;
			}
				
			}

			public void onPageScrollStateChanged(int state) {
				if(state==1){
					CurrentIndex=datailviewpager.getCurrentItem();
				}
				
			}
		});
	}
}
