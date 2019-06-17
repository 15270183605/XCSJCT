package Adapters;

import java.util.List;
import java.util.Map;

import Dialog.CountDetailDialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class CountListViewAdapter extends BaseAdapter {
     private Context context;
     private Map<String,List<Object>> Datas;
     private String str[]={"��������","����֧��","ȫ�����","ȫ������","����ʵ��","����ʵ��"};
     private String str1[]={"��������","����֧��","ȫ�����","ȫ������","����ʵ��","����ʵ��"};
     private CountDetailDialog dialog;
     private int biaozhi;
	public CountListViewAdapter(Context context, Map<String, List<Object>> datas,int biaozhi) {
		super();
		this.context = context;
		Datas = datas;
		this.biaozhi=biaozhi;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return str.length;
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
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder viewholder;
		if(view==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.countlistviewitem,null);
			viewholder.CountClassText=(TextView)view.findViewById(R.id.CountClassText);
			viewholder.CountNumText=(TextView)view.findViewById(R.id.CountNumText);
			viewholder.CountcountText=(TextView)view.findViewById(R.id.CountcountText);
			viewholder.CountDetailText=(TextView)view.findViewById(R.id.CountDetailText);
		view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		viewholder.CountClassText.setText(str[position]);
		viewholder.CountNumText.setText(String.valueOf(Datas.get(str[position]).get(0)));
		viewholder.CountcountText.setText(String.valueOf(Datas.get(str[position]).get(1)));
		viewholder.CountDetailText.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//����»���
		if(String.valueOf(Datas.get(str[position]).get(0))=="0"){
			viewholder.CountDetailText.setVisibility(View.GONE);
		}
		viewholder.CountDetailText.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				if(biaozhi==0){
			if(position==0){
				ShowDialog("���뵥",(String) Datas.get(str[position]).get(2));
			}if(position==1){
				ShowDialog("֧����",(String) Datas.get(str[position]).get(2));
			}if(position==2){
				ShowDialog("��",(String) Datas.get(str[position]).get(2));
			}if(position==3){
				ShowDialog("���",(String) Datas.get(str[position]).get(2));
			}if(position==4){
				ShowDialog("ʵ�յ�",(String) Datas.get(str[position]).get(2));
			}if(position==5){
				ShowDialog("ʵ����",(String) Datas.get(str[position]).get(2));
			}
				
			}
				else{
					if(position==0){
						ShowDialog("���뵥",(String) Datas.get(str1[position]).get(2));
					}if(position==1){
						ShowDialog("֧����",(String) Datas.get(str1[position]).get(2));
					}if(position==2){
						ShowDialog("��",(String) Datas.get(str1[position]).get(2));
					}if(position==3){
						ShowDialog("���",(String) Datas.get(str1[position]).get(2));
					}if(position==4){
						ShowDialog("ʵ�յ�",(String) Datas.get(str1[position]).get(2));
					}if(position==5){
						ShowDialog("ʵ����",(String) Datas.get(str1[position]).get(2));
					}
						
				}}
			
		});
		return view;
	}
 class ViewHolder{
	 TextView CountClassText;
	 TextView CountNumText;
		TextView CountcountText;
		TextView CountDetailText;
}
 public void ShowDialog(String str,String time){
     dialog=new CountDetailDialog(context,str,time);
	  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
      dialog.show();
      dialog.setCancelable(true);
}
}
