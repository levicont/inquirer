package com.lvg.inquirer;

import java.util.HashMap;
import java.util.Map;

public interface InquirerConstants {
	int DEFAULT_ANSWERS_COUNT		= 4;
	String CONTEXT 					= "CONTEXT";
	String VALIDATION_MESSAGE 		= "VALIDATION_MESSAGE";
	String CURRENT_SESSION_ACCOUNT 	= "CURRENT_SESSION_ACCOUNT";
	String ACCOUNTS_LIST			= "ACCOUNTS_LIST";
	
	int ROLE_ADMIN 					= 1;
	int ROLE_ADVANCED_TUTOR 		= 2;
	int ROLE_TUTOR 					= 3;
	int ROLE_STUDENT 				= 4;
	
	String NAME_ROLE_ADMIN			="Administrator";
	String NAME_ROLE_ADVANCED_TUTOR	="Advanced tutor";
	String NAME_ROLE_TUTOR			="Tutor";
	String NAME_ROLE_STUDENT		="Student";
	String APPLICATION				="Inquirer";
	
	static final Map<Integer, String> NAMES_ROLES = new HashMap<Integer,String>(){{
		put(ROLE_ADMIN, NAME_ROLE_ADMIN);
		put(ROLE_ADVANCED_TUTOR, NAME_ROLE_ADVANCED_TUTOR);
		put(ROLE_TUTOR, NAME_ROLE_TUTOR);
		put(ROLE_STUDENT, NAME_ROLE_STUDENT);		
	}};
	
	

}
