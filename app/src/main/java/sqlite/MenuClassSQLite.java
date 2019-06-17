package sqlite;

import java.util.ArrayList;
import java.util.List;

import entity.MenuUseFulClass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MenuClassSQLite extends SQLiteOpenHelper{
	final  String CREATE_TABLE_SQL="create table MenuClass(_id integer primary key autoincrement,className,MenuUsefulName)";
	public MenuClassSQLite(Context context, String name,
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
	public void addMenuClass(SQLiteDatabase db,String className,String MenuUsefulName){
		  db.execSQL("insert into MenuClass values(null,?,?)",new String[]{className,MenuUsefulName});
	}
	public List<MenuUseFulClass> queryAllMenuClass(SQLiteDatabase db){
		List<MenuUseFulClass> MenuUseFulClassDatas=new ArrayList<MenuUseFulClass>();
		  Cursor cursor=db.rawQuery("select * from MenuClass", null);
		  while(cursor.moveToNext()){
			  MenuUseFulClass mCla=new MenuUseFulClass();
			  mCla.setClassName(cursor.getString(cursor.getColumnIndex("className")));
			  mCla.setClassName(cursor.getString(cursor.getColumnIndex("MenuUsefulName")));
			  MenuUseFulClassDatas.add(mCla);
		  }
		  cursor.close();
		  return MenuUseFulClassDatas;
	  }
	public void updateMenuClass(SQLiteDatabase db,String className,String MenuUsefulName){
		
		  db.execSQL("update MenuClass set MenuUsefulName=? where className = ? ",new String[]{MenuUsefulName,className});
		}
	public void updateMenuClassAll(SQLiteDatabase db,String className,String MenuUsefulName,int id){
		
		  db.execSQL("update MenuClass set MenuUsefulName=? , className =? where _id=?",new Object[]{MenuUsefulName,className,id});
		}
	public void updateMenuClass(SQLiteDatabase db,String className,String MenuUsefulName,String className1,String MenuMenuUsefulName1){
		
		  db.execSQL("update MenuClass set MenuUsefulName=? , className =? where MenuUsefulName=? and className=?",new Object[]{MenuUsefulName,className,MenuMenuUsefulName1,className1});
		}
	public Cursor QueryMenucalss(SQLiteDatabase db,String name){
		  Cursor cursor=db.rawQuery("select * from MenuClass where className like ?", new String[]{name});
		  return cursor;
	  }
	public List<MenuUseFulClass> queryMenucalss(SQLiteDatabase db,String name){
		List<MenuUseFulClass> MenuUseFulClassDatas=new ArrayList<MenuUseFulClass>();
		Cursor cursor=db.rawQuery("select * from MenuClass where className = ?", new String[]{name});
		  while(cursor.moveToNext()){
			  MenuUseFulClass mCla=new MenuUseFulClass();
			  mCla.setClassId(cursor.getInt(cursor.getColumnIndex("_id")));
			  mCla.setClassName(cursor.getString(cursor.getColumnIndex("className")));
			  mCla.setMenuUsefulName(cursor.getString(cursor.getColumnIndex("MenuUsefulName")));
			  MenuUseFulClassDatas.add(mCla);
		  }
		  cursor.close();
		  return MenuUseFulClassDatas;
	
	  }
	public void deleteClass(SQLiteDatabase db,int id){
		db.execSQL("delete from MenuClass where _id=?",new Object[]{id});
		
	}
}
