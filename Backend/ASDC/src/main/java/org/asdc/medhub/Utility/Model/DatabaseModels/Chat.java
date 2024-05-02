package org.asdc.medhub.Utility.Model.DatabaseModels;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.asdc.medhub.Utility.Constant.DatabaseConstants;

import java.sql.Timestamp;
import java.time.Instant;
/**
 * The Chat class is annotated with JPA (Jakarta Persistence API) annotations to map it to the "chat_messages" table in the database.
 * It uses Lombok annotations to automatically generate getter and setter methods for its fields.
 * This class models a chat message, containing information about the sender, receiver, message content, and the timestamp of when the message was created.
 */
@Getter @Setter
@NoArgsConstructor
@Entity(name = DatabaseConstants.ChatTable.tableName)
public class Chat {

    /**
     * primary key | auto increment
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * senderId of chat message
     */
    @Column(name = DatabaseConstants.ChatTable.Columns.senderId)
    private String senderId;

    /**
     * receiverId of chat message
     */
    @Column(name = DatabaseConstants.ChatTable.Columns.receiverId)
    private String receiverId;

    /**
     * content of chat message
     */
    @Column(name = DatabaseConstants.ChatTable.Columns.content)
    private String content;

    /**
     * timestamp of chat message
     */
    @Column(name = DatabaseConstants.ChatTable.Columns.createdAt)
    private Timestamp createdAt;

    /**
     * The @PrePersist annotation is used to specify callback methods for the corresponding lifecycle event.
     * This method is called before the entity is persisted, i.e., before it's saved to the database.
     * It sets the timestamp field to the current date and time before the entity is persisted.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = Timestamp.from(Instant.now());
    }
}
