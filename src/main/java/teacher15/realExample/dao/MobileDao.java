package teacher15.realExample.dao;


import teacher15.realExample.pojo.Mobile;

/**
 * Source: github.com/avsharapov/inno20/tree/master/src/stc/inno
 */
public interface MobileDao {
    public boolean addMobile(Mobile mobile);

    public Mobile getMobileById(Integer id);

    public boolean updateMobileById(Mobile mobile);

    public boolean deleteMobileById(Integer id);
}
