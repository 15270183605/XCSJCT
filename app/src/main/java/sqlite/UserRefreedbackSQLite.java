package sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.UserRefreedEntity;

public class UserRefreedbackSQLite extends SQLiteOpenHelper {

	final  String CREATE_TABLE_SQL="create table UserRefreed(_id integer primary key autoincrement,UserName,NickName,Type,Time,Content,HeadImage BLOB)";
	public UserRefreedbackSQLite(Context context, String name,
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
   public void AddUserRefreedContent(SQLiteDatabase db,String username,String nickname,int type,Long time,String content,byte[] headImage){
	   db.execSQL("insert into UserRefreed values(null,?,?,?,?,?,?)", new Object[]{username,nickname,type,time,content,headImage});
	   
   }
   public void AddUserRefreedContentByEntity(SQLiteDatabase db,UserRefreedEntity member){
	   
	   db.execSQL("insert into UserRefreed values(null,?,?,?,?,?,?)", new Object[]{member.getUserName(),member.getNickName(),member.getType(),member.getTime(),member.getContent(),member.getHeadImage()});
	   
   }

 public List<UserRefreedEntity> queryMessage(SQLiteDatabase db){
	     List<UserRefreedEntity> list=new ArrayList<UserRefreedEntity>(); 
	     Cursor cursor=db.rawQuery("select * from UserRefreed",null);
	     while(cursor.moveToNext()){
	    	 UserRefreedEntity chatMessage=new UserRefreedEntity();
	    	 chatMessage.setUserName(cursor.getString(cursor.getColumnIndex("UserName")));
	    	 chatMessage.setNickName(cursor.getString(cursor.getColumnIndex("NickName")));
	    	 chatMessage.setType(cursor.getInt(cursor.getColumnIndex("Type")));
	    	 chatMessage.setTime(cursor.getLong(cursor.getColumnIndex("Time")));
	    	 chatMessage.setContent(cursor.getString(cursor.getColumnIndex("Content")));
	    	 chatMessage.setHeadImage(cursor.getBlob(cursor.getColumnIndex("HeadImage")));
	    	 list.add(chatMessage);
	     }
	     cursor.close();
	     return list;
	}
}
