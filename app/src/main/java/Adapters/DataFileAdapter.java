package Adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.DataFile;

public class DataFileAdapter extends BaseAdapter {
    private Context context;
    private List<DataFile> datas;
	public DataFileAdapter(Context context, List<DataFile> datas) {
		super();
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
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
		ViewHolder holder=null;
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.daochudataitem, null);
			holder.FileName=(TextView)view.findViewById(R.id.FileName);
			holder.FileTime=(TextView)view.findViewById(R.id.FileTime);
			holder.FilePath=(TextView)view.findViewById(R.id.FileAddress);
			holder.ClassName=(TextView)view.findViewById(R.id.ClassName);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		holder.FileName.setText(datas.get(position).getFileName());
		holder.FilePath.setText(datas.get(position).getFilePath());
		holder.FileTime.setText(datas.get(position).getDateTime());
		holder.ClassName.setText(datas.get(position).getFileLeiXing());
		return view;
	}
class ViewHolder{
	TextView FileName,FileTime,FilePath,ClassName;
}
}
