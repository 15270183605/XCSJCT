package jishiben;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sqlite.JSBUISQLite;
import tool.ColorPickerDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiacaitong.R;

import entity.JiShiBenEntity;

public class UnImNote extends Activity implements OnClickListener {
	private TextView UIJSBbianji, UIJSBSave, UIJSBSeek, UIJSBLook, UIJSBUpdate,QuSeText,
			PreViewText, UIJSBYuLan, UIJSBReplace, UIJSBStyle;
	private ImageView JSBUISearchText;
	private EditText WriteContent, JSBUIEditText, OldText, NewText,
			FileContent, TextSize, TextColor;
	private LinearLayout StyleLayout,QuSeLayout;
	private RelativeLayout ReplaceLayout;
	private ReadAndWriteFile readorwritefile;
	private String FilePath = "/sdcard/LiCaiJSB/";
	private String FileName = "licai.txt";
	private JSBUISQLite jsbuiSqlite;
	private SQLiteDatabase db;
	private String time;
	private JiShiBenEntity entity;
	private AlertDialog dialog;
	private int biaozhi;
	private SpannableString ss;

	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.jishibennote);
		init();
	}

	public void init() {
		Calendar cal = Calendar.getInstance();
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		time = format.format(date);
		UIJSBbianji = (TextView) findViewById(R.id.UIJSBbianji);
		UIJSBSave = (TextView) findViewById(R.id.UIJSBSave);
		UIJSBSeek = (TextView) findViewById(R.id.UIJSBSeek);
		UIJSBLook = (TextView) findViewById(R.id.UIJSBLook);
		UIJSBUpdate = (TextView) findViewById(R.id.UIJSBUpdate);
		JSBUISearchText = (ImageView) findViewById(R.id.JSBUISearchText);
		WriteContent = (EditText) findViewById(R.id.WriteContent);
		JSBUIEditText = (EditText) findViewById(R.id.JSBUIEditText);
		UIJSBbianji.setOnClickListener(this);
		UIJSBSave.setOnClickListener(this);
		UIJSBSeek.setOnClickListener(this);
		UIJSBLook.setOnClickListener(this);
		JSBUISearchText.setOnClickListener(this);
		UIJSBUpdate.setOnClickListener(this);
		readorwritefile = new ReadAndWriteFile();
		jsbuiSqlite = new JSBUISQLite(this, "JSBUI.db", null, 1);
		db = jsbuiSqlite.getReadableDatabase();
		entity = new JiShiBenEntity();
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.UIJSBbianji:
			WriteContent.setFocusable(true);
			WriteContent.setFocusableInTouchMode(true);
			WriteContent.requestFocus();
			BackGroundInit(UIJSBbianji);
			break;
		case R.id.UIJSBLook:
			BackGroundInit(UIJSBLook);
			OpenFile(FilePath + FileName);

			break;
		case R.id.UIJSBSave:
			SaveData();
			break;
		case R.id.UIJSBSeek:
			if(WriteContent.getText().toString().trim().length()!=0){
			FindDialog();
			BackGroundInit(UIJSBSeek);}
			break;
		case R.id.JSBUISearchText:
			if (JSBUIEditText.getText().toString().trim().length() != 0) {
				if (jsbuiSqlite.getCountByTime(db, JSBUIEditText.getText()
						.toString()) != 0) {

					entity = jsbuiSqlite.QueryFileData(db, JSBUIEditText
							.getText().toString());
					WriteContent.setText(readorwritefile.GetContent(FilePath
							+ FileName, entity.getStart(), entity.getLength()));
				}
				WriteContent.setFocusable(false);
				UIJSBSave.setText("添加");
			}
			break;
		case R.id.UIJSBUpdate:
			BackGroundInit(UIJSBUpdate);
			try {
				readorwritefile.UpdateFileData(FilePath + FileName, entity
						.getStart(), entity.getStart() + entity.getLength(),
						WriteContent.getText().toString());
				jsbuiSqlite.UpdateFileDataLength(db, time,
						readorwritefile.firststart - entity.getStart());
			} catch (IOException e) {
				e.printStackTrace();
			}
			Toast.makeText(this, "更新完成", 1000).show();
			break;
		case R.id.UIJSBYuLan:
			DialogButtonInit(UIJSBYuLan);
			if (biaozhi == 0) {
				ReplaceText();
			} else {
				TextStyle();
			}
			break;
		case R.id.UIJSBReplace:
			DialogButtonInit(UIJSBReplace);
			ReplaceLayout.setVisibility(View.VISIBLE);
			StyleLayout.setVisibility(View.GONE);
			QuSeLayout.setVisibility(View.GONE);
			biaozhi = 0;
			break;
		case R.id.UIJSBStyle:
			DialogButtonInit(UIJSBStyle);
			ReplaceLayout.setVisibility(View.GONE);
			StyleLayout.setVisibility(View.VISIBLE);
			QuSeLayout.setVisibility(View.VISIBLE);
			biaozhi = 1;
			break;
		case R.id.FindTextSure:
			WriteContent.setText(PreViewText.getText());
			dialog.dismiss();
			break;
		case R.id.FindTextCancel:
			dialog.dismiss();
			break;
		case R.id.QuSe:
			QuSeDialog();
			break;
		}

	}

	// 根据给定的路径打开文件
	public void OpenFile(String path) {
		if (path != null) {
			try {
				File file = new File(path);
				Intent intent2 = new Intent("android.intent.action.VIEW");
				intent2.addCategory("android.intent.category.DEFAULT");
				intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Uri uri = Uri.fromFile(file);
				if(uri==null){
					Toast.makeText(this, "文件暂无生成，您先记录您的第一条事件才能生成！", 1000).show();
				}else{
				if (path.contains(".docx")) {
					intent2.setDataAndType(uri, "application/msword");
				} else if (path.contains(".xlsx")) {
					intent2.setDataAndType(uri, "application/vnd.ms-excel");
				} else {
					intent2.setDataAndType(uri, "text/plain");
				}
				startActivity(intent2);
				}} catch (Exception e) {
				// 没有安装第三方的软件会提示
				Toast toast = Toast.makeText(this, "没有找到打开该文件的应用程序",
						Toast.LENGTH_SHORT);
				toast.show();
			}
			
		}
		
	}

	public void findPathOpenFile() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");// 设置类型，我这里是任意类型，任意后缀的可以这样写。
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, 1);

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 1) {
				Uri uri = data.getData();
				OpenFile(uri.getPath().toString());
			}
		}

	}

	// 背景色的初始化;
	public void BackGroundInit(TextView view) {
		UIJSBbianji.setBackgroundResource(R.drawable.jinjipost);
		UIJSBSave.setBackgroundResource(R.drawable.jinjipost);
		UIJSBSeek.setBackgroundResource(R.drawable.jinjipost);
		UIJSBLook.setBackgroundResource(R.drawable.jinjipost);
		UIJSBUpdate.setBackgroundResource(R.drawable.jinjipost);
		view.setBackgroundResource(R.drawable.putongpost);
	}
	// 背景色的初始化;
		public void DialogButtonInit(TextView view) {
			UIJSBYuLan.setBackgroundResource(R.drawable.jinjipost);
			UIJSBReplace.setBackgroundResource(R.drawable.jinjipost);
			UIJSBStyle.setBackgroundResource(R.drawable.jinjipost);
			view.setBackgroundResource(R.drawable.putongpost);
		}

	// 查找弹出框;
	public void FindDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(
				new ContextThemeWrapper(this, R.style.Alert));
		View view = LayoutInflater.from(this).inflate(
				R.layout.jishibenuifinddialog, null);
		UIJSBYuLan = (TextView) view.findViewById(R.id.UIJSBYuLan);
		UIJSBReplace = (TextView) view.findViewById(R.id.UIJSBReplace);
		UIJSBStyle = (TextView) view.findViewById(R.id.UIJSBStyle);
		TextView FindTextSure = (TextView) view.findViewById(R.id.FindTextSure);
		TextView FindTextCancel = (TextView) view
				.findViewById(R.id.FindTextCancel);
		ImageView QuSe=(ImageView)view.findViewById(R.id.QuSe);
		PreViewText = (TextView) view.findViewById(R.id.PreViewText);
		QuSeText=(TextView) view.findViewById(R.id.QuSeText);
		OldText = (EditText) view.findViewById(R.id.OldText);
		NewText = (EditText) view.findViewById(R.id.NewText);
		FileContent = (EditText) view.findViewById(R.id.FileContent);
		TextSize = (EditText) view.findViewById(R.id.TextSize);
		TextColor = (EditText) view.findViewById(R.id.TextColor);
		StyleLayout = (LinearLayout) view.findViewById(R.id.StyleLayout);
		QuSeLayout = (LinearLayout) view.findViewById(R.id.QuSeLayout);
		ReplaceLayout = (RelativeLayout) view.findViewById(R.id.ReplaceLayout);
		UIJSBYuLan.setOnClickListener(this);
		UIJSBReplace.setOnClickListener(this);
		UIJSBStyle.setOnClickListener(this);
		FindTextSure.setOnClickListener(this);
		FindTextCancel.setOnClickListener(this);
		QuSe.setOnClickListener(this);
		PreViewText.setText(WriteContent.getText());
		builder.setView(view);
		dialog = builder.show();
	}

	// 文本内容中的某个字段的替换
	public void ReplaceText() {
		if (OldText.getText().toString().trim().length() != 0) {
			String text = PreViewText.getText().toString()
					.replace(OldText.getText(), NewText.getText());
			PreViewText.setText(text);
		}

	}

	// 设置文本中某段内容的字体样式
	public void TextStyle() {
		ss = new SpannableString(PreViewText.getText());
		Pattern pattern = Pattern.compile(FileContent.getText().toString());
		Matcher matcher = pattern.matcher(ss);
		if (TextColor.getText().toString().trim().length() != 0
				&& TextSize.getText().toString().trim().length() != 0) {
			while (matcher.find()) {
				int start = matcher.start();
				int end = matcher.end();
				ss.setSpan(
						new ForegroundColorSpan(Integer.parseInt(TextColor
								.getText().toString())), start, end,
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
				ss.setSpan(
						new AbsoluteSizeSpan(Integer.parseInt(TextSize
								.getText().toString()), true), start, end,
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

		}
		if (TextColor.getText().toString().trim().length() != 0  ) {
			while (matcher.find()) {
				int start = matcher.start();
				int end = matcher.end();
				ss.setSpan(
						new ForegroundColorSpan(Integer.parseInt(TextColor
								.getText().toString())), start, end,
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

		}
		if (TextSize.getText().toString().trim().length() != 0) {
			while (matcher.find()) {
				int start = matcher.start();
				int end = matcher.end();
				ss.setSpan(
						new AbsoluteSizeSpan(Integer.parseInt(TextSize
								.getText().toString()), true), start, end,
						Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

		}
		
		PreViewText.setText(ss);

	}
	//取色弹出框
public void QuSeDialog(){
	ColorPickerDialog dialog = new ColorPickerDialog(UnImNote.this,
			Color.BLACK,null,
			new ColorPickerDialog.OnColorChangedListener() {
				public void colorChanged(int color) {
					TextColor.setText(String.valueOf(color));
					QuSeText.setTextColor(color);
				}
			});
	   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
       dialog.show();
	   dialog.show();
}
//保存数据
public void SaveData(){
	BackGroundInit(UIJSBSave);
	if (UIJSBSave.getText().toString().equals("保存")) {
		if (WriteContent.getText().toString().trim().length() != 0) {
			readorwritefile.WriteFile(
					WriteContent.getText().toString(), FilePath,
					FileName);
			if (jsbuiSqlite.getCountByTime(db, time) == 0) {
				JiShiBenEntity entity = new JiShiBenEntity(FileName,
						FilePath, time, readorwritefile.firststart,
						readorwritefile.len);
				jsbuiSqlite.AddFileData(db, entity);
			} else {
				jsbuiSqlite.UpdateFileDataLength(db, time,
						jsbuiSqlite.QueryFileDataLength(db, time)
								+ readorwritefile.len);
			}
			UIJSBSave.setText("添加");
			Toast.makeText(this, "保存完成", 1000).show();
		}else{
			Toast.makeText(this, "事件为空,保存失败", 1000).show();
		}
		
	} else if (UIJSBSave.getText().toString().equals("添加")) {
		UIJSBSave.setText("保存");
	}

	WriteContent.setFocusable(true);
	WriteContent.setFocusableInTouchMode(true);
	WriteContent.requestFocus();
	WriteContent.setText("");
}
}