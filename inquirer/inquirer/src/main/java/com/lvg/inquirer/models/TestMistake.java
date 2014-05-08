package com.lvg.inquirer.models;

public class TestMistake extends AbstractModelBean {

	
	private static final long serialVersionUID = -8830053154483944932L;

	private Integer id;
	private TestResult testResult;
	private Question question;
	private String failAnswerText;
	private String correctAnswerText;
	
	public TestMistake(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TestResult getTestResult() {
		return testResult;
	}

	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}
	

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getFailAnswerText() {
		return failAnswerText;
	}

	public void setFailAnswerText(String failAnswerText) {
		this.failAnswerText = failAnswerText;
	}

	public String getCorrectAnswerText() {
		return correctAnswerText;
	}

	public void setCorrectAnswerText(String correctAnswerText) {
		this.correctAnswerText = correctAnswerText;
	}
	
	@Override
	protected Object getIdModel(){
		return id;
	}
}
