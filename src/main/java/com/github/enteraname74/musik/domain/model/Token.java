package com.github.enteraname74.musik.domain.model;

import com.github.enteraname74.musik.domain.utils.IdGenerator;

import java.time.LocalDateTime;

/**
 * Represent a Token to use for requests.
 *
 * @param token the token.
 * @param maxDate the max validity date of the tokeN;
 */
public record Token(String token, String maxDate) {
    public Token() {
        this(
                IdGenerator.generateRandomId(),
                LocalDateTime.now().plusHours(1).toString()
        );
    }
}
