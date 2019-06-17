package mine;

import Adapters.PictureSelectAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.example.jiacaitong.R;

public class PictureList extends Activity{
	public int[] imaged = new int[] { R.drawable.imageh,
		   R.drawable.imagep, R.drawable.imager,
			R.drawable.images, R.drawable.imagev, R.drawable.imagex };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pictureselect);
		ListView picturelistview = (ListView)findViewById(R.id.pictureselectListView);
		PictureSelectAdapter adapter=new PictureSelectAdapter(this);
		picturelistview.setAdapter(adapter);
		/*BaseAdapter adapter = new BaseAdapter() {

			// 获取数量
			public int getCount() {
				// TODO Auto-generated method stub
				return imaged.length;
			}

			// 获取当前选项
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			// 获取当前选项id
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				ImageView imageview;
				if (convertView == null) {
					imageview = new ImageView(PictureList.this);
					imageview.setAdjustViewBounds(true);
					imageview.setMaxWidth(158);
					imageview.setMaxHeight(150);
					imageview.setPadding(5, 5, 5, 5);
				} else {
					imageview = (ImageView) convertView;
				}
				imageview.setImageResource(imaged[position]);
				return imageview;
			}

		};
		grid.setAdapter(adapter);
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = getIntent();
				Bundle bundle = new Bundle();
				bundle.putInt("imaged", imaged[position]);
				intent.putExtras(bundle);
				setResult(0x11, intent);
				finish();
			}
		});*/
	}
	
}
