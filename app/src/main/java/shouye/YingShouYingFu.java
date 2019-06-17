package shouye;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sqlite.DaiXiCount;
import sqlite.JieXiCount;
import sqlite.MenuClassSQLite;
import sqlite.UserSQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import Adapters.YingFuAdapter;
import Adapters.YingShouAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.YingFu;
import entity.YingShou;

public class YingShouYingFu extends Activity implements OnClickListener {
	private RelativeLayout shouTelephone;
	private TextView textTitle1, amount, shousourcetitle, shoumakeobject,
			shouorfuclass, shouorfumaketime,yingshouId,shousource;
	private Button shouorfuSave, shouorfuExit;
	private CheckBox AddCheckBox;
	private int number;
	private RadioGroup TextGroup;
	private UserSQLite usersqlite;
	private MenuClassSQLite menuclasssqlite;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private JieXiCount jiexisqlite;
	private DaiXiCount daixisqlite;
	private SQLiteDatabase db, db1, db2, db3, db4,db5,db6;
	private RadioButton RedText, BlueText;
	private ImageView  shouimage2, yingshoufuimage3,
			shouorfuimage4;
	private EditText shouorfuamount, shouobject,
			shouorfumakemenuperson, shouorfumakenote, objecttelephone;
	private int status, type;
	private List<YingShou> yingshoulist;
	private List<YingFu> yingfulist;
    private ListView AddYingShouFuData;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.yingshoufu);
		Intent intent = getIntent();
		number = intent.getIntExtra("number", 0);
		init();

	}

	public void init() {
		shouTelephone = (RelativeLayout) findViewById(R.id.shouTelephone);
		textTitle1 = (TextView) findViewById(R.id.textTitle1);
		amount = (TextView) findViewById(R.id.amount);
		shousourcetitle = (TextView) findViewById(R.id.shoumakemenu2);
		shoumakeobject = (TextView) findViewById(R.id.shoumakemenu3);
		shouorfuSave = (Button) findViewById(R.id.shouorfuSave);
		shouorfuExit = (Button) findViewById(R.id.shouorfuExit);
		AddCheckBox=(CheckBox)findViewById(R.id.AddCheckBox);
		TextGroup = (RadioGroup) findViewById(R.id.TextGroup);
		RedText = (RadioButton) findViewById(R.id.RedText);
		BlueText = (RadioButton) findViewById(R.id.BlueText);
		shouimage2 = (ImageView) findViewById(R.id.shouimage2);
		yingshoufuimage3 = (ImageView) findViewById(R.id.yingshoufuimage3);
		shouorfuimage4 = (ImageView) findViewById(R.id.shouorfuimage4);
		AddYingShouFuData=(ListView)findViewById(R.id.AddYingShouFuData);
		shouorfuclass = (TextView) findViewById(R.id.shouorfuclass);
		shouorfuamount = (EditText) findViewById(R.id.shouorfuamount);
		shousource = (TextView) findViewById(R.id.shousource);
		shouobject = (EditText) findViewById(R.id.shouobject);
		shouorfumakemenuperson = (EditText) findViewById(R.id.shouorfumakemenuperson);
		shouorfumaketime = (TextView) findViewById(R.id.shouorfumaketime);
		shouorfumakenote = (EditText) findViewById(R.id.shouorfumakenote);
		yingshouId = (TextView) findViewById(R.id.yingshouId);
		objecttelephone = (EditText) findViewById(R.id.objecttelephone);
		long time = System.currentTimeMillis();
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		shouorfumaketime.setText(sdf.format(date));
		shouorfuSave.setOnClickListener(this);
		shouorfuExit.setOnClickListener(this);
		RedText.setOnClickListener(this);
		BlueText.setOnClickListener(this);
		shouimage2.setOnClickListener(this);
		yingshoufuimage3.setOnClickListener(this);
		shouorfuimage4.setOnClickListener(this);
		objecttelephone.setOnClickListener(this);

		usersqlite = new UserSQLite(this, "FamilyFinance.db", null, 1);
		db = usersqlite.getReadableDatabase();
		menuclasssqlite = new MenuClassSQLite(this, "menuclass.db", null, 1);
		db1 = menuclasssqlite.getReadableDatabase();
		yingshousqlite = new YingShouSQLite(this, "yingshou.db", null, 1);
		db3 = yingshousqlite.getReadableDatabase();
		yingfusqlite = new YingFuSQLite(this, "yingfu.db", null, 1);
		db4 = yingfusqlite.getReadableDatabase();
		jiexisqlite=new JieXiCount(this, "yingshoujie.db", null, 1);
		db5=jiexisqlite.getReadableDatabase();
		daixisqlite=new DaiXiCount(this,"yingfudai.db", null, 1);
		db6=daixisqlite.getReadableDatabase();
		yingshoulist = new ArrayList<YingShou>();
		yingfulist = new ArrayList<YingFu>();
		if (number == 5) {
			long count=yingshousqlite.TotalCount1(db3);
			yingshouId.setText(String.valueOf(count + 1));
			shouorfuclass.setText("��");
			textTitle1.setText("���¼��");
			shousourcetitle.setText("�����Դ:");
			shoumakeobject.setText("������:");
}
		if (number == 6) {
			shouorfuclass.setText("���");
			long count=yingfusqlite.TotalCount1(db4);
				yingshouId.setText(String.valueOf(count + 1));
			textTitle1.setText("����¼��");
			amount.setText("������:");
			shousourcetitle.setText("�������:");
			shoumakeobject.setText("�������:");

		}

	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.shouorfuSave:
			saveMenu();
			if(AddCheckBox.isChecked()){
				recover();
			}else{
				this.finish();
			}
			break;
		case R.id.shouorfuExit:
			this.finish();
			break;
		case R.id.RedText:
			if (number == 5) {
				shouorfuclass.setText("��");
				amount.setText("�����:");
			}
			if (number == 6) {
				shouorfuclass.setText("���");
				amount.setText("������:");
			}
			type = 0;
			break;
		case R.id.BlueText:
			if (number == 5) {
				shouorfuclass.setText("ʵ�յ�");
				amount.setText("���ս��:");
			}
			if (number == 6) {
				shouorfuclass.setText("ʵ����");
				amount.setText("�Ѹ����:");
			}
			type = 1;
			break;
		case R.id.shouimage2:
			Cursor cursor2 = menuclasssqlite.QueryMenucalss(db1, shouorfuclass.getText().toString());
			selectClass(db1, shousource, cursor2, "MenuUsefulName");
			 cursor2.close();
			break;
		case R.id.yingshoufuimage3:
			Cursor cursor = usersqlite.queryUser(db, "");
			selectClass(db, shouorfumakemenuperson, cursor, "userName");
			 cursor.close();
			break;
		case R.id.shouorfuimage4:
			Calendar calendar = Calendar.getInstance();
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
			final DatePickerDialog dialog = new DatePickerDialog(this,
					R.style.DateTime, new OnDateSetListener() {

						public void onDateSet(DatePicker view, int year,
								int month, int dayofmonth) {
							if (month < 9) {
								Toast.makeText(
										YingShouYingFu.this,
										year + "-" + "0" + (month + 1) + "-"
												+ dayofmonth, 1000).show();
								shouorfumaketime.setText(year + "-" + "0"
										+ (month + 1) + "-" + dayofmonth);
							} else {
								Toast.makeText(
										YingShouYingFu.this,
										year + "-" + (month + 1) + "-"
												+ dayofmonth, 1000).show();
								shouorfumaketime.setText(year + "-"
										+ (month + 1) + "-" + dayofmonth);
							}

						}
					}, year, month, dayofmonth);
			dialog.show();
			break;
		case R.id.objecttelephone:
			String tele = null;
			if (number == 5) {
				tele = yingshousqlite.queryShouByName(db3, shouobject.getText()
						.toString());
			}
			if (number == 6) {
				tele = yingfusqlite.queryFuByName(db4, shouobject.getText()
						.toString());
			}
			objecttelephone.setText(tele);
			break;
		}

	}

	// �������ʿ��ٲ���
	public void selectClass(SQLiteDatabase db, final TextView text,
			Cursor cursor, String str) {
		ListView classlocationlistview;
		final List<String> userlist = new ArrayList<String>();
		while (cursor.moveToNext()) {
			userlist.add(cursor.getString(cursor.getColumnIndex(str)));
			// Toast.makeText(AddIncomeorPay.this,
			// cursor.getString(cursor.getColumnIndex("userName")),
			// 1000).show();
		}
		LayoutInflater inflater = (LayoutInflater) YingShouYingFu.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.classlocation, null);
		classlocationlistview = (ListView) view
				.findViewById(R.id.classlistview);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, userlist);
		classlocationlistview.setAdapter(adapter);
		final AlertDialog alert = new AlertDialog.Builder(YingShouYingFu.this)
				.create();
		alert.setView(view);
		alert.show();
		// ���öԻ����С
		WindowManager.LayoutParams params = alert.getWindow().getAttributes();
		params.width =300;
		params.gravity = Gravity.RIGHT | Gravity.CENTER_HORIZONTAL;
		params.y = 260;
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
//���浥��
	public void saveMenu() {
		String menuname = shouorfuclass.getText().toString();
		Double amount = Double.valueOf(shouorfuamount.getText().toString());
		String source = shousource.getText().toString();
		String object = shouobject.getText().toString();
		String tele = objecttelephone.getText().toString();
		String person = shouorfumakemenuperson.getText().toString();
		String time = shouorfumaketime.getText().toString();
		String note = shouorfumakenote.getText().toString();
		String id = yingshouId.getText().toString();
		if (number == 5) {
				if (RedText.isChecked()) {
					// �˴�����ֱ�Ӷ�count�����жϣ��ж�count�Ƿ�Ϊ0.0Ҳ�У���Ϊ0.0�򲻴��ڼ�¼ֱ����ӣ�����λ0.0��ֱ�Ӹ��¼�¼
					if (yingshousqlite.Count(db3, object,"0") != 0) {
						double count2 = yingshousqlite.getCount(db3, object,"0");
						yingshousqlite.updateCount(db3, count2+amount, object,
								String.valueOf(0));
						
					} else {
						yingshousqlite.addYingShou(db3, id,
								String.valueOf(type), menuname,
								amount, source, object, tele, person, time,
								String.valueOf(status), note);
						YingShou yingshou=new YingShou(Integer.parseInt(id), 1, menuname, amount, source, object, person, time, note, status, tele);
						yingshoulist.add(yingshou);
						YingShouAdapter adapter=new YingShouAdapter(this, yingshoulist);
						AddYingShouFuData.setAdapter(adapter);
						Toast.makeText(YingShouYingFu.this, menuname + "����ɹ�",
								1000).show();
					}
					jiexisqlite.addYingShou(db5, id,
							String.valueOf(type), menuname,
							amount, source, object, tele, person, time,
							String.valueOf(status), note);
				} else if (BlueText.isChecked()) {
					if (yingshousqlite.Count(db3, object,"0") == 0) {
						Toast.makeText(this, "�򲻴��ڴ˶���Ľ����ʻ����������", 1000)
								.show();
					} else {
						yingshousqlite.addYingShou(db3, id,
								String.valueOf(type), menuname,
								amount, source, object, tele, person, time,
								String.valueOf(status), note);
						YingShou yingshou=new YingShou(Integer.parseInt(id), 1, menuname, amount, source, object, person, time, note, status, tele);
						yingshoulist.add(yingshou);
						YingShouAdapter adapter=new YingShouAdapter(this, yingshoulist);
						AddYingShouFuData.setAdapter(adapter);
						Toast.makeText(YingShouYingFu.this, menuname + "����ɹ�",
								1000).show();
					}
				}
			
		}
		if (number == 6) {
			
				   if (RedText.isChecked()) {
					// �˴�����ֱ�Ӷ�count�����жϣ��ж�count�Ƿ�Ϊ0.0Ҳ�У���Ϊ0.0�򲻴��ڼ�¼ֱ����ӣ�����λ0.0��ֱ�Ӹ��¼�¼
					if (yingfusqlite.Count(db4, object, String.valueOf(0)) != 0) {
						double count2 = yingfusqlite.getCount(db4, object,
								String.valueOf(0)) + amount;
						yingfusqlite.updateCount(db4, count2, object,
								String.valueOf(0));
						
					} else {
						yingfusqlite.addYingFu(db4, id, String.valueOf(type),
								menuname, amount, source, object, tele, person, time,
								String.valueOf(status), note);
						YingFu yingfu=new YingFu(Integer.parseInt(id), 1, menuname, amount, source, object, person, time, note, status, tele);
						yingfulist.add(yingfu);
						YingFuAdapter adapter=new YingFuAdapter(this, yingfulist);
						AddYingShouFuData.setAdapter(adapter);
						Toast.makeText(YingShouYingFu.this, menuname + "����ɹ�",
								1000).show();
					}
					daixisqlite.addYingShou(db6, id, String.valueOf(type),
							menuname, amount, source, object, tele, person, time,
							String.valueOf(status), note);
				} else if (BlueText.isChecked()) {
					if (yingfusqlite.Count(db4, object, String.valueOf(0)) == 0) {
						Toast.makeText(this, "�򲻴��ڴ˶���Ķ�Ӧ������ʻ����������", 1000)
								.setDuration(3000);
					} else {
						yingfusqlite.addYingFu(db4, id, String.valueOf(type),
								menuname, amount, source, object, tele, person, time,
								String.valueOf(status), note);
						YingFu yingfu=new YingFu(Integer.parseInt(id), 1, menuname, amount, source, object, person, time, note, status, tele);
						yingfulist.add(yingfu);
						YingFuAdapter adapter=new YingFuAdapter(this, yingfulist);
						AddYingShouFuData.setAdapter(adapter);
						Toast.makeText(YingShouYingFu.this, menuname + "����ɹ�",
								1000).show();
					}
				}
			}
	}
	//��ListView��Ӽ����¼�
		public void OnItemClick(){
			AddYingShouFuData.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					Intent intent=new Intent();
					if(number==5){
						intent.putExtra("id", String.valueOf(yingshoulist.get(position).getId()));	
					}else{
						intent.putExtra("id", String.valueOf(yingfulist.get(position).getId()));
					}
					intent.putExtra("number", number);
					intent.setClass(YingShouYingFu.this, ShouFuHeXiao.class);
					startActivity(intent);
					
				}
			});
		}
//�����ÿ�
	public void recover() {
		shouorfuamount.setText("");
		shousource.setText("");
		shouobject.setText("");
		shouorfumakemenuperson.setText("");
		shouorfumaketime.setText("");
		shouorfumakenote.setText("");
		yingshouId.setText(String.valueOf(Integer.parseInt(yingshouId.getText().toString())+1));
		objecttelephone.setText("");
	}
}