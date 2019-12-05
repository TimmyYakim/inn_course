package teacher16.inno20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainLogback {
    private static final Logger logger = LoggerFactory.getLogger(MainLogback.class.getName());

    public static void main(String[] args)  {
        logger.info("HELLO");
        logger.debug("DEBUG");
        logger.trace("DEBUG");
    }

}