package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.GraphTemplate;

public class HorizontalListViewAdapter extends BaseAdapter {
   private Context context;
   private List<GraphTemplate> datas;
   
	public HorizontalListViewAdapter(Context context, List<GraphTemplate> datas) {
	super();
	this.context = context;
	this.datas = datas;
}

	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
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
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder;
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.graphhorizonviewshowdataitem, null);
			holder.MenuNameText=(TextView)view.findViewById(R.id.MenuNameText);
			holder.CountText=(TextView)view.findViewById(R.id.MenuCountText);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.MenuNameText.setText(datas.get(position).getMenuName());
		holder.CountText.setText(String.valueOf(datas.get(position).getCount()));
		return view;
	}
  class ViewHolder{
	  TextView MenuNameText;
	  TextView CountText;
  }
}
