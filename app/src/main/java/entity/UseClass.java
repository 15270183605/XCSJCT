package entity;

public class UseClass {
   private int UseId;
   private String UseName;
public int getUseId() {
	return UseId;
}
public void setUseId(int useId) {
	UseId = useId;
}
public String getUseName() {
	return UseName;
}
public void setUseName(String useName) {
	UseName = useName;
}
public UseClass(int useId, String useName) {
	super();
	UseId = useId;
	UseName = useName;
}
   
}
