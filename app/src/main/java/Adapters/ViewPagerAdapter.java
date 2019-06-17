package Adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewPagerAdapter extends PagerAdapter {

	public ArrayList<ImageView> mImageview;
	public Activity mActivity;
	public ViewPagerAdapter(ArrayList<ImageView> mImageview,Activity mActivity){
		this.mImageview=mImageview;
		this.mActivity=mActivity;
	}
	public int getCount() {
		
		return mImageview.size();
	}

	
	public boolean isViewFromObject(View arg0, Object arg1) {
		
		return arg0==arg1;
	}
	public Object instantiateItem(ViewGroup container,int position){
		View view=mImageview.get(position);
		container.addView(view);
		return view;
	}
	public void destroyItem(ViewGroup container,int position,Object object){
		  container.removeView(mImageview.get(position));
	}

}
