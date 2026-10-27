package two_way.anonymous.FeedbackPlatform.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import two_way.anonymous.FeedbackPlatform.domain.FeedbackSession;
import two_way.anonymous.FeedbackPlatform.domain.SessionStatus;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackReplyRepository;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackSessionRepository;

@Service
public class FeedbackSessionService {
    @Autowired
    private FeedbackSessionRepository repository;

    @Autowired
    private FeedbackReplyRepository feedbackReplyRepository;

    public List<FeedbackSession> getAll() {
        return repository.findAll();
    }

    public Optional<FeedbackSession> getById(UUID id) {
        return repository.findById(id);
    }

    public String save(FeedbackSession session) {
        session.setStatus(SessionStatus.OPENED);
        repository.save(session);
        return "Feedback session saved successfully";
    }

    public String update(UUID id, FeedbackSession changes) {
        Optional<FeedbackSession> result = repository.findById(id);
        if (result.isEmpty()) {
            return "The feedback session with that id does not exist.";
        }

        FeedbackSession session = result.get();
        session.setSessionDate(changes.getSessionDate());
        session.setTopic(changes.getTopic());
        session.setEmployee(changes.getEmployee());
        session.setManager(changes.getManager());

        // Do not change status here; Part D2 owns status changes.
        repository.save(session);
        return "Feedback session updated successfully";
    }

    public String delete(UUID id) {
        Optional<FeedbackSession> result = repository.findById(id);
        if (result.isEmpty()) {
            return "The feedback session with that id does not exist.";
        }

        if (feedbackReplyRepository.existsByFeedbackSession_Id(id)) {
            return "That feedback session has feedback replies, delete them first.";
        }

        repository.delete(result.get());
        return "Feedback session deleted successfully";
    }


    public List<FeedbackSession> findByStatus(SessionStatus status) {
        return repository.findByStatusOrderBySessionDateAsc(status);
    }

    public List<FeedbackSession> findBetweenDates(
            LocalDate startDate, LocalDate endDate) {
        return repository.findBySessionDateBetweenOrderBySessionDateAsc(
                startDate, endDate);
    }
}