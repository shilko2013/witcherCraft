import com.shilko.ru.wither.entity.*;
import com.shilko.ru.wither.repository.*;
import org.hibernate.annotations.Type;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.logging.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.FileHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/ApplicationConfig.xml"})
@Transactional
public class DatabaseTest {

    private static class testException {
        private static void test(Runnable runnable) {
            try {
                runnable.run();
                System.exit(-1);
            } catch (Exception ignore) { }
        }
    }

    @Autowired
    private UsersCrudRepository usersCrudRepository;

    @Autowired
    private UserStatusCrudRepository userStatusCrudRepository;

    @Autowired
    private CategoryComponentCrudRepository categoryComponentCrudRepository;

    @Autowired
    private ComponentCrudRepository componentCrudRepository;

    @Autowired
    private DescriptionComponentCrudRepository descriptionComponentCrudRepository;

    @Autowired
    private DescriptionThingCrudRepository descriptionThingCrudRepository;

    @Autowired
    private DraftCrudRepository draftCrudRepository;

    @Autowired
    private EffectThingCrudRepository effectThingCrudRepository;

    @Autowired
    private ThingCrudRepository thingCrudRepository;

    @Autowired
    private TypeThingCrudRepository typeThingCrudRepository;

    private static final Logger LOG;
    private static boolean beforeWorked;

    private UserStatus reader;

    static {
        LOG = Logger.getLogger("databaseTest");
        try {
            FileHandler fileHandler = new FileHandler("LogDatabase.txt");
            LOG.addHandler(fileHandler);
        } catch (IOException e) {
            LOG.warning("File logging isn't available!");
        }
        LOG.info("Log init.");
        beforeWorked = false;
    }

    @BeforeClass
    public static void logStart() {
        LOG.info("Test's starting...");
    }

    @Before
    @Rollback(false)
    public void init() {
        if (beforeWorked)
            return;
        beforeWorked = true;
        LOG.info("Before method's starting...");

        LOG.info("Init user status's starting...");
        UserStatus admin = new UserStatus("admin");
        userStatusCrudRepository.save(admin);
        UserStatus editor = new UserStatus("editor");
        userStatusCrudRepository.save(editor);
        reader = new UserStatus("reader");
        userStatusCrudRepository.save(reader);
        LOG.info("Init user status ends.");

        LOG.info("Init user's starting...");
        Users users = new Users("admin", "123123123", "admin@mail.ru", admin);
        usersCrudRepository.save(users);
        users = new Users("editor", "123123123", "editor@mail.ru", editor);
        usersCrudRepository.save(users);
        users = new Users("reader", "123123123", "reader@mail.ru", reader);
        usersCrudRepository.save(users);
        LOG.info("Init user's ends.");



        LOG.info("Before method ends.");
    }

    @Test
    public void nullUserStatus() {
        LOG.info("nullUserStatus method's starting...");
        testException.test(() -> {
            UserStatus userStatus = new UserStatus(null);
            userStatusCrudRepository.save(userStatus);
        });
        LOG.info("nullUserStatus method ends.");
    }

    @Test
    public void notUniqueLoginUser() {
        LOG.info("notUniqueLoginUser method's starting...");
        testException.test(() -> {
            Users user = new Users("reader", "123123123", "@mail.ru", reader);
            usersCrudRepository.save(user);
        });
        LOG.info("notUniqueLoginUser method ends.");
    }

    @Test
    public void notUniqueEmailUser() {
        LOG.info("notUniqueEmailUser method's starting...");
        testException.test(() -> {
            Users user = new Users("reader2", "123123123", "reader@mail.ru", reader);
            usersCrudRepository.save(user);
        });
        LOG.info("notUniqueEmailUser method ends.");

    }

    /*@Test
    public void find() {
        LOG.info("Save method's starting...");
        Optional<Users> user = usersCrudRepository.findByLogin("a");
        if (!user.isPresent())
            throw new NoSuchElementException();
        LOG.info(user.get().toString());
        List<UserStatus> userStatuses = userStatusCrudRepository.findAll();
        if (!userStatuses.get(0).getStatus().equals(userStatusStatus))
            throw new NoSuchElementException();
        LOG.info(userStatuses.get(0).toString());
        LOG.info("Save method ends.");
    }*/

    @Test
    public void count() {

    }

    @After
    public void delete() {

    }

    @AfterClass
    public static void logEnd() {
        LOG.info("Test ends.");
    }
}
