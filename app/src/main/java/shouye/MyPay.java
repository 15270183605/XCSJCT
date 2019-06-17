package shouye;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sqlite.GuDingXiSqlite;
import sqlite.PaySQLite;
import tool.MyListView;
import tool.MyListView.OnRefreshListener;
import Adapters.PayListAdapter;
import Adapters.XiJieAdapter;
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
import entity.Pay;

public class MyPay extends Activity implements OnClickListener {
	private MyListView PayList;
	private PaySQLite paysqlite;
	private SQLiteDatabase db,db1;
	private PayListAdapter adapter;
	private int number,count1,count2;//数量变化检测标志
	 private LayoutInflater inflater;
	 private LinearLayout bottomView,shownodata;
	private ImageView timeupdown,countupdown,SeachLuYin,Payseach,ToBottom,returnTop;
	private List<Pay> paylist;
	private MyTask task;
	private int count;//月份加减；
	private String day="";
	private double payCount;
	private TextView addYear,year,cutYear,addMonth,month,cutMonth,payCountText;
	private EditText PayseachMessage;
	private RelativeLayout showdatatitle;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private PopupWindow popupwindow;
	private GuDingXiSqlite gudingxijie;
	private List<GuDingMingXi> GuDingMingXiLists;
	 protected void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	setContentView(R.layout.mypay);
	    	Intent intent=getIntent();
			number=intent.getIntExtra("number", 0);
	    	PayList=(MyListView)findViewById(R.id.PayList);
	    	timeupdown=(ImageView)findViewById(R.id.timeupdown);
	    	countupdown=(ImageView)findViewById(R.id.countupdown);
	    	SeachLuYin=(ImageView)findViewById(R.id.SeachLuYin);
	    	Payseach=(ImageView)findViewById(R.id.Payseach);
	    	ToBottom=(ImageView)findViewById(R.id.ToBottom);
	    	returnTop=(ImageView)findViewById(R.id.returnTop);
	    	shownodata=(LinearLayout)findViewById(R.id.datanodata);
	    	addYear=(TextView)findViewById(R.id.addYear);
	    	year=(TextView)findViewById(R.id.Year);
	    	cutYear=(TextView)findViewById(R.id.cutYear);
	    	addMonth=(TextView)findViewById(R.id.addMonth);
	    	month=(TextView)findViewById(R.id.Month);
	    	cutMonth=(TextView)findViewById(R.id.cutMonth);
	    	PayseachMessage=(EditText)findViewById(R.id.PayseachMessage);
	    	showdatatitle=(RelativeLayout)findViewById(R.id.Title);
	    	countupdown.setOnClickListener(this);
	    	timeupdown.setOnClickListener(this);
	    	addYear.setOnClickListener(this);
	    	cutYear.setOnClickListener(this);
	    	addMonth.setOnClickListener(this);
	    	cutMonth.setOnClickListener(this);
	    	Payseach.setOnClickListener(this);
	    	ToBottom.setOnClickListener(this);
	    	returnTop.setOnClickListener(this);
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
	    	paylist=new ArrayList<Pay>();
	    	paysqlite = new PaySQLite(this,"pay.db",null,1);
			db=paysqlite.getReadableDatabase();
			gudingxijie=new GuDingXiSqlite(this, "gudingcountxj.db", null, 1);
			db1=gudingxijie.getReadableDatabase();
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
			
