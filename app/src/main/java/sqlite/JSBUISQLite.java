package sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.JiShiBenEntity;

public class JSBUISQLite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table JSBUIFile(_id integer primary key autoincrement,FileName,FilePath,Time,Start,Length)";
	public JSBUISQLite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int oldversion, int newversion) {
		

	}
   public void AddFileData(SQLiteDatabase db,JiShiBenEntity entity){
	   db.execSQL("insert into JSBUIFile values(null,?,?,?,?,?)", new Object[]{entity.getFileName(),entity.getFilePath(),entity.getTime(),entity.getStart(),entity.getLength()});
   }
   public void UpdateFileData(SQLiteDatabase db,String time,long start,long length){
	   db.execSQL("update JSBUIFile set Start=? and Length=? where Time=?", new Object[]{start,length,time});
   }
   public void UpdateFileDataLength(SQLiteDatabase db,String time,long length){
	   db.execSQL("update JSBUIFile set  Length=? where Time=?", new Object[]{length,time});
   }
   public void UpdateFileDataStart(SQLiteDatabase db,String time,long start){
	   db.execSQL("update JSBUIFile set Start=? where Time>?", new Object[]{start,time});
   }
   public long QueryFileDataLength(SQLiteDatabase db,String time){
	   Cursor cursor=db.rawQuery("select Length from JSBUIFile where Time=?", new String[]{time});
      cursor.moveToFirst();
      long count=cursor.getLong(0);
      cursor.close();
      return count;
   }
   public long QueryFileDataStart(SQLiteDatabase db,String time){
	   Cursor cursor=db.rawQuery("select Start from JSBUIFile where Time=?", new String[]{time});
	      cursor.moveToFirst();
	  long count=cursor.getLong(0);
	  cursor.close();
	      return count;
   }
   public JiShiBenEntity QueryFileData(SQLiteDatabase db,String time){
	   JiShiBenEntity entity = null;
	   Cursor cursor=db.rawQuery("select * from JSBUIFile where Time=?", new String[]{time});
	    if(cursor.getCount()!=0){ 
	    	while(cursor.moveToNext()){
	    		String FileName=cursor.getString(cursor.getColumnIndex("FileName"));
	    		String FilePath=cursor.getString(cursor.getColumnIndex("FilePath"));
	    		String Time=cursor.getString(cursor.getColumnIndex("Time"));
	    		long start=cursor.getLong(cursor.getColumnIndex("Start"));
	    		long length=cursor.getLong(cursor.getColumnIndex("Length"));
	    		entity=new JiShiBenEntity(FileName, FilePath, Time, start, length);
	    	}
	    }
	    cursor.close();
	    return entity;
   }
   public int getCountByTime(SQLiteDatabase db,String time){
	   Cursor cursor=db.rawQuery("select count(*) from JSBUIFile  where Time=?", new String[]{time});
	   cursor.moveToFirst();
	   int count=cursor.getInt(0);
	   cursor.close();
	   return count;
   }
   
}
