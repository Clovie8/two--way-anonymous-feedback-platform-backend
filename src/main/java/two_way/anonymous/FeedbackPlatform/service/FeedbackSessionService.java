package two_way.anonymous.FeedbackPlatform.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import two_way.anonymous.FeedbackPlatform.domain.FeedbackSession;
import two_way.anonymous.FeedbackPlatform.domain.SessionStatus;
import two_way.anonymous.FeedbackPlatform.domain.Employee;
import two_way.anonymous.FeedbackPlatform.domain.Manager;
import two_way.anonymous.FeedbackPlatform.repository.EmployeeRepository;
import two_way.anonymous.FeedbackPlatform.repository.ManagerRepository;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackReplyRepository;
import two_way.anonymous.FeedbackPlatform.repository.FeedbackSessionRepository;

@Service
public class FeedbackSessionService {
    @Autowired
    private FeedbackSessionRepository repository;

    @Autowired
    private FeedbackReplyRepository feedbackReplyRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private ManagerRepository managerRepository;


    public List<FeedbackSession> getAll() {
        return repository.findAll();
    }

    public Optional<FeedbackSession> getById(UUID id) {
        return repository.findById(id);
    }

    public String save(FeedbackSession session) {
        if (session.getSessionDate() == null) {
            return "Feedback session date is required.";
        }
    
        if (session.getSessionDate().isBefore(LocalDate.now())) {
            return "Feedback session date cannot be in the past.";
        }
    
        if (session.getManager() == null || session.getManager().getId() == null) {
            return "The manager with that id does not exist.";
        }
    
        if (session.getEmployee() == null || session.getEmployee().getId() == null) {
            return "The employee with that id does not exist.";
        }
    
        Optional<Manager> managerResult =
                managerRepository.findById(session.getManager().getId());
        if (managerResult.isEmpty()) {
            return "The manager with that id does not exist.";
        }
    
        Optional<Employee> employeeResult =
                employeeRepository.findById(session.getEmployee().getId());
        if (employeeResult.isEmpty()) {
            return "The employee with that id does not exist.";
        }
    
        boolean managerAlreadyHasSession =
                repository.existsByManager_IdAndSessionDateAndStatusNot(
                        managerResult.get().getId(),
                        session.getSessionDate(),
                        SessionStatus.WITHDRAWN);
    
        if (managerAlreadyHasSession) {
            return "Manager already has a feedback session on that date.";
        }
    
        session.setManager(managerResult.get());
        session.setEmployee(employeeResult.get());
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


    public String changeStatus(UUID id, SessionStatus newStatus) {
        Optional<FeedbackSession> result = repository.findById(id);
    
        if (result.isEmpty()) {
            return "The feedback session with that id does not exist.";
        }
    
        FeedbackSession session = result.get();
        SessionStatus currentStatus = session.getStatus();
    
        boolean allowed =
                (currentStatus == SessionStatus.OPENED &&
                        (newStatus == SessionStatus.SUBMITTED ||
                         newStatus == SessionStatus.WITHDRAWN))
                ||
                (currentStatus == SessionStatus.SUBMITTED &&
                        (newStatus == SessionStatus.REVIEWED ||
                         newStatus == SessionStatus.WITHDRAWN));
    
        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }
    
        session.setStatus(newStatus);
        repository.save(session);
        return "Feedback session status updated successfully.";
    }
}