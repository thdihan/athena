package userPack;

import java.util.Date;

/** Class for teacher information
 * @author Unknown
 * @version 1.0
 * @since March,2023
 */

public class Admin {
    private String id;
    private String name;
    private String email;
    private Date dob;
    private String contact;
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


    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Admin(String id, String name, String email, Date dob, String contact) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.contact = contact;
    }


}
