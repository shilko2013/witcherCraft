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
import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.*;
import java.util.logging.FileHandler;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/ApplicationConfig.xml"})
@Transactional
public class DatabaseTest {

    private static class testException {
        private static void test(Runnable runnable) {
            try {
                runnable.run();
                System.exit(-1);
            } catch (Exception ignore) {
            }
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
    private static FileHandler fileHandler;

    static {
        LOG = Logger.getLogger("databaseTest");
        try {
            fileHandler = new FileHandler("LogDatabase.txt");
            LOG.addHandler(fileHandler);
        } catch (IOException e) {
            LOG.warning("File logging isn't available!");
        }
        LOG.info("Log init.");
    }

    @BeforeClass
    public static void logStart() {
        LOG.info("Test's starting...");
    }



    @Test
    public void test() {
        LOG.info("Test method's starting...");

        LOG.info("Init user status's starting...");
        UserStatus admin = new UserStatus("admin");
        UserStatus editor = new UserStatus("editor");
        UserStatus reader = new UserStatus("reader");
        userStatusCrudRepository.save(admin);
        userStatusCrudRepository.save(editor);
        userStatusCrudRepository.save(reader);
        LOG.info("Init user status ends.");

        LOG.info("Init user's starting...");
        Users users1 = new Users("admin", "123123123", "admin@mail.ru", admin);
        Users users2 = new Users("editor", "123123123", "editor@mail.ru", editor);
        Users users3 = new Users("reader", "123123123", "reader@mail.ru", reader);
        usersCrudRepository.save(users1);
        usersCrudRepository.save(users2);
        usersCrudRepository.save(users3);
        LOG.info("Init user ends.");

        LOG.info("Init description component's starting...");
        DescriptionComponent descriptionComponent1 = new DescriptionComponent("information1");
        DescriptionComponent descriptionComponent2 = new DescriptionComponent("information2");
        DescriptionComponent descriptionComponent3 = new DescriptionComponent("information3");
        DescriptionComponent descriptionComponent4 = new DescriptionComponent("information4");
        descriptionComponentCrudRepository.save(descriptionComponent1);
        descriptionComponentCrudRepository.save(descriptionComponent2);
        descriptionComponentCrudRepository.save(descriptionComponent3);
        descriptionComponentCrudRepository.save(descriptionComponent4);
        LOG.info("Init description component ends.");

        LOG.info("Init category component's starting...");
        CategoryComponent categoryComponent1 = new CategoryComponent("usual", "info");
        CategoryComponent categoryComponent2 = new CategoryComponent("unusual", "info");
        categoryComponentCrudRepository.save(categoryComponent1);
        categoryComponentCrudRepository.save(categoryComponent2);
        LOG.info("Init category component ends.");

        LOG.info("Init description component's starting...");
        Component component1 = new Component("wood", 1, 1.2, descriptionComponent1, categoryComponent1);
        Component component2 = new Component("plastic", 5, 0.1, descriptionComponent2, categoryComponent2);
        Component component3 = new Component("pen", 500, 1.4, descriptionComponent3, categoryComponent1);
        Component component4 = new Component("steak", 10, 0.0, descriptionComponent4, categoryComponent1);
        componentCrudRepository.save(component1);
        componentCrudRepository.save(component2);
        componentCrudRepository.save(component3);
        componentCrudRepository.save(component4);
        LOG.info("Init component ends.");

        LOG.info("Init description thing's starting...");
        DescriptionThing descriptionThing1 = new DescriptionThing("desc1");
        DescriptionThing descriptionThing2 = new DescriptionThing("desc2");
        DescriptionThing descriptionThing3 = new DescriptionThing("desc3");
        DescriptionThing descriptionThing4 = new DescriptionThing("desc4");
        descriptionThingCrudRepository.save(descriptionThing1);
        descriptionThingCrudRepository.save(descriptionThing2);
        descriptionThingCrudRepository.save(descriptionThing3);
        descriptionThingCrudRepository.save(descriptionThing4);
        LOG.info("Init description thing ends.");

        LOG.info("Init effect thing's starting...");
        EffectThing effectThing1 = new EffectThing("burning", "desc1", "info1");
        EffectThing effectThing2 = new EffectThing("burning", "desc2", "info2");
        EffectThing effectThing3 = new EffectThing("burning", "desc3", "info3");
        EffectThing effectThing4 = new EffectThing("burning", "desc4", "info4");
        EffectThing effectThing5 = new EffectThing("burning", "desc5", "info5");
        effectThingCrudRepository.save(effectThing1);
        effectThingCrudRepository.save(effectThing2);
        effectThingCrudRepository.save(effectThing3);
        effectThingCrudRepository.save(effectThing4);
        effectThingCrudRepository.save(effectThing5);
        LOG.info("Init effect thing ends.");

        LOG.info("Init type thing's starting...");
        TypeThing typeThing1 = new TypeThing("usual", null);
        TypeThing typeThing2 = new TypeThing("unusual", "fantastic!");
        typeThingCrudRepository.save(typeThing1);
        typeThingCrudRepository.save(typeThing2);
        LOG.info("Init type thing ends.");

        LOG.info("Init thing's starting...");
        Thing thing1 = new Thing("sword", 800, 5, typeThing1, descriptionThing1, Arrays.asList(effectThing1));
        Thing thing2 = new Thing("armor", 1, 0.1, typeThing2, descriptionThing2, Arrays.asList(effectThing2));
        Thing thing3 = new Thing("cap", 10, 3, typeThing1, descriptionThing3, Arrays.asList(effectThing3,effectThing4));
        Thing thing4 = new Thing("ticket", 830, 4, typeThing2, descriptionThing4, Arrays.asList(effectThing5));
        thingCrudRepository.save(thing1);
        thingCrudRepository.save(thing2);
        thingCrudRepository.save(thing3);
        thingCrudRepository.save(thing4);
        LOG.info("Init thing ends.");

        LOG.info("Init draft's starting...");
        Draft draft1 = new Draft(thing1,null, Arrays.asList(component1,component2,component3,component4));
        Draft draft2 = new Draft(thing2,"info", Arrays.asList(component1,component2,component4));
        Draft draft3 = new Draft(thing3,"inf1", Arrays.asList(component1,component3,component4));
        Draft draft4 = new Draft(thing1,"inf2", Arrays.asList(component4));
        draftCrudRepository.save(draft1);
        draftCrudRepository.save(draft2);
        draftCrudRepository.save(draft3);
        draftCrudRepository.save(draft4);
        LOG.info("Init draft ends.");

        LOG.info("Test method ends.");

        LOG.info("CategoryComponentFindById method's starting...");
        assertEquals(categoryComponent1,(categoryComponentCrudRepository.findById(categoryComponent1.getId()).get()));
        assertEquals(categoryComponent2,(categoryComponentCrudRepository.findById(categoryComponent2.getId()).get()));
        LOG.info("CategoryComponentFindById method ends.");

        LOG.info("CategoryComponentFindByName method's starting...");
        assertEquals(categoryComponent1.getName(),categoryComponentCrudRepository.findByName("usual").getName());
        assertEquals(categoryComponent2.getName(),categoryComponentCrudRepository.findByName("unusual").getName());
        LOG.info("CategoryComponentFindByName method ends.");

    }

    /*@Test
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
    }*/

    /*@Test
    public void CategoryComponentFindById() {
        LOG.info("CategoryComponentFindById method's starting...");
        assertEquals(categoryComponent1,(categoryComponentCrudRepository.findById(categoryComponent1.getId()).get()));
        assertEquals(categoryComponent2,(categoryComponentCrudRepository.findById(categoryComponent2.getId()).get()));
        LOG.info("CategoryComponentFindById method ends.");

    }

    @Test
    public void CategoryComponentFindByName() {
        LOG.info("CategoryComponentFindByName method's starting...");
        assertEquals(categoryComponent1.getName(),categoryComponentCrudRepository.findByName("usual").getName());
        assertEquals(categoryComponent2.getName(),categoryComponentCrudRepository.findByName("unusual").getName());
        LOG.info("CategoryComponentFindByName method ends.");
    }*/



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

    /*@Test
    public void count() {

    }

    @After
    public void delete() {

    }*/

    @AfterClass
    public static void logEnd() {
        LOG.info("Test ends.");
        fileHandler.flush();
        fileHandler.close();
    }
}
