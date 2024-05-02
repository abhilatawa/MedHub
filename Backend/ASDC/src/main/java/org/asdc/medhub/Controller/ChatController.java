package org.asdc.medhub.Controller;

import org.asdc.medhub.Service.Interface.IChatService;
import org.asdc.medhub.Utility.Constant.ProjectConstants;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.ChatDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling chat-related HTTP requests.
 */
@RestController
public class ChatController {

    /**
     * ChatService instance
     */
    private final IChatService chatService;

    /**
     * Constructs an instance of IcharService.
     * @param chatService used to send messages to WebSocket clients.
     */
    @Autowired
    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }

    /**
     * Retrieves the conversation between the current user and another user.
     *
     * @param receiverUserId The ID of the other user to retrieve the conversation with.
     * @return A list of chat messages exchanged between the current user and the specified other user.
     */
    @GetMapping(ProjectConstants.Routes.Chat.conversation)
    public ResponseEntity<ResponseModel<List<ChatDetail>>> getConversation(@RequestParam("receiverUserId") String receiverUserId) {
        return ResponseEntity.ok(this.chatService.getConversationBetweenTwoUsers(SecurityContextHolder.getContext().getAuthentication().getName(), receiverUserId));
    }

    /**
     * Retrieves the chat partners for the current user.
     *
     * @return A ResponseEntity containing a list of user details representing the chat partners of the current user.
     */
    @GetMapping(ProjectConstants.Routes.Chat.partners)
    public ResponseEntity<ResponseModel<List<UserDetail>>> getChatPartners() {
        return ResponseEntity.ok(this.chatService.findAllChatPartnersByUserId(SecurityContextHolder.getContext().getAuthentication().getName()));
    }

    /**
     * Retrieves the username of the current authenticated user.
     *
     * @return A ResponseEntity containing the username of the current authenticated user.
     */
    @GetMapping(ProjectConstants.Routes.Chat.username)
    public ResponseEntity<ResponseModel<String>> getUserName(Authentication authentication){
        User user=(User)authentication.getPrincipal();
        return ResponseEntity.ok(this.chatService.getUsername(user));
    }

}