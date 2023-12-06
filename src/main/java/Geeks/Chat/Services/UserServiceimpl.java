package Geeks.Chat.Services;

import Geeks.Chat.DataTransfere.UserRegistrationRequest;
import Geeks.Chat.Response.LoginResponse;
import Geeks.Chat.entity.Contact;
import Geeks.Chat.entity.User;
import Geeks.Chat.repository.ContactRepository;
import Geeks.Chat.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceimpl implements UserService{

    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    public UserServiceimpl(UserRepository userRepository, ContactRepository contactRepository) {
        this.userRepository = userRepository;
        this.contactRepository = contactRepository;
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
    @Override
    public List<User> searchUsers(String username) {
        return userRepository.findByusernameContaining(username);
    }

    @Override
    public void addContact(Long loggedInUserId, Long contactUserId) {
        User loggedInUser = userRepository.findById(loggedInUserId).orElse(null);
        User contactUser = userRepository.findById(contactUserId).orElse(null);

        if(loggedInUser !=null && contactUser !=null) {
            if (!contactRepository.exist(loggedInUser, contactUser)) {
                Contact contact = new Contact(loggedInUser, contactUser);
                contactRepository.save(contact);
                System.out.println("Contact added successfully!");
            } else {
                System.out.println("Contact already exist in your contact");
            }
        }
    }
    @Override
    public List<User> getContactList(int loggedInUser){
        return userRepository.getChatList(loggedInUser);
    }
    public User getUserById(Long userId){
        Optional<User> userOptional= userRepository.findById(userId);
        return userOptional.orElse(null);
    }

}
