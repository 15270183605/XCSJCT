package sqlite;

import java.util.ArrayList;
import java.util.List;

import entity.YingFu;
import entity.YingShou;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DaiXiCount extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table DaiXi(_id integer primary key autoincrement,id,Property,MenuName,count double,YingFuTo,YingFuObject,telephone,MakePerson,Date,status,MakeNote)";
	public DaiXiCount(Context context, String name, CursorFactory factory,
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
		// TODO Auto-generated method stub

	}
	public void addYingShou(SQLiteDatabase db,String id,String Property,String MenuName,Double count,String YingFuTo,String YingFuObject,String telephone,String MakePerson,String Date,String status,String MakeNote){
		  db.execSQL("insert into DaiXi values(null,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{id,Property,MenuName,count,YingFuTo,YingFuObject,telephone,MakePerson,Date,status,MakeNote});
	}
	 public List<YingFu> queryAllYingFu(SQLiteDatabase db){
			List<YingFu> list=new ArrayList<YingFu>();
			  Cursor cursor=db.query("DaiXi", null, null, null, null, null, "Date ASC");
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
	 public List<YingFu> queryAllYingFu(SQLiteDatabase db,String object){
			List<YingFu> list=new ArrayList<YingFu>();
			  Cursor cursor=db.rawQuery("select * from DaiXi where YingFuTo=?",new String[]{object});
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
}
