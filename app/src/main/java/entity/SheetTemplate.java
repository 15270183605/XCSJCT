package entity;

public class SheetTemplate {
    public String ClassName;
    public String ClassSource;
    public double Count;
    public String time;
    public String operation;
	public String getClassName() {
		return ClassName;
	}
	public void setClassName(String className) {
		ClassName = className;
	}
	public String getClassSource() {
		return ClassSource;
	}
	public void setClassSource(String classSource) {
		ClassSource = classSource;
	}
	public double getCount() {
		return Count;
	}
	public void setCount(double totalCount) {
		Count = totalCount;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public SheetTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SheetTemplate(String className, String classSource, float count,
			String time, String operation) {
		super();
		ClassName = className;
		ClassSource = classSource;
		Count = count;
		this.time = time;
		this.operation = operation;
	}
    
} 
