package bean;

import java.io.Serializable;

public class Teacher extends User implements Serializable {
	/**
	 * 教員ID:String
	 */
	private String teacherId;

	/**
	 * パスワード:String
	 */
	private String password;

	/**
	 * 教員名:String
	 */
	private String teacherName;

	/**
	 * 所属校:School
	 */
	private School school;

	/**
	 * ゲッター・セッター
	 */
	
	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}
}
