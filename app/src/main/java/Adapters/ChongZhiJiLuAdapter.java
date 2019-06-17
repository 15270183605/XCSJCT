package Adapters;

import java.util.List;

import com.example.jiacaitong.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import entity.ChongZhiEntity;

public class ChongZhiJiLuAdapter extends BaseAdapter {
private Context context;
private List<ChongZhiEntity> datas;

	public ChongZhiJiLuAdapter(Context context, List<ChongZhiEntity> datas) {
	super();
	this.context = context;
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
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder=null;
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.chongzhijiluitem, null);
		  holder.ChongZhiJiLuImage=(ImageView)view.findViewById(R.id.ChongZhiJiLuImage);
		  holder.ChongZhiNumText=(TextView)view.findViewById(R.id.ChongZhiNumText);
		  holder.ChongZhiText=(TextView)view.findViewById(R.id.ChongZhiText);
		  holder.ChongZhiTimeText=(TextView)view.findViewById(R.id.ChongZhiTimeText);
		  holder.ChongZhiWayText=(TextView)view.findViewById(R.id.ChongZhiWayText);
		  view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.ChongZhiJiLuImage.setImageBitmap(getBmp(datas.get(position).getImage()));
		holder.ChongZhiNumText.setText("￥"+datas.get(position).getChongZhiNum()+"元");
		holder.ChongZhiText.setText(datas.get(position).getChongZhiContent());
		holder.ChongZhiTimeText.setText(datas.get(position).getChongZhiDate());
		holder.ChongZhiWayText.setText(datas.get(position).getChongZhiWay());
		return view;
	}
class ViewHolder{
	TextView ChongZhiText,ChongZhiWayText,ChongZhiNumText,ChongZhiTimeText;
	ImageView ChongZhiJiLuImage;
}
//将二进制图片转换成bitmap
public Bitmap getBmp(byte[] in) 
{
  Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
  return bmpout;
}
}
