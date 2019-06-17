package sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.ImportantNoteE;

public class JSBINSqlite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table ImportantNote(_id integer primary key autoincrement,id,INContext,Time,status,UserName,Password)";
	public JSBINSqlite(Context context, String name, CursorFactory factory,
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
		
	}
   public void AddNote(SQLiteDatabase db,ImportantNoteE note){
	   db.execSQL("insert into ImportantNote values(null,?,?,?,?,?,?)", new Object[]{note.getId(),note.getContext(),note.getTime(),note.getStatus(),note.getUserName(),note.getPassword()});
   }
   public void UpdateNoteContext(SQLiteDatabase db,String context,long id){
	   db.execSQL("update ImportantNote set INContext=? where id=?",new Object[]{context,id});
   }
   public void UpdateNotePwd(SQLiteDatabase db,String password,long id){
	   db.execSQL("update ImportantNote set Password=? where id=?",new Object[]{password,id});
   }
   public void UpdateNotePwdStatus(SQLiteDatabase db,int status,long id){
	   db.execSQL("update ImportantNote set status=? where id=?",new Object[]{status,id});
   }
   public void deleteNote(SQLiteDatabase db,long id){
	   db.execSQL("delete ImportantNote  where id=?",new Object[]{id});
   }
   public long queryNotePassword(SQLiteDatabase db,long id){
	  Cursor cursor=db.rawQuery("select Password from ImportantNote where id=?", (String[]) new Object[]{id});
	  cursor.moveToFirst();
	  long _id=cursor.getLong(0);
	  cursor.close();
	  return _id;
   }
   public List<ImportantNoteE> QueryDatas(SQLiteDatabase db,String time){
	   List<ImportantNoteE> datas=new ArrayList<ImportantNoteE>();
	   Cursor cursor=db.rawQuery("select * from ImportantNote where Time like ?", new String[]{"%"+time+"%"});
	   if(cursor.getCount()!=0){
		   while(cursor.moveToNext()){
			   long id=cursor.getLong(cursor.getColumnIndex("id"));
			   String Context=cursor.getString(cursor.getColumnIndex("INContext"));
			   String Time=cursor.getString(cursor.getColumnIndex("Time"));
			   int  status=cursor.getInt(cursor.getColumnIndex("status"));
			   String UserName=cursor.getString(cursor.getColumnIndex("UserName"));
			   String Password=cursor.getString(cursor.getColumnIndex("Password"));
			   ImportantNoteE note=new ImportantNoteE(id,Context,Time,UserName,Password,status);
			   datas.add(note);
		   }
	   }
	   cursor.close();
	   return datas;
   }
}
