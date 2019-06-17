package Adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jiacaitong.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import entity.CountEntity;

public class CountDataListViewAdapter extends BaseAdapter {
	 private Map<String,CountEntity> Datas=new HashMap<String, CountEntity>();
	   private Context context;
	   private Map<String,List<Object>> datas;
	   private String str[]={"本月收入","本月支出","全部借款","全部贷款","本月实收","本月实付"};
	   private String str1[]={"本年收入","本年支出","全部借款","全部贷款","本年实收","本年实付"};
	   private int biaozhi;
	   private List<String> ZheXianDataTime;
	   private List<String> time;
		public CountDataListViewAdapter(Map<String,CountEntity> datas,List<String> ZheXianDataTime, Context context,
			int biaozhi,List<String> time) {
		super();
		Datas = datas;
		this.context = context;
		this.biaozhi=biaozhi;
		this.ZheXianDataTime=ZheXianDataTime;
		this.time=time;
	}
		public int getCount() {
			// TODO Auto-generated method stub
			return time.size();
		}

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
		  view = LayoutInflater.from(context).inflate(
					R.layout.countviewpageritem, null);
		   holder.Countpiechart=(PieChart)view.findViewById(R.id.Countpiechart);
		   holder.CountLinechart=(LineChart)view.findViewById(R.id.CountLinechart);
		   holder.NoDataLayout=(LinearLayout)view.findViewById(R.id.TopNoData);
		   holder.TotalCountLayout=(LinearLayout)view.findViewById(R.id.TotalCountLayout);
		   holder.Countpiechart=(PieChart)view.findViewById(R.id.Countpiechart);
		   holder.Countpiechart=(PieChart)view.findViewById(R.id.Countpiechart);
		   holder.Countpiechart=(PieChart)view.findViewById(R.id.Countpiechart);
		   holder.ZheXianLayout=(LinearLayout)view.findViewById(R.id.ZheXianLayout);
			holder.CountListView=(ListView)view.findViewById(R.id.CountListView);
			holder.DataAnalysis=(ListView)view.findViewById(R.id.DataAnalysis);
			holder.Expand=(ImageView)view.findViewById(R.id.Expand);
			holder.noDataText=(TextView)view.findViewById(R.id.CountNoDataText);
			holder.CountTime=(TextView)view.findViewById(R.id.JieZhangCountDataTime);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		AddViewPagerData(holder,position,time.get(position));
		return view;
	}
	class ViewHolder{
		PieChart Countpiechart;
		LineChart CountLinechart;
		LinearLayout NoDataLayout,TotalCountLayout,ZheXianLayout;
		ListView CountListView,DataAnalysis;
		ImageView Expand;
		TextView noDataText,CountTime;
	}
	public void AddViewPagerData(final ViewHolder holder,int position,String time){
			 if(Datas.get(time).getDate()=="0"){
				 holder.NoDataLayout.setVisibility(View.VISIBLE);
				 holder.TotalCountLayout.setVisibility(View.GONE);
				 if(biaozhi==0){
					 holder.noDataText.setText(time+"月无数据");
				 }else{
					 holder.noDataText.setText(time+"年无数据");
				 }
				
			   }else{
				holder. NoDataLayout.setVisibility(View.GONE);
				holder.TotalCountLayout.setVisibility(View.VISIBLE);
				holder.CountTime.setText(time);
			    addMapData(position,time);
				PieData mPieData = getPieData(4, 100); 
				if(mPieData!=null){
				showChart(holder.Countpiechart, mPieData); }
				CountListViewAdapter adapter=new CountListViewAdapter(context, datas,biaozhi);
				holder.CountListView.setAdapter(adapter);
				DataAnalysisAdapter  adapter1=new DataAnalysisAdapter(Datas.get(time), context);
				holder.DataAnalysis.setAdapter(adapter1);
				LineData linedata=getLineData();
				/*if(linedata!=null){
					showChart(holder.CountLinechart, linedata, Color.rgb(114, 188, 223));
				}
*/			showChart(holder.CountLinechart, linedata, Color.rgb(114, 188, 223));
				}
			 holder. Expand.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					if(holder.ZheXianLayout.getVisibility()==View.GONE){
						holder.ZheXianLayout.setVisibility(View.VISIBLE);
						holder.Expand.setImageResource(R.drawable.xiala2);
				
					}else{
						holder.ZheXianLayout.setVisibility(View.GONE);
						holder.Expand.setImageResource(R.drawable.xiala1);
						
					}
					
				}
			});
			
	}
	public void addMapData(int position,String time){
		datas=new HashMap<String, List<Object>>();
		if(biaozhi==0){
		datas.put(str[0], addListData((float)Datas.get(time).getShouRuCount(), String.valueOf(Datas.get(time).getShouRunum()),Datas.get(time).getDate()));
		datas.put(str[1], addListData((float)(Datas.get(time).getZhiChuCount()), String.valueOf(Datas.get(time).getZhiChunum()),Datas.get(time).getDate()));
		datas.put(str[2], addListData((float)(Datas.get(time).getYingShouCount()), String.valueOf(Datas.get(time).getYingShounum()),Datas.get(time).getDate()));
		datas.put(str[3], addListData((float)(Datas.get(time).getYingFuCount()), String.valueOf(Datas.get(time).getYingFunum()),Datas.get(time).getDate()));
		datas.put(str[4], addListData((float)(Datas.get(time).getShiShouCount()), String.valueOf(Datas.get(time).getShiShounum()),Datas.get(time).getDate()));
		datas.put(str[5], addListData((float)(Datas.get(time).getShiFuCount()), String.valueOf(Datas.get(time).getShiFunum()),Datas.get(time).getDate()));
	}
		else{	
			datas.put(str1[0], addListData((float)(Datas.get(time).getShouRuCount()), String.valueOf(Datas.get(time).getShouRunum()),Datas.get(time).getDate()));
			datas.put(str1[1], addListData((float)(Datas.get(time).getZhiChuCount()), String.valueOf(Datas.get(time).getZhiChunum()),Datas.get(time).getDate()));
			datas.put(str1[2], addListData((float)(Datas.get(time).getYingShouCount()), String.valueOf(Datas.get(time).getYingShounum()),Datas.get(time).getDate()));
			datas.put(str1[3], addListData((float)(Datas.get(time).getYingFuCount()), String.valueOf(Datas.get(time).getYingFunum()),Datas.get(time).getDate()));
			datas.put(str1[4], addListData((float)(Datas.get(time).getShiShouCount()), String.valueOf(Datas.get(time).getShiShounum()),Datas.get(time).getDate()));
			datas.put(str1[5], addListData((float)(Datas.get(time).getShiFuCount()), String.valueOf(Datas.get(time).getShiFunum()),Datas.get(time).getDate()));
		}
	}
	public List<Object> addListData(float str1,String str2,String str3){
		List<Object> list=new ArrayList<Object>();
		list.add(str1);
		list.add(str2);
		list.add(str3);
		return list;
	}
