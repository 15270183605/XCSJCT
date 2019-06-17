package life.lifemoney;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.support.v4.view.*;
import loginOrRegister.Main;
import sqlite.ChongZhiSQLite;
import sqlite.UserSQLite;
import userrefreedback.UserRefreedBack;
import Adapters.GridViewAdapter;
import Adapters.InputPwdAdapter;
import Adapters.LifeGridAdapter;
import Adapters.PayCardListAdapter;
import Adapters.PayDialogViewPager;
import Adapters.PwdInputAdapter;
import Dialog.JSBPDialog;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.FamilyMember;
import entity.PayCard;

public class ChongZhi extends Activity implements OnClickListener {
	private GridView ChongZhiGrid, MoreChongZhi,PwdGridView;
	private ImageView ChongBack, SelectPeople, QueryYuE, LiuLiang, ZiDongChong,
			ChongImage, SelectPay, JiaZaiBottom1, JiaZaiBottom2, JiaZaiBottom3,
			ZhiFuImage, PayRebackview1, BackView2,BankCardImage;
	private TextView ChongZhiContact, ChongContactName, PayText,PayJinE,DindDanContent,PayCardText,PayProgressText,BankClass;
	private EditText ChongPhone,InputCardNum,ChongZhiNumber;
	private RelativeLayout AddBankLayout;
	private int Images[] = { R.drawable.shiyuan, R.drawable.ershi,
			R.drawable.sanshi, R.drawable.wushi, R.drawable.yibai,
			R.drawable.erbai, R.drawable.sanbai, R.drawable.wubai,
			R.drawable.addchongzhi };
	private UserSQLite usersqlite;
	private ChongZhiSQLite chongzhisqlite;
	private SQLiteDatabase db,db1;
	private String contactPhone;
	private int MoreChong[] = { R.drawable.shopcard, R.drawable.buscard,
			R.drawable.chongyou, R.drawable.chonggame };
	private String MoreChongText[] = { "购物卡", "公交卡", "油卡", "游戏卡" };
	String PwdNumbers[]={"1","2","3","4","5","6","7","8","9"," ","0","x"};
	private List<ImageView> imageviews;
	private int paymenthod = 1;
	private JSBPDialog dialog,progressdialog,AddBankDialog,AddChongZhiDialog;
	private Dialog dialog1;
	private int count = 0,paystatue=0,count1=0, Times = 0,num,Position;// 计数变量
	public Timer mTimer,mTimer1;
	public TimerTask timetask,timetask1;
	private int randomnumber;
	private List<PayCard> PayDatas;
	private ViewPager payviewpager;
	private double PayCount;
	private List<String> pwdnumber;
	private StringBuffer Pwd;
	  private CustomStatusView customStatusView;
	  private String PoupDatas[]={"建设银行","农商银行","工商银行","中国银行","中国人民银行","中国民生银行","中国邮政","招商银行","中国农业银行","交通银行"};
	   private int BankImages[]={R.drawable.jianhang,R.drawable.nongshang,R.drawable.gongshang,R.drawable.zhonghang,R.drawable.renhang,R.drawable.minhang,R.drawable.youzheng,R.drawable.zhaoshang,R.drawable.nonghang,R.drawable.jiaotong};
	  private PopupWindow popupWindow;
	  private PayCardListAdapter paycardadapter;
	  FamilyMember member=new FamilyMember();
	  SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");   
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int what = msg.what;
			switch (what) {
			case 1:
				stopTask();
				dialog.dismiss();
				PayDialog();
				break;
			case 2:
				InitBottomImage();
				break;
			case 3:
				if(paystatue==1){
					 customStatusView.loadSuccess();
					 PayProgressText.setText("支付成功!");
					 String date=sDateFormat.format(new Date(System.currentTimeMillis())); 
					 chongzhisqlite.insertData(db1, DindDanContent.getText().toString(), PayCount, date, PayCardText.getText().toString(), img(R.drawable.chonghuafei));					 
				}else{
					customStatusView.loadFailure();
					 PayProgressText.setText("支付失败!");
				}
				break;
			case 4:
				progressdialog.dismiss();
				num=0;
				if(paystatue==1){
					dialog1.dismiss();
				}else{
					pwdnumber.clear();
					for(int i=0;i<6;i++){
						pwdnumber.add("");
					}
					RefreshAdapter();
				}
				stopTask();
				break;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chongzhi);
		init();
	}

	public void init() {
		ChongZhiGrid = (GridView) findViewById(R.id.ChongZhiGrid);
		MoreChongZhi = (GridView) findViewById(R.id.MoreChongZhi);
		ChongBack = (ImageView) findViewById(R.id.ChongZhiBack);
		SelectPeople = (ImageView) findViewById(R.id.SelectPeople);
		QueryYuE = (ImageView) findViewById(R.id.QueryYuE);
		LiuLiang = (ImageView) findViewById(R.id.LiuLiang);
		ZiDongChong = (ImageView) findViewById(R.id.ZiDongChong);
		ChongImage = (ImageView) findViewById(R.id.ChongImage);
		SelectPay = (ImageView) findViewById(R.id.SelectPay);
		ChongZhiContact = (TextView) findViewById(R.id.ChongZhiContact);
		ChongContactName = (TextView) findViewById(R.id.ChongContactName);
		ChongPhone = (EditText) findViewById(R.id.ChongPhone);
		LifeGridAdapter adapter = new LifeGridAdapter(this, Images, 2);
		ChongZhiGrid.setAdapter(adapter);
		GridViewAdapter adapter1 = new GridViewAdapter(ChongZhi.this,
				MoreChong, MoreChongText, 1);
		MoreChongZhi.setAdapter(adapter1);
		ChongBack.setOnClickListener(this);
		SelectPeople.setOnClickListener(this);
		QueryYuE.setOnClickListener(this);
		LiuLiang.setOnClickListener(this);
		ZiDongChong.setOnClickListener(this);
		ChongZhiContact.setOnClickListener(this);
		ChongImage.setOnClickListener(this);
		SelectPay.setOnClickListener(this);
		ChongPhone.setOnClickListener(this);
		usersqlite = new UserSQLite(this, "FamilyFinance.db", null, 1);
		db = usersqlite.getReadableDatabase();
		ChongContactName.setText(Main.returnName());
		member=usersqlite.queryMember(db, Main.returnName());
		ChongPhone.setText(DealPhone(member.getPhoneNumber()));
		AddGridClick();
		ChongPhone.setFocusable(false);
	}
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.ChongZhiBack:
			finish();
			break;
		case R.id.SelectPeople:
			SendConatct();
			break;
		case R.id.QueryYuE:
			Toast.makeText(this, "余额查询", Toast.LENGTH_LONG).show();
			break;
		case R.id.LiuLiang:
			Toast.makeText(this, "流量查询", Toast.LENGTH_LONG).show();
			break;
		case R.id.ZiDongChong:
			Toast.makeText(this, "自动充值", Toast.LENGTH_LONG).show();
			break;

		case R.id.ChongZhiContact:
			Intent it=new Intent(this,ChongZhiJiLu.class);
			it.putExtra("time", Integer.parseInt(member.getDate().substring(0,4)));
			startActivity(it);
			break;
		case R.id.ChongImage:
			Toast.makeText(this, "查看更多优惠", Toast.LENGTH_LONG).show();
			break;
		case R.id.SelectPay:
			if (paymenthod == 0) {
				SelectPay.setImageResource(R.drawable.zhifubaopay);
				paymenthod = 1;
				Toast.makeText(this, "支付宝支付已打开", Toast.LENGTH_LONG).show();
			} else {
				SelectPay.setImageResource(R.drawable.weixinpay);
				paymenthod = 0;
				Toast.makeText(this, "微信支付已打开", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.ChongPhone:
			ChongPhone.setFocusable(true);
			ChongPhone.setFocusableInTouchMode(true);
			ChongPhone.requestFocus();
			break;
		case R.id.BackView2:
			payviewpager.setCurrentItem(1);
			break;
		case R.id.ClosePayDialog:
			dialog1.dismiss();
			break;
		case R.id.PayReback:
			Intent intent=new Intent(this, UserRefreedBack.class);
			startActivity(intent);
			break;
		case R.id.SelectPayCard:
			payviewpager.setCurrentItem(0);
			break;
		case R.id.FuKuan:
			num=0;
			payviewpager.setCurrentItem(2);
			pwdnumber.clear(); 
			for(int i=0;i<6;i++){
				pwdnumber.add("");
			}
			RefreshAdapter();
			break;
		case R.id.PayRebackview1:
			Intent intent1=new Intent(this, UserRefreedBack.class);
			startActivity(intent1);
			break;
		case R.id.PwdBackView2:
			payviewpager.setCurrentItem(1);
			break;
		case R.id.PayForgetPwd:
			Toast.makeText(this, "请问有什么需要帮助的吗？", Toast.LENGTH_LONG).show();
		      break;
		case R.id.BankExpandDown:
			initPop(BankClass);
			if (popupWindow != null && !popupWindow.isShowing()) {
				popupWindow.showAsDropDown(AddBankLayout, 0, 0);
			}
			else{
				popupWindow.dismiss();
			}
			break;
		case R.id.SaveBankCard:
			if(InputCardNum.getText().toString().trim().length()==0 || InputCardNum.getText().toString().trim().length()!=18){
				Toast.makeText(this, "银行卡号不合法!", Toast.LENGTH_LONG).show();
			}else {
				PayCard card = new PayCard();
				card.setCardImage(img(BankImages[Position]));
				card.setCardMoney(0.0);
				card.setCardName(BankClass.getText().toString());
				card.setCardNumber(InputCardNum.getText().toString());
				PayDatas.add(card);
				paycardadapter.notifyDataSetChanged();
				Toast.makeText(this, "保存成功!", Toast.LENGTH_LONG).show();
				AddBankDialog.dismiss();
			}
			break;
		case R.id.CancelBankCard:
			AddBankDialog.dismiss();
			break;
		case R.id.CancelChongZhi:
			AddChongZhiDialog.dismiss();
			break;
		case R.id.ChongZhi:
			if(Integer.parseInt(ChongZhiNumber.getText().toString())<=0){
				Toast.makeText(this, "充值金额不能小于或等于0！", Toast.LENGTH_SHORT).show();
			}else{
				PayCount=Double.parseDouble(ChongZhiNumber.getText().toString());
				JiaZaiDialog();
				AddChongZhiDialog.dismiss();
			}
			break;
		}

	}

	// 调用联系人查看联系人
	public void SendConatct() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_PICK);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
		startActivityForResult(intent, 0x12);
	}

	// 数据回调
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
			String contactName = null;
			while (phoneCursor.moveToNext()) {
				contactPhone = phoneCursor
						.getString(phoneCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				contactName = phoneCursor
						.getString(phoneCursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			}
			phoneCursor.close();
			ChongContactName.setText(contactName);
			ChongPhone.setText(DealPhone(contactPhone));
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	public String DealPhone(String phone) {
		StringBuffer buffer = new StringBuffer();
		int length = phone.length() - 3;
		buffer.append(phone.substring(0, 3));
		buffer.append(" ");
		int count = length / 4;
		int num = 0;
		for (int i = 0; i < count; i++) {
			buffer.append(phone.substring(num + 3, num + 7));
			buffer.append(" ");
			num = num + 4;
		}
		if ((length % 4) != 0) {
			buffer.append(phone.substring((phone.length() - (length % 4)),
					phone.length()));
		}
		return buffer.toString();
	}

	// 对GridView加监听
	public void AddGridClick() {
		ChongZhiGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (ChongPhone.getText().toString().indexOf(" ") == -1) {
					ChongPhone.setText(DealPhone(ChongPhone.getText()
							.toString()));
				}
				if (position == 0) {
					JiaZaiDialog();
					PayCount=10;
				}
				if (position == 1) {
					JiaZaiDialog();
					PayCount=20;
				}
				if (position == 2) {
					JiaZaiDialog();
					PayCount=30;
				}

				if (position == 3) {
					JiaZaiDialog();
					PayCount=50;
				}
				if (position == 4) {
					JiaZaiDialog();
					PayCount=100;
				}
				if (position == 5) {
					JiaZaiDialog();
					PayCount=200;
				}
				if (position == 6) {
					JiaZaiDialog();
					PayCount=300;
				}
				if (position == 7) {
					JiaZaiDialog();
					PayCount=500;
				}
				if (position == 8) {
					AddChongZhiDialog();
					
				}
			}
		});

		MoreChongZhi.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == 0) {
					Toast.makeText(ChongZhi.this, "购物卡充值", Toast.LENGTH_LONG).show();
				}
				if (position == 1) {
					Toast.makeText(ChongZhi.this, "公交车充值", Toast.LENGTH_LONG).show();
				}
				if (position == 2) {
					Toast.makeText(ChongZhi.this, "油卡充值", Toast.LENGTH_LONG).show();
				}
				if (position == 3) {
					Toast.makeText(ChongZhi.this, "游戏卡充值", Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	// 支付加载弹出框
	public void JiaZaiDialog() {
		View view = LayoutInflater.from(this).inflate(R.layout.payjiazaidialog,
				null);
		JiaZaiBottom1 = (ImageView) view.findViewById(R.id.JiaZaiBottom1);
		JiaZaiBottom2 = (ImageView) view.findViewById(R.id.JiaZaiBottom2);
		JiaZaiBottom3 = (ImageView) view.findViewById(R.id.JiaZaiBottom3);
		ZhiFuImage = (ImageView) view.findViewById(R.id.ZhiFuImage);
		PayText = (TextView) view.findViewById(R.id.PayText);
		imageviews = new ArrayList<ImageView>();
		imageviews.add(JiaZaiBottom1);
		imageviews.add(JiaZaiBottom2);
		imageviews.add(JiaZaiBottom3);
		if (paymenthod == 1) {
			ZhiFuImage.setImageResource(R.drawable.zhifubaopay);
			PayText.setText("支付宝");
		}else {
			ZhiFuImage.setImageResource(R.drawable.weixinzhifu);
			PayText.setText("微信");
		}
		dialog = new JSBPDialog(this, view);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		dialog.show();
		dialog.setCancelable(false);
		Times = 0;
		randomnumber = new Random().nextInt(4) + 8;
		startTimer();
	}
	// 设置更换时间
	public void startTimer() {
		mTimer = new Timer();
		timetask = new TimerTask() {
			public void run() {
				count++;
				Times++;
				if (count == 3) {
					count = 0;
				}

				if (Times <= randomnumber) {
					handler.sendEmptyMessage(2);
				} else {
					handler.sendEmptyMessage(1);
				}
			}
		};
		mTimer.schedule(timetask, 1 * 200, 1 * 200);
	}

	// 任务停止；
	public void stopTask() {
		try {
			mTimer.cancel();
			mTimer1.cancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 设置支付时间
		public void startTimer1() {
			count1=0;
			mTimer1 = new Timer();
			timetask1= new TimerTask() {
				public void run() {
					count1=count1+1;
					if(count1==1){
					handler.sendEmptyMessage(3);}
					else{
						handler.sendEmptyMessage(4);
					}
				}
			};
			mTimer1.schedule(timetask1, 1 * 2000, 1 * 2000);
		}
	public void InitBottomImage() {
		JiaZaiBottom1.setImageResource(R.drawable.pay_bottom_circle);
		JiaZaiBottom2.setImageResource(R.drawable.pay_bottom_circle);
		JiaZaiBottom3.setImageResource(R.drawable.pay_bottom_circle);
		imageviews.get(count).setImageResource(R.drawable.pay_circle);
	}

	// 支付弹出框
	public void PayDialog() {
		chongzhisqlite=new ChongZhiSQLite(this, "Chongzhi.db", null, 1);
		db1=chongzhisqlite.getReadableDatabase();
		List<View> views = new ArrayList<View>();
		View view = LayoutInflater.from(this).inflate(R.layout.paydialog, null);
		View view1 = LayoutInflater.from(this).inflate(R.layout.paydialogview1,
				null);
		views.add(view1);
		View view2 = LayoutInflater.from(this).inflate(R.layout.paydialogview2,
				null);
		views.add(view2);
		View view3 = LayoutInflater.from(this).inflate(R.layout.paydialogview3,
				null);
		views.add(view3);
		payviewpager = (ViewPager) view.findViewById(R.id.PayDialogViewPager);
		PayDialogViewPager adapter = new PayDialogViewPager(views);
		payviewpager.setAdapter(adapter);
		dialog1 = new Dialog(ChongZhi.this, R.style.DiaologAlert);
		dialog1.setContentView(view);
		dialog1.setCancelable(true);
		Window window = dialog1.getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.DialogStyle);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = this.getWindowManager().getDefaultDisplay().getWidth();
		window.setAttributes(lp);
		dialog1.show();
		initview1(view1);
		initview2(view2);
		initview3(view3);
		//通过反射强制设置到指定页面
		try {
			Field field = payviewpager.getClass().getField("mCurItem");
			field.setAccessible(true);
			field.setInt(payviewpager, 2);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// 通过数据修改
		adapter.notifyDataSetChanged(); 
			// 切换到指定页面
		payviewpager.setCurrentItem(1);
	}

	// 将初始化的图片转换成二进制存储
	public byte[] img(int id) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(id))
				.getBitmap();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
	// 实例化View1
	public void initview1(View view) {
		PayDatas = new ArrayList<PayCard>();
		PayCard card = new PayCard();
		card.setCardImage(img(R.drawable.yue));
		card.setCardMoney(0.0);
		card.setCardName("余额");
		card.setCardNumber("**************6207");
		PayDatas.add(card);
		PayCard card1 = new PayCard();
		card1.setCardImage(img(R.drawable.friendhelp));
		card1.setCardMoney(0.0);
		card1.setCardName("找朋友代付");
		card1.setCardNumber("**************6207");
		PayDatas.add(card1);
		View view1=LayoutInflater.from(this).inflate(R.layout.paylistviewitem, null);
		BackView2 = (ImageView) view.findViewById(R.id.BackView2);
		ImageView PayRebackview1=(ImageView)view.findViewById(R.id.PayRebackview1);
		BackView2.setOnClickListener(this);
		PayRebackview1.setOnClickListener(this);
		final ListView PayCardList = (ListView) view
				.findViewById(R.id.PayCardList);
		PayCardList.addFooterView(view1);
	     paycardadapter = new PayCardListAdapter(this,
				PayDatas);
		PayCardList.setAdapter(paycardadapter);
		PayCardList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				// adapter.getView(position, view,
				// parent).findViewById(R.id.PaySelect).setVisibility(View.VISIBLE);
				/*PayCardList.getSelectedView().findViewById(R.id.PaySelect)
						.setVisibility(View.VISIBLE);*/
				if(position==PayCardList.getCount()-1){
					AddCardDialog();
				}else{
				payviewpager.setCurrentItem(1);
				PayCardText.setText(PayDatas.get(position).getCardName());
			}}
		});
	}
	//实例化View2
	public void initview2(View view){
		TextView ClosePayDialog=(TextView)view.findViewById(R.id.ClosePayDialog);
		ImageView PayReback=(ImageView)view.findViewById(R.id.PayReback);
		LinearLayout SelectPayCard=(LinearLayout)view.findViewById(R.id.SelectPayCard);
	    TextView FuKuan=(TextView)view.findViewById(R.id.FuKuan);
	    PayJinE=(TextView)view.findViewById(R.id.PayJinE);
	    DindDanContent=(TextView)view.findViewById(R.id.DindDanContent);
	    PayCardText=(TextView)view.findViewById(R.id.PayCardText);
	    ClosePayDialog.setOnClickListener(this);
	    PayReback.setOnClickListener(this);
	    SelectPayCard.setOnClickListener(this);
	    FuKuan.setOnClickListener(this);
	    PayJinE.setText(String.valueOf(new BigDecimal(PayCount*0.998).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()));
	    DindDanContent.setText(DealChongPhone()+"  "+"手机充值");
	}

	//实例化View3
	public void initview3(View view){
		pwdnumber=new ArrayList<String>();
		for(int i=0;i<6;i++){
			pwdnumber.add("");
		}
		ImageView PwdBackView2=(ImageView)view.findViewById(R.id.PwdBackView2);
		PwdGridView=(GridView)view.findViewById(R.id.PwdGridView);
		TextView PayForgetPwd=(TextView)view.findViewById(R.id.PayForgetPwd);
		GridView InputPwdGridView=(GridView)view.findViewById(R.id.InputPwdGridView);
		PwdBackView2.setOnClickListener(this);
		PayForgetPwd.setOnClickListener(this);
		RefreshAdapter();
		PwdInputAdapter adapter=new PwdInputAdapter(PwdNumbers, this);
		InputPwdGridView.setAdapter(adapter);
		AddGridClick(InputPwdGridView);
		num=0;
	}
	//处理电话号码
	public String DealChongPhone(){
		String phone=ChongPhone.getText().toString();
		phone=phone.replace(" ", "-");
		phone=phone.substring(0, phone.length()-1);
		return phone;
	}
	//刷新Adapter;
	public void RefreshAdapter(){
		InputPwdAdapter adapter=new InputPwdAdapter(pwdnumber, this);
		PwdGridView.setAdapter(adapter);
	}
	//对InputPwdGridView加item监听
	public void AddGridClick(GridView InputPwdGridView){
		InputPwdGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parnet, View view, int position,
					long id) {
				Pwd=new StringBuffer();
				if(position!=9 && position!=11){
					if(num<6){
						if(num<0){
							num=0;
						}
					pwdnumber.set(num, PwdNumbers[position]);
					num=num+1;}else{
						num=6;
					}
				}
				if(position==11){
					if(num>0){
						if(num>=6){
							num=6;
						}
					num=num-1;
					pwdnumber.set(num, "");
					}
					else{
						num=0;
					}
				}
				RefreshAdapter();
				for(int i=0;i<pwdnumber.size();i++){
					Pwd.append(pwdnumber.get(i));
				}
				
			if(Pwd.toString().length()==6){
				AddPayProgress();
				if((Pwd.toString()).equals("123456")){
					paystatue=1;
				}else{
					paystatue=0;
				}
			}
			}
			
		});
		
	}
	//添加支付加载框
	public void AddPayProgress(){
		View view=LayoutInflater.from(this).inflate(R.layout.payprogress,null);
		ImageView PayLeiXing=(ImageView)view.findViewById(R.id.PayLeiXing);
		customStatusView = (CustomStatusView)view.findViewById(R.id.PayProgress);
		PayProgressText=(TextView)view.findViewById(R.id.PayProgressText);
		progressdialog = new JSBPDialog(this, view);
		progressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progressdialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		progressdialog.show();
		progressdialog.setCancelable(false);
		 customStatusView.loadLoading();
		 if(paymenthod==1){
			 PayLeiXing.setImageResource(R.drawable.zhifubaopay);
		 }else{
			 PayLeiXing.setImageResource(R.drawable.weixinzhifu);
		 }
		 startTimer1();
	}
	//添加银行卡弹出框
	public void AddCardDialog(){
		View view=LayoutInflater.from(this).inflate(R.layout.addbankcard,null);
		 BankClass=(TextView)view.findViewById(R.id.BankClass);
		TextView SaveBankCard=(TextView)view.findViewById(R.id.SaveBankCard);
		TextView CancelBankCard=(TextView)view.findViewById(R.id.CancelBankCard);
		ImageView BankExpandDown=(ImageView)view.findViewById(R.id.BankExpandDown);
		BankCardImage=(ImageView)view.findViewById(R.id.BankCardImage);
		InputCardNum=(EditText)view.findViewById(R.id.InputCardNum);
		AddBankLayout=(RelativeLayout)view.findViewById(R.id.AddBankLayout);
		SaveBankCard.setOnClickListener(this);
		CancelBankCard.setOnClickListener(this);
		BankExpandDown.setOnClickListener(this);
		AddBankDialog=new JSBPDialog(this, view);
		AddBankDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		AddBankDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		AddBankDialog.setCancelable(true);
		AddBankDialog.show();
	}
	//选择类型下拉框
	private void initPop(final TextView textView) {						 
			ListView mlistView = new ListView(this);						
			ArrayAdapter<String> popDataAdapter = new ArrayAdapter<String>(this,R.layout.jsbinjiamipoupwindowitem, PoupDatas);						
			View view=LayoutInflater.from(this).inflate(R.layout.jsbinjiamipoupwindowitem, null);
			mlistView.setAdapter(popDataAdapter);	
			mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
									
				public void onItemClick(AdapterView<?>					
				parent, View view, int position, long id) {	
					Position=position;
						textView.setText(PoupDatas[position]);
						BankCardImage.setImageResource(BankImages[position]);
						popupWindow.dismiss(); } });
								popupWindow = new PopupWindow(mlistView,AddBankLayout.getWidth()-50,
								
							ActionBar.LayoutParams.WRAP_CONTENT, true);
								
			      popupWindow.setOnDismissListener(new
									
					PopupWindow.OnDismissListener() { 
									
				public void onDismiss() {
									
						popupWindow.dismiss(); } });
			
			popupWindow.setAnimationStyle(R.style.popmenu_animation);
									
			popupWindow.setFocusable(true);
									
			popupWindow.setOutsideTouchable(true); }
	//添加自定义充值弹出框
	public void AddChongZhiDialog(){
		View view=LayoutInflater.from(this).inflate(R.layout.addchongzhinumber, null);
		TextView ChongZhiPhone=(TextView)view.findViewById(R.id.ChongZhiPhone);
		ChongZhiNumber=(EditText)view.findViewById(R.id.ChongZhiNumber);
		TextView ChongZhi=(TextView)view.findViewById(R.id.ChongZhi);
		TextView CancelChongZhi=(TextView)view.findViewById(R.id.CancelChongZhi);
		CancelChongZhi.setOnClickListener(this);
		ChongZhi.setOnClickListener(this);
		ChongZhiPhone.setText(ChongPhone.getText().toString());
		AddChongZhiDialog=new JSBPDialog(this, view);
		AddChongZhiDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		AddChongZhiDialog.setCancelable(true);
		AddChongZhiDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		AddChongZhiDialog.show();
	}
	
}