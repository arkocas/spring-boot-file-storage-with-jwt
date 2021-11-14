package com.alirizakocas.filestorage.controller;

import com.alirizakocas.filestorage.dto.UserDTO;
import com.alirizakocas.filestorage.message.ResponseMessage;
import com.alirizakocas.filestorage.model.User;
import com.alirizakocas.filestorage.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "User Api controller documentation")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    @ApiOperation(value = "New User adding method")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user){
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("User created."));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "User get method")
    public UserDTO getUserById(@PathVariable long id){
        User userDetails = userService.getUserById(id);
        UserDTO userDTO = new UserDTO(userDetails.getUsername());
        return userDTO;
    }

}
