package two_way.anonymous.FeedbackPlatform.repository;

import java.util.UUID;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import two_way.anonymous.FeedbackPlatform.domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
    boolean existsByTeam_Id(UUID teamId);
    boolean existsByFeedbackAreas_Id(UUID feedbackAreaId);

    @Query("""
        SELECT DISTINCT m FROM Manager m
        JOIN m.feedbackAreas area
        WHERE LOWER(area.name) = LOWER(:name)
        """)
    List<Manager> findByFeedbackAreaName(@Param("name") String name);
}
