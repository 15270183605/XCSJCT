package Adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class InputPwdAdapter extends BaseAdapter {
    private List<String> Pwds;
    private Context context;
	public InputPwdAdapter(List<String> pwds, Context context) {
		super();
		Pwds = pwds;
		this.context = context;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return Pwds.size();
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
		ViewHolder viewholder1 = null;
		if (view == null) {
			viewholder1 = new ViewHolder();
			view = LayoutInflater.from(context).inflate(R.layout.paypwdgriditem,
					null);
			viewholder1.inputPwd = (EditText) view
					.findViewById(R.id.InputPwd);
			
			view.setTag(viewholder1);

		}else{
		viewholder1 = (ViewHolder) view.getTag();}
		
		viewholder1.inputPwd.setText(Pwds.get(position));
		
		return view;
	}

	class ViewHolder {
		EditText inputPwd;
		LinearLayout PwdLayout;
}

}
