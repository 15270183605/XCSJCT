package jishiben;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.jiacaitong.R;

public class PlayVideo extends Activity {
	private String path;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playvideo);
	final VideoView video=(VideoView)findViewById(R.id.videoview);
	final ImageView stop=(ImageView)findViewById(R.id.Stop);
	stop.setVisibility(View.GONE);
	Intent intent = getIntent();
	path = intent.getStringExtra("video");
	video.setVideoPath(path);
	video.start();
	video.setOnTouchListener(new View.OnTouchListener() {
         @Override
         public boolean onTouch(View v, MotionEvent event) {
             if (video.isPlaying()){
            	 video.pause();
            	 stop.setVisibility(View.VISIBLE);
             }else {
            	 video.start();
            	 stop.setVisibility(View.GONE);
             }
             return false;
         }
     });

     //…Ë÷√—≠ª∑≤•∑≈
	video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
         @Override
         public void onCompletion(MediaPlayer mp) {
             mp.start();
             mp.setLooping(true);
         }
     });

 }
}
