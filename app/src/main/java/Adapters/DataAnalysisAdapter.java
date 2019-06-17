package Adapters;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import sqlite.CountPredictSQLite;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.CountEntity;
import entity.CountPredict;

public class DataAnalysisAdapter extends BaseAdapter {
   private CountEntity CountDatas;
   private Context context;
   private String analysisZhiBiao[]={"�������","���ѱ���","�����Ա���","��ծ�������","��ծ����","�峥����","����Ԥ��ƫ��","֧��Ԥ��ƫ��"};
   private String analysisGongShi[]={"������-֧����/����","֧��/����","����/֧��","ʵ��/���","����/(����-֧��)","ʵ��/����","|(ʵ��-Ԥ��)/ʵ��|","|(ʵ��-Ԥ��)/ʵ��|"};
   private double analysiscankaozhi[]={0.1,0.5,3.0,0.4,0.5,0.5,0.1,0.1};
   private List<String> analydatabilv;
   private List<String> analysisResult;
   private CountPredictSQLite predictsqlite ;
   private SQLiteDatabase db;
	CountPredict predict=new CountPredict();
   public DataAnalysisAdapter(CountEntity countDatas, Context context) {
	super();
	CountDatas = countDatas;
	this.context = context;
	analydatabilv=new ArrayList<String>();
	analysisResult=new ArrayList<String>();
	predictsqlite = new CountPredictSQLite(context, "Predict.db", null, 1);
	db = predictsqlite.getReadableDatabase();
	predict=predictsqlite.getCountPredictDatas(db, countDatas.getDate());
}
	public int getCount() {
		return analysisZhiBiao.length;
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
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder viewholder = null;
		if(viewholder==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.dataanalysisitem, null);
			viewholder.DataAnalysisText1=(TextView)view.findViewById(R.id.DataAnalysisText1);
			viewholder.DataAnalysisText2=(TextView)view.findViewById(R.id.DataAnalysisText2);
			viewholder.DataAnalysisText3=(TextView)view.findViewById(R.id.DataAnalysisText3);
			viewholder.DataAnalysisText4=(TextView)view.findViewById(R.id.DataAnalysisText4);
			viewholder.DataAnalysisText5=(TextView)view.findViewById(R.id.DataAnalysisText5);
			view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		DealDataBiLv(position);
		viewholder.DataAnalysisText1.setText(analysisZhiBiao[position]);
		viewholder.DataAnalysisText2.setText(analysisGongShi[position]);
		viewholder.DataAnalysisText3.setText(analydatabilv.get(position));
		viewholder.DataAnalysisText4.setText(String.valueOf(analysiscankaozhi[position]));
		viewholder.DataAnalysisText5.setText(analysisResult.get(position));
	    return view;
}
	class ViewHolder{
		TextView DataAnalysisText1,DataAnalysisText2,DataAnalysisText3,DataAnalysisText4,DataAnalysisText5;
	}
	public void DealDataBiLv(int position){
		double count=0.0;
		if(position==0){
		 if(CountDatas.getShouRuCount()-CountDatas.getZhiChuCount()==0){
		 analydatabilv.add("0.0");
		 analysisResult.add("��֧ƽ��");}
		 else if(CountDatas.getShouRuCount()-CountDatas.getZhiChuCount()<0){
		 count = new BigDecimal((CountDatas.getShouRuCount()-CountDatas.getZhiChuCount())
		/CountDatas.getShouRuCount()).setScale(1, BigDecimal.ROUND_HALF_UP)
		.doubleValue();//ͨ���������뱣��һλС��
		 analydatabilv.add(String.valueOf(count));
		 analysisResult.add("�����ϴ�"); }else{
		 count = new BigDecimal(CountDatas.getZhiChuCount()/CountDatas.getShouRuCount())
		 .setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//ͨ���������뱣��һλС��
		 analydatabilv.add(String.valueOf(count));
		 if(count<=analysiscankaozhi[position]){analysisResult.add("����һ��");
		 }else{analysisResult.add("����ḻ");}}}
		 if(position==1 || position==2){
			 if(CountDatas.getZhiChuCount()==0 && CountDatas.getShouRuCount()==0){
				 analydatabilv.add("0.0");
					analysisResult.add("��������");
			 }if(CountDatas.getZhiChuCount()==0){
				 if(position==1){
					 analydatabilv.add("0.0");
						analysisResult.add("������");
				 }if(position==2){
					 analydatabilv.add("/");
						analysisResult.add("�����Դ�");
				 }
				 
			 }if(CountDatas.getShouRuCount()==0){
				 if(position==1){
					 analydatabilv.add("/");
						analysisResult.add("������");
				 }else{
					 analydatabilv.add("0.0");
						analysisResult.add("��������");
				 }
			 }
			 if(CountDatas.getZhiChuCount()!=0 && CountDatas.getShouRuCount()!=0){
				 if(position==1){
				 count = new BigDecimal(CountDatas.getZhiChuCount()/CountDatas.getShouRuCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//ͨ���������뱣��һλС��
					analydatabilv.add(String.valueOf(count));
					if(count<analysiscankaozhi[position]){
						analysisResult.add("����ˮƽ��");
					}else{
						analysisResult.add("����ˮƽ��");
					}
			 }else{
				 count = new BigDecimal(CountDatas.getShouRuCount()/CountDatas.getZhiChuCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//ͨ���������뱣��һλС��
					analydatabilv.add(String.valueOf(count));
					if(count<analysiscankaozhi[position]){
						analysisResult.add("�����Խ�С");
					}else{
						analysisResult.add("�����Խϴ�");
					}
			 }
				 
			 }
		 }
if(position==3){
	 if(CountDatas.getShiShouCount()==0 && CountDatas.getYingShouCount()==0){
		 analydatabilv.add("0.0");
			analysisResult.add("�޿���������");
	 }if(CountDatas.getShiShouCount()==0){
			    analydatabilv.add("0.0");
				analysisResult.add("��������");
		 }
		 
	 
	 else{
		count = new BigDecimal(CountDatas.getShiShouCount()/CountDatas.getYingShouCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//ͨ���������뱣��һλС��
		analydatabilv.add(String.valueOf(count));
			if(count<analysiscankaozhi[position]){
				analysisResult.add("�����ʱ���");
			}else{
				analysisResult.add("�����ʱ���");
			}
	
		 
	 }
 
}
if(position==4){
	if(CountDatas.getShouRuCount()-CountDatas.getZhiChuCount()<=0){
		analydatabilv.add("/");
		analysisResult.add("�߸�ծ");
	}
	if(CountDatas.getYingFuCount()==0){
		analydatabilv.add("0.0");
		analysisResult.add("�޸�ծ");
	}
	else{
		count = new BigDecimal(CountDatas.getYingFuCount()/(CountDatas.getShouRuCount()-CountDatas.getZhiChuCount())).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//ͨ���������뱣��һλС��
		analydatabilv.add(String.valueOf(count));
			if(count<analysiscankaozhi[position]){
				analysisResult.add("��ծС");
			}else{
				analysisResult.add("��ծ��");
			}
	}
	 
}
if(position==5){
	 if(CountDatas.getShiFuCount()==0 && CountDatas.getYingFuCount()==0){
		 analydatabilv.add("0.0");
			analysisResult.add("�޸�ծ");
	 }else if(CountDatas.getShiFuCount()==0){
			    analydatabilv.add("0.0");
				analysisResult.add("��ծ��");
		 }
	 else{
		count = new BigDecimal(CountDatas.getShiFuCount()/CountDatas.getYingFuCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//ͨ���������뱣��һλС��
		analydatabilv.add(String.valueOf(count));
			if(count<analysiscankaozhi[position]){
				analysisResult.add("��ծ�ϴ�");
			}else{
				analysisResult.add("��ծ��С");
			}
	
		 
	 }
}
if(position==6){
	double count1=CountDatas.getShouRuCount()-predict.getShouPredictCount();
	double count2=CountDatas.getZhiChuCount()-predict.getZhiChuPreCount();
	if(CountDatas.getShouRuCount()!=0){
	count = new BigDecimal(Math.abs(count1)/CountDatas.getShouRuCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//ͨ���������뱣��һλС��
	analydatabilv.add(String.valueOf(count));
	if(count<analysiscankaozhi[position]){
		analysisResult.add("ƫ���С");
	}else if(count<0.6){
		analysisResult.add("ƫ��ϴ�");
	}else{
		analysisResult.add("ƫ���");
	}
	}else{
		analydatabilv.add("/");
		analysisResult.add("�޷�����");
	}
}
if(position==7){
	double count2=CountDatas.getZhiChuCount()-predict.getZhiChuPreCount();
	if(CountDatas.getZhiChuCount()!=0){
	count = new BigDecimal(Math.abs(count2)/CountDatas.getZhiChuCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//ͨ���������뱣��һλС��
	analydatabilv.add(String.valueOf(count));
	if(count<analysiscankaozhi[position]){
		analysisResult.add("ƫ���С");
	}else if(count<0.6){
		analysisResult.add("ƫ��ϴ�");
	}else{
		analysisResult.add("ƫ���");
	}
	}else{
		analydatabilv.add("/");
		analysisResult.add("�޷�����");
	}
}
	}
}
