import com.shilko.ru.wither.database.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
import java.util.logging.SimpleFormatter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/ApplicationConfig.xml"})
@Transactional
public class DatabaseTest {

    @Autowired
    private UsersCrudRepository usersCrudRepository;

    @Autowired
    private UserStatusCrudRepository userStatusCrudRepository;

    private static final Logger LOG;

    private String userStatusStatus;

    static {
        LOG = Logger.getLogger("databaseTest");
        try {
            FileHandler fileHandler = new FileHandler("log.txt");
            LOG.addHandler(fileHandler);
        } catch (IOException e) {
            LOG.warning("File logging isn't available!");
        }
        LOG.info("Log init.");
    }

    @Before
    @Rollback(false)
    public void setUp() {
        LOG.info("Test's starting...");
        UserStatus userStatus = new UserStatus("1");
        userStatusStatus = userStatus.getStatus();
        userStatusCrudRepository.save(userStatus);
        Users users = new Users("123", "123123123", "123", userStatus);
        usersCrudRepository.save(users);
        LOG.info("Before method end.");
    }

    @Test
    public void save() {
        LOG.info("Save method's starting...");
        Optional<Users> user = usersCrudRepository.findByLogin("123");
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
