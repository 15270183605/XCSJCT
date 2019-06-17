package Adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.jiacaitong.R;

public class JiShiBenPictureSelectAdapter extends BaseAdapter {
      private Context context;
      private List<Bitmap> BtpList;
      private String LeiXing;
	public JiShiBenPictureSelectAdapter(Context context, List<Bitmap> btpList,String LeiXing) {
		super();
		this.context = context;
		BtpList = btpList;
		this.LeiXing=LeiXing;
	}

	public int getCount() {
		
		return BtpList.size();
	}

	public Object getItem(int position) {
		return null;
	}

	
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder=null;
		if(view==null){
			holder=new ViewHolder();
			if(LeiXing.equals("Picture")){
			view=LayoutInflater.from(context).inflate(R.layout.jishibenpicturegridviewitem, null);
			holder.image=(ImageView)view.findViewById(R.id.imageView1);}
			if(LeiXing.equals("Video")){
				view=LayoutInflater.from(context).inflate(R.layout.jishibenvideogridviewitem, null);
				holder.image=(ImageView)view.findViewById(R.id.VideoImageView);}

			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		
		}
		if(LeiXing.equals("Picture")){
        holder.image.setImageBitmap(ThumbnailUtils.extractThumbnail(BtpList.get(position), 220, 220));
		}
		if(LeiXing.equals("Video")){
			 holder.image.setImageBitmap(BtpList.get(position));
		}
		return view;
	}
class ViewHolder{
	ImageView image;
}
}
