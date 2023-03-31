package userPack;

public class Submission {
    private String submissionId;
    private String postId;
    private String submitterEmail;
    private byte[] submissionFile;
    private String submissionTime;


    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getSubmitterEmail() {
        return submitterEmail;
    }

    public void setSubmitterEmail(String submitterEmail) {
        this.submitterEmail = submitterEmail;
    }

    public byte[] getSubmissionFile() {
        return submissionFile;
    }

    public void setSubmissionFile(byte[] submissionFile) {
        this.submissionFile = submissionFile;
    }

    public String getSubmissionTime() {
        return submissionTime;
    }

    public void setSubmissionTime(String submissionTime) {
        this.submissionTime = submissionTime;
    }
}
