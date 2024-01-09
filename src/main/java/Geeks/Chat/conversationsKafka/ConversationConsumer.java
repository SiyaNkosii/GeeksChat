package Geeks.Chat.conversationsKafka;

import Geeks.Chat.entity.Conversation;
import Geeks.Chat.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component

public class ConversationConsumer {

    @Autowired
    private ConversationRepository conversationRepository;

    @KafkaListener(topics = "user-conversations", groupId = "conversation-group")
    public void consume(String message){
        String[] parts = message.split("added");
        if(parts.length==2){
            String loggedInUser = parts[0];
            String searchedUser = parts[1];
            Conversation conversation = new Conversation();
            conversation.setSender(loggedInUser);
            conversation.setReceiver(searchedUser);
            conversation.setMessage("Started conversation");

            conversationRepository.save(conversation);
        }
    }


}
