package shouye;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sqlite.DaiXiCount;
import sqlite.GuDingXiSqlite;
import sqlite.JieXiCount;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import tool.MyListView;
import tool.MyListView.OnRefreshListener;
import Adapters.XiJieAdapter;
import Adapters.YingFuAdapter;
import Adapters.YingFuSheetAdapter;
import Adapters.YingShouAdapter;
import Adapters.YingShouSheetAdapter;
import Dialog.DeletePopupWindow;
import Dialog.JSBPDialog;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.GuDingMingXi;
import entity.YingFu;
import entity.YingShou;

public class YingShouYingFuList extends Activity implements OnClickListener {
 private int number,count1,count2;//数量变化检测标志
 private MyListView ShouFuList;
 private YingShouSQLite yingshousqlite;
 private YingFuSQLite yingfusqlite;
 private JieXiCount jiexisqlite;
	private DaiXiCount daixisqlite;
 private SQLiteDatabase db1,db2,db3,db4,db5;
 private List<YingShou> yingshoulists,Xyingshoulists;
 private List<YingFu> yingfulists,Xyingfulists;
 private YingShouAdapter adapter1;
 private YingFuAdapter adapter2;
 private ImageView timeupdown,countupdown,SeachLuYin,ShouFuseach,ToBottom,returnTop;
 private MyTask task;
 private int count;//月份加减
 private String day="";
 private double yingshouCount,shishouCount,yingfuCount,shifuCount;
 private LinearLayout bottomView,shownodata;
 private LayoutInflater inflater;
 private RelativeLayout showdatatitle;
 private TextView addYear,year,cutYear,addMonth,month,cutMonth,shishouTotalText,yingshouTotalText,yingCount,shiCount;
 private EditText ShouFuseachMessage;
 private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
 private PopupWindow popupwindow;
 private GuDingXiSqlite gudingxijie;
 private List<GuDingMingXi> GuDingMingXiLists;
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	  setContentView(R.layout.yingshoufulist);
	  ShouFuList=(MyListView)findViewById(R.id.ShouFuList);
  	timeupdown=(ImageView)findViewById(R.id.timeupdown);
  	countupdown=(ImageView)findViewById(R.id.countupdown);
	SeachLuYin=(ImageView)findViewById(R.id.SeachLuYin);
	ShouFuseach=(ImageView)findViewById(R.id.ShouFuseach);
	ToBottom=(ImageView)findViewById(R.id.ToBottom);
	returnTop=(ImageView)findViewById(R.id.returnTop);
	shownodata=(LinearLayout)findViewById(R.id.datanodata);
  	addYear=(TextView)findViewById(R.id.addYear);
	year=(TextView)findViewById(R.id.Year);
	cutYear=(TextView)findViewById(R.id.cutYear);
	addMonth=(TextView)findViewById(R.id.addMonth);
	month=(TextView)findViewById(R.id.Month);
	cutMonth=(TextView)findViewById(R.id.cutMonth);
	ShouFuseachMessage=(EditText)findViewById(R.id.ShouFuseachMessage);
	showdatatitle=(RelativeLayout)findViewById(R.id.Title);
  	countupdown.setOnClickListener(this);
  	timeupdown.setOnClickListener(this);
  	addYear.setOnClickListener(this);
	cutYear.setOnClickListener(this);
	addMonth.setOnClickListener(this);
	cutMonth.setOnClickListener(this);
	ShouFuseach.setOnClickListener(this);
	inflater = LayoutInflater.from(this);
	Date date=new Date(System.currentTimeMillis());
	Calendar cal=Calendar.getInstance();
	cal.setTime(date);
	count=cal.get(Calendar.MONTH)+1;
	year.setText(String.valueOf(cal.get(Calendar.YEAR)));
	if (count < 10) {
		month.setText("0" + String.valueOf(count));
	} else {
		month.setText(String.valueOf(count));
	}
	  yingshousqlite = new YingShouSQLite(this,"yingshou.db",null,1);
		db1=yingshousqlite.getReadableDatabase();
		jiexisqlite=new JieXiCount(this,"yingshoujie.db", null, 1);
		db4=jiexisqlite.getReadableDatabase();
		yingfusqlite = new YingFuSQLite(this,"yingfu.db",null,1);
		db2=yingfusqlite.getReadableDatabase();
		daixisqlite=new DaiXiCount(this, "yingfudai.db", null, 1);
		db5=daixisqlite.getReadableDatabase();
		gudingxijie=new GuDingXiSqlite(this, "gudingcountxj.db", null, 1);
		db3=gudingxijie.getReadableDatabase();
		yingshoulists=new ArrayList<YingShou>();
		yingfulists=new ArrayList<YingFu>();
	  Intent intent=getIntent();
	  number=intent.getIntExtra("number", 0);
	  AddFootView();
	  TimeUp();
	  PackageManager pm = getPackageManager();
		List activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() != 0) {
			SeachLuYin.setOnClickListener(this);
		} else { // 若检测不到语音识别程序在本机安装，测将扭铵置灰
			SeachLuYin.setEnabled(false);
		}
		
	  ShouFuList.setOnItemClickListener(new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
		   
			if(number==5){
				if(yingshoulists.get(position-1).getStatus()==2){
					Toast.makeText(YingShouYingFuList.this, "单据已核销", 1000).show();
				}else{
			Intent intent=new Intent();
			intent.putExtra("id", String.valueOf(yingshoulists.get(position-1).getId()));
			intent.putExtra("number", number);
			intent.setClass(YingShouYingFuList.this, ShouFuHeXiao.class);
			startActivity(intent);
			}}
			else if(number==6){
				if(yingfulists.get(position-1).getStatus()==2){
					Toast.makeText(YingShouYingFuList.this, "单据已核銷", 1000).show();
				}else{
					Intent intent=new Intent();
				intent.putExtra("id", String.valueOf(yingfulists.get(position-1).getId()));
				intent.putExtra("number", number);
				intent.setClass(YingShouYingFuList.this, ShouFuHeXiao.class);
				startActivity(intent);}
			}
			}
		
	}); 
	  ShouFuList.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				task=new MyTask();
				task.execute();
			}
			
			
		});
	  ListViewLongClick();
}
public void onClick(View view) {
	switch(view.getId()){
	case R.id.timeupdown:
		if(count1==0){
			count1=1;
			timeupdown.setImageResource(R.drawable.direction);
			TimeUp();
		}else if(count1==1){
			count1=0;
			timeupdown.setImageResource(R.drawable.direction1);
			TimeDown();
		}
		break;
	case R.id.countupdown:
		if(count2==0){ 
			count2=1;
			countupdown.setImageResource(R.drawable.direction);
			CountUp();
		}else if(count2==1){
			count2=0;
			countupdown.setImageResource(R.drawable.direction1);
			CountDown();
		}
		
		break;
	case R.id.addYear:
		year.setText(String.valueOf(Integer.valueOf(year.getText().toString())+1));
		TimeUp();
		break;
	case R.id.cutYear:
		year.setText(String.valueOf(Integer.valueOf(year.getText().toString())-1));
		TimeUp();
		break;
	case R.id.addMonth:
		if(count<10){
			month.setText("0"+String.valueOf(count+1));
			count=count+1;
		}
		else{
			month.setText(String.valueOf(count+1));
			count=count+1;
		}
		if(count>=13){
			count=1;
			month.setText("0"+String.valueOf(count));
		}
		
		TimeUp();
		break;
	case R.id.cutMonth:
		if(count<=10){
			month.setText("0"+String.valueOf(count-1));
			count=count-1;
		}else {
			month.setText(String.valueOf(count-1));
			count=count-1;
		}
		if(count<1){
			count=13;
			month.setText(String.valueOf(count-1));
			count=count-1;
		}
		
		TimeUp();
		break;
	case R.id.SeachLuYin:
		startRecognizerActivity();
	break;
	case R.id.ShouFuseach:
		TimeUp();
		break;
	case R.id.ToBottom:
		ShouFuList.setSelection(ShouFuList.getCount()-1);
		ToBottom.setVisibility(View.GONE);
		returnTop.setVisibility(View.VISIBLE);
		break;
	case R.id.returnTop:
		ShouFuList.setSelection(0);
		ToBottom.setVisibility(View.VISIBLE);
		returnTop.setVisibility(View.GONE);
		break;
	}
	
}
public void TimeUp(){
	Cursor cursor=null;
	baozhaungMenthod();
	String time=year.getText().toString()+"-"+month.getText().toString();
	if(number==5){
	cursor=yingshousqlite.queryYingShouTimeUp(db1,time);
	yingshouCount=yingshousqlite.TotalCount(db1, time, "0");
	shishouCount=yingshousqlite.TotalCount(db1, time, "1");
	}
	else if(number==6){
		cursor=yingfusqlite.queryYingFuTimeUp(db2,time);
		yingfuCount=yingfusqlite.TotalCount(db2, time, "0");
		shifuCount=yingfusqlite.TotalCount(db2, time, "1");
	}
	tool(cursor);
	cursor.close();
}
public void TimeUp1(){
	Cursor cursor=null;
	baozhaungMenthod();
	String time=year.getText().toString()+"-"+month.getText().toString()+"-"+day;
	if(number==5){
	cursor=yingshousqlite.queryYingShouTimeUp(db1,time);
	yingshouCount=yingshousqlite.TotalCount(db1, time, "0");
	shishouCount=yingshousqlite.TotalCount(db1, time, "1");
	}
	else if(number==6){
		cursor=yingfusqlite.queryYingFuTimeUp(db2,time);
		yingfuCount=yingfusqlite.TotalCount(db2, time, "0");
		shifuCount=yingfusqlite.TotalCount(db2, time, "1");
	}
	tool(cursor);
	cursor.close();
}
public void TimeDown(){
	Cursor cursor=null;
	baozhaungMenthod();
	String time=year.getText().toString()+"-"+month.getText().toString();
	if(number==5){
	 cursor=yingshousqlite.queryYingShouTimeDown(db1,time);}
	else if(number==6){
		cursor=yingfusqlite.queryYingFuTimeDown(db2,time);
	}
	tool(cursor);
	cursor.close();
}
public void CountUp(){
	Cursor cursor=null;
	baozhaungMenthod();
	String time=year.getText().toString()+"-"+month.getText().toString();
	if(number==5){
	 cursor=yingshousqlite.queryYingShouCountUp(db1,time);}
	else if(number==6){
		cursor=yingfusqlite.queryYingFuCountUp(db2,time);
	}
	tool(cursor);
	cursor.close();
}
public void CountDown(){
	Cursor cursor=null;
	baozhaungMenthod();
	String time=year.getText().toString()+"-"+month.getText().toString();
	if(number==5){
	 cursor=yingshousqlite.queryYingShouCountDown(db1,time);}
	else if(number==6){
		cursor=yingfusqlite.queryYingFuCountDown(db2,time);
	}
	tool(cursor);
	cursor.close();
}
public void tool(Cursor cursor){
	 List<YingShou> yingshoulist =new ArrayList<YingShou>();
	  List<YingFu> yingfulist=new ArrayList<YingFu>(); 
	  if(number==5){
		  	while(cursor.moveToNext()){
		  		YingShou yingshou=new YingShou();
		  		yingshou.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingshou.setCount(Long.valueOf(cursor.getString(cursor.getColumnIndex("count"))));
		  		yingshou.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingshou.setYingShouSource(cursor.getString(cursor.getColumnIndex("YingShouSource")));
		  		yingshou.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingshou.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingshou.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingshou.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingshou.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingshou.setYingShouObject(cursor.getString(cursor.getColumnIndex("YingShouObject")));
		  		yingshou.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		yingshoulist.add(yingshou);
		  	}
		  	adapter1=new YingShouAdapter(this, yingshoulist);
		  	ShouFuList.setAdapter(adapter1);
		  	yingshoulists=yingshoulist;
		  	if(yingshoulists.size()==0){
		    	shownodata.setVisibility(View.VISIBLE);
		    	showdatatitle.setVisibility(View.GONE);
		    	
		    }else{
		    	shownodata.setVisibility(View.GONE);
		    	showdatatitle.setVisibility(View.VISIBLE);
		    }  
		  	 if(ShouFuList.getFooterViewsCount()!=0){
		    		if(yingshoulist.size()==0){
		    			ShouFuList.removeFooterView(bottomView);
		    		}else{
		    			shishouTotalText.setText("实收合计:");
		    			yingshouTotalText.setText("借款合计:");
		    			shiCount.setText(String.valueOf(shishouCount));
		    			yingCount.setText(String.valueOf(yingshouCount));
		    		}	
		    	}else{
		    		if(yingshoulist.size()!=0){
		    			AddFootView();
		    		}	
		    	}
	  }//shishouTotalText,yingshouTotalText,yingCount,shiCount
	  if(number==6){
		  	while(cursor.moveToNext()){
		  		YingFu yingfu=new YingFu();
		  		yingfu.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingfu.setCount(Long.valueOf(cursor.getString(cursor.getColumnIndex("count"))));
		  		yingfu.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingfu.setYingFuTo(cursor.getString(cursor.getColumnIndex("YingFuTo")));
		  		yingfu.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingfu.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingfu.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingfu.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingfu.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingfu.setYingFuObject(cursor.getString(cursor.getColumnIndex("YingFuObject")));
		  		yingfu.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		yingfulist.add(yingfu);
		  	}
		  	adapter2=new YingFuAdapter(this, yingfulist);
		  	ShouFuList.setAdapter(adapter2);
		  	yingfulists=yingfulist;
		  	if(yingfulists.size()==0){
		    	shownodata.setVisibility(View.VISIBLE);
		    	showdatatitle.setVisibility(View.GONE);
		    	
		    }else{
		    	shownodata.setVisibility(View.GONE);
		    	showdatatitle.setVisibility(View.VISIBLE);
		    }  
		  	if(ShouFuList.getFooterViewsCount()!=0){
	    		if(yingfulist.size()==0){
	    			ShouFuList.removeFooterView(bottomView);
	    		}else{
	    			shishouTotalText.setText("实付合计:");
	    			yingshouTotalText.setText("贷款合计:");
	    			shiCount.setText(String.valueOf(shifuCount));
	    			yingCount.setText(String.valueOf(yingfuCount));
	    		}	
	    	}else{
	    		if(yingfulist.size()!=0){
	    			AddFootView();
	    		}	
	    	}
}
	  
}  
class MyTask extends AsyncTask<Void, Void, Void>{

