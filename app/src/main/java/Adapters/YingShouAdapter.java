package Adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.YingShou;

public class YingShouAdapter extends BaseAdapter {

	private List<YingShou> mData = new ArrayList<YingShou>();
	private Context context;

	public YingShouAdapter(Context mcontext, List<YingShou> mdata) {
		this.mData = mdata;
		this.context = mcontext;

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
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
			viewholder1.Image = (ImageView) view
					.findViewById(R.id.lockImage);
			view.setTag(viewholder1);

		}
		viewholder1 = (ViewHolder) view.getTag();
		YingShou yingshou = mData.get(position);
		viewholder1.SourceName.setText(yingshou.getYingShouSource());
		viewholder1.ClassName.setText(yingshou.getMenuName());
		viewholder1.MakeTime.setText(yingshou.getDate());
		viewholder1.MakePerson.setText(yingshou.getMakePerson());
		viewholder1.Count.setText("("+"￥"+String.valueOf(yingshou.getCount())+")");
		if(yingshou.getStatus()==0){
			viewholder1.MenuStatus.setText("已开始");
			viewholder1.Image.setVisibility(View.GONE);
		}
		else if(yingshou.getStatus()==1){
			viewholder1.MenuStatus.setText("已锁定");
			viewholder1.Image.setVisibility(View.VISIBLE);
			viewholder1.Image.setImageResource(R.drawable.lock2);
		}
		else if(yingshou.getStatus()==2 && yingshou.getProperty()==1){
			view.setBackgroundColor(Color.CYAN);
			viewholder1.MenuStatus.setText("已核销");
			viewholder1.Image.setVisibility(View.VISIBLE);
			viewholder1.Image.setImageResource(R.drawable.hexiao);
		}
		else if(yingshou.getStatus()==2 && yingshou.getProperty()==0){
			view.setBackgroundColor(Color.GRAY);
			viewholder1.MenuStatus.setText("核销完");
			viewholder1.Image.setVisibility(View.VISIBLE);
			viewholder1.Image.setImageResource(R.drawable.hexiao);
		}
		else if(yingshou.getStatus()==3 && yingshou.getProperty()==1){
			viewholder1.MenuStatus.setText("已做账");
			viewholder1.Image.setVisibility(View.VISIBLE);
			viewholder1.Image.setImageResource(R.drawable.makecount2);
		}
		if(yingshou.getProperty()==1){
			view.setBackgroundColor(Color.LTGRAY);
		}
		else if(yingshou.getProperty()==0){
			view.setBackgroundColor(Color.GRAY);
		}
				
		return view;
	}

	class ViewHolder {
		TextView SourceName, ClassName, MenuStatus, MakeTime,MakePerson,Count;
		 ImageView Image;
	}

}
