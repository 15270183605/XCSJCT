package sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class UserShouZhiSQLite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table UserShouZhi(_id integer primary key autoincrement,UserName,Password,Phone,BaoShouImage BLOB ,WeiShouImage BLOB ,BaoFuImage BLOB ,WeiFuImage BLOB)";
	public UserShouZhiSQLite(Context context, String name,
			CursorFactory factory, int version) { 
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
		
	}
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  System.out.println("-----"+oldVersion+"----"+newVersion);
		
	}
	public void AddUserShouZhi(SQLiteDatabase db,String username,String password,String phone,byte[] BaoShouImg,byte[] WeiShouImg,byte[] BaoFuImg,byte[] WeiFuImg){
		db.execSQL("insert into UserShouZhi values(null,?,?,?,?,?,?,?)",new Object[]{username,password,phone,BaoShouImg,WeiShouImg,BaoFuImg,WeiFuImg});
	}
	public Cursor queryUserShouFuCode(SQLiteDatabase db,String username,String pwd){
		Cursor cursor=db.rawQuery("select * from UserShouZhi where UserName =? and Password =?",new String[]{username,pwd});
	       return cursor;
			}
}
