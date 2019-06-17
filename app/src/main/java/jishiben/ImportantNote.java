package jishiben;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import loginOrRegister.Main;
import sqlite.JSBINSqlite;
import Adapters.JSBINAdapter;
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

import entity.ImportantNoteE;

public class ImportantNote extends Activity implements OnClickListener {
    private ListView JSBINoteListView;
    private EditText JSBINEditText,INoteContext,PasswordEdit;
    private ImageView JSBINSearchText,AddColumn,ExpandDown;
    private List<ImportantNoteE> ImportNoteDatas;
    private JSBINSqlite jsbinsqlite;
    private SQLiteDatabase db;
    private TextView Save,Update,JSBImportantTextName,JiaMiMethod,SavePassword,CancelPassword,JSBImportantTime;
    private String time;
    private int biaozhi,Position,DataSize;
    private RadioButton Addkey,NoAddkey;
    private LinearLayout AddKeyLayout,PassWordLayout;
    private RelativeLayout JiaMiLayout;
    private AlertDialog dialog;
    private String PoupDatas[]={"自定义","用登录密码"};
    private PopupWindow popupWindow;
    private View view;//ListView的脚本View；
    protected void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.jishibenimportantnote);
    	Date date=new Date(System.currentTimeMillis());
    	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	time=format.format(date);
    	JSBINoteListView=(ListView)findViewById(R.id.JSBINoteListView);
    	JSBINEditText=(EditText)findViewById(R.id.JSBINEditText);
    	JSBINSearchText=(ImageView)findViewById(R.id.JSBINSearchText);
    	JSBINSearchText.setOnClickListener(this);
    	ImportNoteDatas=new ArrayList<ImportantNoteE>();
    	jsbinsqlite=new JSBINSqlite(this, "JSBINNote.db", null, 1);
		db=jsbinsqlite.getReadableDatabase();
		ImportNoteDatas=jsbinsqlite.QueryDatas(db, time);
		DataSize=ImportNoteDatas.size();
		ListViewAddFooterView();
		ListViewAddDatas();
    }
	
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.AddColumn:
			if(INoteContext.getText().toString().trim().length()!=0){
			ListViewAddFooterView();
			ListViewAddDatas();}
			
			break;
		case R.id.JSBImportantSave:
			if(INoteContext.getText().toString().trim().length()==0){
				Toast.makeText(this, "事件不能为空", 1000).show();
			}else{
				if(Save.getText().toString().equals("保存")){
				AddSaveDialog();}
				if(Save.getText().toString().equals("修改")){
					ImportNoteDatas.get(ImportNoteDatas.size()-1).setContext(INoteContext.getText().toString());
				}
				biaozhi=0;
			}
			break;
		case R.id.JSBImportantUpdate:
			INoteContext.setFocusable(true);
			Save.setText("修改");
			break;
		case R.id.SavePassword:
			SaveData();
			break;
		case R.id.CancelPassword:
			dialog.dismiss();
			break;
		case R.id.ExpandDown:
			initPop(JiaMiMethod);
			if (popupWindow != null && !popupWindow.isShowing()) {
				popupWindow.showAsDropDown(JiaMiLayout, 0, 0);
			}
			else{
				popupWindow.dismiss();
			}
			break;
		case R.id.Addkey:
			AddKeyLayout.setVisibility(View.VISIBLE);
			if(Position==0){
				PassWordLayout.setVisibility(View.VISIBLE);
			}else{
				PassWordLayout.setVisibility(View.GONE);
			}
			break;
		case R.id.NoAddkey:
			AddKeyLayout.setVisibility(View.GONE);
			break;
		case R.id.JSBINSearchText:
			if(JSBINEditText.getText().toString().trim().length()!=0 && !JSBINEditText.getText().toString().equals(time)){
				ImportNoteDatas.clear();
				ImportNoteDatas=jsbinsqlite.QueryDatas(db, JSBINEditText.getText().toString());
				JSBINoteListView.removeFooterView(view);
				 JSBINAdapter adapter=new JSBINAdapter(this, ImportNoteDatas);
			   	 JSBINoteListView.setAdapter(adapter);
				
			}else{
				Toast.makeText(this, "请输入正确的查询条件!", 1000).show();
			}
			break;
		}

	}
 public void ListViewAddDatas(){
    if(biaozhi==0){
   	
    	if(DataSize!=ImportNoteDatas.size()){
    		jsbinsqlite.AddNote(db, ImportNoteDatas.get(ImportNoteDatas.size()-1));
    		DataSize=ImportNoteDatas.size();
    	}
    	 JSBINAdapter adapter=new JSBINAdapter(this, ImportNoteDatas);
      	 JSBINoteListView.setAdapter(adapter);

    }else{
    	Toast.makeText(this, "前一事件未保存，请先保存前一事件吗，以免数据丢失!", 1000).show();
    }
       biaozhi=1; 
 }
 public void ListViewAddFooterView(){
	  view=LayoutInflater.from(this).inflate(R.layout.jishibenimportantnoteitem, null);
	 AddColumn=(ImageView)view.findViewById(R.id.AddColumn);
     JSBImportantTextName=(TextView)view.findViewById(R.id.JSBImportantTextName);
     JSBImportantTime=(TextView)view.findViewById(R.id.JSBImportantTime);
     Save=(TextView)view.findViewById(R.id.JSBImportantSave);
     Update=(TextView)view.findViewById(R.id.JSBImportantUpdate);
     INoteContext=(EditText)view.findViewById(R.id.JSBImportantTextContext);
     AddColumn.setOnClickListener(this);
     Save.setOnClickListener(this);
     Update.setOnClickListener(this);
     JSBImportantTextName.setText("事件"+(ImportNoteDatas.size()+1)+":");
     INoteContext.setText("");
     JSBImportantTime.setText(time);
     JSBINoteListView.addFooterView(view);
 }
 public void AddSaveDialog(){
	 AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(getParent(), R.style.Alert));
		View view = LayoutInflater.from(this).inflate(R.layout.jishibenimportnotejiami, null);
		builder.setView(view);
		dialog = builder.show();
		Addkey=(RadioButton)view.findViewById(R.id.Addkey);
		NoAddkey=(RadioButton)view.findViewById(R.id.NoAddkey);
		AddKeyLayout=(LinearLayout)view.findViewById(R.id.AddKeyLayout);
		PassWordLayout=(LinearLayout)view.findViewById(R.id.PassWordLayout);
		JiaMiLayout=(RelativeLayout)view.findViewById(R.id.JiaMiLayout);
		JiaMiMethod=(TextView)view.findViewById(R.id.JiaMiMethod);
		SavePassword=(TextView)view.findViewById(R.id.SavePassword);
		CancelPassword=(TextView)view.findViewById(R.id.CancelPassword);
		ExpandDown=(ImageView)view.findViewById(R.id.ExpandDown);
		PasswordEdit=(EditText)view.findViewById(R.id.PasswordEdit);
		SavePassword.setOnClickListener(this);
		CancelPassword.setOnClickListener(this);
		ExpandDown.setOnClickListener(this);
		Addkey.setOnClickListener(this);
		NoAddkey.setOnClickListener(this);
 }
 //加密下拉框
 private void initPop(final TextView textView) {						 
		ListView mlistView = new ListView(this);						
		ArrayAdapter<String> popDataAdapter = new ArrayAdapter<String>(this,R.layout.jsbinjiamipoupwindowitem, PoupDatas);						
		mlistView.setAdapter(popDataAdapter);	
		mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
								
			public void onItemClick(AdapterView<?>					
			parent, View view, int position, long id) {			
					textView.setText(PoupDatas[position]);
					Position=position;
					if(Position==1){
						PassWordLayout.setVisibility(View.GONE);
					}else{
						PassWordLayout.setVisibility(View.VISIBLE);
					}
					popupWindow.dismiss(); } });
							popupWindow = new PopupWindow(mlistView,JiaMiLayout.getWidth(),
							
						ActionBar.LayoutParams.WRAP_CONTENT, true);
								
		      popupWindow.setOnDismissListener(new
								
				PopupWindow.OnDismissListener() { 
								
			public void onDismiss() {
								
					popupWindow.dismiss(); } });
		
		popupWindow.setAnimationStyle(R.style.popmenu_animation);
								
		popupWindow.setFocusable(true);
								
		popupWindow.setOutsideTouchable(true); }
 
 
			//保存数据					
		public void SaveData(){
			ImportantNoteE note=new ImportantNoteE();
			if(ImportNoteDatas.size()!=0){
				note.setId(ImportNoteDatas.get(ImportNoteDatas.size()-1).getId()+1);
			}else{
				note.setId(1);
			}
			note.setId(ImportNoteDatas.get(ImportNoteDatas.size()-1).getId()+1);
			note.setContext(INoteContext.getText().toString());
			note.setTime(time);
			note.setUserName(Main.returnName());
			note.setPassword(PasswordEdit.getText().toString());
			if(NoAddkey.isChecked()){
				note.setPassword("");
				note.setStatus(0);
				
			}
			else if(Addkey.isChecked()){
				if(Position==1){
					note.setPassword(Main.returnPsd());
				}else{
					note.setPassword(PasswordEdit.getText().toString());
				}
				note.setStatus(1);
				
			}
			ImportNoteDatas.add(note);
			jsbinsqlite.AddNote(db, note);
			dialog.dismiss();
			JSBINoteListView.removeFooterView(view);
			 JSBINAdapter adapter=new JSBINAdapter(this, ImportNoteDatas);
		   	 JSBINoteListView.setAdapter(adapter);
			
		}
}
