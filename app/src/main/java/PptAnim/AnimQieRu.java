package PptAnim;

import android.graphics.Canvas;
import android.graphics.Region;
import android.view.View;

import tool.EnterAnimLayout;

public class AnimQieRu extends Anim {
    public AnimQieRu(EnterAnimLayout view) {
        super(view);
    }

    @Override
    public void handleCanvas(Canvas canvas, float rate) {

        canvas.translate(0,h-h*rate);

        canvas.save();
    }
}
