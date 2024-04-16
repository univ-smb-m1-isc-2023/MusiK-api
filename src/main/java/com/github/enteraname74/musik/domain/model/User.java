package com.github.enteraname74.musik.domain.model;

import com.github.enteraname74.musik.domain.auth.HashedPassword;


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
}
