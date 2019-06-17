package copyshouye;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

public class ShowAdDataActivity extends Activity implements OnClickListener{

	private WebView festivalWebView;
	private ImageView WebViewBack;
	private String http;
	private TextView ADText;
	private int biaozhi;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.festivaldetail);
		Intent intent=getIntent();
		http=intent.getStringExtra("adhttp");
		biaozhi=intent.getIntExtra("biaozhi", 0);
		festivalWebView=(WebView)findViewById(R.id.festivalWebView);
		WebViewBack=(ImageView)findViewById(R.id.WebViewBack);
		ADText=(TextView)findViewById(R.id.FestivalText);
		if(biaozhi==0){
		ADText.setText("理财小知识");}
		else if(biaozhi==1){
			ADText.setText("扫码结果详情");	
		}
		WebViewBack.setOnClickListener(this);
		festivalWebView.loadUrl(http);
	}
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.WebViewBack:
			this.finish();
			break;
		}

	
	}
}
