package bean;

import java.io.Serializable;

public class Test implements Serializable {
    private Student student; // 学生Bean
    private Subject subject; // 科目Bean
    private School school;   // 学校Bean
    private int no;          // 回数
    private int point;       // 得点

    // ゲッターとセッター
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Subject getSubject() { return subject; }
    public void setSubject(Subject subject) { this.subject = subject; }

    public School getSchool() { return school; }
    public void setSchool(School school) { this.school = school; }

    public int getNo() { return no; }
    public void setNo(int no) { this.no = no; }

    public int getPoint() { return point; }
    public void setPoint(int point) { this.point = point; }
}