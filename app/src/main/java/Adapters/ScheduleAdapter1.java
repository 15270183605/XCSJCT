package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class ScheduleAdapter1 extends BaseAdapter {
	 private List<String> list; 
	  private LayoutInflater inflater; 
	   private Context context;
	  public ScheduleAdapter1(Context context, List<String> list){ 
	    this.list = list; 
	    inflater = LayoutInflater.from(context); 
	    this.context=context;
	  } 
	 
	  
	  public int getCount() { 
	    int num = 0; 
	    if(list!=null){ 
	      num = list.size(); 
	    } 
	    return num; 
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
	      convertView = inflater.inflate(R.layout.makenoteitem1, null); 
	      viewHolder.scheduleText = (TextView) convertView.findViewById(R.id.makenoteText);  
	      convertView.setTag(viewHolder); 
	    }else{ 
	      viewHolder = (ViewHolder) convertView.getTag(); 
	    } 
	     if(!list.get(position).equals("")){
	    viewHolder.scheduleText.setText(list.get(position)); 
	     int number = position %4;
	     switch( number ) {
	     case 0:
	    	 viewHolder.scheduleText.setBackground(context.getResources().getDrawable(R.drawable.shape));
	     break;
	     case 1:
	    	 viewHolder.scheduleText.setBackground(context.getResources().getDrawable(R.drawable.shape1));
	     break;
	     case 2:
	    	 viewHolder.scheduleText.setBackground(context.getResources().getDrawable(R.drawable.shape2));
	     break;
	     case 3:
	    	 viewHolder.scheduleText.setBackground(context.getResources().getDrawable(R.drawable.shape3));
	     break;
	     case 4:
	    	 viewHolder.scheduleText.setBackground(context.getResources().getDrawable(R.drawable.shape4));
	     break;
	    
	

	     } 
	     }
	    return convertView; 
	  } 
	   
	  public static class ViewHolder{ 
	    public TextView scheduleText; 
	   
	  } 
}
