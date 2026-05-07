package bean;

public class Test {

    private Student student;
    private String classNum;
    private Subject cd;
    private School schoolCd;
    private int no;
    private int point;


 	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
	
	public Subject getCd() {
		return cd;
	}

	public void setCd(Subject cd) {
		this.cd = cd;
	}

    public School getSchool() {
		return schoolCd;
	}

	public void setSchoolCd(School schoolCd) {
		this.schoolCd = schoolCd;
	}

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}