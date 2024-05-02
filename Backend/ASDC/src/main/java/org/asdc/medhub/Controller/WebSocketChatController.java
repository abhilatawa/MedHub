package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.Interface.IChatService;
import org.asdc.medhub.Utility.Constant.ProjectConstants;
import org.asdc.medhub.Utility.Model.ResponseModels.ChatDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
/**
 * Controller class for managing socket chat-related operations.
 */
@Controller
public class WebSocketChatController {
    /**
     * Chatservice instance
     */
    private final IChatService chatService;

    /**
     * SimpMessagingTemplate instance
     */
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Constructs an instance of WebSocketChatController.
     * @param messagingTemplate used to send messages to WebSocket clients.
     */
    @Autowired
    public WebSocketChatController(IChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * Handles sending a private chat message via WebSocket
     * @param chatMessage The chat message to send.
     * @return The sent chat message.
     */
    @MessageMapping(ProjectConstants.Routes.WebSocketChat.queueRoute)
    public ChatDetail sendPrivateMessage(@Payload ChatDetail chatMessage) {
        this.chatService.postMessage(chatMessage.getSenderId(), chatMessage.getReceiverId(), chatMessage.getContent());
        this.messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiverId(), "/private", chatMessage);

        return chatMessage;
    }
}
