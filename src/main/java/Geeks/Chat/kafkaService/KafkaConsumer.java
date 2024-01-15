package Geeks.Chat.kafkaService;

import Geeks.Chat.entity.Conversation;
import Geeks.Chat.repository.ConversationRepository;
import Geeks.Chat.repository.UserRepository;
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


    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(Conversation conversation) {
        LOGGER.info(String.format("Event message received -> %s", conversation.getMessage()));
        try {
            conversationRepository.save(conversation);
        }catch (Exception e){
            LOGGER.error("Unexpected error processing Kafka message: {}", e.getMessage());
            e.printStackTrace();
        }
    }

}