package Adapters;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class SecondStartViewPager extends PagerAdapter {
	private Context context;
	private String str[] = { "像这样", "这样", "这样", "这样","还是这样" };
	private String Contents[]={"收支不平衡而发愁","不知道如何计划收支而发愁","不能控制任性购买而发愁","口袋空空而发愁","一时手快....."};
	private String webUri[] = { "licai3.gif", "licai4.gif", "licai6.gif", "licai8.gif","licai16.gif" };
	public List<View> views;
	public SecondStartViewPager(Context context,List<View> views) {
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
			TextView secondstartText = (TextView) views.get(position)
					.findViewById(R.id.ViewpageText);
			WebView secondstartWebView = (WebView) views.get(position)
					.findViewById(R.id.ViewPageWebView);
			TextView ContentTxet = (TextView) views.get(position)
					.findViewById(R.id.ContentText);
		secondstartText.setText(str[position]);
		ContentTxet.setText(Contents[position]);
		secondstartWebView
				.loadDataWithBaseURL(
						null,
						"<HTML><body bgcolor='#e5e5e5'><div align=center><IMG src='file:///android_asset/"+webUri[position]+"'/></div></body></html>",
						"text/html", "UTF-8", null);
		container.addView(views.get(position));
		return views.get(position);
	}
	public void destroyItem(ViewGroup container,int position,Object object){
		  container.removeView(views.get(position));
	}

}
