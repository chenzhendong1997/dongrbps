package dingzhen.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Attence extends BaseEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;    // 用户ID
	private String one_check;
	private String two_check;
	private String three_check;
	private String four_check;
	private String five_check;
	private String six_check;
	private String score;
	private String time;
	private String attence_state;
	private String userName;
	private List<Attence> list = new ArrayList<>();


    public List<Attence> getList() {
        return list;
    }

    public void setList(List<Attence> list) {
        this.list = list;
    }
	public String getAttence_state() {
		return attence_state;
	}
	public void setAttence_state(String attence_state) {
		this.attence_state = attence_state;
	}
	public String getAttence_name() {
		return attence_name;
	}
	public void setAttence_name(String attence_name) {
		this.attence_name = attence_name;
	}
	private String attence_name;
	@Override
	public String toString() {
		return "Attence [userId=" + userId + ", one_check=" + one_check
				+ ", two_check=" + two_check + ", three_check=" + three_check + ", four_check=" + four_check
				+ ", five_check=" + five_check + ", userName=" + userName + ", six_check=" + six_check + ", score=" + score + ", attence_state=" + attence_state + ", time=" + time + ", attence_name=" + attence_name + "]";
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOne_check() {
		return one_check;
	}
	public void setOne_check(String one_check) {
		this.one_check = one_check;
	}
	public String getTwo_check() {
		return two_check;
	}
	public void setTwo_check(String two_check) {
		this.two_check = two_check;
	}
	public String getThree_check() {
		return three_check;
	}
	public void setThree_check(String three_check) {
		this.three_check = three_check;
	}
	public String getFour_check() {
		return four_check;
	}
	public void setFour_check(String four_check) {
		this.four_check = four_check;
	}
	public String getFive_check() {
		return five_check;
	}
	public void setFive_check(String five_check) {
		this.five_check = five_check;
	}
	public String getSix_check() {
		return six_check;
	}
	public void setSix_check(String six_check) {
		this.six_check = six_check;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
