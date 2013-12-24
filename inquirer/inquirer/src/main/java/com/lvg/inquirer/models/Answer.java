package com.lvg.inquirer.models;


public class Answer extends AbstractModelBean {
	
	
	private static final long serialVersionUID = 5800435823785064615L;
	
	
	private Integer id;
	private Question question;
	private String text;
	private Integer isCorrect = 0;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(Integer isCorrect) {
		this.isCorrect = isCorrect;
	}
	public Boolean isCorrect(){
		return isCorrect!=0;
	}
	
	@Override
	protected Object getIdModel() {		
		return this.getId();
	}

}
