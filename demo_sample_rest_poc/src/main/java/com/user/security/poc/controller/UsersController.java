package com.user.security.poc.controller;

import com.user.security.poc.dto.UserTo;
import com.user.security.poc.service.user.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@OpenAPIDefinition(info = @Info(description = "API's to manage users"))
@Slf4j
public class UsersController {

    @Autowired
    private UserService userService;

    @Operation(description= "API to create users")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo createUser(@RequestBody UserTo userTo) {
        log.info("Creating user with name {}", userTo.getUsername());
        return userService.createUser(userTo);
    }

    @PatchMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserTo updateUserById(@Parameter(required = true, description = "UserID to fetch details", example = "100") @PathVariable(name = "userId") Long useId,
                                 @RequestBody UserTo userTo) {
        log.info("Getting user info by id {}", useId);
        return userService.updateUser(useId, userTo);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserTo getUserById(@Parameter(required = true, description = "UserID to fetch details", example = "100") @PathVariable(name = "userId") Long useId) {
        log.info("Getting user info by id {}", useId);
        return userService.getUserById(useId);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PageableAsQueryParam
    public List<UserTo> getAllUsers(@Parameter(hidden = true) Pageable pageable) {
        log.info("Getting all user details");
        return userService.getAllUsers(pageable);
    }
}
