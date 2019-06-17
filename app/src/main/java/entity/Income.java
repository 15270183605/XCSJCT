package entity;

public class Income {
	private int id;//单据编号
    private String MenuName;//单据类型名称；
    private double count;//单据金额
    private String IncomeSource;//单据来源
    private String MakePerson;//制单人
    private String Date;//制单时间
    private String MakeNote;//制单备注
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private int status;//单据状态(0-初始状态，1-锁定状态，2-已结账状态)
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
