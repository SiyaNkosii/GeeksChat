package Geeks.Chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "conversations")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor  // Make sure you have this annotation for Jackson deserialization
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Conversation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "convId")
    private Long convId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @JsonIgnore
    private User receiver;

    private String message;
}
