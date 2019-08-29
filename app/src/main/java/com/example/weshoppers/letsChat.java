package com.example.weshoppers;

public class letsChat {
    String chat;
    String user;
    String phone;
    public letsChat() {
    }

    public letsChat(String chat, String user) {
        this.chat = chat;
        this.user = user;
    }

    public letsChat(String chat, String user, String phone) {
        this.chat = chat;
        this.user = user;
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public String getChat() {
        return chat;
    }

    public String getUser() {
        return user;
    }


}
