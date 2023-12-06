package Geeks.Chat.Controller;

import Geeks.Chat.DataTransfere.ForgotPasswordRequest;
import Geeks.Chat.DataTransfere.UserLoginRequest;
import Geeks.Chat.DataTransfere.UserRegistrationRequest;
import Geeks.Chat.Response.ApiResponse;
import Geeks.Chat.Response.LoginResponse;
import Geeks.Chat.Services.UserService;
import Geeks.Chat.Services.UserServiceimpl;
import Geeks.Chat.entity.User;
import Geeks.Chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/GeeksChat/users")
@CrossOrigin(origins = "http://localhost:4200")

public class UserController{

    private UserServiceimpl userService;

    public UserController() {

    }

    @Autowired
    public UserController(UserServiceimpl userService, UserRepository userRepository) {
        this.userService = userService;
    }
    @CrossOrigin(origins = "http://localhost:4200")

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegistrationRequest request) {
        boolean isRegistered = userService.registerUser(request);
        if (isRegistered) {
            return ResponseEntity.ok(new ApiResponse("Successfully registered"));
        } else {
            return ResponseEntity.badRequest().body(new ApiResponse("Failed to register"));
        }
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody UserLoginRequest request) {
        LoginResponse loginResponse = userService.loginUser(request.getEmail(), request.getPassword());
        if (loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        } else {
            return ResponseEntity.badRequest().body(new LoginResponse());
        }
    }

    @PostMapping("/forgot-password")
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
