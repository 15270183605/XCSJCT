package add;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import Adapters.CallViewPagerAdapter;
import Speak.AudioUtils;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.CallLogEntity;
import entity.Contact;
//import Speak.AudioUtils;
import android.support.v4.view.*;
public class Call extends Activity implements OnClickListener {
	private ViewPager CallViewPage;
	private RelativeLayout CallLayout;
	private TextView TimeCall;
	private WebView RobatWeb;
	private ImageView CallPhone, YuYinInput, CallReback, Clear, ContactImage,Robat;// CallLogImage;
	private EditText PhoneEdit, ContactEditText;
	private GridView CallGridView;
	private DigitalClock digitalTime;
	private LinearLayout RobatLayout;
	private int digtals[] = { R.drawable.phone1, R.drawable.phone2,
			R.drawable.phone3, R.drawable.phone4, R.drawable.phone5,
			R.drawable.phone6, R.drawable.phone7, R.drawable.phone8,
			R.drawable.phone9, R.drawable.phone10, R.drawable.phone11,
			R.drawable.phone12, };
	private int backgrounds[] = { R.drawable.contactbackground,
			R.drawable.contactbackground1, R.drawable.contactbackground2,
			R.drawable.contactbackground3, R.drawable.contactbackground4,
			R.drawable.contactbackground5, R.drawable.contactbackground6 };
	private List<Contact> ContactDatas, UnContactDatas,TotalCotactDatas;
	private List<CallLogEntity> CallLogDatas;
	private BaseAdapter adapter;
	private Calendar cal;
	public Timer mTimer;
	public TimerTask timetask;
	public int mTimeNum = 0;
	private StringBuffer number = new StringBuffer();
	private ContentResolver resolver;
	private Uri contactsUri = Contacts.CONTENT_URI;
	private Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
	private String Weeks[] = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
    private String str[]={"常用联系人","不常用联系人","通信服务电话","生活紧急电话","机构监督举报电话","银行客户服务电话号码","快递公司客服电话","外卖订餐电话","保险客服电话"};
	private Map<String, List<Contact>> PhoneDatas;
	private List<View> Views;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			switch (what) {
			case 2:
				setmyPagerIndex();
				break;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.call);
		init();

	}

	public void init() {
		cal = Calendar.getInstance();
		Date date = new Date();
		cal.setTime(date);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		CallLayout = (RelativeLayout) findViewById(R.id.CallLayout);
		CallViewPage = (ViewPager) findViewById(R.id.CallViewPage);
		TimeCall = (TextView) findViewById(R.id.TimeCall);
		CallPhone = (ImageView) findViewById(R.id.CallPhone);
		YuYinInput = (ImageView) findViewById(R.id.YuYinInput);
		CallReback = (ImageView) findViewById(R.id.CallReback);
		Clear = (ImageView) findViewById(R.id.Clear);
		ContactImage = (ImageView) findViewById(R.id.ContactImage);
		Robat = (ImageView) findViewById(R.id.Robat);
		RobatWeb = (WebView) findViewById(R.id.RobatWeb);
		PhoneEdit = (EditText) findViewById(R.id.PhoneEdit);
		CallGridView = (GridView) findViewById(R.id.CallGridView);
		RobatLayout=(LinearLayout)findViewById(R.id.RobatLayout);
		digitalTime = (DigitalClock) findViewById(R.id.digitalClock1);
		PhoneEdit.clearFocus();
		CallPhone.setOnClickListener(this);
		CallReback.setOnClickListener(this);
		Clear.setOnClickListener(this);
		ContactImage.setOnClickListener(this);
		RobatLayout.setOnClickListener(this);
		RobatWeb.setOnClickListener(this);
		PackageManager pm = getPackageManager();
		List activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() != 0) {
			YuYinInput.setOnClickListener(this);
		} else { // 若检测不到语音识别程序在本机安装，测将扭铵置灰
			YuYinInput.setEnabled(false);
		}
		
		TotalCotactDatas= new ArrayList<Contact>();
		CallLogDatas = new ArrayList<CallLogEntity>();
		PhoneDatas = new HashMap<String, List<Contact>>();
		TimeCall.setText(format.format(date) + "   " + Weeks[week_index]);
		SetAdaptet();
		startTimer();
		GridViewClick();
		getContactDatas();
		GetDatasByFile();
		CallViewPagerAdapter pagerAdapter=new CallViewPagerAdapter(PhoneDatas, this);
		CallViewPage.setAdapter(pagerAdapter);
		Toast.makeText(this, TotalCotactDatas.get(40).getPhone(), Toast.LENGTH_LONG).show();
		floatAnim(Robat,3000);
		RobatWeb.setBackgroundColor(0); // WebView设置背景色  
		RobatWeb.getBackground().setAlpha(2); // WebView设置填充透明度 范围：0-255  
		RobatWeb.loadDataWithBaseURL(null,"<HTML><body ><div align=center><IMG src='file:///android_asset/robat2.gif'/></div></body></html>", "text/html", "UTF-8",null);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.CallPhone:
			if (PhoneEdit.getText().toString().trim().length() != 0) {
				CallPhone(PhoneEdit.getText().toString());
			} else {
				startRecognizerActivity(0x13);
			}
			break;
		case R.id.YuYinInput:
			startRecognizerActivity(0x11);
			break;
		case R.id.CallReback:
			finish();
			break;
		case R.id.Clear:
			number = number.delete(number.length() - 1, number.length());
			PhoneEdit.setText(number.toString());
			break;
		case R.id.ContactImage:
			SendConatct();
			break;
		case R.id.RobatLayout:
			startRecognizerActivity(0x13);
			Toast.makeText(this, "智能通话已开启", Toast.LENGTH_LONG).show();
			break;
		case R.id.RobatWeb:
			startRecognizerActivity(0x13);
			Toast.makeText(this, "智能通话已开启", Toast.LENGTH_LONG).show();
			break;
		}

	}

	public void SetAdaptet() {
		adapter = new BaseAdapter() {

			// 获取数量
			public int getCount() {
				// TODO Auto-generated method stub
				return digtals.length;
			}

			// 获取当前选项
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			// 获取当前选项id
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			public View getView(int position, View convertView, ViewGroup parent) {
				ImageView imageview;
				if (convertView == null) {
					imageview = new ImageView(Call.this);
					imageview.setAdjustViewBounds(true);
					imageview.setMaxWidth(158);
					imageview.setMaxHeight(150);
					imageview.setPadding(15, 15, 15, 15);
				} else {
					imageview = (ImageView) convertView;
				}
				imageview.setImageResource(digtals[position]);
				return imageview;
			}

		};
		CallGridView.setAdapter(adapter);
	}

	public void GridViewClick() {
		CallGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				StringBuffer buffer;
				if (position < 9) {
					buffer = new StringBuffer(PhoneEdit.getText().toString());
					number = buffer.append((position + 1));
				}
				if (position == 9) {
					buffer = new StringBuffer(PhoneEdit.getText().toString());
					number = buffer.append('*');
				}
				if (position == 10) {
					buffer = new StringBuffer(PhoneEdit.getText().toString());
					number = buffer.append('0');
				}
				if (position == 11) {
					buffer = new StringBuffer(PhoneEdit.getText().toString());
					number = buffer.append('#');
				}
				PhoneEdit.setText(String.valueOf(number));
			}
		});
	}

	// 设置ViewPager更换时间
	public void startTimer() {
		mTimer = new Timer();
		timetask = new TimerTask() {
			public void run() {
				mTimeNum++;
				handler.sendEmptyMessage(2);
			}
		};
		mTimer.schedule(timetask, 10 * 1000, 10 * 1000);
	}

	// ViewPage设置内容
	public void setmyPagerIndex() {
		try {
			if (mTimeNum >= backgrounds.length) {
				mTimeNum = 0;
			}
			// CallBackGround.setCurrentItem(mTimeNum, true);
			CallLayout.setBackgroundResource(backgrounds[mTimeNum]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 任务停止；
	public void stopTask() {
		try {
			mTimer.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getContactDatas() {
		ContactDatas = new ArrayList<Contact>();
		UnContactDatas = new ArrayList<Contact>();
		resolver = getContentResolver();
		Cursor contactsCursor = resolver.query(contactsUri, null, null, null,
				Contacts.TIMES_CONTACTED +"    "+"desc");
		while (contactsCursor.moveToNext()) {
			// 获取联系人的id
			int _id = contactsCursor.getInt(contactsCursor
					.getColumnIndex(Contacts._ID));
			// 根据联系人的_id(此处获取的_id和raw_contact_id相同，因为contacts表就是通过这两个字段和raw_contacts表取得联系)查询电话和email
			String contactName = contactsCursor.getString(contactsCursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			int Times = contactsCursor.getInt(contactsCursor
					.getColumnIndex(Contacts.TIMES_CONTACTED));
			Cursor phoneCursor = resolver
					.query(phoneUri,
							new String[] { ContactsContract.CommonDataKinds.Phone.DATA1 },
							"raw_contact_id = ?", new String[] { _id + "" },
							null);
			while (phoneCursor.moveToNext()) {
				String phone = phoneCursor
						.getString(phoneCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				if (phone.trim().length() == 0) {
					contactName = "无名";
					phone=contactName;
					contactName = contactName.replaceAll("\\d+", "无名");
				}
				Contact contact = new Contact(contactName, phone);
				if (Times >20) {
					ContactDatas.add(contact);
					TotalCotactDatas.add(contact);
				} else {
					UnContactDatas.add(contact);
					TotalCotactDatas.add(contact);
				}
			
			}
			phoneCursor.close();
			phoneCursor = null;
			
		}
		if(TotalCotactDatas.size()==0){
			Contact contact1 = new Contact("张三", "15270183605");
			ContactDatas.add(contact1);
			TotalCotactDatas.add(contact1);
			UnContactDatas.add(contact1);
		}
		
		PhoneDatas.put("常用联系人", ContactDatas);
		PhoneDatas.put("不常用联系人", UnContactDatas);
	}

	// 开始识别
	private void startRecognizerActivity(int requestCode) { // 通过Intent传递语音识别的模式，开启语音
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // 语言模式和自由模式的语音识别
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); // 提示语音开始
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "开始语音"); // 开始语音识别
		startActivityForResult(intent, requestCode); // 调出识别界面
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 回调获取从谷歌得到的数据
		if (requestCode == 0x11 && resultCode == RESULT_OK) { // 取得语音的字符
			ArrayList<String> results = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String resultString = "";
			for (int i = 0; i < results.size(); i++) {
				resultString += results.get(i);
			}
			String msg = resultString.substring(0, resultString.length() - 1);
			PhoneEdit.setText(msg);
		} // 语音识别后的回调，将识别的字串以Toast显示
		if (requestCode == 0x12 && resultCode == RESULT_OK) {
			ContentResolver resolver = getContentResolver();
			Uri contactdata = data.getData();
			Cursor cursor = resolver.query(contactdata, null, null, null, null);
			cursor.moveToFirst();
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			cursor.close();
			Cursor phoneCursor = resolver.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					"_id = " + contactId, null, null, null);
			String contactPhone = null;
			while (phoneCursor.moveToNext()) {
				contactPhone = phoneCursor
						.getString(phoneCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			}
			phoneCursor.close();
			PhoneEdit.setText(contactPhone);
		}
		if (requestCode == 0x13 && resultCode == RESULT_OK) {
			ArrayList<String> results = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String resultString = "";
			for (int i = 0; i < results.size(); i++) {
				resultString += results.get(i);
			}
			String msg = resultString.substring(0, resultString.length() - 1);
			YuYinCall(msg);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void CallPhone(String phone) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:" + phone));
		startActivity(intent);
	}

	// 调用联系人查看界面中不存在的联系人
	public void SendConatct() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
		startActivityForResult(intent, 0x12);
	}

	// 语音打电话；
	public void YuYinCall(String message) {
		String phone = null;
		if(message.indexOf("联系人")!=-1){
			SendConatct();
		}else{
		for (int i = 0; i < TotalCotactDatas.size(); i++) {
			if ((message.indexOf(TotalCotactDatas.get(i).getUsername()) != -1) || message.indexOf(TotalCotactDatas.get(i).getPhone()) != -1) {
				phone = TotalCotactDatas.get(i).getPhone();
				break;
			}
		}
		if (phone.trim().length()!=0) {
			CallPhone(phone);
		} 
		else {
			AudioUtils.getInstance().speakText("查不到该联系人!,是否需要打开手机本地联系人查找");
		}}
	}

	public void EditTextListener(final EditText edit) {
		edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stu

			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
	}

	public void GetDatasByFile() {
		List<Contact> Contacts=new ArrayList<Contact>();
		int count = 0;
		try {
			InputStream inputStream = getAssets().open("callphone.txt");
			BufferedReader buffer = new BufferedReader(new InputStreamReader(
					inputStream, "GBK"));
			String temp = "";
			String name;
			String phone = null;
			while ((temp = buffer.readLine()) != null) {
				count = count + 1;
				if ((count % 2) == 0) {
					if (temp.indexOf("#") != -1) {
                      name=temp.substring(0, temp.indexOf("#"));
                      PutDataToMap(name,phone,"生活紧急电话",Contacts);
                      Contacts=new ArrayList<Contact>();
					}
					else if (temp.indexOf("$") != -1) {
						 name=temp.substring(0, temp.indexOf("$"));
						 PutDataToMap(name,phone,"机构监督举报电话",Contacts);
						 Contacts=new ArrayList<Contact>();
					}
					else if (temp.indexOf("%") != -1) {
						 name=temp.substring(0, temp.indexOf("%"));
						 PutDataToMap(name,phone,"通信服务电话",Contacts);
						 Contacts=new ArrayList<Contact>();
					}
					else if (temp.indexOf("&") != -1) {
						 name=temp.substring(0, temp.indexOf("&"));
						 PutDataToMap(name,phone,"银行客户服务电话号码",Contacts);
						 Contacts=new ArrayList<Contact>();
					}
					else if (temp.indexOf("*") != -1) {
						 name=temp.substring(0, temp.indexOf("*"));
						 PutDataToMap(name,phone,"快递公司客服电话",Contacts);
						 Contacts=new ArrayList<Contact>();
					}

					else if (temp.indexOf(",") != -1) {
						 name=temp.substring(0, temp.indexOf(","));
						 PutDataToMap(name,phone,"外卖订餐电话",Contacts);
						 Contacts=new ArrayList<Contact>();
					}
					else if (temp.indexOf("。") != -1) {
						 name=temp.substring(0, temp.indexOf("。"));
						 PutDataToMap(name,phone,"保险客服电话",Contacts);
						 Contacts=new ArrayList<Contact>();
					}
					else{
						name=temp;
						Contact contact=new Contact(name, phone);
						Contacts.add(contact);
						TotalCotactDatas.add(contact);
					}
					count=0;
				}else{
				  phone=temp;	
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();

		}
	}
  public void PutDataToMap(String str,String str1,String str2,List<Contact> data){
	 
	  Contact contact=new Contact(str, str1);
	  data.add(contact);
	  TotalCotactDatas.add(contact);
	  PhoneDatas.put(str2, data);
		 
  }
  private void floatAnim(View view,int delay){
	    List<Animator> animators = new ArrayList<Animator>();
	   /* ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(view, "translationX", -6.0f,6.0f,-6.0f);
	    translationXAnim.setDuration(1500);
	    translationXAnim.setRepeatCount(ValueAnimator.INFINITE);//无限循环
	   // translationXAnim.setRepeatMode(ValueAnimator.INFINITE);//
	    translationXAnim.start();
	    animators.add(translationXAnim);*/
	    
	    ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(view, "translationY", -3.0f,3.0f,-3.0f);
	    translationYAnim.setDuration(1000);
	    translationYAnim.setRepeatCount(ValueAnimator.INFINITE);
	    translationYAnim.start();
	    animators.add(translationYAnim);

	    AnimatorSet btnSexAnimatorSet = new AnimatorSet();
	    btnSexAnimatorSet.playTogether(animators);
	    btnSexAnimatorSet.setStartDelay(delay);
	    btnSexAnimatorSet.start();
	}
}
