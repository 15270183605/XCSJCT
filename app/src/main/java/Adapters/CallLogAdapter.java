package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.CallLogEntity;

public class CallLogAdapter extends BaseAdapter {

	 private Context context;
	 private List<CallLogEntity> datas;
		public CallLogAdapter(Context context, List<CallLogEntity> datas) {
		this.context = context;
		this.datas = datas;
	}

		public int getCount() {
			// TODO Auto-generated method stub
			return datas.size();
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
			ViewHolder holder=null;
			if(view==null){
				holder=new ViewHolder();
				view=LayoutInflater.from(context).inflate(R.layout.contactitem, null);
				holder.CallLogName=(TextView)view.findViewById(R.id.CallLogName);
				holder.CallLogPhone=(TextView)view.findViewById(R.id.CallLogPhone);
				holder.CallLogType=(TextView)view.findViewById(R.id.CallLogType);
				holder.CallLogTime=(TextView)view.findViewById(R.id.CallLogTime);
				holder.CallLogDuration=(TextView)view.findViewById(R.id.CallLogDuration);
				
			view.setTag(holder);
			}else{
				holder=(ViewHolder)view.getTag();
			}
			holder.CallLogName.setText(datas.get(position).getCallLogName());
			holder.CallLogPhone.setText(datas.get(position).getPhone());
			holder.CallLogType.setText(datas.get(position).getType());
			holder.CallLogTime.setText(datas.get(position).getTime());
			holder.CallLogDuration.setText(datas.get(position).getDuration());
			return view;
		}
	class ViewHolder{
		
		TextView CallLogName,CallLogPhone,CallLogType,CallLogTime,CallLogDuration;
	}
}
