package Adapters;

import sqlite.SetTypeSQLite;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class SwitchListAdapter extends BaseAdapter {
  private Context context;
  private String[] SwitchTexts;
  private SetTypeSQLite settypesqlite;
  private SQLiteDatabase db1;
	public SwitchListAdapter(Context context, String[] switchTexts) {
	super();
	this.context = context;
	SwitchTexts = switchTexts;
	settypesqlite=new SetTypeSQLite(context,"settype.db", null, 1);
	db1=settypesqlite.getReadableDatabase();
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return SwitchTexts.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup viewparent) {
		final ViewHolder viewholder;
		if(view==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.setswitchlistitem, null); 
			viewholder.switchItem=(Switch)view.findViewById(R.id.SwitchItem);
			viewholder.SwitchText=(TextView)view.findViewById(R.id.SwitchText);
			view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		viewholder.SwitchText.setText(SwitchTexts[position]);
		viewholder.switchItem.setSwitchTextAppearance(context, R.style.OffTextColor);
		viewholder.switchItem.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			
			public void onCheckedChanged(CompoundButton compoundButton, boolean flag) {
				if(flag){
					
				viewholder.switchItem.setSwitchTextAppearance(context, R.style.OnTextColor);
				settypesqlite.updateSetType(db1, SwitchTexts[position], 2);
				
			}else{
				viewholder.switchItem.setSwitchTextAppearance(context, R.style.OffTextColor);
				settypesqlite.updateSetType(db1, SwitchTexts[position], 1);
				
			}
				
			}
		});
		checkStatus(SwitchTexts[position],viewholder.switchItem);
		return view;
	}
	public void checkStatus(String str,Switch switchType){
		//SetType settype=new SetType();
		Cursor cursor=settypesqlite.returnType(db1, str);
		if(cursor.getCount()==0){
			settypesqlite.AddSet(db1, str, 1);
			}else{
				if(cursor.moveToFirst()){
				
				if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==1){
					switchType.setChecked(false);
				}
				if(cursor.getInt(cursor.getColumnIndex("SetTypeNum"))==2){
					switchType.setChecked(true);
				}
			}
			}
		
		cursor.close();
		}
}
 class ViewHolder{
	 TextView SwitchText;
	Switch switchItem;
}  
 