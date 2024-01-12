package Geeks.Chat.kafkaService;

import Geeks.Chat.config.JacksonObjectMapperConfig;
import Geeks.Chat.entity.Conversation;
import Geeks.Chat.entity.User;
import Geeks.Chat.repository.ConversationRepository;
import Geeks.Chat.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Autowired
    private JacksonObjectMapperConfig jacksonObjectMapperConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String jsonString) {
        LOGGER.warn(String.format("Event message received -> %s", jsonString));
        try {
            ObjectMapper objectMapper = jacksonObjectMapperConfig.ObjectMapper();
            Conversation conversation = objectMapper.readValue(jsonString, Conversation.class);

            if(conversation !=null){
                String senderUsername = conversation.getSender().getUsername();
                String receiverUsername = conversation.getReceiver().getUsername();

                User sender = userRepository.findByUsername(conversation.getSender().getUsername());
                User receiver = userRepository.findByUsername(conversation.getReceiver().getUsername());

                if(sender !=null && receiver !=null){
                    conversation.setSender(sender);
                    conversation.setReceiver(receiver);
                    conversationRepository.save(conversation);
                    LOGGER.info("Saved conversation to the database.");
                }else {
                    LOGGER.warn("Invalid sender or receiver - Sender: {}, Receiver: {}", senderUsername, receiverUsername);
                }
            }else {
                LOGGER.warn("Failed to deserialize the message into a Conversation object");
            }
        }catch (Exception e){
            LOGGER.error("Unexpected error processing Kafka message: {}", e.getMessage());
            e.printStackTrace();
        }
    }

}
