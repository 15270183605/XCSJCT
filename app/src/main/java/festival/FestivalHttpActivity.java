package festival;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.jiacaitong.R;

public class FestivalHttpActivity extends Activity implements OnClickListener {

	private WebView festivalWebView;
	private ImageView WebViewBack;
	private String http;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.festivaldetail);
		Intent intent=getIntent();
		http=intent.getStringExtra("feshttp");
		festivalWebView=(WebView)findViewById(R.id.festivalWebView);
		WebViewBack=(ImageView)findViewById(R.id.WebViewBack);
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
