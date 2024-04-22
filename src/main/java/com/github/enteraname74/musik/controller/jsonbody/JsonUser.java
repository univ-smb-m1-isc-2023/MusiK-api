package com.github.enteraname74.musik.controller.jsonbody;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Body of REST request for creating a user.
 */
public class JsonUser {
    @JsonProperty("name")
    private String name;

    @JsonProperty("password")
    private String password;

    public JsonUser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Empty constructor for Jackson.
     */
    public JsonUser() {
        this(
                "",
                ""
        );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
