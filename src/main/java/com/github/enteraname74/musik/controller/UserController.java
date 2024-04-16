package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.controller.jsonbody.JsonUser;
import com.github.enteraname74.musik.domain.model.Playlist;
import com.github.enteraname74.musik.domain.model.ViewableUser;
import com.github.enteraname74.musik.domain.service.UserService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a user.
     *
     * @param user the user to create.
     * @return a ResponseEntity, with the response of the request.
     */
    @PostMapping()
    public ResponseEntity<?> add(
            @RequestBody JsonUser user
    ) {
        ServiceResult<?> result =  userService.save(user.getName(), user.getPassword());

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }

    /**
     * Retrieve all users.
     *
     * @return a list containing all users as viewable ones or an empty list if there is an error.
     */
    @GetMapping("/all")
    List<ViewableUser> getAll() { return userService.getAll(); }

    /**
     * Retrieves a User from its name.
     *
     * @param name the name of the User to retrieve.
     * @return a ResponseEntity, with the found User as a viewable one or an error.
     */
    @GetMapping("/{name}")
    ResponseEntity<?> get(
            @PathVariable String name
    ) {
        ServiceResult<?> result = userService.getById(name);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }


    /**
     * Delete a User.
     *
     * @param name the name of the User to delete.
     * @return a ResponseEntity, with the result of the request.
     */
    @DeleteMapping("/{name}")
    ResponseEntity<?> delete(
            @PathVariable String name
    ) {
        ServiceResult<?> result = userService.deleteById(name);

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }
}
