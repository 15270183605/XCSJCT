package Dialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.example.jiacaitong.R;


	public class SampleLoadDialog extends Dialog {
		  private Context context;
			public SampleLoadDialog(Context context) {
				super(context);
			this.context=context;	
			
			}
			protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.sampledialogitem);
				
			}
			 
}
