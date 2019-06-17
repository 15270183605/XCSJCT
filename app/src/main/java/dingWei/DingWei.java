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

				Toast.makeText(DingWei.this, "������Ч", 0).show();

				break;

			case ERROR:

				Toast.makeText(DingWei.this, "������Ч", 0).show();

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

			Toast.makeText(this, "·������", 0).show();

			return ;

		}

		dialog=new ProgressDialog(this);

		dialog.setMessage("��������������");

		dialog.show();

		//����������Ǹ���վ

		new Thread(){

			public void run() {

				try {

				    ul=PATH+URLEncoder.encode(city,"UTF-8");

					

					URL url=new URL(ul);

					

					//���ñ�Ҫ�Ĳ�����Ϣ

					HttpURLConnection conn=(HttpURLConnection) url.openConnection();

					conn.setConnectTimeout(5000);

					conn.setRequestMethod("GET");

					

					//�ж���Ӧ��

					int code = conn.getResponseCode();

					if(code==200){

						//��������ɹ�

						InputStream in = conn.getInputStream();

						String data = StreamPool.decodeStream(in);

						

						

						//����json��ʽ������

						JSONObject jsonObj=new JSONObject(data);

						//���desc��ֵ

						String result = jsonObj.getString("desc");

						if("OK".equals(result)){

							//������Ч����������Ҫ������

							JSONObject dataObj = jsonObj.getJSONObject("data");
							JSONArray jsonArray = dataObj.getJSONArray("forecast");
 
							//֪ͨ����ui

							Message msg = Message.obtain();

							msg.obj=jsonArray;

							msg.what=SUCCESS;

							mhandler.sendMessage(msg);
						}else{

							//������Ч

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
//����������ʽ���ַ������д���
	public String dealString(String str){
		/*String tt=str.substring(str.indexOf("-")-1, str.indexOf("��")+1);
		String tianqi=str.replaceAll("[\\p{Punct}\\p{Space}\\p{Symbol}]+", "");
		//String TQ=tianqi.replaceAll("[a-zA-Z]","");
		String TQ=tianqi.replaceAll("[A-Z]","").replaceAll("date", "").replaceAll("��", "��").replaceAll("high", "B").replaceAll("����", "").replaceAll("fengli", "C").replaceAll("����", "").replaceAll("low", "").replaceAll("fengxiang", "D").replaceAll("type", "E");
		//String SS=TQ.substring(0, TQ.indexOf("B"))+TQ.substring(TQ.indexOf("��")+1, TQ.indexOf("D"))+"��"+"/"+TQ.substring(TQ.indexOf("B")+1, TQ.indexOf("C"))+"��"+TQ.substring(TQ.indexOf("D")+1, TQ.indexOf("E"))+TQ.substring(TQ.indexOf("C")+1, TQ.indexOf("��")+1)+TQ.substring(TQ.indexOf("E")+1,TQ.length());
		*/
		String SS=null;
		String tianqi=str.replaceAll("[\\p{Punct}\\p{Space}\\p{Symbol}]+", "");
		//String TQ=tianqi.replaceAll("[a-zA-Z]","");
		String TQ=tianqi.replaceAll("[A-Z]","").replaceAll("date", "").replaceAll("��", "��").replaceAll("high", "B").replaceAll("����", "").replaceAll("fengli", "C").replaceAll("����", "").replaceAll("low", "").replaceAll("fengxiang", "D").replaceAll("type", "E");
		if(str.indexOf("-")!=-1){
			String tt=str.substring(str.indexOf("-")-1, str.indexOf("��")+1);
			SS=TQ.substring(0, TQ.indexOf("B"))+TQ.substring(TQ.indexOf("��")+1, TQ.indexOf("D"))+"��"+"/"+TQ.substring(TQ.indexOf("B")+1, TQ.indexOf("C"))+"��"+TQ.substring(TQ.indexOf("D")+1, TQ.indexOf("E"))+tt+TQ.substring(TQ.indexOf("E")+1,TQ.length());
		}else{
			SS=TQ.substring(0, TQ.indexOf("B"))+TQ.substring(TQ.indexOf("��")+1, TQ.indexOf("D"))+"��"+"/"+TQ.substring(TQ.indexOf("B")+1, TQ.indexOf("C"))+"��"+TQ.substring(TQ.indexOf("D")+1, TQ.indexOf("E"))+TQ.substring(TQ.indexOf("C")+1, TQ.indexOf("��")+1)+TQ.substring(TQ.indexOf("E")+1,TQ.length());
		}return SS;
	}
/*Unicode ���벢��ֻ��Ϊĳ���ַ��򵥶�����һ�����룬���һ���������˹��ࡣ 
/pP ���е�Сд p �� property ����˼����ʾ Unicode ���ԣ����� Unicode �����ʽ��ǰ׺��

P������ַ� 
L����ĸ�� 
M����Ƿ��ţ�һ�㲻�ᵥ�����֣��� 
Z���ָ���������ո񡢻��еȣ��� 
S�����ţ�������ѧ���š����ҷ��ŵȣ��� 
N�����֣����簢�������֡��������ֵȣ��� 
C�������ַ�*/
}

