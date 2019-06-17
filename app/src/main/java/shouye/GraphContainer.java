package shouye;
import java.util.Calendar;
import java.util.Date;

import Adapters.SpinnerAdapter;
import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.widget.DrawerLayout;
import com.example.jiacaitong.R;

public class GraphContainer extends ActivityGroup implements OnClickListener {
	    private TextView addYear1,cutYear1,addMonth1,cutMonth1,Year1,Month1, SheetButton,GraphButton;
	    private EditText DayText;
	    private ImageView downlist,expand;
	    private Spinner spinner1;
	    private LinearLayout YearLayout,MonthLayout,DayLayout,ShowDatasContainer,BottomLayout;
	    private DrawerLayout drawlayout;
		private String[] selectType={"收入","支出","借款","贷款","实收","实付","总账","全部"};
		private int number;// 暂存月份的变量
		private static String day = "";
		private ListView lv;
		 private static String str="收入";
		 private String labels,lable;
		 private Animation animation,animation1;
		 private boolean flag=false;//判断是表格还是图表
		private  String ExceptStr="表格";
		private static String  SpinnerData;
		 protected void onCreate(Bundle savedInstanceState) {
	    	super.onCreate(savedInstanceState);
	    	requestWindowFeature(Window.FEATURE_NO_TITLE);
	    	setContentView(R.layout.graphcontainer);
	    	init();
	    }
		 //初始化
	 public void init(){
		 lv=(ListView)findViewById(R.id.lv);
			drawlayout=(DrawerLayout)findViewById(R.id.drawLayout);
			downlist=(ImageView)findViewById(R.id.downlist);
			expand=(ImageView)findViewById(R.id.expand1);
			addYear1=(TextView)findViewById(R.id.addYear1);
			cutYear1=(TextView)findViewById(R.id.cutYear1);
			addMonth1=(TextView)findViewById(R.id.addMonth1);
			cutMonth1=(TextView)findViewById(R.id.cutMonth1);
			Year1=(TextView)findViewById(R.id.Year1);
			Month1=(TextView)findViewById(R.id.Month1);
			DayText=(EditText)findViewById(R.id.DayText);
		 	YearLayout=(LinearLayout)findViewById(R.id.YearLayout);
			MonthLayout=(LinearLayout)findViewById(R.id.MonthLayout);
			DayLayout=(LinearLayout)findViewById(R.id.DayLayout);
			BottomLayout=(LinearLayout)findViewById(R.id.BottomLayout);
			ShowDatasContainer=(LinearLayout)findViewById(R.id.ShowDataContainer);
			SheetButton=(TextView)findViewById(R.id.SheetButton);
			GraphButton=(TextView)findViewById(R.id.GraphButton);
			addYear1.setOnClickListener(this);
			cutYear1.setOnClickListener(this);
			cutMonth1.setOnClickListener(this);
			addMonth1.setOnClickListener(this);
			downlist.setOnClickListener(this);
			SheetButton.setOnClickListener(this);
			GraphButton.setOnClickListener(this);
			expand.setOnClickListener(this);
			getTime();
			spinnerDatas();
			ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,selectType);
			lv.setAdapter(adapter1);
			ItemClick();    
			setEditTextChange();

	 }
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.addYear1:
			Year1.setText(String.valueOf(Integer.valueOf(Year1.getText()
					.toString()) + 1));
			getDateTime();
			PickMethod();
			break;
		case R.id.cutYear1:
			Year1.setText(String.valueOf(Integer.valueOf(Year1.getText()
					.toString()) - 1));
			getDateTime();
			PickMethod();
			break;
		case R.id.addMonth1:

			if (number < 9) {
				Month1.setText("0" + String.valueOf(number + 1));
				number = number + 1;
			} else {
				Month1.setText(String.valueOf(number + 1));
				number = number + 1;
			}
			if (number >= 13) {
				number = 1;
				Month1.setText("0" + String.valueOf(number));
			}
			getDateTime();
			PickMethod();
			break;
		case R.id.cutMonth1:
			if (number <= 10) {
				Month1.setText("0" + String.valueOf(number - 1));
				number = number - 1;
			} else {
				Month1.setText(String.valueOf(number - 1));
				number = number - 1;
			}
			if (number < 1) {
				number = 13;
				Month1.setText(String.valueOf(number - 1));
				number = number - 1;
			}
			getDateTime();
			PickMethod();
			break;
		case R.id.downlist:
			showDrawerLayout();
			break;
		case R.id.downlistRadioButton:
			Intent intent=new Intent(this,Graph.class);
			startActivity(intent);
			this.finish();
		break;
		case R.id.SheetButton:
			changeContainerView(SheetButton);
			SheetButton.setTextColor(Color.RED);
			GraphButton.setTextColor(Color.BLACK);
			ExceptStr="表格";
			break;
		case R.id.GraphButton:
			changeContainerView(GraphButton);
			SheetButton.setTextColor(Color.BLACK);
			GraphButton.setTextColor(Color.RED);
			ExceptStr="统计图";
			break;
		case R.id.expand1:
			if(flag==false){
				animation=AnimationUtils.loadAnimation(this, R.anim.leftin);
				BottomLayout.setAnimation(animation);
				BottomLayout.setVisibility(View.VISIBLE);
				flag=true;
			}else{
				animation1=AnimationUtils.loadAnimation(this, R.anim.leftout);
				BottomLayout.setAnimation(animation1);
				BottomLayout.setVisibility(View.GONE);
				flag=false;
			}
				
			break;
}}
	//DrawLayout的状态响应
	public void showDrawerLayout(){
		if(!drawlayout.isDrawerOpen(Gravity.LEFT)){
			drawlayout.openDrawer(Gravity.LEFT);
		}else{
			drawlayout.closeDrawer(Gravity.LEFT);
		}
	}
	//Spinner的响应
	public void spinnerDatas(){
		spinner1 = (Spinner) findViewById(R.id.selectTime);
		final String[] selectList;
		if(str.equals("总账")){
			selectList=new String[]{"按年排","按月排"};
		}
		else{
			selectList=new String[]{"按年排","按月排","按周排","按日排"};
		}
		
		final SpinnerAdapter adapter1=new SpinnerAdapter(this,android.R.layout.simple_spinner_item,selectList);
		adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinner1.setAdapter(adapter1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				if(position==0){
					MonthLayout.setVisibility(View.GONE);
					DayLayout.setVisibility(View.GONE);
					Month1.setText("");
					DayText.setText("");
				}
				if(position==1){
					MonthLayout.setVisibility(View.VISIBLE);
					DayLayout.setVisibility(View.GONE);
					getTime();
					DayText.setText("");
					
				}if(position==2){
					MonthLayout.setVisibility(View.VISIBLE);
					DayLayout.setVisibility(View.GONE);
					if(Month1.getText().equals("")){
						getTime();
					}
					DayText.setText("");
						
				}
				if(position==3){
					MonthLayout.setVisibility(View.VISIBLE);
					DayLayout.setVisibility(View.VISIBLE);
					getTime();
				}
				SpinnerData=selectList[position];
				getDateTime();
				PickMethod();
			}
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}
	//ListView点击事件的响应
	public void ItemClick(){
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long id) {
				str=selectType[position];
				showDrawerLayout();
				getDateTime();
				PickMethod();
			}
		});
	} 
	//EditText文本内容监听
	public void setEditTextChange(){
		DayText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				getDateTime();
				PickMethod();
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	//初始化时间
	public void getTime(){
		Date date=new Date(System.currentTimeMillis());
		Calendar cal=Calendar.getInstance();
				cal.setTime(date);
		number=cal.get(Calendar.MONTH)+1;
		Year1.setText(String.valueOf(cal.get(Calendar.YEAR)));
		//number=date.getMonth();
		//Year1.setText(String.valueOf(date.getYear()+1900));
		if (number < 10) {
			Month1.setText("0" + String.valueOf(number));
		} else { 
			Month1.setText(String.valueOf(number));
		}
	if(cal.get(Calendar.DAY_OF_MONTH)<10){
			DayText.setText("0"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		}
		else{
			DayText.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		}
	}
	//获取EditText中的时间;
	public String baozhaungMenthod(){	
		String	Dateday="";
			if(!(DayText.getText().toString()).equals("")){
			if((Integer.parseInt(DayText.getText().toString())<10)&&(Integer.parseInt(DayText.getText().toString())>0)){
				Dateday="0"+DayText.getText().toString();
			}
			else if((Integer.parseInt(DayText.getText().toString())>=10)&&(Integer.parseInt(DayText.getText().toString())<=31)){
				Dateday=DayText.getText().toString();
			}
			else{
				Toast.makeText(GraphContainer.this, "输入数据不合法!", Toast.LENGTH_LONG).show();
				DayText.setText("");
			}
			}
			return Dateday;
			
		}
	//响应Container容器中的View;
	 private void toActivity(String lable,Class<?>cls){
		 Intent intent1=new Intent(this,cls);
	    	ShowDatasContainer.removeAllViews();
	    	getLocalActivityManager().destroyActivity(labels, true);
	    	View v=getLocalActivityManager().startActivity(lable, intent1).getDecorView();
	    	v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
	    	ShowDatasContainer.addView(v);
	    	
	    }
	 //Container添加View
public void changeContainerView(View v){
	    	toActivity("first",Sheet.class);
	    	labels="first";
	    	ShowDatasContainer.setBackgroundColor(Color.WHITE);
   if(v==GraphButton){
		toActivity("second",Graph.class);
		labels="second";
		ShowDatasContainer.setBackgroundColor(Color.GRAY); 
	} 
}
//获取时间
	public void getDateTime(){
		if(Month1.getText().toString().equals("")){day=Year1.getText().toString();}
		else if(DayText.getText().toString().equals("")){day=Year1.getText().toString()+"-"+Month1.getText().toString();}
		else{day=Year1.getText().toString()+"-"+Month1.getText().toString()+"-"+baozhaungMenthod();}
		
	}
	public void PickMethod(){
		if(ExceptStr.equals("表格")){
			SheetButton.setTextColor(Color.RED);
			GraphButton.setTextColor(Color.BLACK);
		changeContainerView(SheetButton);}
		else if(ExceptStr.equals("统计图")){
			SheetButton.setTextColor(Color.BLACK);
			GraphButton.setTextColor(Color.RED);
			changeContainerView(GraphButton);
		}
	}
	//反馈给Sheet和GraphContainer
	public static String returnTime(){
		return day;
	}
	public static  String returnselectType(){
		return str;
	}
	public static  String returnSpinnerData(){
		return SpinnerData;
	}
}