package ru.taskurotta.example.worker.profile;


public class Profile {

    public enum DeliveryType {NONE, SMS, EMAIL};

    private DeliveryType deliveryType = DeliveryType.NONE;

    private String email = null;
    private String phone = null;

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
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


    @Override
    public String toString() {
        return "Profile{" +
                "deliveryType=" + deliveryType +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
