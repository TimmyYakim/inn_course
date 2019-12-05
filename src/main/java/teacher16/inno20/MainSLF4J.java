package teacher16.inno20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainSLF4J {

    private static final Logger logger = LoggerFactory.getLogger(MainSLF4J.class.getName());

    public static void main(String[] args)  {
        logger.error("Hello");
        logger.warn("Hello");
        logger.info("Hello");
        logger.debug("Hello");
        logger.trace("Hello");

    }

}