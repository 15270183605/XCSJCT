package sqlite;

import entity.SetType;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SetTypeSQLite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table SetType(_id integer primary key autoincrement,SetTypeName,SetTypeNum)";
	public SetTypeSQLite(Context context, String name,
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
		db.execSQL("insert into SetType values(null,?,?)", new Object[]{SetTypeName,Type});
	}
   public void updateSetType(SQLiteDatabase db,String SetTypeName,int Type){
	   db.execSQL("update SetType set SetTypeNum=? where SetTypeName=?", new Object[]{Type,SetTypeName});
   }
   public Cursor returnType(SQLiteDatabase db,String SetTypeName){
	   Cursor cursor=db.query("SetType",null, "SetTypeName=?", new String[]{SetTypeName}, null, null, null);
		  return cursor;
   }
   
}
