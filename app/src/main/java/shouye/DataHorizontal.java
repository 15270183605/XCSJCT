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
	//�����߳�һ��
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
	//�洢�������ݵļ���
	String beisuNumber[]=new String[]{"0.8","1.0","1.5","2.0","2.5","3.0","3.5","4.0","4.5","5.0"};
	public String selectType[]=new String[]{"��ֱ","����","֧��","���","����","ʵ��","ʵ��","����"};
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
	private String dataclassselect="����";
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
		case R.id.GoPrevious:// ��һ��
			dataFlipper.removeAllViews();
			num--;
			tool();
			dataFlipper.setInAnimation(animations[2]);
			dataFlipper.setOutAnimation(animations[3]);
			dataFlipper.showPrevious();

			break;
		case R.id.GoNext:// ��һ��
			dataFlipper.removeAllViews();
			num++;
			tool();
			dataFlipper.setInAnimation(animations[0]);
			dataFlipper.setOutAnimation(animations[1]);
			dataFlipper.showNext();

			break;
		case R.id.addYear1:// ��ݼ�
			dataFlipper.removeAllViews();// ���dataFlipper���������¼���
			year.setText(String.valueOf(Integer.valueOf(year.getText()
					.toString()) + 1));
			getData();
			tool();
			break;
		case R.id.cutYear1:// ��ݼ�
			dataFlipper.removeAllViews();
			year.setText(String.valueOf(Integer.valueOf(year.getText()
					.toString()) - 1));
			getData();
			tool();
			break;
		case R.id.addMonth1:// �·ݼ�
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
		case R.id.cutMonth1:// �·ݼ�
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
				showStatus.setText("��ʾ����");
				
			} else if (showStatusTitle.getVisibility()==View.GONE) {
				showStatusTitle.setVisibility(View.VISIBLE);
				showStatus.setText("���ؽ���");
				
			}
			break;
		case R.id.showStatus1:
			if (showStatusTitle1.getVisibility()==View.VISIBLE) {
				showStatusTitle1.setVisibility(View.GONE);
				showStatus1.setText("��ʾ����");
				
			} else if (showStatusTitle1.getVisibility()==View.GONE) {
				showStatusTitle1.setVisibility(View.VISIBLE);
				showStatus1.setText("���ؽ���");
				
			}
			break;
		case R.id.SeachLuYin:
			startRecognizerActivity();
			break;
		case R.id.AuTo:
			AuToPlay();
			if(flag==false){
				flag=true;
				auto.setText("�ֶ�");
				BeiSu.setVisibility(View.VISIBLE);
				BeiSuNumber.setVisibility(View.VISIBLE);
			}
			else{
				flag=false;
				auto.setText("�Զ�");
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

	// incomepaydata_item�пؼ�ʵ����
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

	// shoufudata_item�пؼ�ʵ����ʵ����
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

	// �������ֿؼ�ʵ����
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
		//��ʼ���·ݣ�
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
		
//��ʼ��selectList����
	      ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,selectType);
	  	drawlayoutlist.setAdapter(adapter);
	  	ItemClick();
	  	AddEditTextChangeListener();
	}

	// ��ʾincome����
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
			yema.setText("��ǰ" + " " + (num + 1) + "/" + incomelist.size() + " "
					+ "ҳ");
			income = incomelist.get(num);
			incomepayId.setText(String.valueOf(income.getId()));
			incomepayCLass.setText(income.getMenuName());
			incomepayCount.setText(String.valueOf(income.getCount()));
			incomepayTitle.setText("��Դ:");
			incomepayResource.setText(income.getIncomeSource());
			incomepayPeople.setText(income.getMakePerson());
			incomepayTime.setText(income.getDate());
			if (income.getStatus() == 0) {
				incomepayStatus.setText("�ѿ�ʼ");
			} else if (income.getStatus() == 1) {
				incomepayStatus.setText("������");
			} else if (income.getStatus() == 2) {
				incomepayStatus.setText("������");
			}else if(income.getStatus()==4){
				incomepayStatus.setText("������");
			}
			Progress(income.getStatus());
			incomepayNote.setText(income.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);
			length= incomelist.size();
		}

	}

	// ��ʾpay����
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
			yema.setText("��ǰ" + " " + (num + 1) + "/" + paylist.size() + " "
					+ "ҳ");
			pay = paylist.get(num);
			incomepayId.setText(String.valueOf(pay.getId()));
			incomepayCLass.setText(pay.getMenuName());
			incomepayCount.setText(String.valueOf(pay.getCount()));
			incomepayTitle.setText("����:");
			incomepayResource.setText(pay.getPayTo());
			incomepayPeople.setText(pay.getMakePerson());
			incomepayTime.setText(pay.getDate());
			if (pay.getStatus() == 0) {
				incomepayStatus.setText("�ѿ�ʼ");
			} else if (pay.getStatus() == 1) {
				incomepayStatus.setText("������");
			} else if (pay.getStatus() == 2) {
				incomepayStatus.setText("������");
			}
			Progress(pay.getStatus());
			incomepayNote.setText(pay.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);
			length= paylist.size();
		}

	}

	// ��ʾyingshou����
	public void returnShouView() {
		View view = null;
		view = inflater.inflate(R.layout.shoufudata_item, null);
		initShou(view);
		YingShou yingshou = new YingShou();
		if(dataclassselect.equals("���")){
		if (yingshoulist.size() == 0) {
			dataFlipper.setVisibility(View.GONE);
			dataText.setVisibility(View.VISIBLE);
			yemaLayout.setVisibility(View.GONE);
		} else {
			yemaLayout.setVisibility(View.VISIBLE);
			yema.setText("��ǰ" + " " + (num + 1) + "/" + yingshoulist.size()
					+ " " + "ҳ");
			yingshou = yingshoulist.get(num);
			weishouRadio.setChecked(true);
			
		    length= yingshoulist.size();
		    shoufuId.setText(String.valueOf(yingshou.getId()));
			shoufuClass.setText(yingshou.getMenuName());
			shoufuCount.setText(String.valueOf(yingshou.getCount()));
			shoufuobjectText.setText("������");
			shoufuPeople.setText(yingshou.getMakePerson());
			shoufuTime.setText(yingshou.getDate());
			shoufuTele.setText(yingshou.getTelephone());
			shoufuObject.setText(yingshou.getYingShouObject());
			if (yingshou.getStatus() == 0) {
				shoufuStatus.setText("�ѿ�ʼ");
			} else if (yingshou.getStatus() == 1) {
				shoufuStatus.setText("������");
			} else if (yingshou.getStatus() == 2) {
				shoufuStatus.setText("������");
			}
			Progress(yingshou.getStatus());
			shoufuNote.setText(yingshou.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);}
		}else if(dataclassselect.equals("ʵ��")){
			if (shishoulist.size() == 0) {
				dataFlipper.setVisibility(View.GONE);
				dataText.setVisibility(View.VISIBLE);
				yemaLayout.setVisibility(View.GONE);
			} else {
				yemaLayout.setVisibility(View.VISIBLE);
				yema.setText("��ǰ" + " " + (num + 1) + "/" + shishoulist.size()
						+ " " + "ҳ");
				yingshou = shishoulist.get(num);
				yishouRadio.setChecked(true);
			    length= shishoulist.size();
			shoufuId.setText(String.valueOf(yingshou.getId()));
			shoufuClass.setText(yingshou.getMenuName());
			shoufuCount.setText(String.valueOf(yingshou.getCount()));
			shoufuobjectText.setText("������");
			shoufuPeople.setText(yingshou.getMakePerson());
			shoufuTime.setText(yingshou.getDate());
			shoufuTele.setText(yingshou.getTelephone());
			shoufuObject.setText(yingshou.getYingShouObject());
			if (yingshou.getStatus() == 0) {
				shoufuStatus.setText("�ѿ�ʼ");
			} else if (yingshou.getStatus() == 1) {
				shoufuStatus.setText("������");
			} else if (yingshou.getStatus() == 2) {
				shoufuStatus.setText("������");
			}
			Progress(yingshou.getStatus());
			shoufuNote.setText(yingshou.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);
			
		}	}

	}

	// ��ʾyingfu����
	public void returnFuView() {
		View view = null;
		view = inflater.inflate(R.layout.shoufudata_item, null);
		initShou(view);
		YingFu yingfu = new YingFu();
		if(dataclassselect.equals("����")){
		if (yingfulist.size() == 0) {
			dataFlipper.setVisibility(View.GONE);
			dataText.setVisibility(View.VISIBLE);
			yemaLayout.setVisibility(View.GONE);
		} else {
			yemaLayout.setVisibility(View.VISIBLE);
			yema.setText("��ǰ" + " " + (num + 1) + "/" + yingfulist.size() + " "
					+ "ҳ");
			yingfu = yingfulist.get(num);
			weishouRadio.setChecked(true);
			length= yingfulist.size();
			shoufuId.setText(String.valueOf(yingfu.getId()));
			shoufuClass.setText(yingfu.getMenuName());
			shoufuCount.setText(String.valueOf(yingfu.getCount()));
			shoufuobjectText.setText("�������");
			shoufuPeople.setText(yingfu.getMakePerson());
			shoufuTime.setText(yingfu.getDate());
			shoufuTele.setText(yingfu.getTelephone());
			shoufuObject.setText(yingfu.getYingFuObject());
			if (yingfu.getStatus() == 0) {
				shoufuStatus.setText("�ѿ�ʼ");
			} else if (yingfu.getStatus() == 1) {
				shoufuStatus.setText("������");
			} else if (yingfu.getStatus() == 2) {
				shoufuStatus.setText("������");
			}
			Progress(yingfu.getStatus());
			shoufuNote.setText(yingfu.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);
		}
			}
		else if(dataclassselect.equals("ʵ��")){
			if (shifulist.size() == 0) {
				dataFlipper.setVisibility(View.GONE);
				dataText.setVisibility(View.VISIBLE);
				yemaLayout.setVisibility(View.GONE);
			} else {
				yemaLayout.setVisibility(View.VISIBLE);
				yema.setText("��ǰ" + " " + (num + 1) + "/" + shifulist.size() + " "
						+ "ҳ");
				yingfu = shifulist.get(num);
				yishouRadio.setChecked(true);
				
				length= shifulist.size();
		
			shoufuId.setText(String.valueOf(yingfu.getId()));
			shoufuClass.setText(yingfu.getMenuName());
			shoufuCount.setText(String.valueOf(yingfu.getCount()));
			shoufuobjectText.setText("�������");
			shoufuPeople.setText(yingfu.getMakePerson());
			shoufuTime.setText(yingfu.getDate());
			shoufuTele.setText(yingfu.getTelephone());
			shoufuObject.setText(yingfu.getYingFuObject());
			if (yingfu.getStatus() == 0) {
				shoufuStatus.setText("�ѿ�ʼ");
			} else if (yingfu.getStatus() == 1) {
				shoufuStatus.setText("������");
			} else if (yingfu.getStatus() == 2) {
				shoufuStatus.setText("������");
			}
			Progress(yingfu.getStatus());
			shoufuNote.setText(yingfu.getMakeNote());
			dataFlipper.setVisibility(View.VISIBLE);
			dataFlipper.addView(view);
			dataText.setVisibility(View.GONE);
		}}
		}
