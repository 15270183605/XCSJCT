package add;

import jishiben.ImportantNote;
import jishiben.PictureActivity1;
import jishiben.UnImNote;
import jishiben.VideoActivity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jiacaitong.R;

public class JiShiBen extends ActivityGroup implements OnClickListener{
	 private ImageView reback,JiExpandImage;
	 private LinearLayout JiShiBenContainer;
	
	 protected void onCreate(Bundle savedInstanceState) {
	 	super.onCreate(savedInstanceState);
	 	requestWindowFeature(Window.FEATURE_NO_TITLE);
	 	setContentView(R.layout.jishiben);
	 	reback=(ImageView)findViewById(R.id.jishibenback);
		JiShiBenContainer=(LinearLayout)findViewById(R.id.JiShiBenContainer);
	 	JiExpandImage=(ImageView)findViewById(R.id.JiExpandImage1);
	 
	 	reback.setOnClickListener(this);
	 	JiExpandImage.setOnClickListener(this);
		toActivity("note1",UnImNote.class);
	 }
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.jishibenback:
			this.finish();
			break;
		case R.id.JiExpandImage1:
			AddListener();
			break;
		
		}
		
	}
	public void AddListener() {
		JiShiBenPoupWindow mPopupWindow = new JiShiBenPoupWindow(JiShiBen.this,
				new JiShiBenPoupWindow.OnPopWindowClickListener() {
					@Override
					public void onPopWindowClickListener(View view) {
						switch (view.getId()) {
						case R.id.Video:
							toActivity("video", VideoActivity.class);
							break;
						case R.id.Picture:
							toActivity("picture",PictureActivity1.class);
							break;
						case R.id.ImNote:
							toActivity("note", ImportantNote.class);
							break;
						case R.id.Note:
							toActivity("note1",UnImNote.class);
							break;
						
						}
					}
				});
		mPopupWindow.show();
	
	}
	
	private void toActivity(String lable, Class<?> cls) {
		Intent intent = new Intent(this, cls);
		JiShiBenContainer.removeAllViews();
		View v = getLocalActivityManager().startActivity(lable, intent)
				.getDecorView();
		v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		JiShiBenContainer.addView(v);
	}
  
}
