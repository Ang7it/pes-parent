package com.pespurse.users.web.controller;

import com.pespurse.global.response.Response;
import com.pespurse.players.web.dto.UserLoginResponseDTO;
import com.pespurse.users.web.dto.UserDTO;
import com.pespurse.users.web.handler.UserHandler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/user/v1/")
public class UserController {

    private final UserHandler userHandler;

    @PostMapping("register")
    public Response<UserDTO> newUserRegistration(@RequestBody UserDTO userDTO) {
        return userHandler.handleNewUserRegistration(userDTO);
    }

    @PostMapping("login")
    public Response<UserLoginResponseDTO> loginUser(@RequestHeader Long userId) {
        return userHandler.handleUserLogin(userId);
    }

    @GetMapping("getById")
    public Response<UserDTO> getUserById(@RequestHeader Long id) {
        return userHandler.getUserById(id);
    }

    @PutMapping("update")
    public Response<UserDTO> updateUser(@RequestHeader Long userId, @RequestBody UserDTO userDTO) {
        return userHandler.handleUserUpdate(userId, userDTO);
    }

    @PostMapping("/internal/create/{count}")
    public Response<List<UserDTO>> createDummyData(@PathVariable String count) {
        return userHandler.createDummyUsersForTest(count);
    }

}
