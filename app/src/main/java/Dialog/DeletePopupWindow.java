package Dialog;


import tool.ScreenUtils;
import android.view.View;
import android.widget.PopupWindow;

public class DeletePopupWindow extends PopupWindow{
	
	public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
	    final int windowPos[] = new int[2];
	    final int anchorLoc[] = new int[2];
	 // 获取锚点View在屏幕上的左上角坐标位置
	    anchorView.getLocationOnScreen(anchorLoc);
	    final int anchorHeight = anchorView.getHeight();
	    // 获取屏幕的高宽
	    final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
	    final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
	    contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
	    // 计算contentView的高宽
	    final int windowHeight = contentView.getMeasuredHeight();
	    final int windowWidth = contentView.getMeasuredWidth();
	    // 判断需要向上弹出还是向下弹出显示
	    final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
	    if (isNeedShowUp) {
	        windowPos[0] = screenWidth - windowWidth;
	        windowPos[1] = anchorLoc[1] - windowHeight;
	    } else {
	        windowPos[0] = screenWidth - windowWidth;
	        windowPos[1] = anchorLoc[1] + anchorHeight;
	    }
	    return windowPos;
	}

}
