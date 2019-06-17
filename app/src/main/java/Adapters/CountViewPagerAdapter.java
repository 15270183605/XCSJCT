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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

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
import android.support.v4.view.*;
import entity.CountEntity;

public class CountViewPagerAdapter extends PagerAdapter {
   private List<CountEntity> Datas=new ArrayList<CountEntity>();
   private Context context;
   private List<View> Views;
   private Map<String,List<Object>> datas;
   private String str[]={"��������","����֧��","ȫ�����","ȫ������","����ʵ��","����ʵ��"};
   private String str1[]={"��������","����֧��","ȫ�����","ȫ������","����ʵ��","����ʵ��"};
   private int biaozhi;
   private List<String> ZheXianDataTime;
	public CountViewPagerAdapter(List<CountEntity> datas,List<String> ZheXianDataTime, Context context,
		List<View> views,int biaozhi) {
	super();
	Datas = datas;
	this.context = context;
	Views = views;
	this.biaozhi=biaozhi;
	this.ZheXianDataTime=ZheXianDataTime;
}

	public int getCount() {
		// TODO Auto-generated method stub
		return Datas.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		
		return arg0==arg1;
	}
	public Object instantiateItem(ViewGroup container,int position){
		AddViewPagerData(position);
		 container.addView(Views.get(position));
		return Views.get(position);}
	public void destroyItem(ViewGroup container,int position,Object object){
		//container.removeAllViews();
		  container.removeView(Views.get(position));
	}
	public void AddViewPagerData(int position){
		PieChart Countpiechart=(PieChart)Views.get(position).findViewById(R.id.Countpiechart);
		LineChart CountLinechart=(LineChart)Views.get(position).findViewById(R.id.CountLinechart);	
		LinearLayout NoDataLayout=(LinearLayout)Views.get(position).findViewById(R.id.TopNoData);
			LinearLayout TotalCountLayout=(LinearLayout)Views.get(position).findViewById(R.id.TotalCountLayout);
			final LinearLayout ZheXianLayout=(LinearLayout)Views.get(position).findViewById(R.id.ZheXianLayout);
			ListView CountListView=(ListView)Views.get(position).findViewById(R.id.CountListView);
			ListView DataAnalysis=(ListView)Views.get(position).findViewById(R.id.DataAnalysis);
			final ImageView Expand=(ImageView)Views.get(position).findViewById(R.id.Expand);
			View view=LayoutInflater.from(context).inflate(R.layout.countlistviewhead, null);
			CountListView.addHeaderView(view);
			 if(Datas.get(position).getDate()=="0"){
				  NoDataLayout.setVisibility(View.VISIBLE);
				  TotalCountLayout.setVisibility(View.GONE);
			   }else{
				   NoDataLayout.setVisibility(View.GONE);
				   TotalCountLayout.setVisibility(View.VISIBLE);
			    addMapData(position);
				PieData mPieData = getPieData(4, 100); 
				if(mPieData!=null){
				showChart(Countpiechart, mPieData); }
				CountListViewAdapter adapter=new CountListViewAdapter(context, datas,biaozhi);
				CountListView.setAdapter(adapter);
				/*DataAnalysisAdapter adapter1=new DataAnalysisAdapter(Datas, context);
				DataAnalysis.setAdapter(adapter1);*/
				LineData linedata=getLineData();
				if(linedata!=null){
					showChart(CountLinechart, linedata, Color.rgb(114, 188, 223));
				}
			}
			 Expand.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View view) {
					if(ZheXianLayout.getVisibility()==View.GONE){
						ZheXianLayout.setVisibility(View.VISIBLE);
						Expand.setImageResource(R.drawable.xiala2);
					}else{
						ZheXianLayout.setVisibility(View.GONE);
						Expand.setImageResource(R.drawable.xiala1);
					}
					
				}
			});
			
	}
	public void addMapData(int position){
		datas=new HashMap<String, List<Object>>();
		if(biaozhi==0){
		datas.put(str[0], addListData((float)Datas.get(position).getShouRuCount(), String.valueOf(Datas.get(position).getShouRunum()),Datas.get(position).getDate()));
		datas.put(str[1], addListData((float)(Datas.get(position).getZhiChuCount()), String.valueOf(Datas.get(position).getZhiChunum()),Datas.get(position).getDate()));
		datas.put(str[2], addListData((float)(Datas.get(position).getYingShouCount()), String.valueOf(Datas.get(position).getYingShounum()),Datas.get(position).getDate()));
		datas.put(str[3], addListData((float)(Datas.get(position).getYingFuCount()), String.valueOf(Datas.get(position).getYingFunum()),Datas.get(position).getDate()));
		datas.put(str[4], addListData((float)(Datas.get(position).getShiShouCount()), String.valueOf(Datas.get(position).getShiShounum()),Datas.get(position).getDate()));
		datas.put(str[5], addListData((float)(Datas.get(position).getShiFuCount()), String.valueOf(Datas.get(position).getShiFunum()),Datas.get(position).getDate()));
	}
		else{	
			datas.put(str1[0], addListData((float)(Datas.get(position).getShouRuCount()), String.valueOf(Datas.get(position).getShouRunum()),Datas.get(position).getDate()));
			datas.put(str1[1], addListData((float)(Datas.get(position).getZhiChuCount()), String.valueOf(Datas.get(position).getZhiChunum()),Datas.get(position).getDate()));
			datas.put(str1[2], addListData((float)(Datas.get(position).getYingShouCount()), String.valueOf(Datas.get(position).getYingShounum()),Datas.get(position).getDate()));
			datas.put(str1[3], addListData((float)(Datas.get(position).getYingFuCount()), String.valueOf(Datas.get(position).getYingFunum()),Datas.get(position).getDate()));
			datas.put(str1[4], addListData((float)(Datas.get(position).getShiShouCount()), String.valueOf(Datas.get(position).getShiShounum()),Datas.get(position).getDate()));
			datas.put(str1[5], addListData((float)(Datas.get(position).getShiFuCount()), String.valueOf(Datas.get(position).getShiFunum()),Datas.get(position).getDate()));
		}
	}
	public List<Object> addListData(float str1,String str2,String str3){
		List<Object> list=new ArrayList<Object>();
		list.add(str1);
		list.add(str2);
		list.add(str3);
		return list;
	}
