package Geeks.Chat.Services;

import Geeks.Chat.DataTransfere.UserRegistrationRequest;
import Geeks.Chat.Response.LoginResponse;
import Geeks.Chat.entity.User;

import java.util.List;

public interface UserService {
    LoginResponse loginUser(String email, String password);
    void resetPassword(String email, String newPassword);

    boolean registerUser(UserRegistrationRequest userRequest);
    List<User> searchUsers(String username);
    //void addContact(Long loggedInUserId, Long contactUserId);

    //List<User> getContactList(int loggedInUser);
}
