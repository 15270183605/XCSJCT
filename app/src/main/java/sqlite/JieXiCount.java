package sqlite;

import java.util.ArrayList;
import java.util.List;

import entity.YingShou;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class JieXiCount extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table JieXi(_id integer primary key autoincrement,id,Property,MenuName,count double,YingShouSource,YingShouObject,telephone,MakePerson,Date,status,MakeNote)";
	public JieXiCount(Context context, String name, CursorFactory factory,
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
	public void addYingShou(SQLiteDatabase db,String id,String Property,String MenuName,Double count,String YingShouSource,String YingShouObject,String telephone,String MakePerson,String Date,String status,String MakeNote){
		  db.execSQL("insert into JieXi values(null,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{id,Property,MenuName,count,YingShouSource,YingShouObject,telephone,MakePerson,Date,status,MakeNote});
	}
	 public List<YingShou> queryAllYingShou(SQLiteDatabase db){
			List<YingShou> list=new ArrayList<YingShou>();
			  Cursor cursor=db.query("JieXi", null, null, null, null, null, "Date ASC");
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
	 public List<YingShou> queryAllYingShou(SQLiteDatabase db,String object){
			List<YingShou> list=new ArrayList<YingShou>();
			Cursor cursor=db.rawQuery("select * from JieXi where YingShouObject=?", new String[]{object});
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
}
