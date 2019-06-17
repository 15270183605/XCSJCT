package start;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

import com.example.jiacaitong.R;

public class FirstStartActivity extends Activity {
   private WebView firststartWebView1,firststartWebView2,firststartWebView3;
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.firststart);
	firststartWebView1=(WebView)findViewById(R.id.firststartWebView1);
	firststartWebView2=(WebView)findViewById(R.id.firststartWebView2);
	firststartWebView3=(WebView)findViewById(R.id.firststartWebView3);
	firststartWebView1.loadDataWithBaseURL(null,"<HTML><body bgcolor='#e5e5e5'><div align=center><IMG src='file:///android_asset/licai1.gif'/></div></body></html>", "text/html", "UTF-8",null);
	firststartWebView2.loadDataWithBaseURL(null,"<HTML><body bgcolor='#e5e5e5'><div align=center><IMG src='file:///android_asset/licai15.gif'/></div></body></html>", "text/html", "UTF-8",null);
	firststartWebView3.loadDataWithBaseURL(null,"<HTML><body bgcolor='#e5e5e5'><div align=center><IMG src='file:///android_asset/licai14.gif'/></div></body></html>", "text/html", "UTF-8",null);
}
}
