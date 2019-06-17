package work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loginOrRegister.Main;
import mine.MyInfo;
import sqlite.UserSQLite;
import usercenter.UserCenter;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.FamilyMember;

public class mine extends Activity implements OnClickListener {
	private static int number;
	private ListView MyListView;
	private LayoutInflater inflater;
	private LinearLayout MyHeadView;
	private UserSQLite usersqlite;
	private SQLiteDatabase db;
	private ImageView headImage;
	private TextView name, nickname, tele;
	private FamilyMember member;
	List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
	private int[] imaged = new int[] { R.drawable.exit, R.drawable.image47 };
	private String msg[] = new String[] { "退出登录", "个人中心" };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mine);
		init();
		number = 0;
		MyListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				if (position == 1) {
					number = 1;
					Intent intent = new Intent(mine.this, Main.class);
					startActivity(intent);
					finish();
				}
				if (position == 2) {
					Intent intent = new Intent(mine.this, UserCenter.class);
					startActivity(intent);
				}

			}
		});
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.headImage:
			AddUserData();
			break;
		}

	}

	public static int ExitDengLu() {
		return number;
	}

	public void init() {
		for (int i = 0; i < imaged.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("myheadImage", imaged[i]);
			map.put("myHeadText", msg[i]);
			listItems.add(map);
		}
		member = new FamilyMember();
		MyListView = (ListView) findViewById(R.id.MyListView);
		inflater = LayoutInflater.from(this);
		MyHeadView = (LinearLayout) inflater.inflate(R.layout.myheader, null);
		headImage = (ImageView) MyHeadView.findViewById(R.id.headImage);
		name = (TextView) MyHeadView.findViewById(R.id.name);
		nickname = (TextView) MyHeadView.findViewById(R.id.nickName);
		tele = (TextView) MyHeadView.findViewById(R.id.tele);
		headImage.setOnClickListener(this);
		AddUserData();
		MyListView.addHeaderView(MyHeadView);
		SimpleAdapter adapter = new SimpleAdapter(this, listItems,
				R.layout.mylistviewitem, new String[] { "myHeadText",
						"myheadImage" }, new int[] { R.id.myheadText,
						R.id.myheadImage });
		MyListView.setAdapter(adapter);
		MyHeadView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra("username", member.getUserName());
				intent.setClass(mine.this, MyInfo.class);
				startActivity(intent);
			}
		});
	}

	public Bitmap getBmp(byte[] in) {
		Bitmap bmpout = BitmapFactory.decodeByteArray(in, 0, in.length);
		return bmpout;
	}

	public void AddUserData() {
		
		usersqlite = new UserSQLite(this, "FamilyFinance.db", null, 1);
		db = usersqlite.getReadableDatabase();
		member = usersqlite.queryMember(db, Main.returnName());
		if (member.getHeadImage().length != 0) {
			headImage.setImageBitmap(getBmp(member.getHeadImage()));
		}
		name.setText(member.getUserName());
		StringBuffer phone = new StringBuffer(String.valueOf(member
				.getPhoneNumber()));
		StringBuffer phonenumber = phone.replace(3, 9, "******");
		String str = String.valueOf(phonenumber);
		nickname.setText(member.getNickName());
		tele.setText(str);
	}
}
