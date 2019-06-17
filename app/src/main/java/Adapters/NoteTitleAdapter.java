package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class NoteTitleAdapter extends BaseAdapter {
  private Context context;
  private String strs[];
	public NoteTitleAdapter(Context context, String[] strs) {
	super();
	this.context = context;
	this.strs = strs;
}

	public int getCount() {
		
		return strs.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder=null;
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.notetitleitem, null);
			holder.NoteTitle=(TextView)view.findViewById(R.id.NoteTitleText);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.NoteTitle.setText(strs[position]);
		return view;
	}
class ViewHolder{
	TextView NoteTitle;
}
}
