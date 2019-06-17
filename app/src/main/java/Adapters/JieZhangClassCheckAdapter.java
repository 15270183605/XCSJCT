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

import entity.JieZhangTemplate;

public class JieZhangClassCheckAdapter extends BaseAdapter {
	  private List<JieZhangTemplate> datas=new ArrayList<JieZhangTemplate>();
	  private Context context;
	  private String leixing;
	public JieZhangClassCheckAdapter(List<JieZhangTemplate> datas,
			Context context,String leixing) {
		super();
		this.datas = datas;
		this.context = context;
		this.leixing=leixing;
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
		ViewHolder viewholder=null;
		if(view==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.jiezhangclasschecklistviewitem, null);
			viewholder.JieZhangClassName=(TextView)view.findViewById(R.id.JieZhangClassName);
			viewholder.JieZhangCount=(TextView)view.findViewById(R.id.JieZhangCount);
			viewholder.JieZhangStatus=(TextView)view.findViewById(R.id.JieZhangStatus);
			viewholder.JieZhangTime=(TextView)view.findViewById(R.id.JieZhangTime);
			view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		viewholder.JieZhangClassName.setText(datas.get(position).getClassName());
		viewholder.JieZhangCount.setText(String.valueOf(datas.get(position).getCount()));
		if(leixing.equals("未锁定")){
		if(datas.get(position).getStatus()==0){
			viewholder.JieZhangStatus.setText("未锁定");
		}else{
			viewholder.JieZhangStatus.setText("已锁定");
		}}else if(leixing.equals("未核销")){
			if(datas.get(position).getStatus()==1){
				viewholder.JieZhangStatus.setText("未核销");
			}else{
				viewholder.JieZhangStatus.setText("已核销");
			}
		}
		viewholder.JieZhangTime.setText(datas.get(position).getDate());
		return view;
	}
class ViewHolder{
	TextView JieZhangClassName,JieZhangCount,JieZhangStatus,JieZhangTime;
}
}
