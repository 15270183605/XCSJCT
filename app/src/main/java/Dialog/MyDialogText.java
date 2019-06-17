package Dialog;
import loginOrRegister.Main;
import sqlite.UserSQLite;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class MyDialogText extends Dialog implements android.view.View.OnClickListener{
    private Context context;
    //private TextView close;
    private ImageView textClose;
    private TextView notify;
   private String str;
   private UserSQLite usersqlite;
	private SQLiteDatabase db;
	public MyDialogText(Context context, String str) {
		super(context);
		this.context=context;
		this.str=str;
	}
	
   @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.dialogtext);
	//close=(TextView)findViewById(R.id.closeText);
	notify=(TextView)findViewById(R.id.notify);
	textClose=(ImageView)findViewById(R.id.closeText);
	textClose.setOnClickListener(this);
	notify.setText(str);
	usersqlite = new UserSQLite(context,"FamilyFinance.db",null,1);
	db=usersqlite.getReadableDatabase();
}

@Override
public void onClick(View view) {
	switch(view.getId()){
	case R.id.closeText:
		usersqlite.UpdateCount(db, Main.returnName(), Main.returnPsd(), 2);
		this.dismiss();
		break;
	}
	
}
   
	
}
