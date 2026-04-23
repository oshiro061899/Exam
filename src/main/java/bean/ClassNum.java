package bean;

import java.io.Serializable;

public class ClassNum implements Serializable {

	/**
	 * クラス番号:class_num
	 */
	private String classNum;

	/**
	 * クラス名:class_name
	 */
	private String className;

	/**
	 * 学校:School
	 */
	private School school;


	/**
	 * ゲッター・セッター
	 */

	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
