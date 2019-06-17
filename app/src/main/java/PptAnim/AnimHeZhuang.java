package PptAnim;

import android.graphics.Canvas;
import android.graphics.Region;
import android.util.Log;
import android.view.View;

import tool.EnterAnimLayout;
public class AnimHeZhuang extends Anim {
    public AnimHeZhuang(EnterAnimLayout view) {
        super(view);
    }

    @Override
    public void handleCanvas(Canvas canvas, float rate) {
        float rectLeft =  (w/2 * rate);
        float rectRight = w - rectLeft;
        float rectTop = (h/2 * rate);
        float rectBottom = h - rectTop;

        //������Ҫչʾ������
        canvas.clipRect(rectLeft, rectTop,rectRight , rectBottom, Region.Op.DIFFERENCE);

        canvas.save();
    }
}
