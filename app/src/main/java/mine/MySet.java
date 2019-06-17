package mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;

import com.example.jiacaitong.R;

public class MySet extends Activity implements OnClickListener{
      private Switch MessageSwitch1;
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.myset);
    	MessageSwitch1=(Switch)findViewById(R.id.MessageSwitch1);
    	MessageSwitch1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonview, boolean ischecked) {
				if(ischecked){
					MessageSwitch1.setTextAppearance(MySet.this, R.style.OnTextColor);
				}
				else{	
					MessageSwitch1.setTextAppearance(MySet.this, R.style.OffTextColor);
				}
				
			}
		});
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
} 
