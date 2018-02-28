package dingzhen.entity;

import java.io.Serializable;
public class Leave extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;    // 用户ID
	private String why;   // 用户名
	private String time;   // 密码
	private Integer studentId;    // 所属角色ID
	private String begintime;  // 用户描述
	private String endtime;         // 所属角色名称
	private String status;
	private String userName;
	private String statusname;


	@Override
	public String toString() {
		return "Leave [id=" + id + ", why=" + why
				+ ", time=" + time + ", studentId=" + studentId + ", begintime=" + begintime
				+ ", endtime=" + endtime + ", status=" + status + ", userName=" + userName + ", statusname=" + statusname + "]";
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getWhy() {
		return why;
	}


	public void setWhy(String why) {
		this.why = why;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	public Integer getStudentId() {
		return studentId;
	}


	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}


	public String getBegintime() {
		return begintime;
	}


	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}


	public String getEndtime() {
		return endtime;
	}


	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getStatusname() {
		return statusname;
	}


	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	
}
