package entity;

public class Template {
    private int Id;
    private int MenuId;
    private String makeperson;
    private String source;
    private String menuName;
    private double count;
    private String time;
    private int status;
    private int Property;
	public int getMenuId() {
		return MenuId;
	}
	public void setMenuId(int menuId) {
		MenuId = menuId;
	}
	public int getProperty() {
		return Property;
	}
	public void setProperty(int property) {
		Property = property;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getMakeperson() {
		return makeperson;
	}
	public void setMakeperson(String makeperson) {
		this.makeperson = makeperson;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Template() {
		super();
	
	}
    
}
