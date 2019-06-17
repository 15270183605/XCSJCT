package Adapters;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class LastStartViewPager extends PagerAdapter {
	private Context context;
	private String str[] = { "������?", "����ȫ?", "̫�鷳?", "��Щ��ͳͳ��ͬ����!" };
	private int images[] = { R.drawable.laststart2,R.drawable.laststart3,R.drawable.laststart4,R.drawable.laststart5};
	public List<View> views;
	public LastStartViewPager(Context context,List<View> views) {
		super();
		this.context = context;
		this.views=views;
  
	}
	public int getCount() {

		return str.length;
	}
public boolean isViewFromObject(View arg0, Object arg1) {
		
		return arg0==arg1;
	}
	public Object instantiateItem(ViewGroup container,int position){
			TextView laststartText = (TextView) views.get(position)
					.findViewById(R.id.LastStartText);
			ImageView laststartImageView = (ImageView) views.get(position)
					.findViewById(R.id.LastStartImage);
		laststartText.setText(str[position]);
		laststartImageView.setImageResource(images[position]);
						container.addView(views.get(position));
		return views.get(position);}
	public void destroyItem(ViewGroup container,int position,Object object){
		  container.removeView(views.get(position));
	}

}
