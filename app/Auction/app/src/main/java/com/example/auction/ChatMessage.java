package com.example.auction;

import java.util.Date;

public class ChatMessage {

	private String username;
	private String message;
	private String time;

	private boolean incomingMessage;
	
	public ChatMessage() {
		super();
	}
	public ChatMessage(String message) {
		super();
		this.message = message;
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
	public void setMessage(String message) {
		this.message = message;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public boolean isIncomingMessage() {
		return incomingMessage;
	}
	public void setIncomingMessage(boolean incomingMessage) {
		this.incomingMessage = incomingMessage;
	}


	public boolean isSystemMessage(){
		return getUsername()==null;
	}
}



