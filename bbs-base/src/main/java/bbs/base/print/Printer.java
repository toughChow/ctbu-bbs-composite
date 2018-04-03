package bbs.base.print;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Printer {
    private static Logger logger = LoggerFactory.getLogger("SmartLamp Background Management System");

    public static void info(String var1) {
        logger.info(var1);
    }

    public static void info(String var1, Object var2) {
        logger.info(var1, var2);
    }

    public static void info(String var1, Object... var2) {
        logger.info(var1, var2);
    }

    public static void warn(String var1) {
        logger.warn(var1);
    }

    public static void warn(String var1, Object var2) {
        logger.warn(var1, var2);
    }

    public static void warn(String var1, Object... var2) {
        logger.warn(var1, var2);
    }

    public static void error(String var1) {
        logger.error(var1);
    }

    public static void error(String var1, Object var2) {
        logger.error(var1, var2);
    }

    public static void error(String var1, Object... var2) {
        logger.error(var1, var2);
    }

    public static void error(String var1, Throwable var2) {
        logger.error(var1, var2);
    }
}
