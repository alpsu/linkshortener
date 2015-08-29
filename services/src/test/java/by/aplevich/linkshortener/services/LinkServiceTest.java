package by.aplevich.linkshortener.services;

import by.aplevich.linkshortener.AbstractServiceTest;
import by.aplevich.linkshortener.datamodel.Link;
import by.aplevich.linkshortener.datamodel.Teg;
import by.aplevich.linkshortener.datamodel.UserAccount;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;
import java.util.HashSet;
import java.util.List;

public class LinkServiceTest extends AbstractServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LinkServiceTest.class);

    @Test
    public void basicCRUDTest() {
        LOGGER.debug("Starting basicCRUDTest for Link");
        Link link = createLink();

        Link linkFromDb = linkService.get(link.getId());
        Assert.assertNotNull(linkFromDb);

        Assert.assertEquals(linkFromDb.getUrl(), link.getUrl());
        Assert.assertEquals(linkFromDb.getCode(), link.getCode());
        Assert.assertEquals(linkFromDb.getQuantity(), link.getQuantity());
        Assert.assertEquals(linkFromDb.getDescription(), link.getDescription());
        Assert.assertEquals(linkFromDb.getDescription(), link.getDescription());
        Assert.assertEquals(linkFromDb.getDescription(), link.getDescription());
        Assert.assertEquals(linkFromDb.getTegs(), link.getTegs());

        linkFromDb.setUrl("newURL");
        linkFromDb.setCode("newCode");
        linkFromDb.setDescription("newDesc");
        linkFromDb.setQuantity(12);
        linkService.saveOrUpdate(linkFromDb);
        Link linkFromDbUpdated = linkService.get(linkFromDb.getId());
        Assert.assertEquals(linkFromDbUpdated.getUrl(), linkFromDb.getUrl());
        Assert.assertEquals(linkFromDbUpdated.getCode(), linkFromDb.getCode());
        Assert.assertEquals(linkFromDbUpdated.getDescription(), linkFromDb.getDescription());
        Assert.assertEquals(linkFromDbUpdated.getQuantity(), linkFromDb.getQuantity());

        linkService.delete(linkFromDbUpdated);
        Assert.assertNull(tegService.get(linkFromDb.getId()));
    }

    @Test
    public void uniqueConstraintsTest() {
        LOGGER.info("Starting UniqueConstraintsTest for Link");
        final Link link = createLink();
        final String code = link.getCode();

        final Link duplicateLink = createLink();
        duplicateLink.setCode(code);
        try {
            linkService.saveOrUpdate(duplicateLink);
            Assert.fail("Not unique code. Can`t be saved ");
        } catch (final PersistenceException e) {
            e.printStackTrace();
        }

        duplicateLink.setCode(randomString("c"));
        linkService.saveOrUpdate(duplicateLink);
    }

    @Test
    public void getAllLinksByUserTest() {
        LOGGER.debug("Starting getAllLinksTest by user");
        int num = randomInteger(2, 5);
        LOGGER.debug("count: {}", num);

        UserAccount user = createUser();

        for (int i = 0; i < num; i++) {
            Link link = createLink();
            link.setUserAccount(user);
            linkService.saveOrUpdate(link);
            LOGGER.debug("link: {}", link);
        }

        List<Link> links = linkService.getAllLinksByUser(user.getId());
        LOGGER.debug("links: {}", links);
        Assert.assertEquals(links.size(), num);
    }

    @Test
    public void getNextIdTest(){
        LOGGER.debug("Starting getNextIdTest");
        Long id1 = linkService.getNextId();
        Long id2 = linkService.getNextId();
        LOGGER.debug("id1: {}", id1);
        LOGGER.debug("id2: {}", id2);
        LOGGER.debug("substraction: {}", id2-id1);
        Assert.assertEquals(id1, id2);
        Long id3 = linkService.getNextId();
        Link link = createLink();
        Long id4 = linkService.getNextId();
        LOGGER.debug("id3: {}", id3);
        LOGGER.debug("id4: {}", id4);
        LOGGER.debug("substraction: {}", id4-id3);
        Assert.assertNotEquals(id3, id4);
    }

    @Test
    public void encodeDecodeTest() {
        LOGGER.debug("Starting encodeDecodeTest");
        Long id = linkService.getNextId();
        Link link = new Link();
        String url = randomString("url"); //get url
        link.setUrl(url);
        String code = linkService.encode(id.intValue());
        link.setCode(code);
        UserAccount user = createUser();
        userAccountService.updateUser(user);
        link.setUserAccount(user);
        link.setQuantity(randomInteger());
        link.setDescription(randomString("descr"));
        int randomTestObjectsCount = randomTestObjectsCount();
        HashSet<Teg> tags = new HashSet<>();
        for (int i = 0; i < randomTestObjectsCount; i++) {
            Teg teg = createTeg();
            tegService.saveOrUpdate(teg);
            tags.add(teg);
        }
        link.setTegs(tags);
        linkService.saveOrUpdate(link);

        Link linkFromDB = linkService.get(id);
        Assert.assertNotNull(linkFromDB);

        String codeFromLinkFromDB = linkFromDB.getCode();
        int num = linkService.decode(codeFromLinkFromDB);
        Assert.assertEquals(Long.valueOf(num), id);
        LOGGER.debug("id: {}", id);
        LOGGER.debug("id form db: {}", num);

        String urlFromDb = linkFromDB.getUrl();
        Assert.assertEquals(url, urlFromDb);
        LOGGER.debug("Url from DB: {}", urlFromDb);
    }
}