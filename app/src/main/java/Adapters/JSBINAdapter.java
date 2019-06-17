package Adapters;

import java.util.List;

import loginOrRegister.Main;
import sqlite.JSBINSqlite;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
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

public class JSBINAdapter extends BaseAdapter implements OnClickListener{
    private Context context;
    private List<ImportantNoteE> datas;
    private ViewHolder holder;
	private JSBINSqlite jsbinsqlite;
	private SQLiteDatabase db;
	 private RadioButton Addkey,NoAddkey;
	    private LinearLayout AddKeyLayout,PassWordLayout;
	    private RelativeLayout JiaMiLayout;
	    private AlertDialog dialog;
	    private EditText PasswordEdit;
	    private ImageView ExpandDown;
	    private TextView JiaMiMethod,SavePassword,CancelPassword;
	    private String PoupDatas[]={"自定义","用登录密码"};
	    private PopupWindow popupWindow;
	    private int Position;
	public JSBINAdapter(Context context, List<ImportantNoteE> datas) {
		super();
		this.context = context;
		this.datas = datas;
		jsbinsqlite=new JSBINSqlite(context, "JSBINNote.db", null, 1);
		db=jsbinsqlite.getReadableDatabase();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	
	public View getView(final int position, View view, ViewGroup parent) {
		if(view==null){
			holder=new ViewHolder();
			view=LayoutInflater.from(context).inflate(R.layout.jishibenimportantnoteitem, null);
			holder.add=(ImageView)view.findViewById(R.id.AddColumn);
			holder.Context=(EditText)view.findViewById(R.id.JSBImportantTextContext);
			holder.name=(TextView)view.findViewById(R.id.JSBImportantTextName);
			holder.update=(TextView)view.findViewById(R.id.JSBImportantUpdate);
			holder.save=(TextView)view.findViewById(R.id.JSBImportantSave);
			holder.time=(TextView)view.findViewById(R.id.JSBImportantTime);
			view.setTag(holder);
		}else{
			holder=(ViewHolder)view.getTag();
		}
		final int status=datas.get(position).getStatus();
		
		if(status==0){
			holder.add.setImageResource(R.drawable.nojiamiimage);
		holder.Context.setText(datas.get(position).getContext());}
		if(status==1){
			holder.add.setImageResource(R.drawable.jiamiimage);
			holder.Context.setText("数据已加密!");
		}
		holder.Context.setFocusable(false);
		holder.save.setText("修改");
		holder.time.setText(datas.get(position).getTime());
		holder.update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(status==0){
					holder.Context.setFocusable(true);
					holder.Context.setFocusableInTouchMode(true);
					holder.Context.requestFocus();
				}else{
					UpdateDialog(position);
				}
			
				
			}
		});
		holder.save.setOnClickListener(new OnClickListener() {
			
			
			public void onClick(View arg0) {
			   if(status==0){
				   SaveDialog(position);
			   }
			   else{
				   jsbinsqlite.UpdateNoteContext(db, holder.Context.getText().toString(), datas.get(position).getId()) ;
			   }
				
			}
		});
		return view;
	}
