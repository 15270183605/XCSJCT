package userrefreedback;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class UserRefreedBack extends ActivityGroup implements OnClickListener {
 private ImageView ChangeWay,UserPoint1,UserPoint2,UserPoint3;
 private LinearLayout UserRefreedContainer;
 private RelativeLayout Layout2;
 private TextView ChatOnline,postoffice,sendMessage;
 private boolean flag=false;
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.userrefreed);
	ChangeWay=(ImageView)findViewById(R.id.ChangeWay);
	UserPoint1=(ImageView)findViewById(R.id.UserPoint1);
	UserPoint2=(ImageView)findViewById(R.id.UserPoint2);
	UserPoint3=(ImageView)findViewById(R.id.UserPoint3);
	UserRefreedContainer=(LinearLayout)findViewById(R.id.UserRefreedContainer);
	Layout2=(RelativeLayout)findViewById(R.id.Layout2);
	ChatOnline=(TextView)findViewById(R.id.ChatOnlineText);
	postoffice=(TextView)findViewById(R.id.postofficeText);
	sendMessage=(TextView)findViewById(R.id.sendMessageText);
	ChangeWay.setOnClickListener(this);
	ChatOnline.setOnClickListener(this);
	postoffice.setOnClickListener(this);
	sendMessage.setOnClickListener(this);
	toActivity("chatonline",ChatOnlineActivity.class);
}
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ChangeWay:
			if(flag==false){
				Layout2.setVisibility(View.VISIBLE);
				flag=true;
							}else{
				Layout2.setVisibility(View.GONE);
				flag=false;
				
			}
			break;
		case R.id.ChatOnlineText:
			ImageViewInit();
			UserPoint1.setVisibility(View.VISIBLE);
			toActivity("chatonline",ChatOnlineActivity.class);
			break;
		case R.id.postofficeText:
			ImageViewInit();
			UserPoint2.setVisibility(View.VISIBLE);
			toActivity("postoffice",PostOfficeActivity.class);
			break;
		case R.id.sendMessageText:
			ImageViewInit();
			UserPoint3.setVisibility(View.VISIBLE);
			toActivity("sendMessageText",SendMessageTextActivity.class);
			break;
		}
	}
	private void toActivity(String lable, Class<?> cls) {
		Intent intent = new Intent(this, cls);
		// mViewPager.removeAllViews();
		UserRefreedContainer.removeAllViews();
		View v = getLocalActivityManager().startActivity(lable, intent)
				.getDecorView();
		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		// mViewPager.addView(v);
		UserRefreedContainer.addView(v);
	}
  public void ImageViewInit(){
	  UserPoint1.setVisibility(View.GONE);
	  UserPoint2.setVisibility(View.GONE);
	  UserPoint3.setVisibility(View.GONE);
  }
}
