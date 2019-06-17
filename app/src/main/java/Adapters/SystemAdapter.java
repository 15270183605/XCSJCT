package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class SystemAdapter extends BaseAdapter {

	private String[] mData = new String[]{};
	private Context context;

	public SystemAdapter(Context mcontext, String[] mdata) {
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
			view = LayoutInflater.from(context).inflate(R.layout.systemsetitem,
					null);
			viewholder1.myheadText = (TextView) view
					.findViewById(R.id.myheadText);
			view.setTag(viewholder1);

		}
		viewholder1 = (ViewHolder) view.getTag();
		viewholder1.myheadText.setText(mData[position]);
		return view;
	}

	class ViewHolder {
		TextView myheadText;
 
	}
}