			PayList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					if(PayList.getCount()!=position){
					Intent intent=new Intent();
					intent.putExtra("id", String.valueOf(paylist.get(position-1).getId()));
					intent.putExtra("number", number);
					intent.setClass(MyPay.this, IncomePayLock.class);
					startActivity(intent);
					}}
			});
			PayList.setonRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					task=new MyTask();
					task.execute();
				}
				
				
			});
			AddEditTextChangeListener();
			  ListViewLongClick();
	    }
	 @Override
		public void onClick(View view) {
			switch(view.getId()){
			case R.id.timeupdown:
				if(count1==0){
					count1=1;
					timeupdown.setImageResource(R.drawable.direction1);
					TimeUp();
				}else if(count1==1){
					count1=0;
					timeupdown.setImageResource(R.drawable.direction);
					TimeDown();
				}
				
				break;
			case R.id.countupdown:
				if(count2==0){
					count2=1;
					countupdown.setImageResource(R.drawable.direction1);
					CountUp();
				}else if(count2==1){
					count2=0;
					countupdown.setImageResource(R.drawable.direction);
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
				if(count<9){
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
			case R.id.Payseach:
				TimeUp();
				break;
			case R.id.ToBottom:
				PayList.setSelection(paylist.size()-1);
				ToBottom.setVisibility(View.GONE);
				returnTop.setVisibility(View.VISIBLE);
				break;
			case R.id.returnTop:
				PayList.setSelection(0);
				ToBottom.setVisibility(View.VISIBLE);
				returnTop.setVisibility(View.GONE);
				break;
			}
			}
	 public void TimeUp(){
			baozhaungMenthod();
			String time=year.getText().toString()+"-"+month.getText().toString();
	    	Cursor cursor=paysqlite.queryPayTimeUp(db,time);
	    	payCount=paysqlite.TotalCount(db, time);
	    	tool(cursor);
	    	  cursor.close();
		}
	 public void TimeUp1(){
			baozhaungMenthod();
			String time=year.getText().toString()+"-"+month.getText().toString()+"-"+day;
	    	Cursor cursor=paysqlite.queryPayTimeUp(db,time);
	    	payCount=paysqlite.TotalCount(db, time);
	    	tool(cursor);
	    	  cursor.close();
		}
		public void TimeDown(){
			baozhaungMenthod();
			String time=year.getText().toString()+"-"+month.getText().toString();
	    	Cursor cursor=paysqlite.queryPayTimeDown(db,time);
	    	tool(cursor);
	    	  cursor.close();
		}
		public void CountUp(){
			baozhaungMenthod();
			String time=year.getText().toString()+"-"+month.getText().toString();
	    	Cursor cursor=paysqlite.queryPayCountUp(db,time);
	    	tool(cursor);
	    	cursor.close();
		}
		public void CountDown(){
			baozhaungMenthod();
			String time=year.getText().toString()+"-"+month.getText().toString();
			Cursor cursor=paysqlite.queryPayCountDown(db,time);
	    	tool(cursor);
	    	cursor.close();
		}
		
		public void tool(Cursor cursor){
			 List<Pay> myPay=new ArrayList<Pay>();;
		    	while(cursor.moveToNext()){
		    		Pay pay=new Pay();
		    		pay.setCount(Long.valueOf(cursor.getString(cursor.getColumnIndex("count"))));
		    		pay.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		    		pay.setPayTo(cursor.getString(cursor.getColumnIndex("PayTo")));
		    		pay.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		    		pay.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		    		pay.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		    		pay.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		    		pay.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		    		myPay.add(pay);
		    	}
		    	adapter=new PayListAdapter(this, myPay);
				PayList.setAdapter(adapter);
				paylist=myPay;
				if(paylist.size()==0){
			    	shownodata.setVisibility(View.VISIBLE);
			    	showdatatitle.setVisibility(View.GONE);
			    	
			    }else{
			    	shownodata.setVisibility(View.GONE);
			    	showdatatitle.setVisibility(View.VISIBLE);
			    }
				 if(PayList.getFooterViewsCount()!=0){
			    		if(paylist.size()==0){
			    			PayList.removeFooterView(bottomView);
			    		}else{
			    			payCountText.setText(String.valueOf(payCount));
			    		}	
			    	}else{
			    		if(paylist.size()!=0){
			    			AddFootView();
			    		}	
			    		
			    	}
		}
		//下拉刷新
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
	 	    	 adapter.notifyDataSetChanged();
	 	    	PayList.onRefreshComplete();
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
			if(!(PayseachMessage.getText().toString()).equals("")){
			if((Integer.parseInt(PayseachMessage.getText().toString())<10)&&(Integer.parseInt(PayseachMessage.getText().toString())>0)){
				day="0"+PayseachMessage.getText().toString();
			}
			else if((Integer.parseInt(PayseachMessage.getText().toString())>=10)&&(Integer.parseInt(PayseachMessage.getText().toString())<=31)){
				day=PayseachMessage.getText().toString();
			}
			else{
				Toast.makeText(MyPay.this, "输入数据不合法!", 1000).show();
				PayseachMessage.setText("");
			}
			}
		}
		public void AddFootView(){
			bottomView = (LinearLayout) inflater.inflate(R.layout.incomelistbottomitem, null);
		    payCountText=(TextView)bottomView.findViewById(R.id.incomeCount);
		  	payCountText.setText(String.valueOf(payCount));
		  	PayList.addFooterView(bottomView);
		}
		public void AddEditTextChangeListener(){
			PayseachMessage.addTextChangedListener(new TextWatcher() {
				
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
			PayList.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						final int position, long id) {
					GuDingMingXiLists=new ArrayList<GuDingMingXi>();
					GuDingMingXiLists=gudingxijie.QueryDataByProjectname(db1, paylist.get(position-1).getPayTo());
					if(paylist.get(position-1).getStatus()==0 || GuDingMingXiLists.size()!=0){
					View view1=LayoutInflater.from(MyPay.this).inflate(R.layout.deletepopupwindowitem, null);
					int windowPos[] = DeletePopupWindow.calculatePopWindowPos(PayList.getChildAt(position), view1);
					int xOff =-30;// 可以自己调整偏移
					windowPos[0] -= xOff;
					popupwindow = new PopupWindow(view1, 200, LayoutParams.WRAP_CONTENT);
					popupwindow.showAtLocation(view1, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
					LinearLayout XiJieDelLayout=(LinearLayout)view1.findViewById(R.id.XiJieDelLayout);
					LinearLayout XJMingXiLayout=(LinearLayout)view1.findViewById(R.id.XJMingXiLayout);
					if(paylist.get(position-1).getStatus()!=0){
						XiJieDelLayout.setVisibility(View.GONE);
					}
					if(GuDingMingXiLists.size()==0){
						XJMingXiLayout.setVisibility(View.GONE);
					}
					XiJieDelLayout.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							AddDeleteDialog(paylist.get(position-1).getId());
							popupwindow.dismiss();
						}
					});
					
					XJMingXiLayout.setOnClickListener(new OnClickListener() {
						
						
						public void onClick(View arg0) {
							View view=LayoutInflater.from(MyPay.this).inflate(R.layout.xijiedailog, null);
							TextView XiJiePName=(TextView)view.findViewById(R.id.XiJiePName);
							ListView XiJieListView=(ListView)view.findViewById(R.id.XiJieListView);
							XiJiePName.setText(paylist.get(position-1).getPayTo()+"明细");
							XiJieAdapter adapter=new XiJieAdapter(MyPay.this, GuDingMingXiLists);
							XiJieListView.setAdapter(adapter);
							JSBPDialog dialog=new JSBPDialog(MyPay.this,view);
							dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
							dialog.setCancelable(true);
							dialog.show();
							popupwindow.dismiss();
						}
					});

					XiJieDelLayout.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							AddDeleteDialog(paylist.get(position-1).getId());
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
					paysqlite.Delete(db,String.valueOf(id));
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
}