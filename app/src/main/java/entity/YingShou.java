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
	private int Property;//�������ԣ����֣����֣�
    private String MenuName;//�����������ƣ�
    private double count;//���ݽ��
    private String YingShouSource;//������Դ
    private String YingShouObject;//Ӧ�ն���
    private String MakePerson;//�Ƶ���
    private String Date;//�Ƶ�ʱ��
    private String MakeNote;//�Ƶ���ע
    private int status;//����״̬
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
