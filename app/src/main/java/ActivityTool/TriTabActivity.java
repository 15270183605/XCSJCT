package ActivityTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import viewpagerlib.indicator.TabIndicator;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.view.*;
import com.example.jiacaitong.R;

public class TriTabActivity extends Activity {
	  private List<View> Views = new ArrayList<View>();
    private List<String> mTitle = Arrays.asList("新闻","娱乐","学习");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.life);


        for (String string : mTitle) {
           View view=LayoutInflater.from(this).inflate(R.layout.activity_main, null);
           Views.add(view);
        }
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CusAdapter());
        /**
         * 把 TabIndicator 跟viewpager关联起来
         */
        TabIndicator tabIndecator = (TabIndicator) findViewById(R.id.JinRongTab);
        tabIndecator.setViewPagerSwitchSpeed(viewPager,600);
        tabIndecator.setTabData(viewPager,mTitle, new TabIndicator.TabClickListener() {
            @Override
            public void onClick(int position) {
                //顶部点击的方法公布出来
                viewPager.setCurrentItem(position);
            }
        });


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
       @Override
    public Object instantiateItem(ViewGroup container, int position) {
    	   container.addView(Views.get(position));
    	return Views.get(position);
    }
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			
			 container.removeView(Views.get(position));
		}
    }

}
