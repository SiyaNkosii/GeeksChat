package Geeks.Chat.kafkaService;

import com.fasterxml.jackson.databind.ObjectMapper;
import Geeks.Chat.entity.Conversation;
import Geeks.Chat.repository.ConversationRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class KafkaConsumer {

    @Autowired
    private ConversationRepository conversationRepository;

    //array that holds messages for real-time communication
    private final List<Conversation> inMemoryMessages = new ArrayList<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(ConsumerRecord<String, String> record) {
        log.info(String.format("Received Kafka message -> key: %s, value: %s", record.key(), record.value()));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Conversation conversation = objectMapper.readValue(record.value(), Conversation.class);

            //I am storing the message in-memory for real-time communication
            inMemoryMessages.add(conversation);

            //Then am now saving conversation for later retrieval
            conversationRepository.save(conversation);
        } catch (Exception e) {
            LOGGER.error("Unexpected error processing Kafka message: {}", e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Conversation>getInMemoryMessages(){
        return inMemoryMessages;
    }
}
