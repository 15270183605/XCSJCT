package Adapters;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class ZhongLeiAdapter extends BaseAdapter {
	private String [] Title;
	private Map<String,List<String>> Datas;
	private Context context;
	public ZhongLeiAdapter(String[] title, Map<String, List<String>> datas,
			Context context) {
		super();
		Title = title;
		Datas = datas;
		this.context = context;
	}

	public int getCount() {
		return Title.length;
	}

	@Override
	public Object getItem(int psoition) {
		return getItem(psoition);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		 ViewHolder holder=null;
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.jinrongdailogitem, null);
			holder.ZhongLeiGrid=(GridView)view.findViewById(R.id.ZhongLeiGrid);
			holder.GridText=(TextView)view.findViewById(R.id.ZhongLeiText);
			
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.GridText.setText(Title[position]);
		GridAdapter adapter=new GridAdapter(Datas.get(Title[position]));
		holder.ZhongLeiGrid.setAdapter(adapter);
		/*holder.ZhongLeiGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				Toast.makeText(context, "µã»÷ÁË", 1000).show();
				
			}
		});*/
		return view;
	}
class ViewHolder{
	TextView GridText;
	GridView ZhongLeiGrid;
}
class GridAdapter extends BaseAdapter{
    private List<String> datas;
    
	public GridAdapter(List<String> datas) {
		super();
		this.datas = datas;
	}

	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View view, final ViewGroup parent) {
		ViewHolder holder=null;
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.gridtextitem, null);
			holder.GridText=(TextView)view.findViewById(R.id.GridText);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.GridText.setText(datas.get(position));
		
		return view;
	}
	class ViewHolder{
		TextView GridText;
	}
}
}
