package entity;

import android.widget.ImageView;

public class TianQi {
   private String date;
   private String tianqi;
   private String Condition;
   private int image;
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getTianqi() {
	return tianqi;
}
public void setTianqi(String tianqi) {
	this.tianqi = tianqi;
}
public String getCondition() {
	return Condition;
}
public void setCondition(String condition) {
	Condition = condition;
}
public int getImage() {
	return image;
}
public void setImage(int image) {
	this.image = image;
}
public TianQi() {
	super();
	// TODO Auto-generated constructor stub
}
}
