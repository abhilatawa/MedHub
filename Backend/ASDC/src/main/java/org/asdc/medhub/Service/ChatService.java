package org.asdc.medhub.Service;

import org.asdc.medhub.Repository.ChatRepository;
import org.asdc.medhub.Repository.UserRepository;
import org.asdc.medhub.Service.Interface.IChatService;
import org.asdc.medhub.Utility.Enums.UserRole;
import org.asdc.medhub.Utility.Model.DatabaseModels.Chat;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.ChatDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Service class for managing chat-related operations.
 */
@Service
public class ChatService implements IChatService {

    /**
     * ChatRepository instance
     */
    private final ChatRepository chatRepository;
    /**
     * UserRepository instance
     */
    private final UserRepository userRepository;

    /**
     * Autowired constructor for ChatService.
     *
     * @param chatRepository The repository for handling chat-related data.
     * @param userRepository The repository for handling user-related data.
     */
    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves the conversation between two users.
     *
     * @param userId1 The ID of the first user.
     * @param userId2 The ID of the second user.
     * @return A responsemodel of list of chat messages exchanged between the two specified users.
     */
    public ResponseModel<List<ChatDetail>> getConversationBetweenTwoUsers(String userId1, String userId2) {
        ResponseModel<List<ChatDetail>> response = new ResponseModel<>();
        try {
            response.setResponseData(chatToChatDetail(this.chatRepository.findChatMessagesBetweenTwoUsers(userId1, userId2)));
            response.setSuccess(true);
            response.setMessage("Conversation found");
        } catch (Exception e) {
            response.setMessage("Failed to retrieve conversation: " + e.getMessage());
        }
        return response;
    }

    /**
     * Converts a list of Chat entities into a list of ChatDetail DTOs.
     *
     * @param chats A list of Chat entities. Each entity represents a chat message, containing details such as the message's ID, sender ID, receiver ID, content, and creation timestamp.
     * @return A list of ChatDetail DTOs, where each DTO contains detailed information about a chat message, derived from the Chat entities provided. This includes the message's ID, sender and receiver IDs, content, and the timestamp of when the message was sent.
     */
    private List<ChatDetail> chatToChatDetail(List<Chat> chats){
        List<ChatDetail> response = new ArrayList<>();
        for(Chat chat: chats){
            ChatDetail chatDetail = new ChatDetail();
            chatDetail.setId(chat.getId());
            chatDetail.setSenderId(chat.getSenderId());
            chatDetail.setReceiverId(chat.getReceiverId());
            chatDetail.setContent(chat.getContent());
            chatDetail.setCreatedAt(chat.getCreatedAt());
            response.add(chatDetail);
        }
        return response;
    }

    public ResponseModel<String> getUsername(User user){
        ResponseModel<String> response = new ResponseModel<>();
        response.setResponseData(user.getUsername());
        response.setMessage("Current user username");
        response.setSuccess(true);
        return response;
    }


    /**
     * Posts a message from a sender to a receiver and notifies the receiver via WebSocket.
     *
     * @param senderId The ID of the sender posting the message.
     * @param receiverId The ID of the receiver who will receive the message.
     * @param content The content of the message to be posted.
     * @return The saved chat message containing sender, receiver, and message content details.
     */
    public Chat postMessage(String senderId, String receiverId, String content) {
        try {
            Chat chatMessage = new Chat();
            chatMessage.setSenderId(senderId);
            chatMessage.setReceiverId(receiverId);
            chatMessage.setContent(content);
            return this.chatRepository.save(chatMessage);
        } catch (Exception e) {
            System.err.println("Error posting message: " + e.getMessage());
            throw new RuntimeException("Failed to post message", e);
        }
    }

    /**
     * Retrieves a list of unique user IDs representing all users with whom the specified user has engaged in chats.
     *
     * This method leverages the {@link ChatRepository} to perform a database query that finds all unique chat partners
     * of a given user, identified by their user ID. The query considers both cases where the specified user is the sender
     * or the receiver of the chat messages, ensuring no duplicates in the list of chat partners.
     *
     * @param userId The ID of the user whose chat partners are being requested. This is typically the authenticated user's ID.
     * @return A list of String objects, each representing a unique user ID of a chat partner. The list will be empty if the user
     * has not chatted with anyone.
     */
    @Override
    public ResponseModel<List<UserDetail>> findAllChatPartnersByUserId(String userId) {
        ResponseModel<List<UserDetail>> response=new ResponseModel<>();
        List<UserDetail> userDetails = new ArrayList<>();
        try {
            List<String> userIds = this.chatRepository.findAllChatPartnersByUserId(userId);
            if (!userIds.isEmpty()) {
                userDetails = userToUserDetails(userIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setResponseData(userDetails);
        response.setSuccess(true);
        response.setMessage("Chat partners");
        return response;
    }

    /**
     * Converts a list of user IDs to a list of UserDetail objects.
     *
     * @param users A list of user IDs to convert.
     * @return A list of UserDetail objects containing user details corresponding to the provided IDs.
     */
    private List<UserDetail> userToUserDetails(List<String> users){
        List<UserDetail> response = new ArrayList<>();
        for(String id: users){
            User u = this.userRepository.findUserByUsername(id);
            if (u != null) {
                UserDetail userDetail = new UserDetail();
                userDetail.setUserRole(u.getUserRole());
                userDetail.setUsername(u.getUsername());
                if(u.getUserRole() == UserRole.PATIENT) {
                    userDetail.setFirstName(u.getPatient().getFirstName());
                    userDetail.setLastName(u.getPatient().getLastName());
                }
                if(u.getUserRole() == UserRole.DOCTOR) {
                    userDetail.setFirstName(u.getDoctor().getFirstName());
                    userDetail.setLastName(u.getDoctor().getLastName());
                }
                response.add(userDetail);
            }
        }
        return response;
    }
}