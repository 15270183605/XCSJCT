package PptAnim;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Region;
import android.util.Log;
import android.view.View;

import tool.EnterAnimLayout;

import java.util.Random;
public class AnimSuiJiXianTiao extends Anim {
    private final int[] lines;//��������
    int restNum; //ʣ��������
    Random random = new Random();

    public AnimSuiJiXianTiao(EnterAnimLayout view) {
        super(view);
        //��ʼ�����������ʣ����������
        lines = new int[(int) h];
        restNum = (int) h -1; //��������ռ��һ���ظ߶�
        for (int i = 0; i < h-1; i++)
        {
            lines[i] = i;
        }
    }

    Path path = new Path();
    @Override
    public void handleCanvas(Canvas canvas, float rate) {
        path.reset();
        //������һ����Ҫȡ�õ���������
        int needNum = (int) ((h - 1) * rate - (h - 1 - restNum));
        //ѭ�������ȡ���������е�����
        for (int i = 0; i < needNum; i++)
        {
            //��ȡ��һ�������������ӽ�path��
            int r = random.nextInt(restNum - i);
            path.addRect(0, lines[r], w, lines[r] + 1, Path.Direction.CW);

            //����һ�λ�ȡ������������������ж�Ӧ��Ԫ������������ȡ��������
            //��֤������ǰ�沿�ֵ�����������ȡ����������Ķ���δȡ����
            int temp = lines[r];
            lines[r] = lines[restNum - i - 1];
            lines[restNum - 1 -i] = temp;
        }
        //���������С�֮ǰ��ȡ����������ȫ������path��
        for(int i = 0;i<(h-1) -restNum;i++) {
            path.addRect(0, lines[(int) (h - 2 - i)], w, lines[(int) (h - 2 - i)] + 1, Path.Direction.CW);
        }

        //ʣ����������
        restNum = restNum - needNum;
        canvas.clipPath(path);
        canvas.save();
    }

}
