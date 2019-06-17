package sqlite;

import entity.SetType;
import entity.YiJianType;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class YiJianSQLite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table YiJianType(_id integer primary key autoincrement,SetTypeName,SetTypeNum)";
	public YiJianSQLite(Context context, String name,
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
	public void AddSet(SQLiteDatabase db,String SetTypeName,int Type){
		db.execSQL("insert into YiJianType values(null,?,?)", new Object[]{SetTypeName,Type});
	}
   public void updateSetType(SQLiteDatabase db,String SetTypeName,int Type){
	   db.execSQL("update YiJianType set SetTypeNum=? where SetTypeName=?", new Object[]{Type,SetTypeName});
   }
   public Cursor returnType(SQLiteDatabase db,String SetTypeName){
	   Cursor cursor=db.query("YiJianType",null, "SetTypeName=?", new String[]{SetTypeName}, null, null, null);
	   return cursor;
   }
}
