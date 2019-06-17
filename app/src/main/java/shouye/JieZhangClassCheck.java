package shouye;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sqlite.IncomeSQLite;
import sqlite.PaySQLite;
import sqlite.SetTypeSQLite;
import sqlite.YiJianSQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import tool.MyListView;
import tool.MyListView.OnRefreshListener;
import Adapters.JieZhangClassCheckAdapter;
import Dialog.JieZhangDataDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.CountEntity;
import entity.JieZhangTemplate;

public class JieZhangClassCheck extends Activity implements OnClickListener {
	private final Object lock = new Object();
	private TextView NoLockText, NoHeXiaoText, JieZhangStatus, JieZhangLock,
			JieZhangHeXiao, JieZhangTextView, ProgressText, ResultText;
	private LinearLayout JieZhangBottomLayout;
	private MyListView JieZhangHeXiaoListView, JieZhangLockListView;
	private SetTypeSQLite settypesqlite;
	private SQLiteDatabase db, db1, db2, db3, db4, db5;;
	private YiJianSQLite yijiansqlite;
	private IncomeSQLite incomesqlite;
	private PaySQLite paysqlite;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private JieZhangClassCheckAdapter adapter, adapter1;
	private Map<String, List<JieZhangTemplate>> Datas = new HashMap<String, List<JieZhangTemplate>>();
	private List<CountEntity> CountDatas;
	private List<String> strs = new ArrayList<String>();
	private String time;
	private int year;
	private myThread thread = new myThread();
	private AlertDialog dialog;
	private int progressnum;
	private ProgressBar Progress;
	private String OperationName;
	private MyTask task;
	private int listviewbiaozhi,jiezhangbiaozhi;
	private  AlertDialog dialog1;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				progressnum++;
				Progress.setProgress(progressnum);
				ProgressText.setText(progressnum + "%");

				if (progressnum == 100) {
					if (OperationName.equals("锁定")) {
						ResultText.setText("单据锁定成功!");
						NoLockText.setText("已锁定##");
					}
					if (OperationName.equals("核销")) {
						ResultText.setText("单据核销成功!");
						NoHeXiaoText.setText("已锁定##");
					}
					dialog.dismiss();
					adapter.notifyDataSetChanged();
				}
				
