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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class messageService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private KafkaProducer kafkaProducer;
    public void sendMessage(String senderUsername, String receiverUsername, String message){
        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);

        if(sender !=null && receiver !=null){
            Contact contact = contactRepository.findByUserAndContactuser(sender, receiver).orElseThrow();
            if(contact !=null){
                Conversation conversation = Conversation.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .message(message)
                        .build();
                conversationRepository.save(conversation);

                kafkaProducer.sendMessage(senderUsername, receiverUsername, message);
            }else {
                throw new ResourceNotFoundException("This in not your contact");
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