//����ͼ
	private void showChart(PieChart pieChart, PieData pieData) { 
		pieChart.setHoleColorTransparent(true); 
		pieChart.setHoleRadius(60f); //�뾶
		pieChart.setTransparentCircleRadius(64f); // ��͸��Ȧ
		pieChart.setDescription("���˱�״ͼ"); 
		pieChart.setDrawCenterText(true); //��״ͼ�м�����������
		pieChart.setDrawHoleEnabled(true); 
		pieChart.setRotationAngle(90); // ��ʼ��ת�Ƕ� 
		pieChart.setRotationEnabled(true); // �����ֶ���ת
		pieChart.setUsePercentValues(true); //��ʾ�ɰٷֱ�
		//��������
		pieChart.setData(pieData); 
		Legend mLegend = pieChart.getLegend(); //���ñ���ͼ
		mLegend.setPosition(LegendPosition.RIGHT_OF_CHART); //���ұ���ʾ
		mLegend.setXEntrySpace(7f); 
		mLegend.setYEntrySpace(5f); 
		pieChart.animateXY(1000, 1000); //���ö���
		} 
		
private PieData getPieData(int count, float range) { 
			ArrayList<String> Titles = new ArrayList<String>();
			ArrayList<Entry> Count = new ArrayList<Entry>();
			if(biaozhi==0){
		for(int i=0;i<6;i++){
				Titles.add(str[i]);
				Count.add(new Entry((Float) datas.get(str[i]).get(0), i));
				
			}}else{
				for(int i=0;i<6;i++){
					Titles.add(str1[i]);
					Count.add(new Entry((Float) datas.get(str1[i]).get(0), i));
			}
				}
		//y��ļ���
			if(Count.size()!=0){
		PieDataSet pieDataSet = new PieDataSet(Count, ""/*��ʾ�ڱ���ͼ��*/); 
		pieDataSet.setSliceSpace(0f); //���ø���״ͼ֮��ľ���
		ArrayList<Integer> colors = new ArrayList<Integer>(); 
		// ��ͼ��ɫ
		colors.add(Color.rgb(205, 205, 205)); 
		colors.add(Color.rgb(114, 188, 223)); 
		colors.add(Color.rgb(255, 123, 124)); 
		colors.add(Color.rgb(57, 135, 200)); 
		colors.add(Color.rgb(100, 135, 215));
		pieDataSet.setColors(colors); 
		
		PieData pieData = new PieData(Titles, pieDataSet); 
		return pieData; 
		} 
			else{	
				return null;
			}
		}
