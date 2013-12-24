package com.lvg.inquirer.models;

import java.sql.Timestamp;

public class TestResult extends AbstractModelBean {
	
	private static final long serialVersionUID = -6734640353998729574L;
	
	private Integer id;
	private Test test;
	private Account account;
	private Integer correctAnswers;
	private Integer failAnswers;
	private Timestamp date;
	
	public TestResult(){
		long currDateTime = System.currentTimeMillis();
		this.correctAnswers = 0;
		this.failAnswers = 0;
		
		this.date = new Timestamp(currDateTime);
				
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Test getTest() {
		return test;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public Integer getCorrectAnswers() {
		return correctAnswers;
	}
	public void setCorrectAnswers(Integer correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
	public Integer getFailAnswers() {
		return failAnswers;
	}
	public void setFailAnswers(Integer failAnswers) {
		this.failAnswers = failAnswers;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	
	@Override
	protected Object getIdModel() {
		
		return this.getId();
	}

}
