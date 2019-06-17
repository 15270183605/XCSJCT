package entity;

public class CountPredict {
  public CountPredict() {
		super();
		// TODO Auto-generated constructor stub
	}
double ShouPredictCount;
  double ZhiChuPreCount;
  String Date;
  String ShouDeviate;
  String ZhiDeviate;
public CountPredict(double shouPredictCount, double zhiChuPreCount,
		String date, String shouDeviate, String zhiDeviate) {
	super();
	ShouPredictCount = shouPredictCount;
	ZhiChuPreCount = zhiChuPreCount;
	Date = date;
	ShouDeviate = shouDeviate;
	ZhiDeviate = zhiDeviate;
}
public double getShouPredictCount() {
	return ShouPredictCount;
}
public void setShouPredictCount(double shouPredictCount) {
	ShouPredictCount = shouPredictCount;
}
public double getZhiChuPreCount() {
	return ZhiChuPreCount;
}
public void setZhiChuPreCount(double zhiChuPreCount) {
	ZhiChuPreCount = zhiChuPreCount;
}
public String getDate() {
	return Date;
}
public void setDate(String date) {
	Date = date;
}
public String getShouDeviate() {
	return ShouDeviate;
}
public void setShouDeviate(String shouDeviate) {
	ShouDeviate = shouDeviate;
}
public String getZhiDeviate() {
	return ZhiDeviate;
}
public void setZhiDeviate(String zhiDeviate) {
	ZhiDeviate = zhiDeviate;
}
}
