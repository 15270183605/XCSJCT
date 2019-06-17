package shouye;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loginOrRegister.Main;
import sqlite.CountPredictSQLite;
import sqlite.CountSQLite;
import sqlite.SetTypeSQLite;
import sqlite.UserSQLite;
import tool.EnterAnimLayout;
import Adapters.CountDataListViewAdapter;
import Adapters.CountPredictAdater;
import Adapters.CountViewPagerAdapter;
import Adapters.SpinnerAdapter;
import Dialog.CountSelectDialog;
import Dialog.JieZhangDataDialog;
import PptAnim.Anim;
import PptAnim.AnimQieRu;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.CountEntity;
import entity.CountPredict;

public class Count extends Activity implements OnClickListener {
	private Spinner YearSpinner,MonthSpinner,SelectYearSpinner;
	private TextView MonthText, SelectSure, SelectCancel, Predict, YearText,
			CountSelectWay;
	private ImageView Select,CountBack;
	private ListView CountDataListView;
	private CountSQLite countSqlite;
	private UserSQLite usersqlite;
	private CountPredictSQLite predictsqlite;
	private SetTypeSQLite settypesqlite;
	private SQLiteDatabase db, db1, db2, db3;
	private Calendar cal;
	private String selectList[],selectList1[];
	private List<View> Views;
	private String spinnerData, spinnerData1,SpinnerTime;
	private Map<String,CountEntity> Datas;
	private int CurrentIndex, biaozhi, num, num1,num2;// 标志变量
	private CountViewPagerAdapter adapter;
	private CountSelectDialog dialog;
	private LinearLayout MonthSpinnerLayout,
			SelectTiaoJianLayout, SelectTiaoJianLayout1, PredictBottomLayout;
	private EditText YearSelectEdit, YearSelectEdit1, MonthSelectEdit,
			MonthSelectEdit1, ShouRuPredict, ZhiChuPredict;
	private String months[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
			"10", "11", "12" };
	private Animation animations[] = new Animation[2];
	private int jianyan;
	private int UserTime;// 用户注册日期
	private List<String> ZheXianDataTime,TimeToAdapter;
	private ListView PredictDataListView;
    private String DateTime;//当前日期
    //private String TimeToAdapter;//把Spinner获取的时间传给Adapter;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.count);
		init();

	}

	public void init() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		DateTime=format.format(date);
		cal = Calendar.getInstance();
		cal.setTime(date);
		spinnerData = String.valueOf(cal.get(Calendar.YEAR));
		if(cal.get(Calendar.MONTH)<10){
			SpinnerTime=cal.get(Calendar.YEAR)+"-0"+cal.get(Calendar.MONTH);
			spinnerData1="0"+cal.get(Calendar.MONTH);
		}else{
			SpinnerTime=cal.get(Calendar.YEAR)+"-"+cal.get(Calendar.MONTH);
			spinnerData1="0"+cal.get(Calendar.MONTH);
		}
		CountSelectWay = (TextView) findViewById(R.id.CountSelectWay);
		Predict = (TextView) findViewById(R.id.Predict);
		//CountViewFlipper = (ViewFlipper) findViewById(R.id.CountViewFlipper);
		Select = (ImageView) findViewById(R.id.Select);
		CountBack=(ImageView)findViewById(R.id.CountBack);
		YearSpinner = (Spinner)findViewById(R.id.YearSpinner);
		MonthSpinner=(Spinner)findViewById(R.id.CountMonthSpinner);
		MonthText = (TextView)findViewById(R.id.CountMonthText);
		YearText = (TextView)findViewById(R.id.CountYearText);
		CountDataListView=(ListView)findViewById(R.id.CountDataListView);
		//CountViewPager = (ViewPager)findViewById(R.id.CountViewPager);
		MonthSpinnerLayout = (LinearLayout)
				findViewById(R.id.MonthSpinnerLayout);
		
		Predict.setOnClickListener(this);
		CountSelectWay.setOnClickListener(this);
		Select.setOnClickListener(this);
		CountBack.setOnClickListener(this);
		countSqlite = new CountSQLite(this, "TotalCount.db", null, 1);
		db = countSqlite.getReadableDatabase();
		usersqlite = new UserSQLite(this, "FamilyFinance.db", null, 1);
		db1 = usersqlite.getReadableDatabase();
		predictsqlite = new CountPredictSQLite(this, "Predict.db", null, 1);
		db2 = predictsqlite.getReadableDatabase();
		settypesqlite = new SetTypeSQLite(this, "settype.db", null, 1);
		db3 = settypesqlite.getReadableDatabase();
		animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in);
		animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out);
		UserTime=Integer.parseInt((usersqlite.QueryUserTime(db1,
		Main.returnName(), Main.returnPsd())).substring(0,4));
		spinnerDatas(UserTime);
		spinnerMonthDatas(cal.get(Calendar.YEAR));
		TheMonthEndJieZhangDilog();
		AddListViewData();
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.CountBack:
			finish();
			break;
		case R.id.Select:
			SelectDialog();
			break;
		case R.id.CountSelectWay:
			if (jianyan == 0) {
				CountSelectWay.setText("年度");
				jianyan = 1;
				MonthSpinnerLayout.setVisibility(View.GONE);
			} else {
				CountSelectWay.setText("月度");
				jianyan = 0;
				MonthSpinnerLayout.setVisibility(View.VISIBLE);
			}
			AddListViewData();
			break;
		case R.id.SelectSure:
			DealDialogData();
			break;
		case R.id.SelectCancel:
			dialog.dismiss();
			break;
		case R.id.Predict:
			CountPredictDialog();
			break;
		case R.id.CanKaoPreData:
			AddPredictDatas();
			break;
		case R.id.CountPredictText:
			if(num2==1){
				predictsqlite.UpdateData(db2,Double.parseDouble(ShouRuPredict.getText().toString()), Double.parseDouble(ZhiChuPredict.getText().toString()), DateTime);
			}else{
			PredictData();}
			break;
		case R.id.CountPredictCancel:
			dialog.dismiss();
			break;
		case R.id.LookAllPredictData:
			Intent intent = new Intent(this, ShowPredictData.class);
			startActivity(intent);
			break;
		}
	}

	// Spinner的响应
	public void spinnerDatas(int i) {
		int num = 0;
		 selectList=new String[cal.get(Calendar.YEAR)-i+1]; 
		 for(int j=cal.get(Calendar.YEAR);j>=i;j--){
		 selectList[num]=String.valueOf(j); num++; }
		final SpinnerAdapter adapter1 = new SpinnerAdapter(this,
				android.R.layout.simple_spinner_item, selectList);
		adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		YearSpinner.setAdapter(adapter1);
		YearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				spinnerData = selectList[position];
				spinnerMonthDatas(Integer.parseInt(spinnerData));
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

	}
	// Spinner的响应
		public void spinnerMonthDatas(int i) {
			int num = 0;
			if(i==cal.get(Calendar.YEAR)){
				if((cal.get(Calendar.MONTH)+1)==1){
					selectList1=new String[cal.get(Calendar.MONTH)+1];
					selectList1[0]="01";
				}else{
				selectList1=new String[cal.get(Calendar.MONTH)]; 
				for(int j=cal.get(Calendar.MONTH);j>0;j--){
					if(j<10){
						selectList1[num]="0"+j;
					}else{
						selectList1[num]=String.valueOf(j);
					}
					num++;
				}
			}}else{
				selectList1=new String[12]; 
					for(int j=12;j>0;j--){
						if(j<10){
							selectList1[num]="0"+j;
						}else{
							selectList1[num]=String.valueOf(j);
						}
						num++;
					}
			}
			final SpinnerAdapter adapter1 = new SpinnerAdapter(this,
					android.R.layout.simple_spinner_item, selectList1);
			adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
			MonthSpinner.setAdapter(adapter1);
			MonthSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long arg3) {
					spinnerData1 = selectList1[position];
					SpinnerTime=spinnerData+"-"+spinnerData1;
					AddListViewData();
					
				}

				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});

		}
		//添加listview数据；
