package entity;

public class DataFile {
  public int id;
  public DataFile( String fileName, String dateTime, String filePath,
		String fileLeiXing) {
	super();
	
	FileName = fileName;
	DateTime = dateTime;
	FilePath = filePath;
	FileLeiXing = fileLeiXing;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getFileName() {
	return FileName;
}
public void setFileName(String fileName) {
	FileName = fileName;
}
public String getDateTime() {
	return DateTime;
}
public void setDateTime(String dateTime) {
	DateTime = dateTime;
}
public String getFilePath() {
	return FilePath;
}
public void setFilePath(String filePath) {
	FilePath = filePath;
}
public String getFileLeiXing() {
	return FileLeiXing;
}
public void setFileLeiXing(String fileLeiXing) {
	FileLeiXing = fileLeiXing;
}
public String FileName;
  public String DateTime;
  public String FilePath;
  public String FileLeiXing;
public DataFile() {
	super();
	// TODO Auto-generated constructor stub
}
}
