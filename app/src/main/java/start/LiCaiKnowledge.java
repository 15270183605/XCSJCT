package start;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.example.jiacaitong.R;

public class LiCaiKnowledge extends Activity implements OnClickListener{
     @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
    	setContentView(R.layout.licaiknowledge);
    }

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		
	}
}
