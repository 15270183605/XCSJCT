package sqlite;

import java.util.ArrayList;
import java.util.List;

import entity.JieZhangTemplate;
import entity.YingFu;
import entity.YingShou;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class YingFuSQLite extends SQLiteOpenHelper{
	final  String CREATE_TABLE_SQL="create table YingFu(_id integer primary key autoincrement,id,Property,MenuName,count double,YingFuTo,YingFuObject,telephone,MakePerson,Date,status,MakeNote)";
	public YingFuSQLite(Context context, String name,
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
	public void addYingFu(SQLiteDatabase db,String id,String Property,String MenuName,Double count,String YingFuTo,String YingFuObject,String telephone ,String MakePerson,String Date,String status,String MakeNote){
		  db.execSQL("insert into YingFu values(null,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{id,Property,MenuName,count,YingFuTo,YingFuObject,telephone,MakePerson,Date,status,MakeNote});
	}
	public Cursor queryYingFu(SQLiteDatabase db){
		  Cursor cursor=db.rawQuery("select * from YingFu", null);
		  return cursor;
	  }
	//根据单据id查询单据是否存在
	public long IdCount(SQLiteDatabase db,String id){
		Cursor cursor=db.rawQuery("select count(*) from YingFu where  id like ?",new String[]{id});
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
	}
	//根据时间查询单据笔数
	public long TimeCount(SQLiteDatabase db,String time,String Property){
		Cursor cursor=db.rawQuery("select count(*) from YingFu where  Date like ? and Property = ? ",new String[]{"%"+time+"%",Property});
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
	}
	//查询为锁定或者未核销的单据
	public List<JieZhangTemplate> StatusCount(SQLiteDatabase db,String time,String Property,String status){
		 List<JieZhangTemplate> datas=new ArrayList<JieZhangTemplate>();
			Cursor cursor=db.rawQuery("select * from YingFu where  Date like ? and Property = ? and status=?",new String[]{"%"+time+"%",Property,status});
			while(cursor.moveToNext()){
				String className=cursor.getString(cursor.getColumnIndex("MenuName"));
						String date=cursor.getString(cursor.getColumnIndex("Date"));
						double count=cursor.getDouble(cursor.getColumnIndex("count"));
						int id=cursor.getInt(cursor.getColumnIndex("id"));
						String object=cursor.getString(cursor.getColumnIndex("YingFuObject"));
				JieZhangTemplate template=new JieZhangTemplate(className, count, date, Integer.parseInt(status), id,object);
			   datas.add(template);
			}
			cursor.close();
			return datas;
	}
	
	public long PropertyCount(SQLiteDatabase db,String Property){
		Cursor cursor=db.rawQuery("select count(*) from YingFu where  Property = ? ",new String[]{Property});
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
	}
	 public YingFu queryFuById(SQLiteDatabase db,String id){
		 YingFu yingfu=new YingFu();
		  Cursor cursor=db.rawQuery("select * from YingFu where id like ?", new String[]{id});
		 if(cursor.moveToNext()){
		  		yingfu.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingfu.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingfu.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingfu.setYingFuTo(cursor.getString(cursor.getColumnIndex("YingFuTo")));
		  		yingfu.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingfu.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingfu.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingfu.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingfu.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingfu.setYingFuObject(cursor.getString(cursor.getColumnIndex("YingFuObject")));
		  		yingfu.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		 }
		 cursor.close();
		  return yingfu;
	  }
	 public void updateStatus(SQLiteDatabase db,String id,String status){
			db.execSQL("update YingFu set status=? where id like ? ",new String[]{status,id});
		}
	 //根据时间更新单据的状态
	 public void updateStatusByTime(SQLiteDatabase db,String status,String time,String property ){
			db.execSQL("update YingFu set status=? where Date like ? and Property=?",new String[]{status,time+"%",property});
		}
	 public void updateYingFu(SQLiteDatabase db,String id,String MenuName,Double count,String YingFuTo,String YingFuObject,String telephone,String MakePerson,String Date,String MakeNote){
			db.execSQL("update YingFu set MenuName=?,count=?,YingFuTo=?,YingFuObject=?,telephone=?,MakePerson=?,Date=?,MakeNote=? where id like ? ",new Object[]{MenuName,count,YingFuTo,YingFuObject,telephone,MakePerson,Date,MakeNote,id});
		}
	 public void Delete(SQLiteDatabase db,String id){
			db.execSQL("delete from YingFu  where id =?", new String[]{id});
			}
	 public String queryFuByName(SQLiteDatabase db,String name){
		 String tele=null;
		  Cursor cursor=db.rawQuery("select telephone from YingFu where  YingFuObject like ?", new String[]{name});
		  if(cursor!=null){
				 if(cursor.moveToNext()){
					 tele=cursor.getString(cursor.getColumnIndex("telephone"));
				 }
			 }
		  cursor.close();
		  return tele;
	  }
	 public List<YingFu> queryAllYingFu(SQLiteDatabase db){
			List<YingFu> list=new ArrayList<YingFu>();
			  Cursor cursor=db.query("YingFu", null, null, null, null, null, "Date ASC");
			while(cursor.moveToNext()){
				YingFu yingfu=new YingFu();
		  		yingfu.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingfu.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingfu.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingfu.setYingFuTo(cursor.getString(cursor.getColumnIndex("YingFuTo")));
		  		yingfu.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingfu.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingfu.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingfu.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingfu.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingfu.setYingFuObject(cursor.getString(cursor.getColumnIndex("YingFuObject")));
		  		yingfu.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		list.add(yingfu);
			}
			cursor.close();
			return list;
		}
	 public List<YingFu> queryAllYingFu(SQLiteDatabase db,String property){
			List<YingFu> list=new ArrayList<YingFu>();
			  Cursor cursor=db.rawQuery("select * from YingFu where Property=? ", new String[]{property});
			while(cursor.moveToNext()){
				YingFu yingfu=new YingFu();
		  		yingfu.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingfu.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingfu.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingfu.setYingFuTo(cursor.getString(cursor.getColumnIndex("YingFuTo")));
		  		yingfu.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingfu.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingfu.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingfu.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingfu.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingfu.setYingFuObject(cursor.getString(cursor.getColumnIndex("YingFuObject")));
		  		yingfu.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		list.add(yingfu);
			}
			cursor.close();
			return list;
		}
	 public List<YingFu> queryAllYingFuByTime(SQLiteDatabase db,String time){
			List<YingFu> list=new ArrayList<YingFu>();
			  Cursor cursor=db.query("YingFu", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date ASC");
			while(cursor.moveToNext()){
				YingFu yingfu=new YingFu();
		  		yingfu.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingfu.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingfu.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingfu.setYingFuTo(cursor.getString(cursor.getColumnIndex("YingFuTo")));
		  		yingfu.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingfu.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingfu.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingfu.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingfu.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingfu.setYingFuObject(cursor.getString(cursor.getColumnIndex("YingFuObject")));
		  		yingfu.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		list.add(yingfu);
			}
			cursor.close();
			return list;
		}
	 public List<YingFu> queryAllYingFuByProperty(SQLiteDatabase db,String time,String property){
			List<YingFu> list=new ArrayList<YingFu>();
			  Cursor cursor=db.query("YingFu", null, "Date like ? and  Property=?", new String[]{"%"+time+"%",property}, null, null, "Date ASC");
			while(cursor.moveToNext()){
				YingFu yingfu=new YingFu();
		  		yingfu.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingfu.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingfu.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingfu.setYingFuTo(cursor.getString(cursor.getColumnIndex("YingFuTo")));
		  		yingfu.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingfu.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingfu.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingfu.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingfu.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingfu.setYingFuObject(cursor.getString(cursor.getColumnIndex("YingFuObject")));
		  		yingfu.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		list.add(yingfu);
			}
			cursor.close();
			return list;
		}
	 public List<YingFu> queryAllYingFuBySource(SQLiteDatabase db,String time,String source){
			List<YingFu> list=new ArrayList<YingFu>();
			  Cursor cursor=db.query("YingFu", null, "Date like ? and YingFuTo=? ", new String[]{"%"+time+"%",source}, null, null, "Date ASC");
			while(cursor.moveToNext()){
				YingFu yingfu=new YingFu();
		  		yingfu.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex("id"))));
		  		yingfu.setCount(Double.valueOf(cursor.getDouble(cursor.getColumnIndex("count"))));
		  		yingfu.setDate(cursor.getString(cursor.getColumnIndex("Date")));
		  		yingfu.setYingFuTo(cursor.getString(cursor.getColumnIndex("YingFuTo")));
		  		yingfu.setMakeNote(cursor.getString(cursor.getColumnIndex("MakeNote")));
		  		yingfu.setMakePerson(cursor.getString(cursor.getColumnIndex("MakePerson")));
		  		yingfu.setMenuName(cursor.getString(cursor.getColumnIndex("MenuName")));
		  		yingfu.setStatus(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status"))));
		  		yingfu.setProperty(Integer.valueOf(cursor.getString(cursor.getColumnIndex("Property"))));
		  		yingfu.setYingFuObject(cursor.getString(cursor.getColumnIndex("YingFuObject")));
		  		yingfu.setTelephone(cursor.getString(cursor.getColumnIndex("telephone")));
		  		list.add(yingfu);
			}
			cursor.close();
			return list;
		}
	 public Cursor queryYingFuTimeUp(SQLiteDatabase db,String time){
		 Cursor cursor=db.query("YingFu", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "Date ASC");
		  return cursor;
	  }
	public Cursor queryYingFuTimeDown(SQLiteDatabase db,String time){
		Cursor cursor=db.query("YingFu", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "DateDESC");
		  return cursor;
	  }
	public Cursor queryYingFuCountUp(SQLiteDatabase db,String time){
		Cursor cursor=db.query("YingFu", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "count ASC");
		  return cursor;
	  }
	public Cursor queryYingFuCountDown(SQLiteDatabase db,String time){
		Cursor cursor=db.query("YingFu", null, "Date like ? ", new String[]{"%"+time+"%"}, null, null, "count DESC");		
		return cursor;
	  }
	 //检查当前借款方在数据库中是否存在，若存在则下次直接更新记录，若不存在则添加数据记录；
	 public int Count(SQLiteDatabase db,String YingFuObject,String Property){
			Cursor cursor=db.rawQuery("select count(*) from YingFu where YingFuObject = ? and Property = ?",new String[]{YingFuObject,Property});
			    cursor.moveToFirst();
			    int count=cursor.getInt(0);
			    cursor.close();
			    return count;
		}
	 public void updateCount(SQLiteDatabase db,double count,String YingFuObject,String Property){
		 db.execSQL("update YingFu set count=? where YingFuObject = ? and Property = ? ",new Object[]{count,YingFuObject,Property});
	 }
	 public double getCount(SQLiteDatabase db,String YingFuObject,String Property){
		 double count=0.0;
		Cursor cursor=db.rawQuery("select count from YingFu where YingFuObject = ? and Property = ?",new String[]{YingFuObject,Property});
		cursor.moveToFirst();
		count=cursor.getDouble(0);
		cursor.close();
		 return count;
	 }
	 public Cursor queryAllYingFuByTimeAndProperty(SQLiteDatabase db,String time,String Property){
			  Cursor cursor=db.query("YingFu", null,"Date like ? and Property like ? ",  new String[]{"%"+time+"%",Property}, null, null, "Date ASC");
			return cursor;
		}
	 public double TotalCount(SQLiteDatabase db,String time,String Property){

		 Cursor cursor=db.rawQuery("select sum(count) from YingFu where Date like ? and Property = ?",new String[]{"%"+time+"%",Property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 //根据对象找金额
	 public double TotalCount1(SQLiteDatabase db,String object,String Property){
		 Cursor cursor=db.rawQuery("select sum(count) from YingFu where YingFuObject = ? and Property = ?",new String[]{object,Property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCountByProperty(SQLiteDatabase db,String Property){

		 Cursor cursor=db.rawQuery("select sum(count) from YingFu where Property = ?",new String[]{Property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCountBySource(SQLiteDatabase db,String source,String time,String property){

		 Cursor cursor=db.rawQuery("select sum(count) from YingFu where YingFuTo =? and Date like ? and Property=?",new String[]{source,"%"+time+"%",property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCount(SQLiteDatabase db,String time1,String time2,String Property){

		 Cursor cursor=db.rawQuery("select sum(count) from YingFu where Date >= ? and Date<=? and Property = ?",new String[]{time1,time2,Property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public double TotalCountBySource(SQLiteDatabase db,String source,String time1,String time2,String property){

		 Cursor cursor=db.rawQuery("select sum(count) from YingFu where YingFuTo =? and Date >= ? and Date<=? and Property = ?",new String[]{source,time1,time2,property});
		    cursor.moveToFirst();
		    double count=cursor.getDouble(0);
		    cursor.close();
		    return count;
		}
	 public long TotalCount1(SQLiteDatabase db){

		 Cursor cursor=db.rawQuery("select count(*) from YingFu ",null);
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
		}
}