				break;
			}

		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jiezhangclasscheck);
		init();
	}

	public void init() {
		Intent intent = getIntent();
		time = intent.getStringExtra("time");
		year = intent.getIntExtra("year", 2019);
		settypesqlite = new SetTypeSQLite(this, "settype.db", null, 1);
		db = settypesqlite.getReadableDatabase();
		yijiansqlite = new YiJianSQLite(this, "yijian.db", null, 1);
		db1 = yijiansqlite.getReadableDatabase();
		incomesqlite = new IncomeSQLite(this, "income.db", null, 1);
		db2 = incomesqlite.getReadableDatabase();
		paysqlite = new PaySQLite(this, "pay.db", null, 1);
		db3 = paysqlite.getReadableDatabase();
		yingshousqlite = new YingShouSQLite(this, "yingshou.db", null, 1);
		db4 = yingshousqlite.getReadableDatabase();
		yingfusqlite = new YingFuSQLite(this, "yingfu.db", null, 1);
		db5 = yingfusqlite.getReadableDatabase();
		NoLockText = (TextView) findViewById(R.id.NoLockText);
		NoHeXiaoText = (TextView) findViewById(R.id.NoHeXiaoText);
		JieZhangStatus = (TextView) findViewById(R.id.JieZhangStatus);
		JieZhangLock = (TextView) findViewById(R.id.JieZhangLock);
		JieZhangHeXiao = (TextView) findViewById(R.id.JieZhangHeXiao);
		JieZhangTextView = (TextView) findViewById(R.id.JieZhangTextView);
		JieZhangBottomLayout = (LinearLayout) findViewById(R.id.JieZhangBottomLayout);
		JieZhangHeXiaoListView = (MyListView) findViewById(R.id.JieZhangHeXiaoListView);
		JieZhangLockListView = (MyListView) findViewById(R.id.JieZhangLockListView);
		JieZhangLock.setOnClickListener(this);
		JieZhangHeXiao.setOnClickListener(this);
		JieZhangTextView.setOnClickListener(this);
		Datas = JieZhangDataDialog.JiZhangDatas;
		strs = JieZhangDataDialog.strs;
		Toast.makeText(this, String.valueOf(strs.size()), 1000).show();
		if (strs.size() == 1) {
			if (strs.get(0).equals("未锁定")) {
				adapter = new JieZhangClassCheckAdapter(Datas.get(strs.get(0)),
						this, "未锁定");
				JieZhangLockListView.setAdapter(adapter);
			} else {
				adapter1 = new JieZhangClassCheckAdapter(
						Datas.get(strs.get(0)), this, "未核销");
				JieZhangHeXiaoListView.setAdapter(adapter1);
			}
		} else if (strs.size() == 2) {
			adapter = new JieZhangClassCheckAdapter(Datas.get(strs.get(0)),
					this, "未锁定");
			JieZhangLockListView.setAdapter(adapter);
			adapter1 = new JieZhangClassCheckAdapter(Datas.get(strs.get(1)),
					this, "未核销");
			JieZhangHeXiaoListView.setAdapter(adapter1);
		}
		ItemClick();
		TextsetVisibility();
		// 下拉刷新方法调用
		JieZhangLockListView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				listviewbiaozhi = 0;
				task = new MyTask();
				task.execute();
			}

		});
		JieZhangHeXiaoListView.setonRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				listviewbiaozhi = 1;
				task = new MyTask();
				task.execute();
			}

		});
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.JieZhangLock:
			yijiansuoding();
			break;
		case R.id.JieZhangHeXiao:
			yijianhexiao();
			break;
		case R.id.JieZhangTextView:
			if(strs.size()==2){
				if(Datas.get(strs.get(0)).size()==0 && Datas.get(strs.get(1)).size()==0){
					JieZhang();
					JieZhangStatus.setText("已结账");
					jiezhangbiaozhi=1;
				}
			}else if(strs.size()==1){
				if(Datas.get(strs.get(0)).size()==0){
					JieZhang();
					JieZhangStatus.setText("已结账");
					jiezhangbiaozhi=1;
				}
			}
			
			else{
				Toast.makeText(this, "请先处理待处理的单据!", 1000).show();
			}
			break;
		case R.id.ExitCheck:
			if(jiezhangbiaozhi==1){
				finish();
			}else{
				Toast.makeText(this, "您还未结账，请先结账!", 1000).show();
			}
			dialog1.dismiss();
			break;
		case R.id.ExitCancel:
			dialog1.dismiss();
			break;
		}

	}

	public void ItemClick() {

		JieZhangLockListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				JieZhangTemplate template=new JieZhangTemplate();
				template=Datas.get("未锁定").get(position-1);
				if (template.getClassName().equals("收入单")) {
					Intent intent = new Intent();
					intent.putExtra(
							"id",
							String.valueOf(template
									.getId()));
					intent.putExtra("number", 3);
					intent.setClass(JieZhangClassCheck.this,
							IncomePayLock.class);
					startActivity(intent);
                    
				}
				else if (template.getClassName().equals("支出单")) {
					Intent intent = new Intent();
					intent.putExtra(
							"id",
							String.valueOf(template
									.getId()));
					intent.putExtra("number", 4);
					intent.setClass(JieZhangClassCheck.this,
							IncomePayLock.class);
					startActivity(intent);

				}else if (template.getClassName().equals("实收单")) {
					Intent intent = new Intent();
					intent.putExtra(
							"id",
							String.valueOf(template
									.getId()));
					intent.putExtra("number", 5);
					intent.setClass(JieZhangClassCheck.this,
							ShouFuHeXiao.class);
					startActivity(intent);

				}else if (template.getClassName().equals("实付单")) {
					Intent intent = new Intent();
					intent.putExtra(
							"id",
							String.valueOf(template
									.getId()));
					intent.putExtra("number", 6);
					intent.setClass(JieZhangClassCheck.this,
							ShouFuHeXiao.class);
					startActivity(intent);

				}

			}
		});
		JieZhangHeXiaoListView
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int position, long arg3) {
						JieZhangTemplate template=new JieZhangTemplate();
						template=Datas.get("未核销").get(position-1);
						if (template.getClassName()
								.equals("实收单")) {
							Intent intent = new Intent();
							intent.putExtra(
									"id",
									String.valueOf(template.getId()));
							intent.putExtra("number", 5);
							intent.setClass(JieZhangClassCheck.this,
									ShouFuHeXiao.class);
							startActivity(intent);

						}
						else if (template.getClassName()
								.equals("实付单")) {
							Intent intent = new Intent();
							intent.putExtra(
									"id",
									String.valueOf(template.getId()));
							intent.putExtra("number", 6);
							intent.setClass(JieZhangClassCheck.this,
									ShouFuHeXiao.class);
							startActivity(intent);
Toast.makeText(JieZhangClassCheck.this, position+"-"+Datas.get("未核销").size(), 1000).show();
						}

					}
				});
	}

	// 根据一键模式的判定来设置选项
	public void TextsetVisibility() {
		Cursor cursor = settypesqlite.returnType(db, "一键模式");
		Cursor cursor1 = yijiansqlite.returnType(db1, "一键锁单");
		Cursor cursor2 = yijiansqlite.returnType(db1, "一键核销");

		if (cursor.getCount() == 0) {
			settypesqlite.AddSet(db1, "一键模式", 1);
		}
		if (cursor.moveToFirst()) {

			if (cursor.getInt(cursor.getColumnIndex("SetTypeNum")) == 1) {
				JieZhangLock.setVisibility(View.GONE);
				JieZhangHeXiao.setVisibility(View.GONE);
			}
			if (cursor.getInt(cursor.getColumnIndex("SetTypeNum")) == 2) {
				if (cursor1.getCount() == 0) {
					yijiansqlite.AddSet(db, "一键锁单", 2);
				} else {
					if (cursor1.moveToFirst()) {

						if (cursor1.getInt(cursor.getColumnIndex("SetTypeNum")) == 1) {
							JieZhangLock.setVisibility(View.GONE);
						}
						if (cursor1.getInt(cursor.getColumnIndex("SetTypeNum")) == 2) {
							JieZhangLock.setVisibility(View.VISIBLE);
						}
					}
				}
				if (cursor2.getCount() == 0) {
					yijiansqlite.AddSet(db, "一键核销", 2);
				} else {
					if (cursor2.moveToFirst()) {

						if (cursor2.getInt(cursor.getColumnIndex("SetTypeNum")) == 1) {
							JieZhangHeXiao.setVisibility(View.GONE);
						}
						if (cursor2.getInt(cursor.getColumnIndex("SetTypeNum")) == 2) {
							JieZhangHeXiao.setVisibility(View.VISIBLE);
						}
					}
				}
			}

		}
		cursor.close();
		cursor1.close();
		cursor2.close();
	}

	public void yijiansuoding() {
		if(Datas.get("未锁定").size()==0){
			Toast.makeText(this, "不存在未锁定的单据", 1000).show();
		}else{
		OperationName = "锁定";
		for (int i = 0; i < Datas.get("未锁定").size(); i++) {
			if (Datas.get("未锁定").get(i).getClassName().equals("收入单")) {
				incomesqlite.updateStatus(db2,
						String.valueOf(Datas.get("未锁定").get(i).getId()), "1");
			}
			if (Datas.get("未锁定").get(i).getClassName().equals("支出单")) {
				paysqlite.updateStatus(db3,
						String.valueOf(Datas.get("未锁定").get(i).getId()), "1");

			}
			if (Datas.get("未锁定").get(i).getClassName().equals("实收单")) {
				yingshousqlite.updateStatus(db4,
						String.valueOf(Datas.get("未锁定").get(i).getId()),
						"1");
			}
			if (Datas.get("未锁定").get(i).getClassName().equals("实付单")) {
				yingfusqlite.updateStatus(db5,
						String.valueOf(Datas.get("未核销").get(i).getId()),
						"1");
			}
			
			Datas.get("未锁定").get(i).setStatus(1);
		}
		setDialog("单据锁定中，请耐心等待...");
		}

		/*
		 * adapter=new JieZhangClassCheckAdapter(Datas.get("未锁定"), this,"未锁定");
		 * JieZhangLockListView.setAdapter(adapter);
		 */

	}

	public void yijianhexiao() {
		if(Datas.get("未核销").size()==0){
			Toast.makeText(this, "不存在未核销的单据", 1000).show();
		}else{
		OperationName = "核销";
		for (int i = 0; i < Datas.get("未核销").size(); i++) {
			if (Datas.get("未核销").get(i).getClassName().equals("实收单")) {
				double count2 = yingshousqlite.getCount(db4, Datas.get("未核销")
						.get(i).getObject(), "0");
				double count3 = count2 - Datas.get("未核销").get(i).getCount();
				if (count3 >= 0) {
					yingshousqlite.updateCount(db4, count3, Datas.get("未核销")
							.get(i).getObject(), "0");
					yingshousqlite.updateStatus(db4,
							String.valueOf(Datas.get("未锁定").get(i).getId()),
							"2");
					Datas.get("未核销").get(i).setStatus(2);
				} else {
					Toast.makeText(this, "核销数据有误，请仔细核对数据", 1000).show();
				}
			} else {
				double count2 = yingfusqlite.getCount(db5, Datas.get("未核销")
						.get(i).getObject(), "0");
				double count3 = count2 - Datas.get("未核销").get(i).getCount();
				if (count3 >= 0) {
					yingfusqlite.updateCount(db5, count3,
							Datas.get("未核销").get(i).getObject(), "0");
					yingfusqlite.updateStatus(db5,
							String.valueOf(Datas.get("未核销").get(i).getId()),
							"2");
					Datas.get("未核销").get(i).setStatus(2);
				} else {
					Toast.makeText(this, "核销数据有误，请仔细核对数据", 1000).show();
				}
			}
		}
		setDialog("单据核销中，请耐心等待...");}
		//adapter1.notifyDataSetChanged();
		/*
		 * adapter1=new JieZhangClassCheckAdapter(Datas.get("未核销"), this,"未核销");
		 * JieZhangHeXiaoListView.setAdapter(adapter1);
		 */
	}

	public void JieZhang() {
		JieZhangDataDialog dialog = new JieZhangDataDialog(this, time, year, 2,
				0);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.show();
		dialog.setCancelable(false);

	}

	public void setDialog(String text) {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.Alert));
		View view = LayoutInflater.from(this).inflate(
				R.layout.jiezhangprogresscheck, null);
		Progress = (ProgressBar) view.findViewById(R.id.JiZhangProgress);
		ProgressText = (TextView) view.findViewById(R.id.ProgressText);
		ResultText = (TextView) view.findViewById(R.id.JiZhangResult);
		builder.setView(view);
		builder.setCancelable(false);
		dialog = builder.show();
		ResultText.setText(text);
		thread.start();
		

	}

	public class myThread extends Thread {

		public void run() {
			super.run();

			while (true) {

				try {
					Thread.sleep(100);// 使线程休眠0.1秒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (progressnum == 100) {// 当前进度等于总进度时退出循环
					progressnum = 0;
					break;
				} else {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}

			}
		}

	}

	public void RefreshNoLock() {
		Datas.get("未锁定").clear();
		//List<JieZhangTemplate> datas = new ArrayList<JieZhangTemplate>();
		List<JieZhangTemplate> data;
		data = new ArrayList<JieZhangTemplate>();
		data = incomesqlite.StatusCount(db2, time, "0");
		for (int i = 0; i < data.size(); i++) {
			Datas.get("未锁定").add(data.get(i));
			
		}
		data = new ArrayList<JieZhangTemplate>();
		data = paysqlite.StatusCount(db3, time, "0");
		for (int i = 0; i < data.size(); i++) {
			Datas.get("未锁定").add(data.get(i));
		}
		
		data = new ArrayList<JieZhangTemplate>();
		data = yingshousqlite.StatusCount(db4, time, "1", "0");
		for (int i = 0; i < data.size(); i++) {
			Datas.get("未锁定").add(data.get(i));
		}
		
		data = new ArrayList<JieZhangTemplate>();
		data = yingfusqlite.StatusCount(db5, time, "1", "0");
		for (int i = 0; i < data.size(); i++) {
			Datas.get("未锁定").add(data.get(i));
		}
		adapter = new JieZhangClassCheckAdapter(Datas.get("未锁定"), this, "未锁定");
		JieZhangLockListView.setAdapter(adapter);
		
	}

	public void RefreshNoHeXiao() {
		int number=0;
		for(int i=0;i<strs.size();i++){
			if(strs.get(i).equals("未核销")){
				number=1;
			}
		}
		
		//List<JieZhangTemplate> datas = new ArrayList<JieZhangTemplate>();
		List<JieZhangTemplate> data,data1;
		 data1 = new ArrayList<JieZhangTemplate>();
		 data = new ArrayList<JieZhangTemplate>();
		data1= yingshousqlite.StatusCount(db4, time, "1", "1");
		for (int i = 0; i < data1.size(); i++) {
			data.add(data1.get(i));
		}
		 data1 = new ArrayList<JieZhangTemplate>();
        data1 = yingfusqlite.StatusCount(db5, time, "1", "1");
		for (int i = 0; i < data1.size(); i++) {
			data.add(data1.get(i));
		}
		if(number==0){
			Datas.put("未核销",data);
		}else{
			Datas.get("未核销").clear();
			for(int i=0;i<data.size();i++){
				Datas.get("未核销").add(data.get(i));
			}
			
		}
		adapter1 = new JieZhangClassCheckAdapter(Datas.get("未核销"),
				this, "未核销");
		JieZhangHeXiaoListView.setAdapter(adapter1);
		
	}
	// 下拉刷新
	class MyTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {

			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Void result) {
			if (listviewbiaozhi == 0) {
				RefreshNoLock();
				adapter.notifyDataSetChanged();
				JieZhangLockListView.onRefreshComplete();
				RefreshNoHeXiao();
				adapter1.notifyDataSetChanged();
				//JieZhangHeXiaoListView.onRefreshComplete();
			} else{
				RefreshNoHeXiao();
				adapter1.notifyDataSetChanged();
				JieZhangHeXiaoListView.onRefreshComplete();
			}
		}
	}
	 public boolean onKeyDown(int keyCode, KeyEvent event) {   
		 switch (keyCode) {    	 
		 case KeyEvent.KEYCODE_BACK:         
			 AlertDialog.Builder builder = new AlertDialog.Builder(
						new ContextThemeWrapper(this, R.style.Alert));
				View view = LayoutInflater.from(this).inflate(R.layout.exitjiezhangcheck, null);
				TextView ExitCheck=(TextView)view.findViewById(R.id.ExitCheck);
				TextView ExitCancel=(TextView)view.findViewById(R.id.ExitCancel);
				ExitCheck.setOnClickListener(this);
				ExitCancel.setOnClickListener(this);
				builder.setView(view);
			     dialog1 = builder.show();        
			 break;    	}    	
		 return false;	    }
}