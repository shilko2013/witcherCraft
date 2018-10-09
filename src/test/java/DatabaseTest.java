import com.shilko.ru.wither.database.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        LOG = LoggerFactory.getLogger(DatabaseTest.class);
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
        System.out.println(user.get());
        List<UserStatus> userStatuses = userStatusCrudRepository.findAll();
        if (!userStatuses.get(0).getStatus().equals(userStatusStatus))
            throw new NoSuchElementException();
        System.out.println(userStatuses.get(0));
        LOG.info("Save method end.");
    }

    @Test
    public void count() {

    }

    @After
    public void delete() {

    }
}
