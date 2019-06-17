package entity;

import java.util.Date;

public class Schedule {
     private Date time;
     private String earlyMorning;
     private String Morning;
     private String Noon;
     private String Afternoon;
     private String Evening;
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getEarlyMorning() {
		return earlyMorning;
	}
	public void setEarlyMorning(String earlyMorning) {
		this.earlyMorning = earlyMorning;
	}
	public String getMorning() {
		return Morning;
	}
	public void setMorning(String morning) {
		Morning = morning;
	}
	public String getNoon() {
		return Noon;
	}
	public void setNoon(String noon) {
		Noon = noon;
	}
	public String getAfternoon() {
		return Afternoon;
	}
	public void setAfternoon(String afternoon) {
		Afternoon = afternoon;
	}
	public String getEvening() {
		return Evening;
	}
	public void setEvening(String evening) {
		Evening = evening;
	}
	public Schedule() {
		super();
		
	}
	public Schedule(Date time, String earlyMorning, String morning,
			String noon, String afternoon, String evening) {
		super();
		this.time = time;
		this.earlyMorning = earlyMorning;
		Morning = morning;
		Noon = noon;
		Afternoon = afternoon;
		Evening = evening;
	}
     
}
