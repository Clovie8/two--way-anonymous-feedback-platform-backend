package two_way.anonymous.FeedbackPlatform.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "manager")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = true)
    private Team team;

    @ManyToMany
    @JoinTable(
        name = "manager_feedback_areas",
        joinColumns = @JoinColumn(name = "manager_id"),
        inverseJoinColumns = @JoinColumn(name = "feedback_area_id")
    )
    private List<FeedbackArea> feedbackAreas = new ArrayList<>();

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
    
    public List<FeedbackArea> getFeedbackAreas() { return feedbackAreas; }
    public void setFeedbackAreas(List<FeedbackArea> feedbackAreas) {
        this.feedbackAreas = feedbackAreas;
    }
}