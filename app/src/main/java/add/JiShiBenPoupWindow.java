package add;
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
import android.widget.TextView;

import com.example.jiacaitong.R;

public class JiShiBenPoupWindow extends PopupWindow implements View.OnClickListener {
	private View mMenuView;
	private OnPopWindowClickListener listener;

	private Activity activity;

	private ImageView ExpandImage, Video, VideoImage, Picture,
			PictureImage, Note, NoteImage, Note1, Note1Image;
	private TextView VideoText,PictureText,NoteText,Note1Text;
	private float DVideoW, DVideoH, DPictureW, DPictureH, DNoteW, DNoteH,DNote1W, DNote1H,
			NoteW, NoteH, Note1W, Note1H,VideoW,VideoH,PictureW,PictureH;

	public JiShiBenPoupWindow(Activity activity, OnPopWindowClickListener listener) {
		this.activity = activity;
		initView(activity, listener);
		
	}

	public void show() {
		Rect rect = new Rect();
		/*
		 * getWindow().getDecorView()�õ���View��Window�е����View�����Դ�Window�л�ȡ����View��
		 * Ȼ���View�и�getWindowVisibleDisplayFrame()�������Ի�ȡ��������ʾ������ ��������������������״̬����
		 */
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
		int winHeight = activity.getWindow().getDecorView().getHeight();
		this.showAtLocation(activity.getWindow().getDecorView(),
				Gravity.BOTTOM, 0, winHeight - rect.bottom);
	}

	private void initView(Activity activity, OnPopWindowClickListener listener) {
		// ���ð�ť����
		this.listener = listener;
		initViewSetting(activity);
		// ����SelectPicPopupWindow��View
		this.setContentView(mMenuView);
		// ����SelectPicPopupWindow��������Ŀ�
		this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		// ����SelectPicPopupWindow��������ĸ�
		this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		// ����SelectPicPopupWindow��������ɵ��
		this.setFocusable(true);
		// ʵ����һ��ColorDrawable��ɫΪ��͸��
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// ����SelectPicPopupWindow��������ı���
		this.setBackgroundDrawable(dw);
		startAnimation();
	}

	private void initViewSetting(Activity context) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.jishibenpoupwindow, null);

		ExpandImage = (ImageView) mMenuView
				.findViewById(R.id.ExpandImage);
		ExpandImage.setOnClickListener(this);

		Video = (ImageView) mMenuView.findViewById(R.id.Video);
		Video.setOnClickListener(this);

		Picture = (ImageView) mMenuView.findViewById(R.id.Picture);
		Picture.setOnClickListener(this);

		Note = (ImageView) mMenuView.findViewById(R.id.ImNote);
		Note.setOnClickListener(this);

		Note1 = (ImageView) mMenuView.findViewById(R.id.Note);
		Note1.setOnClickListener(this);

       VideoImage = (ImageView) mMenuView.findViewById(R.id.VideoImage);
		PictureImage = (ImageView) mMenuView.findViewById(R.id.PictureImage);
		NoteImage = (ImageView) mMenuView.findViewById(R.id.ImNoteImage);
		Note1Image = (ImageView) mMenuView.findViewById(R.id.NoteImage);
		
		VideoText = (TextView) mMenuView.findViewById(R.id.VideoTextView);
		PictureText = (TextView) mMenuView.findViewById(R.id.PictureTextView);
		NoteText = (TextView) mMenuView.findViewById(R.id.ImportantNote);
		Note1Text = (TextView) mMenuView.findViewById(R.id.NoteText);
	}

	@Override
	public void onClick(View view) {
		listener.onPopWindowClickListener(view);
		if (view.getId() == R.id.ExpandImage) {
			exitAnimation();
		}

	}

	// �ӿ�
	public interface OnPopWindowClickListener {
		void onPopWindowClickListener(View view);

	}

	// ��ȥ����Ķ���
	private void startAnimation() {
		// ���������Ӱ�ť��ת��ť����
		RotateAnimation rotateAnimation = new RotateAnimation(0, 45,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(300);
		rotateAnimation.setInterpolator(new BounceInterpolator());
		rotateAnimation.setFillAfter(true);
		ExpandImage.startAnimation(rotateAnimation);
		rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

				// Ĭ��videoͼƬ
				int location[] = new int[2];
				VideoImage.getLocationOnScreen(location);
				DVideoW = location[0];
				DVideoH = location[1];
				// ֮���һ���¼�ͼƬ
				int location1[] = new int[2];
				Note1.getLocationOnScreen(location1);
				Note1W = location1[0];
				Note1H = location1[1];
				// ֮�����Ҫ�¼�ͼƬ
				int locationshou[] = new int[2];
				Note.getLocationOnScreen(locationshou);
				NoteW = locationshou[0];
				NoteH = locationshou[1];
				// ֮���pictureͼƬ
				int locationcamer[] = new int[2];
				Picture.getLocationOnScreen(locationcamer);
				PictureW = locationcamer[0];
				PictureH = locationcamer[1];
				// ֮���videoͼƬ
				int locationcall[] = new int[2];
				Video.getLocationOnScreen(locationcall);
				VideoW = locationcall[0];
				VideoH = locationcall[1];

				// Ĭ�ϵ�pictureͼƬ
				int deflocationcall[] = new int[2];
				PictureImage.getLocationOnScreen(deflocationcall);
				DPictureW = deflocationcall[0];
				DPictureH = deflocationcall[1];
				// Ĭ�ϵ���Ҫ�¼�ͼƬ
				int deflocationcamer[] = new int[2];
				NoteImage.getLocationOnScreen(deflocationcamer);
				DNoteW = deflocationcamer[0];
				DNoteH = deflocationcamer[1];
				// Ĭ��һ���¼�
				int locationshou1[] = new int[2];
				Note1Image.getLocationOnScreen(locationshou1);
				DNote1W = locationshou1[0];
				DNote1H = locationshou1[1];
				
				Method(DVideoW, DVideoH, VideoW, VideoH, 700, Video,
						VideoImage,VideoText);

				Method(DPictureW, DPictureH, PictureW, PictureH, 600, Picture,
						PictureImage,PictureText);
				Method(DNoteW, DNoteH, NoteW, NoteH, 500, Note,
						NoteImage,NoteText);
				Method(DNote1W, DNote1H,Note1W, Note1H, 600, Note1, Note1Image,Note1Text);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

	}

	// /��ȥ�Ķ���
	private void exitAnimation() {
		RotateAnimation rotateAnimation = new RotateAnimation(0, 90,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setDuration(300);
		rotateAnimation.setInterpolator(new BounceInterpolator());
		ExpandImage.startAnimation(rotateAnimation);
		VideoText.setVisibility(View.GONE);
		PictureText.setVisibility(View.GONE);
		NoteText.setVisibility(View.GONE);
		Note1Text.setVisibility(View.GONE);
		Exit(DVideoW, DVideoH, VideoW, VideoH, 700, Video);
		Exit(DPictureW, DPictureH, PictureW, PictureH, 600, Picture);
		Exit(DNoteW, DNoteH, NoteW, NoteH, 500, Note);
		Exit(DNote1W, DNote1H,Note1W, Note1H, 600, Note1);
	}

	public void Method(float dw, float dh, float w, float h, long time,
			final ImageView image1, final ImageView image2,final TextView text) {
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
				text.setVisibility(View.VISIBLE);
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
				Video.setVisibility(View.GONE);
				Picture.setVisibility(View.GONE);
				Note.setVisibility(View.GONE);
				Note1.setVisibility(View.GONE);
				dismiss();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
	}
}
