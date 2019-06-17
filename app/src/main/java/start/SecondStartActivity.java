package start;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.support.v4.view.*;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.example.jiacaitong.R;

public class SecondStartActivity extends Activity {
   private ViewPager SecondStartViewPager;
   public Timer mTimer;
	public TimerTask timetask;
	public int mTimeNum = 0;
	private Handler handler;
protected void onCreate(Bundle savedInstanceState) {
	
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.secondstart);
	SecondStartViewPager=(ViewPager)findViewById(R.id.SecondStartViewPage);
	
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
	Adapters.SecondStartViewPager viewpager=new Adapters.SecondStartViewPager(SecondStartActivity.this,ViewList());
	SecondStartViewPager.setAdapter(viewpager);
	startTimer();
	
}
//设置ViewPager更换时间
	public void startTimer() {
		mTimer = new Timer();
		timetask = new TimerTask() {
			public void run() {
				mTimeNum++;
				handler.sendEmptyMessage(2);
			}
		};
		mTimer.schedule(timetask,5* 1000, 5 * 1000);
	}

	// ViewPage设置内容
	public void setmyPagerIndex() {
		try {
			if (mTimeNum >=5) {
				mTimeNum = 0;
			}
			SecondStartViewPager.setCurrentItem(mTimeNum, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 任务停止；
	public void stopTask() {
		try {
			mTimer.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
public List<View> ViewList(){
	List<View> viewlist=new ArrayList<View>();
	
		 for(int i=0;i<5;i++){
			   View view = LayoutInflater.from(this).inflate(
						R.layout.secondstartviewpageitem, null);
			   viewlist.add(view);
		   }
				
	
	return viewlist;
}
}
