package Adapters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.UserRefreedEntity;

public class UserefreedBackChatAdapter extends BaseAdapter {
   private Context context;
	private List<UserRefreedEntity> userrefreedDatas;
	 private SimpleDateFormat mSimple;
	 private SimpleDateFormat mSimpleDay;
	 private SimpleDateFormat mSimpleHour;
	 public static final int MESSAGE_LEFT=0;
	 public static final int MESSAGE_RIGHT=1;
	 public static final int RETURN_TYPE_COUNT=2;
	 private int day;
	 private String Lstr;
	 private String Rstr;
	 private Html.ImageGetter mImageGetter;
	public UserefreedBackChatAdapter(Context context,
			List<UserRefreedEntity> userrefreedDatas,Html.ImageGetter mImageGetter) {
		super();
		this.context = context;
		this.userrefreedDatas = userrefreedDatas;
		this.mImageGetter=mImageGetter;
		 mSimple=new SimpleDateFormat("yy-MM-dd-EE HH:mm");
		 mSimpleDay=new SimpleDateFormat("dd");
		 mSimpleHour=new SimpleDateFormat("HH:mm");
	}

	 public int getViewTypeCount(){
		 return RETURN_TYPE_COUNT;
	 }
	 public int getItemViewType(int position){
		 //获得message中定义的type
		  return userrefreedDatas.get(position).getType();
	 }
	public int getCount() {
		
		return userrefreedDatas.size();
	}

	@Override
	public Object getItem(int position) {
	
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	ViewHoldLeft vhLeft=null;
	ViewHoldRight vhRight=null;
	if(convertView==null){
		//根据获得的Type类型确定选择哪个；
		switch(getItemViewType(position)){
		case MESSAGE_LEFT:
			vhLeft=new ViewHoldLeft();
			convertView=LayoutInflater.from(context).inflate(R.layout.userrefeedbackchatleft, null);
			vhLeft.LeftImage=(ImageView)convertView.findViewById(R.id.HeadImageLeft);
			vhLeft.NickNameLeft=(TextView)convertView.findViewById(R.id.NickNameLeft);
		    vhLeft.ContentLeft=(TextView)convertView.findViewById(R.id.MessageLeft);
		    vhLeft.TimeLeft=(TextView)convertView.findViewById(R.id.TimeLeft);
		    convertView.setTag(vhLeft);
		    break;
		case MESSAGE_RIGHT:
			vhRight=new ViewHoldRight();
			convertView=LayoutInflater.from(context).inflate(R.layout.userrefeedbackchatright, null);
			vhRight.RightImage=(ImageView)convertView.findViewById(R.id.HeadimageRight);
			vhRight.NickNameRight=(TextView)convertView.findViewById(R.id.NickNameRight);
			vhRight.ContentRight=(TextView)convertView.findViewById(R.id.MessageRight);
			  vhRight.TimeRight=(TextView)convertView.findViewById(R.id.TimeRight);
		    convertView.setTag(vhRight);
		    break;
		    default:
		    	break;
		}
	}
	//获得chatMessage对象；
	UserRefreedEntity chatMessage=new UserRefreedEntity();
	chatMessage=userrefreedDatas.get(position);
	long TimeNow=System.currentTimeMillis();
	long TimeLast=chatMessage.getTime();
	 day=Integer.parseInt(mSimpleDay.format(new Date(TimeNow)))-Integer.parseInt(mSimpleDay.format(new Date(TimeLast)));
	switch(getItemViewType(position)){
	case MESSAGE_LEFT:
		vhLeft=(ViewHoldLeft)convertView.getTag();
		vhLeft.LeftImage.setImageBitmap(getBmp(chatMessage.getHeadImage()));
		vhLeft.NickNameLeft.setText(chatMessage.getNickName());
		//将时间格式化；
		String time=mSimple.format(new Date(chatMessage.getTime()));
		if(day==2){
			vhLeft.TimeLeft.setText("前天"+"  "+mSimpleHour.format(new Date(chatMessage.getTime())));
		}else if(day==1){
			vhLeft.TimeLeft.setText("昨天"+"  "+mSimpleHour.format(new Date(chatMessage.getTime())));
		}else if(day==0){
			vhLeft.TimeLeft.setText("今天"+"  "+mSimpleHour.format(new Date(chatMessage.getTime())));
		}else{
		vhLeft.TimeLeft.setText(time);}
		Spanned spanned1=Html.fromHtml(chatMessage.getContent(),mImageGetter,null);
		vhLeft.ContentLeft.setText(spanned1);		
		break;
	case MESSAGE_RIGHT:
		vhRight=(ViewHoldRight)convertView.getTag();
		vhRight.RightImage.setImageBitmap(getBmp(chatMessage.getHeadImage()));
		vhRight.NickNameRight.setText(chatMessage.getNickName());
		String time1=mSimple.format(new Date(chatMessage.getTime()));
		if(day==2){
			vhRight.TimeRight.setText("前天"+"  "+mSimpleHour.format(new Date(chatMessage.getTime())));
		}else if(day==1){
			vhRight.TimeRight.setText("昨天"+"  "+mSimpleHour.format(new Date(chatMessage.getTime())));
		}else if(day==0){
			vhRight.TimeRight.setText("今天"+"  "+mSimpleHour.format(new Date(chatMessage.getTime())));
		}else{
			vhRight.TimeRight.setText(time1);}
		Spanned spanned=Html.fromHtml(chatMessage.getContent(),mImageGetter,null);
		vhRight.ContentRight.setText(spanned);	
		
		break;
		default:
			break;
		
		
	}
	return convertView;
}
class ViewHoldLeft{
	ImageView LeftImage;
	TextView NickNameLeft,ContentLeft,TimeLeft;
}
class ViewHoldRight{
	ImageView RightImage;
	TextView NickNameRight,ContentRight,TimeRight;
}
//将二进制的图片转换成bitmap
	public Bitmap getBmp(byte[] in) 
	{
	    Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
	    return bmpout;
	}
}
