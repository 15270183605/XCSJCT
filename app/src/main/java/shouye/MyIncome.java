package shouye;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sqlite.GuDingXiSqlite;
import sqlite.IncomeSQLite;
import tool.MyListView;
import tool.MyListView.OnRefreshListener;
import Adapters.IncomeListAdapter;
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
import entity.Income;

public class MyIncome extends Activity implements OnClickListener{
	private MyListView IncomeList;
	private IncomeSQLite incomesqlite;
	private List<Income> incomelist;
	private LinearLayout shownodata;
	private ImageView timeupdown,countupdown,SeachLuYin,Incomeseach,ToBottom,returnTop;
	private SQLiteDatabase db,db1;
	private IncomeListAdapter adapter;
	private int number,count1,count2;//�����仯����־
	private MyTask task;
	private TextView addYear,year,cutYear,addMonth,month,cutMonth,IncomeCountText;
	private EditText IncomeseachMessage;
	private int count;//�·ݼӼ���
	private double IncomeCounts;
	private String day="";
	private LinearLayout bottomView;
	private RelativeLayout showdatatitle;
	 private LayoutInflater inflater;
	 private PopupWindow popupwindow;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private GuDingXiSqlite gudingxijie;
	private List<GuDingMingXi> GuDingMingXiLists;
	 protected void onCreate(Bundle savedInstanceState) {
	    	// TODO Auto-generated method stub
	    	super.onCreate(savedInstanceState); 
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	setContentView(R.layout.myincome);
	    	Intent intent=getIntent();
			number=intent.getIntExtra("number", 0);
	    	
	    	IncomeList=(MyListView)findViewById(R.id.IncomeList);
	    	timeupdown=(ImageView)findViewById(R.id.timeupdown);
	    	countupdown=(ImageView)findViewById(R.id.countupdown);
	    	SeachLuYin=(ImageView)findViewById(R.id.SeachLuYin);
	    	Incomeseach=(ImageView)findViewById(R.id.Incomeseach);
	    	ToBottom=(ImageView)findViewById(R.id.ToBottom);
	    	returnTop=(ImageView)findViewById(R.id.returnTop);
	    	shownodata=(LinearLayout)findViewById(R.id.datanodata);
	    	addYear=(TextView)findViewById(R.id.addYear);
	    	year=(TextView)findViewById(R.id.Year);
	    	cutYear=(TextView)findViewById(R.id.cutYear);
	    	addMonth=(TextView)findViewById(R.id.addMonth);
	    	month=(TextView)findViewById(R.id.Month);
	    	cutMonth=(TextView)findViewById(R.id.cutMonth);
	    	showdatatitle=(RelativeLayout)findViewById(R.id.Title);
	    	IncomeseachMessage=(EditText)findViewById(R.id.IncomeseachMessage);
	    	countupdown.setOnClickListener(this);
	    	timeupdown.setOnClickListener(this);
	    	addYear.setOnClickListener(this);
	    	cutYear.setOnClickListener(this);
	    	addMonth.setOnClickListener(this);
	    	cutMonth.setOnClickListener(this);
	    	Incomeseach.setOnClickListener(this);
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
	    	incomelist=new ArrayList<Income>();
	    	incomesqlite = new IncomeSQLite(this,"income.db",null,1);
			db=incomesqlite.getReadableDatabase();
			gudingxijie=new GuDingXiSqlite(this, "gudingcountxj.db", null, 1);
			db1=gudingxijie.getReadableDatabase();
			AddFootView();
			TimeUp();
			PackageManager pm = getPackageManager();
			List activities = pm.queryIntentActivities(new Intent(
					RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
			if (activities.size() != 0) {
				SeachLuYin.setOnClickListener(this);
			} else { // ����ⲻ������ʶ������ڱ�����װ����Ť��ò����c��
				SeachLuYin.setEnabled(false);
			}
			
			IncomeList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					if(position!=IncomeList.getCount()-1){
					Intent intent=new Intent();
					intent.putExtra("id", String.valueOf(incomelist.get(position-1).getId()));
					intent.putExtra("number", number);
					intent.setClass(MyIncome.this, IncomePayLock.class);
					startActivity(intent);
					
				}}
			});
			
