package com.lvg.inquirer.models;


public class Role extends AbstractModelBean {	

	private static final long serialVersionUID = 8377219709761181582L;
	private Integer id;
	private String name;
	
	public Role(){
		super();
	}
	
	public Role(Integer id, String name){
		super();
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	protected Object getIdModel(){
		return getId();
	}

}
