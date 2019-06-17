package sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.CountPredict;

public class CountPredictSQLite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table CountPredict(_id integer primary key autoincrement,ShouPredict double,ZhiPredict double,Date,ShouDeviate,ZhiDeviate)";
	public CountPredictSQLite(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);

	}
	public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newversion) {
		
      
	}
	public void addCountPredict(SQLiteDatabase db,CountPredict predict){
		  db.execSQL("insert into CountPredict values(null,?,?,?,?,?)",new Object[]{predict.getShouPredictCount(),predict.getZhiChuPreCount(),predict.getDate(),predict.getShouDeviate(),predict.getZhiDeviate()});
		}
	public List<CountPredict> getPredictDatas(SQLiteDatabase db,String time1,String time2){
		List<CountPredict> datas=new ArrayList<CountPredict>();
		Cursor cursor=db.rawQuery("select * from CountPredict where Date>=? and Date<=?", new String[]{time1,time2});
		while(cursor.moveToNext()){
			CountPredict predict=new CountPredict();
			predict.setShouPredictCount(cursor.getDouble(cursor.getColumnIndex("ShouPredict")));
			predict.setZhiChuPreCount(cursor.getDouble(cursor.getColumnIndex("ZhiPredict")));
			predict.setDate(cursor.getString(cursor.getColumnIndex("Date")));
			predict.setShouDeviate(cursor.getString(cursor.getColumnIndex("ShouDeviate")));
			predict.setZhiDeviate(cursor.getString(cursor.getColumnIndex("ZhiDeviate")));
			datas.add(predict);
		}
		 cursor.close();
		return datas;
	}
	public List<CountPredict> getAllDatas(SQLiteDatabase db){
		List<CountPredict> datas=new ArrayList<CountPredict>();
		Cursor cursor=db.query("CountPredict", null, null, null, null, null, "Date ASC");
		while(cursor.moveToNext()){
			CountPredict predict=new CountPredict();
			predict.setShouPredictCount(cursor.getDouble(cursor.getColumnIndex("ShouPredict")));
			predict.setZhiChuPreCount(cursor.getDouble(cursor.getColumnIndex("ZhiPredict")));
			predict.setDate(cursor.getString(cursor.getColumnIndex("Date")));
			predict.setShouDeviate(cursor.getString(cursor.getColumnIndex("ShouDeviate")));
			predict.setZhiDeviate(cursor.getString(cursor.getColumnIndex("ZhiDeviate")));
			datas.add(predict);
		}
		 cursor.close();
		return datas;
	}
	public CountPredict getCountPredictDatas(SQLiteDatabase db,String time1){
		CountPredict predict=new CountPredict();
		Cursor cursor=db.rawQuery("select * from CountPredict where Date=?", new String[]{time1});
		if(cursor.getCount()==0){
			predict.setDate("0");
		}else{
		while(cursor.moveToNext()){
			
			predict.setShouPredictCount(cursor.getDouble(cursor.getColumnIndex("ShouPredict")));
			predict.setZhiChuPreCount(cursor.getDouble(cursor.getColumnIndex("ZhiPredict")));
			predict.setDate(cursor.getString(cursor.getColumnIndex("Date")));
			predict.setShouDeviate(cursor.getString(cursor.getColumnIndex("ShouDeviate")));
			predict.setZhiDeviate(cursor.getString(cursor.getColumnIndex("ZhiDeviate")));
		}}
		 cursor.close();
		return predict;
	}
	//查询数据库是否有了当前时间的预算数据
	public int ReturnCount(SQLiteDatabase db,String time){
		Cursor cursor=db.rawQuery("select count(*) from CountPredict where Date=?", new String[]{time});
        cursor.moveToFirst();
		int count=cursor.getInt(0);
		 cursor.close();
		return count;
	}
	//更新数据
	public void UpdateData(SQLiteDatabase db,double count1,double count2,String time){
		db.execSQL("update CountPredictText set ShouPredict=?,ZhiPredict=? where Date=?" ,new Object[]{count1,count2,time});
	}
}
