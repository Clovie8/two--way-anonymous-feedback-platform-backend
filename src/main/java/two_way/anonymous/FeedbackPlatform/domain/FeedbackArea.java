package two_way.anonymous.FeedbackPlatform.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity 
@Table(name = "feedback_area")
public class FeedbackArea {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @JsonIgnore 
    @ManyToMany(mappedBy = "feedbackAreas")
    private List<Manager> managers = new ArrayList<>();


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public List<Manager> getManagers() { return managers; }
    public void setManagers(List<Manager> managers) { this.managers = managers; }
}
