package com.github.enteraname74.musik.infrastructure.daoimpl;

import com.github.enteraname74.musik.domain.dao.TokenDao;
import com.github.enteraname74.musik.domain.model.Token;
import com.github.enteraname74.musik.infrastructure.jpa.PostgresTokenJpa;
import com.github.enteraname74.musik.infrastructure.model.PostgresTokenEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class TokenDaoTests {

    private TokenDao tokenDao;

    @MockBean
    private PostgresTokenJpa tokenJpa;
    ArrayList<PostgresTokenEntity> allTokens;

    private void initTokenJpa() {
        Mockito.when(tokenJpa.findAll()).thenReturn(allTokens);
        Mockito.when(tokenJpa.findById(any(String.class))).thenAnswer(i -> {
            String id = (String) i.getArguments()[0];
            return allTokens.stream().filter(
                    token -> token.getToken().equals(id)
            ).findFirst();
        });
        Mockito.when(tokenJpa.save(any(PostgresTokenEntity.class))).thenAnswer(i -> {
            PostgresTokenEntity token = (PostgresTokenEntity) i.getArguments()[0];
            allTokens.add(token);
            return token;
        });
        Mockito.doAnswer(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length == 1 && arguments[0] != null) {
                String id = (String) arguments[0];
                allTokens = new ArrayList<>(allTokens.stream().filter(token -> !token.getToken().equals(id)).toList());
            }
            return null;
        }).when(tokenJpa).deleteById(any(String.class));
    }

    @BeforeEach
    public void init() {
        tokenDao = new PostgresTokenDaoImpl(tokenJpa);

        PostgresTokenEntity firstToken = new PostgresTokenEntity("1", LocalDateTime.now().plusHours(1).toString());
        PostgresTokenEntity secondToken = new PostgresTokenEntity("2", LocalDateTime.now().plusHours(1).toString());

        allTokens = new ArrayList<>(List.of(firstToken, secondToken));

        initTokenJpa();
    }

    @Test
    public void givenCorrectTokenId_whenGetById_thenFoundToken() {
        String correctId = "1";
        Optional<Token> foundToken = tokenDao.getById(correctId);

        Assert.isTrue(foundToken.isPresent(), "The id should have returned a found token");
    }

    @Test
    public void givenWrongTokenId_whenGetById_thenFoundNothing() {
        String wrongId = "45";
        Optional<Token> foundToken = tokenDao.getById(wrongId);

        Assert.isTrue(foundToken.isEmpty(), "The id should have returned nothing");
    }

    @Test
    public void givenTokens_whenGetAll_thenRetrieveAllTokens() {
        List<Token> result = tokenDao.getAll();

        Assert.isTrue(result.size() == 2, "All tokens were not retrieved");
    }

    @Test
    public void givenCorrectTokenId_whenDeleteById_thenShouldBeDeleted() {
        tokenDao.deleteById("1");
        Optional<Token> foundToken = tokenDao.getById("1");

        Assert.isTrue(foundToken.isEmpty(), "The deleted token should not be found");
    }

    @Test
    public void givenWrongTokenId_whenDeleteById_thenNothingChange() {
        tokenDao.deleteById("45");

        List<Token> allTokens = tokenDao.getAll();

        Assert.isTrue(allTokens.size() == 2, "A token was deleted, it should not be the case");
    }

    @Test
    public void givenNewToken_whenAddingToken_thenTokenAddedInAllTokens() {
        Token newToken = new Token("2", LocalDateTime.now().plusHours(1).toString());
        Token result = tokenDao.upsert(newToken);

        Assert.isTrue(newToken.equals(result), "The element should not be altered after being saved");

        List<Token> allTokens = tokenDao.getAll();
        Assert.isTrue(allTokens.size() == 3, "The token was not added to all tokens");
    }
}