//扇形图
	private void showChart(PieChart pieChart, PieData pieData) { 
		pieChart.setHoleColorTransparent(true); 
		pieChart.setHoleRadius(60f); //半径
		pieChart.setTransparentCircleRadius(64f); // 半透明圈
		pieChart.setDescription("总账饼状图"); 
		pieChart.setDrawCenterText(true); //饼状图中间可以添加文字
		pieChart.setDrawHoleEnabled(true); 
		pieChart.setRotationAngle(90); // 初始旋转角度 
		pieChart.setRotationEnabled(true); // 可以手动旋转
		pieChart.setUsePercentValues(true); //显示成百分比
		//设置数据
		pieChart.setData(pieData); 
		Legend mLegend = pieChart.getLegend(); //设置比例图
		mLegend.setPosition(LegendPosition.RIGHT_OF_CHART); //最右边显示
		mLegend.setXEntrySpace(7f); 
		mLegend.setYEntrySpace(5f); 
		pieChart.animateXY(1000, 1000); //设置动画
		} 
		
private PieData getPieData(int count, float range) { 
			ArrayList<String> Titles = new ArrayList<String>();
			ArrayList<Entry> Count = new ArrayList<Entry>();
			if(biaozhi==0){
		for(int i=0;i<6;i++){
			if( (Float)datas.get(str[i]).get(0)!=0.0){
				Titles.add(str[i]);
				Count.add(new Entry((Float) datas.get(str[i]).get(0), i));
				
			}}}else{
				for(int i=0;i<6;i++){
					if( (Float)datas.get(str1[i]).get(0)!=0.0){
					Titles.add(str1[i]);
					Count.add(new Entry((Float) datas.get(str1[i]).get(0), i));
					}}
				}
		//y轴的集合
			if(Count.size()!=0){
		PieDataSet pieDataSet = new PieDataSet(Count, ""/*显示在比例图上*/); 
		pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离
		ArrayList<Integer> colors = new ArrayList<Integer>(); 
		// 饼图颜色
		colors.add(Color.rgb(205, 205, 205)); 
		colors.add(Color.rgb(114, 188, 223)); 
		colors.add(Color.rgb(255, 123, 124)); 
		colors.add(Color.rgb(57, 135, 200)); 
		colors.add(Color.rgb(100, 160, 215));
		colors.add(Color.rgb(150, 100, 280));
		pieDataSet.setColors(colors); 
		
		PieData pieData = new PieData(Titles, pieDataSet); 
		return pieData; 
		} 
			else{	
				return null;
			}
		}
