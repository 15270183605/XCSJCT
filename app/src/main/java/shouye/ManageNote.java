package shouye;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sqlite.ScheduleSQLite;
import Adapters.DateListAdapter;
import Adapters.NoteTitleAdapter;
import Adapters.ScheduleAdapter1;
import Adapters.SpinnerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.Schedule;

public class ManageNote extends Activity implements OnClickListener {
	private GridView ScheduleView,NoteTitleGridView;
	private ScheduleAdapter1 adapter;
	private List<String> listtime, notelist;
	private TextView addYear1, cutYear1, addMonth1, cutMonth1, Year1, Month1,
			NoteTitle, WeekCountText;
	private LinearLayout DayLayout, BottomLayout, ShowNoteNodata;
	private CheckBox addNote;
	private Spinner spinner1;
	private int number;// 暂存月份的变量
	private EditText DayText;
	private ImageView add, MakeTime, cutWeek, addWeek;
	private ScrollView Scrollview;
	private Button save, update, exit;
	private ListView datelistview;
	private DateListAdapter datelistadapter;
	private ScheduleSQLite schedulesqlite;
	private SQLiteDatabase db;
	private LayoutInflater inflater1;
	private int WeekCount = 1;
	private static String day = "";// 用于初始化的时间变量
	private AlertDialog Dialog;
	private String time;// 用于查询日程的时间
	private int flag;// 判断是保存还是删除(1表示删除，0表示)
	private String selectType;
	private EditText maketime, earlymorning, morning, noon, afternoon, evening;
	private String weeks[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
	private List<Schedule> schedulelist1;
    private String NoteTitleStr[]={"早上","上午","中午","下午","晚上"};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.makenote1);
		init();
		getTime();
		queryByMonth();

	}
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.addYear1:
			Year1.setText(String.valueOf(Integer.valueOf(Year1.getText()
					.toString()) + 1));
			if (selectType.equals("按周排")) {
				queryByWeek();
			} else {
				queryByMonth();
			}
			break;
		case R.id.cutYear1:
			Year1.setText(String.valueOf(Integer.valueOf(Year1.getText()
					.toString()) - 1));
			if (selectType.equals("按周排")) {
				queryByWeek();
			} else {
				queryByMonth();
			}
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
			if (selectType.equals("按周排")) {
				queryByWeek();
			} else {
				queryByMonth();
			}
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
			if (selectType.equals("按周排")) {
				queryByWeek();
			} else {
				queryByMonth();
			}
			break;
		case R.id.add:
			flag = 0;
			setAddNoteDialog();
           
			break;
		case R.id.MakeTime:
			ChooseCalendar();
			break;
		case R.id.save:
			if (flag == 0) {
				save();
				if (!addNote.isChecked()) {
					Dialog.dismiss();
				}

			} else if (flag == 1) {
				delete();
				Dialog.dismiss();
			}
			if (selectType.equals("按周排")) {
				queryByWeek();
			} else {
				queryByMonth();
			}

			break;
		case R.id.Update:
			update();
			if (selectType.equals("按周排")) {
				queryByWeek();
			} else {
				queryByMonth();
			}
			Dialog.dismiss();
			break;
		case R.id.exit:
			Dialog.dismiss();
			break;
		case R.id.cutWeek:
			WeekCount--;
			addWeek.setVisibility(View.VISIBLE);
			if (WeekCount == 1) {
				cutWeek.setVisibility(View.GONE);
			}
			queryByWeek();
			break;
		case R.id.addWeek:
			WeekCount++;
			cutWeek.setVisibility(View.VISIBLE);
			if (WeekCount == 5) {
				addWeek.setVisibility(View.GONE);
			}
			queryByWeek();
			break;
		}

	}
    //控件初始化
	public void init() {
		addYear1 = (TextView) findViewById(R.id.addYear1);
		cutYear1 = (TextView) findViewById(R.id.cutYear1);
		addMonth1 = (TextView) findViewById(R.id.addMonth1);
		cutMonth1 = (TextView) findViewById(R.id.cutMonth1);
		Year1 = (TextView) findViewById(R.id.Year1);
		Month1 = (TextView) findViewById(R.id.Month1);
		WeekCountText = (TextView) findViewById(R.id.WeekCount);
		DayText = (EditText) findViewById(R.id.DayText);
		DayLayout = (LinearLayout) findViewById(R.id.DayLayout);
		BottomLayout = (LinearLayout) findViewById(R.id.BottomLayout);
		add = (ImageView) findViewById(R.id.add);
		cutWeek = (ImageView) findViewById(R.id.cutWeek);
		addWeek = (ImageView) findViewById(R.id.addWeek);
		ShowNoteNodata = (LinearLayout) findViewById(R.id.ShowNoteNodata);
		Scrollview = (ScrollView) findViewById(R.id.Scrollview);
		datelistview = (ListView) findViewById(R.id.datelistview);
		ScheduleView = (GridView) findViewById(R.id.ScheduleView);
		NoteTitleGridView=(GridView)findViewById(R.id.NoteTitleGridView);
		add.setOnClickListener(this);
		addYear1.setOnClickListener(this);
		cutYear1.setOnClickListener(this);
		cutMonth1.setOnClickListener(this);
		addMonth1.setOnClickListener(this);
		add.setOnClickListener(this);
		cutWeek.setOnClickListener(this);
		addWeek.setOnClickListener(this);
		schedulesqlite = new ScheduleSQLite(this, "Schedule.db", null, 1);
		db = schedulesqlite.getReadableDatabase();
		listtime = new ArrayList<String>();
		notelist = new ArrayList<String>();
		schedulelist1 = new ArrayList<Schedule>();
		getTime();
		NoteTitleAdapter adapter=new NoteTitleAdapter(this, NoteTitleStr);
	    NoteTitleGridView.setAdapter(adapter);
		spinnerDatas();
		setEditTextChange();
		datelistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (NoteOutDate(new SimpleDateFormat("yyyy-MM-dd")
						.format(schedulelist1.get(position).getTime())) == 0) {
					Toast.makeText(ManageNote.this, "抱歉！日程已经发生，您不能对其进行操作。", 1000)
							.show();
				} else {
					flag = 1;
					time = Year1.getText().toString() + "-"
							+ Month1.getText().toString() + "-"
							+ listtime.get(position);
					setAddNoteDialog();
				}
			}
		});
		ScheduleView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				GridViewSetDialog(position);
			}
		});
	}

	// 按月查询数据
	public void queryByMonth() {
		getDateTime();
		schedulelist1 = schedulesqlite.queryByTime(db, day);
		final List<String> Timelist = new ArrayList<String>();
		List<String> Notelist = new ArrayList<String>();
		for (int i = 0; i < schedulelist1.size(); i++) {
			Schedule schedule = new Schedule();
			schedule = schedulelist1.get(i);
			Timelist.add(String.valueOf(schedule.getTime().getDate()));
			Notelist.add(schedule.getEarlyMorning());
			Notelist.add(schedule.getMorning());
			Notelist.add(schedule.getNoon());
			Notelist.add(schedule.getAfternoon());
			Notelist.add(schedule.getEvening());
		}
		if (Notelist.size() == 0) {
			Scrollview.setVisibility(View.GONE);
			ShowNoteNodata.setVisibility(View.VISIBLE);
		} else {
			Scrollview.setVisibility(View.VISIBLE);
			ShowNoteNodata.setVisibility(View.GONE);
			adapter = new ScheduleAdapter1(this, Notelist);
			ScheduleView.setAdapter(adapter);
			datelistadapter = new DateListAdapter(this, Timelist);
			datelistview.setAdapter(datelistadapter);
			listtime = Timelist;
			notelist = Notelist;
			setListViewHeight();
		}

	}
