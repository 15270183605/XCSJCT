package Adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {
     private Context context;
     private String[] datas=new String[]{};
	public SpinnerAdapter(Context context, int textViewResourceId,String[] objects) {
		super(context, textViewResourceId,objects);
		this.datas=objects;
		this.context=context;
	}
      @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
        	LayoutInflater inflater=LayoutInflater.from(context);
        	convertView=inflater.inflate(android.R.layout.simple_spinner_item, parent,false);
        }
        TextView text=(TextView)convertView.findViewById(android.R.id.text1);
        text.setText(datas[position]);
        text.setGravity(Gravity.CENTER);
        text.setTextSize(12);
        
    	return convertView;
    }
      @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	  if(convertView==null){
          	LayoutInflater inflater=LayoutInflater.from(context);
          	convertView=inflater.inflate(android.R.layout.simple_spinner_item, parent,false);
          }
          TextView text=(TextView)convertView.findViewById(android.R.id.text1);
          text.setText(datas[position]);
          text.setGravity(Gravity.CENTER);
          text.setTextSize(12);
          
      	return convertView;
    }
}
