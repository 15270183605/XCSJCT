package tool;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jiacaitong.R;

public class ScrollLayout extends HorizontalScrollView {
	private Context context;
	   //�Զ���View��������Ƕ��������LinearLayout
	   private LinearLayout mWapper;
	   //�˵�����
	   private ViewGroup mMenu;
	   //���ݲ���
	   private ViewGroup mContent;
	   //��Ļ���
	   private int mScreenWidth;
	   //�˵�����Ļ�Ҳ�ľ���,��λdp
	   private int mMenuRightPadding = 50;
	   //�˵��Ŀ��
	   private int mMenuWidth;
	   //�����־,��֤onMeasureִֻ��һ��
	   private boolean once = false;
	   //�˵��Ƿ��Ǵ�״̬
	   private static boolean isOpen = false;
	   //�Ƿ��ǳ���ʽ
	   private boolean isDrawerType = false;
	   private ImageView image;
	   public ScrollLayout(Context context,ImageView image) {
		   super(context);
	      this.context=context;
	      this.image=image;
	   }

	   public ScrollLayout(Context context, AttributeSet attrs) {
	       //�������������Ĺ��췽��
	       this(context, attrs, 0);

	   }
	   public ScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
	       super(context, attrs, defStyleAttr);

	       //��ȡ�����Զ��������
	       TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
	               R.styleable.SlidingMenu, defStyleAttr, 0);
	       int n = typedArray.getIndexCount();
	       //����ÿһ������
	       for (int i = 0; i < n; i++) {
	           int attr = typedArray.getIndex(i);
	           switch (attr){
	               //�������Զ������Ե�ֵ���ж�ȡ
	               case R.styleable.SlidingMenu_rightPadding:
	                   //�����Ӧ����ʽʱû�и�ֵ��ʹ��Ĭ��ֵ50,�����ֵ��ֱ�Ӷ�ȡ
	                   mMenuRightPadding = typedArray.getDimensionPixelSize(attr,
	                           (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, context.getResources().getDisplayMetrics()));
	                   break;
	               case R.styleable.SlidingMenu_drawerType:
	                   isDrawerType = typedArray.getBoolean(attr, false);
	                   break;
	               default:
	                   break;
	           }
	       }
	       //�ͷ�
	       typedArray.recycle();

	       //ͨ�����²����õ���Ļ��ȵ�����ֵ
	       WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	       DisplayMetrics displayMetrics = new DisplayMetrics();
	       windowManager.getDefaultDisplay().getMetrics(displayMetrics);
	       mScreenWidth = displayMetrics.widthPixels;
	   }
	   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	       if (!once){
	           once = true;
	           mWapper = (LinearLayout) getChildAt(0);
	           mMenu = (ViewGroup) mWapper.getChildAt(0);
	           mContent = (ViewGroup) mWapper.getChildAt(1);
	           //�˵�����������ĸ߶ȶ����Ա���Ĭ��match_parent
	           //�˵���� = ��Ļ��� - �˵�����Ļ�Ҳ�ļ��
	           mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
	           mContent.getLayoutParams().width = mScreenWidth;
	           //�����������еĲ˵��Ŀ�ߺ���������Ŀ��֮��,������LinearLayout��mWapper���Զ����ú���
	       }
	       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	   }

	   protected void onLayout(boolean changed, int l, int t, int r, int b) {

	       super.onLayout(changed, l, t, r, b);
	       if (changed){
	           //���ַ����仯ʱ����(ˮƽ�����������ƶ�menu�Ŀ��,�����ý�menu����)
	           this.scrollTo(mMenuWidth, 0);
	       }
	   }

	   @Override
	   public boolean onTouchEvent(MotionEvent ev) {
	       int action = ev.getAction();
	       //���º��ƶ�ʹ��HorizontalScrollView��Ĭ�ϴ���
	       switch (action){
	           case MotionEvent.ACTION_UP:
	               //��������ߵ�λ��
	               int scrollX = getScrollX();
	               if (scrollX > mMenuWidth / 2){
	                   //���صĲ��ֽϴ�, ƽ����������ʾ�˵�
	                   this.smoothScrollTo(mMenuWidth, 0);
	                   isOpen = false;
	               }else{
	                   //��ȫ��ʾ�˵�
	                   this.smoothScrollTo(0, 0);
	                   isOpen = true;
	               }
	               return true;
	       }
	       return super.onTouchEvent(ev);
	   }

	   public void openMenu(){
	       if (!isOpen){
	           this.smoothScrollTo(0, 0);
	           isOpen = true;
	       }
	   }

	   public void closeMenu(){
	       if (isOpen){
	           this.smoothScrollTo(mMenuWidth, 0);
	           isOpen = false;
	       }
	   }
	   public void toggleMenu(){
	       if (isOpen==true){
	           closeMenu();
	       }else{
	           openMenu();
	       }
	   }
	   protected void onScrollChanged(int l, int t, int oldl, int oldt) {
	       super.onScrollChanged(l, t, oldl, oldt);
	       if (isDrawerType){
	           float scale = l * 1.0f / mMenuWidth;  //1 ~ 0
	           //�������Զ���,��TranslationX
	           mMenu.setTranslationX(mMenuWidth * scale);
	          
	       }
	   }
	   public static boolean returnStatus(){
		   return isOpen;
	   }
	}
