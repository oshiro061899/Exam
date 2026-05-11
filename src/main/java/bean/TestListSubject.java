package bean;

import java.io.Serializable;
import java.util.HashMap; // 追加：Mapを使うために必要
import java.util.Map;     // 追加：Mapを使うために必要

public class TestListSubject implements Serializable {

    private int entYear;
    private String classNum;
    private String studentNo;
    private String studentName;
    
    // 成績情報を保持するマップ（回数, 点数）
    private Map<Integer, Integer> points = new HashMap<>();

    // --- メソッド ---

    /**
     * 指定した回数の点数を設定する
     */
    public void putPoint(int num, int point) {
        this.points.put(num, point);
    }

    /**
     * 指定した回数の点数を取得する
     */
    public Integer getPoint(int num) {
        return this.points.get(num);
    }

    // --- ゲッター・セッター ---

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

    public Map<Integer, Integer> getPoints() {
        return points;
    }
    public void setPoints(Map<Integer, Integer> points) {
        this.points = points;
    }
}