package by.aplevich.linkshortener.services;

import by.aplevich.linkshortener.AbstractServiceTest;
import by.aplevich.linkshortener.datamodel.Teg;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;
import java.util.List;

public class TegServiceTest extends AbstractServiceTest{
    private static final Logger LOGGER = LoggerFactory.getLogger(TegServiceTest.class);

    @Test
    public void basicCRUDTest() {
        LOGGER.info("Starting basicCRUDTest for Teg");
        Teg teg = createTeg();

        Teg tegFromDB = tegService.get(teg.getId());
        Assert.assertNotNull(tegFromDB);
        Assert.assertEquals(tegFromDB.getName(), teg.getName());

        tegFromDB.setName("newName");
        tegService.saveOrUpdate(tegFromDB);
        Teg tegFromDbUpdated = tegService.get(tegFromDB.getId());
        Assert.assertEquals(tegFromDbUpdated.getName(), tegFromDB.getName());

        tegService.delete(tegFromDbUpdated);
        Assert.assertNull(tegService.get(tegFromDbUpdated.getId()));
    }

    @Test
    public void deleteAllTest() {
        LOGGER.info("Starting deleteAll for tags");
        int count = randomInteger(2, 5);
        LOGGER.info("count: {}", count);
        for (int i = 0; i < count; i++) {
            createTeg();
        }

        tegService.deleteAll();
        List<Teg> tegs = tegService.getAllTegs();
        Assert.assertEquals(tegs.size(), 0);
    }

    @Test
    public void getAllTegsTest() {
        LOGGER.info("Starting getAllTegs test");
        int num = randomInteger(2, 5);
        LOGGER.info("nums: {}", num);
        for (int i = 0; i < num; i++) {
            createTeg();
        }

        List<Teg> tegs = tegService.getAllTegs();
        Assert.assertEquals(tegs.size(), num);
        LOGGER.info("List of teg: {}", tegs);
    }

    @Test
    public void uniqueConstraintsTest() {
        LOGGER.info("Starting UniqueConstraintsTest for Teg");
        final Teg teg = createTeg();
        final String name = teg.getName();

        final Teg duplicateTeg = createTeg();
        duplicateTeg.setName(name);
        try {
            tegService.saveOrUpdate(duplicateTeg);
            Assert.fail("Not unique name. Can`t be saved ");
        } catch (final PersistenceException e) {
            e.printStackTrace();
        }

        duplicateTeg.setName(randomString("name"));
        tegService.saveOrUpdate(duplicateTeg);
    }
}