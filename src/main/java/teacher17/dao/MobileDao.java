package teacher17.dao;

import teacher17.pojo.Mobile;

/**
 *   Source: github.com/avsharapov/inno20Testing
 */
public interface MobileDao {
    boolean addMobile(Mobile mobile);

    Mobile getMobileById(Integer id);

    boolean updateMobileById(Mobile mobile);

    boolean deleteMobileById(Integer id);
}