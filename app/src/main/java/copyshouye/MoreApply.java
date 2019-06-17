package copyshouye;

import shouye.AddIncomeorPay;
import shouye.Count;
import shouye.DataHorizontal;
import shouye.GraphContainer;
import shouye.GuDingCount;
import shouye.Help;
import shouye.ManageNote;
import shouye.MyIncome;
import shouye.MyPay;
import shouye.SystemSet;
import shouye.YingShouYingFu;
import shouye.YingShouYingFuList;
import sqlite.SetTypeSQLite;
import tool.HorizontalListView;
import userrefreedback.UserRefreedBack;
import Adapters.GridViewAdapter;
import Adapters.MoreApplyListViewAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

public class MoreApply extends Activity implements OnClickListener{
	private ImageView  Data, graph,BackShouYe;
	//addIncome, addPay, MyIncome, MyPay, yingshou, yingfu,Count,
	private HorizontalListView MoreApplyListView;
	private GridView MoreApplyGridView,IncomePayApplyGridView,OtherApplyGridView;
	private RadioGroup addorquery;
	private RadioButton add, query;
	private String Text1[]={"��Ӧ��������","�������ݶ�̬","�������趨","�Լ������ճ�","������Ҫ����","�����������","������������"};
		private String Text2[]={"����","ͼ��","����","��ǩ","����","����","����"};
	private int images[]={R.drawable.copydata,R.drawable.graph2,R.drawable.guding,R.drawable.time1,R.drawable.set1,R.drawable.help,R.drawable.feedback};
	private int OtherImages[]={R.drawable.income,R.drawable.pay,R.drawable.income2,R.drawable.pay1,R.drawable.yingshou,R.drawable.yingfu,R.drawable.count};
	private String Otherstr[]={"��������","����֧��","�ҵ�����","�ҵ�֧��","������","�������","�������"};
	private int IncomePayImages[]={R.drawable.income,R.drawable.pay,R.drawable.income2,R.drawable.pay1};
	private String IncomePaystr[]={"��������","����֧��","�ҵ�����","�ҵ�֧��"};
	private int OtherApplyImages[]={R.drawable.time1,R.drawable.set1,R.drawable.help,R.drawable.feedback};
	private String OtherApplystr[]={"�����ǩ","ϵͳ����","ϵͳ����","�û�����"};
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.moreapply);
	init();
	
}
@Override
public void onClick(View view) {
	switch(view.getId()){
	case R.id.Data:
		Intent intent7 = new Intent(this, DataHorizontal.class);
		startActivity(intent7);
		break;
	case R.id.graph:
		//setGraphSelctDialog();
		Intent intent8 = new Intent(this, GraphContainer.class);
		startActivity(intent8);
		break;
	case R.id.BackShouYe:
		this.finish();
		break;
	}
	
}
public void init(){
	MoreApplyListView=(HorizontalListView)findViewById(R.id.ApplyListView);
	Data = (ImageView) findViewById(R.id.Data);
	graph = (ImageView) findViewById(R.id.graph);
	BackShouYe= (ImageView) findViewById(R.id.BackShouYe);
	MoreApplyGridView=(GridView)findViewById(R.id.MoreApplyGridView);
	IncomePayApplyGridView=(GridView)findViewById(R.id.IncomePayApplyGridView);
	OtherApplyGridView=(GridView)findViewById(R.id.OtherApplyGridView);
	BackShouYe.setOnClickListener(this);
	GridViewAdapter OtherAdapter=new GridViewAdapter(this,OtherImages,Otherstr,0);
	MoreApplyGridView.setAdapter(OtherAdapter);
	GridViewAdapter IncomePayAdapter=new GridViewAdapter(this,IncomePayImages,IncomePaystr,0);
	IncomePayApplyGridView.setAdapter(IncomePayAdapter);
	GridViewAdapter OtherApplyAdapter=new GridViewAdapter(this,OtherApplyImages,OtherApplystr,0);
	OtherApplyGridView.setAdapter(OtherApplyAdapter);
	OtherGridViewClick();
	IncomePayGridViewClick();
	graph.setOnClickListener(this);
	Data.setOnClickListener(this);
	MoreApplyListViewAdapter adapter=new MoreApplyListViewAdapter(this, Text1, Text2, images);
	MoreApplyListView.setAdapter(adapter);
	MoreApplyListViewOnClick();
	OtherApplyGridViewClick();
}
public void setYingShouFuDialog(final int number) {
	AlertDialog.Builder builder = new AlertDialog.Builder(
			new ContextThemeWrapper(this, R.style.Alert));
	View view1 = LayoutInflater.from(this).inflate(R.layout.dialogitem, null);
	builder.setView(view1);
	final AlertDialog dialog = builder.show();
	LinearLayout Graphlayout = (LinearLayout) view1
			.findViewById(R.id.GraphLayout);
	LinearLayout CountLayout = (LinearLayout) view1
			.findViewById(R.id.CountLayout);
	addorquery = (RadioGroup) view1.findViewById(R.id.addorquery);
	add = (RadioButton) view1.findViewById(R.id.add);
	query = (RadioButton) view1.findViewById(R.id.query);
	Graphlayout.setVisibility(View.GONE);
	CountLayout.setVisibility(View.GONE);
	addorquery.setVisibility(View.VISIBLE);
	add.setOnClickListener(this);
	query.setOnClickListener(this);
	TextView TextTitle = (TextView) view1.findViewById(R.id.TextTitle);
	TextTitle.setText("��ѡ�����������ǲ�ѯ!");
	Button sure = (Button) view1.findViewById(R.id.sure);
	Button cancel = (Button) view1.findViewById(R.id.cancel);
	sure.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (add.isChecked()) {
				Intent intent5 = new Intent();
				intent5.putExtra("number", number);
				intent5.setClass(MoreApply.this, YingShouYingFu.class);
				startActivity(intent5);
			}
			if (query.isChecked()) {
				Intent intent6 = new Intent();
				intent6.putExtra("number", number);
				intent6.setClass(MoreApply.this, YingShouYingFuList.class);
				startActivity(intent6);
			}
			dialog.dismiss();
		}
	});
	cancel.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			dialog.dismiss();

		}
	});
}



