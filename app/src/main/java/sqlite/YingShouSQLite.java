package sqlite;

import java.util.ArrayList;
import java.util.List;

import entity.JieZhangTemplate;
import entity.Pay;
import entity.YingShou;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class YingShouSQLite extends SQLiteOpenHelper{
	final  String CREATE_TABLE_SQL="create table YingShou(_id integer primary key autoincrement,id,Property,MenuName,count double,YingShouSource,YingShouObject,telephone,MakePerson,Date,status,MakeNote)";
	public YingShouSQLite(Context context, String name,
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
	public void addYingShou(SQLiteDatabase db,String id,String Property,String MenuName,Double count,String YingShouSource,String YingShouObject,String telephone,String MakePerson,String Date,String status,String MakeNote){
		  db.execSQL("insert into YingShou values(null,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{id,Property,MenuName,count,YingShouSource,YingShouObject,telephone,MakePerson,Date,status,MakeNote});
	}
	public Cursor queryYingShou(SQLiteDatabase db){
		  Cursor cursor=db.rawQuery("select * from YingShou", null);
		  return cursor;
	  }
	//根据单据id查询单据是否存在
	public long IdCount(SQLiteDatabase db,String id){
		Cursor cursor=db.rawQuery("select count(*) from YingShou where  id like ?",new String[]{id});
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
	}
	//根据时间查询单据的笔数
	public long TimeCount(SQLiteDatabase db,String time,String Property){
		Cursor cursor=db.rawQuery("select count(*) from YingShou where  Date like ? and Property = ? ",new String[]{"%"+time+"%",Property});
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
	}
	//查询为锁定或者未核销的单据
			public List<JieZhangTemplate> StatusCount(SQLiteDatabase db,String time,String Property,String status){
				 List<JieZhangTemplate> datas=new ArrayList<JieZhangTemplate>();
					Cursor cursor=db.rawQuery("select * from YingShou where  Date like ? and Property = ? and status=?",new String[]{"%"+time+"%",Property,status});
					while(cursor.moveToNext()){
						String className=cursor.getString(cursor.getColumnIndex("MenuName"));
								String date=cursor.getString(cursor.getColumnIndex("Date"));
								double count=cursor.getDouble(cursor.getColumnIndex("count"));
								int id=cursor.getInt(cursor.getColumnIndex("id"));
								String object=cursor.getString(cursor.getColumnIndex("YingShouObject"));
						JieZhangTemplate template=new JieZhangTemplate(className, count, date, Integer.parseInt(status), id,object);
					   datas.add(template);
					}
					cursor.close();
					return datas;
			}
	public long PropertyCount(SQLiteDatabase db,String Property){
		Cursor cursor=db.rawQuery("select count(*) from YingShou where  Property = ? ",new String[]{Property});
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
	}
	 public YingShou queryShouById(SQLiteDatabase db,String id){ 
		 YingShou yingshou=new YingShou();
		  Cursor cursor=db.rawQuery("select * from YingShou where id like ?", new String[]{id});
		  if(cursor.moveToNext()){
		  		yingshou.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingshou.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingshou.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingshou.setYingShouSource(cursor.getString(cursor.getColumnIndex("YingShouSource")));
		  		yingshou.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingshou.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingshou.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingshou.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingshou.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingshou.setYingShouObject(cursor.getString(cursor.getColumnIndex("YingShouObject")));
		  		yingshou.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  }
		  cursor.close();
		  return yingshou;
	  }
	 public void updateStatus(SQLiteDatabase db,String id,String status){
			db.execSQL("update YingShou set status=? where id like ? ",new String[]{status,id});
		}
	 //根据时间更新单据的状态
	 public void updateStatusByTime(SQLiteDatabase db,String status,String time,String property ){
			db.execSQL("update YingShou set status=? where Date like ? and Property=?",new String[]{status,time+"%",property});
		}
	 public void updateYingShou(SQLiteDatabase db,String id,String MenuName,Double count,String YingShouSource,String YingShouObject,String telephone,String MakePerson,String Date,String MakeNote){
			db.execSQL("update YingShou set MenuName=?,count=?,YingShouSource=?,YingShouObject=?,telephone=?,MakePerson=?,Date=?,MakeNote=? where id like ? ",new Object[]{MenuName,count,YingShouSource,YingShouObject,telephone,MakePerson,Date,MakeNote,id});
		}
	 public void Delete(SQLiteDatabase db,String id){
			db.execSQL("delete from YingShou  where id =?", new String[]{id});
			}
	 public String queryShouByName(SQLiteDatabase db,String name){
		 String tele=null;
		  Cursor cursor=db.rawQuery("select telephone from YingShou where  YingShouObject like ?", new String[]{name});
		 if(cursor!=null){
			 if(cursor.moveToNext()){
				 tele=cursor.getString(cursor.getColumnIndex("telephone"));
			 }
		 }
		 cursor.close();
		  return tele;
	  }
	 public List<YingShou> queryAllYingShou(SQLiteDatabase db){
			List<YingShou> list=new ArrayList<YingShou>();
			  Cursor cursor=db.query("YingShou", null, null, null, null, null, "Date ASC");
			while(cursor.moveToNext()){
				YingShou yingshou=new YingShou();
		  		yingshou.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingshou.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingshou.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingshou.setYingShouSource(cursor.getString(cursor.getColumnIndex("YingShouSource")));
		  		yingshou.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingshou.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingshou.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingshou.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingshou.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingshou.setYingShouObject(cursor.getString(cursor.getColumnIndex("YingShouObject")));
		  		yingshou.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		list.add(yingshou);
			}
			cursor.close();
			return list;
		}
	 public List<YingShou> queryAllYingShou(SQLiteDatabase db,String property){
			List<YingShou> list=new ArrayList<YingShou>();
			  Cursor cursor=db.rawQuery("select * from YingShou where Property=?", new String[]{property});
			while(cursor.moveToNext()){
				YingShou yingshou=new YingShou();
		  		yingshou.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingshou.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingshou.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingshou.setYingShouSource(cursor.getString(cursor.getColumnIndex("YingShouSource")));
		  		yingshou.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingshou.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingshou.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingshou.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingshou.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingshou.setYingShouObject(cursor.getString(cursor.getColumnIndex("YingShouObject")));
		  		yingshou.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		list.add(yingshou);
			}
			cursor.close();
			return list;
		}
	 public List<YingShou> queryAllYingShouByTime(SQLiteDatabase db,String time){
			List<YingShou> list=new ArrayList<YingShou>();
			  Cursor cursor=db.query("YingShou", null,"Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date ASC");
			while(cursor.moveToNext()){
				YingShou yingshou=new YingShou();
		  		yingshou.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingshou.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingshou.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingshou.setYingShouSource(cursor.getString(cursor.getColumnIndex("YingShouSource")));
		  		yingshou.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingshou.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingshou.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingshou.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingshou.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingshou.setYingShouObject(cursor.getString(cursor.getColumnIndex("YingShouObject")));
		  		yingshou.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		list.add(yingshou);
		  		
			}
			cursor.close();
			return list;
		}
	 public List<YingShou> queryAllYingShouByProperty(SQLiteDatabase db,String time,String property){
			List<YingShou> list=new ArrayList<YingShou>();
			  Cursor cursor=db.query("YingShou", null,"Date like ? and Property=?", new String[]{"%"+time+"%",property}, null, null, "Date ASC");
			while(cursor.moveToNext()){
				YingShou yingshou=new YingShou();
		  		yingshou.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingshou.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingshou.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingshou.setYingShouSource(cursor.getString(cursor.getColumnIndex("YingShouSource")));
		  		yingshou.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingshou.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingshou.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingshou.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingshou.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingshou.setYingShouObject(cursor.getString(cursor.getColumnIndex("YingShouObject")));
		  		yingshou.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		list.add(yingshou);
		  		
			}
			cursor.close();
			return list;
		}
	 public List<YingShou> queryAllYingShouBySource(SQLiteDatabase db,String time,String source){
			List<YingShou> list=new ArrayList<YingShou>();
			  Cursor cursor=db.query("YingShou", null,"Date like ? and YingShouSource=?", new String[]{"%"+time+"%",source}, null, null, "Date ASC");
			while(cursor.moveToNext()){
				YingShou yingshou=new YingShou();
		  		yingshou.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingshou.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingshou.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingshou.setYingShouSource(cursor.getString(cursor.getColumnIndex("YingShouSource")));
		  		yingshou.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingshou.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingshou.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingshou.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingshou.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingshou.setYingShouObject(cursor.getString(cursor.getColumnIndex("YingShouObject")));
		  		yingshou.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		list.add(yingshou);
		  		
			}
			cursor.close();
			return list;
		}
	 public Cursor queryAllYingShouByTimeAndProperty(SQLiteDatabase db,String time,String Property){
			List<YingShou> list=new ArrayList<YingShou>();
			  Cursor cursor=db.query("YingShou", null,"Date like ? and Property like ? ",  new String[]{"%"+time+"%",Property}, null, null, "Date ASC");
			return cursor;
		}
	 public Cursor queryYingShouTimeUp(SQLiteDatabase db,String time){
		 Cursor cursor=db.query("YingShou", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date ASC");
		  return cursor;
	  }
	public Cursor queryYingShouTimeDown(SQLiteDatabase db,String time){
		 Cursor cursor=db.query("YingShou", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date DESC");
		  return cursor;
	  }
	public Cursor queryYingShouCountUp(SQLiteDatabase db,String time){
		 Cursor cursor=db.query("YingShou", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "count ASC");
		  return cursor;
	  }
	public Cursor queryYingShouCountDown(SQLiteDatabase db,String time){
		 Cursor cursor=db.query("YingShou", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "count DESC");
		  return cursor;}
	  //检查当前借款方在数据库中是否存在，若存在则下次直接更新记录，若不存在则添加数据记录；
	 public int Count(SQLiteDatabase db,String YingShouObject,String Property){
			Cursor cursor=db.rawQuery("select count(*) from YingShou where YingShouObject = ? and Property = ?",new String[]{YingShouObject,Property});
			    cursor.moveToFirst();
			    int count=cursor.getInt(0);
			    cursor.close();
			    return count;
		}
	 public void updateCount(SQLiteDatabase db,double count,String YingShouObject,String Property){
		 db.execSQL("update YingShou set count=? where YingShouObject = ? and Property = ? ",new Object[]{count,YingShouObject,Property});
	 }
	 public double getCount(SQLiteDatabase db,String YingShouObject,String Property){
		 double count=0.0;
		Cursor cursor=db.rawQuery("select count from YingShou where YingShouObject = ? and Property = ?",new String[]{YingShouObject,Property});
		cursor.moveToFirst();
		count=cursor.getDouble(0);
		cursor.close();
		 return count;
	 }
	 public double TotalCount(SQLiteDatabase db,String time,String Property){

		 Cursor cursor=db.rawQuery("select sum(count) from YingShou where Date like ? and Property = ?",new String[]{"%"+time+"%",Property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	//根据对象找金额
	 public double TotalCount1(SQLiteDatabase db,String object,String Property){
		 Cursor cursor=db.rawQuery("select sum(count) from YingShou where YingShouObject = ? and Property = ?",new String[]{object,Property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCountByProperty(SQLiteDatabase db,String Property){
		 Cursor cursor=db.rawQuery("select sum(count) from YingShou where  Property = ?",new String[]{Property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCountBySource(SQLiteDatabase db,String source,String time,String property){

		 Cursor cursor=db.rawQuery("select sum(count) from YingShou where YingShouSource =? and Date like ? and Property = ?",new String[]{source,"%"+time+"%",property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCount(SQLiteDatabase db,String time1,String time2,String Property){

		 Cursor cursor=db.rawQuery("select sum(count) from YingShou where Date >= ? and Date<=? and Property = ?",new String[]{time1,time2,Property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCountBySource(SQLiteDatabase db,String source,String time1,String time2,String property){

		 Cursor cursor=db.rawQuery("select sum(count) from YingShou where YingShouSource =? and Date >= ? and Date<=? and Property = ?",new String[]{source,time1,time2,property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public long TotalCount1(SQLiteDatabase db){

		 Cursor cursor=db.rawQuery("select count(*) from YingShou ",null);
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
		}
}
