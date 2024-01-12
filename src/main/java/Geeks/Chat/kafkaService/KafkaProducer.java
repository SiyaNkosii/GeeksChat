package Geeks.Chat.kafkaService;


import Geeks.Chat.config.JacksonObjectMapperConfig;
import Geeks.Chat.entity.Conversation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaProducer {
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;
    @Autowired
    private JacksonObjectMapperConfig jacksonObjectMapperConfig;



    public void sendMessage(String sender, String receiver, String message){
        try {
            String key = sender + ":"+ receiver;
            Conversation conversation = new Conversation(sender, receiver, message);

            String value = jacksonObjectMapperConfig.writeValueAsStringUsingObjectMapper(jacksonObjectMapperConfig.ObjectMapper(), conversation);

            kafkaTemplate.send(topicName,key,value);
            log.info("Message sent successfully:" + message);

        } catch (Exception e){
            log.error("Failed to send message: {}", e.getMessage());
            e.printStackTrace();

        }

    }
}
