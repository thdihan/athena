package userPack;

public class Notification {
    private String notificationId;
    private String postId;
    private String notificationType;
    private String notifierEmail;


    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotifierEmail() {
        return notifierEmail;
    }

    public void setNotifierEmail(String notifierEmail) {
        this.notifierEmail = notifierEmail;
    }
}

