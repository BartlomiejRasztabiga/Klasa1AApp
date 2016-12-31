package pl.ct8.rasztabiga.utils;

public class NoPermissionsException extends Exception {
        public NoPermissionsException() {
            super();
        }

        public NoPermissionsException(String message) {
            super(message);
        }

        public NoPermissionsException(String message, Throwable cause) {
            super(message, cause);
        }

        public NoPermissionsException(Throwable cause) {
            super(cause);
        }
    }