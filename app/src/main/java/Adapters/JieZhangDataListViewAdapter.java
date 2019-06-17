package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.CountEntity;

public class JieZhangDataListViewAdapter<T> extends BaseAdapter {
	private Context context;
	private List<T> CountDatas;

	public JieZhangDataListViewAdapter(Context context,
			List<T> countDatas) {
		super();
		this.context = context;
		CountDatas = countDatas;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return CountDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return CountDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		CountEntity entity=(CountEntity)this.getItem(position);
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.jiezhangldialoglistviewitem, null);
			init(holder, view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		AddData(holder,entity);
		return view;
	}

	class ViewHolder {
		TextView JiZhangTime, TotalDataShou, TotalDataZhi, TotalDataYingShou,
				TotalDataYingFu, TotalDataShiShou, TotalDataShiFu,
				TotalNumShou, TotalNumFu, TotalNumYingShou, TotalNumYingFu,
				TotalNumShiShou, TotalNumShiFu;
	}

	public void init(ViewHolder holder, View view) {
		holder.JiZhangTime = (TextView) view.findViewById(R.id.JiZhangTime);
		holder.TotalDataShou = (TextView) view.findViewById(R.id.TotalDataShou);
		holder.TotalDataZhi = (TextView) view.findViewById(R.id.TotalDataZhi);
		holder.TotalDataYingShou = (TextView) view
				.findViewById(R.id.TotalDataYingShou);
		holder.TotalDataYingFu = (TextView) view
				.findViewById(R.id.TotalDataYingFu);
		holder.TotalDataShiShou = (TextView) view
				.findViewById(R.id.TotalDataShiShou);
		holder.TotalDataShiFu = (TextView) view
				.findViewById(R.id.TotalDataShiFu);
		holder.TotalNumShou = (TextView) view.findViewById(R.id.TotalNumShou);
		holder.TotalNumFu = (TextView) view.findViewById(R.id.TotalNumZhi);
		holder.TotalNumYingShou = (TextView) view
				.findViewById(R.id.TotalNumYingShou);
		holder.TotalNumYingFu = (TextView) view
				.findViewById(R.id.TotalNumYingFu);
		holder.TotalNumShiShou = (TextView) view
				.findViewById(R.id.TotalNumShiShou);
		holder.TotalNumShiFu = (TextView) view.findViewById(R.id.TotalNumShiFu);

	}

	public void AddData(ViewHolder holder,CountEntity entity) {
		holder.JiZhangTime.setText(entity.getDate());
		holder.TotalDataShou.setText(String.valueOf(entity.getShouRuCount()));
		holder.TotalDataZhi.setText(String.valueOf(entity.getZhiChuCount()));
		holder.TotalDataYingShou.setText(String.valueOf(entity.getYingShouCount()));
		holder.TotalDataYingFu.setText(String.valueOf(entity.getYingFuCount()));
		holder.TotalDataShiShou.setText(String.valueOf(entity.getShiShouCount()));
		holder.TotalDataShiFu.setText(String.valueOf(entity.getShiFuCount()));
		holder.TotalNumShou.setText(String.valueOf(entity.getShouRunum()));
		holder.TotalNumFu.setText(String.valueOf(entity.getZhiChunum()));
		holder.TotalNumYingShou.setText(String.valueOf(entity.getYingShounum()));
		holder.TotalNumYingFu.setText(String.valueOf(entity.getYingFunum()));
		holder.TotalNumShiShou.setText(String.valueOf(entity.getShiShounum()));
		holder.TotalNumShiFu.setText(String.valueOf(entity.getShiFunum()));
	}
}
