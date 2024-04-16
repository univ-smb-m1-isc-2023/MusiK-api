package com.github.enteraname74.musik.domain.serviceimpl;

import com.github.enteraname74.musik.domain.auth.PasswordVerification;
import com.github.enteraname74.musik.domain.model.Token;
import com.github.enteraname74.musik.domain.model.User;
import com.github.enteraname74.musik.domain.repository.TokenRepository;
import com.github.enteraname74.musik.domain.repository.UserRepository;
import com.github.enteraname74.musik.domain.service.AuthService;
import com.github.enteraname74.musik.domain.utils.ServiceMessages;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of the AuthService.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordVerification passwordVerification;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthServiceImpl(PasswordVerification passwordVerification, TokenRepository tokenRepository, UserRepository userRepository) {
        this.passwordVerification = passwordVerification;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }


    @Override
    public ServiceResult<?> authenticateUser(String name, String password) {
        Optional<User> user = userRepository.getById(name);

        if (user.isEmpty()) {
            return new ServiceResult<>(
                    HttpStatus.NOT_FOUND,
                    ServiceMessages.WRONG_USER_ID
            );
        }

        User foundUser = user.get();

        boolean isPasswordCorrect = passwordVerification.isMatching(password, foundUser.getHashedPassword());

        if (!isPasswordCorrect) {
            return new ServiceResult<>(
                    HttpStatus.BAD_REQUEST,
                    ServiceMessages.CANNOT_AUTHENTICATE_USER
            );
        }

        Token userToken = new Token();

        return new ServiceResult<>(
                HttpStatus.OK,
                userToken
        );
    }

    @Override
    public boolean isUserAuthenticated(String token) {
        return tokenRepository.isTokenValid(token);
    }

    @Override
    public void incrementTokenLife(String token) {
        tokenRepository.incrementTokenLife(token);
    }
}
