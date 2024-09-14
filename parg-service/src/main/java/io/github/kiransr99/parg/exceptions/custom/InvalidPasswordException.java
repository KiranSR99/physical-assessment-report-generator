package io.github.kiransr99.parg.exceptions.custom;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String message){
        super(message);
    }
}
