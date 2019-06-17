package start;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.support.v4.view.*;
import tool.ViewPagerAnimation;
import Adapters.LastStartViewPager;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.example.jiacaitong.R;

public class ThirdStartActivity extends Activity{
 
   private List<View> views;
   private ViewPager LastStartViewPage;
 
   private Timer timer;
   private TimerTask timetask;
   private int  number=0,count=0;
   private Handler handler;
   private View view;
 
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.thirdstart);
    	init();
    	    }
	
	public void init(){
		
    	LastStartViewPage=(ViewPager)findViewById(R.id.LastStartViewPage);
    	
    	views=new ArrayList<View>();
    	for(int i=0;i<4;i++){
        		View view1=LayoutInflater.from(this).inflate(R.layout.thirdstartviewpageritem1, null);
         	   views.add(view1);
    	}
            	handler = new Handler() {
    		public void handleMessage(Message msg) {
    			super.handleMessage(msg);
    			int what = msg.what;
    			switch (what) {
        			case 2:
    				setmyPagerIndex();
    				break;
    			}
    		}
    	};
    	LastStartViewPager viewpager=new LastStartViewPager(this, views);
    	LastStartViewPage.setAdapter(viewpager);
    	LastStartViewPage.setPageTransformer(true, new ViewPagerAnimation());
    	startTimer();
    	}
	//设置ViewPager更换时间
		public void startTimer() {
			timer = new Timer();
			timetask = new TimerTask() {
				public void run() {
					number++;
					handler.sendEmptyMessage(2);
				}
			};
			timer.schedule(timetask,5* 1000, 5 * 1000);
		}

		// ViewPage设置内容
		public void setmyPagerIndex() {
			try {
				if (number >=5) {
					number=0;
				}
				LastStartViewPage.setCurrentItem(number, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 任务停止；
		public void stopTask() {
			try {
				timer.cancel();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
}  
