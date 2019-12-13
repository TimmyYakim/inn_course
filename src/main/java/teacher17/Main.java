package teacher17;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teacher17.connectionmanager.ConnectionManager;
import teacher17.connectionmanager.ConnectionManagerJdbcImpl;
import teacher17.dao.MobileDao;
import teacher17.dao.MobileDaoJdbcImpl;
import teacher17.pojo.Mobile;

/**
 *   Source: github.com/avsharapov/inno20Testing
 */

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ConnectionManager connectionManager = ConnectionManagerJdbcImpl.getInstance();
        MobileDao mobileDao = new MobileDaoJdbcImpl(connectionManager);
        Main main = new Main();
        main.method1(mobileDao);
    }


    public void method1(MobileDao mobileDao) {
        Mobile mobile = new Mobile(null, "Iphone 2", 25000, "Apple");
        mobileDao.addMobile(mobile);
        mobile = mobileDao.getMobileById(4);
        LOGGER.info("Начальный объект: {}", mobile);
        mobile.setPrice(70000);
        mobileDao.updateMobileById(mobile);
        mobile = mobileDao.getMobileById(4);
        LOGGER.info("Итоговый объект: {}", mobile);
    }
}