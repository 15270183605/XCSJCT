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

import entity.Template;

public class TemplateAdapter extends BaseAdapter {

	private List<Template> mData = new ArrayList<Template>();
	private Context context;

	public TemplateAdapter(Context mcontext, List<Template> mdata) {
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
			view = LayoutInflater.from(context).inflate(R.layout.menulistitem1,
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
			viewholder1.image = (ImageView) view
					.findViewById(R.id.lockImage);
			viewholder1.image1 = (ImageView) view
					.findViewById(R.id.sign);
			view.setTag(viewholder1);

		}
		viewholder1 = (ViewHolder) view.getTag();
		Template template = mData.get(position);
		if(template.getCount()==0.0){
			viewholder1.image.setVisibility(View.GONE);
			viewholder1.image1.setVisibility(View.GONE);
			viewholder1.Count.setVisibility(View.GONE);
			viewholder1.MakePerson.setVisibility(View.GONE);
			viewholder1.MakeTime.setVisibility(View.GONE);
			viewholder1.MenuStatus.setVisibility(View.GONE);
			viewholder1.ClassName.setVisibility(View.GONE);
			viewholder1.SourceName.setText(template.getSource());
		}else{
			viewholder1.image.setVisibility(View.VISIBLE);
			viewholder1.image1.setVisibility(View.VISIBLE);
			viewholder1.Count.setVisibility(View.VISIBLE);
			viewholder1.MakePerson.setVisibility(View.VISIBLE);
			viewholder1.MakeTime.setVisibility(View.VISIBLE);
			viewholder1.MenuStatus.setVisibility(View.VISIBLE);
			viewholder1.ClassName.setVisibility(View.VISIBLE);
		viewholder1.SourceName.setText(template.getSource());
		viewholder1.ClassName.setText(template.getMenuName());
		viewholder1.MakeTime.setText(template.getTime());
		viewholder1.MakePerson.setText(template.getMakeperson());
		viewholder1.Count .setText("("+"￥"+String.valueOf(template.getCount())+")");
		if(template.getId()==1 || template.getId()==2){
		if(template.getStatus()==0){
			viewholder1.MenuStatus.setText("已开始");
			viewholder1.image.setVisibility(View.GONE);
		}
		else if(template.getStatus()==1){
			viewholder1.MenuStatus.setText("已锁定");
			viewholder1.image.setVisibility(View.VISIBLE);
			viewholder1.image.setImageResource(R.drawable.lock2);
		}
		else if(template.getStatus()==2){
			viewholder1.MenuStatus.setText("已做账");
			viewholder1.image.setVisibility(View.VISIBLE);
			viewholder1.image.setImageResource(R.drawable.makecount2);
		}
		
		}
		else if(template.getId()==3||template.getId()==4||template.getId()==5||template.getId()==6){
			if(template.getStatus()==0){
				viewholder1.MenuStatus.setText("已开始");
				viewholder1.image.setVisibility(View.GONE);
			}
			else if(template.getStatus()==1){
				viewholder1.MenuStatus.setText("已锁定");
				viewholder1.image.setVisibility(View.VISIBLE);
				viewholder1.image.setImageResource(R.drawable.lock2);
			}
			else if(template.getStatus()==2&&template.getProperty()==1){
				viewholder1.MenuStatus.setText("已核销");
				viewholder1.image.setVisibility(View.VISIBLE);
				viewholder1.image.setImageResource(R.drawable.hexiao);
			}
			else if(template.getStatus()==2&&template.getProperty()==0){
				viewholder1.MenuStatus.setText("核销完");
				viewholder1.image.setVisibility(View.VISIBLE);
				viewholder1.image.setImageResource(R.drawable.hexiao);
			}
			
		}
		if(template.getId()==1){
			viewholder1.image1.setImageResource(R.drawable.shous);
		}
		if(template.getId()==2){
			viewholder1.image1.setImageResource(R.drawable.pays);
		}
		if(template.getId()==3){
			viewholder1.image1.setImageResource(R.drawable.yingshous);
		}
		if(template.getId()==4){
			viewholder1.image1.setImageResource(R.drawable.yingfus);
		}
		if(template.getId()==5){
			viewholder1.image1.setImageResource(R.drawable.shishou);
		}
		if(template.getId()==6){
			viewholder1.image1.setImageResource(R.drawable.shifu);
		}
		}
		return view;
	}

	class ViewHolder {
		TextView SourceName, ClassName, MenuStatus, MakeTime,MakePerson,Count;
ImageView image,image1;
	}
}
