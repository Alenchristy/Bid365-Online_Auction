package com.example.auction;

public class ChatTime {

    private String username;
    private String message;
    private String time;

    //	private Date date;
    private boolean incomingMessage;


    public ChatTime(String time) {
        super();
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String message) {
        this.time = time;
    }

    public void setIncomingMessage(boolean incomingMessage) {
        this.incomingMessage = incomingMessage;
    }

    public boolean isSystemMessage() {
        return getUsername() == null;
    }
}
