package com.github.enteraname74.musik.infrastructure.model;

import com.github.enteraname74.musik.domain.auth.HashedPassword;
import com.github.enteraname74.musik.domain.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "AppUser")
public class PostgresUserEntity {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "hash", nullable = false)
    private byte[] hash;

    @Column(name = "salt", nullable = false)
    private byte[] salt;

    @Column(name = "isAdmin", nullable = false)
    private boolean isAdmin;

    public PostgresUserEntity(String id, byte[] hash, byte[] salt, boolean isAdmin) {
        this.id = id;
        this.hash = hash;
        this.salt = salt;
        this.isAdmin = isAdmin;
    }

    public PostgresUserEntity() {
        this(
                "",
                new byte[16],
                new byte[16],
                false
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * Convert a User to a PostgresUserEntity.
     *
     * @param user the User to convert.
     * @return the representation of a User as a PostgresUserEntity.
     */
    public static PostgresUserEntity toPostgresUserEntity(
            User user
    ) {
        return new PostgresUserEntity(
                user.getName(),
                user.getHashedPassword().hash(),
                user.getHashedPassword().salt(),
                user.isAdmin()
        );
    }

    /**
     * Convert a PostgresUser to a User.
     *
     * @return the representation of a PostgresUser as a User.
     */
    public User toUser() {
        return new User(
                id,
                new HashedPassword(
                        hash,
                        salt
                ),
                isAdmin
        );
    }
}
