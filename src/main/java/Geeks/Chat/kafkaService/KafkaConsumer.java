package Geeks.Chat.kafkaService;

import Geeks.Chat.entity.Conversation;
import Geeks.Chat.entity.User;
import Geeks.Chat.repository.ConversationRepository;
import Geeks.Chat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

//@KafkaListener(topics = "user-conversations", groupId = "conversation-group")
    public void consume(String message) {
        try {
            String[] parts = message.split(":");
            if (parts.length >= 3) {
                String senderUsername = parts[0].trim();
                String content = parts[1].trim();
                String receiverUsername = parts[2].trim();

                User sender = userRepository.findByUsername(senderUsername);
                User receiver = userRepository.findByUsername(receiverUsername);

                if (sender != null && receiver != null) {
                        Conversation conversation = Conversation.builder()
                                .sender(sender)
                                .receiver(receiver)
                                .message(content)
                                .build();
                        conversationRepository.save(conversation);
                        logger.info("Saved conversation to the database.");
                    }else {
                        logger.warn("Invalid sender or receiver - Sender: {}, Receiver: {}", senderUsername, receiverUsername);
                    }
                }


        } catch (Exception e) {
            logger.error("Error processing Kafka message - {}", e.getMessage());
            e.printStackTrace();
        }
    }

}
