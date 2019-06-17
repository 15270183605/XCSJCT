package Adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.jiacaitong.R;

public class ADListViewAdapter extends BaseAdapter {
	private Context context;
	private int ADImages[]={R.drawable.adpicture1,R.drawable.adpicture2,R.drawable.adpicture3,R.drawable.adpicture4,R.drawable.adpicture5,R.drawable.adpicture6,R.drawable.adpicture7,R.drawable.adpicture8};
	private int count;
	private List<Integer> images;
	public ADListViewAdapter(Context context,int count) {
		super();
		this.context = context;
		this.count=count;
		images=new ArrayList<Integer>();
		for(int i=count;i<count+2;i++){
			images.add(ADImages[i]);
		}
	}

	public int getCount() {
		
		return ADImages.length;
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
	public View getView(int position, View view, ViewGroup parnet) {
		ViewHolder viewholder;
		if(view==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.adlistviewitem, null);
			viewholder.ADImage=(ImageView)view.findViewById(R.id.ADImageView);
			view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		
		viewholder.ADImage.setImageResource(images.get(position));
		return view;
	}
class ViewHolder{
	ImageView ADImage;
}
}
