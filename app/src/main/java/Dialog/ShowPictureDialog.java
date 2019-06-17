package Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.jiacaitong.R;

public class ShowPictureDialog extends Dialog implements android.view.View.OnClickListener{
   private Context context;
   private int image;
public ShowPictureDialog(Context context,int image) {
	super(context);
	this.context = context;
	this.image=image;
}
   @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showbigpicturedialog);
		ImageView ShowBigPicture=(ImageView)findViewById(R.id.ShowBigPicture);
		ImageView ClosePicture=(ImageView)findViewById(R.id.ClosePicture);
		ClosePicture.setOnClickListener(this);
		ShowBigPicture.setImageResource(image);
	}
@Override
public void onClick(View view) {
	switch(view.getId()){
	case R.id.ClosePicture:
		this.dismiss();
		break;
	}
	
}
}
