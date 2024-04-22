package com.github.enteraname74.musik.controller.utils;

import com.github.enteraname74.musik.domain.utils.ServiceMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Utils for the controllers.
 */
public class ControllerUtils {

    /**
     * Unauthorized response for a request if a user is not authenticated.
     */
    public static final ResponseEntity<?> UNAUTHORIZED_RESPONSE = new ResponseEntity<>(
            ServiceMessages.USER_NOT_AUTHENTICATED,
            HttpStatus.FORBIDDEN
    );
}
