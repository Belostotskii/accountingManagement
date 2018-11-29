package telran.security.accounting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import telran.security.accounting.management.IAccountingManagement;
import telran.security.dto.AccountDto;
import telran.security.dto.ResponseCode;
import static telran.security.dto.AccountingManagementConstants.*;

@RestController
public class AccountingManagementController {

	@Autowired
	IAccountingManagement accounts;
	
	@PostMapping(ACCOUNT)
	ResponseCode addAccount(@RequestBody AccountDto account) {
		return accounts.addAccount(account);
	}
	
	@DeleteMapping(ACCOUNT)
	ResponseCode deleteAccount(@RequestParam(USERNAME) String id) {
		return accounts.removeAccount(id);
	}
	
	@PostMapping(ROLE)
	ResponseCode addRole(@RequestBody AccountDto account) {
		return accounts.addRole(account.getUsername(), account.getRoles().toString());
	}
	
	@DeleteMapping(ROLE)
	ResponseCode removeRole(@RequestBody AccountDto account) {
		return accounts.removeRole(account.getUsername(), account.getRoles().toString());
	}
	
	@PostMapping(REVOKE_ACCOUNT)
	ResponseCode revokeAccount(@RequestBody AccountDto account) {
		return accounts.revokeAccount(account.getUsername());
	}
	
	@PostMapping(ACTIVATE_ACCOUNT)
	ResponseCode activeAccount(@RequestBody AccountDto account) {
		return accounts.activateAccount(account.getUsername());
	}
	
	@PostMapping(UPDATE_PASSWORD)
	ResponseCode updatePassword(@RequestBody AccountDto account) {
		return accounts.updatePassword(account.getUsername(), account.getPassword());
	}
	
}
