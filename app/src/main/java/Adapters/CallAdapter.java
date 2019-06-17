package Adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.Contact;

public class CallAdapter extends BaseAdapter {
 private Context context;
 private List<Contact> Datas=new ArrayList<Contact>();
 private int ImageId;
	public CallAdapter(Context context, List<Contact> datas,int ImageId) {
		this.context = context;
		this.Datas = datas;
		this.ImageId=ImageId;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return Datas.size();
	}
	@Override
	public Object getItem(int position) {
	   return getItem(position);
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder holder=null;
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.contactitem, null);
			holder.ContactPhone=(TextView)view.findViewById(R.id.ContactPhone);
			holder.ContactUserName=(TextView)view.findViewById(R.id.ContactUserName);
			holder.ContactHeadImage=(ImageView)view.findViewById(R.id.ContactHeadImage);
		view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.ContactUserName.setText(Datas.get(position).getUsername());
		holder.ContactPhone.setText(Datas.get(position).getPhone());
		holder.ContactHeadImage.setImageResource(ImageId);
		holder.ContactHeadImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				 Intent intent=new Intent();
			       intent.setAction(Intent.ACTION_CALL);
				   intent.setData(Uri.parse("tel:"+Datas.get(position).getPhone()));
				   context.startActivity(intent);
				
			}
		});
		return view;
	}
class ViewHolder{
	ImageView ContactHeadImage;
	TextView ContactUserName,ContactPhone;
}
}
