package two_way.anonymous.FeedbackPlatform.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import two_way.anonymous.FeedbackPlatform.domain.FeedbackReply;

@Repository
public interface FeedbackReplyRepository extends JpaRepository<FeedbackReply, UUID> {
    boolean existsByFeedbackSession_Id(UUID feedbackSessionId);
}