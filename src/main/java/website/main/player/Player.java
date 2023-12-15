package website.main.player;

public class Player {
	
	private long id;
	private String fullName;
	private String email;
	private String phone;
	private String password;
	private String nickname;
	
	public Player() {
		super();
	}
	public Player(String fullName, String email, String phone, String password, String nickname) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.nickname = nickname;
	}
	public Player(long id, String fullName, String email, String phone, String password, String nickname) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.password = password;
		this.nickname = nickname;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", fullName=" + fullName + ", email=" + email + ", phone=" + phone + ", password="
				+ password + ", nickname=" + nickname + "]";
	}

}
