package com.github.enteraname74.musik.domain.repository;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing basic repository operations on a type T.
 * @param <T> the element on which we do operations.
 */
public interface Repository<T> {
    /**
     * Retrieves all elements.
     * If a problem occurs, return an empty list.
     *
     * @return a list containing all elements.
     */
    List<T> getAll();

    /**
     * Retrieves an element from its id.
     *
     * @param id the id of the element to retrieve.
     * @return an optional element.
     */
    Optional<T> getFromId(String id);

    /**
     * Save a new element.
     *
     * @param element the element to save.
     * @return the saved element.
     */
    T save(T element);

    /**
     * Delete an element by its id.
     *
     * @param id the id of the element to delete.
     */
    void deleteById(String id);

    /**
     * Check if an element exists from its id.
     *
     * @param id the id of the element to check.
     * @return true if the element exists, false if not.
     */
    boolean doesElementExists(String id);
}
