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
import org.springframework.stereotype.Service;

@Component
@Service
public class KafkaConsumer {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String message) {
        LOGGER.info(String.format("Event message received -> %s", message));
        try {
            String[] parts = message.split(":");
            if (parts.length == 3) {
                String senderUsername = parts[0].trim();
                String content = parts[1].trim();
                String receiverUsername = parts[2].trim();

                LOGGER.info("Sender: {}, Content: {}, Receiver: {}", senderUsername, content, receiverUsername);

                User sender = userRepository.findByUsername(senderUsername);
                User receiver = userRepository.findByUsername(receiverUsername);

                if (sender != null && receiver != null) {
                    Conversation conversation = Conversation.builder()
                            .sender(sender)
                            .receiver(receiver)
                            .message(content)
                            .build();
                    conversationRepository.save(conversation);
                    LOGGER.info("Saved conversation to the database.");
                } else {
                    LOGGER.warn("Invalid sender or receiver - Sender: {}, Receiver: {}", senderUsername, receiverUsername);
                }
            } else {
                LOGGER.warn("Invalid message format - {}", message);
            }
        } catch (Exception e) {
            LOGGER.error("Error processing Kafka message - {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
