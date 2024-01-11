package Geeks.Chat.requestPayloads;




public class MessageRequest {
    private String senderUsername;
    private String receiverUsername;
    private String messageContent;
    public MessageRequest(){

    }

    public MessageRequest(String senderUsername, String receiverUsername, String messageContent) {
        this.senderUsername = senderUsername;
        this.receiverUsername = receiverUsername;
        this.messageContent = messageContent;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
}
