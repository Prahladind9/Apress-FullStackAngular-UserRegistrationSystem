package com.apress.rao.Exception;

import com.apress.rao.dto.UsersDTO;

public class CustomErrorType extends UsersDTO{
	
	private String errorMessage;
	
	public CustomErrorType(final String errorMessage){
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String getErrorMessage(){
		return errorMessage;
	}
}
