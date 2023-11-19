package br.com.shared.exception;

public class UserAndCharacterNotFoundException extends RuntimeException {

    public UserAndCharacterNotFoundException(String message) {
        super(message);
    }
}
