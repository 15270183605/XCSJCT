package PptAnim;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.view.View;

import tool.EnterAnimLayout;
public class AnimLingXing extends Anim {
    public AnimLingXing(EnterAnimLayout view) {
        super(view);
    }
    Path path1=new Path();

    @Override
    public void handleCanvas(Canvas canvas, float rate) {
        //ºÙ«–≥ˆ¡‚–Œ«¯”Ú
        path1.reset();
        path1.moveTo(w/2, -h/2+ h*rate);
        path1.lineTo(-w/2+w*rate, h/2);
        path1.lineTo(w/2, h+h/2 - h*rate);
        path1.lineTo(w+w/2 -w*rate, h/2);
        path1.close();//∑‚±’
        canvas.clipPath(path1, Region.Op.XOR);

        canvas.save();
    }
}
