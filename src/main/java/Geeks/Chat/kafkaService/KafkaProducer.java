package Geeks.Chat.kafkaService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducer {
   // private final String TOPIC ="user-conversations";
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;


    public void sendMessage(String sender, String receiver, String message){
        try {
            String key = sender + "_"+ receiver;
            String value = sender + "_"+ message;
            log.info("Message sent successfully:" + message);
            //kafkaTemplate.send(TOPIC,key,value);

        } catch (Exception e){
            log.error("Failed to send message: {}", e.getMessage());
            e.printStackTrace();

        }

    }
}
