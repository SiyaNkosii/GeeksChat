//package Geeks.Chat.controller;
//
//import Geeks.Chat.entity.Conversation;
//import Geeks.Chat.messagingService.MessageService;
//import Geeks.Chat.responsePayloads.ApiResponse;
//import org.apache.kafka.common.errors.ResourceNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class MessageController {
//    @Autowired
//    private MessageService messageService;
//
//    @GetMapping("/sendMessage")
//    public ResponseEntity<ApiResponse> sendMessage(
//            @RequestParam String senderUsername,
//            @RequestParam String receiverUsername,
//            @RequestParam String message
//    ){
//        messageService.sendConversation(senderUsername, receiverUsername, message);
//        return ResponseEntity.ok(new ApiResponse("Message sent successfully"));
//    }
//
//    @GetMapping("/get-conversations")
//    public ResponseEntity<List<Conversation>> getConversations(
//            @RequestParam String loggedInUsername,
//            @RequestParam String contactUsername
//    ){
//        List<Conversation> conversations = messageService.getConversations(loggedInUsername, contactUsername);
//
//        if(conversations != null){
//            return ResponseEntity.ok(conversations);
//        }else {
//            throw new ResourceNotFoundException("Add person to you contact list first");
//        }
//    }
//}
