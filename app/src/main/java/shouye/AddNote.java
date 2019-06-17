package shouye;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sqlite.ScheduleSQLite;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

public class AddNote extends Activity implements OnClickListener{
      private EditText maketime,earlymorning,morning,noon,afternoon,evening;
      private ImageView MakeTime;
      private Button save,update,exit;
      private ScheduleSQLite schedulesqlite;
      private SQLiteDatabase db;
      private TextView NoteTitle;
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.addnote);
    	init();}
    public void init(){
    	maketime=(EditText)findViewById(R.id.maketime);
    	earlymorning=(EditText)findViewById(R.id.EarlyMorning);
    	morning=(EditText)findViewById(R.id.Morning);
    	noon=(EditText)findViewById(R.id.Noon);
    	afternoon=(EditText)findViewById(R.id.Afternoon);
    	evening=(EditText)findViewById(R.id.Evening);
    	MakeTime=(ImageView)findViewById(R.id.MakeTime);
    	save=(Button)findViewById(R.id.save);
    	update=(Button)findViewById(R.id.update);
    	exit=(Button)findViewById(R.id.exit);
    	NoteTitle=(TextView)findViewById(R.id.NoteTitle);
    	save.setOnClickListener(this);
    	update.setOnClickListener(this);
    	exit.setOnClickListener(this);
    	MakeTime.setOnClickListener(this);
    	schedulesqlite=new ScheduleSQLite(this, "Schedule.db", null, 1);
    	db=schedulesqlite.getReadableDatabase();
    }
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.save:
			save();
			
			break;
		case R.id.exit:
			Intent intent =new Intent(this,ManageNote.class);
			startActivity(intent);
			this.finish();
			break;
		case R.id.MakeTime:
			Calendar calendar=Calendar.getInstance();
			int year=calendar.get(Calendar.YEAR);
			int month=calendar.get(Calendar.MONTH);
			int dayofmonth=calendar.get(Calendar.DAY_OF_MONTH);
			final DatePickerDialog dialog=new DatePickerDialog(this, R.style.DateTime,new OnDateSetListener() {
				
				
				public void onDateSet(DatePicker view, int year, int month, int dayofmonth) {
					if(month<9){
						Toast.makeText(AddNote.this, year+"-"+"0"+(month+1)+"-"+dayofmonth, 1000).show();
						maketime.setText(year+"-"+"0"+(month+1)+"-"+dayofmonth);
					}
					else{
						Toast.makeText(AddNote.this, year+"-"+(month+1)+"-"+dayofmonth, 1000).show();
						maketime.setText(year+"-"+(month+1)+"-"+dayofmonth);
					}
					
				}
			},year,month,dayofmonth);
			dialog.show();
			break;
		}
		
	}
	public void save(){
		String time=maketime.getText().toString();
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		long currentTime=System.currentTimeMillis();
		Date date=new Date(currentTime);
		Date datetime = null;
		Date date1=null;
		try {
			datetime = simpleDateFormat.parse(time);
			date1=simpleDateFormat.parse(simpleDateFormat.format(date));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		String daylight=earlymorning.getText().toString();
		String mor=morning.getText().toString();
		String Noon=noon.getText().toString();
		String after=afternoon.getText().toString();
		String eve=evening.getText().toString();
		long currenttime=date1.getTime();
		long Time=datetime.getTime();
		if(Time<currenttime){
			Toast.makeText(this, "抱歉！日程安排表不针对过去时间", 1000).show();
		}else{
		schedulesqlite.addSchedule(db, time, daylight, mor, Noon, after, eve);
		Toast.makeText(this, "日程保存成功", 1000).show();
		recover();}
	}
	//更新
	public void update(){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String time=maketime.getText().toString();
		long currentTime=System.currentTimeMillis();
		Date date=new Date(currentTime);
		
		Date datetime = null;
		Date date1=null;
		try {
			datetime =simpleDateFormat.parse(time);
			date1=simpleDateFormat.parse(simpleDateFormat.format(date));
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		String daylight=earlymorning.getText().toString();
		String mor=morning.getText().toString();
		String Noon=noon.getText().toString();
		String after=afternoon.getText().toString();
		String eve=evening.getText().toString();
		long currenttime=date1.getTime();
		long Time=datetime.getTime();
		if(Time<currenttime){
			Toast.makeText(this, "日程已发生，禁止修改", 1000).show();
		}else{
		schedulesqlite.updateSchedule(db, daylight, mor, Noon, after, eve,time);
		Toast.makeText(this, "日程修改成功", 1000).show();
		Intent intent=new Intent(AddNote.this,ManageNote.class);
		startActivity(intent);
		this.finish();
		}
	}
	//删除
	/*public void delete(){
		schedulesqlite.delete(db, ManageNote.reback());
		Toast.makeText(this, "删除成功", 1000).show();
		Intent intent=new Intent(AddNote.this,ManageNote.class);
		startActivity(intent);
		this.finish();
	}*/
	public void recover(){
		maketime.setText("");
		earlymorning.setText("");
		morning.setText("");
		noon.setText("");
		afternoon.setText("");
		evening.setText("");
	}
}