	@Override
	protected Void doInBackground(Void... arg0) {
		 
 	      try {
 	       Thread.sleep(1000);
 	      } catch (Exception e) {
 	       e.printStackTrace();
 	      }
		return null;
	}
	 protected void onPostExecute(Void result) {
		     TimeUp();
		     if(number==5){
		    	 adapter1.notifyDataSetChanged();
		     }else if(number==6){
		    	 adapter2.notifyDataSetChanged();
		     }
	    	
	    	 ShouFuList.onRefreshComplete();
	     }
}
private void startRecognizerActivity() { // 通过Intent传递语音识别的模式，开启语音
	Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // 语言模式和自由模式的语音识别
	intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
			RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); // 提示语音开始
	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "开始语音"); // 开始语音识别
	startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE); // 调出识别界面
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 回调获取从谷歌得到的数据
	if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
			&& resultCode == RESULT_OK) { // 取得语音的字符
		ArrayList<String> results = data
				.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		String resultString = "";
		for (int i = 0; i < results.size(); i++) {
			resultString += results.get(i);
		}
		String msg = resultString.substring(0, resultString.length() - 1);
		month.setText(getMonth(msg));
		TimeUp();
		Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show();
	} // 语音识别后的回调，将识别的字串以Toast显示
	super.onActivityResult(requestCode, resultCode, data);
}
public String getMonth(String msg){
	String str="";
	int month=0;
	int num=0;
	for(int i=0;i<msg.length();i++){
		if(msg.substring(i, i+1).equals("月")){
			num=i;
		}
	}
	month=Integer.valueOf(msg.substring(0, num));
	if(month<=9){
		str="0"+msg.substring(0, num);
	}
	
	if(month>9){
		str=msg.substring(0, num);
	}
	count=month;
	return str;
}
public void baozhaungMenthod(){	
	day="";
	if(!(ShouFuseachMessage.getText().toString()).equals("")){
	if((Integer.parseInt(ShouFuseachMessage.getText().toString())<10)&&(Integer.parseInt(ShouFuseachMessage.getText().toString())>0)){
		day="0"+ShouFuseachMessage.getText().toString();
	}
	else if((Integer.parseInt(ShouFuseachMessage.getText().toString())>=10)&&(Integer.parseInt(ShouFuseachMessage.getText().toString())<=31)){
		day=ShouFuseachMessage.getText().toString();
	}
	else{
		Toast.makeText(YingShouYingFuList.this, "输入数据不合法!", 1000).show();
		ShouFuseachMessage.setText("");
	}
	}
}
public void AddFootView(){
	bottomView = (LinearLayout) inflater.inflate(R.layout.yingshoufulistbottomitem, null);
    shiCount=(TextView)bottomView.findViewById(R.id.shishouCount);
    yingCount=(TextView)bottomView.findViewById(R.id.yingshouCount);
    shishouTotalText=(TextView)bottomView.findViewById(R.id.shishouTotalText);
    yingshouTotalText=(TextView)bottomView.findViewById(R.id.yingshouTotalText);
    if(number==5){
    	shishouTotalText.setText("实收合计:");
		yingshouTotalText.setText("借款合计:");
		shiCount.setText(String.valueOf(shishouCount));
		yingCount.setText(String.valueOf(yingshouCount));
    }else if(number==6){
    	shishouTotalText.setText("实付合计:");
		yingshouTotalText.setText("贷款合计:");
		shiCount.setText(String.valueOf(shifuCount));
		yingCount.setText(String.valueOf(yingfuCount));
    }
  	ShouFuList.addFooterView(bottomView);
}
public void AddEditTextChangeListener(){
	ShouFuseachMessage.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			TimeUp1();
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}
	});
}
//给listview加长时间点击事件
public void ListViewLongClick(){
	ShouFuList.setOnItemLongClickListener(new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				final int position, long id) {
			
			if(number==5){
				GuDingMingXiLists=new ArrayList<GuDingMingXi>();
				GuDingMingXiLists=gudingxijie.QueryDataByProjectname(db3, yingshoulists.get(position-1).getYingShouObject());
				Xyingshoulists=new  ArrayList<YingShou>();
				if(yingshoulists.get(position-1).getProperty()==0){
				Xyingshoulists=jiexisqlite.queryAllYingShou(db4, yingshoulists.get(position-1).getYingShouObject());}
				PopupWindowMenthod(ShouFuList,position);
			}
			else if(number==6){
				GuDingMingXiLists=new ArrayList<GuDingMingXi>();
				GuDingMingXiLists=gudingxijie.QueryDataByProjectname(db3, yingfulists.get(position-1).getYingFuObject());
				Xyingfulists=new ArrayList<YingFu>();
				if(yingfulists.get(position-1).getProperty()==0){
				Xyingfulists=daixisqlite.queryAllYingFu(db5, yingfulists.get(position-1).getYingFuObject());}
		        PopupWindowMenthod(ShouFuList,position);	
			}
			return true;
		}
	});
	
}
public void AddDeleteDialog(final long id){
	AlertDialog.Builder builder = new AlertDialog.Builder(
			new ContextThemeWrapper(this, R.style.Alert));
	View view=LayoutInflater.from(this).inflate(R.layout.deletedialog, null);
	TextView DeleteClassText=(TextView)view.findViewById(R.id.DeleteClassText);
	TextView CancelClass=(TextView)view.findViewById(R.id.CancelDeleteClass);
	builder.setView(view);
	final AlertDialog dialog = builder.show();
	
	DeleteClassText.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(number==5){
				yingshousqlite.Delete(db1,String.valueOf(id));}
			else if(number==6){
				yingfusqlite.Delete(db2,String.valueOf(id));
			}
			TimeUp();
			dialog.dismiss();
			
		}
	});
	CancelClass.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			dialog.dismiss();
		}
	});
}
public void PopupWindowMenthod(MyListView listview,final int position){
	View view1=LayoutInflater.from(YingShouYingFuList.this).inflate(R.layout.deletepopupwindowitem, null);
	int windowPos[] = DeletePopupWindow.calculatePopWindowPos(listview.getChildAt(position), view1);
	int xOff =30;// 可以自己调整偏移
	windowPos[0] -= xOff;
	popupwindow = new PopupWindow(view1, 200, LayoutParams.WRAP_CONTENT);
	popupwindow.showAtLocation(view1, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
	
	LinearLayout XiJieDelLayout=(LinearLayout)view1.findViewById(R.id.XiJieDelLayout);
	LinearLayout XJMingXiLayout=(LinearLayout)view1.findViewById(R.id.XJMingXiLayout);
	LinearLayout XJDetailLayout=(LinearLayout)view1.findViewById(R.id.XJDetailLayout);
	if(number==5){
		int count=yingshousqlite.Count(db1, yingshoulists.get((position-1)).getYingShouObject(), "1");
		if((yingshoulists.get(position-1).getStatus()==0 && yingshoulists.get(position-1).getProperty()==1) || (count==0 && yingshoulists.get(position-1).getProperty()==0)){
			XiJieDelLayout.setVisibility(View.VISIBLE);
		}else{
			XiJieDelLayout.setVisibility(View.GONE);
		}
		if(Xyingshoulists.size()!=0){
			XJDetailLayout.setVisibility(View.VISIBLE);
		}else{
			XJDetailLayout.setVisibility(View.GONE);
		}
	}
	if(number==6){
		int count=yingfusqlite.Count(db2, yingfulists.get(position-1).getYingFuObject(), "1");
		if((yingfulists.get(position-1).getStatus()==0 && yingfulists.get(position-1).getProperty()==1) || (count==0 && yingfulists.get(position-1).getProperty()==0)){
			XiJieDelLayout.setVisibility(View.VISIBLE);
		}else{
			XiJieDelLayout.setVisibility(View.GONE);
		}
		if(Xyingfulists.size()!=0){
			XJDetailLayout.setVisibility(View.VISIBLE);
		}else{
			XJDetailLayout.setVisibility(View.GONE);
		}
	}
	if(GuDingMingXiLists.size()!=0){
		XJMingXiLayout.setVisibility(View.VISIBLE);
	}else{
		XJMingXiLayout.setVisibility(View.GONE);
	}

	XJMingXiLayout.setOnClickListener(new OnClickListener() {
		public void onClick(View arg0) {
			View view=LayoutInflater.from(YingShouYingFuList.this).inflate(R.layout.xijiedailog, null);
			TextView XiJiePName=(TextView)view.findViewById(R.id.XiJiePName);
			ListView XiJieListView=(ListView)view.findViewById(R.id.XiJieListView);
			if(number==5){
				XiJiePName.setText(yingshoulists.get(position-1).getYingShouObject()+"明细");
			}
			if(number==6){
				XiJiePName.setText(yingfulists.get(position-1).getYingFuObject()+"明细");
			}
			
			XiJieAdapter adapter=new XiJieAdapter(YingShouYingFuList.this, GuDingMingXiLists);
			XiJieListView.setAdapter(adapter);
			JSBPDialog dialog=new JSBPDialog(YingShouYingFuList.this,view);
			dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			dialog.setCancelable(true);
			dialog.show();
			popupwindow.dismiss();
		}
	});
	
	XJDetailLayout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			View view=LayoutInflater.from(YingShouYingFuList.this).inflate(R.layout.xijiedailog, null);
			TextView XiJiePName=(TextView)view.findViewById(R.id.XiJiePName);
			ListView XiJieListView=(ListView)view.findViewById(R.id.XiJieListView);
			
			 LinearLayout headView =(LinearLayout) LayoutInflater.from(YingShouYingFuList.this).inflate(R.layout.shoufusheetitem,
						null);
		 XiJieListView.addHeaderView(headView);
             if(number==5){
            	 XiJiePName.setText(yingshoulists.get(position-1).getYingShouObject()+"——明细");
			 YingShouSheetAdapter<YingShou>  yingshousheetadapter = new YingShouSheetAdapter<YingShou>(YingShouYingFuList.this, Xyingshoulists);
			 XiJieListView.setAdapter(yingshousheetadapter);}
             else{
            	 XiJiePName.setText(yingfulists.get(position-1).getYingFuObject()+"——明细");
            	 YingFuSheetAdapter<YingFu>  yingfusheetadapter = new YingFuSheetAdapter<YingFu>(YingShouYingFuList.this, Xyingfulists);
    			 XiJieListView.setAdapter(yingfusheetadapter);
             }
			JSBPDialog dialog=new JSBPDialog(YingShouYingFuList.this,view);
			dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			dialog.setCancelable(true);
			dialog.show();
			popupwindow.dismiss();
			}
	});
	XiJieDelLayout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			if(number==5){
				AddDeleteDialog(yingshoulists.get(position-1).getId());
			}
			else if(number==6){
				AddDeleteDialog(yingfulists.get(position-1).getId());
			}
			popupwindow.dismiss();
		}
		
	});
	
	TextView CancelText=(TextView)view1.findViewById(R.id.DeleteCancelText);
	CancelText.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			popupwindow.dismiss();
			
		}
	});
}
}