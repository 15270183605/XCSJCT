package PptAnim;

import android.graphics.Canvas;
import android.view.View;

import tool.EnterAnimLayout;
public class AnimChaChu extends Anim {
    public AnimChaChu(EnterAnimLayout view) {
        super(view);
    }

    @Override
    public void handleCanvas(Canvas canvas, float rate) {
        float rectTop =  (h - h * rate);
        //���е�ǰ��Ҫչʾ�������������
        canvas.clipRect(0, rectTop, w, h);

        canvas.save();
    }
}
