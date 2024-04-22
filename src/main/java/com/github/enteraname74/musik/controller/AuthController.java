package com.github.enteraname74.musik.controller;

import com.github.enteraname74.musik.controller.jsonbody.JsonUser;
import com.github.enteraname74.musik.domain.service.AuthService;
import com.github.enteraname74.musik.domain.utils.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticate a user.
     *
     * @param user the user to authenticate.
     * @return a ResponseEntity, with the response of the request.
     */
    @PostMapping()
    public ResponseEntity<?> authenticate(
            @RequestBody JsonUser user
    ) {
        ServiceResult<?> result =  authService.authenticateUser(user.getName(), user.getPassword());

        return new ResponseEntity<>(result.getResult(), result.getHttpStatus());
    }
}
