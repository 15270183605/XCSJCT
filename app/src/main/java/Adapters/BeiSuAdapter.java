package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class BeiSuAdapter extends BaseAdapter {
	private double mData[] = new double[]{};
	private Context context;

	public BeiSuAdapter(Context mcontext, double mdata[]) {
		this.mData = mdata;
		this.context = mcontext;

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mData.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewholder1 = null;
		if (view == null) {

			viewholder1 = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.beisuitem,
					null);
			viewholder1.beisuNumber = (TextView) view
					.findViewById(R.id.ClassName);
		
			view.setTag(viewholder1);

		}else{
		viewholder1 = (ViewHolder) view.getTag();}
		
		viewholder1.beisuNumber.setText(String.valueOf(mData[position]));
		
		return view;
	}

	class ViewHolder {
		TextView beisuNumber;
}
}