package shouye;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sqlite.IncomeSQLite;
import sqlite.PaySQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import Adapters.TemplateAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.Income;
import entity.Pay;
import entity.Template;
import entity.YingFu;
import entity.YingShou;

public class Data extends Activity implements OnClickListener, OnScrollListener {
	private TextView shouText, zhiText, yingshouText, yingfuText, shishouText,
			shifuText, addYear, year, cutYear, addMonth, month, cutMonth,
			IncomeCountText, PayCountText, YingShouCountText, YingFuCountText,
			ShiShouCountText, ShiFuCountText;
	private ListView templateListView;
	private ImageView returnTop, image1, image2, image3, image4, image5,
			image6, SeachLuYin, seach, downlist, ToBottom;
	private EditText seachMessage;
	private TextView[] textview = { shouText, zhiText, yingshouText,
			yingfuText, shishouText, shifuText };
	private ImageView[] imageview = { image1, image2, image3, image4, image5,
			image6 };
	private List<Income> incomelist;
	private List<Pay> paylist;
	private List<YingShou> yingshoulist;
	private List<YingFu> yingfulist;
	private List<YingShou> shishoulist;
	private List<YingFu> shifulist;
	private List<Template> templatelist;
	private IncomeSQLite incomesqlite;
	private PaySQLite paysqlite;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private SQLiteDatabase db1, db2, db3, db4;
	private int lastVisibleItem = 0;
	private LayoutInflater inflater;
	private LinearLayout bottomView;
	private int count;// 暂存数据量的变量
	private int number;// 暂存月份的变量
	private String day = "";
	private TemplateAdapter templateadapter;
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
	private double IncomeCount, PayCount, YingShouCount, YingFuCount,
			ShiShouCount, ShiFuCount;// 暂存对各表单求和Count量
    
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.data);
		init();
		listviewClick();

	}

	// ListView滚动监听反馈方法
	public void onScroll(AbsListView view, int firstItem, int visibleItemCount,
			int totalCount) {
		if (firstItem <= incomelist.size()-1) {
			count = incomelist.size();
			initImageandText(0);
		} else if (firstItem <= (count + paylist.size() - 1)
				&& firstItem >=count) {
			count = count + paylist.size() - 1;
			initImageandText(1);
		} else if (firstItem <=(count + yingshoulist.size() - 1)
				&& firstItem >= count) {
			count = count + yingshoulist.size() - 1;
			initImageandText(2);
		} else if (firstItem <=(count + yingfulist.size() - 1)
				&& firstItem >= count) {
			count = count + yingfulist.size() - 1;
			initImageandText(3);
		} else if (firstItem <=(count + shishoulist.size() - 1)
				&& firstItem >= count) {
			count = count + shishoulist.size() - 1;
			initImageandText(4);
		} else if (firstItem <=(totalCount)
				&& firstItem >= totalCount - (shifulist.size() - 1)) {
			count = count + yingfulist.size() - 1;
			initImageandText(5);
		}
		if (firstItem >= lastVisibleItem) {
			returnTop.setVisibility(View.VISIBLE);
		} else if (firstItem <= lastVisibleItem) {
			returnTop.setVisibility(View.GONE);
		}
		lastVisibleItem = firstItem;
		if (visibleItemCount == totalCount) {
			textview[0].setTextColor(Color.RED);
			returnTop.setVisibility(View.GONE);
		}
	}

	// 监听ListView滚动状态
	public void onScrollStateChanged(AbsListView view, int state) {
		switch (state) {
		case OnScrollListener.SCROLL_STATE_IDLE:// 停止滚动

			if (templateListView.getLastVisiblePosition() == (templateListView
					.getCount() - 1)) {
				returnTop.setVisibility(View.VISIBLE);
			} else if (templateListView.getFirstVisiblePosition() == 0) {
				returnTop.setVisibility(View.GONE);
			}
			break;
		case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时；

			break;
		case OnScrollListener.SCROLL_STATE_FLING:// 产生惯性滑;

			break;
		}

	}

	// 控件点击的响应
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.returnTop:
			templateListView.setSelection(0);
			initImageandText(0);
			ToBottom.setVisibility(View.VISIBLE);
			break;
		case R.id.addYear1:
			year.setText(String.valueOf(Integer.valueOf(year.getText()
					.toString()) + 1));
			getData();
			break;
		case R.id.cutYear1:
			year.setText(String.valueOf(Integer.valueOf(year.getText()
					.toString()) - 1));
			getData();
			break;
		case R.id.addMonth1:
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
			break;
		case R.id.cutMonth1:
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
			break;
		case R.id.shouText:
			templateListView.setSelection(0);
			initImageandText(0);
			break;
		case R.id.zhiText:
			templateListView.setSelection(incomelist.size());
			initImageandText(1);
			break;
		case R.id.yingshouText:
			templateListView.setSelection(incomelist.size() - 1
					+ paylist.size());
			initImageandText(2);
			break;
		case R.id.yingfuText:
			templateListView.setSelection(incomelist.size() + paylist.size()
					+ yingshoulist.size() - 1);
			initImageandText(3);
			break;
		case R.id.shishouText:
			templateListView.setSelection(incomelist.size() + paylist.size()
					+ yingshoulist.size() + yingfulist.size() - 1);
			initImageandText(4);
			break;
		case R.id.shifuText:
			templateListView.setSelection(templateListView.getCount()
					- shifulist.size());
			initImageandText(5);
			break;
		case R.id.SeachLuYin:
			startRecognizerActivity();
			break;
		case R.id.downlist:
			Intent intent = new Intent(this, DataHorizontal.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.seach:
			getData();
			break;
		case R.id.ToBottom:
			templateListView.setSelection(templatelist.size() - 1);
			returnTop.setVisibility(View.VISIBLE);
			ToBottom.setVisibility(View.GONE);
			break;
		}

	}

	// 控件初始化
	public void init() {
		textview[0]= (TextView) findViewById(R.id.shouText);
		textview[1]= (TextView) findViewById(R.id.zhiText);
		textview[2]= (TextView) findViewById(R.id.yingshouText);
		textview[3]= (TextView) findViewById(R.id.yingfuText);
		textview[4]= (TextView) findViewById(R.id.shishouText);
		textview[5]= (TextView) findViewById(R.id.shifuText);
		imageview[0]= (ImageView) findViewById(R.id.image1);
		imageview[1]= (ImageView) findViewById(R.id.image2);
		imageview[2]= (ImageView) findViewById(R.id.image3);
		imageview[3]= (ImageView) findViewById(R.id.image4);
		imageview[4]= (ImageView) findViewById(R.id.image5);
		imageview[5]= (ImageView) findViewById(R.id.image6);
		addYear = (TextView) findViewById(R.id.addYear1);
		year = (TextView) findViewById(R.id.Year1);
		cutYear = (TextView) findViewById(R.id.cutYear1);
		addMonth = (TextView) findViewById(R.id.addMonth1);
		month = (TextView) findViewById(R.id.Month1);
		cutMonth = (TextView) findViewById(R.id.cutMonth1);
		returnTop = (ImageView) findViewById(R.id.returnTop);
		seach = (ImageView) findViewById(R.id.seach);
		downlist = (ImageView) findViewById(R.id.downlist);
		SeachLuYin = (ImageView) findViewById(R.id.SeachLuYin);
		ToBottom = (ImageView) findViewById(R.id.ToBottom);
		templateListView = (ListView) findViewById(R.id.templateListView);

		seachMessage = (EditText) findViewById(R.id.seachMessage);
		returnTop.setOnClickListener(this);
		addYear.setOnClickListener(this);
		cutYear.setOnClickListener(this);
		addMonth.setOnClickListener(this);
		cutMonth.setOnClickListener(this);
		textview[0].setOnClickListener(this);
		textview[1].setOnClickListener(this);
		SeachLuYin.setOnClickListener(this);
		textview[2].setOnClickListener(this);
		textview[3].setOnClickListener(this);
		textview[4].setOnClickListener(this);
		textview[5].setOnClickListener(this);
		downlist.setOnClickListener(this);
		seach.setOnClickListener(this);
		ToBottom.setOnClickListener(this);
		// 获取当前时间；
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		number = cal.get(Calendar.MONTH)+1;
		year.setText(String.valueOf(cal.get(Calendar.YEAR)));
		if (number < 10) {
			month.setText("0" + String.valueOf(number));
		} else {
			month.setText(String.valueOf(number));
		}

		incomelist = new ArrayList<Income>();
		paylist = new ArrayList<Pay>();
		yingshoulist = new ArrayList<YingShou>();
		yingfulist = new ArrayList<YingFu>();
		shishoulist = new ArrayList<YingShou>();
		shifulist = new ArrayList<YingFu>();
		templatelist = new ArrayList<Template>();
		incomesqlite = new IncomeSQLite(this, "income.db", null, 1);
		db1 = incomesqlite.getReadableDatabase();
		paysqlite = new PaySQLite(this, "pay.db", null, 1);
		db2 = paysqlite.getReadableDatabase();
		yingshousqlite = new YingShouSQLite(this, "yingshou.db", null, 1);
		db3 = yingshousqlite.getReadableDatabase();
		yingfusqlite = new YingFuSQLite(this, "yingfu.db", null, 1);
		db4 = yingfusqlite.getReadableDatabase();
		inflater = LayoutInflater.from(this);
		AddFootView();
		getData();
		templateListView.setOnScrollListener(this);
		AddEditTextChangeListener();
	}

	// 获取数据
	// 获取数据
	public void getData() {

		templatelist.clear();
		incomelist.clear();
		paylist.clear();
		yingshoulist.clear();
		yingfulist.clear();
		day = "";
		String time = null;
		if (!(seachMessage.getText().toString()).equals("")) {
			if ((Integer.parseInt(seachMessage.getText().toString()) < 10)
					&& (Integer.parseInt(seachMessage.getText().toString()) > 0)) {
				day = "0" + seachMessage.getText().toString();
			} else if ((Integer.parseInt(seachMessage.getText().toString()) >= 10)
					&& (Integer.parseInt(seachMessage.getText().toString()) <= 31)) {
				day = seachMessage.getText().toString();
			} else {
				Toast.makeText(Data.this, "输入数据不合法!", 1000).show();
				seachMessage.setText("");
			}
			time = year.getText().toString() + "-"
					+ month.getText().toString() + "-" + day;
		}else{
			time = year.getText().toString() + "-"
					+ month.getText().toString();
		}
		
		Cursor cursor1 = incomesqlite.queryIncomeTimeUp(db1, time);
		Cursor cursor2 = paysqlite.queryPayTimeUp(db2, time);
		Cursor cursor3 = yingshousqlite.queryAllYingShouByTimeAndProperty(db3,
				time, "0");
		Cursor cursor5 = yingshousqlite.queryAllYingShouByTimeAndProperty(db3,
				time, "1");
		Cursor cursor4 = yingfusqlite.queryAllYingFuByTimeAndProperty(db4,
				time, "0");
		Cursor cursor6 = yingfusqlite.queryAllYingFuByTimeAndProperty(db4,
				time, "1");
		IncomeCount = incomesqlite.TotalCount(db1, time);
		PayCount = paysqlite.TotalCount(db2, time);
		YingShouCount = yingshousqlite.TotalCount(db3, time, "0");
		ShiShouCount = yingshousqlite.TotalCount(db3, time, "1");
		YingFuCount = yingfusqlite.TotalCount(db4, time, "0");
		ShiFuCount = yingfusqlite.TotalCount(db4, time, "1");
		if (cursor1.getCount() == 0) {
			Template template = new Template();
			Income income = new Income();
			income.setId(1);
			template.setCount(0.0);
			if (day == "") {
				template.setSource("本月无收入");
			} else {
				template.setSource("本日无收入");
			}
			incomelist.add(income);
			templatelist.add(template);
		} else {
			while (cursor1.moveToNext()) {
				Template template = new Template();
				Income income = new Income();
				income.setId(Integer.valueOf(cursor1.getString(cursor1
						.getColumnIndex("id"))));
				tool(template, cursor1, 1);
				template.setSource(cursor1.getString(cursor1
						.getColumnIndex("IncomeSource")));
				templatelist.add(template);
				incomelist.add(income);
			}
		}
		
		if (cursor2.getCount() == 0) {
			Template template = new Template();
			Pay pay = new Pay();
			pay.setId(1);
			template.setCount(0.0);
			if (day == "") {
				template.setSource("本月无支出");
			} else {
				template.setSource("本日无支出");
			}

			paylist.add(pay);
			templatelist.add(template);
		} else {
			while (cursor2.moveToNext()) {
				Template template = new Template();
				Pay pay = new Pay();
				pay.setId(Integer.valueOf(cursor2.getString(cursor2
						.getColumnIndex("id"))));
				tool(template, cursor2, 2);
				template.setSource(cursor2.getString(cursor2
						.getColumnIndex("PayTo")));
				templatelist.add(template);
				paylist.add(pay);
			}
		}
		
		if (cursor3.getCount() == 0) {
			Template template = new Template();
			YingShou yingshou = new YingShou();
			yingshou.setId(1);
			template.setCount(0.0);
			if (day == "") {
				template.setSource("本月无借款往来帐");
			} else {
				template.setSource("本日无借款往来帐");
			}

			yingshoulist.add(yingshou);
			templatelist.add(template);
		} else {
			while (cursor3.moveToNext()) {
				Template template = new Template();
				YingShou yingshou = new YingShou();
				yingshou.setId(Integer.valueOf(cursor3.getString(cursor3
						.getColumnIndex("id"))));
				tool(template, cursor3, 3);
				template.setSource(cursor3.getString(cursor3
						.getColumnIndex("YingShouSource")));
				templatelist.add(template);
				yingshoulist.add(yingshou);
			}
		}
		
		if (cursor4.getCount() == 0) {
			Template template = new Template();
			YingFu yingfu = new YingFu();
			yingfu.setId(1);
			template.setCount(0.0);
			if (day == "") {
				template.setSource("本月无贷款往来帐");
			} else {
				template.setSource("本日无贷款往来帐");
			}
			yingfulist.add(yingfu);
			templatelist.add(template);
		} else {
			while (cursor4.moveToNext()) {
				Template template = new Template();
				YingFu yingfu = new YingFu();
				yingfu.setId(Integer.valueOf(cursor4.getString(cursor4
						.getColumnIndex("id"))));
				tool(template, cursor4, 4);
				template.setSource(cursor4.getString(cursor4
						.getColumnIndex("YingFuTo")));
				templatelist.add(template);
				yingfulist.add(yingfu);
			}
		}
		
		if (cursor5.getCount() == 0) {
			Template template = new Template();
			YingShou yingshou = new YingShou();
			yingshou.setId(1);
			template.setCount(0.0);
			if (day == "") {
				template.setSource("本月无实收帐款");
			} else {
				template.setSource("本日无实收帐款");
			}

			shishoulist.add(yingshou);
			templatelist.add(template);
		} else {
			while (cursor5.moveToNext()) {
				Template template = new Template();
				YingShou yingshou = new YingShou();
				yingshou.setId(Integer.valueOf(cursor5.getString(cursor5
						.getColumnIndex("id"))));
				tool(template, cursor5, 5);
				template.setSource(cursor5.getString(cursor5
						.getColumnIndex("YingShouSource")));
				templatelist.add(template);
				shishoulist.add(yingshou);
			}
		}
		if (cursor6.getCount() == 0) {
			Template template = new Template();
			YingFu yingfu = new YingFu();
			yingfu.setId(1);
			template.setCount(0.0);
			if (day == "") {
				template.setSource("本月无实付帐款");
			} else {
				template.setSource("本日无实付帐款");
			}
			shifulist.add(yingfu);
			templatelist.add(template);
		} else {
			while (cursor6.moveToNext()) {
				Template template = new Template();
				YingFu yingfu = new YingFu();
				yingfu.setId(Integer.valueOf(cursor6.getString(cursor6
						.getColumnIndex("id"))));
				tool(template, cursor6, 6);
				template.setSource(cursor6.getString(cursor6
						.getColumnIndex("YingFuTo")));
				templatelist.add(template);
				shifulist.add(yingfu);
			}
		}
		
		if (templateListView.getFooterViewsCount() != 0) {
			if (cursor1.getCount() == 0 && cursor1.getCount() == 0
					&& cursor2.getCount() == 0 && cursor3.getCount() == 0
					&& cursor4.getCount() == 0 && cursor5.getCount() == 0
					&& cursor6.getCount() == 0) {
				templateListView.removeFooterView(bottomView);
			} else {
				IncomeCountText.setText("￥"+IncomeCount);
				PayCountText.setText("￥"+PayCount);
				YingShouCountText.setText("￥"+YingShouCount);
				YingFuCountText.setText("￥"+YingFuCount);
				ShiShouCountText.setText("￥"+ShiShouCount);
				ShiFuCountText.setText("￥"+ShiFuCount);
			}
		} else {
			if (cursor1.getCount() != 0 || cursor1.getCount() != 0
					|| cursor2.getCount() != 0 || cursor3.getCount() != 0
					|| cursor4.getCount() != 0 || cursor5.getCount() != 0
					|| cursor6.getCount() != 0) {
				AddFootView();
			}
			//AddFootView();
		}
		 cursor1.close();
		 cursor2.close();
		 cursor3.close();
		 cursor4.close();
		 cursor5.close();
		 cursor6.close();
		 
		templateadapter = new TemplateAdapter(this, templatelist);
		templateListView.setAdapter(templateadapter);
		IncomeCountText.setText(String.valueOf(IncomeCount));
		PayCountText.setText(String.valueOf(PayCount));
		YingShouCountText.setText(String.valueOf(YingShouCount));
		YingFuCountText.setText(String.valueOf(YingFuCount));
		ShiShouCountText.setText(String.valueOf(ShiShouCount));
		ShiFuCountText.setText(String.valueOf(ShiFuCount));
	}

	// 方法封装
	public void tool(Template template, Cursor cursor1, int number) {

		template.setId(number);
		template.setMenuId(Integer.valueOf(cursor1.getString(cursor1
				.getColumnIndex("id"))));
		template.setCount(Double.valueOf(cursor1.getString(cursor1
				.getColumnIndex("count"))));
		template.setTime(cursor1.getString(cursor1.getColumnIndex("Date")));
		template.setMakeperson(cursor1.getString(cursor1
				.getColumnIndex("MakePerson")));
		template.setMenuName(cursor1.getString(cursor1
				.getColumnIndex("MenuName")));
		template.setStatus(Integer.valueOf(cursor1.getString(cursor1
				.getColumnIndex("status"))));
	}

	// ListView添加尾布局
	public void listviewClick() {
		templateListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				if (position < templatelist.size()) {

					Template template = templatelist.get(position);
					if (template.getId() == 1) {
						Intent intent = new Intent();
						intent.putExtra("id",
								String.valueOf(template.getMenuId()));
						intent.putExtra("number", 3);
						intent.setClass(Data.this, IncomePayLock.class);
						startActivity(intent);
					} else if (template.getId() == 2) {
						Intent intent = new Intent();
						intent.putExtra("id",
								String.valueOf(template.getMenuId()));
						intent.putExtra("number", 4);
						intent.setClass(Data.this, IncomePayLock.class);
						startActivity(intent);
					} else if (template.getId() == 3 || template.getId() == 5) {
						if(template.getStatus()==2){
							Toast.makeText(Data.this, "单据已核销", 1000).show();
						}else{
						Intent intent = new Intent();
						intent.putExtra("id",
								String.valueOf(template.getMenuId()));
						intent.putExtra("number", 5);
						intent.setClass(Data.this, ShouFuHeXiao.class);
						startActivity(intent);}
					} else if (template.getId() == 4 || template.getId() == 6) {
						if(template.getStatus()==2){
							Toast.makeText(Data.this, "单据已核销", 1000).show();
						}else{
						Intent intent = new Intent();
						intent.putExtra("id",
								String.valueOf(template.getMenuId()));
						intent.putExtra("number", 6);
						intent.setClass(Data.this, ShouFuHeXiao.class);
						startActivity(intent);
					}
				}}
			}
		});
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
			getData();
			Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show();
		} // 语音识别后的回调，将识别的字串以Toast显示
		super.onActivityResult(requestCode, resultCode, data);
	}

	public String getMonth(String msg) {
		String str = "";
		int month = 0;
		int num = 0;
		for (int i = 0; i < msg.length(); i++) {
			if (msg.substring(i, i + 1).equals("月")) {
				num = i;
			}
		}
		month = Integer.valueOf(msg.substring(0, num));
		if (month <= 9) {
			str = "0" + msg.substring(0, num);
		}

		if (month > 9) {
			str = msg.substring(0, num);
		}
		number = month;
		return str;
	}

	public void AddFootView() {
		bottomView = (LinearLayout) inflater.inflate(R.layout.listviewbottom,
				null);
		IncomeCountText = (TextView) bottomView
				.findViewById(R.id.IncomeCountText);
		PayCountText = (TextView) bottomView.findViewById(R.id.PayCountText);
		YingShouCountText = (TextView) bottomView
				.findViewById(R.id.YingShouCountText);
		YingFuCountText = (TextView) bottomView
				.findViewById(R.id.YingFuCountText);
		ShiShouCountText = (TextView) bottomView
				.findViewById(R.id.ShiShouCountText);
		ShiFuCountText = (TextView) bottomView
				.findViewById(R.id.ShiFuCountText);
		IncomeCountText.setText(String.valueOf(IncomeCount));
		PayCountText.setText(String.valueOf(PayCount));
		YingShouCountText.setText(String.valueOf(YingShouCount));
		YingFuCountText.setText(String.valueOf(YingFuCount));
		ShiShouCountText.setText(String.valueOf(ShiShouCount));
		ShiFuCountText.setText(String.valueOf(ShiFuCount));
		templateListView.addFooterView(bottomView);
	}

	// 控件调用方法的封装
	public void initImageandText(int a) {
		for (int i = 0; i < textview.length; i++) {
			if (i == a) {
				textview[i].setTextColor(Color.RED);
				imageview[i].setVisibility(View.VISIBLE);
			} else {
				textview[i].setTextColor(Color.BLACK);
				imageview[i].setVisibility(View.GONE);
			}
		}
	}
	public void AddEditTextChangeListener(){
		seachMessage.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			
				getData();
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
