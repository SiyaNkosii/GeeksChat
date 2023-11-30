package Geeks.Chat.Services;

import Geeks.Chat.entity.User;
import Geeks.Chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceimpl implements UserService{

    private final UserRepository userRepository;
    @Autowired
    public UserServiceimpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public void registerUser(String username, String email, String password) {
        User newUser= new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);

        userRepository.save(newUser);

    }

    @Override
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if(user !=null){
            user.setPassword(newPassword);
            userRepository.save(user);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
