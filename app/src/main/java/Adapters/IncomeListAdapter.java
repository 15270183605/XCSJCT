package Adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.Income;

public class IncomeListAdapter extends BaseAdapter {
	private List<Income> mData = new ArrayList<Income>();
	private Context context;

	public IncomeListAdapter(Context mcontext, List<Income> mdata) {
		this.mData = mdata;
		this.context = mcontext;

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
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
			view = LayoutInflater.from(context).inflate(R.layout.menulistitem,
					null);
			viewholder1.ClassName = (TextView) view
					.findViewById(R.id.ClassName);
			viewholder1.MakeTime = (TextView) view.findViewById(R.id.MakeTime);
			viewholder1.MenuStatus = (TextView) view
					.findViewById(R.id.MenuStatus);
			viewholder1.SourceName = (TextView) view
					.findViewById(R.id.SourceName);
			viewholder1.MakePerson = (TextView) view
					.findViewById(R.id.MakePerson);
			viewholder1.Count = (TextView) view
					.findViewById(R.id.MoneyCounts);
			viewholder1.lockImage = (ImageView) view
					.findViewById(R.id.lockImage);
			view.setTag(viewholder1);

		}
		viewholder1 = (ViewHolder) view.getTag();
		Income income = mData.get(position);
		viewholder1.SourceName.setText(income.getIncomeSource());
		viewholder1.ClassName.setText(income.getMenuName());
		viewholder1.MakeTime.setText(income.getDate());
		viewholder1.Count .setText("("+"￥"+String.valueOf(income.getCount())+")");
		if(income.getStatus()==0){
			viewholder1.MenuStatus.setText("初始化");
			viewholder1.lockImage.setVisibility(View.GONE);
		}
		if(income.getStatus()==1){
			viewholder1.MenuStatus.setText("已锁定");
			viewholder1.lockImage.setVisibility(View.VISIBLE);
			viewholder1.lockImage.setImageResource(R.drawable.lock2);
		}
		if(income.getStatus()==2){
			viewholder1.MenuStatus.setText("已做账");
			viewholder1.lockImage.setVisibility(View.VISIBLE);
			viewholder1.lockImage.setImageResource(R.drawable.makecount2);
		}
		viewholder1.MakePerson.setText(income.getMakePerson());
		return view;
	}

	class ViewHolder {
		TextView SourceName, ClassName, MenuStatus, MakeTime,MakePerson,Count;
        ImageView lockImage;
	}
}