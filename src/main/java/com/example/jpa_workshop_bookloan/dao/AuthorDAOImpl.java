package com.example.jpa_workshop_bookloan.dao;

import com.example.jpa_workshop_bookloan.entity.Author;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public class AuthorDAOImpl implements AuthorDAO{
    @PersistenceContext
    EntityManager entityManager;
    @Override
    @Transactional(readOnly = true)
    public Author findById(Integer id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Author> findAll() {
        return entityManager.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Author create(Author author) {
        entityManager.persist(author);
        return author ;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Author update(Author author) {
        return entityManager.merge(author);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void delete(Integer id) {
        entityManager.remove(findById(id));

    }
}
