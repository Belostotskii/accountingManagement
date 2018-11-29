package telran.security.accounting.management;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import telran.security.accounting.mongo.Account;
import telran.security.dto.AccountDto;
import telran.security.repo.AccountManagementMongoRepository;
import telran.security.dto.ResponseCode;

import static telran.security.util.Flag.*;


@Service
public class AccountingManagementMongo implements IAccountingManagement {

	private static final int LENGTH = 8;

	@Autowired
	AccountManagementMongoRepository accountsRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public ResponseCode addAccount(AccountDto account) {
		if(accountsRepository.existsById(account.getUsername()))
			return ResponseCode.USERNAME_EXIST;
		if(!isPasswordCorrect(account.getPassword()))
			return ResponseCode.PASSWORD_INCORRECT;
		Account res = new Account(account.getUsername(), getHashPassword(account.getPassword()), new String[2], 
				LocalDate.now(), false, account.getRoles());
		accountsRepository.save(res);
		return ResponseCode.OK;
	}

	private boolean isPasswordCorrect(String password) {
		if(password.length() < LENGTH)
			return false;
		int flagsNumber = 0;
		String str = "!@$%^&*()_+-#~";
		char[] chars = password.toCharArray();
		for(char c: chars) {
			if(Character.isUpperCase(c))
				flagsNumber |= CAPITAL_LATER.getBit();
			if(Character.isLowerCase(c))
				flagsNumber |= LOWER_CASE.getBit();
			if(Character.isDigit(c))
				flagsNumber |= DIGIT.getBit();
			if(str.contains(Character.toString(c)))
				flagsNumber |= SPEC_SYMBOL.getBit();
		}
		return flagsNumber == 15? true: false;
	}

	private String getHashPassword(String password) {
		String res = passwordEncoder.encode(password);
		return res;
	}

	@Override
	public ResponseCode removeAccount(String username) {
		if(!accountsRepository.existsById(username))
			return ResponseCode.NO_USERNAME;
		accountsRepository.deleteById(username);
		return ResponseCode.OK;
	}

	@Override
	public ResponseCode addRole(String username, String role) {
		if(!accountsRepository.existsById(username))
			return ResponseCode.NO_USERNAME;
		Account account = accountsRepository.findById(username).orElse(null);
		if(account != null && account.getRoles().add(role)) {
			accountsRepository.save(account);
			return ResponseCode.OK;
		}else
			return ResponseCode.ROLE_EXIST;

	}

	@Override
	public ResponseCode removeRole(String username, String role) {
		if(!accountsRepository.existsById(username))
			return ResponseCode.NO_USERNAME;
		Account account = accountsRepository.findById(username).orElse(null);
		if(account != null && account.getRoles().remove(role)) {
			accountsRepository.save(account);
			return ResponseCode.OK;
		}else
			return ResponseCode.ROLE_NO_EXIST;
	}

	@Override
	public ResponseCode updatePassword(String username, String password) {
		if(!accountsRepository.existsById(username))
			return ResponseCode.NO_USERNAME;
		if(!isPasswordCorrect(password))
			return ResponseCode.PASSWORD_INCORRECT;
		Account account = accountsRepository.findById(username).get();
		if(passwordEncoder.matches(password, account.getPassword_hash()))
			return ResponseCode.PASSWORD_SHOULD_NOT_REPEAT_PREVIOUS_3_PASSWORDS;
		for(String hash: account.getLast_password_hashes()) {
			if(passwordEncoder.matches(password, hash))
					return ResponseCode.PASSWORD_SHOULD_NOT_REPEAT_PREVIOUS_3_PASSWORDS;
		}
		String[] newHashes = updateLastPasswordHashes(account.getLast_password_hashes(), account.getPassword_hash());
		account.setLast_password_hashes(newHashes);
		account.setPassword_hash(getHashPassword(password));
		accountsRepository.save(account);
		return ResponseCode.OK;
	}

	private String[] updateLastPasswordHashes(String[] previous, String last) {
		String[] res = {previous[1], last};
		return res;
	}

	@Override
	public ResponseCode revokeAccount(String username) {
		if(!accountsRepository.existsById(username))
			return ResponseCode.NO_USERNAME;
		Account account = accountsRepository.findById(username).get();
		if(account != null && account.isRevoked() == false) {
			account.setRevoked(true);
			accountsRepository.save(account);
			return ResponseCode.OK;
		}else
			return ResponseCode.ACCOUNT_NO_ACTIVE;
	}

	@Override
	public ResponseCode activateAccount(String username) {
		if(!accountsRepository.existsById(username))
			return ResponseCode.NO_USERNAME;
		Account account = accountsRepository.findById(username).get();
		if(account != null && account.isRevoked() == true) {
			account.setRevoked(false);
			account.setActivationDate(LocalDate.now());
			accountsRepository.save(account);
			return ResponseCode.OK;
		}else
			return ResponseCode.ACCOUNT_NOT_REVOKE;
	}

}
