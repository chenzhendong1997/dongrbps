package dingzhen.entity;

import java.io.Serializable;
public class Woke extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;    // 用户ID
	private Integer studentId;   // 
	private Integer assignId;   // 
	private Integer teacherId;
	private String userName;    // 
	private String question;  // 
	private String questionstandard;         // 
	private String selfscore;		//
	private String selflevel;     //
	private String teacherscore;
	private String teacherlevel;
	private String type;
	

	@Override
	public String toString() {
		return "Woke [id=" + id + ", studentId=" + studentId
				+ ", assignId=" + assignId + ", userName=" + userName + ", question=" + question
				+ ", questionstandard=" + questionstandard + ", selfscore=" + selfscore + ", selflevel=" + selflevel + ", teacherscore=" + teacherscore + ", teacherlevel=" + teacherlevel + "]";
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

	public String getSelfscore() {
		return selfscore;
	}

	public void setSelfscore(String selfscore) {
		this.selfscore = selfscore;
	}

	public String getSelflevel() {
		return selflevel;
	}

	public void setSelflevel(String selflevel) {
		this.selflevel = selflevel;
	}

	public String getTeacherscore() {
		return teacherscore;
	}

	public void setTeacherscore(String teacherscore) {
		this.teacherscore = teacherscore;
	}

	public String getTeacherlevel() {
		return teacherlevel;
	}

	public void setTeacherlevel(String teacherlevel) {
		this.teacherlevel = teacherlevel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	
}