//折线图
private void showChart(LineChart lineChart, LineData lineData, int color) { 
lineChart.setDrawBorders(false);
lineChart.setDescription("");
lineChart.setNoDataTextDescription("You need to provide data for the chart."); 
lineChart.setDrawGridBackground(false); 
lineChart.setGridBackgroundColor(Color.WHITE& 0x70FFFFFF);
lineChart.setTouchEnabled(true); // 设置是否可以触摸
lineChart.setDragEnabled(true);// 是否可以拖拽
lineChart.setScaleEnabled(true);// 是否可以缩放
lineChart.setPinchZoom(false);// 
lineChart.setBackgroundColor(color);// 设置背景
lineChart.setData(lineData); // 设置数据
Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的
mLegend.setForm(LegendForm.CIRCLE);// 样式
mLegend.setFormSize(6f);// 字体
mLegend.setTextColor(Color.WHITE);// 颜色
lineChart.animateX(2500); // 立即执行的动画,x轴
} 
private LineData getLineData() { 
	ArrayList<String> Titles = new ArrayList<String>();
	ArrayList<Entry> Count = new ArrayList<Entry>();//收入数据
	ArrayList<Entry> Count1 = new ArrayList<Entry>();//支出
	ArrayList<Entry> Count2 = new ArrayList<Entry>();//应收
	ArrayList<Entry> Count3 = new ArrayList<Entry>();//应付
	ArrayList<Entry> Count4 = new ArrayList<Entry>();//实收
	ArrayList<Entry> Count5= new ArrayList<Entry>();//实付
for(int i=ZheXianDataTime.size()-1;i>=0;i--){	
		Titles.add(ZheXianDataTime.get(i));
		Count.add(new Entry((float) Datas.get(ZheXianDataTime.get((ZheXianDataTime.size()-1)-i)).getShouRuCount(), i));
		Count1.add(new Entry((float) Datas.get(ZheXianDataTime.get((ZheXianDataTime.size()-1)-i)).getZhiChuCount(), i));
		Count2.add(new Entry((float) Datas.get(ZheXianDataTime.get((ZheXianDataTime.size()-1)-i)).getYingShouCount(), i));
		Count3.add(new Entry((float) Datas.get(ZheXianDataTime.get((ZheXianDataTime.size()-1)-i)).getYingFuCount(), i));
		Count4.add(new Entry((float) Datas.get(ZheXianDataTime.get((ZheXianDataTime.size()-1)-i)).getShiShouCount(), i));
		Count5.add(new Entry((float) Datas.get(ZheXianDataTime.get((ZheXianDataTime.size()-1)-i)).getShiFuCount(), i));
		
	}
ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>(); 
if(Count.size()!=0){
lineDataSets.add(initLineDataSet(Count,Color.WHITE,"收入")); }
if(Count1.size()!=0){
	lineDataSets.add(initLineDataSet(Count1,Color.BLUE,"支出"));
}
if(Count2.size()!=0){
lineDataSets.add(initLineDataSet(Count2,Color.CYAN,"借款")); }
if(Count3.size()!=0){
	lineDataSets.add(initLineDataSet(Count3,Color.GREEN,"贷款"));
}
if(Count4.size()!=0){
lineDataSets.add(initLineDataSet(Count4,Color.RED,"实收")); }
if(Count5.size()!=0){
	lineDataSets.add(initLineDataSet(Count5,Color.YELLOW,"实付"));
}
if(lineDataSets.size()!=0){
LineData lineData = new LineData(Titles, lineDataSets); 
return lineData; 
} else{	
		return null;
	}
}
private LineDataSet initLineDataSet( ArrayList<Entry> Count,int color,String name) {    
		LineDataSet lineDataSet = new LineDataSet(Count,name); 
		lineDataSet.setLineWidth(1.75f); // 线宽
		lineDataSet.setCircleSize(3f);// 显示的圆形大小
		lineDataSet.setColor(color);// 显示颜色
		lineDataSet.setCircleColor(color);// 圆形的颜色
		lineDataSet.setHighLightColor(color); // 高亮的线的颜色
		return lineDataSet;

}

}
