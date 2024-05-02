package org.asdc.medhub.Repository;

import org.asdc.medhub.Utility.Constant.DatabaseConstants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.asdc.medhub.Utility.Model.DatabaseModels.Chat;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Interface for CRUD operations on a repository for the {@link Chat} type.
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {

    /**
     * Finds chat messages between two users, ordered by timestamp in ascending order.
     *
     * @param userId1 The ID of the first user involved in the chat.
     * @param userId2 The ID of the second user involved in the chat.
     * @return A list of {@link Chat} objects representing the chat messages between the two specified users.
     */
    @Query(DatabaseConstants.Queries.findChatMessagesBetweenTwoUsers)
    List<Chat> findChatMessagesBetweenTwoUsers(@Param("userId1") String userId1, @Param("userId2") String userId2);

    /**
     * Finds unique user IDs of all users that have chatted with the specified user.
     *
     * @param userId The ID of the user to find chat partners for.
     * @return A list of unique user IDs representing all users who have chatted with the specified user.
     */
    @Query(DatabaseConstants.Queries.findAllChatPartnersByUserId)
    List<String> findAllChatPartnersByUserId(@Param("userId") String userId);
}
