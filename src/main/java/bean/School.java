package bean;

/**
 * 学校情報を表す Bean クラス
 */
public class School {

	/** 学校コード */
    private String schoolCd;

    /** 学校名 */
    private String schoolName;

    // --- getter / setter ---

	public String getSchoolCd() {
		return schoolCd;
	}

	public void setSchoolCd(String schoolCd) {
		this.schoolCd = schoolCd;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

 }