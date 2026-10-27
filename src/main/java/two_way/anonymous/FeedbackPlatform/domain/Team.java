package two_way.anonymous.FeedbackPlatform.domain;

import java.util.UUID;
import jakarta.persistence.*;

@Entity 
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    @Column (nullable = false, unique = true)
    private int teamNumber;

    private String name;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public int getTeamNumber() { return teamNumber; }
    public void setTeamNumber(int teamNumber) { this.teamNumber = teamNumber; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
}