public void AddListViewData(){
	ZheXianDataTime = new ArrayList<String>();
	TimeToAdapter=new ArrayList<String>();
	Datas = new HashMap<String,CountEntity>();
		if(jianyan==0){
			for(int i=0;i<selectList1.length;i++){
				ZheXianDataTime.add(selectList1[i]);
				 CountEntity count = new CountEntity();
					count = countSqlite.QueryByDate(db, spinnerData+"-"+selectList1[i]);
					Datas.put(selectList1[i], count);
			}
			
				TimeToAdapter.add(spinnerData1);
			
		}else{
			for(int i=0;i<selectList.length;i++){
				ZheXianDataTime.add(selectList[i]);
				 CountEntity count = new CountEntity();
				count = countSqlite.QueryByDate(db, selectList[i]);
				Datas.put(selectList[i], count);
			}
			
				TimeToAdapter.add(spinnerData);
			}
		CountDataListViewAdapter adapter = new CountDataListViewAdapter(Datas, ZheXianDataTime, this,jianyan,TimeToAdapter);
		CountDataListView.setAdapter(adapter);
}
	// 设置弹出框
	public void SelectDialog() {
		View view = LayoutInflater.from(this).inflate(
				R.layout.countselectdialog, null);
		dialog = new CountSelectDialog(this, view);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(true);
		initSelectDialog(view);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.TOP);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.height = 280;
		lp.width = this.getWindowManager().getDefaultDisplay().getWidth();
		window.setAttributes(lp);
		dialog.show();
	}

	// 初始化弹出框
	public void initSelectDialog(View view) {
		final EnterAnimLayout SelectTopLayout = (EnterAnimLayout)view. findViewById(R.id.SelectTopLayout);
		SelectTiaoJianLayout = (LinearLayout) view
				.findViewById(R.id.SelectTiaoJianLayout);
		SelectTiaoJianLayout1 = (LinearLayout) view
				.findViewById(R.id.SelectTiaoJianLayout1);
		YearSelectEdit = (EditText) view.findViewById(R.id.YearSelectEdit);
		YearSelectEdit1 = (EditText) view.findViewById(R.id.YearSelectEdit1);
		MonthSelectEdit = (EditText) view.findViewById(R.id.MonthSelectEdit);
		MonthSelectEdit1 = (EditText) view.findViewById(R.id.MonthSelectEdit1);
		SelectYearSpinner = (Spinner) view.findViewById(R.id.SelectYearSpinner);
		SelectSure = (TextView) view.findViewById(R.id.SelectSure);
		SelectCancel = (TextView) view.findViewById(R.id.SelectCancel);
		SelectSure.setOnClickListener(this);
		SelectCancel.setOnClickListener(this);
		SelectspinnerDatas();
		EditTextAddListener(YearSelectEdit);
		EditTextAddListener(YearSelectEdit1);
		EditTextAddListener(MonthSelectEdit);
		EditTextAddListener(MonthSelectEdit1);
		if (jianyan == 0) {
			SelectTiaoJianLayout1.setVisibility(View.VISIBLE);
			SelectTiaoJianLayout.setVisibility(View.GONE);
		} else {
			SelectTiaoJianLayout1.setVisibility(View.GONE);
			SelectTiaoJianLayout.setVisibility(View.VISIBLE);
		}
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				// 举例设置成百叶窗动画
				Anim anim = new AnimQieRu(SelectTopLayout);
				anim.startAnimation(1500);
			}
		}, 100);

	}
	//通过筛选添加ListView 数据；
	public void AddListViewDataBySelect(int m,int n){
		ZheXianDataTime = new ArrayList<String>();
		TimeToAdapter=new ArrayList<String>();
		Datas = new HashMap<String,CountEntity>();
			if(jianyan==0){
				int num=0;
				for(int i=m;i<=n;i++){
					if(i<10){
						ZheXianDataTime.add("0"+i);
					}else{
						ZheXianDataTime.add(String.valueOf(i));
					}
					 CountEntity count = new CountEntity();
						count = countSqlite.QueryByDate(db, spinnerData+"-"+ZheXianDataTime.get(num));
						Datas.put(ZheXianDataTime.get(num), count);
						TimeToAdapter.add(ZheXianDataTime.get(num));
						num=num+1;
				}
					
				
			}else{
				for(int i=m;i<=n;i++){
					ZheXianDataTime.add(String.valueOf(i));
					 CountEntity count = new CountEntity();
					count = countSqlite.QueryByDate(db,String.valueOf(i));
					Datas.put(String.valueOf(i), count);
					TimeToAdapter.add(String.valueOf(i));
				}
					
				}
			CountDataListViewAdapter adapter = new CountDataListViewAdapter(Datas, ZheXianDataTime, this,jianyan,TimeToAdapter);
			CountDataListView.setAdapter(adapter);
	}
	// EditText文本监听
	public void EditTextAddListener(final EditText edit) {
		edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				int count = 0;
				if (jianyan == 1) {
					for (int i = 0; i < selectList.length; i++) {
						if (edit.getText().toString().equals(selectList[i])) {
							count = 1;
							break;
						}
					}
					if (count == 0) {
						edit.setText("");
						Toast.makeText(Count.this, "输入年份无效，请重输!", 1000).show();
					}
				} else {

					for (int i = 0; i < months.length; i++) {
						if (edit.getText().toString().equals(months[i])) {
							if (spinnerData.equals(String.valueOf(cal
									.get(Calendar.YEAR)))) {
								if (Integer.parseInt(months[i]) > (cal
										.get(Calendar.MONTH) + 1)) {
									count = 0;
								} else {
									count = 1;
								}
							}

							break;
						}
					}
					if (count == 0) {
						edit.setText("");
						Toast.makeText(Count.this, "输入月份无效，请重输!", 1000).show();
					}
				}

			}
		});
	}

	// SelectSpinner的响应
	public void SelectspinnerDatas() {
		spinnerData=String.valueOf(cal.get(Calendar.YEAR));
		final SpinnerAdapter adapter1 = new SpinnerAdapter(this,
				android.R.layout.simple_spinner_item, selectList);
		adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		SelectYearSpinner.setAdapter(adapter1);
		SelectYearSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long arg3) {
						spinnerData = selectList[position];
					}

					public void onNothingSelected(AdapterView<?> arg0) {

					}
				});

	}
	// 处理弹出框的数据
	public void DealDialogData() {
		if (jianyan == 0) {
			if (MonthSelectEdit.getText().toString().trim().length() == 0
					|| MonthSelectEdit1.getText().toString().trim().length() == 0
					|| Integer.parseInt(MonthSelectEdit.getText().toString()) > Integer
							.parseInt(MonthSelectEdit1.getText().toString())) {
				Toast.makeText(this, "查询条件不合法，请重输!", 1000).show();
			} else {
				AddListViewDataBySelect(Integer.parseInt(MonthSelectEdit.getText().toString()),Integer.parseInt(MonthSelectEdit1.getText().toString()));
				dialog.dismiss();
			}
		} else {
			if (YearSelectEdit.getText().toString().trim().length() == 0
					|| YearSelectEdit1.getText().toString().trim().length() == 0
					|| Integer.parseInt(YearSelectEdit.getText().toString()) > Integer
							.parseInt(YearSelectEdit1.getText().toString())) {
				Toast.makeText(this, "查询条件不合法，请重输!", 1000).show();
			} else {
				AddListViewDataBySelect(Integer.parseInt(YearSelectEdit.getText().toString()),Integer.parseInt(YearSelectEdit1.getText().toString()));
				dialog.dismiss();
			}
		}

	}

	// 期初预算弹出框
	public void CountPredictDialog() {
		View view = LayoutInflater.from(this).inflate(R.layout.countpredict,
				null);
		dialog = new CountSelectDialog(this, view);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.setCancelable(true);
		Window window = dialog.getWindow();
		window.setGravity(Gravity.TOP);
		window.setWindowAnimations(R.style.TopDialogStyle);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.height =LayoutParams.WRAP_CONTENT;
		lp.width = this.getWindowManager().getDefaultDisplay().getWidth();
		window.setAttributes(lp);
		initPredictData(view);
		dialog.show();

	}

	// 设置预算弹出框初始化
	public void initPredictData(View view) {
		TextView CountPredictText = (TextView) view
				.findViewById(R.id.CountPredictText);
		TextView CountPredictCancel = (TextView) view
				.findViewById(R.id.CountPredictCancel);
		TextView CanKaoPreData = (TextView) view
				.findViewById(R.id.CanKaoPreData);
		TextView LookAllPredictData = (TextView) view
				.findViewById(R.id.LookAllPredictData);

		ShouRuPredict = (EditText) view.findViewById(R.id.ShouRuPredict);
		ZhiChuPredict = (EditText) view.findViewById(R.id.ZhiChuPredict);
		PredictDataListView = (ListView) view
				.findViewById(R.id.PredictDataListView);
		PredictBottomLayout = (LinearLayout) view
				.findViewById(R.id.PredictBottomLayout);
		CountPredictText.setOnClickListener(this);
		CountPredictCancel.setOnClickListener(this);
		CanKaoPreData.setOnClickListener(this);
		LookAllPredictData.setOnClickListener(this);
		View view1 = LayoutInflater.from(this).inflate(
				R.layout.countpredictitem, null);
		PredictDataListView.addHeaderView(view1);
		if(predictsqlite.ReturnCount(db2, DateTime)!=0){
			CountPredict predict=new CountPredict();
			predict=predictsqlite.getCountPredictDatas(db2, DateTime);
			ShouRuPredict.setText(String.valueOf(predict.getShouPredictCount()));
			ZhiChuPredict.setText(String.valueOf(predict.getZhiChuPreCount()));
			CountPredictText.setText("修改");
			num2=1;
		}
	}

	// 添加预算数据
	public void AddPredictDatas() {
		int month = cal.get(Calendar.MONTH) + 1;
		int yer = cal.get(Calendar.YEAR);
		String time, time1;
		if (month == 1) {
			time = (yer - 1) + "-" + "10";
			time1 = (yer - 1) + "-" + "12";

		}
		if (month == 2) {
			time = (yer - 1) + "-" + "11";
			time1 = (yer - 1) + "-" + (month - 1);
		}
		if (month == 3) {
			time = (yer - 1) + "-" + "12";
			time1 = (yer - 1) + "-" + (month - 1);
		} else {
			time = yer + "-" + "0" + (month - 3);
			if ((month - 1) < 10) {
				time1 = yer + "-" + "0" + (month - 1);
			} else {
				time1 = yer + "-" + (month - 1);
			}
		}
		List<CountPredict> Predictdatas = new ArrayList<CountPredict>();
		Predictdatas = predictsqlite.getPredictDatas(db2, time, time1);
		CountPredictAdater adapter = new CountPredictAdater(this, Predictdatas);
		PredictDataListView.setAdapter(adapter);
		PredictBottomLayout.setVisibility(View.VISIBLE);
	}
 //预算
	public void PredictData(){
		Date date=new Date(System.currentTimeMillis());
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String time=format.format(date);
		CountPredict predict=new CountPredict(Double.parseDouble(ShouRuPredict.getText().toString()), Double.parseDouble(ZhiChuPredict.getText().toString()), DateTime, "预算中", "预算中");
		predictsqlite.addCountPredict(db2, predict);
		Toast.makeText(this, "预算完成!", 1000).show();
	}
	// 设置月底结账提醒弹出框
	public void TheMonthEndJieZhangDilog() {
		if (cal.get(Calendar.DAY_OF_MONTH) == cal.getMaximum(Calendar.DATE)) {
			String time;
			int CountYear;
			int Num = 0;
			int Num1 = 1;// 判断结账方式（手动，自动）
			CountSQLite countsqlite = new CountSQLite(this, "TotalCount.db",
					null, 1);
			SQLiteDatabase Countdb = countsqlite.getReadableDatabase();

			if ((cal.get(Calendar.MONTH) + 1) < 10) {
				time = cal.get(Calendar.YEAR) + "-" + "0"
						+ (cal.get(Calendar.MONTH) + 1);
			} else {
				time = cal.get(Calendar.YEAR) + "-"
						+ (cal.get(Calendar.MONTH) + 1);
			}
			Num = countsqlite.QueryNumByDate(Countdb, time);
			CountYear = cal.get(Calendar.YEAR);

			Cursor cursor = settypesqlite.returnType(db1, "结账方式设置");
			if (cursor.getCount() == 0) {
				settypesqlite.AddSet(db1, "结账方式设置", 1);
			}
			if (cursor.moveToFirst()) {
				Num1 = cursor.getInt(cursor.getColumnIndex("SetTypeNum"));
			}
			  cursor.close();
			//手动结账月末弹出框;
			if (Num1 != 2 && Num == 0) {
				JiZhangDialogInit(time, CountYear, Num1);	
				}
			//自动结账，月末最后一天的23点结账
			if(Num1==2 && Num==0 && cal.get(Calendar.HOUR_OF_DAY)==23){
				JiZhangDialogInit(time, CountYear, Num1);
			}
		}
	}

	// 对结账弹出框的初始化
	public void JiZhangDialogInit(String time, int year, int num) {
		JieZhangDataDialog dialog = new JieZhangDataDialog(this, time, year,
				num, 1);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.show();
		dialog.setCancelable(false);

	}
}
