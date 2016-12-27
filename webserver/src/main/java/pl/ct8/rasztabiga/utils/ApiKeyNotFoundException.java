package pl.ct8.rasztabiga.utils;

public class ApiKeyNotFoundException extends Exception {
        public ApiKeyNotFoundException() {
            super();
        }

        public ApiKeyNotFoundException(String message) {
            super(message);
        }

        public ApiKeyNotFoundException(String message, Throwable cause) {
            super(message, cause);
        }

        public ApiKeyNotFoundException(Throwable cause) {
            super(cause);
        }
    }