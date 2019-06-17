package shouye;

import java.util.ArrayList;
import java.util.List;

import sqlite.MenuClassSQLite;
import sqlite.SetTypeSQLite;
import Adapters.MenuUsefulAdapter;
import Adapters.SpinnerAdapter;
import Adapters.SwitchListAdapter;
import Adapters.SystemAdapter;
import Adapters.YiJianSelectAdapter;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.MenuUseFulClass;

public class SystemSet extends Activity implements OnClickListener {
	private ListView setlist, classListView, SwitchList,YiJianSelectList;
	private LayoutInflater inflater1;
	private TextView className, SetCountWay;
	private Spinner spinnerMenuSelect;
	private Button sure, cancel;
	private CheckBox addCheckBox;
	private EditText UserfulClassName;
	private LinearLayout SetCountLayout,SystemTopYiJianLayout;
	private RadioButton shoudongSetCount,AutoSetCount;
	private MenuClassSQLite menuclasssqlite;
	private SQLiteDatabase db1,db2,db3;
	private List<MenuUseFulClass> MenuUseFulClassDatas;
	private String[] msg = new String[] { "������;����" };
	private String[] yijianFunction = new String[] { "һ������", "һ������", "һ������" };
	private String[] SwitchTexts = {"����ģʽ", "�ճ�����", "���ݲ�������","�̶�����(�磺����)�Զ��뵥"};
	//private YiJianSQLite yijiansqlite;
	private SetTypeSQLite settypesqlite;
	private Switch yijianSwitch;
	private ImageView SetReBack;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.systemset);
		menuclasssqlite = new MenuClassSQLite(this, "menuclass.db", null, 1);
		//yijiansqlite=new YiJianSQLite(this, "yijian.db", null, 1);
		settypesqlite=new SetTypeSQLite(this,"settype.db", null, 1);
		db1 = menuclasssqlite.getReadableDatabase();
		//db2 = yijiansqlite.getReadableDatabase();
		db3 = settypesqlite.getReadableDatabase();
		inflater1 = (LayoutInflater) SystemSet.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		setlist = (ListView) findViewById(R.id.setlist);
		SwitchList = (ListView) findViewById(R.id.Switchlist);
		SetReBack=(ImageView)findViewById(R.id.SetReBack);
		SetReBack.setOnClickListener(this);
		MenuUseFulClassDatas = new ArrayList<MenuUseFulClass>();
		classListViewAddBottomView();
		SetCountWay.setOnClickListener(this);
		SystemAdapter adapter = new SystemAdapter(this, msg);
		setlist.setAdapter(adapter);
		AddSwitchListBottomView();
		SwitchListAdapter switchAdapter = new SwitchListAdapter(this,
				SwitchTexts);
		SwitchList.setAdapter(switchAdapter);
		checkStatus("���˷�ʽ����");
		checkStatus("һ��ģʽ");
		setlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				if (position == 0) {
					UserfulInit();
				}
				if (position==parent.getCount()-1) {
						SetCountWay.setText("v");
						SetCountLayout.setVisibility(View.VISIBLE);	
				}
			}
		});
	}

	public void UserfulInit() {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(SystemSet.this, R.style.Alert));
		View view2 = inflater1.inflate(R.layout.addclass, null);
		builder.setView(view2);
		final AlertDialog dialog = builder.show();
		className = (TextView) view2.findViewById(R.id.className);
		UserfulClassName = (EditText) view2.findViewById(R.id.MenuClassUseful);
		classListView = (ListView) view2.findViewById(R.id.classListView);
		spinnerMenuSelect = (Spinner) view2.findViewById(R.id.Classspinner);
		sure = (Button) view2.findViewById(R.id.sure);
		cancel = (Button) view2.findViewById(R.id.cancel);
		addCheckBox = (CheckBox) view2.findViewById(R.id.addCheckBox);
		String[] selectList = { "���뵥", "֧����", "��", "���", "ʵ�յ�", "ʵ����" };
		final SpinnerAdapter adapter1 = new SpinnerAdapter(this,
				android.R.layout.simple_spinner_item, selectList);
		adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinnerMenuSelect.setAdapter(adapter1);
		MenuUseFulClassDatas = menuclasssqlite.queryMenucalss(db1, className
				.getText().toString());
		final MenuUsefulAdapter adapter = new MenuUsefulAdapter(this,
				MenuUseFulClassDatas);
		classListView.setAdapter(adapter);
		ItemClick();
		spinnerMenuSelect
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long arg3) {
						className.setText(adapter1.getItem(position));
						MenuUseFulClassDatas = menuclasssqlite.queryMenucalss(
								db1, adapter1.getItem(position));
						MenuUsefulAdapter adapter1 = new MenuUsefulAdapter(
								SystemSet.this, MenuUseFulClassDatas);
						classListView.setAdapter(adapter1);

					}

					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (UserfulClassName.getText().toString().trim().length() == 0) {
					Toast.makeText(SystemSet.this, "������;����Ϊ��", 1000).show();
				} else {
					menuclasssqlite.addMenuClass(db1, className.getText()
							.toString(), UserfulClassName.getText().toString());
					MenuUseFulClassDatas = menuclasssqlite.queryMenucalss(db1,
							className.getText().toString());
					MenuUsefulAdapter adapter2 = new MenuUsefulAdapter(
							SystemSet.this, MenuUseFulClassDatas);
					classListView.setAdapter(adapter2);

					Toast.makeText(SystemSet.this, "��ӵ�����;�ɹ�", 1000).show();
					if (addCheckBox.isChecked()) {
						UserfulClassName.setText("");
						classListView.setSelection(MenuUseFulClassDatas.size() - 1);
						adapter.notifyDataSetChanged();
					} else {
						dialog.dismiss();
					}
				}

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();

			}
		});

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.AutoSetCount:
			Toast.makeText(this, "�Զ�����", 1000).show();
			settypesqlite.updateSetType(db3, "���˷�ʽ����", 2);
			break;
		case R.id.ShoudongSetCount:
			Toast.makeText(this, "�ֶ�����", 1000).show();
			settypesqlite.updateSetType(db3, "���˷�ʽ����", 1);
			break;
		case R.id.SetCountWay:
			SetCountWay.setText(">");
			SetCountLayout.setVisibility(View.GONE);
			break;
		case R.id.SetReBack:
			this.finish();
			break;
		}
	}

	public void ItemClick() {
		classListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						new ContextThemeWrapper(SystemSet.this, R.style.Alert));
				View view2 = inflater1.inflate(R.layout.updatemenuuseful, null);
				builder.setView(view2);
				final AlertDialog dialog1 = builder.show();
				final EditText updateclassName = (EditText) view2
						.findViewById(R.id.updateclassName);
				final EditText updateMenuUsefulName = (EditText) view2
						.findViewById(R.id.updateMenuClassUseful);
				Button Cancel = (Button) view2.findViewById(R.id.Cancel);
				Button update = (Button) view2.findViewById(R.id.update);
				Button delete = (Button) view2.findViewById(R.id.delete);
				updateclassName.setText(MenuUseFulClassDatas.get(position)
						.getClassName());
				updateMenuUsefulName.setText(MenuUseFulClassDatas.get(position)
						.getMenuUsefulName());
				Cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dialog1.dismiss();

					}
				});
				update.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						menuclasssqlite.updateMenuClassAll(db1, updateclassName
								.getText().toString(), updateMenuUsefulName
								.getText().toString(), MenuUseFulClassDatas
								.get(position).getClassId());
						Toast.makeText(SystemSet.this, "���³ɹ�", 1000).show();
						dialog1.dismiss();
						MenuUseFulClassDatas = menuclasssqlite.queryMenucalss(
								db1, className.getText().toString());
						MenuUsefulAdapter adapter2 = new MenuUsefulAdapter(
								SystemSet.this, MenuUseFulClassDatas);
						classListView.setAdapter(adapter2);
					}
				});
				delete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						menuclasssqlite.deleteClass(db1, MenuUseFulClassDatas
								.get(position).getClassId());
						Toast.makeText(SystemSet.this, "ɾ���ɹ�", 1000).show();
						dialog1.dismiss();
						MenuUseFulClassDatas = menuclasssqlite.queryMenucalss(
								db1, className.getText().toString());
						MenuUsefulAdapter adapter2 = new MenuUsefulAdapter(
								SystemSet.this, MenuUseFulClassDatas);
						classListView.setAdapter(adapter2);

					}
				});
			}
		});
	}

	public void AddSwitchListBottomView() {
		View view = LayoutInflater.from(SystemSet.this).inflate(
				R.layout.yijianswitchaddbottom, null);
		 yijianSwitch = (Switch) view
				.findViewById(R.id.YiJianSwitch1);
		YiJianSelectList = (ListView) view
				.findViewById(R.id.YiJianSelectList);
		SystemTopYiJianLayout=(LinearLayout)view.findViewById(R.id.SystemTopYiJianLayout);
		YiJianSelectAdapter adapter1 = new YiJianSelectAdapter(yijianFunction,
				SystemSet.this);
		YiJianSelectList.setAdapter(adapter1);
		SwitchList.addFooterView(view);
		yijianSwitch.setSwitchTextAppearance(SystemSet.this,
				R.style.OffTextColor);
		yijianSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton compoundButton,
					boolean flag) {
				if (flag) {
					yijianSwitch.setSwitchTextAppearance(SystemSet.this,
							R.style.OnTextColor);
					SystemTopYiJianLayout.setVisibility(View.VISIBLE);
					settypesqlite.updateSetType(db3, "һ��ģʽ", 2);
					Toast.makeText(SystemSet.this, "һ��ģʽ�ѿ���", 1000).show();
					
				} else {
					yijianSwitch.setSwitchTextAppearance(SystemSet.this,
							R.style.OffTextColor);
					SystemTopYiJianLayout.setVisibility(View.GONE);
					settypesqlite.updateSetType(db3, "һ��ģʽ", 1);
					Toast.makeText(SystemSet.this, "һ��ģʽ�ѹر�", 1000).show();
					
				}

			}
		});

	}
	public void classListViewAddBottomView() {
		View view = LayoutInflater.from(this).inflate(
				R.layout.setcountmethod, null);
		SetCountWay = (TextView) view.findViewById(R.id.SetCountWay);
		SetCountLayout = (LinearLayout) view.findViewById(R.id.SetCountLayout);
		shoudongSetCount = (RadioButton) view.findViewById(R.id.ShoudongSetCount);
		AutoSetCount = (RadioButton) view.findViewById(R.id.AutoSetCount);
		shoudongSetCount.setOnClickListener(this);
		AutoSetCount.setOnClickListener(this);
		shoudongSetCount.setChecked(true);
		setlist.addFooterView(view);
	}
	//�������״̬
	public void checkStatus(String str){
		//SetType settype=new SetType();
		Cursor cursor=settypesqlite.returnType(db3, str);
		if(cursor.getCount()==0&&str.equals("���˷�ʽ����")){
			settypesqlite.AddSet(db3, str, 1);
			}else if(str.equals("���˷�ʽ����")){
				if(cursor.moveToFirst()){
				if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==1){
					shoudongSetCount.setChecked(true);
					AutoSetCount.setChecked(false);
				}
				if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==2){
					AutoSetCount.setChecked(true);
					shoudongSetCount.setChecked(false);
				}
				
			}
			}
		if(cursor.getCount()==0&&str.equals("һ��ģʽ")){
			
				settypesqlite.AddSet(db3, str,1);
				}else if(str.equals("һ��ģʽ")){
					if(cursor.moveToFirst()){
					
					if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==1){
						yijianSwitch.setChecked(false);
					
					}
					if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==2){
						yijianSwitch.setChecked(true);
					}
					
				}}
		cursor.close();
		}
	
	}
