package dingzhen.entity;

import java.io.Serializable;
public class Class extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String class_name;
	private Integer teacher_code;
	private String userName;
	@Override
	public String toString() {
		return "Class [id=" + id + ", class_name=" + class_name + ", userName=" + userName + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClass_name() {
		return class_name;
	}
	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}
	public Integer getTeacher_code() {
		return teacher_code;
	}
	public void setTeacher_code(Integer teacher_code) {
		this.teacher_code = teacher_code;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
