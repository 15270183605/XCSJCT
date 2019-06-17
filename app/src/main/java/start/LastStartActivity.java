package start;

import java.util.Timer;
import java.util.TimerTask;

import tool.EnterAnimLayout;
import PptAnim.Anim;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jiacaitong.R;

import copyshouye.ActivityViewGroup;

public class LastStartActivity extends Activity implements OnClickListener{
	  private ImageView LiCaiNotify,ShouzhiPicture,LendPicture,DataPicture,MorePicture,WeatherPicture,DingWeiPicture;
	 private EnterAnimLayout ShouText,LendText,DataTxet,MoreText,WeatherTxet,DingweiTxet;
	  private Button letstart;
	   private boolean  isHide=true;
	   private Timer timer1;
	   private TimerTask timetask1;
	   private ImageView images[]={ShouzhiPicture,LendPicture,DataPicture,MorePicture,WeatherPicture,DingWeiPicture};
	  private EnterAnimLayout textviews[]={ShouText,LendText,DataTxet,MoreText,WeatherTxet,DingweiTxet};
	   private Handler handler;
	   private int count=0;
	   private Anim anims[];
	      protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.laststart);
    	LiCaiNotify=(ImageView)findViewById(R.id.LiCaiNotify);
    	letstart=(Button)findViewById(R.id.letstart);
    	letstart.setOnClickListener(this);
    	LiCaiNotify.setOnClickListener(this);
    	    	initLastViewPager();
    	handler=new Handler(){  
    		
    			 @Override
    			 	public void handleMessage(Message msg) {
    			 		// TODO Auto-generated method stub
    			 		super.handleMessage(msg);
    			 		switch(msg.what){
    			 		case 1:
    			 			ClearAnimation(count);
    			 			break;
    			 		}
    			 	}	
    		
    	};
    	runLastViewPager();
     }
    @Override
	public void onClick(View view) {
	   switch(view.getId()){
	   case R.id.LiCaiNotify:
		   Intent intent=new Intent(this,LiCaiKnowledge.class);
		   startActivity(intent);
		 		   break;
	   case R.id.letstart:
		   Intent intent1=new Intent(this,ActivityViewGroup.class);
		   startActivity(intent1);
		   this.finish();
		   break;
		   
	   }
		
	}
	public void initLastViewPager(){
		images[1]=(ImageView)findViewById(R.id.LendPicture);
		images[0]=(ImageView)findViewById(R.id.ShouzhiPicture);
		images[2]=(ImageView)findViewById(R.id.DataPicture);
		images[3]=(ImageView)findViewById(R.id.MorePicture);
		images[4]=(ImageView)findViewById(R.id.WeatherPicture);
		images[5]=(ImageView)findViewById(R.id.DingweiPicture);
		textviews[0]=(EnterAnimLayout)findViewById(R.id.ShouText);
		textviews[1]=(EnterAnimLayout)findViewById(R.id.LendTxet);
		textviews[2]=(EnterAnimLayout)findViewById(R.id.DataText);
		textviews[3]=(EnterAnimLayout)findViewById(R.id.MoreApplyText);
		textviews[4]=(EnterAnimLayout)findViewById(R.id.WeatherText);
		textviews[5]=(EnterAnimLayout)findViewById(R.id.LoactionTxet);
		/*Anim anim=new AnimBaiYeChuang(textviews[0]);
		Anim anim1=new AnimJieTi(textviews[1]);
		Anim anim2=new AnimQieRu(textviews[2]);
		Anim anim3=new AnimLunZi(textviews[3]);
		Anim anim4=new AnimQiPan(textviews[4]);
		Anim anim5=new AnimYuanXingKuoZhan(textviews[5]);
		anims=new Anim[]{anim,anim1,anim2,anim3,anim4,anim5};*/
        
		}
	
	public void ClearAnimation(int num){
		for(int i=0;i<images.length;i++){
			images[i].clearAnimation();
			//textviews[i].clearAnimation();
		}
		Animation animation=AnimationUtils.loadAnimation(this, R.drawable.pictureanimation);
		images[num].setAnimation(animation);
	   //anims[num].startAnimation(2500);
		animation.start();
		
	}
	public void runLastViewPager(){
		timer1=new Timer();
		timetask1=new TimerTask() {
			
			@Override
			public void run() {
				count++;
				if(count>5){
					count=0;
				}
				handler.sendEmptyMessage(1);
				
			}
		};
		timer1.schedule(timetask1,4* 1000, 4* 1000);
	}
}
