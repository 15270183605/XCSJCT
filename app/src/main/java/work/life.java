package work;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.support.v4.view.*;
import life.CheDai;
import life.FangDai;
import life.GongYi;
import life.JiaoYu;
import life.LifeMoney;
import life.WuXian;
import life.YiSheBao;
import saoma.activity.CaptureActivity;
import tool.ChildViewPager;
import viewpagerlib.bean.PageBean;
import viewpagerlib.callback.PageHelperListener;
import viewpagerlib.indicator.TabIndicator;
import viewpagerlib.indicator.TransIndicator;
import viewpagerlib.view.BannerViewPager;
import ActivityTool.LoopBean;
import Adapters.ZhongLeiAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

public class life extends Activity implements OnClickListener {
  private ImageView LifeSeachApply,LifeSaoApply,LifeZhongLei;
    private List<View> Views;
    private static final Integer[] RESURL = {R.drawable.lifemoney1,R.drawable.yishebao,R.drawable.xianmonney,
        R.drawable.jiaoyumoney,R.drawable.fangdai,R.drawable.chedai ,R.drawable.gongyimongey};
    private List<String> mTitle = Arrays.asList("生活缴费","医社保","五险一金","教育基金","房贷","车贷","公益基金");
   private static final String[] TEXT = {"生活缴费","医社保","五险一金","教育基金","房贷","车贷","公益基金"};
   private LocalActivityManager manager=null;
   private Dialog dialog;
   private String Title[]={"生活缴费","医社保","五险一金","教育基金","房贷","车贷","公益基金"};
   private Map<String,List<String>> ZhongLeiDatas;
   private List<String> ZhongLeilists;
   protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.life);
	 manager=new LocalActivityManager(this,true);
	  manager.dispatchCreate(savedInstanceState);
	init();
}
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.LifeSeachApply:
			Toast.makeText(this, "搜索应用", Toast.LENGTH_LONG).show();
			break;
		case R.id.LifeSaoApply:
			Intent openCameraIntent = new Intent(this,
					CaptureActivity.class);
			startActivityForResult(openCameraIntent, 0x11);	
			break;
		case R.id.LifeZhongLei:
			AddDialog();
			break;
		}
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
	public void init(){
		LifeSeachApply=(ImageView)findViewById(R.id.LifeSeachApply);
		LifeSaoApply=(ImageView)findViewById(R.id.LifeSaoApply);
		LifeZhongLei=(ImageView)findViewById(R.id.LifeZhongLei);
		LifeSaoApply.setOnClickListener(this);
		LifeSeachApply.setOnClickListener(this);
		LifeZhongLei.setOnClickListener(this);
		Views=new ArrayList<View>();
	        	Intent intent=new Intent(this,LifeMoney.class);
	        	Views.add(getView("A",intent));
	    		Intent intent2=new Intent(this,YiSheBao.class);
	    		Views.add(getView("B",intent2));
	    		Intent intent3=new Intent(this,WuXian.class);
	    		Views.add(getView("C",intent3));
	    		Intent intent4=new Intent(this,JiaoYu.class);
	    		Views.add(getView("D",intent4));
	    		Intent intent5=new Intent(this,FangDai.class);
	    		Views.add(getView("E",intent5));
	    		Intent intent6=new Intent(this,CheDai.class);
	    		Views.add(getView("F",intent6));
	    		Intent intent7=new Intent(this,GongYi.class);
	    		Views.add(getView("G",intent7));
		 final ChildViewPager viewPager = (ChildViewPager) findViewById(R.id.Lifeviewpager);
		 TabIndicator tritabIndecator = (TabIndicator) findViewById(R.id.LifeTab);
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
	    
	     List loopBeens = new ArrayList<LoopBean>();
	     for (int i = 0; i <TEXT.length; i++) {
	         LoopBean bean2 = new LoopBean();
	         bean2.res = RESURL[i];
	         bean2.text = TEXT[i];
	         loopBeens.add(bean2);

	     }
	     BannerViewPager transBannerViewPager = (BannerViewPager) findViewById(R.id.LifeBanner);
	     TransIndicator transIndicator = (TransIndicator) findViewById(R.id.LifeTran);
	     //配置pagerbean，这里主要是为了viewpager的指示器的作用，注意记得写上泛型
	   PageBean  bean = new PageBean.Builder<LoopBean>()
	             .setDataObjects(loopBeens)
	             .setIndicator(transIndicator)
	             .builder();

	     transBannerViewPager.setPageListener(bean, R.layout.loop_layout, new PageHelperListener<LoopBean>() {
	         @Override
	         public void getItemView(View view,final LoopBean bean) {
	             ImageView imageView = (ImageView) view.findViewById(R.id.loop_icon);
	             imageView.setImageResource(bean.res);
	             TextView textView = (TextView) view.findViewById(R.id.loop_text);
	             textView.setText(bean.text);
	             view.setOnClickListener(new View.OnClickListener() {
	                 @Override
	                 public void onClick(View view) {
	                     Toast.makeText(life.this, bean.text, Toast.LENGTH_SHORT).show();
	                 }
	             });

	             //如若你要设置点击事件，也可以直接通过这个view 来设置，或者图片的更新等等
	         }
	     });
	     AddMap();
	}
	private View getView(String id,Intent intent){	
		return manager.startActivity(id, intent).getDecorView();
	}
	
	public void AddDialog(){
		View view=LayoutInflater.from(this).inflate(R.layout.lifedialog, null);
		ListView LifeZhongLeiList=(ListView)view.findViewById(R.id.LifeZhongLeiList);
		ZhongLeiAdapter adapter=new ZhongLeiAdapter(Title, ZhongLeiDatas, this);
		LifeZhongLeiList.setAdapter(adapter);
		 dialog=new Dialog(getParent(),R.style.DiaologAlert);
		dialog.setContentView(view);
		Window window=dialog.getWindow();
		window.setGravity(Gravity.TOP);
		window.setWindowAnimations(R.style.DialogStyle2);
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
