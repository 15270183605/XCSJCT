package copyshouye;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.support.v4.view.*;
import loginOrRegister.LoactionAndTianQi;
import loginOrRegister.Main;
import shouye.AddIncomeorPay;
import shouye.Count;
import shouye.Data;
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
import sqlite.CountSQLite;
import sqlite.ScheduleSQLite;
import sqlite.SetTypeSQLite;
import sqlite.UserSQLite;
import tool.ChildViewPager;
import tool.HorizontalListView;
import tool.MemoryDeal;
import tool.NongLiCalendar;
import userrefreedback.UserRefreedBack;
import work.basic;
import Adapters.CalenderListViewAdapter;
import Adapters.GridViewAdapter;
import Adapters.TianQiListViewAdapter;
import Adapters.ViewPagerAdapter;
import Dialog.JSBPDialog;
import Dialog.JieZhangDataDialog;
import Dialog.NoteRemindDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.jiacaitong.R;

import entity.FestivalEntity;
import entity.Schedule;
import entity.TianQi;
import festival.Festival;
import festival.FestivalDialog;

public class copyshouye extends Activity implements OnClickListener, Runnable {
	private ImageView Right, Left, viewflipperImage, TianQiImage, LuYin,
			AdImage1, AdImage2,SearchText;
	private RadioGroup addorquery;
	private RadioButton add, query;
	private LayoutInflater inflater1;
	private LinearLayout ShouYeTopLayout, topLayout, ChangeLayout,ShouYeTwoLayout,TianQiLayout;
	private ViewFlipper ShouYeViewFlipper;
	private EditText YuYinEditText;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private int count = 0;// ��������
	public Timer mTimer;
	public TimerTask timetask;
	public int mTimeNum = 0, listviewcount = 0;
	private JSBPDialog dialog;
	private TextView viewflipperText1, viewflipperText2, Loaction,
			ShouYeLoaction, ShouYeTianQiCondition, ShouYeTianQi,
			ChineseMingYan, EnglishMingYan;
	private GridView ShouYeGridView;
	private ChildViewPager viewpagerImage;
	private Handler handler;
	private Thread thread;
	private boolean flag;
	private boolean showorhidden = true;
	private Class Class[] = { DataHorizontal.class, GraphContainer.class,GuDingCount.class,
			ManageNote.class, SystemSet.class, Help.class,UserRefreedBack.class};
	private String Text1[] = { "�������鿴������������", "�������鿴��ֱ�۵����ݲ���","�������������̶���������",
			"�������Ϊ�������ճ̰���", "����������ϵͳ����", "�������Ѱ�����", "������ﷴӦ������������" };
	private String Text2[] = { "�����Ӧ���в������ݵ�����", "ֱ�۵ķ�Ӧ�������ݵı仯���","�����ظ��������̶�����һ������",
			"�滮���ճ̣���ÿ�������������", "������Ҫ�������ã�ʹ����������", "��������������⣬ʹ����ͳ�Ƹ�˳��",
			"��ʱΪ��������⣬����Ƹ����" };
	private String GongNengText[]={"��������(����)--�ҵ�����","����֧��(֧��)--�ҵ�֧��","�������(���)--�ҵĽ��","��������(����)--�ҵĴ���","�ҵ�����(����)","���ݹ���(�ҵ�����)","����ͼ��(ͼ��)","�ճ̰���(�����ǩ)","��������(����-����-����)"};
	private List<String> ADHttp;
	private int images[] = { R.drawable.copydata, R.drawable.graph2,R.drawable.guding,
			R.drawable.time1, R.drawable.set1, R.drawable.help,
			R.drawable.feedback };
	/*private int images[] = { R.drawable.copydata, R.drawable.graph2
			 };*/
	private int Images[] = { R.drawable.income, R.drawable.pay,
			R.drawable.income2, R.drawable.pay1, R.drawable.yingshou,
			R.drawable.yingfu, R.drawable.count, R.drawable.more1 };
	private int TopImages[] = { R.drawable.picture1, R.drawable.picture2,
			R.drawable.picture3, R.drawable.viewpicture1,
			R.drawable.viewpicture10, R.drawable.viewpicture11,
			R.drawable.viewpicture5, R.drawable.viewpicture6,
		    R.drawable.viewpicture9 };
	private int ADImages[] = { R.drawable.adpicture1, R.drawable.adpicture2,
			R.drawable.adpicture3, R.drawable.adpicture4,
			R.drawable.adpicture5, R.drawable.adpicture6,
			R.drawable.adpicture7, R.drawable.adpicture8 };
	private String str[] = { "��������", "����֧��", "�ҵ�����", "�ҵ�֧��", "������", "�������",
			"�������", "����Ӧ��" };
	Animation[] animations = new Animation[6];
	private List<String> DayList;
	private int Month, Year, day;
	private String Day,CurrentDate;
	private HorizontalListView CalenderListView, TianQiList;
	private CalenderListViewAdapter adapter;
	private String Weeks[] = { "����", "��һ", "�ܶ�", "����", "����", "����", "����" };
	private BufferedReader buffer;
	private InputStream inputStream;
	private List<String> EnglishList;
	private List<String> ChineseList;
/*	private List<String> FestivalTxetView;
	private String LoactionText, City;*/
	private SetTypeSQLite settypesqlite;
	private SQLiteDatabase db1,db;
	private UserSQLite usersqlite;
	private MemoryDeal memorydeal;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.copyshouye1);
		init();
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.Left:
			count--;
			if (count == -1) {
				count = 0;
			}
			ShouYeViewFlipper.removeAllViews();
			ViewFlipperAddView(count);
			ShouYeViewFlipper.showPrevious();
			break;
		case R.id.Right:
			count++;
			if (count >= images.length) {
				count = 0;
			}
			ShouYeViewFlipper.removeAllViews();
			ViewFlipperAddView(count);
			ShouYeViewFlipper.showNext();
			break;
		case R.id.ShouYeViewFlipper:
			if(count==2){
				CheckGuDingCount();
			}else{
			Intent intent = new Intent(this, Class[count]);
			startActivity(intent);}
			break;
		case R.id.ShouYeTopLayout:
			AddTianQiListViewData();
			if (showorhidden) {
				topLayout.setVisibility(View.VISIBLE);
				ShouYeTwoLayout.setVisibility(View.GONE);
				showorhidden = false;
			} else {
				topLayout.setVisibility(View.GONE);
				ShouYeTwoLayout.setVisibility(View.VISIBLE);
				showorhidden = true;
			}
			break;
		case R.id.ChangeLayout:
			listviewcount = listviewcount + 2;
			if (listviewcount == 8) {
				listviewcount = 0;
			}
			ADChangeData(listviewcount);
			break;
		case R.id.AdImage1:
			Intent Adintent=new Intent(this,ShowAdDataActivity.class);
			Adintent.putExtra("adhttp", ADHttp.get(listviewcount));
			Adintent.putExtra("biaozhi", 0);
			startActivity(Adintent);
			break;
		case R.id.AdImage2:
			Intent Adintent1=new Intent(this,ShowAdDataActivity.class);
			Adintent1.putExtra("adhttp", ADHttp.get(listviewcount+1));
			Adintent1.putExtra("biaozhi", 0);
			startActivity(Adintent1);
			break;
		case R.id.LuYin:
			startRecognizerActivity();
			break;
		case R.id.SearchText:
			intent(YuYinEditText.getText().toString());
			YuYinEditText.setText("");
			break;
		case R.id.TianQiLayout:
			RefreshTianQiData();
			break;
		case R.id.ExitCheck:
			finish();
			break;
		case R.id.ExitCancel:
			dialog.dismiss();
			break;
		}

	}

	// ����Ӧ�ո�������
	public void setYingShouFuDialog(final int number) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(getParent(), R.style.Alert));
		View view1 = inflater1.inflate(R.layout.dialogitem, null);
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
					intent5.setClass(copyshouye.this, YingShouYingFu.class);
					startActivity(intent5);
				}
				if (query.isChecked()) {
					Intent intent6 = new Intent();
					intent6.putExtra("number", number);
					intent6.setClass(copyshouye.this, YingShouYingFuList.class);
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
	private void init() {
		memorydeal=new MemoryDeal();
		settypesqlite = new SetTypeSQLite(this, "settype.db", null, 1);
		db1 = settypesqlite.getReadableDatabase();
		usersqlite = new UserSQLite(this, "FamilyFinance.db", null, 1);
		db= usersqlite.getReadableDatabase();
		inflater1 = (LayoutInflater) copyshouye.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		DayList = new ArrayList<String>();
		EnglishList = new ArrayList<String>();
		ChineseList = new ArrayList<String>();
		//FestivalTxetView = new ArrayList<String>();
		ArrayList<ImageView> mImageList = new ArrayList<ImageView>();
		ShouYeViewFlipper = (ViewFlipper) findViewById(R.id.ShouYeViewFlipper);
		CalenderListView = (HorizontalListView) findViewById(R.id.CalenderListView);
		TianQiList = (HorizontalListView) findViewById(R.id.TianQiListView);
		ShouYeTopLayout = (LinearLayout) findViewById(R.id.ShouYeTopLayout);
		topLayout = (LinearLayout) findViewById(R.id.TopLayout);
		TianQiLayout = (LinearLayout) findViewById(R.id.TianQiLayout);
		ChangeLayout = (LinearLayout) findViewById(R.id.ChangeLayout);
		ShouYeTwoLayout = (LinearLayout) findViewById(R.id.ShouYeTwoLayout);
		ShouYeGridView = (GridView) findViewById(R.id.ShouYeGridView);
		viewpagerImage = (ChildViewPager) findViewById(R.id.viewpagerImage);
		Loaction = (TextView) findViewById(R.id.Loaction);
		ShouYeLoaction = (TextView) findViewById(R.id.ShouYeLoaction);
		ShouYeTianQiCondition = (TextView) findViewById(R.id.ShouYeTianQiCondition);
		ShouYeTianQi = (TextView) findViewById(R.id.ShouYeTianQi);
		EnglishMingYan = (TextView) findViewById(R.id.EnglishMingYan);
		ChineseMingYan = (TextView) findViewById(R.id.ChineseMingYan);
		TianQiImage = (ImageView) findViewById(R.id.TianQiImage);
		LuYin = (ImageView) findViewById(R.id.LuYin);
		AdImage1 = (ImageView) findViewById(R.id.AdImage1);
		AdImage2 = (ImageView) findViewById(R.id.AdImage2);
		SearchText=(ImageView)findViewById(R.id.SearchText);
		YuYinEditText = (EditText) findViewById(R.id.YuYinEditText);
		ShouYeViewFlipper.setOnClickListener(this);
		ShouYeTopLayout.setOnClickListener(this);
		ChangeLayout.setOnClickListener(this);
		LuYin.setOnClickListener(this);
		AdImage1.setOnClickListener(this);
		AdImage2.setOnClickListener(this);
		SearchText.setOnClickListener(this);
		TianQiLayout.setOnClickListener(this);
		animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in);
		animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out);
		animations[2] = AnimationUtils.loadAnimation(this, R.anim.slide_in);
		animations[3] = AnimationUtils
				.loadAnimation(this, R.anim.slide_out);
		animations[4] = AnimationUtils.loadAnimation(this,
				R.anim.copyslide_out);
		animations[5] = AnimationUtils.loadAnimation(this,
				R.anim.copyslide_out);
		LuYin();
		ViewFlipperAddView(count);
		GridViewClick();
		EditListener();
		thread = new Thread(this);
		AutoLoad();
		handler = new Handler() {
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				int what = msg.what;
				switch (what) {
				case 1:
					ShouYeViewFlipper.removeAllViews();
					ViewFlipperAddView(count);
					ShouYeViewFlipper.showNext();
					break;
				case 2:
					setmyPagerIndex();
					break;
				}
			}
		};
	
	for (int i = 0; i < TopImages.length; i++) { ImageView nowImageView =
		  new ImageView(this); nowImageView.setImageResource(TopImages[i]);
		  mImageList.add(nowImageView);
		  
		  }
	/*ImageView nowImageView = new ImageView(this);
		nowImageView.setImageResource(R.drawable.viewpicture9);
		mImageList.add(nowImageView);
*/
		// ����ͼƬ���ֲ�(Banner)
		ViewPagerAdapter mViewPagerAdapter = new ViewPagerAdapter(mImageList,
				this);
		viewpagerImage.setAdapter(mViewPagerAdapter);
		// ����ListView
		AddDay();
		adapter = new CalenderListViewAdapter(this, DayList, Month, Year, Day);
		CalenderListView.setAdapter(adapter);

		CalenderListView.scrollTo((this.getWindowManager().getDefaultDisplay()
				.getWidth()) / 7 * (day - 1)-21);

		// GridView��ʾ����
		GridViewAdapter gridviewadapter = new GridViewAdapter(this, Images, str,0);
		ShouYeGridView.setAdapter(gridviewadapter);
		startTimer();
		readMingYanFile();// ��ȡ����
		Random random = new Random();
		int MingYanCount = random.nextInt(95);// ����100���ڵ������
		EnglishMingYan.setText(EnglishList.get(MingYanCount));
		ChineseMingYan.setText(ChineseList.get(MingYanCount));
		ADChangeData(listviewcount);
		RefreshTianQiData();
		FestivalDialog();
		NoteRemindDialog();
		if(!((usersqlite.QueryUserTime(db,
		Main.returnName(), Main.returnPsd())).substring(0,7)).equals(CurrentDate)){
		 SetJiZhangDialog();}
		 //SetJiZhangDialog();
	}
	// �Զ�����ViewFlipper
	public void AutoLoad() {
		if (!thread.isAlive()) {
			flag = true;
			if (thread.getState() == Thread.State.TERMINATED) {
				thread = new Thread(this);
				thread.start();
			} else {
				thread.start();

			}
		} else {
			flag = false;
		}
	}

	@Override
	public void run() {

		while (flag && count < images.length && count >= 0) {
			try {
				thread.sleep((long) (4000));
				count++;
				 
				if (count == images.length) {
					count = 0;
				}// ��δ����ǹؼ�
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message message = Message.obtain();
			message.what = 1;
			handler.sendMessage(message);
		}

	}

	// ������ҳˮƽflipper����
	public void ViewFlipperAddView(int num) {
		View view = LayoutInflater.from(this).inflate(
				R.layout.shoueyeviewflipperitem, null);
		viewflipperImage = (ImageView) view.findViewById(R.id.viewflipperImage);
		viewflipperText1 = (TextView) view.findViewById(R.id.viewflipperText1);
		viewflipperText2 = (TextView) view.findViewById(R.id.viewflipperText2);
		Left = (ImageView) view.findViewById(R.id.Left);
		Right = (ImageView) view.findViewById(R.id.Right);
		Left.setOnClickListener(this);
		Right.setOnClickListener(this);
		viewflipperImage.setImageBitmap(memorydeal.getBitmap(images[num], this));
		viewflipperText1.setText(Text1[num]);
		viewflipperText2.setText(Text2[num]);
		if (num == 0) {
			Left.setVisibility(View.GONE);
		} else {
			Left.setVisibility(View.VISIBLE);
		}
		if (num == images.length - 1) {
			Right.setVisibility(View.GONE);
		} else {
			Right.setVisibility(View.VISIBLE);
		}
		ShouYeViewFlipper.addView(view);
		ShouYeViewFlipper.setInAnimation(animations[0]);
		ShouYeViewFlipper.setOutAnimation(animations[1]);
	}

	// ��ȡ��ǰʱ������
	public void AddDay() {
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
		CurrentDate=format.format(date);
		Year = cal.get(Calendar.YEAR);
		Month = cal.get(Calendar.MONTH) + 1;
		day = cal.get(Calendar.DAY_OF_MONTH);
		// Week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (day < 10) {
			Day = "0" + day;
		} else {
			Day = String.valueOf(day);
		}
		int number = cal.getActualMaximum(Calendar.DATE);
		if(Month!=1){
			Calendar calendar=Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            try {
				calendar.setTime(sdf.parse(String.valueOf(Year)+"-"+String.valueOf(Month-1)));
				int num=calendar.getActualMaximum(Calendar.DATE);
				if(Month-1<10){
				DayList.add("0"+(Month-1)+"-"+(num-2));
				DayList.add("0"+(Month-1)+"-"+(num-1));
				DayList.add("0"+(Month-1)+"-"+(num));}
				else{
					DayList.add((Month-1)+"-"+(num-2));
					DayList.add((Month-1)+"-"+(num-1));
					DayList.add((Month-1)+"-"+(num));
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String year=String.valueOf(Year-1).substring(1, String.valueOf(Year-1).length()-1);
			DayList.add(year+"-"+"12"+"-"+"29");
			DayList.add(year+"-"+"12"+"-"+"30");
			DayList.add(year+"-"+"12"+"-"+"31");
		}
		
		for (int i = 1; i <= number; i++) {
			if (i < 10) {
				DayList.add("0" + i);
			} else {
				DayList.add(String.valueOf(i));
			}
		}
		if(Month!=12){
			if(Month+1<10){
			DayList.add("0"+(Month+1)+"-"+"01");
			DayList.add("0"+(Month+1)+"-"+"02");
			DayList.add("0"+(Month+1)+"-"+"03");
		}else{
			DayList.add((Month+1)+"-"+"01");
			DayList.add((Month+1)+"-"+"02");
			DayList.add((Month+1)+"-"+"03");
		}
			}else{
				String year=String.valueOf(Year+1).substring(1, String.valueOf(Year+1).length()-1);
				
					DayList.add(year+"-"+"01"+"-"+"01");
					DayList.add(year+"-"+"01"+"-"+"02");
					DayList.add(year+"-"+"01"+"-"+"03");
				
			}
		
	}

	// ��ʾ��������
	public void AddTianQiListViewData() {
		if (LoactionAndTianQi.returnLoactionTotal() != null
				&& LoactionAndTianQi.returnTainQiDatas().size() != 0) {
			Loaction.setText(LoactionAndTianQi.returnLoactionTotal());
			TianQiListViewAdapter tianqiadapter = new TianQiListViewAdapter(
					this, LoactionAndTianQi.returnTainQiDatas());
			TianQiList.setAdapter(tianqiadapter);
		} else {
			Toast.makeText(this, "��ȡ����ʧ�ܣ������½���", Toast.LENGTH_LONG).show();
		}
	}

	// GridView�ĵ���¼�
	public void GridViewClick() {
		ShouYeGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parnet, View view,
					int position, long id) {
				if (position == 0) {
					Intent intent1 = new Intent();
					intent1.putExtra("number", 1);
					intent1.setClass(copyshouye.this, AddIncomeorPay.class);
					startActivity(intent1);
				}
				if (position == 1) {
					Intent intent2 = new Intent();
					intent2.putExtra("number", 2);
					intent2.setClass(copyshouye.this, AddIncomeorPay.class);
					startActivity(intent2);
				}
				if (position == 2) {
					Intent intent3 = new Intent();
					intent3.putExtra("number", 3);
					intent3.setClass(copyshouye.this, MyIncome.class);
					startActivity(intent3);
				}

				if (position == 3) {
					Intent intent4 = new Intent();
					intent4.putExtra("number", 4);
					intent4.setClass(copyshouye.this, MyPay.class);
					startActivity(intent4);
				}
				if (position == 4) {
					setYingShouFuDialog(5);
				}
				if (position == 5) {
					setYingShouFuDialog(6);
				}
				if (position == 6) {
					Intent intent5 = new Intent(copyshouye.this, Count.class);
					startActivity(intent5);
				}
				if (position == 7) {
					Intent it = new Intent(copyshouye.this, MoreApply.class);
					startActivity(it);
				}

			}
		});
	}

	// ����ViewPager����ʱ��
	public void startTimer() {
		mTimer = new Timer();
		timetask = new TimerTask() {
			public void run() {
				mTimeNum++;
				handler.sendEmptyMessage(2);
			}
		};
		mTimer.schedule(timetask, 8 * 1000, 8 * 1000);
	}

	// ViewPage��������
	public void setmyPagerIndex() {
		try {
			if (mTimeNum >= TopImages.length) {
				mTimeNum = 0;
			}
			viewpagerImage.setCurrentItem(mTimeNum, true);
			SpannableString spannable = new SpannableString(GongNengText[mTimeNum]);//���������Լ���Ҫ����ʾ����
			YuYinEditText.setHint(spannable);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ����ֹͣ��
	public void stopTask() {
		try {
			mTimer.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���й����Ե��ļ�
	public void readMingYanFile() {
		int count = 0;
		try {
			inputStream = getAssets().open("mingyan.txt");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					inputStream, "GBK"));
			String temp = "";
			while ((temp = buffer.readLine()) != null) {
				count = count + 1;
				if ((count % 2) == 0) {
					ChineseList.add(temp);
				} else {
					EnglishList.add(temp);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}

	// ������ַ���ļ�
	public void readAdHttpFile() {
		ADHttp = new ArrayList<String>();
		int count = 0;
		try {
			inputStream = getAssets().open("adhttp.txt");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					inputStream, "GBK"));
			String temp = "";
			while ((temp = buffer.readLine()) != null) {
				ADHttp.add(temp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// ���ý��յ�����
	public void FestivalDialog() {
		FestivalDialog fesDialog = null;
		int num = 0;
		Festival fes = new Festival();
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		String time = null;
		if (Month < 10) {
			time = "0" + String.valueOf(Month) + Day;
		} else {
			time = String.valueOf(Month) + Day;
		}
		NongLiCalendar calendar = new NongLiCalendar(cal);
		String nontime = calendar.chineseNumber[calendar.month - 1] + "��"
				+ calendar.getChinaDayString(calendar.day);
		for (Map.Entry<String, FestivalEntity> entry : fes.returnFestival()
				.entrySet()) {
			if (entry.getKey().equals(time)) {
				num = 1;
				break;
			} else if (entry.getKey().equals(nontime)) {
				num = 2;
				break;
			}
		}
		if (num != 0) {
			if (num == 1) {
				fesDialog = new FestivalDialog(getParent(), fes
						.returnFestival().get(time).getFestivalImage(), fes
						.returnFestival().get(time).getFestivalText(), fes
						.returnFestival().get(time).getHttp());
			} else if (num == 2) {
				fesDialog = new FestivalDialog(getParent(), fes
						.returnFestival().get(nontime).getFestivalImage(), fes
						.returnFestival().get(nontime).getFestivalText(), fes
						.returnFestival().get(nontime).getHttp());
			}
			fesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			fesDialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			fesDialog.show();
			fesDialog.setCancelable(false);
		}
	}

	// �ճ����ѵ�����
	public void NoteRemindDialog() {
		int num = 0;
		ScheduleSQLite schedulesqlite = new ScheduleSQLite(this, "Schedule.db",
				null, 1);

		SQLiteDatabase db = schedulesqlite.getReadableDatabase();
		Schedule schedule;
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK);
		NongLiCalendar calendar = new NongLiCalendar(cal);
		String nontime = calendar.chineseNumber[calendar.month - 1] + "��"
				+ calendar.getChinaDayString(calendar.day);

		if (Month < 10) {
			schedule = schedulesqlite.queryByTime1(db, String.valueOf(Year)
					+ "-" + "0" + String.valueOf(Month) + "-" + Day);
		} else {
			schedule = schedulesqlite.queryByTime1(db, String.valueOf(Year)
					+ "-" + String.valueOf(Month) + "-" + Day);
		}

		Cursor cursor = settypesqlite.returnType(db1, "�ճ�����");
		if (cursor.moveToFirst()) {
			num = cursor.getInt(cursor.getColumnIndex("SetTypeNum"));
		}
		  cursor.close();
		NoteRemindDialog dialog = new NoteRemindDialog(getParent(),
				String.valueOf(Year) + "-" + "0" + String.valueOf(Month) + "-"
						+ Day + "   ũ��" + nontime + "   "
						+ Weeks[week_index - 1], schedule, day);
		if (num == 2 && dialog.returnNumber() == 0
				&& schedule.getTime().toString().trim().length() != 0) {
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			dialog.show();
			dialog.setCancelable(false);
		}
	}

	// ���ý��˵�����
	public void SetJiZhangDialog() {
		String time;
		int CountYear;
		int Num = 0;
		int Num1 = 1;// �жϽ��˷�ʽ���ֶ����Զ���
		CountSQLite countsqlite = new CountSQLite(this, "TotalCount.db", null,
				1);
		SQLiteDatabase Countdb = countsqlite.getReadableDatabase();
		if (Month == 1) {
			time = (Year - 1) + "-" + 12;
			Num = countsqlite.QueryNumByDate(Countdb, time);
			CountYear = Year - 1;
		} else {
			if (Month - 1 < 10) {
				time = Year + "-" + "0" + (Month - 1);
			} else {
				time = Year + "-" + (Month - 1);
			}
			Num = countsqlite.QueryNumByDate(Countdb, time);
			CountYear = Year;
		}
		Cursor cursor = settypesqlite.returnType(db1, "���˷�ʽ����");
		if (cursor.getCount() == 0) {
			settypesqlite.AddSet(db1, "���˷�ʽ����", 1);
		}
		if (cursor.moveToFirst()) {
			Num1 = cursor.getInt(cursor.getColumnIndex("SetTypeNum"));
		}
		  cursor.close();
		if (Num == 0) {
			JiZhangDialogInit(time, CountYear, Num1);
		}

	}

	// �Խ��˵�����ĳ�ʼ��
	public void JiZhangDialogInit(String time, int year, int num) {
		JieZhangDataDialog dialog = new JieZhangDataDialog(getParent(), time, year,
				num, 0);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.show();
		dialog.setCancelable(false);
	}
	// �Թ��ListView��ӵ�������¼�
	public void ADChangeData(int count) {
		readAdHttpFile();
		List<Integer> images = new ArrayList<Integer>();
		for (int i = count; i < count + 2; i++) {
			images.add(ADImages[i]);
		}
        AdImage1.setImageResource(images.get(0));
        AdImage2.setImageResource(images.get(1));
	}
//��EditText���ı�����
	public void EditListener(){
		YuYinEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			 intent(YuYinEditText.getText().toString());
				
			}
		});
	}
	public void LuYin(){
		PackageManager pm = getPackageManager();
		List activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		/*
		if (activities.size() != 0) {
			LuYin.setOnClickListener(this);
		} else { // ����ⲻ������ʶ������ڱ�����װ���⽫Ť��û�
			LuYin.setEnabled(false);
			}
*/
	}
	// ��ʼʶ��
		private void startRecognizerActivity() { // ͨ��Intent��������ʶ���ģʽ����������
			Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // ����ģʽ������ģʽ������ʶ��
			intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
					RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); // ��ʾ������ʼ
			intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "��ʼ����"); // ��ʼ����ʶ��
			//����ͨ����������������
			getParent().startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE); // ����ʶ�����
			
		}
		//����Ҫ��һ��,ע�⣺
