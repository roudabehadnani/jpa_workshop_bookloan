package com.example.jpa_workshop_bookloan;

import com.example.jpa_workshop_bookloan.dao.*;
import com.example.jpa_workshop_bookloan.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;


@Transactional
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    public MyCommandLineRunner(BookDAO bookDAO, EntityManager entityManager, AppUserDAO appUserDAO,
                               AuthorDAO authorDAO, BookLoanDAO bookLoanDAO, DetailsDAO detailsDAO) {
        this.bookDAO = bookDAO;
        this.entityManager = entityManager;
        this.appUserDAO = appUserDAO;
        this.authorDAO = authorDAO;
        this.bookLoanDAO = bookLoanDAO;
        this.detailsDAO = detailsDAO;
    }


    private final BookDAO bookDAO;
    private final EntityManager entityManager;
    private final AppUserDAO appUserDAO;
    private final AuthorDAO authorDAO;
    private final BookLoanDAO bookLoanDAO;
    private final DetailsDAO detailsDAO;


    @Override
    public void run(String... args) throws Exception {

        AppUser appUser1 = appUserDAO.create(new AppUser("roudabeh","pass123",
                new Details("roudabeh@gmail.com", "Roudabeh Ad", LocalDate.parse("1989-05-01"))));
        AppUser appUser2 = appUserDAO.create(new AppUser("soheil","new456",
                new Details("soheil@gmail.com", "Soheil Kei", LocalDate.parse("1989-06-26"))));
        AppUser appUser3 = appUserDAO.create(new AppUser("erik","abc123",
                new Details("erik@gmail.com", "Erik Sven", LocalDate.parse("1970-10-01"))));


        BookLoan bookLoan1 = bookLoanDAO.create(new BookLoan(LocalDate.now(),LocalDate.now().plusDays(30),false));
        BookLoan bookLoan2 = bookLoanDAO.create(new BookLoan(LocalDate.now(),LocalDate.now().plusDays(45),false));
        BookLoan bookLoan3 = bookLoanDAO.create(new BookLoan(LocalDate.now(),LocalDate.now().plusDays(100),false));
        BookLoan bookLoan4 = bookLoanDAO.create(new BookLoan(LocalDate.now(),LocalDate.now().plusDays(60),false));

        appUser1.addBookLoan(bookLoan1);
        appUser1.addBookLoan(bookLoan4);
        appUser2.addBookLoan(bookLoan2);
        appUser3.addBookLoan(bookLoan3);

        Book book1 = bookDAO.create(new Book("isbn123","Java Advance",35));
        Book book2 = bookDAO.create(new Book("isbn567","Java Fundamental",60));
        Book book3 = bookDAO.create(new Book("isbn189","Java Basic",100));

        Author author1 = authorDAO.create(new Author("David","Johanson"));
        Author author2 = authorDAO.create(new Author("Erik","Klarson"));

        author1.addBook(book1);
        author1.addBook(book3);
        author2.addBook(book2);

        entityManager.flush();

        System.out.println("-----Print AppUser-----");
        appUserDAO.findAll().forEach(System.out::println);
















    }
}
