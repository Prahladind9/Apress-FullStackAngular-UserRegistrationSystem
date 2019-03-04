package com.apress.rao.Exception;

import java.awt.TrayIcon.MessageType;

public class FieldValidationError {
	private String filed;
	private String message;
	
	private MessageType messageType;

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}
	
	
	
}