/*public void setCountSelectDialog() {
	AlertDialog.Builder builder = new AlertDialog.Builder(
			new ContextThemeWrapper(this, R.style.Alert));
	View view = LayoutInflater.from(this).inflate(R.layout.dialogitem, null);
	builder.setView(view);
	final AlertDialog dialog = builder.show();
	LinearLayout Graphlayout = (LinearLayout) view
			.findViewById(R.id.GraphLayout);
	addorquery = (RadioGroup) view.findViewById(R.id.addorquery);
	LinearLayout CountLayout = (LinearLayout) view
			.findViewById(R.id.CountLayout);
	RadioGroup YesOrNo = (RadioGroup) view.findViewById(R.id.YesOrNo);
	final RadioButton Yes = (RadioButton) view.findViewById(R.id.Yes);
	final RadioButton No = (RadioButton) view.findViewById(R.id.No);
	Yes.setOnClickListener(this);
	No.setOnClickListener(this);
	Graphlayout.setVisibility(View.GONE);
	addorquery.setVisibility(View.GONE);
	CountLayout.setVisibility(View.VISIBLE);
	TextView TextTitle = (TextView) view.findViewById(R.id.TextTitle);
	TextTitle
			.setText("��ʾ���ڽ���֮ǰ�����뱣֤�������е��� ����������������δ�������������������ݣ������Ƿ���Ҫ������е���״̬?");
	Button sure = (Button) view.findViewById(R.id.sure);
	Button cancel = (Button) view.findViewById(R.id.cancel);
	sure.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (Yes.isChecked()) {
				Intent intent = new Intent(MoreApply.this, Data.class);
				startActivity(intent);
			}
			if (No.isChecked()) {
				Intent intent = new Intent(MoreApply.this, Count.class);
				startActivity(intent);
			}
			dialog.dismiss();
		}
	});
	cancel.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			dialog.dismiss();

		}
	});
}*/
public void MoreApplyListViewOnClick(){
	MoreApplyListView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			if(position==0){
				Intent intent = new Intent(MoreApply.this, DataHorizontal.class);
				startActivity(intent);	
			}
			if(position==1){
				Intent intent1 = new Intent(MoreApply.this, GraphContainer.class);
				startActivity(intent1);
			}
			if(position==2){
				  SetTypeSQLite  settypesqlite=new SetTypeSQLite(MoreApply.this,"settype.db", null, 1);;
				   SQLiteDatabase db1=settypesqlite.getReadableDatabase();
				Cursor cursor=settypesqlite.returnType(db1, "�̶�����(�磺����)�Զ��뵥");
				if(cursor.getCount()==0){
					settypesqlite.AddSet(db1, "�̶�����(�磺����)�Զ��뵥", 1);
					}else if(cursor.moveToFirst()){
						if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==1){
							Toast.makeText(MoreApply.this, "��Ǹ���̶�����δ�򿪣������������д򿪹̶�����!", 1000).show();
						}
						if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==2){
							Intent intent1 = new Intent(MoreApply.this, GuDingCount.class);
							startActivity(intent1);
						}
						
					}
					}
				
		
			
			if(position==3){
				Intent intent2 = new Intent(MoreApply.this, ManageNote.class);
				startActivity(intent2);
			}
            if(position==4){
            	Intent intent3 = new Intent();
				intent3.setClass(MoreApply.this, SystemSet.class);
				startActivity(intent3);
			}
			if(position==5){
				Intent intent4= new Intent(MoreApply.this, Help.class);
				startActivity(intent4);
			}
			if(position==6){
				Intent intent5= new Intent(MoreApply.this, UserRefreedBack.class);
				startActivity(intent5);
			}
		}
	});
}
public void OtherGridViewClick(){
	MoreApplyGridView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parnet, View view, int position,
				long id) {
			if(position==0){
				Intent intent1 = new Intent();
				intent1.putExtra("number", 1);
				intent1.setClass(MoreApply.this, AddIncomeorPay.class);
				startActivity(intent1);
			}
if(position==1){
	Intent intent2 = new Intent();
	intent2.putExtra("number", 2);
	intent2.setClass(MoreApply.this, AddIncomeorPay.class);
	startActivity(intent2);
			}
if(position==2){
	Intent intent3 = new Intent();
	intent3.putExtra("number", 3);
	intent3.setClass(MoreApply.this, MyIncome.class);
	startActivity(intent3);
}

if(position==3){
	Intent intent4 = new Intent();
	intent4.putExtra("number", 4);
	intent4.setClass(MoreApply.this, MyPay.class);
	startActivity(intent4);
}
if(position==4){
	setYingShouFuDialog(5);
}
if(position==5){
	setYingShouFuDialog(6);
}
if(position==6){
	Intent intent=new Intent(MoreApply.this,Count.class);
	startActivity(intent);
}		
		}
	});
}
public void IncomePayGridViewClick(){
	IncomePayApplyGridView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parnet, View view, int position,
				long id) {
			if(position==0){
				Intent intent1 = new Intent();
				intent1.putExtra("number", 1);
				intent1.setClass(MoreApply.this, AddIncomeorPay.class);
				startActivity(intent1);
			}
if(position==1){
	Intent intent2 = new Intent();
	intent2.putExtra("number", 2);
	intent2.setClass(MoreApply.this, AddIncomeorPay.class);
	startActivity(intent2);
			}
if(position==2){
	Intent intent3 = new Intent();
	intent3.putExtra("number", 3);
	intent3.setClass(MoreApply.this, MyIncome.class);
	startActivity(intent3);
}

if(position==3){
	Intent intent4 = new Intent();
	intent4.putExtra("number", 4);
	intent4.setClass(MoreApply.this, MyPay.class);
	startActivity(intent4);
}
		
		}
	});}
public void OtherApplyGridViewClick(){
	OtherApplyGridView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parnet, View view, int position,
				long id) {
			if(position==0){
				Intent intent1 = new Intent();
				intent1.setClass(MoreApply.this, ManageNote.class);
				startActivity(intent1);
			}
if(position==1){
	Intent intent2 = new Intent();
	intent2.setClass(MoreApply.this, SystemSet.class);
	startActivity(intent2);
			}
if(position==2){
	Intent intent3 = new Intent();
	intent3.setClass(MoreApply.this, Help.class);
	startActivity(intent3);
}

if(position==3){
	Intent intent4 = new Intent();
	intent4.setClass(MoreApply.this, UserRefreedBack.class);
	startActivity(intent4);
}
		
		}
	});}
}
