 package sqlite;

import java.util.ArrayList;
import java.util.List;

import entity.Income;
import entity.JieZhangTemplate;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class IncomeSQLite extends SQLiteOpenHelper{
	final  String CREATE_TABLE_SQL="create table Income(_id integer primary key autoincrement,id,MenuName,count double,IncomeSource,MakePerson,Date,status,MakeNote)";
	public IncomeSQLite(Context context, String name,
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
	public void addIncome(SQLiteDatabase db,String id,String MenuName,Double count,String IncomeSource,String MakePerson,String Date,String status,String MakeNote){
	  db.execSQL("insert into Income values(null,?,?,?,?,?,?,?,?)",new Object[]{id,MenuName,count,IncomeSource,MakePerson,Date,status,MakeNote});
	}
	public Cursor queryIncomeById(SQLiteDatabase db,String id){
		  Cursor cursor=db.rawQuery("select * from Income where id = ?", new String[]{id});
		  return cursor;
	  }
	public Cursor queryIncomeTimeUp(SQLiteDatabase db,String time){
		Cursor cursor=db.query("Income", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date ASC");
		  return cursor;
	  }
	public Cursor queryIncomeTimeDown(SQLiteDatabase db,String time){
		Cursor cursor=db.query("Income", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date DESC");
		  return cursor;
	  }
	public Cursor queryIncomeCountUp(SQLiteDatabase db,String time){
		Cursor cursor=db.query("Income", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "count ASC");
		  return cursor;
	  }
	public Cursor queryIncomeCountDown(SQLiteDatabase db,String time){
		Cursor cursor=db.query("Income", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "count DESC");
		  return cursor;
	  }
	//根据Id查询单据是否存在
	 public long IdCount(SQLiteDatabase db,String id){
			Cursor cursor=db.rawQuery("select count(*) from Income where  id=?",new String[]{id});
			    cursor.moveToFirst();
			    long count=cursor.getLong(0);
			    cursor.close();
			    return count;
		}
	 //根据时间查单据比数
	 public long TimeCount(SQLiteDatabase db,String time){
			Cursor cursor=db.rawQuery("select count(*) from Income where  Date like ?",new String[]{"%"+time+"%"});
			    cursor.moveToFirst();
			    long count=cursor.getLong(0);
			    cursor.close();
			    return count;
		}
	 //查未锁定单据
	 public List<JieZhangTemplate> StatusCount(SQLiteDatabase db,String time,String status){
		 List<JieZhangTemplate> datas=new ArrayList<JieZhangTemplate>();
			Cursor cursor=db.rawQuery("select * from Income where  Date like ? and status=?",new String[]{"%"+time+"%",status});
			while(cursor.moveToNext()){
				String className=cursor.getString(cursor.getColumnIndex("MenuName"));
						String date=cursor.getString(cursor.getColumnIndex("Date"));
						double count=cursor.getDouble(cursor.getColumnIndex("count"));
						long id=cursor.getLong(cursor.getColumnIndex("id"));
				JieZhangTemplate template=new JieZhangTemplate(className, count, date, Integer.parseInt(status), id,"");
			   datas.add(template);
			}
			    
			    cursor.close();
			    return datas;
		}
	public void updateStatus(SQLiteDatabase db,String id,String status){
		db.execSQL("update Income set status=? where id =? ",new String[]{status,id});
	}
	//根据时间来更改单据的状态
	public void updateStatusbyTime(SQLiteDatabase db,String status,String time){
		db.execSQL("update Income set status=? where Date like ? ",new String[]{status,time+"%"});
	}
	public void updateIncome(SQLiteDatabase db,String id,String MenuName,double count,String IncomeSource,String MakePerson,String Date,String MakeNote){
		db.execSQL("update Income set MenuName=?,count=?,IncomeSource=?,MakePerson=?,Date=?,MakeNote=? where id like ? ",new Object[]{MenuName,count,IncomeSource,MakePerson,Date,MakeNote,id});
	}
	public void Delete(SQLiteDatabase db,String id){
	db.execSQL("delete from Income where id =?", new String[]{id});
	}
	public List<Income> queryAllIncome(SQLiteDatabase db){
		List<Income> list=new ArrayList<Income>();
		  Cursor cursor=db.query("Income", null, null, null, null, null, "Date ASC");
		while(cursor.moveToNext()){
			Income income=new Income();
    		income.setCount(cursor.getDouble(cursor.getColumnIndex("count")));
    		income.setDate(cursor.getString(cursor.getColumnIndex("Date")));
    		income.setIncomeSource(cursor.getString(cursor.getColumnIndex("IncomeSource")));
    		income.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
    		income.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
    		income.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
    		income.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
    		income.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
    		list.add(income);
		}
		cursor.close();
		return list;
	}
	public List<Income> queryAllIncomeByTime(SQLiteDatabase db,String time){
		List<Income> list=new ArrayList<Income>();
		  Cursor cursor=db.query("Income", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date ASC");
		while(cursor.moveToNext()){
			Income income=new Income();
    		income.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
    		income.setDate(cursor.getString(cursor.getColumnIndex("Date")));
    		income.setIncomeSource(cursor.getString(cursor.getColumnIndex("IncomeSource")));
    		income.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
    		income.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
    		income.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
    		income.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
    		income.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
    		list.add(income);
		}
		cursor.close();
		return list;
	}
	//根据时间查询总金额
	 public double TotalCount(SQLiteDatabase db,String time){

		 Cursor cursor=db.rawQuery("select sum(count) from Income where Date like  ?",new String[]{"%"+time+"%"});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 //根据收入类型查询总金额
	 public double TotalCountBySource(SQLiteDatabase db,String source,String time){

		 Cursor cursor=db.rawQuery("select sum(count) from Income where IncomeSource =? and Date like ?",new String[]{source,"%"+time+"%"});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public List<Income> queryAllIncomeBySource(SQLiteDatabase db,String time,String source){
			List<Income> list=new ArrayList<Income>();
			  Cursor cursor=db.query("Income", null, "Date like ? and IncomeSource=?", new String[]{"%"+time+"%",source}, null, null, "Date ASC");
			while(cursor.moveToNext()){
				Income income=new Income();
	    		income.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
	    		income.setDate(cursor.getString(cursor.getColumnIndex("Date")));
	    		income.setIncomeSource(cursor.getString(cursor.getColumnIndex("IncomeSource")));
	    		income.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
	    		income.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
	    		income.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
	    		income.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
	    		income.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
	    		list.add(income);
			}
			cursor.close();
			return list;
		}
	 public double TotalCountBySource(SQLiteDatabase db,String source,String time1,String time2){

		 Cursor cursor=db.rawQuery("select sum(count) from Income where IncomeSource =? and Date >= ? and Date<=?",new String[]{source,time1,time2});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCount(SQLiteDatabase db,String time,String time1){

		 Cursor cursor=db.rawQuery("select sum(count) from Income where Date >= ? and Date<=?",new String[]{time,time1});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public long TotalCount1(SQLiteDatabase db){
		 Cursor cursor=db.rawQuery("select count(*) from Income",null);
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
		}
}