//按周查询数据
	public void queryByWeek() {
		Calendar cal = Calendar.getInstance();
		getDateTime();
		List<Schedule> schedulelist1 = null;
		if (WeekCount == 1) {
			schedulelist1 = schedulesqlite.queryByTime(db, day + "-" + "01",
					day + "-" + "07");
			WeekCountText.setText("1");
		}
		if (WeekCount == 2) {
			schedulelist1 = schedulesqlite.queryByTime(db, day + "-" + "08",
					day + "-" + "14");
			WeekCountText.setText("2");
		}
		if (WeekCount == 3) {
			schedulelist1 = schedulesqlite.queryByTime(db, day + "-" + "15",
					day + "-" + "21");
			WeekCountText.setText("3");
		}
		if (WeekCount == 4) {
			schedulelist1 = schedulesqlite.queryByTime(db, day + "-" + "22",
					day + "-" + "28");
			WeekCountText.setText("4");
		}
		if (WeekCount == 5) {
			schedulelist1 = schedulesqlite.queryByTime(db, day + "-" + "29",
					day + "-" + GetDay(day));
			WeekCountText.setText("5");
		}
		final List<String> Timelist = new ArrayList<String>();
		final List<String> timelist = new ArrayList<String>();
		List<String> Notelist = new ArrayList<String>();
		for (int i = 0; i < schedulelist1.size(); i++) {
			Schedule schedule = new Schedule();
			schedule = schedulelist1.get(i);
			cal.setTime(schedule.getTime());
			int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
			// SimpleDateFormat format=new SimpleDateFormat("EEEE");获取星期几
			timelist.add(weeks[week_index]);
			Timelist.add(String.valueOf(schedule.getTime().getDate()));
			Notelist.add(schedule.getEarlyMorning());
			Notelist.add(schedule.getMorning());
			Notelist.add(schedule.getNoon());
			Notelist.add(schedule.getAfternoon());
			Notelist.add(schedule.getEvening());
		}
		if (Notelist.size() == 0) {
			Scrollview.setVisibility(View.GONE);
			ShowNoteNodata.setVisibility(View.VISIBLE);
		} else {
			Scrollview.setVisibility(View.VISIBLE);
			ShowNoteNodata.setVisibility(View.GONE);
			adapter = new ScheduleAdapter1(this, Notelist);
			ScheduleView.setAdapter(adapter);
			datelistadapter = new DateListAdapter(this, timelist);
			datelistview.setAdapter(datelistadapter);
			listtime = Timelist;
			notelist = Notelist;
			setListViewHeight();
		}

	}

	// 动态更改ListView的高度;
	public void setListViewHeight() {
		ListAdapter listadapter = datelistview.getAdapter();
		if (listadapter == null) {
			return;
		}
		int height = 0;
		for (int i = 0; i < listadapter.getCount(); i++) {
			View listitem = listadapter.getView(i, null, datelistview);
			listitem.measure(0, 0);
			height += listitem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = datelistview.getLayoutParams();
		params.height = height
				+ (datelistview.getDividerHeight() * (listadapter.getCount() - 1));
		datelistview.setLayoutParams(params);
	}

	// 设置GridView弹出框
	public void GridViewSetDialog(int number) {
		inflater1 = (LayoutInflater) ManageNote.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		final int num = number % 5;
		String time = Year1.getText().toString() + "-"
				+ Month1.getText().toString() + "-" + listtime.get(number / 5);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		Date datetime = null;
		Date date1 = null;
		try {
			datetime = simpleDateFormat.parse(time);
			date1 = simpleDateFormat.parse(simpleDateFormat.format(date));
		} catch (ParseException e) {

			e.printStackTrace();
		}
		long currenttime = date1.getTime();
		long Time = datetime.getTime();
		if (Time >= currenttime) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					new ContextThemeWrapper(ManageNote.this, R.style.Alert));
			View view = inflater1.inflate(R.layout.gridviewdialog, null);
			builder.setView(view);
			final AlertDialog dialog = builder.show();
			WindowManager.LayoutParams params = dialog.getWindow()
					.getAttributes();
			params.width = WindowManager.LayoutParams.WRAP_CONTENT;
			dialog.getWindow().setAttributes(params);
			final TextView notetime = (TextView) view
					.findViewById(R.id.edittime);
			final TextView noteTime = (TextView) view
					.findViewById(R.id.noteTime);
			final EditText editnote = (EditText) view
					.findViewById(R.id.editnote);
			Button cancel = (Button) view.findViewById(R.id.cancel);
			Button update = (Button) view.findViewById(R.id.update);
			if (num == 0) {
				noteTime.setText("早上:");
			} else if (num == 1) {
				noteTime.setText("上午:");
			} else if (num == 2) {
				noteTime.setText("中午:");
			} else if (num == 3) {
				noteTime.setText("下午:");
			} else if (num == 4) {
				noteTime.setText("晚上:");
			}
			editnote.setText(notelist.get(number));
			notetime.setText(time);
			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					dialog.dismiss();

				}
			});
			update.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (num == 0) {
						schedulesqlite.updateSchedule1(db, editnote.getText()
								.toString(), notetime.getText().toString());
					} else if (num == 1) {
						schedulesqlite.updateSchedule2(db, editnote.getText()
								.toString(), notetime.getText().toString());
					} else if (num == 2) {
						schedulesqlite.updateSchedule3(db, editnote.getText()
								.toString(), notetime.getText().toString());
					} else if (num == 3) {
						schedulesqlite.updateSchedule4(db, editnote.getText()
								.toString(), notetime.getText().toString());
					} else if (num == 4) {
						schedulesqlite.updateSchedule5(db, editnote.getText()
								.toString(), notetime.getText().toString());
					}
					Toast.makeText(ManageNote.this, "更新成功", 1000).show();
					if (selectType.equals("按周排")) {
						queryByWeek();
					} else {
						queryByMonth();
					}
					dialog.dismiss();

				}

			});
		}

	}

	// 获取EditText中的时间;
	public String baozhaungMenthod() {
		String Dateday = "";
		if (!(DayText.getText().toString()).equals("")) {
			if ((Integer.parseInt(DayText.getText().toString()) < 10)
					&& (Integer.parseInt(DayText.getText().toString()) > 0)) {
				Dateday = "0" + DayText.getText().toString();
			} else if ((Integer.parseInt(DayText.getText().toString()) >= 10)
					&& (Integer.parseInt(DayText.getText().toString()) <= 31)) {
				Dateday = DayText.getText().toString();
			} else {
				Toast.makeText(ManageNote.this, "输入数据不合法!", 1000).show();
				DayText.setText("");
			}
		}
		return Dateday;

	}

	// 获取时间
	public void getDateTime() {
		if (Month1.getText().toString().equals("")) {
			day = Year1.getText().toString();
		} else if (DayText.getText().toString().equals("")) {
			day = Year1.getText().toString() + "-"
					+ Month1.getText().toString();
		} else {
			day = Year1.getText().toString() + "-"
					+ Month1.getText().toString() + "-" + baozhaungMenthod();
		}

	}

	// 初始化时间
	public void getTime() {
		Date date = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		number = cal.get(Calendar.MONTH) + 1;
		Year1.setText(String.valueOf(cal.get(Calendar.YEAR)));
		if (number < 10) {
			Month1.setText("0" + String.valueOf(number));
		} else {
			Month1.setText(String.valueOf(number));
		}
		if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
			DayText.setText("0"
					+ String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		} else {
			DayText.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
		}
	}

	// EditText文本内容监听
	public void setEditTextChange() {
		DayText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				getDateTime();
				queryByMonth();
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	// Spinner的响应
	public void spinnerDatas() {
		spinner1 = (Spinner) findViewById(R.id.selectTime);
		final String[] selectList = { "按月排", "按周排", "按日排" };
		final SpinnerAdapter adapter1 = new SpinnerAdapter(this,
				android.R.layout.simple_spinner_item, selectList);
		adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinner1.setAdapter(adapter1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				if (position == 0) {
					DayLayout.setVisibility(View.GONE);
					//getTime();
					DayText.setText("");
					BottomLayout.setVisibility(View.GONE);
					getDateTime();
					queryByMonth();
					selectType = selectList[position];
				}
				if (position == 1) {
					DayLayout.setVisibility(View.GONE);
					//getTime();
					DayText.setText("");
					WeekCount = 1;
					BottomLayout.setVisibility(View.VISIBLE);
					queryByWeek();
					selectType = selectList[position];
				}

				if (position == 2) {
					DayLayout.setVisibility(View.VISIBLE);
					//getTime();
					BottomLayout.setVisibility(View.GONE);
					getDateTime();
					queryByMonth();
					selectType = selectList[position];
				}

			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}
    //设置日程操作弹出框
	public void setAddNoteDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(ManageNote.this, R.style.NoteDialog));
		View view = LayoutInflater.from(this).inflate(R.layout.addnote, null);
		builder.setView(view);
		Dialog = builder.show();
		NoteTitle = (TextView) view.findViewById(R.id.NoteTitle);
		maketime = (EditText) view.findViewById(R.id.maketime);
		earlymorning = (EditText) view.findViewById(R.id.EarlyMorning);
		morning = (EditText) view.findViewById(R.id.Morning);
		noon = (EditText) view.findViewById(R.id.Noon);
		afternoon = (EditText) view.findViewById(R.id.Afternoon);
		evening = (EditText) view.findViewById(R.id.Evening);
		MakeTime = (ImageView) view.findViewById(R.id.MakeTime);
		save = (Button) view.findViewById(R.id.save);
		update = (Button) view.findViewById(R.id.Update);
		exit = (Button) view.findViewById(R.id.exit);
		addNote = (CheckBox) view.findViewById(R.id.addNote);
		MakeTime.setOnClickListener(this);
		save.setOnClickListener(this);
		update.setOnClickListener(this);
		exit.setOnClickListener(this);
		ChooseAddOrDelete();

	}
    //时间选择器
	public void ChooseCalendar() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
		final DatePickerDialog dialog = new DatePickerDialog(this,
				R.style.DateTime, new OnDateSetListener() {

					public void onDateSet(DatePicker view, int year, int month,
							int dayofmonth) {
						if (month < 9) {
							maketime.setText(year + "-" + "0" + (month + 1)
									+ "-" + dayofmonth);
						} else {
							maketime.setText(year + "-" + (month + 1) + "-"
									+ dayofmonth);
						}

					}
				}, year, month, dayofmonth);
		dialog.show();
	}
    //获取对应月份的最大天数
	public int GetDay(String time) {
		SimpleDateFormat format;
		int num;
		format = new SimpleDateFormat("yyyy-MM");
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		num = cal.getActualMaximum(Calendar.DATE);
		return num;
	}

	// 保存日程但只针对未发生时间段；
	public void save() {
		String time = maketime.getText().toString();
		String daylight = earlymorning.getText().toString();
		String mor = morning.getText().toString();
		String Noon = noon.getText().toString();
		String after = afternoon.getText().toString();
		String eve = evening.getText().toString();

		if (NoteOutDate(time) == 0) {
			Toast.makeText(this, "抱歉！日程安排表不针对过去时间", 1000).show();
		}
		if(schedulesqlite.QueryCount(db, time)!=0){
			Toast.makeText(this, "今天的日程安排已经存在，你可以去日程查看或修改", 1000).show();
		}else {
			schedulesqlite.addSchedule(db, time, daylight, mor, Noon, after,
					eve);
			Toast.makeText(this, "日程保存成功", 1000).show();
			recover();
		}
	}

	// 更新
	public void update() {

		String time = maketime.getText().toString();
		String daylight = earlymorning.getText().toString();
		String mor = morning.getText().toString();
		String Noon = noon.getText().toString();
		String after = afternoon.getText().toString();
		String eve = evening.getText().toString();
		if (NoteOutDate(time) == 0) {
			Toast.makeText(this, "日程已发生，禁止修改", 1000).show();
		} else if (NoteOutDate(time) == 1) {
			schedulesqlite.updateSchedule(db, daylight, mor, Noon, after, eve,
					time);
			Toast.makeText(this, "日程修改成功", 1000).show();
		}
	}

	// 删除
	public void delete() {
		schedulesqlite.delete(db, maketime.getText().toString());
		Toast.makeText(this, "删除成功", 1000).show();

	}
   //判断弹出框用途（增加或者更新）
	public void ChooseAddOrDelete() {
		if (flag == 0) {
			long time = System.currentTimeMillis();
			Date date = new Date(time);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			maketime.setText(sdf.format(date));
			save.setText("保存");
			update.setVisibility(View.GONE);
			addNote.setVisibility(View.VISIBLE);
		} else if (flag == 1) {
			NoteTitle.setText("日程操作");
			Schedule schedule = new Schedule();
			schedule = schedulesqlite.queryByTime1(db, time);
			maketime.setText(time);
			earlymorning.setText(schedule.getEarlyMorning());
			morning.setText(schedule.getMorning());
			noon.setText(schedule.getNoon());
			afternoon.setText(schedule.getAfternoon());
			evening.setText(schedule.getEvening());
			save.setText("删除");
			update.setVisibility(View.VISIBLE);
			addNote.setVisibility(View.GONE);
		}
	}

	// 判断当前所点击的日程时间是否已经过去
	public int NoteOutDate(String time) {

		int num = 0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		long currentTime = System.currentTimeMillis();
		Date date = new Date(currentTime);
		Date datetime = null;
		Date date1 = null;
		try {
			datetime = simpleDateFormat.parse(time);
			date1 = simpleDateFormat.parse(simpleDateFormat.format(date));
		} catch (ParseException e) {

			e.printStackTrace();
		}
		long currenttime = date1.getTime();
		long Time = datetime.getTime();
		if (Time < currenttime) {
			num = 0;
		} else {
			num = 1;
		}
		return num;
	}

	// 重置;
	public void recover() {
		maketime.setText("");
		earlymorning.setText("");
		morning.setText("");
		noon.setText("");
		afternoon.setText("");
		evening.setText("");
	}

}
