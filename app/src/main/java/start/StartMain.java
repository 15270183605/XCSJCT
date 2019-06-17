package start;
import java.util.ArrayList;
import java.util.List;
import android.support.v4.view.*;
import loginOrRegister.LoactionAndTianQi;
import loginOrRegister.Main;
import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import com.example.jiacaitong.R;

public class StartMain extends Activity{
	 private ViewPager viewpager=null;
	   private ImageView underline1,underline2,underline3,underline4;
	   private ImageView underlines[];
	   Context context=null;
	   private LocalActivityManager manager=null;
	   TabHost tabhost=null;
	   private int currIndex=0;//当前页卡号
		private Bitmap bitmap;
		  private LinearLayout StartImageContainer;
		  private ImageView LeftImage,RightImage;
	protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.startmain);
    	context=StartMain.this;
    	underline1=(ImageView)findViewById(R.id.underline1);
    	underline2=(ImageView)findViewById(R.id.underline2);
    	underline3=(ImageView)findViewById(R.id.underline3);
        underline4=(ImageView)findViewById(R.id.underline4);
    	//underlines=new ImageView[]{underline1,underline2,underline3};
    	underlines=new ImageView[]{underline1,underline2,underline3,underline4};
		manager=new LocalActivityManager(this,true);
		manager.dispatchCreate(savedInstanceState);
		bitmap = Main.returnBitmap();
		StartImageContainer=(LinearLayout)findViewById(R.id.StartImageContainer);
		LeftImage=(ImageView)findViewById(R.id.LeftImage);
		RightImage=(ImageView)findViewById(R.id.RightImage);
	    Start();
	    LeftImage.clearAnimation();
		RightImage.clearAnimation();
	
		//initImageView();
		initPagerViewer();
		underlines[0].setBackgroundResource(R.drawable.loadpoint1);
		LoactionAndTianQi loactionandtianqi=new LoactionAndTianQi(context);//初始化定位以及天气数据
		
		
}
	public void initPagerViewer(){
		viewpager=(ViewPager)findViewById(R.id.viewpager);
		final ArrayList<View> list=new ArrayList<View>();
		Intent intent=new Intent(context,FirstStartActivity.class);
		list.add(getView("A",intent));
		Intent intent2=new Intent(context,SecondStartActivity.class);
		list.add(getView("B",intent2));
		Intent intent3=new Intent(context,ThirdStartActivity.class);
		list.add(getView("C",intent3));
		Intent intent4=new Intent(context,LastStartActivity.class);
		list.add(getView("D",intent4));
		viewpager.setAdapter(new MyPagerAdapter(list));
		viewpager.setCurrentItem(0);
		viewpager.setOnPageChangeListener(new MyOnPageChangeListener());}
		private View getView(String id,Intent intent){	
			return manager.startActivity(id, intent).getDecorView();
		}
		public class MyPagerAdapter extends PagerAdapter{

			List<View> list=new ArrayList<View>();
			public MyPagerAdapter(ArrayList<View> list){
				this.list=list;
			}
			public int getCount() {
				
				return list.size();
			}

			public boolean isViewFromObject(View arg0, Object arg1) {
				
				return arg0==arg1;
			}
			public Object instantiateItem(View v,int position){
				ViewPager pviewpager=((ViewPager)v);
				pviewpager.addView(list.get(position));
				return list.get(position);
			}
			public void destroyItem(ViewGroup container,int position,Object object){
				  container.removeView(list.get(position));
			}
	    
		}
	  public class MyOnPageChangeListener implements OnPageChangeListener{
		     
		public void onPageScrollStateChanged(int arg0) {
			
			
		}

		
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		
		public void onPageSelected(int arg0) {
			currIndex=arg0;
			switch(arg0){
			case 0:
				runUnderLines();
				break;
			case 1:
				runUnderLines();
				break;
			case 2:
				runUnderLines();
				break;
			case 3:
				runUnderLines();
				break;
			}
			
		}
		  
	  }
	  private void runUnderLines(){
		  for(int i=0;i<underlines.length;i++){
				if(currIndex==i){
					underlines[currIndex].setBackgroundResource(R.drawable.loadpoint1);
				}
				else{
					underlines[i].setBackgroundResource(R.drawable.loadpoint2);
				}
			}
	  }
	  //根据获取到的位图来布置开门动画
	  private Bitmap[] getImageView(Bitmap bitmap){
			if(bitmap==null){
				return null;
			}
			int width=bitmap.getWidth();
			int height=bitmap.getHeight();
			int wid,heig,retX;
			wid=width/2;
			heig=height/2;
			retX=width/2;
			Bitmap[] bitmaps=new Bitmap[2];
			Bitmap bit=Bitmap.createBitmap(bitmap, 0, 0, wid,heig,null,false);
			Bitmap bitt=Bitmap.createBitmap(bitmap, retX, 0, wid,heig,null,false);
			bitmaps[0]=bit;
			bitmaps[1]=bitt;
			if(bitmap!=null&&!bitmap.equals(bit)&&!bitmap.isRecycled()){
				bitmap.recycle();
			}
			return bitmaps;
		}
	  //设置并开启开门效果的动画
		public void Start(){
			Bitmap bitmaps[]=new Bitmap[2];
			bitmaps=getImageView(bitmap);
			LeftImage.setImageBitmap(bitmaps[0]);
			RightImage.setImageBitmap(bitmaps[1]);
			//添加动画效果;
			LeftImage.animate().setDuration(1000).translationX(-bitmaps[0].getWidth()).start();
			RightImage.animate().setDuration(1000).translationX(bitmaps[1].getWidth()).withEndAction(new Runnable() {
				
				@Override
				public void run() {
					StartImageContainer.setVisibility(View.GONE);
					
				}
			}).start();
		}
}