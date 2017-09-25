package entity;


import exception.MsgException;

public class User {
	private int id;
	private String username;
	private String password;
	private String password2;
	private String nickname;
	private String email;

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password="
				+ password + ", nickname=" + nickname + ", email=" + email
				+ "]";
	}


	public void check() {
		if(username == null || "".equals(username)){
			throw new MsgException("用户名不能为空");
		}

		if(password == null || "".equals(password)){
			throw new MsgException( "密码不能为空");
		}

		if(password2 == null || "".equals(password2)){
			throw new MsgException("确认密码不能为空");
		}

		if(nickname == null || "".equals(nickname)){
			throw new MsgException("昵称不能为空");
		}

		if(email == null || "".equals(email)){
			throw new MsgException("邮箱不能为空");
		}
		//>两次密码是否一致校验
		if(!password.equals(password2)){
			throw new MsgException( "两次密码不一致");
		}

		//>邮箱格式
		//abc123@163.com
		if(!email.matches("^\\w+@\\w+(\\.\\w+)+$")){
			throw new MsgException("邮箱格式不正确");
		}
	}
}
