package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.jiacaitong.R;

public class LifeGridAdapter extends BaseAdapter {

	private Context context;
	private int Images[];
	private int  biaozhi;
	public LifeGridAdapter(Context context, int[] images,int biaozhi) {
		super();
		this.context = context;
		Images = images;
		this.biaozhi=biaozhi;
	}

	public int getCount() {
		
		return Images.length;
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
	public View getView(int position, View view, ViewGroup parent) {
	ViewHolder holder=null;
	if(biaozhi==0){
	if(view==null){
		holder=new ViewHolder();
		view=LayoutInflater.from(context).inflate(R.layout.imageitem, null);
		holder.image=(ImageView)view.findViewById(R.id.ImageItem);
		view.setTag(holder);
	}else{
		holder=(ViewHolder)view.getTag();
	}
	holder.image.setImageResource(Images[position]);}
	else if(biaozhi==1){
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.imageitem1, null);
			holder.image=(ImageView)view.findViewById(R.id.ImageItem1);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.image.setImageResource(Images[position]);
	}else if(biaozhi==2){
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.chongzhigriditem, null);
			holder.image=(ImageView)view.findViewById(R.id.ChongZhiImage);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.image.setImageResource(Images[position]);
	}
		return view;
	}
 class ViewHolder{
	 ImageView image;
 }
}
