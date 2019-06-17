package life;

import Adapters.LifeGridAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;

import com.example.jiacaitong.R;

public class WuXian extends Activity implements OnClickListener {

	private GridView WuXianGridView;
	 private int Images1[]={R.drawable.xian1,R.drawable.xian2,R.drawable.xian3,R.drawable.xian4,R.drawable.xian5,R.drawable.xian6};
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wuxian);
	     WuXianGridView=(GridView)findViewById(R.id.WuXianGridView);
	     LifeGridAdapter adapter1=new LifeGridAdapter(this, Images1,1);
	     WuXianGridView.setAdapter(adapter1);
	     
	}
	public void onClick(View arg0) {
		

	}

}
