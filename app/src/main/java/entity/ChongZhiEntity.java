package entity;

public class ChongZhiEntity {
   private String ChongZhiContent;
   private double ChongZhiNum;
   private String ChongZhiDate;
   private String ChongZhiWay;
   public byte[] image;
public byte[] getImage() {
	return image;
}
public void setImage(byte[] image) {
	this.image = image;
}
public String getChongZhiContent() {
	return ChongZhiContent;
}
public void setChongZhiContent(String chongZhiContent) {
	ChongZhiContent = chongZhiContent;
}
public double getChongZhiNum() {
	return ChongZhiNum;
}
public void setChongZhiNum(double chongZhiNum) {
	ChongZhiNum = chongZhiNum;
}
public String getChongZhiDate() {
	return ChongZhiDate;
}
public void setChongZhiDate(String chongZhiDate) {
	ChongZhiDate = chongZhiDate;
}
public String getChongZhiWay() {
	return ChongZhiWay;
}
public void setChongZhiWay(String chongZhiWay) {
	ChongZhiWay = chongZhiWay;
}

public ChongZhiEntity(String chongZhiContent, double chongZhiNum,
		String chongZhiDate, String chongZhiWay, byte[] image) {
	super();
	ChongZhiContent = chongZhiContent;
	ChongZhiNum = chongZhiNum;
	ChongZhiDate = chongZhiDate;
	ChongZhiWay = chongZhiWay;
	this.image = image;
}
public ChongZhiEntity() {
	super();
	// TODO Auto-generated constructor stub
}

}
