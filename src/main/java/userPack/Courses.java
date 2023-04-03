package userPack;
/** Class for course information
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */
public class Courses {


    private String code;
    private String title;
    private String dept;
    private String offered_dept;
    private Double credit;

    private String semester;
    public Courses(String code, String title, String dept, String offered_dept, Double credit,String semester) {
        this.code = code;
        this.title = title;
        this.dept = dept;
        this.offered_dept = offered_dept;
        this.credit = credit;
        this.semester=semester;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getOffered_dept() {
        return offered_dept;
    }

    public void setOffered_dept(String offered_dept) {
        this.offered_dept = offered_dept;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getSemester(){return semester;}

    public void setSemester(String semester){this.semester=semester;}



}
