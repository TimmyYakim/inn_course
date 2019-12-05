package teacher16.inno20;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.MapMessage;

public class MainLog4J {
    private static final Logger logger = LogManager.getLogger(MainLog4J.class);

    public static void main(String[] args)  {
        MapMessage mapMessage = new MapMessage();
        mapMessage.put("msg1", "Сообщение 1");
        mapMessage.put("msg2", "Сообщение 2");

        Marker marker = MarkerManager.getMarker("consoleonly");

        logger.info(new Throwable("WARNING"));
        logger.throwing(Level.INFO, new Throwable("WARNING"));
        logger.info(marker, mapMessage);
        logger.debug("DEBUG");
        logger.trace("DEBUG");
    }

}