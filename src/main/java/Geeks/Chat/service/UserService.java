package Geeks.Chat.service;

import Geeks.Chat.entity.Contact;
import Geeks.Chat.requestPayloads.UserRegistrationRequest;
import Geeks.Chat.responsePayloads.LoginResponse;
import Geeks.Chat.entity.User;

import java.util.List;

public interface UserService {
    LoginResponse loginUser(String email, String password);
    void resetPassword(String email, String newPassword);

    boolean registerUser(UserRegistrationRequest userRequest);
    List<User> searchUsers(String username);
    Contact addToMyContact(String loggedInUser, String searchedUser);
}
