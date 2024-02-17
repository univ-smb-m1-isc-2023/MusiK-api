package com.github.enteraname74.musik.domain.utils;

import org.springframework.http.HttpStatus;

/**
 * Handle result for services.
 *
 * @param <T> the type of the result that should be sent as the response of a request.
 */
public class ServiceResult<T> {
    private HttpStatus httpStatus;
    private T result;

    public ServiceResult(HttpStatus httpStatus, T result) {
        this.httpStatus = httpStatus;
        this.result = result;
    }

    /**
     * Retrieves the HttpStatus of the ServiceResult.
     *
     * @return the HttpStatus of the ServiceResult.
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * Set a new HttpStatus to the ServiceResult.
     *
     * @param httpStatus the new HttpStatus of the ServiceResult.
     */
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * Retrieves the result of the ServiceResult.
     *
     * @return the result of the ServiceResult.
     */
    public T getResult() {
        return result;
    }

    /**
     * Set a new result to the ServiceResult.
     *
     * @param result the new result of the ServiceResult.
     */
    public void setResult(T result) {
        this.result = result;
    }
}
