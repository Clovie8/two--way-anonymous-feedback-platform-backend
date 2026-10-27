package two_way.anonymous.FeedbackPlatform.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import two_way.anonymous.FeedbackPlatform.domain.FeedbackSession;

@Repository
public interface FeedbackSessionRepository extends JpaRepository<FeedbackSession, UUID> {
        boolean existsByEmployee_Id(UUID employeeId);
        boolean existsByManager_Id(UUID managerId);
}
