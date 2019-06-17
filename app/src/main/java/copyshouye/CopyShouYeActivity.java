package copyshouye;

import java.util.ArrayList;
import java.util.List;

import shouye.AddIncomeorPay;
import shouye.Count;
import shouye.Data;
import shouye.DataHorizontal;
import shouye.Graph;
import shouye.Help;
import shouye.ManageNote;
import shouye.MyIncome;
import shouye.MyPay;
import shouye.Sheet;
import shouye.SystemSet;
import shouye.YingShouYingFu;
import shouye.YingShouYingFuList;
import tool.CircleMenuLayout;
import tool.CircleMenuLayout.OnMenuItemClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

public class CopyShouYeActivity extends Activity implements OnClickListener{

	private CircleMenuLayout mCircleMenuLayout;
	private String mItemTexts[] = new String[] { "新增收入", "新增支出", "我的收入",
			"我的支出", "应收管理", "应付管理", "总账", "数据管理", "图表" };
	private int[] mItemImgs = new int[] { R.drawable.copyincome,
			R.drawable.copypay, R.drawable.copyincome2, R.drawable.copypay1,
			R.drawable.copyyingshou, R.drawable.copyyingfu,
			R.drawable.copycount, R.drawable.copydata, R.drawable.copygraph2 };
	private RadioButton add, query, sheet, Graph, ListVertical, ListHorizontal;
	private LayoutInflater inflater1;
	private static String msgselect;
	private String dataclassselect;
	private Spinner spinner1;
	private RadioGroup addorquery, sheetOrgraph;
	private TextView NoteText,HelpText,SetText,MoreText;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 自已切换布局文件看效果
		setContentView(R.layout.copyshouye);
		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
		inflater1 = (LayoutInflater) CopyShouYeActivity.this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		NoteText=(TextView)findViewById(R.id.NoteText);
		HelpText=(TextView)findViewById(R.id.HelpText);
		SetText=(TextView)findViewById(R.id.SetText);
		MoreText=(TextView)findViewById(R.id.MoreText);
		NoteText.setOnClickListener(this);
		HelpText.setOnClickListener(this);
		SetText.setOnClickListener(this);
		MoreText.setOnClickListener(this);
		mCircleMenuLayout
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public void itemClick(View view, int pos) {
						OnClickView(pos);
						Toast.makeText(CopyShouYeActivity.this,
								mItemTexts[pos], Toast.LENGTH_SHORT).show();
						
					}

