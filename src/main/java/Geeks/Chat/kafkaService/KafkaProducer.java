package Geeks.Chat.kafkaService;


import Geeks.Chat.entity.Conversation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaProducer {
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Autowired
    private KafkaTemplate<String,Conversation> kafkaTemplate;



    public void sendMessage(Conversation conversation){
        try {
            Message<Conversation> message = MessageBuilder
                    .withPayload(conversation)
                    .setHeader(KafkaHeaders.TOPIC, topicName)
                    .build();

            kafkaTemplate.send(message);
            log.info("Message sent successfully:" + message);

        } catch (Exception e){
            log.error("Failed to send message: {}", e.getMessage());
            e.printStackTrace();

        }

    }
}