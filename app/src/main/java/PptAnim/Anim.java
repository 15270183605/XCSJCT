package PptAnim;

import android.graphics.Canvas;
import android.view.View;

import tool.EnterAnimLayout;

public abstract class Anim {
    protected EnterAnimLayout view;
    protected float w;
    protected float h;
    public float totalPaintTime;//���ƻ���ʱ��

    public Anim(EnterAnimLayout view) {
        this(view,2000);
    }

    public Anim(EnterAnimLayout view, float totalPaintTime) {
        this.totalPaintTime = totalPaintTime;
        this.view = view;
        this.view.setAnim(this);
        w = view.getWidth();
        h = view.getHeight();
    }

    public void startAnimation() {
        view.setmIsAnimaionRun(true);
        view.setStartTime(System.currentTimeMillis());

        view.invalidate();
    }
    public void startAnimation(long animTime) {
        totalPaintTime = animTime;
        startAnimation();
    }

    public abstract void handleCanvas(Canvas canvas, float rate);
}
