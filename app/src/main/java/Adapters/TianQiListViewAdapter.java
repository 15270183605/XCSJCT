package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.TianQi;

public class TianQiListViewAdapter extends BaseAdapter {
    private Context context;
    private List<TianQi> tianqidata;
	
	public TianQiListViewAdapter(Context context, List<TianQi> tianqidata) {
		super();
		this.context = context;
		this.tianqidata = tianqidata;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return tianqidata.size();
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
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewholder;
		if(view==null){
			viewholder=new ViewHolder();
			  view=LayoutInflater.from(context).inflate(R.layout.tianqilistviewitem,null);
			   viewholder.TianQiImage=(ImageView)view.findViewById(R.id.TianQiImage);
			  viewholder.TianQiDate=(TextView)view.findViewById(R.id.TianQiDate);
			  viewholder.tianQi=(TextView)view.findViewById(R.id.TianQi);
			  viewholder.TianQiCondition=(TextView)view.findViewById(R.id.TianQiCondition);
		view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		viewholder.TianQiImage.setImageResource(tianqidata.get(position).getImage());
		viewholder.TianQiDate.setText(tianqidata.get(position).getDate());
		viewholder.tianQi.setText(tianqidata.get(position).getTianqi());
		viewholder.TianQiCondition.setText(tianqidata.get(position).getCondition());
		return view;
	}
 class ViewHolder{
	 TextView TianQiDate,tianQi,TianQiCondition;
	 ImageView TianQiImage;
 }
}
