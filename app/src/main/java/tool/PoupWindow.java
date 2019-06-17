package tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.jiacaitong.R;

public class PoupWindow extends PopupWindow implements View.OnClickListener {
	private View mMenuView;
	private OnPopWindowClickListener listener;

	private Activity activity;

	private ImageView PoupWindowImage, DaoRuImage, JiShiBenImage, DaoRu,
			JiShiBen, DaoChu, DaoChuImage, SaoMaImage, SaoMa, Call, CallImage;
	private float DphotoW, DphotoH, DTaobaoW, DTaobaoH, DShouyeW, DShouyeH,
			photoW, photoH, TaobaoW, TaobaoH, ShouyeW, ShouyeH, CamerW,
			DCamerW, CamerH, DCamerH, CallW, CallH, DCallW, DCallH;

	public PoupWindow(Activity activity, OnPopWindowClickListener listener) {
		this.activity = activity;
		initView(activity, listener);
		
	}

	public void show() {
		Rect rect = new Rect();
		/*
		 * getWindow().getDecorView()得到的View是Window中的最顶层View，可以从Window中获取到该View，
		 * 然后该View有个getWindowVisibleDisplayFrame()方法可以获取到程序显示的区域， 包括标题栏，但不包括状态栏。
		 */
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int winHeight = activity.getWindow().getDecorView().getHeight();
		this.showAtLocation(activity.getWindow().getDecorView(),
				Gravity.BOTTOM, 0, winHeight - rect.bottom);
	}

	private void initView(Activity activity, OnPopWindowClickListener listener) {
		// 设置按钮监听
		this.listener = listener;
		initViewSetting(activity);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		startAnimation();
	}

