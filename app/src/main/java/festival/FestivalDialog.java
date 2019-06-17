package festival;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class FestivalDialog extends Dialog implements OnClickListener {
   
    private int image;
    private String FestivalText,FestivalHttp;
    private ImageView FestivalImage,expandTextImage,closeText;
    private TextView FestivalTextView,ExpandText,FestivalDetail;
    private RelativeLayout ExpandLayout;
    private LinearLayout FestivalLayout,NullLayout;
    private Context context;
	public FestivalDialog(Context context, int image,
			String festivalText,String FestivalHttp) {
		super(context);
		this.context=context;
		this.image = image;
		FestivalText = festivalText;
		this.FestivalHttp=FestivalHttp;
	}
  @Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.festivalitem);
	FestivalImage=(ImageView)findViewById(R.id.FestivalImage);
	//expandTextImage=(ImageView)findViewById(R.id.expandTextImage);
	closeText=(ImageView)findViewById(R.id.closeText);
	FestivalTextView=(TextView)findViewById(R.id.FesttivalTextView);
	ExpandText=(TextView)findViewById(R.id.ExpandText);
	FestivalDetail=(TextView)findViewById(R.id.FestivalDetail);
	ExpandLayout=(RelativeLayout)findViewById(R.id.ExpandLayout);
	FestivalLayout=(LinearLayout)findViewById(R.id.FestivalDetailLayout);
	NullLayout=(LinearLayout)findViewById(R.id.NullLayout);
	ExpandText.setOnClickListener(this);
	closeText.setOnClickListener(this);
	FestivalDetail.setOnClickListener(this);
	FestivalDetail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
	FestivalImage.setImageResource(image);
	FestivalTextView.setMaxLines(2);
	FestivalTextView.setText(FestivalText);
}
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.closeText:
			this.dismiss();
			break;
		case R.id.ExpandText:
			FestivalTextView.setMaxLines(800);
			ExpandLayout.setVisibility(View.GONE);
			FestivalLayout.setVisibility(View.VISIBLE);
			NullLayout.setVisibility(View.VISIBLE);
			break;
		case R.id.FestivalDetail:
			Intent intent4 = new Intent();
			intent4.putExtra("feshttp", FestivalHttp);
			intent4.setClass(context, FestivalHttpActivity.class);
			context.startActivity(intent4);
			break;
		}
	}
}