//onActivityResult���ڵ���ģʽ����ActivityGroup�����ǵĻص���������д�ڸ������棬Ȼ��ͨ����ȡ��ǰ��Activity����������������
		public void handleActivityResult(int requestCode, int resultCode, Intent data){
			if (requestCode == VOICE_RECOGNITION_REQUEST_CODE
					) { // ȡ���������ַ�
				ArrayList<String> results = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
				String resultString = "";
				for (int i = 0; i < results.size(); i++) {
					resultString += results.get(i);
				}
				String msg = resultString.substring(0, resultString.length() - 1);
				YuYinEditText.setText(msg);
				
			} 
			
			}
		public void intent(String msg) {
			if (msg.equals("��������") || msg.equals("����")) {
				Intent intent1 = new Intent();
				intent1.putExtra("number", 1);
				intent1.setClass(this, AddIncomeorPay.class);
				startActivity(intent1);
			}
			if (msg.equals("����֧��") || msg.equals("֧��")) {
				Intent intent1 = new Intent();
				intent1.putExtra("number", 2);
				intent1.setClass(this, AddIncomeorPay.class);
				startActivity(intent1);
			}
			if (msg.equals("�ҵ�����")) {
				Intent intent3 = new Intent();
				intent3.putExtra("number", 3);
				intent3.setClass(this, MyIncome.class);
				startActivity(intent3);
			}
			if (msg.equals("�ҵ�֧��")) {
				Intent intent3 = new Intent();
				intent3.putExtra("number", 4);
				intent3.setClass(this, MyPay.class);
				startActivity(intent3);
			}
			if (msg.equals("����Ӧ��") || msg.equals("Ӧ��")) {
				Intent intent1 = new Intent();
				intent1.putExtra("number", 5);
				intent1.setClass(this, YingShouYingFu.class);
				startActivity(intent1);
			}
			if (msg.equals("�ҵ�Ӧ��")) {
				Intent intent1 = new Intent();
				intent1.putExtra("number", 5);
				intent1.setClass(this, YingShouYingFuList.class);
				startActivity(intent1);
			}
			if (msg.equals("����Ӧ��") || msg.equals("Ӧ��")) {
				Intent intent1 = new Intent();
				intent1.putExtra("number", 6);
				intent1.setClass(this, YingShouYingFu.class);
				startActivity(intent1);
			}
			if (msg.equals("�ҵ�Ӧ��")) {
				Intent intent1 = new Intent();
				intent1.putExtra("number", 6);
				intent1.setClass(this, YingShouYingFuList.class);
				startActivity(intent1);
			}
			if (msg.equals("�ҵ�����") || msg.equals("����")) {
				Intent intent = new Intent(this, Count.class);
				startActivity(intent);
			}
			if (msg.equals("���ݹ���") || msg.equals("�ҵ�����")) {
				Intent intent = new Intent(this, Data.class);
				startActivity(intent);
			}
			if (msg.equals("����ͼ��") || msg.equals("ͼ��")) {
				Intent intent = new Intent(this, GraphContainer.class);
				startActivity(intent);

			}
			if (msg.equals("�ճ̰���") || msg.equals("�����ǩ")) {
				Intent intent = new Intent(this, ManageNote.class);
				startActivity(intent);
			}
			if (msg.equals("����") || msg.equals("ϵͳ����")) {
				Intent intent = new Intent(this, SystemSet.class);
				startActivity(intent);
			}
			if (msg.equals("����") || msg.equals("ϵͳ����")) {
				Intent intent = new Intent(this, Help.class);
				startActivity(intent);
			}
			if (msg.equals("����") || msg.equals("�û�����")) {
				Intent intent = new Intent(this, UserRefreedBack.class);
				startActivity(intent);
			}
			
		}
		//ˢ����������
		public void RefreshTianQiData(){
			if(basic.netState!=false && LoactionAndTianQi.returnTainQiDatas().size()!=0){
				TianQi tianqi=new TianQi();
				tianqi=LoactionAndTianQi.returnTainQiDatas().get(0);
				 ShouYeLoaction.setText(LoactionAndTianQi.returnCity());
				  ShouYeTianQiCondition.setText(tianqi.getCondition()); 
				  ShouYeTianQi.setText(tianqi.getTianqi());
				  TianQiImage.setImageResource(tianqi.getImage());} 
				  else{ Toast.makeText(this, "�����쳣����ȡ����ʧ��!", Toast.LENGTH_LONG);
				  }
		}
		//���̶������Ƿ��
		public void CheckGuDingCount(){
			 SetTypeSQLite  settypesqlite=new SetTypeSQLite(copyshouye.this,"settype.db", null, 1);;
			   SQLiteDatabase db1=settypesqlite.getReadableDatabase();
			Cursor cursor=settypesqlite.returnType(db1, "�̶�����(�磺����)�Զ��뵥");
			if(cursor.getCount()==0){
				settypesqlite.AddSet(db1, "�̶�����(�磺����)�Զ��뵥", 1);
				}else if(cursor.moveToFirst()){
					if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==1){
						Toast.makeText(copyshouye.this, "��Ǹ���̶�����δ�򿪣������������д򿪹̶�����!", Toast.LENGTH_LONG).show();
					}
					if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==2){
						Intent intent1 = new Intent(copyshouye.this, GuDingCount.class);
						startActivity(intent1);
					}
					
				}
		}
		  public boolean onKeyDown(int keyCode, KeyEvent event) {   
				 switch (keyCode) {    	 
				 case KeyEvent.KEYCODE_BACK: 
						View view = LayoutInflater.from(this).inflate(R.layout.exitjiezhangcheck, null);
						 dialog=new JSBPDialog(getParent(), view);
						TextView ExitCheck=(TextView)view.findViewById(R.id.ExitCheck);
						TextView ExitCancel=(TextView)view.findViewById(R.id.ExitCancel);
						ExitCheck.setOnClickListener(this);
						ExitCancel.setOnClickListener(this);
						dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
						dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
						dialog.show();
					 break;    	}    	
				 return false;	    }
}
