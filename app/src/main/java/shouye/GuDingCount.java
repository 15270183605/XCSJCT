package shouye;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loginOrRegister.Main;
import sqlite.GuDingSqlite;
import sqlite.GuDingXiSqlite;
import sqlite.MenuClassSQLite;
import sqlite.YingFuSQLite;
import sqlite.YingShouSQLite;
import Adapters.GuDingCountAdapter;
import Adapters.ShowMingXiDataAdapter;
import Dialog.JSBPDialog;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.GuDingEntity;
import entity.GuDingMingXi;

public class GuDingCount extends Activity implements OnClickListener {
     private ImageView ExitGuDing,GuExpandDown,CalImage;
	private LinearLayout AddProject;
	private ListView GuDingCountListView;
	private GuDingSqlite gudingsqlite;
	private SQLiteDatabase db,db1,db2,db3,db4;
	private List<GuDingEntity> GuDingDatas;
	private  GuDingCountAdapter adapter;
	private EditText ProjectEdit,GuDingCountEdit,EditMingXiType,EditMingXiCount;
	private TextView ClassTextView,BangDing,UpdateGuCount,CancelGuCount,ClassEdit,AddProjectMingXi,AddMingXi,CancelAdd,CalMenthod,CalCount,LookProjectMingXi;
	private RelativeLayout GuDingLayout;
	private RadioButton ZhengMingXi,FuMingXi;
	private  AlertDialog dialog,dialog1;
	private int biaozhi;//判断是添加项目还是查看项目
	private int ZhengFu;//判断是收益明细还是扣款明细
	private double TotalCount;
	 private String PoupDatas[]={"固定收入","固定支出","固定借款","固定贷款","固定实收","固定实付"};
	    private PopupWindow popupWindow;
	    private YingShouSQLite yingshousqlite;
	    private YingFuSQLite yingfusqlite;
	    private String time;
	    private GuDingXiSqlite gudingxisqlite;
	   public StringBuffer buffer1;
		public StringBuffer buffer2;
		private List<GuDingMingXi> gudingmingxiData;
		private MenuClassSQLite menuclasssqlite;
		private int Position;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	 requestWindowFeature(Window.FEATURE_NO_TITLE);
	 setContentView(R.layout.guding);
	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	 Date date=new Date(System.currentTimeMillis());
	  time=format.format(date);
	 ExitGuDing=(ImageView)findViewById(R.id.ExitGuDing);
	 AddProject=(LinearLayout)findViewById(R.id.AddProject);
	 GuDingCountListView=(ListView)findViewById(R.id.GuDingCountListView);
	 ExitGuDing.setOnClickListener(this);
	 AddProject.setOnClickListener(this);
	 gudingsqlite=new GuDingSqlite(this, "gudingcount.db", null, 1);
	 db=gudingsqlite.getReadableDatabase();
	 
	 GuDingDatas=new ArrayList<GuDingEntity>();
	 GuDingDatas=gudingsqlite.QueryAllData(db);
	adapter=new GuDingCountAdapter(this, GuDingDatas);
	 GuDingCountListView.setAdapter(adapter);
	 ListViewClick();
	}
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.ExitGuDing:
			finish();
			break;
		case R.id.AddProject:
			AddDialog();
			biaozhi=0;
			break;
		case R.id.BangDing:
			SaveData();
			dialog.dismiss();
			break;
		case R.id.UpdateGuCount:
			UpdateData();
			dialog.dismiss();
			break;
		case R.id.CancelGuCount:
			dialog.dismiss();
			break;
		case R.id.GuExpandDown:
			initPop(ClassEdit);
			if (popupWindow != null && !popupWindow.isShowing()) {
				popupWindow.showAsDropDown(GuDingLayout, 0, 0);
			}
			else{
				popupWindow.dismiss();
			}
			break;
		case R.id.AddProjectMingXi:
			if(ProjectEdit.getText().toString().trim().length()==0){
				Toast.makeText(this, "在添加明细之前，项目名不能为空!", 1000).show();
			}else{
				MingXiDialog();
			}
			break;
		case R.id.ZhengMingXi:
			ZhengFu=0;
			break;
		case R.id.FuMingXi:
			ZhengFu=1;
			break;
		case R.id.AddMingXi:
			if(ZhengFu==2){
				for(int i=0;i<gudingmingxiData.size();i++){
					gudingxisqlite.Add(db3, gudingmingxiData.get(i));
				}
				GuDingCountEdit.setText(String.valueOf(TotalCount));
				dialog1.dismiss();
			}else{
				AddMingXiData();
			}
			break;
		case R.id.CancelAdd:
			dialog1.dismiss();
			break;
		case R.id.CaulatorImage:
			if(buffer1.length()!=0 && buffer2.length()!=0){
				buffer1.append("="+ProjectEdit.getText().toString());
				buffer2.append("="+String.valueOf(TotalCount));
				CalMenthod.setText(buffer1.toString());
				CalCount.setText(buffer2.toString());
				ZhengFu=2;
			}else{
				Toast.makeText(this, "请先添加完整数据!", 1000).show();
			}
		     break;
		case R.id.LookProjectMingXi:
			GetMingXiData();
			break;
		}
		
	}
	//数据操作的弹出框
