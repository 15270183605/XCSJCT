package Adapters;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.Contact;

public class ContactAdapter extends BaseAdapter {
 private Context context;
 private List<Contact> datas;
	
 public ContactAdapter(Context context, List<Contact> Datas) {
	 datas=new ArrayList<Contact>();
	this.context = context;
	this.datas = Datas;
}

	public int getCount() {
		return datas.size();
	}
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
		ViewHolder holder=null;
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.contactitem, null);
			holder.ContactPhone=(TextView)view.findViewById(R.id.ContactPhone);
			holder.ContactUserName=(TextView)view.findViewById(R.id.ContactUserName);
		view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.ContactUserName.setText(datas.get(position).getUsername());
		holder.ContactPhone.setText(datas.get(position).getPhone());
		return view;
	}
class ViewHolder{
	
	TextView ContactUserName,ContactPhone;
}
}
