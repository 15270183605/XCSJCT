package shouye;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sqlite.IncomeSQLite;
import sqlite.MenuClassSQLite;
import sqlite.PaySQLite;
import sqlite.UserSQLite;
import Adapters.IncomeListAdapter;
import Adapters.PayListAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.Income;
import entity.Pay;

public class AddIncomeorPay extends Activity implements OnClickListener{
      private RelativeLayout incomeSourceTitle;
      private TextView textTitle,incomemakemenu2,incomeclass,incomemaketime,incomeId,incomesource;
      private Button save,exit;
      private CheckBox AddCheckBox;
      private int number;
      private ImageView MakeTime,Makemenupeople,incomeimage2,incomepayLuYin;
      private ListView classlocationlistview,AddDataListView;
      private EditText makemenuperson,incomeamount,incomemakenote;
      private UserSQLite usersqlite;
      private MenuClassSQLite menuclasssqlite;
      private IncomeSQLite incomesqlite;
      private PaySQLite paysqlite;
      private SQLiteDatabase db,db1,db2,db3,db4;
      private int status=0;
      private List<Income> incomelist;
      private List<Pay> paylist;
  	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.addincomeorpay); 
    	init();
    	
    }
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case  R.id.Save:
			saveIncomePay();
			if(AddCheckBox.isChecked()){
				recover();
			}else{
				AddIncomeorPay.this.finish();
			}
			break;
		case R.id.Exit:
			this.finish();
			break;
		case R.id.Makemenupeople:
			Cursor cursor=usersqlite.queryUser(db,"");
			selectClass(db, makemenuperson,cursor,"userName");
			 cursor.close();
			break;
		case R.id.incomeimage2:
			Cursor cursor2=menuclasssqlite.QueryMenucalss(db1, incomeclass.getText().toString());
			selectClass(db1, incomesource,cursor2,"MenuUsefulName");
			 cursor2.close();
			break;
		
		case R.id.MakeTime:
			Calendar calendar=Calendar.getInstance();
			int year=calendar.get(Calendar.YEAR);
			int month=calendar.get(Calendar.MONTH);
			int dayofmonth=calendar.get(Calendar.DAY_OF_MONTH);
			final DatePickerDialog dialog=new DatePickerDialog(this, R.style.DateTime,new OnDateSetListener() {
				public void onDateSet(DatePicker view, int year, int month, int dayofmonth) {
					if(month<9&&dayofmonth<10){
						Toast.makeText(AddIncomeorPay.this, year+"-"+"0"+(month+1)+"-"+"0"+dayofmonth, 1000).show();
						incomemaketime.setText(year+"-"+"0"+(month+1)+"-"+"0"+dayofmonth);
					}
					else if(month<9){
						Toast.makeText(AddIncomeorPay.this, year+"-"+"0"+(month+1)+"-"+dayofmonth, 1000).show();
						incomemaketime.setText( year+"-"+"0"+(month+1)+"-"+dayofmonth);
					}
					else if(dayofmonth<10){
						Toast.makeText(AddIncomeorPay.this, year+"-"+(month+1)+"-"+"0"+dayofmonth, 1000).show();
						incomemaketime.setText(year+"-"+(month+1)+"-"+"0"+dayofmonth);
					}
					else{
						Toast.makeText(AddIncomeorPay.this, year+"-"+(month+1)+"-"+dayofmonth, 1000).show();
						incomemaketime.setText(year+"-"+(month+1)+"-"+dayofmonth);
					}
				}
			},year,month,dayofmonth);
			dialog.show();
			break;
		case R.id.incomepayLuYin:
			startRecognizerActivity();
			break;
		}
		
	}
	public void init(){
		Intent intent=getIntent();
		number=intent.getIntExtra("number", 0);
		
		incomeSourceTitle=(RelativeLayout)findViewById(R.id.incomeSourceTitle);
    	textTitle=(TextView)findViewById(R.id.textTitle);
    	incomemakemenu2=(TextView)findViewById(R.id.incomemakemenu2);
    	MakeTime=(ImageView)findViewById(R.id.MakeTime);
    	Makemenupeople=(ImageView)findViewById(R.id.Makemenupeople);
    	incomeimage2=(ImageView)findViewById(R.id.incomeimage2);
    	incomepayLuYin=(ImageView)findViewById(R.id.incomepayLuYin);
    	incomesource=(TextView)findViewById(R.id.incomesource);
    	incomeclass=(TextView)findViewById(R.id.incomeclass);
    	makemenuperson=(EditText)findViewById(R.id.makemenuperson);
    	incomemaketime=(TextView)findViewById(R.id.incomemaketime);
    	incomeamount=(EditText)findViewById(R.id.incomeamount);
    	incomemakenote=(EditText)findViewById(R.id.incomemakenote);
    	incomeId=(TextView)findViewById(R.id.incomeId);
		save=(Button)findViewById(R.id.Save);
		exit=(Button)findViewById(R.id.Exit);
		AddCheckBox=(CheckBox)findViewById(R.id.AddCheckBox);
		AddDataListView=(ListView)findViewById(R.id.AddDataListView);
		long time=System.currentTimeMillis();
    	Date date=new Date(time);
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    	incomemaketime.setText(sdf.format(date));
		save.setOnClickListener(this);
		exit.setOnClickListener(this);
		MakeTime.setOnClickListener(this);
		Makemenupeople.setOnClickListener(this);
		incomeimage2.setOnClickListener(this);
		usersqlite = new UserSQLite(this,"FamilyFinance.db",null,1);
		db=usersqlite.getReadableDatabase();
		menuclasssqlite = new MenuClassSQLite(this,"menuclass.db",null,1);
		db1=menuclasssqlite.getReadableDatabase();
		incomesqlite = new IncomeSQLite(this,"income.db",null,1);
		db3=incomesqlite.getReadableDatabase();
		paysqlite = new PaySQLite(this,"pay.db",null,1);
		db4=paysqlite.getReadableDatabase();
		incomelist=new ArrayList<Income>();
		paylist=new ArrayList<Pay>();
		
		if(number==1){
			long count =incomesqlite.TotalCount1(db3);
			incomeId.setText(String.valueOf(count+1));
			incomeclass.setText("收入单");
			textTitle.setText("收入录入");
    		incomemakemenu2.setText("来源:");
		}
    	if(number==2){
    		long count=paysqlite.TotalCount1(db4);
    		
    		incomeId.setText(String.valueOf(count+1));
    		
    		incomeclass.setText("支出单");
    		textTitle.setText("支出录入");
    		incomemakemenu2.setText("出处:");
    		
    	}
    	PackageManager pm = getPackageManager();
		List activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		// 本地识别程序 // new Intent(RecognizerIntent.ACTION_WEB_SEARCH), 0);
		// 网络识别程序
		/*
		 *  * 此处没有使用捕捉异常，而是检测是否有语音识别程序。 *
		 * 也可以在startRecognizerActivity()方法中捕捉ActivityNotFoundException异常
		 */
		if (activities.size() != 0) {
			incomepayLuYin.setOnClickListener(this);
		} else { // 若检测不到语音识别程序在本机安装，测将扭铵置灰
			incomepayLuYin.setEnabled(false);
		}
	}
	public void selectClass(SQLiteDatabase db,final TextView text,Cursor cursor,String str){
		final List<String> userlist=new ArrayList<String>();
		
		while(cursor.moveToNext()){
		   userlist.add(cursor.getString(cursor.getColumnIndex(str)));
		   //Toast.makeText(AddIncomeorPay.this, cursor.getString(cursor.getColumnIndex("userName")), 1000).show();
		}
		LayoutInflater inflater = (LayoutInflater)AddIncomeorPay.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		       View view = inflater.inflate(R.layout.classlocation, null);
		       classlocationlistview=(ListView)view.findViewById(R.id.classlistview);
		      ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,userlist);
		      classlocationlistview.setAdapter(adapter);
		    final AlertDialog alert = new AlertDialog.Builder(
				AddIncomeorPay.this).create();
		       alert.setView(view);
		       alert.show();
				//设置对话框大小
				WindowManager.LayoutParams params=alert.getWindow().getAttributes();
				params.width=300;
				params.gravity=Gravity.RIGHT|Gravity.CENTER_HORIZONTAL;
				params.y=150;
				alert.getWindow().setAttributes(params);
				classlocationlistview.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
						text.setText(userlist.get(position));
						alert.dismiss();
						
					}
				});
	}
	public void saveIncomePay(){
		Long count=null;
		Long count1=null;
		String menuclass=incomeclass.getText().toString();
		Double amount=Double.valueOf(incomeamount.getText().toString());
		String source=incomesource.getText().toString();
		String person=makemenuperson.getText().toString();
		String time=incomemaketime.getText().toString();
		String note=incomemakenote.getText().toString();
		String id=incomeId.getText().toString();
		count=incomesqlite.IdCount(db3, id);
		count1=paysqlite.IdCount(db4, id);
		if(number==1){
			if(count==1){
				Toast.makeText(AddIncomeorPay.this, "单据编号已存在", 1000).show();
			}
			else{
		incomesqlite.addIncome(db3,id,menuclass, amount, source, person, time, String.valueOf(status), note); 
		Income income=new Income(Integer.parseInt(id), menuclass, amount, source, person, time, note, status);
		incomelist.add(income);
		IncomeListAdapter adapter=new IncomeListAdapter(this, incomelist);
		AddDataListView.setAdapter(adapter);
		Toast.makeText(AddIncomeorPay.this, menuclass+"保存成功", 1000).show();
			
			}}
		if(number==2){
			if(count1==1){
				Toast.makeText(AddIncomeorPay.this, "单据编号已存在", 1000).show();
			}else{
			paysqlite.addPay(db4,id, menuclass, amount, source, person, time, String.valueOf(status), note); 
			Pay pay=new Pay(Integer.parseInt(id), menuclass, amount, source, person, time, note, status);
			paylist.add(pay);
		    PayListAdapter adapter=new PayListAdapter(this, paylist);
			AddDataListView.setAdapter(adapter);
			Toast.makeText(AddIncomeorPay.this, menuclass+"保存成功", 1000).show();
			}}
		
		}
	//给ListView添加监听事件
	public void OnItemClick(){
		AddDataListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent=new Intent();
				if(number==1){
					intent.putExtra("id", String.valueOf(incomelist.get(position).getId()));	
				}else{
					intent.putExtra("id", String.valueOf(paylist.get(position).getId()));
				}
				intent.putExtra("number", number);
				intent.setClass(AddIncomeorPay.this, IncomePayLock.class);
				startActivity(intent);
				
			}
		});
	}
	public void recover(){
		incomeId.setText(String.valueOf(Integer.parseInt(incomeId.getText().toString())+1));
		incomeamount.setText("");
		incomesource.setText("");
		makemenuperson.setText("");
		incomemaketime.setText("");
		incomemakenote.setText("");
	}
	// 开始识别
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
				if(msg.equals("退出")){
					AddIncomeorPay.this.finish();
				}if(msg.equals("保存")){
					//saveIncomePay();
				}else{
				check(msg);}
				Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show();
			} // 语音识别后的回调，将识别的字串以Toast显示
			super.onActivityResult(requestCode, resultCode, data);
		}
		//处理语音内容；
		public void check(String msg){
			int num1=0;
			String str=msg.replaceAll("[\\p{Punct}\\p{Space}]+", "");//去除标点符号的正则表达式
			for(int i=0;i<str.length();i++){
				String a=str.substring(i, i+1);
				if(a.equals("金")){
					num1=i;
				}
				}
		
			
			
			incomesource.setText(str.substring(0,num1));
			String msg1=str.substring(num1+2, str.length());
			incomeamount.setText(TransformDouble(msg1));
}
		//处理语音内容
		public String TransformDouble(String msg){
			
			String number=null;
		if(msg.equals("十")){
			number=String.valueOf(10);
			
		}if(msg.equals("一百")){
			number=String.valueOf(100);
		}if(msg.equals("一千")){
			number=String.valueOf(1000);
		}
		if(msg.substring(msg.length()-1, msg.length()).equals("万")){
		if(msg.equals("一万")){
			number=String.valueOf(10000);
		}
		else{
			number=msg.substring(0, msg.length()-1)+"0000";
			}
		}
		else{
			number=msg;
		}
		return number;
		}

}