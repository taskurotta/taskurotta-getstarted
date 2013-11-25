package worker;

public class Profile {

    public enum NotificationType {NONE, SMS, EMAIL};

    private NotificationType notificationType = NotificationType.NONE;

    private String email = null;
    private String phone = null;

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

}
