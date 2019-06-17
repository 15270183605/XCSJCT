package entity;

public class GraphTemplate {
  public String MenuName;
  private double Count;
public String getMenuName() {
	return MenuName;
}
public void setMenuName(String menuName) {
	MenuName = menuName;
}
public double getCount() {
	return Count;
}
public void setCount(double count) {
	Count = count;
}
public GraphTemplate() {
	super();
}
public GraphTemplate(String menuName, float count) {
	super();
	MenuName = menuName;
	Count = count;
}
}
