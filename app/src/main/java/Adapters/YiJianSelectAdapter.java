package Adapters;

import sqlite.YiJianSQLite;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class YiJianSelectAdapter extends BaseAdapter {
   private String str[];
   private Context context;
   private YiJianSQLite yijiansqlite;
   private SQLiteDatabase db;
   private boolean flag=true;
	public YiJianSelectAdapter(String[] str, Context context) {
	super();
	this.str = str;
	this.context = context;
	yijiansqlite=new YiJianSQLite(context, "yijian.db", null, 1);
	db=yijiansqlite.getReadableDatabase();
}

	public int getCount() {
		
		return str.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
	     ViewHolder viewholder;
	     if(view==null){
	    	 viewholder=new ViewHolder();
	    	 view=LayoutInflater.from(context).inflate(R.layout.yijianselectitem, null);
	    	 viewholder.yijianText=(TextView)view.findViewById(R.id.yijianText);
	    	 viewholder.yijianCheckBox=(CheckBox)view.findViewById(R.id.yijianCheckBox);
	    	 view.setTag(viewholder);
	     }else{
	    	viewholder=(ViewHolder)view.getTag();
	     }
	     viewholder.yijianText.setText(str[position]) ;
	     viewholder.yijianCheckBox.setOnClickListener(new OnClickListener() {
			
			public void onClick(View view) {
				if(flag==true){
					yijiansqlite.updateSetType(db, str[position], 2);
					flag=false;
				}else{
					yijiansqlite.updateSetType(db, str[position], 1);
					flag=true;
				}
				
			}
		});
	     checkStatus(str[position],viewholder.yijianCheckBox);
		return view;
	}
   class ViewHolder{
	TextView yijianText;
	CheckBox yijianCheckBox;
}
   public void checkStatus(String str,CheckBox checkbox){
		//SetType settype=new SetType();
		Cursor cursor=yijiansqlite.returnType(db, str);
		if(cursor.getCount()==0){
			yijiansqlite.AddSet(db, str, 2);
			}else{
				if(cursor.moveToFirst()){
				
				if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==1){
					checkbox.setChecked(false);
				}
				if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==2){
					checkbox.setChecked(true);
				}
			}
			}
		
		cursor.close();
		}
}
