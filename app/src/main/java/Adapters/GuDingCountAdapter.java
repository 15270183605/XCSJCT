package Adapters;

import java.math.BigDecimal;
import java.util.List;

import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.GuDingEntity;

public class GuDingCountAdapter extends BaseAdapter {
private Context context;
private List<GuDingEntity> datas;
private YingShouSQLite yingshousqlite;
private YingFuSQLite yingfusqlite;
private SQLiteDatabase db1,db2;
	public GuDingCountAdapter(Context context, List<GuDingEntity> datas) {
	super();
	this.context = context;
	this.datas = datas;
	yingshousqlite = new YingShouSQLite(context, "yingshou.db", null, 1);
	db1= yingshousqlite.getReadableDatabase();
	yingfusqlite = new YingFuSQLite(context, "yingfu.db", null, 1);
	db2 = yingfusqlite.getReadableDatabase();
}

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
		String classname=datas.get(position).getClassName();
		
		if(classname.equals("固定收入") || classname.equals("固定支出") || classname.equals("固定实收") || classname.equals("固定实付")){
			ViewHolder holder=null;
			if(view==null){
				holder=new ViewHolder();
				view=LayoutInflater.from(context).inflate(R.layout.gudinglistviewitem, null);
				holder.ProjectName=(TextView)view.findViewById(R.id.ProjectName);
				holder.GuClassName=(TextView)view.findViewById(R.id.GuClassName);
				holder.GuCount=(TextView)view.findViewById(R.id.GuCount);
				view.setTag(holder);
			}else{
				holder=(ViewHolder)view.getTag();
			}
			holder.ProjectName.setText(datas.get(position).getProjectName());
			holder.GuClassName.setText(datas.get(position).getClassName());
			holder.GuCount.setText(String.valueOf(datas.get(position).getGuDingCount()));
			}else if(classname.equals("固定借款") || classname.equals("固定贷款")){
				ViewHolder1 holder=null;
				if(view==null){
					holder=new ViewHolder1();
					view=LayoutInflater.from(context).inflate(R.layout.gudinglistviewitem1, null);
					holder.ProjectName1=(TextView)view.findViewById(R.id.ProjectName1);
					holder.GuClassName1=(TextView)view.findViewById(R.id.GuClassName1);
					holder.GuCount1=(TextView)view.findViewById(R.id.GuCount1);
					holder.GuProgress=(ProgressBar)view.findViewById(R.id.GuProgress);
					holder.GuProgressText=(TextView)view.findViewById(R.id.GuProgressText);
					view.setTag(holder);
				}else{
					holder=(ViewHolder1)view.getTag();
				}
				holder.ProjectName1.setText(datas.get(position).getProjectName());
				holder.GuClassName1.setText(datas.get(position).getClassName());
				holder.GuCount1.setText(String.valueOf(datas.get(position).getGuDingCount()));
				double count=0.0;
				if(classname.equals("固定借款")){
				  double count1=yingshousqlite.TotalCount1(db1, datas.get(position).getProjectName(), "1");
				  double count2=yingshousqlite.TotalCount1(db1, datas.get(position).getProjectName(), "0");
					count = new BigDecimal(count1/(count1+count2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//通过四舍五入保留一位小数
				}else{
					double count1=yingfusqlite.TotalCount1(db2, datas.get(position).getProjectName(), "1");
					  double count2=yingfusqlite.TotalCount1(db2, datas.get(position).getProjectName(), "0");
						count = new BigDecimal(count1/(count1+count2)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();//通过四舍五入保留一位小数
				}
				
				holder.GuProgress.setProgress((int)count*100);
				holder.GuProgressText.setText(count*100+"%");
			}
		return view;
	}
class ViewHolder{
	TextView ProjectName,GuClassName,GuCount;
}
class ViewHolder1{
	TextView ProjectName1,GuClassName1,GuCount1,GuProgressText;
	ProgressBar GuProgress;
}
}
