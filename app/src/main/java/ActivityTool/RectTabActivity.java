package ActivityTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jinrong.BaoXianActivity;
import jinrong.CaiPiaoActivity;
import jinrong.ChuXuActivity;
import jinrong.GuPiaoActivity;
import jinrong.TouZiActivity;
import viewpagerlib.indicator.TabIndicator;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import com.example.jiacaitong.R;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
public class RectTabActivity extends Activity {
    private List<View> Views = new ArrayList<View>();
    private List<String> mTitle = Arrays.asList("投资","储蓄","股票","保险","彩票");
    private LocalActivityManager manager=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jinrong);
        	Intent intent=new Intent(this,TouZiActivity.class);
        	Views.add(getView("A",intent));
    		Intent intent2=new Intent(this,ChuXuActivity.class);
    		Views.add(getView("B",intent2));
    		Intent intent3=new Intent(this,GuPiaoActivity.class);
    		Views.add(getView("C",intent3));
    		Intent intent4=new Intent(this,BaoXianActivity.class);
    		Views.add(getView("D",intent4));
    		Intent intent5=new Intent(this,CaiPiaoActivity.class);
    		Views.add(getView("E",intent5));
        final ViewPager viewPager = (ViewPager) findViewById(R.id.JinRongviewpager);
        TabIndicator tritabIndecator= findViewById(R.id.JinRongTab);
        viewPager.setAdapter(new CusAdapter());
        tritabIndecator.setViewPagerSwitchSpeed(viewPager,600);
		//使用这个方法，则使用xml里面的控件
        tritabIndecator.setTabData(viewPager, mTitle,new TabIndicator.TabClickListener() {
            @Override
            public void onClick(int position) {
                viewPager.setCurrentItem(position);
            }
        });


    }

private View getView(String id,Intent intent){	
	return manager.startActivity(id, intent).getDecorView();
}
    class CusAdapter extends PagerAdapter{
		public CusAdapter() {
			super();
			
		}
		public int getCount() {
			
			return Views.size();
		}

		public boolean isViewFromObject(View arg0, Object arg1) {
			
			return arg0==arg1;
		}
    public Object instantiateItem(ViewGroup container, int position) {
    	   container.addView(Views.get(position));
    	return Views.get(position);
    }
		public void destroyItem(ViewGroup container, int position, Object object) {
			
			 container.removeView(Views.get(position));
		}
    }
}
