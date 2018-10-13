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

    private UserStatus admin;
    private UserStatus editor;
    private UserStatus reader;

    private List<Users> users = new ArrayList<>();
    private List<DescriptionComponent> descriptionComponent = new ArrayList<>();
    private List<CategoryComponent> categoryComponent = new ArrayList<>();
    private List<Component> component = new ArrayList<>();
    private List<DescriptionThing> descriptionThing = new ArrayList<>();
    private List<EffectThing> effectThing = new ArrayList<>();
    private List<TypeThing> typeThing = new ArrayList<>();
    private List<Thing> thing = new ArrayList<>();
    private List<Draft> draft = new ArrayList<>();

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

    private void initUserStatus() {
        LOG.info("Init user status's starting...");
        admin = new UserStatus("admin");
        editor = new UserStatus("editor");
        reader = new UserStatus("reader");
        userStatusCrudRepository.save(admin);
        userStatusCrudRepository.save(editor);
        userStatusCrudRepository.save(reader);
        LOG.info("Init user status ends.");
    }

    private void initUsers() {
        LOG.info("Init user's starting...");
        users.add(new Users("admin", "123123123", "admin@mail.ru", admin));
        users.add(new Users("editor", "123123123", "editor@mail.ru", editor));
        users.add(new Users("reader", "123123123", "reader@mail.ru", reader));
        users.forEach(usersCrudRepository::save);
        LOG.info("Init user ends.");
    }

    private void initDescriptionComponent() {
        LOG.info("Init description component's starting...");
        descriptionComponent.add(new DescriptionComponent("information1"));
        descriptionComponent.add(new DescriptionComponent("information2"));
        descriptionComponent.add(new DescriptionComponent("information3"));
        descriptionComponent.add(new DescriptionComponent("information4"));
        descriptionComponent.forEach(descriptionComponentCrudRepository::save);
        LOG.info("Init description component ends.");
    }

    private void initCategoryComponent() {
        LOG.info("Init category component's starting...");
        categoryComponent.add(new CategoryComponent("usual", "info"));
        categoryComponent.add(new CategoryComponent("unusual", "info"));
        categoryComponent.forEach(categoryComponentCrudRepository::save);
        LOG.info("Init category component ends.");
    }

    private void initComponent() {
        LOG.info("Init description component's starting...");
        component.add(new Component("wood", 1, 1.2, descriptionComponent.get(0), categoryComponent.get(0)));
        component.add(new Component("plastic", 5, 0.1, descriptionComponent.get(1), categoryComponent.get(1)));
        component.add(new Component("pen", 500, 1.4, descriptionComponent.get(2), categoryComponent.get(2)));
        component.add(new Component("steak", 10, 0.0, descriptionComponent.get(3), categoryComponent.get(3)));
        component.forEach(componentCrudRepository::save);
        LOG.info("Init component ends.");
    }

    private void initDescriptionThing() {
        LOG.info("Init description thing's starting...");
        descriptionThing.add(new DescriptionThing("desc1"));
        descriptionThing.add(new DescriptionThing("desc2"));
        descriptionThing.add(new DescriptionThing("desc3"));
        descriptionThing.add(new DescriptionThing("desc4"));
        descriptionThing.forEach(descriptionThingCrudRepository::save);
        LOG.info("Init description thing ends.");
    }

    private void initEffectThing() {
        LOG.info("Init effect thing's starting...");
        effectThing.add(new EffectThing("burning", "desc1", "info1"));
        effectThing.add(new EffectThing("burning", "desc2", "info2"));
        effectThing.add(new EffectThing("burning", "desc3", "info3"));
        effectThing.add(new EffectThing("burning", "desc4", "info4"));
        effectThing.add(new EffectThing("burning", "desc5", "info5"));
       effectThing.forEach(effectThingCrudRepository::save);
        LOG.info("Init effect thing ends.");
    }

    private void initTypeThing() {
        LOG.info("Init type thing's starting...");
        typeThing.add(new TypeThing("usual", null));
        typeThing.add(new TypeThing("unusual", "fantastic!"));
        typeThing.forEach(typeThingCrudRepository::save);
        LOG.info("Init type thing ends.");
    }

    private void initThing() {
        LOG.info("Init thing's starting...");
        thing.add(new Thing("sword", 800, 5, typeThing.get(0), descriptionThing.get(0), Arrays.asList(effectThing.get(0))));
        thing.add(new Thing("armor", 1, 0.1, typeThing.get(1), descriptionThing.get(1), Arrays.asList(effectThing.get(1))));
        thing.add(new Thing("cap", 10, 3, typeThing.get(0), descriptionThing.get(2), Arrays.asList(effectThing.get(2), effectThing.get(3))));
        thing.add(new Thing("ticket", 830, 4, typeThing.get(1), descriptionThing.get(3), Arrays.asList(effectThing.get(4))));
        thing.forEach(thingCrudRepository::save);
        LOG.info("Init thing ends.");
    }

    private void initDraft() {
        LOG.info("Init draft's starting...");
        draft.add(new Draft(thing.get(0), null, component));
        draft.add(new Draft(thing.get(1), "info", Arrays.asList(component.get(0), component.get(1), component.get(3))));
        draft.add(new Draft(thing.get(2), "inf1", Arrays.asList(component.get(1), component.get(2), component.get(3))));
        draft.add(new Draft(thing.get(0), "inf2", Arrays.asList(component.get(3))));
        draft.forEach(draftCrudRepository::save);
        LOG.info("Init draft ends.");
    }

    @Test
    public void test() {
        LOG.info("Test method's starting...");
        initUserStatus();
        initUsers();
        initDescriptionComponent();
        initCategoryComponent();
        initComponent();
        initDescriptionThing();
        initEffectThing();
        initTypeThing();
        initThing();
        initDraft();

        LOG.info("CategoryComponentFindById method's starting...");
        assertEquals(categoryComponent.get(0), (categoryComponentCrudRepository.findById(categoryComponent.get(0).getId()).get()));
        assertEquals(categoryComponent.get(1), (categoryComponentCrudRepository.findById(categoryComponent.get(1).getId()).get()));
        LOG.info("CategoryComponentFindById method ends.");

        LOG.info("CategoryComponentFindByName method's starting...");
        assertEquals(categoryComponent.get(0).getName(), categoryComponentCrudRepository.findByName("usual").getName());
        assertEquals(categoryComponent.get(1).getName(), categoryComponentCrudRepository.findByName("unusual").getName());
        LOG.info("CategoryComponentFindByName method ends.");

        LOG.info("Test method ends.");
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
