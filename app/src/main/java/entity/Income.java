package entity;

public class Income {
	private int id;//���ݱ��
    private String MenuName;//�����������ƣ�
    private double count;//���ݽ��
    private String IncomeSource;//������Դ
    private String MakePerson;//�Ƶ���
    private String Date;//�Ƶ�ʱ��
    private String MakeNote;//�Ƶ���ע
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private int status;//����״̬(0-��ʼ״̬��1-����״̬��2-�ѽ���״̬)
	public Income() {
		super();
		// TODO Auto-generated constructor stub
	}
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
	public String getIncomeSource() {
		return IncomeSource;
	}
	public void setIncomeSource(String incomeSource) {
		IncomeSource = incomeSource;
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
	public Income(int id, String menuName, double count, String incomeSource,
			String makePerson, String date, String makeNote, int status) {
		super();
		this.id = id;
		MenuName = menuName;
		this.count = count;
		IncomeSource = incomeSource;
		MakePerson = makePerson;
		Date = date;
		MakeNote = makeNote;
		this.status = status;
	}
	
}
