package Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class PictureSelectAdapter extends BaseAdapter implements Runnable{
	 private boolean flag;
private Context context;
private String strDatas[]={"人物类","宠物类","美景类","搞笑类"};
private int ImageDatas[][]={{ R.drawable.imageh, R.drawable.imagep, R.drawable.imager, R.drawable.images, R.drawable.imagev},
		                    {R.drawable.animal1,R.drawable.animal19,R.drawable.animal2,R.drawable.animal8,R.drawable.animal9},
		                    {R.drawable.view10,R.drawable.view2,R.drawable.view3,R.drawable.view4,R.drawable.view5},
		                    { R.drawable.face1,R.drawable.face10, R.drawable.face11,R.drawable.face13, R.drawable.face2}};
private int classImage[]={R.drawable.imageh,R.drawable.animal1,R.drawable.view1,R.drawable.face1};
private BaseAdapter adapter;
private Thread thread=new Thread(this);
private Handler handler;
private AlertDialog dialog;
private ProgressBar progressbar;
	public PictureSelectAdapter(Context context ) {
	super();
	this.context = context;
}

	public int getCount() {
		// TODO Auto-generated method stub
		return strDatas.length;
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
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder viewholder;
		if(view==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.picturegridview, null);
			viewholder.HeadImageLoad1=(TextView)view.findViewById(R.id.HeadImageLoad1);
			viewholder.HeadImageTitle=(TextView)view.findViewById(R.id.HeadImageTitle);
			viewholder.gridView1=(GridView)view.findViewById(R.id.gridView1);
			viewholder.image=(ImageView)view.findViewById(R.id.animal);
			view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		viewholder.image.setImageResource(classImage[position]);
		viewholder.HeadImageTitle.setText(strDatas[position]);
		
		//viewholder.gridView1.setAdapter(adapter);
		viewholder.HeadImageLoad1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SetAdaptet(ImageDatas[position]);
				load(viewholder.HeadImageLoad1,viewholder.gridView1);
			}
		});
		setGridClick(viewholder.gridView1,ImageDatas[position]);
		return view;
	}
class ViewHolder{
	TextView HeadImageTitle,HeadImageLoad1;
	GridView gridView1;
	ImageView image;
}
public void SetAdaptet(final int imaged[]){
	 adapter = new BaseAdapter() {

		// 获取数量
		public int getCount() {
			// TODO Auto-generated method stub
			return imaged.length;
		}

		// 获取当前选项
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		// 获取当前选项id
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageview;
			if (convertView == null) {
				imageview = new ImageView(context);
				imageview.setAdjustViewBounds(true);
				imageview.setMaxWidth(158);
				imageview.setMaxHeight(150);
				imageview.setPadding(5, 5, 5, 5);
			} else {
				imageview = (ImageView) convertView;
			}
			imageview.setImageResource(imaged[position]);
			return imageview;
		}

	};
}
public void setGridClick(GridView grid ,final int imaged[]){
        grid.setOnItemClickListener(new OnItemClickListener() {

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		Intent intent = ((Activity) context).getIntent();
		Bundle bundle = new Bundle();
		bundle.putInt("imaged", imaged[position]);
		intent.putExtras(bundle);
		((Activity) context).setResult(0x11, intent);
		((Activity) context).finish();
	}
});}

@Override
public void run() {
		
		try {
			handler.sendEmptyMessage(0);
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
   handler.sendEmptyMessage(1);
	
}
public void AutoLoad(){
	if(!thread.isAlive()){
		flag=true;
		if(thread.getState()==Thread.State.TERMINATED){
			thread=new Thread(this);
			thread.start();
		}else{
			thread.start();
			
		}
	}else{
		flag=false;
	}
	
}
public void load(final TextView text,final GridView gridview){
AutoLoad();
	handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch(msg.what){
			case 0:
				text.setText("下载中");
				break;
			case 1:
				text.setText("已下载");
				gridview.setAdapter(adapter);
				break;
			}
			
		};
	};
}
/*public void ProgressBar(){
	View view=LayoutInflater.from(context).inflate(R.layout.progressbar, null);
	 progressbar=(ProgressBar)view.findViewById(R.id.progressBar1);
	AlertDialog.Builder builder = new AlertDialog.Builder(
			new ContextThemeWrapper(context, R.style.Alert));
	builder.setView(view);
	 dialog = builder.show();
}*/
}