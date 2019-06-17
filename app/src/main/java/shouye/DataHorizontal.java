package shouye;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.support.v4.widget.DrawerLayout;
import sqlite.IncomeSQLite;
import sqlite.PaySQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.jiacaitong.R;

import entity.Income;
import entity.Pay;
import entity.YingFu;
import entity.YingShou;

public class DataHorizontal extends Activity implements OnClickListener,Runnable {
	//处理线程一块
	private Handler mainHandler;
	private TextView mTextView;
	private Thread mThread;
	private boolean mflag;
	private int mCount=0;
	private double second=1;
	private int length;
	private String day="";
	private ListView drawlayoutlist;
	private DrawerLayout drawlayout;
	//存储倍速数据的集合
	String beisuNumber[]=new String[]{"0.8","1.0","1.5","2.0","2.5","3.0","3.5","4.0","4.5","5.0"};
	public String selectType[]=new String[]{"垂直","收入","支出","借款","贷款","实收","实付","总账"};
	private TextView DataTitle, auto, incomepayId,
			incomepayCLass, incomepayCount, incomepayTitle, incomepayResource,
			incomepayPeople, incomepayTime, incomepayStatus, incomepayNote,
			addYear, year, cutYear, addMonth, month, cutMonth, shoufuId, shoufuTele, shoufuClass, shoufuCount,
			 shoufuobjectText, shoufuObject,
			shoufuPeople, shoufuTime, shoufuStatus, shoufuNote, yema, showStatus,showStatus1,BeiSuNumber;
	private RadioButton yishouRadio, weishouRadio;
	private EditText seachMessage;
	private ViewFlipper dataFlipper;
	private List<Income> incomelist;
	private List<Pay> paylist;
	private List<YingShou> yingshoulist;
	private List<YingShou> shishoulist;
	private List<YingFu> yingfulist;
	private List<YingFu> shifulist;
	private IncomeSQLite incomesqlite;
	private PaySQLite paysqlite;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private SQLiteDatabase db1, db2, db3, db4;
	private int num;
	private String dataclassselect="收入";
	Animation[] animations = new Animation[4];
	private int number;
	LayoutInflater inflater;
	private LinearLayout showStatusTitle,showStatusTitle1,dataText;
	private RelativeLayout yemaLayout;
	private ImageView incomepoint1, incomepoint2, incomepoint3, incomepoint4,
			incomepoint5,shoupoint1, shoupoint2, shoupoint3, shoupoint4,
			shoupoint5, shoupoint6, shoupoint7,SeachLuYin,GoPrevious, GoNext,seach,downlist;
	private Button BeiSu;
	private Animation flare;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	boolean flag=false;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.datahorizontal);
		init();
		
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.GoPrevious:// 上一张
			dataFlipper.removeAllViews();
			num--;
			tool();
			dataFlipper.setInAnimation(animations[2]);
			dataFlipper.setOutAnimation(animations[3]);
			dataFlipper.showPrevious();

			break;
		case R.id.GoNext:// 下一张
			dataFlipper.removeAllViews();
			num++;
			tool();
			dataFlipper.setInAnimation(animations[0]);
			dataFlipper.setOutAnimation(animations[1]);
			dataFlipper.showNext();

			break;
		case R.id.addYear1:// 年份加
			dataFlipper.removeAllViews();// 清空dataFlipper容器，重新加载
			year.setText(String.valueOf(Integer.valueOf(year.getText()
					.toString()) + 1));
			getData();
			tool();
			break;
		case R.id.cutYear1:// 年份减
			dataFlipper.removeAllViews();
			year.setText(String.valueOf(Integer.valueOf(year.getText()
					.toString()) - 1));
			getData();
			tool();
			break;
		case R.id.addMonth1:// 月份加
			dataFlipper.removeAllViews();
			if (number < 9) {
				month.setText("0" + String.valueOf(number + 1));
				number = number + 1;
			} else {
				month.setText(String.valueOf(number + 1));
				number = number + 1;
			}
			if (number >= 13) {
				number = 1;
				month.setText("0" + String.valueOf(number));
			}
			getData();
			tool();
			break;
		case R.id.cutMonth1:// 月份减
			dataFlipper.removeAllViews();
			if (number <= 10) {
				month.setText("0" + String.valueOf(number - 1));
				number = number - 1;
			} else {
				month.setText(String.valueOf(number - 1));
				number = number - 1;
			}
			if (number < 1) {
				number = 13;
				month.setText(String.valueOf(number - 1));
				number = number - 1;
			}
			getData();
			tool();
			break;
		case R.id.showStatus:
			if (showStatusTitle.getVisibility()==View.VISIBLE) {
				showStatusTitle.setVisibility(View.GONE);
				showStatus.setText("显示进度");
				
			} else if (showStatusTitle.getVisibility()==View.GONE) {
				showStatusTitle.setVisibility(View.VISIBLE);
				showStatus.setText("隐藏进度");
				
			}
			break;
		case R.id.showStatus1:
			if (showStatusTitle1.getVisibility()==View.VISIBLE) {
				showStatusTitle1.setVisibility(View.GONE);
				showStatus1.setText("显示进度");
				
			} else if (showStatusTitle1.getVisibility()==View.GONE) {
				showStatusTitle1.setVisibility(View.VISIBLE);
				showStatus1.setText("隐藏进度");
				
			}
			break;
		case R.id.SeachLuYin:
			startRecognizerActivity();
			break;
		case R.id.AuTo:
			AuToPlay();
			if(flag==false){
				flag=true;
				auto.setText("手动");
				BeiSu.setVisibility(View.VISIBLE);
				BeiSuNumber.setVisibility(View.VISIBLE);
			}
			else{
				flag=false;
				auto.setText("自动");
				BeiSu.setVisibility(View.GONE);
				BeiSuNumber.setVisibility(View.GONE);
			}
			break;
		case R.id.BeiSu:
			selectBeiSu();
			break;
		case R.id.seach:
			
			getData();
			tool();
			break;
		case R.id.downlist:
			showDrawerLayout();
		}
		

	}

	// incomepaydata_item中控件实例化
	public void initIncome(View view) {
		incomepayId = (TextView) view.findViewById(R.id.incomepayId);
		incomepayCLass = (TextView) view.findViewById(R.id.incomepayCLass);
		incomepayCount = (TextView) view.findViewById(R.id.incomepayCount);
		incomepayTitle = (TextView) view.findViewById(R.id.incomepayTitle);
		incomepayResource = (TextView) view
				.findViewById(R.id.incomepayResource);
		incomepayPeople = (TextView) view.findViewById(R.id.incomepayPeople);
		incomepayTime = (TextView) view.findViewById(R.id.incomepayTime);
		incomepayStatus = (TextView) view.findViewById(R.id.incomepayStatus);
		incomepayNote = (TextView) view.findViewById(R.id.incomepayNote);
		showStatus = (TextView)view.findViewById(R.id.showStatus);
		incomepoint1 = (ImageView) view.findViewById(R.id.point1);
		incomepoint2 = (ImageView) view.findViewById(R.id.point2);
		incomepoint3 = (ImageView) view.findViewById(R.id.point3);
		incomepoint4 = (ImageView) view.findViewById(R.id.point4);
		incomepoint5 = (ImageView) view.findViewById(R.id.point5);
		
		showStatusTitle = (LinearLayout) view
				.findViewById(R.id.showStatusTitle);
		showStatus.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		showStatus.setOnClickListener(this);
		
		
		
	}

	// shoufudata_item中控件实例化实例化
	public void initShou(View view) {
		shoufuId = (TextView) view.findViewById(R.id.shoufuId);
		shoufuTele = (TextView) view.findViewById(R.id.shoufuTele);
		shoufuClass = (TextView) view.findViewById(R.id.shoufuClass);
		shoufuCount = (TextView) view.findViewById(R.id.shoufuCount);
		shoufuobjectText = (TextView) view.findViewById(R.id.shoufuobjectText);
		shoufuObject = (TextView) view.findViewById(R.id.shoufuObject);
		shoufuPeople = (TextView) view.findViewById(R.id.shoufuPeople);
		shoufuTime = (TextView) view.findViewById(R.id.shoufuTime);
		shoufuStatus = (TextView) view.findViewById(R.id.shoufuStatus);
		shoufuNote = (TextView) view.findViewById(R.id.shoufuNote);
		showStatus1 = (TextView)view.findViewById(R.id.showStatus1);
		yishouRadio = (RadioButton) view.findViewById(R.id.BlueText);
		weishouRadio = (RadioButton) view.findViewById(R.id.RedText);
		
		shoupoint1 = (ImageView) view.findViewById(R.id.point1);
		shoupoint2 = (ImageView) view.findViewById(R.id.point2);
		shoupoint3 = (ImageView) view.findViewById(R.id.point3);
		shoupoint4 = (ImageView) view.findViewById(R.id.point4);
		shoupoint5 = (ImageView) view.findViewById(R.id.point5);
		shoupoint6 = (ImageView) view.findViewById(R.id.point6);
		shoupoint7 = (ImageView) view.findViewById(R.id.point7);	
		showStatusTitle1 = (LinearLayout) view
				.findViewById(R.id.showStatusTitle1);
		showStatus1.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		showStatus1.setOnClickListener(this);
		
	}

	// 基础布局控件实例化
	public void init() {
		inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		seachMessage=(EditText)findViewById(R.id.seachMessage);
		BeiSu= (Button) findViewById(R.id.BeiSu);
		dataFlipper = (ViewFlipper) findViewById(R.id.dataFlipper);
		
		addYear = (TextView) findViewById(R.id.addYear1);
		year = (TextView) findViewById(R.id.Year1);
		cutYear = (TextView) findViewById(R.id.cutYear1);
		addMonth = (TextView) findViewById(R.id.addMonth1);
		month = (TextView) findViewById(R.id.Month1);
		DataTitle = (TextView) findViewById(R.id.DataTitle);
		cutMonth = (TextView) findViewById(R.id.cutMonth1);
		yema = (TextView)findViewById(R.id.yema);
		auto = (TextView)findViewById(R.id.AuTo);
		BeiSuNumber = (TextView)findViewById(R.id.beisuNumber);
		
		GoPrevious = (ImageView)findViewById(R.id.GoPrevious);
		GoNext = (ImageView)findViewById(R.id.GoNext);
		seach=(ImageView)findViewById(R.id.seach);
		SeachLuYin=(ImageView)findViewById(R.id.SeachLuYin);
		dataText = (LinearLayout) findViewById(R.id.dataText);
		downlist = (ImageView) findViewById(R.id.downlist);
		yemaLayout=(RelativeLayout)findViewById(R.id.yemaLayout);
		drawlayoutlist=(ListView)findViewById(R.id.lv);
		drawlayout=(DrawerLayout)findViewById(R.id.drawLayout);
		
		incomelist = new ArrayList<Income>();
		paylist = new ArrayList<Pay>();
		yingshoulist = new ArrayList<YingShou>();
		yingfulist = new ArrayList<YingFu>();
		shishoulist = new ArrayList<YingShou>();
		shifulist = new ArrayList<YingFu>();
		incomesqlite = new IncomeSQLite(this, "income.db", null, 1);
		db1 = incomesqlite.getReadableDatabase();
		paysqlite = new PaySQLite(this, "pay.db", null, 1);
		db2 = paysqlite.getReadableDatabase();
		yingshousqlite = new YingShouSQLite(this, "yingshou.db", null, 1);
		db3 = yingshousqlite.getReadableDatabase();
		yingfusqlite = new YingFuSQLite(this, "yingfu.db", null, 1);
		db4 = yingfusqlite.getReadableDatabase();
		animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in);
		animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out);
		animations[2] = AnimationUtils.loadAnimation(this, R.anim.right_in);
		animations[3] = AnimationUtils.loadAnimation(this, R.anim.right_out);
		flare = AnimationUtils.loadAnimation(this, R.anim.flare);
		GoPrevious.setOnClickListener(this);
		GoNext.setOnClickListener(this);
		GoPrevious.setVisibility(View.GONE);
		addYear.setOnClickListener(this);
		cutYear.setOnClickListener(this);
		addMonth.setOnClickListener(this);
		cutMonth.setOnClickListener(this);
		SeachLuYin.setOnClickListener(this);
		auto.setOnClickListener(this);
		BeiSu.setOnClickListener(this);
		seach.setOnClickListener(this);
		downlist.setOnClickListener(this);
		DataTitle.setText(dataclassselect);
		//初始化月份：
		Date date=new Date(System.currentTimeMillis());
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		number=cal.get(Calendar.MONTH)+1;
		year.setText(String.valueOf(cal.get(Calendar.YEAR)));
		if (number < 10) {
			month.setText("0" + String.valueOf(number));
		} else {
			month.setText(String.valueOf(number));
		}
		getData();
		tool();
		mainHandler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int what = msg.what;
				switch (what) {
				case 1:
					int arg1 = msg.arg1;
					//auto.setText(String.valueOf(arg1));
					dataFlipper.removeAllViews();
					num=arg1;
					//getData();
					tool();
					dataFlipper.setInAnimation(animations[0]);
					dataFlipper.setOutAnimation(animations[1]);
					dataFlipper.showNext();
					break;
				} 
			}
		};
		
		mThread =new Thread(this);
		
