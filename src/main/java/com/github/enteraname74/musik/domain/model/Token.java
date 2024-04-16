package com.github.enteraname74.musik.domain.model;

import com.github.enteraname74.musik.domain.utils.IdGenerator;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token1 = (Token) o;
        return Objects.equals(token, token1.token) && Objects.equals(maxDate, token1.maxDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, maxDate);
    }
}
