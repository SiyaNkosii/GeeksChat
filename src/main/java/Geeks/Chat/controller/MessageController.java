package Geeks.Chat.controller;

import Geeks.Chat.entity.Conversation;
import Geeks.Chat.kafkaService.KafkaConsumer;
import Geeks.Chat.messagingService.MessageService;
import Geeks.Chat.requestPayloads.MessageRequest;
import Geeks.Chat.responsePayloads.ApiResponse;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private KafkaConsumer kafkaConsumer;

    @PostMapping("/send-message")
    public ResponseEntity<ApiResponse> sendMessage(@RequestBody MessageRequest messageRequest){
      messageService.sendConversation(
              messageRequest.getSenderUsername(),
              messageRequest.getReceiverUsername(),
              messageRequest.getMessageContent()
      );
        return ResponseEntity.ok(new ApiResponse("Message sent successfully"));
    }

    @GetMapping("/received-messages")
    public List<Conversation>getInMemoryMessages(){
        return kafkaConsumer.getInMemoryMessages();
    }

    @GetMapping("/sent-messages")
    public List<Conversation>getInMemorySentMessages(){
        return messageService.getInMemorySentMessages();
    }
}
