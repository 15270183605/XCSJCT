package shouye;

import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.YingFu;
import entity.YingShou;

public class ShouFuHeXiao extends Activity implements OnClickListener {
	private String id;
	private int number,type;
	private LayoutInflater inflater1;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private SQLiteDatabase db1, db2;
	private LinearLayout title1, title2;
	private RadioGroup TextGroup;
	private Cursor cursor2;
	private RadioButton redText, blueText;
	private EditText shouorfuclass, shouorfuamount, shousource, shouobject,
			shouorfumakemenuperson, shouorfumaketime, shouorfumakenote,
			objecttelephone, status;
	private TextView shouorfuclass1, shouorfuamount1, shousource1, shouobject1,
			shouorfumakemenuperson1, shouorfumaketime1, shouorfumakenote1,
			objecttelephone1, textTitle1, amount, shoumakemenu2, shoumakemenu3,
			status1, amount1, shoumakemenu21, shoumakemenu31;
	private Button lock, update, exit, exit1,hexiaoButton;
     private YingShou yingshou;
     private YingFu yingfu;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shoufuhexiao);
		init();
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		number = intent.getIntExtra("number", 0);
		yingfusqlite = new YingFuSQLite(this, "yingfu.db", null, 1);
		db2 = yingfusqlite.getReadableDatabase();
		yingshousqlite = new YingShouSQLite(this, "yingshou.db", null, 1);
		db1 = yingshousqlite.getReadableDatabase();
		if (number==5) {
			yingshou=yingshousqlite.queryShouById(db1, id);
			textTitle1.setText("借款单");    
				type=yingshou.getProperty();
				if (yingshou.getStatus()==0) {
					if (type==0) {
						redText.setChecked(true);
						blueText.setChecked(false);
						shoumakemenu2.setText("借款来源");
						shoumakemenu3.setText("借款对象");
						amount.setText("借款金额");
						lock.setText("催单");}
					
					else if (type==1){
						blueText.setChecked(true);
						redText.setChecked(false);
						shoumakemenu2.setText("借款来源");
						shoumakemenu3.setText("借款对象");
						amount.setText("已收金额");
						lock.setText("锁定");
					} 
					shouorfuclass.setText(yingshou.getMenuName());
					shouorfuamount.setText(String.valueOf(yingshou.getCount()));
					shousource.setText(yingshou.getYingShouSource());
					shouobject.setText(yingshou.getYingShouObject());
					shouorfumakemenuperson.setText(yingshou.getMakePerson()); 
					shouorfumaketime.setText(yingshou.getDate());
					shouorfumakenote.setText(yingshou.getMakeNote());
					objecttelephone.setText(yingshou.getTelephone());
					status.setText("已开始");
					title1.setVisibility(View.VISIBLE);
				} else if (yingshou.getStatus() == 1) {
					lock();
				}
				else{
					Toast.makeText(this, "此单据已核销!", 1000).show();
				}
		} else if (number==6) {
			yingfu = yingfusqlite.queryFuById(db2, id);
			textTitle1.setText("应付单");
				type=yingfu.getProperty();
				if (yingfu.getStatus() == 0) {
				      if (type == 0) {
						redText.setChecked(true);
						shoumakemenu3.setText("贷款对象");
						shoumakemenu2.setText("贷款出处");
						amount.setText("贷款金额");
						lock.setText("提醒");
					}
			     else if (type ==1) {
						blueText.setChecked(true);
						shoumakemenu3.setText("贷款对象");
						shoumakemenu2.setText("贷款出处");
						amount.setText("已付金额");
						lock.setText("锁定");
					}
					shouorfuclass.setText(yingfu.getMenuName());
					shouorfuamount.setText(String.valueOf(yingfu.getCount()));
					shousource.setText(yingfu.getYingFuTo());
					shouobject.setText(yingfu.getYingFuObject());
					shouorfumakemenuperson.setText(yingfu.getMakePerson());
					shouorfumaketime.setText(yingfu.getDate());
					shouorfumakenote.setText(yingfu.getMakeNote());
					objecttelephone.setText(yingfu.getTelephone());
					status.setText("已开始");
					title1.setVisibility(View.VISIBLE);
				} else if (yingfu.getStatus()== 1
						) {
					lock();
				}
				else{
					Toast.makeText(this, "此单据已核销!", 1000).show();
				}
		}
	}
	public void init() {
		inflater1 = (LayoutInflater) ShouFuHeXiao.this
			.getSystemService(LAYOUT_INFLATER_SERVICE);
		shouorfuclass = (EditText) findViewById(R.id.shouorfuclass);
		shouorfuamount = (EditText) findViewById(R.id.shouorfuamount);
		shousource = (EditText) findViewById(R.id.shousource);
		shouobject = (EditText) findViewById(R.id.shouobject);
		shouorfumakemenuperson = (EditText) findViewById(R.id.shouorfumakemenuperson);
		shouorfumaketime = (EditText) findViewById(R.id.shouorfumaketime);
		shouorfumakenote = (EditText) findViewById(R.id.shouorfumakenote);
		objecttelephone = (EditText) findViewById(R.id.objecttelephone);
		status = (EditText) findViewById(R.id.status);
		shouorfuclass1 = (TextView) findViewById(R.id.shouorfuclass1);
		shouorfuamount1 = (TextView) findViewById(R.id.shouorfuamount1);
		shousource1 = (TextView) findViewById(R.id.shousource1);
		shouobject1 = (TextView) findViewById(R.id.shouobject1);
		shouorfumakemenuperson1 = (TextView) findViewById(R.id.shouorfumakemenuperson1);
		shouorfumaketime1 = (TextView) findViewById(R.id.shouorfumaketime1);
		shouorfumakenote1 = (TextView) findViewById(R.id.shouorfumakenote1);
		objecttelephone1 = (TextView) findViewById(R.id.objecttelephone1);
		status1 = (TextView) findViewById(R.id.status1);
		textTitle1 = (TextView) findViewById(R.id.textTitle1);
		amount = (TextView) findViewById(R.id.amount);
		shoumakemenu2 = (TextView) findViewById(R.id.shoumakemenu2);
		shoumakemenu3 = (TextView) findViewById(R.id.shoumakemenu3);
		amount1 = (TextView) findViewById(R.id.amount1);
		shoumakemenu21 = (TextView) findViewById(R.id.shoumakemenu21);
		shoumakemenu31 = (TextView) findViewById(R.id.shoumakemenu31);

		TextGroup = (RadioGroup) findViewById(R.id.TextGroup);
		redText = (RadioButton) findViewById(R.id.RedText);
		blueText = (RadioButton) findViewById(R.id.BlueText);
		lock = (Button) findViewById(R.id.lock);
		update = (Button) findViewById(R.id.update);
		exit = (Button) findViewById(R.id.Exit);
		exit1 = (Button) findViewById(R.id.Exit1);
		hexiaoButton=(Button) findViewById(R.id.hexiaoButton);
		title1=(LinearLayout)findViewById(R.id.title1);
		title2=(LinearLayout)findViewById(R.id.title2);
		lock.setOnClickListener(this);
		update.setOnClickListener(this);
		exit.setOnClickListener(this);
		exit1.setOnClickListener(this); 
		hexiaoButton.setOnClickListener(this);
		
		yingshou=new YingShou();
		yingfu=new YingFu();
	}
