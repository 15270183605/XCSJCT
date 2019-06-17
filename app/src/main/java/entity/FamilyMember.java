package entity;

public class FamilyMember {
    private String userName;//用户名
    private String phoneNumber;//用户号码
    private String pwd;//用户密码
    private int status;//在职状态
    private double salary;//薪资
    private int loginstatus;//在线状态
    private String NickName;//昵称
    private byte[] headImage;
    private int count;//记录进入平台次数；
    private String Date;
    private byte[] MyCode;
    public byte[] getMyCode() {
		return MyCode;
	}
	public void setMyCode(byte[] myCode) {
		MyCode = myCode;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public byte[] getHeadImage() {
		return headImage;
	}
	public void setHeadImage(byte[] headImage) {
		this.headImage = headImage;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public int getLoginstatus() {
		return loginstatus;
	}
	public void setLoginstatus(int loginstatus) {
		this.loginstatus = loginstatus;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public FamilyMember() {
		super();
		
	}
	
}
