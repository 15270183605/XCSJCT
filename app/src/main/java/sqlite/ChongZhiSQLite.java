package sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.ChongZhiEntity;

public class ChongZhiSQLite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table ChongZhi(_id integer primary key autoincrement,ChongZhiContent,ChongZhiNum,ChongZhiDate,ChongZhiWay,ChongZhiImage BLOB)";
	public ChongZhiSQLite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		

	}
  public void insertData(SQLiteDatabase db,String content,double num,String time,String Way,byte[] image){
	  db.execSQL("insert into ChongZhi values(null,?,?,?,?,?)",new Object[]{content,num,time,Way,image});
  }
  public List<ChongZhiEntity> QueryData(SQLiteDatabase db,String time){
	  List<ChongZhiEntity> datas=new ArrayList<ChongZhiEntity>();
	  Cursor cursor=db.rawQuery("select * from ChongZhi where ChongZhiDate like ?", new String[]{"%"+time+"%"});
	  while(cursor.moveToNext()){
		  String Content=cursor.getString(cursor.getColumnIndex("ChongZhiContent"));
		  double num=cursor.getDouble(cursor.getColumnIndex("ChongZhiNum"));
		  String date=cursor.getString(cursor.getColumnIndex("ChongZhiDate"));
		  String way=cursor.getString(cursor.getColumnIndex("ChongZhiWay"));
		  byte[] image=cursor.getBlob(cursor.getColumnIndex("ChongZhiImage"));
		  ChongZhiEntity entity=new ChongZhiEntity(Content,num,date,way,image);
		  datas.add(entity);
	  }
	  return datas;
  }
}
