package entity;

public class CallLogEntity {
  public String CallLogName;
  public String phone;
  public  String type;
  public  String Time;
  private String  duration;
public String getCallLogName() {
	return CallLogName;
}
public void setCallLogName(String callLogName) {
	CallLogName = callLogName;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public CallLogEntity() {
	super();
	// TODO Auto-generated constructor stub
}
public CallLogEntity(String callLogName, String phone, String type, String time,
		String duration) {
	super();
	CallLogName = callLogName;
	this.phone = phone;
	this.type = type;
	Time = time;
	this.duration = duration;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getTime() {
	return Time;
}
public void setTime(String time) {
	Time = time;
}
public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}
}
