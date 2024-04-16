package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.domain.auth.HashFactory;
import com.github.enteraname74.musik.domain.auth.HashedPassword;
import com.github.enteraname74.musik.domain.model.User;
import com.github.enteraname74.musik.domain.model.ViewableUser;
import com.github.enteraname74.musik.domain.repository.UserRepository;
import com.github.enteraname74.musik.domain.service.UserService;
import com.github.enteraname74.musik.domain.utils.ServiceMessages;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the UserService.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final HashFactory hashFactory;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, HashFactory hashFactory) {
        this.userRepository = userRepository;
        this.hashFactory = hashFactory;
    }

    @Override
    public List<ViewableUser> getAll() {
        return userRepository.getAll().stream().map(User::toViewableUser).toList();
    }

    @Override
    public ServiceResult<?> getById(String name) {
        if (!userRepository.doesElementExists(name)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_USER_ID
            );
        }


        return new ServiceResult<>(
                HttpStatus.OK,
                userRepository.getById(name).map(User::toViewableUser)
        );
    }

    @Override
    public ServiceResult<?> save(String name, String password) {
        // The name of the user should be unique
        if (userRepository.doesElementExists(name)) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.USER_NAME_ALREADY_TAKEN
            );
        }


        Optional<HashedPassword> hashedPassword = hashFactory.toHashedPassword(password);

        if (hashedPassword.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.USER_CANNOT_BE_SAVED
            );
        }

        User userToSave = new User(name, hashedPassword.get(), false);

        return new ServiceResult<>(
                HttpStatus.CREATED,
                userRepository.save(userToSave).toViewableUser()
        );
    }

    @Override
    public ServiceResult<?> deleteById(String name) {
        if (!userRepository.doesElementExists(name)) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_USER_ID
            );
        }

        userRepository.deleteById(name);

        return new ServiceResult<>(
                HttpStatus.OK,
                ServiceMessages.USER_DELETED
        );
    }

    @Override
    public boolean isAdmin(String name) {
        Optional<User> foundUser = userRepository.getById(name);

        return foundUser.map(User::isAdmin).orElse(false);
    }
}
