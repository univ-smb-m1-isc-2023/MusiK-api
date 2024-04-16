package com.github.enteraname74.musik.domain.model;

import com.github.enteraname74.musik.domain.auth.HashedPassword;

import java.util.Objects;


/**
 * Represent a user of the application.
 */
public class User {

    private String name;
    private HashedPassword hashedPassword;
    private boolean isAdmin;

    public User(String name, HashedPassword hashedPassword, boolean isAdmin) {
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.isAdmin = isAdmin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashedPassword getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(HashedPassword hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    /**
     * Convert a User to a ViewableUser.
     *
     * @return the representation of a User as a ViewableUser.
     */
    public ViewableUser toViewableUser() {
        return new ViewableUser(name, isAdmin);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isAdmin == user.isAdmin && Objects.equals(name, user.name) && Objects.equals(hashedPassword, user.hashedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hashedPassword, isAdmin);
    }
}
