package entity;

public class Pay {
   public Pay() {
		super();
		// TODO Auto-generated constructor stub
	}
   private int id;//单据编号
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
   private String MenuName;//单据类型名称；
   private double count;//单据金额
   private String PayTo;//单据出处；
   private String MakePerson;//制单人
   private String Date;//制单时间
   private String MakeNote;//制单备注
   private int status;//单据状态
   public int getStatus() {
	return status;
}
public void setStatus(int status) {
	this.status = status;
}
public String getMenuName() {
	return MenuName;
}
public void setMenuName(String menuName) {
	MenuName = menuName;
}
public double getCount() {
	return count;
}
public void setCount(double count) {
	this.count = count;
}
public String getPayTo() {
	return PayTo;
}
public void setPayTo(String payTo) {
	PayTo = payTo;
}
public String getMakePerson() {
	return MakePerson;
}
public void setMakePerson(String makePerson) {
	MakePerson = makePerson;
}
public String getDate() {
	return Date;
}
public void setDate(String date) {
	Date = date;
}
public String getMakeNote() {
	return MakeNote;
}
public void setMakeNote(String makeNote) {
	MakeNote = makeNote;
}
public Pay(int id, String menuName, double count, String payTo,
		String makePerson, String date, String makeNote, int status) {
	super();
	this.id = id;
	MenuName = menuName;
	this.count = count;
	PayTo = payTo;
	MakePerson = makePerson;
	Date = date;
	MakeNote = makeNote;
	this.status = status;
}


}
