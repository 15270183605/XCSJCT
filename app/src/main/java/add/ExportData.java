package add;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import loginOrRegister.Main;
import sqlite.CountSQLite;
import sqlite.FileSQLite;
import sqlite.IncomeSQLite;
import sqlite.PaySQLite;
import sqlite.UserSQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import Adapters.DataFileAdapter;
import Adapters.SpinnerAdapter;
import Excel.ExcelUtil;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.CountEntity;
import entity.DataFile;
import entity.Income;
import entity.Pay;
import entity.YingFu;
import entity.YingShou;

public class ExportData extends Activity implements OnClickListener {
	private Spinner YearSpinner, MonthSpinner, ClassSpinner;
	private LinearLayout ExportBottom;
	private Calendar cal;
	private int Year, Month, UserTime, num;
	private UserSQLite usersqlite;
	private IncomeSQLite incomesqlite;
	private PaySQLite paysqlite;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private CountSQLite countsqlite;
	private FileSQLite filesqlite;
	private SQLiteDatabase db, db1, db2, db3, db4, db5, db6;
	private ListView FileListView;
	private ImageView DaoChuImage;
	String Class[] = { "收入", "支出", "应收", "应付", "实收", "实付", "总账" };
	private String YearSpinnerData[];
	private String MonthSpinnerData[];
	private String className = "收入", DateTime, NowTime;// 类别名称
	private List<DataFile> datafileList;
	List<Pay> paylist;
	List<Income> incomelist;
	List<YingShou> shoulist;
	List<YingFu> fulist;
	List<CountEntity> countlist;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.exportdata);
		init();
	}

	public void init() {
		Date date = new Date(System.currentTimeMillis());
		cal = Calendar.getInstance();
		cal.setTime(date);
		Year = cal.get(Calendar.YEAR);
		Month = cal.get(Calendar.MONTH) + 1;
		if (Month == 1) {
			DateTime = (Year - 1) + "-" + "12";
		}
		if (Month <= 10 && Month > 1) {
			DateTime = Year + "-0" + (Month - 1);
		}
		if (Month > 10) {
			DateTime = Year + "-" + (Month - 1);
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		NowTime = format.format(date);
		YearSpinner = (Spinner) findViewById(R.id.YearSpinner);
		MonthSpinner = (Spinner) findViewById(R.id.MonthSpinner);
		ClassSpinner = (Spinner) findViewById(R.id.ClassSpinner);
		DaoChuImage = (ImageView) findViewById(R.id.AddDaoChuImage);
		FileListView = (ListView) findViewById(R.id.FileListView);
		ExportBottom = (LinearLayout) findViewById(R.id.ExportBottomLayout);
		DaoChuImage.setOnClickListener(this);
		SpinnerData(Class, ClassSpinner, "类别");
		countsqlite = new CountSQLite(this, "TotalCount.db", null, 1);
		db5 = countsqlite.getReadableDatabase();
		usersqlite = new UserSQLite(this, "FamilyFinance.db", null, 1);
		db = usersqlite.getReadableDatabase();
		incomesqlite = new IncomeSQLite(this, "income.db", null, 1);
		db1 = incomesqlite.getReadableDatabase();
		paysqlite = new PaySQLite(this, "pay.db", null, 1);
		db2 = paysqlite.getReadableDatabase();
		yingshousqlite = new YingShouSQLite(this, "yingshou.db", null, 1);
		db3 = yingshousqlite.getReadableDatabase();
		yingfusqlite = new YingFuSQLite(this, "yingfu.db", null, 1);
		db4 = yingfusqlite.getReadableDatabase();
		filesqlite = new FileSQLite(this, "DaoChuDataFile.db", null, 1);
		db6 = filesqlite.getReadableDatabase();
		UserTime = Integer.parseInt((usersqlite.QueryUserTime(db,
				Main.returnName(), Main.returnPsd())).substring(0, 4));
		YearSpinnerData = new String[cal.get(Calendar.YEAR) - UserTime + 1];
		for (int j = cal.get(Calendar.YEAR); j >= UserTime; j--) {
			YearSpinnerData[num] = String.valueOf(j);
			num++;
		}
		SpinnerData(YearSpinnerData, YearSpinner, "年份");
		initMonthData(cal.get(Calendar.YEAR));
		SpinnerData(MonthSpinnerData, MonthSpinner, "月份");
		datafileList = new ArrayList<DataFile>();
		datafileList = filesqlite.getDataFileByTime(db6, NowTime, className);
		AddAdapterData();
		CheckDataSize();
		SetListViewItemClick();
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.AddDaoChuImage:
			exportExcel();
			break;
		}

	}

	public void SpinnerData(String str[], Spinner spinner, final String leibie) {
		SpinnerAdapter adapter = new SpinnerAdapter(this,
				android.R.layout.simple_spinner_item, str);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				if (leibie.equals("类别")) {
					className = Class[position];
				} else if (leibie.equals("年份")) {
					initMonthData(Integer.parseInt(YearSpinnerData[position]));
					DateTime = YearSpinnerData[position]
							+ "-"
							+ MonthSpinnerData[MonthSpinner
									.getSelectedItemPosition()];

				} else if (leibie.equals("月份")) {
					DateTime = YearSpinnerData[YearSpinner
							.getSelectedItemPosition()]
							+ "-"
							+ MonthSpinnerData[position];
				}
				datafileList = filesqlite.getDataFileByTime(db6, DateTime,
						className);
				AddAdapterData();
				CheckDataSize();
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	public void initMonthData(int year) {
		if (year == Year) {
			MonthSpinnerData = new String[Month];
			for (int i = 1; i <= Month; i++) {
				if (i < 10) {
					MonthSpinnerData[i - 1] = "0" + i;
				} else {
					MonthSpinnerData[i - 1] = String.valueOf(i);
				}

			}
		} else {
			MonthSpinnerData = new String[12];
			for (int i = 1; i <= 12; i++) {
				if (i < 10) {
					MonthSpinnerData[i - 1] = "0" + i;
				} else {
					MonthSpinnerData[i - 1] = String.valueOf(i);
				}

			}
		}
		SpinnerData(MonthSpinnerData, MonthSpinner, "月份");
	}

	private void exportExcel() {
		// String PATH =
		// Environment.getExternalStorageDirectory().getAbsolutePath() +
		// "/..../";
		String filePath = "/sdcard/";
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		String excelFileName = DateTime + className + ".xls";
		filePath = filePath + excelFileName;
		if (className.equals("收入")) {
			AddTitle(db1, "Income", filePath);
			ExcelUtil
					.writeObjListToExcel(incomelist, filePath, this, className);
		}
		if (className.equals("支出")) {
			AddTitle(db2, "Pay", filePath);
			ExcelUtil.writeObjListToExcel(paylist, filePath, this, className);
		}

		if (className.equals("实收") || className.equals("应收")) {
			AddTitle(db3, "YingShou", filePath);
			ExcelUtil.writeObjListToExcel(shoulist, filePath, this, className);
		}

		if (className.equals("实付") || className.equals("应付")) {
			AddTitle(db4, "YingFu", filePath);
			ExcelUtil.writeObjListToExcel(fulist, filePath, this, className);

		}

		if (className.equals("总账")) {
			AddTitle(db5, "Count", filePath);
			ExcelUtil.writeObjListToExcel(countlist, filePath, this, className);

		}
		DataFile datafile = new DataFile(excelFileName, DateTime, filePath,
				className);
		filesqlite.AddFileData(db6, datafile);
		datafileList.add(datafile);
		AddAdapterData();

	}

	public void AddTitle(SQLiteDatabase db, String tablename, String filePath) {
		Cursor cursor = db.query(tablename, null, null, null, null, null, null);
		String[] title = cursor.getColumnNames();
		ExcelUtil.initExcel(filePath, className, title);
	}

	public void SetListViewItemClick() {
		FileListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				OpenFile(datafileList.get(position).getFilePath());
				/*
				 * ReadExcel readexcel = null; try { readexcel = ReadExcel
				 * .getInstance() .openExcel(new
				 * File(datafileList.get(position).getFilePath()))
				 * .openSheet(0);
				 * 
				 * 
				 * } catch (BiffException e) { // TODO Auto-generated catch
				 * block e.printStackTrace(); } catch (IOException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 * //打开第1个sheet try { readexcel.close(); } catch (WriteException
				 * e) { // TODO Auto-generated catch block e.printStackTrace();
				 * } catch (IOException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
			}
		});
	}

	public void AddAdapterData() {
		if (datafileList.size() != 0) {
			ExportBottom.setVisibility(View.GONE);
			FileListView.setVisibility(View.VISIBLE);
			DataFileAdapter adapter = new DataFileAdapter(this, datafileList);
			FileListView.setAdapter(adapter);
			DaoChuImage.setVisibility(View.VISIBLE);
		} else {
			ExportBottom.setVisibility(View.VISIBLE);
			FileListView.setVisibility(View.GONE);
		}
	}

	// 根据给定的路径打开文件
	public void OpenFile(String path) {
		if (path != null) {
			try {
				File file = new File(path);
				Intent intent2 = new Intent("android.intent.action.VIEW");
				intent2.addCategory("android.intent.category.DEFAULT");
				intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Uri uri = Uri.fromFile(file);
				if (uri == null) {
					Toast.makeText(this, "文件暂无生成，您先记录您的第一条事件才能生成！", 1000)
							.show();
				} else {
					if (path.contains(".docx")) {
						intent2.setDataAndType(uri, "application/msword");
					} else if (path.contains(".xlsx")) {
						intent2.setDataAndType(uri, "application/vnd.ms-excel");
					} else {
						intent2.setDataAndType(uri, "text/plain");
					}
					startActivity(intent2);
				}
			} catch (Exception e) {
				// 没有安装第三方的软件会提示
				Toast toast = Toast.makeText(this, "没有找到打开该文件的应用程序",
						Toast.LENGTH_SHORT);
				toast.show();
			}

		}

	}

	public void CheckDataSize() {
		if (className.equals("收入")) {
			incomelist = new ArrayList<Income>();
			incomelist = incomesqlite.queryAllIncomeByTime(db1, DateTime);
			if(incomelist.size()==0){
				DaoChuImage.setVisibility(View.GONE);
			}else{
				DaoChuImage.setVisibility(View.VISIBLE);
			}
		}
		if (className.equals("支出")) {
			paylist = new ArrayList<Pay>();
			paylist = paysqlite.queryAllPayByTime(db2, DateTime);
			if(paylist.size()==0){
				DaoChuImage.setVisibility(View.GONE);
			}else{
				DaoChuImage.setVisibility(View.VISIBLE);
			}
		}

		if (className.equals("实收") || className.equals("应收")) {
			shoulist = new ArrayList<YingShou>();
			if (className.equals("实收")) {

				shoulist = yingshousqlite.queryAllYingShouByProperty(db3,
						DateTime, "1");
			} else {
				shoulist = yingshousqlite.queryAllYingShouByProperty(db3,
						DateTime, "0");
			}
			if(shoulist.size()==0){
				DaoChuImage.setVisibility(View.GONE);
			}else{
				DaoChuImage.setVisibility(View.VISIBLE);
			}
		}

		if (className.equals("实付") || className.equals("应付")) {
			fulist = new ArrayList<YingFu>();
			if (className.equals("实付")) {
				fulist = yingfusqlite.queryAllYingFuByProperty(db4, DateTime,
						"1");
			} else {
				fulist = yingfusqlite.queryAllYingFuByProperty(db4, DateTime,
						"0");
			}
			if(fulist.size()==0){
				DaoChuImage.setVisibility(View.GONE);
			}else{
				DaoChuImage.setVisibility(View.VISIBLE);
			}
		}

		if (className.equals("总账")) {
			countlist = new ArrayList<CountEntity>();
			countlist = countsqlite.QueryCountByDate(db5, DateTime);
			if(countlist.size()==0){
				DaoChuImage.setVisibility(View.GONE);
			}else{
				DaoChuImage.setVisibility(View.VISIBLE);
			}
		}
	}

}
