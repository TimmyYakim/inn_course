package teacher17;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teacher17.connectionmanager.ConnectionManager;
import teacher17.connectionmanager.ConnectionManagerJdbcImpl;
import teacher17.dao.MobileDao;
import teacher17.dao.MobileDaoJdbcImpl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *   Source: github.com/avsharapov/inno20Testing
 */
class MainTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainTest.class);

    private Main main;
    private MobileDao mobileDao;
    private ConnectionManager connectionManager;

    @BeforeEach
    void setUp() {
        LOGGER.trace("BeforeEach in MainTest");
        main = new Main();
        connectionManager = ConnectionManagerJdbcImpl.getInstance();
        mobileDao = new MobileDaoJdbcImpl(connectionManager);
    }

    @BeforeAll
    static void tearDownAll() {
        LOGGER.trace("BeforeAll in MainTest");
    }

    @AfterAll
    static void setUpAll() {
        LOGGER.trace("AfterAll in MainTest");
    }

    @AfterEach
    void tearDown() {
        LOGGER.trace("AfterEach in MainTest");
    }

    @Test
    void main() {
        assertDoesNotThrow(() -> main.method1(mobileDao));
    }

    @Test
    void mainWithException() {
        assertThrows(NullPointerException.class, () -> main.method1(null));
    }
}