package jinrong;

import java.util.ArrayList;
import java.util.List;

import viewpagerlib.anim.MzTransformer;
import viewpagerlib.bean.PageBean;
import viewpagerlib.callback.PageHelperListener;
import viewpagerlib.indicator.ZoomIndicator;
import viewpagerlib.view.BannerViewPager;
import ActivityTool.LoopBean;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class TouZiActivity extends Activity implements OnClickListener {

	 private static final Integer[] RESURL = {R.drawable.touzi1,R.drawable.touzi2,R.drawable.touzi3,
         R.drawable.touzi4 };

 private static final String[] TEXT = {"Ͷ��ģʽ1","Ͷ��ģʽ2","Ͷ��ģʽ3","Ͷ��ģʽ4"};
 private BannerViewPager mBannerCountViewPager;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touzi);
		 mBannerCountViewPager = (BannerViewPager) findViewById(R.id.TouZiBanner);
	        ZoomIndicator zoomIndicator = (ZoomIndicator) findViewById(R.id.TouZiZoom);
	        //��������
	        List<LoopBean> loopBeens = new ArrayList<LoopBean>();
	        for (int i = 0; i < TEXT.length; i++) {
	           LoopBean bean = new LoopBean();
	            bean.res = RESURL[i];
	            bean.text = TEXT[i];
	            loopBeens.add(bean);

	        }
	        //����pagerbean��������Ҫ��Ϊ��viewpager��ָʾ�������ã�ע��ǵ�д�Ϸ���
	        PageBean bean = new PageBean.Builder<LoopBean>()
	                .setDataObjects(loopBeens)
	                .setIndicator(zoomIndicator)
	                .builder();
	        // ����viewpager�Ķ����������ṩ�����֣��ֱ���MzTransformer��ZoomOutPageTransformer,
	        // ��DepthPageTransformer����������һ��
	        mBannerCountViewPager.setPageTransformer(false,new MzTransformer());
	        //
	        mBannerCountViewPager.setPageListener(bean, R.layout.loop_layout, new PageHelperListener<LoopBean>() {
	            @Override
	            public void getItemView(View view, LoopBean data) {
	                ImageView imageView = (ImageView) view.findViewById(R.id.loop_icon);
	                imageView.setImageResource(data.res);
	                TextView textView = (TextView) view.findViewById(R.id.loop_text);
	                textView.setText(data.text);

	                //������Ҫ���õ���¼���Ҳ����ֱ��ͨ�����view �����ã�����ͼƬ�ĸ��µȵ�
	            }
	        });

	}
	public void onClick(View arg0) {
		

	}

}
