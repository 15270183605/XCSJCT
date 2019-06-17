package tool;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.support.v4.view.*;
public class ChildViewPager extends ViewPager {
	public ChildViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ChildViewPager(Context context) {
		super(context);
	} // �������뼰���� �黹���ؼ�����

	private float xDistance, yDistance, xLast, yLast, xDown, mLeft,YDown;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// //��touch����Ȩ������view����������touch�¼���Ҳ����viewpager
		getParent().requestDisallowInterceptTouchEvent(true);
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDistance = yDistance = 0f;
			xLast = ev.getX();
			yLast = ev.getY();
			xDown = ev.getX();
			mLeft = ev.getX(); 
			YDown=ev.getY();// ���������������ͻ
			break;
		case MotionEvent.ACTION_MOVE:
			final float curX = ev.getX();
			final float curY = ev.getY();
			xDistance += Math.abs(curX - xLast);
			yDistance += Math.abs(curY - yLast);
			xLast = curX;
			yLast = curY;
			if (mLeft < 100 || xDistance < yDistance) {
				getParent().requestDisallowInterceptTouchEvent(false);
			} else {
				if (getCurrentItem() == 0) {
					if (curX < xDown) {
						getParent().requestDisallowInterceptTouchEvent(true);
					} else {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else if (getCurrentItem() == (getAdapter().getCount() - 1)) {
					if (curX > xDown) {
						getParent().requestDisallowInterceptTouchEvent(true);
					} else {
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				} else {
					getParent().requestDisallowInterceptTouchEvent(true);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

}
