package Adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tool.HorizontalListView;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jiacaitong.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import entity.GraphTemplate;

public class GraphShowDataAdapter extends BaseAdapter {
	private Context context;
	private List<String> ClassType = new ArrayList<String>();
	private Map<String, List<GraphTemplate>> datas,SingleDatas;
	private List<String> listStrs = new ArrayList<String>();
	private String spinnerData,selectData;
	//private View view1, view2, view3;
	private ViewHolder viewholder;
	private  String[] selectList={"收入","支出","应收","应付","实收","实付"};
	private List<String> selectlist;
	public GraphShowDataAdapter(Context context,
			Map<String, List<GraphTemplate>> datas,Map<String, List<GraphTemplate>> SingleDatas, List<String> listStrs,
			List<String> ClassType, String spinnerData,String selectData) {
		super();
		this.context = context;
		this.datas = datas;
		this.SingleDatas=SingleDatas;
		this.ClassType = ClassType;
		this.listStrs = listStrs;
		this.spinnerData = spinnerData;
		this.selectData=selectData;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
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
		List<GraphTemplate> datasTemplate = new ArrayList<GraphTemplate>();
		if (view == null) {
			viewholder = new ViewHolder();
			view = LayoutInflater.from(context).inflate(
					R.layout.graphshowdatasitem, null);
			viewholder.container = (LinearLayout) view
					.findViewById(R.id.Graphcontainer);
			viewholder.zhuzhuangradioButton = (RadioButton) view
					.findViewById(R.id.zhuzhuangradioButton);
			viewholder.zhexianradioButton = (RadioButton) view
					.findViewById(R.id.zhexianradioButton);
			viewholder.shanxingradioButton = (RadioButton) view
					.findViewById(R.id.shanxingradioButton);
			viewholder.horizontalListView = (HorizontalListView) view
					.findViewById(R.id.horizontalListView);
			viewholder.bottomText = (TextView) view
					.findViewById(R.id.bottomText);
			viewholder.SingleRadioButton = (RadioButton) view
					.findViewById(R.id.SingleradioButton);
			viewholder.TotalRadioButton = (RadioButton) view
					.findViewById(R.id.TotalradioButton);
			viewholder.SelectSpinner = (Spinner) view
					.findViewById(R.id.SelectSpinner);
			viewholder.GraphLayout = (LinearLayout) view
					.findViewById(R.id.GraphLayout);
			view.setTag(viewholder);
		} else {
			viewholder = (ViewHolder) view.getTag();
		}
		View view1 = LayoutInflater.from(context).inflate(R.layout.zhuzhuanggraph,
				null);
		View view2 = LayoutInflater.from(context).inflate(R.layout.zhexiangraph,
				null);

		View view3 = LayoutInflater.from(context).inflate(R.layout.shanxinggraph,
				null);
		click(viewholder,view1,view2,view3);
		if (spinnerData.equals("按年排") && selectData.equals("总账") ) {
			viewholder.GraphLayout.setVisibility(View.VISIBLE);
			if((viewholder.SingleRadioButton.isChecked())==true){
				
				AddData(SingleDatas, position, selectlist,view1,view2,view3);
			}
			if((viewholder.TotalRadioButton.isChecked())==true){
				AddData(datas, position, listStrs,view1,view2,view3);
			}
		} else {
			viewholder.GraphLayout.setVisibility(View.GONE);
			AddData(datas, position, listStrs,view1,view2,view3);

		}
		
		return view;
	}

	class ViewHolder {
		LinearLayout container;
		RadioButton zhuzhuangradioButton, zhexianradioButton,
				shanxingradioButton, SingleRadioButton, TotalRadioButton;
		HorizontalListView horizontalListView;
		TextView bottomText;
		Spinner SelectSpinner;
		LinearLayout GraphLayout;
	}

	private void showBarChart(BarChart barChart, BarData barData) {
		barChart.setDrawBorders(false); // //是否在柱状图上添加边框
		barChart.setDescription("");// 数据描述
		barChart.setNoDataTextDescription("You need to provide data for the chart.");
		barChart.setDrawGridBackground(false); // 是否显示表格颜色
		barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
		barChart.setTouchEnabled(true); // 设置是否可以触摸
		barChart.setDragEnabled(true);// 是否可以拖拽
		barChart.setScaleEnabled(true);// 是否可以缩放
		barChart.setPinchZoom(false);//
		barChart.setDrawBarShadow(true);
		barChart.setData(barData); // 设置数据
		Legend mLegend = barChart.getLegend(); // 设置比例图标示
		mLegend.setForm(LegendForm.CIRCLE);// 样式
		mLegend.setFormSize(6f);// 字体
		mLegend.setTextColor(Color.BLACK);// 颜色
		barChart.animateX(2500); // 立即执行的动画,x轴
	}

