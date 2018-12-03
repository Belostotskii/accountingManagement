package telran.security.dto;

import java.util.ArrayList;
import java.util.List;

public interface AccountRoles {

	String ADMIN = "ADMIN";
	String ADMIN_CLEANER = "ADMIN_CLEANER";
	String ADMIN_MANAGER = "ADMIN_MANAGER";
	String ADMIN_UPDATER = "ADMIN_UPDATER";
	
	public default List<String> getRolesList(){
		ArrayList<String> list = new ArrayList<>();
		list.add(ADMIN);
		list.add(ADMIN_CLEANER);
		list.add(ADMIN_MANAGER);
		list.add(ADMIN_UPDATER);
		return list;
	}
}
