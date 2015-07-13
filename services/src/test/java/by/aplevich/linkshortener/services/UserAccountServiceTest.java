package by.aplevich.linkshortener.services;

import by.aplevich.linkshortener.AbstractServiceTest;
import by.aplevich.linkshortener.datamodel.UserAccount;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;

public class UserAccountServiceTest extends AbstractServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAccountServiceTest.class);

    @Test
    public void crudTest() {
        LOGGER.info("Starting CrudTest for UserAccount");
        UserAccount userOne = createUser();

        final UserAccount createdUserAccount = userAccountService.get(userOne.getId());
        Assert.assertNotNull(createdUserAccount);
        Assert.assertEquals(createdUserAccount.getName(), userOne.getName());
        Assert.assertEquals(createdUserAccount.getLogin(), userOne.getLogin());
        Assert.assertEquals(createdUserAccount.getPassword(), userOne.getPassword());

        createdUserAccount.setName("NewUserName");
        userAccountService.updateUser(createdUserAccount);
        final UserAccount createdUserAccountFromDB = userAccountService.get(createdUserAccount.getId());
        Assert.assertEquals(createdUserAccountFromDB.getName(), createdUserAccount.getName());

        userAccountService.deteteUser(createdUserAccountFromDB.getId());
        Assert.assertNull(userAccountService.get(createdUserAccountFromDB.getId()));
    }

    @Test
    public void uniqueConstraintsTest() {
        LOGGER.info("Starting UniqueConstraintsTest for UserAccount ");
        final UserAccount user = createUser();
        final String login = randomString("login");
        user.setLogin(login);
        userAccountService.updateUser(user);

        final UserAccount duplicateUser = createUser();
        duplicateUser.setLogin(login);
        try {
            userAccountService.updateUser(duplicateUser);
            Assert.fail("Not unique login can`t be saved ");
        } catch (final PersistenceException e) {
            e.printStackTrace();
        }

        duplicateUser.setLogin(randomString("login"));
        userAccountService.updateUser(duplicateUser);
    }
}