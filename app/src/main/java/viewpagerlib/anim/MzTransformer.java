package viewpagerlib.anim;

import android.view.View;
import android.support.v4.view.*;
/**
 * Created by Administrator on 2017/11/8.
 */

public class MzTransformer implements ViewPager.PageTransformer {
    private static final float MAX_SCALE = 1.0f;
    private static final float MIN_SCALE = 0.9f;//0.85f
    @Override
    public void transformPage(View view, float position) {
        //setScaleYֻ֧��api11����
        if (position < -1) {
            // view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        } else if (position <= 1) //aҳ������bҳ �� aҳ�� 0.0 -1 ��bҳ��1 ~ 0.0
        { // [-1,1]
//              Log.e("TAG", view + " , " + position + "");
            float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
            //  view.setScaleX(scaleFactor);
            //ÿ�λ��������΢С���ƶ�Ŀ����Ϊ�˷�ֹ�����ǵ�ĳЩ�ֻ��ϳ������ߵ�ҳ��Ϊ��ʾ�����
            view.setScaleY(scaleFactor);
        } else { // (1,+Infinity]
            // view.setScaleX(MIN_SCALE);
            view.setScaleY(MIN_SCALE);
        }
    }
}
