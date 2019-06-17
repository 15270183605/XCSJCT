package Adapters;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.CountPredict;


public class CountPredictAdater extends BaseAdapter {
    private Context context;
    private List<CountPredict> datas;
	public CountPredictAdater(Context context, List<CountPredict> datas) {
		super();
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
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder=null;
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.allpredictdataitem, null);
			holder.ShouPredictText=(TextView)view.findViewById(R.id.AllShouPredictText);
			holder.ZhiPredictText=(TextView)view.findViewById(R.id.AllZhiPredictText);
			holder.ShouPredictDeviate=(TextView)view.findViewById(R.id.AllShouPredictDeviate);
			holder.ZhiPredictDeviate=(TextView)view.findViewById(R.id.AllZhiPredictDeviate);
			holder.PredictTime=(TextView)view.findViewById(R.id.AllPredictTime);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.ShouPredictText.setText(String.valueOf(datas.get(position).getShouPredictCount()));
		holder.ZhiPredictText.setText(String.valueOf(datas.get(position).getZhiChuPreCount()));
		holder.ShouPredictDeviate.setText(datas.get(position).getShouDeviate());
		holder.ZhiPredictDeviate.setText(datas.get(position).getZhiDeviate());
		holder.PredictTime.setText(datas.get(position).getDate());
		return view;
	}
class ViewHolder{
	TextView ShouPredictText,ShouPredictDeviate,ZhiPredictText,ZhiPredictDeviate,PredictTime;
}
}