//初始化selectList数据
	      ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,selectType);
	  	drawlayoutlist.setAdapter(adapter);
	  	ItemClick();
	  	AddEditTextChangeListener();
	}

	// 显示income数据
	public void returnIncomeView() {

		View view = null;
		view = inflater.inflate(R.layout.incomepaydata_item, null);
		initIncome(view);
		Income income = new Income();
		if (incomelist.size() == 0) {
			dataFlipper.setVisibility(View.GONE);
			dataText.setVisibility(View.VISIBLE);
			yemaLayout.setVisibility(View.GONE);
		} else {
			yemaLayout.setVisibility(View.VISIBLE);
			yema.setText("当前" + " " + (num + 1) + "/" + incomelist.size() + " "
					+ "页");
			income = incomelist.get(num);
			incomepayId.setText(String.valueOf(income.getId()));
			incomepayCLass.setText(income.getMenuName());
			incomepayCount.setText(String.valueOf(income.getCount()));
			incomepayTitle.setText("来源:");
			incomepayResource.setText(income.getIncomeSource());
			incomepayPeople.setText(income.getMakePerson());
			incomepayTime.setText(income.getDate());
			if (income.getStatus() == 0) {
				incomepayStatus.setText("已开始");
			} else if (income.getStatus() == 1) {
				incomepayStatus.setText("已锁定");
			} else if (income.getStatus() == 2) {
				incomepayStatus.setText("已入账");
			}else if(income.getStatus()==4){
				incomepayStatus.setText("已做账");
			}
			Progress(income.getStatus());
			incomepayNote.setText(income.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);
			length= incomelist.size();
		}

	}

	// 显示pay数据
	public void returnPayView() {
		View view = null;
		view = inflater.inflate(R.layout.incomepaydata_item, null);
		initIncome(view);
		Pay pay = new Pay();
		if (paylist.size() == 0) {
			dataFlipper.setVisibility(View.GONE);
			dataText.setVisibility(View.VISIBLE);
			yemaLayout.setVisibility(View.GONE);
		} else {
			yemaLayout.setVisibility(View.VISIBLE);
			yema.setText("当前" + " " + (num + 1) + "/" + paylist.size() + " "
					+ "页");
			pay = paylist.get(num);
			incomepayId.setText(String.valueOf(pay.getId()));
			incomepayCLass.setText(pay.getMenuName());
			incomepayCount.setText(String.valueOf(pay.getCount()));
			incomepayTitle.setText("出处:");
			incomepayResource.setText(pay.getPayTo());
			incomepayPeople.setText(pay.getMakePerson());
			incomepayTime.setText(pay.getDate());
			if (pay.getStatus() == 0) {
				incomepayStatus.setText("已开始");
			} else if (pay.getStatus() == 1) {
				incomepayStatus.setText("已锁定");
			} else if (pay.getStatus() == 2) {
				incomepayStatus.setText("已入账");
			}
			Progress(pay.getStatus());
			incomepayNote.setText(pay.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);
			length= paylist.size();
		}

	}

	// 显示yingshou数据
	public void returnShouView() {
		View view = null;
		view = inflater.inflate(R.layout.shoufudata_item, null);
		initShou(view);
		YingShou yingshou = new YingShou();
		if(dataclassselect.equals("借款")){
		if (yingshoulist.size() == 0) {
			dataFlipper.setVisibility(View.GONE);
			dataText.setVisibility(View.VISIBLE);
			yemaLayout.setVisibility(View.GONE);
		} else {
			yemaLayout.setVisibility(View.VISIBLE);
			yema.setText("当前" + " " + (num + 1) + "/" + yingshoulist.size()
					+ " " + "页");
			yingshou = yingshoulist.get(num);
			weishouRadio.setChecked(true);
			
		    length= yingshoulist.size();
		    shoufuId.setText(String.valueOf(yingshou.getId()));
			shoufuClass.setText(yingshou.getMenuName());
			shoufuCount.setText(String.valueOf(yingshou.getCount()));
			shoufuobjectText.setText("借款对象");
			shoufuPeople.setText(yingshou.getMakePerson());
			shoufuTime.setText(yingshou.getDate());
			shoufuTele.setText(yingshou.getTelephone());
			shoufuObject.setText(yingshou.getYingShouObject());
			if (yingshou.getStatus() == 0) {
				shoufuStatus.setText("已开始");
			} else if (yingshou.getStatus() == 1) {
				shoufuStatus.setText("已锁定");
			} else if (yingshou.getStatus() == 2) {
				shoufuStatus.setText("已做账");
			}
			Progress(yingshou.getStatus());
			shoufuNote.setText(yingshou.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);}
		}else if(dataclassselect.equals("实收")){
			if (shishoulist.size() == 0) {
				dataFlipper.setVisibility(View.GONE);
				dataText.setVisibility(View.VISIBLE);
				yemaLayout.setVisibility(View.GONE);
			} else {
				yemaLayout.setVisibility(View.VISIBLE);
				yema.setText("当前" + " " + (num + 1) + "/" + shishoulist.size()
						+ " " + "页");
				yingshou = shishoulist.get(num);
				yishouRadio.setChecked(true);
			    length= shishoulist.size();
			shoufuId.setText(String.valueOf(yingshou.getId()));
			shoufuClass.setText(yingshou.getMenuName());
			shoufuCount.setText(String.valueOf(yingshou.getCount()));
			shoufuobjectText.setText("借款对象");
			shoufuPeople.setText(yingshou.getMakePerson());
			shoufuTime.setText(yingshou.getDate());
			shoufuTele.setText(yingshou.getTelephone());
			shoufuObject.setText(yingshou.getYingShouObject());
			if (yingshou.getStatus() == 0) {
				shoufuStatus.setText("已开始");
			} else if (yingshou.getStatus() == 1) {
				shoufuStatus.setText("已锁定");
			} else if (yingshou.getStatus() == 2) {
				shoufuStatus.setText("已做账");
			}
			Progress(yingshou.getStatus());
			shoufuNote.setText(yingshou.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);
			
		}	}

	}

	// 显示yingfu数据
	public void returnFuView() {
		View view = null;
		view = inflater.inflate(R.layout.shoufudata_item, null);
		initShou(view);
		YingFu yingfu = new YingFu();
		if(dataclassselect.equals("贷款")){
		if (yingfulist.size() == 0) {
			dataFlipper.setVisibility(View.GONE);
			dataText.setVisibility(View.VISIBLE);
			yemaLayout.setVisibility(View.GONE);
		} else {
			yemaLayout.setVisibility(View.VISIBLE);
			yema.setText("当前" + " " + (num + 1) + "/" + yingfulist.size() + " "
					+ "页");
			yingfu = yingfulist.get(num);
			weishouRadio.setChecked(true);
			length= yingfulist.size();
			shoufuId.setText(String.valueOf(yingfu.getId()));
			shoufuClass.setText(yingfu.getMenuName());
			shoufuCount.setText(String.valueOf(yingfu.getCount()));
			shoufuobjectText.setText("贷款对象");
			shoufuPeople.setText(yingfu.getMakePerson());
			shoufuTime.setText(yingfu.getDate());
			shoufuTele.setText(yingfu.getTelephone());
			shoufuObject.setText(yingfu.getYingFuObject());
			if (yingfu.getStatus() == 0) {
				shoufuStatus.setText("已开始");
			} else if (yingfu.getStatus() == 1) {
				shoufuStatus.setText("已锁定");
			} else if (yingfu.getStatus() == 2) {
				shoufuStatus.setText("已做账");
			}
			Progress(yingfu.getStatus());
			shoufuNote.setText(yingfu.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);
		}
			}
		else if(dataclassselect.equals("实付")){
			if (shifulist.size() == 0) {
				dataFlipper.setVisibility(View.GONE);
				dataText.setVisibility(View.VISIBLE);
				yemaLayout.setVisibility(View.GONE);
			} else {
				yemaLayout.setVisibility(View.VISIBLE);
				yema.setText("当前" + " " + (num + 1) + "/" + shifulist.size() + " "
						+ "页");
				yingfu = shifulist.get(num);
				yishouRadio.setChecked(true);
				
				length= shifulist.size();
		
			shoufuId.setText(String.valueOf(yingfu.getId()));
			shoufuClass.setText(yingfu.getMenuName());
			shoufuCount.setText(String.valueOf(yingfu.getCount()));
			shoufuobjectText.setText("贷款对象");
			shoufuPeople.setText(yingfu.getMakePerson());
			shoufuTime.setText(yingfu.getDate());
			shoufuTele.setText(yingfu.getTelephone());
			shoufuObject.setText(yingfu.getYingFuObject());
			if (yingfu.getStatus() == 0) {
				shoufuStatus.setText("已开始");
			} else if (yingfu.getStatus() == 1) {
				shoufuStatus.setText("已锁定");
			} else if (yingfu.getStatus() == 2) {
				shoufuStatus.setText("已做账");
			}
			Progress(yingfu.getStatus());
			shoufuNote.setText(yingfu.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);
		}}
		}
