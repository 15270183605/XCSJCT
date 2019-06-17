package shouye;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.example.jiacaitong.R;

public class MenuSafeGuard extends Activity {
         @Override
        protected void onCreate(Bundle savedInstanceState) {
        	
        	super.onCreate(savedInstanceState);
        	requestWindowFeature(Window.FEATURE_NO_TITLE);
        	setContentView(R.layout.menusafeguard);
        }
}
