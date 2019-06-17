package Adapters;

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

public class PwdInputAdapter extends BaseAdapter {
	 private String Pwds[];
	    private Context context;
		public PwdInputAdapter(String pwds[], Context context) {
			super();
			Pwds = pwds;
			this.context = context;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return Pwds.length;
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
				view = LayoutInflater.from(context).inflate(R.layout.pwdtext,
						null);
				viewholder1.inputPwd = (TextView) view
						.findViewById(R.id.InputPwdText);
				viewholder1.PwdLayout=(LinearLayout)view.findViewById(R.id.PwdLayout);
				view.setTag(viewholder1);

			}else{
			viewholder1 = (ViewHolder) view.getTag();}
			
			viewholder1.inputPwd.setText(Pwds[position]);
			if(position==9){
				viewholder1.PwdLayout.setBackgroundColor(Color.GRAY);
			}
			return view;
		}

		class ViewHolder {
			TextView inputPwd;
			LinearLayout PwdLayout;
	}

}
