package com.naoesqueca.naoesqueca.api.exeption;

public class ResourceNotFoundException {

    public static class resourceNotFoundException extends RuntimeException {
        public resourceNotFoundException(String message) {
            super(message);
        }
    }
}
