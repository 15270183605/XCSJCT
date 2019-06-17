package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.MenuUseFulClass;

public class MenuUsefulAdapter extends BaseAdapter {

	private Context context;
	private List<MenuUseFulClass> datas;
	
	public MenuUsefulAdapter(Context context, List<MenuUseFulClass> datas) {
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
			holder= new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.menuusefulitem,
					null);
			holder.text1 = (TextView) view
					.findViewById(R.id.MenuClassName);
			holder.text2 = (TextView) view
					.findViewById(R.id.MenuUsefulName);
			view.setTag(holder);

		}
		holder = (ViewHolder) view.getTag();
		holder.text1.setText(datas.get(position).getClassName());
		holder.text2.setText(datas.get(position).getMenuUsefulName());
	return view;
	}
class ViewHolder{
	TextView text1,text2;
}
}
