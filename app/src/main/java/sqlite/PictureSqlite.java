package sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.PictureEntity;

public class PictureSqlite extends SQLiteOpenHelper {
	//Property==0 代表图片，Property==1 代表视频
	final  String CREATE_TABLE_SQL="create table Picture(_id integer primary key autoincrement,Content,Path,Time,Property)";
	public PictureSqlite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
     db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int olversion, int newversion) {
		
	}
	public void Add(SQLiteDatabase db,String content,String path,String time,String Property){
		db.execSQL("insert into Picture values(null,?,?,?,?)", new String[]{content,path,time,Property});
	}
    public void Delete(SQLiteDatabase db,String time,String property){
    	db.execSQL("delete from Picture where Time=? and Property=?", new String[]{time,property});
    }
    public List<PictureEntity> QueryData(SQLiteDatabase db,String time,String property){
    	List<PictureEntity> datas=new ArrayList<PictureEntity>();
    	Cursor cursor=db.rawQuery("select * from Picture where Time=? and Property=?", new String[]{time,property});
    	if(cursor.getCount()!=0){
    		while(cursor.moveToNext()){
    			String content=cursor.getString(cursor.getColumnIndex("Content"));
    			String path=cursor.getString(cursor.getColumnIndex("Path"));
    			String Time=cursor.getString(cursor.getColumnIndex("Time"));
    			PictureEntity picture=new PictureEntity(content, path, Time);
    			datas.add(picture);
    			
    		}
    	}
    	  cursor.close();
    	return datas;
    }
}
