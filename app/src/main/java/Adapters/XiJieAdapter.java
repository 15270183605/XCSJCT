package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.GuDingMingXi;

public class XiJieAdapter extends BaseAdapter {
    private Context context;
    private List<GuDingMingXi> datas;
	public XiJieAdapter(Context context, List<GuDingMingXi> datas) {
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
			view=LayoutInflater.from(context).inflate(R.layout.xijieitem, null);
			holder.XiJiePN=(TextView)view.findViewById(R.id.XiJiePN);
			holder.XiJieMN=(TextView)view.findViewById(R.id.XiJieMN);
			holder.XiJieCount=(TextView)view.findViewById(R.id.XiJieCount);
			holder.XiJieProperty=(TextView)view.findViewById(R.id.XiJieProperty);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.XiJiePN.setText(datas.get(position).getProjectName());
		holder.XiJieMN.setText(datas.get(position).getMingXiName());
		holder.XiJieCount.setText(String.valueOf(datas.get(position).getCount()));
		if(datas.get(position).getProperty()==0){
			holder.XiJieProperty.setText("收益项");
		}else if(datas.get(position).getProperty()==1){
			holder.XiJieProperty.setText("扣款项");
		}
		return view;
	}
class ViewHolder{
	TextView XiJiePN,XiJieMN,XiJieCount,XiJieProperty;
}
}
