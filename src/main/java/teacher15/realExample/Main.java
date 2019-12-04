package teacher15.realExample;


import teacher15.realExample.dao.MobileDao;
import teacher15.realExample.dao.MobileDaoJdbcImpl;
import teacher15.realExample.pojo.Mobile;

/**
 * Source: github.com/avsharapov/inno20/tree/master/src/stc/inno
 */
public class Main {
    public static void main(String[] args) {
        Mobile mobile = new Mobile(null, "Iphone 2", 25000, "Apple");
        MobileDao mobileDao = new MobileDaoJdbcImpl();
        mobileDao.addMobile(mobile);
        mobile = mobileDao.getMobileById(5);
        System.out.println(mobile);
        mobile.setPrice(70000);
        mobileDao.updateMobileById(mobile);
        mobile = mobileDao.getMobileById(5);
        System.out.println(mobile);
    }
}