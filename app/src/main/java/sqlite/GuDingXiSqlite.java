package sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.GuDingEntity;
import entity.GuDingMingXi;

public class GuDingXiSqlite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table GuDingXi(_id integer primary key autoincrement,ProjectName,MingXiName,Count,Property)";
	public GuDingXiSqlite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	public void Add(SQLiteDatabase db,GuDingMingXi entity){
		  db.execSQL("insert into GuDingXi values(null,?,?,?,?)",new Object[]{entity.getProjectName(),entity.getMingXiName(),entity.getCount(),entity.getProperty()});
	  }
	  public void Update(SQLiteDatabase db,double count,String projectname,String MingXiName,int property){
		  db.execSQL("update GuDingXi set Count=?,ProjectName=?,Property=? where MingXiName=?", new Object[]{count,projectname,MingXiName,property});
	  }
	  public void Delete(SQLiteDatabase db,String MingXiName){
		  db.execSQL("delete from  GuDingXi  where MingXiName=?", new String[]{MingXiName});
	  }
	  public List<GuDingMingXi> QueryAllData(SQLiteDatabase db){
		  List<GuDingMingXi> datas=new ArrayList<GuDingMingXi>();
		  Cursor cursor=db.rawQuery("select * from GuDingXi", null);
		  while(cursor.moveToNext()){
			 String projectname=cursor.getString(cursor.getColumnIndex("ProjectName"));
			 String classname=cursor.getString(cursor.getColumnIndex("MingXiName"));
			 double count=cursor.getDouble(cursor.getColumnIndex("Count"));
			 int Property=cursor.getInt(cursor.getColumnIndex("Property"));
			 GuDingMingXi entity=new GuDingMingXi( projectname, classname,count,Property);
			 datas.add(entity);
		  }
		  cursor.close();
		  return datas;
	  }
	 public GuDingMingXi QueryData(SQLiteDatabase db,String MingXiName){
		 GuDingMingXi entity=null;
		  Cursor cursor=db.rawQuery("select * from GuDingXi where MingXiName=?", new String[]{MingXiName});
		  while(cursor.moveToNext()){
			 String projectname=cursor.getString(cursor.getColumnIndex("ProjectName"));
			 double count=cursor.getDouble(cursor.getColumnIndex("Count"));
			 int Property=cursor.getInt(cursor.getColumnIndex("Property"));
			 entity=new GuDingMingXi( projectname,MingXiName, count,Property);
			
		  }
		  cursor.close();
		  return entity;
	  }
	 public List<GuDingMingXi> QueryDataByclassname(SQLiteDatabase db,String ProjectName){
		 List<GuDingMingXi> datas=new ArrayList<GuDingMingXi>();
		  Cursor cursor=db.rawQuery("select * from GuDingXi where ProjectName=?", new String[]{ProjectName});
		  while(cursor.moveToNext()){
			  String classname=cursor.getString(cursor.getColumnIndex("MingXiName"));
			 double count=cursor.getDouble(cursor.getColumnIndex("Count"));
			 int Property=cursor.getInt(cursor.getColumnIndex("Property"));
			 GuDingMingXi entity=new GuDingMingXi( ProjectName,classname, count,Property);
			datas.add(entity);
		  }
		  cursor.close();
		  return datas;
	  }
	 public List<GuDingMingXi> QueryDataByProjectname(SQLiteDatabase db,String ProjectName){
		 List<GuDingMingXi> datas=new ArrayList<GuDingMingXi>();
		  Cursor cursor=db.rawQuery("select * from GuDingXi where ProjectName=?", new String[]{ProjectName});
		  while(cursor.moveToNext()){
			 String MingXiName=cursor.getString(cursor.getColumnIndex("MingXiName"));
			 double count=cursor.getDouble(cursor.getColumnIndex("Count"));
			 int Property=cursor.getInt(cursor.getColumnIndex("Property"));
			 GuDingMingXi entity=new GuDingMingXi(ProjectName,MingXiName, count,Property);
			datas.add(entity);
		  }
		  cursor.close();
		  return datas;
	  }
	 public double QueeryCount(SQLiteDatabase db,String MingXiName){
		 double count=0.0;
		 Cursor cursor=db.rawQuery("select Count from GuDingXi where MingXiName=?", new String[]{MingXiName});
		 while(cursor.moveToNext()){
			 count=count+cursor.getDouble(cursor.getColumnIndex("Count"));
		 }
		 cursor.close();
		 return count;
	 }
	 public double QueeryCount1(SQLiteDatabase db,String MingXiName,String projectname){
		 double count=0.0;
		 Cursor cursor=db.rawQuery("select Count from GuDingXi where MingXiName=? and ProjectName=?", new String[]{MingXiName,projectname});
		 while(cursor.moveToNext()){
			 count=count+cursor.getDouble(cursor.getColumnIndex("Count"));
		 }
		 cursor.close();
		 return count;
	 }
	 public int  QueryNum(SQLiteDatabase db,String MingXiName,String ProjectName){
		 Cursor cursor=db.rawQuery("select count(*) from GuDingXi where MingXiName=? and  ProjectName=?", new String[]{MingXiName,ProjectName});
		 cursor.moveToFirst();
		 int num=cursor.getInt(0);
		 cursor.close();
		 return num;
	 }
	 public int  QueryNum1(SQLiteDatabase db,String ProjectName){
		 Cursor cursor=db.rawQuery("select count(*) from GuDingXi where ProjectName=?", new String[]{ProjectName});
		 cursor.moveToFirst();
		 int num=cursor.getInt(0);
		 cursor.close();
		 return num;
	 }
}

