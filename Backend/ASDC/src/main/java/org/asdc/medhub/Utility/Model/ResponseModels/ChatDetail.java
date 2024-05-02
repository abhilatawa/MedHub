package org.asdc.medhub.Utility.Model.ResponseModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Model that carries details of chat for response
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDetail {
    /**
     * chat id
     */
    private Long id;

    /**
     * sender user name
     */
    private String senderId;

    /**
     * receiver user name
     */
    private String receiverId;

    /**
     * content of chat message
     */
    private String content;

    /**
     * timestamp of chat message
     */
    private Timestamp createdAt;
}
