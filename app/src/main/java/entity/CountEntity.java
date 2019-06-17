package entity;

public class CountEntity {
   private String Date;//结账日期
   private double ShouRuCount;//收入总账
   private double ZhiChuCount;//支出总账
   private double YingShouCount;//应收总账
   private double YingFuCount;//应付总账
   private double ShiShouCount;//实收总账
   private double ShiFuCount;//实付总账
   private long ShouRunum;//收入记录数
   private long ZhiChunum;//支出记录数
   private long YingShounum;//应收记录数
   private long YingFunum;//应付记录数
   private long ShiShounum;//实收记录数
   private long ShiFunum;//实付记录数
public String getDate() {
	return Date;
}
public void setDate(String date) {
	Date = date;
}
public double getShouRuCount() {
	return ShouRuCount;
}
public void setShouRuCount(double shouRuCount) {
	ShouRuCount = shouRuCount;
}
public double getZhiChuCount() {
	return ZhiChuCount;
}
public void setZhiChuCount(double zhiChuCount) {
	ZhiChuCount = zhiChuCount;
}
public double getYingShouCount() {
	return YingShouCount;
}
public void setYingShouCount(double yingShouCount) {
	YingShouCount = yingShouCount;
}
public double getYingFuCount() {
	return YingFuCount;
}
public void setYingFuCount(double yingFuCount) {
	YingFuCount = yingFuCount;
}
public double getShiShouCount() {
	return ShiShouCount;
}
public void setShiShouCount(double shiShouCount) {
	ShiShouCount = shiShouCount;
}
public double getShiFuCount() {
	return ShiFuCount;
}
public void setShiFuCount(double shiFuCount) {
	ShiFuCount = shiFuCount;
}
public long getShouRunum() {
	return ShouRunum;
}
public void setShouRunum(long shouRunum) {
	ShouRunum = shouRunum;
}
public long getZhiChunum() {
	return ZhiChunum;
}
public void setZhiChunum(long zhiChunum) {
	ZhiChunum = zhiChunum;
}
public long getYingShounum() {
	return YingShounum;
}
public void setYingShounum(long yingShounum) {
	YingShounum = yingShounum;
}
public long getYingFunum() {
	return YingFunum;
}
public void setYingFunum(long yingFunum) {
	YingFunum = yingFunum;
}
public long getShiShounum() {
	return ShiShounum;
}
public void setShiShounum(long shiShounum) {
	ShiShounum = shiShounum;
}
public long getShiFunum() {
	return ShiFunum;
}
public void setShiFunum(long shiFunum) {
	ShiFunum = shiFunum;
}
public CountEntity() {
	super();
	
}
public CountEntity(String date, double shouRuCount, double zhiChuCount,
		double yingShouCount, double yingFuCount, double shiShouCount,
		double shiFuCount, long shouRunum, long zhiChunum, long yingShounum,
		long yingFunum, long shiShounum, long shiFunum) {
	super();
	Date = date;
	ShouRuCount = shouRuCount;
	ZhiChuCount = zhiChuCount;
	YingShouCount = yingShouCount;
	YingFuCount = yingFuCount;
	ShiShouCount = shiShouCount;
	ShiFuCount = shiFuCount;
	ShouRunum = shouRunum;
	ZhiChunum = zhiChunum;
	YingShounum = yingShounum;
	YingFunum = yingFunum;
	ShiShounum = shiShounum;
	ShiFunum = shiFunum;
}
   
}
