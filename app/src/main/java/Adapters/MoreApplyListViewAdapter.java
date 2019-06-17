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

public class MoreApplyListViewAdapter extends BaseAdapter {
   private Context context;
   private String str[];
   private String str1[];
   private int images[];
   private MemoryDeal memorydeal;
	public MoreApplyListViewAdapter(Context context, String[] str, String[] str1,
		int[] images) {
	super();
	this.context = context;
	this.str = str;
	this.str1 = str1;
	this.images = images;
	memorydeal=new MemoryDeal();
}

	public int getCount() {
		
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
		if(view==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.moreapplylistviewitem, null);
			viewholder.Note=(ImageView)view.findViewById(R.id.Note);
			viewholder.Text1=(TextView)view.findViewById(R.id.Text1);
			viewholder.Text2=(TextView)view.findViewById(R.id.Text2);
			view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder) view.getTag();
		}
		viewholder.Note.setImageBitmap(memorydeal.getBitmap(images[position], context));
		viewholder.Text1.setText(str[position]);
		viewholder.Text2.setText(str1[position]);
		return view;
	}
class ViewHolder{
	ImageView Note;
	TextView Text1,Text2;
}

}
