package entity;

public class UserRefreedEntity {
    private int type;//0表示管理员，1表示用户
    private String NickName;
    private String UserName;
    private byte[] HeadImage;
    private String Content;
    private Long time;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNickName() {
		return NickName;
	}
	public void setNickName(String nickName) {
		NickName = nickName;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public byte[] getHeadImage() {
		return HeadImage;
	}
	public void setHeadImage(byte[] headImage) {
		HeadImage = headImage;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public UserRefreedEntity() {
		super();
		
	} 
    
}
