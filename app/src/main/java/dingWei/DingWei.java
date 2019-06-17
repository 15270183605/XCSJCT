package dingWei;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

 

 

public class DingWei extends Activity implements View.OnClickListener {
	private Button btn;

	private EditText ed_city;

	private TextView city_result1;

	private TextView city_result2;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.tianqi);

        btn=(Button) findViewById(R.id.btn);

        btn.setOnClickListener(this);

        

        ed_city=(EditText) findViewById(R.id.ed_city);

        city_result1=(TextView) findViewById(R.id.city_result1);

        city_result2=(TextView) findViewById(R.id.city_result2);

    }
    private final static String PATH="http://wthrcdn.etouch.cn/weather_mini?city=";

	protected static final int SUCCESS = 0;

	protected static final int INVALID_CITY = 1;

	protected static final int ERROR = 2;

	

    private String city;

    String ul;

    

    

    private Handler mhandler=new Handler(){

    	public void handleMessage(android.os.Message msg) {

    		dialog.dismiss();

    		switch (msg.what) {

			case SUCCESS:
				JSONArray data=(JSONArray) msg.obj;
				try {

					String day01= data.getString(0);

					String day02= data.getString(3);
					
					city_result1.setText(dealString(day01));

					city_result2.setText(dealString(day02));

				} catch (Exception e) {

					// TODO Auto-generated catch block

					e.printStackTrace();

				}

					break;
			case INVALID_CITY:

				Toast.makeText(DingWei.this, "城市无效", 0).show();

				break;

			case ERROR:

				Toast.makeText(DingWei.this, "网络无效", 0).show();

				break;

			default:

				break;

			}

    	};

    };

    ProgressDialog dialog=null;

    

	public void onClick(View v) {

		// TODO Auto-generated method stub

		city=ed_city.getText().toString().trim();

		if(TextUtils.isEmpty(city)){

			Toast.makeText(this, "路径错误", 0).show();

			return ;

		}

		dialog=new ProgressDialog(this);

		dialog.setMessage("正在玩命加载中");

		dialog.show();

		//发起请求给那个网站

		new Thread(){

			public void run() {

				try {

				    ul=PATH+URLEncoder.encode(city,"UTF-8");

					

					URL url=new URL(ul);

					

					//设置必要的参数信息

					HttpURLConnection conn=(HttpURLConnection) url.openConnection();

					conn.setConnectTimeout(5000);

					conn.setRequestMethod("GET");

					

					//判断响应码

					int code = conn.getResponseCode();

					if(code==200){

						//连接网络成功

						InputStream in = conn.getInputStream();

						String data = StreamPool.decodeStream(in);

						

						

						//解析json格式的数据

						JSONObject jsonObj=new JSONObject(data);

						//获得desc的值

						String result = jsonObj.getString("desc");

						if("OK".equals(result)){

							//城市有效，返回了需要的数据

							JSONObject dataObj = jsonObj.getJSONObject("data");
							JSONArray jsonArray = dataObj.getJSONArray("forecast");
 
							//通知更新ui

							Message msg = Message.obtain();

							msg.obj=jsonArray;

							msg.what=SUCCESS;

							mhandler.sendMessage(msg);
						}else{

							//城市无效

							Message msg=Message.obtain();

							msg.what=INVALID_CITY;

							mhandler.sendMessage(msg);

						}

					}

				} catch (Exception e) {

					// TODO Auto-generated catch block

					e.printStackTrace();

					Message msg = Message.obtain();

					msg.what=ERROR;

					mhandler.sendMessage(msg);

				}

			};

		}.start();

	}  
//利用正则表达式对字符串进行处理
	public String dealString(String str){
		/*String tt=str.substring(str.indexOf("-")-1, str.indexOf("级")+1);
		String tianqi=str.replaceAll("[\\p{Punct}\\p{Space}\\p{Symbol}]+", "");
		//String TQ=tianqi.replaceAll("[a-zA-Z]","");
		String TQ=tianqi.replaceAll("[A-Z]","").replaceAll("date", "").replaceAll("日", "号").replaceAll("high", "B").replaceAll("高温", "").replaceAll("fengli", "C").replaceAll("低温", "").replaceAll("low", "").replaceAll("fengxiang", "D").replaceAll("type", "E");
		//String SS=TQ.substring(0, TQ.indexOf("B"))+TQ.substring(TQ.indexOf("级")+1, TQ.indexOf("D"))+"℃"+"/"+TQ.substring(TQ.indexOf("B")+1, TQ.indexOf("C"))+"℃"+TQ.substring(TQ.indexOf("D")+1, TQ.indexOf("E"))+TQ.substring(TQ.indexOf("C")+1, TQ.indexOf("级")+1)+TQ.substring(TQ.indexOf("E")+1,TQ.length());
		*/
		String SS=null;
		String tianqi=str.replaceAll("[\\p{Punct}\\p{Space}\\p{Symbol}]+", "");
		//String TQ=tianqi.replaceAll("[a-zA-Z]","");
		String TQ=tianqi.replaceAll("[A-Z]","").replaceAll("date", "").replaceAll("日", "号").replaceAll("high", "B").replaceAll("高温", "").replaceAll("fengli", "C").replaceAll("低温", "").replaceAll("low", "").replaceAll("fengxiang", "D").replaceAll("type", "E");
		if(str.indexOf("-")!=-1){
			String tt=str.substring(str.indexOf("-")-1, str.indexOf("级")+1);
			SS=TQ.substring(0, TQ.indexOf("B"))+TQ.substring(TQ.indexOf("级")+1, TQ.indexOf("D"))+"℃"+"/"+TQ.substring(TQ.indexOf("B")+1, TQ.indexOf("C"))+"℃"+TQ.substring(TQ.indexOf("D")+1, TQ.indexOf("E"))+tt+TQ.substring(TQ.indexOf("E")+1,TQ.length());
		}else{
			SS=TQ.substring(0, TQ.indexOf("B"))+TQ.substring(TQ.indexOf("级")+1, TQ.indexOf("D"))+"℃"+"/"+TQ.substring(TQ.indexOf("B")+1, TQ.indexOf("C"))+"℃"+TQ.substring(TQ.indexOf("D")+1, TQ.indexOf("E"))+TQ.substring(TQ.indexOf("C")+1, TQ.indexOf("级")+1)+TQ.substring(TQ.indexOf("E")+1,TQ.length());
		}return SS;
	}
/*Unicode 编码并不只是为某个字符简单定义了一个编码，而且还将其进行了归类。 
/pP 其中的小写 p 是 property 的意思，表示 Unicode 属性，用于 Unicode 正表达式的前缀。

P：标点字符 
L：字母； 
M：标记符号（一般不会单独出现）； 
Z：分隔符（比如空格、换行等）； 
S：符号（比如数学符号、货币符号等）； 
N：数字（比如阿拉伯数字、罗马数字等）； 
C：其他字符*/
}

