package Geeks.Chat.Services;

import Geeks.Chat.entity.User;

import java.util.List;

public interface UserService {
    void registerUser(String username, String email, String password);
    User loginUser(String email, String password);
    void resetPassword(String email, String newPassword);
    List<User> getAllUsers();
}
