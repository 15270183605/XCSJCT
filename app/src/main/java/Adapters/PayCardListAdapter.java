package Adapters;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.PayCard;

public class PayCardListAdapter extends BaseAdapter {
	private Context context;
	private List<PayCard> Datas;

	public PayCardListAdapter(Context context, List<PayCard> datas) {
		super();
		this.context = context;
		Datas = datas;
	}

	public int getCount() {

		return Datas.size();
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
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.paylistviewitem, null);
			holder.CardImage = (ImageView) view.findViewById(R.id.YinHangImage);
			holder.CardName = (TextView) view.findViewById(R.id.YinHangText);
			holder.PayListLayout = (LinearLayout) view
					.findViewById(R.id.PayListLayout);
			holder.PaySelect = (ImageView) view.findViewById(R.id.PaySelect);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.CardImage.setImageBitmap(getBmp(Datas.get(position)
				.getCardImage()));
		holder.CardName.setText(Datas.get(position).getCardName());
		
		return view;
	}

	class ViewHolder {
		ImageView CardImage, PaySelect;
		TextView CardName;
		LinearLayout PayListLayout;
	}

	// 将二进制图片转换成bitmap
	public Bitmap getBmp(byte[] in) {
		Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
		return bmpout;
	}
}
