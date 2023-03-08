package userPack;

public class AcademicProgressModel {
    private String courseCode;
    private Double attendance;
    private Double quiz_1;
    private Double quiz_2;
    private Double mid_marks;
    private Double quiz_3;
    private Double quiz_4;
    private Double final_marks;
    private Double progress;
    private String grade;
    public AcademicProgressModel(String courseCode, Double attendance, Double quiz_1, Double quiz_2, Double mid_marks, Double quiz_3, Double quiz_4, Double final_marks, Double progress, String grade) {
        this.courseCode = courseCode;
        this.attendance = attendance;
        this.quiz_1 = quiz_1;
        this.quiz_2 = quiz_2;
        this.mid_marks = mid_marks;
        this.quiz_3 = quiz_3;
        this.quiz_4 = quiz_4;
        this.final_marks = final_marks;
        this.progress = progress;
        this.grade = grade;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Double getAttendance() {
        return attendance;
    }

    public void setAttendance(Double attendance) {
        this.attendance = attendance;
    }

    public Double getQuiz_1() {
        return quiz_1;
    }

    public void setQuiz_1(Double quiz_1) {
        this.quiz_1 = quiz_1;
    }

    public Double getQuiz_2() {
        return quiz_2;
    }

    public void setQuiz_2(Double quiz_2) {
        this.quiz_2 = quiz_2;
    }

    public Double getMid_marks() {
        return mid_marks;
    }

    public void setMid_marks(Double mid_marks) {
        this.mid_marks = mid_marks;
    }

    public Double getQuiz_3() {
        return quiz_3;
    }

    public void setQuiz_3(Double quiz_3) {
        this.quiz_3 = quiz_3;
    }

    public Double getQuiz_4() {
        return quiz_4;
    }

    public void setQuiz_4(Double quiz_4) {
        this.quiz_4 = quiz_4;
    }

    public Double getFinal_marks() {
        return final_marks;
    }

    public void setFinal_marks(Double final_marks) {
        this.final_marks = final_marks;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }






}
