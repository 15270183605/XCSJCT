package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.Schedule;

public class ScheduleAdapter extends BaseAdapter {

	 private List<Schedule> list; 
	  private LayoutInflater inflater; 
	   
	  public ScheduleAdapter(Context context, List<Schedule> list){ 
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
	     
		  Schedule schedule = (Schedule) this.getItem(position); 
	     
	    ViewHolder viewHolder; 
	     
	    if(convertView == null){ 
	       
	      viewHolder = new ViewHolder(); 
	       
	      convertView = inflater.inflate(R.layout.makenotelistitem, null); 
	      viewHolder.DateId = (TextView) convertView.findViewById(R.id.DateId); 
	      viewHolder.EarlyMorning = (TextView) convertView.findViewById(R.id.EarlyMorning); 
	      viewHolder.Morning = (TextView) convertView.findViewById(R.id.Morning); 
	      viewHolder.Noon = (TextView) convertView.findViewById(R.id.Noon); 
	      viewHolder.Afternoon = (TextView) convertView.findViewById(R.id.Afternoon); 
	      viewHolder.Evening = (TextView) convertView.findViewById(R.id.Evening); 
	      convertView.setTag(viewHolder); 
	    }else{ 
	      viewHolder = (ViewHolder) convertView.getTag(); 
	    } 
	     
	    viewHolder.DateId.setText(String.valueOf(schedule.getTime())); 
	    viewHolder.EarlyMorning.setText(schedule.getEarlyMorning()); 
	    viewHolder.Morning.setText(schedule.getMorning());  
	    viewHolder.Noon.setText(schedule.getNoon()); 
	    viewHolder.Afternoon.setText(schedule.getAfternoon()); 
	    viewHolder.Evening.setText(schedule.getEvening()); 
	    return convertView; 
	  } 
	   
	  public static class ViewHolder{ 
	    public TextView DateId; 
	    public TextView EarlyMorning; 
	    public TextView Morning; 
	    public TextView Noon; 
	    public TextView Afternoon; 
	    public TextView Evening; 
	  } 

}
