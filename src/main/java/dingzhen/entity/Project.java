package dingzhen.entity;

import java.io.Serializable;
public class Project extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;    // 用户ID
	private Integer studentId;
	private Integer assignId;
	private String selfscore;   // 
	private String teacherscore;  // 
	private String userName;         // 
	private String question;     //
	private String questionstandard;
	private String selflevel;
	private String teacherlevel;
	
	
	@Override
	public String toString() {
		return "Project [id=" + id + ", studentId=" + studentId
				+ ", assignId=" + assignId + ", selfscore=" + selfscore
				+ ", teacherscore=" + teacherscore + ", selflevel=" + selflevel + ", teacherlevel=" + teacherlevel + ", userName=" + userName + ", question=" + question + ", questionstandard=" + questionstandard + "]";
	}

	public String getSelflevel() {
		return selflevel;
	}

	public void setSelflevel(String selflevel) {
		this.selflevel = selflevel;
	}

	public String getTeacherlevel() {
		return teacherlevel;
	}

	public void setTeacherlevel(String teacherlevel) {
		this.teacherlevel = teacherlevel;
	}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getStudentId() {
		return studentId;
	}


	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}


	public Integer getAssignId() {
		return assignId;
	}


	public void setAssignId(Integer assignId) {
		this.assignId = assignId;
	}


	public String getSelfscore() {
		return selfscore;
	}


	public void setSelfscore(String selfscore) {
		this.selfscore = selfscore;
	}


	public String getTeacherscore() {
		return teacherscore;
	}


	public void setTeacherscore(String teacherscore) {
		this.teacherscore = teacherscore;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public String getQuestionstandard() {
		return questionstandard;
	}


	public void setQuestionstandard(String questionstandard) {
		this.questionstandard = questionstandard;
	}


}
