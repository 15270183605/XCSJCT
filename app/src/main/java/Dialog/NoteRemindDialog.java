package Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jiacaitong.R;

import entity.Schedule;

public class NoteRemindDialog extends Dialog implements OnClickListener{
private Context context;
private ImageView closeDialog;
private TextView NoteEarly,NoteMorning,NoteNoon,NoteAfternoon,NoteEvening,NoteTime;
  private Schedule schedule;
  private String time;
  private int day;
private static int preDay;
  private static int number;
  private CheckBox NoteCheckBox;
  private  SharedPreferences share;
	private int remind, noremind;
	private boolean flag=false;
	Editor editor;
	public NoteRemindDialog(Context context, String time, Schedule schedule,int day) {
	super(context);
	this.context = context;
	this.schedule = schedule;
	this.time=time;
	this.day=day;
}
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notetixingitem);
		closeDialog=(ImageView)findViewById(R.id.closeText);
		NoteEarly=(TextView)findViewById(R.id.NoteEarly);
		NoteMorning=(TextView)findViewById(R.id.NoteMorning);
		NoteNoon=(TextView)findViewById(R.id.NoteNoon);
		NoteAfternoon=(TextView)findViewById(R.id.NoteAfternoon);
		NoteEvening=(TextView)findViewById(R.id.NoteEvening);
		NoteTime=(TextView)findViewById(R.id.NoteTime);
		NoteCheckBox=(CheckBox)findViewById(R.id.NoteRemindCheckBox);
		NoteCheckBox.setOnClickListener(this);
		closeDialog.setOnClickListener(this);
		NoteTime.setText(time);
		NoteEarly.setText(schedule.getEarlyMorning());
		NoteMorning.setText(schedule.getMorning());
		NoteNoon.setText(schedule.getNoon());
		NoteAfternoon.setText(schedule.getAfternoon());
		NoteEvening.setText(schedule.getEvening());
		share = context.getSharedPreferences("NoteRemind", Context.MODE_WORLD_READABLE);
		editor = share.edit();
		number = share.getInt("remind", 0);
		preDay = share.getInt("time", 0);
		if((day-preDay)!=0){
			number=0;
		}
	}
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.closeText:
			this.dismiss();
			break;
		case R.id.NoteRemindCheckBox:
			
			if(flag==false){
				NoteCheckBox.setChecked(true);
				editor.putInt("time",day);
				editor.putInt("remind", 1);
				flag=true;
			}else{
				NoteCheckBox.setChecked(false);
				editor.putInt("time",day);
				editor.putInt("remind", 0);
				flag=false;
			}
			editor.commit();
			break;
		}

	}
	public static int returnNumber(){
		return preDay;
	}
	/*@Override
	public void onCheckedChanged(CompoundButton parent, boolean flag) {
		
		if(flag){
				editor.putInt("time",day);
				editor.putInt("remind", 0);
				
			} else {
				
				
			}
			
		
	}*/
}
