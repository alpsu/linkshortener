package by.aplevich.linkshortener;

import by.aplevich.linkshortener.datamodel.*;
import by.aplevich.linkshortener.services.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.math3.random.RandomData;
import org.apache.commons.math3.random.RandomDataImpl;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public abstract class AbstractServiceTest {
    protected static final RandomData RANDOM_DATA = new RandomDataImpl();
    private final static Random random = new Random();
    private static final int RANDOM_STRING_SIZE = 8;

    @Inject
    protected TegService tegService;
    @Inject
    protected LinkService linkService;
    @Inject
    protected UserAccountService userAccountService;

    public static String randomString() {
        return RandomStringUtils.randomAlphabetic(RANDOM_STRING_SIZE);
    }

    public static String randomString(final String prefix) {
        return String.format("%s-%s", new Object[]{prefix, randomString()});
    }

    public static int randomTestObjectsCount() {
        return RANDOM_DATA.nextInt(0, 5) + 1;
    }

    public static int randomInteger() {
        return randomInteger(0, 9999);
    }

    public static int randomInteger(final int lower, final int upper) {
        return RANDOM_DATA.nextInt(lower, upper);
    }

    public static boolean randomBoolean() {
        return Math.random() < 0.5;
    }

    public static long randomLong() {
        return RANDOM_DATA.nextLong(0, 9999999);
    }

    public static BigDecimal randomBigDecimal() {
        return new BigDecimal(randomDouble()).setScale(2, RoundingMode.HALF_UP);
    }

    public static double randomDouble() {
        final double value = random.nextDouble() + randomInteger();
        return Math.round(value * 1e2) / 1e2;

    }

    public static <T> T randomFromCollection(final Collection<T> all) {
        final int size = all.size();
        final int item = new Random().nextInt(size); // In real life, the Random
        // object should be
        // rather more shared
        // than this
        int i = 0;
        for (final T obj : all) {
            if (i == item) {
                return obj;
            }
            i = i + 1;
        }
        return null;
    }

    static Date randomDate() {
        final int year = randBetween(1900, 2010);
        final GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.YEAR, year);
        final int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));
        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }

    public static int randBetween(final int start, final int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    protected UserAccount createUser() {
        UserAccount user = new UserAccount();
        user.setName(randomString("name"));
        user.setLogin(randomString("login"));
        user.setPassword(randomString("pass"));
        userAccountService.createNewUser(user);
        return user;
    }

    protected Teg createTeg() {
        Teg teg = new Teg();
        teg.setName(randomString("name"));
        tegService.saveOrUpdate(teg);
        return teg;
    }

    protected Link createLink() {
        Link link = new Link();
        UserAccount user = createUser();
        userAccountService.updateUser(user);
        link.setUserAccount(user);
        link.setUrl(randomString("url"));
        link.setCode(randomString("c"));
        link.setQuantity(randomInteger());
        link.setDescription(randomString("descr"));

        linkService.saveOrUpdate(link);
        return link;
    }

    @Before
    public void cleanUpData() {
        linkService.deleteAll();
        tegService.deleteAll();
        userAccountService.deleteAll();
    }
}