package com.github.enteraname74.musik.infrastructure.repositoryimpl;

import com.github.enteraname74.musik.domain.dao.TokenDao;
import com.github.enteraname74.musik.domain.model.Token;
import com.github.enteraname74.musik.domain.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the TokenRepository for a Postgres database.
 */
@Component
public class PostgresTokenRepositoryImpl implements TokenRepository {

    private final TokenDao dao;

    @Autowired
    public PostgresTokenRepositoryImpl(TokenDao dao) {
        this.dao = dao;
    }


    @Override
    public List<Token> getAll() {
        clearInvalidTokens();
        return dao.getAll();
    }

    @Override
    public Optional<Token> getById(String id) {
        clearInvalidTokens();
        return dao.getById(id);
    }

    @Override
    public Token save(Token element) {
        clearInvalidTokens();
        return dao.upsert(element);
    }

    @Override
    public void deleteById(String id) {

        dao.deleteById(id);
        clearInvalidTokens();
    }

    @Override
    public boolean doesElementExists(String id) {
        return dao.getById(id).isPresent();
    }

    @Override
    public boolean isTokenValid(String token) {
        Optional<Token> foundToken = dao.getById(token);

        if (foundToken.isEmpty()) return false;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tokenMaxDate = LocalDateTime.parse(foundToken.get().maxDate());

        return tokenMaxDate.isBefore(now);
    }

    @Override
    public void incrementTokenLife(String token) {
        Optional<Token> foundToken = dao.getById(token);

        if (foundToken.isEmpty()) return;
        Token userToken = foundToken.get();
        LocalDateTime tokenMaxDate = LocalDateTime.parse(userToken.maxDate());
        LocalDateTime newDate = tokenMaxDate.plusHours(1);

        dao.upsert(
                new Token(
                        userToken.token(),
                        newDate.toString()
                )
        );

        clearInvalidTokens();
    }

    @Override
    public void clearInvalidTokens() {
        List<Token> tokens = dao.getAll();

        tokens.forEach(token -> {
            if (!isTokenValid(token.token())) {
                dao.deleteById(token.token());
            }
        });
    }
}
