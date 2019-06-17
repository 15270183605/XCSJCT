package entity;

public class GuDingMingXi {
public String ProjectName;
public String MingXiName;
public double count;
public int property;
public String getProjectName() {
	return ProjectName;
}
public void setProjectName(String projectName) {
	ProjectName = projectName;
}
public String getMingXiName() {
	return MingXiName;
}
public void setMingXiName(String mingXiName) {
	MingXiName = mingXiName;
}
public double getCount() {
	return count;
}
public void setCount(double count) {
	this.count = count;
}
public int getProperty() {
	return property;
}
public void setProperty(int property) {
	this.property = property;
}
public GuDingMingXi(String projectName, String mingXiName, double count,
		int property) {
	super();
	ProjectName = projectName;
	MingXiName = mingXiName;
	this.count = count;
	this.property = property;
}
public GuDingMingXi() {
	super();
	// TODO Auto-generated constructor stub
}

}
