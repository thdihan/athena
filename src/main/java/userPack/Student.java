package userPack;

import java.util.Date;
/** Class for student information
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */
public class Student {


    private String id;
    private String name;
    private String email;
    private String dept;
    private String semester;
    private String dob;
    private String contact;

    public Student(String id, String name, String email, String dept, String semester, String dob, String contact) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dept = dept;
        this.semester = semester;
        this.dob = dob;
        this.contact = contact;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


}
