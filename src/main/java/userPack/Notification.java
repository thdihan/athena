package userPack;

public class Notification {
    private String notificationId;
    private String postId;
    private String notificationType;
    private String notifierEmail;

    private  String notifyToEmail;
    private  String notificationText;

    private  String notifacationDeadline;


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

    public String getNotifyToEmail() {
        return notifyToEmail;
    }

    public void setNotifyToEmail(String notifyToEmail) {
        this.notifyToEmail = notifyToEmail;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public  void setNotifacationDeadline(String notifacationDeadline){
        this.notifacationDeadline = notifacationDeadline;
    }
    public String getNotifacationDeadline() {
        return notifacationDeadline;
    }
}

