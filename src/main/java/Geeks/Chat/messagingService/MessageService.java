package Geeks.Chat.messagingService;

import Geeks.Chat.entity.Contact;
import Geeks.Chat.entity.Conversation;
import Geeks.Chat.entity.User;
import Geeks.Chat.exceptions.ResourceNotFoundException;
import Geeks.Chat.kafkaService.KafkaProducer;
import Geeks.Chat.repository.ContactRepository;
import Geeks.Chat.repository.ConversationRepository;
import Geeks.Chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private KafkaProducer kafkaProducer;
    public void sendConversation(String senderUsername, String receiverUsername, String message){
        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);

        if(sender !=null && receiver !=null){

            Contact contact = contactRepository.findByUserAndContactuser(sender, receiver).orElseThrow(()-> new ResourceNotFoundException("This is not your contact"));
            try{
                kafkaProducer.sendMessage(senderUsername, receiverUsername, message);
            }catch (KafkaException e){
                throw new ResourceNotFoundException("Failed to send message");
            }
        }
    }
    public List<Conversation> getConversations(String loggedInUsername, String contactUsername){
        User loggedInUser = userRepository.findByUsername(loggedInUsername);
        User contactUser = userRepository.findByUsername(contactUsername);
        if (loggedInUser != null && contactUser != null){
            Contact contact = contactRepository.findByUserAndContactuser(loggedInUser, contactUser).orElseThrow();

            if(loggedInUser != null && contactUser !=null){
              return conversationRepository.findBySenderAndReceiver(loggedInUser, contactUser);
            }else {
                throw new ResourceNotFoundException("This is not your contact");
            }
        }
        return null;
    }
}
