package entity;

public class ImportantNoteE {
   public long id;
   public String Context;
   public String Time;
   public String UserName;
   public String Password;
   public int status;//0代表未加锁，1代表加锁
public ImportantNoteE(long id, String context, String time, String userName,
		String password, int status) {
	super();
	this.id = id;
	Context = context;
	Time = time;
	UserName = userName;
	Password = password;
	this.status = status;
}
public ImportantNoteE() {
	super();
	// TODO Auto-generated constructor stub
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getContext() {
	return Context;
}
public void setContext(String context) {
	Context = context;
}
public String getTime() {
	return Time;
}
public void setTime(String time) {
	Time = time;
}
public String getUserName() {
	return UserName;
}
public void setUserName(String userName) {
	UserName = userName;
}
public String getPassword() {
	return Password;
}
public void setPassword(String password) {
	Password = password;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
}
