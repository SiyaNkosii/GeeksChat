package Geeks.Chat.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="conversations")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "message")
    private String message;
}