public void AddDialog(){
	AlertDialog.Builder builder = new AlertDialog.Builder(
			new ContextThemeWrapper(this, R.style.Alert));
	View view = LayoutInflater.from(this).inflate(R.layout.gudingdailog, null);
	ProjectEdit=(EditText)view.findViewById(R.id.ProjectEdit);
	ClassEdit=(TextView)view.findViewById(R.id.ClassEdit);
	GuDingCountEdit=(EditText)view.findViewById(R.id.GuDingCountEdit);
	ClassTextView=(TextView)view.findViewById(R.id.ClassTextView);
	BangDing=(TextView)view.findViewById(R.id.BangDing);
	UpdateGuCount=(TextView)view.findViewById(R.id.UpdateGuCount);
	CancelGuCount=(TextView)view.findViewById(R.id.CancelGuCount);
	AddProjectMingXi=(TextView)view.findViewById(R.id.AddProjectMingXi);
	LookProjectMingXi=(TextView)view.findViewById(R.id.LookProjectMingXi);
	GuDingLayout=(RelativeLayout)view.findViewById(R.id.GuDingLayout);
	GuExpandDown=(ImageView)view.findViewById(R.id.GuExpandDown);
	CancelGuCount.setOnClickListener(this);
	UpdateGuCount.setOnClickListener(this);
	BangDing.setOnClickListener(this);
	GuExpandDown.setOnClickListener(this);
	AddProjectMingXi.setOnClickListener(this);
	LookProjectMingXi.setOnClickListener(this);
	builder.setView(view);
	dialog = builder.show();
	yingshousqlite = new YingShouSQLite(this, "yingshou.db", null, 1);
	db1= yingshousqlite.getReadableDatabase();
	yingfusqlite = new YingFuSQLite(this, "yingfu.db", null, 1);
	db2= yingfusqlite.getReadableDatabase();
	menuclasssqlite = new MenuClassSQLite(this, "menuclass.db", null, 1);
	db4= menuclasssqlite.getReadableDatabase();
	gudingxisqlite=new GuDingXiSqlite(this, "gudingcountxj.db", null, 1);
	db3=gudingxisqlite.getReadableDatabase();
}
//保存数据
public  void SaveData(){
	if(BangDing.getText().toString().equals("绑定")){
	String classname=ClassEdit.getText().toString();
	String projectname=ProjectEdit.getText().toString();
	double Count=Double.parseDouble(GuDingCountEdit.getText().toString());
	int num=gudingsqlite.QueryNum(db, classname, projectname);
	if(num==0 && projectname.trim().length()!=0 && Count>0.0){
	if(classname.equals("固定收入") || classname.equals("固定支出") ){
		GuDingEntity entity=new GuDingEntity(classname,classname+"-"+projectname, Count);
		gudingsqlite.Add(db, entity);
		if(classname.equals("固定收入")){
			menuclasssqlite.addMenuClass(db4, "收入单", classname+"-"+projectname);
		}
		if(classname.equals("固定支出")){
			menuclasssqlite.addMenuClass(db4, "支出单", classname+"-"+projectname);
		}
		}
		else if(classname.equals("固定借款") || classname.equals("固定贷款")){
			GuDingEntity entity=new GuDingEntity(classname,classname+"-"+projectname, Count);
			gudingsqlite.Add(db, entity);
			if(classname.equals("固定借款")){
				yingshousqlite.addYingShou(db1, String.valueOf(yingshousqlite.TotalCount1(db1)+1),
						"0", "借款单",
						Count, "固定借款",classname+"-"+projectname, "未知", Main.returnName(), time,
						"1", "固定借款自动入单");
				menuclasssqlite.addMenuClass(db4, "借款单", classname+"-"+projectname);
			}else{
				
				yingfusqlite.addYingFu(db2, String.valueOf(yingfusqlite.TotalCount1(db2)+1),
						"0", "贷款单",
						Count, "固定贷款",classname+"-"+projectname, "未知", Main.returnName(), time,
						"1", "固定贷款自动入单");
				menuclasssqlite.addMenuClass(db4, "贷款单", classname+"-"+projectname);
			}
		}else if(classname.equals("固定实收")){
			int num1=gudingsqlite.QueryNum(db, "固定借款", classname+"-"+projectname);
			if(num1==0 || Count>yingshousqlite.getCount(db1, classname+"-"+projectname, "0")){
				Toast.makeText(this, "不存在对应的借款单或数据有误，单据生成失败", 1000).show();
			}else{
				GuDingEntity entity=new GuDingEntity(classname,classname+"-"+projectname, Count);
				gudingsqlite.Add(db, entity);
				menuclasssqlite.addMenuClass(db4, "实收单", classname+"-"+projectname);
			}
		}else if(classname.equals("固定实付")){
			int num1=gudingsqlite.QueryNum(db, "固定贷款",classname+"-"+projectname);
			if(num1==0 || Count>yingfusqlite.getCount(db2, classname+"-"+projectname, "0")){
				Toast.makeText(this, "不存在对应的贷款单或数据有误，单据生成失败", 1000).show();
			}else{
				GuDingEntity entity=new GuDingEntity(classname,classname+"-"+projectname, Count);
				gudingsqlite.Add(db, entity);
				menuclasssqlite.addMenuClass(db4, "实付单", classname+"-"+projectname);
			}
		}
	}else{
		Toast.makeText(this, "项目已存在或项目名称不能为空或数据无效!", 1000).show();
	}
}
	else{	
		gudingsqlite.Delete(db,GuDingDatas.get(Position).getId());
	}
	GuDingDatas=gudingsqlite.QueryAllData(db);
	adapter=new GuDingCountAdapter(this, GuDingDatas);
	 GuDingCountListView.setAdapter(adapter);
	
}
//更新数据
public void UpdateData(){
	String classname=ClassTextView.getText().toString();
	if(classname.equals("固定收入") || classname.equals("固定支出") || classname.equals("固定实收") || classname.equals("固定实付")){
		gudingsqlite.Update(db, Double.parseDouble(GuDingCountEdit.getText().toString()), ProjectEdit.getText().toString(), classname,GuDingDatas.get(Position).getId());
		
		
	}else if(classname.equals("固定借款") || classname.equals("固定贷款")){
		if(Double.parseDouble(GuDingCountEdit.getText().toString())>gudingsqlite.QueeryCount1(db, classname, ProjectEdit.getText().toString())){
			Toast.makeText(this, "固定总数据不能小于固定单笔数据!", 1000).show();
		}else{
			gudingsqlite.Update(db, Double.parseDouble(GuDingCountEdit.getText().toString()), ProjectEdit.getText().toString(), classname,GuDingDatas.get(Position).getId());
			
		}
	}
}
//处理明细数据弹出框
public void MingXiDialog(){
	AlertDialog.Builder builder = new AlertDialog.Builder(
			new ContextThemeWrapper(this, R.style.Alert));
	View view = LayoutInflater.from(this).inflate(R.layout.gudingmingxidialog, null);
	ZhengMingXi=(RadioButton)view.findViewById(R.id.ZhengMingXi);
	FuMingXi=(RadioButton)view.findViewById(R.id.FuMingXi);
	EditMingXiType=(EditText)view.findViewById(R.id.EditMingXiType);
	AddMingXi=(TextView)view.findViewById(R.id.AddMingXi);
	EditMingXiCount=(EditText)view.findViewById(R.id.EditMingXiCount);
	CancelAdd=(TextView)view.findViewById(R.id.CancelAdd);
	CalMenthod=(TextView)view.findViewById(R.id.CalMenthod);
	CalCount=(TextView)view.findViewById(R.id.CalCount);
	CalImage=(ImageView)view.findViewById(R.id.CaulatorImage);
	ZhengMingXi.setOnClickListener(this);
	FuMingXi.setOnClickListener(this);
	AddMingXi.setOnClickListener(this);
	CancelAdd.setOnClickListener(this);
	CalImage.setOnClickListener(this);
	builder.setView(view);
	dialog1 = builder.show();
	
	buffer1 = new StringBuffer();
	buffer2 = new StringBuffer();
	gudingmingxiData=new ArrayList<GuDingMingXi>();
	TotalCount=gudingsqlite.QueeryCount1(db, ClassEdit.getText().toString(), ProjectEdit.getText().toString());
	buffer1.append("原账");
	buffer2.append(String.valueOf(TotalCount));
	ZhengFu=0;
	ZhengMingXi.setChecked(true);
}
//添加明细数据
public void AddMingXiData(){
	if(EditMingXiType.getText().toString().trim().length()==0 || EditMingXiCount.getText().toString().trim().length()==0){
		Toast.makeText(this, "添加数据前请先检查数据的完整性!", 1000).show();
	}else{
		GuDingMingXi entity=new GuDingMingXi(ClassEdit.getText().toString()+"-"+ProjectEdit.getText().toString(), EditMingXiType.getText().toString(), Double.parseDouble(EditMingXiCount.getText().toString()), ZhengFu);
		gudingmingxiData.add(entity);
		//gudingxisqlite.Add(db3, entity);
		
		if(ZhengFu==0){
			buffer1.append("+"+EditMingXiType.getText().toString());
			buffer2.append("+"+EditMingXiCount.getText().toString());
			TotalCount=TotalCount+Double.parseDouble(EditMingXiCount.getText().toString());
		}else if(ZhengFu==1){
			buffer1.append("-"+EditMingXiType.getText().toString());
			buffer2.append("-"+EditMingXiCount.getText().toString());
			TotalCount=TotalCount-Double.parseDouble(EditMingXiCount.getText().toString());
		
	}
		EditMingXiType.setText("");
		EditMingXiCount.setText("");
		CalMenthod.setText(buffer1.toString());
		CalCount.setText(buffer2.toString());
		}
}
//listview点击事件
public void ListViewClick(){
	GuDingCountListView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			String classname=GuDingDatas.get(position).getClassName();
			biaozhi=1;
			AddDialog();
			GuDingLayout.setVisibility(View.GONE);
			ClassTextView.setVisibility(View.VISIBLE);
			ProjectEdit.setText(GuDingDatas.get(position).getProjectName());
			ClassTextView.setText(classname);
			GuDingCountEdit.setText(String.valueOf(GuDingDatas.get(position).getGuDingCount()));
			BangDing.setText("删除");
			Position=position;
		}
	});
}

