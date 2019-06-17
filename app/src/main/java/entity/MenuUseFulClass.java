package entity;

public class MenuUseFulClass {
   private int classId;
   private String className;
   private String MenuUsefulName;
public String getMenuUsefulName() {
	return MenuUsefulName;
}
public void setMenuUsefulName(String menuUsefulName) {
	MenuUsefulName = menuUsefulName;
}
public int getClassId() {
	return classId;
}
public void setClassId(int classId) {
	this.classId = classId;
}
public String getClassName() {
	return className;
}
public void setClassName(String className) {
	this.className = className;
}
public MenuUseFulClass(int classId, String className, String menuUsefulName) {
	super();
	this.classId = classId;
	this.className = className;
	MenuUsefulName = menuUsefulName;
}
public MenuUseFulClass() {
	super();
	
}
 
}
