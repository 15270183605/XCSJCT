package viewpagerlib.view;

import java.util.List;
import android.support.v4.view.*;
import viewpagerlib.ViewPagerHelperUtils;
import viewpagerlib.bean.PageBean;
import viewpagerlib.callback.PageHelperListener;
import viewpagerlib.indicator.NormalIndicator;
import viewpagerlib.indicator.TextIndicator;
import viewpagerlib.indicator.TransIndicator;
import viewpagerlib.indicator.ZoomIndicator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.jiacaitong.R;

/**
 * Created by Administrator on 2017/11/9.
 */

public class BannerViewPager extends ViewPager implements View.OnTouchListener {
    /**
     * const
     */
    private static final String TAG = "BannerViewPager";
    private static final int LOOP_MSG = 0x1001;
    private static final int LOOP_COUNT = 5000;
    /**
     * attrs
     */
    private int mLoopTime;
    private boolean isLoop; //�Ƿ��Զ��ֲ�
    private int mSwitchTime;
    private int mLoopMaxCount = 1;
    private boolean isSlide; //�Ƿ�����ֲ�����
    /**
     * others
     */
    private int mCurrentIndex;
    private LayoutInflater mInflater;
    private Rect mScreentRect;
    /**
     * handle
     */
    private  Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == LOOP_MSG){
                if (isSlide) {
                    mCurrentIndex = getCurrentItem(); //���»�ȡindex
                    if (mCurrentIndex >= LOOP_COUNT / 2) {
                        mCurrentIndex++;
                    }
                    if (mCurrentIndex > LOOP_COUNT) {
                        mCurrentIndex = LOOP_COUNT / 2;
                    }
                   // Log.d(TAG, "zsr --> handleMessage: "+mCurrentIndex);

                    setCurrentItem(mCurrentIndex);

                    mHandler.sendEmptyMessageDelayed(LOOP_MSG, mLoopTime);

                }
            }
        }
    };


    public BannerViewPager(Context context) {
        this(context,null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BannerViewPager);
        isLoop = ta.getBoolean(R.styleable.BannerViewPager_banner_isloop,false);
        mLoopTime = ta.getInteger(R.styleable.BannerViewPager_banner_looptime,2000);
        mSwitchTime = ta.getInteger(R.styleable.BannerViewPager_banner_switchtime,600);
        mLoopMaxCount = ta.getInteger(R.styleable.BannerViewPager_banner_loop_max_count,mLoopMaxCount);
        ta.recycle();
        mInflater = LayoutInflater.from(context);
        setOnTouchListener(this);
        ViewPagerHelperUtils.initSwitchTime(getContext(),this,mSwitchTime);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        mScreentRect = new Rect(0,0,dm.widthPixels,dm.heightPixels);
    }





    /**
     * ���ü���
     * @param bean ���õ�����
     * @param layoutid �ӿؼ��� layout
     * @param listener ������԰� �ӿؼ��� layout ��õ�view������ȥ
     */
    public void setPageListener(PageBean bean, int layoutid, PageHelperListener listener){
        if (bean.datas.size() >= mLoopMaxCount){
            isSlide = true;
        }else{
            isSlide = false;
        }
        CusViewPagerAdapter adapter = new CusViewPagerAdapter<Object>(bean.datas,layoutid,listener);
        adapter.notifyDataSetChanged();
        setAdapter(adapter);
        setOffscreenPageLimit(3);
        if (isSlide) {
            int index = ViewPagerHelperUtils.LOOP_COUNT/2 % bean.datas.size();
            //�����ܱ�֤�ӵ�һҳ��ʼ
            setCurrentItem(ViewPagerHelperUtils.LOOP_COUNT / 2 - index +bean.datas.size());
        }else{
            setCurrentItem(0);
        }
        if (bean.bottomLayout != null){
            //ѡ��ͬ��indicator
            if (bean.bottomLayout instanceof NormalIndicator){
                ((NormalIndicator) bean.bottomLayout).addPagerData(bean,this);
            }
            if (bean.bottomLayout instanceof TransIndicator){
                ((TransIndicator) bean.bottomLayout).addPagerData(bean,this);
            }
            if (bean.bottomLayout instanceof ZoomIndicator){
                ((ZoomIndicator) bean.bottomLayout).addPagerData(bean,this);
            }
            if (bean.bottomLayout instanceof TextIndicator){
                ((TextIndicator) bean.bottomLayout).addPagerData(bean,this);
            }
        }

    }

    /**
     * ���д���ʱֹͣ
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        mHandler.removeMessages(LOOP_MSG);
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_UP :
                if (isLoop) {
                    mHandler.sendEmptyMessageDelayed(LOOP_MSG, mLoopTime);
                }
                break;

            default:
                break;
        }
        return false;
    }

    /**
     * �ֶ�ֹͣ
     */
    public void stopAnim(){
        if (isLoop) {
            mHandler.removeMessages(LOOP_MSG);
        }
    }

    /**
     * �ֶ���ʼ
     */
    public void startAnim(){
        if (isLoop) {

            mHandler.removeMessages(LOOP_MSG);
            mHandler.sendEmptyMessageDelayed(LOOP_MSG, mLoopTime);


        }
    }



    /**
     * ����adapter
     * @param <T>
     */
    class CusViewPagerAdapter<T> extends PagerAdapter{
        PageHelperListener listener;
        List<T> list;
        int layoutid ;

        public CusViewPagerAdapter(List<T> list,
                                   int layoutid,
                                  PageHelperListener listener) {
            this.listener = listener;
            this.list = list;
            this.layoutid = layoutid;

        }

        @Override
        public int getCount() {
            if (isSlide) {
                return this.list.size() + ViewPagerHelperUtils.LOOP_COUNT;
            }else{
                return this.list.size();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mInflater.inflate(layoutid,null);

            if (isSlide) {
                this.listener.getItemView(view, this.list.get(position % this.list.size()));
            }else{
                this.listener.getItemView(view,this.list.get(position));
            }
            container.addView(view,0);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * ����˳��ˣ��Զ�ֹͣ���������Զ���ʼ
     * @param visibility
     */
    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (isLoop){
            if (visibility == View.VISIBLE){
               // Log.d(TAG, "zsr --> onWindowVisibilityChanged: ");
                startAnim();
            }else{
               stopAnim();
            }
        }
    }

    /**
     * �ж��Ƿ��Ƴ��ɼ���Ļ��
     * @return
     */
    public boolean isOutVisiableWindow(){
        int[] pos = new int[2];
        this.getLocationOnScreen(pos);
        boolean isOutVisiabel = pos[1] <= 0 || (pos[1] > (mScreentRect.height() - this.getHeight())); //������Ļ
        return isOutVisiabel ;
    }

    @Override
    protected void detachAllViewsFromParent() {
        super.detachAllViewsFromParent();
        mHandler.removeCallbacksAndMessages(null);
    }
}
