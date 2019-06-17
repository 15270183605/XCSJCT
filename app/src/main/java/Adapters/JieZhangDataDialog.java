package Adapters;

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
   private Context context;
	private ListView JiZhangDataListView;
	private LinearLayout JiZhangDataLayout,JiZhangTopLayout;
	private TextView JiZhangText1,TotalTime,NotifyTime,JieZhang,JiZhangResult,LookReason,ProgressText;
	private int Year,Month,year;
	Calendar cal;
	private List<CountEntity> CountDatas;
	private String time;
	private int biaozhi,count,biaozhi1;//��־����
	private IncomeSQLite incomesqlite;
	private PaySQLite paysqlite;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private CountSQLite countsqlite;
	private SQLiteDatabase db1,db2,db3,db4,db5;
	private static Map<String,List<JieZhangTemplate>> JiZhangDatas;//δ�����������������
	List<JieZhangTemplate> datas;//�ݴ�����
	private Thread thread;
	private int RandomNum;
	private ProgressBar JiZhangProgress;// ������
	private int progressnum;
	 private AlertDialog dialog;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 1:
				progressnum++;
				JiZhangProgress.setProgress(progressnum);
				ProgressText.setText(progressnum+"%");
				if(progressnum==100){
					JiZhangResult.setText("���˳ɹ�!");
					LookReason.setVisibility(View.VISIBLE);
                    LookReason.setText("ȷ��");
                    biaozhi1=1;
				}
				break;
			case 0:
				JiZhangResult.setText("��Ǹ������ʧ�ܡ�");
				LookReason.setVisibility(View.VISIBLE);
				biaozhi1=0;
			}
		}
	};
	public JieZhangDataDialog(Context context,String time,int year,int count) {
		super(context);
		this.context = context;
		this.time=time;
		this.year=year;
		this.count=count;
	}
	
     @Override
    	protected void onCreate(Bundle savedInstanceState) {
    	
    		super.onCreate(savedInstanceState);
    		setContentView(R.layout.jiezhangdialog);
    		init();
    	}
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.JieZhang:
			if(biaozhi==0){
			JiZhangTopLayout.setVisibility(View.GONE);
			JiZhangDataLayout.setVisibility(View.VISIBLE);
			JieZhang.setText("�� ��");
			SetData();
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
				context.startActivity(intent);
				dialog.dismiss();
				this.dismiss();
			}
			
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
	   JieZhang.setOnClickListener(this);
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
		NotifyTime.setText("֪ͨʱ��:"+format.format(date));
		if(count==1){
			JiZhangText1.setText(Html.fromHtml("�ܱ�Ǹ��������<font color=red>"+time+"</font>���˿�δ�ᣬ�����������ޣ����Ƚ���!"));
			JiZhangTopLayout.setVisibility(View.VISIBLE);
			JiZhangDataLayout.setVisibility(View.GONE);
			JieZhang.setText("�� ��");
		}if(count==2){
			JiZhangTopLayout.setVisibility(View.GONE);
			JiZhangDataLayout.setVisibility(View.VISIBLE);
			JieZhang.setText("�� ��");
			SetData();
			biaozhi=1;
		}
		
   
   }
   public void SetData(){
	   CheckClassStatus();
	   if(JiZhangDatas.size()==0){
	   JieZhangTool jizhangtool=new JieZhangTool(context, time, year);
	   CountDatas=jizhangtool.SetData();
	  if(year==Year){
		  TotalTime.setText(1+"-"+cal.get(Calendar.MONTH));
	  }
	  else{
		  TotalTime.setText(String.valueOf(year)+"��"+1+"-"+12+"��");
	  }
	  JieZhangDataListViewAdapter adapter=new JieZhangDataListViewAdapter(context, CountDatas);
	  JiZhangDataListView.setAdapter(adapter);}
	   else{
		  setDialog();
	   }
	
   }
  public void CheckClassStatus(){ 
	    JiZhangDatas=new HashMap<String, List<JieZhangTemplate>>();
	    datas=new ArrayList<JieZhangTemplate>();
	    DataDealNoLock("����");
	    DataDealNoLock("֧��");
	    DataDealNoLock("ʵ��");
	    DataDealNoLock("ʵ��");
	    JiZhangDatas.put("δ����", datas);	
	    datas.clear();
	    DateDealNoHeXiao("ʵ��");
	    DateDealNoHeXiao("ʵ��");
	    JiZhangDatas.put("δ����", datas);	
	    datas.clear();
  }
  public void DataDealNoLock(String str){
	  List<JieZhangTemplate> data=new ArrayList<JieZhangTemplate>();
	  if(str.equals("����")){
	    data=incomesqlite.StatusCount(db1, time, "0");}
	
	  if(str.equals("֧��")){
		  data=paysqlite.StatusCount(db2, time, "0");
	  }
	  if(str.equals("ʵ��")){
		    data=yingshousqlite.StatusCount(db3, time,"1", "0");}
		  if(str.equals("ʵ��")){
			  data=yingfusqlite.StatusCount(db4, time,"1", "0");
		  }
		  for(int i=0;i<data.size();i++){
			  datas.add(data.get(i));
		  }
  }
  public void DateDealNoHeXiao(String str){
	  List<JieZhangTemplate> data=new ArrayList<JieZhangTemplate>();
	  if(str.equals("ʵ��")){
		    data=yingshousqlite.StatusCount(db3, time,"1", "1");}
		  if(str.equals("ʵ��")){
			  data=yingfusqlite.StatusCount(db4, time,"1", "1");
		  }
		  for(int i=0;i<data.size();i++){
			  datas.add(data.get(i));
		  }
  }
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
		 RandomNum=random.nextInt(40)+60;
		 Intent intent=new Intent(context,JieZhangClassCheck.class);
			context.startActivity(intent);
			dialog.dismiss();
			this.dismiss();
		//thread.start();
		
  }
  public void AddThread(){
	  thread = new Thread(new Runnable() {	
		  Message msg = new Message(); 
			public void run() {			
				while (!thread.currentThread().interrupted()) {				
					try {					

					thread.sleep(40);										
					} catch (InterruptedException e) {					
						// TODO Auto-generated catch block					
						e.printStackTrace();					
						thread.interrupt(); //��ֹһЩ����Ҫ���쳣�׳�					}				} 			}		});
				}
					
					if(progressnum==100){
						 progressnum=0; break; } 
					if(progressnum==RandomNum){
						thread.interrupt();
						msg.what=0;
						 handler.sendMessage(msg);
					}else{
					    msg.what=1;
					    handler.sendMessage(msg);}	
			}
		}
		});
	
  }
  public static Map<String,List<JieZhangTemplate>> returnMapDatas(){
	  return JiZhangDatas;
  }
}
