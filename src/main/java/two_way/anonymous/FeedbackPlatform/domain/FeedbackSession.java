package two_way.anonymous.FeedbackPlatform.domain;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "feedback_session")
public class FeedbackSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private LocalDate sessionDate;

    @Enumerated(EnumType.STRING)
    private SessionStatus status;

    private String topic;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private Manager manager;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDate getSessionDate() { return sessionDate; }
    public void setSessionDate(LocalDate sessionDate) { this.sessionDate = sessionDate; }

    public SessionStatus getStatus() { return status; }
    public void setStatus(SessionStatus status) { this.status = status; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    
    public Manager getManager() { return manager; }
    public void setManager(Manager manager) { this.manager = manager; }
}