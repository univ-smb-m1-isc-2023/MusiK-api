package com.github.enteraname74.musik.infrastructure.model;

import com.github.enteraname74.musik.domain.model.Token;
import com.github.enteraname74.musik.domain.utils.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "Token")
public class PostgresTokenEntity {
    @Id
    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "maxDate", nullable = false)
    private String maxDate;

    public PostgresTokenEntity(String token, String maxDate) {
        this.token = token;
        this.maxDate = maxDate;
    }

    public PostgresTokenEntity() {
        this(
                IdGenerator.generateRandomId(),
                LocalDateTime.now().toString()
        );
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    /**
     * Convert a Token to a PostgresTokenEntity.
     *
     * @param token the Token to convert.
     * @return the representation of a Token as a PostgresTokenEntity.
     */
    public static PostgresTokenEntity toPostgresTokenEntity(
            Token token
    ) {
        return new PostgresTokenEntity(
                token.token(),
                token.maxDate()
        );
    }

    /**
     * Convert a PostgresUser to a Token.
     *
     * @return the representation of a PostgresUser as a Token.
     */
    public Token toToken() {
        return new Token(
                token,
                maxDate
        );
    }
}
