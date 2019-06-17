package Adapters;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jishiben.PlayVideo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.PictureEntity;

public class JiShiBenPictureAdapter extends BaseAdapter {
private List<PictureEntity> datas;
private List<Uri> uris;
private String LeiXing;
private List<String> Paths;
private List<Bitmap> Bitmaps;
	public JiShiBenPictureAdapter(List<PictureEntity> datas, Context context,String LeiXing) {
	super();
	this.datas = datas;
	this.context = context;
	this.LeiXing=LeiXing;
}

	private Context context;
	public int getCount() {
		// TODO Auto-generated method stub
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
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder=null;
		if(view==null){
			holder=new ViewHolder();
			if(LeiXing.equals("Picture")){
			view=LayoutInflater.from(context).inflate(R.layout.jishibenpictureitem, null);
		   holder.Context=(TextView)view.findViewById(R.id.JSBPContent);
		   holder.Time=(TextView)view.findViewById(R.id.JSBPTime);
		   holder.PictureGrid=(GridView)view.findViewById(R.id.JSBPGridView);}
			if(LeiXing.equals("Video")){
				view=LayoutInflater.from(context).inflate(R.layout.jishibenvideoitem, null);
			   holder.Context=(TextView)view.findViewById(R.id.JSBVContent);
			   holder.Time=(TextView)view.findViewById(R.id.JSBVTime);
			   holder.PictureGrid=(GridView)view.findViewById(R.id.JSBVGridView);}
		   view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.Context.setText(datas.get(position).getContent());
		holder.Time.setText(datas.get(position).getTime());
		dealString(datas.get(position).getPath());
		if(LeiXing.equals("Picture")){
		JiShiBenPictureListAdapter adapter=new JiShiBenPictureListAdapter(context, uris,null,LeiXing);
		holder.PictureGrid.setAdapter(adapter);}
		if(LeiXing.equals("Video")){
			dealStringToBitmap();
			JiShiBenPictureListAdapter adapter=new JiShiBenPictureListAdapter(context, null,Bitmaps,LeiXing);
			holder.PictureGrid.setAdapter(adapter);
			holder.PictureGrid.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Intent intent=new Intent(context,PlayVideo.class);
					intent.putExtra("video", Paths.get(position));
					context.startActivity(intent);	
				}
			});
		}
		
		return view;
	}
class ViewHolder{
	TextView Context,Time;
	GridView PictureGrid;
	
}
//处理字符串转换成Uri
public void dealString(String str){
	uris=new ArrayList<Uri>();
	int count=0;
	
	while(str.indexOf(',')!=-1 ){

		String ss=str.substring(count, str.indexOf(','));
		str=str.replace(ss+",","");
		Uri uri=Uri.parse(ss);
		uris.add(uri);
		count=count+1;
		break;
	}
	
}
public void dealStringToBitmap(){
	Bitmaps=new ArrayList<Bitmap>();
	Paths=new ArrayList<String>();
	for(int i=0;i<uris.size();i++){
	String path = getRealPathFromURI(uris.get(i));
		File file = new File(path);
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		mmr.setDataSource(file.getAbsolutePath());
		Bitmap bitmap1 = mmr.getFrameAtTime();// 获得视频第一帧的Bitmap对象
		Bitmap bitmap=ThumbnailUtils.extractThumbnail(bitmap1, 210, 210);
      Paths.add(path);
      Bitmaps.add(bitmap);
}}

public String getRealPathFromURI(Uri contentUri) {
	String res = null;
	String[] proj = { MediaStore.Images.Media.DATA };
	Cursor cursor = context.getContentResolver().query(contentUri, proj, null,
			null, null);
	if (cursor.moveToFirst()) {
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		res = cursor.getString(column_index);
	}
	cursor.close();
	return res;
}
}
