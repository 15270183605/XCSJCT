package tool;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

public class ShimmerTextView extends TextView {
  private LinearGradient mLinearGradient;
  private Matrix mGradientMatrix;
  private Paint paint;
  private int mViewWidth=0;
  private int mTranslate=0;
  private boolean mAnimating=true;
	public ShimmerTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
protected void onSizeChanged(int width,int height,int oldWidth,int oldHeight) {
	super.onSizeChanged(width, height, oldWidth, oldHeight);
	if(mViewWidth==0){
		mViewWidth=getMeasuredWidth();
		if(mViewWidth>0){
			paint=getPaint();
			mLinearGradient=new LinearGradient(-mViewWidth,0,0,0,
					new int[]{Color.GRAY,Color.RED,Color.GRAY},
					new float[]{0,0.7f,1},Shader.TileMode.CLAMP);
			paint.setShader(mLinearGradient);
			mGradientMatrix=new Matrix();
		}
	} 
}
protected void onDraw(Canvas canvas) { 
	super.onDraw(canvas);
	if(mAnimating&& mGradientMatrix!=null){
		mTranslate+=mViewWidth/10;
		if(mTranslate>2*mViewWidth){
			mTranslate=-mViewWidth;
		} 
		mGradientMatrix.setTranslate(mTranslate, 0);
		mLinearGradient.setLocalMatrix(mGradientMatrix);
		postInvalidateDelayed(300);
	}
}
}
