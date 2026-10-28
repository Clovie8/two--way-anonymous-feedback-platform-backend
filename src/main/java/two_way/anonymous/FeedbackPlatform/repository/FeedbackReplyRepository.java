package two_way.anonymous.FeedbackPlatform.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import two_way.anonymous.FeedbackPlatform.domain.FeedbackReply;

@Repository
public interface FeedbackReplyRepository extends JpaRepository<FeedbackReply, UUID> {
    boolean existsByFeedbackSession_Id(UUID feedbackSessionId);

    @Query("""
        SELECT reply FROM FeedbackReply reply
        WHERE reply.feedbackSession.employee.staffId = :staffId
        ORDER BY reply.repliedDate DESC
        """)
    List<FeedbackReply> findHistoryByEmployeeStaffId(
            @Param("staffId") String staffId);
}