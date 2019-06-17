package entity;

public class JiShiBenEntity {
	public String FileName;
	public String FilePath;
   public String getFileName() {
		return FileName;
	}
	public void setFileName(String fileName) {
		FileName = fileName;
	}
	public String getFilePath() {
		return FilePath;
	}
	public void setFilePath(String filePath) {
		FilePath = filePath;
	}
public String Time;
   public long start;
   public long length;

public JiShiBenEntity(String fileName, String filePath, String time,
		long start, long length) {
	super();
	FileName = fileName;
	FilePath = filePath;
	Time = time;
	this.start = start;
	this.length = length;
}
public String getTime() {
	return Time;
}
public void setTime(String time) {
	Time = time;
}
public long getStart() {
	return start;
}
public void setStart(long start) {
	this.start = start;
}
public long getLength() {
	return length;
}
public void setLength(long length) {
	this.length = length;
}
public JiShiBenEntity() {
	super();
	
}
   
}
