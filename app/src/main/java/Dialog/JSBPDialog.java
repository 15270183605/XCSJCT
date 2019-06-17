package Dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


public class JSBPDialog extends Dialog implements OnClickListener {
    private Context context;
	private View view;
	public JSBPDialog(Context context,View view) {
		super(context);
		this.context=context;
	this.view=view;
	}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(view);
    	
    }
	public void onClick(View view) {
		switch(view.getId()){
	    
		}

	}
}
