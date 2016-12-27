package pl.ct8.rasztabiga.utils;

public class EmailSender implements Runnable {

        private String address;
        private String message;
        
        public EmailSender(String address, String message) {
            this.address = address;
            this.message = message;
        }
        
        @Override
        public void run() {
            EmailUtils.sendEmail(address, message);
        }
    }