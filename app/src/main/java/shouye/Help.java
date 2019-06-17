package shouye;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import Dialog.ShowPictureDialog;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.jiacaitong.R;

public class Help extends Activity implements OnClickListener {
	private BufferedReader buffer;
	private InputStream inputStream;
	String text = "";
	private ImageView up, left, right, down, HelpBack, imageview;
	private TextView topTitle, topContent, bottomTitle, bottomContent,
			textContents;
	private ViewFlipper viewflipper;
	private int count = 0;
	private int number = 1;
	private String title[][] = { { "开始", "单据设置步骤阅读" },
			{ "单据性质设置", "单据性质设置步骤" }, { "收支管理", "收支单据设置步骤" },
			{ "应收付管理", "应收付单据设置步骤" }, { "实收支管理", "实收支单据设置步骤" },
			{ "数据管理", "数据查看步骤" }, { "图表管理", "图表查看步骤" }, { "总账管理", "总账数据管理步骤" },
			{ "其它功能管理", "其它功能设置步骤" }, { "结束", "步骤阅读完毕" } };
	private String str[][] = { { "设置单据用途", "额外功能操作控制" },
			{ "收支单据录入", "收支单据锁定", "收支单据入账" }, { "应收付单据录入", "应收付单据核销" },
			{ "实收付单据录入", "实收付单据核销", "实收付单据入账" }, { "单一列表数据", "复合列表数据" },
			{ "表数据管理", "统计图数据管理" }, { "月度总账数据管理", "年度总账数据管理" },
			{ "日程安排功能设置", "用户反馈功能设置" } };
	private int image[][] = {
			{ R.drawable.helpset1, R.drawable.helpset2 },
			{ R.drawable.helpshou, R.drawable.helpshoulock,
					R.drawable.helpshourujiezhang },
			{ R.drawable.helpyingshou, R.drawable.helpyingshoufuhexiao },
			{ R.drawable.helpshishou, R.drawable.helphexiaofinish,
					R.drawable.helphexiaofinish },
			{ R.drawable.helpshoulist, R.drawable.helpyingshoufulist1 },
			{ R.drawable.helpsheet, R.drawable.helpgraph },
			{ R.drawable.helpcount1, R.drawable.helpcount3 },
			{ R.drawable.helpnote, R.drawable.helpfeedback } };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.help);
		up = (ImageView) findViewById(R.id.up);
		left = (ImageView) findViewById(R.id.left);
		right = (ImageView) findViewById(R.id.right);
		down = (ImageView) findViewById(R.id.down);
		HelpBack = (ImageView) findViewById(R.id.HelpBack);
		viewflipper = (ViewFlipper) findViewById(R.id.viewflipper);
		topTitle = (TextView) findViewById(R.id.TopTitle);
		topContent = (TextView) findViewById(R.id.TopContent);
		bottomTitle = (TextView) findViewById(R.id.BottomTitle);
		bottomContent = (TextView) findViewById(R.id.BottomContent);
		textContents = (TextView) findViewById(R.id.TitleContent);
		Animation animation3 = AnimationUtils.loadAnimation(this,
				R.anim.down_in);
		down.setAnimation(animation3);
		up.setOnClickListener(this);
		down.setOnClickListener(this);
		left.setOnClickListener(this);
		right.setOnClickListener(this);
		HelpBack.setOnClickListener(this);
		addView();
		readFile();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.up:
			viewflipper.removeAllViews();
			number--;
			count = 0;
			addView();
			break;
		case R.id.down:
			viewflipper.removeAllViews();
			number++;
			count = 0;
			addView();
			break;
		case R.id.left:
			viewflipper.removeAllViews();
			count--;
			addView();
			viewflipper.showPrevious();
			break;
		case R.id.right:
			viewflipper.removeAllViews();
			count++;
			addView();
			viewflipper.showNext();
			break;
		case R.id.HelpBack:
			finish();
			break;
		case R.id.imageview:
             ShowPictureDialog dialog = new ShowPictureDialog(this,
					image[number - 1][count - 1]);
           dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
           dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
           dialog.setCancelable(true);
           dialog.show();
			break;
		}

	}

	public void addView() {
		topTitle.setText(title[number - 1][0]);
		topContent.setText(title[number - 1][1]);
		View view = null;
		if (count == 0) {
			view = LayoutInflater.from(this).inflate(R.layout.helpaddview1,
					null);
			
			TextView text = (TextView) view.findViewById(R.id.Title);
			TextView text1 = (TextView) view.findViewById(R.id.Content);
			text.setText(title[number][0]);
			text1.setText(title[number][1]);
			left.setVisibility(View.GONE);
		} else {
			left.setVisibility(View.VISIBLE);
			view = LayoutInflater.from(this)
					.inflate(R.layout.helpaddiew2, null);
			TextView text = (TextView) view.findViewById(R.id.textview);
			imageview = (ImageView) view.findViewById(R.id.imageview);
			text.setText(str[number - 1][count - 1]);
			imageview.setImageResource(image[number - 1][count - 1]);
			imageview.setOnClickListener(this);
		}
		viewflipper.addView(view);
		if (number == 1) {
			up.setVisibility(View.GONE);
		} else {
			up.setVisibility(View.VISIBLE);
			Animation animation2 = AnimationUtils.loadAnimation(this,
					R.anim.up_in);
			up.setAnimation(animation2);
		}
		if (number == title.length - 2) {
			down.setVisibility(View.GONE);
		} else {
			down.setVisibility(View.VISIBLE);
		}
		if (count == 0) {
			Animation animation = AnimationUtils.loadAnimation(this,
					R.anim.help_out);
			left.setAnimation(animation);
			left.setVisibility(View.GONE);
			Animation animation1 = AnimationUtils.loadAnimation(this,
					R.anim.helpright_in);
			right.setAnimation(animation1);
		} else if (count == str[number - 1].length) {
			Animation animation = AnimationUtils.loadAnimation(this,
					R.anim.help_out);
			right.setAnimation(animation);
			right.setVisibility(View.GONE);
		} else {
			Animation animation1 = AnimationUtils.loadAnimation(this,
					R.anim.helpleft_in);
			left.setAnimation(animation1);
			left.setVisibility(View.VISIBLE);
			Animation aniamtion2 = AnimationUtils.loadAnimation(this,
					R.anim.helpright_in);
			right.setAnimation(aniamtion2);
			right.setVisibility(View.VISIBLE);
		}
		bottomTitle.setText(title[number + 1][0]);
		bottomContent.setText(title[number + 1][1]);
	}

	public void readFile() {
		try {

			inputStream = getAssets().open("helpText.txt");

			buffer = new BufferedReader(new InputStreamReader(inputStream));

			String temp = "";

			while ((temp = buffer.readLine()) != null) {

				temp += "\n";

				text = text + temp;
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}
		textContents.setText(text);

	}

}
