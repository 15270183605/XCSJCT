package jinrong;

import java.util.ArrayList;
import java.util.List;

import viewpagerlib.bean.PageBean;
import viewpagerlib.callback.PageHelperListener;
import viewpagerlib.indicator.TransIndicator;
import viewpagerlib.view.BannerViewPager;
import ActivityTool.LoopBean;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

public class GuPiaoActivity extends Activity implements OnClickListener {


	 private static final Integer[] RESURL = {R.drawable.gupiao1,R.drawable.gupiao3,R.drawable.gupiao4,
        };

private static final String[] TEXT = {"��Ʊģʽ1","��Ʊģʽ2","��Ʊģʽ3"};
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gupiao);
		List loopBeens = new ArrayList<LoopBean>();
	        for (int i = 0; i <TEXT.length; i++) {
	            LoopBean bean2 = new LoopBean();
	            bean2.res = RESURL[i];
	            bean2.text = TEXT[i];
	            loopBeens.add(bean2);

	        }
	        BannerViewPager transBannerViewPager = (BannerViewPager) findViewById(R.id.GuPiaoBanner);
	        TransIndicator transIndicator = (TransIndicator) findViewById(R.id.GuPiaoTran);
	        //����pagerbean��������Ҫ��Ϊ��viewpager��ָʾ�������ã�ע��ǵ�д�Ϸ���
	      PageBean  bean = new PageBean.Builder<LoopBean>()
	                .setDataObjects(loopBeens)
	                .setIndicator(transIndicator)
	                .builder();

	        transBannerViewPager.setPageListener(bean, R.layout.loop_layout, new PageHelperListener<LoopBean>() {
	            @Override
	            public void getItemView(View view,final LoopBean bean) {
	                ImageView imageView = (ImageView) view.findViewById(R.id.loop_icon);
	                imageView.setImageResource(bean.res);
	                TextView textView = (TextView) view.findViewById(R.id.loop_text);
	                textView.setText(bean.text);
	                view.setOnClickListener(new View.OnClickListener() {
	                    @Override
	                    public void onClick(View view) {
	                        Toast.makeText(GuPiaoActivity.this, bean.text, Toast.LENGTH_SHORT).show();
	                    }
	                });

	                //������Ҫ���õ���¼���Ҳ����ֱ��ͨ�����view �����ã�����ͼƬ�ĸ��µȵ�
	            }
	        });

	}
	public void onClick(View arg0) {
		

	}

}
