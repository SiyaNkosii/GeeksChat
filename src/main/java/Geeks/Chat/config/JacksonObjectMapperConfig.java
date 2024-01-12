package Geeks.Chat.config;

import Geeks.Chat.entity.Conversation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonObjectMapperConfig {

    @Bean
    public ObjectMapper ObjectMapper()   {
        return new ObjectMapper();
    }

    public String writeValueAsStringUsingObjectMapper(ObjectMapper objectMapper, Conversation conversation) {
        try {
            return objectMapper.writeValueAsString(conversation);
        } catch (Exception e) {
            // Handle or log the exception appropriately
            e.printStackTrace();
            return null;
        }
    }
}