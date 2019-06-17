package entity;

public class JieZhangTemplate {
   private String ClassName;
   private double Count;
   private String Date;
   private int  status;
   private long id;
   private String object;
public String getObject() {
	return object;
}
public void setObject(String object) {
	this.object = object;
}
public String getClassName() {
	return ClassName;
}
public void setClassName(String className) {
	ClassName = className;
}
public double getCount() {
	return Count;
}
public void setCount(double count) {
	Count = count;
}
public String getDate() {
	return Date;
}
public void setDate(String date) {
	Date = date;
}
public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}

public JieZhangTemplate(String className, double count, String date,
		int status, long id, String object) {
	super();
	ClassName = className;
	Count = count;
	Date = date;
	this.status = status;
	this.id = id;
	this.object = object;
}
public JieZhangTemplate() {
	super();
	// TODO Auto-generated constructor stub
}
   
}
