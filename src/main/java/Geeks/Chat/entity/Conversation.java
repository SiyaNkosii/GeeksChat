package Geeks.Chat.entity;

import liquibase.pro.packaged.J;
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
    @Column(name = "conv_id")
    private Long convId;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String message;

}
