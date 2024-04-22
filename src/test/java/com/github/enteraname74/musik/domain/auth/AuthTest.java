package com.github.enteraname74.musik.domain.auth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class AuthTest {

    @Test
    void givenPassword_whenHashing_thenShouldBeCheckable() {
        HashFactory factory = new ShaHashFactoryImpl();
        String password = "xX_sussyBakaAmoong_Xx";

        Optional<HashedPassword> hashedPassword = factory.toHashedPassword(password);

        Assert.isTrue(hashedPassword.isPresent(), "A hashed password should have been made.");

        PasswordVerification passwordVerification = new ShaPasswordVerificationImpl();

        boolean isPasswordMatchingHash = passwordVerification.isMatching(password, hashedPassword.get());

        Assert.state(isPasswordMatchingHash, "The password should match the hash");
    }
}
