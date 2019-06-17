package festival;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tool.NongLiCalendar;
import work.basic;

import com.example.jiacaitong.R;

import entity.FestivalEntity;
public class Festival{
	public String date1,date2,date3;
	public  Map<String,FestivalEntity> festivalMap=new HashMap<String,FestivalEntity>();
	private List<String> FestivalText=new ArrayList<String>();
	private List<String> FestivalHttp=new ArrayList<String>();
	public String date[]={"0101","一月初一"," 一月十五","0214","0308","0312","0501","0504",
			              "0601","0701","0801","五月初五","七月初七","七月十五","八月十五","0910",
			              "九月初九","1001","1031","1224","1225","0405","0401"};
	public int Images[]={R.drawable.yuandanjie,R.drawable.chunjie1,R.drawable.yuanxiaojie,R.drawable.qingrenjie1,
			             R.drawable.funvjie2,R.drawable.zhishujie3,R.drawable.laodongjie1,R.drawable.qingnianjie3,
			             R.drawable.ertongjie,R.drawable.jiandangjie2,R.drawable.jianjunjie3,R.drawable.duanwujie3,
			             R.drawable.qixijie2,R.drawable.zhongyu,R.drawable.zhongqiujie1,R.drawable.jiaoshijie,
			             R.drawable.chongyangjie,R.drawable.guoqingjie,R.drawable.wanshengjie2,
			             R.drawable.pinganye2,R.drawable.shengdanjie2,R.drawable.qingmingjie,R.drawable.yurenjie};
   public Festival() {
		super();
		FestivalText=basic.returnFestivalTextView();
		FestivalHttp=basic.returnFestivalHttp();
		 Date dateTime=new Date(System.currentTimeMillis());
		   Calendar cal=Calendar.getInstance();
		   cal.setTime(dateTime);
		   int Year=cal.get(Calendar.YEAR);
		   String Time=String.valueOf(Year)+"-"+"05-01";
		   String Time1=String.valueOf(Year)+"-"+"06-01";
		   String Time2=String.valueOf(Year)+"-"+"11-01";
		   MapAddData(Time, "05",R.drawable.muqinjie1,5);
		   MapAddData(Time1, "06",R.drawable.fuqinjie1,6);
		   MapAddData(Time2, "11",R.drawable.ganenjie,11);
		for(int i=0;i<date.length;i++){
			FestivalEntity festival=new FestivalEntity();
			festival.setFestivalImage(Images[i]);
			festival.setFestivalText(FestivalText.get(i+3));
			festival.setHttp(FestivalHttp.get(i+3));
			festivalMap.put(date[i],festival);
		
		}
		FestivalEntity Festival=new FestivalEntity();
		Festival.setFestivalImage(R.drawable.chuxi2);
		Festival.setFestivalText(FestivalText.get(FestivalText.size()-1));
		Festival.setHttp(FestivalHttp.get(FestivalHttp.size()-1));
		if(NongLiCalendar.monthDays(Year, 12)==29){
			festivalMap.put("十二月廿九",Festival);
		}else if(NongLiCalendar.monthDays(Year, 12)==30){
			festivalMap.put("十二月三十",Festival);
		}
	}


public Map<String,FestivalEntity> returnFestival(){
	return festivalMap;
}
public void MapAddData(String time,String month,int image,int Id){
	FestivalEntity festival=new FestivalEntity();
	festival.setFestivalImage(image);
	Calendar cal=Calendar.getInstance();
	 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	  try {
		  //
		Date dateTime1=format.parse(time);
		cal.setTime(dateTime1);
		if(Id==5){
		while(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY){
			cal.add(Calendar.DATE, 1);
		}
		cal.add(Calendar.DATE, 7);
		festival.setFestivalText(FestivalText.get(0));
		festival.setHttp(FestivalHttp.get(0));
		}
		if(Id==6){
			while(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY){
				cal.add(Calendar.DATE, 1);
				
			}
			cal.add(Calendar.DATE, 14);
			festival.setFestivalText(FestivalText.get(1));
			festival.setHttp(FestivalHttp.get(1));}
		if(Id==11){
			while(cal.get(Calendar.DAY_OF_WEEK)!=Calendar.THURSDAY){
				cal.add(Calendar.DATE, 1);
			}
			cal.add(Calendar.DATE,21);
			festival.setHttp(FestivalHttp.get(2));
			festival.setFestivalText(FestivalText.get(2));}
		
		
		if(cal.get(Calendar.DAY_OF_MONTH)<10){
			festivalMap.put(month+"0"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH)),festival);
		}else{
			festivalMap.put(month+String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), festival);
		}
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}

}