	private BarData getBarData(Map<String, List<GraphTemplate>> data,List<String> list,int count, float range, int position) {
	
		ArrayList<String> Titles = new ArrayList<String>();
		ArrayList<BarEntry> Count = new ArrayList<BarEntry>();
		for (int i = 0; i < data.get(list.get(position)).size()-1; i++) {
			Titles.add(data.get(list.get(position)).get(i).MenuName);
			Count.add(new BarEntry((float) data.get(list.get(position))
					.get(i).getCount(), i));

		}
		// y轴的数据集合
		if (Count.size() != 0) {
			BarDataSet barDataSet = new BarDataSet(Count,
					ClassType.get(position) + "柱状图");
			// barDataSet.setColor(Color.rgb(114, 188, 223));
			barDataSet.setColor(Color.RED);
			ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
			barDataSets.add(barDataSet); // add the datasets
			BarData barData = new BarData(Titles, barDataSets);
			return barData;
		} else {
			return null;
		}
	}

	// 设置显示的样式
	private void showChart(LineChart lineChart, LineData lineData, int color) {
		lineChart.setDrawBorders(false); // 是否在折线图上添加边框
		// no description text
		lineChart.setDescription("");// 数据描述
		lineChart
				.setNoDataTextDescription("You need to provide data for the chart.");
		lineChart.setDrawGridBackground(false); // 是否显示表格颜色
		lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
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
	private LineData getLineData(Map<String, List<GraphTemplate>> data,List<String> list,int count, float range, int position) {
		ArrayList<String> Titles = new ArrayList<String>();
		ArrayList<Entry> Count = new ArrayList<Entry>();
		for (int i = 0; i < data.get(list.get(position)).size()-1; i++) {

			Titles.add(data.get(list.get(position)).get(i).MenuName);
			Count.add(new Entry((float) data.get(list.get(position))
					.get(i).getCount(), i));

		}
		// create a dataset and give it a type
		// y轴的数据集合
		if (Count.size() != 0) {
			LineDataSet lineDataSet = new LineDataSet(Count,
					ClassType.get(position) + "折线图" /* 显示在比例图上 */);
			// mLineDataSet.setFillAlpha(110);
			// mLineDataSet.setFillColor(Color.RED);
			// 用y轴的集合来设置参数
			lineDataSet.setLineWidth(1.75f); // 线宽
			lineDataSet.setCircleSize(3f);// 显示的圆形大小
			lineDataSet.setColor(Color.WHITE);// 显示颜色
			lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色
			lineDataSet.setHighLightColor(Color.WHITE); // 高亮的线的颜色
			ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
			lineDataSets.add(lineDataSet); // add the datasets
			// create a data object with the datasets
			LineData lineData = new LineData(Titles, lineDataSets);
			return lineData;
		} else {
			return null;
		}
	}

	private void showChart(PieChart pieChart, PieData pieData, int position) {
		pieChart.setHoleColorTransparent(true);
		pieChart.setHoleRadius(60f); // 半径
		pieChart.setTransparentCircleRadius(64f); // 半透明圈
		pieChart.setDescription(ClassType.get(position) + "饼状图");
		pieChart.setDrawCenterText(true); // 饼状图中间可以添加文字
		pieChart.setDrawHoleEnabled(true);
		pieChart.setRotationAngle(90); // 初始旋转角度
		pieChart.setRotationEnabled(true); // 可以手动旋转
		pieChart.setUsePercentValues(true); // 显示成百分比
		// 设置数据
		pieChart.setData(pieData);
		Legend mLegend = pieChart.getLegend(); // 设置比例图
		mLegend.setPosition(LegendPosition.RIGHT_OF_CHART); // 最右边显示
		mLegend.setXEntrySpace(7f);
		mLegend.setYEntrySpace(5f);
		pieChart.animateXY(1000, 1000); // 设置动画
	}

	private PieData getPieData(Map<String, List<GraphTemplate>> data,List<String> list,int count, float range, int position) {
		// int j=0;
		ArrayList<String> Titles = new ArrayList<String>();
		ArrayList<Entry> Count = new ArrayList<Entry>();
		/*
		 * Iterator<String> it=datas.keySet().iterator(); while(it.hasNext()){
		 * if(datas.get(it.next()).get(j).getCount()!=0){
		 * Titles.add(datas.get(it.next()).get(j).MenuName); Count.add(new
		 * Entry(datas.get(it.next()).get(j).getCount(), j)); j++; } }
		 * listStrs=Titles;
		 */
		for (int i = 0; i < data.get(list.get(position)).size()-1; i++) {
			Titles.add(data.get(list.get(position)).get(i).MenuName);
			Count.add(new Entry((float) data.get(list.get(position))
					.get(i).getCount(), i));
		}
		// y轴的集合
		if (Count.size() != 0) {
			PieDataSet pieDataSet = new PieDataSet(Count, ""/* 显示在比例图上 */);
			pieDataSet.setSliceSpace(0f); // 设置个饼状图之间的距离
			ArrayList<Integer> colors = new ArrayList<Integer>();
			// 饼图颜色
			colors.add(Color.rgb(205, 205, 205));
			colors.add(Color.rgb(114, 188, 223));
			colors.add(Color.rgb(255, 123, 124));
			colors.add(Color.rgb(57, 135, 200));
			colors.add(Color.rgb(100, 135, 215));
			colors.add(Color.rgb(150, 260, 215));
			pieDataSet.setColors(colors);
			PieData pieData = new PieData(Titles, pieDataSet);
			return pieData;
		} else {
			return null;
		}
	}

	
	//Spinner的响应
		public void spinnerDatas(final View view1,final View view2,final View view3){
			
			final SpinnerAdapter adapter1=new SpinnerAdapter(context,android.R.layout.simple_spinner_item,selectList);
			adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
			viewholder.SelectSpinner.setAdapter(adapter1);
			viewholder.SelectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long arg3) {
					AddData(SingleDatas,position,selectlist,view1,view2,view3);
				}
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});

		}
		public void initSelectlistData(){
			selectlist=new ArrayList<String>();
			for(int i=0;i<selectList.length;i++){
				selectlist.add(selectList[i]);
			}
		}
		public void AddData(Map<String, List<GraphTemplate>> data,int position,List<String> list,View view1,View view2,View view3){
			initSelectlistData();
			
			BarChart barchart = (BarChart) view1.findViewById(R.id.barchart);
			BarData mBarData = getBarData(data,list,10, 100, position);
			if (mBarData != null) {
				showBarChart(barchart, mBarData);
			}

			
			LineChart lineChart = (LineChart) view2.findViewById(R.id.linechart);
			LineData mLineData = getLineData(data,list,36, 100, position);
			if (mLineData != null) {
				showChart(lineChart, mLineData, Color.rgb(114, 188, 223));
			}
			PieChart pieChart = (PieChart) view3.findViewById(R.id.piechart);
			PieData mPieData = getPieData(data,list,4, 100, position);
			if (mPieData != null) {
				showChart(pieChart, mPieData, position);
			}
			
			viewholder.container.removeAllViews();// 注：这条语句一定要写在container添加view之前，目的是清除container中存在的view;
			viewholder.container.addView(view1);
			viewholder.zhuzhuangradioButton.setChecked(true);
			HorizontalListViewAdapter adapter = new HorizontalListViewAdapter(
					context, data.get(list.get(position)));
			viewholder.horizontalListView.setAdapter(adapter);
			viewholder.bottomText.setText(list.get(position));
		}
		public void click(final ViewHolder holder,final View view1,final View view2,final View view3){
			holder.zhexianradioButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					holder.container.removeAllViews();
					holder.container.addView(view2);
					
				}
			});
			holder.zhuzhuangradioButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					holder.container.removeAllViews();
					holder.container.addView(view1);
					
				}
			});
			holder.shanxingradioButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					holder.container.removeAllViews();
					holder.container.addView(view3);
					
				}
			});
			holder.SingleRadioButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					spinnerDatas(view1,view2,view3);
					holder.SelectSpinner.setVisibility(View.VISIBLE);
					
				}
			});
			holder.TotalRadioButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					holder.SelectSpinner.setVisibility(View.GONE);
					
				}
			});
		}
}
