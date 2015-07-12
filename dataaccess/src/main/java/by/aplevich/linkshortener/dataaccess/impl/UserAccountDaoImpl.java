package by.aplevich.linkshortener.dataaccess.impl;

import by.aplevich.linkshortener.dataaccess.UserAccountDao;
import by.aplevich.linkshortener.datamodel.UserAccount;
import org.springframework.stereotype.Repository;

@Repository
public class UserAccountDaoImpl extends AbstractDaoImpl<Long, UserAccount> implements UserAccountDao{
    protected UserAccountDaoImpl(Class<UserAccount> userAccountClass) {
        super(userAccountClass);
    }
}
