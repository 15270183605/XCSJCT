package Adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v4.view.*;
import com.example.jiacaitong.R;

public class DataDetailViewPagerAdapter<T> extends PagerAdapter {
	  private List<View> Views;
	  private Context context;
	  private String leixing;
	  private List<T> ObjectList;
	  private List<T> list=new ArrayList<T>();
	public DataDetailViewPagerAdapter(Context context,List<View> views, List<T> objectList,String leixing) {
		super();
		Views = views;
		this.ObjectList = objectList;
		this.leixing=leixing;
		this.context=context;
	}

	public int getCount() {
		
		return Views.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
   @Override
public Object instantiateItem(ViewGroup container, int position) {
	   GetData(position);
	   container.addView(Views.get(position));
	return Views.get(position);
}
public void destroyItem(ViewGroup container, int position, Object object) {
	   container.removeView(Views.get(position));
}
public void GetData(int position){
	
	if(!leixing.equals("总帐单")){
	 int count=0;
	   for(int i= position*9;i<ObjectList.size();i++){
		   list.add(ObjectList.get(i));
		   count=count+1;
		   if(count==9){
			   break;
		   }
	   }}else{
		   int count=0;
		   for(int i= position*9;i<ObjectList.size();i++){
			   list.add(ObjectList.get(i));
			   count=count+1;
			   if(count==1){
				   break;
			   }
		   }
	   }
	ListView SheetDatasDetailListView=(ListView)Views.get(position).findViewById(R.id.SheetDatasDetailListView);
 if(leixing.equals("收入单")){
	
	   IncomeSheetAdapter<T>  incomesheetadapter = new IncomeSheetAdapter<T>(context,  list);
		 LinearLayout headView =(LinearLayout) LayoutInflater.from(context).inflate(R.layout.incomepaysheetitem,
					null);
		 SheetDatasDetailListView.addHeaderView(headView);
		 SheetDatasDetailListView.setAdapter(incomesheetadapter);
 }
 if(leixing.equals("支出单")){
	   PaySheetAdapter<T>  paysheetadapter = new PaySheetAdapter<T>(context, list);
	  LinearLayout headView =(LinearLayout) LayoutInflater.from(context).inflate(R.layout.incomepaysheetitem,
					null);
	 SheetDatasDetailListView.addHeaderView(headView);
	SheetDatasDetailListView.setAdapter(paysheetadapter);
	   
 }if(leixing.equals("借款单")|| leixing.equals("实收单")){
	   YingShouSheetAdapter<T>  yingshousheetadapter = new YingShouSheetAdapter<T>(context, list);
	 LinearLayout headView =(LinearLayout) LayoutInflater.from(context).inflate(R.layout.shoufusheetitem,
					null);
	SheetDatasDetailListView.addHeaderView(headView);
	SheetDatasDetailListView.setAdapter(yingshousheetadapter);
 	   
 }if(leixing.equals("贷款单")|| leixing.equals("实付单")){
	   YingFuSheetAdapter<T>  yingfusheetadapter = new YingFuSheetAdapter<T>(context, list);
	   LinearLayout headView =(LinearLayout) LayoutInflater.from(context).inflate(R.layout.shoufusheetitem,
				null);
	   SheetDatasDetailListView.addHeaderView(headView);
	   SheetDatasDetailListView.setAdapter(yingfusheetadapter);
 }if(leixing.equals("总账单")){
	 Toast.makeText(context, String.valueOf(list.size()), 1000).show();
	   JieZhangDataListViewAdapter<T> jiezhangadapter = new JieZhangDataListViewAdapter<T>(context, list);
	   /*LinearLayout headView =(LinearLayout) LayoutInflater.from(context).inflate(R.layout.jiezhangldialoglistviewitem,
				null);
	   SheetDatasDetailListView.addHeaderView(headView);*/
	   SheetDatasDetailListView.setAdapter(jiezhangadapter);
}
 
}
}