package Geeks.Chat.derserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import Geeks.Chat.entity.Conversation;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class ConversationDeserializer implements Deserializer<Conversation>{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }
    @Override
    public Conversation deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, Conversation.class);
        } catch (IOException e) {
            throw new SerializationException("Error deserializing message from topic " + topic, e);
        }
    }
    @Override
    public void close() {

    }
}
