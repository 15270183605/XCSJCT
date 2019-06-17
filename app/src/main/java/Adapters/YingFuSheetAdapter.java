package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.YingFu;

public class YingFuSheetAdapter<T> extends BaseAdapter{
	 private List<T> list; 
	  private LayoutInflater inflater; 
	   
	  public YingFuSheetAdapter(Context context, List<T> list){ 
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
	     
		  YingFu yingfu = (YingFu) this.getItem(position); 
	     
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
	     
	    viewHolder.Id.setText(String.valueOf(yingfu.getId())); 
	    viewHolder.Id.setTextSize(9); 
	    viewHolder.classname.setText(yingfu.getMenuName()); 
	    viewHolder.classname.setTextSize(9); 
	    viewHolder.way.setText(yingfu.getYingFuTo()); 
	    viewHolder.way.setTextSize(9); 
	    viewHolder.object.setText(yingfu.getYingFuObject()); 
	    viewHolder.object.setTextSize(9); 
	    viewHolder.count.setText("гд"+String.valueOf(yingfu.getCount())); 
	    viewHolder.count.setTextSize(9); 
	    viewHolder.makeperson.setText(yingfu.getMakePerson()); 
	    viewHolder.makeperson.setTextSize(9); 
	    viewHolder.maketime.setText(yingfu.getDate()); 
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
