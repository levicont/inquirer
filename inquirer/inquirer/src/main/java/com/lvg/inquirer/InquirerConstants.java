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
	
	// Resource bundle session attribute name
	String RESOURCE_BUNDLE = "RBUNDLE";
	String ERR_USNAME_BLANK					= "err_usname_blank";
	String ERR_PASSWORD_BLANK				= "err_password_blank";
	String ERR_OLD_PASSWORD_BLANK			= "err_old_password_blank";
	String ERR_CONF_PASSWORD_BLANK			= "err_conf_password_blank";
	String ERR_EMAIL_BLANK					= "err_email_blank";
	String ERR_INVALID_ROLE					= "err_invalid_role";
	String ERR_ROLE_NOT_CHECKED				= "err_role_not_checked";
	String ERR_UNSUPPORTED_ROLE				= "err_unsupported_role";
	String ERR_INVALID_PASSWORD				= "err_invalid_password";
	String ERR_INVALID_USER					= "err_invalid_user";
	String ERR_USNAME_EMAIL_EXISTS			= "err_usname_email_exists";
	String ERR_USER_EXISTS					= "err_user_exists";
	String ERR_NO_RIGHTS					= "err_no_rights";
	String ERR_PROFILE_NO_CHANGE			= "err_profile_no_change";
	String ERR_PROFILE_OLD_PASSWORD_BLANK	= "err_profile_old_password_blank";
	String ERR_PROFILE_PASSWORDS_NOT_EQUAL	= "err_profile_passwords_not_equal";
	String ERR_PROFILE_BAD_OLD_PASSWORD		= "err_profile_bad_old_password";
	String ERR_EMPTY_TITLE					= "err_empty_title";
	String ERR_TEST_TIME_LIMIT				= "err_test_time_limit";
	String ERR_QUESTION_NO_ANSWERS			= "err_question_no_answers";
	String ERR_QUESTION_NO_CORRECT_ANSWERS	= "err_question_no_correct_answers";
	String ERR_QUESTION_BAD_CHECKSUM		= "err_question_bad_checksum";
	String ERR_SEND_EMAIL					= "err_send_email";
	String ERR_PAGE_NOT_FOUND 				= "err_page_not_found";
	String ERR_PAGE_INCORRECT				= "err_page_incorrect";
	String ERR_DELETE_USER					= "err_delete_user";
	String ERR_UNRECONIZED_TEST				= "err_unreconized_test";
	String ERR_NOT_SELECTED_TEST			= "err_not_selected_test";
	String ERR_TIME_STAMP_KEY				= "err_time_stamp_key";
	String ERR_NO_MORE_QUESTIONS			= "err_no_more_questions";
	
	
	@SuppressWarnings("serial")
	static final Map<Integer, String> NAMES_ROLES = new HashMap<Integer,String>(){{
		put(ROLE_ADMIN, NAME_ROLE_ADMIN);
		put(ROLE_ADVANCED_TUTOR, NAME_ROLE_ADVANCED_TUTOR);
		put(ROLE_TUTOR, NAME_ROLE_TUTOR);
		put(ROLE_STUDENT, NAME_ROLE_STUDENT);		
	}};
	
	

}
