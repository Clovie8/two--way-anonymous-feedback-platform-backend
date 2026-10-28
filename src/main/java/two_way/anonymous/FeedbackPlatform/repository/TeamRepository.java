package two_way.anonymous.FeedbackPlatform.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import two_way.anonymous.FeedbackPlatform.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, UUID> {
    @Query("""
        SELECT t FROM Team t
        WHERE NOT EXISTS (
            SELECT m.id FROM Manager m WHERE m.team = t
        )
        ORDER BY t.teamNumber
        """)
    List<Team> findFreeTeams();

    Optional<Team> findByTeamNumber(int teamNumber);
}
