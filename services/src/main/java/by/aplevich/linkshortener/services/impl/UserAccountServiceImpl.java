package by.aplevich.linkshortener.services.impl;

import by.aplevich.linkshortener.dataaccess.UserAccountDao;
import by.aplevich.linkshortener.datamodel.UserAccount;
import by.aplevich.linkshortener.datamodel.UserAccount_;
import by.aplevich.linkshortener.services.UserAccountService;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    @Inject
    private UserAccountDao dao;

    @Override
    public UserAccount get(Long id) {
        return dao.getById(id);
    }

    @Override
    public void createNewUser(UserAccount userOne) {
        Validate.isTrue(userOne.getId() == null,
                "This method should be called for 'not saved yet' user only. Use UPDATE instead");
        LOGGER.debug("Create new: {}", userOne);
        dao.insert(userOne);
    }

    @Override
    public void updateUser(UserAccount userOne) {
        LOGGER.debug("Update: {}", userOne);
        dao.update(userOne);
    }

    @Override
    public void deteteUser(Long id) {
        LOGGER.debug("Delete user with id: {}", id);
        dao.delete(id);
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("Remove all users");
        dao.deleteAll();
    }

    @Override
    public UserAccount getUserByLogin(String userLogin) {
        final List<UserAccount> allByFieldRestriction = dao.getAllByFieldRestriction(UserAccount_.login, userLogin);
        return !allByFieldRestriction.isEmpty() ? allByFieldRestriction.get(0) : null;
    }
//
//    @Override
//    public List<UserRole> getRoles(Long userId) {
//        return dao.getUserRole(userId);
//    }
}