//����ͼ
private void showChart(LineChart lineChart, LineData lineData, int color) { 
lineChart.setDrawBorders(false);
lineChart.setDescription("");
lineChart.setNoDataTextDescription("You need to provide data for the chart."); 
lineChart.setDrawGridBackground(false); 
lineChart.setGridBackgroundColor(Color.WHITE& 0x70FFFFFF);
lineChart.setTouchEnabled(true); // �����Ƿ���Դ���
lineChart.setDragEnabled(true);// �Ƿ������ק
lineChart.setScaleEnabled(true);// �Ƿ��������
lineChart.setPinchZoom(false);// 
lineChart.setBackgroundColor(color);// ���ñ���
lineChart.setData(lineData); // ��������
Legend mLegend = lineChart.getLegend(); // ���ñ���ͼ��ʾ�������Ǹ�һ��y��value��
mLegend.setForm(LegendForm.CIRCLE);// ��ʽ
mLegend.setFormSize(6f);// ����
mLegend.setTextColor(Color.WHITE);// ��ɫ
lineChart.animateX(2500); // ����ִ�еĶ���,x��
} 

private LineData getLineData() { 
	ArrayList<String> Titles = new ArrayList<String>();
	ArrayList<Entry> Count = new ArrayList<Entry>();//��������
	ArrayList<Entry> Count1 = new ArrayList<Entry>();//֧��
	ArrayList<Entry> Count2 = new ArrayList<Entry>();//Ӧ��
	ArrayList<Entry> Count3 = new ArrayList<Entry>();//Ӧ��
	ArrayList<Entry> Count4 = new ArrayList<Entry>();//ʵ��
	ArrayList<Entry> Count5= new ArrayList<Entry>();//ʵ��
for(int i=0;i<ZheXianDataTime.size();i++){	
		Titles.add(ZheXianDataTime.get(i));
		Count.add(new Entry((float) Datas.get(i).getShouRuCount(), i));
		Count1.add(new Entry((float) Datas.get(i).getZhiChuCount(), i));
		Count2.add(new Entry((float) Datas.get(i).getYingShouCount(), i));
		Count3.add(new Entry((float) Datas.get(i).getYingFuCount(), i));
		Count4.add(new Entry((float) Datas.get(i).getShiShouCount(), i));
		Count5.add(new Entry((float) Datas.get(i).getShiFuCount(), i));
		
	}

ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>(); 
if(Count.size()!=0){
lineDataSets.add(initLineDataSet(Count,Color.WHITE,"����")); }
if(Count1.size()!=0){
	lineDataSets.add(initLineDataSet(Count1,Color.BLUE,"֧��"));
}
if(Count2.size()!=0){
lineDataSets.add(initLineDataSet(Count2,Color.CYAN,"���")); }
if(Count3.size()!=0){
	lineDataSets.add(initLineDataSet(Count3,Color.GREEN,"����"));
}
if(Count4.size()!=0){
lineDataSets.add(initLineDataSet(Count4,Color.RED,"ʵ��")); }
if(Count5.size()!=0){
	lineDataSets.add(initLineDataSet(Count5,Color.YELLOW,"ʵ��"));
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
		lineDataSet.setLineWidth(1.75f); // �߿�
		lineDataSet.setCircleSize(3f);// ��ʾ��Բ�δ�С
		lineDataSet.setColor(Color.WHITE);// ��ʾ��ɫ
		lineDataSet.setCircleColor(Color.WHITE);// Բ�ε���ɫ
		lineDataSet.setHighLightColor(Color.WHITE); // �������ߵ���ɫ
		return lineDataSet;

}
}