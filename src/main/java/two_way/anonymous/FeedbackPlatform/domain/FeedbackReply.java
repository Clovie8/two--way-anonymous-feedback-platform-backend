package two_way.anonymous.FeedbackPlatform.domain;

import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "feedback_reply")
public class FeedbackReply {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String subject;
    private String message;
    private int helpfulnessScore;
    private LocalDate repliedDate;

    @ManyToOne
    @JoinColumn(name = "feedback_session_id", nullable = false)
    private FeedbackSession feedbackSession;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public int getHelpfulnessScore() { return helpfulnessScore; }
    public void setHelpfulnessScore(int helpfulnessScore) {
        this.helpfulnessScore = helpfulnessScore;
    }

    public LocalDate getRepliedDate() { return repliedDate; }
    public void setRepliedDate(LocalDate repliedDate) { this.repliedDate = repliedDate; }
    
    public FeedbackSession getFeedbackSession() { return feedbackSession; }
    public void setFeedbackSession(FeedbackSession feedbackSession) {
        this.feedbackSession = feedbackSession;
    }
}