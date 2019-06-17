package Adapters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import tool.NongLiCalendar;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class CalenderListViewAdapter extends BaseAdapter {
private Context context;
private String Weeks[] = {"周日","周一","周二","周三","周四","周五","周六"};
private List<String> dayList;
	private int Month,Year;
	private String Day;
	Calendar calender=Calendar.getInstance();
	NongLiCalendar nongliCalendar;
public CalenderListViewAdapter(Context context, 
			List<String> dayList,int Month,int Year,String Day) {
		super();
		this.context = context;
		this.dayList = dayList;
		this.Month=Month;
		this.Year=Year;
		this.Day=Day;
	}

public int getCount() {
		
		return dayList.size();
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
		final ViewHolder viewholder;
		if(view==null){
			viewholder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.calenderlistviewitem, null);
			viewholder.WeekDay=(TextView)view.findViewById(R.id.WeekDay);
			viewholder.DayofMonth=(TextView)view.findViewById(R.id.DayOfMonth);
			viewholder.UnderLine=(View)view.findViewById(R.id.UnderLine);
			view.setTag(viewholder);
		}else{
			viewholder=(ViewHolder)view.getTag();
		}
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try { 
			//date = format.parse(String.valueOf(Year)+"-"+String.valueOf(Month)+"-"+dayList.get(position));
			if(position>=3 && position<dayList.size()-3){
			date = format.parse(String.valueOf(Year)+"-"+String.valueOf(Month)+"-"+dayList.get(position));}
			else if(position<3 && Month==1){
				date = format.parse(dayList.get(position));
			}else{
				date = format.parse(String.valueOf(Year)+"-"+dayList.get(position));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calender.setTime(date);
		final int week_index = calender.get(Calendar.DAY_OF_WEEK) - 1;
		
		if(Day.equals(dayList.get(position))){
			viewholder.UnderLine.setVisibility(View.VISIBLE);
			viewholder.WeekDay.setText("今天");
			nongliCalendar=new NongLiCalendar(calender);
			String Time=nongliCalendar.chineseNumber[nongliCalendar.month-1]+"月"+nongliCalendar.getChinaDayString(nongliCalendar.day);
			if(Month<10){
				viewholder.DayofMonth.setText(Year+"-"+"0"+String.valueOf(Month)+"-"+dayList.get(position)+"/"+Time);
			}else{
			viewholder.DayofMonth.setText(Year+"-"+String.valueOf(Month)+"-"+dayList.get(position)+"/"+Time);
			}
			viewholder.WeekDay.setTextColor(Color.RED);
			viewholder.DayofMonth.setTextColor(Color.RED);
			
		}else{
		viewholder.WeekDay.setText(Weeks[week_index]);
		viewholder.DayofMonth.setText(dayList.get(position));
		viewholder.WeekDay.setTextColor(Color.WHITE);
		viewholder.DayofMonth.setTextColor(Color.WHITE);
		viewholder.UnderLine.setVisibility(View.GONE);
		}
		/*else{
			viewholder.WeekDay.setText("");
			viewholder.DayofMonth.setText("");
			viewholder.UnderLine.setVisibility(View.GONE);
		}*/
		return view;
	}
class ViewHolder{
	TextView WeekDay,DayofMonth;
	View UnderLine;
}
}
