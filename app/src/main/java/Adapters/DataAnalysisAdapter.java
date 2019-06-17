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
   private String analysisZhiBiao[]={"结余比率","消费比率","流动性比率","外债收入比率","负债比率","清偿比率","收入预算偏差","支出预算偏差"};
   private String analysisGongShi[]={"（收入-支出）/收入","支出/收入","收入/支出","实收/借款","贷款/(收入-支出)","实付/贷款","|(实际-预算)/实际|","|(实际-预算)/实际|"};
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
		 analysisResult.add("收支平衡");}
		 else if(CountDatas.getShouRuCount()-CountDatas.getZhiChuCount()<0){
		 count = new BigDecimal((CountDatas.getShouRuCount()-CountDatas.getZhiChuCount())
		/CountDatas.getShouRuCount()).setScale(1, BigDecimal.ROUND_HALF_UP)
		.doubleValue();//通过四舍五入保留一位小数
		 analydatabilv.add(String.valueOf(count));
		 analysisResult.add("开销较大"); }else{
		 count = new BigDecimal(CountDatas.getZhiChuCount()/CountDatas.getShouRuCount())
		 .setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//通过四舍五入保留一位小数
		 analydatabilv.add(String.valueOf(count));
		 if(count<=analysiscankaozhi[position]){analysisResult.add("收益一般");
		 }else{analysisResult.add("收益丰富");}}}
		 if(position==1 || position==2){
			 if(CountDatas.getZhiChuCount()==0 && CountDatas.getShouRuCount()==0){
				 analydatabilv.add("0.0");
					analysisResult.add("无流动性");
			 }if(CountDatas.getZhiChuCount()==0){
				 if(position==1){
					 analydatabilv.add("0.0");
						analysisResult.add("无消费");
				 }if(position==2){
					 analydatabilv.add("/");
						analysisResult.add("流动性大");
				 }
				 
			 }if(CountDatas.getShouRuCount()==0){
				 if(position==1){
					 analydatabilv.add("/");
						analysisResult.add("无收入");
				 }else{
					 analydatabilv.add("0.0");
						analysisResult.add("无流动性");
				 }
			 }
			 if(CountDatas.getZhiChuCount()!=0 && CountDatas.getShouRuCount()!=0){
				 if(position==1){
				 count = new BigDecimal(CountDatas.getZhiChuCount()/CountDatas.getShouRuCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//通过四舍五入保留一位小数
					analydatabilv.add(String.valueOf(count));
					if(count<analysiscankaozhi[position]){
						analysisResult.add("消费水平低");
					}else{
						analysisResult.add("消费水平高");
					}
			 }else{
				 count = new BigDecimal(CountDatas.getShouRuCount()/CountDatas.getZhiChuCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//通过四舍五入保留一位小数
					analydatabilv.add(String.valueOf(count));
					if(count<analysiscankaozhi[position]){
						analysisResult.add("流动性较小");
					}else{
						analysisResult.add("流动性较大");
					}
			 }
				 
			 }
		 }
if(position==3){
	 if(CountDatas.getShiShouCount()==0 && CountDatas.getYingShouCount()==0){
		 analydatabilv.add("0.0");
			analysisResult.add("无可收外收入");
	 }if(CountDatas.getShiShouCount()==0){
			    analydatabilv.add("0.0");
				analysisResult.add("无外收入");
		 }
		 
	 
	 else{
		count = new BigDecimal(CountDatas.getShiShouCount()/CountDatas.getYingShouCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//通过四舍五入保留一位小数
		analydatabilv.add(String.valueOf(count));
			if(count<analysiscankaozhi[position]){
				analysisResult.add("可收资本多");
			}else{
				analysisResult.add("可收资本少");
			}
	
		 
	 }
 
}
if(position==4){
	if(CountDatas.getShouRuCount()-CountDatas.getZhiChuCount()<=0){
		analydatabilv.add("/");
		analysisResult.add("高负债");
	}
	if(CountDatas.getYingFuCount()==0){
		analydatabilv.add("0.0");
		analysisResult.add("无负债");
	}
	else{
		count = new BigDecimal(CountDatas.getYingFuCount()/(CountDatas.getShouRuCount()-CountDatas.getZhiChuCount())).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//通过四舍五入保留一位小数
		analydatabilv.add(String.valueOf(count));
			if(count<analysiscankaozhi[position]){
				analysisResult.add("负债小");
			}else{
				analysisResult.add("负债大");
			}
	}
	 
}
if(position==5){
	 if(CountDatas.getShiFuCount()==0 && CountDatas.getYingFuCount()==0){
		 analydatabilv.add("0.0");
			analysisResult.add("无负债");
	 }else if(CountDatas.getShiFuCount()==0){
			    analydatabilv.add("0.0");
				analysisResult.add("负债大");
		 }
	 else{
		count = new BigDecimal(CountDatas.getShiFuCount()/CountDatas.getYingFuCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//通过四舍五入保留一位小数
		analydatabilv.add(String.valueOf(count));
			if(count<analysiscankaozhi[position]){
				analysisResult.add("负债较大");
			}else{
				analysisResult.add("负债较小");
			}
	
		 
	 }
}
if(position==6){
	double count1=CountDatas.getShouRuCount()-predict.getShouPredictCount();
	double count2=CountDatas.getZhiChuCount()-predict.getZhiChuPreCount();
	if(CountDatas.getShouRuCount()!=0){
	count = new BigDecimal(Math.abs(count1)/CountDatas.getShouRuCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//通过四舍五入保留一位小数
	analydatabilv.add(String.valueOf(count));
	if(count<analysiscankaozhi[position]){
		analysisResult.add("偏差较小");
	}else if(count<0.6){
		analysisResult.add("偏差较大");
	}else{
		analysisResult.add("偏差大");
	}
	}else{
		analydatabilv.add("/");
		analysisResult.add("无法估计");
	}
}
if(position==7){
	double count2=CountDatas.getZhiChuCount()-predict.getZhiChuPreCount();
	if(CountDatas.getZhiChuCount()!=0){
	count = new BigDecimal(Math.abs(count2)/CountDatas.getZhiChuCount()).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();//通过四舍五入保留一位小数
	analydatabilv.add(String.valueOf(count));
	if(count<analysiscankaozhi[position]){
		analysisResult.add("偏差较小");
	}else if(count<0.6){
		analysisResult.add("偏差较大");
	}else{
		analysisResult.add("偏差大");
	}
	}else{
		analydatabilv.add("/");
		analysisResult.add("无法估计");
	}
}
	}
}
