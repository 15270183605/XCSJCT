package life;

import life.lifemoney.ChongZhi;
import Adapters.LifeGridAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.example.jiacaitong.R;

public class LifeMoney extends Activity implements OnClickListener {

	private GridView LefeMoneyGridView;
	private int Images[]={R.drawable.chongzhi,R.drawable.shui,R.drawable.dian,R.drawable.qi,R.drawable.wuye,R.drawable.kuandai,R.drawable.dianshi
			};
	  
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lifemoney);
		 LefeMoneyGridView=(GridView)findViewById(R.id.LefeMoneyGridView);
	     LifeGridAdapter adapter=new LifeGridAdapter(this, Images,0);
	     LefeMoneyGridView.setAdapter(adapter);
	     ItemClick();
	}
	public void ItemClick(){
		LefeMoneyGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				if(position==0){
					Intent intent=new Intent(LifeMoney.this,ChongZhi.class);
					startActivity(intent);
				}
				
			}
		});
	}
	public void onClick(View arg0) {
		

	}

}
