package two_way.anonymous.FeedbackPlatform.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import two_way.anonymous.FeedbackPlatform.domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    boolean existsByTeam_Id(UUID teamId);
    boolean existsByFeedbackAreas_Id(UUID feedbackAreaId);
}
