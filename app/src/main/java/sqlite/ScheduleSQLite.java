package sqlite;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.Income;
import entity.Schedule;

public class ScheduleSQLite extends SQLiteOpenHelper{
	final  String CREATE_TABLE_SQL="create table Schedule(_id integer primary key autoincrement,time ,daylight,morning,noon,afternoon,evening)";
	public ScheduleSQLite(Context context, String name,
			CursorFactory factory, int version) { 
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  System.out.println("-----"+oldVersion+"----"+newVersion);
		
	}
	public void addSchedule(SQLiteDatabase db,String time,String daylight,String morning,String noon,String afternoon,String evening){
	  db.execSQL("insert into Schedule values(null,?,?,?,?,?,?)",new String[]{time,daylight,morning,noon,afternoon,evening});
	}
	public void updateSchedule(SQLiteDatabase db,String daylight,String morning,String noon,String afternoon,String evening,String time){
		db.execSQL("update Schedule set daylight=?,morning=?,noon=?,afternoon=?,evening=? where time = ? ",new String[]{daylight,morning,noon,afternoon,evening,time});
	}
	public void updateSchedule1(SQLiteDatabase db,String daylight,String time){
		db.execSQL("update Schedule set daylight=? where time = ? ",new String[]{daylight,time});
	}
	public void updateSchedule2(SQLiteDatabase db,String morning,String time){
		db.execSQL("update Schedule set morning=?where time = ? ",new String[]{morning,time});
	}
	public void updateSchedule3(SQLiteDatabase db,String noon,String time){
		db.execSQL("update Schedule set noon=? where time = ? ",new String[]{noon,time});
	}
	public void updateSchedule4(SQLiteDatabase db,String afternoon,String time){
		db.execSQL("update Schedule set afternoon=? where time = ? ",new String[]{afternoon,time});
	}
	public void updateSchedule5(SQLiteDatabase db,String evening,String time){
		db.execSQL("update Schedule set evening=? where time = ? ",new String[]{evening,time});
	}
	public List<Schedule> queryAllSchedule(SQLiteDatabase db){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		List<Schedule> list=new ArrayList<Schedule>();
		Cursor cursor=db.query("Schedule", null, null, null, null, null, "time ASC");
	
		while(cursor.moveToNext()){
			Schedule schedule=new Schedule();
			try {
				schedule.setTime(simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex("time"))));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			schedule.setEarlyMorning(cursor.getString(cursor.getColumnIndex("daylight")));
			schedule.setMorning(cursor.getString(cursor.getColumnIndex("morning")));
			schedule.setNoon(cursor.getString(cursor.getColumnIndex("noon")));
			schedule.setAfternoon(cursor.getString(cursor.getColumnIndex("afternoon")));
			schedule.setEvening(cursor.getString(cursor.getColumnIndex("evening")));
    		list.add(schedule);
		}
		  cursor.close();
		return list;
	}
	public List<Schedule> queryByTime(SQLiteDatabase db, String time){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		List<Schedule> list=new ArrayList<Schedule>();
      Cursor cursor=db.query("Schedule", null, "time like ? ", new String[]{"%"+time+"%"}, null, null, "time ASC");
	
		while(cursor.moveToNext()){
			Schedule schedule=new Schedule();
			try {
				schedule.setTime(simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex("time"))));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			schedule.setEarlyMorning(cursor.getString(cursor.getColumnIndex("daylight")));
			schedule.setMorning(cursor.getString(cursor.getColumnIndex("morning")));
			schedule.setNoon(cursor.getString(cursor.getColumnIndex("noon")));
			schedule.setAfternoon(cursor.getString(cursor.getColumnIndex("afternoon")));
			schedule.setEvening(cursor.getString(cursor.getColumnIndex("evening")));
    		list.add(schedule);
		}
		  cursor.close();
		return list;
	}
	public List<Schedule> queryByTimeDown(SQLiteDatabase db, String time){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		List<Schedule> list=new ArrayList<Schedule>();
      Cursor cursor=db.query("Schedule", null, "time like ? ", new String[]{"%"+time+"%"}, null, null, "time DESC");
	
		while(cursor.moveToNext()){
			Schedule schedule=new Schedule();
			try {
				schedule.setTime(simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex("time"))));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			schedule.setEarlyMorning(cursor.getString(cursor.getColumnIndex("daylight")));
			schedule.setMorning(cursor.getString(cursor.getColumnIndex("morning")));
			schedule.setNoon(cursor.getString(cursor.getColumnIndex("noon")));
			schedule.setAfternoon(cursor.getString(cursor.getColumnIndex("afternoon")));
			schedule.setEvening(cursor.getString(cursor.getColumnIndex("evening")));
    		list.add(schedule);
		}
		  cursor.close();
		return list;
	}
	public List<Schedule> queryByTime(SQLiteDatabase db, String time1,String time2){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		List<Schedule> list=new ArrayList<Schedule>();
      Cursor cursor=db.query("Schedule", null, "time >=? and time<=? ", new String[]{time1,time2}, null, null, null);
	
		while(cursor.moveToNext()){
			Schedule schedule=new Schedule();
			try {
				schedule.setTime(simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex("time"))));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			schedule.setEarlyMorning(cursor.getString(cursor.getColumnIndex("daylight")));
			schedule.setMorning(cursor.getString(cursor.getColumnIndex("morning")));
			schedule.setNoon(cursor.getString(cursor.getColumnIndex("noon")));
			schedule.setAfternoon(cursor.getString(cursor.getColumnIndex("afternoon")));
			schedule.setEvening(cursor.getString(cursor.getColumnIndex("evening")));
    		list.add(schedule);
		}
		  cursor.close();
		return list;
	}
	public Schedule queryByTime1(SQLiteDatabase db, String time){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
		Schedule schedule=new Schedule();
      Cursor cursor=db.rawQuery("select * from Schedule where time=?",new String[]{time});
	
		while(cursor.moveToNext()){
			
			try {
				schedule.setTime(simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex("time"))));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			schedule.setEarlyMorning(cursor.getString(cursor.getColumnIndex("daylight")));
			schedule.setMorning(cursor.getString(cursor.getColumnIndex("morning")));
			schedule.setNoon(cursor.getString(cursor.getColumnIndex("noon")));
			schedule.setAfternoon(cursor.getString(cursor.getColumnIndex("afternoon")));
			schedule.setEvening(cursor.getString(cursor.getColumnIndex("evening")));
    		
		}
		  cursor.close();
		return schedule;
	}
	public void delete(SQLiteDatabase db,String time){
		 db.execSQL("delete  from Schedule where  time =?",new String[]{time});
	}
	public int QueryCount(SQLiteDatabase db, String time){
		  Cursor cursor=db.rawQuery("select count(*) from Schedule where time=?",new String[]{time});
		    cursor.moveToFirst();
		    int count=cursor.getInt(0);
		    cursor.close();
		    return count;
	
	}
}
