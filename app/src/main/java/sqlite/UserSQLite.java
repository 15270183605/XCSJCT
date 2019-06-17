 package sqlite;

import java.util.ArrayList;

import java.util.List;

import entity.FamilyMember;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class UserSQLite extends SQLiteOpenHelper {
     //final  String CREATE_TABLE_SQL="create table qqChatMessages(_id integer primary key autoincrement,id,userImg ,time ,userName,userTitle,userMessage,type)";
	final  String CREATE_TABLE_SQL="create table User(_id integer primary key autoincrement,userName,passWord,phoneNumber,status,salary,nickname,headImage BLOB,Count,myCode BLOB,Time)";
	public UserSQLite(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SQL);
		
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		  System.out.println("-----"+oldVersion+"----"+newVersion);
		
	}
	public void addUser(SQLiteDatabase db,String username,String pwd,String phone,String status,String salary,String nickname,byte[] headImage,int count,byte[] myCode,String date){
		  db.execSQL("insert into User values(null,?,?,?,?,?,?,?,?,?,?)",new Object[]{username,pwd,phone,status,salary,nickname,headImage,count,myCode,date});
	}
  public void deleteUser(SQLiteDatabase db,String username){
		 db.execSQL("delete  from User where  userName =?",new String[]{username});
		
}
  public Cursor queryUser(SQLiteDatabase db,String username){
	  Cursor cursor=db.rawQuery("select * from User where userName = ?",new String[]{username});
	  return cursor;
  }
  public Cursor queryAllUser(SQLiteDatabase db){
	  Cursor cursor=db.rawQuery("select * from User", null);
	  return cursor;
  }
  public byte[] queryUserCode(SQLiteDatabase db,String username,String pwd){
	  byte[] code = null;
	  Cursor cursor=db.rawQuery("select myCode from User where userName =? and passWord =?",new String[]{username,pwd});
	  if(cursor.getCount()!=0){
		  cursor.moveToFirst();
		  code=cursor.getBlob(0);
	  }
	  cursor.close();
	  return code;
  }
  public long UserCount(SQLiteDatabase db,String username,String pwd){
		Cursor cursor=db.rawQuery("select count(*) from User where userName =? and passWord =?",new String[]{username,pwd});
		    cursor.moveToFirst();
		    long count=cursor.getLong(0);
		    cursor.close();
		    return count;
	}
  public void updatePwd(SQLiteDatabase db,String username,String pwd){
		
	  db.execSQL("update User set password=? where userName like ? ",new String[]{pwd,username});
	 
	}
  public  FamilyMember queryMember(SQLiteDatabase db,String username){
	 FamilyMember member=new FamilyMember();
	 Cursor cursor=db.rawQuery("select * from User where userName =? ",new String[]{username});
	 while(cursor.moveToNext()){
		 member.setMyCode(cursor.getBlob((cursor.getColumnIndex("myCode"))));
		 member.setHeadImage(cursor.getBlob((cursor.getColumnIndex("headImage"))));
		 member.setNickName(cursor.getString(cursor.getColumnIndex("nickname")));
		 member.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phoneNumber")));
		 member.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
		 member.setSalary(cursor.getInt(cursor.getColumnIndex("salary")));
		 member.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
		 member.setDate(cursor.getString(cursor.getColumnIndex("Time")));
	 }
	 cursor.close();
	 return member;
  }
  public  String  queryPhone(SQLiteDatabase db,String username){
	 String phone=null;
		 FamilyMember member=new FamilyMember();
		 Cursor cursor=db.rawQuery("select phoneNumber from User where userName =? ",new String[]{username});
		 while(cursor.moveToNext()){
			phone=cursor.getString(cursor.getColumnIndex("phoneNumber"));
		 }
		 cursor.close();
		 return phone;
	  }
  public  FamilyMember queryMemberByTel(SQLiteDatabase db,String tel){
		 FamilyMember member=new FamilyMember();
		 Cursor cursor=db.rawQuery("select * from User where phoneNumber =? ",new String[]{tel});
		 while(cursor.moveToNext()){
			 member.setMyCode(cursor.getBlob((cursor.getColumnIndex("myCode"))));
			 member.setHeadImage(cursor.getBlob((cursor.getColumnIndex("headImage"))));
			 member.setNickName(cursor.getString(cursor.getColumnIndex("nickname")));
			 member.setPhoneNumber(cursor.getString(cursor.getColumnIndex("phoneNumber")));
			 member.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
			 member.setSalary(cursor.getInt(cursor.getColumnIndex("salary")));
			 member.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
			 member.setDate(cursor.getString(cursor.getColumnIndex("Time")));
		 }
		 cursor.close();
		 return member;
	  }
  public void updateMember(SQLiteDatabase db,String nickname,String telephone,double salary,int status,byte[] headImage ,String username){
	  db.execSQL("update User set nickname=?,phoneNumber=?,salary=?,status=?,headImage=? where userName = ? ",new Object[]{nickname,telephone,salary,status,headImage,username});
  }
  public int QueryStatus(SQLiteDatabase db,String UserName,String pwd){
	  Cursor cursor=db.rawQuery("select Count from User where userName =? and passWord =?",new String[]{UserName,pwd});
	    cursor.moveToFirst();
	     int count=cursor.getInt(0);
	    cursor.close();
	    return count;  
  }
  public void UpdateCount(SQLiteDatabase db,String username,String pwd,int count){
	  db.execSQL("update User set Count=? where userName = ? and password=?",new Object[]{count,username,pwd});
  }
  public String QueryUserTime(SQLiteDatabase db,String username,String password){
	  Cursor cursor=db.rawQuery("select Time from User where userName=? and passWord=?", new String[]{username,password});
	 String time = null;
	 while(cursor.moveToNext()){
		 time=cursor.getString(cursor.getColumnIndex("Time"));
	 }
	 cursor.close();
	  return time;
  }
}
