package work;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jinrong.BaoXianActivity;
import jinrong.CaiPiaoActivity;
import jinrong.ChuXuActivity;
import jinrong.GuPiaoActivity;
import jinrong.TouZiActivity;
import saoma.activity.CaptureActivity;
import tool.ChildViewPager;
import viewpagerlib.indicator.TabIndicator;
import Adapters.ZhongLeiAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jiacaitong.R;

public class JinRong extends Activity implements OnClickListener {
	 private List<View> Views = new ArrayList<View>();
	    private List<String> mTitle = Arrays.asList("投资","储蓄","股票",
	            "保险","彩票");
	    private LocalActivityManager manager=null;
	    private ImageView SeachApply,SaoApply,ZhongLei;
	    private Dialog dialog;
	    private String Title[]={"投资种类","储蓄种类","股票种类","保险种类","彩票种类"};
	    private Map<String,List<String>> ZhongLeiDatas;
	    private List<String> ZhongLeilists;
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.jinrong);
	        SeachApply=(ImageView)findViewById(R.id.SeachApply);
	        SaoApply=(ImageView)findViewById(R.id.SaoApply);
	        ZhongLei=(ImageView)findViewById(R.id.ZhongLei);
	        SeachApply.setOnClickListener(this);
	        SaoApply.setOnClickListener(this);
	        ZhongLei.setOnClickListener(this);
	        manager=new LocalActivityManager(this,true);
			manager.dispatchCreate(savedInstanceState);
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
	        final ChildViewPager viewPager = (ChildViewPager) findViewById(R.id.JinRongviewpager);
	        TabIndicator tritabIndecator = (TabIndicator) findViewById(R.id.JinRongTab);
	        viewPager.setAdapter(new CusAdapter());
	        tritabIndecator.setViewPagerSwitchSpeed(viewPager,600);
	        // 使用这个方法，则使用xml里面的控件
	        //tritabIndecator.setTabData(viewPager,new TabIndicator.TabClickListener()
	        tritabIndecator.setTabData(viewPager, mTitle,new TabIndicator.TabClickListener() {
	            @Override
	            public void onClick(int position) {
	                viewPager.setCurrentItem(position);
	            }
	        });

	        viewPager.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					
					return false;
				}
			});
	        AddMap();
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
		@Override
		public void onClick(View view) {
			switch(view.getId()){
			case R.id.SeachApply:
				Toast.makeText(this, "搜索应用", 1000).show();
				break;
			case R.id.SaoApply:
				Intent openCameraIntent = new Intent(this,
						CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0x11);	
				break;
			case R.id.ZhongLei:
				AddDialog();
				break;
		}
}
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (requestCode == 0x11 && resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				String scanResult = bundle.getString("result");
				Toast.makeText(this, "扫码成功", 1000).show();
			}
		}
		public void AddDialog(){
			View view=LayoutInflater.from(this).inflate(R.layout.jinrongdialog, null);
			ListView JinRongZhongLeiList=(ListView)view.findViewById(R.id.JinRongZhongLeiList);
			ZhongLeiAdapter adapter=new ZhongLeiAdapter(Title, ZhongLeiDatas, this);
			JinRongZhongLeiList.setAdapter(adapter);
			 dialog=new Dialog(getParent(),R.style.DiaologAlert);
			dialog.setContentView(view);
			Window window=dialog.getWindow();
			window.setGravity(Gravity.TOP);
			window.setWindowAnimations(R.style.DialogStyle1);
			WindowManager.LayoutParams lp=window.getAttributes();
			lp.height=400;
			lp.width=this.getWindowManager().getDefaultDisplay().getWidth();
			window.setAttributes(lp);
			dialog.show();
		}
		public void AddMap(){
			ZhongLeiDatas=new HashMap<String, List<String>>();
			for(int i=0;i<Title.length;i++){
				ZhongLeilists=new ArrayList<String>();
				for(int j=0;j<Title.length;j++){
					ZhongLeilists.add(Title[i]+j);
				}
				ZhongLeiDatas.put(Title[i], ZhongLeilists);
			}	
		}
}