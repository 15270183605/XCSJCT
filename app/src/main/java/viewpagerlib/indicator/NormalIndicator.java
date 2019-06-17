package viewpagerlib.indicator;

import viewpagerlib.bean.PageBean;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.support.v4.view.*;
import com.example.jiacaitong.R;
public class NormalIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    private static final String TAG = "zsr";


    /**
     * normal and logic
     */
    private Context mContext;
    private int mLastPosition ;
    private int mCount = 0;
    /**
     * attrs
     */
    private int mSelector;
    private int mLeftMargin;
    private boolean mDismissOpen; //�Ƿ����صײ�ָʾ���������������顱��view ���ֵ�ʱ��
    /**
     * ui
     */
    private View mOpenView;


    public NormalIndicator(Context context) {
        this(context,null);
    }

    public NormalIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NormalIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NormalIndicator);
        mSelector = ta.getResourceId(R.styleable.NormalIndicator_normal_selector,
                R.drawable.pay_bottom_circle);
        mLeftMargin = (int) ta.getDimension(R.styleable.NormalIndicator_normal_leftmargin,15);
        mDismissOpen =  ta.getBoolean(R.styleable.NormalIndicator_normal_dismiss_open,false);
        setGravity(Gravity.CENTER);
        ta.recycle();
    }


    public void addPagerData(PageBean bean, ViewPager viewPager){
        if (bean != null) {
            mCount = bean.datas.size();
            //�����СԲ��
            LayoutParams params = new
                    LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(mLeftMargin,0,0,0);
            for (int i = 0; i < mCount; i++) {
                ImageView imageView = new ImageView(mContext);
                if (i == 0){
                    imageView.setSelected(true);
                }else{
                    imageView.setSelected(false);
                }
                imageView.setLayoutParams(params);
                imageView.setBackgroundResource(mSelector);

                addView(imageView);
            }

            if (viewPager != null) {
                viewPager.setOnPageChangeListener(this);
            }
        }
        if (bean.openview != null){
            mOpenView = bean.openview;
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }


    public void onPageSelected(int position) {
        viewPagerSeleted(position%mCount);
        showStartView(position%mCount);
    }



    public void onPageScrollStateChanged(int state) {

    }

    /**
     *  ����viewpager ����ʱ���ײ�Բ���״̬��ʾ
     * @param position
     */
    private void viewPagerSeleted(int position) {
        View lastView ;
        if (mLastPosition >= 0){
            lastView = getChildAt(mLastPosition);
            if (lastView != null) {
                lastView.setSelected(false);

            }
        }

        View currentView = getChildAt(position);
        if (currentView != null){
            currentView.setSelected(true);
        }
        mLastPosition = position;
    }


    /**
     * ��ʾ���һҳ��״̬
     * @param position
     */
    private void showStartView(final int position) {
        // ���һҳ
        if (position == mCount - 1) {
            if (mOpenView != null) {
                mOpenView.setVisibility(VISIBLE);
                ObjectAnimator animator = ObjectAnimator.ofFloat(mOpenView,
                        "alpha", 0, 1);
                animator.setDuration(500);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();
                if (mDismissOpen){
                    setVisibility(View.GONE);
                }
            }
        } else {
            if (mOpenView != null) {

                mOpenView.setVisibility(GONE);

                if (mDismissOpen){
                    setVisibility(VISIBLE);
                }
            }
        }
    }
}
