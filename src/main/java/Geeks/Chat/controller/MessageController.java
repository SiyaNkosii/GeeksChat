package Geeks.Chat.controller;

import Geeks.Chat.entity.Conversation;
import Geeks.Chat.exceptions.ResourceNotFoundException;
import Geeks.Chat.kafkaService.KafkaConsumer;
import Geeks.Chat.messagingService.MessageService;
import Geeks.Chat.requestPayloads.MessageRequest;
import Geeks.Chat.responsePayloads.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/get-saved-messages/{loggedInUsername}/{contactUsername}")
    public ResponseEntity<List<Conversation>> getSavedMessages(
            @PathVariable String loggedInUsername,
            @PathVariable String contactUsername
    ){
        try{
            List<Conversation> savedMessage = messageService.getConversations(loggedInUsername,contactUsername);
            return  ResponseEntity.ok(savedMessage);
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
