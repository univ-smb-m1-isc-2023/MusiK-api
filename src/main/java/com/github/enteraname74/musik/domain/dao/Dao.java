package com.github.enteraname74.musik.domain.dao;

import java.util.List;
import java.util.Optional;

/**
 * Interface for managing basic CRUD operations on a type T.
 * @param <T> the element on which we do operations.
 */
public interface Dao<T> {

    /**
     * Tries to retrieve an element by its id.
     * @param id the id of the element we want to retrieve.
     * @return the potential found element.
     */
    Optional<T> getById(String id);

    /**
     * Retrieve all elements in the database.
     * @return a list containing all elements in the database.
     */
    List<T> getAll();

    /**
     * Insert or update an element to the database.
     * @param element the element to save / modify.
     */
    T upsert(T element);

    /**
     * Delete an element by its id.
     * @param id the id of the element to delete.
     */
    void deleteById(String id);
}