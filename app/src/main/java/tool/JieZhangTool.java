package tool;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import loginOrRegister.Main;
import sqlite.CountSQLite;
import sqlite.GuDingSqlite;
import sqlite.IncomeSQLite;
import sqlite.PaySQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import entity.CountEntity;
import entity.GuDingEntity;

public class JieZhangTool {
	private int Year,Month,year;
	Calendar cal;
	private List<CountEntity> CountDatas;
	private String time;
	private IncomeSQLite incomesqlite;
	private PaySQLite paysqlite;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private CountSQLite countsqlite;
	private GuDingSqlite gudingsqlite;
	private SQLiteDatabase db1,db2,db3,db4,db5,db6;
	private Context context;
	private  List<GuDingEntity> gudingdata;
	public JieZhangTool(Context context,String time,int year) {
		this.time=time;
		this.year=year;
		this.context=context;
	}
	public void init(){
		  CountDatas=new ArrayList<CountEntity>();
			  Date date=new Date(System.currentTimeMillis());
	   cal=Calendar.getInstance();
	   cal.setTime(date);
	   Year=cal.get(Calendar.YEAR);
	   Month=cal.get(Calendar.MONTH)+1;
	   incomesqlite = new IncomeSQLite(context,"income.db",null,1);
	   db1=incomesqlite.getReadableDatabase();
		paysqlite = new PaySQLite(context,"pay.db",null,1);
		db2=paysqlite.getReadableDatabase();
		yingshousqlite = new YingShouSQLite(context, "yingshou.db", null, 1);
		db3 = yingshousqlite.getReadableDatabase();
		yingfusqlite = new YingFuSQLite(context, "yingfu.db", null, 1);
		db4 = yingfusqlite.getReadableDatabase();
		countsqlite=new CountSQLite(context, "TotalCount.db", null, 1);
		db5=countsqlite.getReadableDatabase();
		 gudingsqlite=new GuDingSqlite(context, "gudingcount.db", null, 1);
		 db6=gudingsqlite.getReadableDatabase();
}
	public List<CountEntity> SetData(){
		init();
		  long num1,num2,num3,num4,num5,num6,num;
		  
		gudingdata=new ArrayList<GuDingEntity>();
		gudingdata=gudingsqlite.QueryDataByclassname(db6, "固定收入");
		  num=incomesqlite.TotalCount1(db1);
		  for(int i=0;i<gudingdata.size();i++){
			  if(gudingdata.get(i).getGuDingCount()!=0.0){
				  num=num+1;
					 incomesqlite.addIncome(db1,String.valueOf(num),"收入单", gudingdata.get(i).getGuDingCount(), gudingdata.get(i).getProjectName(), Main.returnName(), time, String.valueOf(2), "固定款项自动入单"); 
					}
		  }
		  num1=incomesqlite.TimeCount(db1, time);
		  gudingdata=new ArrayList<GuDingEntity>();
		  gudingdata=gudingsqlite.QueryDataByclassname(db6, "固定支出");
		  num=paysqlite.TotalCount1(db2);
			  for(int i=0;i<gudingdata.size();i++){
				   if(gudingdata.get(i).getGuDingCount()!=0.0){
					   num=num+1;
						paysqlite.addPay(db2,String.valueOf(num),"支出单", gudingdata.get(i).getGuDingCount(),gudingdata.get(i).getProjectName(), Main.returnName(), time, String.valueOf(2), "固定款项自动入单"); 
						}
			  }
		   num2=paysqlite.TimeCount(db2, time) ;
		   num3=yingshousqlite.PropertyCount(db3,"0");
		   num4=yingfusqlite.PropertyCount(db4,"0");
		   gudingdata=new ArrayList<GuDingEntity>();
		   gudingdata=gudingsqlite.QueryDataByclassname(db6, "固定实收");
		   num=yingshousqlite.TotalCount1(db3);
		   for(int i=0;i<gudingdata.size();i++){
			   if(gudingdata.get(i).getGuDingCount()!=0.0){
				   num=num+1;
				   yingshousqlite.addYingShou(db3, String.valueOf(num),
							"1", "实收单",
							gudingdata.get(i).getGuDingCount(), gudingdata.get(i).getProjectName(), gudingdata.get(i).getProjectName().substring(5, gudingdata.get(i).getProjectName().length()-1), "未知", Main.returnName(), time,
							"2", "固定实收自动入账");
				   double count2=yingshousqlite.getCount(db3,gudingdata.get(i).getProjectName(),"0");
					double count3=count2-gudingdata.get(i).getGuDingCount();
					if(count3>=0){
					yingshousqlite.updateCount(db3, count3,gudingdata.get(i).getProjectName(),"0");
					Toast.makeText(context, "核销成功", 1000).show();}
					else{
						Toast.makeText(context, "核销数据有误，请仔细核对数据", 1000).show();
					}
			   }
		   }
		   num5=yingshousqlite.TimeCount(db3, time,"1");
		   gudingdata=new ArrayList<GuDingEntity>();
		   gudingdata=gudingsqlite.QueryDataByclassname(db6, "固定实付");
		   num=yingfusqlite.TotalCount1(db4);
		   for(int i=0;i<gudingdata.size();i++){
			   if(gudingdata.get(i).getGuDingCount()!=0.0){
				   num=num+1;
				   yingfusqlite.addYingFu(db4, String.valueOf(num),
							"1", "实付单",
							gudingdata.get(i).getGuDingCount(),gudingdata.get(i).getProjectName(), gudingdata.get(i).getProjectName().substring(5, gudingdata.get(i).getProjectName().length()-1), "未知", Main.returnName(), time,
							"2", "固定实付自动入账");
				   double count2=yingfusqlite.getCount(db4,gudingdata.get(i).getProjectName(),"0");
					double count3=count2-gudingdata.get(i).getGuDingCount();
					if(count3>=0){
					yingfusqlite.updateCount(db4, count3,gudingdata.get(i).getProjectName(),"0");
					Toast.makeText(context, "核销成功", 1000).show();}
					else{
						Toast.makeText(context, "核销数据有误，请仔细核对数据", 1000).show();
					}
			   }
		   }
		   num6=yingfusqlite.TimeCount(db4, time,"1");
		      double shou=incomesqlite.TotalCount(db1, time);
			  double zhi=paysqlite.TotalCount(db2, time);
			  double yingshou=yingshousqlite.TotalCountByProperty(db3, "0");
			  double yingfu=yingfusqlite.TotalCountByProperty(db4,"0");
			  double shishou=yingshousqlite.TotalCount(db3, time, "1");
			  double shifu=yingfusqlite.TotalCount(db4, time, "1");
		  CountEntity count=new CountEntity(time,shou,zhi,yingshou,yingfu,shishou,shifu,num1,num2,num3,num4,num5,num6);
		  countsqlite.AddCount(db5, count);
		  if((Month==1 && year!=Year) || (time.equals((Year+"-"+"12")))){
			  shou=countsqlite.TimeCount(db5, String.valueOf(year), "ShouRu");
			  zhi=countsqlite.TimeCount(db5, String.valueOf(year), "ZhiChu");
			  shishou=countsqlite.TimeCount(db5, String.valueOf(year), "ShiShou");
			  shifu=countsqlite.TimeCount(db5, String.valueOf(year), "ShiFu");
			  num1=countsqlite.TimeCount(db5, String.valueOf(year), "ShouRuNum");
			  num2=countsqlite.TimeCount(db5, String.valueOf(year), "ZhiChuNum");
			  num5=countsqlite.TimeCount(db5, String.valueOf(year), "ShiShouNum");
			  num6=countsqlite.TimeCount(db5, String.valueOf(year), "ShiFuNum");
			  CountEntity count1=new CountEntity(String.valueOf(year),shou,zhi,yingshou,yingfu,shishou,shifu,num1,num2,num3,num4,num5,num6);
			  countsqlite.AddCount(db5, count1);
		  }
		  CountDatas=countsqlite.QueryCountByDate(db5, String.valueOf(year));
		  incomesqlite.updateStatusbyTime(db1, "2", time);
		  paysqlite.updateStatusbyTime(db2, "2", time);
		  yingshousqlite.updateStatusByTime(db3, "3", time, "1");
		  yingfusqlite.updateStatusByTime(db4, "3", time, "1");
		  return CountDatas;
	}
	
}