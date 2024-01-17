package Geeks.Chat.messagingService;

import Geeks.Chat.entity.Contact;
import Geeks.Chat.entity.Conversation;
import Geeks.Chat.entity.User;
import Geeks.Chat.exceptions.ResourceNotFoundException;
import Geeks.Chat.kafkaService.KafkaProducer;
import Geeks.Chat.repository.ContactRepository;
import Geeks.Chat.repository.ConversationRepository;
import Geeks.Chat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MessageService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private KafkaProducer kafkaProducer;
    private final List<Conversation> inMemorySentMessages = new ArrayList<>();

    @Transactional
    public void sendConversation(String senderUsername, String receiverUsername, String message) {
        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);

        if (sender != null && receiver != null) {
            Contact contact = contactRepository.findByUserAndContactuser(sender, receiver)
                    .orElseThrow(() -> new ResourceNotFoundException("This is not your contact"));

            try {

                Conversation conversation = new Conversation();
                conversation.setSender(sender);
                conversation.setReceiver(receiver);
                conversation.setMessage(message);

                sender.getSentConversations().add(conversation);
                receiver.getReceivedConversations().add(conversation);
                conversationRepository.save(conversation);

                inMemorySentMessages.add(conversation);


                kafkaProducer.sendMessage(conversation);


            } catch (Exception e) {
                log.error("Failed to send message: {}", e.getMessage());
                e.printStackTrace();

                if (e instanceof KafkaException) {
                    throw (KafkaException) e;
                } else {
                    throw new RuntimeException("Failed to send message", e);
                }
            }
        }
    }

    public List<Conversation> getConversations(String loggedInUsername, String contactUsername) {
        User loggedInUser = userRepository.findByUsername(loggedInUsername);
        User contactUser = userRepository.findByUsername(contactUsername);

        if (loggedInUser != null && contactUser != null) {
            Contact contact = contactRepository.findByUserAndContactuser(loggedInUser, contactUser)
                    .orElseThrow(() -> new ResourceNotFoundException("This is not your contact"));

            if (contact != null) {
                return conversationRepository.findBySenderAndReceiver(loggedInUser, contactUser);
            } else {
                throw new ResourceNotFoundException("This is not your contact");
            }
        }
        return null;
    }
    public List<Conversation>getInMemorySentMessages(){
        return inMemorySentMessages;
    }
}

