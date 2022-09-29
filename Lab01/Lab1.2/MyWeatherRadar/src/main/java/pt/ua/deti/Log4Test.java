package pt.ua.deti;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Log4Test {
    private static Logger logger = LogManager.getLogger(WeatherStarter.class);
    public static void main(String[] args) {
        logger.debug("Debug log message");
        logger.info("Info log message");
        logger.error("Error log message");
    }
}
