package com.lvg.inquirer.models;

import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;

public class Account extends AbstractModelBean implements HttpSessionBindingListener {
	
	

	

	private static final long serialVersionUID = 3787958159884864403L;
	private static final Logger LOGGER = Logger.getLogger(Account.class);
	private String username;
	private String password;
	private List<Role> role;
	private Integer id;
	private String email;
	private Integer enabled;
	
	public Account(){
		super();
		this.enabled = 1;
	}
	public Account(String username, String password){
		super();
		this.username = username;
		this.password = password;
		this.enabled = 1;
	}
	public Account(String username, String password, List<Role> role){
		super();
		this.username = username;
		this.password = password;
		this.role = role;
		this.enabled = 1;
	}
	public void setId(int id){
		this.id = id;
	}
	
	public Integer getId(){
		return this.id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Role> getRole() {
		return this.role;
	}
	public void setRole(List<Role> role) {
		this.role = role;
	}
	
	@Override
	protected Object getIdModel(){
		return getUsername();
	}
	
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	public Boolean isEnabled(){
		return this.enabled!=0;
	}
	
	public void valueBound(HttpSessionBindingEvent event) {
		if(LOGGER.isDebugEnabled()){
			StringBuilder message = new StringBuilder("Accounts has been added with session id='");
			message.append(event.getSession().getId());
			message.append("'");
			LOGGER.debug(message);
		}
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		if(LOGGER.isDebugEnabled()){
			StringBuilder message = new StringBuilder("Accounts has been removed from session id='");
			message.append(event.getSession().getId());
			message.append("'");
			LOGGER.debug(message);
		}
	}
	
	public Boolean isAdmin(){
		for(Role r : role){
			if(r.getId().intValue()==InquirerConstants.ROLE_ADMIN)
				return true;
		}
		return false;
	}
	
}
