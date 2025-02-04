package tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircleImageView extends ImageView {

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CircleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public CircleImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public void onDraw(Canvas canvas){
		Drawable drawable=getDrawable();
		if(drawable==null){
			return;
		}
		if(getWidth()==0||getHeight()==0){
			return;
		}
		if(!(drawable instanceof BitmapDrawable)){
			return;
		}
		Bitmap bit=((BitmapDrawable)drawable).getBitmap();
		if(null==bit){
			return;
		}
		Bitmap bitmap=bit.copy(Bitmap.Config.ARGB_8888,true);
		int width=getWidth();
		Bitmap roundBitmap=getCroppedBitmap(bitmap,width);
		canvas.drawBitmap(roundBitmap, 0,0,null);
	}
public static Bitmap getCroppedBitmap(Bitmap bitmap,int radius){
	Bitmap bit;
	if(bitmap.getWidth()!=radius||bitmap.getHeight()!=radius){
		bit=Bitmap.createScaledBitmap(bitmap, radius, radius, false);
		
	}else{
		bit=bitmap;
	}
	Bitmap btp=Bitmap.createBitmap(bit.getWidth(),bit.getHeight(),Config.ARGB_8888);
	Canvas canvas=new Canvas(btp);
	final Paint paint=new Paint();
	final Rect rect=new Rect(0,0,bit.getWidth(),bit.getHeight());
	paint.setAntiAlias(true);
	paint.setFilterBitmap(true);
	paint.setDither(true);
	canvas.drawARGB(0, 0, 0, 0);
	paint.setColor(Color.parseColor("#BAB399"));
	canvas.drawCircle(bit.getWidth()/2+0.7f, bit.getHeight()/2+0.7f, bit.getWidth()/2+0.1f, paint);
	paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	canvas.drawBitmap(bit, rect, rect,paint);
	return btp;
}
}
