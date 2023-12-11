package Geeks.Chat.controller;

import Geeks.Chat.requestPayloads.ForgotPasswordRequest;
import Geeks.Chat.requestPayloads.UserLoginRequest;
import Geeks.Chat.requestPayloads.UserRegistrationRequest;
import Geeks.Chat.responsePayloads.ApiResponse;
import Geeks.Chat.responsePayloads.LoginResponse;
import Geeks.Chat.service.serviceImpl.UserServiceimpl;
import Geeks.Chat.entity.User;
import Geeks.Chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class UserController{

    @Autowired
    private UserServiceimpl userService;


   // @CrossOrigin(origins = "http://localhost:4200")

    @PostMapping(value ="/users/register", consumes = "application/json" ,produces = "application/json")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegistrationRequest request) {
        boolean isRegistered = userService.registerUser(request);
        if (isRegistered) {
            return ResponseEntity.ok(new ApiResponse("Successfully registered"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse("Failed to register"));
        }
    }

    @PostMapping(value ="/users/login", consumes = "application/json" ,produces = "application/json")    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserLoginRequest request) {
        LoginResponse loginResponse = userService.loginUser(request.getEmail(), request.getPassword());
        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.badRequest().body(new LoginResponse());
        }
    }

    @PostMapping(value ="/users/forgot-password", consumes = "application/json" ,produces = "application/json")
    public ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordRequest request) {
        userService.resetPassword(request.getEmail(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }
    @GetMapping("/searchUsers/{username}")
    public ResponseEntity<List<User>> searchUsers(@PathVariable String username){
        List<User> foundUsers = userService.searchUsers(username);
        return  ResponseEntity.ok(foundUsers);
    }

    /*
    @PostMapping("/{loggedInUserId}/addContact/{contactUserId}")
    public void addContact(@PathVariable String loggedInUserId, @PathVariable String contactUserId)
    {
        userService.addContact(Long.parseLong(loggedInUserId) , Long.parseLong(contactUserId));
    }
     */

//    @GetMapping("/{loggedInUserId}/contacts")
//    public ResponseEntity<List<User>> ge
}
