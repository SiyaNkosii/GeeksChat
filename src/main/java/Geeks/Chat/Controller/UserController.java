package Geeks.Chat.Controller;

import Geeks.Chat.DataTransfere.ForgotPasswordRequest;
import Geeks.Chat.DataTransfere.UserLoginRequest;
import Geeks.Chat.DataTransfere.UserRegistrationRequest;
import Geeks.Chat.Services.UserService;
import Geeks.Chat.entity.User;
import Geeks.Chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/GeeksChat/users")
public class UserController{

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationRequest request){
        userService.registerUser(request.getUsername(), request.getEmail(), request.getPassword());
        return  ResponseEntity.ok("Successfully registered");
    }
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody UserLoginRequest request)
    {
        User user= userService.loginUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> resetPassword(@RequestBody ForgotPasswordRequest request) {
        userService.resetPassword(request.getEmail(), request.getNewPassword());
        return ResponseEntity.ok("Password reset successfully");
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users= userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
