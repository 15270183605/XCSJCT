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
	String Class[] = { "����", "֧��", "Ӧ��", "Ӧ��", "ʵ��", "ʵ��", "����" };
	private String YearSpinnerData[];
	private String MonthSpinnerData[];
	private String className = "����", DateTime, NowTime;// �������
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
		SpinnerData(Class, ClassSpinner, "���");
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
		SpinnerData(YearSpinnerData, YearSpinner, "���");
		initMonthData(cal.get(Calendar.YEAR));
		SpinnerData(MonthSpinnerData, MonthSpinner, "�·�");
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
				if (leibie.equals("���")) {
					className = Class[position];
				} else if (leibie.equals("���")) {
					initMonthData(Integer.parseInt(YearSpinnerData[position]));
					DateTime = YearSpinnerData[position]
							+ "-"
							+ MonthSpinnerData[MonthSpinner
									.getSelectedItemPosition()];

				} else if (leibie.equals("�·�")) {
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
		SpinnerData(MonthSpinnerData, MonthSpinner, "�·�");
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
		if (className.equals("����")) {
			AddTitle(db1, "Income", filePath);
			ExcelUtil
					.writeObjListToExcel(incomelist, filePath, this, className);
		}
		if (className.equals("֧��")) {
			AddTitle(db2, "Pay", filePath);
			ExcelUtil.writeObjListToExcel(paylist, filePath, this, className);
		}

		if (className.equals("ʵ��") || className.equals("Ӧ��")) {
			AddTitle(db3, "YingShou", filePath);
			ExcelUtil.writeObjListToExcel(shoulist, filePath, this, className);
		}

		if (className.equals("ʵ��") || className.equals("Ӧ��")) {
			AddTitle(db4, "YingFu", filePath);
			ExcelUtil.writeObjListToExcel(fulist, filePath, this, className);

		}

		if (className.equals("����")) {
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
				 * //�򿪵�1��sheet try { readexcel.close(); } catch (WriteException
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

	// ���ݸ�����·�����ļ�
	public void OpenFile(String path) {
		if (path != null) {
			try {
				File file = new File(path);
				Intent intent2 = new Intent("android.intent.action.VIEW");
				intent2.addCategory("android.intent.category.DEFAULT");
				intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Uri uri = Uri.fromFile(file);
				if (uri == null) {
					Toast.makeText(this, "�ļ��������ɣ����ȼ�¼���ĵ�һ���¼��������ɣ�", 1000)
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
				// û�а�װ���������������ʾ
				Toast toast = Toast.makeText(this, "û���ҵ��򿪸��ļ���Ӧ�ó���",
						Toast.LENGTH_SHORT);
				toast.show();
			}

		}

	}

	public void CheckDataSize() {
		if (className.equals("����")) {
			incomelist = new ArrayList<Income>();
			incomelist = incomesqlite.queryAllIncomeByTime(db1, DateTime);
			if(incomelist.size()==0){
				DaoChuImage.setVisibility(View.GONE);
			}else{
				DaoChuImage.setVisibility(View.VISIBLE);
			}
		}
		if (className.equals("֧��")) {
			paylist = new ArrayList<Pay>();
			paylist = paysqlite.queryAllPayByTime(db2, DateTime);
			if(paylist.size()==0){
				DaoChuImage.setVisibility(View.GONE);
			}else{
				DaoChuImage.setVisibility(View.VISIBLE);
			}
		}

		if (className.equals("ʵ��") || className.equals("Ӧ��")) {
			shoulist = new ArrayList<YingShou>();
			if (className.equals("ʵ��")) {

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

		if (className.equals("ʵ��") || className.equals("Ӧ��")) {
			fulist = new ArrayList<YingFu>();
			if (className.equals("ʵ��")) {
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

		if (className.equals("����")) {
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
