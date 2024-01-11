package Geeks.Chat.kafkaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private final String TOPIC ="${spring.kafka.topic.name}";
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessage(String sender, String receiver, String message){
        String key = sender + "_"+ receiver;
        String value = sender + "_"+ message;
        kafkaTemplate.send(TOPIC,key,value);
    }
}
