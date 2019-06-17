package userrefreedback;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class SendMessageActivity extends Activity implements OnClickListener {
	  private Button SendMessage,CloseMessage;
	  private EditText MessageEditText;
	  private TextView Phone;
	  protected void onCreate(Bundle savedInstanceState) {
	  	super.onCreate(savedInstanceState);
	  	requestWindowFeature(Window.FEATURE_NO_TITLE);
	  	setContentView(R.layout.userrefreedsendmessage);
	  	SendMessage=(Button)findViewById(R.id.SendMessage);
	  	CloseMessage=(Button)findViewById(R.id.CloseMessage);
	  	Phone=(TextView)findViewById(R.id.MsgPhone);
	  	MessageEditText=(EditText)findViewById(R.id.MessageEditText);
	  	CloseMessage.setOnClickListener(this);
	  	SendMessage.setOnClickListener(this);
	  	
	  }
	  	@Override
	  	public void onClick(View view) {
	  		switch(view.getId()){
	  		case R.id.CloseMessage:
	  			this.finish();
	  			break;
	  		case R.id.SendMessage:
	  			if(!MessageEditText.getText().toString().equals(null) || MessageEditText.getText().toString()!=null){
	  			Intent intent=new Intent();
				//intent.setAction(Intent.ACTION_SENDTO);
				intent.setData(Uri.parse("smsto:"+Phone.getText().toString()));
				intent.putExtra("sms_body",MessageEditText.getText().toString());
				startActivity(intent);
	  			}
	  			break;
	  		}

	  	}


}