//单据锁定
	public void lock() {
		if (number == 5) {
			yingshousqlite.updateStatus(db1, id, "1");
			 yingshou= yingshousqlite.queryShouById(db1, id);
			shoumakemenu21.setText("借款来源");
			shoumakemenu31.setText("借款对象");
			if (yingshou.getProperty() == 0) {
				amount1.setText("借款金额");
				redText.setChecked(true);
				blueText.setChecked(false);
			} else if (yingshou.getProperty() == 1) {
				amount1.setText("已收金额");
				blueText.setChecked(true);
				redText.setChecked(false);
			}
			shouorfuclass1.setText(yingshou.getMenuName());
			shouorfuamount1.setText(String.valueOf(yingshou.getCount()));
			shousource1.setText(yingshou.getYingShouSource());
			shouorfumakemenuperson1.setText(yingshou.getMakePerson());
			shouorfumaketime1.setText(yingshou.getDate());
			shouorfumakenote1.setText(yingshou.getMakeNote());
			objecttelephone1.setText(yingshou.getTelephone());
			shouobject1.setText(yingshou.getYingShouObject());
			status1.setText("已锁定");
		}
   else if (number == 6) {
			yingfusqlite.updateStatus(db2, id, "1");
			yingfu= yingfusqlite.queryFuById(db2, id);
			shoumakemenu21.setText("贷款出处");
			shoumakemenu31.setText("贷款对象");
			if (yingfu.getProperty() == 0) {
				redText.setChecked(true);
				blueText.setChecked(false);
				amount1.setText("贷款金额");
			} else if (yingfu.getProperty() == 1) {
				amount1.setText("已付金额");
				blueText.setChecked(true);
				redText.setChecked(false);
			}
			shouorfuclass1.setText(yingfu.getMenuName());
			shouorfuamount1.setText(String.valueOf(yingfu.getCount()));
			shousource1.setText(yingfu.getYingFuTo());
			shouorfumakemenuperson1.setText(yingfu.getMakePerson());
			shouorfumaketime1.setText(yingfu.getDate());
			shouorfumakenote1.setText(yingfu.getMakeNote());
			objecttelephone1.setText(yingfu.getTelephone());
			shouobject1.setText(yingfu.getYingFuObject());
			status1.setText("已锁定");

		
   }
		title1.setVisibility(View.GONE);
		title2.setVisibility(View.VISIBLE);
	}
