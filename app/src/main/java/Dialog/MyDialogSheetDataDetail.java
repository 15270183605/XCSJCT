package Dialog;

import java.util.ArrayList;
import java.util.List;

import sqlite.CountSQLite;
import sqlite.IncomeSQLite;
import sqlite.PaySQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import Adapters.DataDetailViewPagerAdapter;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.CountEntity;
import entity.Income;
import entity.Pay;
import entity.SheetTemplate;
import entity.YingFu;
import entity.YingShou;

public class MyDialogSheetDataDetail extends Dialog implements android.view.View.OnClickListener{
  private Context context;
  private SheetTemplate sheetTemplate;
  private List<Income> incomelist= new ArrayList<Income>(); 
  private List<Pay> paylist= new ArrayList<Pay>(); 
  private List<YingShou> yingshoulist = new ArrayList<YingShou>();
  private List<YingFu> yingfulist= new ArrayList<YingFu>();
  private List<CountEntity> countEntity=new ArrayList<CountEntity>();
  private List<View> Views=new ArrayList<View>();
  private ViewPager datailviewpager;
  private TextView NumText,YeMaNum;
  private int count=0;
  private int YeMa=1;
  private int biaozhi,number,CurrentIndex;//标志变量
	public MyDialogSheetDataDetail(Context context,SheetTemplate sheetTemplate) {
		super(context);
	this.context=context;	
	this.sheetTemplate=sheetTemplate;
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
		DetailText.setText(sheetTemplate.getClassName()+"明细");
		getDatas(sheetTemplate.getClassName(),sheetTemplate.getTime(),sheetTemplate.getClassSource());
		ViewPagerListener();
	}
	 public void getDatas(String str,String time,String source){
		   IncomeSQLite  incomesqlite = new IncomeSQLite(context,"income.db",null,1);
		  PaySQLite paysqlite = new PaySQLite(context,"pay.db",null,1);
		    YingShouSQLite yingshousqlite = new YingShouSQLite(context,"yingshou.db",null,1);
		   YingFuSQLite yingfusqlite = new YingFuSQLite(context,"yingfu.db",null,1);
		   CountSQLite countSqlite=new CountSQLite(context, "TotalCount.db", null, 1);
		   SQLiteDatabase db1,db2,db3,db4,db5;
			if(str.equals("收入单")){
				db1=incomesqlite.getReadableDatabase();
				incomelist=incomesqlite.queryAllIncomeBySource(db1, time, source);
				
				if((incomelist.size()%9)==0){
					count=incomelist.size()/9;
				}else{
					count=(incomelist.size()/9)+1;
				}
				for(int i=0;i<count;i++){
					View view=LayoutInflater.from(context).inflate(R.layout.detaillistview, null);
					Views.add(view);
				}
				DataDetailViewPagerAdapter<Income> adapter=new DataDetailViewPagerAdapter<Income>(context,Views, incomelist,str);
				datailviewpager.setAdapter(adapter);
				NumText.setText(String.valueOf(incomelist.size()));
				YeMaNum.setText(YeMa+"/"+String.valueOf(count));
				 /*incomesheetadapter = new IncomeSheetAdapter(context, incomelist);
				 LinearLayout headView =(LinearLayout) LayoutInflater.from(context).inflate(R.layout.incomepaysheetitem,
							null);
				 listview.addHeaderView(headView);
				 listview.setAdapter(incomesheetadapter);
				 */
			}	
		       else if(str.equals("支出单")){
		    	   db2=paysqlite.getReadableDatabase();
		    	  paylist=paysqlite.queryAllPayBySource(db2, time, source);
					if((paylist.size()%9)==0){
						count=paylist.size()/9;
					}else{
						count=(paylist.size()/9)+1;
					}
					for(int i=0;i<count;i++){
						View view=LayoutInflater.from(context).inflate(R.layout.detaillistview, null);
						Views.add(view);
					}
					DataDetailViewPagerAdapter<Pay> adapter=new DataDetailViewPagerAdapter<Pay>(context,Views, paylist,str);
					datailviewpager.setAdapter(adapter);
					NumText.setText(String.valueOf(paylist.size()));
					YeMaNum.setText(YeMa+"/"+String.valueOf(count));
		    	 /* paysheetadapter = new PaySheetAdapter(context, paylist);
		    	  LinearLayout headView =(LinearLayout) LayoutInflater.from(context).inflate(R.layout.incomepaysheetitem,
							null);
				 listview.addHeaderView(headView);
		    	  listview.setAdapter(paysheetadapter);*/
				}
		      else if(str.equals("借款单")||str.equals("实收单")){
		    		db3=yingshousqlite.getReadableDatabase();
		    	 yingshoulist=yingshousqlite.queryAllYingShouBySource(db3, time, source);
		    	 
					if((yingshoulist.size()%9)==0){
						count=yingshoulist.size()/9;
					}else{
						count=(yingshoulist.size()/9)+1;
					}
					for(int i=0;i<count;i++){
						View view=LayoutInflater.from(context).inflate(R.layout.detaillistview, null);
						Views.add(view);
					}
					DataDetailViewPagerAdapter<YingShou> adapter=new DataDetailViewPagerAdapter<YingShou>(context,Views, yingshoulist,str);
					datailviewpager.setAdapter(adapter);
					NumText.setText(String.valueOf(yingshoulist.size()));
					YeMaNum.setText(YeMa+"/"+String.valueOf(count));
		    	 /*yingshousheetadapter = new YingShouSheetAdapter(context, yingshoulist);
		    	 LinearLayout headView =(LinearLayout) LayoutInflater.from(context).inflate(R.layout.shoufusheetitem,
							null);
				 listview.addHeaderView(headView);
		    	  listview.setAdapter(yingshousheetadapter);*/
		     	   
		         }
		       if(str.equals("贷款单")||str.equals("实付单")){
		    	  db4=yingfusqlite.getReadableDatabase();
		    	  yingfulist=yingfusqlite.queryAllYingFuBySource(db4, time, source); 
		    	 
					if((yingfulist.size()%9==0)){
						count=yingfulist.size()/9;
					}else{
						count=(yingfulist.size()/9)+1;
					}
					for(int i=0;i<count;i++){
						View view=LayoutInflater.from(context).inflate(R.layout.detaillistview, null);
						Views.add(view);
					}
					DataDetailViewPagerAdapter<YingFu> adapter=new DataDetailViewPagerAdapter<YingFu>(context,Views, yingfulist,str);
					datailviewpager.setAdapter(adapter);
					NumText.setText(String.valueOf(yingfulist.size()));
					YeMaNum.setText(YeMa+"/"+String.valueOf(count));
		    	 /*  yingfusheetadapter = new YingFuSheetAdapter(context, yingfulist);
		    	   LinearLayout headView =(LinearLayout) LayoutInflater.from(context).inflate(R.layout.shoufusheetitem,
							null);
				 listview.addHeaderView(headView);
		    	   listview.setAdapter(yingfusheetadapter);*/
		      }
		       if(str.equals("总账单")){
		    	   db5=countSqlite.getReadableDatabase();
		    	   countEntity=countSqlite.QueryCountByDate(db5, time);

					if((countEntity.size()%9==0)){
						count=countEntity.size()/9;
					}else{
						count=(countEntity.size()/9)+1;
					}
					for(int i=0;i<count;i++){
						View view=LayoutInflater.from(context).inflate(R.layout.detaillistview, null);
						Views.add(view);
					}
					DataDetailViewPagerAdapter<CountEntity> adapter=new DataDetailViewPagerAdapter<CountEntity>(context,Views, countEntity,str);
					datailviewpager.setAdapter(adapter);
					NumText.setText(String.valueOf(countEntity.size()));
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
			
			@Override
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
			
			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
			if(CurrentIndex==position){
				biaozhi=1;
			}else{
				biaozhi=2;
			}
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				if(state==1){
					CurrentIndex=datailviewpager.getCurrentItem();
				}
				
			}
		});
	}
}
