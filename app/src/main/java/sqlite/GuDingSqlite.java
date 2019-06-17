package sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.GuDingEntity;

public class GuDingSqlite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table GuDingCount(_id integer primary key autoincrement,ProjectName,ClassName,Count)";
	public GuDingSqlite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
  public void Add(SQLiteDatabase db,GuDingEntity entity){
	  db.execSQL("insert into GuDingCount values(null,?,?,?)",new Object[]{entity.getProjectName(),entity.getClassName(),entity.getGuDingCount()});
  }
  public void Update(SQLiteDatabase db,double count,String projectname,String className,int id){
	  db.execSQL("update GuDingCount set Count=?,ProjectName=? , ClassName=? where _id=?", new Object[]{count,projectname,className,id});
  }
  public void Delete(SQLiteDatabase db,int id){
	  db.execSQL("delete from  GuDingCount  where _id=?", new String[]{String.valueOf(id)});
  }
  public List<GuDingEntity> QueryAllData(SQLiteDatabase db){
	  List<GuDingEntity> datas=new ArrayList<GuDingEntity>();
	  Cursor cursor=db.rawQuery("select * from GuDingCount", null);
	  while(cursor.moveToNext()){
		  int id=cursor.getInt(cursor.getColumnIndex("_id"));
		 String projectname=cursor.getString(cursor.getColumnIndex("ProjectName"));
		 String classname=cursor.getString(cursor.getColumnIndex("ClassName"));
		 double count=cursor.getDouble(cursor.getColumnIndex("Count"));
		 GuDingEntity entity=new GuDingEntity(id,classname, projectname, count);
		 datas.add(entity);
	  }
	  cursor.close();
	  return datas;
  }
 public GuDingEntity QueryData(SQLiteDatabase db,int id){
	 GuDingEntity entity=null;
	  Cursor cursor=db.rawQuery("select * from GuDingCount where _id=?", (String[]) new Object[]{id});
	  while(cursor.moveToNext()){
		 String projectname=cursor.getString(cursor.getColumnIndex("ProjectName"));
		 double count=cursor.getDouble(cursor.getColumnIndex("Count"));
		 String classname=cursor.getString(cursor.getColumnIndex("ClassName"));
		 entity=new GuDingEntity(id,classname, projectname, count);
		
	  }
	  cursor.close();
	  return entity;
  }
 public List<GuDingEntity> QueryDataByclassname(SQLiteDatabase db,String classname){
	 List<GuDingEntity> datas=new ArrayList<GuDingEntity>();
	  Cursor cursor=db.rawQuery("select * from GuDingCount where ClassName=?", new String[]{classname});
	  while(cursor.moveToNext()){
		 String projectname=cursor.getString(cursor.getColumnIndex("ProjectName"));
		 double count=cursor.getDouble(cursor.getColumnIndex("Count"));
		 int id=cursor.getInt(cursor.getColumnIndex("_id"));
		 GuDingEntity entity=new GuDingEntity(id,classname, projectname, count);
		datas.add(entity);
	  }
	  cursor.close();
	  return datas;
  }
 public double QueeryCount(SQLiteDatabase db,String classname){
	 double count=0.0;
	 Cursor cursor=db.rawQuery("select Count from GuDingCount where ClassName=?", new String[]{classname});
	 while(cursor.moveToNext()){
		 count=count+cursor.getDouble(cursor.getColumnIndex("Count"));
	 }
	 cursor.close();
	 return count;
 }
 public double QueeryCount1(SQLiteDatabase db,String classname,String projectname){
	 double count=0.0;
	 Cursor cursor=db.rawQuery("select Count from GuDingCount where ClassName=? and ProjectName=?", new String[]{classname,projectname});
	 while(cursor.moveToNext()){
		 count=count+cursor.getDouble(cursor.getColumnIndex("Count"));
	 }
	 cursor.close();
	 return count;
 }
 public int  QueryNum(SQLiteDatabase db,String classname,String ProjectName){
	 Cursor cursor=db.rawQuery("select count(*) from GuDingCount where ClassName=? and  ProjectName=?", new String[]{classname,ProjectName});
	 cursor.moveToFirst();
	 int num=cursor.getInt(0);
	 cursor.close();
	 return num;
 }
}
