package telran.security.dto;

import java.util.Set;

public class AccountDto {

	String username;
	String password;
	Set<String> roles;
	
	public AccountDto(String username, String password, Set<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.roles = roles;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Set<String> getRoles() {
		return roles;
	}
	

	public AccountDto() {

	}

	
}
