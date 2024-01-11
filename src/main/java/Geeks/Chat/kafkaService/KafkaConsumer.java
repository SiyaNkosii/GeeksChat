package Geeks.Chat.kafkaService;

import Geeks.Chat.entity.Conversation;
import Geeks.Chat.entity.User;
import Geeks.Chat.repository.ConversationRepository;
import Geeks.Chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private UserRepository userRepository;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message){
        String[] parts= message.split(":");
        String senderUsername= parts[0].trim();
        String content = parts[1].trim();
        String receiverUsername =parts[2].trim();

        User sender = userRepository.findByUsername(senderUsername);
        User receiver = userRepository.findByUsername(receiverUsername);

        if(sender != null && receiver !=null){
            Conversation conversation = Conversation.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .message(content)
                    .build();
            conversationRepository.save(conversation);
        }
    }
}
