package Geeks.Chat.kafkaService;


import Geeks.Chat.entity.Conversation;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class KafkaProducer {
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(Conversation conversation) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String serializedConversation = objectMapper.writeValueAsString(conversation);

            kafkaTemplate.send(topicName, serializedConversation);

            log.info("Message sent successfully: {}", serializedConversation);
        } catch (Exception e) {
            log.error("Failed to send message: {}", e.getMessage());
            e.printStackTrace();
        }
    }
}
