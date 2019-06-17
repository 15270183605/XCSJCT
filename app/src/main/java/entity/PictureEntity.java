package entity;

public class PictureEntity {
  public  String content;
  public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getPath() {
	return Path;
}
public void setPath(String path) {
	Path = path;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public PictureEntity() {
	super();
	// TODO Auto-generated constructor stub
}
public PictureEntity(String content, String path, String time) {
	super();
	this.content = content;
	Path = path;
	this.time = time;
}
public String Path;
  private String time;
}
