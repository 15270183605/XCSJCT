package add;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import com.example.jiacaitong.R;

public class SaoMa extends Activity {
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	 	// TODO Auto-generated method stub
	 	super.onCreate(savedInstanceState);
	 	requestWindowFeature(Window.FEATURE_NO_TITLE);
	 	setContentView(R.layout.camer);
	 }
	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			if (resultCode == RESULT_OK) {
				Bundle bundle = data.getExtras();
				String scanResult = bundle.getString("result");
				Toast.makeText(this, scanResult, 1000).show();
			}
		}
}
