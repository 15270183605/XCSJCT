package Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public class CountSelectDialog extends Dialog {
   private View view;
   private Context context;
public CountSelectDialog(Context context, View view) {
	super(context);
	this.view = view;
	this.context = context;
}
@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(view);
	}
}
