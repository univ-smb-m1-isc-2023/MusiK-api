package com.github.enteraname74.musik.domain.auth;

/**
 * Information about a hashed password.
 *
 * @param hash the hash of a password.
 * @param salt the salt used to generate the hash.
 */
public record HashedPassword(byte[] hash, byte[] salt) {}
