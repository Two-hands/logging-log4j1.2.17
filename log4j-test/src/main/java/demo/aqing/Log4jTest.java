package demo.aqing;


import org.apache.log4j.Logger;

public class Log4jTest {

    private static final Logger logger = Logger.getLogger(Log4jTest.class);

    public static void main(String[] args) {
        logger.trace("log4j...debug");
        logger.debug("log4j...debug");
        logger.info("log4j...info");
        logger.warn("log4j...warn");
        logger.error("log4j...error");
        logger.fatal("log4j...fatal");
    }
}
