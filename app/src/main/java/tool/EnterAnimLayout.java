package tool;
import PptAnim.Anim;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.example.jiacaitong.R;

public class EnterAnimLayout extends FrameLayout {
    private Anim anim ; //��Ҫ���ŵĶ�������
    private long startTime = 0;//��ʼʱ��
    private boolean mIsAnimaionRun = false;//��ʼ���Ŷ����ı�־λ������Ϊtrue��Ȼ��invalidate����ʼ���Ŷ���
    private boolean mIsVisibleAtFirst = true;//view����ҳ���ʱ�Ƿ���ʾ��view

    public EnterAnimLayout(Context context) {
        super(context);
        initialize();
    }

    public EnterAnimLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attribute = context.obtainStyledAttributes(attrs, R.styleable.EnterAnimLayout);
        mIsVisibleAtFirst = attribute.getBoolean(R.styleable.EnterAnimLayout_isVisibleAtFirst, true);

        attribute.recycle();
        initialize();
    }

    public EnterAnimLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attribute = context.obtainStyledAttributes(attrs, R.styleable.EnterAnimLayout);
        mIsVisibleAtFirst = attribute.getBoolean(R.styleable.EnterAnimLayout_isVisibleAtFirst, false);

        attribute.recycle();
        initialize();
    }

    protected void initialize() {
    }

    public void setAnim(Anim anim) {
        this.anim = anim;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public boolean ismIsAnimaionRun() {
        return mIsAnimaionRun;
    }

    public void setmIsAnimaionRun(boolean mIsAnimaionRun) {
        this.mIsAnimaionRun = mIsAnimaionRun;
    }



    @Override
    protected void dispatchDraw(Canvas canvas) {
        //��������˴�ҳ��ʱ������ʾ������δ���ò��Ŷ������������ַ�draw
        if (mIsVisibleAtFirst && !mIsAnimaionRun) {
            super.dispatchDraw(canvas);
            return;
        }
        //��������˴�ҳ��ʱ�ؼ��Ȳ��ɼ�������δ���ò��Ŷ������򲻷ַ�draw
        if (!mIsVisibleAtFirst && !mIsAnimaionRun) {
            return;
        }

        //mIsAnimaionRun Ϊtrue�����Ŷ������Զ���ַ�draw

        //���㶯���Ѳ���ʱ�����rate=����ǰʱ��-������ʼʱ�䣩/�ܹ���Ҫ���ŵ�ʱ��
        long currentTime = System.currentTimeMillis();
        float rate = ((float)(currentTime - startTime) )/ anim.totalPaintTime;
        rate = rate > 1 ? 1 : rate;

        //�Ƚ���anim���ݵ�ǰ�����Ѳ���ʱ�����������canvas���Ѵ�����canvas�ٷַ�����view��draw
        //��ͬ��anim���в�ͬ�Ĵ���canvas�ķ�ʽ
        anim.handleCanvas(canvas,rate);
        super.dispatchDraw(canvas);

        //�������δ������ɣ������ˢ�£��������ɣ��򲻼���ˢ�£����ñ�־λ��
        if (rate < 1) {
            invalidate();
        } else {
            mIsAnimaionRun = false;
            mIsVisibleAtFirst = true;
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //���ڲ��Ŷ���ʱ���������
        if (ev.getAction() == MotionEvent.ACTION_DOWN && mIsAnimaionRun == true) {
            mIsAnimaionRun = false;
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
