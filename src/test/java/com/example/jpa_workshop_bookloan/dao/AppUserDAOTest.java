package com.example.jpa_workshop_bookloan.dao;

import com.example.jpa_workshop_bookloan.entity.AppUser;
import com.example.jpa_workshop_bookloan.entity.Details;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@DirtiesContext
@Transactional
class AppUserDAOTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AppUserDAO appUserDAO;
    private AppUser testObject;

    public List<AppUser> appUsers(){
        return Arrays.asList(
                new AppUser("roudabeh","abc123",
                        new Details("rod@gmail.com","Roudabeh Ad", LocalDate.parse("1989-05-01")))
                ,new AppUser("soheil", "pass147",
                        new Details("Soheil Kei", "soheil@gamil.com", LocalDate.parse("1989-06-26")))
        );
    }

    @BeforeEach
    void setUp() {
        List<AppUser> persistedAppUsers =
                appUsers().stream()
                        .map(entityManager::persist)
                        .collect(Collectors.toList());

        testObject = persistedAppUsers.get(0);
    }

    @Test
    void findById() {
        AppUser found = appUserDAO.findById(testObject.getAppUserId());
        assertNotNull(found);
    }

    @Test
    void findAll() {
        Collection<AppUser> found = appUserDAO.findAll();
        assertEquals(2,found.size());

    }

    @Test
    void create() {
        AppUser persistUser = new AppUser("test","passTest",null);
        AppUser app = appUserDAO.create(persistUser);
        assertNotNull(app);
    }

    @Test
    void update() {

    }

    @Test
    void delete() {
        assertNotNull(entityManager.find(AppUser.class, testObject.getAppUserId()));

        appUserDAO.delete(testObject.getAppUserId());

        assertNull(entityManager.find(AppUser.class, testObject.getAppUserId()));
    }
}