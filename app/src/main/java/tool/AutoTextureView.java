package tool;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

public class AutoTextureView extends TextureView {
  private int mRadioWidth=0;
  private int mRadioHeight=0;
	public AutoTextureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
  public void setAspectRadio(int width,int height){
	  mRadioHeight=height;
	  mRadioWidth=width;
	  requestLayout();
}
  @Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int width=MeasureSpec.getSize(widthMeasureSpec);
		int height=MeasureSpec.getSize(heightMeasureSpec);
		if(mRadioWidth==0||mRadioHeight==0){
			setMeasuredDimension(width, height);
		}else{
			if(width<height*mRadioWidth/mRadioHeight){
				setMeasuredDimension(width, width*mRadioHeight/mRadioWidth);
			}else{
				setMeasuredDimension(height* mRadioHeight/mRadioWidth,height);
			}
		}
	}
}