package com.example.demo.Models;

import com.example.demo.Models.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {
    @SequenceGenerator(
            name = "message_sequence",
            sequenceName = "message_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "message_sequence"
    )
    private Long id;
    private String content;
    private Boolean isRead;
    private Boolean isSent;
    private Boolean isReceived;
    private Long receiverId;
    private LocalDateTime sentAt;
    @ManyToOne(cascade = { CascadeType.REMOVE })
    @JoinColumn(name="app_user_id")
    private AppUser user;

    public Message(String content, Boolean isRead, Boolean isSent, Boolean isReceived, AppUser user, Long receiverId) {
        this.content = content;
        this.isRead = isRead;
        this.isSent = isSent;
        this.isReceived = isReceived;
        this.user = user;
        this.sentAt = LocalDateTime.now();
        this.receiverId = receiverId;
    }
}
