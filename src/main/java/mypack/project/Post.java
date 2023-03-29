package mypack.project;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private String postid;
    private String courseCode;
    private String post_giver_email;
    private String post_giver_type;
    private String post_text;
    private String post_type;
    private String attachment_link;
    private String deadline;

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getPost_giver_email() {
        return post_giver_email;
    }


    public void setPost_giver_email(String post_giver_email) {
        this.post_giver_email = post_giver_email;
    }

    public String getPost_giver_type() {
        return post_giver_type;
    }

    public void setPost_giver_type(String post_giver_type) {
        this.post_giver_type = post_giver_type;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }

    public String getAttachment_link() {
        return attachment_link;
    }

    public void setAttachment_link(String attachment_link) {
        this.attachment_link = attachment_link;
    }

    public Timestamp getDeadline() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = dateFormat.format(new Date()); // get the current date and time
//        String formattedDate = dateFormat.format(date); // format the date and time as a string
//        Timestamp timestamp = Timestamp.valueOf(formattedDate); // parse the formatted string into a Timestamp object
//        return timestamp;
        return null;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }


}

