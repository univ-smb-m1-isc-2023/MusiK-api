package com.github.enteraname74.musik.infrastructure.repositoryimpl;

import com.github.enteraname74.musik.domain.dao.UserDao;
import com.github.enteraname74.musik.domain.model.User;
import com.github.enteraname74.musik.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserRepository for a Postgres database.
 */
@Component
public class PostgresUserRepositoryImpl implements UserRepository {
    private final UserDao dao;

    @Autowired
    public PostgresUserRepositoryImpl(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public List<User> getAll() {
        return dao.getAll();
    }

    @Override
    public Optional<User> getById(String id) {
        return dao.getById(id);
    }

    @Override
    public User save(User element) {
        return dao.upsert(element);
    }

    @Override
    public void deleteById(String id) {
        dao.deleteById(id);
    }

    @Override
    public boolean doesElementExists(String id) {
        return dao.getById(id).isPresent();
    }
}
