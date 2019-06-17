package Adapters;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class PayDialogViewPager extends PagerAdapter {

	public List<View> views;
	
	public PayDialogViewPager(List<View> views){
		this.views=views;
	}
	public int getCount() {
		
		return views.size();
	}

	
	public boolean isViewFromObject(View arg0, Object arg1) {
		
		return arg0==arg1;
	}
	public Object instantiateItem(ViewGroup container,int position){
		View view=views.get(position);
		container.addView(view);
		return view;
	}
	public void destroyItem(ViewGroup container,int position,Object object){
		  container.removeView(views.get(position));
	}
	

}
