package tool;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

public class AnimationImage extends ImageView {

    private int mTop;

    public AnimationImage(Context context) {
        super(context);
        init();
    }

    public AnimationImage(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ValueAnimator animator = ValueAnimator.ofInt(0,100,0);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(4000);
        animator.start();

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
          
            public void onAnimationUpdate(ValueAnimator animation) {
                int dx = (Integer) animation.getAnimatedValue();
              setVisibility(View.VISIBLE);
                setTop(mTop-dx);
            }
        });}
       /* animator.addListener(new Animator.AnimatorListener() {
            public void onAnimationStart(Animator animation) {
                //setImageResource(R.drawable.people);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
              
                       // setImageResource(R.drawable.people);
                        
            }
                 
        });
        //animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(4000);
        animator.start();

    }
*/
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mTop = top;
    }
}

