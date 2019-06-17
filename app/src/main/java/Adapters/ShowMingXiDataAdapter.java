package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.GuDingMingXi;

public class ShowMingXiDataAdapter extends BaseAdapter {
   private Context context;
   private List<GuDingMingXi> datas;
	public ShowMingXiDataAdapter(Context context, List<GuDingMingXi> datas) {
	super();
	this.context = context;
	this.datas = datas;
}

	public int getCount() {
		   int ret = 0; 
		    if(datas!=null){ 
		      ret = datas.size(); 
		    } 
		    return ret; 
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		GuDingMingXi template=(GuDingMingXi)this.getItem(position);
		ViewHolder viewholder;
		if(view==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.mingxiitem, null);
			viewholder.count=(TextView)view.findViewById(R.id.Count);
			viewholder.operation=(TextView)view.findViewById(R.id.Operation);
			viewholder.maketime=(TextView)view.findViewById(R.id.makeTime);
			viewholder.way=(TextView)view.findViewById(R.id.Way);
			view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		
		viewholder.way.setText(template.getProjectName());
		viewholder.count.setText(String.valueOf(template.getCount()));
		viewholder.maketime.setText(template.getMingXiName());
		if(template.getProperty()==0){
			viewholder.operation.setText("收益项");
			
		}else{
			viewholder.operation.setText("扣款项");
		}
		
		
		return view;
	}
   class ViewHolder{
	   TextView way,count,maketime,operation;
   }
  
}
