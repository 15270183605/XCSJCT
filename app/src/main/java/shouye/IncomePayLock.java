package shouye;

import sqlite.IncomeSQLite;
import sqlite.PaySQLite;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

public class IncomePayLock extends Activity implements OnClickListener {
      private EditText incomeId,incomeclass,incomeamount,incomesource,makemenuperson,incomemaketime,incomemakenote,status;
      private TextView incomeId1,incomeclass1,incomeamount1,incomesource1,makemenuperson1,incomemaketime1,incomemakenote1,status1,incomemakemenu2,incomemakemenu21,textTitle;
      private Button lock,update,Exit,Exit1;
      private IncomeSQLite incomesqlite;
      private PaySQLite paysqlite;
      private SQLiteDatabase db,db1;
      private String id;
      private LinearLayout title1,title2;
      private int number;
    
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.incomeorpaylock);
    	Intent intent=getIntent();
		id=intent.getStringExtra("id");
		number=intent.getIntExtra("number", 0);
    	init();
    	if(number==3){
    		incomesqlite = new IncomeSQLite(this,"income.db",null,1);
    		db=incomesqlite.getReadableDatabase();
    	    Cursor cursor=incomesqlite.queryIncomeById(db, id);
    	if(cursor.moveToNext()){
        	if(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status")))==0){
        		incomeId.setText(cursor.getString(cursor.getColumnIndex("id")));
        		incomeclass.setText(cursor.getString(cursor.getColumnIndex("MenuName")));
        		incomeamount.setText(cursor.getString(cursor.getColumnIndex("count")));
        		incomesource.setText(cursor.getString(cursor.getColumnIndex("IncomeSource")));
        		makemenuperson.setText(cursor.getString(cursor.getColumnIndex("MakePerson")));
        		incomemaketime.setText(cursor.getString(cursor.getColumnIndex("Date")));
        		incomemakenote.setText(cursor.getString(cursor.getColumnIndex("MakeNote")));
        	    status.setText("已开始");
        		title1.setVisibility(View.VISIBLE);
        	
        	}
        	else if(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status")))==1){
        		lock();
        	}else if(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status")))==2){
        		incomeId1.setText(cursor.getString(cursor.getColumnIndex("id")));
	    		incomeclass1.setText(cursor.getString(cursor.getColumnIndex("MenuName")));
	    		incomeamount1.setText(cursor.getString(cursor.getColumnIndex("count")));
	    		incomesource1.setText(cursor.getString(cursor.getColumnIndex("IncomeSource")));
	    		makemenuperson1.setText(cursor.getString(cursor.getColumnIndex("MakePerson")));
	    		incomemaketime1.setText(cursor.getString(cursor.getColumnIndex("Date")));
	    		incomemakenote1.setText(cursor.getString(cursor.getColumnIndex("MakeNote")));
	    	   
        	    status1.setText("已做账");
        		title2.setVisibility(View.VISIBLE);
        		title1.setVisibility(View.GONE);
        	}
        	
    	}
    	 cursor.close();
    	}
    	else if(number==4){
    		paysqlite = new PaySQLite(this,"pay.db",null,1);
    		db1=paysqlite.getReadableDatabase();
    	   Cursor	cursor=paysqlite.queryPayById(db1, id);
    	if(cursor.moveToNext()){
        	if(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status")))==0){
        		incomeId.setText(cursor.getString(cursor.getColumnIndex("id")));
        		incomeclass.setText(cursor.getString(cursor.getColumnIndex("MenuName")));
        		incomeamount.setText(cursor.getString(cursor.getColumnIndex("count")));
        		incomesource.setText(cursor.getString(cursor.getColumnIndex("PayTo")));
        		makemenuperson.setText(cursor.getString(cursor.getColumnIndex("MakePerson")));
        		incomemaketime.setText(cursor.getString(cursor.getColumnIndex("Date")));
        		incomemakenote.setText(cursor.getString(cursor.getColumnIndex("MakeNote")));
        		status.setText("已开始");
        		title1.setVisibility(View.VISIBLE);
        	
        	}
        	else if(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status")))==1){
        		lock();
        	}
        	else if(Integer.valueOf(cursor.getString(cursor.getColumnIndex("status")))==2){
        		incomeId1.setText(cursor.getString(cursor.getColumnIndex("id")));
        		incomeclass1.setText(cursor.getString(cursor.getColumnIndex("MenuName")));
        		incomeamount1.setText(cursor.getString(cursor.getColumnIndex("count")));
        		incomesource1.setText(cursor.getString(cursor.getColumnIndex("PayTo")));
        		makemenuperson1.setText(cursor.getString(cursor.getColumnIndex("MakePerson")));
        		incomemaketime1.setText(cursor.getString(cursor.getColumnIndex("Date")));
        		incomemakenote1.setText(cursor.getString(cursor.getColumnIndex("MakeNote")));
        		status1.setText("已做账");
        		title1.setVisibility(View.GONE);
        		title2.setVisibility(View.VISIBLE);
        	}
    	}
    	 cursor.close();
    	}
    	
    }
	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.lock:
			lock();
			title1.setVisibility(View.GONE);
			title2.setVisibility(View.VISIBLE);
			break;
		case R.id.update:
			String Id=incomeId.getText().toString();
			String classText=incomeclass.getText().toString();
			Double count=Double.valueOf(incomeamount.getText().toString());
			String source=incomesource.getText().toString();
			String person=makemenuperson.getText().toString();
			String maketime=incomemaketime.getText().toString();
			String makenote=incomemakenote.getText().toString();
			if(number==3){
				incomesqlite.updateIncome(db, Id, classText, count, source, person, maketime,makenote);
			}else if(number==4){
				paysqlite.updatePay(db, Id, classText, count, source, person, maketime,makenote);
			}
			Toast.makeText(this, "更新成功", 1000).show();
			break;
		case R.id.Exit:
			exit();
			break;
		case R.id.Exit1:
			exit();
			break;
		}
		
	}
	public void init(){
		
		incomeId=(EditText)findViewById(R.id.incomeId);
		incomeclass=(EditText)findViewById(R.id.incomeclass);
		incomeamount=(EditText)findViewById(R.id.incomeamount);
		incomesource=(EditText)findViewById(R.id.incomesource);
		makemenuperson=(EditText)findViewById(R.id.makemenuperson);
		incomemaketime=(EditText)findViewById(R.id.incomemaketime);
		incomemakenote=(EditText)findViewById(R.id.incomemakenote);
		status=(EditText)findViewById(R.id.status);
		
		incomeId1=(TextView)findViewById(R.id.incomeId1);
		incomeclass1=(TextView)findViewById(R.id.incomeclass1);
		incomeamount1=(TextView)findViewById(R.id.incomeamount1);
		incomesource1=(TextView)findViewById(R.id.incomesource1);
		makemenuperson1=(TextView)findViewById(R.id.makemenuperson1);
		incomemaketime1=(TextView)findViewById(R.id.incomemaketime1);
		incomemakenote1=(TextView)findViewById(R.id.incomemakenote1);
		status1=(TextView)findViewById(R.id.status1);
		incomemakemenu2=(TextView)findViewById(R.id.incomemakemenu2);
		incomemakemenu21=(TextView)findViewById(R.id.incomemakemenu21);
		textTitle=(TextView)findViewById(R.id.textTitle);
		
		lock=(Button)findViewById(R.id.lock);
		update=(Button)findViewById(R.id.update);
		Exit=(Button)findViewById(R.id.Exit);
		Exit1=(Button)findViewById(R.id.Exit1);
		
		title1=(LinearLayout)findViewById(R.id.title1);
		title2=(LinearLayout)findViewById(R.id.title2);
		
		lock.setOnClickListener(this);
		update.setOnClickListener(this);
		Exit.setOnClickListener(this);
		Exit1.setOnClickListener(this);
		if(number==3){
			textTitle.setText("收入单");
			incomemakemenu2.setText("来源");
			incomemakemenu21.setText("来源");
		}if(number==4){
			textTitle.setText("支出单");
			incomemakemenu2.setText("出处");
			incomemakemenu21.setText("出处");
		}
		
	}
	public void lock(){
		if(number==3){
			incomesqlite.updateStatus(db, id, "1");
		  Cursor  cursor=incomesqlite.queryIncomeById(db, id);
		  if(cursor.moveToNext()){
		 		incomeId1.setText(cursor.getString(cursor.getColumnIndex("id")));
	    		incomeclass1.setText(cursor.getString(cursor.getColumnIndex("MenuName")));
	    		incomeamount1.setText(cursor.getString(cursor.getColumnIndex("count")));
	    		incomesource1.setText(cursor.getString(cursor.getColumnIndex("IncomeSource")));
	    		makemenuperson1.setText(cursor.getString(cursor.getColumnIndex("MakePerson")));
	    		incomemaketime1.setText(cursor.getString(cursor.getColumnIndex("Date")));
	    		incomemakenote1.setText(cursor.getString(cursor.getColumnIndex("MakeNote")));
	    	    status1.setText("已锁定");
	    		
			}
		  cursor.close();
		}else if(number==4){
			paysqlite.updateStatus(db1, id, "1");
		   Cursor cursor=paysqlite.queryPayById(db1, id);
		   if(cursor.moveToNext()){
		 		incomeId1.setText(cursor.getString(cursor.getColumnIndex("id")));
	    		incomeclass1.setText(cursor.getString(cursor.getColumnIndex("MenuName")));
	    		incomeamount1.setText(cursor.getString(cursor.getColumnIndex("count")));
	    		incomesource1.setText(cursor.getString(cursor.getColumnIndex("PayTo")));
	    		makemenuperson1.setText(cursor.getString(cursor.getColumnIndex("MakePerson")));
	    		incomemaketime1.setText(cursor.getString(cursor.getColumnIndex("Date")));
	    		incomemakenote1.setText(cursor.getString(cursor.getColumnIndex("MakeNote")));
	    	    status1.setText("已锁定");
	    		
			}
		   cursor.close();
		}
		
    	title1.setVisibility(View.GONE);
    	title2.setVisibility(View.VISIBLE);
	}
	public void exit(){
		this.finish();
	}
}
