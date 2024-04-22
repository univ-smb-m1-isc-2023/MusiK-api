package com.github.enteraname74.musik.infrastructure.daoimpl;

import com.github.enteraname74.musik.domain.dao.UserDao;
import com.github.enteraname74.musik.domain.model.User;
import com.github.enteraname74.musik.infrastructure.jpa.PostgresUserJpa;
import com.github.enteraname74.musik.infrastructure.model.PostgresUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the MusicDao using a JpaRepository.
 */
@Component
public class PostgresUserDaoImpl implements UserDao {

    private final PostgresUserJpa userJpa;

    @Autowired
    public PostgresUserDaoImpl(PostgresUserJpa userJpa) {
        this.userJpa = userJpa;
    }

    @Override
    public Optional<User> getById(String id) {
        return userJpa.findById(id).map(PostgresUserEntity::toUser);
    }

    @Override
    public List<User> getAll() {
        return userJpa.findAll().stream().map(PostgresUserEntity::toUser).toList();
    }

    @Override
    public User upsert(User element) {
        return userJpa.save(PostgresUserEntity.toPostgresUserEntity(element)).toUser();
    }

    @Override
    public void deleteById(String id) {
        userJpa.deleteById(id);
    }
}
