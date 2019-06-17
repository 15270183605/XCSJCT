package userrefreedback;

import java.util.Random;

import loginOrRegister.Main;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

public class SendMessageTextActivity extends Activity implements
		OnClickListener {
	private LinearLayout AssessLayout, TextOpenation;
	private Button Commit, GiveUp, ComeOrExit;
	private TextView TopText, Rank;
	private RatingBar AssessRateingBar;
  private EditText Answer1,Answer2,Answer3;
  private boolean flag=false;
  private int gradeRank,Grade;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.userrefreedsendmessagetext);
		init();
	}

	public void init() {
		AssessLayout = (LinearLayout) findViewById(R.id.AssessLayout);
		TextOpenation = (LinearLayout) findViewById(R.id.TextOpenation);
		Commit = (Button) findViewById(R.id.Commit);
		GiveUp = (Button) findViewById(R.id.GiveUp);
		ComeOrExit = (Button) findViewById(R.id.ComeOrExit);
		TopText = (TextView) findViewById(R.id.TopText);
		Rank = (TextView) findViewById(R.id.Rank);
		AssessRateingBar = (RatingBar) findViewById(R.id.AssessRateingBar);
		Answer1 = (EditText) findViewById(R.id.Answer1);
		Answer2 = (EditText) findViewById(R.id.Answer2);
		Answer3 = (EditText) findViewById(R.id.Answer3);
		Commit.setOnClickListener(this);
		GiveUp.setOnClickListener(this);
		ComeOrExit.setOnClickListener(this);
	
		TopText.setText(Html.fromHtml("      �𾴵�<font color=red>"+Main.returnName()+"</font>"+"�û����ã�����������Ҫ���ʵ���һ�����ⷴ�����棬��������֮ǰ���ǽ�������������в�������������������ȼ���4�����ϣ�������Ȩ�����ʣ������ⷴ�����������⽫ֱ���Ʒ�����ˣ���һʱ��Ϊ��������⣬��������಻�������!"));
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.Commit:
			CheckAnswer();
			AssessLayout.setVisibility(View.VISIBLE);
			TextOpenation.setVisibility(View.GONE);
			ComeOrExit.setVisibility(View.VISIBLE);
			if(gradeRank>=4){
				ComeOrExit.setText("����");
			}else{
				ComeOrExit.setText("�˳�");
			}
			AssessRateingBar.setRating(gradeRank);
			Rank.setText(String.valueOf(gradeRank)+"��");
			break;
		case R.id.GiveUp:
			this.finish();
			break;
		case R.id.ComeOrExit:
			if(gradeRank>=4){
				Intent intent=new Intent(this,SendMessageActivity.class);
				startActivity(intent);
			}else{
				this.finish();
			}
			break;
		}

	}
   public void CheckAnswer(){
	    Random random=new Random();
	   if(Answer1.getText().toString().trim().length()==0 && Answer2.getText().toString().trim().length()==0 &&Answer3.getText().toString().trim().length()==0){
		   Toast.makeText(this, "���������", 1000).show();
	   }else if(Answer1.getText().toString().trim().length()!=0 && Answer2.getText().toString().trim().length()!=0 &&Answer3.getText().toString().trim().length()!=0){
		      Grade=random.nextInt(40)+60;
	   }else{
		   Grade=random.nextInt(60);
	   }
	   if(Grade>0 && Grade<30){
		   gradeRank=1;
	   }
	   if(Grade>=30 && Grade<60){
		   gradeRank=2;
		 
	   }
	   if(Grade>=60 && Grade<80){
		   gradeRank=3;
		 
	   }
	   if(Grade>=80 && Grade<90){
		   gradeRank=4;
		   
	   }
	   if(Grade>=90 && Grade<100){
		   gradeRank=5;
		 	   }
	  
   }
}
