package two_way.anonymous.FeedbackPlatform.repository;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import two_way.anonymous.FeedbackPlatform.domain.FeedbackSession;
import two_way.anonymous.FeedbackPlatform.domain.SessionStatus;

@Repository
public interface FeedbackSessionRepository extends JpaRepository<FeedbackSession, UUID> {
        boolean existsByEmployee_Id(UUID employeeId);
        boolean existsByManager_Id(UUID managerId);

        List<FeedbackSession> findByStatusOrderBySessionDateAsc(SessionStatus status);

        List<FeedbackSession> findBySessionDateBetweenOrderBySessionDateAsc(
        LocalDate startDate,
        LocalDate endDate);

        boolean existsByManager_IdAndSessionDateAndStatusNot(
                UUID managerId,
                LocalDate sessionDate,
                SessionStatus status);
}
