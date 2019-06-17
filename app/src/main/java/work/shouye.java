package work;
import tool.Animation3D;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;

import com.example.jiacaitong.R;

import copyshouye.copyshouye;

public class shouye extends ActivityGroup {
	private LinearLayout container,layout;
	private Animation3D animation1, animation2, animation3, animation4;
	private int centerX;
	private int centerY;
	private int depthZ = 400;
	private int duration = 600;
	private boolean isStart = false;
	private float x1=0;
	private float x2=0;
	private float y1=0;
	private float y2=0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shouye);
		container = (LinearLayout) findViewById(R.id.container);
		layout = (LinearLayout) findViewById(R.id.layout);
		toActivity("second", copyshouye.class);
		/*layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN){
				x1=event.getX();
				y1=event.getY();
			}
			if(event.getAction()==MotionEvent.ACTION_UP){
				x2=event.getX();
				y2=event.getY();
			}
			if(x1-x2>50){
				onClickView();
			}else if(x1-x2<50){
				onClickView();
			}
				return true;
			}
		});*/
	}
	/*public boolean onTouchEvent(MotionEvent event){
		boolean flag=true;
		if(event.getAction()==MotionEvent.ACTION_DOWN){
			x1=event.getX();
			y1=event.getY();
		}
		if(event.getAction()==MotionEvent.ACTION_UP){
			x2=event.getX();
			y2=event.getY();
			if(x1-x2>50||x1-x2<-50){
				onClickView();
				flag=true;
			}else if(x1-x2>=-50&&x1-x2<=50){
				flag=false;
				
			}
		}
		
		return flag;
		
	}
	 private void initOpenAnim() {
	        //从0到90度，顺时针旋转视图，此时reverse参数为true，达到90度时动画结束时视图变得不可见，
	        animation1 = new Animation3D(0, 90, centerX, centerY, depthZ, true);
	        animation1.setDuration(duration);
	        animation1.setFillAfter(true);
	        animation1.setInterpolator(new AccelerateInterpolator());
	        animation1.setAnimationListener(new AnimationListener() {

	            @Override
	            public void onAnimationStart(Animation animation) {

	            }

	            @Override
	            public void onAnimationRepeat(Animation animation) {

	            }

	            @Override
	            public void onAnimationEnd(Animation animation) {
	                //从270到360度，顺时针旋转视图，此时reverse参数为false，达到360度动画结束时视图变得可见
	                 animation2 = new Animation3D(270, 360, centerX, centerY, depthZ, false);
	                 animation2.setDuration(duration);
	                 animation2.setFillAfter(true);
	                 animation2.setInterpolator(new DecelerateInterpolator());
	                 container.startAnimation(animation2);
	            }
	        });
	    }

	    *//**
	     * 卡牌文本介绍关闭效果：旋转角度与打开时逆行即可
	     *//*
	    private void initCloseAnim() {
	    	animation3 = new Animation3D(360, 270, centerX, centerY, depthZ, true);
	    	animation3.setDuration(duration);
	    	animation3.setFillAfter(true);
	    	animation3.setInterpolator(new AccelerateInterpolator());
	    	animation3.setAnimationListener(new AnimationListener() {

	           
	            public void onAnimationStart(Animation animation) {

	            }

	            @Override
	            public void onAnimationRepeat(Animation animation) {

	            }

	            @Override
	            public void onAnimationEnd(Animation animation) {
	                animation4 = new Animation3D(90, 0, centerX, centerY, depthZ, false);
	                animation4.setDuration(duration);
	                animation4.setFillAfter(true);
	                animation4.setInterpolator(new DecelerateInterpolator());
	                container.startAnimation(animation4);
	            }
	        });
	    }

	    public void onClickView() {
	        //以旋转对象的中心点为旋转中心点，这里主要不要再onCreate方法中获取，因为视图初始绘制时，获取的宽高为0
	        centerX = container.getWidth()/2;
	        centerY = container.getHeight()/2;
	        if (animation1 == null) {
	            initOpenAnim();
	            initCloseAnim();
	        }

	        //用作判断当前点击事件发生时动画是否正在执行
	        if (animation1.hasStarted() && !animation1.hasEnded()) {
	            return;
	        }
	        if (animation3.hasStarted() && !animation3.hasEnded()) {
	            return;
	        }

	        //判断动画执行
	        if (isStart) {
	        	container.startAnimation(animation3);
	        	toActivity("second", copeshouye.class);
	        	layout.setBackgroundColor(Color.WHITE);
	            isStart=false;
	        	
	        }else {
	        	container.startAnimation(animation1);
	        	toActivity("first", CopyShouYeActivity.class);
	        	layout.setBackgroundColor(Color.parseColor("#8cfffb"));
	            isStart=true;	
	        }
	    }*/
		private void toActivity(String lable, Class<?> cls) {
			container.removeAllViews();
			Intent intent = new Intent(this, cls);
			// mViewPager.removeAllViews();
			
			View v = getLocalActivityManager().startActivity(lable, intent)
					.getDecorView();
			v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			// mViewPager.addView(v);
			container.addView(v);
		}

		
}