//显示总账(Count)结果
public void returnCount(){
	dataFlipper.setVisibility(View.GONE);
	dataText.setVisibility(View.VISIBLE);
	yemaLayout.setVisibility(View.GONE);
}

	// 获取数据（从数据库中）
	//获取数据
	public void getData() {
		incomelist.clear();
		paylist.clear();
		yingshoulist.clear();
		shishoulist.clear();
		yingfulist.clear();
		shifulist.clear();
		num = 0;
		day="";
		String time=null;
		if(!(seachMessage.getText().toString()).equals("")){
		if((Integer.parseInt(seachMessage.getText().toString())<10)&&(Integer.parseInt(seachMessage.getText().toString())>0)){
			day="0"+seachMessage.getText().toString();
		}
		else if((Integer.parseInt(seachMessage.getText().toString())>=10)&&(Integer.parseInt(seachMessage.getText().toString())<=31)){
			day=seachMessage.getText().toString();
		}
		else{
			Toast.makeText(DataHorizontal.this, "输入数据不合法!", Toast.LENGTH_LONG).show();
			seachMessage.setText("");
		}
		time = year.getText().toString() + "-"
				+ month.getText().toString()+"-"+day;
		}else{
			time = year.getText().toString() + "-"
					+ month.getText().toString();
		}
	
		if (dataclassselect.equals("收入")) {
			incomelist = incomesqlite.queryAllIncomeByTime(db1, time);
		}
		if (dataclassselect.equals("支出")) {
			paylist = paysqlite.queryAllPayByTime(db2, time);
		}
		if (dataclassselect.equals("借款")) {
			yingshoulist = yingshousqlite.queryAllYingShouByTime(db3, time);
            for(int i=0;i<yingshoulist.size();i++){
            	if(yingshoulist.get(i).getProperty()==1){
            		shishoulist.add(yingshoulist.get(i));
            		yingshoulist.remove(i);
            	}
            }
		}
		if (dataclassselect.equals("贷款")) {
			yingfulist = yingfusqlite.queryAllYingFuByTime(db4, time);
			 for(int i=0;i<yingfulist.size();i++){
	            	if(yingfulist.get(i).getProperty()==1){
	            		shifulist.add(yingfulist.get(i));
	            		yingfulist.remove(i);
	            	}
	            }
		}
		if (dataclassselect.equals("实收")) {
			yingshoulist = yingshousqlite.queryAllYingShouByTime(db3, time);
            for(int i=0;i<yingshoulist.size();i++){
            	if(yingshoulist.get(i).getProperty()==1){
            		shishoulist.add(yingshoulist.get(i));
            		yingshoulist.remove(i);
            	}
            }
		}
		if (dataclassselect.equals("实付")) {
			yingfulist = yingfusqlite.queryAllYingFuByTime(db4, time);
			 for(int i=0;i<yingfulist.size();i++){
	            	if(yingfulist.get(i).getProperty()==1){
	            		shifulist.add(yingfulist.get(i));
	            		yingfulist.remove(i);
	            	}
	            }
		}

	}

	// 工具方法（重复调用方法的封装）
	public void tool() {
		if (num <= 0) {
			GoPrevious.setVisibility(View.GONE);
			GoNext.setVisibility(View.VISIBLE);
		} else if (num > 0) {
			GoNext.setVisibility(View.VISIBLE);
			GoPrevious.setVisibility(View.VISIBLE);
		}
		if (dataclassselect.equals("收入")) {
			if (num >= incomelist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}
			returnIncomeView();
		}
		if (dataclassselect.equals("支出")) {
			if (num >= paylist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}
			returnPayView();
		}
		if (dataclassselect.equals("借款")) {
			if (num >= yingshoulist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}

			returnShouView();
		}
		if (dataclassselect.equals("贷款")) {
			if (num >= yingfulist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}
			returnFuView();
		}
		if (dataclassselect.equals("实收")) {
			if (num >=shishoulist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}
			returnShouView();
		}
		if (dataclassselect.equals("实付")) {
			if (num >=shifulist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}
			returnFuView();
		}
		if (dataclassselect.equals("总账")) {
			
			returnCount();
		}
	}

	public void Progress(int i) {
		if (dataclassselect.equals("收入") || dataclassselect.equals("支出")) {
			if (i== 0) {
				incomepoint1.setImageResource(R.drawable.past);
				incomepoint2.setImageResource(R.drawable.point);
                 incomepoint2.startAnimation(flare);
			}
			if(i==1){
				incomepoint1.setImageResource(R.drawable.past);
				incomepoint2.setImageResource(R.drawable.past);
				incomepoint3.setImageResource(R.drawable.point);
                incomepoint3.startAnimation(flare);
			}
			
			if(i==2){
				incomepoint1.setImageResource(R.drawable.past);
				incomepoint2.setImageResource(R.drawable.past);
				incomepoint3.setImageResource(R.drawable.past);
				incomepoint4.setImageResource(R.drawable.past);
				incomepoint5.setImageResource(R.drawable.past);
				
			}
		}
		if(dataclassselect.equals("借款")||dataclassselect.equals("贷款")){
			if (i== 0) {
				shoupoint1.setImageResource(R.drawable.past);
				shoupoint2.setImageResource(R.drawable.point);
                 shoupoint2.startAnimation(flare);
			}
			if(i==1){
				shoupoint1.setImageResource(R.drawable.past);
				shoupoint2.setImageResource(R.drawable.past);
				shoupoint3.setImageResource(R.drawable.point);
                shoupoint3.startAnimation(flare);
			}
			if(i==2){
				shoupoint1.setImageResource(R.drawable.past);
				shoupoint2.setImageResource(R.drawable.past);
				shoupoint3.setImageResource(R.drawable.past);
				shoupoint4.setImageResource(R.drawable.point);
				shoupoint4.startAnimation(flare);
			}
			
			if(i==3){
				shoupoint1.setImageResource(R.drawable.past);
				shoupoint2.setImageResource(R.drawable.past);
				shoupoint3.setImageResource(R.drawable.past);
				shoupoint4.setImageResource(R.drawable.past);
				shoupoint5.setImageResource(R.drawable.past);
				shoupoint6.setImageResource(R.drawable.past);
				shoupoint7.setImageResource(R.drawable.past);
			}
			}
		if(dataclassselect.equals("实收")||dataclassselect.equals("实付")){
			if (i== 0) {
				shoupoint1.setImageResource(R.drawable.past);
				shoupoint2.setImageResource(R.drawable.point);
                 shoupoint2.startAnimation(flare);
			}
			if(i==1){
				shoupoint1.setImageResource(R.drawable.past);
				shoupoint2.setImageResource(R.drawable.past);
				shoupoint3.setImageResource(R.drawable.point);
                shoupoint3.startAnimation(flare);
			}
			if(i==2){
				shoupoint1.setImageResource(R.drawable.past);
				shoupoint2.setImageResource(R.drawable.past);
				shoupoint3.setImageResource(R.drawable.past);
				shoupoint4.setImageResource(R.drawable.point);
				shoupoint4.startAnimation(flare);
			}

			if(i==3){
				shoupoint1.setImageResource(R.drawable.past);
				shoupoint2.setImageResource(R.drawable.past);
				shoupoint3.setImageResource(R.drawable.past);
				shoupoint4.setImageResource(R.drawable.past);
				shoupoint5.setImageResource(R.drawable.past);
				shoupoint6.setImageResource(R.drawable.past);
				shoupoint7.setImageResource(R.drawable.past);
			}
		}
		}
	//语音识别
	private void startRecognizerActivity() { // 通过Intent传递语音识别的模式，开启语音
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // 语言模式和自由模式的语音识别
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); // 提示语音开始
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "开始语音"); // 开始语音识别
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE); // 调出识别界面
	}

	
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
			getData();
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
		number=month;
		return str;
	}
	public void AuToPlay() {
		if (!mThread.isAlive()) { // 开始计时器或者是重启计时器，设置标记为true
			mflag = true;
			// 判断是否是第一次启动，如果是不是第一次启动，那么状态就是Thread.State.TERMINATED
			// 不是的话，就需要重新的初始化，因为之前的已经结束了。
			// 并且要判断这个mCount 是否为-1，如果是的话，说名上一次的计时已经完成了，那么要重新设置。
			if (mThread.getState() == Thread.State.TERMINATED) {
				mThread = new Thread(this);
				if (mCount==length-1)
					mCount =0;
				mThread.start();
			} else {
				mThread.start();
			}
		} else { // 暂停计时器，设置标记为false
			mflag=false; }}
	
			//不可以使用 stop方法，会报错，java.lang.UnsupportedOperationException
					// //mThread.stop(); } } 
	public void run() {
					// //子线程必须要设置这个标记mflag和倒计时数。
				 while(mflag&&num>=0&&num<length){ 
					 try {
						
				  Thread.sleep((long) (second*1000)); 
				  num++;
				  if (num==length){
						num =0;}} 
					 catch (InterruptedException e) {
					 e.printStackTrace(); } //每间隔 一秒钟 发送 一个Message 给主线程的
					// handler让主线程的hanlder 来修改UI //注意 这里的 message可以是通过obtain来获取
					// 这样节省内存，它会自动的看有没有可以复用的，就不重复创建
					Message message =Message.obtain();
					message.what=1; 
					message.arg1=num;
					mainHandler.sendMessage(message);}
				 }
				
    protected void onDestroy(){ 
						 super.onDestroy();
					     mflag=false; }

    public void selectBeiSu(){
	
	LayoutInflater inflater = (LayoutInflater)DataHorizontal.this
			.getSystemService(LAYOUT_INFLATER_SERVICE);
	       View view = inflater.inflate(R.layout.beisu, null);
	       ListView beisuList=(ListView)view.findViewById(R.id.BeiSuList);
	       ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,beisuNumber);
	      beisuList.setAdapter(adapter);
	    final AlertDialog alert = new AlertDialog.Builder(
			DataHorizontal.this).create();
	       alert.setView(view);
	       alert.show();
			//设置对话框大小
			WindowManager.LayoutParams params=alert.getWindow().getAttributes();
			params.width=240;
			params.gravity=Gravity.RIGHT|Gravity.CENTER_HORIZONTAL;
			params.y=135;
			params.height=400;
			alert.getWindow().setAttributes(params);
			beisuList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long arg3) {
					BeiSuNumber.setText(beisuNumber[position]);
					second=Double.parseDouble(beisuNumber[position]);
					alert.dismiss();
					
				}
			});
}
public void ItemClick(){   
	drawlayoutlist.setOnItemClickListener(new OnItemClickListener() {

	  		@Override
	  		public void onItemClick(AdapterView<?> arg0, View view,
	  				int position, long arg3) {
	  			dataclassselect=selectType[position];
	  			if(dataclassselect=="垂直"){
	  				Intent intent=new Intent(DataHorizontal.this,Data.class);
	  				startActivity(intent);
	  				DataHorizontal.this.finish();
	  			}else{
	  			dataFlipper.removeAllViews();
	  			DataTitle.setText(dataclassselect);
	  			getData();
	  			tool();
	  			
	  		}
	  			showDrawerLayout();}
	  		
	  	});
}
public void showDrawerLayout(){
	if(!drawlayout.isDrawerOpen(Gravity.LEFT)){
		drawlayout.openDrawer(Gravity.LEFT);
	}else{
		drawlayout.closeDrawer(Gravity.LEFT);
	}
}
public void AddEditTextChangeListener(){
	seachMessage.addTextChangedListener(new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			
			getData();
			tool();
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
} 