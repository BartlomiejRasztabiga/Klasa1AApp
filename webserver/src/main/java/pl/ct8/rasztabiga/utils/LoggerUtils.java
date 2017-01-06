package pl.ct8.rasztabiga.utils;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtils {

    private static Logger logger;

    public static Logger getLogger() {
        if(logger == null) {
            logger = Logger.getLogger("App");
            setUpLogger(logger);
        }

        return logger;

    }

    public static void setUpLogger(Logger logger) {
        try {
            FileHandler fh = new FileHandler("LogFile.log", 0, 1, true);
            ConsoleHandler ch = new ConsoleHandler();
            logger.addHandler(fh);
            logger.addHandler(ch);
            ch.setEncoding("UTF8");
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            fh.setEncoding("UTF8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