	private void initViewSetting(Activity context) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.poupwindow, null);

		PoupWindowImage = (ImageView) mMenuView
				.findViewById(R.id.PoupWindowImage);
		PoupWindowImage.setOnClickListener(this);

		JiShiBen = (ImageView) mMenuView.findViewById(R.id.JiShiBen);
		JiShiBen.setOnClickListener(this);

		DaoRu = (ImageView) mMenuView.findViewById(R.id.DaoRu);
		DaoRu.setOnClickListener(this);

		DaoChu = (ImageView) mMenuView.findViewById(R.id.DaoChu);
		DaoChu.setOnClickListener(this);

		SaoMa = (ImageView) mMenuView.findViewById(R.id.SaoMa);
		SaoMa.setOnClickListener(this);

		Call = (ImageView) mMenuView.findViewById(R.id.Call);
		Call.setOnClickListener(this);

		DaoRuImage = (ImageView) mMenuView.findViewById(R.id.DaoRuImage);
		JiShiBenImage = (ImageView) mMenuView.findViewById(R.id.JiShiBenImage);
		DaoChuImage = (ImageView) mMenuView.findViewById(R.id.DaoChuImage);
		SaoMaImage = (ImageView) mMenuView.findViewById(R.id.SaoMaImage);
		CallImage = (ImageView) mMenuView.findViewById(R.id.CallImage);

	}

	@Override
	public void onClick(View view) {
		listener.onPopWindowClickListener(view);
		if (view.getId() == R.id.PoupWindowImage) {
			exitAnimation();
		}

	}

	// 接口
	public interface OnPopWindowClickListener {
		void onPopWindowClickListener(View view);

	}

	// 进去界面的动画
	private void startAnimation() {
		// 最下面的添加按钮旋转按钮动画
		RotateAnimation rotateAnimation = new RotateAnimation(0, 45,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(300);
		rotateAnimation.setInterpolator(new BounceInterpolator());
		rotateAnimation.setFillAfter(true);
		PoupWindowImage.startAnimation(rotateAnimation);
		rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				// 默认记事本图片
				int location[] = new int[2];
				JiShiBenImage.getLocationOnScreen(location);
				DphotoW = location[0];
				DphotoH = location[1];
				// 之后的导入图片
				int location1[] = new int[2];
				DaoRu.getLocationOnScreen(location1);
				TaobaoW = location1[0];
				TaobaoH = location1[1];
				// 之后的导出图片
				int locationshou[] = new int[2];
				DaoChu.getLocationOnScreen(locationshou);
				ShouyeW = locationshou[0];
				ShouyeH = locationshou[1];
				// 之后的相机图片
				int locationcamer[] = new int[2];
				SaoMa.getLocationOnScreen(locationcamer);
				CamerW = locationcamer[0];
				CamerH = locationcamer[1];
				// 之后的call图片
				int locationcall[] = new int[2];
				Call.getLocationOnScreen(locationcall);
				CallW = locationcall[0];
				CallH = locationcall[1];
				// 之后的记事本图片
				int location2[] = new int[2];
				JiShiBen.getLocationOnScreen(location2);
				photoW = location2[0];
				photoH = location2[1];

				// 默认的call图片
				int deflocationcall[] = new int[2];
				CallImage.getLocationOnScreen(deflocationcall);
				DCallW = deflocationcall[0];
				DCallH = deflocationcall[1];
				// 默认的相机图片
				int deflocationcamer[] = new int[2];
				SaoMaImage.getLocationOnScreen(deflocationcamer);
				DCamerW = deflocationcamer[0];
				DCamerH = deflocationcamer[1];
				// 默认导出的图片位置
				int locationshou1[] = new int[2];
				DaoChuImage.getLocationOnScreen(locationshou1);
				DShouyeW = locationshou1[0];
				DShouyeH = locationshou1[1];

				// 默认导入的图片位置
				int location3[] = new int[2];
				DaoRuImage.getLocationOnScreen(location3);
				DTaobaoW = location3[0];
				DTaobaoH = location3[1];

				Method(DphotoW, DphotoH, photoW, photoH, 300, JiShiBen,
						JiShiBenImage);

				Method(DTaobaoW, DTaobaoH, TaobaoW, TaobaoH, 700, DaoRu,
						DaoRuImage);
				Method(DShouyeW, DShouyeH, ShouyeW, ShouyeH, 500, DaoChu,
						DaoChuImage);
				Method(DCamerW, DCamerH, CamerW, CamerH, 600, SaoMa, SaoMaImage);
				Method(DCallW, DCallH, CallW, CallH, 500, Call, CallImage);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

	}

	// /出去的动画
	private void exitAnimation() {
		RotateAnimation rotateAnimation = new RotateAnimation(0, 90,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(300);
		rotateAnimation.setInterpolator(new BounceInterpolator());
		PoupWindowImage.startAnimation(rotateAnimation);
		Exit(DTaobaoW, DTaobaoH, TaobaoW, TaobaoH, 700, DaoRu);
		Exit(DphotoW, DphotoH, photoW, photoH, 300, JiShiBen);
		Exit(DShouyeW, DShouyeH, ShouyeW, ShouyeH, 500, DaoChu);
		Exit(DCamerW, DCamerH, CamerW, CamerH, 600, SaoMa);
		Exit(DCallW, DCallH, CallW, CallH, 500, Call);
	}

	public void Method(float dw, float dh, float w, float h, long time,
			final ImageView image1, final ImageView image2) {
		TranslateAnimation sa = new TranslateAnimation(0, w - dw, 0, h - dh);
		sa.setDuration(time);
		sa.setInterpolator(new BounceInterpolator());
		image2.startAnimation(sa);
		sa.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				image2.setVisibility(View.GONE);
				image1.setVisibility(View.VISIBLE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

	}

	public void Exit(float dw, float dh, float w, float h, long time,
			final ImageView image1) {

		TranslateAnimation sa = new TranslateAnimation(0, dw - w, 0, dh - h);
		sa.setDuration(time);
		sa.setInterpolator(new BounceInterpolator());
		sa.setFillAfter(true);
		image1.startAnimation(sa);
		sa.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				JiShiBen.setVisibility(View.GONE);
				DaoChu.setVisibility(View.GONE);
				DaoRu.setVisibility(View.GONE);
				SaoMa.setVisibility(View.GONE);
				Call.setVisibility(View.GONE);
				dismiss();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}
}
