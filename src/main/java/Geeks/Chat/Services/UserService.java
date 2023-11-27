package Geeks.Chat.Services;

import Geeks.Chat.entity.User;
import Geeks.Chat.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registerUser(String username, String email, String password)
    {
        if(userRepository.findByEmail(email).isPresent())
        {
            throw new RuntimeException("User with this email already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        return userRepository.save(user);
    }
    public  User loginUser(String email, String password){
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        if(!user.getPassword().equals(password))
        {
            throw new RuntimeException("Invalid password");
        }
        return user;

    }
    public void resetPassword(String email, String newPassword)
    {
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(()->new RuntimeException("User not found"));
        user.setPassword(newPassword);
        userRepository.save(user);

    }
}
