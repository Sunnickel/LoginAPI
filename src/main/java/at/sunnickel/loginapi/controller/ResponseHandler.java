package at.sunnickel.loginapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {

    /**
     * Generates a Error Response
     *
     * @param message the Error message
     * @param status  the status code
     *
     * @return a response in the form of a map
     */
    public static ResponseEntity<Map<String, Object>> generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());

        return new ResponseEntity<>(map, status);
    }
}
