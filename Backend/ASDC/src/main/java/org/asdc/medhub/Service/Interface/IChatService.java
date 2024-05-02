package org.asdc.medhub.Service.Interface;

import org.asdc.medhub.Utility.Model.DatabaseModels.Chat;
import org.asdc.medhub.Utility.Model.DatabaseModels.User;
import org.asdc.medhub.Utility.Model.ResponseModel;
import org.asdc.medhub.Utility.Model.ResponseModels.ChatDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.UserDetail;

import java.util.List;
/**
 * Interface defining the contract for chat services.
 * Provides methods for managing and retrieving chat messages and chat partners in the application.
 */
public interface IChatService {
    /**
     * Retrieves a list of chat messages between two specified users.
     * This method is useful for displaying the conversation history between two participants.
     *
     * @param userId1 The username of the first user involved in the conversation.
     * @param userId2 The username of the second user involved in the conversation.
     * @return A Response model containing a list of Chat objects representing the messages exchanged between the two users.
     */
    ResponseModel<List<ChatDetail>> getConversationBetweenTwoUsers(String userId1, String userId2);

    /**
     * Posts a new message from one user to another.
     * This method handles the creation and storage of a new chat message in the system.
     *
     * @param senderId The ID of the user sending the message.
     * @param receiverId The ID of the user receiving the message.
     * @param content The textual content of the message being sent.
     * @return The Chat object representing the newly created message.
     */
    Chat postMessage(String senderId, String receiverId, String content);

    /**
     * Finds all unique chat partners for a specified user.
     * This method is useful for generating a list of all users with whom the specified user has had conversations.
     *
     * @param userId The ID of the user whose chat partners are being requested.
     * @return A list of UserDetail objects, each representing a unique chat partner of the specified user.
     */
    ResponseModel<List<UserDetail>> findAllChatPartnersByUserId(String userId);

    /**
     * Retrieves the username of the specified user.
     *
     * @param user The user whose username is to be retrieved.
     * @return A ResponseModel containing the username of the given user.
     */
    ResponseModel<String> getUsername(User user);
}
