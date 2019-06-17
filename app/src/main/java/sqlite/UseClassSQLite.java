package sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class UseClassSQLite extends SQLiteOpenHelper{
	final  String CREATE_TABLE_SQL="create table UseClass(_id integer primary key autoincrement,UserId,UserName)";
	public UseClassSQLite(Context context, String name,
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
	public void addUseClass(SQLiteDatabase db,String UserId,String UserName){
		  db.execSQL("insert into UseClass values(null,?,?)",new String[]{UserId,UserName});
	}
	public Cursor queryAllUseClass(SQLiteDatabase db){
		  Cursor cursor=db.rawQuery("select * from UseClass", null);
		  return cursor;
	  }
	public void updateUseClass(SQLiteDatabase db,String UserId,String UserName){
		
		  db.execSQL("update UseClass set UserName=? where UserId like ? ",new String[]{UserName,UserId});
		}
}
