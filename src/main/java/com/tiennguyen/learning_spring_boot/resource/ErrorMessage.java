package com.tiennguyen.learning_spring_boot.resource;

public class ErrorMessage {
    String errorMessage;
    public ErrorMessage(String message){
        this.errorMessage = message;
    }
    public String getErrorMessage(){
        return this.errorMessage;
    }
    public void setErrorMessage(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
