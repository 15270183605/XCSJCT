package sqlite;

import java.util.ArrayList;
import java.util.List;

import entity.Income;
import entity.JieZhangTemplate;
import entity.Pay;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class PaySQLite extends SQLiteOpenHelper{
	final  String CREATE_TABLE_SQL="create table Pay(_id integer primary key autoincrement,id,MenuName,count double,PayTo,MakePerson,Date,status,MakeNote)";
	public PaySQLite(Context context, String name,
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
	public void addPay(SQLiteDatabase db,String id,String MenuName,Double count,String PayTo,String MakePerson,String Date,String status,String MakeNote){
		  db.execSQL("insert into Pay values(null,?,?,?,?,?,?,?,?)",new Object[]{id,MenuName,count,PayTo,MakePerson,Date,status,MakeNote});
	}
	public Cursor queryPay(SQLiteDatabase db){
		  Cursor cursor=db.rawQuery("select * from Pay", null);
		  return cursor;
	  }
	//根据单据Id查询单据是否存在
	 public long IdCount(SQLiteDatabase db,String id){
			Cursor cursor=db.rawQuery("select count(*) from Pay where  id = ?",new String[]{id});
			    cursor.moveToFirst();
			    long count=cursor.getLong(0);
			    cursor.close();
			    return count;
		}
	 //根据时间查询单据比数
	 public long TimeCount(SQLiteDatabase db,String time){
			Cursor cursor=db.rawQuery("select count(*) from Pay where  Date like ?",new String[]{"%"+time+"%"});
			    cursor.moveToFirst();
			    long count=cursor.getLong(0);
			    cursor.close();
			    return count;
		}
	 //查未锁定单据比数
	 public List<JieZhangTemplate> StatusCount(SQLiteDatabase db,String time,String status){
		 List<JieZhangTemplate> datas=new ArrayList<JieZhangTemplate>();
			Cursor cursor=db.rawQuery("select * from Pay where  Date like ? and status=?",new String[]{"%"+time+"%",status});
			while(cursor.moveToNext()){
				String className=cursor.getString(cursor.getColumnIndex("MenuName"));
						String date=cursor.getString(cursor.getColumnIndex("Date"));
						double count=cursor.getDouble(cursor.getColumnIndex("count"));
						int id=cursor.getInt(cursor.getColumnIndex("id"));
				JieZhangTemplate template=new JieZhangTemplate(className, count, date, Integer.parseInt(status), id,"");
			   datas.add(template);
			}  
			    cursor.close();
			    return datas;
		}
	 public Cursor queryPayById(SQLiteDatabase db,String id){
		  Cursor cursor=db.rawQuery("select * from Pay where id like ?", new String[]{id});
		  return cursor;
	  }
	 public void updateStatus(SQLiteDatabase db,String id,String status){
			db.execSQL("update Pay set status=? where id like ? ",new String[]{status,id});
		}
	//根据时间来更改单据的状态
		public void updateStatusbyTime(SQLiteDatabase db,String status,String time){
			db.execSQL("update Pay set status=? where Date like ? ",new String[]{status,time+"%"});
		}
	 public void updatePay(SQLiteDatabase db,String id,String MenuName,Double count,String PayTo,String MakePerson,String Date,String MakeNote){
			db.execSQL("update Pay set MenuName=?,count=?,PayTo=?,MakePerson=?,Date=?,MakeNote=? where id like ? ",new Object[]{MenuName,count,PayTo,MakePerson,Date,MakeNote,id});
		}
	 public void Delete(SQLiteDatabase db,String id){
			db.execSQL("delete from Pay where id =?", new String[]{id});
			}
	 public List<Pay> queryAllPay(SQLiteDatabase db){
			List<Pay> list=new ArrayList<Pay>();
			  Cursor cursor=db.query("Pay", null, null, null, null, null, "Date ASC");
			while(cursor.moveToNext()){
				Pay pay=new Pay();
	    		pay.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
	    		pay.setDate(cursor.getString(cursor.getColumnIndex("Date")));
	    		pay.setPayTo(cursor.getString(cursor.getColumnIndex("PayTo")));
	    		pay.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
	    		pay.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
	    		pay.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
	    		pay.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
	    		pay.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
	    		list.add(pay);
			}
			  cursor.close();
			return list;
		}
	 public List<Pay> queryAllPayByTime(SQLiteDatabase db,String time){
			List<Pay> list=new ArrayList<Pay>();
			  Cursor cursor=db.query("Pay", null,"Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date ASC");
			while(cursor.moveToNext()){
				Pay pay=new Pay();
	    		pay.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
	    		pay.setDate(cursor.getString(cursor.getColumnIndex("Date")));
	    		pay.setPayTo(cursor.getString(cursor.getColumnIndex("PayTo")));
	    		pay.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
	    		pay.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
	    		pay.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
	    		pay.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
	    		pay.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
	    		list.add(pay);
			}
			  cursor.close();
			return list;
		}
	 public List<Pay> queryAllPayBySource(SQLiteDatabase db,String time,String source){
			List<Pay> list=new ArrayList<Pay>();
			  Cursor cursor=db.query("Pay", null,"Date like ? and PayTo=?", new String[]{"%"+time+"%",source}, null, null, "Date ASC");
			while(cursor.moveToNext()){
				Pay pay=new Pay();
	    		pay.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
	    		pay.setDate(cursor.getString(cursor.getColumnIndex("Date")));
	    		pay.setPayTo(cursor.getString(cursor.getColumnIndex("PayTo")));
	    		pay.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
	    		pay.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
	    		pay.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
	    		pay.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
	    		pay.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
	    		list.add(pay);
			}
			  cursor.close();
			return list;
		}
	 public Cursor queryPayTimeUp(SQLiteDatabase db,String time){
		 Cursor cursor=db.query("Pay", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date ASC");
		  return cursor;
	  }
	public Cursor queryPayTimeDown(SQLiteDatabase db,String time){
		Cursor cursor=db.query("Pay", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date DESC");
		  return cursor;
	  }
	public Cursor queryPayCountUp(SQLiteDatabase db,String time){
		Cursor cursor=db.query("Pay", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "count ASC");
		  return cursor;
	  }
	public Cursor queryPayCountDown(SQLiteDatabase db,String time){
		Cursor cursor=db.query("Pay", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "count DESC");
		  return cursor;
	  }
	 public double TotalCount(SQLiteDatabase db,String time){

		 Cursor cursor=db.rawQuery("select sum(count) from Pay where Date like  ?",new String[]{"%"+time+"%"});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCountBySource(SQLiteDatabase db,String source,String time){

		 Cursor cursor=db.rawQuery("select sum(count) from Pay where PayTo =? and Date like ?",new String[]{source,"%"+time+"%"});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCountBySource(SQLiteDatabase db,String source,String time1,String time2){

		 Cursor cursor=db.rawQuery("select sum(count) from Pay where PayTo =? and Date >= ? and Date<=?",new String[]{source,time1,time2});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCount(SQLiteDatabase db,String time,String time1){

		 Cursor cursor=db.rawQuery("select sum(count) from Pay where Date >= ? and Date<=?",new String[]{time,time1});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public long TotalCount1(SQLiteDatabase db){

		 Cursor cursor=db.rawQuery("select count(*) from Pay ",null);
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
		}
}
