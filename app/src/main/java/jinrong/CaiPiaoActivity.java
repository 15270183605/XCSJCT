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

public class CaiPiaoActivity extends Activity implements OnClickListener {


	 private static final Integer[] RESURL = {R.drawable.caipiao1,R.drawable.caipiao2,R.drawable.caipiao3,
    };

private static final String[] TEXT = {"��Ʊģʽ1","��Ʊģʽ2","��Ʊģʽ3"};
private BannerViewPager mBannerCountViewPager;
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caipiao);
		 mBannerCountViewPager = (BannerViewPager) findViewById(R.id.CaiPiaoBanner);
	        ZoomIndicator zoomIndicator = (ZoomIndicator) findViewById(R.id.CaiPiaoZoom);
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
