package com.example.helpyar;

public class chatItem {

    String senderName;
    String chatMessage;
    String message_date;

    public chatItem(String senderName, String chatMessage, String message_date) {
        this.senderName = senderName;
        this.chatMessage = chatMessage;
        this.message_date = message_date;
    }


    public String getSenderName() {
        return senderName;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public String getMessage_date() {
        return message_date;
    }

}
