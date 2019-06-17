package entity;

public class GuDingEntity {
	private int id;
   public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
public GuDingEntity(int id, String className, String projectName,
			double guDingCount) {
		super();
		this.id = id;
		ClassName = className;
		ProjectName = projectName;
		GuDingCount = guDingCount;
	}
private String ClassName;
   private String ProjectName;
   private double GuDingCount;
public String getClassName() {
	return ClassName;
}
public void setClassName(String className) {
	ClassName = className;
}
public String getProjectName() {
	return ProjectName;
}
public void setProjectName(String projectName) {
	ProjectName = projectName;
}
public double getGuDingCount() {
	return GuDingCount;
}
public void setGuDingCount(double guDingCount) {
	GuDingCount = guDingCount;
}

public GuDingEntity(String className, String projectName, double guDingCount) {
	super();
	ClassName = className;
	ProjectName = projectName;
	GuDingCount = guDingCount;
	
}
public GuDingEntity() {
	super();
	
}
   
}
