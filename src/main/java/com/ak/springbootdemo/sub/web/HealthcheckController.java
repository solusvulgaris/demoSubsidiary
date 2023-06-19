package com.ak.springbootdemo.sub.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
class HealthcheckController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/healthcheck")
    public Map<String, String> healthcheck(
            @RequestParam(value = "format", required = false) String format) {
        if (Objects.equals(format, "short")) {
            return Collections.singletonMap("status", "OK");
        } else if ("full".equals(format)) {
            String timeNow = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("status", "OK");
            hashMap.put("currentTime", timeNow);

            return hashMap;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    /*
        @PostMapping("/students")
        public ResponseEntity<Student> postStudent(
                @RequestBody Student student) {

            log.info("Request to create student: {}", student);
            Student newStudent = service.addNewStudent(student);
            return new ResponseEntity<>(student, HttpStatus.CREATED);
        }
    */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/healthcheck-with-response-entity")
    //, produces = MediaType.APPLICATION_JSON_VALUE)
    //, produces = { "application/json" })
    public ResponseEntity<Map<String, String>> healthcheckwithresponseentity(
            @RequestParam(value = "format", required = false) String format) {
        if (Objects.equals(format, "short")) {
            //HttpHeaders headers = new HttpHeaders();
            //headers.add("Content-Type", "application/json");
            //headers.setContentType(MediaType.APPLICATION_JSON);
            //headers.add("status", "OK");

            //return ResponseEntity.ok(gson.toJson("status", "OK"));
            //return ResponseEntity.status(HttpStatus.OK).body("status", "OK");
            //return new ResponseEntity<>("Custom header set", headers, HttpStatus.OK);
            return new ResponseEntity<>(Collections.singletonMap("status", "OK"), HttpStatus.OK);

        } else if ("full".equals(format)) {
            String timeNow = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);

            //return ResponseEntity.ok();
            //return ResponseEntity.status(HttpStatus.OK).body("currentTime", timeNow);
            //return new ResponseEntity<>("Custom header set", headers, HttpStatus.OK);

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("status", "OK");
            hashMap.put("currentTime", timeNow);

            return new ResponseEntity<>(hashMap, HttpStatus.OK);
        } else {
            //throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) //405
    @PutMapping(value = "/healthcheck")
    public void healthcheckPut() {

    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) //405
    @PostMapping(value = "/healthcheck")
    public void healthcheckPost() {

    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED) //405
    @DeleteMapping(value = "/healthcheck")
    public void healthcheckDelete() {

    }

}
