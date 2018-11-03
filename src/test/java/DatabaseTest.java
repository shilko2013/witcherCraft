import com.shilko.ru.witcher.entity.*;
import com.shilko.ru.witcher.repository.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import java.util.logging.FileHandler;

import static org.junit.Assert.*;


/**
 * Test class DatabaseTest includes one test method {@link DatabaseTest#test()} for testing entities.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/ApplicationConfig.xml"})
@Transactional
public class DatabaseTest {

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

    @Autowired
    private CraftOrAlchemyCrudRepository craftOrAlchemyCrudRepository;

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
    private List<CraftOrAlchemy> craftOrAlchemies = new ArrayList<>();

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

    /**
     * Log start.
     */
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

    private void initCraftOrAlchemy() {
        LOG.info("initCraftOrAlchemy starting...");
        craftOrAlchemies.add(new CraftOrAlchemy(true));
        craftOrAlchemies.add(new CraftOrAlchemy(false));
        craftOrAlchemies.forEach(craftOrAlchemyCrudRepository::save);
        LOG.info("initCraftOrAlchemy ends.");
    }

    private void initComponent() {
        LOG.info("Init description component's starting...");
        component.add(new Component("wood", 1, 1.2, descriptionComponent.get(0), categoryComponent.get(0),craftOrAlchemies.get(0)));
        component.add(new Component("plastic", 5, 0.1, descriptionComponent.get(1), categoryComponent.get(1),craftOrAlchemies.get(1)));
        component.add(new Component("pen", 500, 1.4, descriptionComponent.get(2), categoryComponent.get(0),craftOrAlchemies.get(0)));
        component.add(new Component("steak", 10, 0.0, descriptionComponent.get(3), categoryComponent.get(1),craftOrAlchemies.get(1)));
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

    private void initTypeThing() {
        LOG.info("Init type thing's starting...");
        typeThing.add(new TypeThing("usual", null));
        typeThing.add(new TypeThing("unusual", "fantastic!"));
        typeThing.forEach(typeThingCrudRepository::save);
        LOG.info("Init type thing ends.");
    }

    private void initThing() {
        LOG.info("Init thing's starting...");
        thing.add(new Thing("sword", 800, 5, typeThing.get(0), descriptionThing.get(0),craftOrAlchemies.get(0)));
        thing.add(new Thing("armor", 1, 0.1, typeThing.get(1), descriptionThing.get(1),craftOrAlchemies.get(1)));
        thing.add(new Thing("cap", 10, 3, typeThing.get(0), descriptionThing.get(2),craftOrAlchemies.get(1)));
        thing.add(new Thing("ticket", 830, 4, typeThing.get(1), descriptionThing.get(3),craftOrAlchemies.get(0)));
        thing.forEach(thingCrudRepository::save);
        LOG.info("Init thing ends.");
    }

    private void initEffectThing() {
        LOG.info("Init effect thing's starting...");
        effectThing.add(new EffectThing("burning", "desc1", "info1", thing.get(0)));
        effectThing.add(new EffectThing("burning", "desc2", "info2", thing.get(1)));
        effectThing.add(new EffectThing("burning", "desc3", "info3", thing.get(2)));
        effectThing.add(new EffectThing("burning", "desc4", "info4", thing.get(0)));
        effectThing.add(new EffectThing("burning", "desc5", "info5", thing.get(3)));
        effectThing.forEach(effectThingCrudRepository::save);
        LOG.info("Init effect thing ends.");
    }

    private void initDraft() {
        LOG.info("Init draft's starting...");
        draft.add(new Draft(thing.get(0), null, component, craftOrAlchemies.get(1)));
        draft.add(new Draft(thing.get(1), "info", Arrays.asList(component.get(0), component.get(1), component.get(3)),craftOrAlchemies.get(0)));
        draft.add(new Draft(thing.get(2), "inf1", Arrays.asList(component.get(1), component.get(2), component.get(3)),craftOrAlchemies.get(1)));
        draft.add(new Draft(thing.get(0), "inf2", Arrays.asList(component.get(3)),craftOrAlchemies.get(0)));
        draft.forEach(draftCrudRepository::save);
        LOG.info("Init draft ends.");
    }

    private void categoryComponentFindById() {
        LOG.info("CategoryComponentFindById method's starting...");
        assertEquals(categoryComponent.get(0), (categoryComponentCrudRepository.findById(categoryComponent.get(0).getId()).get()));
        assertEquals(categoryComponent.get(1), (categoryComponentCrudRepository.findById(categoryComponent.get(1).getId()).get()));
        LOG.info("CategoryComponentFindById method ends.");

    }

    private void categoryComponentFindByName() {
        LOG.info("CategoryComponentFindByName method's starting...");
        assertEquals(categoryComponent.get(0), categoryComponentCrudRepository.findByName("usual"));
        assertEquals(categoryComponent.get(1), categoryComponentCrudRepository.findByName("unusual"));
        LOG.info("CategoryComponentFindByName method ends.");
    }

    private void categoryComponentCount() {
        LOG.info("categoryComponentCount method's starting...");
        assertEquals(componentCrudRepository.findAll().size(), 4);
        LOG.info("categoryComponentCount method ends.");
    }

    private void componentFindByName() {
        LOG.info("componentFindByName method's starting...");
        assertEquals(component.get(0).getName(), componentCrudRepository.findByName("wood").getName());
        assertEquals(component.get(3).getName(), componentCrudRepository.findByName("steak").getName());
        LOG.info("componentFindByName method ends.");
    }

    private void componentFindAllByCategoryComponentEquals() {
        LOG.info("componentFindAllByCategoryComponentEquals method's starting...");
        assertEquals(componentCrudRepository.findAllByCategoryComponentEquals(categoryComponent.get(0)).size(), 2);
        assertEquals(componentCrudRepository.findAllByCategoryComponentEquals(categoryComponent.get(1)).size(), 2);
        LOG.info("componentFindAllByCategoryComponentEquals method ends.");
    }

    private void componentFindAllByDraftsContains() {
        LOG.info("componentFindAllByDraftsContains method's starting...");
        assertEquals(componentCrudRepository.findAllByDraftsContains(draft.get(0)).size(), component.size());
        assertEquals(componentCrudRepository.findAllByDraftsContains(draft.get(1)).size(), 3);
        assertEquals(componentCrudRepository.findAllByDraftsContains(draft.get(2)).size(), 3);
        assertEquals(componentCrudRepository.findAllByDraftsContains(draft.get(3)).size(), 1);
        LOG.info("componentFindAllByDraftsContains method ends.");
    }

    private void descriptionComponentFindByComponent() {
        LOG.info("descriptionComponentFindByComponent method's starting...");
        assertEquals(descriptionComponentCrudRepository.findByComponent(component.get(0)), component.get(0).getDescriptionComponent());
        assertEquals(descriptionComponentCrudRepository.findByComponent(component.get(1)), component.get(1).getDescriptionComponent());
        assertEquals(descriptionComponentCrudRepository.findByComponent(component.get(2)), component.get(2).getDescriptionComponent());
        assertEquals(descriptionComponentCrudRepository.findByComponent(component.get(3)), component.get(3).getDescriptionComponent());
        LOG.info("descriptionComponentFindByComponent method ends.");
    }

    private void descriptionThingFindByThing() {
        LOG.info("descriptionThingFindByThing method's starting...");
        assertEquals(descriptionThingCrudRepository.findByThing(thing.get(0)), descriptionThing.get(0));
        assertEquals(descriptionThingCrudRepository.findByThing(thing.get(1)), descriptionThing.get(1));
        assertEquals(descriptionThingCrudRepository.findByThing(thing.get(2)), descriptionThing.get(2));
        assertEquals(descriptionThingCrudRepository.findByThing(thing.get(3)), descriptionThing.get(3));
        LOG.info("descriptionThingFindByThing method ends.");
    }

    private void draftFindAllByComponentsContains() {
        LOG.info("draftFindAllByComponentsContains method's starting...");
        assertEquals(draftCrudRepository.findAllByComponentsContains(component.get(0)).size(), 2);
        assertEquals(draftCrudRepository.findAllByComponentsContains(component.get(1)).size(), 3);
        assertEquals(draftCrudRepository.findAllByComponentsContains(component.get(2)).size(), 2);
        assertEquals(draftCrudRepository.findAllByComponentsContains(component.get(3)).size(), component.size());
        LOG.info("draftFindAllByComponentsContains method ends.");
    }

    private void draftFindAllByThing() {
        LOG.info("draftFindAllByThing method's starting...");
        assertEquals(draftCrudRepository.findAllByThing(thing.get(0)).size(), 2);
        assertEquals(draftCrudRepository.findAllByThing(thing.get(1)).get(0), draft.get(1));
        assertEquals(draftCrudRepository.findAllByThing(thing.get(2)).get(0), draft.get(2));
        LOG.info("draftFindAllByThing method ends.");
    }

    private void effectThingFindAllByName() {
        LOG.info("effectThingFindByName method's starting...");
        assertEquals(effectThingCrudRepository.findAllByName("burning").size(), 5);
        LOG.info("effectThingFindByName method ends.");
    }

    private void effectThingFindAllByThing() {
        LOG.info("effectThingFindAllByThing method's starting...");
        assertEquals(effectThingCrudRepository.findAllByThing(thing.get(0)).get(0), effectThing.get(0));
        assertEquals(effectThingCrudRepository.findAllByThing(thing.get(1)).get(0), effectThing.get(1));
        assertEquals(effectThingCrudRepository.findAllByThing(thing.get(0)).size(), 2);
        assertEquals(effectThingCrudRepository.findAllByThing(thing.get(3)).get(0), effectThing.get(4));
        LOG.info("effectThingFindAllByThing method ends.");
    }

    private void thingFindByDraftsContains() {
        LOG.info("thingFindByDraftsContains method's starting...");
        assertEquals(thingCrudRepository.findByDraftsContains(draft.get(0)), thing.get(0));
        assertEquals(thingCrudRepository.findByDraftsContains(draft.get(1)), thing.get(1));
        assertEquals(thingCrudRepository.findByDraftsContains(draft.get(2)), thing.get(2));
        assertEquals(thingCrudRepository.findByDraftsContains(draft.get(3)), thing.get(0));
        LOG.info("thingFindByDraftsContains method ends.");
    }

    private void thingFindAllByTypeThing() {
        LOG.info("thingFindAllByTypeThing method's starting...");
        assertEquals(thingCrudRepository.findAllByTypeThing(typeThing.get(0)).size(), 2);
        assertEquals(thingCrudRepository.findAllByTypeThing(typeThing.get(1)).size(), 2);
        LOG.info("thingFindAllByTypeThing method ends.");
    }

    private void typeThingFindByName() {
        LOG.info("typeThingFindByName method's starting...");
        assertEquals(typeThingCrudRepository.findByName("usual"), typeThing.get(0));
        assertEquals(typeThingCrudRepository.findByName("unusual"), typeThing.get(1));
        LOG.info("typeThingFindByName method ends.");
    }

    private void typeThingFindByThingsContains() {
        LOG.info("typeThingFindByThingsContains method's starting...");
        assertEquals(typeThingCrudRepository.findByThingsContains(thing.get(0)).get(0), typeThing.get(0));
        assertEquals(typeThingCrudRepository.findByThingsContains(thing.get(1)).get(0), typeThing.get(1));
        assertEquals(typeThingCrudRepository.findByThingsContains(thing.get(2)).get(0), typeThing.get(0));
        assertEquals(typeThingCrudRepository.findByThingsContains(thing.get(3)).get(0), typeThing.get(1));
        LOG.info("typeThingFindByThingsContains method ends.");
    }

    private void usersFindByEmail() {
        LOG.info("usersFindByEmail method's starting...");
        assertEquals(usersCrudRepository.findByEmail("admin@mail.ru").get(),users.get(0));
        assertEquals(usersCrudRepository.findByEmail("editor@mail.ru").get(),users.get(1));
        assertEquals(usersCrudRepository.findByEmail("reader@mail.ru").get(),users.get(2));
        LOG.info("usersFindByEmail method ends.");
    }

    private void usersFindByLogin() {
        LOG.info("usersFindByLogin method's starting...");
        assertEquals(usersCrudRepository.findByUsername("admin").get(),users.get(0));
        assertEquals(usersCrudRepository.findByUsername("editor").get(),users.get(1));
        assertEquals(usersCrudRepository.findByUsername("reader").get(),users.get(2));
        LOG.info("usersFindByLogin method ends.");
    }

    private void usersFindAllByUserStatus() {
        LOG.info("usersFindAllByUserStatus method's starting...");
        assertEquals(usersCrudRepository.findAllByUserStatus(admin).size(),1);
        assertEquals(usersCrudRepository.findAllByUserStatus(editor).size(),1);
        assertEquals(usersCrudRepository.findAllByUserStatus(reader).get(0),users.get(2));
        LOG.info("usersFindAllByUserStatus method ends.");
    }

    private void usersStatusFindByUsers() {
        LOG.info("usersStatusFindByUsers method's starting...");
        assertEquals(userStatusCrudRepository.findByUsers(users.get(0)),admin);
        assertEquals(userStatusCrudRepository.findByUsers(users.get(1)),editor);
        assertEquals(userStatusCrudRepository.findByUsers(users.get(2)),reader);
        LOG.info("usersStatusFindByUsers method ends.");
    }

    private void usersDeleteById() {
        usersCrudRepository.deleteById(users.get(0).getId());
        assertFalse(usersCrudRepository.findById(users.get(0).getId()).isPresent());
    }

    private void usersDelete() {
        usersCrudRepository.delete(users.get(1));
        assertFalse(usersCrudRepository.findById(users.get(1).getId()).isPresent());
    }

    private void usersDeleteAll() {
        usersCrudRepository.deleteAll();
        assertEquals(usersCrudRepository.findAll().size(),0);
    }

    /**
     * Test.
     */
    @Test
    public void test() {
        LOG.info("Test method's starting...");

        initUserStatus();
        initUsers();
        initCraftOrAlchemy();
        initDescriptionComponent();
        initCategoryComponent();
        initComponent();
        initDescriptionThing();
        initTypeThing();
        initThing();
        initEffectThing();
        initDraft();

        categoryComponentFindById();
        categoryComponentFindByName();
        categoryComponentCount();

        componentFindByName();
        componentFindAllByCategoryComponentEquals();
        componentFindAllByDraftsContains();

        descriptionComponentFindByComponent();

        descriptionThingFindByThing();

        draftFindAllByComponentsContains();
        draftFindAllByThing();

        effectThingFindAllByName();
        effectThingFindAllByThing();

        thingFindByDraftsContains();
        thingFindAllByTypeThing();

        typeThingFindByName();
        typeThingFindByThingsContains();

        usersFindByEmail();
        usersFindByLogin();
        usersFindAllByUserStatus();

        usersStatusFindByUsers();

        usersDeleteById();
        usersDelete();
        usersDeleteAll();

        LOG.info("Test method ends.");
    }

    /**
     * Log end.
     */
    @AfterClass
    public static void logEnd() {
        LOG.info("Test ends.");
        fileHandler.flush();
        fileHandler.close();
    }
}
