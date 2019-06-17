package entity;

public class Contact {
   private String username;
   private String phone;
public Contact(String username, String phone) {
	super();
	this.username = username;
	this.phone = phone;
}

public Contact() {
	super();
	// TODO Auto-generated constructor stub
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}


}
