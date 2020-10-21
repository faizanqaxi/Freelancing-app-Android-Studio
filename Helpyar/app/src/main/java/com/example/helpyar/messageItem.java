package com.example.helpyar;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class messageItem implements Parcelable {


    String message_id;
    String chatUserId;
    String chatUser;
    String text;
    String message_date;


    public messageItem(String message_id, String chatUserId, String chatUser, String text, String message_date) {

        this.message_id = message_id;
        this.chatUserId = chatUserId;
        this.chatUser = chatUser;
        this.text = text;
        this.message_date = message_date;


    }


    protected messageItem(Parcel in) {

        message_id = in.readString();
        chatUserId = in.readString();
        chatUser = in.readString();
        text = in.readString();
        message_date = in.readString();

    }

    public static final Creator<messageItem> CREATOR = new Creator<messageItem>() {
        @Override
        public messageItem createFromParcel(Parcel in) {
            return new messageItem(in);
        }

        @Override
        public messageItem[] newArray(int size) {
            return new messageItem[size];
        }
    };

    public String getmessageId() {
        return message_id;
    }

    public String getChatUserId() {
        return chatUserId;
    }

    public String getChatUser() {
        return chatUser;
    }

    public String getText() {
        return text;
    }

    public String getMessage_date() {
        return message_date;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(message_id);
        parcel.writeString(chatUserId);
        parcel.writeString(chatUser);
        parcel.writeString(text);
        parcel.writeString(message_date);
    }
}