//单据核销
	public void hexiao(){
		if(number==5){
			double count2=yingshousqlite.getCount(db1,shouobject1.getText().toString(),String.valueOf(0));
			double count3=count2-yingshou.getCount();
			if(count3>=0){
			yingshousqlite.updateCount(db1, count3,shouobject1.getText().toString(),String.valueOf(0));
			yingshousqlite.updateStatus(db1, id, String.valueOf(2));
			status1.setText("已核销");
			Toast.makeText(this, "核销成功", 1000).show();}
			else{
				Toast.makeText(this, "核销数据有误，请仔细核对数据", 1000).show();
			}}else if(number==6){
				
				double count2=yingfusqlite.getCount(db2,shouobject1.getText().toString(),"0");
				double count3=count2-yingfu.getCount();
				if(count3>=0){
				yingfusqlite.updateCount(db2, count3,shouobject1.getText().toString(),String.valueOf(0));
				yingfusqlite.updateStatus(db2, id, String.valueOf(2));
				status1.setText("已核销");
				Toast.makeText(this, "核销成功", 1000).show();}
				else{
					Toast.makeText(this, "核销数据有误，请仔细核对数据", 1000).show();
				}
			}
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.lock:
			if(type==1){
				lock();
			}
			if(type==0){
				setCuiDanDialog();
			}
			break;
		case R.id.update:
			
			String classname=shouorfuclass.getText().toString();
			Double count=Double.valueOf(shouorfuamount.getText().toString());
			String resource=shousource.getText().toString();
			String object=shouobject.getText().toString();
			String tele=objecttelephone.getText().toString();
			String person=shouorfumakemenuperson.getText().toString();
			String time=shouorfumaketime.getText().toString();
			String note=shouorfumakenote.getText().toString();
			if(number==5){
				yingshousqlite.updateYingShou(db1, id, classname, count, resource, object, tele, person, time, note);
			}else if(number==6){
				yingfusqlite.updateYingFu(db2, id, classname, count, resource, object, tele, person, time, note);
			}
			Toast.makeText(this, "更新成功", 1000).show();
			break;
		case R.id.Exit:
			this.finish();
			break;
		case R.id.Exit1:
			this.finish();
			break;
		case R.id.hexiaoButton:
			hexiao();
			break;
		}

	}
	public void setCuiDanDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.Alert));
		View view = inflater1.inflate(R.layout.cuidanxelect, null);
		builder.setView(view);
		final AlertDialog dialog = builder.show();
		final RadioButton SetMessage= (RadioButton) view.findViewById(R.id.SetMessage);
		final RadioButton Call= (RadioButton) view.findViewById(R.id.Call);
		Button cancel=(Button)view.findViewById(R.id.cancel);
		Button sure=(Button)view.findViewById(R.id.sure);
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				
			}
		});
		sure.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if (SetMessage.isChecked()) {
					Intent intent=new Intent();
					//intent.setAction(Intent.ACTION_SENDTO);
					intent.setData(Uri.parse("smsto:"+objecttelephone.getText().toString()));
					intent.putExtra("sms_body","手头宽裕点了吗？");
					startActivity(intent);
					
				}
				if (Call.isChecked()) {

				       Intent intent = new Intent();
				      intent.setAction(Intent.ACTION_CALL);//设置意图对象的动作，打电话
				      intent.setData(Uri.parse("tel:"+objecttelephone.getText().toString()));// 设置意图对象的数据，告诉奴隶拨打的电话号码
								startActivity(intent);
				}
			}

		});
		
	}
}
