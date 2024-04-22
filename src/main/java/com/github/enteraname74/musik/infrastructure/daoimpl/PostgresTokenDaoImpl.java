package com.github.enteraname74.musik.infrastructure.daoimpl;

import com.github.enteraname74.musik.domain.dao.TokenDao;
import com.github.enteraname74.musik.domain.model.Token;
import com.github.enteraname74.musik.infrastructure.jpa.PostgresTokenJpa;
import com.github.enteraname74.musik.infrastructure.model.PostgresTokenEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the MusicDao using a JpaRepository.
 */
@Component
public class PostgresTokenDaoImpl implements TokenDao {

    private final PostgresTokenJpa tokenJpa;

    @Autowired
    public PostgresTokenDaoImpl(PostgresTokenJpa tokenJpa) {
        this.tokenJpa = tokenJpa;
    }


    @Override
    public Optional<Token> getById(String id) {
        return tokenJpa.findById(id).map(PostgresTokenEntity::toToken);
    }

    @Override
    public List<Token> getAll() {
        return tokenJpa.findAll().stream().map(PostgresTokenEntity::toToken).toList();
    }

    @Override
    public Token upsert(Token element) {
        return tokenJpa.save(PostgresTokenEntity.toPostgresTokenEntity(element)).toToken();
    }

    @Override
    public void deleteById(String id) {
        tokenJpa.deleteById(id);
    }
}
