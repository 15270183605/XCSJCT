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
	private String Weeks[] = { "����", "��һ", "�ܶ�", "����", "����", "����", "����" };
    private String str[]={"������ϵ��","��������ϵ��","ͨ�ŷ���绰","��������绰","�����ල�ٱ��绰","���пͻ�����绰����","��ݹ�˾�ͷ��绰","�������͵绰","���տͷ��绰"};
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
		} else { // ����ⲻ������ʶ������ڱ�����װ���⽫Ť��û�
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
		RobatWeb.setBackgroundColor(0); // WebView���ñ���ɫ  
		RobatWeb.getBackground().setAlpha(2); // WebView�������͸���� ��Χ��0-255  
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
			Toast.makeText(this, "����ͨ���ѿ���", Toast.LENGTH_LONG).show();
			break;
		case R.id.RobatWeb:
			startRecognizerActivity(0x13);
			Toast.makeText(this, "����ͨ���ѿ���", Toast.LENGTH_LONG).show();
			break;
		}

	}

	public void SetAdaptet() {
		adapter = new BaseAdapter() {

			// ��ȡ����
			public int getCount() {
				// TODO Auto-generated method stub
				return digtals.length;
			}

			// ��ȡ��ǰѡ��
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return position;
			}

			// ��ȡ��ǰѡ��id
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

	// ����ViewPager����ʱ��
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

	// ViewPage��������
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

	// ����ֹͣ��
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
			// ��ȡ��ϵ�˵�id
			int _id = contactsCursor.getInt(contactsCursor
					.getColumnIndex(Contacts._ID));
			// ������ϵ�˵�_id(�˴���ȡ��_id��raw_contact_id��ͬ����Ϊcontacts�����ͨ���������ֶκ�raw_contacts��ȡ����ϵ)��ѯ�绰��email
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
					contactName = "����";
					phone=contactName;
					contactName = contactName.replaceAll("\\d+", "����");
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
			Contact contact1 = new Contact("����", "15270183605");
			ContactDatas.add(contact1);
			TotalCotactDatas.add(contact1);
			UnContactDatas.add(contact1);
		}
		
		PhoneDatas.put("������ϵ��", ContactDatas);
		PhoneDatas.put("��������ϵ��", UnContactDatas);
	}

	// ��ʼʶ��
	private void startRecognizerActivity(int requestCode) { // ͨ��Intent��������ʶ���ģʽ����������
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); // ����ģʽ������ģʽ������ʶ��
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_FREE_FORM); // ��ʾ������ʼ
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "��ʼ����"); // ��ʼ����ʶ��
		startActivityForResult(intent, requestCode); // ����ʶ�����
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { // �ص���ȡ�ӹȸ�õ�������
		if (requestCode == 0x11 && resultCode == RESULT_OK) { // ȡ���������ַ�
			ArrayList<String> results = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String resultString = "";
			for (int i = 0; i < results.size(); i++) {
				resultString += results.get(i);
			}
			String msg = resultString.substring(0, resultString.length() - 1);
			PhoneEdit.setText(msg);
		} // ����ʶ���Ļص�����ʶ����ִ���Toast��ʾ
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

	// ������ϵ�˲鿴�����в����ڵ���ϵ��
	public void SendConatct() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
		startActivityForResult(intent, 0x12);
	}

	// ������绰��
	public void YuYinCall(String message) {
		String phone = null;
		if(message.indexOf("��ϵ��")!=-1){
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
			AudioUtils.getInstance().speakText("�鲻������ϵ��!,�Ƿ���Ҫ���ֻ�������ϵ�˲���");
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
                      PutDataToMap(name,phone,"��������绰",Contacts);
                      Contacts=new ArrayList<Contact>();
					}
					else if (temp.indexOf("$") != -1) {
						 name=temp.substring(0, temp.indexOf("$"));
						 PutDataToMap(name,phone,"�����ල�ٱ��绰",Contacts);
						 Contacts=new ArrayList<Contact>();
					}
					else if (temp.indexOf("%") != -1) {
						 name=temp.substring(0, temp.indexOf("%"));
						 PutDataToMap(name,phone,"ͨ�ŷ���绰",Contacts);
						 Contacts=new ArrayList<Contact>();
					}
					else if (temp.indexOf("&") != -1) {
						 name=temp.substring(0, temp.indexOf("&"));
						 PutDataToMap(name,phone,"���пͻ�����绰����",Contacts);
						 Contacts=new ArrayList<Contact>();
					}
					else if (temp.indexOf("*") != -1) {
						 name=temp.substring(0, temp.indexOf("*"));
						 PutDataToMap(name,phone,"��ݹ�˾�ͷ��绰",Contacts);
						 Contacts=new ArrayList<Contact>();
					}

					else if (temp.indexOf(",") != -1) {
						 name=temp.substring(0, temp.indexOf(","));
						 PutDataToMap(name,phone,"�������͵绰",Contacts);
						 Contacts=new ArrayList<Contact>();
					}
					else if (temp.indexOf("��") != -1) {
						 name=temp.substring(0, temp.indexOf("��"));
						 PutDataToMap(name,phone,"���տͷ��绰",Contacts);
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
	    translationXAnim.setRepeatCount(ValueAnimator.INFINITE);//����ѭ��
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
