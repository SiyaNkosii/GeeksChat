package Geeks.Chat.service.serviceImpl;

import Geeks.Chat.entity.Contact;
import Geeks.Chat.exceptions.ResourceAlreadyExistException;
import Geeks.Chat.exceptions.ResourceNotFoundException;
import Geeks.Chat.requestPayloads.UserRegistrationRequest;
import Geeks.Chat.responsePayloads.LoginResponse;
import Geeks.Chat.entity.User;
import Geeks.Chat.repository.ContactRepository;
import Geeks.Chat.repository.UserRepository;
import Geeks.Chat.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  ContactRepository contactRepository;


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
        List<User> users=userRepository.findByUsernameContaining(username);
        if(!users.isEmpty()){
            return users;
        }else {
            log.error("unable to find username: {}",username);
            throw new ResourceNotFoundException("Could not find contact with username :"+username);
        }
    }

    @Override
    public Contact addToMyContact(String loggedInUser, String searcheduser) {
        User loggedinUser= userRepository.findByUsername(loggedInUser);
        User searchedUser= userRepository.findByUsername(searcheduser);


        Optional<Contact> existingContact = contactRepository.findByUserUsername(loggedInUser);
        if(existingContact.isPresent()){
            throw new ResourceAlreadyExistException("Contact is already part of your contacts.");
        }else {

            Contact contact= Contact.builder()
                    .user(loggedinUser)
                    .contactuser(searchedUser)
                    .build();
            log.info("Saving contact with username: {} to my contact",searchedUser);
            contactRepository.save(contact);
            return contact;
        }
    }

//    @Override
//    public void addContact(Long loggedInUserId, Long contactUserId) {
//        User loggedInUser = userRepository.findById(loggedInUserId).orElse(null);
//        User contactUser = userRepository.findById(contactUserId).orElse(null);
//
//        if(loggedInUser !=null && contactUser !=null) {
//            if (!contactRepository.exist(loggedInUser, contactUser)) {
//                Contact contact = new Contact(loggedInUser, contactUser);
//                contactRepository.save(contact);
//                System.out.println("Contact added successfully!");
//            } else {
//                System.out.println("Contact already exist in your contact");
//            }
//        }
//    }
//    @Override
//    public List<User> getContactList(int loggedInUser){
//        return userRepository.getChatList(loggedInUser);
//    }
//    public User getUserById(Long userId){
//        Optional<User> userOptional= userRepository.findById(userId);
//        return userOptional.orElse(null);
//    }

}
