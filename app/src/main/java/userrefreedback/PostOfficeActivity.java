package userrefreedback;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.jiacaitong.R;

public class PostOfficeActivity extends Activity implements OnClickListener {
	  private Button PuTongPost,JinJiPost,SendPost,SavePost,ClosePost;
	  private EditText ShouEditTxet,TitleEditTxet,ContentEditText;
	  protected void onCreate(Bundle savedInstanceState) {
	  	// TODO Auto-generated method stub
	  	super.onCreate(savedInstanceState);
	  	requestWindowFeature(Window.FEATURE_NO_TITLE);
	  	setContentView(R.layout.userrefreedpostoffice);
	  	init();
	  }
	  public void init(){
		  PuTongPost=(Button)findViewById(R.id.PuTongPost);
		  	JinJiPost=(Button)findViewById(R.id.JinJiPost);
		  	SendPost=(Button)findViewById(R.id.SendPost);
		  	SavePost=(Button)findViewById(R.id.SavePost);
		  	ClosePost=(Button)findViewById(R.id.ClosePost);
		  	ShouEditTxet=(EditText)findViewById(R.id.ShouEditTxet);
		  	TitleEditTxet=(EditText)findViewById(R.id.TitleEditTxet);
		  	ContentEditText=(EditText)findViewById(R.id.ContentEditText);
		  	PuTongPost.setOnClickListener(this);
		  	JinJiPost.setOnClickListener(this);
		  	SendPost.setOnClickListener(this);
		  	SavePost.setOnClickListener(this);
		  	ClosePost.setOnClickListener(this);

	  }
	  	@Override
	  	public void onClick(View view) {
	  		switch(view.getId()){
	  		case R.id.PuTongPost:
	  			JinJiPost.setBackgroundResource(R.drawable.jinjipost);
	  			SavePost.setVisibility(View.VISIBLE);
	  			PuTongPost.setBackgroundResource(R.drawable.putongpost);
	  			break;
	  		case R.id.JinJiPost:
	  			JinJiPost.setBackgroundResource(R.drawable.putongpost);
	  			SavePost.setVisibility(View.GONE);
	  			PuTongPost.setBackgroundResource(R.drawable.jinjipost);
	  			break;
	  		case R.id.SendPost:
	  			break;
	  		case R.id.SavePost:
	  			break;
	  		case R.id.ClosePost:
	  			this.finish();
	  			break;
	  		}

	  	}

}
