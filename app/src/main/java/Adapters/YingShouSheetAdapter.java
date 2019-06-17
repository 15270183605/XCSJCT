 package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.YingShou;

public class YingShouSheetAdapter<T> extends BaseAdapter {
	 private List<T> list; 
	  private LayoutInflater inflater; 
	   
	  public YingShouSheetAdapter(Context context, List<T> list){ 
	    this.list = list; 
	    inflater = LayoutInflater.from(context); 
	  } 
	 
	  
	  public int getCount() { 
	    int ret = 0; 
	    if(list!=null){ 
	      ret = list.size(); 
	    } 
	    return ret; 
	  } 
	 
	
	  public Object getItem(int position) { 
	    return list.get(position); 
	  } 
	
	  public long getItemId(int position) { 
	    return position; 
	  } 
	 
	 
	  public View getView(int position, View convertView, ViewGroup parent) { 
	     
		  YingShou yingshou = (YingShou) this.getItem(position); 
	     
	    ViewHolder viewHolder; 
	     
	    if(convertView == null){ 
	       
	      viewHolder = new ViewHolder(); 
	       
	      convertView = inflater.inflate(R.layout.shoufusheetitem, null); 
	      viewHolder.Id = (TextView) convertView.findViewById(R.id.id); 
	      viewHolder.classname = (TextView) convertView.findViewById(R.id.classname); 
	      viewHolder.way = (TextView) convertView.findViewById(R.id.way); 
	      viewHolder.count = (TextView) convertView.findViewById(R.id.count); 
	      viewHolder.makeperson = (TextView) convertView.findViewById(R.id.makeperson); 
	      viewHolder.maketime = (TextView) convertView.findViewById(R.id.maketime); 
	      viewHolder.object = (TextView) convertView.findViewById(R.id.object);
	      convertView.setTag(viewHolder); 
	    }else{ 
	      viewHolder = (ViewHolder) convertView.getTag(); 
	    } 
	     
	    viewHolder.Id.setText(String.valueOf(yingshou.getId())); 
	    viewHolder.Id.setTextSize(9); 
	    viewHolder.classname.setText(yingshou.getMenuName()); 
	    viewHolder.classname.setTextSize(9); 
	    viewHolder.way.setText(yingshou.getYingShouSource()); 
	    viewHolder.way.setTextSize(9); 
	    viewHolder.object.setText(yingshou.getYingShouObject()); 
	    viewHolder.object.setTextSize(9); 
	    viewHolder.count.setText("гд"+String.valueOf(yingshou.getCount())); 
	    viewHolder.count.setTextSize(9); 
	    viewHolder.makeperson.setText(yingshou.getMakePerson()); 
	    viewHolder.makeperson.setTextSize(9); 
	    viewHolder.maketime.setText(yingshou.getDate()); 
	    viewHolder.maketime.setTextSize(9); 
	    return convertView; 
	  } 
	   
	  public static class ViewHolder{ 
	    public TextView Id; 
	    public TextView classname; 
	    public TextView way; 
	    public TextView count; 
	    public TextView makeperson; 
	    public TextView maketime; 
	    public TextView object;
	  } 
}
