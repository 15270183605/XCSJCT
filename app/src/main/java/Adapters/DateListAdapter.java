package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class DateListAdapter extends BaseAdapter {
	 private List<String> list; 
	  private LayoutInflater inflater; 
	   
	  public DateListAdapter(Context context, List<String> list){ 
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
	     
	    ViewHolder viewHolder; 
	     
	    if(convertView == null){ 
	       
	      viewHolder = new ViewHolder(); 
	       
	      convertView = inflater.inflate(R.layout.notedatelistitem, null); 
	      viewHolder.dateText = (TextView) convertView.findViewById(R.id.dateText);  
	      convertView.setTag(viewHolder); 
	    }else{ 
	      viewHolder = (ViewHolder) convertView.getTag(); 
	    } 
	     
	    viewHolder.dateText.setText(list.get(position)); 
	   
	    return convertView; 
	  } 
	   
	  public static class ViewHolder{ 
	    public TextView dateText; 
	  
	  } 

}
