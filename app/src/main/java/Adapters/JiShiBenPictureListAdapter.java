package Adapters;

import java.io.FileNotFoundException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.jiacaitong.R;

public class JiShiBenPictureListAdapter extends BaseAdapter {
	private Context context;
	private List<Uri> BtpList;
   private String LeiXing;
   private List<Bitmap> Bitmaps;
	public JiShiBenPictureListAdapter(Context context, List<Uri> btpList,List<Bitmap> Bitmaps,String LeiXing) {
		super();
		this.context = context;
		BtpList = btpList;
		this.LeiXing=LeiXing;
		this.Bitmaps=Bitmaps;
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
		ViewHolder holder = null;
		if(LeiXing.equals("Picture")){
			if (view == null) {
				holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.jishibenpicturelistgridviewitem, null);
			holder.image = (ImageView) view.findViewById(R.id.JSBPicture);	
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();

		}
		holder.image.setImageBitmap(returnBitmap(BtpList.get(position)));}
	if(LeiXing.equals("Video")){
		if(view==null){
			holder=new ViewHolder();
		view = LayoutInflater.from(context).inflate(
				R.layout.jishibenvideogridviewitem, null);
		holder.image = (ImageView) view.findViewById(R.id.VideoImageView);
		view.setTag(holder);
		}else{
			holder = (ViewHolder) view.getTag();
		}
		holder.image.setImageBitmap(Bitmaps.get(position));}
		return view;
	}

	class ViewHolder {
		ImageView image;
	}

	public Bitmap returnBitmap(Uri uri) {
		// 将拍摄的照片显示出来
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver()
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return ThumbnailUtils.extractThumbnail(bitmap, 220, 220);
		
	}

	
}