					@Override
					public void itemCenterClick(View view) {
						Toast.makeText(CopyShouYeActivity.this, "欢迎使用家财通  ",
								Toast.LENGTH_SHORT).show();

					}
				});

	}
	public void OnClickView(int i){
		if(i==0)
		{
			Intent intent1 = new Intent();
			intent1.putExtra("number", 1);
			intent1.setClass(this, AddIncomeorPay.class);
			startActivity(intent1);	
		}
		if(i==1){
			Intent intent2 = new Intent();
			intent2.putExtra("number", 2);
			intent2.setClass(this, AddIncomeorPay.class);
			startActivity(intent2);
		}
		if(i==2){
			Intent intent3 = new Intent();
			intent3.putExtra("number", 3);
			intent3.setClass(this, MyIncome.class);
			startActivity(intent3);

		}
		if(i==3)
		{
			Intent intent4 = new Intent();
			intent4.putExtra("number", 4);
			intent4.setClass(this, MyPay.class);
			startActivity(intent4);
		}
		if(i==4){
			setYingShouFuDialog(5);
		}
		if(i==5){
			setYingShouFuDialog(6);
		}
		if(i==6)
		{
			setCountSelectDialog();
		}
		if(i==7){
			setDataDialog();
		}
		if(i==8){
			setGraphSelctDialog();
		}
	}
	public void onClick(View view) {
	switch(view.getId()){
	case R.id.NoteText:
		Intent intent10 = new Intent(this, ManageNote.class);
		startActivity(intent10);
		break;
	case R.id.HelpText:
		Intent intent12 = new Intent(this, Help.class);
		startActivity(intent12);
		break;
	case R.id.SetText:
		Intent intent11 = new Intent();
		intent11.putExtra("number", 11);
		intent11.setClass(this, SystemSet.class);
		startActivity(intent11);
		break;
	case R.id.MoreText:
		Toast.makeText(this, "很抱歉，功能还在完善中!", 1000).show();
		break;
	case R.id.ListVertical:
		spinner1.setVisibility(View.GONE);
		break;
	case R.id.ListHorizontal:
		spinner1.setVisibility(View.VISIBLE);
		break;
	}
		
	}
	public void setYingShouFuDialog(final int number) {
		View view1 = inflater1.inflate(R.layout.yingshouorfudialog, null);
		addorquery = (RadioGroup) view1.findViewById(R.id.addorquery);
		add = (RadioButton) view1.findViewById(R.id.add);
		query = (RadioButton) view1.findViewById(R.id.query);
		add.setOnClickListener(this);
		query.setOnClickListener(this);
		AlertDialog.Builder alert = new AlertDialog.Builder(
				new ContextThemeWrapper(getParent(), R.style.Alert));
		alert.setTitle("用户选择");
		alert.setView(view1);
		alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}

		});
		alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
				if (add.isChecked()) {
					Intent intent5 = new Intent();
					intent5.putExtra("number", number);
					intent5.setClass(CopyShouYeActivity.this, YingShouYingFu.class);
					startActivity(intent5);
				}
				if (query.isChecked()) {
					Intent intent6 = new Intent();
					intent6.putExtra("number", number);
					intent6.setClass(CopyShouYeActivity.this, YingShouYingFuList.class);
					startActivity(intent6);
				}
			}

		});
		alert.show();
	}

	public void setGraphSelctDialog() {
		View view2 = inflater1.inflate(R.layout.graphtitleselect, null);
		sheetOrgraph = (RadioGroup) view2.findViewById(R.id.SheetOrGraph);
		sheet = (RadioButton) view2.findViewById(R.id.Sheet);
		Graph = (RadioButton) view2.findViewById(R.id.Graph);
		sheet.setOnClickListener(this);
		Graph.setOnClickListener(this);
		sheet.isChecked();
		AlertDialog.Builder alert = new AlertDialog.Builder(
				new ContextThemeWrapper(getParent(), R.style.Alert));
		alert.setTitle("条件选择");
		alert.setView(view2);
		Spinner spinner = (Spinner) view2.findViewById(R.id.spinner);
		List<String> selectlist = new ArrayList<String>();
		selectlist.add("支出");
		selectlist.add("收入");
		selectlist.add("应收");
		selectlist.add("应付");
		selectlist.add("总账");
		selectlist.add("实收");
		selectlist.add("实付");
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, selectlist);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				msgselect = adapter.getItem(position);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}

		});
		alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
				if (sheet.isChecked()) {
					Intent intent = new Intent(CopyShouYeActivity.this, Sheet.class);
					startActivity(intent);
				} else if (Graph.isChecked()) {
					Intent intent9 = new Intent(CopyShouYeActivity.this,
							Graph.class);
					startActivity(intent9);
				}
			}

		});
		alert.show();
	}

	public void setCountSelectDialog() {
		View view = inflater1.inflate(R.layout.countselect, null);
		RadioGroup YesOrNo = (RadioGroup) view.findViewById(R.id.YesOrNo);
		final RadioButton Yes = (RadioButton) view.findViewById(R.id.Yes);
		final RadioButton No = (RadioButton) view.findViewById(R.id.No);
		Yes.setOnClickListener(this);
		No.setOnClickListener(this);
		AlertDialog.Builder alert = new AlertDialog.Builder(
				new ContextThemeWrapper(getParent(), R.style.Alert));
		alert.setTitle("特别提醒：");
		alert.setView(view);
		alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}

		});
		alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
				if (Yes.isChecked()) {
					Intent intent = new Intent(CopyShouYeActivity.this, Data.class);
					startActivity(intent);
				}
				if (No.isChecked()) {
					Intent intent = new Intent(CopyShouYeActivity.this, Count.class);
					startActivity(intent);
				}
			}

		});
		alert.show();
	}

	public void setDataDialog() {
		View view2 = inflater1.inflate(R.layout.dataselect, null);
		ListVertical = (RadioButton) view2.findViewById(R.id.ListVertical);
		ListHorizontal = (RadioButton) view2.findViewById(R.id.ListHorizontal);
		ListVertical.setOnClickListener(this);
		ListHorizontal.setOnClickListener(this);
		AlertDialog.Builder alert = new AlertDialog.Builder(
				new ContextThemeWrapper(getParent(), R.style.Alert));
		alert.setTitle("排列方式:");
		alert.setView(view2);
		spinner1 = (Spinner) view2.findViewById(R.id.ListSpinner);
		List<String> selectlist = new ArrayList<String>();
		selectlist.add("支出");
		selectlist.add("收入");
		selectlist.add("应收");
		selectlist.add("应付");
		selectlist.add("总账");
		selectlist.add("实收");
		selectlist.add("实付");
		selectlist.add("全部");
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, selectlist);
		adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long arg3) {
				dataclassselect = adapter.getItem(position);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		alert.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {

			}

		});
		alert.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
				if (ListHorizontal.isChecked()) {
					Intent intent = new Intent();
					intent.putExtra("dataclassslect", dataclassselect);
					intent.setClass(CopyShouYeActivity.this, DataHorizontal.class);
					startActivity(intent);
				} else if (ListVertical.isChecked()) {
					Intent intent9 = new Intent(CopyShouYeActivity.this, Data.class);
					startActivity(intent9);
				}
			}

		});
		alert.show();
	}
	public static String returnMsgSelect() {
		return msgselect;
	}
}
