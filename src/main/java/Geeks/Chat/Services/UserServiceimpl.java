package Geeks.Chat.Services;

import Geeks.Chat.DataTransfere.UserRegistrationRequest;
import Geeks.Chat.Response.LoginResponse;
import Geeks.Chat.entity.User;
import Geeks.Chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceimpl implements UserService{

    private final UserRepository userRepository;
    @Autowired
    public UserServiceimpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public boolean registerUser(UserRegistrationRequest userRequest){
        User existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser != null) {
            return false;
        }
        User newUser = new User();
        newUser.setUsername(userRequest.getUsername());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(userRequest.getPassword());

        userRepository.save(newUser);
        return true;
    }

    @Override
    public List<String> searchUsers(String username) {
        List<User> users = userRepository.findByUsernameContaining(username);
        List<String> usernames= new ArrayList<>();
        for(User user: users){
            usernames.add(user.getUsername());
        }
        return usernames;
    }

    @Override
    public boolean addContact(String token, String contactUsername) {
        String loggedInUserEmail= tokenService.extractEmail(token);
        User loggenInUser= userRepository.findByEmail(loggedInUserEmail);
        User contactUser= userRepository.findByUsernameContaining(contactUsername);
        if (loggenInUser != null && contactUser !=null){
            loggenInUser.addContact(contactUser);

            return true;
        }
        return false;
    }

    @Override
    public LoginResponse loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return new LoginResponse(true, "Successfully logged in",user.getUsername());
        }
        return new LoginResponse(false, "Invalid credentials"," ");
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if(user !=null){
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }

}
