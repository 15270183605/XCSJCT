package Adapters;

import java.util.List;

import Dialog.MyDialogSheetDataDetail;
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

import entity.SheetTemplate;

public class SheetShowDataAdapter extends BaseAdapter {
   private Context context;
   private List<SheetTemplate> datas;
   private MyDialogSheetDataDetail dialog;
	public SheetShowDataAdapter(Context context, List<SheetTemplate> datas) {
	super();
	this.context = context;
	this.datas = datas;
}

	public int getCount() {
		   int ret = 0; 
		    if(datas!=null){ 
		      ret = datas.size(); 
		    } 
		    return ret; 
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		SheetTemplate template=(SheetTemplate)this.getItem(position);
		ViewHolder viewholder;
		if(view==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.sheettitleitem, null);
			viewholder.classname=(TextView)view.findViewById(R.id.className);
			viewholder.count=(TextView)view.findViewById(R.id.Count);
			viewholder.operation=(TextView)view.findViewById(R.id.Operation);
			viewholder.maketime=(TextView)view.findViewById(R.id.makeTime);
			viewholder.way=(TextView)view.findViewById(R.id.Way);
			view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		viewholder.classname.setText(template.getClassName());
		viewholder.way.setText(template.getClassSource());
		viewholder.count.setText(String.valueOf(template.getCount()));
		viewholder.maketime.setText(template.getTime());
		viewholder.operation.setText(template.getOperation());
		viewholder.operation.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		viewholder.operation.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				ShowDialog(position);
			}
		});
		return view;
	}
   class ViewHolder{
	   TextView classname,way,count,maketime,operation;
   }
   public void ShowDialog(int position){
	      dialog=new MyDialogSheetDataDetail(context,datas.get(position));
		  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	       dialog.show();
	       dialog.setCancelable(true);
   }
}
