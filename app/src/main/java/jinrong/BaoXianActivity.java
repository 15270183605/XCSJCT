package jinrong;

import java.util.ArrayList;
import java.util.List;

import viewpagerlib.bean.PageBean;
import viewpagerlib.callback.PageHelperListener;
import viewpagerlib.indicator.TextIndicator;
import viewpagerlib.view.BannerViewPager;
import ActivityTool.LoopBean;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class BaoXianActivity extends Activity implements OnClickListener {
	 private static final int[] RESURL = {
         R.drawable.baoxian1,
         R.drawable.baoxian2,
         R.drawable.baoxian3,
        };
  private static final String[] TEXT = {"保险模式1","保险模式2","保险模式3"};
  private BannerViewPager mBannerCountViewPager;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.baoxian);
        //配置数据
        List<LoopBean> loopBeens = new ArrayList<LoopBean>();
        for (int i = 0; i < TEXT.length; i++) {
           LoopBean bean = new LoopBean();
            bean.res = RESURL[i];
            bean.text = TEXT[i];
            loopBeens.add(bean);
        }
        BannerViewPager textBannerViewPager = (BannerViewPager) findViewById(R.id.BaoXianBanner);
        TextIndicator textIndicator = (TextIndicator) findViewById(R.id.BaoXianText);
        PageBean  bean = new PageBean.Builder<LoopBean>()
                .setDataObjects(loopBeens)
                .setIndicator(textIndicator)
                .builder();
        textBannerViewPager.setPageListener(bean, R.layout.loop_layout,new PageHelperListener<LoopBean>() {
            @Override
            public void getItemView(View view, LoopBean data) {
               /* ImageView imageView = (ImageView) view.findViewById(R.id.icon);
                imageView.setImageResource(data.res);*/
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