class ViewHolder{
	ImageView add;
	EditText Context;
	TextView name,update,save,time;
	
}
public void UpdateDialog(final int position){
	 AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(context, R.style.Alert));
		View view = LayoutInflater.from(context).inflate(R.layout.jishibenimportantnotejiemi, null);
		final EditText JieMiEdit=(EditText)view.findViewById(R.id.JieMiEdit);
		TextView UpdatePassword=(TextView)view.findViewById(R.id.UpdatePassword);
		TextView OpenPassword=(TextView)view.findViewById(R.id.OpenPassword);
		TextView CancelOpenPassword=(TextView)view.findViewById(R.id.CancelOpenPassword);
		builder.setView(view);
	  final  AlertDialog dialog = builder.show();
	    OpenPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!JieMiEdit.getText().toString().equals(datas.get(position).getPassword())){
				Toast.makeText(context, "密钥错误，请重试", 1000).show();
			}else{
				holder.Context.setFocusable(true);
				holder.Context.setText(datas.get(position).getContext());
				holder.Context.setFocusableInTouchMode(true);
				holder.Context.requestFocus();
				holder.add.setImageResource(R.drawable.nojiamiimage);
				dialog.dismiss();
			}
				}
		}) ;
	    CancelOpenPassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
				
			}
		});
	    UpdatePassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			UpdatePwd(position);
				
			}
		});
}
public void SaveDialog(final int position){
	AlertDialog.Builder builder = new AlertDialog.Builder(
			new ContextThemeWrapper(context, R.style.Alert));
	View view = LayoutInflater.from(context).inflate(R.layout.jishibenimnotejiemicheck, null);
	TextView NOJiaMi=(TextView)view.findViewById(R.id.NOJiaMi);
	TextView JiaMi=(TextView)view.findViewById(R.id.JiaMi);
	builder.setView(view);
   final  AlertDialog dialog = builder.show();
   NOJiaMi.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		jsbinsqlite.UpdateNoteContext(db, holder.Context.getText().toString(), datas.get(position).getId()) ;
		dialog.dismiss();
	}
});
   JiaMi.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
	 JiaMiDailog(position);
		
	}
});
}
public void UpdatePwd(final int position){
	AlertDialog.Builder builder = new AlertDialog.Builder(
			new ContextThemeWrapper(context, R.style.Alert));
	View view = LayoutInflater.from(context).inflate(R.layout.jishibenimnoteupdatekey, null);
	final EditText OldJieMiEdit=(EditText)view.findViewById(R.id.OldJieMiEdit);
	final EditText NewJieMiEdit=(EditText)view.findViewById(R.id.NewJieMiEdit);
	TextView UpdatePwd=(TextView)view.findViewById(R.id.UpdatePwd);
	TextView CancelUpdatePassword=(TextView)view.findViewById(R.id.CancelUpdatePassword);
	builder.setView(view);
   final  AlertDialog dialog = builder.show();
    UpdatePwd.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			 if(!OldJieMiEdit.getText().toString().equals(datas.get(position).getPassword())){
			    	Toast.makeText(context, "密钥错误，请重试", 1000).show();
			    }
			 else {
				 if(NewJieMiEdit.getText().toString().trim().length()==0){
				 jsbinsqlite.UpdateNotePwdStatus(db, 0, datas.get(position).getId());
			 }else{
				 jsbinsqlite.UpdateNotePwd(db, NewJieMiEdit.getText().toString(), datas.get(position).getId()); 
			    
			 }
				 Toast.makeText(context, "修改成功", 1000).show();
				 dialog.dismiss();
			 }
		}
	});
    CancelUpdatePassword.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			dialog.dismiss();
			
		}
	});
   
}
public void JiaMiDailog(final int position){
	AlertDialog.Builder builder = new AlertDialog.Builder(
			new ContextThemeWrapper(context, R.style.Alert));
	View view = LayoutInflater.from(context).inflate(R.layout.jishibenimportnotejiami, null);
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
		CancelPassword.setOnClickListener(this);
		ExpandDown.setOnClickListener(this);
		Addkey.setOnClickListener(this);
		NoAddkey.setOnClickListener(this);
		 SavePassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
					 jsbinsqlite.UpdateNotePwdStatus(db, 1, datas.get(position).getId());
					if(Position==0){
						if(PasswordEdit.getText().toString().trim().length()!=0){
						 jsbinsqlite.UpdateNotePwd(db, PasswordEdit.getText().toString(), datas.get(position).getId());
						 dialog.dismiss();
						 Toast.makeText(context, "密钥设置成功！", 1000).show();} 
						else{
							Toast.makeText(context, "密钥不能为空！", 1000).show();
						}
					}else{
						 jsbinsqlite.UpdateNotePwd(db,Main.returnPsd(), datas.get(position).getId());
						 Toast.makeText(context, "密钥设置成功！", 1000).show();
						 dialog.dismiss();
						
					}
					
				}
				
			
		});
		 
}

@Override
public void onClick(View view) {
	switch(view.getId()){

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
		PassWordLayout.setVisibility(View.INVISIBLE);
	}
	break;
case R.id.NoAddkey:
	dialog.dismiss();
	break;
	
}}
//加密下拉框
private void initPop(final TextView textView) {						 
		ListView mlistView = new ListView(context);						
		ArrayAdapter<String> popDataAdapter = new ArrayAdapter<String>(context,R.layout.jsbinjiamipoupwindowitem, PoupDatas);						
		mlistView.setAdapter(popDataAdapter);	
		mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
								
			public void onItemClick(AdapterView<?>					
			parent, View view, int position, long id) {			
					textView.setText(PoupDatas[position]);
					Position=position;
					popupWindow.dismiss(); 
					if(Position==0){
						PassWordLayout.setVisibility(View.VISIBLE);
					}else{
						PassWordLayout.setVisibility(View.INVISIBLE);
					}
					} });
							popupWindow = new PopupWindow(mlistView,JiaMiLayout.getWidth(),
							
						ActionBar.LayoutParams.WRAP_CONTENT, true);
								
		      popupWindow.setOnDismissListener(new
								
				PopupWindow.OnDismissListener() { 
								
			public void onDismiss() {
								
					popupWindow.dismiss(); } });
		
		popupWindow.setAnimationStyle(R.style.popmenu_animation);
								
		popupWindow.setFocusable(true);
								
		popupWindow.setOutsideTouchable(true); }

}
