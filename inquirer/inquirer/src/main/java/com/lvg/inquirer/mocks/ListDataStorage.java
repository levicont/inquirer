package com.lvg.inquirer.mocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Role;

public class ListDataStorage implements InquirerConstants {
	
	static final Role ADMIN_ROLE 	= 		new Role(ROLE_ADMIN, "Administrator");
	static final Role STUDENT_ROLE 	=	 	new Role(ROLE_STUDENT,"Student");
	static final Role TUTOR_ROLE 	= 		new Role(ROLE_TUTOR,"Tutor");
	static final Role ADVANCED_TUTOR_ROLE = new Role(ROLE_ADVANCED_TUTOR,"Advanced tutor");
	
	public static final List<Role> ALL_ROLES = Arrays.asList(new Role[]{
			ADMIN_ROLE,
			ADVANCED_TUTOR_ROLE,
			TUTOR_ROLE,
			STUDENT_ROLE
	});
	
	@SuppressWarnings("serial")
	public static final Map<Integer,Role> ALL_ROLES_MAP = new HashMap<Integer,Role>(){{
		put(ROLE_ADMIN,ADMIN_ROLE);
		put(ROLE_ADVANCED_TUTOR, ADVANCED_TUTOR_ROLE);
		put (ROLE_TUTOR, TUTOR_ROLE);
		put(ROLE_STUDENT, STUDENT_ROLE);}};
	
	static final Account STUDENT = new Account("student","1",Arrays.asList(new Role[]{STUDENT_ROLE}));
	static final Account ADMIN = new Account("admin","1",ALL_ROLES);
	static final Account TUTOR = new Account("tutor","1",Arrays.asList(new Role[]{TUTOR_ROLE,STUDENT_ROLE}));
	static final Account ADVANCED_TUTOR = new Account("advanced_tutor","1",Arrays.asList(new Role[]{
			ADVANCED_TUTOR_ROLE,
			TUTOR_ROLE,
			STUDENT_ROLE
	}));
	static final List<Account> ALL_ACCOUNTS = Arrays.asList(new Account[]{
			STUDENT,
			ADMIN,
			TUTOR,
			ADVANCED_TUTOR
	});

}
