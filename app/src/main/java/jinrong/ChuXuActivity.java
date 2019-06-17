package jinrong;

import java.util.ArrayList;
import java.util.List;

import viewpagerlib.anim.MzTransformer;
import viewpagerlib.bean.PageBean;
import viewpagerlib.callback.PageHelperListener;
import viewpagerlib.indicator.ZoomIndicator;
import viewpagerlib.view.ArcImageView;
import viewpagerlib.view.BannerViewPager;
import ActivityTool.LoopBean;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.jiacaitong.R;

public class ChuXuActivity extends Activity implements OnClickListener {


	 private static final Integer[] RESURL = {R.drawable.chuxu1,R.drawable.chuxu2,R.drawable.chuxu3,
        R.drawable.chuxu4 };

private static final String[] TEXT = {"储蓄模式1","储蓄模式2","储蓄模式3","储蓄模式4"};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chuxu);
		 List<LoopBean> loopBeens = new ArrayList<LoopBean>();
	        for (int i = 0; i < TEXT.length; i++) {
	           LoopBean bean = new LoopBean();
	            bean.res = RESURL[i];
	            bean.text = TEXT[i];
	            loopBeens.add(bean);

	        }
        BannerViewPager arcBannerViewPager = (BannerViewPager) findViewById(R.id.ChuXuBanner);
        ZoomIndicator arcZoomIndicator = (ZoomIndicator) findViewById(R.id.ChuXuZoom);
        arcBannerViewPager.setPageTransformer(false,new MzTransformer());
        PageBean arcbean = new PageBean.Builder<LoopBean>()
                .setDataObjects(loopBeens)
                .setIndicator(arcZoomIndicator)
                .builder();
        arcBannerViewPager.setPageListener(arcbean, R.layout.arc_loop_layout, new PageHelperListener<LoopBean>() {
            @Override
            public void getItemView(View view, LoopBean data) {
                ArcImageView imageView = (ArcImageView) view.findViewById(R.id.arc_icon);
                imageView.setImageResource(data.res);
            }
        });


	}
	public void onClick(View arg0) {
		

	}

}
