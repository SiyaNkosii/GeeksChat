package Geeks.Chat.repository;

import Geeks.Chat.entity.Conversation;
import Geeks.Chat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findBySenderAndReceiver(User sender, User receiver);

}
