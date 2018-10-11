import com.shilko.ru.wither.entity.*;
import com.shilko.ru.wither.repository.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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

    @Autowired
    private UsersCrudRepository usersCrudRepository;

    @Autowired
    private UserStatusCrudRepository userStatusCrudRepository;

    private static final Logger LOG;
    private static boolean beforeWorked;
    private static final String userStatusStatus = "1";

    static {
        LOG = Logger.getLogger("databaseTest");
        try {
            FileHandler fileHandler = new FileHandler("log.txt");
            LOG.addHandler(fileHandler);
        } catch (IOException e) {
            LOG.warning("File logging isn't available!");
        }
        LOG.info("Log init.");
        beforeWorked = false;
    }

    @Before
    @Rollback(false)
    public void setUp() {
        if (beforeWorked)
            return;
        beforeWorked = true;
        LOG.info("Test's starting...");
        UserStatus userStatus = new UserStatus(userStatusStatus);
        userStatusCrudRepository.save(userStatus);
        Users users = new Users("a", "123123123", "a@mail.ru", userStatus);
        usersCrudRepository.save(users);
        LOG.info("Before method end.");
    }

    @Test
    public void save() {
        LOG.info("Save method's starting...");
        Optional<Users> user = usersCrudRepository.findByLogin("a");
        if (!user.isPresent())
            throw new NoSuchElementException();
        LOG.info(user.get().toString());
        List<UserStatus> userStatuses = userStatusCrudRepository.findAll();
        if (!userStatuses.get(0).getStatus().equals(userStatusStatus))
            throw new NoSuchElementException();
        LOG.info(userStatuses.get(0).toString());
        LOG.info("Save method end.");
    }

    @Test
    public void count() {

    }

    @After
    public void delete() {

    }
}
