package tool;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.example.jiacaitong.R;
public class RunImageView extends View
{
  private static int mLeft = 0;
  private static int width = 0;
  private static int fullWidth = 0;
  private  BitmapDrawable  drawable;
  Context mContext;
  private  CloudHandler handler;
  private boolean directionLeft = true;
  public RunImageView(Context paramContext)
  {
    super(paramContext);
    mContext =paramContext;
    init();
  }

  public RunImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    mContext =paramContext;
    init();
  }

  public RunImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    mContext =paramContext;
    init();
  }
 
  public void init()
  {
	  drawable =   (BitmapDrawable)mContext.getResources().getDrawable(R.drawable.clound);
	  handler   = new CloudHandler();
	  handler.removeCallbacksAndMessages(null);
	  handler.sendEmptyMessageDelayed(1, 300L);
  }

  public void stop()
  {
	  handler.removeCallbacksAndMessages(null);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.translate(mLeft, 0.0F);
    drawable.draw(paramCanvas);
    super.onDraw(paramCanvas);
  }

  protected void onLayout(boolean paramBoolean, int left, int top, int right, int buttom)
  {
    if (paramBoolean)
    {
      width = right - left;
      fullWidth = 5 * width >> 2;
      drawable.setBounds(0, 0, fullWidth, buttom);
      this.getLayoutParams().height= drawable.getBitmap().getHeight()*right/drawable.getBitmap().getWidth();
    }
  }
 

  public class CloudHandler extends Handler{
	  
	  public void handleMessage(Message paramMessage)
	  {
		  if(directionLeft)
		  {
			if(mLeft==width-fullWidth)
			{
				mLeft++;
				directionLeft = false;
			}else
			{
				mLeft--;
			}
			  
		  }else
		  {
			if(mLeft==0)
			{
				mLeft--;
				directionLeft = true;
			}else
			{
				mLeft++;
			}
		  }
		 RunImageView.this.invalidate();
	     handler.sendEmptyMessageDelayed(1, 40);//����ͼƬ�ƶ��ٶ�
	  }
  }
}
 