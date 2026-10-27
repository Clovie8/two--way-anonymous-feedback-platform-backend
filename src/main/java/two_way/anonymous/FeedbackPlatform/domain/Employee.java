package two_way.anonymous.FeedbackPlatform.domain;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table (name = "employee")
public class Employee {

    @Id 
    @GeneratedValue (strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;
    private String lastName;

    @Column (nullable = false, unique = true)
    private String staffId;

    private String email;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
}
