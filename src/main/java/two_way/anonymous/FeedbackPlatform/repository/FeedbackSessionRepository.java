package two_way.anonymous.FeedbackPlatform.repository;

import java.util.UUID;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
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


        @Query("""
            SELECT session.status, COUNT(session)
            FROM FeedbackSession session
            GROUP BY session.status
            """)
        List<Object[]> countSessionsByStatus();


        @Modifying
        @Query("""
            UPDATE FeedbackSession session
            SET session.status =
                two_way.anonymous.FeedbackPlatform.domain.SessionStatus.WITHDRAWN
            WHERE session.manager.id = :managerId
              AND session.sessionDate = :date
              AND session.status <>
                two_way.anonymous.FeedbackPlatform.domain.SessionStatus.REVIEWED
            """)
        int withdrawSessionsForManagerOnDate(
                @Param("managerId") UUID managerId,
                @Param("date") LocalDate date);

        long countByStatus(SessionStatus status);
}
