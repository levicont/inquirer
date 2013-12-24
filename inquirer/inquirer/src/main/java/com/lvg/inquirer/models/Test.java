package com.lvg.inquirer.models;


public class Test extends AbstractModelBean {
	
	
	private static final long serialVersionUID = -1115999882375048722L;
	
	
	private Integer id;
	private Account author = null;
	private String title;
	private String description;
	private Integer timeLimit = 2;
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Account getAuthor() {
		return author;
	}
	public void setAuthor(Account author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	@Override
	protected Object getIdModel() {
		
		return this.getId();
	}
	
}
