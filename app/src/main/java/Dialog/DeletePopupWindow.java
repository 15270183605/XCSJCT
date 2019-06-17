package Dialog;


import tool.ScreenUtils;
import android.view.View;
import android.widget.PopupWindow;

public class DeletePopupWindow extends PopupWindow{
	
	public static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
	    final int windowPos[] = new int[2];
	    final int anchorLoc[] = new int[2];
	 // ��ȡê��View����Ļ�ϵ����Ͻ�����λ��
	    anchorView.getLocationOnScreen(anchorLoc);
	    final int anchorHeight = anchorView.getHeight();
	    // ��ȡ��Ļ�ĸ߿�
	    final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
	    final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
	    contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
	    // ����contentView�ĸ߿�
	    final int windowHeight = contentView.getMeasuredHeight();
	    final int windowWidth = contentView.getMeasuredWidth();
	    // �ж���Ҫ���ϵ����������µ�����ʾ
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
