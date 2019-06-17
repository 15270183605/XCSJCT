package Adapters;

import tool.MemoryDeal;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class GridViewAdapter extends BaseAdapter {
private Context context;
private int images[];
private String str[];
private MemoryDeal memorydeal;
private int biaozhi;
public GridViewAdapter(Context context, int[] images, String[] str,int biaozhi) {
	super();
	this.context = context;
	this.images = images;
	this.str = str;
	memorydeal=new MemoryDeal();
	this.biaozhi=biaozhi;
}

	public int getCount() {
		// TODO Auto-generated method stub
		return images.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewholder;
		if(biaozhi==0){
		if(view==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.gridviewitem, null);
			viewholder.text=(TextView)view.findViewById(R.id.GridViewText);
			viewholder.image=(ImageView)view.findViewById(R.id.GridViewImage);
			view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		viewholder.text.setText(str[position]);
		viewholder.image.setImageBitmap(memorydeal.getBitmap(images[position], context));}
		else if(biaozhi==1){
			if(view==null){
				viewholder=new ViewHolder();
				view=LayoutInflater.from(context).inflate(R.layout.morechongzhi, null);
				viewholder.text=(TextView)view.findViewById(R.id.morechongtext);
				viewholder.image=(ImageView)view.findViewById(R.id.morechongimage);
				view.setTag(viewholder);
			}else{
				viewholder=(ViewHolder)view.getTag();
			}
			viewholder.text.setText(str[position]);
			viewholder.image.setImageBitmap(memorydeal.getBitmap(images[position], context));
		}
		return view;
	}
class ViewHolder{
	TextView text;
	ImageView image;
}


}
