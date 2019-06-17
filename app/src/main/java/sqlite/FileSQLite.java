package sqlite;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import entity.DataFile;

public class FileSQLite extends SQLiteOpenHelper {
	final  String CREATE_TABLE_SQL="create table FileTable(_id integer primary key autoincrement,FileName,FilePath,DateTime,FileClass)";
	public FileSQLite(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		
	}
	public void onCreate(SQLiteDatabase db) {
		
     db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
	

	}
	public void AddFileData(SQLiteDatabase db,DataFile file){
		db.execSQL("insert into FileTable values(null,?,?,?,?)", new String[]{file.getFileName(),file.getFilePath(),file.getDateTime(),file.getFileLeiXing()});
	}
	public List<DataFile> getDataFile(SQLiteDatabase db,String time){
		List<DataFile> datafile=new ArrayList<DataFile>();
		Cursor cursor=db.rawQuery("select * from FileTable where DateTime=?", new String[]{time});
		while(cursor.moveToNext()){
			DataFile file=new DataFile();
			file.setFileName(cursor.getString(cursor.getColumnIndex("FileName")));
			file.setFilePath(cursor.getString(cursor.getColumnIndex("FilePath")));
			file.setFileLeiXing(cursor.getString(cursor.getColumnIndex("FileClass")));
			file.setDateTime(cursor.getString(cursor.getColumnIndex("DateTime")));
			datafile.add(file);
		}
		 cursor.close();
		return datafile;
	}
	public List<DataFile> getDataFileByTime(SQLiteDatabase db,String time,String leixing){
		List<DataFile> datafile=new ArrayList<DataFile>();
		Cursor cursor=db.rawQuery("select * from FileTable where DateTime like ? and FileClass=?", new String[]{"%"+time+"%",leixing});
		while(cursor.moveToNext()){
			DataFile file=new DataFile();
			file.setFileName(cursor.getString(cursor.getColumnIndex("FileName")));
			file.setFilePath(cursor.getString(cursor.getColumnIndex("FilePath")));
			file.setFileLeiXing(cursor.getString(cursor.getColumnIndex("FileClass")));
			file.setDateTime(cursor.getString(cursor.getColumnIndex("DateTime")));
			datafile.add(file);
		}
		 cursor.close();
		return datafile;
	}

}
