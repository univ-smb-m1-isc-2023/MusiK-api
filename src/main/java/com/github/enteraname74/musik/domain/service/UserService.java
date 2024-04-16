package com.github.enteraname74.musik.domain.service;

import com.github.enteraname74.musik.domain.model.User;
import com.github.enteraname74.musik.domain.model.ViewableUser;
import com.github.enteraname74.musik.domain.utils.ServiceResult;

import java.util.List;

/**
 * Service used to manage Users.
 */
public interface UserService {
    /**
     * Retrieves all Users.
     * If a problem occurs, return an empty list.
     *
     * @return a list containing all Users as ViewableUsers.
     */
    List<ViewableUser> getAll();

    /**
     * Retrieves a User from its name.
     *
     * @param name the name of the User to retrieve.
     * @return a ServiceResult, holding the found User as a ViewableUser or an error.
     */
    ServiceResult<?> getById(String name);

    /**
     * Save a new User.
     *
     * @param name the name of the user (unique) to save.
     * @param password the password of the User (will be saved as a hash + salt).
     * @return a ServiceResult, holding the saved User or an error.
     */
    ServiceResult<?> save(String name, String password);

    /**
     * Delete a User from its name.
     *
     * @param name the name of the User to delete.
     * @return a ServiceResult, holding the response of the request or an error.
     */
    ServiceResult<?> deleteById(String name);

    /**
     * Check if a User is an admin.
     *
     * @param name the name of the User to checK.
     * @return true if the User is an admin, false if not.
     */
    boolean isAdmin(String name);
}
