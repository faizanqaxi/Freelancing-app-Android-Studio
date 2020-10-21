package com.example.helpyar;

public class notificationItem {

    String notificationId;
    String notificationText;
    String notification_date;
    String notificationByUserId;
    String notificationByUserName;


    public notificationItem(String notificationId, String notificationText, String notification_date, String notificationByUserId, String notificationByUserName) {
        this.notificationId = notificationId;
        this.notificationText = notificationText;
        this.notification_date = notification_date;
        this.notificationByUserId = notificationByUserId;
        this.notificationByUserName = notificationByUserName;

    }


    public String getNotificationId() {
        return notificationId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public String getNotification_date() {
        return notification_date;
    }

    public String getNotificationByUserId() {
        return notificationByUserId;
    }

    public String getNotificationByUserName() {
        return notificationByUserName;
    }



}
