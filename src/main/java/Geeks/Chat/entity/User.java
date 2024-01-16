package Geeks.Chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    private String password;

    @OneToMany(mappedBy = "sender",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Conversation> sentConversations;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Conversation> receivedConversations;
}
