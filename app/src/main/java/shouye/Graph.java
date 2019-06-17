package shouye; 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sqlite.CountSQLite;
import sqlite.IncomeSQLite;
import sqlite.MenuClassSQLite;
import sqlite.PaySQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import Adapters.GraphShowDataAdapter;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.jiacaitong.R;

import entity.CountEntity;
import entity.GraphTemplate;
import entity.MenuUseFulClass;
public class Graph extends Activity {
    private ImageView GraphNoData;
    private LinearLayout ShowDataLayout;
    private List<GraphTemplate> datasTemplate;
    private IncomeSQLite incomesqlite;
	private PaySQLite paysqlite;
	private YingShouSQLite yingshousqlite;
	private YingFuSQLite yingfusqlite;
	private MenuClassSQLite menuclasssqlite;
	private CountSQLite countsqlite;
	private SQLiteDatabase db1, db2, db3, db4,db5,db6;
	private ListView lv,GraphDatasList;
	private static String selectData="����";
	private Map<String,List<GraphTemplate>> graphDatas,graphDatasSingle;
	private List<String> strs,ClassType;
	List<MenuUseFulClass> menuuseclass=new ArrayList<MenuUseFulClass>();
	private String day = "";
	private int number;
	private String SpinnerData; 
	
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.graph);
    	init();
    } 
  //��ʼ��
    public void init(){
    	day=GraphContainer.returnTime();
    	selectData=GraphContainer.returnselectType();
    	SpinnerData=GraphContainer.returnSpinnerData();
    	GraphDatasList=(ListView)findViewById(R.id.GraphDatasList);
    	GraphNoData=(ImageView)findViewById(R.id.GraphNoData);
    	ShowDataLayout=(LinearLayout)findViewById(R.id.ShowDataLayout);
    	strs=new ArrayList<String>();
    	ClassType=new ArrayList<String>();
    	datasTemplate=new ArrayList<GraphTemplate>();
    	graphDatas=new HashMap<String,List<GraphTemplate>>();
    	graphDatasSingle=new HashMap<String,List<GraphTemplate>>();
    	incomesqlite = new IncomeSQLite(this, "income.db", null, 1);
		db1 = incomesqlite.getReadableDatabase();
		paysqlite = new PaySQLite(this, "pay.db", null, 1);
		db2 = paysqlite.getReadableDatabase();
		yingshousqlite = new YingShouSQLite(this, "yingshou.db", null, 1);
		db3 = yingshousqlite.getReadableDatabase();
		yingfusqlite = new YingFuSQLite(this, "yingfu.db", null, 1);
		db4 = yingfusqlite.getReadableDatabase();
		menuclasssqlite = new MenuClassSQLite(this,"menuclass.db",null,1);
		db5=menuclasssqlite.getReadableDatabase();
		countsqlite=new CountSQLite(this, "TotalCount.db", null, 1);
		db6=countsqlite.getReadableDatabase();
		ShowData();
    }
    //��ʾ����
    public void ShowData(){
    	datasTemplate.clear();
    	graphDatas.clear();
    	strs.clear();
    	ClassType.clear();
    		if(SpinnerData.equals("������")){
    			PickMenthod(selectData, day);
        	}else{
        		PickMenthod1(selectData, day);}
    	if(selectData.equals("ȫ��")){
    		if(SpinnerData.equals("������")){
    			PickMenthod("����", day);
    			PickMenthod("֧��", day);
    			PickMenthod("���", day);
    			PickMenthod("����", day);
    			PickMenthod("ʵ��", day);
    			PickMenthod("ʵ��", day);
    			
        	}else{
        		PickMenthod1("����", day);
    			PickMenthod1("֧��", day);
    			PickMenthod1("���", day);
    			PickMenthod1("����", day);
    			PickMenthod1("ʵ��", day);
    			PickMenthod1("ʵ��", day);
    	}}
    	if( graphDatas.size()==0){
    		GraphDatasList.setVisibility(View.GONE);
            GraphNoData.setVisibility(View.VISIBLE);
            ShowDataLayout.setBackgroundColor(Color.WHITE);
    	}
    	else{
    		GraphDatasList.setVisibility(View.VISIBLE);
            GraphNoData.setVisibility(View.GONE);
            ShowDataLayout.setBackgroundColor(Color.GRAY);
       	   GraphShowDataAdapter adapter=new GraphShowDataAdapter(this,graphDatas,graphDatasSingle,strs,ClassType,SpinnerData,selectData);
   		   GraphDatasList.setAdapter(adapter);
    	}
    }
	public void OrderByOther(List<GraphTemplate> templateDatas,String data){
		//templateDatas=new ArrayList<GraphTemplate>();
		menuuseclass=menuclasssqlite.queryMenucalss(db5, data+"��");
		for(int i=0;i<menuuseclass.size();i++){
			GraphTemplate template1=new GraphTemplate();
			double TotalCount=0.0;
			MenuUseFulClass menu=new MenuUseFulClass();
			menu=menuuseclass.get(i);
			if(data.equals("����")){
				TotalCount=incomesqlite.TotalCountBySource(db1, menu.getMenuUsefulName(),day);
	    		}
	    	if(data.equals("֧��")){
	    		TotalCount=paysqlite.TotalCountBySource(db2, menu.getMenuUsefulName(),day);
	    	}	
	    	
	    	if(data.equals("���")){
	    		TotalCount=yingshousqlite.TotalCountBySource(db3, menu.getMenuUsefulName(),day,"0");
	    	}	
	    	if(data.equals("����")){
	    		TotalCount=yingfusqlite.TotalCountBySource(db4, menu.getMenuUsefulName(),day,"0");
	    	}	
	    	if(data.equals("ʵ��")){
	    		TotalCount=yingshousqlite.TotalCountBySource(db3, menu.getMenuUsefulName(),day,"1");
	    	}	
	    	if(data.equals("ʵ��")){
	    		TotalCount=yingfusqlite.TotalCountBySource(db4, menu.getMenuUsefulName(),day,"1");
	    	}
			if(TotalCount!=0){
			template1.setMenuName(menu.getMenuUsefulName());
			template1.setCount(TotalCount);
			templateDatas.add(template1);
    	}
			
		} 
		if(templateDatas.size()!=0){
			GraphTemplate template=new GraphTemplate();
    		template.setMenuName(data+"�ϼ�");
    		if(data.equals("����")){
    			template.setCount(incomesqlite.TotalCount(db1,day));
	    		}
	    	if(data.equals("֧��")){
	    		template.setCount(paysqlite.TotalCount(db2,day));
	    	}	
	    	
	    	if(data.equals("���")){
	    		template.setCount(yingshousqlite.TotalCount(db3,day,"0"));
	    	}	
	    	if(data.equals("����")){
	    		template.setCount(yingfusqlite.TotalCount(db4,day,"0"));
	    	}	
	    	if(data.equals("ʵ��")){
	    		template.setCount(yingshousqlite.TotalCount(db3,day,"1"));
	    	}	
	    	if(data.equals("ʵ��")){
	    		template.setCount(yingfusqlite.TotalCount(db4,day,"1"));
	    	}
    		templateDatas.add(template);
       		strs.add(day+"  "+data);
     	    graphDatas.put(day+"  "+data, templateDatas);
     	    ClassType.add(data);}
		}
	public void GetTotalData(List<GraphTemplate> templateDatas){
		//templateDatas=new ArrayList<GraphTemplate>();
		CountEntity entity=new CountEntity();
		entity=countsqlite.QueryByDate(db6, day);
		SetTempalteData(templateDatas,"����",entity.getShouRuCount());
		SetTempalteData(templateDatas,"֧��",entity.getZhiChuCount());
		SetTempalteData(templateDatas,"���",entity.getYingShouCount());
		SetTempalteData(templateDatas,"����",entity.getYingFuCount());
		SetTempalteData(templateDatas,"ʵ��",entity.getShiShouCount());
		SetTempalteData(templateDatas,"ʵ��",entity.getShiFuCount());
		SetTempalteData(templateDatas,"����",entity.getShouRuCount()+entity.getShiShouCount()-entity.getZhiChuCount()-entity.getShiFuCount());
   		strs.add(day+"  "+selectData);
 	    graphDatas.put(day+"  "+selectData, templateDatas);
 	    ClassType.add(selectData);
		if(SpinnerData.equals("������")){
			graphDatasSingle=countsqlite.TimeDatas(db6, day);
		}
		}
	
	public void PickMenthod1(String data,String Time){
		List<GraphTemplate> datasTemplate1=new ArrayList<GraphTemplate>();
		if(!data.equals("����")){
			OrderByOther(datasTemplate1,data);}
		else{
			GetTotalData(datasTemplate1);
		}
       	}
	public void OrderByWeek(List<GraphTemplate> templateDatas,String str,String time1,String time2,String Time,int week){
		templateDatas=new ArrayList<GraphTemplate>();
		menuuseclass=menuclasssqlite.queryMenucalss(db5, str+"��");
		for(int i=0;i<menuuseclass.size();i++){
			GraphTemplate template1=new GraphTemplate();
			double TotalCount=0;
			MenuUseFulClass menu=new MenuUseFulClass();
			menu=menuuseclass.get(i);
			if(str.equals("����")){
				TotalCount=incomesqlite.TotalCountBySource(db1, menu.getMenuUsefulName(),time1,time2);
	    		}
	    	if(str.equals("֧��")){
	    		TotalCount=paysqlite.TotalCountBySource(db2, menu.getMenuUsefulName(),time1,time2);
	    	}	
	    	
	    	if(str.equals("���")){
	    		TotalCount=yingshousqlite.TotalCountBySource(db3, menu.getMenuUsefulName(),time1,time2,"0");
	    	}	
	    	if(str.equals("����")){
	    		TotalCount=yingfusqlite.TotalCountBySource(db4, menu.getMenuUsefulName(),time1,time2,"0");
	    	}	
	    	if(str.equals("ʵ��")){
	    		TotalCount=yingshousqlite.TotalCountBySource(db3, menu.getMenuUsefulName(),time1,time2,"1");
	    	}	
	    	if(str.equals("ʵ��")){
	    		TotalCount=yingfusqlite.TotalCountBySource(db4, menu.getMenuUsefulName(),time1,time2,"1");
	    	}
			if(TotalCount!=0){
			template1.setMenuName(menu.getMenuUsefulName());
			template1.setCount(TotalCount);
			templateDatas.add(template1);
    	}} 
		if(templateDatas.size()!=0){
			GraphTemplate template=new GraphTemplate();
    		template.setMenuName(str+"�ϼ�");
    		if(str.equals("����")){
    			template.setCount(incomesqlite.TotalCount(db1,time1,time2));
	    		}
	    	if(str.equals("֧��")){
	    		template.setCount(paysqlite.TotalCount(db2,time1,time2));
	    	}	
	    	
	    	if(str.equals("���")){
	    		template.setCount(yingshousqlite.TotalCount(db3,time1,time2,"0"));
	    	}	
	    	if(str.equals("����")){
	    		template.setCount(yingfusqlite.TotalCount(db4,time1,time2,"0"));
	    	}	
	    	if(str.equals("ʵ��")){
	    		template.setCount(yingshousqlite.TotalCount(db3,time1,time2,"1"));
	    	}	
	    	if(str.equals("ʵ��")){
	    		template.setCount(yingfusqlite.TotalCount(db4,time1,time2,"1"));
	    	}
    		templateDatas.add(template);
       		strs.add(Time+"��"+" "+week+" "+"��"+"  "+str);
     	    graphDatas.put(Time+"��"+" "+week+" "+"��"+"  "+str, templateDatas);
     	    ClassType.add(str);}
		}
	//��ѯȫ��������
	public void PickMenthod(String data,String Time){
		GetTime();
		List<GraphTemplate> datasTemplate1=new ArrayList<GraphTemplate>();
  		 List<GraphTemplate> datasTemplate2=new ArrayList<GraphTemplate>();
  		 List<GraphTemplate> datasTemplate3=new ArrayList<GraphTemplate>();
  		 List<GraphTemplate> datasTemplate4=new ArrayList<GraphTemplate>();
  		 List<GraphTemplate> datasTemplate5=new ArrayList<GraphTemplate>();
       OrderByWeek(datasTemplate1, data, Time+"-"+"01", Time+"-"+"07", Time, 1);
       OrderByWeek(datasTemplate2, data, Time+"-"+"08", Time+"-"+"14", Time, 2);
       OrderByWeek(datasTemplate3, data, Time+"-"+"15", Time+"-"+"21", Time, 3);
       OrderByWeek(datasTemplate4, data, Time+"-"+"22", Time+"-"+"28", Time, 4);
       OrderByWeek(datasTemplate5, data, Time+"-"+"29", Time+"-"+number, Time, 5);
	}
public void GetTime(){
	SimpleDateFormat format;
	if(day.length()==4){
		format=new SimpleDateFormat("yyyy");
	}else if(day.length()==7){
		format=new SimpleDateFormat("yyyy-MM");
	}else{
		format=new SimpleDateFormat("yyyy-MM-DD");
	}
    	Date date = null;
		try {
			date = format.parse(day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		number=cal.getActualMaximum(Calendar.DATE);
	}
public void SetTempalteData(List<GraphTemplate> templateDatas,String username,double count){
	GraphTemplate template1=new GraphTemplate();	
	template1.setMenuName(username);
	template1.setCount(count);
	templateDatas.add(template1);
}
}