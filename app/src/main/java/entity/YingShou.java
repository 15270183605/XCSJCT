 package entity;

public class YingShou {
	public YingShou() {
		super();
		// TODO Auto-generated constructor stub
	}
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private int Property;//单据属性（红字，蓝字）
    private String MenuName;//单据类型名称；
    private double count;//单据金额
    private String YingShouSource;//单据来源
    private String YingShouObject;//应收对象
    private String MakePerson;//制单人
    private String Date;//制单时间
    private String MakeNote;//制单备注
    private int status;//单据状态
    private String telephone;
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getProperty() {
		return Property;
	}
	public void setProperty(int property) {
		Property = property;
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
	public String getYingShouSource() {
		return YingShouSource;
	}
	public void setYingShouSource(String yingShouSource) {
		YingShouSource = yingShouSource;
	}
	public String getYingShouObject() {
		return YingShouObject;
	}
	public void setYingShouObject(String yingShouObject) {
		YingShouObject = yingShouObject;
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
	public YingShou(int id, int property, String menuName, double count,
			String yingShouSource, String yingShouObject, String makePerson,
			String date, String makeNote, int status, String telephone) {
		super();
		this.id = id;
		Property = property;
		MenuName = menuName;
		this.count = count;
		YingShouSource = yingShouSource;
		YingShouObject = yingShouObject;
		MakePerson = makePerson;
		Date = date;
		MakeNote = makeNote;
		this.status = status;
		this.telephone = telephone;
	}
	
	
}
