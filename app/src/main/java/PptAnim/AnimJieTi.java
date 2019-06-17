package PptAnim;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.util.Log;
import android.view.View;

import tool.EnterAnimLayout;
public class AnimJieTi extends Anim {
    public AnimJieTi(EnterAnimLayout view) {
        super(view);
        //ÿһ�д����ܵ��յ�����Ҫ��ʱ�� = ��ʱ�� - ��һ�С��ȴ������������������ʱ��
        timePerLine =  totalPaintTime - (timeInterval* (lineNum-1));
    }

    float lineNum = 5;//���ݵ�����
    float timeInterval = 200;//ÿһ�н�������һ������ʱ���

    float timePerLine;//ÿһ�д����ܵ��յ�����Ҫ��ʱ��

    Path path = new Path();
    @Override
    public void handleCanvas(Canvas canvas, float rate) {
        path.reset();

        float left1 = w - (rate*totalPaintTime - 0*timeInterval)/timePerLine *w;
        float top1 = h/lineNum *0;
        float right1 = w;
        float bottom1 = top1 + h / lineNum;

        path.addRect(left1, top1,right1,bottom1, Path.Direction.CW);

        //����ÿһ����Ҫչʾ���������£����һ�����
        for(int i = 1; i< lineNum; i++) {
            float left = w - (rate*totalPaintTime - i*timeInterval)/timePerLine *w;
            float top = h/lineNum *i;
            float right = w;
            float bottom = top + h / lineNum;
            path.addRect(left,top,right,bottom, Path.Direction.CW);
        }
        canvas.clipPath(path);

        canvas.save();
    }
}
