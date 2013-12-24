package com.lvg.inquirer.models;


public class Question extends AbstractModelBean {
		
	private static final long serialVersionUID = -1347125635319754606L;
	
	
	private Integer id;
	private Test test;
	private String text;
	
	protected Object getIdModel(){
		return this.id;
	}
	public Question(){
		super();
		
	}
	
	public Question(Integer id, Test test, String text){
		super();
		
		this.id= id;
		this.test = test;
		this.text = text;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