			//����ˢ�·�������
			IncomeList.setonRefreshListener(new OnRefreshListener() {

				@Override
				public void onRefresh() {
					task=new MyTask();
					task.execute();
				}
				
				
			});
			IncomeseachMessageChangeLister();
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
			 adapter.notifyDataSetChanged();
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
			 adapter.notifyDataSetChanged();
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
		case R.id.Incomeseach:
			TimeUp();
			break;
		case R.id.ToBottom:
			IncomeList.setSelection(incomelist.size()-1);
			ToBottom.setVisibility(View.GONE);
			returnTop.setVisibility(View.VISIBLE);
			break;
		case R.id.returnTop:
			IncomeList.setSelection(0);
			ToBottom.setVisibility(View.VISIBLE);
			returnTop.setVisibility(View.GONE);
			break;
		}
		
	}
	//��ʱ������
	public void TimeUp(){
		baozhaungMenthod();
		String time=year.getText().toString()+"-"+month.getText().toString();
    	Cursor cursor=incomesqlite.queryIncomeTimeUp(db,time);
    	IncomeCounts=incomesqlite.TotalCount(db, time);
    	tool(cursor);
    	cursor.close();
	}
	//��ʱ������
	public void TimeUp1(){
		baozhaungMenthod();
		String time=year.getText().toString()+"-"+month.getText().toString()+"-"+day;
    	Cursor cursor=incomesqlite.queryIncomeTimeUp(db,time);
    	IncomeCounts=incomesqlite.TotalCount(db, time);
    	tool(cursor);
    	cursor.close();
	}
	//��ʱ�併��
	public void TimeDown(){
		baozhaungMenthod();
		String time=year.getText().toString()+"-"+month.getText().toString();
	Cursor cursor=incomesqlite.queryIncomeTimeDown(db,time);
	tool(cursor);
	 cursor.close();
	}
	//���������
	public void CountUp(){
		baozhaungMenthod();
		String time=year.getText().toString()+"-"+month.getText().toString();
		Cursor cursor=incomesqlite.queryIncomeCountUp(db,time);
		tool(cursor);
		 cursor.close();
	}
	//������
	public void CountDown(){
		baozhaungMenthod();
		String time=year.getText().toString()+"-"+month.getText().toString();
		Cursor cursor=incomesqlite.queryIncomeCountDown(db,time);
		tool(cursor);
		 cursor.close();
	}
	//�������ݵĹ���
	public void tool(Cursor cursor){
	 List<Income> Incomes=new ArrayList<Income>();
    	while(cursor.moveToNext()){
    		Income income=new Income();
    		income.setCount(Long.valueOf(cursor.getString(cursor.getColumnIndex("count"))));
    		income.setDate(cursor.getString(cursor.getColumnIndex("Date")));
    		income.setIncomeSource(cursor.getString(cursor.getColumnIndex("IncomeSource")));
    		income.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
    		income.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
    		income.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
    		income.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
    		income.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
    		Incomes.add(income);
    	}
    	    adapter=new IncomeListAdapter(this, Incomes);
			IncomeList.setAdapter(adapter);
		    incomelist=Incomes;
		    if(incomelist.size()==0){
		    	shownodata.setVisibility(View.VISIBLE);
		    	showdatatitle.setVisibility(View.GONE);
		    	
		    }else{
		    	shownodata.setVisibility(View.GONE);
		    	showdatatitle.setVisibility(View.VISIBLE);
		    }
		    if(IncomeList.getFooterViewsCount()!=0){
	    		if(incomelist.size()==0){
	    			IncomeList.removeFooterView(bottomView);
	    		}else{
	    			IncomeCountText.setText(String.valueOf(IncomeCounts));
	    		}	
	    	}else{
	    		if(incomelist.size()!=0){
	    			AddFootView();
	    		}	
	    	}
			
	}
	//����ˢ��
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
 	         IncomeList.onRefreshComplete();
 	     }
}
	private void startRecognizerActivity() { // ͨ��Intent��������ʶ���ģʽ����������
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // ����ģʽ������ģʽ������ʶ��
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); // ��ʾ������ʼ
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "��ʼ����"); // ��ʼ����ʶ��
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE); // ����ʶ�����
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { // �ص���ȡ�ӹȸ�õ�������
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
				&& resultCode == RESULT_OK) { // ȡ���������ַ�
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
		} // ����ʶ���Ļص�����ʶ����ִ���Toast��ʾ
		super.onActivityResult(requestCode, resultCode, data);
	}
	public String getMonth(String msg){
		String str="";
		int month=0;
		int num=0;
		for(int i=0;i<msg.length();i++){
			if(msg.substring(i, i+1).equals("��")){
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
	if(!(IncomeseachMessage.getText().toString()).equals("")){
	if((Integer.parseInt(IncomeseachMessage.getText().toString())<10)&&(Integer.parseInt(IncomeseachMessage.getText().toString())>0)){
		day="0"+IncomeseachMessage.getText().toString();
	}
	else if((Integer.parseInt(IncomeseachMessage.getText().toString())>=10)&&(Integer.parseInt(IncomeseachMessage.getText().toString())<=31)){
		day=IncomeseachMessage.getText().toString();
	}
	else{
		Toast.makeText(MyIncome.this, "�������ݲ��Ϸ�!", 1000).show();
		IncomeseachMessage.setText("");
	}
	}
}
public void AddFootView(){
	bottomView = (LinearLayout) inflater.inflate(R.layout.incomelistbottomitem, null);
    IncomeCountText=(TextView)bottomView.findViewById(R.id.incomeCount);
  	IncomeCountText.setText(String.valueOf(IncomeCounts));
  	IncomeList.addFooterView(bottomView);
}
public void IncomeseachMessageChangeLister(){
	IncomeseachMessage.addTextChangedListener(new TextWatcher() {
		
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
//��listview�ӳ�ʱ�����¼�
public void ListViewLongClick(){
	IncomeList.setOnItemLongClickListener(new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				final int position, long id) {
			GuDingMingXiLists=new ArrayList<GuDingMingXi>();
			GuDingMingXiLists=gudingxijie.QueryDataByProjectname(db1, incomelist.get(position-1).getIncomeSource());
			if(incomelist.get(position-1).getStatus()==0 || GuDingMingXiLists.size()!=0){
			View view1=LayoutInflater.from(MyIncome.this).inflate(R.layout.deletepopupwindowitem, null);
			int windowPos[] = DeletePopupWindow.calculatePopWindowPos(IncomeList.getChildAt(position), view1);
			int xOff =-30;// �����Լ�����ƫ��
			windowPos[0] -= xOff;
			popupwindow = new PopupWindow(view1, 200, LayoutParams.WRAP_CONTENT);
			popupwindow.showAtLocation(view1, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
			LinearLayout XiJieDelLayout=(LinearLayout)view1.findViewById(R.id.XiJieDelLayout);
			LinearLayout XJMingXiLayout=(LinearLayout)view1.findViewById(R.id.XJMingXiLayout);
			if(incomelist.get(position-1).getStatus()!=0){
				XiJieDelLayout.setVisibility(View.GONE);
			}
			if(GuDingMingXiLists.size()==0){
				XJMingXiLayout.setVisibility(View.GONE);
			}
			XiJieDelLayout.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					AddDeleteDialog(incomelist.get(position-1).getId());
					popupwindow.dismiss();
				}
			});
			
			XJMingXiLayout.setOnClickListener(new OnClickListener() {
				
				
				public void onClick(View arg0) {
					View view=LayoutInflater.from(MyIncome.this).inflate(R.layout.xijiedailog, null);
					TextView XiJiePName=(TextView)view.findViewById(R.id.XiJiePName);
					ListView XiJieListView=(ListView)view.findViewById(R.id.XiJieListView);
					XiJiePName.setText(incomelist.get(position-1).getIncomeSource()+"��ϸ");
					XiJieAdapter adapter=new XiJieAdapter(MyIncome.this, GuDingMingXiLists);
					XiJieListView.setAdapter(adapter);
					JSBPDialog dialog=new JSBPDialog(MyIncome.this,view);
					dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
					dialog.setCancelable(true);
					dialog.show();
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
			Toast.makeText(MyIncome.this, String.valueOf(id), 1000).show();
			incomesqlite.Delete(db,String.valueOf(id));
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