//选择类型下拉框
private void initPop(final TextView textView) {						 
		ListView mlistView = new ListView(this);						
		ArrayAdapter<String> popDataAdapter = new ArrayAdapter<String>(this,R.layout.jsbinjiamipoupwindowitem, PoupDatas);						
		View view=LayoutInflater.from(this).inflate(R.layout.jsbinjiamipoupwindowitem, null);
		mlistView.setAdapter(popDataAdapter);	
		mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
								
			public void onItemClick(AdapterView<?>					
			parent, View view, int position, long id) {			
					textView.setText(PoupDatas[position]);
					popupWindow.dismiss(); } });
							popupWindow = new PopupWindow(mlistView,GuDingLayout.getWidth()-50,
							
						ActionBar.LayoutParams.WRAP_CONTENT, true);
							
		      popupWindow.setOnDismissListener(new
								
				PopupWindow.OnDismissListener() { 
								
			public void onDismiss() {
								
					popupWindow.dismiss(); } });
		
		popupWindow.setAnimationStyle(R.style.popmenu_animation);
								
		popupWindow.setFocusable(true);
								
		popupWindow.setOutsideTouchable(true); }
//查看明细数据
public void GetMingXiData(){
	View view=LayoutInflater.from(GuDingCount.this).inflate(R.layout.xijiedailog, null);
	TextView XiJiePName=(TextView)view.findViewById(R.id.XiJiePName);
	ListView XiJieListView=(ListView)view.findViewById(R.id.XiJieListView);
	
	 View headView =(View) LayoutInflater.from(GuDingCount.this).inflate(R.layout.mingxiitem,
				null);
    XiJieListView.addHeaderView(headView);
    	XiJiePName.setText(GuDingDatas.get(Position).getClassName()+"——明细");
    	 gudingmingxiData=new ArrayList<GuDingMingXi>();
    	 gudingmingxiData=gudingxisqlite.QueryDataByclassname(db3, GuDingDatas.get(Position).getProjectName());
	ShowMingXiDataAdapter  adapter = new ShowMingXiDataAdapter(GuDingCount.this, gudingmingxiData);
	 XiJieListView.setAdapter(adapter);
	JSBPDialog dialog=new JSBPDialog(GuDingCount.this,view);
	dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	dialog.setCancelable(true);
	dialog.show();

}
}
