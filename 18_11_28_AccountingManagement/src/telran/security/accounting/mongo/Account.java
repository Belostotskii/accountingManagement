package telran.security.accounting.mongo;

import java.time.LocalDate;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="accounts")
public class Account implements Comparable<Account>{

	@Id
	String username;
	String password_hash;
	String[] last_password_hashes;
	LocalDate activationDate;
	boolean revoked;
	Set<String> roles;
	
	public void setPassword_hash(String password_hash) {
		this.password_hash = password_hash;
	}
	

	public void setActivationDate(LocalDate activation_date) {
		this.activationDate = activation_date;
	}


	public void setLast_password_hashes(String[] last_password_hashes) {
		this.last_password_hashes = last_password_hashes;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}

	public Account(String username, String password_hash, String[] last_password_hashes, LocalDate activation_date,
			boolean revoked, Set<String> roles) {
		super();
		this.username = username;
		this.password_hash = password_hash;
		this.last_password_hashes = last_password_hashes;
		this.activationDate = activation_date;
		this.revoked = revoked;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword_hash() {
		return password_hash;
	}

	public String[] getLast_password_hashes() {
		return last_password_hashes;
	}

	public LocalDate getActivation_date() {
		return activationDate;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public Set<String> getRoles() {
		return roles;
	}

	@Override
	public int compareTo(Account o) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	
	
}
