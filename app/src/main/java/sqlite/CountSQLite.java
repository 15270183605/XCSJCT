package sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.CountEntity;
import entity.GraphTemplate;

public class CountSQLite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table Count(_id integer primary key autoincrement,Time,ShouRu ,ZhiChu ,YingShou ,YingFu ,ShiShou ,ShiFu,ShouRuNum,ZhiChuNum,YingShouNum,YingFuNum,ShiShouNum,ShiFuNum)";
	
	public CountSQLite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
   
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  System.out.println("-----"+oldVersion+"----"+newVersion);
	}
public void AddCount(SQLiteDatabase db,CountEntity count){
	db.execSQL("insert into Count values(null,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]{count.getDate(),count.getShouRuCount(),count.getZhiChuCount(),count.getYingShouCount(),count.getYingFuCount(),count.getShiShouCount(),count.getShiFuCount(),count.getShouRunum(),count.getZhiChunum(),count.getYingShounum(),count.getYingFunum(),count.getShiShounum(),count.getShiFunum()});
	
}
//根据查询返回的条数判断数据是否存在
public int QueryNumByDate(SQLiteDatabase db,String time){
	Cursor cursor=db.rawQuery("select count(*) from Count where Time=?", new String[]{time});
    cursor.moveToFirst();
    int count=cursor.getInt(0);
    cursor.close();
    return count;
}
public List<CountEntity> QueryCountByDate(SQLiteDatabase db,String time){
	List<CountEntity> CountDatas=new ArrayList<CountEntity>();
 	Cursor cursor=db.rawQuery("select * from Count where Time like ?", new String[]{"%"+time+"%"});
 	while(cursor.moveToNext()){
 		CountEntity count=new CountEntity();
 		count.setDate(cursor.getString(cursor.getColumnIndex("Time")));
 		count.setShouRuCount(cursor.getDouble(cursor.getColumnIndex("ShouRu")));
 		count.setZhiChuCount(cursor.getDouble(cursor.getColumnIndex("ZhiChu")));
 		count.setYingShouCount(cursor.getDouble(cursor.getColumnIndex("YingShou")));
 		count.setYingFuCount(cursor.getDouble(cursor.getColumnIndex("YingFu")));
 		count.setShiShouCount(cursor.getDouble(cursor.getColumnIndex("ShiShou")));
 		count.setShiFuCount(cursor.getDouble(cursor.getColumnIndex("ShiFu")));
 		count.setShouRunum(cursor.getLong(cursor.getColumnIndex("ShouRuNum")));
 		count.setZhiChunum(cursor.getLong(cursor.getColumnIndex("ZhiChuNum")));
 		count.setYingShounum(cursor.getLong(cursor.getColumnIndex("YingShouNum")));
 		count.setYingFunum(cursor.getLong(cursor.getColumnIndex("YingFuNum")));
 		count.setShiShounum(cursor.getLong(cursor.getColumnIndex("ShiShouNum")));
 		count.setShiFunum(cursor.getLong(cursor.getColumnIndex("ShiFuNum")));
 		CountDatas.add(count);
 	}
 	cursor.close();
	return CountDatas;
}

public CountEntity QueryByDate(SQLiteDatabase db,String time){
	CountEntity count=new CountEntity();
	Cursor cursor=db.rawQuery("select * from Count where Time=?", new String[]{time});
	if(cursor.getCount()==0){
		count.setDate("0");
	}else{
		while(cursor.moveToNext()){
	 		count.setDate(cursor.getString(cursor.getColumnIndex("Time")));
	 		count.setShouRuCount(cursor.getDouble(cursor.getColumnIndex("ShouRu")));
	 		count.setZhiChuCount(cursor.getDouble(cursor.getColumnIndex("ZhiChu")));
	 		count.setYingShouCount(cursor.getDouble(cursor.getColumnIndex("YingShou")));
	 		count.setYingFuCount(cursor.getDouble(cursor.getColumnIndex("YingFu")));
	 		count.setShiShouCount(cursor.getDouble(cursor.getColumnIndex("ShiShou")));
	 		count.setShiFuCount(cursor.getDouble(cursor.getColumnIndex("ShiFu")));
	 		count.setShouRunum(cursor.getLong(cursor.getColumnIndex("ShouRuNum")));
	 		count.setZhiChunum(cursor.getLong(cursor.getColumnIndex("ZhiChuNum")));
	 		count.setYingShounum(cursor.getLong(cursor.getColumnIndex("YingShouNum")));
	 		count.setYingFunum(cursor.getLong(cursor.getColumnIndex("YingFuNum")));
	 		count.setShiShounum(cursor.getLong(cursor.getColumnIndex("ShiShouNum")));
	 		count.setShiFunum(cursor.getLong(cursor.getColumnIndex("ShiFuNum")));
	 		
	 	}
		
	}
	cursor.close();
return count;
}
public List<CountEntity> QueryCountByDate1(SQLiteDatabase db,String time,String time1){
	List<CountEntity> CountDatas=new ArrayList<CountEntity>();
 	Cursor cursor=db.rawQuery("select * from Count where Time>=? and Time<=?", new String[]{time,time1});
 	
 	while(cursor.moveToNext()){
 		CountEntity count=new CountEntity();
 		if(cursor.getCount()==0){
 			count.setDate("0");
 		}else{
 			count.setDate(cursor.getString(cursor.getColumnIndex("Time")));
	 		count.setShouRuCount(cursor.getDouble(cursor.getColumnIndex("ShouRu")));
	 		count.setZhiChuCount(cursor.getDouble(cursor.getColumnIndex("ZhiChu")));
	 		count.setYingShouCount(cursor.getDouble(cursor.getColumnIndex("YingShou")));
	 		count.setYingFuCount(cursor.getDouble(cursor.getColumnIndex("YingFu")));
	 		count.setShiShouCount(cursor.getDouble(cursor.getColumnIndex("ShiShou")));
	 		count.setShiFuCount(cursor.getDouble(cursor.getColumnIndex("ShiFu")));
	 		count.setShouRunum(cursor.getInt(cursor.getColumnIndex("ShouRuNum")));
	 		count.setZhiChunum(cursor.getInt(cursor.getColumnIndex("ZhiChuNum")));
	 		count.setYingShounum(cursor.getInt(cursor.getColumnIndex("YingShouNum")));
	 		count.setYingFunum(cursor.getInt(cursor.getColumnIndex("YingFuNum")));
	 		count.setShiShounum(cursor.getInt(cursor.getColumnIndex("ShiShouNum")));
	 		count.setShiFunum(cursor.getInt(cursor.getColumnIndex("ShiFuNum")));
 		CountDatas.add(count);}
 	}
 	cursor.close();
	return CountDatas;
}
public Map<String,List<GraphTemplate>> TimeDatas(SQLiteDatabase db,String time){
	Map<String,List<GraphTemplate>> datas=new HashMap<String, List<GraphTemplate>>();
	List<GraphTemplate> graph=new ArrayList<GraphTemplate>();
	List<GraphTemplate> graph1=new ArrayList<GraphTemplate>();
	List<GraphTemplate> graph2=new ArrayList<GraphTemplate>();
	List<GraphTemplate> graph3=new ArrayList<GraphTemplate>();
	List<GraphTemplate> graph4=new ArrayList<GraphTemplate>();
	List<GraphTemplate> graph5=new ArrayList<GraphTemplate>();
 	Cursor cursor=db.rawQuery("select * from Count where Time like ?", new String[]{"%"+time+"%"});
 	while(cursor.moveToNext()){
 		SetTempalteData(graph,repalce(cursor.getString(cursor.getColumnIndex("Time")),time),cursor.getDouble(cursor.getColumnIndex("ShouRu")));
 		SetTempalteData(graph1,repalce(cursor.getString(cursor.getColumnIndex("Time")),time),cursor.getDouble(cursor.getColumnIndex("ZhiChu")));
 		SetTempalteData(graph2,repalce(cursor.getString(cursor.getColumnIndex("Time")),time),cursor.getDouble(cursor.getColumnIndex("YingShou")));
 		SetTempalteData(graph3,repalce(cursor.getString(cursor.getColumnIndex("Time")),time),cursor.getDouble(cursor.getColumnIndex("YingFu")));
 		SetTempalteData(graph4,repalce(cursor.getString(cursor.getColumnIndex("Time")),time),cursor.getDouble(cursor.getColumnIndex("ShiShou")));
 		SetTempalteData(graph5,repalce(cursor.getString(cursor.getColumnIndex("Time")),time),cursor.getDouble(cursor.getColumnIndex("ShiFu")));
		}
 	datas.put("收入", graph); 
 	datas.put("支出", graph1); 
 	datas.put("应收", graph2); 
 	datas.put("应付", graph3); 
 	datas.put("实收", graph4); 
 	datas.put("实付", graph5); 
 	cursor.close();
	return datas;
}
public long TimeCount(SQLiteDatabase db,String time,String name){
	Cursor cursor=db.rawQuery("select count("+name+") from Count where  Date=?",new String[]{"%"+time+"%"});
	    cursor.moveToFirst();
	    long count=cursor.getLong(0);
	    cursor.close();
	    return count;
}
public void SetTempalteData(List<GraphTemplate> templateDatas,String username,double count){
	GraphTemplate template1=new GraphTemplate();	
	template1.setMenuName(username);
	template1.setCount(count);
	templateDatas.add(template1);
}
public String repalce(String str,String time){
	String str1=str.replace(time+"-", "");
	return str1;
}
}
