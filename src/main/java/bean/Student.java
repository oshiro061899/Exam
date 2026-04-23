package bean;

/**
 * 学生情報を表す Bean クラス
 */
public class Student {

    /** 学籍番号 */
    private String studentNo;

    /** 氏名 */
    private String studentName;

    /** 入学年度 */
    private int entYear;

    /** クラス番号 */
    private String classNum;

    /** 在籍状況 */
    private boolean isAttend;

    /** 所属学校 */
    private School school;

    // --- getter / setter ---

 	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

    public int getEntYear() {
		return entYear;
	}

	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public boolean isAttend() {
        return isAttend;
    }

    public void setAttend(boolean isAttend) {
        this.isAttend = isAttend;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }
}