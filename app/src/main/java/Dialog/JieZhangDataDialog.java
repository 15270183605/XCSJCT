package Dialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import shouye.JieZhangClassCheck;
import sqlite.CountSQLite;
import sqlite.IncomeSQLite;
import sqlite.PaySQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import tool.JieZhangTool;
import Adapters.JieZhangDataListViewAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.CountEntity;
import entity.JieZhangTemplate;

public class JieZhangDataDialog extends Dialog implements OnClickListener {
	 private final Object lock = new Object();
	private Context context;
	private ListView JiZhangDataListView;
	private LinearLayout JiZhangDataLayout,JiZhangTopLayout;
	private TextView JiZhangText1,TotalTime,NotifyTime,JieZhang,NoJieZhang,JiZhangResult,LookReason,ProgressText;
	private int Year,Month,year;
	Calendar cal;
	private List<CountEntity> CountDatas;
	private String time;
	private int biaozhi,count,biaozhi1,layoutId;//标志变量其中layoutId用来判断当前结账弹出框是从copyshouye传过来，还是由Count传过来
	private IncomeSQLite incomesqlite;
	private PaySQLite paysqlite;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private CountSQLite countsqlite;
	private SQLiteDatabase db1,db2,db3,db4,db5;
	public static Map<String,List<JieZhangTemplate>> JiZhangDatas;//未满足结账条件的数据
	List<JieZhangTemplate> datas,datas1;//暂存数据
	private myThread thread=new myThread();
	private int RandomNum;
	private ProgressBar JiZhangProgress;// 进度条
	private int progressnum;
	 private AlertDialog dialog;
	 public static List<String> strs; 
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				progressnum++;
				JiZhangProgress.setProgress(progressnum);
				ProgressText.setText(progressnum+"%");
				if(progressnum==100){
					JiZhangResult.setText("结账成功!");
					LookReason.setVisibility(View.VISIBLE);
                    LookReason.setText("确定");
                    biaozhi1=1;
				}
				break;
			case 0:
				JiZhangResult.setText("抱歉！结账失败。");
				LookReason.setVisibility(View.VISIBLE);
				biaozhi1=0;
			}
		}
	};
	public JieZhangDataDialog(Context context,String time,int year,int count,int layoutId) {
		super(context);
		this.context = context;
		this.time=time;
		this.year=year;
		this.count=count;
		this.layoutId=layoutId;
	}
	
     @Override
    	protected void onCreate(Bundle savedInstanceState) {
    	
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.jiezhangdialog);
    		init();
    		//AddThread();
    		//thread.start();
    	}
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.JieZhang:
			if(biaozhi==0){
			JiZhangTopLayout.setVisibility(View.GONE);
			JiZhangDataLayout.setVisibility(View.VISIBLE);
			SetData();
			JieZhang.setText("退 出");
			biaozhi=1;}
			else{
				this.dismiss();
			}
			
			break;
		case R.id.LookReason:
			if(biaozhi1==1){
				SetData();
				dialog.dismiss();
			}else{
				Intent intent=new Intent(context,JieZhangClassCheck.class);
				intent.putExtra("time", time);
				intent.putExtra("year", year);
				context.startActivity(intent);
				dialog.dismiss();
				this.dismiss();
			}
			break;
		case R.id.NoJieZhang:
			this.dismiss();
			break;
			
		}

	}
   public void init(){
	   CountDatas=new ArrayList<CountEntity>();
	   Date date=new Date(System.currentTimeMillis());
	   cal=Calendar.getInstance();
	   cal.setTime(date);
	   Year=cal.get(Calendar.YEAR);
	   Month=cal.get(Calendar.MONTH)+1;
	   JiZhangDataListView=(ListView)findViewById(R.id.JiZhangDataListView);
	   JiZhangDataLayout=(LinearLayout)findViewById(R.id.JiZhangDataLayout);
	   JiZhangTopLayout=(LinearLayout)findViewById(R.id.JiZhangTopLayout);
	   JiZhangText1=(TextView)findViewById(R.id.JiZhangText1);
	   TotalTime=(TextView)findViewById(R.id.TotalTime);
	   NotifyTime=(TextView)findViewById(R.id.NotifyTime);
	   JieZhang=(TextView)findViewById(R.id.JieZhang);
	   NoJieZhang=(TextView)findViewById(R.id.NoJieZhang);
	   JieZhang.setOnClickListener(this);
	   NoJieZhang.setOnClickListener(this);
	  
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
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		NotifyTime.setText("通知时间:"+format.format(date));
		if(count==1){
			JiZhangTopLayout.setVisibility(View.VISIBLE);
			JiZhangDataLayout.setVisibility(View.GONE);
			if(layoutId==0){
			JiZhangText1.setText(Html.fromHtml("很抱歉，由于您<font color=red>"+time+"</font>的账款未结，下期做账受限，请先结账!"));
			NoJieZhang.setVisibility(View.GONE);}
			else{
				if(Month<10){
					time=Year+"-"+"0"+Month+cal.getMaximum(Calendar.DATE);
				}
				else{
					time=Year+"-"+Month+cal.getMaximum(Calendar.DATE);
				}
				JiZhangText1.setText(Html.fromHtml("今天是<font color=red>"+time+"</font>号，本月已到月底，请问是否需要结账?"));
			  NoJieZhang.setVisibility(View.VISIBLE);
			}
			JieZhang.setText("结 账");
		}if(count==2){
			JiZhangTopLayout.setVisibility(View.GONE);
			JiZhangDataLayout.setVisibility(View.VISIBLE);
			JieZhang.setText("退 出");
			SetData();
			biaozhi=1;
		}
		
   
   }
   //为结账弹出框设置数据
   public void SetData(){
	  CheckClassStatus();
	   if(JiZhangDatas.size()==0){
	  JieZhangTool jizhangtool=new JieZhangTool(context, time, year);
	  CountDatas=jizhangtool.SetData();
	  if(year==Year){
		  if((cal.get(Calendar.MONTH)+1)!=1){
		  TotalTime.setText(1+"-"+cal.get(Calendar.MONTH));}
	  }
	  else{
		  TotalTime.setText(String.valueOf(year)+"年"+1+"-"+12+"月");
	  }
	  JieZhangDataListViewAdapter adapter=new JieZhangDataListViewAdapter(context, CountDatas);
	  JiZhangDataListView.setAdapter(adapter);}
	   else{
		  
		 setDialog();
		   
	   }
	
   }
   //统一检查单据的状态
  public void CheckClassStatus(){ 
	  JiZhangDatas=new HashMap<String, List<JieZhangTemplate>>();
	    datas=new ArrayList<JieZhangTemplate>();
	    strs=new ArrayList<String>();
	   DataDealNoLock("收入");
	   DataDealNoLock("支出");
	   DataDealNoLock("实收");
	   DataDealNoLock("实付");
	   if(datas.size()!=0){
	    JiZhangDatas.put("未锁定", datas);
	    strs.add("未锁定");
	    }
	   datas1=new ArrayList<JieZhangTemplate>();
	    DateDealNoHeXiao("实收");
	    DateDealNoHeXiao("实付");
	   if(datas1.size()!=0){
	    JiZhangDatas.put("未核销", datas1);
	    strs.add("未核销");
	    }
	  
	    
  }
  //获取未锁定的数据
  public void DataDealNoLock(String str){
	  List<JieZhangTemplate> data=new ArrayList<JieZhangTemplate>();
	  if(str.equals("收入")){
	    data=incomesqlite.StatusCount(db1, time, "0");}
	   if(str.equals("支出")){
		  data=paysqlite.StatusCount(db2, time, "0");
	  }
	   if(str.equals("实收")){
		    data=yingshousqlite.StatusCount(db3, time,"1", "0");}
	   if(str.equals("实付")){
			  data=yingfusqlite.StatusCount(db4, time,"1", "0");
		  }
	
		 for(int i=0;i<data.size();i++){
			  datas.add(data.get(i));
		  }
  } 
  //获取未核销的数据
  public void DateDealNoHeXiao(String str){
	  List<JieZhangTemplate> data=new ArrayList<JieZhangTemplate>();
	  if(str.equals("实收")){
		    data=yingshousqlite.StatusCount(db3, time,"1", "1");}
		  if(str.equals("实付")){
			  data=yingfusqlite.StatusCount(db4, time,"1", "1");
		  }
		  for(int i=0;i<data.size();i++){
			  datas1.add(data.get(i));
		  }
  }
  //设置加载弹出框
  public void setDialog(){
	  AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(context, R.style.Alert));
		View view = LayoutInflater.from(context).inflate(R.layout.jiezhangprogresscheck, null);
		JiZhangProgress=(ProgressBar)view.findViewById(R.id.JiZhangProgress);
		ProgressText=(TextView)view.findViewById(R.id.ProgressText);
		JiZhangResult=(TextView)view.findViewById(R.id.JiZhangResult);
		LookReason=(TextView)view.findViewById(R.id.LookReason);
		LookReason.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		LookReason.setOnClickListener(this);
		builder.setView(view);
		builder.setCancelable(false);
		 dialog = builder.show();
		 Random random=new Random();
		 RandomNum=random.nextInt(40)+55;
		thread.start();
	
		
  }
//加载线程
  public class myThread extends Thread{  
		
      public void run() {     
   	   super.run();      
   	  
   	   while(true){         
   		   
   		   try {           
   			   Thread.sleep(50);//使线程休眠0.1秒            
   			   }catch (InterruptedException e) {     
   				   e.printStackTrace();                }            
   		   if(progressnum>=100){//当前进度等于总进度时退出循环            
   			   progressnum=0;                   
   			   break;             
   			   }  
   		   else if(progressnum>=RandomNum){
   			   thread.interrupt();
   			  
   			   Message msg = new Message();
   			   msg.what=0;
   			   handler.sendMessage(msg); 	
				}
   		    else{ 	
   		    	 Message msg = new Message();
                 msg.what=1;
                handler.sendMessage(msg); 	}


   		       
   	   }        
   	   
      
      }   

      void onPause() {
    	 
          synchronized (lock) {
              try {
                  lock.wait();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
      

  } 
  
}		   

