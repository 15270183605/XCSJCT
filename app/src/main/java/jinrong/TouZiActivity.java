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

 private static final String[] TEXT = {"投资模式1","投资模式2","投资模式3","投资模式4"};
 private BannerViewPager mBannerCountViewPager;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touzi);
		 mBannerCountViewPager = (BannerViewPager) findViewById(R.id.TouZiBanner);
	        ZoomIndicator zoomIndicator = (ZoomIndicator) findViewById(R.id.TouZiZoom);
	        //配置数据
	        List<LoopBean> loopBeens = new ArrayList<LoopBean>();
	        for (int i = 0; i < TEXT.length; i++) {
	           LoopBean bean = new LoopBean();
	            bean.res = RESURL[i];
	            bean.text = TEXT[i];
	            loopBeens.add(bean);

	        }
	        //配置pagerbean，这里主要是为了viewpager的指示器的作用，注意记得写上泛型
	        PageBean bean = new PageBean.Builder<LoopBean>()
	                .setDataObjects(loopBeens)
	                .setIndicator(zoomIndicator)
	                .builder();
	        // 设置viewpager的动画，这里提供了三种，分别是MzTransformer，ZoomOutPageTransformer,
	        // 和DepthPageTransformer，可以体验一下
	        mBannerCountViewPager.setPageTransformer(false,new MzTransformer());
	        //
	        mBannerCountViewPager.setPageListener(bean, R.layout.loop_layout, new PageHelperListener<LoopBean>() {
	            @Override
	            public void getItemView(View view, LoopBean data) {
	                ImageView imageView = (ImageView) view.findViewById(R.id.loop_icon);
	                imageView.setImageResource(data.res);
	                TextView textView = (TextView) view.findViewById(R.id.loop_text);
	                textView.setText(data.text);

	                //如若你要设置点击事件，也可以直接通过这个view 来设置，或者图片的更新等等
	            }
	        });

	}
	public void onClick(View arg0) {
		

	}

}
