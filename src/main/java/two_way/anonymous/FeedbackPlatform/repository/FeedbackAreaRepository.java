package two_way.anonymous.FeedbackPlatform.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import two_way.anonymous.FeedbackPlatform.domain.FeedbackArea;

@Repository
public interface FeedbackAreaRepository extends JpaRepository<FeedbackArea, UUID> {
    @Query("""
        SELECT area FROM FeedbackArea area
        LEFT JOIN area.managers manager
        WHERE manager.id IS NULL
        """)
    List<FeedbackArea> findUnusedAreas();
}
