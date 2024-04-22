package com.github.enteraname74.musik.infrastructure.daoimpl;

import com.github.enteraname74.musik.domain.auth.HashedPassword;
import com.github.enteraname74.musik.domain.dao.UserDao;
import com.github.enteraname74.musik.domain.model.User;
import com.github.enteraname74.musik.infrastructure.jpa.PostgresUserJpa;
import com.github.enteraname74.musik.infrastructure.model.PostgresUserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserDaoTests {

    private UserDao userDao;

    @MockBean
    private PostgresUserJpa userJpa;
    ArrayList<PostgresUserEntity> allUsers;

    private void initUserJpa() {
        Mockito.when(userJpa.findAll()).thenReturn(allUsers);
        Mockito.when(userJpa.findById(any(String.class))).thenAnswer(i -> {
            String id = (String) i.getArguments()[0];
            return allUsers.stream().filter(
                    user -> user.getName().equals(id)
            ).findFirst();
        });
        Mockito.when(userJpa.save(any(PostgresUserEntity.class))).thenAnswer(i -> {
            PostgresUserEntity user = (PostgresUserEntity) i.getArguments()[0];
            allUsers.add(user);
            return user;
        });
        Mockito.doAnswer(invocationOnMock -> {
            Object[] arguments = invocationOnMock.getArguments();
            if (arguments != null && arguments.length == 1 && arguments[0] != null) {
                String id = (String) arguments[0];
                allUsers = new ArrayList<>(allUsers.stream().filter(user -> !user.getName().equals(id)).toList());
            }
            return null;
        }).when(userJpa).deleteById(any(String.class));
    }

    @BeforeEach
    public void init() {
        userDao = new PostgresUserDaoImpl(userJpa);

        PostgresUserEntity firstUser = new PostgresUserEntity("1", new byte[16], new byte[16], false);
        PostgresUserEntity secondUser = new PostgresUserEntity("2", new byte[16], new byte[16], false);

        allUsers = new ArrayList<>(List.of(firstUser, secondUser));

        initUserJpa();
    }

    @Test
    public void givenCorrectUserId_whenGetById_thenFoundUser() {
        String correctId = "1";
        Optional<User> foundUser = userDao.getById(correctId);

        Assert.isTrue(foundUser.isPresent(), "The id should have returned a found user");
    }

    @Test
    public void givenWrongUserId_whenGetById_thenFoundNothing() {
        String wrongId = "45";
        Optional<User> foundUser = userDao.getById(wrongId);

        Assert.isTrue(foundUser.isEmpty(), "The id should have returned nothing");
    }

    @Test
    public void givenUsers_whenGetAll_thenRetrieveAllUsers() {
        List<User> result = userDao.getAll();

        Assert.isTrue(result.size() == 2, "All users were not retrieved");
    }

    @Test
    public void givenCorrectUserId_whenDeleteById_thenShouldBeDeleted() {
        userDao.deleteById("1");
        Optional<User> foundUser = userDao.getById("1");

        Assert.isTrue(foundUser.isEmpty(), "The deleted user should not be found");
    }

    @Test
    public void givenWrongUserId_whenDeleteById_thenNothingChange() {
        userDao.deleteById("45");

        List<User> allUsers = userDao.getAll();

        Assert.isTrue(allUsers.size() == 2, "A user was deleted, it should not be the case");
    }

    @Test
    public void givenNewUser_whenAddingUser_thenUserAddedInAllUsers() {
        User newUser = new User("1", new HashedPassword(new byte[16], new byte[16]), false);
        User result = userDao.upsert(newUser);

        Assert.isTrue(newUser.equals(result), "The element should not be altered after being saved");

        List<User> allUsers = userDao.getAll();
        Assert.isTrue(allUsers.size() == 3, "The user was not added to all users");
    }
}