//��ʾ����(Count)���
public void returnCount(){
	dataFlipper.setVisibility(View.GONE);
	dataText.setVisibility(View.VISIBLE);
	yemaLayout.setVisibility(View.GONE);
}

	// ��ȡ���ݣ������ݿ��У�
	//��ȡ����
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
			Toast.makeText(DataHorizontal.this, "�������ݲ��Ϸ�!", Toast.LENGTH_LONG).show();
			seachMessage.setText("");
		}
		time = year.getText().toString() + "-"
				+ month.getText().toString()+"-"+day;
		}else{
			time = year.getText().toString() + "-"
					+ month.getText().toString();
		}
	
		if (dataclassselect.equals("����")) {
			incomelist = incomesqlite.queryAllIncomeByTime(db1, time);
		}
		if (dataclassselect.equals("֧��")) {
			paylist = paysqlite.queryAllPayByTime(db2, time);
		}
		if (dataclassselect.equals("���")) {
			yingshoulist = yingshousqlite.queryAllYingShouByTime(db3, time);
            for(int i=0;i<yingshoulist.size();i++){
            	if(yingshoulist.get(i).getProperty()==1){
            		shishoulist.add(yingshoulist.get(i));
            		yingshoulist.remove(i);
            	}
            }
		}
		if (dataclassselect.equals("����")) {
			yingfulist = yingfusqlite.queryAllYingFuByTime(db4, time);
			 for(int i=0;i<yingfulist.size();i++){
	            	if(yingfulist.get(i).getProperty()==1){
	            		shifulist.add(yingfulist.get(i));
	            		yingfulist.remove(i);
	            	}
	            }
		}
		if (dataclassselect.equals("ʵ��")) {
			yingshoulist = yingshousqlite.queryAllYingShouByTime(db3, time);
            for(int i=0;i<yingshoulist.size();i++){
            	if(yingshoulist.get(i).getProperty()==1){
            		shishoulist.add(yingshoulist.get(i));
            		yingshoulist.remove(i);
            	}
            }
		}
		if (dataclassselect.equals("ʵ��")) {
			yingfulist = yingfusqlite.queryAllYingFuByTime(db4, time);
			 for(int i=0;i<yingfulist.size();i++){
	            	if(yingfulist.get(i).getProperty()==1){
	            		shifulist.add(yingfulist.get(i));
	            		yingfulist.remove(i);
	            	}
	            }
		}

	}

	// ���߷������ظ����÷����ķ�װ��
	public void tool() {
		if (num <= 0) {
			GoPrevious.setVisibility(View.GONE);
			GoNext.setVisibility(View.VISIBLE);
		} else if (num > 0) {
			GoNext.setVisibility(View.VISIBLE);
			GoPrevious.setVisibility(View.VISIBLE);
		}
		if (dataclassselect.equals("����")) {
			if (num >= incomelist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}
			returnIncomeView();
		}
		if (dataclassselect.equals("֧��")) {
			if (num >= paylist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}
			returnPayView();
		}
		if (dataclassselect.equals("���")) {
			if (num >= yingshoulist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}

			returnShouView();
		}
		if (dataclassselect.equals("����")) {
			if (num >= yingfulist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}
			returnFuView();
		}
		if (dataclassselect.equals("ʵ��")) {
			if (num >=shishoulist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}
			returnShouView();
		}
		if (dataclassselect.equals("ʵ��")) {
			if (num >=shifulist.size() - 1) {
				GoNext.setVisibility(View.GONE);
			}
			returnFuView();
		}
		if (dataclassselect.equals("����")) {
			
			returnCount();
		}
	}

	public void Progress(int i) {
		if (dataclassselect.equals("����") || dataclassselect.equals("֧��")) {
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
		if(dataclassselect.equals("���")||dataclassselect.equals("����")){
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
		if(dataclassselect.equals("ʵ��")||dataclassselect.equals("ʵ��")){
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
	//����ʶ��
	private void startRecognizerActivity() { // ͨ��Intent��������ʶ���ģʽ����������
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // ����ģʽ������ģʽ������ʶ��
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); // ��ʾ������ʼ
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "��ʼ����"); // ��ʼ����ʶ��
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE); // ����ʶ�����
	}

	
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
			getData();
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
		number=month;
		return str;
	}
	public void AuToPlay() {
		if (!mThread.isAlive()) { // ��ʼ��ʱ��������������ʱ�������ñ��Ϊtrue
			mflag = true;
			// �ж��Ƿ��ǵ�һ������������ǲ��ǵ�һ����������ô״̬����Thread.State.TERMINATED
			// ���ǵĻ�������Ҫ���µĳ�ʼ������Ϊ֮ǰ���Ѿ������ˡ�
			// ����Ҫ�ж����mCount �Ƿ�Ϊ-1������ǵĻ���˵����һ�εļ�ʱ�Ѿ�����ˣ���ôҪ�������á�
			if (mThread.getState() == Thread.State.TERMINATED) {
				mThread = new Thread(this);
				if (mCount==length-1)
					mCount =0;
				mThread.start();
			} else {
				mThread.start();
			}
		} else { // ��ͣ��ʱ�������ñ��Ϊfalse
			mflag=false; }}
	
			//������ʹ�� stop�������ᱨ��java.lang.UnsupportedOperationException
					// //mThread.stop(); } } 
	public void run() {
					// //���̱߳���Ҫ����������mflag�͵���ʱ����
				 while(mflag&&num>=0&&num<length){ 
					 try {
						
				  Thread.sleep((long) (second*1000)); 
				  num++;
				  if (num==length){
						num =0;}} 
					 catch (InterruptedException e) {
					 e.printStackTrace(); } //ÿ��� һ���� ���� һ��Message �����̵߳�
					// handler�����̵߳�hanlder ���޸�UI //ע�� ����� message������ͨ��obtain����ȡ
					// ������ʡ�ڴ棬�����Զ��Ŀ���û�п��Ը��õģ��Ͳ��ظ�����
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
			//���öԻ����С
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
	  			if(dataclassselect=="��ֱ"){
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