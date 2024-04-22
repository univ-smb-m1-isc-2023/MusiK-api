package com.github.enteraname74.musik.domain.model;

/**
 * Viewable user to send as result of requests.
 *
 * @param name the name the user.
 * @param isAdmin if it is an admin or not.
 */
public record ViewableUser(String name, boolean isAdmin) {
}
